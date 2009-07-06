/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 15 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.sqlSupport;

import jlib.log.Log;
import jlib.log.StackStraceSupport;
import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;
import nacaLib.varEx.VarAndEdit;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SQLCursor  extends CJMapObject // extends SQLCursor 
{
	public SQLCursor(BaseProgramManager programManager)
	{
		m_programManager = programManager;
		m_SQL = null;
		m_bOpen = false;
		m_programManager.registerCursor(this);
	}
	
	public void setQuery(String csQuery)
	{
		//VarBuffer working = m_programManager.m_DataDivision.getWorkingStorageSectionVarBuffer();
		//CESMEnvironment env = m_programManager.m_CESMEnv;
		//CSQLStatus sqlstatus = m_programManager.getSQLStatus();
		//m_SQL = new SQL(working, env, csQuery, true, sqlstatus);
		
		//String csFileLine = StackStraceSupport.getFileLineAtStackDepth(3);	// Caller File Line
		//m_SQL = m_programManager.getOrCreateSQLForCursor(csQuery, this);//, csFileLine);
		m_SQL = m_programManager.getOrCreateSQLGeneral(csQuery, this);//, csFileLine);
	}
	
	public void setMustBeNamed(boolean bNameToSet)
	{
		m_bNameToSet = bNameToSet;
	}
	
	public boolean getMustNameCursor()
	{
		return m_bNameToSet;
	}
	
//	public SQLCursor(ProgramManager programManager, VarBuffer Working, CESMEnvironment env, String csQuery, CSQLStatus sqlstatus)
//	{
//		m_programManager = programManager;
//		m_SQL = new SQL(Working, env, csQuery, true, sqlstatus);
//		m_bOpen = false;
//	}
	
	public CSQLStatus open()
	{
		CSQLStatus sqlStatus = m_programManager.getSQLStatus();
		if(m_SQL != null)
		{
			if(m_bOpen)
				sqlStatus.setSQLCode(SQLCode.SQL_CURSOR_ALREADY_OPENED);
			else
				sqlStatus.reset();
		}		
		m_bOpen = true;
		return sqlStatus;
	}
	
	public CSQLStatus close()
	{
		CSQLStatus sqlStatus = m_programManager.getSQLStatus();
		if(m_SQL != null)
		{
			if(!m_bOpen)
				sqlStatus.setSQLCode(SQLCode.SQL_CURSOR_NOT_OPEN);				
			else
				sqlStatus.reset();
			m_SQL.close();			
		}
		else	// too many close
		{
			sqlStatus.setSQLCode(SQLCode.SQL_CURSOR_NOT_OPEN);
		}
		m_bOpen = false;

		m_SQL = null;
		m_SQLCursorFetch = null;
		return sqlStatus;
	}
	
	public void closeIfOpen()
	{
		if(m_bOpen)
		{
			close();
		}
	}
		
	public boolean isOpen()
	{
		return m_bOpen;
	}
	
	public SQLCursor param(int nName, VarAndEdit var)
	{
		if(isLogSql)
			Log.logDebug("param "+nName+"="+var.getLoggableValue());
		if(m_SQL != null)
			m_SQL.param(nName, var);
		return this;
	}
	
	public SQLCursor param(String csName, VarAndEdit var)
	{
		if(isLogSql)
			Log.logDebug("param "+csName+"="+var.getLoggableValue());
		if(m_SQL != null)
			m_SQL.param(csName, var);
		return this;
	}
	
	public SQLCursor param(int nName, int nValue)
	{
		if(isLogSql)
			Log.logDebug("param "+nName+"="+nValue);
		m_SQL.param(nName, nValue);
		return this;
	}
	
	public SQLCursor param(String csName, int nValue)
	{
		if(isLogSql)
			Log.logDebug("param "+csName+"="+nValue);
		if(m_SQL != null)
			m_SQL.param(csName, nValue);
		return this;
	}

	public SQLCursor param(int nName, double dValue)
	{
		if(isLogSql)
			Log.logDebug("param "+nName+"="+dValue);
		if(m_SQL != null)
			m_SQL.param(nName, dValue);
		return this;
	}
	
	public SQLCursor param(String csName, double dValue)
	{
		if(isLogSql)
			Log.logDebug("param "+csName+"="+dValue);
		if(m_SQL != null)
			m_SQL.param(csName, dValue);
		return this;
	}
		
	public SQLCursor param(int nName, String csValue)
	{
		if(isLogSql)
			Log.logDebug("param "+nName+"="+csValue);
		if(m_SQL != null)
			m_SQL.param(nName, csValue);
		return this;
	}
	
	public SQLCursor param(String csName, String csValue)
	{	
		if(isLogSql)
			Log.logDebug("param "+csName+"="+csValue);
		if(m_SQL != null)
			m_SQL.param(csName, csValue);
		return this;
	}
	
	public SQLCursor onWarningGoto(Paragraph paragraphSQGErrorGoto)
	{
		if(isLogSql)
			Log.logDebug("onWarningGoto "+paragraphSQGErrorGoto.toString());
		if(m_SQL != null)
			m_SQL.onWarningGoto(paragraphSQGErrorGoto);
		return this;
	}
	
	public SQLCursor onWarningGoto(Section section)
	{
		if(isLogSql)
			Log.logDebug("onWarningGoto "+section.toString());
		if(m_SQL != null)
			m_SQL.onWarningGoto(section);
		return this;
	}
	
	public SQLCursor onWarningContinue()
	{
		if(isLogSql)
			Log.logDebug("onWarningContinue");
		if(m_SQL != null)
			m_SQL.onWarningContinue();
		return this;
	}
	
	public SQLCursor onErrorGoto(Paragraph paragraphSQGErrorGoto)
	{
		if(isLogSql)
			Log.logDebug("onErrorGoto "+paragraphSQGErrorGoto.toString());
		if(m_SQL != null)
			m_SQL.onErrorGoto(paragraphSQGErrorGoto);
		return this;
	}
	
	public SQLCursor onErrorGoto(Section section)
	{
		if(isLogSql)
			Log.logDebug("onErrorGoto "+section.toString());
		if(m_SQL != null)
			m_SQL.onErrorGoto(section);
		return this;
	}
	
	public SQLCursor onErrorContinue()
	{
		if(isLogSql)
			Log.logDebug("onErrorContinue");
		if(m_SQL != null)
			m_SQL.onErrorContinue();
		return this;
	}
	
	public SQLCursorFetch fetch(BaseEnvironment env)
	{
		if(m_SQLCursorFetch == null)
			m_SQLCursorFetch = new SQLCursorFetch(m_bOpen, m_SQL);
		if(m_bOpen && m_SQL != null)
		{
			m_SQL.resetExecuted(env);
			m_SQL.resetErrorManager();
			// PJD ROWID Support:
			//	if(m_SQL.hasRowIdGenerated())
			//{
			//	m_sqlItemRowId = new CSQLIntoItem();	
			//	m_SQL.into(m_sqlItemRowId);
			//}			
		}
		return m_SQLCursorFetch;		
	}
	
	public void setName(String csProgramName, String csName)
	{
		String cs = csProgramName + csName;
		m_csUniqueName = cs.toUpperCase();
	}

	public String getUniqueCursorName()	// use for updatable cusrot that use Cursor Name
	{
		return m_csUniqueName;
	}
	
	private SQLCursorFetch m_SQLCursorFetch = null; 
	public /*private*/ SQL m_SQL = null;
	private boolean m_bOpen = false;
	private BaseProgramManager m_programManager = null;
	private String m_csUniqueName = null;
	private boolean m_bNameToSet = false;	
	
	public SQLCursor setHoldability(boolean b)
	{
		m_SQL.setHoldability(b);
		return this;
	}
}
