package com.thinkjoy.file.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author xufei
 *
 */
public class DateUtil {
	public static Calendar getCalendar() {
		Calendar cal = Calendar.getInstance(Locale.CHINA);

		if (cal.getTimeZone().getID().equalsIgnoreCase("GMT")) {
			cal.add(Calendar.HOUR_OF_DAY, 8);
		}

		return cal;
	}

	public static String add(Date sDate, int datepart, int value) {
		Calendar cal = getCalendar();
		cal.setTime(sDate);
		cal.add(datepart, value);
		Date date = cal.getTime();
		return date2str(date, "yyyy-MM-dd");
	}

	public static String getTimeStampto() {
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		return simple.format(new Date());
	}

	public static String getTimeStamptoEinfo() {
		SimpleDateFormat simple = new SimpleDateFormat("MM月dd日 HH:mm");
		return simple.format(new Date());
	}

	public static String getCode(Date date) {
		Calendar cale = Calendar.getInstance();
		Calendar caleNew = Calendar.getInstance();
		cale.setTime(date);
		caleNew.set(cale.get(Calendar.YEAR), cale.get(Calendar.MONTH),
				cale.get(Calendar.DAY_OF_MONTH));
		return DateUtil.date2str(cale.getTime(), "yyyyMMddHHmms");
	}

	/**
	 * 取当前时间格式"yyyyMMddHHmmssSSSS"
	 *
	 * @return
	 */
	public static String getTimeStamp() {
		return date2str(new Date(), "yyyyMMddHHmmssSSS");
	}

	/**
	 * 取当前时间格式"yyyyMMddHHmmss"
	 *
	 * @return
	 */
	public static String getNowTimeStamp() {
		return date2str(new Date(), "yyyyMMddHHmmss");
	}

	public static String getDateTimeStamp() {
		return date2str(new Date(), "yyyyMMddHH");
	}


