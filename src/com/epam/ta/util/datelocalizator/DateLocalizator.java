package com.epam.ta.util.datelocalizator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateLocalizator {
	private static final SimpleDateFormat US_DATE_FORMAT = 
			new SimpleDateFormat("MM/dd/yyyy");

	private static final Logger logger = Logger.getLogger(DateLocalizator.class);
	
	private DateLocalizator() {
	}

	public static Date parseUSDate(String dateStr) {
		Date date = null;
		try {
			 date = US_DATE_FORMAT.parse(dateStr);
		} catch (ParseException e) {
			logger.error(e);
		}
		return date;
	}
	
	public static String USformat(Date date) {
		return US_DATE_FORMAT.format(date);
	}
}
