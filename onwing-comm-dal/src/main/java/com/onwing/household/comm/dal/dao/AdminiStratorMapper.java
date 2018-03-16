package com.onwing.household.comm.dal.dao;

import java.util.List;

import com.onwing.household.comm.dal.model.AdminiStrator;
import com.onwing.household.comm.dal.model.UserRoleCommunity;

public interface AdminiStratorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AdminiStrator record);

    int insertSelective(AdminiStrator record);

    AdminiStrator selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminiStrator record);

    int updateByPrimaryKey(AdminiStrator record);
    
    
    /****************自定义查询*******************/
    
    List<AdminiStrator> selectBySelective(AdminiStrator record);
    
    AdminiStrator login(AdminiStrator record);
    
    List<UserRoleCommunity> selectbyAllUser();
}