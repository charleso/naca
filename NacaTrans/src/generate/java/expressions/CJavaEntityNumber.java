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

import generate.CBaseLanguageExporter;
import semantic.expression.CEntityNumber;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaEntityNumber extends CEntityNumber
{

	/**
	 * @param l
	 * @param cat
	 * @param out
	 * @param number
	 */
	public CJavaEntityNumber(CObjectCatalog cat, CBaseLanguageExporter out, String number)
	{
		super(cat, out, number);
	}
	public String ExportReference(int nLine)
	{
		int n = m_csValue.indexOf('.');
		if (n == -1)
		{
			try
			{
				String out =String.valueOf(Integer.parseInt(m_csValue)) ; 
				return out ;
			}
			catch (NumberFormatException ex)
			{
				try
				{
					String out = String.valueOf(Long.parseLong(m_csValue)) ;
					return out+"L" ;
				}
				catch (NumberFormatException ex2)
				{
					return  m_csValue ;
				}
			}
		}
		else
		{
			// CV : this next line is used to export doubles numbers as strings.
			return "\"" + m_csValue + "\"" ;
//			String in = m_csValue.substring(0, n);
//			String dec = m_csValue.substring(n+1);
//			try
//			{
//				String out = String.valueOf(Integer.parseInt(in)) + "." +  dec ;
//				return out ;
//			}
//			catch (NumberFormatException e)
//			{
//				try
//				{
//					String out = String.valueOf(Long.parseLong(in)) + "." +  dec ;
//					return out ;
//				}
//				catch (NumberFormatException ex)
//				{
//					m_logger.warn("WARNING : parsing integer : " + e.getMessage()) ;
//					return in + "." + dec ;
//				}
//			}
		}
	}
	public String GetConstantValue()
	{
		return m_csValue ;
	} 	 
	public boolean isValNeeded()
	{
		return true;
	}

}
