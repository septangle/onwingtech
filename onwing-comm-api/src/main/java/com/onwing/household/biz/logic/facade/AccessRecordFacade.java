package com.onwing.household.biz.logic.facade;

import com.onwing.household.biz.response.AccessRecordResponse;

public interface AccessRecordFacade {

	/**
	 * 出入记录
	 */
	public AccessRecordResponse findAllAccessRecord(int startRow, int pageSize, int count, String searchContent,
			String communityId) throws Exception;

}
