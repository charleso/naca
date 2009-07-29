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
 * Created on 19 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntitySetConstant;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSetConstant extends CEntitySetConstant
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaSetConstant(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	protected void DoExport()
	{
		String cs = "" ;
		if (m_CsteValue != null)
		{
			cs = "moveAll(" + m_CsteValue.ExportReference(getLine()) + ", " + m_Variable.ExportReference(getLine()) + ") ;" ;			
		}
		else 
		{
			// Before PJReady
//			cs = "move" ;
//			if (m_SubStringRefLength != null && m_SubStringRefStart != null)
//			{
//				cs += "SubString" ;
//			}
//			if (m_bSetToLowValue)
//			{
//				cs += "LowValue(";
//			}
//			else if (m_bSetToHighValue)
//			{
//				cs += ("HighValue(");
//			}
//			else if (m_bSetToSpace)
//			{
//				cs += ("Space(");
//			}
//			else if (m_bSetToZero)
//			{
//				cs += ("Zero(");
//			}
//			else if (m_bSetToTrue)
//			{
//				cs += ("True(");
//			}
//			else if (m_bSetToFalse)
//			{
//				cs += ("False(");
//			}
//			cs += m_Variable.ExportReference(getLine()) ;
//			if (m_SubStringRefStart != null && m_SubStringRefLength != null)
//			{
//				cs += ", " + m_SubStringRefStart.Export() + ", " + m_SubStringRefLength.Export();
//			}
//			cs += ") ;" ;
			
			// After PJReady
			cs = "move" ;
			if (m_bSetToLowValue)
			{
				cs += "LowValue(";
			}
			else if (m_bSetToHighValue)
			{
				cs += ("HighValue(");
			}
			else if (m_bSetToSpace)
			{
				cs += ("Space(");
			}
			else if (m_bSetToZero)
			{
				cs += ("Zero(");
			}
			else if (m_bSetToTrue)
			{
				cs += ("True(");
			}
			else if (m_bSetToFalse)
			{
				cs += ("False(");
			}
			cs += m_Variable.ExportReference(getLine()) ;
			if (m_SubStringRefStart != null && m_SubStringRefLength != null)
			{
//				cs += ", " + m_SubStringRefStart.Export() + ", " + m_SubStringRefLength.Export();
				cs += ".subString(" ;
				cs += m_SubStringRefStart.Export();
				cs += ", ";
				cs += m_SubStringRefLength.Export() + ")" ;
			}
			cs += ") ;" ;
		}
		WriteLine(cs);
	}

}
