package com.onwing.household.comm.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.onwing.household.comm.dal.model.StrangerAccessRecord;
import com.onwing.household.comm.dal.model.StrangerAccessRecordMap;

public interface StrangerAccessRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StrangerAccessRecord record);

    int insertSelective(StrangerAccessRecord record);

    StrangerAccessRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StrangerAccessRecord record);

    int updateByPrimaryKey(StrangerAccessRecord record);
    
    /****************自定义查询*******************/
    List<StrangerAccessRecordMap> getAllStrangerAccessRecord(@Param("startRow")int startRow,
			@Param("pageSize")int pageSize,@Param("entity")StrangerAccessRecordMap sAccessRecordMap);
    
    int getCountByStrangerAcessRecord();

}