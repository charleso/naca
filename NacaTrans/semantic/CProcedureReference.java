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
 * Created on 29 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CProcedureReference
{

	public CProcedureReference(String csProcedureName, String csSectionName, CObjectCatalog programCatalog)
	{
		m_csProcedureName = csProcedureName ;	
		m_csSectionName = csSectionName ;
		m_programCatalog = programCatalog ;		
	}
	public CEntityProcedure getProcedure()
	{
		return m_programCatalog.GetProcedure(m_csProcedureName, m_csSectionName) ;
	}
	protected String m_csProcedureName = "" ;
	protected String m_csSectionName = "" ;
	protected CObjectCatalog m_programCatalog = null ;
	public void Clear()
	{
		m_programCatalog = null ;
	}
	public String getProcedureName()
	{
		return m_csProcedureName;
	}
	
}
