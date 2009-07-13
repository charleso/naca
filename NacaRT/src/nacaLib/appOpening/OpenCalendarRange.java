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
import jlib.misc.NumberParser;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: OpenCalendarRange.java,v 1.2 2006/05/08 10:38:06 cvsadmin Exp $
 */
public class OpenCalendarRange
{
	OpenCalendarRange()
	{
		m_nHour = new int[2];
		m_nMinute = new int[2];
		m_nSecond = new int[2];
	}
	
	void set(CalendarOpenState state, String csMin, String csMax)
	{
		m_openState = state;
		fill(0, csMin);
		fill(1, csMax);
	}
	
	void setBegin(CalendarInstant i)
	{
		m_openState = i.m_openState;
		m_nHour[0] = i.m_nHour;
		m_nMinute[0] = i.m_nMinute;
		m_nSecond[0] = i.m_nSecond;
	}

	void setEnd(CalendarInstant i)
	{
		m_nHour[1] = i.m_nHour;
		m_nMinute[1] = i.m_nMinute;
		m_nSecond[1] = i.m_nSecond;
	}
	
	private void fill(int nMinMax, String csTime)
	{
		int n = csTime.indexOf(":");
		String cs = csTime.substring(0, n);
		m_nHour[nMinMax] = NumberParser.getAsInt(cs);
		
		csTime = csTime.substring(n+1);
		n = csTime.indexOf(":");
		cs = csTime.substring(0, n);
		m_nMinute[nMinMax] = NumberParser.getAsInt(cs);
		
		csTime = csTime.substring(n+1);
		m_nSecond[nMinMax] = NumberParser.getAsInt(csTime);		
	}
	
	void setCloseAllDay()
	{
		m_openState = CalendarOpenState.AppClosed; 
		m_nHour[0] = 0;
		m_nMinute[0] = 0;
		m_nSecond[0] = 0;
		m_nHour[1] = 24;
		m_nMinute[1] = 0;
		m_nSecond[1] = 0;
	}
	
	boolean isSameType(OpenCalendarRange r)
	{
		if(m_openState == r.m_openState)
			return true;
		return false;
	}
	
	CalendarInstant getInstant(int n)
	{
		CalendarInstant i = new CalendarInstant();
		i.m_nHour = m_nHour[n];
		i.m_nMinute = m_nMinute[n];
		i.m_nSecond = m_nSecond[n];
		i.m_openState = m_openState;
		return i;	
	}

	boolean concernDate(CurrentDateInfo currentDate)
	{
		int nHour = currentDate.getHour();
		int nMinute = currentDate.getMinute();
		int nSecond = currentDate.getSecond();
		if(nHour > m_nHour[0] || (nHour == m_nHour[0] && nMinute > m_nMinute[0]) || (nHour == m_nHour[0] && nMinute == m_nMinute[0] && nSecond >= m_nSecond[0]))
		{
			if(nHour < m_nHour[1] || (nHour == m_nHour[1] && nMinute < m_nMinute[1]) || (nHour == m_nHour[1] && nMinute == m_nMinute[1] && nSecond < m_nSecond[1]))
				return true;
		}
		return false;
	}
	
	CalendarOpenState getOpenState()
	{
		return m_openState;
	}
	
	String getAsString()
	{
		String cs = m_nHour[0]+":"+m_nMinute[0]+":"+m_nSecond[0] + " -> " + m_nHour[1]+":"+m_nMinute[1]+":"+m_nSecond[1];
		return cs;
	}
	
	void setCalendarAtEnd(Calendar cal)
	{
		cal.set(Calendar.HOUR, m_nHour[1]);
		cal.set(Calendar.MINUTE, m_nMinute[1]);
		cal.set(Calendar.SECOND, m_nSecond[1]);
	}
	
	private CalendarOpenState m_openState = CalendarOpenState.Unknown;
	int m_nHour[] = null;
	int m_nMinute[] = null;
	int m_nSecond[] = null;
}
