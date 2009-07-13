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
 * @version $Id: NotifRegisterFileOpen.java,v 1.1 2006/05/03 05:39:50 cvsadmin Exp $
 */
public class NotifRegisterFileOpen extends BaseNotification
{
	public CEntityFileDescriptor m_FileDesc = null ;
}
