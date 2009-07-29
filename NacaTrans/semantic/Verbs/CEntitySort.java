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
package semantic.Verbs;

import java.util.Vector;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import semantic.CEntityFileDescriptor;
import semantic.CEntityProcedure;
import utils.CObjectCatalog;

public abstract class CEntitySort extends CBaseActionEntity
{

	public CEntitySort(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	public void setFileDesriptor(CEntityFileDescriptor fileDesc)
	{
		m_FileDescriptor = fileDesc ;
	}
	
	protected CEntityFileDescriptor m_FileDescriptor = null ;
	protected class CEntitySortKey
	{
		public CDataEntity m_Key = null;
		public boolean m_bAscending = false ;
	}
	protected Vector<CEntitySortKey> m_arrSortKey = new Vector<CEntitySortKey>() ;
	
	public void AddKey(boolean ascending, CDataEntity key)
	{
		CEntitySortKey sk = new CEntitySortKey() ;
		sk.m_bAscending = ascending ;
		sk.m_Key = key ;
		m_arrSortKey.add(sk) ;
	}

	protected CEntityFileDescriptor m_fdInputFile = null ;
	protected CEntityFileDescriptor m_fdOutputFile = null ;
	protected CEntityProcedure m_pInputProcedure = null ;
	protected CEntityProcedure m_pOutputProcedure = null ;
	
	public void setInputFile(CEntityFileDescriptor input)
	{
		m_fdInputFile = input ;		
	}

	public void setInputProcedure(CEntityProcedure proc)
	{
		m_pInputProcedure = proc ;
	}

	public void setOutputFile(CEntityFileDescriptor output)
	{
		m_fdOutputFile = output ;
	}

	public void setOutputProcedure(CEntityProcedure proc)
	{
		m_pOutputProcedure = proc ;
	}

	protected String m_csOutputProcedureName = null ;
	public void setOutputProcedure(String string)
	{
		m_csOutputProcedureName = string ;	
	}
	
	protected String m_csInputProcedureName = null ;
	public void setInputProcedure(String string)
	{
		m_csInputProcedureName = string ;	
	}
	
	
	


}
