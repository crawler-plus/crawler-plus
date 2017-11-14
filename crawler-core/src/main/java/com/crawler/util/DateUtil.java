package com.crawler.util;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 时间戳生成audit no
	 * 
	 * @return
	 */
	public static String getUniqueID() {
		return DateTime.now().getYear() + "" + DateTime.now().getMonthOfYear()
				+ "" + DateTime.now().getDayOfMonth() + ""
				+ DateTime.now().getMillisOfDay();
	}

	/**
	 * GMT 转 UTC: 2016-12-07T16:00:00.000Z → Thu Dec 08 00:00:00 CST 2016
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date getUTCFormatDate(String dateStr) throws ParseException {

		dateStr = dateStr.replace("Z", " UTC");// 空格+UTC
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS Z");// 格式化表达式
		Date d = format.parse(dateStr);
		return d;
	}

	/**
	 * GMT 转 yyyy-MM-dd: 2016-12-07T16:00:00.000Z → 2016-12-08
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static String getFormatDate(String dateStr) throws ParseException {

		dateStr = dateStr.replace("Z", " UTC");// 空格+UTC
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS Z");// 格式化表达式
		Date d = format.parse(dateStr);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String formatDate = format1.format(d);
		return formatDate;
	}

	/**
	 * 获取当前指定格式Date
	 * 
	 * @param dataFormat
	 *            日期格式
	 * @return Date
	 * @throws ParseException
	 */
	public static Date getCurrentFormatDate(String dataFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);// 格式化表达式
		try {
			return sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			LoggerUtils.printExceptionLogger(logger, e);
		}
		return null;
	}

	/**
	 * 格式化excel日期
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date formatExcelDateStr(String dateStr) throws ParseException {
		if (dateStr.contains("/")) {
			dateStr.replace("/", "-").replace("/", "-");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(dateStr);
	}

	public static String formatDateTime(String formatstr) {
		SimpleDateFormat sdf=new SimpleDateFormat(formatstr);  
		Date date=new Date();
		String str=sdf.format(date);  
		return str;
	}
	
	public static String formatDateTime(String formatstr, Date dt) {
		SimpleDateFormat sdf=new SimpleDateFormat(formatstr);  
		String str=sdf.format(dt);  
		return str;
	}
	
	public static String getTMSTodayDatetime() {
		return formatDateTime("yyyy-MM-dd HH:mm:ss");
	}
}
