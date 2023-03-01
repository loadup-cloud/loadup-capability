package com.github.loadup.capability.common.util.date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期工具类。
 */
public class DateUtil {

    /**
     * Date Format定义
     */
    public final static  String shortFormat          = "yyyyMMdd";
    public final static  String longFormat           = "yyyyMMddHHmmss";
    public final static  String webFormat            = "yyyy-MM-dd";
    public final static  String timeFormat           = "HHmmss";
    public final static  String monthFormat          = "yyyyMM";
    public final static  String chineseDtFormat      = "yyyy年MM月dd日";
    public final static  String newFormat            = "yyyy-MM-dd HH:mm:ss";
    public final static  String noSecondFormat       = "yyyy-MM-dd HH:mm";
    /**
     * 常量：UTC时区
     */
    public final static  String TZ_UTC               = "UTC";
    /**
     * 常量：GMT8
     */
    public final static  String TZ_GMT8              = "GMT+08:00";
    /**
     * 缺省时间格式字符串<br>
     * <p>
     * 时间格式是:"年-月-日 时:分:秒.毫秒",比如: "2013-01-01 19:20:31.789"
     */
    public final static  String DEFAULT_DATE_FORMAT  = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 24小时制一天的秒数,毫秒数常量
     */
    public final static  long   ONE_DAY_SECONDS      = 86400;
    public final static  long   ONE_DAY_MILL_SECONDS = 86400000;
    /**
     * 日志
     */
    private final static Logger log                  = LoggerFactory.getLogger(DateUtil.class);
    /**
     * 时区间隔符
     */
    private final static String TIMEZONE_INTERVAL    = "|";

    /**
     * ISO datetime最小长度，必须带有时区，可能还有微秒
     */
    private final static int ISO_DATE_LENGTH = 25;

    /**
     * UTC 0时区的ISO datetime最小长度，时区信息是字符Z，不是+00:00格式
     */
    private final static int UTC_0_ISO_DATE_LENGTH = 20;

    /**
     * UTC 0时区的timezone标识
     */
    private final static String UTC_0_TIMEZONE = "Z";

    /**
     * @param pattern
     * @return
     */
    public static DateFormat getNewDateFormat(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        df.setLenient(false);
        return df;
    }

    /**
     * 指定时区的方式来获取DateFormat
     *
     * @param pattern 指定时间格式
     * @param tz      指定时区
     * @return 新的DateFormat对象
     */
    public static DateFormat getDateFormatWithTZ(String pattern, String tz) {
        DateFormat df = new SimpleDateFormat(pattern);
        df.setLenient(false);
        df.setTimeZone(TimeZone.getTimeZone(tz));
        return df;
    }

    /**
     * 指定UTC时区的方式来获取DateFormat
     *
     * @param pattern 指定时间格式
     * @return 新的UTC时区的DateFormat对象
     */
    public static DateFormat getUtcDateFormat(String pattern) {
        return getDateFormatWithTZ(pattern, TZ_UTC);
    }

    /**
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }

        return new SimpleDateFormat(format).format(date);
    }

    /**
     * @param sDate shortFormat
     * @throws ParseException
     */
    public static Date parseDateNoTime(String sDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(shortFormat);

        if ((sDate == null) || (sDate.length() < shortFormat.length())) {
            throw new ParseException("length too little", 0);
        }

        if (!StringUtils.isNumeric(sDate)) {
            throw new ParseException("not all digit", 0);
        }

        return dateFormat.parse(sDate);
    }

    /**
     * @param sDate
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parseDateNoTime(String sDate, String format) throws ParseException {
        if (StringUtils.isBlank(format)) {
            throw new ParseException("Null format. ", 0);
        }

        DateFormat dateFormat = new SimpleDateFormat(format);

        if ((sDate == null) || (sDate.length() < format.length())) {
            throw new ParseException("length too little", 0);
        }

        return dateFormat.parse(sDate);
    }

    /**
     * 将字符串转换为utc Date
     *
     * DateUtil.shortFormat = "yyyyMMdd";
     * DateUtil.longFormat = "yyyyMMddHHmmss";
     * DateUtil.webFormat = "yyyy-MM-dd";
     * DateUtil.timeFormat = "HHmmss";
     * DateUtil.monthFormat = "yyyyMM";
     * DateUtil.chineseDtFormat = "yyyy年MM月dd日";
     * DateUtil.newFormat = "yyyy-MM-dd HH:mm:ss";
     * DateUtil.noSecondFormat = "yyyy-MM-dd HH:mm";
     *
     * @param sDate
     * @param format
     * @return
     */
    public static Date parseUtcDate(String sDate, String format) {

        return parseDateWithTZ(sDate, format, TZ_UTC);
    }

    /**
     * 将字符串按照指定格式、时区转换为Date
     *
     * DateUtil.shortFormat = "yyyyMMdd";
     * DateUtil.longFormat = "yyyyMMddHHmmss";
     * DateUtil.webFormat = "yyyy-MM-dd";
     * DateUtil.timeFormat = "HHmmss";
     * DateUtil.monthFormat = "yyyyMM";
     * DateUtil.chineseDtFormat = "yyyy年MM月dd日";
     * DateUtil.newFormat = "yyyy-MM-dd HH:mm:ss";
     * DateUtil.noSecondFormat = "yyyy-MM-dd HH:mm";
     *
     * @param sDate
     * @param format
     * @param tz
     * @return sDate、format不一致，tz为空，或者转换异常则返回null
     */
    public static Date parseDateWithTZ(String sDate, String format, String tz) {

        try {
            Date date = null;
            DateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setLenient(false);
            dateFormat.setTimeZone(TimeZone.getTimeZone(tz));

            if ((sDate != null) && (sDate.length() == format.length())) {
                date = dateFormat.parse(sDate);
            }
            return date;
        } catch (Exception e) {
            log.error("DateUtil.parseDateWithTZ Exception,dateStr=" + sDate, "format=" + format, "tz=" + tz);
            return null;
        }
    }

