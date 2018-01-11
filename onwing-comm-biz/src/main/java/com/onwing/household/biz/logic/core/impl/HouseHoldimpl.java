package com.onwing.household.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onwing.household.biz.dto.HouseHoldDto;
import com.onwing.household.biz.exception.BusinessException;
import com.onwing.household.biz.logic.core.HouseHoldBiz;
import com.onwing.household.comm.AppConstants;
import com.onwing.household.comm.dal.dao.HouseHoldMapper;
import com.onwing.household.comm.dal.model.HouseHold;
import com.onwing.household.util.ModelUtil;

@Service
public class HouseHoldimpl implements HouseHoldBiz {

	@Autowired
	private HouseHoldMapper householdMapper;

	@Override
	public boolean addHousehold(HouseHoldDto householdDto) throws BusinessException {
		boolean flag = false;
		HouseHold household;
		try {
			household = ModelUtil.dtoToModel(householdDto, HouseHold.class);
			int i = householdMapper.insertSelective(household);
			if (i == 0) {
				throw new BusinessException(AppConstants.ADD_HOUSE_HOLD_FAIL_CODE,
						AppConstants.ADD_HOUSE_HOLD_FAIL_MESSAGE);
			}
			flag = true;
		} catch (BusinessException e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
			throw new BusinessException(AppConstants.UPDATE_HOUSE_HOLD_FAIL_CODE,
					AppConstants.UPDATE_HOUSE_HOLD_FAIL_MESSAGE);
		}
		return flag;
	}

	@Override
	public List<HouseHoldDto> findHousehold(int startRow,int pageSize,String strFile) throws BusinessException {
		List<HouseHoldDto> householdDtoList = null;
		try {
			householdDtoList = new ArrayList<HouseHoldDto>();
			List<HouseHold> householdList = householdMapper.getAllHouseHold(startRow, pageSize, null);
			String path = System.getProperty("onwing.root");
			if (householdList != null) {
				for (HouseHold houseHoldParam : householdList) {
					houseHoldParam.setPhotoId(path+houseHoldParam.getPhotoId());
					householdDtoList.add(ModelUtil.modelToDto(houseHoldParam, HouseHoldDto.class));
				}
			}
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return householdDtoList;
	}

	@Override
	public HouseHoldDto findHouseholdById(HouseHoldDto houseHoldDto) throws BusinessException {
		HouseHold houseHold;
		try {
			houseHold = householdMapper.selectByPrimaryKey(houseHoldDto.getId());
			System.out.println(houseHold.getGender());
			houseHoldDto = ModelUtil.modelToDto(houseHold, HouseHoldDto.class);
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return householdDtoList;
	}

}
