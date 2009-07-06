/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.sqlMapper;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: OrderSegment.java,v 1.1 2007/12/04 14:00:23 u930di Exp $
 */
public class OrderSegment
{
	private String m_csColName = null;
	boolean m_bAscending = true;
	
	protected OrderSegment(String csColName, boolean bAscending)
	{
		m_csColName = csColName;
		m_bAscending = bAscending;
	}
	
	String getAsString()
	{
		if(m_bAscending)
			return m_csColName;
		return m_csColName + " desc"; 
	}
}
