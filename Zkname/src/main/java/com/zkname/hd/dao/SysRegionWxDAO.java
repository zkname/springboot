package com.zkname.hd.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zkname.frame.dao.BaseDAO;
import com.zkname.hd.entity.SysRegionWx;

@Repository
public class SysRegionWxDAO extends BaseDAO<SysRegionWx> {

	
    /**
     * 
     * update:(修改).
     */
    public void update(SysRegionWx entity){
        String sql = "update sys_region_wx set name=?,parentId=?,levelType=? where id=?";
        super.update(sql, entity.getName(),entity.getParentId(),entity.getLevelType(),entity.getId());
    }

	public List<SysRegionWx> findProvince() {
		String sql = "select id,name from sys_region_wx where levelType=1";
		return super.find(sql);
	}
	
	public List<SysRegionWx> findCity(Integer parentId) {
		String sql = "select id,name from sys_region_wx where levelType=2 and parentId=?";
		return super.find(sql, parentId);
	}
	
	/**
	 * 查询城市数据
	 * @param parentId
	 * @param levelType
	 * @param name
	 * @return
	 */
	public SysRegionWx findByName(Integer parentId,int levelType,String name) {
		if(parentId==null || parentId.intValue()==0){
			parentId=100000;
		}
		String sql = "select id,name from sys_region_wx where levelType=? and parentId=? and name=? limit 1";
		return super.findBy(sql,levelType,parentId,name);
	}
	
}