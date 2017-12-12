/** 
 * 
 * Copyright (c) 2015-2015 ele.me,Inc.All Rights Reserved.
 */
package com.onwing.household.biz.response;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseResponse implements Serializable {

	private Error error = null;

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}



}
