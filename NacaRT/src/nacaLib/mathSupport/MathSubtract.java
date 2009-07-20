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

public class MathSubtract extends MathBase
{
	/**
	 * @param VarAndEdit var1
	 * @param VarAndEdit var2
	 * Set current object to var1-var2 (var are treated as numeric optionnaly decimals / signed)
	 */
	public MathSubtract(VarAndEdit var1, VarAndEdit var2)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(s1);

		String s2 = var2.getDottedSignedString();
		BigDecimal val2 = new BigDecimal(s2);
		
		m_d = m_d.subtract(val2);
	}

	public MathSubtract(int a, int n)
	{
		String s1 = String.valueOf(a);
		m_d = new BigDecimal(s1);
		
		BigDecimal val2 = new BigDecimal(String.valueOf(n));
		m_d = m_d.subtract(val2);
	}
	
	public MathSubtract(int n, MathBase mathBase)
	{
		String s1 = String.valueOf(n) ;
		m_d = new BigDecimal(s1);
		
		m_d = m_d.subtract(mathBase.m_d);
	}		


	/**
	 * @param VarAndEdit var1
	 * @param int n
	 * Set current object to var1-n
	 */	
	public MathSubtract(VarAndEdit var1, int n)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		BigDecimal val2 = new BigDecimal(String.valueOf(n));
		m_d = m_d.subtract(val2);
	}

	/**
	 * @param VarAndEdit var1
	 * @param int n
	 * Set current object to var1-n
	 */	
	public MathSubtract(VarAndEdit var1)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(s1);
	}
	
	/**
	 * @param VarAndEdit var1
	 * @param int n
	 * Set current object to var1-n
	 */	
	public MathSubtract(int n)
	{
		m_d = new BigDecimal(String.valueOf(n));
	}

	/**
	 * @param int n
	 * @param VarAndEdit var1
	 * Set current object to n-var1
	 */
	public MathSubtract(int n, VarAndEdit var1)
	{
		m_d = new BigDecimal(String.valueOf(n));

		String s1 = var1.getDottedSignedString();
		BigDecimal val2 = new BigDecimal(s1);
		
		m_d = m_d.subtract(val2);
	}
	
	/**
	 * @param VarAndEdit var1
	 * @param double d
	 * Set current object to var1-d
	 */
	public MathSubtract(VarAndEdit var1, double d)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		BigDecimal val2 = new BigDecimal(d);
		m_d = m_d.subtract(val2);
	}
	
	/**
	 * @param double d
	 * @param VarAndEdit var1
	 * Set current object to d-var1
	 */
	public MathSubtract(double d, VarAndEdit var1)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(d);
		
		BigDecimal val2 = new BigDecimal(s1);
		m_d = m_d.subtract(val2);
	}

	/**
	 * @param VarAndEdit var1
	 * @param String s, treated as a number
	 * Set current object to var1-s
	 */
	public MathSubtract(VarAndEdit var1, String s)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		BigDecimal val2 = new BigDecimal(s);
		m_d = m_d.subtract(val2);
	}

	/**
	 * @param String s, treated as a number
	 * @param VarAndEdit var1
	 * Set current object to s-var1
	 */
	public MathSubtract(String s, VarAndEdit var1)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(s);
		
		BigDecimal val2 = new BigDecimal(s1);
		m_d = m_d.subtract(val2);
	}
	
	/**
	 * @param VarAndEdit var1
	 * @param MathBase mathBase
	 * Set current object to var1-mathBase
	 */
	public MathSubtract(VarAndEdit var1, MathBase mathBase)
	{
		String s1 = var1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		m_d = m_d.subtract(mathBase.m_d);
	}		
	
	/**
	 * @param MathBase mathBase
	 * @param VarAndEdit var1
	 * Set current object to mathBase-var1 
	*/
	public MathSubtract(MathBase mathBase, VarAndEdit var1)
	{
		//m_d = mathBase.m_d;
		setWithMathBase(mathBase);

		String s1 = var1.getDottedSignedString();
		BigDecimal val1 = new BigDecimal(s1);
		
		m_d = m_d.subtract(val1);
	}

	/**
	 * @param MathBase mathBase
	 * @param int n
	 * Set current object to mathBase-n
	*/
	public MathSubtract(MathBase mathBase, int n)
	{
		//m_d = mathBase.m_d;
		setWithMathBase(mathBase);
		
		BigDecimal val2 = new BigDecimal(String.valueOf(n));
		m_d = m_d.subtract(val2);
	}
	
	/**
	 * @param MathBase mathBase
	 * @param double d
	 * Set current object to mathBase-d 
	*/
	public MathSubtract(MathBase mathBase, double d)
	{
		//m_d = mathBase.m_d;
		setWithMathBase(mathBase);
		
		BigDecimal val2 = new BigDecimal(d);
		m_d = m_d.subtract(val2);
	}

	/**
	 * @param MathBase mathBase
	 * @param String s
	 * Set current object to mathBase-s 
	*/
	public MathSubtract(MathBase mathBase, String s)
	{
		//m_d = mathBase.m_d;
		setWithMathBase(mathBase);
		
		BigDecimal val2 = new BigDecimal(s);
		m_d = m_d.subtract(val2);
	}
	
	public MathSubtract(String s, MathBase mathBase)
	{
		m_d = new BigDecimal(s).subtract(mathBase.m_d);
	}
	
	/**
	 * @param MathBase mathBase1
	 * @param MathBase mathBase2
	 * Set current object to mathBase1-mathBase2 
	*/
	public MathSubtract(MathBase mathBase1, MathBase mathBase2)
	{
		//m_d = mathBase1.m_d;
		setWithMathBase(mathBase1);
		
		m_d = m_d.subtract(mathBase2.m_d);
	}	

	/**
	 * @param VarAndEdit var
	 * @return this
	 * Set current object to var-current objet
	 */
	public MathSubtract from(VarAndEdit var)
	{
		String cs = var.getDottedSignedString();
		BigDecimal val = new BigDecimal(cs);
		m_d = val.subtract(m_d);
		//m_d = val ;
		to(var) ; // store value in var.
		return this ;
	}

}
