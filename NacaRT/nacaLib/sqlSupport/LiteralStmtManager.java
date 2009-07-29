/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.sqlSupport;

public class LiteralStmtManager
{
	private SQL m_sql = null;
	
	LiteralStmtManager(SQL sql)
	{
		m_sql = sql;
	}
	
	void tryExecuteLiteralStmt()
	{
		CSQLPreparedStatement SQLStatement = m_sql.bindAndPrepareLiteralStmt();
		if (SQLStatement != null)
		{
			CSQLResultSet resultSet  = SQLStatement.executeQuery(m_sql);
			int gg = 0;
		}
	}
	
}
