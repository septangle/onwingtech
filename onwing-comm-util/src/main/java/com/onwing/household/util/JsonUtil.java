package com.onwing.household.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class JsonUtil {

	private static final Log LOGGER = LogFactory.getLog(JsonUtil.class);

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 把json字符串转成java对象
	 * @throws Exception 
	 */
	public static <T> T toObject(String json, Class<T> objectClass) throws Exception {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		try {
			T result = mapper.readValue(json.getBytes("utf-8"), objectClass);
			return result;
		} catch (JsonParseException | UnrecognizedPropertyException ex) {
			LOGGER.error("JSON解析为[" + ClassUtils.getShortClassName(objectClass) + "]出错!", ex);
			throw new Exception("json to object error!");
		} catch (IOException ex) {
			LOGGER.error("json to object error", ex);
			throw new Exception("");
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> toMap(String json) throws Exception {
		if (StringUtils.isEmpty(json)) {
			return new HashMap<>();
		}

		try {
			JsonNode node = mapper.readTree(json);
			return mapper.convertValue(node, Map.class);
		} catch (IOException ex) {
			LOGGER.error("json to map error", ex);
			throw new Exception("json to map error!");
		}
	}

	public static <T> List<T> toObjectList(String json, Class<T> objectClass) throws Exception {
		if (StringUtils.isEmpty(json)) {
			return Collections.emptyList();
		}

		try {
			List<T> result = new ArrayList<T>();
			JsonNode listNode = mapper.readTree(json);
			for (JsonNode objNode : listNode) {
				T t = mapper.convertValue(objNode, objectClass);
				result.add(t);
			}

			return result;
		} catch (JsonParseException | UnrecognizedPropertyException ex) {
			LOGGER.error("JSON解析为[" + ClassUtils.getShortClassName(objectClass) + "]出错!", ex);
			throw new Exception("json to object list error!");
		} catch (IOException e) {
			LOGGER.error("Json format error", e);
			throw new Exception("json to object list error!");
		}
	}

	/**
	 * 把对象转成json字符串
	 * @throws Exception 
	 */
	public static String toJson(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}

		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOGGER.error("object to json error!", e);
			throw new Exception("object to json error!", e);
		} catch (Exception e) {
			LOGGER.error("object to json error!", e);
			throw new Exception("object to json error!", e);
		}
	}

	/**
	 * 将json数组转换为List<Map<String,Object>> json数组格式[{},{}]
	 *
	 * @param jsonArray 需要转换的json数组
	 * @return 转换后的列表   如果转换失败返回null
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> jsonArray2List(String jsonArray) {
		try {
			return mapper.readValue(jsonArray, List.class);
		} catch (JsonParseException e) {
			LOGGER.error("|JsonParseException|异常字符串|" + jsonArray, e);
		} catch (JsonMappingException e) {
			LOGGER.error("|JsonMappingException|异常字符串|" + jsonArray, e);
		} catch (IOException e) {
			LOGGER.error("|IOException|异常字符串|" + jsonArray, e);
		}
		return new ArrayList<Map<String, Object>>();
	}

	/**
	 * 将HttpServletRequest中的inputStream转换为Json对象
	 * 
	 * @param in --前台传入HttpServletRequest中的InputStream
	 * @return String
	 */
	public static String inputStreamToString(InputStream in) throws IOException {
		StringBuilder out = new StringBuilder();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	/**
	* 将json数据转换成pojo对象list
	* <p>Title: jsonToList</p>
	* <p>Description: </p>
	* @param jsonData
	* @param beanType
	* @return
	*/
	@SuppressWarnings("deprecation")
	public static <T> List<T> jsontwoList(String jsonData, Class<T> beanType) {
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			List<T> list = mapper.readValue(jsonData, javaType);
			return list;
		} catch (Exception e) {
			LOGGER.error(e);
		}

		return Collections.emptyList();
	}

	/**
	* 将json结果集转化为对象
	* 
	* @param jsonData json数据
	* @param clazz 对象中的object类型
	* @return
	*/
	public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
		try {
			T t = mapper.readValue(jsonData, beanType);
			return t;
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return null;
	}
}
