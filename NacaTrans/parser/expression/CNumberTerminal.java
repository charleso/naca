/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 27, 2004
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
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CNumberTerminal extends CTerminal
{
	public CNumberTerminal(String val)
	{
		m_csValue = val ;
		/*
		if (m_csValue.indexOf("x") == -1)
		{	
			int i = 0;
			for (; i < m_csValue.length() - 1 && m_csValue.charAt(i) == '0'; i++);
			if (i > 0)
				m_csValue = m_csValue.substring(i);
		}
		*/
	}
	/* (non-Javadoc)
	 * @see parser.condition.CConditionalTerminal#Export()
	 */
//	public String GetType()
//	{
//		return "Number" ;
//	}
//	public String GetValue()
//	{
//		return m_csValue;
//	}
	String m_csValue = "" ;
	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#ExportTo(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void ExportTo(Element e, Document root)
	{
		e.setAttribute("Number", m_csValue)	;	
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
		if(Integer.parseInt(m_csValue) == 1)
			return true;
		return false;
	}
	
	public boolean IsMinusOne()
	{
		if(Integer.parseInt(m_csValue) == -1)
			return true;
		return false;
	}
	
	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#ExportTo(semantic.CBaseExporter)
	 */
//	public void ExportTo(CBaseLanguageExporter e)
//	{
//		e.WriteWord(m_csValue) ;
//	}
	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#GetValue()
	 */
	public String GetValue()
	{
		return m_csValue ;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#GetDataEntity(semantic.CBaseEntityFactory)
	 */
	public CDataEntity GetDataEntity(int nLine, CBaseEntityFactory factory)
	{
		return factory.NewEntityNumber(m_csValue);
	}
	public String toString()
	{
		return m_csValue ;
	}

	public boolean IsNumber()
	{
		return true ;
	}
}
