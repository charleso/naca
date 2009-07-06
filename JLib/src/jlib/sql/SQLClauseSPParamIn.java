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

import java.sql.SQLException;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SQLClauseSPParamIn.java,v 1.1 2007/10/16 09:47:08 u930di Exp $
 */
public abstract class SQLClauseSPParamIn extends SQLClauseSPParam
{
	protected SQLClauseSPParamIn()
	{
		super(SQLClauseSPParamWay.In);
	}

	// Do nothing; in only parameter
	protected void registerOutParameterWithException(int nParamId, DbPreparedCallableStatement stmt)
		throws SQLException
	{
	}
	
	// Do nothing; in only parameter
	protected void retrieveOutValuesWithException(int nParamId, DbPreparedCallableStatement stmt)
	 	throws SQLException
	{
	}
}
