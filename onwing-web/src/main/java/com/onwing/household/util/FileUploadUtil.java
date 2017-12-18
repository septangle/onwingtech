package com.onwing.household.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	public static boolean upload(MultipartFile[] files) throws IOException {
		boolean flag = false;
		String strFilePath = "/onwing-web/target/webapp/image";
		for (int i = 0; i < files.length; i++) {
			if (files[i].isEmpty()) {
				System.out.println("上传文件[" + i + "]为空");
			} else {
				FileUtils.copyInputStreamToFile(files[i].getInputStream(),
						new File(strFilePath, files[i].getOriginalFilename()));
				flag = true;
			}
		}
		return flag;

	}

}
