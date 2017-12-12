/** 
 * 
 * Copyright (c) 2015-2015 ele.me,Inc.All Rights Reserved.
 */
package com.onwing.household.biz.request;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseRequest implements Serializable {

	/** 签名 */
	private String sign;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
