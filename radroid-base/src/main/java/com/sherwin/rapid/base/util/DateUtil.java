package com.sherwin.rapid.base.util;

import android.content.Context;
import android.text.format.DateFormat;


import com.sherwin.radroid.base.R;
import com.sherwin.rapid.base.core.RapidDroid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sherwin.Ye
 * @data 2015-11-30 下午8:25:04
 * @desc DateUtil.java
 */
public class DateUtil {
    public final static long ONE_WEEK = 7 * 24 * 60 * 60 * 1000;
    public final static long ONE_DAY = 24 * 60 * 60 * 1000;
    public final static long ONE_HOUR = 60 * 60 * 1000;
    public final static long ONE_MIN = 60 * 1000;
    public final static long ONE_SECOND = 1000;

    public static Context mContext;

    static {
        mContext = RapidDroid.application;
    }

    /**
     * 得到时区 0~12表示东半球；-12~-1为西半球
     *
     * @return
     */
    public static int getTimeZone() {
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();
        int timeZoneByte = timeZone.getRawOffset() / (60 * 60 * 1000);
        return timeZoneByte;
    }


    /**
     * 计时，常用于记录某段代码的执行时间，单位：毫秒
     *
     * @param timePre 之前记录的时间
     * @return 时间差，毫秒
     */
    public static long spendTime(long timePre) {
        return System.currentTimeMillis() - timePre;
    }

    public static String getListRefreshTime() {
        return DateFormat.format("HH:mm:ss", System.currentTimeMillis()).toString();
    }

    public static String getFormatTime(long time) {
        if (mContext != null) {
            return DateFormat.format(mContext.getString(R.string.common_date_format_date_time), time).toString();
        }
        return DateFormat.format("yyyy年MM月dd日 HH:mm:ss", time).toString();
    }

    /**
     * 设置月日时分时间格式
     *
     * @param time
     * @return
     */
    public static String getRoughFormatTime(long time) {
        if (mContext != null) {
            return DateFormat.format(mContext.getString(R.string.common_date_format_moth_time), time).toString();
        }
        return DateFormat.format("MM月dd日 HH:mm", time).toString();
    }

