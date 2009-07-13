/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.batchPrgEnv;

import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.programPool.SharedProgramInstanceData;
import nacaLib.tempCache.TempCacheLocator;

public class BatchProgramManager extends BaseProgramManager
{
	public BatchProgramManager(BaseProgram program, SharedProgramInstanceData sharedProgramInstanceData, boolean bInheritedSharedProgramInstanceData)
	{
		super(program, sharedProgramInstanceData, bInheritedSharedProgramInstanceData);
		
		BaseEnvironment env = TempCacheLocator.getTLSTempCache().getCurrentEnv();
		setEnv(env);
	}
	
	public String getTerminalID()
	{
		return "";
	}
		
	public void setEnv(BaseEnvironment env)
	{
		m_env = (BatchEnvironment)env;
	}
	
	public void detachFromEnv()
	{
		m_env = null;
	}
	
	public BaseEnvironment getEnv()
	{
		return m_env;
	}
	
	public void prepareRunMain(BaseProgram prg)
	{
		if (prg instanceof BatchProgram)
			((BatchProgram)prg).prepareRunMain(m_env);
	}
	
	private BatchEnvironment m_env = null; 
}
