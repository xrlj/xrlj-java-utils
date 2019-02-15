package com.xrlj.utils.time;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;


/**
 *  关于日期和时间处理的辅助类
 */
public class DateUtil {

	// 每月天数(非润年)
	static int daysInMonth[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	// 闰年的特殊月份
	static final int MONTH_FEBRUARY = 2;
	public static final int PRECISE_YEAR = 1;
	public static final int PRECISE_MONTH = 2;
	public static final int PRECISE_DAY = 3;
	public static final int PRECISE_HOUR = 4;
	public static final int PRECISE_MINUTE = 5;
	public static final int PRECISE_SECOND = 6;
	public static final int PRECISE_MilliSECOND = 7;
	public static final String DEFAULT_TIME_STRING = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String TIME_STRING = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获得当天日期
	 * 
	 * @return
	 */
	public static String getCurrentDateStr(String splitStr) {
		String curDateStr = "";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		curDateStr = String.valueOf(year) + splitStr;
		curDateStr += ((month < 10) ? "0" + String.valueOf(month) : String
				.valueOf(month)) + splitStr;
		curDateStr += ((day < 10) ? "0" + String.valueOf(day) : String
				.valueOf(day));
		return curDateStr;
	}

	public static String getCurrentDateStr(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		@SuppressWarnings("resource")
		Formatter ft = new Formatter(Locale.CHINA);// %1$tY年%1$tm月%1$td日%1$tA，%1$tT
													// %1$tp
		return ft.format("yyyy-MM-dd HH:mm:ss.SSS", cal).toString();
	}

	@SuppressWarnings("resource")
	public static String getCurrentDateStr(long millis, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		Formatter ft = new Formatter(Locale.CHINA);// %1$tY年%1$tm月%1$td日%1$tA，%1$tT
													// %1$tp
		return ft.format(format, cal).toString();
	}

	/**
	 * 获取当前月份
	 * 
	 * @param splitStr
	 * @return
	 */
	public static String getCurrentYearMonthStr(String splitStr) {
		String curDateStr = "";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		curDateStr = String.valueOf(year) + splitStr;
		curDateStr += ((month < 10) ? "0" + String.valueOf(month) : String
				.valueOf(month)) + splitStr;
		return curDateStr;
	}

	/**
	 * 获得当前年份
	 * 
	 * @return yyyy
	 */
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获取当前月份
	 * 
	 * @return
	 */
	public static int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前日
	 * 
	 * @return
	 */
	public static int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 判断是否闰月，用于计算当前时间加上分钟后的时间
	 * 
	 * @param year
	 *            年份
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		// 能被100整除, 不能被400整除的年份, 不是闰年.
		// 能被100整除, 也能被400整除的年份, 是闰年.
		if ((year % 100) == 0) {
			return ((year % 400) == 0);
		} else // 不能被100整除, 能被4整除的年份是闰年.
		{
			return ((year % 4) == 0);
		}
	}

	/**
	 * 计算当前时间加上秒钟后的时间,建议方法名换为increaseCurDateTime
	 * 
	 * @param addedSecond
	 *            在当前时间上要加的秒数，注意输入的秒钟数不能大于一个月
	 * @return yyyy-mm-dd hh-mm-ss.XXXX
	 */
	public static String calDateTime(int addedSecond) {
		// 若要限制输入的秒钟数不能大于一个月，则应在此加以判断
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		int millisecond = cal.get(Calendar.MILLISECOND);
		// 获取当前月含有的天数, 如果是闰年的二月, 加一天.
		int daysInCurMonth = daysInMonth[month - 1];
		if (isLeapYear(year) && (month == MONTH_FEBRUARY)) {
			daysInCurMonth += 1;
		}
		addedSecond += second;
		second = addedSecond % 60;
		// 输入的分钟数不能大于一个月 ？？？ pay attention to here
		minute = minute + addedSecond / 60;
		// 总的小时数
		hour = hour + minute / 60;
		// 分钟数
		minute = minute % 60;
		// 总的天数
		day = day + hour / 24;
		// 小时数
		hour = hour % 24;
		if (day > daysInCurMonth) {
			// 总的月份数,限制输入的秒钟数不能大于一个月的原因在此
			month = month + day / daysInCurMonth;
			// 天数
			day = day % daysInCurMonth;
		}
		if (month > 12) {
			// 总的年数
			year = year + month / 12;
			// 月份数
			month = month % 12;
		}
		String dateTimeStr = "1900-01-01";
		dateTimeStr = String.valueOf(year) + "-";
		dateTimeStr += ((month < 10) ? "0" + String.valueOf(month) : String
				.valueOf(month)) + "-";
		dateTimeStr += ((day < 10) ? "0" + String.valueOf(day) : String
				.valueOf(day)) + " ";
		dateTimeStr += ((hour < 10) ? "0" + String.valueOf(hour) : String
				.valueOf(hour)) + ":";
		dateTimeStr += ((minute < 10) ? "0" + String.valueOf(minute) : String
				.valueOf(minute)) + ":";
		dateTimeStr += ((second < 10) ? "0" + String.valueOf(second) : String
				.valueOf(second));
		dateTimeStr += "." + String.valueOf(millisecond);
		return dateTimeStr;
	}

