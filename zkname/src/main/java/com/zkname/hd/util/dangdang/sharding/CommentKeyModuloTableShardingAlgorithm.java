package com.zkname.hd.util.dangdang.sharding;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;
import com.zkname.frame.util.DateUtil;
import com.zkname.frame.util.ParamType;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

public class CommentKeyModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Date> {

	public static String getTable(Date d){
    	return DateUtil.Date2Str(d, "yyyyMM");
	}
	
    @Override
    public String doEqualSharding(final Collection<String> availableTargetNames, final ShardingValue<Date> shardingValue) {
    	return shardingValue.getLogicTableName()+"_"+getTable(shardingValue.getValue());
    }
    
    @Override
    public Collection<String> doInSharding(final Collection<String> availableTargetNames, final ShardingValue<Date> shardingValue) {
        Collection<String> result = new LinkedHashSet<>();
        result.add(shardingValue.getLogicTableName()+"_"+getTable(shardingValue.getValue()));
        return result;
    }
    
    @Override
    public Collection<String> doBetweenSharding(final Collection<String> availableTargetNames, final ShardingValue<Date> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Range<Date> range = shardingValue.getValueRange();
        Date d1=range.lowerEndpoint();
        Date d2=range.upperEndpoint();
        while (ParamType.getint(DateUtil.Date2Str(d1,"yyyyMM"))<=ParamType.getint(DateUtil.Date2Str(d2,"yyyyMM"))) {
        	result.add(shardingValue.getLogicTableName()+"_"+getTable(d1));
        	d1=DateUtil.addMonth(d1, 1);
		}
        return result;
    }
    
    public static void main(String[] args) {
		System.out.println("aa_11".endsWith("1"));
	}
}
