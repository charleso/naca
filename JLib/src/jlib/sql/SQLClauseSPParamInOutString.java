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
public class SQLClauseSPParamInOutString extends SQLClauseSPParamInOut
{
	private String m_tcsVal[] = null;
	
	public SQLClauseSPParamInOutString(SQLClauseSPParamWay wayInOut, String tcsVal[])
	{
		super(wayInOut);
		m_tcsVal = tcsVal;
	}
	
	protected void setInValueWithException(int nParamId, DbPreparedCallableStatement stmt)
		throws SQLException
	{
		stmt.setInValueWithException(nParamId, m_tcsVal[0]);
	}
	
	protected void registerOutParameterWithException(int nParamId, DbPreparedCallableStatement stmt)
		throws SQLException
	{
		stmt.registerOutParameterWithException(nParamId, Types.CHAR);
	}
	
	protected void retrieveOutValuesWithException(int nParamId, DbPreparedCallableStatement stmt)
		throws SQLException
	{
		m_tcsVal[0] = stmt.getOutValueStringWithException(nParamId);
	}
}
