package com.onwing.household.comm;

public class AppConstants {

	public final static String SYSTEM_ERROR_CODE = "10000001"; //系统异常
	public final static String SYSTEM_ERROR_MESSAGE = "系统异常";

	public final static String SUCCESS_CODE = "1"; //成功标记
	public final static String FAIL_CODE = "-1"; //失败标记

	//login模块 1002
	public final static String QUERY_LOGIN_USER_ERROR_CODE = "10020001";
	public final static String QUERY_LOGIN_USER_ERROR_MESSAGE = "用户登录失败:账号或密码错误！";
	public final static String QUERY_LOGIN_USERLOING_ERROR_CODE = "10010002";
	public final static String QUERY_LOGIN_USERLOING_ERROR_MESSAGE = "该用户已登录！";
	public final static String NOT_LOGIN_CODE="10010003";
	public final static String NOT_LOGIN_MESSAGE="该用户未登录";
	public final static String EXISTED_CODE="10010004";
	public final static String EXISTED_MESSAGE="用户名已经存在";
	public final static String ROLE_EXISTED_CODE="10010005";
	public final static String ROLE_EXISTED_MESSAGE="角色名不存在或有重复";
	public final static String NAME_MULTI_OR_NULL_CODE="10010006";
	public final static String NAME_MULTI_OR_NULL_MESSAGE="用户名有重复或不存在";

	//addHousehold 1003
	public final static String ADD_HOUSE_HOLD_FAIL_CODE = "10030001";
	public final static String ADD_HOUSE_HOLD_FAIL_MESSAGE = "住户信息添加失败";
	public final static String ADD_HOUSE_HOLD_SUCCESS_MESSAGE = "住户信息添加成功";

	//removeHousehold 1004
	public final static String REMOVE_HOUSE_HOLD_FAIL_CODE = "10040001";
	public final static String REMOVE_HOUSE_HOLD_FAIL_MESSAGE = "住户信息删除失败";
	public final static String REMOVE_HOUSE_HOLD_SUCCESS_MESSAGE = "住户信息删除成功";

	//updateHousehold 1005
	public final static String UPDATE_HOUSE_HOLD_FAIL_CODE = "10050001";
	public final static String UPDATE_HOUSE_HOLD_FAIL_MESSAGE = "住户信息更新失败";
	public final static String UPDATE_HOUSE_HOLD_SUCCESS_MESSAGE = "住户信息更新成功";
	
	//Community module 1006
	public final static String COMMUNITY_PARENT_NODE_NOT_EXISTED = "10060001";
	public final static String COMMUNITY_PARENT_NODE_NOT_EXISTED_MESSAGE = "小区：父节点不存在";
	
	//addphoto
	public final static String ADD_PHOTO_SUCCESS="上传成功";
	public final static String FILE_PATH="householdImage/";
	public final static String CSV_PATH="csv/";
	public final static String STRANGER_FILE_PATH="strangerImage/";
	
	//addStranger
	public final static String ADD_STRANGER_SUCCESS_MESSAGE="访客添加成功";
	public final static String ADD_STRANGER_FAIL_MESSAGE="访客添加失败";
	
	//updateStranger
	public final static String UPDATE_STRANGER_SUCCESS_MESSAGE="操作成功";
	public final static String UPDATE_STRANGER_FAIL_MESSAGE="操作失败";

	

}
