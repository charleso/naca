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

import jlib.misc.SortableItem;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CalendarInstant extends SortableItem
{
	CalendarOpenState m_openState = CalendarOpenState.Unknown;
	int m_nHour = 0;
	int m_nMinute = 0;
	int m_nSecond = 0;
	
	public int compare(SortableItem item)
	{
		CalendarInstant i = (CalendarInstant)item;
		if(m_nHour == i.m_nHour && m_nMinute == i.m_nMinute && m_nSecond == i.m_nSecond)
			return 0;
		else if(m_nHour < i.m_nHour || (m_nHour == i.m_nHour && m_nMinute < i.m_nMinute) || (m_nHour == i.m_nHour && m_nMinute == i.m_nMinute && m_nSecond == i.m_nSecond))
			return -1;
		return 1;
	}
}
