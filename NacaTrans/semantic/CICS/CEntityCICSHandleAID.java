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
/*
 * Created on 1 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.CICS;


import com.sun.org.apache.xml.internal.utils.StringVector;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import utils.CObjectCatalog;
import utils.CobolTranscoder.Notifs.NotifDeclareUseCICSPreprocessor;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityCICSHandleAID extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCICSHandleAID(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}
	public void HandleAID(String cond, String label)
	{
		m_arrHandledAIDLabels.addElement(label);
		m_arrHandledAIDs.addElement(cond);		
	}
	public void UnhandleAID(String cond)
	{
		m_arrUnhandledAIDs.addElement(cond);		
	}
	
	protected StringVector m_arrHandledAIDs = new StringVector();
	protected StringVector m_arrUnhandledAIDs = new StringVector();
	protected StringVector m_arrHandledAIDLabels = new StringVector();

	public boolean ignore()
	{
		if (m_arrHandledAIDs.size() == 0 && m_arrUnhandledAIDs.size() == 0)
		{
			return true;
		}
		return false ;
	}
}
