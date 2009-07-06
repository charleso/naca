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
 * @version $Id: SQLClauseSPParamInOutDouble.java,v 1.1 2007/10/16 09:47:08 u930di Exp $
 */
public class SQLClauseSPParamInOutDouble extends SQLClauseSPParamInOut
{
	private double m_tdVal[] = null;
	
	public SQLClauseSPParamInOutDouble(SQLClauseSPParamWay wayInOut, double tdVal[])
	{
		super(wayInOut);
		m_tdVal = tdVal;
	}	
	
	protected void setInValueWithException(int nParamId, DbPreparedCallableStatement stmt)
		throws SQLException
	{
		stmt.setInValueWithException(nParamId, m_tdVal[0]);
	}
	
	protected void registerOutParameterWithException(int nParamId, DbPreparedCallableStatement stmt)
		throws SQLException
	{
		stmt.registerOutParameterWithException(nParamId+1, Types.DOUBLE);
	}
	
	protected void retrieveOutValuesWithException(int nParamId, DbPreparedCallableStatement stmt)
		throws SQLException
	{
		m_tdVal[0] = stmt.getOutValueDoubleWithException(nParamId);
	}
}
