/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 6 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CSubStringAttributReference;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSubStringReference extends CSubStringAttributReference
{

	/**
	 * @param l
	 * @param cat
	 * @param out
	 */
	public CJavaSubStringReference(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#ExportReference(semantic.CBaseLanguageExporter)
	 */
	public String ExportReference(int nLine)
	{
//		if (m_Reference.HasAccessors())
//		{
			String cs = "subString(" + m_Reference.ExportReference(getLine()) ;
			cs += ", " + m_Start.Export();
			if(m_Length != null)
				cs += ", " + m_Length.Export();
			cs += ")" ;
			return cs ;
//		}
//		else
//		{
//			String cs = m_Reference.ExportReference(getLine()) ;
//			cs += ".subString(" + m_Start.Export() + ", " + m_Length.Export() + ")" ;
//			return cs ;
//		}		
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#HasAccessors()
	 */
	public boolean HasAccessors()
	{
		return true;
	}
	protected void DoExport()
	{
		// unused
	}
	public String ExportWriteAccessorTo(String value)
	{
		String cs = m_Reference.ExportReference(getLine()) ;
		cs = "setSubString(" + cs + ", " + m_Start.Export();
		if (m_Length != null)
			cs += ", " + m_Length.Export();
		cs += ", " + value ;
		cs += ") ;" ;
		return cs ;		
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
		return CDataEntityType.VAR ;
	}



}
