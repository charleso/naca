/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils.FPacTranscoder;

import java.util.Hashtable;
import java.util.Set;
import java.util.Map.Entry;

import semantic.CEntityFileBuffer;
import semantic.CEntityFileDescriptor;
import utils.FPacTranscoder.notifs.*;
import jlib.engine.BaseNotificationHandler;

public class DefaultFileManager extends BaseNotificationHandler
{

	private String m_csDefaultInputFile = "" ;
	private String m_csDefaultOutputFile = "" ;
	private Hashtable<String, CEntityFileBuffer> m_tabInputFiles = new  Hashtable<String, CEntityFileBuffer>() ;
	private Hashtable<String, CEntityFileBuffer> m_tabOutputFiles = new  Hashtable<String, CEntityFileBuffer>() ;
	private Hashtable<String, CEntityFileBuffer> m_tabUpdateFiles = new  Hashtable<String, CEntityFileBuffer>() ;
	private Hashtable<CEntityFileDescriptor, Boolean> m_tabOpenFiles = new Hashtable<CEntityFileDescriptor, Boolean>() ;
	private Hashtable<CEntityFileDescriptor, Boolean> m_tabCloseFiles = new Hashtable<CEntityFileDescriptor, Boolean>() ;
	
	public boolean onRegisterInputFile(NotifRegisterInputFile notif) 
	{
		m_tabInputFiles.put(notif.id, notif.fileBuffer) ;
		m_tabInputFiles.put(notif.fileBuffer.GetFileDescriptor().GetName(), notif.fileBuffer) ;
		m_tabOpenFiles.put(notif.fileBuffer.GetFileDescriptor(), Boolean.FALSE) ;
		m_tabCloseFiles.put(notif.fileBuffer.GetFileDescriptor(), Boolean.FALSE) ;
		return true ;
	}
	public boolean onRegisterOutputFile(NotifRegisterOutputFile notif) 
	{
		m_tabOutputFiles.put(notif.id, notif.fileBuffer) ;
		m_tabOutputFiles.put(notif.fileBuffer.GetFileDescriptor().GetName(), notif.fileBuffer) ;
		m_tabOpenFiles.put(notif.fileBuffer.GetFileDescriptor(), Boolean.FALSE) ;
		m_tabCloseFiles.put(notif.fileBuffer.GetFileDescriptor(), Boolean.FALSE) ;
		return true ;
	}
	public boolean onRegisterUpdateFile(NotifRegisterUpdateFile notif) 
	{
		m_tabUpdateFiles.put(notif.id, notif.fileBuffer) ;
		m_tabUpdateFiles.put(notif.fileBuffer.GetFileDescriptor().GetName(), notif.fileBuffer) ;
		m_tabOpenFiles.put(notif.fileBuffer.GetFileDescriptor(), Boolean.FALSE) ;
		m_tabCloseFiles.put(notif.fileBuffer.GetFileDescriptor(), Boolean.FALSE) ;
		return true ;
	}
	public boolean onGetDefaultInputFile(NotifGetDefaultInputFile notif)
	{
		if (!m_csDefaultInputFile.equals(""))
		{
			notif.fileBuffer = m_tabInputFiles.get(m_csDefaultInputFile) ;
			return true ;
		}
		else if (m_tabInputFiles.size() == 1 || m_tabInputFiles.size() == 2)  // two entries per file
		{
			notif.fileBuffer = m_tabInputFiles.elements().nextElement() ;
			return true ;
		}
		else if (m_tabInputFiles.size() == 1 || m_tabUpdateFiles.size() == 2) // two entries per file
		{
			notif.fileBuffer = m_tabUpdateFiles.elements().nextElement() ;
			return true ;
		}
		else
		{
			return false ;
		}
	}
	public boolean onGetDefaultOutputFile(NotifGetDefaultOutputFile notif)
	{
		if (!m_csDefaultOutputFile.equals(""))
		{
			notif.fileBuffer = m_tabOutputFiles.get(m_csDefaultOutputFile) ;
			return true ;
		}
		else if (m_tabOutputFiles.size() == 1 || m_tabOutputFiles.size() == 2) // two entries per file
		{
			notif.fileBuffer = m_tabOutputFiles.elements().nextElement() ;
			return true ;
		}
		else if (m_tabUpdateFiles.size() == 1 || m_tabUpdateFiles.size() == 2) // two entries per file
		{
			notif.fileBuffer = m_tabUpdateFiles.elements().nextElement() ;
			return true ;
		}
		else
		{
			return false ;
		}
	}
	
	public boolean onSetDefaultOutputFile(NotifSetDefaultOutputFile notif)
	{
		m_csDefaultOutputFile = notif.fileRef ;
		return true ;
	}
	
	public boolean onSetDefaultInputFile(NotifSetDefaultInputFile notif)
	{
		m_csDefaultInputFile = notif.fileRef ;
		return true ;
	}

//	public boolean onRegisterFileGet(NotifRegisterFileGet notif)
//	{
//		if (notif.readFile != null)
//		{
//			m_bHasExplicitFileGet = true ;
//		}
//		return true ;
//	}
//	protected boolean m_bHasExplicitFileGet = false ;
//	public boolean onHasExplicitFileGet(NotifHasExplicitFileGet notif)
//	{
//		notif.hasExplicitFileGet = m_bHasExplicitFileGet ;
//		return true ;
//	}
	
	public boolean onRegisterOpenFile(NotifRegisterFileOpen notif)
	{
		m_tabOpenFiles.put(notif.m_FileDesc, Boolean.TRUE) ;
		return true ;
	}
	public boolean onRegisterCloseFile(NotifRegisterFileClose notif)
	{
		m_tabCloseFiles.put(notif.m_FileDesc, Boolean.TRUE) ;
		return true ;
	}
	
	public boolean onGetAllFilesNotOpen(NotifGetAllFilesNotOpen notif)
	{
		Set<Entry<CEntityFileDescriptor, Boolean>> set = m_tabOpenFiles.entrySet();
		for (Entry<CEntityFileDescriptor, Boolean> entry : set)
		{
			if (!entry.getValue())
			{
				notif.m_arrFiles.add(entry.getKey()) ;
			}
		}
		return true ;
	}
	public boolean onGetAllFilesNotClosed(NotifGetAllFilesNotClosed notif)
	{
		Set<Entry<CEntityFileDescriptor, Boolean>> set = m_tabCloseFiles.entrySet();
		for (Entry<CEntityFileDescriptor, Boolean> entry : set)
		{
			if (!entry.getValue())
			{
				notif.m_arrFiles.add(entry.getKey()) ;
			}
		}
		return true ;
	}
	
}
