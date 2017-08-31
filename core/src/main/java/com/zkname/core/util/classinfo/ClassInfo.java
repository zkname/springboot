package com.zkname.core.util.classinfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class ClassInfo {
	
	protected static final Logger logger = LoggerFactory.getLogger(ClassInfo.class);
	
	//类名
    private String className;
    //class
    private Class<?> clazz;
 
    //get 方法
    private Map<String, Method> getterMethodMap = Maps.newHashMap();
 
    //set 方法
    private Map<String, Method> setterMethodMap = Maps.newHashMap();
 
    //其他 方法
    private Map<String, Method> otherMethodMap = Maps.newHashMap();
    
    //bean元素
    private Map<String, Field> fieldMap = Maps.newLinkedHashMap();
    
    public ClassInfo(Class<?> clazz)
    {
        this.className = clazz.getName();
        this.clazz = clazz;
    }
    
    
    /**
     * 获取 get方法
     * @param key
     * @return
     */
    public Method getGetterMethod(String key){
    	
        Method method = getterMethodMap.get(key);
        if (method == null)
        {
            if (key.startsWith(Constants.PREFIX_IS))
            {
                String subName = key.substring(Constants.PREFIX_IS.length());
                if (subName.length() == 1)
                {
                    method = getterMethodMap.get(subName.toLowerCase());
                }
                else if (subName.length() > 1)
                {
                    if (Character.isUpperCase(subName.charAt(1)))
                    {
                        method = getterMethodMap.get(subName);
                    }
                    else
                    {
                        method = getterMethodMap.get(StringUtils.uncapitalize(subName));
                    }
                }
            }
            else if (key.startsWith(Constants.PREFIX_GET))
            {
                String subName = key.substring(Constants.PREFIX_GET.length());
                if (subName.length() == 1)
                {
                    method = getterMethodMap.get(subName.toLowerCase());
                }
                else if (subName.length() > 1)
                {
                    if (Character.isUpperCase(subName.charAt(1)))
                    {
                        method = getterMethodMap.get(subName);
                    }
                    else
                    {
                        method = getterMethodMap.get(StringUtils.uncapitalize(subName));
                    }
                }
            }
        }
        return method;
    }
    
    /**
     * 获取 set 方法
     * @param key
     * @return
     */
    public Method getSetterMethod(String key){
    	
        Method method = setterMethodMap.get(key);
 
        if (method == null && key.startsWith(Constants.PREFIX_SET))
        {
            String subName = key.substring(Constants.PREFIX_SET.length());
            if (subName.length() == 1)
            {
                method = setterMethodMap.get(subName.toLowerCase());
            }
            else if (subName.length() > 1)
            {
                if (Character.isUpperCase(subName.charAt(1)))
                {
                    method = setterMethodMap.get(subName);
                }
                else
                {
                    method = setterMethodMap.get(StringUtils.uncapitalize(subName));
                }
            }
        }
 
        return method;
    }
    
    /**
     * 获取其他方法
     * @param key
     * @return
     */
    public Method getOtherMethod(String key)
    {
        Method method = otherMethodMap.get(key);
        return method;
    }
    
    /**
     * 获取其他方法
     * @param key
     * @return
     */
    public Field getFieldMap(String key)
    {
        return fieldMap.get(key);
    }
    
    public void putGetterMethod(String key, Method method)
    {
        if (key == null)
        {
            throw new IllegalArgumentException("Key can't be null");
        }
        if (method == null)
        {
            throw new IllegalArgumentException("Method can't be null");
        }
 
        if (getterMethodMap.containsKey(key))
        {
        	logger.warn("Getter method map contains a mapping for the specified key {}", key);
        	return;
            //throw new IllegalArgumentException("Getter method map contains a mapping for the specified key");
        }
 
        getterMethodMap.put(key, method);
    }
 
    public void putSetterMethod(String key, Method method)
    {
        if (key == null)
        {
            throw new IllegalArgumentException("Key can't be null");
        }
        if (method == null)
        {
            throw new IllegalArgumentException("Method can't be null");
        }
 
        if (setterMethodMap.containsKey(key))
        {
            //throw new IllegalArgumentException("Setter method map contains a mapping for the specified key");
            logger.warn("Setter method map contains a mapping for the specified key {}", key);
        	return;
        }
 
        setterMethodMap.put(key, method);
    }
 

    public void putOtherMethod(String key, Method method)
    {
        if (key == null)
        {
            throw new IllegalArgumentException("Key can't be null");
        }
        if (method == null)
        {
            throw new IllegalArgumentException("Method can't be null");
        }
 
        if (otherMethodMap.containsKey(key))
        {
            //throw new IllegalArgumentException("Other method map contains a mapping for the specified key");
            logger.warn("Other method map contains a mapping for the specified key {}", key);
        	return;
        }
 
        otherMethodMap.put(key, method);
    }
    
    public void putFieldMap(String key, Field field)
    {
        if (key == null)
        {
            throw new IllegalArgumentException("Key can't be null");
        }
        if (field == null)
        {
            throw new IllegalArgumentException("Field can't be null");
        }
 
        if (fieldMap.containsKey(key))
        {
            //throw new IllegalArgumentException("Other method map contains a mapping for the specified key");
            logger.warn("Field map contains a mapping for the specified key {}", key);
        	return;
        }
 
        fieldMap.put(key, field);
    }
    
    
 
    public String getClassName()
    {
        return className;
    }
 
    public void setClassName(String className)
    {
        this.className = className;
    }
 
    public Class<?> getClazz()
    {
        return clazz;
    }
 
    public void setClazz(Class<?> clazz)
    {
        this.clazz = clazz;
    }
 
    public Map<String, Method> getGetterMethodMap()
    {
        return getterMethodMap;
    }
 
    public void setGetterMethodMap(Map<String, Method> getterMethodMap)
    {
        this.getterMethodMap = getterMethodMap;
    }
 
    public Map<String, Method> getSetterMethodMap()
    {
        return setterMethodMap;
    }
 
    public void setSetterMethodMap(Map<String, Method> setterMethodMap)
    {
        this.setterMethodMap = setterMethodMap;
    }
 
    public Map<String, Method> getOtherMethodMap()
    {
        return otherMethodMap;
    }
 
    public void setOtherMethodMap(Map<String, Method> otherMethodMap)
    {
        this.otherMethodMap = otherMethodMap;
    }


	public Map<String, Field> getFieldMap() {
		return fieldMap;
	}


	public void setFieldMap(Map<String, Field> fieldMap) {
		this.fieldMap = fieldMap;
	}
    
    
}
