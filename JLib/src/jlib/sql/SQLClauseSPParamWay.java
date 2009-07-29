/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.sql;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class SQLClauseSPParamWay
{
	public static SQLClauseSPParamWay In = new SQLClauseSPParamWay("In");
	public static SQLClauseSPParamWay Out = new SQLClauseSPParamWay("Out");
	public static SQLClauseSPParamWay InOut = new SQLClauseSPParamWay("InOut");
	
	private SQLClauseSPParamWay(String cs)
	{
		m_cs = cs;
	}
	
	public String toString()
	{
		return m_cs;
	}
	
	private String m_cs = null;
	
	
}
