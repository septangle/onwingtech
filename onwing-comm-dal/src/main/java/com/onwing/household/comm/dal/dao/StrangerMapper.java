package com.onwing.household.comm.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.onwing.household.comm.dal.model.Stranger;

public interface StrangerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Stranger record);

    int insertSelective(Stranger record);

    Stranger selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Stranger record);

    int updateByPrimaryKey(Stranger record);
    
    /*************自定义查询**************/
    
    List<Stranger> selectBySelective(Stranger record);
    
    int updateStrangerByIdentify(Stranger record);
    
    List<Stranger> getAllStranger(@Param("startRow")int startRow,
			@Param("pageSize")int pageSize,@Param("entity")Stranger record);
    
    int getCountByStranger(Stranger record);
    
}