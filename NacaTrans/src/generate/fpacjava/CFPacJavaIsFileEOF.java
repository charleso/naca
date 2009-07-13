/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.java.expressions.CJavaCondNot;
import semantic.CEntityFileDescriptor;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CEntityIsFileEOF;

/**
 * @author S. Charton
 * @version $Id: CFPacJavaIsFileEOF.java,v 1.2 2007/06/28 06:19:46 u930bm Exp $
 */
public class CFPacJavaIsFileEOF extends CEntityIsFileEOF
{

	/**
	 * @param fb 
	 * 
	 */
	public CFPacJavaIsFileEOF(CEntityFileDescriptor fb)
	{
		super(fb);
	}

	/**
	 * @see semantic.expression.CBaseEntityCondition#GetPriorityLevel()
	 */
	@Override
	public int GetPriorityLevel()
	{
		return 7;
	}

	/**
	 * @see semantic.expression.CBaseEntityCondition#GetOppositeCondition()
	 */
	@Override
	public CBaseEntityCondition GetOppositeCondition()
	{
		CJavaCondNot not = new  CJavaCondNot();
		not.SetCondition(this) ;
		return not;
	}

	/**
	 * @see semantic.expression.CBaseEntityCondExpr#Export()
	 */
	@Override
	public String Export()
	{
		return "isEof("+m_FileDescriptor.ExportReference(getLine())+")" ;
	}

}