    /**
     * 将字符串按照指定格式、时区转换为Date
     *
     * DateUtil.shortFormat = "yyyyMMdd";
     * DateUtil.longFormat = "yyyyMMddHHmmss";
     * DateUtil.webFormat = "yyyy-MM-dd";
     * DateUtil.timeFormat = "HHmmss";
     * DateUtil.monthFormat = "yyyyMM";
     * DateUtil.chineseDtFormat = "yyyy年MM月dd日";
     * DateUtil.newFormat = "yyyy-MM-dd HH:mm:ss";
     * DateUtil.noSecondFormat = "yyyy-MM-dd HH:mm";
     *
     * @param sDate
     * @param format
     * @param tz
     * @param isCheckFormat 是否按format格式对sDate做预校验
     * @return sDate、format不一致，tz为空，或者转换异常则返回null
     */
    public static Date parseDateWithTZ(String sDate, String format, String tz, boolean isCheckFormat) {

        // 是否按format格式对sDate做校验
        if (isCheckFormat) {
            return parseDateWithTZ(sDate, format, tz);
        }

        if (StringUtils.isBlank(sDate)) {
            return null;
        }

        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setLenient(false);
            dateFormat.setTimeZone(TimeZone.getTimeZone(tz));

            return dateFormat.parse(sDate);
        } catch (Exception e) {
            log.error("DateUtil.parseDateWithTZ Exception,dateStr=" + sDate, "format=" + format, "tz=" + tz);
            return null;
        }
    }

    /**
     * @param sDate
     * @return
     */
    public static Date parseDateLongFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat(longFormat);
        Date d = null;

        if ((sDate != null) && (sDate.length() == longFormat.length())) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException ex) {
                return null;
            }
        }

        return d;
    }

    /**
     * @param sDate
     * @return
     */

    public static Date parseDateNewFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat(newFormat);
        Date d = null;
        dateFormat.setLenient(false);
        if ((sDate != null) && (sDate.length() == newFormat.length())) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException ex) {
                return null;
            }
        }
        return d;
    }

    /**
     * 判断输入的字符串是否为合法的小时
     *
     * @param hourStr
     * @return true/false
     */
    public static boolean isValidHour(String hourStr) {
        if (!StringUtils.isEmpty(hourStr) && StringUtils.isNumeric(hourStr)) {
            int hour = new Integer(hourStr).intValue();

            if ((hour >= 0) && (hour <= 23)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断输入的字符串是否为合法的分或秒
     *
     * @param str
     * @return true/false
     */
    public static boolean isValidMinuteOrSecond(String str) {
        if (!StringUtils.isEmpty(str) && StringUtils.isNumeric(str)) {
            int hour = new Integer(str).intValue();

            if ((hour >= 0) && (hour <= 59)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param date
     * @return
     */

    public static String getLongDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(longFormat);

        return getDateString(date, dateFormat);
    }

    /**
     * @param date
     * @return
     */

    public static String getNewFormatDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(newFormat);
        return getDateString(date, dateFormat);
    }

    /**
     * @param date
     * @param dateFormat
     * @return
     */

    public static String getDateString(Date date, DateFormat dateFormat) {
        if (date == null || dateFormat == null) {
            return null;
        }

        return dateFormat.format(date);
    }

    /**
     * 将date转换为utc指定格式的字符串,pattern为:
     *
     * DateUtil.shortFormat = "yyyyMMdd";
     * DateUtil.longFormat = "yyyyMMddHHmmss";
     * DateUtil.webFormat = "yyyy-MM-dd";
     * DateUtil.timeFormat = "HHmmss";
     * DateUtil.monthFormat = "yyyyMM";
     * DateUtil.chineseDtFormat = "yyyy年MM月dd日";
     * DateUtil.newFormat = "yyyy-MM-dd HH:mm:ss";
     * DateUtil.noSecondFormat = "yyyy-MM-dd HH:mm";
     *
     * @param date
     * @param format
     * @return
     */
    public static String getUtcDateString(Date date, String format) {

        return getDateStringWithTZ(date, format, TZ_UTC);
    }

    /**
     * 将date转换为指定时区、指定格式的字符串，date为null则返回null，pattern为:
     *
     * DateUtil.shortFormat = "yyyyMMdd";
     * DateUtil.longFormat = "yyyyMMddHHmmss";
     * DateUtil.webFormat = "yyyy-MM-dd";
     * DateUtil.timeFormat = "HHmmss";
     * DateUtil.monthFormat = "yyyyMM";
     * DateUtil.chineseDtFormat = "yyyy年MM月dd日";
     * DateUtil.newFormat = "yyyy-MM-dd HH:mm:ss";
     * DateUtil.noSecondFormat = "yyyy-MM-dd HH:mm";
     *
     * @param date
     * @param format
     * @param tz
     * @return
     */
    public static String getDateStringWithTZ(Date date, String format, String tz) {
        if (null == date) {
            return null;
        }

        DateFormat dateFormat = getDateFormatWithTZ(format, tz);
        return dateFormat.format(date);
    }

    /**
     * 将date转换为指定时区、指定locale、指定格式的字符串,pattern为:
     *
     * DateUtil.shortFormat = "yyyyMMdd";
     * DateUtil.longFormat = "yyyyMMddHHmmss";
     * DateUtil.webFormat = "yyyy-MM-dd";
     * DateUtil.timeFormat = "HHmmss";
     * DateUtil.monthFormat = "yyyyMM";
     * DateUtil.chineseDtFormat = "yyyy年MM月dd日";
     * DateUtil.newFormat = "yyyy-MM-dd HH:mm:ss";
     * DateUtil.noSecondFormat = "yyyy-MM-dd HH:mm";
     *
     * 注：date为null则返回null。
     *
     * @param date
     * @param format
     * @param tz
     * @param locale
     * @return
     */
    public static String getDateStringWithTzAndLocale(Date date, String format, String tz, Locale locale) {
        if (null == date) {
            return null;
        }

        DateFormat dateFormat = new SimpleDateFormat(format, locale);
        dateFormat.setLenient(false);
        dateFormat.setTimeZone(TimeZone.getTimeZone(tz));

        return dateFormat.format(date);
    }

    /**
     * @param date
     * @return 当天的时间格式化为"yyyyMMdd"
     */

    public static String getDateString(Date date) {
        DateFormat df = getNewDateFormat(shortFormat);

        return df.format(date);
    }

    /**
     * @param date
     * @return
     */

    public static String getWebDateString(Date date) {
        DateFormat dateFormat = getNewDateFormat(webFormat);

        return getDateString(date, dateFormat);
    }

    /**
     * 取得“X年X月X日”的日期格式
     * <br/>
     *
     * @param date
     * @return
     */

    public static String getChineseDateString(Date date) {
        DateFormat dateFormat = getNewDateFormat(chineseDtFormat);

        return getDateString(date, dateFormat);
    }

    /**
     * @return
     */

    public static String getTodayString() {
        DateFormat dateFormat = getNewDateFormat(shortFormat);

        return getDateString(new Date(), dateFormat);
    }

    /**
     * @param date
     * @return
     */

    public static String getTimeString(Date date) {
        DateFormat dateFormat = getNewDateFormat(timeFormat);

        return getDateString(date, dateFormat);
    }

    /**
     * 获取UTC时区当天日期yyyyMMdd字符串
     *
     * @return yyyyMMdd字符串
     */
    public static String getUtcTodayString() {

        return getUtcDateString(new Date(), shortFormat);
    }

    /**
     * 取得两个日期间隔秒数（日期1-日期2）
     *
     * @param one 日期1
     * @param two 日期2
     * @return 间隔秒数
     */
    public static long getDiffSeconds(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();

        sysDate.setTime(one);

        Calendar failDate = new GregorianCalendar();

        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000;
    }

    public static long getDiffMinutes(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();

        sysDate.setTime(one);

        Calendar failDate = new GregorianCalendar();

        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (60 * 1000);
    }

    public static boolean isValidShortDateFormat(String strDate) {
        if (strDate.length() != shortFormat.length()) {
            return false;
        }

        try {
            //---- 避免日期中输入非数字 ----
            Integer.parseInt(strDate);
        } catch (Exception NumberFormatException) {
            return false;
        }

        DateFormat df = getNewDateFormat(shortFormat);

        try {
            df.parse(strDate);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public static boolean isValidShortDateFormat(String strDate, String delimiter) {
        String temp = strDate.replaceAll(delimiter, "");

        return isValidShortDateFormat(temp);
    }

    /**
     * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
     *
     * @param strDate
     * @return
     */
    public static boolean isValidLongDateFormat(String strDate) {
        if (strDate.length() != longFormat.length()) {
            return false;
        }

        try {
            Long.parseLong(strDate); //---- 避免日期中输入非数字 ----
        } catch (Exception NumberFormatException) {
            return false;
        }

        DateFormat df = getNewDateFormat(longFormat);

        try {
            df.parse(strDate);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    /**
     * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
     *
     * @param strDate
     * @param delimiter
     * @return
     */
    public static boolean isValidLongDateFormat(String strDate, String delimiter) {
        String temp = strDate.replaceAll(delimiter, "");

        return isValidLongDateFormat(temp);
    }

    public static String getShortDateString(String strDate) {
        return getShortDateString(strDate, "-|/");
    }

    public static String getShortDateString(String strDate, String delimiter) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        String temp = strDate.replaceAll(delimiter, "");

        if (isValidShortDateFormat(temp)) {
            return temp;
        }

        return null;
    }

    /**
     * @return
     */

    public static String getShortFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        Date dt = new Date();

        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        DateFormat df = getNewDateFormat(shortFormat);

        return df.format(cal.getTime());
    }

    /**
     * @return
     */

    public static String getWebTodayString() {
        DateFormat df = getNewDateFormat(webFormat);

        return df.format(new Date());
    }

    /**
     * @return
     */

    public static String getWebFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        Date dt = new Date();

        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        DateFormat df = getNewDateFormat(webFormat);

        return df.format(cal.getTime());
    }

    /**
     * 获取当前月第一天的日期（指定日期格式、指定时区）
     *
     * @param format 日期格式
     * @param tz     指定时区
     * @return 当前月第一天的日期
     */
    public static String getFirstDayOfMonthWithTZ(String format, String tz) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(tz));
        Date dt = new Date();
        cal.setTime(dt);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        DateFormat df = getDateFormatWithTZ(format, tz);
        return df.format(cal.getTime());
    }

    public static String convert(String dateString, DateFormat formatIn, DateFormat formatOut) {
        try {
            Date date = formatIn.parse(dateString);

            return formatOut.format(date);
        } catch (ParseException e) {
            log.warn("convert() --- orign date error: " + dateString);
            return "";
        }
    }

    public static String convert2WebFormat(String dateString) {
        DateFormat df1 = getNewDateFormat(shortFormat);
        DateFormat df2 = getNewDateFormat(webFormat);

        return convert(dateString, df1, df2);
    }

    public static String convert2ChineseDtFormat(String dateString) {
        DateFormat df1 = getNewDateFormat(shortFormat);
        DateFormat df2 = getNewDateFormat(chineseDtFormat);

        return convert(dateString, df1, df2);
    }

    public static String convertFromWebFormat(String dateString) {
        DateFormat df1 = getNewDateFormat(shortFormat);
        DateFormat df2 = getNewDateFormat(webFormat);

        return convert(dateString, df2, df1);
    }

    public static boolean webDateNotLessThan(String date1, String date2) {
        DateFormat df = getNewDateFormat(webFormat);

        return dateNotLessThan(date1, date2, df);
    }

    /**
     * @param date1
     * @param date2
     * @param format
     * @return
     */
    public static boolean dateNotLessThan(String date1, String date2, DateFormat format) {
        try {
            Date d1 = format.parse(date1);
            Date d2 = format.parse(date2);

            if (d1.before(d2)) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * @param today
     * @return
     */

    public static String getEmailDate(Date today) {
        String todayStr;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");

        todayStr = sdf.format(today);
        return todayStr;
    }

    /**
     * @param today
     * @return
     */

    public static String getSmsDate(Date today) {
        String todayStr;
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH:mm");

        todayStr = sdf.format(today);
        return todayStr;
    }

    public static String formatTimeRange(Date startDate, Date endDate, String format) {
        if ((endDate == null) || (startDate == null)) {
            return null;
        }

        String rt = null;
        long range = endDate.getTime() - startDate.getTime();
        long day = range / DateUtils.MILLIS_PER_DAY;
        long hour = (range % DateUtils.MILLIS_PER_DAY) / DateUtils.MILLIS_PER_HOUR;
        long minute = (range % DateUtils.MILLIS_PER_HOUR) / DateUtils.MILLIS_PER_MINUTE;

        if (range < 0) {
            day = 0;
            hour = 0;
            minute = 0;
        }

        rt = format.replaceAll("dd", String.valueOf(day));
        rt = rt.replaceAll("hh", String.valueOf(hour));
        rt = rt.replaceAll("mm", String.valueOf(minute));

        return rt;
    }

    /**
     * @param date
     * @return
     */

    public static String formatMonth(Date date) {
        if (date == null) {
            return null;
        }

        return new SimpleDateFormat(monthFormat).format(date);
    }

    /**
     * 获取系统日期的前一天日期，返回Date
     *
     * @return
     */
    public static Date getBeforeDate() {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1); //得到前1天

        return calendar.getTime();
    }

    /**
     * 获得指定时间当天起点时间
     * <br/>
     * 请使用getDayBeginWithTZ方法代替。
     *
     * @param date
     * @return
     */

    public static Date getDayBegin(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setLenient(false);

        String dateString = df.format(date);

        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            return date;
        }
    }

    /**
     * 获取指定时区的当天0点时间（即基于入参time经过指定时区转换后的所在日期的0点时间）
     *
     * @param time 指定时刻
     * @param tz   指定时区常量
     * @return 返回指定时刻time的指定时区的0点时间
     */
    public static Date getDayBeginWithTZ(Date time, String tz) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone(tz));
        c.setTime(time);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定时区的当天最后时间（即基于入参time经过指定时区转换后的所在日期的0点最后时间）
     *
     * @param time 指定时刻
     * @param tz   指定时区常量
     * @return 返回指定时刻time的指定时区的当天最后时间
     */
    public static Date getDayEndWithTZ(Date time, String tz) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone(tz));
        c.setTime(time);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 获取UTC时区下的当天0点时间
     *
     * @param time
     * @return
     */
    public static Date getUtcDayBegin(Date time) {
        return getDayBeginWithTZ(time, TZ_UTC);
    }

    /**
     * 获取UTC时区下的当天最后时间
     *
     * @param time
     * @return
     */
    public static Date getUtcDayEnd(Date time) {
        return getDayEndWithTZ(time, TZ_UTC);
    }

    /**
     * 判断参date上min分钟后，是否小于当前时间
     *
     * @param date
     * @param min
     * @return
     */
    public static boolean dateLessThanNowAddMin(Date date, long min) {
        return addMinutes(date, min).before(new Date());

    }

    public static boolean isBeforeNow(Date date) {
        if (date == null) {
            return false;
        }
        return date.compareTo(new Date()) < 0;
    }

    /**
     * @param sDate
     * @return
     * @throws ParseException
     */

    public static Date parseNoSecondFormat(String sDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(noSecondFormat);

        if ((sDate == null) || (sDate.length() < noSecondFormat.length())) {
            throw new ParseException("length too little", 0);
        }

        return dateFormat.parse(sDate);
    }

    /**
     * 获取当前日的前面某一天。
     * <br/>
     *
     * @param span 跨度
     * @return 当前日的前面某一天
     */

    public static String getBeforeDayString(int span) {
        return getBeforeDayString(getTodayString(), span);
    }

    /**
     * 获取UTC时区当前日的前面某一天。
     *
     * @param span 跨度
     * @return 当前日的前面某一天
     */
    public static String getUtcBeforeDayString(int span) {
        return getBeforeDayString(getUtcTodayString(), span);
    }

    /**
     * 获取某日期的前面某一天。由于美国时区有冬令时和夏令时问题，<br>
     * com.iwallet.biz.common.util.DateUtil处理该问题有缺陷。所以日期计算必须用该方法。
     *
     * @param dateStr 日期字符串
     * @param span    间隔天数
     * @return 结果日期
     */
    public static String getBeforeDayString(String dateStr, int span) {

        Date date;
        DateFormat df = getNewDateFormat(shortFormat);

        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            log.error(
                    "DateUtil.getBeforeDayString ParseException,dateStr=", dateStr);
            throw new RuntimeException("DateUtil.getBeforeDayString ParseException", e);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -span);

        return df.format(calendar.getTime());

    }

    /**
     * 指定字符串格式和时区，获取某日期的前面某一天。
     *
     * @param dateStr 日期字符串
     * @param format  字符串格式
     *                DateUtil.shortFormat = "yyyyMMdd";
     *                DateUtil.longFormat = "yyyyMMddHHmmss";
     *                DateUtil.webFormat = "yyyy-MM-dd";
     *                DateUtil.timeFormat = "HHmmss";
     *                DateUtil.monthFormat = "yyyyMM";
     *                DateUtil.chineseDtFormat = "yyyy年MM月dd日";
     *                DateUtil.newFormat = "yyyy-MM-dd HH:mm:ss";
     *                DateUtil.noSecondFormat = "yyyy-MM-dd HH:mm";
     * @param tz      时区
     *                DateUtil.TZ_America_Los_Angeles = "America/Los_Angeles";
     *                DateUtil.TZ_UTC = "UTC";
     * @param span    间隔天数
     * @return 结果日期
     */
    public static String getBeforeDayStringWithTZ(String dateStr, String format, String tz, int span) {

        try {
            DateFormat df = getDateFormatWithTZ(format, tz);
            Date date = df.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone(tz));
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -span);

            return df.format(calendar.getTime());
        } catch (Exception e) {
            log.error(
                    "DateUtil.getBeforeDayString Exception,dateStr=", dateStr, "format=" + format, "tz=" + tz);
            throw new RuntimeException("DateUtil.getBeforeDayString Exception", e);
        }

    }

    /**
     * 获取某日期的前面某一月。
     *
     * @param dateStr 日期字符串
     * @param span    间隔月数
     * @return 结果日期
     */
    public static String getBeforeMonthString(String dateStr, int span) {

        Date date;
        DateFormat df = getNewDateFormat(shortFormat);

        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            log.error(
                    "DateUtil.getBeforeMonthString ParseException,dateStr=", dateStr);
            throw new RuntimeException("DateUtil.getBeforeMonthString ParseException", e);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -span);

        return df.format(calendar.getTime());
    }

    /**
     * 指定字符串格式和时区，获取某日期的前面某月日期。由于美国时区有冬令时和夏令时问题
     *
     * @param dateStr 日期字符串
     * @param format  字符串格式
     *                DateUtil.shortFormat = "yyyyMMdd";
     *                DateUtil.longFormat = "yyyyMMddHHmmss";
     *                DateUtil.webFormat = "yyyy-MM-dd";
     *                DateUtil.timeFormat = "HHmmss";
     *                DateUtil.monthFormat = "yyyyMM";
     *                DateUtil.chineseDtFormat = "yyyy年MM月dd日";
     *                DateUtil.newFormat = "yyyy-MM-dd HH:mm:ss";
     *                DateUtil.noSecondFormat = "yyyy-MM-dd HH:mm";
     * @param tz      时区
     *                DateUtil.TZ_America_Los_Angeles = "America/Los_Angeles";
     *                DateUtil.TZ_UTC = "UTC";
     * @param span    间隔天数
     * @return 结果日期，格式异常、时区为空等返回null
     */
    public static String getBeforeMonthStringWithTZ(String dateStr, String format, String tz, int span) {

        Date date;
        try {
            DateFormat df = getNewDateFormat(format);
            TimeZone timeZone = TimeZone.getTimeZone(tz);
            df.setTimeZone(timeZone);
            date = df.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(timeZone);
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -span);

            return df.format(calendar.getTime());
        } catch (Exception e) {
            log.error(
                    "DateUtil.getBeforeMonthStringWithTZ Exception,dateStr=", dateStr, "format=" + format, "tz=" + tz);
            throw new RuntimeException("DateUtil.getBeforeMonthStringWithTZ Exception", e);
        }

    }

    /**
     * 给指定时间加上<code>span<code>年数（以当前运行环境的时区来计算）
     *
     * 注：已废弃！请使用addYears(Date date, int span, String tz)方法代替，以避免时区计算风险。
     *
     * @param date 日期
     * @param span 时间跨度
     * @return 计算后的时间
     */

    public static Date addYears(Date date, int span) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, span);

        return calendar.getTime();
    }

    /**
     * 给指定时间加上<code>span<code>年数（以指定时区来计算）
     *
     * 注：对于指定America/Los_Angeles时区的计算，会考虑冬夏令时时区跳变问题，以保证计算后洛杉矶时区下的时分秒信息保持不变。
     *
     * @param date 日期
     * @param span 时间跨度
     * @param tz   指定时区
     * @return 计算后的时间
     */
    public static Date addYears(Date date, int span, String tz) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(tz));
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, span);
        return calendar.getTime();
    }

    /**
     * 给指定时间加上span年数（按非夏令时时区来计算，即按每天都是24个小时来计算，以此来保证计算后的结果在非夏令时时区下的时分秒保持不变）
     *
     * @param date 日期
     * @param span 时间跨度
     * @return 计算后的时间
     */
    public static Date addYearsWithNonDST(Date date, int span) {
        return addYears(date, span, TZ_UTC);
    }

    /**
     * 给指定时间加上<code>span<code>月数（以当前运行环境的时区来计算）
     *
     * 注：已废弃！请使用addMonths(Date date, int span, String tz)方法代替，以避免时区计算风险。
     *
     * @param date 日期
     * @param span 时间跨度
     * @return 计算后的时间
     */

    public static Date addMonths(Date date, int span) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, span);

        return calendar.getTime();
    }

    /**
     * 给指定时间加上<code>span<code>月数（以指定时区来计算）
     *
     * 注：对于指定America/Los_Angeles时区的计算，会考虑冬夏令时时区跳变问题，以保证计算后洛杉矶时区下的时分秒信息保持不变。
     *
     * @param date 日期
     * @param span 时间跨度
     * @param tz   指定时区
     * @return 计算后的时间
     */
    public static Date addMonths(Date date, int span, String tz) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(tz));
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, span);
        return calendar.getTime();
    }

    /**
     * 给指定时间加上span月数（按非夏令时时区来计算，即按每天都是24个小时来计算，以此来保证计算后的结果在非夏令时时区下的时分秒保持不变）
     *
     * @param date 日期
     * @param span 时间跨度
     * @return 计算后的时间
     */
    public static Date addMonthsWithNonDST(Date date, int span) {
        return addMonths(date, span, TZ_UTC);
    }

    /**
     * 取得新的日期
     *
     * 注：已废弃！请使用addDays(Date date, int span, String tz)方法代替，以避免时区计算风险。
     *
     * @param date1 日期
     * @param days  天数
     * @return 新的日期
     */

    public static Date addDays(Date date1, long days) {
        return addDays(date1, (int) days);
    }

    /**
     * 给指定时间加上<code>days<code>天数（以当前运行环境的时区来计算）。
     *
     * @param date 时间
     * @param span 跨度
     * @return 计算后的时间
     */

    public static Date addDays(Date date, int span) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, span);
        return calendar.getTime();
    }

    /**
     * 给指定时间加上<code>span<code>天数。（以指定时区来计算）
     *
     * @param date 时间
     * @param span 跨度
     * @param tz   指定时区
     * @return 计算后的时间
     */
    public static Date addDays(Date date, int span, String tz) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(tz));
        calendar.setTime(date);
        calendar.add(Calendar.DATE, span);
        return calendar.getTime();
    }

    /**
     * 计算当前时间几小时之后的时间
     *
     * @param date
     * @param hours
     * @return
     */
    public static Date addHours(Date date, long hours) {
        return addHours(date, (int) hours);
    }

    /**
     * 给指定时间增加小时，
     *
     * @param date  时间
     * @param hours 小时数
     * @return 增加后的时间
     */
    public static Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);

        return calendar.getTime();
    }

    /**
     * 计算当前时间几分钟之后的时间
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date addMinutes(Date date, long minutes) {
        return addMinutes(date, (int) minutes);
    }

    /**
     * 给指定时间增加分钟，
     *
     * @param date    时间
     * @param minutes 分钟数
     * @return 增加后的时间
     */
    public static Date addMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);

        return calendar.getTime();
    }

    /**
     * @param date1
     * @param secs
     * @return
     */

    public static Date addSeconds(Date date1, long secs) {
        return addSeconds(date1, (int) secs);
    }

    /**
     * 给指定时间增加秒，
     *
     * @param date    时间
     * @param seconds 秒数
     * @return 增加后的时间
     */
    public static Date addSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);

        return calendar.getTime();
    }

    /**
     * 获取明天的日期
     *
     * @param dateStr 日期（yyyyMMdd格式）
     * @return 明天的日期
     */
    public static String getTomorrowDateString(String dateStr) {

        return getBeforeDayString(dateStr, -1);
    }

    /**
     * 获取昨天的日期
     *
     * @param dateStr 日期（yyyyMMdd格式）
     * @return 昨天的日期
     */
    public static String getYesterDayDateString(String dateStr) {

        return getBeforeDayString(dateStr, 1);
    }

    /**
     * 得到两个时间点之间相差的天数。
     *
     * @param startDt 开始时间
     * @param endDt   结束时间
     * @return 相差天数
     */

    public static long getDiffDays(Date startDt, Date endDt) {

        Calendar startCl = Calendar.getInstance();
        startCl.setTime(startDt);
        Calendar endCl = Calendar.getInstance();
        endCl.setTime(endDt);
        boolean positive = false;
        if (startCl.after(endCl)) {
            Calendar swap = startCl;
            startCl = endCl;
            endCl = swap;
            positive = true;
        }
        //计算2个时间点的差
        int days = endCl.get(Calendar.DAY_OF_YEAR) - startCl.get(Calendar.DAY_OF_YEAR);
        int year = endCl.get(Calendar.YEAR);
        if (startCl.get(Calendar.YEAR) != year) {
            startCl = (Calendar) startCl.clone();
            do {
                //如果不是同一年则累计该年的天数。
                days = days + startCl.getActualMaximum(Calendar.DAY_OF_YEAR);
                startCl.add(Calendar.YEAR, 1);
            } while (startCl.get(Calendar.YEAR) != year);
        }
        if (!positive) {
            days = days * -1;
        }
        return days;
    }

    /**
     * 得到两个时间点之间相差的天数。
     *
     * @param startDt 开始时间
     * @param endDt   结束时间
     * @param tz      指定时区
     * @return 相差天数
     */
    public static long getDiffDaysWithTZ(Date startDt, Date endDt, String tz) {

        Calendar startCl = Calendar.getInstance(TimeZone.getTimeZone(tz));
        startCl.setTime(startDt);
        Calendar endCl = Calendar.getInstance(TimeZone.getTimeZone(tz));
        endCl.setTime(endDt);

        int diffDays = DateUtil.getDiffDays(startCl, endCl, false);
        if (!startCl.after(endCl)) {
            diffDays = diffDays * -1;
        }

        return diffDays;
    }

    /**
     * 获取时间的2位月份index，不足2位在前面用0补齐。
     * 这方法返回的是"月序列", 从0开始, 比如12月份日期返回的将是"11"
     *
     * @param date 时间
     * @return 2位月份信息
     */

    public static String getMonthIndex(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int monthIndex = cal.get(Calendar.MONTH);
        String monthIndexStr = "";
        if (monthIndex < 10) {
            monthIndexStr = "0" + monthIndex;
        } else {
            monthIndexStr = String.valueOf(monthIndex);
        }

        return monthIndexStr;
    }

    /**
     * 获取时间的2位月份index，不足2位在前面用0补齐。
     * 这方法返回的是"月序列", 从0开始, 比如12月份日期返回的将是"11"
     *
     * @param dateTime 时间的绝对毫秒数
     * @return 2位月份信息
     */

    public static String getMonthIndex(long dateTime) {
        return getMonthIndex(new Date(dateTime));
    }

    /**
     * 获取时间的2位月份index，不足2位在前面用0补齐。
     * 这方法返回的是"月序列", 从0开始, 比如12月份日期返回的将是"11"
     *
     * @param date 时间
     * @param tz   指定时区
     * @return 2位月份信息
     */
    public static String getMonthIndexWithTZ(Date date, String tz) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(tz));
        cal.setTime(date);
        int monthIndex = cal.get(Calendar.MONTH);
        String monthIndexStr = "";
        if (monthIndex < 10) {
            monthIndexStr = "0" + monthIndex;
        } else {
            monthIndexStr = String.valueOf(monthIndex);
        }

        return monthIndexStr;
    }

    /**
     * 以指定时区来获取年份值
     *
     * @param date 时间
     * @param tz   指定时区
     * @return
     */
    public static int getYearNo(Date date, String tz) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(tz));
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 以指定时区来获取对应的月份（值为1到12）
     *
     * @param date 时间
     * @param tz   指定时区
     * @return 值为1-12
     */
    public static int getMonthNo(Date date, String tz) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(tz));
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 以指定时区来获取日期值（值为1到31）
     *
     * @param date 时间
     * @param tz   指定时区
     * @return 值为1-31
     */
    public static int getDayNo(Date date, String tz) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(tz));
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 以指定时区来获取一年中的日期值（值为1到366）
     *
     * @param date 时间
     * @param tz   指定时区
     * @return 值为1-366
     */
    public static int getDayNoOfYear(Date date, String tz) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(tz));
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 将给定的Date对象格式化为带时区的日期/时间字符串.
     * <p>
     * 时间字符串格式是:"年-月-日 时:分:秒.毫秒|时区",比如: "2013-01-01 19:20:31.789|America/Los_Angeles".
     * <p>
     * <ul>
     * <li>返回字符串符合{@value #DEFAULT_DATE_FORMAT}格式.
     * <li>返回值日期/时间字符串中的时区格式符合Java TimeZone ids标准.
     * </ul>
     *
     * @param date     要格式化为时间字符串的时间值
     * @param timeZone 时区
     * @return 格式化结果
     */
    public static String dateFormat(Date date, TimeZone timeZone) {
        DateFormat df = getDefaultDateFormat();
        df.setTimeZone(timeZone);
        String result = df.format(date) + TIMEZONE_INTERVAL + timeZone.getID();
        return result;
    }

    /**
     * 从本类dateFormat()方法格式后的日期/时间字符串中解析出Date对象,
     * <p>
     * 时间字符串格式是:"年-月-日 时:分:秒.毫秒|时区",比如: "2013-01-01 19:20:31.789|America/Los_Angeles".
     *
     * @param source 一个 String，从其开始处进行解析。
     * @return 解析字符串得到的 Date。
     * @throws ParseException 如果无法解析指定字符串的开始处
     */
    public static Date parseDate(String source) throws ParseException {
        String[] sa = retriveDateStr(source);

        TimeZone tz = TimeZone.getTimeZone(sa[1]);

        DateFormat sdf = getDefaultDateFormat();
        sdf.setTimeZone(tz);
        return sdf.parse(sa[0]);
    }

    /**
     * 从本类dateFormat()方法格式后的日期/时间字符串中解析出TimeZone对象,
     * <p>
     * 时间字符串格式是:"年-月-日 时:分:秒.毫秒|时区",比如: "2013-01-01 19:20:31.789|America/Los_Angeles"
     *
     * @param source 时间字符串
     * @return 解析结果
     */
    public static TimeZone parseTimeZone(String source) {
        String[] sa = retriveDateStr(source);
        TimeZone tz = TimeZone.getTimeZone(sa[1]);
        return tz;
    }

    /**
     * 从字符串中提取出日期/时间字符串和时区信息
     *
     * @param source 时间字符串
     * @return 解析结果
     */
    private static String[] retriveDateStr(String source) {
        if (source.length() == 0 || source.trim().length() == 0) {
            throw new RuntimeException("date/time string is null or blank.");
        }
        if (source.indexOf(TIMEZONE_INTERVAL) == -1) {
            throw new RuntimeException("date/time string format is illegal.");
        }

        String[] sa = new String[2];
        sa[0] = source.substring(0, source.indexOf(TIMEZONE_INTERVAL));
        sa[1] = source.substring(source.indexOf(TIMEZONE_INTERVAL) + 1, source.length());
        if (sa.length != 2) {
            throw new RuntimeException("date/time string format is illegal.");
        }
        return sa;
    }

    /**
     * 从缺省pattern取得DateFormat
     *
     * @return 日期格式
     */
    private static DateFormat getDefaultDateFormat() {
        DateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        df.setLenient(false);
        return df;
    }

    /**
     * 将ISO标准的时间字符串转换为带时区的字符串
     *
     * @param isoDateStr
     * @return 转换成带时区的字符串
     */
    public static String dateFormatISODateStr(String isoDateStr) {

        if (StringUtils.isBlank(isoDateStr)) {
            return "";
        }

        try {
            Date date = parseISODateTimeStr(isoDateStr);
            return dateFormat(date, TimeZone.getDefault());
        } catch (Exception e) {
            log.error("[" + isoDateStr
                    + "] illegal argument, error=[date format error]", e);
            return "";
        }
    }

    /**
     * 获取ISO的时间字符串
     * <br/>
     *
     * @param date
     * @return ISO的时间字符串
     */

    public static String getISODateTimeStr(Date date) {
        return getISODateTimeStr(date, null);
    }

    /**
     * 获取utc的ISO的时间字符串
     *
     * @param date 日期
     * @return ISO的时间字符串
     */
    public static String getUtcISODateTimeStr(Date date) {
        return getISODateTimeStr(date, TimeZone.getTimeZone(TZ_UTC));
    }

    /**
     * 获取北京时区的ISO的时间字符串
     *
     * @param date 日期
     * @return ISO的时间字符串
     */
    public static String getGMT8ISODateTimeStr(Date date) {
        return getISODateTimeStr(date, TimeZone.getTimeZone(TZ_GMT8));
    }

    /**
     * 获取指定时区的ISO的时间字符串
     *
     * @param date 日期
     * @param tz
     * @return ISO的时间字符串
     */
    public static String getISODateTimeStrWithTZ(Date date, String tz) {
        return getISODateTimeStr(date, TimeZone.getTimeZone(tz));
    }

    /**
     * 获取指定时区的ISO的时间字符串
     *
     * @param date     日期
     * @param timeZone 时区
     * @return ISO的时间字符串
     */
    public static String getISODateTimeStr(Date date, TimeZone timeZone) {
        if (date == null) {
            return null;
        }

        // 将毫秒数设置为000
        // 注意不能使用cal.set(Calendar.MILLISECOND, 0)方式来设置, 会导致时区跳变时的前一个小时时间都不正确.
        date.setTime(date.getTime() / 1000L * 1000L);

        Calendar cal = new GregorianCalendar();
        if (timeZone != null) {
            cal.setTimeZone(timeZone);
        }
        cal.setTime(date);
        return DatatypeConverter.printDateTime(cal);
    }

    /**
     * 解析ISO的时间字符串
     *
     * @param isoDateStr
     * @return ISO的时间字符串
     */
    public static Date parseISODateTimeStr(String isoDateStr) {
        if (StringUtils.isBlank(isoDateStr)) {
            return null;
        }

        int minDateStrLength;
        // UTC 0时区字符串，时区标识是字符Z，不是+00:00
        if (isoDateStr.endsWith(UTC_0_TIMEZONE)) {
            minDateStrLength = UTC_0_ISO_DATE_LENGTH;

        } else {
            minDateStrLength = ISO_DATE_LENGTH;
        }

        if (isoDateStr.length() < minDateStrLength) {
            return null;
        }

        try {
            Calendar cal = DatatypeConverter.parseDate(isoDateStr);
            return cal.getTime();
        } catch (IllegalArgumentException e) {

            log.error("DateUtil.parseXSDate exception,isoDatetime=" + isoDateStr);

            return null;
        }
    }

    /**
     * ISO datetime 日期格式转换
     * <br/>
     *
     * @param longDateString 长整型日期格式，yyyyMMddHHmmss
     * @return ISO datetime 日期格式
     */

    public static String convertISODatetimeString(String longDateString) {
        try {
            Date date = parseDateNoTime(longDateString, longFormat);
            return getISODateTimeStr(date);
        } catch (ParseException e) {

            log.error(
                    "DateUtil.convertISODatetimeString exception,longDateString=", longDateString);

            return null;
        }
    }

    /**
     * 得到两个时间点之间相差的天数(绝对值)
     *
     * @param one      时间1
     * @param other    时间2
     * @param contains 是否包含当天
     * @return 相差天数
     */
    public static int getDiffDays(Calendar one, Calendar other, boolean contains) {
        if (one.after(other)) {
            Calendar swap = one;
            one = other;
            other = swap;
        }
        //计算2个时间点的差
        int days = other.get(Calendar.DAY_OF_YEAR) - one.get(Calendar.DAY_OF_YEAR);
        int year = other.get(Calendar.YEAR);
        if (one.get(Calendar.YEAR) != year) {
            one = (Calendar) one.clone();
            do {
                //如果不是同一年则累计该年的天数。
                days = days + one.getActualMaximum(Calendar.DAY_OF_YEAR);
                one.add(Calendar.YEAR, 1);
            } while (one.get(Calendar.YEAR) != year);
        }
        return contains ? (days + 1) : days;
    }

    /**
     * 得到两个时间点之间相差的周数(绝对值)
     *
     * @param one      时间1
     * @param other    时间2
     * @param contains 是否包括当周
     * @return 相差的周数
     */
    public static int getDiffWeeks(Calendar one, Calendar other, boolean contains) {
        if (one.after(other)) {
            Calendar swap = one;
            one = other;
            other = swap;
        }
        //计算2个时间点的差
        int weeks = other.get(Calendar.WEEK_OF_YEAR) - one.get(Calendar.WEEK_OF_YEAR);
        int year = other.get(Calendar.YEAR);
        if (one.get(Calendar.YEAR) != year) {
            one = (Calendar) one.clone();
            do {
                //如果不是同一年则累计该年的周数
                weeks = weeks + one.getActualMaximum(Calendar.WEEK_OF_YEAR);
                one.add(Calendar.YEAR, 1);
            } while (one.get(Calendar.YEAR) != year);
        }
        return contains ? (weeks + 1) : weeks;
    }

    /**
     * 得到两个时间点之间相差的月数(绝对值)
     *
     * @param one      时间1
     * @param other    时间2
     * @param contains 是否包括当月
     * @return 相差的月数
     */
    public static int getDiffMonths(Calendar one, Calendar other, boolean contains) {
        if (one.after(other)) {
            Calendar swap = one;
            one = other;
            other = swap;
        }
        //计算2个时间点的差
        int months = other.get(Calendar.MONTH) - one.get(Calendar.MONTH);
        int year = other.get(Calendar.YEAR);
        if (one.get(Calendar.YEAR) != year) {
            one = (Calendar) one.clone();
            do {
                //如果不是同一年则累计该年的月数,月份从0开始
                months = months + (one.getActualMaximum(Calendar.MONTH) + 1);
                one.add(Calendar.YEAR, 1);
            } while (one.get(Calendar.YEAR) != year);
        }
        return contains ? (months + 1) : months;
    }

    /**
     * 得到两个时间点之间相差的年数(绝对值)
     *
     * @param one      时间1
     * @param other    时间2
     * @param contains 是否包括当年
     * @return 相差的年数
     */
    public static int getDiffYears(Calendar one, Calendar other, boolean contains) {
        int years = Math.abs(other.get(Calendar.YEAR) - one.get(Calendar.YEAR));
        return contains ? (years + 1) : years;
    }

    /**
     * 获取指定日期中从指定位置起的天数
     *
     * @param calendar the calendar to work with, not null
     * @param tz       timezone
     * @param fragment the Calendar field part of calendar to calculate
     * @return number of days within the fragment of date
     * @see DateUtils#getFragmentInDays(Calendar, int)
     */
    public static long getFragmentInDays(Calendar calendar, String tz, int fragment) {
        calendar.setTimeZone(TimeZone.getTimeZone(tz));
        return DateUtils.getFragmentInDays(calendar, fragment);
    }

    /**
     * 获取指定日期中从指定位置起的小时数
     *
     * @param calendar the calendar to work with, not null
     * @param tz       timezone
     * @param fragment the Calendar field part of calendar to calculate
     * @return number of hours within the fragment of date
     */
    public static long getFragmentInHours(Calendar calendar, String tz, int fragment) {
        calendar.setTimeZone(TimeZone.getTimeZone(tz));
        return DateUtils.getFragmentInHours(calendar, fragment);
    }

    /**
     * 获取指定日期中从指定位置起的分钟数
     *
     * @param calendar the calendar to work with, not null
     * @param tz       timezone
     * @param fragment the Calendar field part of calendar to calculate
     * @return number of minutes within the fragment of date
     * @see DateUtils#getFragmentInMinutes(Calendar, int)
     */
    public static long getFragmentInMinutes(Calendar calendar, String tz, int fragment) {
        calendar.setTimeZone(TimeZone.getTimeZone(tz));
        return DateUtils.getFragmentInMinutes(calendar, fragment);
    }

    /**
     * 获取指定日期中从指定位置起的秒数
     *
     * @param calendar the calendar to work with, not null
     * @param tz       timezone
     * @param fragment the Calendar field part of calendar to calculate
     * @return number of seconds within the fragment of date
     * @see DateUtils#getFragmentInSeconds(Calendar, int)
     */
    public static long getFragmentInSeconds(Calendar calendar, String tz, int fragment) {
        calendar.setTimeZone(TimeZone.getTimeZone(tz));
        return DateUtils.getFragmentInSeconds(calendar, fragment);
    }

}
