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
		// PJReady start		
		String cs = m_Reference.ExportReference(getLine());
		if(m_Length == null)
		{
			cs += ".subStringFrom(" ;
			cs += m_Start.Export();
			cs += ")" ;
		}
		else
		{
			cs += ".subString(" ;
			cs += m_Start.Export();
			cs += ", ";
			cs += m_Length.Export() + ")" ;
		}
		return cs ;
		// PJReady end
		/* Old code: 
//		if (m_Reference.HasAccessors())
//		{
			String cs = "subString(" + m_Reference.ExportReference(getLine()) ;
			cs += ", " + m_Start.Export() + ", " + m_Length.Export() + ")" ;
			return cs ;
//		}
//		else
//		{
//			String cs = m_Reference.ExportReference(getLine()) ;
//			cs += ".subString(" + m_Start.Export() + ", " + m_Length.Export() + ")" ;
//			return cs ;
//		}		
		 */
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
		String csRef = m_Reference.ExportReference(getLine()) ;
		// PJREADY: Start commented
		//String cs = "setSubString(" + csRef + ", " + m_Start.Export() + ", " + m_Length.Export() + ", " + value + ") ;" ;
		// PJREADY: End commented
		
		// PJREADY: Added
		String cs = null;
		if(m_Start != null && m_Length != null)
			cs = "move(" + value + ", " + csRef + ".subString(" + m_Start.Export() + ", " + m_Length.Export() + ")) ;" ;
		else if(m_Start != null && m_Length == null)
			cs = "move(" + value + ", " + csRef + ".subStringFrom(" + m_Start.Export() + ")) ;" ;
		else
			cs = "move(" + value + ", " + csRef + ") ;";
		return cs;
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
