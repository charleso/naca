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

import java.math.BigDecimal;
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
public class RecordColTypeManagerDecimal extends RecordColTypeManagerBase
{
	public RecordColTypeManagerDecimal(int nColSourceIndex)
	{
		super(nColSourceIndex);
	}
	
	public boolean transfer(int nColumnNumber1Based, ResultSet resultSetSource, PreparedStatement insertStatementInsert)
	{
		try
		{			
			BigDecimal value = resultSetSource.getBigDecimal(m_nColSourceIndex);
			if (!resultSetSource.wasNull())
				insertStatementInsert.setBigDecimal(m_nColSourceIndex, value);
			else
				insertStatementInsert.setNull(m_nColSourceIndex, Types.DECIMAL);			
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
			BigDecimal bdValue = rs.getBigDecimal(m_nColSourceIndex);
			if(bdValue != null)
			{
				varInto.m_varDef.write(varInto.m_bufferPos, bdValue);
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
