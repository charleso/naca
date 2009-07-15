/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */

package nacaLib.mathSupport;

import java.math.BigDecimal;

import nacaLib.varEx.VarAndEdit;

public class MathPow extends MathBase
{
	/**
	 * @param Var var1
	 * @param String s, treated as a number 
	 * Set current object to var1^s (var is treated as numeric optionally decimals / signed)
	 */
	public MathPow(VarAndEdit var1, String s)
	{
		m_d = new BigDecimal(Math.pow(Double.parseDouble(var1
				.getDottedSignedString()), Double.parseDouble(s)));
	}

}
