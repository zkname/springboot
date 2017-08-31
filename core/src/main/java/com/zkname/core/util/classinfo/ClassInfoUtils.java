package com.zkname.core.util.classinfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.apache.commons.lang3.StringUtils;

public class ClassInfoUtils {

	public ClassInfoUtils() {
	}

	public ClassInfo createClassInfo(Object object) {
		return createClassInfo(object.getClass(), null);
	}

	public ClassInfo createClassInfo(Object object, Integer methodType) {
		return createClassInfo(object.getClass(), methodType);
	}

	public ClassInfo createClassInfo(Class<?> clazz) {
		return createClassInfo(clazz, null);
	}

	public static ClassInfo createClassInfo(Class<?> clazz, Integer methodType) {
		if (clazz.isInterface()) {
			throw new IllegalArgumentException("Class can't be an interface.");
		}

		if (methodType == null) {
			methodType = Constants.METHOD_ACCESS;
		} else if (methodType != Constants.METHOD_ALL && methodType != Constants.METHOD_ACCESS && methodType != Constants.METHOD_GETTER && methodType != Constants.METHOD_SETTER && methodType != Constants.FIELD) {
			throw new IllegalArgumentException("Method type is invalid.");
		}

		ClassInfo classInfo = new ClassInfo(clazz);
		if (methodType != Constants.FIELD) {
			Method[] methods = clazz.getMethods();
			for (Method tmpMethod : methods) {
				int modifiers = tmpMethod.getModifiers();
				if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
					addMethod(classInfo, tmpMethod, methodType);
				}
			}
		}

		if (methodType == Constants.METHOD_ACCESS || methodType == Constants.FIELD) {
			superField(classInfo, clazz);
		}
		return classInfo;
	}
	
	private static void superField(ClassInfo classInfo,Class<?> clazz){
		if(clazz!=java.lang.Object.class){
			// clazz
			// 只获取本类的所有属性，不获取父类的
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (!Modifier.isStatic(field.getModifiers())) {
					classInfo.putFieldMap(field.getName(), field);
				}
			}
			superField(classInfo, clazz.getSuperclass());
		}
	}

	private static void addMethod(ClassInfo classInfo, Method method, Integer methodType) {
		String methodName = method.getName();
		if (methodType == Constants.METHOD_ALL) {
			if (methodName.startsWith(Constants.PREFIX_IS)) {
				addIsMethod(classInfo, method, methodType);
			} else if (methodName.startsWith(Constants.PREFIX_GET)) {
				addGetterMethod(classInfo, method, methodType);
			} else if (methodName.startsWith(Constants.PREFIX_SET)) {
				addSetterMethod(classInfo, method, methodType);
			} else {
				classInfo.putOtherMethod(methodName, method);
			}
		} else if (methodType == Constants.METHOD_ACCESS) {
			if (methodName.startsWith(Constants.PREFIX_IS)) {
				addIsMethod(classInfo, method, methodType);
			} else if (methodName.startsWith(Constants.PREFIX_GET)) {
				addGetterMethod(classInfo, method, methodType);
			} else if (methodName.startsWith(Constants.PREFIX_SET)) {
				addSetterMethod(classInfo, method, methodType);
			}
		} else if (methodType == Constants.METHOD_GETTER) {
			if (methodName.startsWith(Constants.PREFIX_IS)) {
				addIsMethod(classInfo, method, methodType);
			} else if (methodName.startsWith(Constants.PREFIX_GET)) {
				addGetterMethod(classInfo, method, methodType);
			}
		} else if (methodType == Constants.METHOD_SETTER) {
			if (methodName.startsWith(Constants.PREFIX_SET)) {
				addSetterMethod(classInfo, method, methodType);
			}
		}
	}

	private static void addIsMethod(ClassInfo classInfo, Method method, Integer methodType) {
		String methodName = method.getName();
		String subName = methodName.substring(Constants.PREFIX_IS.length());

		if (method.getReturnType().equals(boolean.class) && method.getParameterTypes().length == 0) {
			if (subName.length() == 0 && methodType == Constants.METHOD_ALL) {
				classInfo.putOtherMethod(methodName, method);
			} else if (subName.length() == 1) {
				classInfo.putGetterMethod(subName.toLowerCase(), method);
			} else {
				if (Character.isUpperCase(subName.charAt(1))) {
					classInfo.putGetterMethod(subName, method);
				} else {
					classInfo.putGetterMethod(StringUtils.uncapitalize(subName), method);
				}
			}
		} else if (methodType == Constants.METHOD_ALL) {
			classInfo.putOtherMethod(methodName, method);
		}
	}

	private static void addGetterMethod(ClassInfo classInfo, Method method, Integer methodType) {
		String methodName = method.getName();
		String subName = methodName.substring(Constants.PREFIX_GET.length());

		if (!method.getReturnType().equals(void.class) && method.getParameterTypes().length == 0) {
			if (subName.length() == 0 && methodType == Constants.METHOD_ALL) {
				classInfo.putOtherMethod(methodName, method);
			} else if (subName.length() == 1) {
				classInfo.putGetterMethod(subName.toLowerCase(), method);
			} else {
				if (Character.isUpperCase(subName.charAt(1))) {
					classInfo.putGetterMethod(subName, method);
				} else {
					classInfo.putGetterMethod(StringUtils.uncapitalize(subName), method);
				}
			}
		} else if (methodType == Constants.METHOD_ALL) {
			classInfo.putOtherMethod(methodName, method);
		}
	}

	private static void addSetterMethod(ClassInfo classInfo, Method method, Integer methodType) {
		String methodName = method.getName();
		String subName = methodName.substring(Constants.PREFIX_SET.length());

		if (method.getReturnType().equals(void.class) && method.getParameterTypes().length == 1) {
			if (subName.length() == 0 && methodType == Constants.METHOD_ALL) {
				classInfo.putOtherMethod(methodName, method);
			} else if (subName.length() == 1) {
				classInfo.putSetterMethod(subName.toLowerCase(), method);
			} else {
				if (Character.isUpperCase(subName.charAt(1))) {
					classInfo.putSetterMethod(subName, method);
				} else {
					classInfo.putSetterMethod(StringUtils.uncapitalize(subName), method);
				}
			}
		} else if (methodType == Constants.METHOD_ALL) {
			classInfo.putOtherMethod(methodName, method);
		}
	}
	
	
}