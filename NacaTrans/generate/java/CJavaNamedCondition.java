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
 * Created on 9 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.CEntityNamedCondition;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaNamedCondition extends CEntityNamedCondition
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaNamedCondition(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#ExportReference(semantic.CBaseLanguageExporter)
	 */
	public String ExportReference(int nLine)
	{
		String cs = "" ;
		if (m_Of != null)
		{
			cs = m_Of.ExportReference(getLine()) + "." ;
		}
		cs += FormatIdentifier(GetName()) ;
		return cs ;		
	}
	public boolean HasAccessors()
	{
		return false;
	}
	protected void DoExport()
	{
		WriteWord("Cond "+ FormatIdentifier(GetName()) + " = declare.condition()")  ;
		for (int i=0; i<m_arrValues.size();i++)
		{
			CDataEntity e = m_arrValues.get(i);
			if (e == null)
			{
				WriteWord(".value([undefined])");
			}
			else
			{
				WriteWord(".value(" + e.ExportReference(getLine()) + ")");
			}
		}
		for (int i=0; i<m_arrStartIntervals.size() && i<m_arrEndIntervals.size();i++)
		{
			CDataEntity e1 = m_arrStartIntervals.get(i);
			CDataEntity e2 = m_arrEndIntervals.get(i);
			WriteWord(".value(" + e1.ExportReference(getLine())+ ", "+ e2.ExportReference(getLine()) + ")");
		}
		WriteWord(".var() ;");
		WriteEOL();
	}
	public String ExportWriteAccessorTo(String value)
	{
		// unsued
		return "" ;
	}
	public boolean isValNeeded()
	{
		return false;
	}

	public CDataEntityType GetDataType()
	{
		return CDataEntityType.CONDITION ;
	}

}
