/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Dec 21, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.expressions;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaConstantValue extends CDataEntity
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaConstantValue(CObjectCatalog cat, CBaseLanguageExporter out, String val)
	{
		super(0, "", cat, out);
		m_Value = val ;
	}

	public CDataEntityType GetDataType()
	{
		return CDataEntityType.CONSTANT ;
	}

	public String ExportReference(int nLine)
	{
		return m_Value ;
	}

	public boolean HasAccessors()
	{
		return false;
	}

	public String ExportWriteAccessorTo(String value)
	{
		return null;
	}

	public String GetConstantValue()
	{
		return m_Value;
	}
	protected String m_Value = "" ;

	protected void DoExport()
	{
		// unused
		
	}
	public boolean isValNeeded()
	{
		return false;
	}
}
