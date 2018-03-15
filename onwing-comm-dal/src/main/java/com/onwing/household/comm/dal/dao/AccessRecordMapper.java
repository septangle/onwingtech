package com.onwing.household.comm.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.onwing.household.comm.dal.model.AccessRecord;
import com.onwing.household.comm.dal.model.HouseAccessRecord;

public interface AccessRecordMapper {
	int deleteByPrimaryKey(Long id);

	int insertSelective(AccessRecord record);

	AccessRecord selectByPrimaryKey(Long id);

	/************* 自定义查询 ******************/

	List<AccessRecord> selectAccessRecord(@Param("startRow") int startRow, @Param("pageSize") int pageSize,
			@Param("searchContent") String searchContent, @Param("entity") AccessRecord accessRecord);

	// 查询住户出入记录总数
	int getCountByHouseHold();

	int getTotalCountBySearchContent(@Param("searchContent") String searchContent,
			@Param("entity") AccessRecord accessRecord);

	List<AccessRecord> getAccessRecordByDate(@Param("numberDay") int numberDay);

	int delAccessRecordByDate(@Param("numberDay") int numberDay);
}