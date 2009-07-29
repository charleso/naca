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
package jlib.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class SqlRequest extends DbPreparedStatement
{
	public SqlRequest(/*DbConnectionBase con*/)
	{
		super(/*con*/);
	}
	
	synchronized public static int getNextSeq(DbConnectionBase con, String csTableSequence, String csSequence)
	{	
		SqlRequest sq = new SqlRequest(/*con*/);
		sq.cmdSelect("Select Value From " + csTableSequence + " where Name=:Name For Update");
		sq.setParam("Name", csSequence);
		sq.execSQL(con);
		if(!sq.fetch())
		{
			// Create sequence
			SqlRequest sqInsert = new SqlRequest(/*con*/);
			sqInsert.cmdInsert(csTableSequence);
			sqInsert.setCol("Name", csSequence);
			sqInsert.setCol("Value", 1);
			boolean b = sqInsert.execSQL(con);
			if(!b)
				return -1;	// Error
			return 1;
		}
		else
		{
			int nValue = sq.getColAsInt("Value");
			nValue++;
			// Update sequence
			SqlRequest sqUpdate = new SqlRequest(/*con*/);
			sqUpdate.cmdUpdate(csTableSequence, "Name=:Name");
			sqUpdate.setCol("Value", nValue);
			sqUpdate.setParam("Name", csSequence);			
			boolean b = sqUpdate.execSQL(con);
			if(!b)
				return -1;
			return nValue;
		}
	}
	
	public void cmdInsert(String csTable)
	{
		m_csTable = csTable;
		m_csOperation = "Insert"; 
	}
	
	public void cmdUpdate(String csTable, String csWhere)
	{
		m_csTable = csTable;
		m_csWhere = csWhere;
		m_csOperation = "Update";		
	}

	public void cmdUpdate(String csTable, String csWhere, String csOrder)
	{
		m_csTable = csTable;
		m_csWhere = csWhere;
		m_csOrder = csOrder;
		m_csOperation = "Update";		
	}

	public void cmdUpdate(String csTable, String csWhere, String csOrder, int nNbRows)
	{
		m_csTable = csTable;
		m_csWhere = csWhere;
		m_csOrder = csOrder;
		m_nNbRows = nNbRows; 
		m_csOperation = "Update";		
	}
	
	public void cmdSelect(String csSelect)
	{
		m_csSelect = csSelect;
		m_csOperation = "Select";
	}

	public void setCol(String csColName, String csValue)
	{
		checkArrCol();
		ColValue col = new ColValueString(csColName, csValue);
		m_arrCol.add(col);
	}
	
	public void setCol(String csColName, int nValue)
	{
		checkArrCol();
		ColValue col = new ColValueInt(csColName, nValue);
		m_arrCol.add(col);
	}
	
	public void setColNow(String csColName)
	{
		checkArrCol();
		ColValue col = new ColValueTimestamp(csColName, null);
		m_arrCol.add(col);
	}
	
	public void setCol(String csColName, boolean bValue)
	{
		checkArrCol();
		ColValue col = new ColValueBoolean(csColName, bValue);
		m_arrCol.add(col);
	}
	
	public void setCol(String csColName, double dValue)
	{
		checkArrCol();
		ColValue col = new ColValueDouble(csColName, dValue);
		m_arrCol.add(col);
	}
	
	public void setParam(String csId, String csValue)
	{
		checkArrParam();
		ColValue col = new ColValueString(csId, csValue);
		m_arrParam.add(col);
	}
	
	public void setParam(String csId, int nValue)
	{
		checkArrParam();
		ColValue col = new ColValueInt(csId, nValue);
		m_arrParam.add(col);
	}
	
//	public void execSQLDebug()
//	{
//		m_resultSet = null;
//		m_csRequest = buildSelectClause();
//		for(int n=0; n<10000; n++)
//		{
//			if(m_curConnection != null)
//			{
//				try
//				{
//					m_PreparedStatement = m_curConnection.m_dbConnection.prepareStatement(m_csRequest);
//				}
//				catch (SQLException e)
//				{
//					LogSQLException.log(e);
//				}
//			}
//		}
//	}
	
	public boolean execSQL(DbConnectionBase con)
	{
		m_resultSet = null;
		if(m_csOperation != null)
		{
			if(m_csOperation.equalsIgnoreCase("Select"))
			{
				m_csRequest = buildSelectClause();
				prepare(con, m_csRequest, false);
				
				int nNbParam = getNbParam();
				for(int nParam=0; nParam<nNbParam; nParam++)
				{
					ColValue colValue = getParamAtOrder(nParam);
					if(colValue != null)
						setColParam(nParam, colValue);
				}
				
				m_resultSet = executeSelect();
				if(m_resultSet != null)
					return true;
				return false;
			}
			else if(m_csOperation.equalsIgnoreCase("Insert"))
			{
				m_csRequest = buildInsertClause();
				prepare(con, m_csRequest, false);
				
				if(m_arrCol != null)
				{
					for(int n=0; n<m_arrCol.size(); n++)
					{
						ColValue col = m_arrCol.get(n);
						setColParam(n, col); 
					}			
				}
				
				int n = executeInsert();
				if(n > 0)
					return true;
				return false;
			}
			else if(m_csOperation.equalsIgnoreCase("Update"))
			{
				m_csRequest = buildUpdateClause();
				prepare(con, m_csRequest, false);
				
				int nCol=0;
				if(m_arrCol != null)
				{
					for(; nCol<m_arrCol.size(); nCol++)
					{
						ColValue col = m_arrCol.get(nCol);
						setColParam(nCol, col); 
					}			
				}
				
				int nNbParam = getNbParam();
				for(int nParam=0; nParam<nNbParam; nParam++)
				{
					ColValue colValue = getParamAtOrder(nParam);
					if(colValue != null)
						setColParam(nCol+nParam, colValue);
				}
				
				
				int n = executeUpdate();
				if(n > 0)
					return true;
				return false;
			}
		}
		return false;
	}
	
	public boolean fetch()
	{
		if(m_resultSet != null)
		{
			try
			{
				return m_resultSet.next();
			} 
			catch (SQLException e)
			{
			}
		}
		return false;
	}
	
	public String getCol(String csName)
	{
		String cs = null;
		if(m_resultSet != null)
		{
			try
			{
				cs = m_resultSet.getString(csName);
			} 
			catch (SQLException e)
			{
			}
		}
		return cs;
	}
	
	public String getCol(int n0BasedColId)
	{
		String cs = null;
		if(m_resultSet != null)
		{			
			try
			{
				cs = m_resultSet.getString(n0BasedColId+1);
			} 
			catch (SQLException e)
			{
			}
		}
		return cs;
	}
	
	public int getColAsInt(String csName)
	{
		int n = 0;
		if(m_resultSet != null)
		{
			try
			{
				n = m_resultSet.getInt(csName);
			} 
			catch (SQLException e)
			{
			}
		}
		return n;
	}
	
	public Date getColAsDate(String csName)
	{
		Date date = null;
		if(m_resultSet != null)
		{			
			try
			{
				date = m_resultSet.getDate(csName);
			} 
			catch (SQLException e)
			{
				int n = 0;
			}
		}
		return date;
	}
	
	public Date getColAsDate(int n0BasedColId)
	{
		Date date = null;
		if(m_resultSet != null)
		{			
			try
			{
				date = m_resultSet.getDate(n0BasedColId+1);
			} 
			catch (SQLException e)
			{
			}
		}
		return date;
	}
	
	public Timestamp getColAsTimestamp(String csName)
	{
		Timestamp timestamp = null;
		if(m_resultSet != null)
		{			
			try
			{
				timestamp = m_resultSet.getTimestamp(csName);
			} 
			catch (SQLException e)
			{
				int n = 0;
			}
		}
		return timestamp;
	}
	
	public Timestamp getColAsTime(int n0BasedColId)
	{
		Timestamp timestamp = null;
		if(m_resultSet != null)
		{			
			try
			{
				timestamp = m_resultSet.getTimestamp(n0BasedColId+1);
			} 
			catch (SQLException e)
			{
			}
		}
		return timestamp;
	}
	
	
	public int getColAsInt(int n0BasedColId)
	{
		int n = 0;
		if(m_resultSet != null)
		{			
			try
			{
				n = m_resultSet.getInt(n0BasedColId+1);
			} 
			catch (SQLException e)
			{
			}
		}
		return n;
	}
	
	public boolean getColAsBoolean(String csName)
	{
		boolean b = false;
		if(m_resultSet != null)
		{
			try
			{
				b = m_resultSet.getBoolean(csName);
			} 
			catch (SQLException e)
			{
			}
		}
		return b;
	}
	
	public boolean getColAsBoolean(int n0BasedColId)
	{
		boolean b = false;
		if(m_resultSet != null)
		{			
			try
			{
				b = m_resultSet.getBoolean(n0BasedColId+1);
			} 
			catch (SQLException e)
			{
			}
		}
		return b;
	}
	
	private String buildInsertClause()
	{
		String csRequest = "Insert into " + m_csTable;
		if(m_arrCol != null)
		{
			String csNames = "(";
			String csValues = "(";
			for(int n=0; n<m_arrCol.size(); n++)
			{
				if(n != 0)
				{
					csNames += ", ";
					csValues += ", ";
				}
				
				ColValue col = m_arrCol.get(n);
				csNames += col.m_csName;
				csValues += "?";
				//csValues += "'" + col.getValueAsString() + "'";
			}
			
			csNames += ")";
			csValues += ")";
	 		
			csRequest += csNames + " Values " + csValues;
		}
		return csRequest;		
	}
	
	private String buildSelectClause()
	{
		m_csWhere = m_csSelect;
		return buildWhere();
	}
	
	private String buildUpdateClause()
	{
		String csRequest = "Update " + m_csTable + " set ";
		for(int n=0; n<m_arrCol.size(); n++)
		{
			if(n != 0)
				csRequest += ", ";
			
			ColValue col = m_arrCol.get(n);
			String cs = col.m_csName + "=?";	// + col.getValueAsString() + "'";
			csRequest += cs; 
		}
		if(m_csWhere != null)
		{
			csRequest += " Where ";
			String csWhere = buildWhere();
			csRequest += csWhere;
		}
		
		if(m_csOrder != null)
		{
			csRequest += " Order by " + m_csOrder;
		}
		
		if(m_nNbRows != -1)
		{
			csRequest += " Limit " + m_nNbRows;
		}
		
		return csRequest;
	}
	
	private String buildWhere()
	{
		int nOrder = 0;
		String csResult = "";
		String csRight = m_csWhere;
		while(csRight != null)
		{
			int nSep = csRight.indexOf(':');
			if(nSep != -1)
			{
				String csLeft = csRight.substring(0, nSep);
				csRight = csRight.substring(nSep+1);			
	
				csResult += csLeft + "? ";
				int nNext = csRight.indexOf(' ');
				String csKey = null;
				if(nNext != -1)
				{
					csKey = csRight.substring(0, nNext);
					csRight = csRight.substring(nNext+1);
				}
				else
				{
					csKey = csRight;
					csRight = null;
				}
				if(csKey != null)
				{
					ColValue colValue = getParam(csKey);
					if(colValue != null)
					{
						colValue.setOrder(nOrder);
						nOrder++;
					}
				}
			}
			else
			{
				csResult += csRight; 
				csRight = null;
			}
		}		
		
		return csResult;
	}
	
	private void checkArrCol()
	{
		if(m_arrCol == null)
		{
			m_arrCol = new ArrayList<ColValue>();
		}
	}
	
	private void checkArrParam()
	{
		if(m_arrParam == null)
		{
			m_arrParam = new ArrayList<ColValue>();
		}
	}
	
	private ColValue getParam(String csKey)
	{
		ColValue colValue = null;
		if(m_arrParam != null)
		{
			for(int n=0; n<m_arrParam.size(); n++)
			{
				colValue = m_arrParam.get(n);
				if(colValue.hasName(csKey))
					return colValue; 
			}
		}
		return null;
	}
	
	private ColValue getParamAtOrder(int nOrder)
	{
		ColValue colValue = null;
		if(m_arrParam != null)
		{
			for(int n=0; n<m_arrParam.size(); n++)
			{
				colValue = m_arrParam.get(n);
				if(colValue.isOrder(nOrder))
					return colValue; 
			}
		}
		return null;
	}
	
	private int getNbParam()
	{
		if(m_arrParam != null)
			return m_arrParam.size();
		return 0;
	}	
	
	private String m_csRequest = null;
	private String m_csTable = null;
	private String m_csWhere = null;
	private String m_csOrder = null;
	private String m_csSelect = null;
	private int m_nNbRows = -1;
	private String m_csOperation = null;
	private ArrayList<ColValue> m_arrCol = null;
	private ArrayList<ColValue> m_arrParam = null;
	private ResultSet m_resultSet = null;
}
