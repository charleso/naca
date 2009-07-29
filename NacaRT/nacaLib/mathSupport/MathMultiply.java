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
/*
 * Created on 15 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author PJD
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.mathSupport;

import java.math.BigDecimal;

import nacaLib.varEx.VarAndEdit;

public class MathMultiply extends MathBase
{
	/**
	 * @param Var var1
	 * @param Var var2
	 * Set current object to var1*var2 (var are treated as numeric optionnaly decimals / signed)
	 */
	public MathMultiply(VarAndEdit var1, VarAndEdit var2)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		String s2 = var2.getDottedSignedString();
		BigDecimal val2 = new BigDecimal(s2);
		m_d = m_d.multiply(val2);
	}

	/**
	 * @param Var var1
	 * @param int n
	 * Set current object to var1*n (var are treated as numeric optionnaly decimals / signed)
	 */
	public MathMultiply(VarAndEdit var1, int n)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		BigDecimal val2 = new BigDecimal(String.valueOf(n));
		m_d = m_d.multiply(val2); 
	}
	
	/**
	 * @param int a
	 * @param int n
	 * Set current object to a*n
	 */
	public MathMultiply(int a, int n)
	{
		String s1 = String.valueOf(a);
		m_d = new BigDecimal(s1);
		
		BigDecimal val2 = new BigDecimal(String.valueOf(n));
		m_d = m_d.multiply(val2); 
	}

	/**
	 * @param Var var1
	 * @param double d
	 * Set current object to var1*d (var are treated as numeric optionnaly decimals / signed)
	 */	
	public MathMultiply(VarAndEdit var1, double d)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		BigDecimal val2 = new BigDecimal(d);
		m_d = m_d.multiply(val2); 
	}

	/**
	 * @param Var var1
	 * @param String s, treated as a number 
	 * Set current object to var1*s (var are treated as numeric optionnaly decimals / signed)
	 */
	public MathMultiply(VarAndEdit var1, String s)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		BigDecimal val2 = new BigDecimal(s);
		m_d = m_d.multiply(val2); 
	}
	
	/**
	 * @param Var var1
	 * @param MathBase mathBase
	 * Set current object to var1*mathBase (var are treated as numeric optionnaly decimals / signed)
	 */
	public MathMultiply(VarAndEdit var1, MathBase mathBase)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		m_d = m_d.multiply(mathBase.m_d);
	}	
	
	/**
	 * @param int n
	 * @param MathBase mathBase
	 * Set current object to n*mathBase
	 */
	public MathMultiply(int n, MathBase mathBase)
	{
		String s1 = String.valueOf(n);
		m_d = new BigDecimal(s1);
		
		m_d = m_d.multiply(mathBase.m_d);
	}	
	
	/**
	 * @param double d
	 * @param MathBase mathBase
	 * Set current object to d*mathBase
	 */	
	public MathMultiply(double d, MathBase mathBase)
	{
		m_d = new BigDecimal(d);
		m_d = m_d.multiply(mathBase.m_d);	
	}
	
	/**
	 * @param MathBase mathBase1
	 * @param MathBase mathBase2
	 * Set current object to mathBase1*mathBase2
	 */	
	public MathMultiply(MathBase mathBase1, MathBase mathBase2)
	{
		setWithMathBase(mathBase1);
		m_d = m_d.multiply(mathBase2.m_d);
	}	
	

	
	/**
	 * @param Var var
	 * @return this
	 * Set current object to current objet * var
	 */
	public MathMultiply multiply(VarAndEdit var)
	{
		String s = var.getDottedSignedString();
		
		BigDecimal val = new BigDecimal(s);
		m_d = m_d.multiply(val);
		return this;
	}	
	
	/**
	 * @param String s; treated as a number
	 * @return this
	 * Set current object to current objet * s
	 */
	public MathMultiply multiply(String s)
	{
		BigDecimal val = new BigDecimal(s);
		m_d = m_d.multiply(val);
		return this;
	}

	/**
	 * @param int n
	 * @return this
	 * Set current object to current objet * n
	 */
	public MathMultiply multiply(int n)
	{
		long l = n;
		m_d = m_d.multiply(BigDecimal.valueOf(l));
		return this;
	}

	/**
	 * @param double d
	 * @return this
	 * Set current object to current objet * d
	 */
	public MathMultiply multiply(double d)
	{
		BigDecimal val = new BigDecimal(d);
		m_d = m_d.multiply(val);
		return this;
	}
	
	/**
	 * @param MathBase mathBase
	 * @return this
	 * Set current object to current objet * mathBase
	 */
	public MathMultiply multiply(MathBase mathBase)
	{
		m_d = m_d.multiply(mathBase.m_d);
		return this;
	}	
	

}
