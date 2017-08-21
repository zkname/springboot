package com.zkname.frame.util;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;
import com.zkname.frame.page.Page;
import com.zkname.frame.util.jackson.JsonUtil;

/**
 * json 格式  {"code":"0（错误代码）","message":"错误信息（属性可能不存在）"}
 * @author Kai
 */
public class CodeHttpUtil {
    
	public static final int 失败=0;
    public static final int 成功=200;
    public static final int 无权限=403;
    public static final int 错误=500;
	
	public final static String contentType = "application/json";
	
	public static void writer(HttpServletResponse response,int code){
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String jsonObject = "{\"code\":"+code+"}";
			response.setContentType(contentType);
			out.print(jsonObject);
		} catch (Exception e) {
		}finally{
			if(out!=null){
				out.flush();
				out.close();
			}
		}
	}
	
    public static void writer(HttpServletResponse response,int code,Object message){
        PrintWriter out = null;
        try {
            out = response.getWriter();
            Map<String,Object> map=Maps.newHashMap();
            map.put("code", code);
            map.put("message", message);
            String jsonObject = JsonUtil.objectToJson(map);
            response.setContentType(contentType);
            out.print(jsonObject);
        } catch (Exception e) {
        }finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
    }
    
    
    public static String getJson(int code,Object result){
        Map<String,Object> map=Maps.newHashMap();
        map.put("code", code);
        map.put("result", result);
        return JsonUtil.objectToJson(map);
     }
    
    public static String getJson(int code){
        Map<String,Object> map=Maps.newHashMap();
        map.put("code", code);
        return JsonUtil.objectToJson(map);
     }
    
    public static String getJson(int code, Object result, Class<?>[] clazz, String... strings) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", code);
		map.put("result", result);
		return JsonUtil.objectToJson(map, clazz, strings);
	}
    
	public static void page(Map<String,Object> map,Page<?> page){
		Map<String,Object> map1=Maps.newHashMap();
		map1.put("pageNo", page.getPageNo());
		map1.put("pageSize", page.getPageSize());
		map1.put("totalItems", page.getTotalItems());
		map.put("pagination", map1);
	}
}