	/**
	 * 计算两个时间之间的时间差
	 * 
	 * @param startTime
	 *            减数，格式为yyyy-mm-dd hh-mm-ss
	 * @param endTime
	 *            被减数，格式为yyyy-mm-dd hh-mm-ss
	 * @return strDateTime1 - strDateTime2的时间差，单位为毫秒
	 */
	public static long computeInterval(String startTime, String endTime) {
		long interval = 0;
		Timestamp date1 = convertStrToDate(startTime);
		Timestamp date2 = convertStrToDate(endTime);
		interval = date1.getTime() - date2.getTime();
		return interval;
	}

	/**
	 * 得到指定精度的时间字符串
	 * 
	 * @param dateTimeString
	 *            原始时间字符串，格式为yyyy-mm-dd hh:mm:ss
	 * @param precise
	 *            指定的精度
	 * @return
	 */
	public static String customDateTimeStr(String dateTimeString, int precise) {
		if (dateTimeString == null) {
			dateTimeString = "";
			return dateTimeString;
		}
		if (dateTimeString.trim().length() == 0) {
			return dateTimeString;
		}
		if (dateTimeString.startsWith("1900")) {
			dateTimeString = "";
			return dateTimeString;
		}
		if (precise == PRECISE_YEAR && dateTimeString.length() >= 4) {
			dateTimeString = dateTimeString.substring(0, 4);
		}
		if (precise == PRECISE_MONTH && dateTimeString.length() >= 7) {
			dateTimeString = dateTimeString.substring(0, 7);
		}
		if (precise == PRECISE_DAY && dateTimeString.length() >= 10) {
			dateTimeString = dateTimeString.substring(0, 10);
		}
		if (precise == PRECISE_HOUR && dateTimeString.length() >= 13) {
			dateTimeString = dateTimeString.substring(0, 13);
		}
		if (precise == PRECISE_MINUTE && dateTimeString.length() >= 16) {
			dateTimeString = dateTimeString.substring(0, 16);
		}
		if (precise == PRECISE_SECOND && dateTimeString.length() >= 19) {
			dateTimeString = dateTimeString.substring(0, 19);
		}
		return dateTimeString;
	}

	public static String convertDateToStr(Timestamp date) {
		String result = "1900-01-01 00:00:00.000";
		if (date != null) {
			result = date.toString();
		}
		return result;
	}

	public static Timestamp convertStrToDate(String strDate) {
		if (strDate == null) {
			strDate = "1900-01-01 00:00:00.000";
		} else {
			if (strDate.trim().length() == 0) {
				strDate = "1900-01-01 00:00:00.000";
			} else if (strDate.trim().length() <= 10) // 传入的日期不包含时间
			{
				strDate += " 00:00:00.000";
			} else if (strDate.trim().length() == 16) // 传入的日期包含时间到分钟位,如2000-01-01
														// 10:10
			{
				strDate += ":00.000";
			}
		}
		return Timestamp.valueOf(strDate);
	}

	/**
	 * java 获取当前时间毫秒
	 * 
	 * @return
	 */
	public static long getCurrentTimeMillis() {
		return Calendar.getInstance().getTimeInMillis();
	}

	// //////////////////////////////////////////

	/**
	 * 字符串时间转换成日期
	 * 
	 * @param dateString
	 * @return Date Thu Apr 26 10:28:05 CST 2012
	 */
	public static Date toDate(String dateString) {
		return toDate(dateString, null);
	}

