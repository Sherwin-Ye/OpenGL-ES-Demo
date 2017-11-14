package com.sherwin.rapid.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sherwin.Ye
 * @data 2016年4月21日 上午9:57:45
 * @desc NumberUtil.java
 */
public class NumberUtil {




    /**
     * 判断字符串是否是整型
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.trim().length() <= 0) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    /**
     * 验证非零的正整数
     *
     * @param str 待验证的字符串
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isPositiveInteger(String str) {
        String regex = "^\\+?[1-9][0-9]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 是否为0或者空
     *
     * @param number
     * @return
     */
    public static Integer valueOfInt(Integer number) {
        return number == null ? 0 : number;
    }

    /**
     * 是否为0或者空
     *
     * @param number
     * @return
     */
    public static Long valueOfLong(Long number) {
        return number == null ? 0L : number;
    }

    /**
     * 是否为0或者空
     *
     * @param number
     * @return
     */
    public static Short valueOfShort(Short number) {
        return number == null ? 0 : number;
    }

    /**
     * 是否为0或者空
     *
     * @param number
     * @return
     */
    public static Float valueOfFloat(Float number) {
        return number == null ? 0 : number;
    }

    /**
     * 是否为0或者空
     *
     * @param number
     * @return
     */
    public static Double valueOfDouble(Double number) {
        return number == null ? 0 : number;
    }

    /**
     * 是否为0或者空
     *
     * @param number
     * @return
     */
    public static boolean isZeroOrNull(Number number) {
        return number == null || number.doubleValue() == 0;
    }

}
