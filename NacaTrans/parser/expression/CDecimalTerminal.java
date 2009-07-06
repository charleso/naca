/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 30 juil. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.expression;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.CDataEntity;
import semantic.CBaseEntityFactory;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CDecimalTerminal extends CTerminal 
{
	
	public CDecimalTerminal(String intval, String decval)
	{
		m_csIntVal = intval ;
		m_csDecVal = decval ;
	}

	protected String m_csIntVal = "" ;
	protected String m_csDecVal = "" ;
	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#ExportTo(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void ExportTo(Element e, Document root)
	{
		e.setAttribute("Decimal", m_csIntVal + "," + m_csDecVal) ;	
	}

	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#IsReference()
	 */
	public boolean IsReference()
	{
		return false;
	}
	
	public boolean IsOne()
	{
		return false;
	}
	
	public boolean IsMinusOne()
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#ExportTo(semantic.CBaseExporter)
	 */
//	public void ExportTo(CBaseLanguageExporter e)
//	{
//		e.WriteWord(m_csIntVal + "." + m_csDecVal) ;		
//	}

	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#GetValue()
	 */
	public String GetValue()
	{
		return m_csIntVal + "." + m_csDecVal ;
	}

	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#GetDataEntity(semantic.CBaseEntityFactory)
	 */
	public CDataEntity GetDataEntity(int nLine, CBaseEntityFactory factory)
	{
		return factory.NewEntityNumber(m_csIntVal + "." + m_csDecVal);
	} 
	public String toString()
	{
		return m_csIntVal +"."+ m_csDecVal ;
	}

	public boolean IsNumber()
	{
		return true ;
	}
}
