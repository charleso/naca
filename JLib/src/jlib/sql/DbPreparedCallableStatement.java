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

import java.sql.CallableStatement;
import java.sql.SQLException;
/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class DbPreparedCallableStatement
{
	protected CallableStatement m_callableStatement = null;
	
	public DbPreparedCallableStatement(CallableStatement callableStatement)
	{
		init(callableStatement);
	}	
	
	public void init(CallableStatement callableStatement)
	{
		m_callableStatement = callableStatement;
	}
	
	public boolean setInValue(int nParamId, double d)
	{
		try
		{
			m_callableStatement.setDouble(nParamId, d);
			return true;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean setInValueWithException(int nParamId, double d)
		throws SQLException
	{
		m_callableStatement.setDouble(nParamId, d);
		return true;
	}
	
	public boolean setInValue(int nParamId, int n)
	{
		try
		{
			m_callableStatement.setInt(nParamId, n);
			return true;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean setInValueWithException(int nParamId, short s)
		throws SQLException
	{
		m_callableStatement.setShort(nParamId, s);
		return true;
	}
	
	public boolean setInValue(int nParamId, short s)
	{
		try
		{
			m_callableStatement.setShort(nParamId, s);
			return true;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean setInValueWithException(int n, String cs)
		throws SQLException
	{
		m_callableStatement.setString(n, cs);
		return true;
	}

	public boolean setInValue(int n, String cs)
	{
		try
		{
			m_callableStatement.setString(n, cs);
			return true;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public String getOutValueStringWithException(int nParamId)
		throws SQLException
	{
		return m_callableStatement.getString(nParamId);
	}
	
	public String getOutValueString(int nParamId) throws SQLException
	{
		return m_callableStatement.getString(nParamId);
	}
//	
//	public String getOutValueString(int nParamId)
//	{
//		try
//		{
//			return m_callableStatement.getString(nParamId);
//		}
//		catch (SQLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "";
//	}
	
	public double getOutValueDoubleWithException(int nParamId)
		throws SQLException
	{
		return m_callableStatement.getDouble(nParamId);
	}
	
	public double getOutValueDouble(int nParamId)
	{
		try
		{
			return m_callableStatement.getDouble(nParamId);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.0;
	}
	
	public int getOutValueIntWithException(int nParamId)
		throws SQLException
	{
		return m_callableStatement.getInt(nParamId);
	}

	public int getOutValueInt(int nParamId)
	{
		try
		{
			return m_callableStatement.getInt(nParamId);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
	public short getOutValueShortWithException(int nParamId)
		throws SQLException
	{
		return m_callableStatement.getShort(nParamId);
	}
	
	public short getOutValueShort(int nParamId)
	{
		try
		{
			return m_callableStatement.getShort(nParamId);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean registerOutParameterWithException(int nParamId, int nTypeId)
		throws SQLException
	{
		m_callableStatement.registerOutParameter(nParamId, nTypeId);
		return true;
	}
	
	public boolean registerOutParameter(int nParamId, int nTypeId)
	{
		try
		{
			m_callableStatement.registerOutParameter(nParamId, nTypeId);
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean registerOutParameter(String csName, ColDescriptionInfo colDescriptionInfo)
	{
		try
		{
			m_callableStatement.registerOutParameter(csName, colDescriptionInfo.m_nTypeId);
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean registerOutParameter(int nParamId, ColDescriptionInfo colDescriptionInfo)
	{
		try
		{
			m_callableStatement.registerOutParameter(nParamId, colDescriptionInfo.m_nTypeId);
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean executeWithException() 
		throws SQLException
	{
		boolean b = m_callableStatement.execute();
		return b;
	}

	public boolean execute() 
		throws SQLException
	{
		boolean b = m_callableStatement.execute();
		return b;
	}
	
	public boolean closeWithException()
		throws SQLException
	{		
		m_callableStatement.close();
		return true;
	}

	public boolean close()
	{
		try
		{
			m_callableStatement.close();
			return true;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
