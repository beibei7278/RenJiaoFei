package com.sunsoft.zyebiz.b2e.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class EmptyUtil {

	/**
	 * 
	 * 判断是否为空，为空返回true,非空返回false
	 * 
	 * 
	 * @param obj
	 * 
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {

		if (obj == null) {

			return true;
		}

		if (obj instanceof String) {

			String str = (String) obj;

			return "".equals(str.trim());

		}

		if (obj instanceof Number) {

			Number num = (Number) obj;

			return num.byteValue() == 0;

		}

		if (obj instanceof Collection) {

			Collection col = (Collection) obj;

			return col.isEmpty();

		}

		if (obj instanceof Map) {

			Map map = (Map) obj;

			return map.isEmpty();

		}

		if (obj.getClass().getSimpleName().endsWith("[]")) {

			List<Object> list = Arrays.asList(obj);

			Object[] objs = (Object[]) list.get(0);

			return objs.length == 0;

		}

		return false;

	}

	/**
	 * 
	 * �ṩ�ַ����ϣ����飬map�ȳ�������пմ���
	 * 
	 * 
	 * 
	 * @param obj
	 * 
	 * @return
	 */

	public static boolean isNotEmpty(Object obj) {

		return !isEmpty(obj);

	}

}
