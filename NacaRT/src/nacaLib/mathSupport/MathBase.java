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


import nacaLib.base.CJMapObject;
import nacaLib.varEx.Dec;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarAndEdit;
import nacaLib.varEx.VarDefBuffer;


public class MathBase extends CJMapObject
{
	/**
	 * Constructor
	 * Base class for all math operations 
	 */
	public MathBase()
	{
	}	
	
	public String getSTCheckValue()
	{
		String sValue = m_d.abs().unscaledValue().toString();
		return sValue;
	}
	
			
	/**
	 * @param Var varDest
	 * @return this
	 * Set the destination var to the result of the last math operation, adjusting precision, but without roundings
	 */
	public static Dec toDec(BigDecimal bd)
	{
		boolean bPositive = true;

		if(bd.signum() < 0)
			bPositive = false;
			
		String sValue = bd.abs().unscaledValue().toString();
		int nScale = bd.scale();
		if(sValue.length() > nScale)
		{
			String sInt = sValue.substring(0, sValue.length()-nScale);
			String sDec = sValue.substring(sValue.length()-nScale);
			Dec dec = new Dec(sInt, sDec);
			dec.setPositive(bPositive);
			return dec;
		}
		else
		{
			String sDec = new String();
			int nNbLeadingZeros = nScale - sValue.length();
			for(int n=0; n<nNbLeadingZeros; n++)
			{
				sDec = sDec + "0";
			}
			sDec = sDec + sValue;
			
			Dec dec = new Dec(0, sDec); 
			dec.setPositive(bPositive);
			return dec;
		}
	}
	
	public MathBase to(VarAndEdit varDest)
	{
		Dec dec = MathBase.toDec(m_d);
		varDest.set(dec);
		if(varDest.getVarDef().getLength() < dec.toString().length())
		{
			m_bError = true;
		}
		return this;
	}
	
	/**
	 * @param Var varDest
	 * @return this
	 * Set the destination var to the result of the last math operation, adjusting precision, with roundings if needed by destination variable 
	 */
	public MathBase toRounded(Var varDest)
	{
		round(varDest);
		return to(varDest);
	}

	protected void round(Var varDest)
	{
		VarDefBuffer varDef = varDest.getVarDef();
		int nNbDecimal = varDef.getNbDigitDecimal();
		if(nNbDecimal >= 0)
		{
			m_d = m_d.setScale(nNbDecimal, BigDecimal.ROUND_HALF_UP);
		}
	}
	
	/**
	 * @param int n 
	 * @return 0 if the resut of the last math operation equals n
	 * @return -1 if the resut of the last math operation < n
	 * @return 1 if the resut of the last math operation > n 
	 */
	public int compareTo(int var)
	{
		BigDecimal bigValue = new BigDecimal(String.valueOf(var));
		return compareTo(bigValue); 
	}
	
	/**
	 * @param Var var 
	 * @return 0 if the resut of the last math operation equals var's value
	 * @return -1 if the resut of the last math operation < var's value
	 * @return 1 if the resut of the last math operation > var's value 
	 */	
	public int compareTo(Var var)
	{
		String s = var.getDottedSignedString();
		return compareTo(s);
	}
	
	/**
	 * @param String cs, treated as a number
	 * @return 0 if the resut of the last math operation equals cs's value
	 * @return -1 if the resut of the last math operation < cs's value
	 * @return 1 if the resut of the last math operation > cs's value 
	 */	
	public int compareTo(String cs)
	{
		BigDecimal bigValue = new BigDecimal(cs);
		return compareTo(bigValue); 
	}	

	/**
	 * @param BigDecimal bigValue
	 * @return 0 if the resut of the last math operation equals bigValue's value
	 * @return -1 if the resut of the last math operation < bigValue's value
	 * @return 1 if the resut of the last math operation > bigValue's value 
	 */	
	public int compareTo(BigDecimal bigValue)
	{
		return m_d.compareTo(bigValue); 
	}

	/**
	 * @return true if an error has been set during one of the math operations done on the current objet
	 * Errors are set for divide by 0 
	 */
	public boolean isError()
	{
		return m_bError;
	} 
	
	/**
	 * @param boolean b
	 * Internal usage only
	 */
	public void setError(boolean b)
	{
		m_bError = b;
	}
	
	public String toString()
	{
		if(m_bError)
			return "Math Error";
		if(m_d != null)
			return m_d.toString();
		return "Unknown";
	}
	

	protected void setWithMathBase(MathBase mathBase)
	{
		m_d = new BigDecimal(0) ;
		m_d = m_d.add(mathBase.m_d);
	}

	public BigDecimal m_d = null;
	private boolean m_bError = false;
}
