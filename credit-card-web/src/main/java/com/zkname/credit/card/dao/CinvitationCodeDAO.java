package com.zkname.credit.card.dao;

import java.util.*;
import com.zkname.credit.card.entity.*;
import com.zkname.core.dao.BaseDAO;
import org.springframework.stereotype.Repository;

@Repository
public class CinvitationCodeDAO extends BaseDAO<CinvitationCode> {
	
	/**
	 * 查询邀请码
	 * @param invitationCode
	 * @return
	 */
	public CinvitationCode findByInvitationCode(String invitationCode){
		String sql="SELECT a.* FROM c_invitation_code as a,c_invitation_code_batch as b where a.invitationCode=? and b.id=a.invitationCodeBatch and a.deleStatus='1' and b.deleStatus='1' and b.updateLock='0' and a.userId='0'";
		return super.findBy(sql, invitationCode);
	}
	
	
    /**
     * 注册
     * @param cinvitationCode
     * @return
     */
    public int register(Long id,Long userId){
		String sql = "update c_invitation_code set userId=?,updateTime=now() where id=? and userId='0' and deleStatus=1";
		return super.update(sql, userId, id);
    }
}