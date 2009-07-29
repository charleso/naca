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
public class OpenCalendarManager
{
	public final static int Standard = 0;
	public final static int Custom = 1;
	
	public OpenCalendarManager()
	{
		m_cacheManager = new CalendarCacheManager();
	}
	
	synchronized public void setReloadCalendarFiles()
	{
		m_cacheManager.flush();
		if(m_tCalendar == null)
			return ;
		if(m_tCalendar[Standard] != null)
			m_tCalendar[Standard].reloadDefinition();
		if(m_tCalendar[Custom] != null)
			m_tCalendar[Custom].reloadDefinition();
	}

	synchronized public void addCalendarDefinition(int nCalendardId, String csCalendarFilePath)
	{
		if(m_tCalendar == null)
			m_tCalendar = new OpenCalendar[2]; 

		OpenCalendar cal = new OpenCalendar();
		cal.loadDefinition(csCalendarFilePath);
		m_tCalendar[nCalendardId] = cal;
	}
	
	public boolean isServiceOpen()
	{
		CalendarOpenState state = getServiceOpenState();
		return state.isOpen();
	}
	
	synchronized public CalendarOpenState getAppCustomOpenState()
	{
		if(m_tCalendar == null)	// No def: Always open
		{
			m_cacheManager.setNoDefinition();
			return CalendarOpenState.AppOpened;
		}
		
		// Check custom calendar
		OpenCalendar cal = m_tCalendar[Custom];
		if(cal != null)
		{
			CalendarOpenState openState = cal.getOpenState(m_cacheManager, false);
			if(openState.isKnown())
				return openState;
		}
		return CalendarOpenState.Unknown;
	}
	
	synchronized public CalendarOpenState getAppStandardOpenState()
	{
		if(m_tCalendar == null)	// No def: Always open
		{
			m_cacheManager.setNoDefinition();
			return CalendarOpenState.AppOpened;
		}
		
		// No custom definition: See std def
		OpenCalendar cal = m_tCalendar[Standard];
		if(cal != null)
		{
			CalendarOpenState openState = cal.getOpenState(m_cacheManager, false);
			if(openState.isKnown())
				return openState;
			return CalendarOpenState.AppClosed;	// Missign standard def are same as closed 
		}
		// No standard def: open
		return CalendarOpenState.AppOpened;
	}
	
	synchronized public CalendarOpenState getServiceOpenState()
	{
		if(m_tCalendar == null)	// No def: Always open
		{
			m_cacheManager.setNoDefinition();
			return CalendarOpenState.AppOpened;
		}
		
		if(!m_cacheManager.mustCheckServiceOpenState())
			return m_cacheManager.getCurrentState();
		
		// Check custom calendar
		OpenCalendar cal = m_tCalendar[Custom];
		if(cal != null)
		{
			CalendarOpenState openState = cal.getOpenState(m_cacheManager, true);
			if(openState.isKnown())
			{
				return openState;
			}
		}
		
		// No custom definition: See std def
		cal = m_tCalendar[Standard];
		if(cal != null)
		{
			CalendarOpenState openState = cal.getOpenState(m_cacheManager, true);
			if(openState.isKnown())
				return openState;
			return CalendarOpenState.AppClosed;	// Missign standard def are same as closed 
		}
		
		// No standard def: open
		return CalendarOpenState.AppOpened;
	}
	
	synchronized public String getCurrentOpenCalendarRangeString()
	{
		return m_cacheManager.getCurrentOpenCalendarRangeString();
	}
	
	public void flushCalendarCache()
	{
		m_cacheManager.flush();
	}

	private OpenCalendar m_tCalendar[] = null;
	private CalendarCacheManager m_cacheManager = null; 
}
