/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 17 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntityRoutineEmulationCall;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaRoutineEmulationCall extends CEntityRoutineEmulationCall
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaRoutineEmulationCall(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		WriteWord(m_csDisplay + "(") ;
		boolean bDynamicAllocation = false;
		if (m_csDisplay.equals("tools.dynamicAllocation")) {
			bDynamicAllocation = true;
			WriteWord("new Var[] {");
		}
		boolean bFirstArg = true ;
		for (int i=0; i<m_arrParameters.size(); i++)
		{
			String cs = "" ;
			CDataEntity e = m_arrParameters.get(i) ;
			if (e == null)
			{
				cs = "[UNDEFINED]" ;
			}
			else if (!e.ignore())
			{
				cs = e.ExportReference(getLine()) ;
			}
			if (!cs.equals(""))
			{
				if (bFirstArg)
				{
					bFirstArg = false ;
				}
				else
				{
					WriteWord(", ") ;
				}
				WriteWord(cs) ;				
			}
		}
		if (bDynamicAllocation) {
			WriteWord("}");
		}
		WriteWord(") ;") ;
		WriteEOL() ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#ignore()
	 */
	public boolean ignore()
	{
		return m_csDisplay.equals(""); 
	}

}
