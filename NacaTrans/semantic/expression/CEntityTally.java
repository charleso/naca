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

import semantic.CDataEntity;

public abstract class CEntityTally extends CDataEntity
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityTally()
	{
		super(0, "", null, null);
	}

	/**
	 * @see semantic.CDataEntity#GetDataType()
	 */
	@Override
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.CONSTANT;
	}

	/**
	 * @see semantic.CDataEntity#HasAccessors()
	 */
	@Override
	public boolean HasAccessors()
	{
		return false;
	}

	/**
	 * @see semantic.CDataEntity#ExportWriteAccessorTo(java.lang.String)
	 */
	@Override
	public String ExportWriteAccessorTo(String value)
	{
		return null;
	}

	/**
	 * @see semantic.CDataEntity#isValNeeded()
	 */
	@Override
	public boolean isValNeeded()
	{
		return false;
	}

	/**
	 * @see semantic.CDataEntity#GetConstantValue()
	 */
	@Override
	public String GetConstantValue()
	{
		return null;
	}
}