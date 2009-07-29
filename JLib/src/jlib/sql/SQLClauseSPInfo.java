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


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class SQLClauseSPInfo
{
	public String m_csCatalog = null;
	public String m_csName = null;
	public String m_csRemarks = null;
	public String m_csSchem = null;
	public short m_sType = 0;
	
	SQLClauseSPInfo()
	{
	}
	
	boolean fill(ResultSet rsProc)
	{
		try
		{
			m_csCatalog = rsProc.getString("PROCEDURE_CAT");
			m_csName = rsProc.getString("PROCEDURE_NAME");
			m_csRemarks = rsProc.getString("REMARKS");
			m_sType = rsProc.getShort("PROCEDURE_TYPE");
			m_csSchem = rsProc.getString("PROCEDURE_SCHEM");
			return true;
		}
		catch(SQLException e)
		{
		}
		return false;
	}
}
