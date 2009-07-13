/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 4 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.CICS;

import java.util.Vector;

import com.sun.org.apache.xml.internal.utils.StringVector;


import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;
import utils.CobolTranscoder.Notifs.NotifDeclareUseCICSPreprocessor;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityCICSAssign extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCICSAssign(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}
	
	public void AddRequest(String param, CDataEntity var)
	{
		m_arrParameters.addElement(param);
		m_arrVariables.add(var) ;
	}
	
	protected StringVector m_arrParameters = new StringVector() ;
	protected Vector<CDataEntity> m_arrVariables = new Vector<CDataEntity>() ;

	public boolean ignore()
	{
		boolean ignore = true ;
		for (int i=0; i<m_arrParameters.size(); i++)
		{
			CDataEntity e = m_arrVariables.elementAt(i);
			ignore &= e.ignore() ;
		}
		return ignore;
	}
	
	public void Clear()
	{
		super.Clear();
		m_arrVariables.clear() ;
	}

}
