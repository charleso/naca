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
package nacaLib.basePrgEnv;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class EnvironmentStatus
{
	private String m_csStatus = null;
	private boolean m_bRunning = false;
	
	public static final EnvironmentStatus UNKNOWN = new EnvironmentStatus("Unknown", false);
	public static final EnvironmentStatus RUNNING = new EnvironmentStatus("Running", true);
	public static final EnvironmentStatus STOPPED = new EnvironmentStatus("Stopped", false);
	public static final EnvironmentStatus STOP_REQUESTED = new EnvironmentStatus("StopRequested", true);
	
	private EnvironmentStatus(String csStatus, boolean bRunning)
	{
		m_csStatus = csStatus;
		m_bRunning = bRunning;
	}
	
	public String getString()
	{
		return m_csStatus;
	}
	
	public boolean isRunning()
	{
		return m_bRunning;
	}
}
