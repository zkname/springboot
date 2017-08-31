package com.zkname.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	static final Logger log = LoggerFactory.getLogger(DateUtil.class);

	public DateUtil() {
	}

	public static String tansFormat(String date) {
		String rev = null;
		SimpleDateFormat from = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
		SimpleDateFormat to = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date dt = null;
		try {
			dt = from.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		rev = to.format(dt);
		return rev;
	}

	public static Date getNowTime() {
		return new Date(Calendar.getInstance().getTime().getTime());
	}

	public static Date getNowDate() {
		Date now = getNowTime();
		String today = Date2Str(now, "yyyy-MM-dd");
		now = Str2Date((new StringBuilder(String.valueOf(today))).append(" 00:00:00").toString());
		return now;
	}
	
	public static Date getNowDate(String pattern) {
		Date now = getNowTime();
		String today = Date2Str(now, pattern);
		now = Str2Date(today,pattern);
		return now;
	}

	public static Date getNowDateEnd() {
		Date now = getNowTime();
		String today = Date2Str(now, "yyyy-MM-dd");
		now = Str2Date((new StringBuilder(String.valueOf(today))).append(" 23:59:59").toString());
		return now;
	}

	public static String getReturnTime() {
		Date now = getNowTime();
		String today = Date2Str(now, "yyyy-MM-dd");
		return today;
	}

	public static String getReturnTime(String pattern) {
		Date now = getNowTime();
		String today = Date2Str(now, pattern);
		return today;
	}

	public static String getReturnTimeM() {
		Date now = getNowTime();
		String today = Date2Str(now, "yyyy-MM-dd HH:mm");
		return today;
	}

	public static String getReturnTimeMs() {
		Date now = getNowTime();
		String today = Date2Str(now, "yyyyMMddHHmmss");
		return today;
	}

	public static Date Str2Date(String date) {
		try {
			if (date.length() > 10)
				return Str2Date(date, "yyyy-MM-dd HH:mm:ss");
			else
				return Str2Date(date, "yyyy-MM-dd");
		} catch (Exception ex) {
			log.debug((new StringBuilder("Parse Date Error!")).append(ex.getMessage()).toString());
			return new Date(Calendar.getInstance().getTime().getTime());
		}
	}

	public static Date Str2DateX(String date, String pattern) {
		return Str2Date(date, pattern);
	}

	public static Date Str2Date(String date, String pattern) {
		java.util.Date d;
		try {
			SimpleDateFormat ft = new SimpleDateFormat(pattern);
			d = ft.parse(date);
			return new Date(d.getTime());
		} catch (Exception e) {
			log.debug((new StringBuilder("Parse Date Error!")).append(e.getMessage()).toString());
			return null;
		}
	}

	public static java.util.Date Str2utilDate(String date, boolean all) {
		java.util.Date d;
		try {
			SimpleDateFormat ft = null;
			if (all)
				ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			else
				ft = new SimpleDateFormat("yyyy-MM-dd");
			d = ft.parse(date);
			return d;
		} catch (Exception ex) {
			log.debug((new StringBuilder("Parse Date Error!")).append(ex.getMessage()).toString());
			return null;
		}

	}

	public static java.util.Date getStrutilDate() {
		return StrutilDate((new StringBuilder(String.valueOf(getReturnTime()))).append(" 23:59:59").toString(), "yyyy-MM-dd HH:mm:ss");
	}

	public static java.util.Date StrutilDate(String date, String pattern) {
		java.util.Date d;
		try {
			SimpleDateFormat ft = new SimpleDateFormat(pattern);
			d = ft.parse(date);
			return d;
		} catch (Exception e) {
			log.debug((new StringBuilder("Parse Date Error!")).append(e.getMessage()).toString());
			return null;
		}
	}

	public static String Date4Seq() {
		return Date2Str(getNowTime(), "yyyyMMddHHmmss");
	}

	public static String Date2Str(Date date, String pattern) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat ft = new SimpleDateFormat(pattern);
			return ft.format(date);
		}
	}

	public static String Date2Str(java.util.Date date) {
		if (date == null)
			date = new java.util.Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return ft.format(date);
	}

	public static String getYear(Date date) {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy");
		return ft.format(date);
	}

	public static String getMonth(Date date) {
		SimpleDateFormat ft = new SimpleDateFormat("MM");
		return ft.format(date);
	}

	public static String getDay(Date date) {
		SimpleDateFormat ft = new SimpleDateFormat("dd");
		return ft.format(date);
	}

	public static String getHour(Date date) {
		SimpleDateFormat ft = new SimpleDateFormat("HH");
		return ft.format(date);
	}

	public static String getMinute(Date date) {
		SimpleDateFormat ft = new SimpleDateFormat("mm");
		return ft.format(date);
	}

	public static String getWeek(java.util.Date time) {
		String theWeek = "";
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStringToParse = bartDateFormat.format(time);
		try {
			java.util.Date date = bartDateFormat.parse(dateStringToParse);
			SimpleDateFormat bartDateFormat2 = new SimpleDateFormat("EEEE");
			theWeek = bartDateFormat2.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return theWeek;
	}

	public static int getWeekInt(java.util.Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int i = c.get(7);
		c.clear();
		if (i - 1 == 0)
			i = 8;
		return i - 1;
	}

	public static String getDateStr(java.util.Date date) {
		String time = "";
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		time = bartDateFormat.format(date);
		time = (new StringBuilder(String.valueOf(time))).append(" ").append(getWeek(date)).toString();
		return time;
	}

	public static String getDateStr2(java.util.Date date) {
		String time = "";
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		time = bartDateFormat.format(date);
		return time;
	}

	public static Date addDate(java.util.Date date, int i) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, i);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推一天的结果
	}
	
	public static Date addMonth(java.util.Date date, int i) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, i);// 把日期往后增加一月.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推一天的结果
	}
	
	/**
	 * 当月最后一天
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date monthLastDay(java.util.Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(DateUtil.Str2Date(DateUtil.Date2Str(date, "yyyy-MM")+"-01"));
		calendar.add(Calendar.MONTH, 1);// 把日期往后增加一月.整数往后推,负数往前移动
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime(); // 这个时间就是日期往后推一天的结果
	}
	
	/**
	 * 当月第一天
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date monthBeginDay(java.util.Date date) {
		return DateUtil.Str2Date(DateUtil.Date2Str(date, "yyyy-MM")+"-01");
	}
	
	public static long getMonth(String startDate, String endDate) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        long monthday;
        try {
            Date startDate1 = f.parse(startDate);
            //开始时间与今天相比较
            Date endDate1 = f.parse(endDate);

            Calendar starCal = Calendar.getInstance();
            starCal.setTime(startDate1);

            int sYear = starCal.get(Calendar.YEAR);
            int sMonth = starCal.get(Calendar.MONTH);
//            int sDay = starCal.get(Calendar.DATE);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate1);
            int eYear = endCal.get(Calendar.YEAR);
            int eMonth = endCal.get(Calendar.MONTH);
//            int eDay = endCal.get(Calendar.DATE);

            monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));

//            if (sDay < eDay) {
//                monthday = monthday + 1;
//            }
            return monthday;
        } catch (Exception e) {
            monthday = 0;
        }
        return monthday;
    }
	
	public static final int daysBetween(Date early, Date late) { 
	     
        java.util.Calendar calst = java.util.Calendar.getInstance();   
        java.util.Calendar caled = java.util.Calendar.getInstance();   
        calst.setTime(early);   
         caled.setTime(late);   
         //设置时间为0时   
         calst.set(java.util.Calendar.HOUR_OF_DAY, 0);   
         calst.set(java.util.Calendar.MINUTE, 0);   
         calst.set(java.util.Calendar.SECOND, 0);   
         caled.set(java.util.Calendar.HOUR_OF_DAY, 0);   
         caled.set(java.util.Calendar.MINUTE, 0);   
         caled.set(java.util.Calendar.SECOND, 0);   
        //得到两个日期相差的天数   
         int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;   
         
        return days;   
   }   

	public static void main(String args[]) {
		System.out.println(getMonth("2014-01-01", "2014-05-02"));
		System.out.println(DateUtil.Date2Str(addMonth(DateUtil.Str2Date("2014-01-02".substring(0,8)+"01"),2)));
	}

}