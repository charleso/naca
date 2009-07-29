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
public class CStringTerminal extends CTerminal
{
	public CStringTerminal(char[] arr)
	{
		m_Value = arr ;
		m_Str = new String(m_Value);
	}
	public CStringTerminal(String cs)
	{
		m_Value = cs.toCharArray() ;
		m_Str = cs ;
	}

	protected char[] m_Value = {} ;
	protected String m_Str = "" ;

	public void ExportTo(Element e, Document root)
	{
		String cs = new String(m_Value);
		e.setAttribute("String", cs) ;		
	}

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
	

	public String GetValue()
	{
		String cs = new String(m_Value);
		return cs ;
	}

	public CDataEntity GetDataEntity(int nLine, CBaseEntityFactory factory)
	{
		String val = new String(m_Value) ;
		CDataEntity e = factory.getSpecialConstantValue(val);
		if (e != null)
		{
			return e ;
		}
		else
		{
			return factory.NewEntityString(m_Value);
		}
	}
	public String toString()
	{
		return "\"" + new String(m_Value) + "\"" ;
	}

	public boolean IsNumber()
	{
		return false ;
	}
}
