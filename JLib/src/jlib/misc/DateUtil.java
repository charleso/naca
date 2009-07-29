/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import jlib.exception.ProgrammingException;

/**
 * <p>Utilitaire pour formattage de date/heure</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Consultas</p>
 * @author <a href=mailto:dbarman@consultas.ch>Barman Dominique</a>
 * @version 1.0
 */

public class DateUtil 
{
	GregorianCalendar calendar = null;
	private String format = "yyyy.MM.dd HH:mm:ss";

	public DateUtil() {
		calendar = new GregorianCalendar();
	}
	public DateUtil(String format) {
		calendar = new GregorianCalendar();
		this.format = format;
	}
	public DateUtil(String format, Date date) {
		calendar = new GregorianCalendar();
		calendar.setTime(date);
		this.format = format;
	}

	/**
	 * Retourne la date
	 */
	public Date getDate() {
		return calendar.getTime();
	}
	/**
	 * Initialise la date
	 */
	public void setDate(Date date) {
		calendar.setTime(date);
	}
	/**
	 * Initialise la date
	 */
	public void setDate(String cs) 
	{
		DateFormat df = DateFormat.getInstance() ;
		Date date ;
		try
		{
			date = df.parse(cs);
			calendar.setTime(date);
		} 
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Initialise le format
	 */
	public void setFormat(String format) {
		this.format = format;
	}	
  
	public String getCurrentDateYYYYMMDD()
	{
		Calendar cal = new GregorianCalendar();
		return getCurrentDateYYYYMMDD(cal);
	}
	
	public static String GetDisplayDateHour(Calendar cal)
	{
		String csY = StringUtil.FormatWithFill4LeftZero(cal.get(Calendar.YEAR)); 
		String csM = StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.MONTH));
		String csD = StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.DAY_OF_MONTH));
		
		String csH = StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.HOUR_OF_DAY)); 
		String csm = StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.MINUTE));
		String csS = StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.SECOND));

		return csD + "/" + csM + "/" + csY + " " + csH + ":" + csm + ":" + csS;
	}
  	
  	public String getCurrentDateYYYYMMDD(Calendar cal)
  	{
		String csY = StringUtil.FormatWithFill4LeftZero(cal.get(Calendar.YEAR)); 
		String csM = StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.MONTH) + 1);
		String csD = StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.DAY_OF_MONTH));
		return csY + csM + csD;
  	}
  	
  	public String getCurrentTimeHHMMSS()
	{
		Calendar cal = new GregorianCalendar();
		return getCurrentTimeHHMMSS(cal);
	}
  	
  	public String getCurrentTimeHHMMSS(Calendar cal)
  	{
		String csH = StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.HOUR_OF_DAY)); 
		String csM = StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.MINUTE));
		String csS = StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.SECOND));
		return csH + csM + csS;
  	}
  	
  	public static int getNbSecondSinceMidnight()
  	{
  		Calendar cal = new GregorianCalendar();
  		int nH = cal.get(Calendar.HOUR_OF_DAY);
  		int nM = cal.get(Calendar.MINUTE);
  		int nS = cal.get(Calendar.SECOND);
  		int n = nS + (nM * 60) + (nH * 3600);
  		return n;
  	}
  	
  	public static int getNbSecondsFromHour(int hour)
  	{
  		int n = ((hour/10000) * 3600) + ((hour%10000)/100 * 60) + ((hour%100));
  		return n;
  	}
  	
 	public String getCurrentDateTimeYYYYMMDD_HHMMSS()
	{
		Calendar cal = new GregorianCalendar();
 		return getCurrentDateYYYYMMDD(cal) + getCurrentTimeHHMMSS(cal);
	}
 	
 	public String getCurrentDateDisplayDateTime()
 	{
 		// "Format: yyyy/MM/dd HH:mm:ss
 		String cs = getCurrentDateTimeYYYYMMDD_HHMMSS();
 		return cs;
 	}


 	/**
 	 * Transforme la date sous un spécial format
 	 */
 	public String toString()
 	{
 		Format formatter = new SimpleDateFormat(format);
 		return formatter.format(calendar.getTime());
 	}
 	
 	public static String getCurrentDisplayableDateTime()
	{ 		
 		GregorianCalendar cal = new GregorianCalendar();  
		StringBuffer sb = new StringBuffer();
		
		sb.append(StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.DAY_OF_MONTH)));
		sb.append('/');
		sb.append(StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.MONTH)+1));
		sb.append('/');
		sb.append(StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.YEAR)));
		sb.append(' ');
		sb.append(StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.HOUR_OF_DAY)));
		sb.append(':');
		sb.append(StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.MINUTE)));
		sb.append(':');
		sb.append(StringUtil.FormatWithFill2LeftZero(cal.get(Calendar.SECOND)));
		return sb.toString();
	}
 	
 	
	/**
	 * Allow to format a date apart from any ISO date pattern
	 * @param Date date
	 * @param String sDateFormat
	 * @return String sDate
	 * @Examples :
	 *  The following pattern letters are defined (all other characters from 'A' to 'Z' and from 'a' to 'z' are reserved):
	 * <p> 
	 *        Letter  Date or Time Component  Presentation        Examples
	 * <p> 
	 * <ul>
	 * <li>
	 *        G       Era designator          Text                AD
	 * </li>
	 * <li>  
	 *        y       Year                    Year                1996; 96
	 * </li>
	 * <li>   
	 *        M       Month in year           Month               July; Jul; 07
	 * </li>
	 * <li>   
	 *        w       Week in year            Number              27 
	 * </li>
	 * <li> 
	 *        W       Week in month           Number              2
	 * </li>
	 * <li>   
	 *        D       Day in year             Number              189
	 * </li>
	 * <li>   
	 *        d       Day in month            Number              10
	 * </li>
	 * <li>   
	 *        F       Day of week in month    Number              2
	 * </li>
	 * <li>   
	 *        E       Day in week             Text                Tuesday; Tue
	 * </li>
	 * <li>   
	 *        a       Am/pm marker            Text                PM
	 * </li>
	 * <li>   
	 *        H       Hour in day (0-23)      Number              0
	 * </li>
	 * <li>   
	 *        k       Hour in day (1-24)      Number              24
	 * </li>
	 * <li>   
	 *        K       Hour in am/pm (0-11)    Number              0
	 * </li>
	 * <li>   
	 *        h       Hour in am/pm (1-12)    Number              12
	 * </li>
	 * <li>   
	 *        m       Minute in hour          Number              30
	 * </li>
	 * <li>   
	 *        s       Second in minute        Number              55
	 * </li>
	 * <li>   
	 *        S       Millisecond             Number              978
	 * </li>
	 * <li>   
	 *        z       Time zone               General time zone   Pacific Standard Time; PST; GMT-08:00
	 * </li>
	 * <li>   
	 *        Z       Time zone               RFC 822 time zone   -0800
	 * </li>
	 * </ul>   
	 *  <p>
	 *  Other examples :
	 *  The following examples show how date and time patterns are interpreted in the U.S. locale. 
	 *  The given date and time are 2001-07-04 12:08:56 local time in the U.S. Pacific Time time zone. 
	 *  <p>
	 *         Date and Time Pattern                        Result  
	 *  <p>
	 * <ul>
	 * <li>
	 *         "yyyy.MM.dd G 'at' HH:mm:ss z"               2001.07.04 AD at 12:08:56 PDT
	 * </li>
	 * <li>   
	 *         "EEE, MMM d, ''yy"                           Wed, Jul 4, '01
	 * </li>
	 * <li>    
	 *         "h:mm a"                                     12:08 PM
	 * </li>
	 * <li>    
	 *         "hh 'o''clock' a, zzzz"                      12 o'clock PM, Pacific Daylight Time
	 * </li>
	 * <li>    
	 *         "K:mm a, z"                                  0:08 PM, PDT
	 * </li>
	 * <li>    
	 *         "yyyyy.MMMMM.dd GGG hh:mm aaa"               02001.July.04 AD 12:08 PM
	 * </li>
	 * <li>    
	 *         "EEE, d MMM yyyy HH:mm:ss Z"                 Wed, 4 Jul 2001 12:08:56 -0700
	 * </li>
	 * <li>    
	 *         "yyMMddHHmmssZ"                              010704120856-0700
	 * </li>
	 * <li>    
	 *         "yyyy-MM-dd'T'HH:mm:ss.SSSZ"                 2001-07-04T12:08:56.235-0700
	 * </li>
	 * </ul>   
	 *  <p>
	 *  Synchronization : Date formats are not synchronized. It is recommended to create separate format 
	 *  instances for each thread. If multiple threads access a format concurrently, it must be synchronized externally.
	 */
 	public static String formatDateToString(Date date, String sDateFormat) {
 		
 		if (date == null || sDateFormat == null) return null;
 		
 		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
		String sDate = null;
			
		try {
			sDate = sdf.format(date);
				
		} catch (Exception ex) {
			String errMsg = "Wrong ISO date formating instructions : " + sDateFormat + ")";
			throw new ProgrammingException(ProgrammingException.DATE_FORMATTING_ERROR, errMsg, ex);
		}
		
		return sDate;
	}
 	
    /**
	 * Allow to create a text line of a collection of date date apart from an ISO date pattern
	 * @param Collection<Date> dateColl
	 * @param String sDateFormat - ISO pattern
	 * @param String sSeparator - list separator (e.g. : ' ' or ';')
	 * @return String concatenated list of date
	 */
	public static String createStringListOfDates(Collection<Date> dateColl, String sDateFormat, String sSeparator) {

		StringBuffer listOfDates = new StringBuffer();
		
		for (Date date : dateColl) {
			
			String sDate = formatDateToString(date, sDateFormat);
			listOfDates.append(sDate);
			listOfDates.append(sSeparator);
		}
		
		return listOfDates.toString();
	}		
 	
 	/**
	 * Allow to parse a String apart from any ISO date pattern
	 * @param String sDate
	 * @param String sDateFormat
	 * @return Date date
	 * @Examples :
	 *  The following pattern letters are defined (all other characters from 'A' to 'Z' and from 'a' to 'z' are reserved):
	 * <p> 
	 *        Letter  Date or Time Component  Presentation        Examples
	 * <p> 
	 * <ul>
	 * <li>
	 *        G       Era designator          Text                AD
	 * </li>
	 * <li>  
	 *        y       Year                    Year                1996; 96
	 * </li>
	 * <li>   
	 *        M       Month in year           Month               July; Jul; 07
	 * </li>
	 * <li>   
	 *        w       Week in year            Number              27 
	 * </li>
	 * <li> 
	 *        W       Week in month           Number              2
	 * </li>
	 * <li>   
	 *        D       Day in year             Number              189
	 * </li>
	 * <li>   
	 *        d       Day in month            Number              10
	 * </li>
	 * <li>   
	 *        F       Day of week in month    Number              2
	 * </li>
	 * <li>   
	 *        E       Day in week             Text                Tuesday; Tue
	 * </li>
	 * <li>   
	 *        a       Am/pm marker            Text                PM
	 * </li>
	 * <li>   
	 *        H       Hour in day (0-23)      Number              0
	 * </li>
	 * <li>   
	 *        k       Hour in day (1-24)      Number              24
	 * </li>
	 * <li>   
	 *        K       Hour in am/pm (0-11)    Number              0
	 * </li>
	 * <li>   
	 *        h       Hour in am/pm (1-12)    Number              12
	 * </li>
	 * <li>   
	 *        m       Minute in hour          Number              30
	 * </li>
	 * <li>   
	 *        s       Second in minute        Number              55
	 * </li>
	 * <li>   
	 *        S       Millisecond             Number              978
	 * </li>
	 * <li>   
	 *        z       Time zone               General time zone   Pacific Standard Time; PST; GMT-08:00
	 * </li>
	 * <li>   
	 *        Z       Time zone               RFC 822 time zone   -0800
	 * </li>
	 * </ul>   
	 *  <p>
	 *  Other examples :
	 *  The following examples show how date and time patterns are interpreted in the U.S. locale. 
	 *  The given date and time are 2001-07-04 12:08:56 local time in the U.S. Pacific Time time zone. 
	 *  <p>
	 *         Date and Time Pattern                        Result  
	 *  <p>
	 * <ul>
	 * <li>
	 *         "yyyy.MM.dd G 'at' HH:mm:ss z"               2001.07.04 AD at 12:08:56 PDT
	 * </li>
	 * <li>   
	 *         "EEE, MMM d, ''yy"                           Wed, Jul 4, '01
	 * </li>
	 * <li>    
	 *         "h:mm a"                                     12:08 PM
	 * </li>
	 * <li>    
	 *         "hh 'o''clock' a, zzzz"                      12 o'clock PM, Pacific Daylight Time
	 * </li>
	 * <li>    
	 *         "K:mm a, z"                                  0:08 PM, PDT
	 * </li>
	 * <li>    
	 *         "yyyyy.MMMMM.dd GGG hh:mm aaa"               02001.July.04 AD 12:08 PM
	 * </li>
	 * <li>    
	 *         "EEE, d MMM yyyy HH:mm:ss Z"                 Wed, 4 Jul 2001 12:08:56 -0700
	 * </li>
	 * <li>    
	 *         "yyMMddHHmmssZ"                              010704120856-0700
	 * </li>
	 * <li>    
	 *         "yyyy-MM-dd'T'HH:mm:ss.SSSZ"                 2001-07-04T12:08:56.235-0700
	 * </li>
	 * </ul>   
	 *  <p>
	 *  Synchronization : Date formats are not synchronized. It is recommended to create separate format 
	 *  instances for each thread. If multiple threads access a format concurrently, it must be synchronized externally.
	 */
 	public static Date parseStringToDate(String sDate, String sDateFormat) {
 		
 		if (sDate == null || sDateFormat == null
 			|| sDate.length() < sDateFormat.length()) return null;
 		
 		SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
		Date date = null;
			
		try {
			date = sdf.parse(sDate);
				
		} catch (Exception ex) {
			String errMsg = "Wrong ISO date formating instructions : " + sDateFormat + ")";
			throw new ProgrammingException(ProgrammingException.DATE_PARSING_ERROR, errMsg, ex);
		}
		
		return date;
	}
 	
	public static Date formatDate(String csDate, String csFormat, boolean strict)
	{
		Date date = null;

		if (csDate != null && csFormat != null && csFormat.length() > 0) 
		{
			try 
			{
				SimpleDateFormat formatter = new SimpleDateFormat(csFormat);
				formatter.setLenient(false);
				date = formatter.parse(csDate);
				if (strict) 
				{
					if (csFormat.length() != csDate.length()) 
					{
						date = null;
					}
				}
			} 
			catch (ParseException e) 
			{
			}
		}
		return date;
	}
	
	public static String getTimeStamp()
	{
		Date date = new Date();
		return getTimeStamp(date);
	}
	
	public static String getTimeStamp(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String cs = formatter.format(date) ;
		return cs ;
	}
	
	public static String getDateNowYYYYMMDD()
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String cs = formatter.format(date) ;
		return cs ;
	}
	
	public static String getDisplayDateNow()
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String cs = formatter.format(date) ;
		return cs ;
	}
	
	public static String getTimeNowHHMMSS()
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		String cs = formatter.format(date) ;
		return cs ;
	}
	
	public static String getDisplayTimeNow()
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String cs = formatter.format(date) ;
		return cs ;
	}

	
	public static String getDisplayTimeStamp()
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String cs = formatter.format(date) ;
		return cs ;
	}
	public static String getDisplayTimeStamp(Date date)
	{
		if (date != null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String cs = formatter.format(date) ;
			return cs ;
		}
		return "" ;
	}
	public static int getElapsedtime(Date dt)
	{
		Date d = new Date() ;
		long l = d.getTime() - dt.getTime() ;
		return (int) (l/1000) ;
	}
	
	
	// Code from DateHelper
	
	

