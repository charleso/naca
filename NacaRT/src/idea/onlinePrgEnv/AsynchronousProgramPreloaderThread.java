/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package idea.onlinePrgEnv;

import idea.manager.PreloadProgramSettings;
import idea.manager.ProgramPreloader;

import java.util.ArrayList;

import nacaLib.basePrgEnv.ProgramSequencer;

import jlib.xml.Tag;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: AsynchronousProgramPreloaderThread.java,v 1.1 2006/12/15 14:25:55 u930di Exp $
 */
public class AsynchronousProgramPreloaderThread extends Thread
{
	private OnlineResourceManager m_onlineResourceManager = null;
	private ProgramPreloader m_programPreloader = null;
	private ArrayList<PreloadProgramSettings> m_arrProgramToPreload = null;	
	private String m_csProgramListToKeep = null;
	
	public AsynchronousProgramPreloaderThread(OnlineResourceManager onlineResourceManager, ProgramPreloader programPreloader, ArrayList<PreloadProgramSettings> arrProgramToPreload, String csProgramListToKeep)
	{
		m_onlineResourceManager = onlineResourceManager;
		m_programPreloader = programPreloader;
		m_arrProgramToPreload = arrProgramToPreload;
		m_csProgramListToKeep = csProgramListToKeep;		
	}
	
	public void run()
	{
		m_onlineResourceManager.AsynchronouslyPreloadPrograms(m_arrProgramToPreload, m_programPreloader, m_csProgramListToKeep);
	}
}
