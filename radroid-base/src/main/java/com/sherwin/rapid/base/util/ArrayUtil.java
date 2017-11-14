package com.sherwin.rapid.base.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author Sherwin.Ye
 * @data 2016年4月21日 上午9:51:08
 * @desc ArrayUtil.java
 */
public class ArrayUtil {

	/**
	 * map是否为空
	 * @param map
	 * @return
	 */
	public static boolean isMapEmpty(Map<?, ?> map) {
		return map == null || map.size() == 0;
	}

	/**
	 * map是否不为空
	 * @param map
	 * @return
	 */
	public static boolean isMapNotEmpty(Map<?, ?> map) {
		return !isMapEmpty(map);
	}

	/**
	 * array是否为空
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

	/**
	 * array是否不为空
	 * @param array
	 * @return
	 */
	public static boolean isNotEmpty(Object[] array) {
		return !isEmpty(array);
	}

	/**
	 * List是否为空
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(Collection<?> list) {
		return list == null || list.size() == 0;
	}

	/**
	 * List是否不为空
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> list) {
		return !isEmpty(list);
	}
}
