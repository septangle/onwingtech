package com.onwing.household.biz.response;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Error implements Serializable {
	private String code;
	private String message;

	public Error() {

	}

	public Error(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