	public static Date stringToDate(String dateStr, String formatStr) {
		DateFormat dd = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = dd.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date toDate(String dateString, String dateFormat) {
		DateFormat format = null;
		Date date = null;
		try {
			if (null == dateFormat || "".equals(dateFormat)) {
				format = new SimpleDateFormat(DEFAULT_TIME_STRING);
			} else {
				format = new SimpleDateFormat(dateFormat);
			}
			date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串时间转换成日期
	 *
	 * @param str
	 * @param dateTimeString
	 * @return Date Thu Apr 26 10:28:05 CST 2012
	 */
	public static Date strToDate(String str, String dateTimeString) {
		DateFormat format;
		Date date = null;
		try {
			if (null == dateTimeString || "".equals(dateTimeString)) {
				format = new SimpleDateFormat(DEFAULT_TIME_STRING);
			} else {
				format = new SimpleDateFormat(dateTimeString);
			}
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str 2011-10-26 15:37:35
	 */
	public static String getDateToString(Date date) {
		return getDateToString(date, null);
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str 2011-10-26 15:37:35 638
	 */
	public static String getDateToString(Date date, String dateFormat) {
		SimpleDateFormat format;
		if (null == dateFormat || "".equals(dateFormat)) {
			format = new SimpleDateFormat(DEFAULT_TIME_STRING);
		} else {
			format = new SimpleDateFormat(dateFormat);
		}
		return format.format(date);
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date 2011-10-26 15:37:35
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * 字符串转换成日期2013-10-13 10:01:47,109
	 * 
	 * @param str
	 * @return date 2011-10-26 15:37:35
	 */
	public static Date StrToDate(String str, String formatStr) {

		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public static Date StrToDate1(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/*************************************************************************
	 * 8 字符串转换为java.util.Date<br>
	 * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z 如 '2002-1-1 AD at 22:10:59 PSD'<br>
	 * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br>
	 * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br>
	 * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br>
	 * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br>
	 * 
	 * @param time
	 *            String 字符串<br>
	 * @return Date 日期<br>
	 */

	public static Date stringToDate(String time) {
		SimpleDateFormat formatter;
		int tempPos = time.indexOf("AD");
		time = time.trim();
		formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		if (tempPos > -1) {
			time = time.substring(0, tempPos) + "公元"
					+ time.substring(tempPos + "AD".length());// china
			formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		}
		tempPos = time.indexOf("-");
		if (tempPos > -1 && (time.indexOf(" ") < 0)) {
			formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
		} else if ((time.indexOf("/") > -1) && (time.indexOf(" ") > -1)) {
			formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		} else if ((time.indexOf("-") > -1) && (time.indexOf(" ") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if ((time.indexOf("/") > -1) && (time.indexOf("am") > -1)
				|| (time.indexOf("pm") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		} else if ((time.indexOf("-") > -1) && (time.indexOf("am") > -1)
				|| (time.indexOf("pm") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		}
		ParsePosition pos = new ParsePosition(0);
		Date ctime = formatter.parse(time, pos);

		return ctime;
	}

	/**
	 * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss'(24小时制)<br>
	 * 如Sat May 11 17:24:21 CST 2002 to '2002-05-11 17:24:21'<br>
	 * 
	 * @param time
	 *            Date 日期<br>
	 * @return String 字符串<br>
	 */

	public static String dateToString(Date time) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = formatter.format(time);

		return ctime;
	}

	/**
	 * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss a'(12小时制)<br>
	 * 如Sat May 11 17:23:22 CST 2002 to '2002-05-11 05:23:22 下午'<br>
	 * 
	 * @param time
	 *            Date 日期<br>
	 * @param x
	 *            int 任意整数如：1<br>
	 * @return String 字符串<br>
	 */
	public static String dateToString(Date time, int x) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		String ctime = formatter.format(time);

		return ctime;
	}

	/**
	 * 取系统当前时间:返回只值为如下形式 2002-10-30 08:28:56 下午
	 * 
	 * @param hour
	 *            为任意整数
	 * @return String
	 */
	public static String Now(int hour) {
		return dateToString(new Date(), hour);
	}

	/**
	 * 取系统时间:返回值为如下形式 2002-10-30 date -1 前一天 +1 后一天
	 * 
	 * @return String
	 */
	public static String getYYYY_MM_DD(int date) {
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.DATE, date); // 得到前一天
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

	/**
	 * 取系统时间:返回值为如下形式 2002-10-30 date -1 前一天 +1 后一天
	 * 
	 * @return String
	 */
	public static String getYYYY_MM_DD(int date, String dateFormat) {
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.DATE, date); // 得到前一天
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss").format(calendar
				.getTime());
	}

	// /**
	// * 取系统给定时间:返回值为如下形式 2002-10-30
	// *
	// * @return String
	// */
	// public static String getYYYY_MM_DD(String date) {
	// return date.substring(0, 10);
	//
	// }
	/**
	 * 获取小时
	 * 
	 * @return
	 */
	public static String getHour() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("H");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	/**
	 * 获取天
	 * 
	 * @return
	 */
	public static String getDay() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("d");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	/**
	 * 获取指定时间“天”
	 * 
	 * @return
	 */
	public static String getDay(Date date) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("d");
		String ctime = formatter.format(date);
		return ctime;
	}

	/**
	 * 获取当前月份
	 * 
	 * @return
	 */
	public static String getMonth() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("M");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	/**
	 * 获取年份
	 * 
	 * @return
	 */
	public static String getYear() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	/**
	 * 获取星期
	 * 
	 * @return
	 */
	public static String getWeek() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("E");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	/**
	 * 获取一个日历对象为当前系统时间
	 * 
	 * @return
	 */
	public static Calendar getCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar;
	}

	/**
	 * 获取一个指定时间日历对象
	 * 
	 * @return
	 */
	public static Calendar getCalendar(String dateString) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate(dateString));
		return calendar;
	}

	/**
	 * 获取一个指定时间日历对象
	 * 
	 * @return
	 */
	public static Calendar getCalendar(String dateString, String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate(dateString, dateFormat));
		return calendar;
	}

	/**
	 * 获得相隔指定的时间 -1 或 1
	 * 
	 * @param num
	 * @param calendarDateType
	 *            : Calendar.DATE,Calendar.Month等
	 * @param date
	 * @param format
	 *            "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String getAssignYesterday(int num, int calendarDateType,
			Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// calendar.add(Calendar.DATE, num);
		calendar.add(calendarDateType, num);
		Date cdate = calendar.getTime();
		String str = sdf.format(cdate);
		return str;
	}

	/**
	 * 获得相隔指定的时间 -1 或 1
	 * 
	 * @param num
	 * @param calendarDateType
	 *            : Calendar.DATE,Calendar.Month等
	 * @param date
	 * @return
	 */
	public static Date getAssignYesterday(int num, int calendarDateType,
			Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// calendar.add(Calendar.DATE, num);
		calendar.add(calendarDateType, num);
		Date cdate = calendar.getTime();
		return cdate;
	}

	/**
	 * 返回两个日期相差的月数
	 * 
	 * @param date1
	 *            <String>
	 * @param date2
	 *            <String>
	 * @return int
	 * @throws ParseException
	 */
	public static int getMonthSpace(String date1, String date2, String format) {
		int result = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(sdf.parse(date1));
			c2.setTime(sdf.parse(date2));

			int c1year = c1.get(Calendar.YEAR);
			int c1month = c1.get(Calendar.MONTH);

			int c2year = c2.get(Calendar.YEAR);
			int c2month = c2.get(Calendar.MONTH);

			if (c2year == c1year) {
				result = c2month - c1month;// 两个日期相差几个月，即月份差
			} else {
				result = 12 * (c2year - c1year) + c2month - c1month;// 两个日期相差几个月，即月份差
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result == 0 ? 1 : Math.abs(result);
	}

	/**
	 * 返回两个日期相差的月数
	 * 
	 * @param date1
	 *            <String>
	 * @param date2
	 *            <String>
	 * @return int
	 * @throws ParseException
	 */
	public static int getMonthSpace(Date date1, Date date2) {
		int result = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int c1year = c1.get(Calendar.YEAR);
		int c1month = c1.get(Calendar.MONTH);

		int c2year = c2.get(Calendar.YEAR);
		int c2month = c2.get(Calendar.MONTH);

		if (c2year == c1year) {
			result = c2month - c1month;// 两个日期相差几个月，即月份差
		} else {
			result = 12 * (c2year - c1year) + c2month - c1month;// 两个日期相差几个月，即月份差
		}
		return result == 0 ? 1 : Math.abs(result);
	}

	/**
	 * 返回两个日期年月日是否相同
	 * 
	 * @param date1
	 *            <String>
	 * @param date2
	 *            <String>
	 * @return int
	 * @throws ParseException
	 */
	public static boolean isEqualDate(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int c1year = c1.get(Calendar.YEAR);
		int c1month = c1.get(Calendar.MONTH);
		int c1day = c1.get(Calendar.DATE);

		int c2year = c2.get(Calendar.YEAR);
		int c2month = c2.get(Calendar.MONTH);
		int c2day = c2.get(Calendar.DATE);

		if (c2year == c1year && c1month == c2month && c1day == c2day)
			return true;
		return false;
	}

	/**
	 * 获得当天的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateStart(Date date) {
		try {
			SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
			return shortSdf.parse(shortSdf.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获得当天的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateEnd(Date date) {
		try {
			SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat longSdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return longSdf.parse(shortSdf.format(date) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
	}

	/**
	 * 获得当月的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthStart(Date date) {
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		try {
			c1.set(Calendar.DATE, 1);
			return shortSdf.parse(shortSdf.format(c1.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
	}

	/**
	 * 获得当月的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthEnd(Date date) {
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		try {
			c1.set(Calendar.DATE, 1);
			c1.add(Calendar.MONTH, 1);
			c1.add(Calendar.DATE, -1);
			return longSdf.parse(shortSdf.format(c1.getTime()) + " 23:59:59");
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
	}

	/**
	 * 当前时间与传入时间相隔多少年
	 * 
	 * @param date
	 * @return
	 */
	public static int getYearCount(Date date) {
		if (date == null)
			return 0;
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		Calendar nowDate = Calendar.getInstance();
		nowDate.setTime(new Date());
		try {
			int c1Year = c1.get(Calendar.YEAR);
			int nowYear = nowDate.get(Calendar.YEAR);
			int result = nowYear - c1Year;
			if (result < 0)
				result = 0;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static String getOneHoursAgoTimeString(int i) {
		String oneHoursAgoTime = "";
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR);// 小时
		System.out.println(hour);
		cal.set(Calendar.HOUR, hour - i); // 把时间设置为当前时间-1小时，同理，也可以设置其他时间
		oneHoursAgoTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss")
				.format(cal.getTime());// 获取到完整的时间
		return oneHoursAgoTime;
	}

	public static long getOneHoursAgoTime(int i) {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR);// 小时
		cal.set(Calendar.HOUR, hour - i); // 把时间设置为当前时间-1小时，同理，也可以设置其他时间
		return cal.getTimeInMillis();
	}

	/*public static void main(String[] args) {

		// Date date = new Date();
		// System.out.println("日期转字符串：" + DateUtil.DateToStr(date));
		// System.out.println("字符串转日期："
		// + DateUtil.StrToDate(DateUtil.DateToStr(date)));
		// System.out.println(DateUtil.getYYYY_MM_DD());

		// 2. 获取前一天、前一个月的日期

		// Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
		// calendar.add(Calendar.DATE, +1); //得到前一天
		//
		// String yestedayDate = new
		// SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		// System.out.println(yestedayDate);

		// calendar.add(Calendar.MONTH, -1); //得到前一个月
		// int year = calendar.get(Calendar.YEAR);
		//
		//
		// int month = calendar.get(Calendar.MONTH)+1; //输出前一月的时候要记得加1

		// System.out.println(JuFengDateUtil.getYYYY_MM_DD() + " 06:00:00");
		// System.out.println(JuFengDateUtil.getYYYY_MM_DD(+1) + " 06:00:00");
		// System.out.println(JuFengDateUtil.timeMillisToDate(System.currentTimeMillis()));
		// System.out.println(JuFengDateUtil.timeMillisToDate(System.currentTimeMillis(),
		// "yyyy-MM-dd HH:mm:ss"));

		// Calendar now = Calendar.getInstance();
		// System.out.println("Current Year is : " + now.get(Calendar.YEAR));
		// // month start from 0 to 11
		// System.out.println("Current Month is : " + (now.get(Calendar.MONTH) +
		// 1));
		// System.out.println("Current Date is : " + now.get(Calendar.DATE));
		// System.out.println("yyyy-MM-dd HH:mm:ss SSS".length());

		// System.out.println(getDateToString(new Date(), "yyyy"));
		// System.out.println(getDateToString(new Date(), "MM"));
		// System.out.println(getDateToString(new Date(), "dd"));
		// System.out.println(getDateToString(new Date(), "yyyy-MM"));
		// System.out.println(getDateToString(new Date(), "yyyy-MM-dd"));
		// System.out.println(getDateToString(new Date(), "yyyy-MM-dd HH"));
		// System.out.println(getDateToString(new Date(), "yyyy-MM-dd HH:mm"));
		// System.out.println(getDateToString(new Date(),
		// "yyyy-MM-dd HH:mm:ss"));
		// System.out.println(getDateToString(new Date(),
		// "yyyy-MM-dd HH:mm:ss.SSS"));
		// System.out.println(getDateToString(new Date(),
		// "yyyy-MM-dd HH:mm:ss.SSS"));
		// System.out.println(getDateToString(new Date(), "HH"));
		// System.out.println(getDateToString(new Date(), "HH:mm"));
		// System.out.println(getDateToString(new Date(), "HH:mm:ss"));
		// System.out.println(getDateToString(new Date(), "HH:mm:ss.SSS"));
		//
		//
		// System.out.println(isLeapYear(2012));
		// System.out.println(computeInterval(getDateToString(new Date()),
		// "2012-04-26 14:42:12.580")/1000);
		// System.out.println(getCurrentTimeMillis());
		// System.out.println(convertDateToStr(Timestamp.valueOf(getDateToString(new
		// Date()))));
		//
		// System.out.println(new Date(getCurrentTimeMillis()));
		// System.out.println(getDateToString(new
		// Date(getCurrentTimeMillis())));
		//
		// System.out.println(getAssignYesterday(-3, Calendar.MONTH, new Date(),
		// "yyyy-MM-dd HH:mm:ss"));
		//
		// System.out.println(DateUtil.getDateToString(new
		// Date(),"yyMdHmsSSS"));
		//
		// System.out.println(getYYYY_MM_DD(-1, "yyyy-MM-dd HH:mm:ss.sss"));
		//
		//
		// Date date = DateUtil.StrToDate("2011-09-10 15:55:36.697",
		// "yyyy-MM-dd HH:mm:ss.sss");
		// System.out.println(date);
		// date.setHours(18);
		// System.out.println(date);
		//
		// System.out.println("ddd");
		// long millis = getOneHoursAgoTime(18);
		// System.out.println(millis);
		// Calendar c = Calendar.getInstance();
		// c.setTimeInMillis(millis);
		// System.out.println(getDateToString(c.getTime(),
		// "yyyy-MM-dd HH:mm:ss.sss"));
		//
		//
		// System.out.println(new Date().getTime());

		// Date date = StrToDate("2014-03-03 14:15:12");
		// System.out.println(getDateToString(getMonthStart(date),"yyyy年MM月dd日HH时mm分"));
		// System.out.println(getDateToString(getMonthEnd(date),"yyyy-MM-dd HH:mm:ss"));

		// System.out.println(getDateToString(new Date(), "yyyyMMddHHmm"));
		// System.out.println(getDateToString(new Date(),"yyyy年MM月dd日HH时mm分"));
		// System.out.println(getMonthSpace("2013-09-30 18:02:28",
		// "2015-02-27 18:02:28", "yyyy-MM-dd HH:mm:ss"));
		// System.out.println(getMonthSpace(StrToDate("2013-09-30 18:02:28",
		// "yyyy-MM-dd HH:mm:ss"),
		// "2015-02-27 18:02:28", "yyyy-MM-dd HH:mm:ss"));
		// System.out.println(getAssignYesterday(17, Calendar.MONTH,
		// StrToDate("2013-09-30 18:02:28", "yyyy-MM-dd HH:mm:ss"),
		// "yyyy-MM-dd HH:mm:ss"));

		// Date datea = new Date("3/31/2014 12:59:30 AM");
		// System.out.println(datea);
		// String str = getDateToString(datea);
		// System.out.println(str);
		Date date = new Date();
		String test = getDateToString(date, "ddssS");
		if (test.length() > 6)
			test = test.substring(0, 6);
		System.out.println(test);
		System.out.println(getDateToString(date, "ddssS"));
		System.out.println(getDateToString(date, "ddssSSS"));
		System.out.println(getDateToString(date, "ddss.SSS"));
	}*/



}
