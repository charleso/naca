/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic.expression;

import semantic.CDataEntity;

public abstract class CBaseEntityCondExpr extends CDataEntity
{
//	public CBaseEntityCondExpr(int nLine)
//	{
//		super(nLine);
//	}

	@Override
	public String ExportReference(int nLine)
	{
		return Export();
	}
	@Override
	public String ExportWriteAccessorTo(String value)
	{
		return null;
	}

	@Override
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.EXPRESSION ;
	}
	@Override
	public boolean HasAccessors()
	{
		return false;
	}
	@Override
	public boolean isValNeeded()
	{
		return false;
	}
	public CBaseEntityCondExpr()
	{
		super(0, "", null, null);
	}
	protected void RegisterMySelfToCatalog()
	{
		// unused
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		// 
		return false ;
	}
	public abstract String Export() ;
	protected void DoExport()
	{
		String cs = Export() ;
		WriteWord(cs);
	}
	public CBaseEntityCondition getAsCondition()
	{
		return null;
	}
	
	@Override
	public String GetConstantValue()
	{
		return null;
	}
}
