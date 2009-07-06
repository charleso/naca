/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 5 juil. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jlib.misc;

import java.io.File;
import java.util.Map;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProcessExecutionTask
{
	protected Map<String, String> m_Env ;
	protected String m_csCommand = "" ;
	protected File m_dirRuntimeDir = null ;
	protected StringArray m_arrParameters = new StringArray() ;
	protected StringArray m_arrClassPath = new StringArray() ;

	public void setCommand(String cmd)
	{
		if (cmd != null  && !cmd.equals(""))
		{
			m_csCommand = cmd ;
		}
	}
	public void addParameter(String p)
	{
		if (p!= null && !p.equals(""))
		{
			m_arrParameters.add(p) ;
		}
	}
	public boolean setRuntimeDirectory(String dir)
	{
		if (dir != null && !dir.equals(""))
		{
			m_dirRuntimeDir = new File(dir) ;
			return m_dirRuntimeDir.isDirectory() ;
		}
		return false ;
	}
	public boolean setRuntimeDirectory(File dir)
	{
		if (dir != null)
		{
			m_dirRuntimeDir = dir ;
			return m_dirRuntimeDir.isDirectory() ;
		}
		return false ;
	}
//	public void addClassPath(String path)
//	{
//		m_arrClassPath.addElement(path) ;
//	}
	public void addEnvironmentVariable(String var, String val)
	{
		m_Env.put(var, val) ;
	}

}
