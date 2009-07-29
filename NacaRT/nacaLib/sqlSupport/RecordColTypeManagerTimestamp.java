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

import jlib.misc.DateUtil;
import jlib.misc.StringUtil;
import jlib.sql.LogSQLException;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class RecordColTypeManagerTimestamp extends RecordColTypeManagerBase
{
	public RecordColTypeManagerTimestamp(int nColSourceIndex)
	{
		super(nColSourceIndex);
	}
	
	public boolean transfer(int nColumnNumber1Based, ResultSet resultSetSource, PreparedStatement insertStatementInsert)
	{
		try
		{			
			Timestamp value = resultSetSource.getTimestamp(m_nColSourceIndex);
			if (!resultSetSource.wasNull())
				insertStatementInsert.setTimestamp(m_nColSourceIndex, value);
			else
				insertStatementInsert.setNull(m_nColSourceIndex, Types.TIMESTAMP);
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
			Timestamp ts = rs.getTimestamp(m_nColSourceIndex);
			if(ts != null)
			{
				String csValue = new DateUtil("yyyy-MM-dd-HH.mm.ss.", new java.util.Date(ts.getTime())).toString();

				int nNanos = ts.getNanos() / 1000;
				nNanos = nNanos % 1000000;	// Keep 6 rightmost digit

				if(nNanos >= 100000)
					csValue += "" + nNanos;
				else if(nNanos >= 10000)
					csValue += "0" + nNanos;
				else if(nNanos >= 1000)
					csValue += "00" + nNanos;
				else if(nNanos >= 100)
					csValue += "000" + nNanos;
				else if(nNanos >= 10)
					csValue += "0000" + nNanos;
				else 
					csValue += "00000" + nNanos;
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
