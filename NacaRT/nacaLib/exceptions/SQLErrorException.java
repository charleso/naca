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
package nacaLib.exceptions;

import java.sql.SQLException;

import nacaLib.sqlSupport.SQL;


public class SQLErrorException extends NacaRTException
{
	private static final long serialVersionUID = 1L;
	private String m_csMethod = null;
	private SQLException m_eSQLException = null;
	private SQL m_sql = null;
	
	public SQLErrorException(String csMethod, SQLException e, SQL sql)
	{
		m_csMethod = csMethod; 
		m_eSQLException = e;
		m_sql = sql; 
	}
	
	public String getMessage()
	{
		String cs = "SQLErrorException";
		return cs;
	}
}
