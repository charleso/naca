/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;

import java.sql.Driver;
import java.util.Properties;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: DbConnectionParam.java,v 1.7 2008/07/02 12:44:21 u930di Exp $
 */
public class DbConnectionParam
{
	Driver m_driver = null;
	String m_csUrl = "" ;
	String m_csEnvironment = "" ;
	String m_csPackage = "" ;
	Properties m_propertiesUserPassword = null;
	String m_csConnectionUrlOptionalParams = null;
	boolean m_bAutoCommit = false;
	boolean m_bCloseCursorOnCommit = false;
	
	public DbDriverId getDbDriverId()
	{
		return DbDriverId.getByClassName(m_driver.getClass().toString());
	}
	
	String getEnvironment()
	{
		return m_csEnvironment;
	}
	
	void setEnvironment(String cs)
	{
		m_csEnvironment = cs;
	}
}
