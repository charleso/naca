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
/*
 * Created on 11 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.sqlSupport;

import java.sql.SQLException;

import jlib.misc.StringUtil;

import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CSQLStatus extends CJMapObject
{
	private int m_nSQLCode = 0 ;
	private int m_nLastNbRecordUpdatedInsertedDeleted = 0;	// Accessible by getSQLDiagnosticCode(3)
	private String m_csQueryString = null;
	//private String m_csSourceFileLine = null;
	private String m_csMethod = null;
	private String m_csReason = null;
	private String m_csReasonParams = null;
	private String m_csReasonValues = null;
	
	public CSQLStatus()
	{
	}

	public int getSQLCode()
	{
		return m_nSQLCode ;
	}
	
	public void reset()
	{
		doSetSQLCode(0) ;
		m_csMethod = null;
		m_csReason = null;
		m_csReasonParams = null;
		m_csReasonValues = null;
		//m_csQueryString = null;
		//m_csSourceFileLine = null;
	}
	
	public void setSQLCode(int n)
	{
		reset();
		doSetSQLCode(n) ;
	}
	
	public void setSQLCodeOk()
	{
		reset();
		doSetSQLCode(SQLCode.SQL_OK.getMainCode());
	}
	
	public void setSQLCode(SQLException e)
	{
		reset();
		doSetSQLCode(e.getErrorCode());
		m_csReason = "SQL Exception (" + m_nSQLCode + "):" + e.getMessage()  + " SQLState="+ e.getSQLState();
	}

	public void setSQLCode(String csMethod, SQLException e, String csQueryString/*, String csSourceFileLine*/, SQL sql)
	{		
		doSetSQLCode(e.getErrorCode());
		m_csMethod = csMethod;		
		m_csReason = "SQL Exception (" + m_nSQLCode + "):" + e.getMessage()  + " SQLState="+ e.getSQLState();
		if(sql != null)
		{
			m_csReasonParams = sql.getDebugParams();
			m_csReasonValues = sql.getDebugValues();
		}	
		m_csQueryString = csQueryString;
	}
	
	public void fillLastSQLCodeErrorText()
	{
		TempCache cache = TempCacheLocator.getTLSTempCache();
		cache.fillLastSQLCodeErrorText(this);
	}
	
	public void setSQLCode(String csMethod, int nCode, String csReason, String csQueryString)	//, String csSourceFileLine)
	{
		m_csReasonParams = null;
		m_csReasonValues = null;
	
		doSetSQLCode(nCode);
		m_csMethod = csMethod;
		m_csReason = csReason;
		m_csQueryString = csQueryString;
	}
	
	public void setQuery(String csQueryString)
	{
		m_csQueryString = csQueryString;
	}
	
	public boolean isLastSQLCodeAnError()
	{
		return SQLCode.isError(m_nSQLCode);
	}
	
	public boolean isLastSQLCodeConnectionKiller()
	{
		return SQLCode.isConnectionKillerSQLCode(m_nSQLCode);
	}

	public int getSQLDiagnosticCode(int n)
	{
		// See http://publib.boulder.ibm.com/infocenter/dzichelp/index.jsp?topic=/com.ibm.db2.doc.apsg/bjnqmstr370.htm
		if(n == 3)
			return m_nLastNbRecordUpdatedInsertedDeleted;			
		return 0;
	}
	
	void setLastNbRecordUpdatedInsertedDeleted(int n)
	{
		m_nLastNbRecordUpdatedInsertedDeleted = n;
	}
	
	public CSQLStatus onErrorGoto(Paragraph para)
	{
		SQLErrorManager sqlErrorManager = new SQLErrorManager();
		sqlErrorManager.manageOnErrorGoto(para, this);		
		return this;
	}
	
	public CSQLStatus onErrorGoto(Section section)
	{
		SQLErrorManager sqlErrorManager = new SQLErrorManager();
		sqlErrorManager.manageOnErrorGoto(section, this);		
		return this;
	}
	
	public CSQLStatus onErrorContinue()
	{
		SQLErrorManager sqlErrorManager = new SQLErrorManager();
		sqlErrorManager.manageOnErrorContinue(this);		
		return this;
	}
	
	public CSQLStatus onWarningGoto(Paragraph paragraphSQGErrorGoto)
	{
		// TODO
		return this;
	}
	
	public CSQLStatus onWarningGoto(Section section)
	{
		// TODO
		return this;
	}
	
	public CSQLStatus onWarningContinue()
	{
		// TODO
		return this;
	}	
	
	public CSQLStatus onNotFoundContinue()
	{
		return this;
	}
	
	public String getReason()
	{
		return m_csReason;
	}
	
	public String getReasonParams()
	{
		return m_csReasonParams;
	}
	
	public String getReasonValues()
	{
		return m_csReasonValues;
	}
	
	public String getQueryString()
	{
		return m_csQueryString;
	}
	
	public String getMethod()
	{
		return m_csMethod;
	}
	
//	public String getSourceFileLine()
//	{
//		return m_csSourceFileLine;
//	}
//	
	public String toString()
	{
		StringBuffer sb = getAsStringBuffer();
		return sb.toString();
	}
	
	public StringBuffer getAsStringBuffer()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getReason());
		sb.append(" | SQLCode:"+m_nSQLCode);
		sb.append(" | Query:");
		sb.append(m_csQueryString);
		if (!StringUtil.isEmpty(m_csReasonParams))
		{
			sb.append(" | Params:");
			sb.append(m_csReasonParams);
		}
		if (StringUtil.isEmpty(m_csReasonValues))
		{
			sb.append(" | Values:");
			sb.append(m_csReasonValues);
		}
		return sb;
	}
	
	private void doSetSQLCode(int n)
	{
		m_nSQLCode = n;
		if(m_nSQLCode == 100)
		{
			int gg = 0;
		}
	}
}