	/**
	 * 从regex1格式的date字符串转为regex2格式的date字符串
	 *
	 * @param date
	 * @param regex1
	 * @param regex2
	 * @return
	 */
	public static String dateFormateChange(String date, String regex1,
										   String regex2) {
		Date d = null;
		try {
			d = new SimpleDateFormat(regex1).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat(regex2).format(d);

	}

	/**
	 * 把格式为regex的字符串转为date类型的日期
	 *
	 * @param date
	 * @param regex
	 * @return
	 */
	public static Date str2date(String date, String regex) {
		Date d = null;
		try {
			d = new SimpleDateFormat(regex).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 把date类型的日期转为格式为regex的字符串
	 *
	 * @param date
	 * @param regex
	 * @return
	 */
	public static String date2str(Date date, String regex) {
		SimpleDateFormat simple = new SimpleDateFormat(regex);
		return simple.format(date);
	}

	/**
	 * 日期转化为大小写(吉林大学珠海学院文件专用)
	 *
	 * @param date
	 * @return
	 */
	public static String dataToUpper(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH) + 1;
		int day = ca.get(Calendar.DAY_OF_MONTH);
		return numToUpper(year) + "年" + monthToUppder(month) + "月"
				+ dayToUppder(day) + "日";
	}

	/**
	 * 将数字转化为大写
	 *
	 * @param num
	 * @return
	 */
	public static String numToUpper(int num) {
		String u[] = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		char[] str = String.valueOf(num).toCharArray();
		String rstr = "";
		for (int i = 0; i < str.length; i++) {
			rstr = rstr + u[Integer.parseInt(str[i] + "")];
		}
		return rstr;
	}

	/**
	 * 月转化为大写
	 *
	 * @param month
	 * @return
	 */
	public static String monthToUppder(int month) {
		if (month < 10) {
			return numToUpper(month);
		} else if (month == 10) {
			return "十";
		} else {
			return "十" + numToUpper(month - 10);
		}
	}

	/**
	 * 日转化为大写
	 *
	 * @param day
	 * @return
	 */
	public static String dayToUppder(int day) {
		if (day < 20) {
			return monthToUppder(day);
		} else {
			char[] str = String.valueOf(day).toCharArray();
			// 三元运算
			return (str[1] == '0') ? numToUpper(Integer.parseInt(str[0] + ""))
					+ "十" : numToUpper(Integer.parseInt(str[0] + "")) + "十"
					+ numToUpper(Integer.parseInt(str[1] + ""));
		}
	}

	/**
	 *
	 * 获得一个月有几周,-1为默认当前
	 *
	 */
	public static int weeksInMonth(int year, int month) {

		Calendar calendar = Calendar.getInstance();

		calendar.setFirstDayOfWeek(Calendar.MONDAY);

		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}

		if (-1 == month) {
			month = calendar.get(Calendar.MONTH) + 1;
		}

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);

		return calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);

	}

	/**
	 *
	 * 获得某年，某月，第n周的开始时间和结束时间，用“，”分割 -1为默认当前
	 *
	 */
	public static String dateWeekOfMonth(int year, int month, int weeks) {

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");

		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}

		if (-1 == month) {
			month = calendar.get(Calendar.MONTH) + 1;
		}
		if (-1 == weeks) {
			weeks = calendar.get(Calendar.WEEK_OF_MONTH);
		}

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.WEEK_OF_MONTH, weeks);

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		String weekBegin = formatter.format(calendar.getTime());

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		String weekEnd = formatter.format(calendar.getTime());

		return weekBegin + "," + weekEnd;
	}

	/**
	 * 获得某月，时间格式：YYYY年MM月,-1为默认当前
	 */

	public static String getMonthDate(int year, int month) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");

		Calendar calendar = Calendar.getInstance();

		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}

		if (-1 == month) {
			month = calendar.get(Calendar.MONTH) + 1;
		}

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);

		return dateFormat.format(calendar.getTime());

	}

	/**
	 * 获得某年，某月的第n周是一年中的第几周,-1为默认当前
	 *
	 */
	public static int weeksInYear(int year, int month, int weeksInMonth) {

		Calendar calendar = Calendar.getInstance();

		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}
		if (-1 == month) {
			month = calendar.get(Calendar.MONTH) + 1;
		}
		if (-1 == weeksInMonth) {
			weeksInMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		}

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.WEEK_OF_MONTH, weeksInMonth);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 *
	 * 根据某年、某月、第n周获得本周的最后一天。-1为默认当前
	 *
	 *
	 */
	public static Date getLastDayOfWeek(int year, int month, int weeksInMonth) {

		Calendar calendar = Calendar.getInstance();
		// 设置星期一为每周的第一天
		calendar.setFirstDayOfWeek(Calendar.MONDAY);

		if (-1 == year) {
			year = calendar.get(Calendar.YEAR);
		}
		if (-1 == month) {
			month = calendar.get(Calendar.MONTH) + 1;
		}
		if (-1 == weeksInMonth) {
			weeksInMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		}
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.WEEK_OF_MONTH, weeksInMonth);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		return calendar.getTime();
	}

	/**
	 * 获得当前年
	 */
	public static int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获得当前月
	 */
	public static int getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得某月的最后一天
	 */
	public static Date getLastDayOfMonth(int year, int month) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 获得当前周是当前月的第几周
	 */
	public static int getWeekOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);// 设置周一为一周的第一天
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 比较两个时间,如果d1在d2之前则返回1，之后则返回-1，相等返回0
	 */
	public static int compareDate(Date d1, Date d2) {

		if (d1.before(d2)) {
			return 1;
		} else if (d1.after(d2)) {
			return -1;
		} else {
			return 0;
		}
	}

	public static String getToday(String pattern) {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		return formatDate(today, pattern);
	}

	public static String formatDate(Date date, String pattern) {
		if (null == pattern || "".equals(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 判断两个日期相差的天数,前面的日期大于后面的
	 */
	public static long daysBetween(Date date1, Date date2) {

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);

		long milliseconds1 = c1.getTimeInMillis();
		long milliseconds2 = c2.getTimeInMillis();

		long diff = milliseconds1 - milliseconds2;

		long days = diff / (24 * 60 * 60 * 1000);

		return days;
	}

	/**
	 *
	 * 判断两个日期相差的月数,前面的日期小于后面的日期
	 */

	public static int monthBetween(Date date1, Date date2) {

		int result = 0;

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);

		if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
			result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		} else {
			result = 12 * (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR))
					+ c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		}
		return result == 0 ? 1 : Math.abs(result);
	}

	/**
	 * 得到N个月后的时间元素
	 */
	public static Calendar calendarAfterNWeek(Calendar calendar, int n) {

		calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR)
				- n);
		return calendar;
	}

	/**
	 *
	 * 判断两个日期相差的年数,前面的日期大于后面的
	 */

	public static long yearBetween(Date date1, Date date2) {
		long days = daysBetween(date1, date2);
		return days / 365;
	}

	/**
	 * 判断两个日期相差的分钟数，前面的日期大于后面的
	 * @param date1
	 * @param date2
	 * @return
	 */

	public static long minutesBetween(Date date1, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);

		long milliseconds1 = c1.getTimeInMillis();
		long milliseconds2 = c2.getTimeInMillis();

		long diff = milliseconds1 - milliseconds2;

		long minutes = diff / (60 * 1000);
		return minutes;
	}


	/**
	 * 判断今天是星期几
	 *
	 * @return
	 */
	public static int getNowWeekDay() {
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(Calendar.DAY_OF_WEEK);

		if(week == 1){
			week = 7;
			return week;
		}
		return week-1;
	}

	public static int getNextWeekDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_WEEK,1);
		int week = calendar.get(Calendar.DAY_OF_WEEK);

		if(week == 1){
			week = 7;
			return week;
		}
		return week-1;
	}


	public static int getWeekOfYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);// 设置周一为一周的第一天
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public static void main(String[] args) {
		String date1 = "2014-11-11 10:54:56";
		String date2 = "2014-11-09 10:52:58";
		System.out.println(DateUtil.minutesBetween(DateUtil.str2date(date1,"yyyy-MM-dd HH:mm:ss"), DateUtil.str2date(date2,"yyyy-MM-dd HH:mm:ss")));

	}





}