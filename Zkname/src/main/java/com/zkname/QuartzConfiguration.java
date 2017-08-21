package com.zkname;

import java.text.ParseException;
import java.util.List;

import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.google.common.collect.Lists;
import com.zkname.frame.util.spring.SpringContextHolder;
import com.zkname.hd.util.quartz.CreateTableQuartz;

/**
 * Quartz 定时器
 * @author ZhangKai
 *
 */
@Configuration
public class QuartzConfiguration {

	private static final Logger log = LoggerFactory.getLogger(QuartzConfiguration.class);

	@Bean("quartzScheduler")
	public SchedulerFactoryBean quartzScheduler() throws ClassNotFoundException, NoSuchMethodException, ParseException {
		log.info("init Quartz Configuration ");
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setApplicationContext(SpringContextHolder.getApplicationContext());
		//启动时延期2秒开始任务
		schedulerFactoryBean.setStartupDelay(2);
		List<Trigger> list=Lists.newArrayList();
		list.add(getCreateTableQuartzSimpleTrigger().getObject());
		list.add(getCreateTableQuartzTrigger().getObject());
		schedulerFactoryBean.setTriggers(list.toArray(new Trigger[list.size()]));
		return schedulerFactoryBean;
	}
	
	//=============================建表==============================================
	@Bean
	public JobDetailFactoryBean getCreateTableQuartz()  {
		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(CreateTableQuartz.class);
        factoryBean.setDurability(true);
        return factoryBean;
	}
	
	@Bean("CreateTableQuartzSimpleTrigger")
	public SimpleTriggerFactoryBean getCreateTableQuartzSimpleTrigger() throws ClassNotFoundException, NoSuchMethodException {
		SimpleTriggerFactoryBean schedulerFactoryBean = new SimpleTriggerFactoryBean();
		schedulerFactoryBean.setJobDetail(getCreateTableQuartz().getObject());
		schedulerFactoryBean.setStartDelay(1000);
		schedulerFactoryBean.setRepeatInterval(0);
		schedulerFactoryBean.setRepeatCount(0);
		return schedulerFactoryBean;
	}
	
	@Bean("CreateTableQuartzTrigger")
	public CronTriggerFactoryBean getCreateTableQuartzTrigger() throws ClassNotFoundException, NoSuchMethodException, ParseException {
		CronTriggerFactoryBean schedulerFactoryBean = new CronTriggerFactoryBean();
		schedulerFactoryBean.setJobDetail(getCreateTableQuartz().getObject());
		schedulerFactoryBean.setCronExpression("0 0 0 * * ?");
		return schedulerFactoryBean;
	}
}



