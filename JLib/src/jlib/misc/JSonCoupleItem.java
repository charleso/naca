/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.misc;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class JSonCoupleItem
{
	private String m_csName;
	private String m_csValue;
	private JSonCoupleItemType m_type;
	
	JSonCoupleItem()
	{
	}
	
	String getName()
	{
		return m_csName;
	}
	
	int getValueAsInt()
	{
		return NumberParser.getAsInt(m_csValue);
	}
	
	String getValueAsString()
	{
		return m_csValue;
	}
	
	boolean getValueAsBoolean()
	{
		return NumberParser.getAsBoolean(m_csValue);
	}
	
	boolean parse(String csCouple)
	{
		int nIndex = csCouple.indexOf(":");
		if(nIndex != -1)
		{
			m_csName = csCouple.substring(0, nIndex);
			m_csName = StringUtil.removeSurroundingQuotes(m_csName);
			
			String csValue = csCouple.substring(nIndex+1);
			if(csValue.startsWith("\"") && csValue.endsWith("\""))
			{
				// Remove quotes
				m_csValue = StringUtil.removeSurroundingQuotes(csValue);
				m_type = JSonCoupleItemType.TypeString;
				return true;
			}
			else	// Number or null
			{
				if(csValue.equals("null"))
				{
					m_csValue = null;
					m_type = JSonCoupleItemType.TypeString;
					return true;
				}
				else if(csValue.equalsIgnoreCase("true"))
				{
					m_csValue = csValue;
					m_type = JSonCoupleItemType.TypeBoolean;
					return true;
				}
				else if(csValue.equalsIgnoreCase("false"))
				{
					m_csValue = csValue;
					m_type = JSonCoupleItemType.TypeBoolean;
					return true;
				}
				else	// Number
				{
					// Check numeric value
					m_csValue = csValue;
					if(csValue.indexOf(".") >= 0)	// ouble
						m_type = JSonCoupleItemType.TypeDouble;
					else
						m_type = JSonCoupleItemType.TypeInteger;
					return true;
				}				
			}
		}
		return false;
	}
}
