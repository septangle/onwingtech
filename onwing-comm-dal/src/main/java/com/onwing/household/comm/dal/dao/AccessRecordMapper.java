package com.onwing.household.comm.dal.dao;

import com.onwing.household.comm.dal.model.AccessRecord;

public interface AccessRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AccessRecord record);

    int insertSelective(AccessRecord record);

    AccessRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccessRecord record);

    int updateByPrimaryKey(AccessRecord record);
}