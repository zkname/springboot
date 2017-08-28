package com.zkname.frame.util.jackson;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * json 工具类
 * 
 *
 * @version
 * @since Ver 1.1
 * @Date 2012-6-27
 */
public class JsonUtil {
	private static ObjectMapper mapper = new ObjectMapper();

	static{
		mapper.getSerializationConfig().with(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static ObjectMapper getMapper() {
		return mapper;
	}

	/**
	 * objectToJson(java对象转json格式) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param data
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	public static String toJSONString(final Object data) {
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(data);
			return jsonString;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * jsonToObject(json格式转java对象) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param json
	 * @param typeReference
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToObject(final String json, TypeReference<?> typeReference) {
		try {
			return (T)mapper.readValue(json, typeReference);
		} catch (JsonParseException e) {
			throw new IllegalArgumentException(e);
		} catch (JsonMappingException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T jsonToObject(final String json, Class<?> clazz) {
		try {
			return (T)mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			throw new IllegalArgumentException(e);
		} catch (JsonMappingException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	public static String objectToJson(final Object data,Class<?> [] clazz,String...filter) {
		String jsonString = null;
		try {
			com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
			mapper.setFilterProvider(filter("objectFilter", filter));
			for(Class<?> c:clazz){
				mapper.addMixIn(c, ObjectFilterMixIn.class);
			}
			jsonString = mapper.writeValueAsString(data);
			return jsonString;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	private static FilterProvider filter(String filterName, String... propertyes) {
		// 过滤不想要的
		FilterProvider filter = new SimpleFilterProvider().addFilter(filterName, SimpleBeanPropertyFilter.serializeAllExcept(propertyes));
		// 过滤想要的
		/*
		 * FilterProvider filter = new SimpleFilterProvider().addFilter(
		 * filterName, SimpleBeanPropertyFilter.filterOutAllExcept(propertyes);
		 */
		return filter;
	}
	
	@JsonFilter("objectFilter")
	interface ObjectFilterMixIn {
	}

}
