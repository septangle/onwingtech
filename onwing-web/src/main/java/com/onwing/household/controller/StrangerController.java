package com.onwing.household.controller;

import java.io.File;

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
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 访客登记
 * @author zx
 *
 */
@Controller
@RequestMapping("/stranger")
public class StrangerController {

	@Autowired
	private StrangerFacade strangerFacade;

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
		String path = System.getProperty("onwing.root")/*+AppConstants.STRANGER_FILE_PATH*/;
		System.out.println(path);
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, file.getOriginalFilename()));
		StrangerDto strangerDto = new StrangerDto();
		StrangerRequest strangerRequest = new StrangerRequest();
		strangerDto.setName(name);
		strangerDto.setIdentifyCard(identifyCard);
		strangerDto.setTel(tel);
		strangerDto.setReason(reason);
		strangerDto.setFileUrl(file.getOriginalFilename());
		strangerRequest.setStrangerDto(strangerDto);
		return strangerFacade.addStranger(strangerRequest);
	}
}
