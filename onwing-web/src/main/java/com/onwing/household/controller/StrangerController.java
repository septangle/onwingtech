package com.onwing.household.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.onwing.household.biz.dto.StrangerDto;
import com.onwing.household.biz.logic.facade.StrangerFacade;
import com.onwing.household.biz.request.StrangerRequest;
import com.onwing.household.biz.response.StrangerResponse;
import com.onwing.household.comm.AppConstants;
import com.onwing.household.comm.dal.dao.StrangerMapper;
import com.onwing.household.util.Page;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 访客登记
 * @author zx
 *
 */
@Controller
@RequestMapping("/stranger")
public class StrangerController extends BaseController<StrangerController>{

	@Autowired
	private StrangerFacade strangerFacade;
	
	@Autowired
	private StrangerMapper strangerMapper;

	/**
	 * 录入访客信息
	 */
	@ApiOperation(value = "录入访客信息", httpMethod = "POST", response = StrangerResponse.class)
	@RequestMapping(value = "/addStranger.do", method = RequestMethod.POST)
	public @ResponseBody StrangerResponse addStranger(@RequestParam("name") String name,
			@RequestParam("sex") String sex, @RequestParam("identifyCard") String identifyCard,
			@RequestParam("tel") String tel, @RequestParam("reason") String reason,
			@RequestParam("remarks") String remarks, @RequestParam MultipartFile file,
			HttpServletRequest servletRequest) throws Exception {
		String strNowTime = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		String path = System.getProperty("onwing.root") + AppConstants.STRANGER_FILE_PATH+identifyCard;
		File files = new File(path);
		if (!files.exists() && !files.isDirectory()) {
			files.mkdir();
		}
		String  tmpFileName=file.getOriginalFilename();//上传的文件名			
		String extension = tmpFileName.substring(tmpFileName.lastIndexOf("."));
		String nowFileName=strNowTime+extension;
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path,nowFileName));
		StrangerDto strangerDto = new StrangerDto();
		StrangerRequest strangerRequest = new StrangerRequest();
		strangerDto.setName(name);
		strangerDto.setSex(sex);
		strangerDto.setIdentifyCard(identifyCard);
		strangerDto.setTel(tel);
		strangerDto.setReason(reason);
		strangerDto.setRemarks(remarks);
		strangerRequest.setStrangerDto(strangerDto);
		return strangerFacade.addStranger(strangerRequest,AppConstants.STRANGER_FILE_PATH+identifyCard+"/"+ nowFileName);
	}

	/**
	 * 查询访客列表
	 */
	@ApiOperation(value = "查询访客列表", httpMethod = "GET", response = StrangerResponse.class)
	@RequestMapping(value = "/getAllStranger.do", method = RequestMethod.GET)
	public @ResponseBody StrangerResponse findStranger(HttpServletRequest servletRequest) throws Exception {
		int count =strangerMapper.getCountByStranger(null);
 		Page pageTool=Page.getPageByRequest(servletRequest,count);
 		int startRow=(pageTool.getPage()-1) * Integer.parseInt(servletRequest.getParameter("pageSize"));
		return strangerFacade.findAllStranger(startRow,pageTool.getPageSize());

	}

	/**
	 * 记录访客离开
	 */
	@ApiOperation(value = "记录访客离开", httpMethod = "POST", response = StrangerResponse.class)
	@RequestMapping(value = "/getStrangerLevel.do", method = RequestMethod.POST)
	public @ResponseBody StrangerResponse updateStrangerByIdentify(@RequestParam("identifyCard") String identifyCard,
			@RequestParam MultipartFile file, HttpServletRequest servletRequest) throws Exception {
		StrangerRequest strangerRequest = new StrangerRequest();
		StrangerDto strangerDto = new StrangerDto();
		String strNowTime = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		String path = System.getProperty("onwing.root") + AppConstants.STRANGER_FILE_PATH+identifyCard;
		File files  = new File(path);
		if  (!files .exists()  && !files .isDirectory())      
		{       
		    files .mkdir();    
		}
		String  tmpFileName=file.getOriginalFilename();//上传的文件名			
		String extension = tmpFileName.substring(tmpFileName.lastIndexOf("."));
		String nowFileName=strNowTime+extension;
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path,nowFileName));
		strangerDto.setIdentifyCard(identifyCard);
		strangerRequest.setStrangerDto(strangerDto);
		return strangerFacade.updateStrangerByIdentify(strangerRequest,AppConstants.STRANGER_FILE_PATH+identifyCard+"/"+nowFileName);

	}

}
