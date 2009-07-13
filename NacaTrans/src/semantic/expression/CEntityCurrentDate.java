/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Sep 29, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.expression;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityCurrentDate extends CBaseEntityFunction
{
	/**
	 * @param cat
	 * @param out
	 * @param data
	 */
	public CEntityCurrentDate(CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(cat, out, null);
	}
	public void RegisterReadingAction(CBaseActionEntity act)
	{
		m_arrActionsReading.add(act) ;
	}
	public void RegisterValueAccess(CBaseEntityCondExpr cond)
	{
		m_arrAccessAsValue.add(cond) ;
	}
	public void RegisterVarTesting(CBaseEntityCondition cond)
	{
		m_arrTestsAsVar.add(cond) ;
	}
	public void RegisterWritingAction(CBaseActionEntity act)
	{
		m_arrActionsWriting.add(act) ;
	}
}
