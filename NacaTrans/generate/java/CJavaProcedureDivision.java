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
 * Created on Oct 11, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.CEntityProcedureDivision;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaProcedureDivision extends CEntityProcedureDivision
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaProcedureDivision(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected void DoExport()
	{
		if (m_arrCallParameters.size() > 0)
		{
			String line = "ParamDeclaration callParameters = declare" ;
			WriteWord(line);
			for (int i=0; i<m_arrCallParameters.size(); i++)
			{
				CDataEntity e = m_arrCallParameters.get(i);
				WriteWord(".using(" + e.ExportReference(getLine())+")");
			}
			WriteWord(";");
			WriteEOL() ;
		}
		if (m_ProcedureBloc != null)
		{
			WriteLine("public void procedureDivision() {");
			DoExport(m_ProcedureBloc) ;
			WriteLine("}");
		}		
	}
}
