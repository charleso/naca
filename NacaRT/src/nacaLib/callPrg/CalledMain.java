/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.callPrg;

import jlib.sql.DbConnectionBase;
import nacaLib.accounting.CriteriaEndRunMain;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.exceptions.AbortSessionException;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: CalledMain.java,v 1.1 2007/09/18 08:22:28 u930di Exp $
 */
public class CalledMain
{
	public static int executeTranscodedProgram(String csConfigFile, DbConnectionBase dbConnection, String csPrgClassName)
	{
		CalledResourceManager calledResourceManager = CalledResourceManagerFactory.GetInstance(csConfigFile, dbConnection.getEnvironmentPrefix());
		if(calledResourceManager == null)
			return 8;
		
		CalledSession session = new CalledSession(calledResourceManager) ;
			
		BaseEnvironment env = null;
		try
		{
			BaseProgramLoader loader = CalledProgramLoader.GetProgramLoaderInstance() ;
			env = loader.GetEnvironment(session, csPrgClassName, null) ;
			env.setExternalDbConnection(dbConnection);
			env.fillEnvConnectionWithAllocatedConnection(dbConnection.getDbConnection(), "ExternalConnection", dbConnection.getEnvironmentPrefix(), true);	// bUseStatementCache							
	
			boolean bStarted = env.startRunTransaction();
			if(!bStarted)
			{
				env.endRunTransaction(CriteriaEndRunMain.Abort);
				return 8;
			}

//			if (csParameter != null)
//			{
//				InternalCharBuffer charBuffer = new InternalCharBuffer(2 + csParameter.length());
//				int nPos = 0;
//				nPos = charBuffer.writeShort(new Integer(csParameter.length()).shortValue(), nPos);
//				nPos = charBuffer.writeString(csParameter, nPos);
//				CCommarea comm = new CCommarea();
//				comm.setVarPassedByValue(charBuffer);
//				env.setCommarea(comm);
//			}
				
			loader.runTopProgram(env, null);
			
			env.endRunTransaction(CriteriaEndRunMain.Normal);
			return 0;
		}
		catch (AbortSessionException e)
		{
			env.endRunTransaction(CriteriaEndRunMain.Abort);
			return 8;
		}
		catch(Exception e)
		{
			env.endRunTransaction(CriteriaEndRunMain.Abort);
			return 8;
		}
	}
}
