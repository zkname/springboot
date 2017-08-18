package com.zkname.hd.dao;

import java.util.*;
import com.zkname.hd.entity.*;
import com.zkname.frame.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class HdShopDAO extends BaseDAO<HdShop> {

	public void updateStatus(Long id, int status) {
		String sql = "update hd_shop o set o.deleStatus=?,o.updateTime=now() where o.id=? and o.deleStatus!=?";
		super.update(sql, status, id, status);
	}

	public void updateUuid(Long id) {
		String sql = "update hd_shop o set uuid=concat('CZ_',replace(uuid(),'-','')) where id=?";
		super.update(sql,id);
	}
	
	/**
	 * uuid（二维码） 查询
	 * @param uuid
	 * @return
	 */
	public HdShop findByUuid(String uuid) {
		String sql = "select * from hd_shop o where uuid=?";
		return super.findBy(sql,uuid);
	}
}