/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.sqlSupport;

import java.sql.Connection;

import nacaLib.basePrgEnv.BaseResourceManager;

import jlib.sql.DbConnectionBase;
import jlib.sql.DbConnectionManagerBase;
import jlib.sql.DbDriverId;

public class SQLConnectionManager extends DbConnectionManagerBase
{
	public SQLConnectionManager()
	{
	}
	
	public DbConnectionBase createConnection(Connection connection, String csPrefId, String csEnvironment, boolean bUseStatementCache, boolean bUseJmx, DbDriverId dbDriverId)
	{
		SQLConnection sqlConnection = new SQLConnection(connection, csPrefId, csEnvironment, bUseStatementCache, bUseJmx, dbDriverId);
		return sqlConnection;
	}
}
