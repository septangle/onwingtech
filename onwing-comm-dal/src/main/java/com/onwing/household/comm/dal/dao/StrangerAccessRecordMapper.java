package com.onwing.household.comm.dal.dao;

import com.onwing.household.comm.dal.model.StrangerAccessRecord;

public interface StrangerAccessRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StrangerAccessRecord record);

    int insertSelective(StrangerAccessRecord record);

    StrangerAccessRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StrangerAccessRecord record);

    int updateByPrimaryKey(StrangerAccessRecord record);
}