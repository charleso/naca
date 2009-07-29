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
 * Created on 11 oct. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.mathSupport;

import java.math.BigDecimal;


import nacaLib.tempCache.CStr;
import nacaLib.varEx.Dec;
import nacaLib.varEx.VarAndEdit;

public class MathAdd extends MathBase
{
	/**
	 * @param VarAndEdit VarAndEdit1: VarAndEditiable to be added
	 * Set current object to VarAndEdit1's numeric value
	 */
//	public MathAdd(VarAndEdit VarAndEdit1)
//	{
//		String s1 = VarAndEdit1.getDottedSignedString();
//		m_d = new BigDecimal(s1);
//	}
	
	/**
	 * @param VarAndEdit VarAndEdit1
	 * @param VarAndEdit VarAndEdit2
	 * Set current object to VarAndEdit1+VarAndEdit2 (VarAndEdit are treated as numeric optionnaly decimals / signed)
	 */
	public MathAdd(VarAndEdit VarAndEdit1, VarAndEdit VarAndEdit2)
	{
		String s1 = VarAndEdit1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		String s2 = VarAndEdit2.getDottedSignedString();
		BigDecimal val2 = new BigDecimal(s2);
		m_d = m_d.add(val2); 
	}

	/**
	 * @param VarAndEdit VarAndEdit1
	 * @param int n
	 * Set current object to VarAndEdit1+n
	 */
	public MathAdd(VarAndEdit VarAndEdit1, int n)
	{
		String s1 = VarAndEdit1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		BigDecimal val2 = new BigDecimal(String.valueOf(n));
		m_d = m_d.add(val2); 
	}
	
	/**
	 * @param int m
	 * @param int n
	 * Set current object to m+n
	 */	
	public MathAdd(int m, int n)
	{
		m_d = new BigDecimal(String.valueOf(m));
		
		BigDecimal val2 = new BigDecimal(String.valueOf(n));
		m_d = m_d.add(val2); 
	}
	
	/**
	 * @param int n
	 * Set current object to n
	 */	
	public MathAdd(int n)
	{
		m_d = new BigDecimal(String.valueOf(n));
	}

	/**
	 * @param double 
	 * Set current object to d
	 */
	public MathAdd(double d)
	{
		m_d = new BigDecimal(d);
	}
	
	public MathAdd(String s1, int n)
	{
		m_d = new BigDecimal(s1);
		BigDecimal val2 = new BigDecimal(n);
		m_d = m_d.add(val2); 
	}
	
	public MathAdd(CStr s1, int n)
	{
		m_d = s1.makeBigDecimal();
		BigDecimal val2 = new BigDecimal(n);
		m_d = m_d.add(val2); 
	}
	
//	public MathAdd(String s1, BigDecimal val2)
//	{
//		m_d = new BigDecimal(s1);
//		m_d = m_d.add(val2); 
//	}
	
	public MathAdd(String s1, MathBase mathBase)
	{
		m_d = new BigDecimal(s1);
		m_d = m_d.add(mathBase.m_d); 
	}
	
//	public static Dec inc(String s1, BigDecimal val2)
//	{
//		BigDecimal bd = new BigDecimal(s1);
//		bd = bd.add(val2);
//		return MathBase.toDec(bd);
//	}
	
	public static Dec inc(CStr s1, BigDecimal val2)
	{
		BigDecimal bd = s1.makeBigDecimal();
		bd = bd.add(val2);
		return MathBase.toDec(bd);
	}
	
//	public static Dec inc(String s1, int n)
//	{
//		BigDecimal bds1 = new BigDecimal(s1);
//		BigDecimal bdn = new BigDecimal(n);
//		bds1 = bds1.add(bdn);
//		return MathBase.toDec(bds1);
//	}
	
	public static Dec inc(CStr s1, int n)
	{
		BigDecimal bds1 = s1.makeBigDecimal();
		BigDecimal bdn = new BigDecimal(n);
		bds1 = bds1.add(bdn);
		return MathBase.toDec(bds1);
	}

	
	/**
	 * @param VarAndEdit VarAndEdit1
	 * @param double d
	 * Set current object to VarAndEdit1+d
	 */
	public MathAdd(VarAndEdit VarAndEdit1, double d)
	{
		String s1 = VarAndEdit1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		BigDecimal val2 = new BigDecimal(d);
		m_d = m_d.add(val2); 
	}

	/**
	 * @param VarAndEdit VarAndEdit1
	 * @param String s, will be converted to a number
	 * Set current object to VarAndEdit1+s
	 */
	public MathAdd(VarAndEdit VarAndEdit1, String s)
	{
		String s1 = VarAndEdit1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		BigDecimal val2 = new BigDecimal(s);
		m_d = m_d.add(val2); 
	}
	
	/**
	 * @param VarAndEdit VarAndEdit1
	 * @param MathBase mathBase
	 * Set current object to VarAndEdit1+mathBase
	 */
	public MathAdd(VarAndEdit VarAndEdit1, MathBase mathBase)
	{
		String s1 = VarAndEdit1.getDottedSignedString();
		m_d = new BigDecimal(s1);
		
		m_d = m_d.add(mathBase.m_d); 
	}	

	/**
	 * @param VarAndEdit VarAndEdit1
	 * @param MathBase mathBase
	 * Set current object to VarAndEdit1+mathBase
	 */
	public MathAdd(MathBase VarAndEdit1, MathBase mathBase)
	{
		m_d = VarAndEdit1.m_d ;
		
		m_d = m_d.add(mathBase.m_d); 
	}	

	/**
	 * @param MathBase mathBase
	 * @param int n
	 * Set current object to mathBase+n
	 */
	public MathAdd(MathBase mathBase, int n)
	{
		String s1 = String.valueOf(n);
		m_d = new BigDecimal(s1);
		
		m_d = m_d.add(mathBase.m_d); 
	}		
	
	/**
	 * @param int n
	 * @param MathBase mathBase
	 * Set current object to mathBase+n
	 */
	public MathAdd(int n, MathBase mathBase)
	{
		String s1 = String.valueOf(n);
		m_d = new BigDecimal(s1);
		
		m_d = m_d.add(mathBase.m_d); 
	}		
	
	/**
	 * @param VarAndEdit VarAndEdit; treated as a number
	 * Set current object's value to current object+VarAndEdit
	 */
	public MathAdd add(VarAndEdit VarAndEdit)
	{
		String s = VarAndEdit.getDottedSignedString();
		
		BigDecimal val = new BigDecimal(s);
		m_d = m_d.add(val);
		return this;
	}	
	
	/**
	 * @param String cs; treated as a number
	 * Set current object's value to current object+cs
	 */
	public MathAdd add(String cs)
	{
		BigDecimal val = new BigDecimal(cs);
		m_d = m_d.add(val);
		return this;
	}

	/**
	 * @param int n
	 * Set current object's value to current object+n
	 */
	public MathAdd add(int n)
	{
		long l = n;
		m_d = m_d.add(BigDecimal.valueOf(l));
		return this;
	}

	/**
	 * @param double d
	 * Set current object's value to current object+d
	 */
	public MathAdd add(double d)
	{
		BigDecimal val = new BigDecimal(d);
		m_d = m_d.add(val);
		return this;
	}
	
	/**
	 * @param MathBase mathBase
	 * Set current object's value to current object+mathBase
	 */	
	public MathAdd add(MathBase mathBase)
	{
		m_d = m_d.add(mathBase.m_d);
		return this;
	}
}
