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

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: OpenCalendarRangesForDates.java,v 1.2 2006/05/23 11:34:31 u930cv Exp $
 */
public class OpenCalendarRangesForDates
{
	OpenCalendarRangesForDates()
	{
	}
	
	void addDateRange(Integer iDate, CalendarOpenState state, String csMin, String csMax)
	{
		if(m_hashCalendarRangesByDate == null)
			m_hashCalendarRangesByDate = new Hashtable<Integer, OpenCalendarRanges>();
		OpenCalendarRanges ranges = m_hashCalendarRangesByDate.get(iDate);
		if(ranges == null)
		{
			ranges = new OpenCalendarRanges();
			m_hashCalendarRangesByDate.put(iDate, ranges);
		}
		ranges.addRange(state, csMin, csMax);
	}
	
	void generateSortedIntervals()
	{
		if(m_hashCalendarRangesByDate != null)
		{
			Collection<OpenCalendarRanges> col = m_hashCalendarRangesByDate.values();
			Iterator<OpenCalendarRanges> iter = col.iterator();
			while(iter.hasNext())
			{
				OpenCalendarRanges ranges = iter.next();
				ranges.sortIntervals();
			}
		}
	}
	
	CalendarOpenState getOpenState(CalendarCacheManager cacheManager, boolean bCacheState)
	{
		Integer iDate = cacheManager.getCurrentDateAsIntegerYYYYMMDD();
		OpenCalendarRanges ranges = m_hashCalendarRangesByDate.get(iDate);
		if(ranges != null)	// Ranges have been defined at this date
			return ranges.getOpenState(cacheManager, bCacheState);
		
		if(bCacheState)
			cacheManager.setCurrentOpenStateUnknown();
		return CalendarOpenState.Unknown;
	}
	
	private Hashtable<Integer, OpenCalendarRanges> m_hashCalendarRangesByDate = null;
}
