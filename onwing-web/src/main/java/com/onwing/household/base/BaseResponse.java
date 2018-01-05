package com.onwing.household.base;


import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseResponse implements Serializable {

	private Error error = null;

	private int page;

	private int pageCount;

	private int pageSize;

	private int total;

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public int getPage() {
		return page;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setPage(String page, int total, int pageSize) {
		this.total = total;
		this.pageSize = pageSize;
		this.pageCount = (int) ((this.total + this.pageSize - 1) / this.pageSize);

		if (page == null) {
			this.page = 1;
		} else {
			try {
				this.page = Integer.parseInt(page);
				if (this.page < 1) {
					this.page = pageCount;
				} else if (this.page > pageCount) {
					//	this.page = 1;
				}
			} catch (Exception e) {
				this.page = 1;
			}
		}
	}
}
