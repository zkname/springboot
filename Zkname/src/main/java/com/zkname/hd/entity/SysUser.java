package com.zkname.hd.entity;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.zkname.hd.entity.base.BaseSysUser;

import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect
@JsonIgnoreProperties(value = { "sysUserUserRoleRs", "sysUser", "password" })
public class SysUser extends BaseSysUser {

	private static final long serialVersionUID = -3451888062016242057L;

	public SysUser() {
	}

	public SysUser(java.lang.Long id) {
		super(id);
	}

	public String getUserRoleName() {
		StringBuffer sb = new StringBuffer();
		// Set<SysUserUserRoleR> set=this.getSysUserUserRoleRs();
		// for(SysUserUserRoleR surr:set){
		// sb.append(surr.getSysUserRole().getName()).append(" ");
		// }
		return sb.toString();
	}
	
    /**
     * wifi客户名称
     */
	@Getter
	@Setter
	private java.lang.String name;

    /**
     * appKey
     */
	@Getter
	@Setter
	private java.lang.String appKey;

    /**
     * appSecret
     */
	@Getter
	@Setter
	private java.lang.String appSecret;

    /**
     * 联系人
     */
	@Getter
	@Setter
	private java.lang.String atten;

    /**
     * 联系电话
     */
	@Getter
	@Setter
	private java.lang.String tel;

    /**
     * 联系地址
     */
	@Getter
	@Setter
	private java.lang.String address;

}