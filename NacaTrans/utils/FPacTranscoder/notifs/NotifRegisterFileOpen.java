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
package utils.FPacTranscoder.notifs;

import semantic.CEntityFileDescriptor;
import jlib.engine.BaseNotification;

/**
 * @author S. Charton
 * @version $Id$
 */
public class NotifRegisterFileOpen extends BaseNotification
{
	public CEntityFileDescriptor m_FileDesc = null ;
}
