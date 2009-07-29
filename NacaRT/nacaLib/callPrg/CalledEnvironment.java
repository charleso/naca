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
package nacaLib.callPrg;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */

import idea.manager.CESMManager;
import jlib.sql.DbConnectionManagerBase;
import nacaLib.basePrgEnv.BaseCESMManager;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.basePrgEnv.BaseSession;

public class CalledEnvironment extends BaseEnvironment
{
	public CalledEnvironment(CalledSession calledSession, DbConnectionManagerBase connectionManager, BaseResourceManager baseResourceManager)
	{	
		super(calledSession, connectionManager, baseResourceManager);
	}
	
	public BaseCESMManager createCESMManager()
	{
		return new CESMManager(this);
	}
	
	public BaseSession getSession()
	{
		return null;
	}
	
	public void breakCurrentSessionIfTimeout()
	{
	}
	
}
