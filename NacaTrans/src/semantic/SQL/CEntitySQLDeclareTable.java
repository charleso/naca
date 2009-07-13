/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 20 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.SQL;
import generate.CBaseLanguageExporter;

import java.util.ArrayList;

import parser.Cobol.elements.SQL.CSQLTableColDescriptor;

import semantic.CBaseActionEntity;
import utils.CObjectCatalog;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public abstract class CEntitySQLDeclareTable extends CBaseActionEntity
{
	public CEntitySQLDeclareTable(int line, CObjectCatalog cat, CBaseLanguageExporter out, String csTableName, String csViewName, ArrayList arrTableColDescription)
	{
		super(line, cat, out);
		m_csViewName = csViewName ;
		m_csTableName = csTableName;
		if (csTableName == null)
		{
			int n=0; 
		}
		m_arrTableColDescription = arrTableColDescription;
		m_ProgramCatalog.RegisterSQLTable(m_csViewName, this);
	}
	protected String m_csTableName = "";
	protected String m_csViewName = "" ;
	protected ArrayList m_arrTableColDescription = null;
	public void Clear()
	{
		super.Clear();
		m_arrTableColDescription.clear() ;
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#RegisterMySelfToCatalog()
	 */

	public String ExportColReferences()
	{
		String out = "" ;
		for (int i=0; i<m_arrTableColDescription.size();i++)
		{
			CSQLTableColDescriptor desc = (CSQLTableColDescriptor)m_arrTableColDescription.get(i);
			if (!out.equals(""))
			{
				out += ", " ;
			}
			out += desc.GetName();
		}
		return out;
	}
	public String ExportColReferences(String alias)
	{
		String out = "" ;
		for (int i=0; i<m_arrTableColDescription.size();i++)
		{
			CSQLTableColDescriptor desc = (CSQLTableColDescriptor)m_arrTableColDescription.get(i);
			if (!out.equals(""))
			{
				out += ", " ;
			}
			out += alias+"."+desc.GetName();
		}
		return out;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#GetName()
	 */
	public String GetTableName()
	{
		return m_csTableName ; 
	}

	public int GetNbCols()
	{
		return m_arrTableColDescription.size();
	}
	public boolean ignore()
	{
		return false ;
	}
	/**
	 * @return
	 */
	public String GetViewName()
	{
		return m_csViewName ;
	}
	public String GetName()
	{
		return m_csViewName ;
	}

}
