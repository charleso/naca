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
public class SQLClauseSPParamInInt extends SQLClauseSPParamIn
{
	private int m_nVal = 0;
	
	public SQLClauseSPParamInInt(int nVal)
	{
		m_nVal = nVal;
	}
	
	protected void setInValueWithException(int nParamId, DbPreparedCallableStatement stmt)
		throws SQLException
	{
		stmt.setInValueWithException(nParamId, m_nVal);
	}
}
