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
 * @version $Id: CalendarOpenState.java,v 1.2 2006/05/08 10:38:06 cvsadmin Exp $
 */
public class CalendarOpenState
{
	public static final CalendarOpenState Unknown = new CalendarOpenState(0, false, false, false);
	public static final CalendarOpenState AppClosed = new CalendarOpenState(1, true, false, false);
	public static final CalendarOpenState AppManuallyClosed = new CalendarOpenState(2, true, false, true);
	public static final CalendarOpenState AppOpened = new CalendarOpenState(3, true, true, false);
	
	private CalendarOpenState(int nId, boolean bKnown, boolean bOpen, boolean bManual)
	{
		m_nId = nId;
		m_bKnown = bKnown;
		m_bOpen = bOpen;
		m_bManual = bManual;
	}
	
	public boolean isKnown()
	{
		return m_bKnown;
	}
	
	public boolean isOpen()
	{
		return m_bOpen;
	}
	
	public String getString()
	{
		if(m_bKnown)
		{
			if(m_bOpen)
				return "Ouvert / geöffnet / aperto";
			else
			{
				if(m_bManual)
					return "Fermé manuellement / Manuell geschlossen / Chiuso manualmente ";
				return "Fermé / geschlossen / chiuso";
			}
		}
		return "Unknown";
	}
	
	public int getId()
	{
		return m_nId;
	}

	private int m_nId = 0;
	private boolean m_bKnown = false;
	private boolean m_bOpen = false;
	private boolean m_bManual = false;
}
