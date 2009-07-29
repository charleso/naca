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


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class OpenCalendarWeek
{
	OpenCalendarWeek()
	{
		m_tRangesOfDays = new OpenCalendarRanges[8]; 
	}
	
	void addRange(int nDayOfWeek, String csType, String csMin, String csMax)
	{		
		CalendarOpenState openState = CalendarOpenState.AppClosed;
		if(csType.equalsIgnoreCase("Open"))
			openState = CalendarOpenState.AppOpened;
		
		OpenCalendarRanges rangesOfDay = m_tRangesOfDays[nDayOfWeek];
		if(rangesOfDay == null)
		{
			rangesOfDay = new OpenCalendarRanges();
			m_tRangesOfDays[nDayOfWeek] = rangesOfDay;
		}
		
		OpenCalendarRange range = new OpenCalendarRange();
		range.set(openState, csMin, csMax);

		rangesOfDay.addRange(range);
	}
	
	void generateSortedIntervals()
	{
		for(int nDayOfWeek=0; nDayOfWeek<8; nDayOfWeek++)
		{
			if(m_tRangesOfDays[nDayOfWeek] == null)	// No definition for day: It's closed all day long 
			{
				OpenCalendarRanges rangesOfDay = new OpenCalendarRanges();
				m_tRangesOfDays[nDayOfWeek] = rangesOfDay;
				rangesOfDay.setCloseAllDay();
			}
			else	// Merge definitions
			{
				OpenCalendarRanges rangesOfDay = m_tRangesOfDays[nDayOfWeek];
				rangesOfDay.sortIntervals();
			}			
		}
	}
	
	CalendarOpenState getOpenState(CalendarCacheManager cacheManager, boolean bCacheState)
	{
		int nDayOfWeek = cacheManager.getCurrentDayOfWeek();
		
		OpenCalendarRanges rangesOfDay = m_tRangesOfDays[nDayOfWeek];
		return rangesOfDay.getOpenState(cacheManager, bCacheState);		
	}
	
	private OpenCalendarRanges[] m_tRangesOfDays = null;
}

