package com.zkname.frame.util.classinfo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Reflection cache.
 */
public class ReflectionCache{

    private static Map<String, ClassInfo> classInfoMap = new HashMap<String, ClassInfo>();
    
    public ReflectionCache()
    {
        super();
    }

    public static ClassInfo getClassInfo(String key)
    {
        return classInfoMap.get(key);
    }

    public static ClassInfo getClassInfo(Class<?> clazz)
    {
        return classInfoMap.get(clazz.getName());
    }

    public static ClassInfo getClassInfo(Object object)
    {
        return classInfoMap.get(object.getClass().getName());
    }

    public static ClassInfo putClassInfo(Class<?> clazz){
        return putClassInfo(clazz, Constants.METHOD_ACCESS);
    }

    public static ClassInfo putClassInfo(Class<?> clazz,Integer methodType)
    {
        if (clazz == null)
        {
            throw new IllegalArgumentException("Class can't be null");
        }
        ClassInfo classInfo = null;
        String className = clazz.getName();
        
        if (classInfoMap.containsKey(className))
        {
            classInfo = classInfoMap.get(className);
        }
        else
        {
        	synchronized(ReflectionCache.class){
                if (classInfoMap.containsKey(className))
                {
                	classInfo = classInfoMap.get(className);
                }else{
                	classInfo = ClassInfoUtils.createClassInfo(clazz, methodType);
	                classInfoMap.put(className, classInfo);
                }
        	}
            
        }
        return classInfo;
    }

    public static ClassInfo putClassInfo(Object object){
        if (object == null)
        {
            throw new IllegalArgumentException("Object can't be null");
        }
        Class<?> clazz = object.getClass();
        return putClassInfo(clazz);
    }

    public static ClassInfo putClassInfo(Object object,Integer methodType){
        if (object == null)
        {
            throw new IllegalArgumentException("Object can't be null");
        }
        Class<?> clazz = object.getClass();
        return putClassInfo(clazz, methodType);
    }
    
    public static void setValue(Object obj,Field field,Object value){
    	try {
        	field.setAccessible(true);
        	field.set(obj,value);
		}catch (Exception e) {
			throw CacheExceptionUtils.unchecked(e);
		}
    }
}
