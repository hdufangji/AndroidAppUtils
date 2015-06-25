package com.victor_fun.android_app_utils.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/**
	 * 
	 * @param stringDate  e.g. "200911120000"
	 * @return a String like yyyy-MM-dd HH:mm:ss
	 * @throws ParseException
	 */
	public static String parseString2Date(String stringDate) throws ParseException {
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String reTime = format2.format(stringDate);

		return reTime;
	}

	/**
	 * 
	 * @param time if you send a second date, please * 1000
	 * @return a String like yyyy-MM-dd HH:mm:ss
	 */
	public static String parseLong2Date(long milliseconds) {
		Date d = new Date(milliseconds);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String t = formatter.format(d);
		return t;
	}
}
