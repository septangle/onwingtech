package com.onwing.household.comm.dal.dao;

import java.util.List;

import com.onwing.household.comm.dal.model.Community;

public interface CommunityMapper {

	Community selectByPrimaryKey(Long id);

	int insertSelective(Community record);

	/**************** 自定义查询 *******************/

	List<Community> selectBySelective(Community record);

}
