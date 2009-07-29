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
package generate.fpacjava;

import semantic.expression.CEntityExprSum;

public class CFPacExprSum extends CEntityExprSum
{

	public CFPacExprSum()
	{
		super();
	}

	@Override
	public String Export()
	{
		return m_Op1.ExportReference(getLine()) + "+" + m_Op2.ExportReference(getLine()) ;
	}

}
