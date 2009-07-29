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
package utils.CobolTranscoder;

import utils.CobolTranscoder.Notifs.*;
import jlib.engine.BaseNotificationHandler;

public class SpecialCobolActionNotifHandler extends BaseNotificationHandler
{
	private boolean m_bUseCICSPreprocessor = false ;
	
	
	public boolean OnUseCICSPreprocessor(NotifDeclareUseCICSPreprocessor notif)
	{
		m_bUseCICSPreprocessor = true ;
		return true ;
	}
	
	public boolean OnIsUsedCICSPRe(NotifIsUsedCICSPreprocessor notif)
	{
		notif.m_bUsed = m_bUseCICSPreprocessor ;
		return true ;
	}
}
