/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaTrans - Naca Transcoder v1.2.0.beta.1
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic.expression;

import generate.CBaseLanguageExporter;
import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import semantic.CDataEntity.CDataEntityType;
import utils.CObjectCatalog;

public abstract class CEntityBoolean extends CDataEntity
{

	protected boolean m_bValue = false ;
	
	public CEntityBoolean(CObjectCatalog cat, CBaseLanguageExporter out, boolean bValue)
	{
		super(0, "", cat, out);
		m_bValue = bValue;
	}
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.BOOLEAN;
	}
	public boolean HasAccessors()
	{
		return false;
	}
	public String ExportWriteAccessorTo(String value)
	{
		// unused
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
	public CBaseEntityCondition GetSpecialCondition(int nLine, String value, CBaseEntityCondition.EConditionType type, CBaseEntityFactory factory)
	{
		return null ;
	}
}

