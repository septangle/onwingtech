package com.onwing.household.comm.dal.dao;

import java.util.List;

import com.onwing.household.comm.dal.model.Camara;

public interface CamaraMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Camara record);

    int insertSelective(Camara record);

    Camara selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Camara record);

    int updateByPrimaryKey(Camara record);
    
    /****************自定义查询*******************/
    List<Camara> selectBySelective(Camara camara);
    List<Camara> getAllCamara();
}