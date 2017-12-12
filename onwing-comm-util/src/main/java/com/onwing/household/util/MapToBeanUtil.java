package com.onwing.household.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MapToBeanUtil {

	/**
	 * 日志
	 */
	private static final Log LOGGER = LogFactory.getLog(MapToBeanUtil.class);

	public static Object convertMap(Class<?> type, Map<?, ?> map) throws IntrospectionException,
			IllegalAccessException, InstantiationException, InvocationTargetException {
		Object obj = type.newInstance(); // 创建 JavaBean 对象
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				try {
					Object value = null;
					Class<?> propertyType = descriptor.getPropertyType();
					if (propertyType.getName().equals("java.util.Date")) {
						String dateStr = (String) map.get(propertyName);
						if (StringUtils.isNotBlank(dateStr)) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							value = sdf.parse(dateStr);
							LOGGER.info(descriptor.getClass());
						}
					} else {
						value = map.get(propertyName);
					}
					Object[] args = new Object[1];
					args[0] = value;
					descriptor.getWriteMethod().invoke(obj, args);
				} catch (Exception e) {
					LOGGER.error(e);
				}
			}
		}
		return obj;
	}

}
