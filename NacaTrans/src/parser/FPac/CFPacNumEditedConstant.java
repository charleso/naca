/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package parser.FPac;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CDataEntity;

/**
 * @author S. Charton
 * @version $Id: CFPacNumEditedConstant.java,v 1.3 2007/06/28 06:19:48 u930bm Exp $
 */
public class CFPacNumEditedConstant extends CTerminal
{

	protected String m_csFormat = "" ;
	/**
	 * 
	 */
	public CFPacNumEditedConstant(String cs)
	{
		m_csFormat = cs ;
	}

	/**
	 * @see parser.expression.CTerminal#GetValue()
	 */
	@Override
	public String GetValue()
	{
		return m_csFormat;
	}

	/**
	 * @see parser.expression.CTerminal#IsReference()
	 */
	@Override
	public boolean IsReference()
	{
		return false;
	}

	/**
	 * @see parser.expression.CTerminal#ExportTo(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	@Override
	public void ExportTo(Element e, Document root)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @see parser.expression.CTerminal#GetDataEntity(semantic.CBaseEntityFactory)
	 */
	@Override
	public CDataEntity GetDataEntity(int nLine, CBaseEntityFactory factory)
	{
		return factory.NewEntityString(m_csFormat) ;
	}


	public boolean IsNumber()
	{
		return false ;
	}
}
