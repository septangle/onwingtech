package com.onwing.household.job;

import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onwing.household.biz.logic.core.StrangerAccessRecordBiz;

@Component("accessRecordTask")
public class AccessRecordTask {

	private final static Logger logger = LoggerFactory.getLogger(AccessRecordTask.class);

	@Autowired
	StrangerAccessRecordBiz stRecordBiz;

	@Resource
	private Map<String, String> accessRecordProperties;

	public void startAccessRecordTask() {
		logger.info("清理acessRecord数据开始...");
		try {
			int numberDay = Integer.parseInt(accessRecordProperties.get("numberDay"));
			// 执行业务方法
			stRecordBiz.startCleanUpAccessRecord(numberDay);
		} catch (Exception e) {
			logger.error("清理数据失败", e);
		}
		logger.info("清理accessRecord数据结束...");

	}

}
