package com.zkname.frame.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.google.common.collect.Maps;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Bytes;
import com.zkname.frame.security.LoginUser;
import com.zkname.frame.security.springsecurity.SpringSecurityUtils;
import com.zkname.frame.util.conf.ConfigurationProperties;
import com.zkname.frame.util.conf.base.BaseProperties;
import com.zkname.frame.util.json.JsonUtil;
import com.zkname.frame.util.spring.DateConvertEditor;

/**
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-1-9
 */
public abstract class BaseController {
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 增加了@ModelAttribute的方法可以在本controller的方法调用前执行,可以存放一些共享变量,如枚举值
	 */
	@ModelAttribute
	public void init(ModelMap model,HttpServletRequest request) {
//	    PageRequest.DEFAULT_PAGESIZE=10;
		model.put("ctx", request.getContextPath());
	}
	/**
	 * 初始化Date类型，进行数据类型转换，把页面提交的String类型转为Date类型
	 * @param binder
	 */
	@InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}
	
	/**
	 * renderJson(json直接输出)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param data
	 * @param response 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	protected void renderJsonObject(final Object data,HttpServletResponse response) {
		try {
			String value=JsonUtil.getMapper().writeValueAsString(data);
			renderJson(value, response);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * renderJson(json直接输出)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param data
	 * @param response 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	protected void renderJsonObject(final Object data,Class<?> rootType,HttpServletResponse response) {
		try {
			String value=JsonUtil.getMapper().writerWithType(rootType).writeValueAsString(data);
			renderJson(value, response);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 直接输出文本.
	 */
	protected void renderText(final String text,HttpServletResponse response) {
		try {
			response.getWriter().write(text);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 输出XML
	 */
	protected void renderXML(final String xml,HttpServletResponse response) {
		try {
			response.setHeader("Content-Type", "text/xml");
			response.getWriter().write(xml);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 输出json
	 */
	protected void renderJson(final String json,HttpServletResponse response) {
		try {
			response.setHeader("Content-Type", "application/json");
			response.getWriter().write(json);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static Map getParameterMap(HttpServletRequest request) {
	    // 参数Map
	    Map properties = request.getParameterMap();
	    // 返回值Map
	    Map returnMap = new HashMap();
    	properties.forEach((k,v) ->{
    		String name = (String) k;
    		String value = "";
	        Object valueObj = v;
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
	        }else{
	            value = valueObj.toString();
	        }
	        returnMap.put(name, value);
    	});
	    return returnMap;
	}

	protected final static byte commonCsvHead[] = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
	
	protected void dow(HttpServletRequest request,HttpServletResponse response,String filename,StringBuffer sb){
 		try (OutputStream ouputStream = response.getOutputStream()){
 	        response.setCharacterEncoding("UTF-8");
 	        response.setContentType("application/x-download;charset=UTF-8");
 	        // 文件名
 	 		response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(filename, "UTF-8") + "\"");
 	        ouputStream.write(Bytes.concat(commonCsvHead, sb.toString().getBytes(Charsets.UTF_8.toString())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void dow(HttpServletRequest request, HttpServletResponse response, String filename, File file) {
		try (OutputStream out = response.getOutputStream(); FileInputStream inputStream = new FileInputStream(file)) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download;charset=UTF-8");
			// 文件名
			response.setHeader("Content-Disposition",
					"attachment;filename=\"" + URLEncoder.encode(filename, "UTF-8") + "\"");
			int b = 0;
			byte[] buffer = new byte[512];
			while (b != -1) {
				b = inputStream.read(buffer);
				// 4.写到输出流(out)中
				out.write(buffer, 0, b);
			}
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	  /**
     * 
     * @param request
     * @return
     */
	@SuppressWarnings("static-access")
	public  boolean isSignature(HttpServletRequest request) {
		return isSignature(this.getParameterMap(request));
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean isSignature(Map map) {
		TreeMap treemap = new TreeMap(map);
		String signature = (String) treemap.get("signature");
		//测试环境签名666
		if(StringUtils.equals("&*(HFFCVG$@#DFBkjrei-=21=-03VSHJA)*(^&DBJASUOsklf893w8921", signature)){
			return true;
		}
		treemap.remove("signature");
		StringBuffer sb=new StringBuffer();
		treemap.forEach((k,v)->{
			sb.append(k).append("=").append(v);
		});
		sb.append(ConfigurationProperties.getInstance().getProperty("signature_key"));
		return md5(sb.toString()).equals(signature);
	}
	
	/**
	 * 支付签名
	 * @param map
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getPaySignature(Map map,String key) {
		TreeMap treemap = new TreeMap(map);
		StringBuffer sb=new StringBuffer();
		treemap.forEach((k,v)->{
			if(sb.length()>0){
				sb.append("&");
			}
			sb.append(k).append("=").append(v);
		});
		sb.append("&key=").append(key);
		return md5(sb.toString()).toUpperCase();
	}
	
	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		Hasher hasher = Hashing.md5().newHasher();  
		hasher.putString(str,Charset.forName("UTF-8")); 
		return hasher.hash().toString();
	}
	
	public static String sha1(String str) {
		Hasher hasher = Hashing.sha1().newHasher();  
		hasher.putString(str,Charset.forName("UTF-8")); 
		return hasher.hash().toString();
	}
	
	public String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
}

