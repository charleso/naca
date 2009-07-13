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
import semantic.CBaseDataReference;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CBaseEntityFunction extends CBaseDataReference
{
	//protected CDataEntity m_DataRef = null ;
	public void Clear()
	{
		super.Clear() ;
		m_Reference = null ;
	}
	
	public CBaseEntityFunction(CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity data)
	{
		super(0, "", cat, out);
		m_Reference = data ;
	}
	public CDataEntityType GetDataType()
	{
		if (m_Reference != null)
		{
			return m_Reference.GetDataType();
		}
		else
		{
			return null ;
		}
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#HasAccessors()
	 */
	public boolean HasAccessors()
	{
		return false;
	}
	public String ExportWriteAccessorTo(String value)
	{
		// nothing
		return "" ;
	}
	protected void DoExport()
	{
		// nothing
	}
	public boolean ignore()
	{
		if (m_Reference != null)
		{
			return m_Reference.ignore() ;
		}
		else
		{
			return false ;
		}
	}
	public String GetConstantValue()
	{
		return "" ;
	} 	 
}
