package com.onwing.household.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.csvreader.CsvReader;
import com.onwing.household.base.CsvTopologyNode;
import com.onwing.household.biz.dto.HouseHoldDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.request.HouseholdRequest;
import com.onwing.household.biz.response.CommunityResponse;
import com.onwing.household.biz.response.Error;
import com.onwing.household.biz.response.HouseholdResponse;
import com.onwing.household.comm.AppConstants;
import com.onwing.household.comm.dal.dao.CommunityMapper;
import com.onwing.household.comm.dal.model.Community;
import com.onwing.socket.client.CplusClient;
import com.onwing.socket.client.QueryCardRecordsThread;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController<CommunityController> {
	private final static Logger logger = LoggerFactory.getLogger(CommunityController.class);

	@Autowired
	private CommunityMapper communityMapper;

	/**
	 * 创建小区
	 */
	@ApiOperation(value = "创建小区", httpMethod = "POST", response = CommunityResponse.class)
	@RequestMapping(value = "/createCommunity.do", method = RequestMethod.POST)
	public @ResponseBody CommunityResponse createCommunity(@RequestParam("name") String name,
			@RequestParam("address") String address, HttpServletRequest servletRequest) throws Exception {
		Community community = new Community();
		community.setName(name);
		List<Community> oldCommunityList = communityMapper.selectBySelective(community);
		if (oldCommunityList!=null && oldCommunityList.size()>0) {
			logger.error("community name: {} already existed in db", name);
			throw new BusinessException(AppConstants.COMMUNITY_NAME_EXISTED_CODE,
					AppConstants.COMMUNITY_NAME_EXISTED_MESSAGE);
		}
		
		community.setAddress(address);
		communityMapper.insertSelective(community);
		logger.info("createCommunity done! : {}", name);
		return new CommunityResponse();
	}

	/**
	 * 从csv中导入某小区楼栋、房间列表
	 */
	@ApiOperation(value = "从csv导入小区楼栋等信息", httpMethod = "POST", response = CommunityResponse.class)
	@RequestMapping(value = "/importRoomsFromCsv.do", method = RequestMethod.POST)
	public @ResponseBody CommunityResponse importRoomsFromCsv(@RequestParam("communityId") String communityId,
			@RequestParam MultipartFile file, HttpServletRequest servletRequest) throws Exception {
		synchronized (CommunityController.class) {
			String path = System.getProperty("onwing.root") + AppConstants.CSV_PATH;
			File files = new File(path);
			if (!files.exists() && !files.isDirectory()) {
				files.mkdir();
			}
			String tmpFileName = file.getOriginalFilename();// 上传的csv文件名
			String extension = tmpFileName.substring(tmpFileName.lastIndexOf("."));
			String nowFileName = communityId + extension;
			File nowFile = new File(path, nowFileName);
			FileUtils.copyInputStreamToFile(file.getInputStream(), nowFile);

			// 读取csv文件，将拓扑存入数据库
			readCsv(nowFile.getAbsolutePath(), communityId);
			// end
			return new CommunityResponse();
		}
	}

	/**
	 * 根据父节点获取子节点列表：比如 根据楼栋名称: /小区id/2号楼 获取该楼栋下 单元列表； 或者根据楼层名称： /小区id/2号楼/1单元/5层
	 * 获取该层所有的房间列表
	 */
	@ApiOperation(value = "获取小区拓扑，根据父节点查子节点列表", httpMethod = "GET", response = CommunityResponse.class)
	@RequestMapping(value = "/getChildNodes.do", method = RequestMethod.GET)
	public @ResponseBody CommunityResponse getChildNodes(HttpServletRequest servletRequest) throws Exception {
		String parentNodePath = servletRequest.getParameter("parentNodePath");
		String[] parentNodePathList = parentNodePath.split("/");
		String communityId = parentNodePathList[1];
		String communityPath = System.getProperty("onwing.root") + "community_" + communityId;

		String parentNodeFullPath = null;
		if (parentNodePathList.length == 2) { // 查询某小区的所有楼栋
			parentNodeFullPath = communityPath;
		} else {
			int otherPathIndex = parentNodePath.indexOf("/", 1); // 除去小区id后
																	// parentNodePath的剩余部分
			String otherPath = parentNodePath.substring(otherPathIndex);
			parentNodeFullPath = communityPath + otherPath;
		}
		logger.info("parentNodeFullPath is: {}", parentNodeFullPath);

		CommunityResponse response = new CommunityResponse();
		File parentNodeFullPathFile = new File(parentNodeFullPath);
		if (!parentNodeFullPathFile.exists() || !parentNodeFullPathFile.isDirectory()) {
			logger.error("parentNodeFullPathFile: {} isnot existed or isDirectory", parentNodeFullPathFile);
			response.setError(new Error(AppConstants.COMMUNITY_PARENT_NODE_NOT_EXISTED,
					AppConstants.COMMUNITY_PARENT_NODE_NOT_EXISTED_MESSAGE));
			response.setCode(AppConstants.FAIL_CODE);
			return response;
		}

		File[] subFiles = parentNodeFullPathFile.listFiles();
		ArrayList<String> childNodeNameList = new ArrayList<String>();
		for (File subFile : subFiles) {
			if (subFile.isDirectory()) {
				childNodeNameList.add(subFile.getName());
			}
		}
		response.setChildNodeNameList(childNodeNameList);
		response.setCode(AppConstants.SUCCESS_CODE);
		return response;
	}

	private void readCsv(String csvPath, String communityId) {
		String communityPath = System.getProperty("onwing.root") + "community_" + communityId;
		// 判断communityPath是否存在，如果存在，则删除该目录，目的：适配csv的重新导入
		File communityPathFile = new File(communityPath);
		if (communityPathFile.exists() && communityPathFile.isDirectory()) {
			try {
				FileUtils.deleteDirectory(communityPathFile);
			} catch (IOException e) {
				logger.error("delete folder: {} failed", communityPathFile, e);
			}
		}

		CsvReader csvReader = null;
		try {
			// 创建CSV读对象
			csvReader = new CsvReader(csvPath, ',', Charset.forName("utf-8"));
			// 读表头
			csvReader.readHeaders();
			while (csvReader.readRecord()) {
				// 读一整行
				// System.out.println(csvReader.getRawRecord());
				// 读这行的某一列
				String buildingName = csvReader.get(0);
				String buildingCellName = csvReader.get(1);
				String floorName = csvReader.get(2);
				String roomName = csvReader.get(3);
				// 构造room的完整path
				String roomPath = communityPath
						+ String.format("/%s/%s/%s/%s", buildingName, buildingCellName, floorName, roomName);
				File roomPathFile = new File(roomPath);
				if (!roomPathFile.exists() && !roomPathFile.isDirectory()) {
					roomPathFile.mkdirs();// 注意与mkdir的区别，mkdirs可以创建不存在的父目录
				}

			}
			logger.info("read community topology: {} from csv: {} done!", communityId, csvPath);

		} catch (IOException e) {
			logger.error("read community topology from csv error", e);
		} finally {
			csvReader.close();
		}
	}
}
