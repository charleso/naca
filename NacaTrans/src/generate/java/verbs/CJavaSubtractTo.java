/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Aug 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntitySubtractTo;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSubtractTo extends CEntitySubtractTo
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaSubtractTo(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	protected void DoExport()
	{
		if (!m_Destination.isEmpty())
		{
			String cs = "subtract(" + m_Variable.ExportReference(getLine()) ;
			for(CDataEntity value : m_Values)
			{
				cs += ", " + value.ExportReference(getLine()) ;
			}
			cs += ")" ;
			WriteWord(cs);
			for(CDataEntity value : m_Destination)
			{
				WriteWord(".to(" + value.ExportReference(getLine()) + ")") ;
			}
			WriteWord(" ;");
		} 
		else if(m_Values.size() == 1)
		{
			CDataEntity m_Value = m_Values.get(0);
			if (m_Value.GetConstantValue().equals("1"))
			{
				String cs = "dec(" + m_Variable.ExportReference(getLine()) + ") ;" ;
				WriteLine(cs) ;
			}
			else  if (m_Value.GetConstantValue().equals("-1"))
			{
				String cs = "inc(" + m_Variable.ExportReference(getLine()) + ") ;" ;
				WriteLine(cs) ;
			}
			else
			{
				String cs = "dec(" + m_Value.ExportReference(getLine()) + ", " + m_Variable.ExportReference(getLine()) +") ;" ;
				WriteWord(cs) ;
			}
		} 
		else
		{
			String cs = "dec(" ;
			for(CDataEntity m_Value : m_Values)
			{
				cs += m_Value.ExportReference(getLine()) + ", " ;
			}
			cs += m_Variable.ExportReference(getLine()) +") ;" ;
			WriteWord(cs) ;
		}
		WriteEOL() ;
	}
}
