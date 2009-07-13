/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic.expression;

import semantic.CDataEntity;

/**
 * @author S. Charton
 * @version $Id: CEntityConstant.java,v 1.1 2006/03/14 20:46:36 U930CV Exp $
 */
public abstract class CEntityConstant extends CDataEntity
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityConstant(Value val)
	{
		super(0, "", null, null);
		m_eValue = val ;
	}

	/**
	 * @see semantic.CDataEntity#GetDataType()
	 */
	@Override
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.CONSTANT ;
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


	protected Value m_eValue ;
	
	public enum Value 
	{
		HIGH_VALUE, SPACES ;
	}
}
