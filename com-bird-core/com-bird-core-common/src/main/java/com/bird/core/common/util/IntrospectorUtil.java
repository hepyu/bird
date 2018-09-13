package com.bird.core.common.util;

import org.apache.commons.lang3.StringUtils;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * 反射工具类
 * 
 * 这个类禁止在业务中使用，只能用于系统初始化。 by hepengyuan 2018.8.17
 * 
 * <p>
 * Created by jiangjun on 2016/7/15.
 */
public class IntrospectorUtil {

	/**
	 * 判断一个实体的属性时候全是空
	 *
	 * @param object
	 * @return: true:全为空 false：不为空
	 */
	public static Boolean entityIsNull(Object object)
			throws IntrospectionException, InvocationTargetException, IllegalAccessException {
		if (object == null) {
			return true;
		}
		BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass(), Object.class);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			String clazzSimpleName = pd.getReadMethod().getReturnType().getSimpleName();
			Object objValue = pd.getReadMethod().invoke(object);
			if (clazzSimpleName.equals(String.class.getSimpleName())) {
				String value = (String) objValue;
				if (StringUtils.isNotBlank(value)) {
					return false;
				}
			} else {
				if (objValue != null) {
					return false;
				}
			}
		}
		return true;
	}

}
