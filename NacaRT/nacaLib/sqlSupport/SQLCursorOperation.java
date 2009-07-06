/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 17 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.sqlSupport;

import jlib.sql.*;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;
import nacaLib.varEx.Var;
// PJD ROWID Support:import oracle.sql.ROWID;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SQLCursorOperation
{
//	public SQLCursorOperation(CSQLConnection sqlConnection, VarBuffer Working, SQLCursor sqlCursor, String csClause, CSQLStatus sqlStatus)
	public SQLCursorOperation(BaseProgramManager programManager, SQLCursor sqlCursor, String csClause)
	{
		DbConnectionBase sqlConnection = programManager.getEnv().getSQLConnection();
		if(sqlConnection.supportCursorName())
		{
			String csCursorName = sqlCursor.getUniqueCursorName();
			if(csCursorName != null)
			{
				csClause += " WHERE CURRENT OF " + csCursorName;
				m_sqlUpdateDelete = new SQL(programManager, csClause, null/*, ""*/, 0);
			}
		}
		else	// Row Id must have been generated in the cursor 
		{
			// PJD ROWID Support:
			/*
			ROWID rowId = sqlCursor.getCurrentRowId();
			if(rowId != null)
			{
				csClause += " WHERE ROWID=?";
				m_sqlUpdateDelete = new SQL(Working, sqlConnection, csClause, false);
				m_sqlUpdateDelete.setCurrentRowId(rowId);
			}
			*/
		}
				
		manageOperationEnding();
	}
	
		// Update cursor
	public SQLCursorOperation value(int nName, int nValue)
	{
		String csName = String.valueOf(nName);
		return value(csName, nValue);
	}

	public SQLCursorOperation value(String csName, int nValue)
	{
		if(m_sqlUpdateDelete != null)
			m_sqlUpdateDelete.value(csName, nValue);
		return this;
	}
	
	public SQLCursorOperation value(int nName, double dValue)
	{
		String csName = String.valueOf(nName);
		return value(csName, dValue);
	}

	public SQLCursorOperation value(String csName, double dValue)
	{
		if(m_sqlUpdateDelete != null)
			m_sqlUpdateDelete.value(csName, dValue);
		return this;
	}
	
	public SQLCursorOperation value(int nName, String csValue)
	{
		String csName = String.valueOf(nName);
		return value(csName, csValue);
	}

	public SQLCursorOperation value(String csName, String csValue)
	{
		if(m_sqlUpdateDelete != null)
			m_sqlUpdateDelete.value(csName, csValue);
		return this;
	}

	public SQLCursorOperation value(int nName, Var varValue)
	{
		String csName = String.valueOf(nName);
		return value(csName, varValue);
	}

	public SQLCursorOperation value(String csName, Var varValue)
	{
		if(m_sqlUpdateDelete != null)
			m_sqlUpdateDelete.value(csName, varValue);
		return this;	
	}
	
	public SQLCursorOperation onErrorGoto(Paragraph paragraphSQGErrorGoto)
	{
		if(m_sqlUpdateDelete != null)
			m_sqlUpdateDelete.onErrorGoto(paragraphSQGErrorGoto);
		return this;
	}
	
	public SQLCursorOperation onErrorGoto(Section section)
	{
		if(m_sqlUpdateDelete != null)
			m_sqlUpdateDelete.onErrorGoto(section);
		return this;
	}	
		
	public SQLCursorOperation onErrorContinue()
	{
		if(m_sqlUpdateDelete != null)
			m_sqlUpdateDelete.onErrorContinue();
		return this;
	}
	
	private void manageOperationEnding()
	{
		if(m_sqlUpdateDelete != null)
			m_sqlUpdateDelete.manageOperationEnding();
	}
	
	private SQL m_sqlUpdateDelete = null;
}
