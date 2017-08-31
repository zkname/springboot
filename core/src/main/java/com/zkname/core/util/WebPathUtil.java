package com.zkname.core.util;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class WebPathUtil {

	public WebPathUtil() {
	}

	public static String getWebModulPath() {
		String ret = getWebinfPath();
		int cnt = ret.indexOf("/WEB-INF");
		if (cnt > -1)
			ret = ret.substring(0, cnt);
		return ret;
	}

	private static String _webinfPath = null;

	public static String getWebinfPath() {
		if (_webinfPath == null) {
			String res = null;
			String classname = WebPathUtil.class.getName().replace('.', '/') + ".class";
			ClassLoader cl = WebPathUtil.class.getClassLoader();
			if (cl != null) {
				java.net.URL url = cl.getResource(classname);
				if (url != null) {
					String path = url.getFile();
					int fileStrPosition = path.indexOf("file:/");
					int begin = 0;
					int end = path.length();

					if (fileStrPosition >= 0)
						begin = fileStrPosition + 5;

					// 先判断是否是未打包文件

					end = path.indexOf("classes/" + classname);
					if (end < 0) {
						// 如果是在webModule下面的jar包
						end = path.indexOf("lib/");
						if (end < 0) {
							// 在普通目录下的jar包
							int tmpend = path.indexOf("!/");
							end = path.substring(0, tmpend).lastIndexOf("/");
						}
					}
					String rf = path.substring(begin, end);
					res = new File(rf).getAbsolutePath().replace('\\', '/') + "/";
				}
			}
			try {
				_webinfPath = java.net.URLDecoder.decode(res, "UTF-8");
			} catch (UnsupportedEncodingException ex) {
			}
			System.out.println("WEB-INF Path=" + _webinfPath);
		}
		return _webinfPath;
	}

	private static String _systemPath = null;

	public static String getSystemPath() {
		if (_systemPath == null) {
			String res = getWebinfPath();
			if (res.indexOf("WEB-INF/") > 0 && res.length() > 10) {
				res = res.substring(0, res.lastIndexOf("/", res.length() - 12)) + "/";
			}
			_systemPath = res;
			System.out.println("System Path=" + _systemPath);
		}
		return _systemPath;
	}
	
	private static String _containerPath = null;
	
	public static String getContainerPath() {
		if (_containerPath == null) {
			String res = getSystemPath();
			res = res.substring(0, res.lastIndexOf("/", res.length()- 6)) + "/";
			_containerPath = res;
			System.out.println("Container Path=" + _containerPath);
		}
		return _containerPath;
	}

	public static void main(String[] args) {
		System.out.println(WebPathUtil.getWebModulPath());
		System.out.println(getSystemPath());
		System.out.println(getWebinfPath());
		System.out.println(getContainerPath());

	}
}
