/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 24 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task ;

import utils.NacaTransLauncher;

/**
 * @author SLY
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NacaTransTask extends Task
{
	protected String m_cfgFilePath = "" ;
	private String m_groupToTranscode = "" ;
	
	public void setConfig(String filename)
	{
		m_cfgFilePath = filename ;
	}
	
	public void execute() throws BuildException
	{
		super.getLocation() ;
		NacaTransLauncher obj = new NacaTransLauncher() ;
		try
		{
			obj.Start(m_cfgFilePath, m_groupToTranscode) ;
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
			throw new BuildException() ;
		}
	}
	/**
	 * @param csGroupToTranscode The csGroupToTranscode to set.
	 */
	public void setGroupToTranscode(String csGroupToTranscode)
	{
		m_groupToTranscode = csGroupToTranscode;
	}
}
