/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.basePrgEnv;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Hashtable;

import jlib.log.Log;
import jlib.misc.DateUtil;
import jlib.misc.Time_ms;
import nacaLib.CESM.CESMLink;
import nacaLib.CESM.CESMQueueManager;
import nacaLib.CESM.CESMReadQueue;
import nacaLib.CESM.CESMReturnCode;
import nacaLib.CESM.CESMStart;
import nacaLib.CESM.CESMWriteQueue;
import nacaLib.CESM.CESMXctl;
import nacaLib.base.CJMapObject;
import nacaLib.exceptions.AbortSessionException;
import nacaLib.exceptions.CESMAbendException;
import nacaLib.exceptions.CESMReturnException;
import nacaLib.misc.CCESMFakeMethodContainer;
import nacaLib.misc.CCommarea;
import nacaLib.misc.Pointer;
import nacaLib.program.CESMCommandCode;
import nacaLib.program.CJMapRunnable;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;
import nacaLib.varEx.Form;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarAndEdit;

public class BaseCESMManager extends CJMapObject
{
	protected BaseEnvironment m_CESMEnv = null ;
	
	public BaseCESMManager(BaseEnvironment env)
	{
		m_CESMEnv = env;
	}
	
	public BaseEnvironment getEnvironment()
	{
		return m_CESMEnv ;
	}
	
	public void returnTrans(String csTransaction, Var v1, VarAndEdit len)
	{
		int l = len.getInt();
		returnTrans(csTransaction, v1, l, true);
	}
	public void returnTrans(Class cl, Var v1, VarAndEdit len)
	{
		returnTrans(cl.getName(), v1, len.getInt(), false);
	}
	public void returnTrans(String csTransaction, Form f1, VarAndEdit len)
	{
		returnTrans(csTransaction, f1, len.getInt(), true);
	}
	public void returnTrans(Class cl, Form f1, VarAndEdit len)
	{
		returnTrans(cl.getName(), f1, len.getInt(), false);
	}
	public void returnTrans(VarAndEdit varTransaction, Var v1, VarAndEdit len)
	{
		returnTrans(varTransaction.getString(), v1, len.getInt(), true);
	}
	public void returnTrans(VarAndEdit varTransaction, Var v1)
	{
		returnTrans(varTransaction.getString(), v1, v1.getLength(), true);
	}
	public void returnTrans(VarAndEdit varTransaction, Form form1, VarAndEdit len)
	{
		returnTrans(varTransaction.getString(), form1, len.getInt(), true);
	}	
	public void returnTrans(Class cl, Var v1)
	{
		returnTrans(cl.getName(), v1, v1.getLength(), false);
	}
	public void returnTrans(String csTransaction, Var v1)
	{
		returnTrans(csTransaction, v1, v1.getLength(), true);
	}
	public void returnTrans(String csTransaction, Var v1, int length)
	{
		returnTrans(csTransaction, v1, length, true);
	}	
	public void returnTrans(Class cl, Form form)
	{
		returnTrans(cl.getName(), form, false);
	}	
	public void returnTrans(String csTransaction, Form form)
	{
		returnTrans(csTransaction, form, true);
	}
	
	public void returnTrans()
	{
		if(isLogCESM)
			Log.logDebug("returnTrans");
		m_CESMEnv.setLastCommandCode(CESMCommandCode.RETURN);
		m_CESMEnv.setNextProgramToLoad("");
		m_CESMEnv.setCommarea(null);
		CESMReturnException excp = new CESMReturnException();
		throw excp;
	}	
	private void returnTrans(String csProgramId, Form form, boolean bResolveProgram)
	{
		if (bResolveProgram)
			csProgramId = BaseProgramLoader.ResolveTransID(csProgramId);
			
		if(isLogCESM)
			Log.logDebug("returnTrans program="+csProgramId+" Form="+form.getLoggableValue());		
		m_CESMEnv.setLastCommandCode(CESMCommandCode.RETURN);
		m_CESMEnv.setNextProgramToLoad(csProgramId) ;
		CCommarea comm = new CCommarea() ;
		comm.setVarPassedByValue(form);
		m_CESMEnv.setCommarea(comm);
		CESMReturnException excp = new CESMReturnException();
		throw excp;
	}	
	private void returnTrans(String csProgramId, Var v1, int length, boolean bResolveProgram)
	{
		if (bResolveProgram)
			csProgramId = BaseProgramLoader.ResolveTransID(csProgramId);
		if (length > v1.getLength())
			length = v1.getLength();
		
		if(isLogCESM)
			Log.logDebug("returnTrans program="+csProgramId+ " Var="+v1.getLoggableValue());		
		m_CESMEnv.setLastCommandCode(CESMCommandCode.RETURN) ;
		m_CESMEnv.setNextProgramToLoad(csProgramId) ;
		CCommarea comm = new CCommarea() ;
		comm.setVarPassedByValue(v1, length);
		m_CESMEnv.setCommarea(comm);
		CESMReturnException excp = new CESMReturnException();
		throw excp;
	}
	
