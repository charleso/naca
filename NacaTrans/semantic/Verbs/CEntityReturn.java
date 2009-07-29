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
 * Created on 5 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityReturn extends CBaseActionEntity
{

	/**
	 * @param cat
	 * @param out
	 */
	public CEntityReturn(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	public void SetStopProgram()
	{
		m_bStopAllStackCalls = true;
	}
	
	public void SetOnlyReturnFromProcedure()
	{
		m_bonlyLeaveParagraph = true;
	}
	
	protected boolean m_bStopAllStackCalls = false ;
	protected boolean m_bonlyLeaveParagraph = false ;
	public boolean ignore()
	{
		return false ;
	}
	public boolean hasExplicitGetOut()
	{
		return true ;
	}
}
