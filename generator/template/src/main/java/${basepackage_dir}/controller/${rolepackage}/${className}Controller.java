<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign toclassName = table.toLowerCaseClassName>
package ${basepackage}.controller.${rolepackage};

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ${basepackage}.page.*;
import ${basepackage}.entity.*;
import ${basepackage}.service.*;
import com.zkname.core.controller.BaseController;
import com.zkname.core.util.spring.annotation.AvoidDuplicateSubmission;

@Controller
@RequestMapping(value = "/${rolepackage}/${toclassName}")
public class ${className}Controller extends BaseController{
	
	// 用户业务层类
	@Autowired
	private ${className}Service service;
	
	/**
	 * list(列表)
	 */
	@AvoidDuplicateSubmission(needSaveToken=true)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(Page${className} page,HttpServletRequest request,HttpServletResponse response) {
		page.setHttpServletRequestValue(request);
		page.query();
        ModelAndView mv = new ModelAndView("${rolepackage}/${toclassName}/list");
        mv.addObject("page", page);
		return mv;
	}
	
	/**
	 * add(添加页面)
	 */
	@AvoidDuplicateSubmission(needSaveToken=true)
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) {
		
        ModelAndView mv = new ModelAndView("${rolepackage}/${toclassName}/add");
		return mv;
	}
	
	/**
	 * addInput(添加提交功能)
	 */
	@AvoidDuplicateSubmission(needRemoveToken=true)
	@RequestMapping(value = "/addInput", method = RequestMethod.POST)
	public RedirectView add(${className} entity,HttpServletRequest request,HttpServletResponse response) {
		service.save(entity);
		RedirectView mv = new RedirectView("list");
		return mv;
	}
	
	
	/**
	 * update(修改页面)
	 */
	@AvoidDuplicateSubmission(needSaveToken=true)
	@RequestMapping(value = "/update/{<@byId/>}", method = RequestMethod.GET)
	public ModelAndView update(@PathVariable <@newIdkey/>,HttpServletRequest request,HttpServletResponse response) {
		${className} entity = service.findById(<@newIdkey1/>);
        ModelAndView mv = new ModelAndView("${rolepackage}/${toclassName}/update");
        mv.addObject("entity", entity);
		return mv;
	}
	
	/**
	 * updateInput(修改提交功能)
	 */
	@AvoidDuplicateSubmission(needRemoveToken=true)
	@RequestMapping(value = "/updateInput", method = RequestMethod.POST)
	public RedirectView updateInput(${className} entity,<@newIdkey/>,HttpServletRequest request,HttpServletResponse response) {
		${className} entityUpdate = service.findById(<@newIdkey1/>);
		//获取参数实体，操作更新实体，不拷贝字段
		BeanUtils.copyProperties(entity,entityUpdate,new String[]{<@newIdkey2/>});
		service.update(entityUpdate);
		RedirectView mv = new RedirectView("list");
		return mv;
	}
	
	/**
	 * delete(删除功能)
	 */
	@AvoidDuplicateSubmission(needRemoveToken=true)
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public RedirectView delete(Object [] ids,HttpServletRequest request,HttpServletResponse response) {
		RedirectView mv = new RedirectView("list");
		service.delete(ids);
		return mv;
	}
}
<#macro byId>
<#list table.columns as column><#t>
<#if column.pk>
${column.sqlName}</#if><#t>
</#list>
</#macro>


<#macro newIdkey>
<#local idjavaType="" />
<#assign columnNames=""/> 
<#list table.columns as column>
<#if column.pk>
<#if columnNames=="">
<#assign columnNames=columnNames+column.javaType+" "+column.columnNameLower /> 
<#else>
<#assign columnNames=columnNames+","+column.javaType+" "+column.columnNameLower /> 
</#if>
<#if idjavaType=="">
<#local idjavaType='true'/>
<#else>
<#local idjavaType="false"/>
</#if>
</#if>
</#list>
<#if idjavaType="false">${columnNames}<#else>${table.idColumn.javaType} ${table.idColumn.columnNameLower}</#if></#macro>



<#macro newIdkey1>
<#local idjavaType="" />
<#assign columnNames=""/> 
<#list table.columns as column>
<#if column.pk>
<#if columnNames=="">
<#assign columnNames=columnNames+column.columnNameLower /> 
<#else>
<#assign columnNames=columnNames+","+column.columnNameLower /> 
</#if>
<#if idjavaType=="">
<#local idjavaType='true'/>
<#else>
<#local idjavaType="false"/>
</#if>
</#if>
</#list>
<#if idjavaType="false">new Object[]{${columnNames}}<#else>${table.idColumn.columnNameLower}</#if></#macro>


<#macro newIdkey2>
<#local idjavaType="" />
<#assign columnNames=""/> 
<#list table.columns as column>
<#if column.pk>
<#if columnNames=="">
<#assign columnNames=columnNames+"\""+column.columnNameLower+"\"" /> 
<#else>
<#assign columnNames=columnNames+",\""+column.columnNameLower+"\"" /> 
</#if>
<#if idjavaType=="">
<#local idjavaType='true'/>
<#else>
<#local idjavaType="false"/>
</#if>
</#if>
</#list>
<#if idjavaType="false">${columnNames}<#else>"${table.idColumn.columnNameLower}"</#if></#macro>
