package com.onwing.household.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.onwing.household.biz.dto.HouseHoldDto;
import com.onwing.household.biz.logic.facade.HouseHoldFacade;
import com.onwing.household.biz.request.HouseholdRequest;
import com.onwing.household.biz.response.HouseholdResponse;
import com.onwing.household.biz.response.PhotoResponse;
import com.onwing.household.comm.AppConstants;

@Controller
@RequestMapping("/addphotos")
public class PhotoController extends BaseController<PhotoController> {
	
	@Autowired
	private HouseHoldFacade householdfacade;

	/**
	 * 上传照片
	 */
	@RequestMapping(value = "/uploadPhotos.do", method = RequestMethod.POST)
	public @ResponseBody PhotoResponse addPhotos(HttpServletRequest servletRequest) throws Exception {
		HouseholdRequest householdRequest = new HouseholdRequest();
		PhotoResponse photoResponse = new PhotoResponse();
		HouseHoldDto householdDto = new HouseHoldDto();
		HouseholdResponse householdResponse = new HouseholdResponse();
		//创建解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				servletRequest.getSession().getServletContext());
		//检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(servletRequest)) {
			//将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) servletRequest;
			List<MultipartFile> fileList = multiRequest.getFiles("file");
			for (MultipartFile multipartFile : fileList) {
				if (multipartFile != null) {
					String path = System.getProperty("onwing.root") + multipartFile.getOriginalFilename();
/*					String [] strArray=multipartFile.getOriginalFilename().split("-");
					householdDto.setBuildingBlockNumber(strArray[0]);
					householdDto.setRoomNumber(strArray[1]);
					String [] strArrayParam=strArray[2].split(".");
					householdDto.setHouseholdName(strArrayParam[0]);
					householdRequest.setHouseholdDto(householdDto);
					householdResponse=householdfacade.queryHouseHold(householdRequest);
					householdRequest.setHouseholdDto(householdResponse.getHouseHoldDto());
					householdResponse=householdfacade.findHouseHoldById(householdRequest);
					householdDto.setId(householdResponse.getHouseHoldDto().getId());
                    householdRequest.setHouseholdDto(householdDto);
					householdfacade.updateHouseHold(householdRequest);*/
					//上传
					multipartFile.transferTo(new File(path));
				}
				photoResponse.setMessage(AppConstants.ADD_PHOTO_SUCCESS);

			}

		}
		return photoResponse;

	}

}