	public void abend()
	{
		if(isLogCESM)
			Log.logDebug("abend");
		m_CESMEnv.setLastCommandCode(CESMCommandCode.ABEND);
		CESMAbendException e = new CESMAbendException("none");
		throw e;
	}

	public void abend(VarAndEdit v)
	{
		abend(v.getString()); 
	}
	public void abend(String cs)
	{
		if(isLogCESM)
			Log.logDebug("abend");
		m_CESMEnv.setLastCommandCode(CESMCommandCode.ABEND) ;
		CESMAbendException e = new CESMAbendException(cs);
		throw e ; 
	}

	public BaseCESMManager getAddressOfTCTUA(Pointer p)
	{
		if(isLogCESM)
			Log.logDebug("getAddressOfTCTUA");
		m_CESMEnv.setLastCommandCode(CESMCommandCode.GET_ADDRESS) ;
		//p.m_AddressOf.m_VarManager.redefinesAs(m_CESMEnv.getTCTUA());
		
		char [] acTCTUA = m_CESMEnv.getTCTUA();	
		p.m_AddressOf.setCustomBuffer(acTCTUA);
		
		return this;	
	}

	public BaseCESMManager getAddressOfTWA(Pointer p)
	{
		if(isLogCESM)
			Log.logDebug("getAddressOfTCTUA");
		// p.m_AddressOf.m_VarManager.redefinesAs(m_CESMEnv.getTWA());
		char [] acTWA = m_CESMEnv.getTWA();
		p.m_AddressOf.setCustomBuffer(acTWA);
		//p.m_AddressOf.m_VarManager.manageRedefines();
			
		return this;	
	}
	
	public BaseCESMManager getAddressOfCWA(Pointer p)
	{
		if(isLogCESM)
			Log.logDebug("getAddressOfTCTUA");
		//p.m_AddressOf.m_VarManager.redefinesAs(m_CESMEnv.getCWA());
		char [] acCWA = m_CESMEnv.getCWA();
		p.m_AddressOf.setCustomBuffer(acCWA);
		
		return this;	
	}

	public CCESMFakeMethodContainer assign()
	{
		// TODO fake method CEMS Assign
		return new CCESMFakeMethodContainer() ;
	}
	
	public BaseCESMManager ignoreCondition(String string)
	{
		if(isLogCESM)
			Log.logDebug("ignoreCondition "+string);
		m_CESMEnv.setLastCommandCode(CESMCommandCode.IGNORE) ;
		m_tabConditionHandles.remove(string);
		return this ;
	}
	public BaseCESMManager unhandleCondition(String string)
	{
		if(isLogCESM)
			Log.logDebug("unhandleCondition"+string);
		m_CESMEnv.setLastCommandCode(CESMCommandCode.HANDLE) ;
		m_tabConditionHandles.remove(string);
		return this ;
	}
	public BaseCESMManager handleCondition(String string, Paragraph par)
	{
		if(isLogCESM)
			Log.logDebug("handleCondition"+string);
		m_CESMEnv.setLastCommandCode(CESMCommandCode.HANDLE) ;
		m_tabConditionHandles.put(string, par);
		return this ;
	}
	public BaseCESMManager handleCondition(String string, Section par)
	{
		if(isLogCESM)
			Log.logDebug("handleCondition"+string);
		m_CESMEnv.setLastCommandCode(CESMCommandCode.HANDLE) ;
		m_tabConditionHandles.put(string, par);
		return this ;
	}
	protected Hashtable<String, CJMapRunnable> m_tabConditionHandles = new Hashtable<String, CJMapRunnable>();
	
