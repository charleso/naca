/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 5 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.forms;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.forms.CEntityFieldAttribute;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for thi
s generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaFieldAttribute extends CEntityFieldAttribute
{

	/**
	 * @param name
	 * @param cat
	 * @param out
	 * @param type
	 */
	public CJavaFieldAttribute(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity owner)
	{
		super(l, name, cat, out, owner);
	}
	public String ExportReference(int nLine)
	{
		return "getAttribute(" + m_Reference.ExportReference(getLine()) + ")" ;
	}
	protected void DoExport()
	{
		// unsued
	}
	public boolean HasAccessors()
	{
		return false;
	}
	public String ExportWriteAccessorTo(String value)
	{
		//return "moveAttribute(" + m_RefField.ExportReference(getLine()) + ", " ;
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
		return CDataEntityType.FIELD_ATTRIBUTE ;
	}


}
