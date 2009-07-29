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

import java.util.Vector;

import semantic.CEntityFileDescriptor;

import jlib.engine.BaseNotification;

/**
 * @author S. Charton
 * @version $Id$
 */
public class NotifGetAllFilesNotOpen extends BaseNotification
{

	public Vector<CEntityFileDescriptor> m_arrFiles = new Vector<CEntityFileDescriptor>() ;

}