	public String getLastCommandReturnCode()
	{
		return m_CESMEnv.getLastCommandReturnCode().getCode();
	}

	public int getConditionOccured()
	{
		int n = m_CESMEnv.getLastCommandReturnCode().getCondition() ;
		if(isLogCESM)
			Log.logDebug("getConditionOccured value="+n);
		return n;
	}

	public void setConditionOccured(int n)
	{
		if(isLogCESM)
			Log.logDebug("setConditionOccured value="+n);
		m_CESMEnv.setCommandReturnCode(CESMReturnCode.Select(n)) ;
	}

	public CCESMFakeMethodContainer startBrowseDataSet(String ws_Fichier)
	{
		// TODO fake Method
		return new CCESMFakeMethodContainer() ;
	}

	public CCESMFakeMethodContainer startBrowseDataSet(Var ws_Fichier)
	{
		// TODO fake Method
		return new CCESMFakeMethodContainer() ;
	}

	public CCESMFakeMethodContainer readNextDataSet(Var res_Fichier)
	{
		// TODO fake Method
		return new CCESMFakeMethodContainer() ;
	}

	public CCESMFakeMethodContainer readNextDataSet(String res_Fichier)
	{
		// TODO fake Method
		return new CCESMFakeMethodContainer() ;
	}

	public CCESMFakeMethodContainer readPreviousDataSet(Var res_Fichier)
	{
		// TODO fake Method
		return new CCESMFakeMethodContainer() ;
	}

	public String getConfig(String string)
	{
		return m_CESMEnv.getConfigOption(string) ;
	}

	public String getSQLEnvironment()
	{
		if(isLogCESM)
			Log.logDebug("getSQLEnvironment");
		return m_CESMEnv.getSQLConnection().getEnvironmentPrefix() ;
	}

	public void delayInterval(Var delay)
	{
		// delay uses format HHMMSS
		int nNextTime_s = DateUtil.getNbSecondsFromHour(delay.getInt());
		long lWaitTime_ms = nNextTime_s * 1000;
		m_CESMEnv.offsetMaxTimeLimit(lWaitTime_ms);
		Time_ms.wait_ms(lWaitTime_ms);
	}
	public void delaySeconds(Var delay)
	{
		long lWaitTime_ms = delay.getLong() * 1000;
		m_CESMEnv.offsetMaxTimeLimit(lWaitTime_ms);
		Time_ms.wait_ms(lWaitTime_ms);		
	}

	public boolean hasCredentials()
	{
		return !m_CESMEnv.getApplicationCredentials().equals("");
	}
	public String getDeclaredUserId()
	{
		if (m_CESMEnv.getApplicationCredentials().length() > 7)
		{
			return m_CESMEnv.getApplicationCredentials().substring(5, 8);
		}
		else
		{
			return m_CESMEnv.getApplicationCredentials().substring(5, 7);
		}
	}
	public String getDeclaredCompany()
	{
		return m_CESMEnv.getApplicationCredentials().substring(0, 2) ;
	}
	public String getDeclaredAgency()
	{
		return m_CESMEnv.getApplicationCredentials().substring(2, 5) ;
	}	

	public CCESMFakeMethodContainer enQ(Var enqsycr, int i)
	{
		// TODO fake method CEMS ENQ
		return new CCESMFakeMethodContainer() ;
	}

	public CCESMFakeMethodContainer deQ(Var enqsycr, int i)
	{
		// TODO fake method CEMS DEQ
		return new CCESMFakeMethodContainer() ;
	}


	public CCESMFakeMethodContainer setTDQueueClosed(String string)
	{
		// TODO fake method CEMS TD CLOSE
		return new CCESMFakeMethodContainer() ;
	}
	public CCESMFakeMethodContainer setTDQueueOpen(String string)
	{
		// TODO fake method CEMS TD CLOSE
		return new CCESMFakeMethodContainer() ;
	}

