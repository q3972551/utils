package org.peanut.common.utils; /**
 * Author  : Cao_Xiao_Bin
 * Created on : 2015年4月1日上午11:46:25
 * Content : 时间生成类
 * Example :
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {
	public static final int ONE_SECOND = 1000;
	public static final int ONE_MINUTE = 60 * ONE_SECOND;
	public static final int ONE_HOUR = 60 * ONE_MINUTE;
	public static final long ONE_DAY = 24 * ONE_HOUR;
	public static final long ONE_WEEK = 7 * ONE_DAY;
	public static final int ONE_WEEKS = 7;
	public static final int ONE_Month = 30;
	public static final int Fifteen_WEEK = 15 * ONE_WEEKS;
	public static final int Fifteen_Month = 15 * ONE_Month;

	public static long getCertainClockOfToday(long dateTime, int hour,
			int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateTime);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 是否超过了 每周指定某天的某个时间
	 * 
	 * @return
	 */
	public static boolean hasBetweenDayOfWeek(int minDayOfWeek, int minHour,
			int minMin, int maxDayOfWeek, int maxHour, int maxMin) {
		Calendar minTime = new GregorianCalendar();
		minTime.set(Calendar.DAY_OF_WEEK, minDayOfWeek);
		minTime.set(Calendar.HOUR, minHour);
		minTime.set(Calendar.MINUTE, minMin);
		Calendar maxTime = new GregorianCalendar();
		minTime.set(Calendar.DAY_OF_WEEK, maxDayOfWeek);
		minTime.set(Calendar.HOUR, maxHour);
		minTime.set(Calendar.MINUTE, maxMin);
		long now = System.currentTimeMillis();
		return now >= minTime.getTimeInMillis()
				&& now < maxTime.getTimeInMillis();
	}

	/**
	 * 获得延迟时间
	 * 
	 * @param certainTime
	 *            延迟时间段
	 * @return
	 */
	public static long getTimeMillisAfter(long certainTime) {
		return System.currentTimeMillis() + certainTime;
	}

	public static long getTimeMillsAfter(long startTime, int month, int day,
			int hour) {
		GregorianCalendar start = new GregorianCalendar();
		start.setTimeInMillis(startTime);
		start.add(GregorianCalendar.MONTH, month);
		start.add(GregorianCalendar.HOUR, day * 24 + hour);
		return start.getTimeInMillis();
	}

	/**
	 * 是否超过了开始时间，之后的一段时间
	 *
	 * @param startTime
	 * @param month
	 *            月份位移
	 * @param day
	 *            天数位移
	 * @param hour
	 *            小时位移
	 * @return
	 */
	public static boolean hasExceedAfter(long startTime, int month, int day,
			int hour) {
		GregorianCalendar start = new GregorianCalendar();
		start.setTimeInMillis(startTime);
		start.add(GregorianCalendar.MONTH, month);
		start.add(GregorianCalendar.HOUR, day * 24 + hour);
		long now = System.currentTimeMillis();
		return now >= start.getTimeInMillis();
	}

	public static boolean hasExceedAfterHasDiffer(long startTime, int month,
			int day, int hour, int differ) {
		GregorianCalendar start = new GregorianCalendar();
		start.setTimeInMillis(startTime);
		start.add(GregorianCalendar.MONTH, month);
		start.add(GregorianCalendar.HOUR, day * 24 + hour);
		long now = System.currentTimeMillis();
		return now >= start.getTimeInMillis() - differ;
	}

	/**
	 * 判断是不是同一天
	 * 
	 * @param oneDateTime
	 * @param anotherDateTime
	 * @return
	 */
	public static boolean isInSameDate(long oneDateTime, long anotherDateTime) {
		Calendar firstCalendar = Calendar.getInstance();
		firstCalendar.setTimeInMillis(oneDateTime);

		Calendar anotherCalendar = Calendar.getInstance();
		anotherCalendar.setTimeInMillis(anotherDateTime);

		int firstYear = firstCalendar.get(Calendar.YEAR);
		int anotherYear = anotherCalendar.get(Calendar.YEAR);
		if (firstYear != anotherYear) {
			return false;
		}
		int firstMonth = firstCalendar.get(Calendar.MONTH);
		int anotherMonth = anotherCalendar.get(Calendar.MONTH);
		if (firstMonth != anotherMonth) {
			return false;
		}

		int firstDay = firstCalendar.get(Calendar.DAY_OF_MONTH);
		int anotherDay = anotherCalendar.get(Calendar.DAY_OF_MONTH);
		return firstDay == anotherDay;
	}

	/***
	 * 判断是不是 在当天的某一时间之前
	 * 
	 * @param dateTime
	 *            参与比较的事件
	 * @param hour
	 *            当天的小时数
	 * @param minute
	 *            当天的分数
	 * @param second
	 *            当天的秒数
	 * @return
	 */
	public static boolean isBeforeDateTime(long dateTime, int hour, int minute,
			int second) {
		return dateTime <= getCertainClockOfToday(dateTime, hour, minute,
				second);
	}

	public static int caculateIndexOfTodayInMonth() {
		long time = System.currentTimeMillis();
		Calendar calendar0 = Calendar.getInstance();
		calendar0.setTimeInMillis(time);
		return calendar0.get(Calendar.DAY_OF_MONTH);
	}

	public static int caculateMaxIndexOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		calendar.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = calendar.get(Calendar.DATE);
		return maxDate;
	}

	public static boolean isSameDay(Calendar lastNext, Calendar curday) {
		return lastNext.get(Calendar.YEAR) == curday.get(Calendar.YEAR)
				&& lastNext.get(Calendar.MONTH) == curday.get(Calendar.MONTH)
				&& lastNext.get(Calendar.DAY_OF_MONTH) == curday
				.get(Calendar.DAY_OF_MONTH);
	}

	public static boolean isSameDay(long lastTime, long curTime) {
		Calendar lastTonext = Calendar.getInstance();
		lastTonext.setTimeInMillis(lastTime);

		Calendar cur = Calendar.getInstance();
		cur.setTimeInMillis(curTime);

		return isSameDay(lastTonext, cur);
	}

	/**
	 * 得到某个时间的当天的0:00分的时间
	 * 
	 * @param time
	 * @return
	 */
	public static long getStartTimeOneDay(long time) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(time);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.MILLISECOND, 0);
		c1.set(Calendar.SECOND, 0);
		return c1.getTimeInMillis();
	}

	/**
	 * 得到今天的0:00分的时间
	 * 
	 * @return
	 */
	public static long getStartTimeToday() {
		return getStartTimeOneDay(System.currentTimeMillis());
	}

	/**
	 * 获得当前小时，剩余多少次5分钟,若是14 分 ，得到 3次 ，
	 * 
	 * @return 返回次数
	 */
	public static int get5MinCurHour() {
		Calendar c1 = Calendar.getInstance();
		int min = c1.get(Calendar.MINUTE);

		int count = min % 5 == 0 ? 0 : 1;
		int times = min / 5 + count;
		return times;
	}
	/**
	 * 获取这周剩余天数
	 */
	public static int getSurDayForWeek()
	{
		Calendar c1 = Calendar.getInstance();
		int day     = c1.get(Calendar.DAY_OF_WEEK);
		return 7 - (day - 1);
	}

	/***
	 * 日期转换时间戳
	 * @param time
	 * @return
	 */
	public static Long dateToStamp(String time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmm");
		Date date = null;
		try {
			date = simpleDateFormat.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}

	/***
	 * 日期转换时间戳
	 * @param time
	 * @return
	 */
	public static Long dateToStamp(String time,String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				format);
		Date date = null;
		try {
			date = simpleDateFormat.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}

	/**
	 * 判断是否在该时间内 HH:mm
	 * @param nowTime
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException 
	 */
	public static boolean isEffectiveDate(long nowTime, String startTime, String endTime,String format) throws ParseException {
		
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date startDate = df.parse(startTime);
		Date endDate = df.parse(endTime);
		Date nowDate = df.parse(df.format(new Date()));
		if (nowDate.getTime() == startDate.getTime()
				|| nowDate.getTime() == endDate.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowDate);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startDate);

		Calendar end = Calendar.getInstance();
		end.setTime(endDate);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 * 时间戳转换日期
	 * @param time
	 * @return
	 */
	public static String stampToDate(Long time) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		res = simpleDateFormat.format(date);
		return res;
	}

	public static Date stampToDate1(Long time) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		res = simpleDateFormat.format(date);
		try {
			date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(res);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return date;
	}
}
