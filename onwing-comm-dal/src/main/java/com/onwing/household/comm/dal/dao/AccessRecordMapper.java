package com.onwing.household.comm.dal.dao;

import java.util.List;
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
    
    List<HouseAccessRecord> selectAccessRecord(HouseAccessRecord houseAccessRecord);
}