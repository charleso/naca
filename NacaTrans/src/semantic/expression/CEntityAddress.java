/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic.expression;


import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import utils.CObjectCatalog;

public abstract class CEntityAddress extends CDataEntity
{

	public CEntityAddress(CObjectCatalog cat, CBaseLanguageExporter out, String address)
	{
		super(0, "", cat, out);
		m_csAddress = address ;
	}

	@Override
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.ADDRESS ;
	}

	protected String m_csAddress ="" ;

	@Override
	public boolean HasAccessors()
	{
		return false;
	}

	@Override
	public String ExportWriteAccessorTo(String value)
	{
		return null;
	}

	@Override
	public boolean isValNeeded()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String GetConstantValue()
	{
		return m_csAddress;
	}


	@Override
	protected void DoExport()
	{
		// unused
	}

}