//	***************************************************************************************
//	**                   Fonction qui ajoute une durée (un entier) à une date            **
//	***************************************************************************************

	/**
	 * @param date
	 * @param duration
	 * @return
	 */
	public static Date addDuration(Date date, int duration)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, duration);
		return calendar.getTime();
	}
	
//	***************************************************************************************
//	**                   Function that adds minutes to a given date                      **
//	***************************************************************************************

	/**
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinutes(Date date, int minutes) 
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}
	
//	*************************************************************************************************
//	** Function converting a date in String formatted as RCA Date format (YYYY-MM-DDT00:00:00.0Z) **
//	*************************************************************************************************
	private static final Format ms_formatterDate = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat ms_dfYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * @param date
	 * @return a String as RCADate
	 */
	public static String convertDate2RCA(Date date)
	{
		String rcaDate = ms_formatterDate.format(date)+"T00:00:00.0Z";
     	return rcaDate;
	}
	
	/**
	 * Convert a DD.MM.YYYY Date to a RCA formatted date (YYYY-MM-DDT00:00:00.0Z)
	 * @param date de la forme dd.mm.yyyy
	 * @return a String as RCADate
	 */
	public static String convertDate2RCA(String date)
	{
		String day = date.substring(0,2);
		String month = date.substring(3,5);
		String year = date.substring(6,10); 
		String rcaDate = year + "-" + month + "-" + day + "T00:00:00.0Z";
     	return rcaDate;
	}
	
//	***************************************************************************************************
//	**					Function that converts a YYYY-MM-DD date into a calendar                     **
//	***************************************************************************************************
	/**
	 * @param date de la forme yyyy-mm-dd
	 * @return a Calendar
	 */	
	
	
	public Calendar dateString2Calendar(String s) throws Exception 
	{
	    Calendar cal=Calendar.getInstance();
	    Date d1 = ms_dfYYYYMMDD.parse(s);
	    cal.setTime(d1);
	    return cal;
	  }
	
//	***************************************************************************************************
//	**					Function that converts a YYYY-MM-DD date into a date                         **
//	***************************************************************************************************
	/**
	 * @param date de la forme yyyy-mm-dd
	 * @return a Date
	 */

	public Date dateString2Date(String s) throws Exception
	{	    
		Date d1 = ms_dfYYYYMMDD.parse(s);
	    return d1;
	} 
}