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
 * @version $Id: SpServerProgramLoaderFactory.java,v 1.2 2006/11/23 14:50:32 u930bm Exp $
 */
import jlib.misc.BasicLogger;
import nacaLib.basePrgEnv.CBaseProgramLoaderFactory;
import nacaLib.basePrgEnv.ProgramSequencer;

public class SpServerProgramLoaderFactory extends CBaseProgramLoaderFactory
{
	public ProgramSequencer NewSequencer()
	{
		BasicLogger.log("SpServerProgramLoaderFactory::NewSequencer() (with exception handler)");
		
		if(m_connectionManager != null)
			BasicLogger.log("m_connectionManager="+m_connectionManager.toString());
		else
			BasicLogger.log("m_connectionManager=null");
		
		if(m_connectionManager != null)
			BasicLogger.log("m_tagSequencerConfig="+m_tagSequencerConfig.toString());
		else
			BasicLogger.log("m_tagSequencerConfig=null");
		try
		{
			SpServerProgramLoader prog = new SpServerProgramLoader(null, null);
			prog.initMailService(m_tagSequencerConfig);
			BasicLogger.log("SpServerProgramLoaderFactory::NewSequencer; SpServerProgramLoader="+prog.toString());
			return prog;
		}
		catch (Exception e)
		{
			BasicLogger.log("SpServerProgramLoaderFactory::NewSequencer; Exception catched="+e.toString());
			return null;
		}
	}
}
