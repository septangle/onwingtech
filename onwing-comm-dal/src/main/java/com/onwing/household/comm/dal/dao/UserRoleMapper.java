package com.onwing.household.comm.dal.dao;

import org.apache.ibatis.annotations.Param;

import com.onwing.household.comm.dal.model.UserRole;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);
    
    /****************自定义查询*******************/

    UserRole selectByUserId(@Param("userId")Long userId);
}