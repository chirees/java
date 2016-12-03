package com.framework.runtime.application.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.framework.runtime.application.LogU;
/**
 * @author badqiu
 */
public class DateConvertUtils {
	
	private static final DateFormat yymmdd = new SimpleDateFormat("yyMMdd");
	
	private static final DateFormat yyyymmddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static final DateFormat yymmddHHmm = new SimpleDateFormat("yyyyMMddHHmm");
	
	private static final DateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
	
	private static final DateFormat yyyymm = new SimpleDateFormat("yyyyMM");
	
	private static final DateFormat yyyy = new SimpleDateFormat("yyyy");
	
	private static final String endTime = "235959";
	
	public static final String FORMAT_YYYYMMDD = "yyyy-MM-dd";
	
	public static java.util.Date parse(String dateString,String dateFormat) {
		return parse(dateString, dateFormat,java.util.Date.class);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends java.util.Date> T parse(String dateString,String dateFormat,Class<T> targetResultType) {
		if(StringUtils.isEmpty(dateString))
			return null;
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			long time = df.parse(dateString).getTime();
			java.util.Date t = targetResultType.getConstructor(long.class).newInstance(time);
			return (T)t;
		} catch (ParseException e) {
			String errorInfo = "cannot use dateformat:"+dateFormat+" parse datestring:"+dateString;
			throw new IllegalArgumentException(errorInfo,e);
		} catch (Exception e) {
			throw new IllegalArgumentException("error targetResultType:"+targetResultType.getName(),e);
		}
	}
	
	public static String format(java.util.Date date,String dateFormat) {
		 if(date == null)
			 return null;
		 return new SimpleDateFormat(dateFormat).format(date);
	}
	
	public static String formatYYMMDD(Date date) {
		if(date == null)
			 return null;
		return yymmdd.format(date);
	}
	
	public static String formatYYYYMMDDHHMMSS(Date date) {
		if(date == null)
			 return null;
		return yyyymmddHHmmss.format(date);
	}
	
	public static String formatYYMMDDHHMM(Date date) {
		if(date == null)
			 return null;
		return yymmddHHmm.format(date);
	}
	
	public static String formatYYYYMMDD(Date date) {
		if(date == null)
			 return null;
		return yyyymmdd.format(date);
	}
	
	public static String formatYYYYMM(Date date) {
		if(date == null)
			return null;
		return yyyymm.format(date);
	}
	
	public static String formatYYYY(Date date) {
		if(date == null)
			return null;
		return yyyy.format(date);
	}
	
	public static int getYearAndDay(Date date) {
		String s = formatYYYYMMDD(date);
		if(s == null)
			return 0;
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	* @Title: getFirstDayOfMonth
	* @Description:获取当前月份第一天日期
	* @return param
	* @return String return type
	* @throws
	*/
	public static String getFirstDayOfMonth()
	{
		Calendar c = Calendar.getInstance();   
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        return yyyymmdd.format(c.getTime());
	}
	
	/**
	* @Title: getLastDayOfMonth
	* @Description:获取当前月份最后一天日期
	* @return param
	* @return String return type
	* @throws
	*/
	public static String getLastDayOfMonth()
	{
		Calendar ca = Calendar.getInstance();   
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); 
        return yyyymmdd.format(ca.getTime());
	}
	
	/**
	* @Title: getPreviousDate
	* @Description:获取当前日期前一天
	* @param date
	* @return param
	* @return String return type
	* @throws
	*/
	public static String getPreviousDate(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date finalDate = calendar.getTime();
		return yyyymmdd.format(finalDate);
	}
	
	/**
	* @Title: getAfterDate
	* @Description:获取当前日期后一天
	* @param date
	* @return param
	* @return String return type
	* @throws
	*/
	public static String getAfterDate(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date finalDate = calendar.getTime();
		return yyyymmdd.format(finalDate);
	}
	
	public static Date getEndTimeOfDay(String date)
	{
		return DateConvertUtils.parse(date + endTime, "yyyyMMddHHmmss");
	}
	
	
	/**获取当天最小时间(即当天0点0分0秒)*/
	public static Date getCurrentDateMin(){
		Calendar c = Calendar.getInstance();  //得到当前日期和时间
        c.set(Calendar.HOUR_OF_DAY, 0);              //把当前时间小时变成０
        c.set(Calendar.MINUTE, 0);            //把当前时间分钟变成０
        c.set(Calendar.SECOND, 0);            //把当前时间秒数变成０
        c.set(Calendar.MILLISECOND, 0);       //把当前时间毫秒变成０
        
        return c.getTime();
	}
	/**获取前n天时间*/
	public static Date getDate(int num){
		return DateUtils.addDays(Calendar.getInstance().getTime(), -num);
	}
	/**返回今天剩余时间-毫秒*/
	public static Long todayLeftTime(){
		return (DateUtils.addDays(getCurrentDateMin(),1).getTime() - new Date().getTime());
	}
	
    /**
     * @Description: date1大于date2返回1，date1小于date2返回-1
     * @param format_date
     * @param date1
     * @param date2
     * @return int
     */ 
    public static int compare_date(String format_date, String date1, String date2) {
         DateFormat df = new SimpleDateFormat(format_date);
         try {
             Date dt1 = df.parse(date1);
             Date dt2 = df.parse(date2);
             if (dt1.getTime() > dt2.getTime()) {
                 return 1;
             } else if (dt1.getTime() < dt2.getTime()) {
                 return -1;
             } else {
                 return 0;
             }
         } catch (Exception exception) {
             exception.printStackTrace();
         }
         return 0;
    }
    
    /**
     * 获取当前时间
     * @throws ParseException 
     * 
     * @return返回短时间格式
     */
    public static Date getCurrentDate(String format_date)
    {
	    Date currentTime = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat(format_date);
	    Date date = null;
		try {
			date = formatter.parse(formatter.format(currentTime));
		} catch (ParseException e) {
			LogU.e("DateConvertUtils", "获取当前日期时间出错", e);
		} 
	    return date;
    }
    
    public static Date formatDate(String dateStr, String format_date)
    {
    	SimpleDateFormat formatter = new SimpleDateFormat(format_date);
    	Date date = null;
    	try {
    		date = formatter.parse(dateStr);
    	} catch (ParseException e) {
    		LogU.e("DateConvertUtils", "获取当前日期时间出错", e);
    	} 
    	return date;
    }
    
    public static void main(String[] args) {
		System.out.println(formatDate("2016-06-20",FORMAT_YYYYMMDD));
	}
    
}
