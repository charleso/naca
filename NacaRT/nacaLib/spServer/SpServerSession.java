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
/**
 * 
 */
package nacaLib.spServer;


import java.sql.Connection;

import org.w3c.dom.Document;

import jlib.misc.BasicLogger;
import jlib.sql.DbConnectionManagerBase;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.basePrgEnv.CurrentUserInfo;
import nacaLib.sqlSupport.SQLConnection;

public class SpServerSession extends BaseSession
{	
	private Connection m_connection = null;
	
	public SpServerSession(Connection connection, BaseResourceManager baseResourceManager)
	{
		super(baseResourceManager);
		m_connection = connection;
		setAsync(true);
	}
		
	public BaseEnvironment createEnvironment(DbConnectionManagerBase connectionManager)
	{
		BasicLogger.log("SpServerSession::createEnvironment()");
		SpServerEnvironment env = new SpServerEnvironment(this, connectionManager, m_baseResourceManager);
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
}
