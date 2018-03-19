package com.onwing.household.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onwing.household.biz.dto.HouseHoldDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.logic.core.HouseHoldBiz;
import com.onwing.household.comm.AppConstants;
import com.onwing.household.comm.dal.dao.HouseHoldMapper;
import com.onwing.household.comm.dal.model.Community;
import com.onwing.household.comm.dal.model.HouseHold;
import com.onwing.household.util.ModelUtil;

@Service
public class HouseHoldimpl implements HouseHoldBiz {
	
	private final Logger logger = LoggerFactory.getLogger(HouseHoldimpl.class);

	@Autowired
	private HouseHoldMapper householdMapper;


	@Override
	public boolean addHousehold(HouseHoldDto householdDto) throws BusinessException {
		boolean flag = false;
		HouseHold household;
		Community community = new Community();
		try {
			household = ModelUtil.dtoToModel(householdDto, HouseHold.class);
			community.setId(householdDto.getCommunityId());
			household.setCommunity(community);
			int i = householdMapper.insertSelective(household);
			if (i == 0) {
				throw new BusinessException(AppConstants.ADD_HOUSE_HOLD_FAIL_CODE,
						AppConstants.ADD_HOUSE_HOLD_FAIL_MESSAGE);
			}
			flag = true;
		} catch (BusinessException e) {
            logger.error("add household error", e);
			throw new BusinessException(AppConstants.ADD_HOUSE_HOLD_FAIL_CODE,
					AppConstants.ADD_HOUSE_HOLD_FAIL_MESSAGE);
		}
		return flag;
	}

	@Override
	public boolean removeHousehold(HouseHoldDto householdDto) throws BusinessException {
		boolean flag = false;
		try {
			int i = householdMapper.deleteByPrimaryKey(householdDto.getId());
			if (i == 0) {
				throw new BusinessException(AppConstants.REMOVE_HOUSE_HOLD_FAIL_CODE,
						AppConstants.REMOVE_HOUSE_HOLD_FAIL_MESSAGE);
			}
			flag = true;
		} catch (BusinessException e) {
            logger.error("remove household error", e);
			throw new BusinessException(AppConstants.REMOVE_HOUSE_HOLD_FAIL_CODE,
					AppConstants.REMOVE_HOUSE_HOLD_FAIL_MESSAGE);
		}
		return flag;
	}

	@Override
	public boolean updateHousehold(HouseHoldDto householdDto) throws BusinessException {
		boolean flag = false;
		HouseHold household;
		try {
			household = ModelUtil.dtoToModel(householdDto, HouseHold.class);
			int i = householdMapper.updateByPrimaryKeySelective(household);
			if (i == 0) {
				throw new BusinessException(AppConstants.UPDATE_HOUSE_HOLD_FAIL_CODE,
						AppConstants.UPDATE_HOUSE_HOLD_FAIL_MESSAGE);
			}
			flag = true;
		} catch (BusinessException e) {
            logger.error("update household error", e);
			throw new BusinessException(AppConstants.UPDATE_HOUSE_HOLD_FAIL_CODE,
					AppConstants.UPDATE_HOUSE_HOLD_FAIL_MESSAGE);
		}
		return flag;
	}

	@Override
	public List<HouseHoldDto> findHousehold(int startRow,int pageSize,String searchContent,Long communityId) throws BusinessException {
		List<HouseHoldDto> householdDtoList = null;
		try {
			householdDtoList = new ArrayList<HouseHoldDto>();
			List<HouseHold> householdList = householdMapper.getAllHouseHoldList(startRow, pageSize, searchContent,communityId, new HouseHold());
			if (householdList != null) {
				for (HouseHold houseHoldParam : householdList) {
					householdDtoList.add(ModelUtil.modelToDto(houseHoldParam, HouseHoldDto.class));
				}
			}
	    	
		} catch (Exception e) {
          logger.error("find household error", e);
		}
		return householdDtoList;
	}

	@Override
	public HouseHoldDto findHouseholdById(HouseHoldDto houseHoldDto) throws BusinessException {
		HouseHold houseHold;
		try {
			houseHold = householdMapper.selectByPrimaryKey(houseHoldDto.getId());
			houseHoldDto = ModelUtil.modelToDto(houseHold, HouseHoldDto.class);
			if (houseHold.getCommunity()==null) {
				throw new BusinessException(AppConstants.FIND_HOUSEHOLD_ID_FAIL_CODE,
						AppConstants.FIND_HOUSEHOLD_ID_FAIL_MESSAGE);
			}
			houseHoldDto.setCommunityId(houseHold.getCommunity().getId());
		} catch (Exception e) {
          logger.error("find household by Id error", e);
		}
		return houseHoldDto;
	}

	@SuppressWarnings("null")
	@Override
	public List<HouseHoldDto> queryHouseHold(HouseHoldDto houseHoldDto) throws BusinessException {
		List<HouseHoldDto> householdDtoList = null;
		HouseHold household;
		try {
			household = ModelUtil.dtoToModel(houseHoldDto, HouseHold.class);
			List<HouseHold> householdList = householdMapper.selectBySelective(household);
			if (householdList != null) {
				for (HouseHold houseHoldParam : householdList) {
					householdDtoList.add(ModelUtil.modelToDto(houseHoldParam, HouseHoldDto.class));
				}
			}
		} catch (Exception e) {
            logger.error("query household error", e);
		}
		return householdDtoList;
	}

	@Override
	public HouseHold findHouseHoldBySelective(HouseHold houseHold) throws BusinessException {
		List<HouseHold> hList=householdMapper.selectBySelective(houseHold);
        if (hList!=null) {
        	houseHold=hList.get(0);
			return houseHold;
		}
		return null;
	}


}
