package com.onwing.household.comm.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.onwing.household.comm.dal.model.HouseHold;

public interface HouseHoldMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HouseHold record);

    int insertSelective(HouseHold record);

    HouseHold selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HouseHold record);

    int updateByPrimaryKey(HouseHold record);
    
    /****************自定义查询*******************/
    
    List<HouseHold> selectBySelective(HouseHold record);
    
    List<HouseHold> getAllHouseHold(@Param("startRow")int startRow,
			@Param("pageSize")int pageSize,@Param("entity")HouseHold houseHold);
    
    int getCountByHousehold();
}