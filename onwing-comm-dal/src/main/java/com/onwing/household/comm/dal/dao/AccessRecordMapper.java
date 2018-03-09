package com.onwing.household.comm.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.onwing.household.comm.dal.model.AccessRecord;
import com.onwing.household.comm.dal.model.HouseAccessRecord;

public interface AccessRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AccessRecord record);

    int insertSelective(AccessRecord record);

    AccessRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccessRecord record);

    int updateByPrimaryKey(AccessRecord record);
    
   /*************自定义查询******************/
    
    List<HouseAccessRecord> selectAccessRecord(@Param("startRow")int startRow,
			@Param("pageSize")int pageSize,@Param("communityId")Long communityId,@Param("entity")HouseAccessRecord houseAccessRecord);
    
    //查询住户出入记录总数
    int  getCountByHouseHold();
    
    List<AccessRecord> getAccessRecordByDate(@Param("numberDay")int numberDay);
    
    int delAccessRecordByDate(@Param("numberDay")int numberDay);
}