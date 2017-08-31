package com.zkname.core.dao.sql;

import java.util.Set;

import com.google.common.collect.Sets;
import com.zkname.core.dao.sql.SqlFactory.SqlType;
import com.zkname.core.util.Iterables;

/**
 * ClassName:SqlUtil
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhangk@autoradio.cn
 * @version  
 * @since    Ver 1.1
 * @Date	 2011-7-27
 */
public class SqlUtil {
	
	/**
	 * 生成sql语句
	 * @param sqlBean
	 */
	public static void creatSql(SqlBean sqlBean){
		//主键查询
		creatFindById(sqlBean);
		//新增
		creatSave(sqlBean);
		//修改
		creatUpdate(sqlBean);
		//删除
		creatDelete(sqlBean);
		//查询所有
		creatFindAll(sqlBean);
	}
	
	/**
	 * findById(获取sql)
	 * (这里描述这个方法适用条件 – 可选)
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	private static void creatFindById(SqlBean sqlBean) {
		StringBuffer sb=new StringBuffer();
		sb.append("select ");
		Iterables.forEach(sqlBean.getIds(), (i,v)->{
			if(i!=0){
				sb.append(" , ");
			}
			sb.append(v);
		});
		sqlBean.getOther().forEach((v)->{
			sb.append(",").append(v);
		});
		sb.append(" from  ").append(sqlBean.getTableName());
		sb.append(" where 1=1 ");
		sqlBean.getIds().forEach((id)->{
			sb.append(" and ").append(id).append("=?");
		});
		sqlBean.getSqlMap().put(SqlType.FIND_BY_ID, sb.toString());
	}
	
	/**
	 * getSave(获取新增SQL)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entityClass
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	private static void creatSave(SqlBean sqlBean) {
		StringBuffer sb1=new StringBuffer();
		StringBuffer sb2=new StringBuffer();
		sb1.append("insert into ").append(sqlBean.getTableName()).append("(");
		Iterables.forEach(sqlBean.getIds(), (i,v)->{
			if(i!=0){
				sb1.append(" , ");sb2.append(" , ");
			}
			sb1.append(v);sb2.append("?");
		});
		sqlBean.getOther().forEach((v)->{
			sb1.append(",").append(v);
			sb2.append(",?");
		});
		sb1.append(")values(").append(sb2).append(")");
		sqlBean.getSqlMap().put(SqlType.SAVE, sb1.toString());
	}
	
	/**
	 * getUpdate(获取更新sql)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entityClass
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	private static void creatUpdate(SqlBean sqlBean) {
		StringBuffer sb=new StringBuffer();
		sb.append("update ").append(sqlBean.getTableName()).append(" set ");
		Iterables.forEach(sqlBean.getOther(), (i,v)->{
			if(i>0){
				sb.append(",");
			}
			sb.append(v).append("=?");
		});
		sb.append(" where 1=1 ");
		sqlBean.getIds().forEach((id)->{
			sb.append(" and ").append(id).append("=?");
		});
		sqlBean.getSqlMap().put(SqlType.UPDATE, sb.toString());
	}
	
	/**
	 * getDelete(删除)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entityClass
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	private static void creatDelete(SqlBean sqlBean) {
		StringBuffer sb=new StringBuffer();
		sb.append("delete from ").append(sqlBean.getTableName()).append(" where 1=1 ");
		sqlBean.getIds().forEach((id)->{
			sb.append(" and ").append(id).append("=?");
		});
		sqlBean.getSqlMap().put(SqlType.DELETE, sb.toString());
	}
	
	/**
	 * findAll(查询所有)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entityClass
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	private static void creatFindAll(SqlBean sqlBean) {
		StringBuffer sb=new StringBuffer();
		sb.append("select ");
		Iterables.forEach(sqlBean.getIds(), (i,id)->{
			if(i!=0){
				sb.append(" , ");
			}
			sb.append(id);
		});
		sqlBean.getOther().forEach((v)->{
			sb.append(",").append(v);
		});
		sb.append(" from  ").append(sqlBean.getTableName());
		sqlBean.getSqlMap().put(SqlType.FIND_All, sb.toString());
	}
	
	
	/**
	 * getUpdateSql(获取更新sql)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param entityClass
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String getUpdateSql(SqlBean sqlBean,String... fields) {
		StringBuffer sb=new StringBuffer();
		sb.append("update ").append(sqlBean.getTableName()).append(" set ");
		Set<String> s=Sets.newHashSet(fields);
		Iterables.forEach(sqlBean.getOther(), (i,v)->{
			if(s.contains(v)){
				if(i>0){
					sb.append(",");
				}
				sb.append(v).append("=?");
			};
		});
		sb.append(" where 1=1 ");
		sqlBean.getIds().forEach((id)->{
			sb.append(" and ").append(id).append("=?");
		});
		return sb.toString();
	}
}

