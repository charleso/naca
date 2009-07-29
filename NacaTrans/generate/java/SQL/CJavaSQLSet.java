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
/**
 * 
 */
package generate.java.SQL;

import parser.Cobol.elements.SQL.SQLSetDateTimeType;
import generate.CBaseLanguageExporter;
import semantic.CDataEntity.CDataEntityType;
import semantic.SQL.CEntitySQLCursor;
import semantic.SQL.CEntitySQLSet;
import utils.CObjectCatalog;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CJavaSQLSet extends CEntitySQLSet
{
	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaSQLSet(int nLine, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(nLine, cat, out);
	}

	public CDataEntityType GetDataType()
	{
		return null;
	}

	public String ExportReference(int nLine)
	{
		return FormatIdentifier(GetName());
	}

	public boolean isValNeeded()
	{
		return false;
	}
	
	protected void DoExport()
	{
		
		String cs = "" ;
		if(m_sqlSetType == SQLSetDateTimeType.Date)
			cs = "SQLSetCurrentDate(" ;
		else if(m_sqlSetType == SQLSetDateTimeType.Time)
			cs = "SQLSetCurrentTime(" ;
		else if(m_sqlSetType == SQLSetDateTimeType.TimeStamp)
			cs = "SQLSetCurrentTimeStamp(" ;
		else
			cs = "ERROR_SQLSetCurrentDateTime(" ;
		
		cs += m_Terminal.ExportReference(getLine());
		cs += ") ;";
		WriteWord(cs);
		WriteEOL();
	}

}