    /**
     * 获取星期  0-6 代表周日-周六
     *
     * @param time
     * @return
     */
    public static int getDayOfWeek(long time) {
        Date date = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 得到小时数
     *
     * @param time
     * @return
     */
    public static long getHours(long time) {
        return time / ONE_HOUR;
    }

    /**
     * 得到时间中的小时数<24H
     *
     * @param time
     * @return
     */
    public static int getHoursInTime(long time) {
        return (int) (getHours(time) % 24);
    }

    /**
     * 得到分钟数
     *
     * @param time
     * @return
     */
    public static long getMins(long time) {
        return time / ONE_MIN;
    }

    /**
     * 得到时间中的分钟数<60m
     *
     * @param time
     * @return
     */
    public static int getMinsInTime(long time) {
        return (int) (getMins(time) % 60);
    }

    /**
     * 得到秒数
     *
     * @param time
     * @return
     */
    public static long getSeconds(long time) {
        return time / ONE_SECOND;
    }

    /**
     * 得到时间中的秒数<60s yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static int getSecondsInTime(long time) {
        return (int) (getSeconds(time) % 60);
    }

    public static String formatDate(String dateString) {
        return dateString.replaceAll("T", " ");
    }

    public static String formatDate(String format, Date date) {
        if (date != null) {
            try {
                SimpleDateFormat df = new SimpleDateFormat(format);// 设置日期格式
                return df.format(date);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Date formatToDate(String format, String date) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);// 设置日期格式
            return df.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getTimeformat(long time, String format) {
        String timeString = "无";
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            Date dateline = new Date(time);
            timeString = df.format(dateline);
        } catch (Exception e) {
            return timeString;
        }
        return timeString;
    }

    public static String getTimeformat(String dateString, String oldFormat, String newFormat) {
        String timeString = "无";
        if (mContext != null) {
            timeString = mContext.getString(R.string.common_date_format_error);
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(oldFormat);
            SimpleDateFormat df2 = new SimpleDateFormat(newFormat);
            timeString = df2.format(df.parse(dateString.trim()));
        } catch (Exception e) {
            return timeString;
        }
        return timeString;
    }

    /**
     * 得到友好时间，精确到秒
     *
     * @param dateString
     * @param dateFormat
     * @return
     */
    public static String getFriendlyTimeByString(String dateString, String dateFormat) {
        dateString = formatDate(dateString);
        dateFormat = formatDate(dateFormat);
        String timeInterval = "无";
        if (mContext != null) {
            timeInterval = mContext.getString(R.string.common_date_format_error);
        }
        // "MM/dd/yyyy HH:mm:ss"
        try {
            if (dateString != null && !"".equals(dateString.trim())) {
                SimpleDateFormat df = new SimpleDateFormat(dateFormat);
                Date dateline = df.parse(dateString);
                Date nowdate = new Date();
                long seconds = (nowdate.getTime() - dateline.getTime()) / 1000;
                if (seconds < 60) {
                    timeInterval = "刚刚";
                    if (mContext != null) {
                        timeInterval = mContext.getString(R.string.common_date_just_now);
                    }
                } else if (seconds < 60 * 60) {
                    if (mContext != null) {
                        timeInterval = mContext.getString(R.string.common_date_minutes_ago, seconds / 60);
                    } else {
                        timeInterval = seconds / 60 + "分钟前";
                    }
                } else if (seconds < 60 * 60 * 24) {
                    if (mContext != null) {
                        timeInterval = mContext.getString(R.string.common_date_hours_ago, seconds / (60 * 60));
                    } else {
                        timeInterval = seconds / (60 * 60) + "小时前";
                    }
                } else if (seconds < 60 * 60 * 24 * 2) {
                    timeInterval = "昨天";
                    if (mContext != null) {
                        timeInterval = mContext.getString(R.string.common_date_yesterday);
                    }
                } else if (seconds < 60 * 60 * 24 * 3) {
                    timeInterval = "前天";
                    if (mContext != null) {
                        timeInterval = mContext.getString(R.string.common_date_before_yesterday);
                    }
                } else if (seconds < 60 * 60 * 24 * 7) {
                    if (mContext != null) {
                        timeInterval = mContext.getString(R.string.common_date_day_ago, seconds / (60 * 60 * 24));
                    } else {
                        timeInterval = seconds / (60 * 60 * 24) + "天前";
                    }
                } else {
                    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                    timeInterval = df2.format(df.parse(dateString.trim()));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return timeInterval;
        }
        return timeInterval;
    }


    /**
     * 得到友好时间
     *
     * @param date
     * @return
     */
    public static String getFriendlyTime(Date date) {
        if (date != null) {
            return getFriendlyTimeByLong(date.getTime());
        } else {
            return getFriendlyTimeByLong(0L);
        }
    }

    /**
     * 得到友好时间
     *
     * @param times
     * @return
     */
    public static String getFriendlyTimeByLong(Long times) {
        String timeInterval = "无";
        if (mContext != null) {
            timeInterval = mContext.getString(R.string.common_date_format_error);
        }
        // "MM/dd/yyyy HH:mm:ss"
        try {
            if (times <= 0) {
                return timeInterval;
            }
            Date dateline = new Date(times);
            Date nowdate = new Date();
            long seconds = (nowdate.getTime() - dateline.getTime()) / 1000;

            if (seconds < 0) {
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_just_now);
                } else {
                    timeInterval = "刚刚";
                }
            } else if (seconds < 60) {
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_seconds_ago, seconds);
                } else {
                    timeInterval = seconds + "秒前";
                }
            } else if (seconds < 60 * 60) {
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_minutes_ago, seconds / 60);
                } else {
                    timeInterval = seconds / 60 + "分钟前";
                }
            } else if (seconds < 60 * 60 * 24) {
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_hours_ago, seconds / (60 * 60));
                } else {
                    timeInterval = seconds / (60 * 60) + "小时前";
                }
            } else if (seconds < 60 * 60 * 24 * 2) {
                timeInterval = "昨天";
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_yesterday);
                }
            } else if (seconds < 60 * 60 * 24 * 3) {
                timeInterval = "前天";
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_before_yesterday);
                }
            } else if (seconds < 60 * 60 * 24 * 7) {
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_day_ago, seconds / (60 * 60 * 24));
                } else {
                    timeInterval = seconds / (60 * 60 * 24) + "天前";
                }
            } else {
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                timeInterval = df2.format(dateline);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return timeInterval;
        }
        return timeInterval;
    }

    /**
     * 婴儿时间，精确到几个月
     *
     * @param dateString
     * @param dateFormat
     * @return
     */
    public static String getBabyTimeByString(String dateString, String dateFormat) {
        dateString = formatDate(dateString);
        dateFormat = formatDate(dateFormat);
        String timeInterval = "无";
        if (mContext != null) {
            timeInterval = mContext.getString(R.string.common_date_format_error);
        }
        // "MM/dd/yyyy HH:mm:ss"
        try {
            if (dateString != null && !"".equals(dateString.trim())) {
                SimpleDateFormat df = new SimpleDateFormat(dateFormat);
                Date dateline = df.parse(dateString);
                Date nowdate = new Date();
                long seconds = (nowdate.getTime() - dateline.getTime()) / 1000;
                if (seconds < 60 * 60 * 24 * 30) {
                    if (mContext != null) {
                        timeInterval = mContext.getString(R.string.common_date_less_than_one_months);
                    } else {
                        timeInterval = "小于1个月";
                    }
                } else if (seconds < 60 * 60 * 24 * 365) {
                    int month = (int) (seconds / (60 * 60 * 24 * 30));
                    if (mContext != null) {
                        timeInterval = mContext.getString(R.string.common_date_a_few_moths, month);
                    } else {
                        timeInterval = month + "个月";
                    }
                } else {
                    int year = (int) (seconds / (60 * 60 * 24 * 365));
                    int month = (int) ((seconds % (60 * 60 * 24 * 365)) / (60 * 60 * 24 * 30));

                    if (mContext != null) {
                        timeInterval = mContext.getString(R.string.common_date_a_few_yeahs_and_moths, year, month);
                    } else {
                        timeInterval = year + "年" + month + "个月";
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return timeInterval;
        }
        return timeInterval;
    }

    /**
     * 格式化时间
     *
     * @param dateString
     * @param dateFormat
     * @return
     */
    public static String getDateFormat(String dateString, String dateFormat) {
        dateString = formatDate(dateString);
        dateFormat = formatDate(dateFormat);
        String timeInterval = "无";
        if (mContext != null) {
            timeInterval = mContext.getString(R.string.common_date_format_error);
        }
        // "MM/dd/yyyy HH:mm:ss"
        if (dateString != null && !"".equals(dateString.trim())) {
            Date date = new Date(dateString);
            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            timeInterval = df.format(date);
        }
        return timeInterval;
    }

    /**
     * 得到友好时间，精确到天
     *
     * @param dateString 时间字符串
     * @param dateFormat 时间格式
     * @return
     */
    public static String getDateRecentlyByString(String dateString, String dateFormat) {
        dateString = formatDate(dateString);
        dateFormat = formatDate(dateFormat);
        String timeInterval = "无";
        if (mContext != null) {
            timeInterval = mContext.getString(R.string.common_date_format_error);
        }
        // "MM/dd/yyyy HH:mm:ss"
        try {
            if (dateString != null && !"".equals(dateString.trim())) {
                SimpleDateFormat df = new SimpleDateFormat(dateFormat);
                Date dateline = df.parse(dateString);
                Date nowdate = new Date();
                long seconds = (nowdate.getTime() - dateline.getTime()) / 1000;
                if (seconds < 60 * 60 * 24) {
                    if (mContext != null) {
                        timeInterval = mContext.getString(R.string.common_date_today);
                    } else {
                        timeInterval = "今天";
                    }
                } else if (seconds < 60 * 60 * 24 * 2) {
                    if (mContext != null) {
                        timeInterval = mContext.getString(R.string.common_date_yesterday);
                    } else {
                        timeInterval = "昨天";
                    }
                } else if (seconds < 60 * 60 * 24 * 3) {
                    if (mContext != null) {
                        timeInterval = mContext.getString(R.string.common_date_before_yesterday);
                    } else {
                        timeInterval = "前天";
                    }
                } else if (seconds < 60 * 60 * 24 * 7) {
                    if (mContext != null) {
                        timeInterval = mContext.getString(R.string.common_date_day_ago, seconds / (60 * 60 * 24));
                    } else {
                        timeInterval = seconds / (60 * 60 * 24) + "天前";
                    }
                } else {
                    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                    timeInterval = df2.format(df.parse(dateString.trim()));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return timeInterval;
        }
        return timeInterval;
    }

    /**
     * 获取时间区间
     *
     * @param time
     * @return
     */
    public static String getDateInterval(Long time) {
        String timeInterval = "无";
        if (mContext != null) {
            timeInterval = mContext.getString(R.string.common_date_format_error);
        }
        try {
            Date date0 = new Date(time);
            long seconds = (System.currentTimeMillis() - date0.getTime()) / 1000;
            if (seconds < 60) {
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_just_now);
                } else {
                    timeInterval = "刚刚";
                }
            } else if (seconds < 60 * 60) {
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_minutes_ago, seconds / 60);
                } else {
                    timeInterval = seconds / 60 + "分钟前";
                }
            } else if (seconds < 60 * 60 * 24) {
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_hours_ago, seconds / (60 * 60));
                } else {
                    timeInterval = seconds / (60 * 60) + "小时前";
                }
            } else if (seconds < 60 * 60 * 24 * 2) {
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_yesterday);
                } else {
                    timeInterval = "昨天";
                }
            } else if (seconds < 60 * 60 * 24 * 3) {
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_before_yesterday);
                } else {
                    timeInterval = "前天";
                }
            } else if (seconds < 60 * 60 * 24 * 30) {
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_day_ago, seconds / (60 * 60 * 24));
                } else {
                    timeInterval = seconds / (60 * 60 * 24) + "天前";
                }
            } else if (seconds < 60 * 60 * 24 * 365) {
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_moths_ago, seconds / (60 * 60 * 24 * 30));
                } else {
                    timeInterval = seconds / (60 * 60 * 24 * 30) + "月前";
                }
            } else if (seconds >= 60 * 60 * 24 * 365) {
                if (mContext != null) {
                    timeInterval = mContext.getString(R.string.common_date_yeahs_ago, seconds / (60 * 60 * 24 * 30 * 365));
                } else {
                    timeInterval = seconds / (60 * 60 * 24 * 365) + "年前";
                }
            }
        } catch (Exception e) {
            return timeInterval;
        }
        return timeInterval;
    }

    /**
     * 得到今天剩余时间，毫秒
     */
    public static long getTodayLeftMillis() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long diff = cal.getTimeInMillis() - System.currentTimeMillis();
        return diff;
    }

    /**
     * 得到今天的第几毫秒
     *
     * @return
     */
    public static long getTodayMillis(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);//小时
        int minute = cal.get(Calendar.MINUTE);//分
        int second = cal.get(Calendar.SECOND);//秒
        long nowTime = (hour * 60 * 60 + minute * 60 + second) * 1000L;
        return nowTime;
    }


    /**
     * 得到本月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        // 1号的日期
        Date firstDayOfMonth = c.getTime();
        return firstDayOfMonth;
    }

    /**
     * 获取上个月第一天的方法
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取上个月最后一天的方法，这个稍微要变通一下，先将日期设置为本月的第一天，然后减去一天就变成了上个月的最后一天了：
     *
     * @param date
     * @return
     */
    public static Date getEndDayOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 得到这个月的天数
     *
     * @param date
     * @return
     */
    public static int getNowMonthDayNum(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * 得到上个月的天数
     *
     * @param date
     * @return
     */
    public static int getLastMonthDayNum(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * 今年年份
     *
     * @param date
     * @return
     */
    public static int getNowYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param dateA
     * @param dateB
     * @return
     */
    public static boolean isSameDay(Date dateA, Date dateB) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR) && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到当前时间
     *
     * @return
     */
    public static Date getToday() {
        return new Date();
    }

    /**
     * 得到昨日时间
     *
     * @return
     */
    public static Date getYesterday() {
        return new Date(System.currentTimeMillis() - ONE_DAY);
    }

    /**
     * 得到明天时间
     *
     * @return
     */
    public static Date getTomorrow() {
        return new Date(System.currentTimeMillis() + ONE_DAY);
    }

    /**
     * 判断是否是今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        try {
            if (date != null) {
                return isSameDay(date, new Date());
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是昨天
     *
     * @param date
     * @return
     */
    public static boolean isYesterday(Date date) {
        return isToday(new Date(date.getTime() + ONE_DAY));
    }

    /**
     * 判断是否是明天
     *
     * @param date
     * @return
     */
    public static boolean isTomorrow(Date date) {
        return isToday(new Date(date.getTime() - ONE_DAY));
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
