package com.zkname.hd.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 红包接口调用日志
 * @author Kai
 */
public class HongBaoLog {
	private static Logger logger = LoggerFactory.getLogger(HongBaoLog.class);
	

	public static void logger(String xml){
		logger.info("{}",xml.replaceAll("\r", "").replaceAll("\n", ""));
	}
	
	
}
