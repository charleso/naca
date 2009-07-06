/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.batchPrgEnv;

import jlib.sql.DbConnectionManagerBase;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.basePrgEnv.CurrentUserInfo;

import org.w3c.dom.Document;

public class BatchSession extends BaseSession
{	
	public BatchSession(BaseResourceManager baseResourceManager)
	{
		super(baseResourceManager);
	}
		
	public BaseEnvironment createEnvironment(DbConnectionManagerBase connectionManager)
	{
		BatchEnvironment env = new BatchEnvironment(this, connectionManager, m_baseResourceManager);
		return env;
	}
	
	public String getType()
	{
		return "Batch";
	}
	
	public void RunProgram(BaseProgramLoader seq)
	{
	}
	
	public void setHelpPage(Document doc)
	{
	}

	public void fillCurrentUserInfo(CurrentUserInfo currentUserInfo)
	{
		currentUserInfo.reset();
	}
	
	public Document getLastScreenXMLData()
	{
		return null;
	}
	
//	public void addBatchFile(String csLogicalName, String csPath)
//	{
//		addBatchFile(csLogicalName, csPath, false, false, 0);
//	}
//
//	
//	public void addBatchFile(String csLogicalName, String csPath, boolean bEbcdic)
//	{
//		addBatchFile(csLogicalName, csPath, bEbcdic, false, 0);
//	}
//
//	
//	public void addBatchFile(String csLogicalName, String csPath, boolean bEbcdic, boolean bExt)
//	{
//		addBatchFile(csLogicalName, csPath, bEbcdic, bExt, 0);
//	}

}
