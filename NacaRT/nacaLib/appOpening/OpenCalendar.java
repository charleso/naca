/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
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

import jlib.xml.Tag;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class OpenCalendar
{
	OpenCalendar()
	{
	}
		
	void loadDefinition(String csFile)
	{
		m_csFile = csFile;
		Tag tag = Tag.createFromFile(csFile);
		if(tag != null)
		{
			Tag tagDay = tag.getEnumChild("Day");
			while(tagDay != null)
			{
				String csDayValue = tagDay.getVal("Value");
				int nDayOfWeekId = getDayOfWeeekId(csDayValue);
				loadDayRange(nDayOfWeekId, tagDay);
				tagDay = tag.getEnumChild();
			}
			
			Tag tagDate = tag.getEnumChild("Date");
			while(tagDate != null)
			{
				String csDateValue = tagDate.getVal("Value");
				Integer iDate = getDate(csDateValue);
				loadDateRange(iDate, tagDate);
				tagDate = tag.getEnumChild();
			}
		}
		
		if(m_week != null)
			m_week.generateSortedIntervals();

		if(m_rangesForDates != null)
			m_rangesForDates.generateSortedIntervals();
	}
	
	void reloadDefinition()
	{
		m_week = null;
		m_rangesForDates = null;
		loadDefinition(m_csFile);
	}
	
	private void loadDayRange(int nDayOfWeeekId, Tag tagDay)
	{
		Tag tagOpenRange = tagDay.getEnumChild("Open");
		while(tagOpenRange != null)
		{
			String csMin = tagOpenRange.getVal("Min");
			String csMax = tagOpenRange.getVal("Max");
			addRange(nDayOfWeeekId, "Open", csMin, csMax);
			tagOpenRange = tagDay.getEnumChild();
		}
	}
		
	private void addRange(int nDayId, String csType, String csMin, String csMax)
	{
		if(m_week == null)
			m_week = new OpenCalendarWeek();
		m_week.addRange(nDayId, csType, csMin, csMax);
	}
	
	private int getDayOfWeeekId(String csDayValue)
	{
		csDayValue = csDayValue.toUpperCase();
		if(csDayValue.startsWith("MON"))
			return Calendar.MONDAY;
		else if(csDayValue.startsWith("TUE"))
			return Calendar.TUESDAY;
		else if(csDayValue.startsWith("WED"))
			return Calendar.WEDNESDAY;
		else if(csDayValue.startsWith("THU"))
			return Calendar.THURSDAY;
		else if(csDayValue.startsWith("FRI"))
			return Calendar.FRIDAY;
		else if(csDayValue.startsWith("SAT"))
			return Calendar.SATURDAY;
		return Calendar.SUNDAY;
	}
	
	private Integer getDate(String csDateValue)
	{
		int n = csDateValue.indexOf("/");
		String csDay = csDateValue.substring(0, n);
		
		csDateValue = csDateValue.substring(n+1);
		n = csDateValue.indexOf("/");
		String csMonth = csDateValue.substring(0, n);
		
		csDateValue = csDateValue.substring(n+1);
		
		Integer iDate = new Integer(csDateValue + csMonth + csDay);
		return iDate;
	}
	

	private void loadDateRange(Integer iDate, Tag tagDate)
	{
		Tag tagRange = tagDate.getEnumChild();
		while(tagRange != null)
		{
			String csName = tagRange.getName();
			String csMin = tagRange.getVal("Min");
			String csMax = tagRange.getVal("Max");
			addDateRange(iDate, csName, csMin, csMax);
			tagRange = tagDate.getEnumChild();
		}
	}
	
	private void addDateRange(Integer iDate, String csType, String csMin, String csMax)
	{
		if(csType.equalsIgnoreCase("Close") || csType.equalsIgnoreCase("Open"))
		{
			if(m_rangesForDates == null)
				m_rangesForDates = new OpenCalendarRangesForDates();
			
			CalendarOpenState state = CalendarOpenState.AppOpened; 
			if(csType.equalsIgnoreCase("Close"))
				state = CalendarOpenState.AppClosed;
			m_rangesForDates.addDateRange(iDate, state, csMin, csMax);
		}
	}

	CalendarOpenState getOpenState(CalendarCacheManager cacheManager, boolean bCacheState)
	{
		if(m_rangesForDates != null)	// Check individual dates
		{
			CalendarOpenState state = m_rangesForDates.getOpenState(cacheManager, bCacheState);
			if(state.isKnown())
				return state;
		}
		
		if(m_week != null)
			return m_week.getOpenState(cacheManager, bCacheState);
		return CalendarOpenState.Unknown;
	}
	
	private String m_csFile = null;
	private OpenCalendarWeek m_week = null;
	private OpenCalendarRangesForDates m_rangesForDates = null;
}
