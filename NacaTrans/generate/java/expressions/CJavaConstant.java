/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.java.expressions;

import semantic.expression.CEntityConstant;

/**
 * @author S. Charton
 * @version $Id: CJavaConstant.java,v 1.2 2007/06/28 06:19:48 u930bm Exp $
 */
public class CJavaConstant extends CEntityConstant
{

	/**
	 * @param val
	 */
	public CJavaConstant(Value val)
	{
		super(val);
	}

	/**
	 * @see semantic.CDataEntity#ExportReference(getLine())
	 */
	@Override
	public String ExportReference(int nLine)
	{
		switch (m_eValue)
		{
			case HIGH_VALUE :
				return "CobolConstant.HighValue" ;
			case SPACES :
				return "CobolConstant.Spaces" ;
			default:
				return "[Undefined]" ;
		}
	}

	/**
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	@Override
	protected void DoExport()
	{
		// unused
	}

}
