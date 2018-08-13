package cloud.simple.service.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * 日期处理工具类
 * @version 1.0
 */
public class DateTimeUtils extends DateUtils{
	
	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String FULL_DATE_FORMAT_CN = "yyyy年MM月dd日 HH时mm分ss秒";
	public static final String PART_DATE_FORMAT = "yyyy-MM-dd";
	public static final String PART_DATE_FORMAT_CN = "yyyy年MM月dd日";
	public static final String YEAR_DATE_FORMAT = "yyyy";
	public static final String MONTH_DATE_FORMAT = "MM";
	public static final String DAY_DATE_FORMAT = "dd";
	public static final String WEEK_DATE_FORMAT = "week";
	
	
	/**
	 * 将日期类型转换为字符串
	 * @param date      日期
	 * @param xFormat   格式
	 * @return
	 */
	public static String getFormatDate(Date date,String xFormat){
		date = date == null ? new Date() : date;
		xFormat = StringUtils.isNotEmpty(xFormat) == true ? xFormat : FULL_DATE_FORMAT;
		SimpleDateFormat sdf  = new SimpleDateFormat(xFormat);
		return sdf.format(date);
	}
	
	
	
	
	/**
	 * 比较日期大小
	 * @param dateX
	 * @param dateY
	 * @return x < y return [-1];
	 * 		   x = y return [0] ; 
	 *         x > y return [1] ;
	 */
	public static int compareDate(Date dateX,Date dateY){
		return dateX.compareTo(dateY);
	}
	
	
	
	
	/**
	 * 将日期字符串转换为日期格式类型
	 * @param xDate
	 * @param xFormat 为NULL则转换如：2012-06-25
	 * @return
	 */
	public static Date parseString2Date(String xDate, String xFormat) {
		while(!isNotDate(xDate)){
			xFormat = StringUtils.isNotEmpty(xFormat) == true ? xFormat : PART_DATE_FORMAT;
			SimpleDateFormat sdf  = new SimpleDateFormat(xFormat);
			Date date = null ;
			try {
				date = sdf.parse(xDate);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
			return date;
		}
		return null;
	}
	
	
	
	
	/**
	 * 判断需要转换类型的日期字符串是否符合格式要求
	 * @param xDate   
	 * @param xFormat 可以为NULL
	 * @return
	 */
	public static boolean isNotDate(String xDate){
		SimpleDateFormat sdf  = new SimpleDateFormat(PART_DATE_FORMAT);
		try {
			if(StringUtils.isEmpty(xDate)){
				return true;
			}
			sdf.parse(xDate);
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public static boolean isDate(String xDate){
		return !isDate(xDate);
	}
	
	
	
	/**
	 * 获取俩个日期之间相差天数
	 * @param dateX
	 * @param dateY
	 * @return
	 */
	public static int getDiffDays(Date dateX,Date dateY){
		if ((dateX == null) || (dateY == null)){
			return 0;
		}
		
		int dayX = (int)(dateX.getTime() / (60 * 60 * 1000 * 24)); 
		int dayY = (int)(dateY.getTime() / (60 * 60 * 1000 * 24)); 
		
		return dayX > dayY ? dayX - dayY : dayY - dayX;
	}
	
	/**
	 * 获取俩个日期之间相差天数(日期)
	 * @param dateX
	 * @param dateY
	 * @return
	 */
	public static int getDiffDaysNoABS(Date dateX,Date dateY){
		if ((dateX == null) || (dateY == null)){
			return 0;
		}
		
		int dayX = (int)(dateX.getTime() / (60 * 60 * 1000 * 24)); 
		int dayY = (int)(dateY.getTime() / (60 * 60 * 1000 * 24)); 
		
		return dayX - dayY;
	}
	
	
	
	
	
	/**
	 * 获取传值日期之后几天的日期并转换为字符串类型
	 * @param date       需要转换的日期 date 可以为NULL 此条件下则获取当前日期
	 * @param after      天数
	 * @param xFormat    转换字符串类型 (可以为NULL)
	 * @return
	 */
	public static String getAfterCountDate(Date date, int after, String xFormat) {
		date = date == null ? new Date() : date;
		xFormat = StringUtils.isNotEmpty(xFormat) == true ? xFormat : PART_DATE_FORMAT;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, after);
		return getFormatDate(calendar.getTime(), xFormat);
	}
	
	/**
	 * 获取传值日期之前几天的日期并转换为字符串类型
	 * @param date       需要转换的日期 date 可以为NULL 此条件下则获取当前日期
	 * @param after      天数
	 * @param xFormat    转换字符串类型 (可以为NULL)
	 * @return
	 */
	public static String getBeforeCountDate(Date date, int before, String xFormat) {
		date = date == null ? new Date() : date;
		xFormat = StringUtils.isNotEmpty(xFormat) == true ? xFormat : PART_DATE_FORMAT;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -before);
		return getFormatDate(calendar.getTime(), xFormat);
	}
	
	
	
	/**
	 * 获取日期的参数 如：年 , 月 , 日 , 星期几
	 * 
	 * @param xDate 日期 可以为日期格式,可以是字符串格式; 为NULL或者其他格式时都判定为当前日期
	 * @param xFormat 年 yyyy 月 MM 日 dd 星期 week ;其他条件下都返回0
	 */
	public static int getDateTimeParam(Object xDate,String xFormat) {
		xDate = xDate == null ? new Date() : xDate;
		Date date = null;
		if(xDate instanceof String){
			date = parseString2Date(xDate.toString(), null);
		} else if(xDate instanceof Date){
			date = (Date) xDate;
		} else {
			date = new Date();
		}
		date = date == null ? new Date() : date;
		if (StringUtils.isNotEmpty(xFormat) 
				&& (xFormat.equals(YEAR_DATE_FORMAT) 
						|| xFormat.equals(MONTH_DATE_FORMAT)
						|| xFormat.equals(DAY_DATE_FORMAT)) ){
			return Integer.parseInt(getFormatDate(date, xFormat));
		} else if(StringUtils.isNotEmpty(xFormat) 
				&& (WEEK_DATE_FORMAT.equals(xFormat))) {
			Calendar cal = Calendar.getInstance(); cal.setTime(date);
			int week = cal.get(java.util.Calendar.DAY_OF_WEEK) - 1 == 0 ? 
					7 : cal.get(java.util.Calendar.DAY_OF_WEEK) - 1;
			return week;
		} else {
			return 0;
		}
	}
	
	
	/**
	 * 日期格式转换为时间戳
	 * @param time
	 * @param format
	 * @return
	 */
	public static Long getLongTime(String time,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = null;
			try {
				date = sdf.parse(time);
				return (date.getTime()/1000);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	
	/**
	 * 获取星期字符串
	 * @param xDate
	 * @return
	 */
	public static String getWeekString(Object xDate){
		int week = getDateTimeParam(xDate,WEEK_DATE_FORMAT);
		switch (week) {
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		case 7:
			return "星期日";
		default : 
			return "";
		}
	}
	
	/**
	 * 获得十位时间
	 */
	public static Long getTenBitTimestamp(){
		return System.currentTimeMillis()/1000;
	}
	
	/**
	 * 获得某天的结束时间
	 */
	public static Date getDateEnd(Date date){
		return new Date(date.getTime()+(86400-1)*1000);
	}
	
	/**
	 * 日期格式转换为毫秒
	 * @param time
	 * @param format
	 * @return
	 */
	public static Long getLongDateTime(String time,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = null;
			try {
				date = sdf.parse(time);
				return date.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	/**
	 * 获取某天开始时间戳_10位
	 */
	public static Long getStartTimestamp(Date date){
		
        Calendar calendar = Calendar.getInstance();
        date=date==null ? new Date() : date;
        calendar.setTime(date);
        
        calendar.set(Calendar.HOUR_OF_DAY, 0);  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 0);  
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime().getTime()/1000;  
    }
	
	/**
	 * 获取某天结束时间戳_10位
	 */
	public static Long getEndTimestamp(Date date){ 
    	
        Calendar calendar = Calendar.getInstance();
        date=date==null ? new Date() : date;
        calendar.setTime(date);
        
        calendar.set(Calendar.HOUR_OF_DAY, 23);  
        calendar.set(Calendar.MINUTE, 59);  
        calendar.set(Calendar.SECOND, 59);  
        calendar.set(Calendar.MILLISECOND, 999);
        
        return calendar.getTime().getTime()/1000;  
    }
	
	/**
	 * 获取昨天日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYesterday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		
        calendar.set(Calendar.HOUR_OF_DAY, 9);  
        calendar.set(Calendar.MINUTE, 59);  
        calendar.set(Calendar.SECOND, 59);  
        calendar.set(Calendar.MILLISECOND, 999);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 获取明天时间（参数时间+1天）
	 * @param date
	 * @return
	 */
	public static Date getTomorrowday(Date date){
		 Calendar c = Calendar.getInstance();  
         c.setTime(date);  
         c.add(Calendar.DAY_OF_YEAR, +1);  
        return c.getTime();
	}

	 /* 10位int型的时间戳转换为String(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param time
	 * @return
	 */
	public static String timestampToString(Integer time,String format) {
		// int转long时，先进行转型再进行计算，否则会是计算结束后在转型
		long temp = (long) time * 1000;
		Timestamp ts = new Timestamp(temp);
		String tsStr = "";
		DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			// 方法一
			tsStr = dateFormat.format(ts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;
	}
    /**
     * 获取某天开始时间
     */
    public static Date getStartTime(Date date){
        
        Calendar calendar = Calendar.getInstance();
        date=date==null ? new Date() : date;
        calendar.setTime(date);
        
        calendar.set(Calendar.HOUR_OF_DAY, 0);  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 0);  
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();  
    }
    
    /**
     * 获取某天结束时间
     */
    public static Date getEndTime(Date date){ 
        
        Calendar calendar = Calendar.getInstance();
        date=date==null ? new Date() : date;
        calendar.setTime(date);
        
        calendar.set(Calendar.HOUR_OF_DAY, 23);  
        calendar.set(Calendar.MINUTE, 59);  
        calendar.set(Calendar.SECOND, 59);  
        calendar.set(Calendar.MILLISECOND, 999);
        
        return calendar.getTime();  
    }
    
    /**
	 * Date类型转换为10位时间戳
	 * 
	 * @param time
	 * @return
	 */
	public static Integer DateToTimestamp(Date time) {
		Timestamp ts = new Timestamp(time.getTime());

		return (int) ((ts.getTime()) / 1000);
	}
	/**
	 * 获取当前时间之前或之后几分钟
	 * @param minute
	 * @return
	 */
	public static String getTimeByMinute(int minute,Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MINUTE, minute);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

    }
}
