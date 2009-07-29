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
 * Created on Jul 19, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package lexer;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CTokenConstant extends CBaseToken
{
	public CTokenConstant(CReservedConstant cste, int line, boolean newline)
	{
		super(line, newline) ;
		m_cste = cste ;
		m_Value = cste.m_Name ;
	}
	/* (non-Javadoc)
	 * @see lexer.CBaseToken#GetType()
	 */
	public CTokenType GetType()
	{
		return CTokenType.CONSTANT ;
	}
	public CReservedConstant GetConstant()
	{
		return m_cste ;
	}
	
	CReservedConstant m_cste = null ;
	/* (non-Javadoc)
	 * @see lexer.CBaseToken#GetDisplay()
	 */
	public String GetDisplay()
	{
		return m_cste.m_Name + " ";
	}
}
