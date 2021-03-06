/** 
 * 
 * Copyright (c) 2015-2015 ele.me,Inc.All Rights Reserved.
 */
package com.onwing.household.biz.response;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseResponse implements Serializable {

	private Error error = null;
	
	private String message;	
	
	private String code;
	
	private int totalNumber;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}



}
