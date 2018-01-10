package com.onwing.household.biz.logic.core.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
			household.setPhotoId(householdDto.getBuildingBlockNumber() + "-" + householdDto.getRoomNumber() + "-"
					+ householdDto.getHouseholdName());
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
			if (householdList != null) {
				for (HouseHold houseHoldParam : householdList) {
					householdDtoList.add(ModelUtil.modelToDto(houseHoldParam, HouseHoldDto.class));
				}
			}
	    	HashMap<String, String> testMap =new HashMap<String, String>();
	    	//查找指定路径
	    	File root = new File(strFile); 
	    	//存入集合
	        File[] files = root.listFiles();
	        //格式key=12-46-李四    value=12-46-李四.jpg
	        if (files!=null) {
	        	  for (File file : files) { 
	  	        	String [] fileKey =file.getName().split("\\.");
	  	        	testMap.put(fileKey[0], file.getName());
	  	        }  
	  	        //循环业主信息
	  	        for (int i = 0; i < householdDtoList.size(); i++) {
	  	        	HouseHoldDto houses =householdDtoList.get(i);
	  	        	String photoId=houses.getPhotoId();
	  	        	if (testMap.containsKey(photoId)) {
	  	        		householdDtoList.get(i).setPhotoUrl(AppConstants.FILE_PATH+testMap.get(photoId));
	  	        	}
	  				
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
