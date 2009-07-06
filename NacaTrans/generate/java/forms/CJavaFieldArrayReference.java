/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Oct 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.forms;

import generate.CBaseLanguageExporter;
import semantic.expression.CBaseEntityExpression;
import semantic.forms.CEntityFieldArrayReference;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaFieldArrayReference extends CEntityFieldArrayReference
{
	/**
	 * @param l
	 * @param cat
	 * @param out
	 */
	public CJavaFieldArrayReference(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}
	public String ExportReference(int nLine)
	{
		String out = "" ;
		if (m_Reference != null && m_arrIndexes != null)
		{
			CBaseEntityExpression exp = m_arrIndexes.get(0) ;
			out = m_Reference.ExportReference(getLine()) + ".getAt(" + exp.Export();
			for (int i=1; i<m_arrIndexes.size();i++)
			{
				exp = m_arrIndexes.get(i) ;
				out += ", " + exp.Export();
			} 
			out += ")" ;
		}
		return out ;
	}
	public boolean HasAccessors()
	{
		return false;
	}
	protected void DoExport()
	{
		// unused
	}
	public String ExportWriteAccessorTo(String value)
	{
		//unsued
		return "" ; 
	}
	public boolean isValNeeded()
	{
		return true;
	}


	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetDataType()
	 */
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.FIELD ;
	}
}
