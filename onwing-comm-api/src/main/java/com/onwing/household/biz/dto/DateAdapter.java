package com.onwing.household.biz.dto;

import java.text.ParseException;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateAdapter extends XmlAdapter<String, Date> {

	private static final String DATEPATTERNS = "yyyy-MM-dd HH:mm:ss";

	@Override
	public String marshal(Date v) {
		return DateFormatUtils.format(v, DATEPATTERNS);
	}

	@Override
	public Date unmarshal(String v) throws Exception {
		try {
			return DateUtils.parseDate(v, DATEPATTERNS);
		} catch (ParseException e) {
			throw new Exception();
		}
	}

}
