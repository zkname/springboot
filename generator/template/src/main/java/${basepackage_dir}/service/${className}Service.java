<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import ${basepackage}.dao.*;
import ${basepackage}.entity.*;
import com.yuewuxian.frame.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class ${className}Service extends BaseService<${className}> {
	
	@Autowired
	private ${className}DAO dao;

	@Transactional(readOnly=true)//非事务处理
	public ${className}DAO getDAO() {
		return dao;
	}
	
    public void delete(Object [] ids){
    	for(Object id:ids){
    		this.getDAO().deleteId(id);
    	}
    }
}