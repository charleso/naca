/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import semantic.CDataEntity;
import semantic.expression.CEntityExprTerminal;

public class CFPacJavaExprTerminal extends CEntityExprTerminal
{

	public CFPacJavaExprTerminal(CDataEntity term)
	{
		super(term);
	}

	@Override
	public String Export()
	{
		if (m_Term != null)
		{
			return m_Term.ExportReference(getLine());
		}
		else
		{
			return "[UNDEFINED]";
		}	
	}
	
	public String toString()
	{
		return m_Term.toString() ;
	}
	

}
