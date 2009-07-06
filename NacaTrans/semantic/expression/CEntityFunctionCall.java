/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic.expression;

import generate.CBaseLanguageExporter;

import java.util.Vector;


import semantic.CDataEntity;
import utils.CObjectCatalog;


public abstract class CEntityFunctionCall extends CBaseEntityFunction
{
	public CEntityFunctionCall(CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity data)
	{
		super(cat, out, data);
	}
	protected String m_csFunction = "" ;
	protected Vector<CDataEntity> m_arrParameters = new Vector<CDataEntity>() ;

	public void CallFunction(String function)
	{
		m_csFunction = function ;
	}
	public void AddParameter(CDataEntity e)
	{
		m_arrParameters.add(e) ;
	}

	public boolean ignore()
	{
		return m_Reference.ignore() ;
	}

}
