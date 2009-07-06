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
 * @version $Id: ListCoupleRender.java,v 1.5 2007/10/16 09:47:51 u930di Exp $
 */
public class ListCoupleRender
{	
	public static ListCoupleRender set()
	{
		ListCoupleRender l = new ListCoupleRender();
		return l;
	}
	
	public static ListCoupleRender set(String csTitle)
	{
		ListCoupleRender l = new ListCoupleRender(csTitle);
		return l;
	}
	
	private static String m_csValue = null;
	
	private ListCoupleRender()
	{
		m_csValue = "";
	}
	
	private ListCoupleRender(String csTitle)
	{
		m_csValue = csTitle + ": ";
	}
	
	public ListCoupleRender set(String csName, String csValue)
	{
		if(m_csValue != null)
			m_csValue += "; ";
		m_csValue += "(" + csName + ",'" + csValue +"')";
		return this;
	}
	
	public ListCoupleRender set(String csName, Integer nValue)
	{
		if(m_csValue != null)
			m_csValue += "; ";
		m_csValue += "(" + csName + ",'" + nValue +"')";
		return this;
	}
	
	public ListCoupleRender set(String csName, Short sValue)
	{
		if(m_csValue != null)
			m_csValue += "; ";
		m_csValue += "(" + csName + ",'" + sValue +"')";
		return this;
	}
	
	public ListCoupleRender set(String csName, Double dValue)
	{
		if(m_csValue != null)
			m_csValue += "; ";
		m_csValue += "(" + csName + ",'" + dValue +"')";
		return this;
	}
	
	public String toString()
	{
		if(m_csValue != null)
			return m_csValue;
		return "";
	}
}
