package com.zkname.hd.util;

import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.common.collect.Maps;
import com.zkname.frame.page.Page;
import com.zkname.frame.util.json.JsonUtil;

/**
 * json 格式 {"code":"0（错误代码）","message":"错误信息（属性可能不存在）"}
 * 
 * @author Kai
 */
public class CodeUtil {

	public static final int 失败 = 0;
	public static final int 登陆超时 = 1;
	public static final int 成功 = 200;
	public static final int 无权限 = 403;
	public static final int 错误 = 500;
	public static final int 签名错误 = 505;
	public static final int 非法链接 = 506;
	public static final int 参数错误 = 507;

	public static final int 获取SESSION_KEY错误 = 601;
	public static final int 获取SESSIONID超时 = 602;
	public static final int 微信解密错误 = 603;

	public static final int 可兑换金币不足 = 700;

	public final static String contentType = "application/json";
	public static final int 房间不存在 = 900;
	public static final int 房间已满 = 901;
	public static final int 已在其他房间 = 902;
	public static final int 金豆不足 = 903;
	public static final int 不在同一房间 = 904;
	public static final int 未设置赌注 = 905;
	public static final int 当前房间不是房主 = 906;
	public static final int 房间未开放 = 907;
	public static final int 缓存读取错误 = 908;
	public static final int 不能跟自己交易 = 909;
	public static final int 不在房间中 = 910;
	public static final int 房间人未满 = 911;
	public static final int 对局不存在 = 912;
	public static final int 余额不足 = 913;
	public static final int 对方余额不足 = 914;
	public static final int 玩家退出 = 915;
	public static final int 房主退出房间 = 916;

	public static void writer(HttpServletResponse response, int code) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String jsonObject = "{\"code\":" + code + "}";
			response.setContentType(contentType);
			out.print(jsonObject);
		} catch (Exception e) {
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	@Deprecated
	public static void writer(HttpServletRequest request, HttpServletResponse response, int code) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String jsonObject = "{\"code\":" + code + ",\"jsessionid\":\"" + request.getSession().getId() + "\"}";
			response.setContentType(contentType);
			out.print(jsonObject);
		} catch (Exception e) {
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	@Deprecated
	public static void writer(HttpServletResponse response, int code, Object message) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String jsonObject = "{\"code\":" + code + ",\"message\":\"" + message + "\"}";
			response.setContentType(contentType);
			out.print(jsonObject);
		} catch (Exception e) {
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	@Deprecated
	public static void writer(HttpServletResponse response, int code, String message, String result) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String jsonObject = "{\"code\":" + code + ",\"message\":\"" + message + "\",\"result\":" + result + "}";
			response.setContentType(contentType);
			out.print(jsonObject);
		} catch (Exception e) {
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	public static String getJson(int code, Object result) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", code);
		map.put("result", result);
		return JsonUtil.objectToJson(map);
	}

	public static String getJson(int code) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", code);
		return JsonUtil.objectToJson(map);
	}

	public static String getJson(int code, Object result, Class<?>[] clazz, String... strings) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", code);
		map.put("result", result);
		return JsonUtil.objectToJson(map, clazz, strings);
	}

	public static void page(Map<String, Object> map, Page<?> page) {
		Map<String, Object> map1 = Maps.newHashMap();
		map1.put("pageNo", page.getPageNo());
		map1.put("pageSize", page.getPageSize());
		map1.put("totalItems", page.getTotalItems());
		map.put("pagination", map1);
	}
}
