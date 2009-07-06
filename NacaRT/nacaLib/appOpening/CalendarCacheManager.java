/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.appOpening;

import java.util.Calendar;

import jlib.misc.CurrentDateInfo;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: CalendarCacheManager.java,v 1.1 2006/05/05 12:15:53 cvsadmin Exp $
 */
public class CalendarCacheManager
{
	CalendarCacheManager()
	{
		
	}
	
	void flush()
	{
		m_timeNextCheck_ms = 0;
	}
	
	boolean mustCheckServiceOpenState()
	{
		long lCurrentTime_ms = m_currentDate.setNow();
		if(lCurrentTime_ms >= m_timeNextCheck_ms)	// No need to check: not enough tine has elaped
			return true;
		return false;
	}
	
	void setNoDefinition()
	{
		m_currentState = CalendarOpenState.AppOpened;
		m_currentOpenCalendarRange = null;
	}
	
	CalendarOpenState getCurrentState()
	{
		return m_currentState;
	}
	
	void setCurrentOpenStateUnknown()
	{
		m_currentState = CalendarOpenState.Unknown;
		m_currentOpenCalendarRange = null;
	}
	
	void setCurrentOpenState(CalendarOpenState state, OpenCalendarRange range)
	{
		long lCurrentTime_ms = m_currentDate.getTimeInMillis();
		String cs0 = m_currentDate.toString();
		
		m_currentState = state;
		m_currentOpenCalendarRange = range;

		Calendar calEnd = Calendar.getInstance();
		calEnd.set(m_currentDate.getYear(), m_currentDate.getMonth(), m_currentDate.getDay(), range.m_nHour[1], range.m_nMinute[1], range.m_nSecond[1]); 

		m_timeNextCheck_ms = calEnd.getTimeInMillis();
		String cs = calEnd.toString();
		
		long l = (m_timeNextCheck_ms - lCurrentTime_ms);
		l /= 1000 ;
		int n = 0;		
	}
	
	Integer getCurrentDateAsIntegerYYYYMMDD()
	{
		return m_currentDate.getDateAsIntegerYYYYMMDD();
	}
	
	int getCurrentDayOfWeek()
	{
		return m_currentDate.getDayOfWeek();
	}
	
	CurrentDateInfo getCurrentDate()
	{
		return m_currentDate;
	}
	
	String getCurrentOpenCalendarRangeString()
	{
		if(m_currentOpenCalendarRange != null)
			return m_currentOpenCalendarRange.getAsString();
		return "";			
	}
	
	private CalendarOpenState m_currentState = null; 
	private long m_timeNextCheck_ms = 0;
	private CurrentDateInfo m_currentDate = new CurrentDateInfo();
	private OpenCalendarRange m_currentOpenCalendarRange = null;
}
