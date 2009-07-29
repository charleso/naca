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

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class StatementPosInPool
{
	StatementPosInPool(DbConnectionBase connection, String csStatementId)
	{
		m_connection = connection; 
		m_csStatementId = csStatementId;
	}
	
	boolean forceRemoveStatement()
	{
		if(m_connection != null)
		{
			boolean b = m_connection.forceRemoveStatement(m_csStatementId);
			m_connection = null;
			return b;
		}
		return false;
	}	
	
	private DbConnectionBase m_connection = null;
	private String m_csStatementId = null;
}
