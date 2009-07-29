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
import java.sql.Timestamp;
import java.sql.Types;

import nacaLib.varEx.VarBase;

import jlib.sql.LogSQLException;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class RecordColTypeManagerVarchar extends RecordColTypeManagerBase
{
	public RecordColTypeManagerVarchar(int nColSourceIndex)
	{
		super(nColSourceIndex);
	}
	
	public boolean transfer(int nColumnNumber1Based, ResultSet resultSetSource, PreparedStatement insertStatementInsert)
	{
		try
		{			
			String csValue = resultSetSource.getString(m_nColSourceIndex);
			if (!resultSetSource.wasNull())
				insertStatementInsert.setString(m_nColSourceIndex, csValue);
			else
				insertStatementInsert.setNull(m_nColSourceIndex, Types.VARCHAR);
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
		boolean isLongVarCharVarStructure = varInto.getVarDef().isLongVarCharVarStructure();
		try
		{
			String csValue = rs.getString(m_nColSourceIndex);
			if(csValue != null)
			{
				if (isLongVarCharVarStructure)
				{	
					int nLen = csValue.length();
					char cHigh = (char)(nLen / 256);
					char cLow = (char)(nLen % 256);
					StringBuffer buf = new StringBuffer();
					buf.append(cHigh);	// big endian
					buf.append(cLow);
					buf.append(csValue);
					csValue = buf.toString();
				}	
				varInto.m_varDef.write(varInto.m_bufferPos, csValue);
				return false;				
			}
		}
		catch (SQLException e)
		{
			LogSQLException.log(e);
			// Maybe should I set m_bNull = true; ?
		}
		if (isLongVarCharVarStructure)
		{	
			int nLen = 0;
			char cHigh = (char)(nLen / 256);
			char cLow = (char)(nLen % 256);
			StringBuffer buf = new StringBuffer();
			buf.append(cHigh);	// big endian
			buf.append(cLow);
			buf.append("");
			varInto.m_varDef.write(varInto.m_bufferPos, buf.toString());
		}
		else
		{
			varInto.m_varDef.write(varInto.m_bufferPos, "");
		}
		return true;
	}
}
