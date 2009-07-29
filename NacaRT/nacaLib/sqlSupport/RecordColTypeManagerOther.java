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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import jlib.sql.LogSQLException;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.varEx.VarBase;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class RecordColTypeManagerOther extends RecordColTypeManagerBase
{
	public RecordColTypeManagerOther(int nColSourceIndex)
	{
		super(nColSourceIndex);
	}
	
	public boolean transfer(int nColumnNumber1Based, ResultSet resultSetSource, PreparedStatement insertStatementInsert)
	{
		try
		{			
			String csValue = resultSetSource.getString(m_nColSourceIndex);
			insertStatementInsert.setString(m_nColSourceIndex, csValue);
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;		
	}
		
	boolean fillColValue(ResultSet rs, VarBase varInto)
	{
		try
		{			
			String csValue = rs.getString(m_nColSourceIndex);
			if(csValue != null)
			{
				varInto.m_varDef.write(varInto.m_bufferPos, csValue);
				return false;
			}
			
			
		}
		catch (SQLException e)
		{
			LogSQLException.log(e);
			// Maybe should I set m_bNull = true; ?
		}
		varInto.m_varDef.write(varInto.m_bufferPos, "");	//varInto.set("");
		return true;
	}
}
