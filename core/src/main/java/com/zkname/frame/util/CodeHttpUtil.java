package com.zkname.frame.util;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

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
		writer(response, code, null);
	}
	
    public static void writer(HttpServletResponse response,int code,Object message){
        PrintWriter out = null;
        try {
            out = response.getWriter();
            Map<String,Object> map=Maps.newHashMap();
            map.put("code", code);
            if(message!=null){
            	map.put("message", message);
            }
            String jsonObject = JSON.toJSONString(map);
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
        return JSON.toJSONString(map);
     }
}
