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
 * @version $Id: SpServerProgramLoader.java,v 1.2 2007/02/21 17:48:02 u930bm Exp $
 */

import jlib.sql.DbConnectionManagerBase;
import jlib.xml.Tag;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.basePrgEnv.CBaseMapFieldLoader;
import nacaLib.exceptions.AbortSessionException;

public class SpServerProgramLoader extends BaseProgramLoader
{
	public SpServerProgramLoader(DbConnectionManagerBase connectionManager, Tag tagSequencerConfig)
	{
		super(connectionManager, tagSequencerConfig, false);
	}

	public void RunProgram(BaseSession appSession) throws AbortSessionException
	{
	}
	
	public static SpServerProgramLoader GetProgramLoaderInstance() 
	{
		return (SpServerProgramLoader)ms_Instance ;
	}
	
	public void doHelp(CBaseMapFieldLoader fieldLoader, BaseSession session)
	{
	}

}

