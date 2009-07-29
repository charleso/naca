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
package semantic.expression;

import parser.CIdentifier;
import generate.CBaseLanguageExporter;
import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import semantic.CDataEntity.CDataEntityType;
import utils.CObjectCatalog;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: CEntityInsertSQLFunction.java,v 1.1 2009/05/25 11:33:22 u930di Exp $
 */
public abstract class CEntityInsertSQLFunction extends CDataEntity
{
	protected CIdentifier m_id = null;
	protected String m_csFormat = null;
	
	public CEntityInsertSQLFunction(CObjectCatalog cat, CBaseLanguageExporter out, CIdentifier id, String csFormat)
	{
		super(0, "", cat, out);
		m_id = id;
		m_csFormat = csFormat;
	}
	
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.SQL_FUNCTION_WITH_PARAMETER;
	}
	
	public boolean HasAccessors()
	{
		return false;
	}
	public String ExportWriteAccessorTo(String value)
	{
		// unsued
		return "" ;
	}
	protected void DoExport()
	{
		// unused
	}
	public boolean ignore()
	{
		return false ;
	}
	public String GetConstantValue()
	{
		return null;
	} 	 
	public CBaseEntityCondition GetSpecialCondition(int nLine, String value, CBaseEntityCondition.EConditionType type, CBaseEntityFactory factory)
	{
		return null ;
	}
}
