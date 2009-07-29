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

import java.sql.SQLException;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
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
