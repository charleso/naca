/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

public abstract class CEntityInspectConverting extends CBaseActionEntity
{
	public CEntityInspectConverting(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	
	public void Clear()
	{
		super.Clear() ;
		m_Variable = null;
	}
	
	public boolean ignore()
	{
		return m_Variable.ignore();
	}
	
	public void SetConvert(CDataEntity var)
	{
		m_Variable = var;
	}
	
	public void SetFrom(CDataEntity var)
	{
		m_From = var;
	}
	
	public void SetTo(CDataEntity var)
	{
		m_To = var;
	}
	
	protected CDataEntity m_Variable = null ;
	protected CDataEntity m_From = null ;
	protected CDataEntity m_To = null ;
}
