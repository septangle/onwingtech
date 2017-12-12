package com.unre.photo.controller;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

public class BaseTest {

	public static final String url_prefix = "http://127.0.0.1:8080/onwing-web/";

	public static String postRequest(String urlSuffix, String json) {
		System.out.println("url=========" + url_prefix + urlSuffix);
		System.out.println("json==========" + json);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url_prefix + urlSuffix);
		StringEntity postData = new StringEntity(json, "utf-8");
		postData.setContentEncoding("UTF-8");
		postData.setContentType("application/json");
		String result = "";
		CloseableHttpResponse response = null;
		try {
			String sign = "";
			Header head = new BasicHeader("Authorization", sign);
			httpPost.setHeader(head);
			httpPost.setEntity(postData);
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
			System.out.println("result============" + result);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}
}
