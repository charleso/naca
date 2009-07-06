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

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SpServerEnvironment.java,v 1.4 2007/06/22 21:38:46 u930bm Exp $
 */
import java.sql.SQLException;

import jlib.sql.DbConnectionManagerBase;
import nacaLib.basePrgEnv.BaseCESMManager;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseResourceManager;

public class SpServerEnvironment extends BaseEnvironment
{
	public SpServerEnvironment(SpServerSession spServerSession, DbConnectionManagerBase connectionManager, BaseResourceManager baseResourceManager)
	{	
		super(spServerSession, connectionManager, baseResourceManager);
	}
	
	public BaseCESMManager createCESMManager()
	{
		return null;
	}
	
	public SpServerSession getSession()
	{
		return null;
	}
	
	public void breakCurrentSessionIfTimeout()
	{
	}
	
	public SQLException commitSQL()
	{	
		return null;
	}
	
	public SQLException rollbackSQL()
	{
		return null;
	}
	
	public void cleanupOnExceptionCatched()
	{
		rollbackSQL() ;
		releaseSQLConnection() ;
		autoCloseOpenFile();
		returnTempCacheToStack();
	}
	
	public void releaseSQLConnection()
	{
		if (getSQLConnection() != null)
		{
			getSQLConnection().removeAllPreparedStatements();
			super.releaseSQLConnection();
		}
	}
}
