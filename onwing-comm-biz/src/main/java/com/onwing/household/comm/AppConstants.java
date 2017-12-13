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

}
