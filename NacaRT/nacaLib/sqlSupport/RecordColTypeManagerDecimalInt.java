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
import nacaLib.varEx.VarBase;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class RecordColTypeManagerDecimalInt extends RecordColTypeManagerBase
{
	public RecordColTypeManagerDecimalInt(int nColSourceIndex)
	{
		super(nColSourceIndex);
	}
	
	public boolean transfer(int nColumnNumber1Based, ResultSet resultSetSource, PreparedStatement insertStatementInsert)
	{
		try
		{
			int nValue = resultSetSource.getInt(m_nColSourceIndex);
			if (!resultSetSource.wasNull())
				insertStatementInsert.setInt(m_nColSourceIndex, nValue);
			else
				insertStatementInsert.setNull(m_nColSourceIndex, Types.INTEGER);
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
			int nValue = rs.getInt(m_nColSourceIndex);
			if (nValue != 0 || !rs.wasNull())
			{
				varInto.m_varDef.write(varInto.m_bufferPos, nValue);
				return false;
			}
		}
		catch (SQLException e)
		{
			LogSQLException.log(e);
			// Maybe should I set m_bNull = true; ?
		}
		varInto.m_varDef.write(varInto.m_bufferPos, 0);
		return true;
	}
}
