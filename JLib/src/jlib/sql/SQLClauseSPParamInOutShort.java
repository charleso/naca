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
import java.sql.Types;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class SQLClauseSPParamInOutShort extends SQLClauseSPParamInOut
{
	private short m_tsVal[] = null;
	
	public SQLClauseSPParamInOutShort(SQLClauseSPParamWay wayInOut, short tsVal[])
	{
		super(wayInOut);
		m_tsVal = tsVal;
	}
	
	protected void setInValueWithException(int nParamId, DbPreparedCallableStatement stmt)
		throws SQLException
	{
		stmt.setInValueWithException(nParamId, m_tsVal[0]);
	}
	
	protected void registerOutParameterWithException(int nParamId, DbPreparedCallableStatement stmt)
		throws SQLException
	{
		stmt.registerOutParameterWithException(nParamId, Types.SMALLINT);
	}
	
	protected void retrieveOutValuesWithException(int nParamId, DbPreparedCallableStatement stmt)
	 	throws SQLException
	{
		m_tsVal[0] = stmt.getOutValueShortWithException(nParamId);
	}
}
