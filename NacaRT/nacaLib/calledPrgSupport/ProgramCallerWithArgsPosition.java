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
package nacaLib.calledPrgSupport;

import jlib.sql.DbConnectionBase;
import nacaLib.accounting.CriteriaEndRunMain;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.callPrg.CalledProgramLoader;
import nacaLib.callPrg.CalledResourceManager;
import nacaLib.callPrg.CalledResourceManagerFactory;
import nacaLib.callPrg.CalledSession;
import nacaLib.exceptions.AbortSessionException;
import nacaLib.exceptions.ProgramCallerException;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ProgramCallerWithArgsPosition extends CalledProgramParamSupportByPosition
{
	private String m_csConfigFile = null;
	private DbConnectionBase m_dbConnection = null;
	private String m_csPrgClassName = null;
	
	public ProgramCallerWithArgsPosition(String csConfigFile, DbConnectionBase dbConnection, String csPrgClassName)
	{
		m_csConfigFile = csConfigFile;
		m_dbConnection = dbConnection;
		m_csPrgClassName = csPrgClassName;
	}
	public ProgramCallerWithArgsPosition(String csConfigFile, DbConnectionBase dbConnection, Class classPrgToCall)
	{
		m_csConfigFile = csConfigFile;
		m_dbConnection = dbConnection;
		m_csPrgClassName = classPrgToCall.getName();
	}

	public boolean execute() throws ProgramCallerException
	{
		CalledResourceManager calledResourceManager = CalledResourceManagerFactory.GetInstance(m_csConfigFile, m_dbConnection.getEnvironmentPrefix());
		if(calledResourceManager == null)
			return false;
		
		CalledSession session = new CalledSession(calledResourceManager) ;
			
		BaseEnvironment env = null;
		try
		{
			BaseProgramLoader loader = CalledProgramLoader.GetProgramLoaderInstance() ;
			env = loader.GetEnvironment(session, m_csPrgClassName, null) ;
			env.setExternalDbConnection(m_dbConnection);
			boolean bUseStatementCache = BaseResourceManager.getUseStatementCache();
			env.fillEnvConnectionWithAllocatedConnection(m_dbConnection.getDbConnection(), "ExternalConnection", m_dbConnection.getEnvironmentPrefix(), bUseStatementCache);							
	
			boolean bStarted = env.startRunTransaction();
			if(!bStarted)
			{
				env.endRunTransaction(CriteriaEndRunMain.Abort);
				return false;
			}
				
			loader.runTopProgram(env, m_arrPublicArgs);
			
			env.endRunTransaction(CriteriaEndRunMain.Normal);
			return true;
		}
		catch (AbortSessionException e)
		{
			env.endRunTransaction(CriteriaEndRunMain.Abort);
			String csMessage = e.getReason();
			ProgramCallerException callerException = new ProgramCallerException(csMessage);
			throw callerException;
		}
		catch(Exception e)
		{
			env.endRunTransaction(CriteriaEndRunMain.Abort);
			String csMessage = e.getMessage();
			ProgramCallerException callerException = new ProgramCallerException(csMessage);
			throw callerException;
		}
	}
}
