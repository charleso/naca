/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 18 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.expressions;

import semantic.CDataEntity;
import semantic.expression.CEntityExprTerminal;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaExprTerminal extends CEntityExprTerminal
{

	/**
	 * @param cat
	 * @param out
	 * @param term
	 */
	public CJavaExprTerminal(CDataEntity term)
	{
		super(term);
	}
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

}
