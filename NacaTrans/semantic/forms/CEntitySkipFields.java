/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Aug 17, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.forms;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import generate.CBaseLanguageExporter;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntitySkipFields extends CEntityResourceField
{
	protected int m_nbFields = 0 ;
	protected String m_csLevel = "" ;
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntitySkipFields(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, int nbFields, String level)
	{
		super(l, name, cat, out);
		if (name.equals(""))
		{
			name = GetDefaultName() ;
			if (!name.equals(""))
			{
				SetName(name) ;
			}
		}
		m_nbFields = nbFields ;
		m_csLevel = level ;
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetDataType()
	 */
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.FIELD ;
	}
	public boolean ignore()
	{
		return false ;
	}
	public String GetConstantValue()
	{
		return "" ;
	} 	 

	public boolean IsEntryField()
	{
		return false;
	}


	public String GetTypeDecl()
	{
		return null;
	}
	public Element DoXMLExport(Document doc, CResourceStrings res)
	{
		return null;
	}
	protected void RegisterMySelfToCatalog()
	{
		String name = GetName() ;
		m_ProgramCatalog.RegisterDataEntity(name, this) ;
	}
}