	public CCESMFakeMethodContainer writeTransiantQueue(String string)
	{
		// TODO fake method writeTransiantQueue
		return new CCESMFakeMethodContainer() ;
	}

	public CCESMFakeMethodContainer getMain()
	{
		// TODO fake method getMain
		return new CCESMFakeMethodContainer() ;
	}

	public String getCurrentDay()
	{
		Calendar cal = Calendar.getInstance() ;
		int day = cal.get(Calendar.DAY_OF_MONTH) ;
		String cs = "" + (day/10) + (day%10) ;
		return cs ;
	}

	public Calendar getCurrentDate()
	{
		Calendar cal = Calendar.getInstance() ;
		return cal ;
	}

	public String getCurrentMonth()
	{
		Calendar cal = Calendar.getInstance() ;
		int n = cal.get(Calendar.MONTH) +1 ;
		String cs = "" + (n/10) + (n%10) ;
		return cs ;
	}

	public String getCurrentShortYear()
	{
		Calendar cal = Calendar.getInstance() ;
		int n = cal.get(Calendar.YEAR) ;
		String cs = "" + ((n%100)/10) + (n%10) ;
		return cs ;
	}
	
	public void askTime()
	{
		if(isLogCESM)
			Log.logDebug("askTime");
		m_CESMEnv.setLastCommandCode(CESMCommandCode.ASKTIME) ;
		m_CESMEnv.resetDateTime() ;
	}
	
	public CCESMFakeMethodContainer inquire()
	{
		if(isLogCESM)
			Log.logDebug("inquire");
		
		m_CESMEnv.setLastCommandCode(CESMCommandCode.INQUIRE) ;
		// TODO fake method inquire
		return new CCESMFakeMethodContainer();
	}

	public CESMReadQueue readTempQueue(Var varName)
	{
		return readTempQueue(varName.getString());
	}
	public CESMReadQueue readTempQueue(String csName)
	{
		if(isLogCESM)
			Log.logDebug("readTempQueue "+csName);
		
		m_CESMEnv.setLastCommandCode(CESMCommandCode.READ_TEMPQUEUE);		
		m_CESMEnv.setCommandReturnCode(CESMReturnCode.NORMAL) ;
		
		return new CESMReadQueue(false, csName, m_CESMEnv.getQueueManager());
	}
	
	public void deleteTempQueue(Var varName)
	{
		deleteTempQueue(varName.getString());
	}
	public void deleteTempQueue(String csName)
	{
		if(isLogCESM)
			Log.logDebug("deleteTempQueue "+csName);
		
		m_CESMEnv.setLastCommandCode(CESMCommandCode.DELETE_TEMPQUEUE);
		m_CESMEnv.setCommandReturnCode(CESMReturnCode.NORMAL) ;
		
		CESMQueueManager queueManager = m_CESMEnv.getQueueManager();
		queueManager.deleteTempQueue(csName);
	}
	
	
	public CESMWriteQueue writeTempQueue(Var varName)
	{
		return writeTempQueue(varName.getString());	
	}
	public CESMWriteQueue writeTempQueue(String csName)
	{
		if(isLogCESM)
			Log.logDebug("writeTempQueue "+csName);
		
		m_CESMEnv.setLastCommandCode(CESMCommandCode.WRITE_TEMPQUEUE);
		m_CESMEnv.setCommandReturnCode(CESMReturnCode.NORMAL) ;
		
		return new CESMWriteQueue(false, csName, m_CESMEnv.getQueueManager());
	}

	public CESMWriteQueue writeTempQueue(Var tsNom, Var reWriteItem)
	{
		if(isLogCESM)
			Log.logDebug("writeTempQueue "+tsNom.getLoggableValue());
		
		m_CESMEnv.setLastCommandCode(CESMCommandCode.WRITE_TEMPQUEUE);
		m_CESMEnv.setCommandReturnCode(CESMReturnCode.NORMAL) ;
		
		String name = tsNom.getString();
		CESMWriteQueue writeorder = new CESMWriteQueue(false, name, m_CESMEnv.getQueueManager());
		int item = reWriteItem.getInt() ;
		writeorder.rewrite(item) ;
		return writeorder ;	
	}

