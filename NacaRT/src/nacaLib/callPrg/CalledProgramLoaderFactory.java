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

import nacaLib.basePrgEnv.CBaseProgramLoaderFactory;
import nacaLib.basePrgEnv.ProgramSequencer;

public class CalledProgramLoaderFactory extends CBaseProgramLoaderFactory
{
	public ProgramSequencer NewSequencer()
	{
		CalledProgramLoader prog = new CalledProgramLoader(m_connectionManager, m_tagSequencerConfig);
		if (m_tagSequencerConfig != null)
			prog.initMailService(m_tagSequencerConfig);
		return prog ;
	}
}
