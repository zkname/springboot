package com.zkname.demo.controller.admin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.zkname.core.controller.BaseController;
import com.zkname.core.util.CodeHttpUtil;
import com.zkname.core.util.jackson.JsonUtil;
import com.zkname.demo.entity.SysUser;
import com.zkname.demo.security.LoginUser;
import com.zkname.demo.security.springsecurity.MyInvocationSecurityMetadataSource;
import com.zkname.demo.security.springsecurity.SpringSecurityUtils;
import com.zkname.demo.service.SysUserService;
import com.zkname.demo.vo.MenuVo;

/**
 * 首页
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController{
	
	@Autowired
	private MyInvocationSecurityMetadataSource myInvocationSecurityMetadataSource;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws InterruptedException, ExecutionException, Exception {
        ModelAndView mv = new ModelAndView("admin/index");
		return mv;
	}
	
	@RequestMapping(value = "/bodyMenu")
	public ModelAndView bodyMenu(HttpServletRequest request,HttpServletResponse response) throws IOException, ClassNotFoundException {
		LoginUser lu=SpringSecurityUtils.getCurrentUser();
		MenuVo mvo=myInvocationSecurityMetadataSource.getPurviewMenuTemplate();
		//克隆list
		List<MenuVo> dest=deepcopy(mvo.getLists());
		menuVoClear(dest, lu);
        ModelAndView mv = new ModelAndView("admin/body_menu");
        mv.addObject("MenuVos",dest);
		return mv;
	}
	
    public ArrayList<MenuVo> deepcopy(ArrayList<MenuVo> src) throws IOException, ClassNotFoundException {  
        ByteArrayOutputStream byteout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteout);
        out.writeObject(src);
        ByteArrayInputStream bytein = new ByteArrayInputStream(byteout.toByteArray()); 
        ObjectInputStream in = new ObjectInputStream(bytein);
        @SuppressWarnings("unchecked")
		ArrayList<MenuVo> dest = (ArrayList<MenuVo>) in.readObject();
        return dest;  
  
    }  
	/**
	 * menuVoClear(权限排重菜单)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param mvos
	 * @param lu 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	private void menuVoClear(List<MenuVo> mvos,LoginUser lu){
		for(int i=0;i<mvos.size();i++){
			MenuVo mvo=mvos.get(i);
			boolean istrue=false;
			for(GrantedAuthority grantedAuthority:lu.getAuthorities()){
				if(grantedAuthority.getAuthority().equals(mvo.getSecurityName())){
					istrue=true;
					break;
				}
			}
			if(!istrue){
				mvos.remove(i);
				i--;
			}
			menuVoClear(mvo.getLists(), lu);
		}
	}
}

