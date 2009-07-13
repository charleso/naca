/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.accounting;

import java.sql.Date;
import java.util.NoSuchElementException;
import java.util.Stack;

import jlib.log.Log;
import jlib.misc.StopWatchNano;
import jlib.sql.DbConnectionBase;
import jlib.sql.DbPreparedStatement;
import nacaLib.base.JmxGeneralStat;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.basePrgEnv.CurrentUserInfo;

public class AccountingRecordTrans
{
	private BaseResourceManager m_baseResourceManager = null;
	
	public AccountingRecordTrans(BaseResourceManager baseResourceManager)
	{
		m_baseResourceManager = baseResourceManager;
		m_accountingRessourceDesc = m_baseResourceManager.getAccountingRessourceDesc();
		if(m_accountingRessourceDesc != null)
		{
			m_csMachineId = m_accountingRessourceDesc.getMachineId();
			m_csTomcatId = m_accountingRessourceDesc.getTomcatId();
			m_nUniqueSessionRequestId = m_baseResourceManager.getUniqueSessionRequestId();
		}
	}
	
	public void startRunTransaction(String csCurrentTransaction)
	{
		m_bFilled = false;
		m_nTransactionId++;
		m_nNbSelect = 0;
		m_nNbInsert = 0;
		m_nNbUpdate = 0;
		m_nNbDelete = 0;
		m_nNbFetchCursor = 0;
		m_nNbCursorOpen = 0;
		m_swnDbTimeRunTransaction.reset();
		//JmxGeneralStat.startRunTransaction();
		
		createNewAccountingRecord(csCurrentTransaction, "");
	}

	public void endRunTransaction(String csCurrentTransaction, CriteriaEndRunMain criteria)
	{
		if(BaseResourceManager.getUsingJmx())
		{
			long lRuntimeTrans_ns = m_swnDbTimeRunTransaction.getElapsedTime();
			JmxGeneralStat.endRunTransaction(criteria, lRuntimeTrans_ns / 1000000, m_lSumDbTimeIO_ns / 1000000);
		}
		
		endRunProgram(criteria);
	}

	public AccountingRecordProgram createNewAccountingRecord(String csCurrentTransaction, String csTermId)
	{
		m_csCurrentTransaction = csCurrentTransaction;
		m_csTerminalId = csTermId;
		AccountingRecordProgram accountingRecord = new AccountingRecordProgram();
		m_accountingStack.push(accountingRecord);
		return accountingRecord;
	}
	
	public void endRunProgram(CriteriaEndRunMain criteria)
	{
		if(m_accountingRessourceDesc != null)
		{
			AccountingRecordProgram accountingRecordProgram = m_accountingStack.pop();
			int nDepthLevel = m_accountingStack.size();
			if(m_accountingRessourceDesc.canWrite(nDepthLevel))
			{
				accountingRecordProgram.endRunProgram(criteria);
				write(accountingRecordProgram, nDepthLevel);
			}
		}
	}
	
	public void write(AccountingRecordProgram accountingRecordProgram, int nDepthLevel)
	{
		DbConnectionBase dbConnection = m_accountingRessourceDesc.getConnection();
		if(dbConnection != null)
		{
			DbPreparedStatement stInsert = m_accountingRessourceDesc.getInsertStatement(dbConnection);
			if(stInsert != null)
			{
				try
				{
					int nCol = 0;
					
					stInsert.setColParam(nCol++, m_nUniqueSessionRequestId);	// INTEGER SESSIONID
					stInsert.setColParam(nCol++, m_nTransactionId);	// TRANSACTIONID
		
					long l = accountingRecordProgram.getTimeDateStart();
					Date date = new Date(l);
					stInsert.setColParam(nCol++, date);	// START_TIMESTAMP
		
					stInsert.setColParam(nCol++, nDepthLevel);	// LEVEL_DEPTH
					stInsert.setColParam(nCol++, m_csCurrentTransaction);	// TRANSACTIONNAME
					String csProg = accountingRecordProgram.getProgramName();
					if(csProg.length() > 8)
						csProg = csProg.substring(0, 8);			
					stInsert.setColParam(nCol++, csProg);	// PROGRAMNAME
					stInsert.setColParam(nCol++, m_csSessionType);	// SESSIONTYPE
					stInsert.setColParam(nCol++, m_csMachineId);	// MACHINEID
					stInsert.setColParam(nCol++, m_csTomcatId);	// TOMCATID
					stInsert.setColParam(nCol++, accountingRecordProgram.getRunTime_ms());	// RUNTIME_MS
					stInsert.setColParam(nCol++, m_csTerminalId);	// TERMINALID
					stInsert.setColParam(nCol++, m_currentUserInfo.m_csLUName);	// LUNAME
					stInsert.setColParam(nCol++, m_currentUserInfo.m_csUserLdapId);	// USERLDAPID
					stInsert.setColParam(nCol++, accountingRecordProgram.getCriteriaEnd());	// CRITERIAEND
					stInsert.setColParam(nCol++, m_nNbSelect);	// NBSELECT
					stInsert.setColParam(nCol++, m_nNbInsert);	// NBINSERT, 
					stInsert.setColParam(nCol++, m_nNbUpdate);	// NBUPDATE, 
					stInsert.setColParam(nCol++, m_nNbDelete);	// NBDELETE, 
					stInsert.setColParam(nCol++, m_nNbCursorOpen);	// NBOPENCURSOR, 
					stInsert.setColParam(nCol++, m_nNbFetchCursor);	// NBFETCHCURSOR, 
					stInsert.setColParam(nCol++, m_currentUserInfo.m_csPub2000ProfitCenter);	// PROFITCENTERPUB2000, 
					stInsert.setColParam(nCol++, m_currentUserInfo.m_csPub2000UserId);	// USERIDPUB2000
					stInsert.setColParam(nCol++, StopWatchNano.getMilliSecond(accountingRecordProgram.getRunTimeIO_ns()));
					stInsert.setColParam(nCol++, m_nNetwork_ms);
					int n = stInsert.executeInsert();
					if(n != 1)
					{
						Log.logCritical("Could not insert accounting record");
					}
				}
				catch(Exception e)
				{
					Log.logCritical("Could not insert accounting record, because of exception " + e.getMessage());
				}
			}
			m_accountingRessourceDesc.returnConnection(dbConnection);
		}		
	}
	
	
	public void incDelete()
	{
		m_nNbDelete++;
	}
	
