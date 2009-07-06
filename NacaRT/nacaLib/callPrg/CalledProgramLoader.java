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

import jlib.sql.DbConnectionManagerBase;
import jlib.xml.Tag;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.basePrgEnv.CBaseMapFieldLoader;
import nacaLib.exceptions.AbortSessionException;

public class CalledProgramLoader extends BaseProgramLoader
{
	public CalledProgramLoader(DbConnectionManagerBase connectionManager, Tag tagSequencerConfig)
	{
		super(connectionManager, tagSequencerConfig, true);
	}

	public void RunProgram(BaseSession appSession) throws AbortSessionException
	{
	}
	
	public static CalledProgramLoader GetProgramLoaderInstance() 
	{
		return (CalledProgramLoader)ms_Instance ;
	}
	
	public void doHelp(CBaseMapFieldLoader fieldLoader, BaseSession session)
	{
	}
}
