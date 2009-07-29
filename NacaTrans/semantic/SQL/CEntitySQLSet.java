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
package semantic.SQL;

import parser.Cobol.elements.SQL.SQLSetDateTimeType;
import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public abstract class CEntitySQLSet extends CBaseActionEntity
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntitySQLSet(int nLine, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(nLine, cat, out);
	}
	
	public boolean HasAccessors()
	{
		return false;
	}
	public String ExportWriteAccessorTo(String value)
	{
		return "" ;
	}
	public String GetConstantValue()
	{
		return "" ;
	}
	
	public void SetTerminal(CDataEntity terminal)
	{
		m_Terminal = terminal;
	}
	
	public void SetSQLSetDateTimeType(SQLSetDateTimeType sqlSetType)
	{
		m_sqlSetType = sqlSetType;
	}
	
	protected CDataEntity m_Terminal = null ;
	protected SQLSetDateTimeType m_sqlSetType = null;
}