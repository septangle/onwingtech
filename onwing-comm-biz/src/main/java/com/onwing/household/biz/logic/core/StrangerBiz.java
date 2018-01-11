package com.onwing.household.biz.logic.core;

import java.util.List;

import com.onwing.household.biz.dto.StrangerDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.comm.dal.model.Stranger;

/**
 * 访客信息
 * @author zx
 *
 */
public interface StrangerBiz {
	
	/**
	 * 新增stranger
	 * @param stranger
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean addStranger(StrangerDto strangerDto) throws BusinessException;
	
	/**
	 * 查询stranger
	 * @param stranger
	 * @return list<strangerDto>
	 * @throws BusinessException
	 */
	public List<StrangerDto> findAllStranger(int page,int pageSize) throws BusinessException;
	
	/**
	 * 更新Stranger
	 * @param identify_card
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean updateStrangerByIdentify(StrangerDto strangerDto) throws BusinessException;
	
	/**
	 * 根据身份证查询访客
	 * @param identifyCard
	 * @return
	 * @throws BusinessException
	 */
	public Stranger findStrangerByIdentifyCard(String identifyCard) throws BusinessException;
}
