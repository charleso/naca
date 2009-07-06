/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 11 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.forms;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.forms.CEntityFieldLength;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaFieldLength extends CEntityFieldLength
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 * @param owner
	 */
	public CJavaFieldLength(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity owner)
	{
		super(l, name, cat, out, owner);
	}
	public String ExportReference(int nLine)
	{
//		return "getLength(" + m_RefField.ExportReference(getLine()) + ")" ;
		return m_Reference.ExportReference(getLine()) ;
	}
	protected void DoExport()
	{
		// unsued
	}
	public boolean HasAccessors()
	{
		return true;
	}
	public String ExportWriteAccessorTo(String value)
	{
		return "moveLength(" +value +", " + m_Reference.ExportReference(getLine())  + ") ;" ;		
	}
	public boolean isValNeeded()
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetDataType()
	 */
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.FIELD ;
	}

}
