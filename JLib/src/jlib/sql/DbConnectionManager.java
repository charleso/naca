/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */

package jlib.sql;

import java.sql.*;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DbConnectionManager extends DbConnectionManagerBase
{
	public DbConnectionManager()
	{
		super();
	}

	public DbConnectionBase createConnection(Connection connection, String csPrefId, String csEnvironment, boolean bUseStatementCache, boolean bUseJmx, DbDriverId dbDriver)
	{
		DbConnection sqlConnection = new DbConnection(connection, csPrefId, m_DbConnectionParam.getEnvironment(), bUseStatementCache, bUseJmx, dbDriver);
		return sqlConnection;
	}
}

