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
/**
 * 
 */
package nacaLib.sqlSupport;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class StoredProcInfo
{
	public String m_csCatalog = null;
	public String m_csName = null;
	public String m_csRemarks = null;
	public String m_csSchem = null;
	public short m_sType = 0;
	
	StoredProcInfo()
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
