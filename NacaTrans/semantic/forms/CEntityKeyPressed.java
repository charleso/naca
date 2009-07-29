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
 * Created on Oct 18, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.forms;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CBaseDataReference;
import semantic.CDataEntity;
import semantic.expression.CBaseEntityCondExpr;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityKeyPressed extends CDataEntity
{
	public void RegisterReadingAction(CBaseActionEntity act)
	{
		super.RegisterReadingAction(act);
		m_ProgramCatalog.addImportDeclaration("KEYPRESSED") ;
	}
	public void RegisterReadReference(CBaseDataReference ent)
	{
		m_ProgramCatalog.addImportDeclaration("KEYPRESSED") ;
		super.RegisterReadReference(ent);
	}
	public void RegisterValueAccess(CBaseEntityCondExpr cond)
	{
		m_ProgramCatalog.addImportDeclaration("KEYPRESSED") ;
		super.RegisterValueAccess(cond);
	}
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityKeyPressed(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out);
	}

	public CDataEntityType GetDataType()
	{
		return CDataEntityType.CONSOLE_KEY ;
	}

	protected void DoExport()
	{
		// unused
	}
	public boolean ignore()
	{
		return false ;
	}
	public String GetConstantValue()
	{
		return "" ;
	} 	 
}
