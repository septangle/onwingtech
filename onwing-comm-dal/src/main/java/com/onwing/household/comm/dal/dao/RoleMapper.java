package com.onwing.household.comm.dal.dao;

import java.util.List;

import com.onwing.household.comm.dal.model.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    /****************自定义查询*******************/
    
    List<Role> selectBySelective(Role record);
}