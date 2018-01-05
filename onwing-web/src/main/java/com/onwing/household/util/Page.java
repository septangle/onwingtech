package com.onwing.household.util;

import javax.servlet.http.HttpServletRequest;

public class Page {

	private int page;//当前页
	private int pageSize;//页面大小
	private int count;//总行数
	private int pageCount;//总页数

	public Page(int page,int pageSize, int count){
		this.pageSize = pageSize;
		this.count = count;
		this.page = page;
		if(this.page < 1){
			this.page =1;
		}
		if(((this.count-1) / this.pageSize +1) < this.page){
			this.page = page-1;
		}
		this.pageCount = (count-1) / pageSize +1;
	}

	public Page() {
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if(this.page < 1){
			this.page =1;
		}
		if(((this.count-1) / this.pageSize +1) < this.page){
			this.page = page-1;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	/**
	 * 从request中获取page以及pageSize,以及获取到的count
	 * 封装成page对象
	 * @param request
	 * @param count
	 * @return
	 */
	public static Page getPageByRequest(HttpServletRequest req, int count){
		int page = 0, pageSize = 5;
		if(req.getParameter("page") !=null){
			page = Integer.parseInt(req.getParameter("page"));
		}
		if(req.getParameter("pageSize") != null){
			pageSize = Integer.parseInt(req.getParameter("pageSize"));
		}
		Page pageTool = new Page(page, pageSize, count);
		return pageTool;
	}
	
	
	
}
