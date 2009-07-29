/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.mathSupport;

import java.math.BigDecimal;

import jlib.misc.NumberParser;

import nacaLib.varEx.VarAndEdit;

public class MathPower extends MathBase
{
	public MathPower(VarAndEdit var1, int n)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		m_d = m_d.pow(n);
	}
	
	public MathPower(int n, VarAndEdit vPow)
	{
		m_d = new BigDecimal(n);
		
		String sPow = vPow.getDottedSignedString();
		int nPow = NumberParser.getAsInt(sPow);

		m_d = m_d.pow(nPow);
	}

}
