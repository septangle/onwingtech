package com.onwing.household.comm.dal.dao;

import com.onwing.household.comm.dal.model.UserRole;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}