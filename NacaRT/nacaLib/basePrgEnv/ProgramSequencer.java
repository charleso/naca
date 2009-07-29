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
package nacaLib.basePrgEnv;

import nacaLib.base.CJMapObject;
import nacaLib.programPool.SharedProgramInstanceData;

public abstract class ProgramSequencer extends CJMapObject
{
	public ProgramSequencer()
	{
	}

	public abstract SharedProgramInstanceData forcePreloadSessionProgram(String defaultProgramName, int nNbInstanceToPreload);

	public abstract void removeSession(BaseSession session) ;
	
	public abstract void unloadProgram(String csProgramName);
	public abstract SessionEnvironmentRequester getSessionEnvironmentRequester(BaseSession appSession) ;
	
	public abstract void doHelp(CBaseMapFieldLoader fieldLoader, BaseSession session);
}
