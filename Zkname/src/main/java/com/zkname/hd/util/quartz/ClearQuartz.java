package com.zkname.hd.util.quartz;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.zkname.frame.util.memcache.XMemcachedClientImp;
import com.zkname.frame.util.spring.SpringContextHolder;
import com.zkname.hd.service.HdRoomService;
import com.zkname.hd.service.HdRoomUserService;
import com.zkname.hd.service.HdUserWalletService;

/**
 * 12点清理所有用户数据
 */
public class ClearQuartz extends BaseQuartz{

	public static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ClearQuartz.class);

	public void executeInternal(JobExecutionContext context)throws JobExecutionException {
		logger.info(this.getClass().getSimpleName()+" 调用清理房间！~");
		HdRoomService hdRoomService=SpringContextHolder.getApplicationContext().getBean(HdRoomService.class);
		HdRoomUserService hdRoomUserService=SpringContextHolder.getApplicationContext().getBean(HdRoomUserService.class);
		HdUserWalletService udUserWalletService=SpringContextHolder.getApplicationContext().getBean(HdUserWalletService.class);
		
		XMemcachedClientImp userXMemcachedClient=(XMemcachedClientImp) SpringContextHolder.getApplicationContext().getBean("userXMemcachedClient");
		logger.info("【清理】关闭房间{}条",hdRoomService.clear());
		logger.info("【清理】删除房间数据{}条",hdRoomUserService.clear());
		logger.info("【清理】清空金豆{}条",udUserWalletService.clearGoldBean());
		userXMemcachedClient.invalidateNamespace();
		logger.info("【清理】用户游戏缓存清空！");
		logger.info(this.getClass().getSimpleName() + " 清理房间结束！~");
	}
}
