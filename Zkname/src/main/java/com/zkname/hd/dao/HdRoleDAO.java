package com.zkname.hd.dao;

import java.util.*;
import com.zkname.hd.entity.*;
import com.zkname.frame.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class HdRoleDAO extends BaseDAO<HdRole> {

	public void updateStatus(Long id, int status) {
		String sql = "update hd_role o set o.deleStatus=?,o.updateTime=now() where o.id=? and o.deleStatus!=?";
		super.update(sql, status, id, status);
	}
}