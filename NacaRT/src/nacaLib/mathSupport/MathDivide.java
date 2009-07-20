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
import java.math.BigInteger;

import nacaLib.varEx.Var;
import nacaLib.varEx.VarAndEdit;

public class MathDivide extends MathBase
{
	private static int PRECISION = 10;

	/**
	 * @param Var var1
	 * @param Var var2
	 * Set current object to var1/var2 (var are treated as numeric optionnaly decimals / signed)
	 * if var2 = 0, then an error is set; it can be tested by chaining .isError() (member of base class MathBase)
	 */	 
	public MathDivide(VarAndEdit var1, VarAndEdit var2)
	{
		try
		{
			String s1 = var1.getDottedSignedString();
			m_d = new BigDecimal(s1);
	
			String s2 = var2.getDottedSignedString();
			BigDecimal val2 = new BigDecimal(s2);
			
			decimalDivide(val2);
			
			// m_d = m_d.divide(val2, PRECISION, BigDecimal.ROUND_HALF_UP);
		}
		catch (ArithmeticException e)
		{
			setError(true);
		}
	}

	/**
	 * @param Var var1
	 * @param int n
	 * Set current object to var1/n (var are treated as numeric optionnaly decimals / signed)
	 * if var2 = 0, then an error is set; it can be tested by chaining .isError() (member of base class MathBase)
	 */	 
	public MathDivide(int var1, int n)
	{
		try
		{
			m_d = new BigDecimal(String.valueOf(var1));
				
			BigDecimal val2 = new BigDecimal(String.valueOf(n));
			decimalDivide(val2);
			//m_d = m_d.divide(val2, PRECISION, BigDecimal.ROUND_HALF_UP);
		}
		catch (ArithmeticException e)
		{
			setError(true);
		}
	}
	
	public MathDivide(int n, VarAndEdit var1)
	{
		try
		{
			m_d = new BigDecimal(String.valueOf(n));

			String s1 = var1.getDottedSignedString();
			BigDecimal val2 = new BigDecimal(s1);

			decimalDivide(val2);
			//m_d = m_d.divide(val2, PRECISION, BigDecimal.ROUND_HALF_UP);
		}
		catch (ArithmeticException e)
		{
			setError(true);
		}
	}

	/**
	 * @param Var var1
	 * @param double d
	 * Set current object to var1/d (var are treated as numeric optionnaly decimals / signed)
	 * if var2 = 0, then an error is set; it can be tested by chaining .isError() (member of base class MathBase)
	 */	 
	public MathDivide(VarAndEdit var1, double d)
	{
		try
		{
			String s1 = var1.getDottedSignedString();
			m_d = new BigDecimal(s1);
			
			BigDecimal val2 = new BigDecimal(d);
			decimalDivide(val2);
			//m_d = m_d.divide(val2, PRECISION, BigDecimal.ROUND_HALF_UP);
		}	
		catch (ArithmeticException e)
		{
			setError(true);
		}
	}

	/**
	 * @param Var var1
	 * @param String cs; treated as a number
	 * Set current object to var1/cs
	 * if var2 = 0, then an error is set; it can be tested by chaining .isError() (member of base class MathBase)
	 */	 
	public MathDivide(VarAndEdit var1, String cs)
	{
		try
		{
			String s1 = var1.getDottedSignedString();
			m_d = new BigDecimal(s1);
			
			BigDecimal val2 = new BigDecimal(cs);
			decimalDivide(val2);
			//m_d = m_d.divide(val2, PRECISION, BigDecimal.ROUND_HALF_UP);
		}	
		catch (ArithmeticException e)
		{
			setError(true);
		}
	}
	
	/**
	 * @param Var var1
	 * @param MathBase mathBase
	 * Set current object to var1/mathBase
	 * if var2 = 0, then an error is set; it can be tested by chaining .isError() (member of base class MathBase)
	 */	 
	public MathDivide(VarAndEdit var1, MathBase mathBase)
	{
		try
		{
			String s1 = var1.getDottedSignedString();
			m_d = new BigDecimal(s1);
			
			decimalDivide(mathBase.m_d);
			//m_d = m_d.divide(mathBase.m_d, PRECISION, BigDecimal.ROUND_HALF_UP);
		}	
		catch (ArithmeticException e)
		{
			setError(true);
		}
	}		