	public void incSelect()
	{
		m_nNbSelect++;
	}

	public void incCursorOpen()
	{
		m_nNbCursorOpen++;
	}
	
	public void incFetchCursor()
	{
		m_nNbFetchCursor++;
	}

	public void incUpdate()
	{
		m_nNbUpdate++;
	}
	
	public void incInsert()
	{
		m_nNbInsert++;
	}
	
	public void startDbIO()
	{
		m_swnDbTimeIO.reset();
	}

	public void endDbIO()
	{
		m_lDbTimeIO_ns = m_swnDbTimeIO.getElapsedTimeReset();
		m_lSumDbTimeIO_ns += m_lDbTimeIO_ns; 
		//JmxGeneralStat.reportDbTimeIo_ns(m_lDbTimeIO_ns / 1000000);
		try
		{
			AccountingRecordProgram prg = m_accountingStack.firstElement();
			if (prg != null)
				prg.reportDBIOTime(m_lDbTimeIO_ns);
		}
		catch (NoSuchElementException e)
		{
		}
	}

	public void setSessionPub2000Info(BaseSession session, String csProfitCenter, String csUserId)
	{
		m_currentUserInfo.m_csPub2000ProfitCenter = csProfitCenter;
		m_currentUserInfo.m_csPub2000UserId = csUserId;
		
		if(session != null)
		{
			session.fillCurrentUserInfo(m_currentUserInfo);
			m_csSessionType = session.getType();
			m_nNetwork_ms = session.getNetwork_ms();
		}
		else
		{
			m_csSessionType = "Batch";
		}
		m_bFilled = true;
	}
	
	public boolean isFilled()
	{
		return m_bFilled;
	}
	
	private int m_nNbSelect = 0;
	private int m_nNbInsert = 0;
	private int m_nNbUpdate = 0;
	private int m_nNbDelete = 0;
	private int m_nNbFetchCursor = 0;
	private int m_nNbCursorOpen = 0;
	private long m_lDbTimeIO_ns = 0;	// Time in nano seconds
	private long m_lSumDbTimeIO_ns = 0;
	private StopWatchNano m_swnDbTimeIO = new StopWatchNano();
	private StopWatchNano m_swnDbTimeRunTransaction = new StopWatchNano();
		
	private String m_csMachineId = "";
	private String m_csTomcatId = "";
	
	private String m_csSessionType = "";
	private String m_csTerminalId = "";
	
  	//int m_nTransactionId = 0;
	private int m_nUniqueSessionRequestId = 0;
	private String m_csCurrentTransaction = "";
  	
	private CurrentUserInfo m_currentUserInfo = new CurrentUserInfo();
	
	private AccountingRessourceDesc m_accountingRessourceDesc = null;
	private Stack<AccountingRecordProgram> m_accountingStack = new Stack<AccountingRecordProgram>() ;
	private int m_nTransactionId = 0;
	private boolean m_bFilled = false;
	
	private int m_nNetwork_ms = 0;
}
