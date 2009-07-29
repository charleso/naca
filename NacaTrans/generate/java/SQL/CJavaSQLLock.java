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
package generate.java.SQL;

import generate.CBaseLanguageExporter;
import semantic.SQL.CEntitySQLLock;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id$
 */
public class CJavaSQLLock extends CEntitySQLLock
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaSQLLock(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/**
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	@Override
	protected void DoExport()
	{
		//sql("LOCK TABLE RS3151 IN EXCLUSIVE MODE").onErrorGoto(p_Erreur);
		WriteWord("sql(\"LOCK TABLE "+m_csTableName+" IN EXCLUSIVE MODE\")");
		String csSQLErrorWarningStatement = m_ProgramCatalog.getSQLWarningErrorStatement();
		if(csSQLErrorWarningStatement != null)
		{
			WriteWord(csSQLErrorWarningStatement);
		}
		WriteWord(" ;") ;
		WriteEOL() ;
	}

}