	/**
	 * @param MathBase mathBase
	 * @param Var var2
	 * Set current object to mathBase/var2
	 * if var2 = 0, then an error is set; it can be tested by chaining .isError() (member of base class MathBase)
	 */	 	
	public MathDivide(MathBase mathBase, VarAndEdit var2)
	{
		try
		{
			setWithMathBase(mathBase);
	
			String s2 = var2.getDottedSignedString();
			BigDecimal val2 = new BigDecimal(s2);
			
			decimalDivide(val2);
			//m_d = m_d.divide(val2, PRECISION, BigDecimal.ROUND_HALF_UP);
		}		
		catch (ArithmeticException e)
		{
			setError(true);
		}
	}

	/**
	 * @param MathBase mathBase
	 * @param int n
	 * Set current object to mathBase/n
	 * if var2 = 0, then an error is set; it can be tested by chaining .isError() (member of base class MathBase)
	 */	 	
	public MathDivide(MathBase mathBase, int n)
	{
		try
		{
			setWithMathBase(mathBase);
			
			BigDecimal val2 = new BigDecimal(String.valueOf(n));
			decimalDivide(val2);
			//m_d = m_d.divide(val2, PRECISION, BigDecimal.ROUND_HALF_UP);
		}		
		catch (ArithmeticException e)
		{
			setError(true);
		}
	}
	
	/**
	 * @param MathBase mathBase
	 * @param double d
	 * Set current object to mathBase/d
	 * if var2 = 0, then an error is set; it can be tested by chaining .isError() (member of base class MathBase)
	 */	 	
	public MathDivide(MathBase mathBase, double d)
	{
		try
		{
			setWithMathBase(mathBase);
			
			BigDecimal val2 = new BigDecimal(d);
			decimalDivide(val2);
			//m_d = m_d.divide(val2, PRECISION, BigDecimal.ROUND_HALF_UP);
		}
		catch (ArithmeticException e)
		{
			setError(true);
		}
	}

	/**
	 * @param MathBase mathBase
	 * @param String cs, treated as a number 
	 * Set current object to mathBase/cs
	 * if var2 = 0, then an error is set; it can be tested by chaining .isError() (member of base class MathBase)
	 */	 
	public MathDivide(MathBase mathBase, String cs)
	{
		try
		{
			setWithMathBase(mathBase);
			
			BigDecimal val2 = new BigDecimal(cs);
			decimalDivide(val2);
			//m_d = m_d.divide(val2, PRECISION, BigDecimal.ROUND_HALF_UP);
		}		
		catch (ArithmeticException e)
		{
			setError(true);
		}
	}

	/**
	 * @param MathBase mathBase1
	 * @param MathBase mathBase2 
	 * Set current object to mathBase1/mathBase2
	 * if var2 = 0, then an error is set; it can be tested by chaining .isError() (member of base class MathBase)
	 */	 
	public MathDivide(MathBase mathBase1, MathBase mathBase2)
	{
		try
		{
			setWithMathBase(mathBase1);
			
			decimalDivide(mathBase2.m_d);
			//m_d = m_d.divide(mathBase2.m_d, PRECISION, BigDecimal.ROUND_HALF_UP);
		}		
		catch (ArithmeticException e)
		{
			setError(true);
		}
	}
	
		
	private void decimalDivide(BigDecimal val)
	{
		m_dA = m_d;
		m_dB = val;			
		m_d = m_d.divide(val, PRECISION, BigDecimal.ROUND_HALF_UP);
	}

	public MathBase toRounded(Var varDest, VarAndEdit varRest) {
		round(varDest);
		return to(varDest, varRest);
	}

	/**
	 * @param Var varQuotient: destination variable that will receive the quotient of the last divide operation, recomputed as an integer divide
	 * @param Var varRest: destination variable that will receive the rest of the last divide operation, recomputed as an integer divide
	 * @return
	 * The last division is done again, as an integer one. This to method must follow immediatly the division to do.
	 */
	public MathDivide to(VarAndEdit varQuotient, VarAndEdit varRest)
	{
		if(m_dA != null && m_dB != null)
		{
			// Do the integer division
			BigInteger nA = m_dA.toBigInteger();
			BigInteger nB = m_dB.toBigInteger();
			BigInteger[] t = nA.divideAndRemainder(nB);
			
			int n = t[0].intValue();
			varQuotient.set(n);
			
			if (varRest != null)
			{
				n = t[1].intValue();
				varRest.set(n);
			}
		}
		return this ;
	}
		
	private BigDecimal m_dA = null;
	private BigDecimal m_dB = null;
}
