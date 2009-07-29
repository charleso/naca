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
package semantic.SQL;

import generate.CBaseLanguageExporter;

import java.util.Vector;

import semantic.CBaseActionEntity;
import utils.CObjectCatalog;

import com.sun.org.apache.xml.internal.utils.StringVector;

public abstract class CEntitySQLInsertStatement extends CBaseActionEntity
{
	public CEntitySQLInsertStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	
	public void SetInsert(CEntitySQLDeclareTable table, Vector arrVal, Vector arrIndicators, boolean bSingleIndicatorInOccurs)
	{
		m_table = table ;
		m_arrValues = arrVal;
		m_arrIndicators = arrIndicators; 
		m_bSingleIndicatorInOccurs = bSingleIndicatorInOccurs;
		if(this.getLine() == 4984)
		{
			int gg = 0;
		}
	}
	public void SetInsert(String tableName, StringVector arrColumns, Vector arrVal, Vector arrIndicators, boolean bSingleIndicatorInOccurs)
	{
		m_csTable = tableName;
		ASSERT(arrColumns) ;
		m_arrCollumns = arrColumns ;
		ASSERT(arrVal) ;
		m_arrValues = arrVal ;
		if(this.getLine() == 1313)
		{
			int gg = 0;
		}
		m_arrIndicators = arrIndicators;
		m_bSingleIndicatorInOccurs = bSingleIndicatorInOccurs;
	}
	public void SetInsert(String tablename, String clause, Vector arrParam)
	{
		m_csTable = tablename ;
		m_SelectClause = clause ;
		m_arrSelectParameters = arrParam ;	
	}
	
	public void setSessionTable(boolean bSessionTable)
	{
		m_bSessionTable = bSessionTable;
	}
	
	protected String m_csTable = "" ;
	protected boolean m_bSessionTable = false;
	protected CEntitySQLDeclareTable m_table = null ;
	protected StringVector m_arrCollumns = null;
	protected Vector m_arrValues = null;
	protected Vector m_arrIndicators = null;
	protected boolean m_bSingleIndicatorInOccurs = false;
	protected String m_SelectClause = "" ;
	protected Vector m_arrSelectParameters = null ;
	
	public void Clear()
	{
		super.Clear();
		if (m_table != null)
		{
			m_table.Clear() ;
		}
		if (m_arrValues != null)
		{
			m_arrValues.clear() ;
		}
		if (m_arrSelectParameters != null)
		{
			m_arrSelectParameters.clear() ;
		}
	}
	public boolean ignore()
	{
		return false ;
	}	
	
}
