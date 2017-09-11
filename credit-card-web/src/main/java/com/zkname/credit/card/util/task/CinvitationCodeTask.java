package com.zkname.credit.card.util.task;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zkname.core.util.CompuUtils;
import com.zkname.core.util.DateUtil;
import com.zkname.core.util.ParamType;
import com.zkname.credit.card.entity.CcardInfo;
import com.zkname.credit.card.entity.CcardRange;
import com.zkname.credit.card.entity.CinvitationCodeBatch;
import com.zkname.credit.card.service.CcardInfoService;
import com.zkname.credit.card.service.CcardJobService;
import com.zkname.credit.card.service.CcardRangeService;
import com.zkname.credit.card.service.CinvitationCodeBatchService;
import com.zkname.credit.card.service.CinvitationCodeService;

/**
 * 生成注册码
 */
@Component
public class CinvitationCodeTask {

	private static final Logger logger = LoggerFactory.getLogger(CinvitationCodeTask.class);
	
	@Autowired
	private CinvitationCodeBatchService cinvitationCodeBatchService; 
	
	
	@Scheduled(cron="0 0/5 * * * ?")
	public void run() {
		logger.info(this.getClass().getSimpleName()+" 调用！~");
		List<CinvitationCodeBatch> list = cinvitationCodeBatchService.getDAO().findNoGenerate();
		for(CinvitationCodeBatch cinfo:list){
			try {
				Set<String> set=Sets.newHashSet();
				for(int i=0;i<cinfo.getTotal();i++){
					set.add(UUID.randomUUID().toString().replace("-", ""));
				}
				if(cinfo.getTotal()!=set.size()){
					continue;
				}
				cinvitationCodeBatchService.createCode(cinfo, set);
			} catch (Exception e) {
				logger.error("生成数据错误：",e);
			}
		}
		logger.info(this.getClass().getSimpleName() + " 结束！~");
	}
}



