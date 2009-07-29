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
 * Created on Aug 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
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
		if (m_Destination != null)
		{
			String cs = "subtract(" + m_Variable.ExportReference(getLine()) + ", " + m_Value.ExportReference(getLine()) + ")" ;
			WriteWord(cs);
			WriteWord(".to(" + m_Destination.ExportReference(getLine()) + ") ;") ;
		} 
		else
		{
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
		WriteEOL() ;
	}
}
