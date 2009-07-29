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
 * Created on 4 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.SQL;

import generate.CBaseLanguageExporter;
import generate.java.SQL.SQLErrorType;
import semantic.CBaseActionEntity;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntitySqlOnErrorGoto extends CBaseActionEntity
{

	/**
	 * @param cat
	 * @param out
	 */
	public CEntitySqlOnErrorGoto(int l, CObjectCatalog cat, CBaseLanguageExporter out, String Reference, SQLErrorType errorType)	// , boolean OnWarning)
	{
		super(l, cat, out);
		m_csRef = Reference ; 
		//m_bOnWarning = OnWarning ;
		m_errorType = errorType; 
	}
	
	protected String m_csRef = "" ;
	//protected boolean m_bOnWarning = false ;
	protected SQLErrorType m_errorType = null;
	public boolean ignore()
	{
		return false ;
	}
}
