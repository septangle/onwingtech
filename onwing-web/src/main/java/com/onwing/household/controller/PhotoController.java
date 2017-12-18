package com.onwing.household.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.onwing.household.biz.response.PhotoResponse;
import com.onwing.household.comm.AppConstants;

@Controller
@RequestMapping("/addphotos")
public class PhotoController extends BaseController<PhotoController> {

	/**
	 * 上传照片
	 */
	@RequestMapping(value = "/uploadPhotos.do", method = RequestMethod.POST)
	public @ResponseBody PhotoResponse addPhotos(HttpServletRequest servletRequest) throws Exception {
		PhotoResponse photoResponse = new PhotoResponse();
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
					String path = "E:/springUpload/" + multipartFile.getOriginalFilename();
					//上传
					multipartFile.transferTo(new File(path));
				}
				photoResponse.setMessage(AppConstants.ADD_PHOTO_SUCCESS);

			}

		}
		return photoResponse;

	}

}
