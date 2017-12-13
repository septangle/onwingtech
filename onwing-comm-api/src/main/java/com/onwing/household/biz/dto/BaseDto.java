package com.onwing.household.biz.dto;

import java.io.Serializable;

public class BaseDto implements Serializable {
	private static final long serialVersionUID = -1470795469324417184L;

	private int pageNum;//当前�?
	private int pageSize = 10;//每页多少条数
	private long total;//总记录数

	private Integer profileId = null;//用户ID
	private Integer operaId = null;//操作人ID
	private String operaName = null;//操作人姓�?

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public Integer getOperaId() {
		return operaId;
	}

	public void setOperaId(Integer operaId) {
		this.operaId = operaId;
	}

	public String getOperaName() {
		return operaName;
	}

	public void setOperaName(String operaName) {
		this.operaName = operaName;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {

		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}
