package com.sherwin.rapid.base.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author Sherwin.Ye 674718661@qq.com
 * @date 2017/9/27.16:32
 * @desc 判断字符串是否为空，是否相等
 */
public class StringUtil {

	/**
	 * 返回字符串，否则返回默认值
	 * @param str
	 * @return
     */
	public static String getDefaultByNull(String str, String defaultStr) {
		if (str != null) {
			return str;
		} else {
			return defaultStr;
		}
	}
	/**
	 * 返回字符串，否则返回默认值
	 * @param str
	 * @return
	 */
	public static String getDefaultByBlank(String str, String defaultStr) {
		if (isNotBlank(str)) {
			return str;
		} else {
			return defaultStr;
		}
	}

	/**
	 * 判断两个字符串是否相等，都为null时返回false
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equals(String str1, String str2) {
		if (str1 != null) {
			return str1.equals(str2);
		} else {
			return false;
		}
	}

	/**
	 * 判断两个字符串是否相等，都为null时返回true
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equalsCanNull(String str1, String str2) {
		if (str1 != null) {
			return str1.equals(str2);
		} else if (str2 == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 字符串是否为空白 空白的定义如下： <br>
	 * 1、为null <br>
	 * 2、为不可见字符（如空格）<br>
	 * 3、""<br>
	 * 
	 * @param str 被检测的字符串
	 * @return 是否为空
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 字符串是否为非空白 空白的定义如下： <br>
	 * 1、不为null <br>
	 * 2、不为不可见字符（如空格）<br>
	 * 3、不为""<br>
	 * 
	 * @param str 被检测的字符串
	 * @return 是否为非空
	 */
	public static boolean isNotBlank(String str) {
		return false == isBlank(str);
	}

	/**
	 * 字符串是否为空，空的定义如下 1、为null <br>
	 * 2、为""<br>
	 * 
	 * @param str 被检测的字符串
	 * @return 是否为空
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 字符串是否为非空白 空白的定义如下： <br>
	 * 1、不为null <br>
	 * 2、不为""<br>
	 * 
	 * @param str 被检测的字符串
	 * @return 是否为非空
	 */
	public static boolean isNotEmpty(String str) {
		return false == isEmpty(str);
	}



	/**
	 * 验证大写字母
	 *
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isUpChar(String str) {
		String regex = "^[A-Z]+$";
		return match(regex, str);
	}

	/**
	 * 验证小写字母
	 *
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isLowChar(String str) {
		String regex = "^[a-z]+$";
		return match(regex, str);
	}

	/**
	 * 验证验证输入字母
	 *
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isLetter(String str) {
		String regex = "^[A-Za-z]+$";
		return match(regex, str);
	}

	/**
	 * 验证验证输入汉字
	 *
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isChinese(String str) {
		String regex = "^[\u4e00-\u9fa5],{0,}$";
		return match(regex, str);
	}

	/**
	 * @param regex 正则表达式字符串
	 * @param str   要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}


	/** 获取去掉"-"的UUID */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

}
