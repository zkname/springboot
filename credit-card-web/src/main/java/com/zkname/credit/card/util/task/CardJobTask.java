package com.zkname.credit.card.util.task;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.zkname.core.util.CompuUtils;
import com.zkname.core.util.DateUtil;
import com.zkname.core.util.ParamType;
import com.zkname.credit.card.entity.CcardInfo;
import com.zkname.credit.card.entity.CcardRange;
import com.zkname.credit.card.service.CcardInfoService;
import com.zkname.credit.card.service.CcardJobService;
import com.zkname.credit.card.service.CcardRangeService;

/**
 * Spring task 定时器
 */
@Component
public class CardJobTask {

	private static final Logger logger = LoggerFactory.getLogger(CardJobTask.class);

	@Autowired
	private CcardInfoService ccardInfoService; 
	
	@Autowired
	private CcardJobService ccardJobService; 
	
	@Autowired
	private CcardRangeService ccardRangeService;
	
	@Scheduled(cron="0 0/1 * * * ?")
	public void run() {
		logger.info(this.getClass().getSimpleName()+" 调用！~");
		Date d=DateUtil.getNowDate();
		int day=ParamType.getint(DateUtil.Date2Str(d, "dd"));
		List<CcardInfo> list = ccardInfoService.getDAO().findBillDate(day, d);
		for(CcardInfo cinfo:list){
			try {
				CcardRange ccardRange=ccardRangeService.findById(cinfo.getCardRangeId());
		    	//刷卡次数
		    	int num=RandomUtils.nextInt(ccardRange.getFrequencyPropStartValue(),ccardRange.getFrequencyPropEndtValue()+1);
		    	//刷卡百分比
		    	int moneyProp=RandomUtils.nextInt(ccardRange.getMoneyPropStartValue(),ccardRange.getMoneyPropEndValue()+1);
		    	//刷卡金额
		    	double money=CompuUtils.multiply(CompuUtils.divide(moneyProp, 100.0D,2),cinfo.getMoney(),2);
		    	
				ccardJobService.createJob(cinfo,ccardRange,getMoney(money, num));
			} catch (Exception e) {
				logger.error("生成数据错误：",e);
			}
		}
		logger.info(this.getClass().getSimpleName() + " 结束！~");
	}
	
	
	
    /**
     * 百分比
     * @param num
     * @return
     */
    private static List<Double> getProp(int num){
    	int max=0;
    	List<Integer> list=Lists.newArrayList();
    	List<Double> list1=Lists.newArrayList();
		for(int i=0;i<num;i++){
			int v=RandomUtils.nextInt(100,1000*num);
			list.add(v);
			max+=v;
		}
		double f=0;
		for(int i=0;i<list.size();i++){
			Integer a=list.get(i);
			if(i==list.size()-1){
				list1.add(CompuUtils.subtract(1.0D, f));
			}else{
				double a2=CompuUtils.divide(a, max,4);
				f=CompuUtils.add(f,a2);
				list1.add(a2);
			}
			
		}
		return list1;
    }
    
    /**
     * 获取次数金额数组
     * @param num
     * @return
     */
    private static List<Double> getMoney(double money,int num){
    	List<Double> a=getProp(num);
    	List<Double> list1=Lists.newArrayList();
    	double mv1=0;
		for(Double d:a){
			double mv=CompuUtils.multiply(d, money,0);
			list1.add(mv);
			mv1=CompuUtils.add(mv1,mv);
		}
		double ak=CompuUtils.subtract(money, mv1);
		if(ak>0){
			int index=RandomUtils.nextInt(0,list1.size());
			double v=CompuUtils.add(list1.get(index),ak);
			list1.remove(index);
			list1.add(v);
		}else{
			while (true) {
				int index=RandomUtils.nextInt(0,list1.size());
				if(index+ak<1){
					continue;
				}
				double v=CompuUtils.add(list1.get(index),ak);
				list1.remove(index);
				list1.add(v);
				break;
			}
		}
		Collections.shuffle(list1);
		return list1;
    }
}



