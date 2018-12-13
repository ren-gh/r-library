
package com.rengh.lib.java.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rengh on 17-5-29.
 */
public class DateUtils {
    private final static String TAG = "DateUtils";

    private DateUtils() {
    }

    public static int getDaysNumber(String date) {
        int index = -1;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date d1 = null;
            Date d2 = null;
            d1 = sdf.parse(date);
            d2 = sdf.parse(getNowDate());
            long t1 = d1.getTime();
            long t2 = d2.getTime();
            if (t1 >= t2) {
                index = (int) ((t1 - t2) / (24 * 60 * 60 * 1000l));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    /**
     * 通过简单的日期对比，判断当前日期是否正确。
     *
     * @return true或false
     * @see 2017-05-22 00:00:00
     */
    public static boolean isDateRight() {
        long flagTime = 0;
        try {
            flagTime = getMillis("2017-05-22 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (flagTime < System.currentTimeMillis()) {
            System.out.print(TAG+" system time is right.");
            return true;
        } else {
            System.out.print(TAG+" system time is error.");
            return false;
        }
    }

    /**
     * 判断两个日期打的大小
     *
     * @param date1      第一个日期
     * @param date2      第二个日期
     * @param dateFormat 日期格式
     * @return date1大于date2时返回1，日期相同时返回0，date1小于date2时返回-1，异常情况返回-2
     */
    public static int compareDate(String date1, String date2, String dateFormat) {
        int result = -2;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
            Date d1 = null;
            Date d2 = null;
            d1 = sdf.parse(date1);
            d2 = sdf.parse(date2);
            result = d1.compareTo(d2);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.print(TAG+" parse time error");
        } catch (Exception e) {
            System.out.print(TAG+" Exception: " + e.getMessage());
        }
        return result;
    }

    /**
     * 判断两个日期打的大小：yyyy-MM-dd
     *
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return date1大于date2时返回1，日期相同时返回0，date1小于date2时返回-1，异常情况返回-2
     */
    public static int compareDate(String date1, String date2) {
        return compareDate(date1, date2, "yyyy-MM-dd");
    }

    /**
     * 判断当前日期和指定日期的比对结果：yyyy-MM-dd
     *
     * @param date 指定日期
     * @return 当前日期大返回1，date大返回-1，日期相同时返回0，常情况返回-2
     */
    public static int compareDate(String date) {
        int result = -1;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String now = sdf.format(System.currentTimeMillis());
            Date playDate = null;
            Date nowdate = null;
            playDate = sdf.parse(date);
            nowdate = sdf.parse(now);
            result = nowdate.compareTo(playDate);
        } catch (Exception e) {
            System.out.print(TAG+" Exception: " + e.getMessage());
        }
        return result;
    }

    /**
     * 返回格式：2015-07-08
     */
    public static String getDate(int day) {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            if (0 == day) {
                date = sdf.format(System.currentTimeMillis());
            } else {
                date = sdf.format(System.currentTimeMillis() + day * 24 * 60 * 60 * 1000l);
            }
        } catch (Exception e) {
            System.out.print(TAG+" Exception: " + e.getMessage());
        }
        return date;
    }

    /**
     * 返回格式：2015-07-08
     */
    public static String getTomorrowDate() {
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            date = sdf.format(System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000l);
        } catch (Exception e) {
            System.out.print(TAG+" Exception: " + e.getMessage());
        }
        return date;
    }

    /**
     * 返回格式：2015-07-08 00:00:00
     */
    public static String getTomorrowTime() {
        String time = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String now = sdf.format(System.currentTimeMillis() + 1 * 24 * 60 * 60 * 1000l);
            long zeroHourMillis = 0;
            try {
                zeroHourMillis = getMillis(now + " " + "00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            time = sdf.format(zeroHourMillis);
        } catch (Exception e) {
            System.out.print(TAG+" Exception: " + e.getMessage());
        }
        return time;
    }

    /**
     * 返回格式：2015-07-08
     */
    public static String getNowDate() {
        String now = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            now = sdf.format(System.currentTimeMillis());
        } catch (Exception e) {
            System.out.print(TAG+" Exception: " + e.getMessage());
        }
        return now;
    }

    /**
     * 返回格式：2015-07-08 08:00:00
     */
    public static String getNowTime() {
        String now = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            now = sdf.format(System.currentTimeMillis());
        } catch (Exception e) {
            System.out.print(TAG+" Exception: " + e.getMessage());
        }
        return now;
    }

    /**
     * 返回 指定时间的毫秒数 减 当前日期的毫秒数；参数格式： yyyy-MM-dd HH:mm:ss，返回null代表计算失败
     */
    public static long getMillisDistanceNow(String dstr) throws ParseException {
        long distance = -1;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = sdf.parse(dstr);
            long s1 = date.getTime();
            long s2 = System.currentTimeMillis();
            distance = s1 - s2;
        } catch (Exception e) {
            System.out.print(TAG+" Exception: " + e.getMessage());
        }
        return distance;
    }

    /**
     * 获取指定时间的毫秒数，参数格式： yyyy-MM-dd HH:mm:ss，返回null代表计算失败
     */
    public static long getMillis(String dstr) throws ParseException {
        long s1 = -1;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = sdf.parse(dstr);
            s1 = date.getTime();
        } catch (Exception e) {
            System.out.print(TAG+" Exception: " + e.getMessage());
        }
        return s1;
    }

    public static String formatTime(String showTime) {
        String result = null;
        try {
            long time = -1;
            time = Long.valueOf(FileUtils.removeWhitespace(showTime));
            result = formatTime(null, time);
        } catch (Exception e) {
            System.out.print(TAG+" Exception: " + e.getMessage());
        }
        return result;
    }

    public static String formatTime(long showTime) {
        return formatTime(null, showTime);
    }

    public static String formatTime(String format, long showTime) {
        String result = null;
        try {
            if (null == format || "".equalsIgnoreCase(format)) {
                format = "yyyy-MM-dd HH:mm:ss";
            }
            result = new SimpleDateFormat(format, Locale.getDefault()).format(showTime);
        } catch (Exception e) {
            System.out.print(TAG+" Exception: " + e.getMessage());
        }
        return result;
    }

    public static String formatDate(String showTime) {
        String result = null;
        try {
            long time = -1;
            time = Long.valueOf(FileUtils.removeWhitespace(showTime));
            result = formatDate(time);
        } catch (Exception e) {
            System.out.print(TAG+" Exception: " + e.getMessage());
        }
        return result;
    }

    public static String formatDate(long showTime) {
        String result = null;
        try {
            result = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(showTime);
        } catch (Exception e) {
            System.out.print(TAG+" Exception: " + e.getMessage());
        }
        return result;
    }
}