	public CCESMFakeMethodContainer readDataSet(Var var)
	{
		return readDataSet(var.getString());
	}
	public CCESMFakeMethodContainer readDataSet(String string)
	{
		if(isLogCESM)
			Log.logDebug("readDataSet "+string);
		m_CESMEnv.setLastCommandCode(CESMCommandCode.READ_DATASET) ;
		m_CESMEnv.setCommandReturnCode(CESMReturnCode.NORMAL) ;
		// --> VSAM will be suppressed from COBOL
		return new CCESMFakeMethodContainer();
	}
	
	public CCESMFakeMethodContainer writeDataSet(Var var)
	{
		return writeDataSet(var.getString());
	}
	public CCESMFakeMethodContainer writeDataSet(String string)
	{
		if(isLogCESM)
			Log.logDebug("writeDataSet "+string);
		m_CESMEnv.setLastCommandCode(CESMCommandCode.WRITE_DATASET) ;
		m_CESMEnv.setCommandReturnCode(CESMReturnCode.NORMAL) ;
		// --> VSAM will be suppressed from COBOL
		return new CCESMFakeMethodContainer();
	}
	
	public CCESMFakeMethodContainer reWriteDataSet(String string)
	{
		if(isLogCESM)
			Log.logDebug("reWriteDataSet "+string);
		m_CESMEnv.setCommandReturnCode(CESMReturnCode.NORMAL) ;
		// --> VSAM will be suppressed from COBOL
		return new CCESMFakeMethodContainer();
	}
	
	public CESMStart start(String csTransaction)
	{
		return start(csTransaction, true);
	}
	public CESMStart start(Var varTransaction)
	{
		return start(varTransaction.getString(), true);
	}
	public CESMStart start(Class cl)
	{
		return start(cl.getName(), false);
	}
	private CESMStart start(String csProgramId, boolean bResolveProgram)
	{
		if (bResolveProgram)
			csProgramId = BaseProgramLoader.ResolveTransID(csProgramId);

		if(isLogCESM)
			Log.logDebug("start "+csProgramId);
		m_CESMEnv.setCommarea(null);
		return new CESMStart(csProgramId, m_CESMEnv);
	}

	public void syncPointRollback()
	{
		if(m_CESMEnv.hasSQLConnection())
		{
			if(isLogCESM)
				Log.logDebug("syncPointRollback");
			m_CESMEnv.rollbackSQL();
		}
		else
		{
			if(isLogCESM)
				Log.logDebug("syncPointRollback: Nothing to do: No connection opened");
		}
	}
	
	public void syncPointCommit()
	{
		if(m_CESMEnv.hasSQLConnection())
		{
			if(isLogCESM)
				Log.logDebug("syncPointCommit");
			SQLException e = m_CESMEnv.commitSQL();
			if(e != null)
			{
				AbortSessionException exp = new AbortSessionException() ;
				exp.m_Reason = new Error("Problem with syncPointCommit");
				exp.m_ProgramName = null;
				throw exp ;
			}
		}
		else
		{
			if(isLogCESM)
				Log.logDebug("syncPointCommit: Nothing to do: No connection opened");
		}
	}
	
	public CESMLink link(Var varProgram)
	{
		return link(varProgram.getString().trim());
	}
	public CESMLink link(Class cl)
	{
		return link(cl.getName());
	}
	public CESMLink link(String csProgramName)
	{
		if(isLogCESM)
			Log.logDebug("link "+csProgramName);
		m_CESMEnv.setLastCommandCode(CESMCommandCode.LINK );
		m_CESMEnv.setCommarea(null);
		return new CESMLink(m_CESMEnv, csProgramName);
	}
	
	public CESMXctl xctl(Class cl)
	{
		return xctl(cl.getName());
	}
	public CESMXctl xctl(Var varProgram)
	{
		return xctl(varProgram.getString());
	}
	public CESMXctl xctl(String csProgram)
	{
		if(isLogCESM)
			Log.logDebug("xctl "+csProgram);
		m_CESMEnv.setLastCommandCode(CESMCommandCode.XCTL);
		m_CESMEnv.setCommarea(null);
		return new CESMXctl(m_CESMEnv, csProgram);
	}

	public String getLastCommandCode()
	{
		return m_CESMEnv.getLastCommandCode() ;
	}
}