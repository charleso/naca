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
 * @version $Id: SQLClauseSPParamInOut.java,v 1.1 2007/10/16 09:47:08 u930di Exp $
 */
public abstract class SQLClauseSPParamInOut extends SQLClauseSPParam
{
	protected SQLClauseSPParamInOut(SQLClauseSPParamWay wayInOut)
	{
		super(wayInOut);
	}
}
