package com.zkname.credit.card.util.task;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zkname.credit.card.controller.IndexController;
import com.zkname.credit.card.entity.SysParam;
import com.zkname.credit.card.service.SysParamService;

/**
 * Spring task 定时器
 */
@Component
public class MinuteJobTask {

	private static final Logger logger = LoggerFactory.getLogger(MinuteJobTask.class);

	@Autowired
	private SysParamService sysParamService;
	
	@PostConstruct
	@Scheduled(cron="0 0/1 * * * ?")
	public void run() {
		logger.info(this.getClass().getSimpleName()+" 调用！~");
		try {
			UPDATE(sysParamService.findByKey("open_register"));
			UPDATE(sysParamService.findByKey("reset_password_url"));
		} catch (Exception e) {
			logger.error("错误：",e);
		}
		logger.info(this.getClass().getSimpleName() + " 结束！~");
	}
	
	public static void UPDATE(SysParam sy){
		if(sy==null){
			return;
		}
		if(StringUtils.equals(sy.getK(), "open_register")){
			IndexController.OPEN_REGISTER=sy.getIntV();
		}else if(StringUtils.equals(sy.getK(), "reset_password_url")){
			IndexController.RESET_PASSWORD_URL=sy.getV();
		}
	}
}



