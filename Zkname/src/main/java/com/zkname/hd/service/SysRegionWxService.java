package com.zkname.hd.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zkname.frame.service.BaseService;
import com.zkname.frame.util.spring.SpringContextHolder;
import com.zkname.hd.dao.SysRegionWxDAO;
import com.zkname.hd.entity.SysRegion;
import com.zkname.hd.entity.SysRegionWx;

@Service
@Transactional(rollbackFor=Exception.class)//注解实现事务，所有异常都回滚；
public class SysRegionWxService extends BaseService<SysRegionWx> {
	
	@Autowired
	private SysRegionWxDAO dao;

	@Transactional(readOnly=true)//非事务处理
	public SysRegionWxDAO getDAO() {
		return dao;
	}
	
    public void delete(Object [] ids){
    	for(Object id:ids){
    		this.getDAO().deleteId(id);
    	}
    }
    
    
    
    public static void main(String[] args) {
		String path = "C:\\Users\\Administrator\\Desktop\\mmregioncode_zh_CN_copy.txt";
		File file = new File(path);
		BufferedReader reader = null;
        try {
        	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/applicationContext*.xml");
        	SpringContextHolder contextHolder = new SpringContextHolder();
    		contextHolder.setApplicationContext(context);
    		SysRegionService sysRegionService = SpringContextHolder.getBean(SysRegionService.class);
    		SysRegionWxService sysRegionWxService = SpringContextHolder.getBean(SysRegionWxService.class);
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            SysRegionWx sysRegionWx = null;
            // 一次读入一行，直到读入null为文件结束
            System.out.println("读取文件");
            SysRegion province = null;
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				// System.out.println("line " + line + ": " + tempString);
				// System.out.println(tempString.substring(tempString.lastIndexOf("_")+1,
				// tempString.lastIndexOf("|")));
				//省
				String name = tempString.substring(tempString.lastIndexOf("|") + 1);
				if (line == 1) {
					tempString = tempString.substring(1);
				}
				if(tempString.trim().lastIndexOf("_")==2){
					System.out.println(name+"省");
					province = sysRegionService.getDAO().findProviceByName(name);
					if (province == null) {
						System.out.println("line " + line + ": " + tempString);
					}else{
						try {
							sysRegionWx = new SysRegionWx();
							BeanUtils.copyProperties(province, sysRegionWx);
							sysRegionWx.setName(name);
							sysRegionWxService.save(sysRegionWx);
						} catch (Exception e) {
							System.out.println("line " + line + ": " + tempString);
						}
					}
				} else {
					// 市or县
					SysRegion sysRegion = sysRegionService.getDAO().findByProviceNameAndCityName(name, province.getShortName());
					if (sysRegion == null) {
						System.out.println("line " + line + ": " + tempString);
					} else {
						try {
							sysRegionWx = new SysRegionWx();
							BeanUtils.copyProperties(sysRegion, sysRegionWx);
							sysRegionWx.setName(name);
							sysRegionWx.setLevelType(2);
							sysRegionWx.setParentId(province.getId());
							sysRegionWxService.save(sysRegionWx);
						} catch (Exception e) {
							System.out.println("line " + line + ": " + tempString);
						}
					}
				}
				line++;
			}
			System.out.println("写入完毕");
            reader.close();
        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
	}
}