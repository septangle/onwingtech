package com.onwing.household.comm.dal.dao;

import com.onwing.household.comm.dal.model.Stranger;

public interface StrangerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Stranger record);

    int insertSelective(Stranger record);

    Stranger selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Stranger record);

    int updateByPrimaryKey(Stranger record);
}