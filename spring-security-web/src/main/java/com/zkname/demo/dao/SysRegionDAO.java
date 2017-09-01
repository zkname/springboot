package com.zkname.demo.dao;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.zkname.core.dao.BaseDAO;
import com.zkname.demo.entity.*;

@Repository
public class SysRegionDAO extends BaseDAO<SysRegion> {

    
	/**
	 * 查询所有
	 */
	public List<Map<String,Object>> findAllInfo() {
		String sql="select o.id,o.shortName as name,o.parentId as pid,o.levelType as level from sys_region o where o.levelType>0 order by o.id ";
		return super.getJdbcTemplate().queryForList(sql);
	}

	public List<SysRegion> findProvince() {
		String sql = "select id,name from sys_region where levelType=1";
		return super.find(sql);
	}

	public List<SysRegion> findCity(Integer parentId) {
		String sql = "select id,name from sys_region where levelType=2 and parentId=?";
		return super.find(sql, parentId);
	}

	public SysRegion findProviceByName(String name) {
		String sql = "select * from sys_region where shortName=? and levelType=1";
        return super.findBy(sql,name);
	}

	public SysRegion findByProviceNameAndCityName(String name, String parentName) {
		String sql = "select * from sys_region where levelType>1 and mergerName like ? and (shortName like ? or name like ?)";
		return super.findBy(sql, "中国," + parentName + "%", "%" + name + "%", "%" + name + "%");
	}
	
}



