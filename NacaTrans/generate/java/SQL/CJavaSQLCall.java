/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.java.SQL;

import generate.CBaseLanguageExporter;
import semantic.SQL.CEntitySQLCall;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id: CJavaSQLCall.java,v 1.2 2007/06/28 06:19:46 u930bm Exp $
 */
public class CJavaSQLCall extends CEntitySQLCall
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaSQLCall(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/**
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	@Override
	protected void DoExport()
	{
		WriteWord("sqlCall("+m_ProgramReference.ExportReference(getLine())+")") ;
		for (int i=0; i<m_arrParameters.size(); i++)
		{
			WriteWord(".param("+(i+1)+", "+m_arrParameters.get(i).ExportReference(getLine())+")");
		}
		String csSQLErrorWarningStatement = m_ProgramCatalog.getSQLWarningErrorStatement();
		if(csSQLErrorWarningStatement != null)
		{
			WriteWord(csSQLErrorWarningStatement);
		}
		WriteWord(" ;") ;
		WriteEOL() ;
	}

}
