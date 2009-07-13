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

import nacaLib.varEx.VarBase;

import jlib.sql.LogSQLException;

public class RecordColTypeManagerDate extends RecordColTypeManagerBase
{
	public RecordColTypeManagerDate(int nColSourceIndex)
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
				insertStatementInsert.setNull(m_nColSourceIndex, Types.DATE);
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
				String csYYYY = csValue.substring(0, 4);
				String csMM = csValue.substring(5, 7);
				String csDD = csValue.substring(8, 10);
				csValue = csDD + "." + csMM + "." + csYYYY;
				//varInto.set(csValue);
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
