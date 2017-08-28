package com.zkname.hd.util.xml;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.fasterxml.jackson.xml.XmlMapper;

/**
 * xml 工具类
 * 
 *
 * @version
 * @since Ver 1.1
 * @Date 2012-6-27
 */
public class XmlUtil {
	private static XmlMapper mapper = new XmlMapper();

	static{
		mapper.getSerializationConfig().withDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static ObjectMapper getMapper() {
		return mapper;
	}

	public static String objectToXml(final Object data) {
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(data);
			return jsonString;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T xmlToObject(final String xml, TypeReference<?> typeReference) {
		try {
			return (T)mapper.readValue(xml, typeReference);
		} catch (JsonParseException e) {
			throw new IllegalArgumentException(e);
		} catch (JsonMappingException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T xmlToObject(final String xml, Class<?> clazz) {
		try {
			return (T)mapper.readValue(xml, clazz);
		} catch (JsonParseException e) {
			throw new IllegalArgumentException(e);
		} catch (JsonMappingException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * jsonToObject(jsonNode格式转java对象) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param jsonNode
	 * @param typeReference
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlToObject(final JsonNode jsonNode, TypeReference<?> typeReference) {
		try {
			return (T)mapper.readValue(jsonNode, typeReference);
		} catch (JsonParseException e) {
			throw new IllegalArgumentException(e);
		} catch (JsonMappingException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
