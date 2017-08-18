package com.zkname.hd.util.quartz;


import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.zkname.frame.util.DateUtil;
import com.zkname.frame.util.spring.SpringContextHolder;
import com.zkname.hd.service.SysUserService;

/**
 * 创建表
 */
public class CreateTableQuartz extends BaseQuartz{

	public static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CreateTableQuartz.class);

	public void executeInternal(JobExecutionContext context)throws JobExecutionException {
		logger.info(this.getClass().getSimpleName()+" 调用！~");
		SysUserService sysUserService=SpringContextHolder.getApplicationContext().getBean(SysUserService.class);
		Date d=new Date();
		logger.info(this.getClass().getSimpleName()+" create hd_user_pay_log");
		sysUserService.createTable("hd_user_pay_log",d);
		sysUserService.createTable("hd_user_pay_log",DateUtil.addMonth(d, 1));
		logger.info(this.getClass().getSimpleName()+" create hd_user_recharge_log");
		sysUserService.createTable("hd_user_recharge_log",d);
		sysUserService.createTable("hd_user_recharge_log",DateUtil.addMonth(d, 1));
		logger.info(this.getClass().getSimpleName()+" create hd_shop_wallet_log");
		sysUserService.createTable("hd_shop_wallet_log",d);
		sysUserService.createTable("hd_shop_wallet_log",DateUtil.addMonth(d, 1));
		logger.info(this.getClass().getSimpleName()+" create hd_user_wallet_log");
		sysUserService.createTable("hd_user_wallet_log",d);
		sysUserService.createTable("hd_user_wallet_log",DateUtil.addMonth(d, 1));
		logger.info(this.getClass().getSimpleName() + " 结束！~");
	}
}
