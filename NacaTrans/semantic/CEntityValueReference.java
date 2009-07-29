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
package semantic;

import generate.CBaseLanguageExporter;
import utils.CEntityHierarchy;
import utils.CObjectCatalog;

public class CEntityValueReference extends CBaseDataReference
{


	public CEntityValueReference(CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity ref)
	{
		super(0, "", cat, out);
		m_Reference = ref ;
	}

	@Override
	public CDataEntityType GetDataType()
	{
		return m_Reference.GetDataType();
	}

	@Override
	public String ExportReference(int nLine)
	{
		return m_Reference.ExportReference(getLine()) ;
	}

	@Override
	public boolean HasAccessors()
	{
		return m_Reference.HasAccessors();
	}

	@Override
	public String ExportWriteAccessorTo(String value)
	{
		return m_Reference.ExportWriteAccessorTo(value);
	}

	@Override
	public boolean isValNeeded()
	{
		return m_Reference.isValNeeded() ;
	}

	@Override
	public String GetConstantValue()
	{
		return m_Reference.GetConstantValue() ;
	}

	@Override
	protected void DoExport()
	{
		m_Reference.DoExport() ;
	}
	public String GetName()
	{
		return m_Reference.GetName() ;
	}
	
	@Override
	public CBaseLanguageEntity GetParent()
	{
		return m_Reference.GetParent() ;
	}
	
	@Override
	public CEntityHierarchy GetHierarchy()
	{
		return m_Reference.GetHierarchy() ;
	}
}
