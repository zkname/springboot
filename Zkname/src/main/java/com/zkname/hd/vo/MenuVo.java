package com.zkname.hd.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

/**
 * 菜单权限
 * 
 *
 * @version
 * @since Ver 1.1
 * @Date 2013-1-14
 */
@JsonAutoDetect
@JsonIgnoreProperties (value = {"parentId","deleStatus","url","lists","purviewLists"})
public class MenuVo implements Serializable, Cloneable {
	/**
	 * serialVersionUID:TODO
	 * 
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = -991596712277288593L;
	//
	private int id;
	// 菜单名
	private String name;
	// 权限名
	private String securityName;
	// 权限名
	private String url;
	// 父节点id
	private int parentId;
	//删除标准 - 隐藏显示
	private String deleStatus;
	// 子模块
	private ArrayList<MenuVo> lists = Lists.newArrayList();

	// 模块权限
	private ArrayList<PurviewVo> purviewLists = Lists.newArrayList();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public ArrayList<MenuVo> getLists() {
		return lists;
	}

	public void setLists(ArrayList<MenuVo> lists) {
		this.lists = lists;
	}

	public ArrayList<PurviewVo> getPurviewLists() {
		return purviewLists;
	}

	public void setPurviewLists(ArrayList<PurviewVo> purviewLists) {
		this.purviewLists = purviewLists;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getDeleStatus() {
		return deleStatus;
	}

	public void setDeleStatus(String deleStatus) {
		this.deleStatus = deleStatus;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> get(List<MenuVo> ls){
		List<Map> list=Lists.newArrayList();
		for(MenuVo m:ls){
			List<Map> list1=Lists.newArrayList();
			for(PurviewVo pv:m.getPurviewLists()){
				list1.add(ImmutableMap.of("id", pv.getId(),"name",pv.getName(),"s",m.getSecurityName()+"_"+pv.getSecurityName()));
			}
			list.add(ImmutableMap.of("id", m.getId(),"name",m.getName(),"s",m.getSecurityName(),"purviews", list1));
		}
		return list;
	}
}
