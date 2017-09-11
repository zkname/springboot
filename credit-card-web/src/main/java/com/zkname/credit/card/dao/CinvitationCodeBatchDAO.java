package com.zkname.credit.card.dao;

import java.util.*;
import com.zkname.credit.card.entity.*;
import com.zkname.core.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class CinvitationCodeBatchDAO extends BaseDAO<CinvitationCodeBatch> {
	
	public List<CinvitationCodeBatch> findNoGenerate(){
		String sql="SELECT a.* FROM c_invitation_code_batch as a where a.isGenerate='0' and a.deleStatus='1' ";
		return super.find(sql);
	}
}