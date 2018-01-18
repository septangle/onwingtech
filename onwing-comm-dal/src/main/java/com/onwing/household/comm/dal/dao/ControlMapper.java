package com.onwing.household.comm.dal.dao;

import java.util.List;
import com.onwing.household.comm.dal.model.Control;

public interface ControlMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Control record);

    int insertSelective(Control record);

    Control selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Control record);

    int updateByPrimaryKey(Control record);
    
    List<Control> getAllControl();
}