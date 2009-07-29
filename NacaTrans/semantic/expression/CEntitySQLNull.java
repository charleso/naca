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

import generate.CBaseLanguageExporter;
import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import semantic.CDataEntity.CDataEntityType;
import utils.CObjectCatalog;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public abstract class CEntitySQLNull extends CDataEntity
{
	public CEntitySQLNull(CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(0, "", cat, out);
	}
	
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.NUMBER;
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
		return "NULL";
	} 	 
	public CBaseEntityCondition GetSpecialCondition(int nLine, String value, CBaseEntityCondition.EConditionType type, CBaseEntityFactory factory)
	{
		return null ;
	}
}
