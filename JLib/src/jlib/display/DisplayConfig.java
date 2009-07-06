/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 7 juil. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jlib.display;

import java.io.File;

import jlib.log.Log;
import jlib.xml.Tag;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DisplayConfig
{
	private DisplayConfig()
	{
	}
	private static DisplayConfig ms_Instance = null ;
	public static DisplayConfig getInstance()
	{
		if (ms_Instance == null)
		{
			ms_Instance = new DisplayConfig() ;
		}
		return ms_Instance ;
	}
	public void setRootPath(String path)
	{
		m_csRootPath = path.replace('\\', '/') ;
		if (!m_csRootPath.endsWith("/"))
		{
			m_csRootPath += '/' ;
		}
		System.out.println("JLIB DIsplay Config -> Root Path = "+m_csRootPath) ;
	}
	protected String m_csRootPath = "" ;
	public String getRootPath()
	{
		return m_csRootPath;
	}
	public void LoadConfig(String csINIFilePath)
	{
		Tag tagConfig = Tag.createFromFile(csINIFilePath) ;
		if  (tagConfig != null)
		{
			String csInitialDialogClass = tagConfig.getVal("InitialDialogFactory") ;
			try
			{
				m_factoryDialogs = (BaseDialogFactory) Class.forName(csInitialDialogClass).newInstance() ;
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
				m_factoryDialogs = null;
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
				m_factoryDialogs = null;
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
				m_factoryDialogs = null;
			}
			
			m_ResourceManager = new ResourceManager() ;
			
			String csXSLFilePath = tagConfig.getVal("XSLFilePath") ;
			File fXSLFilePath = new File(m_csRootPath + csXSLFilePath) ;
			if (!fXSLFilePath.isFile())
			{
				fXSLFilePath = null ;
			}
			m_ResourceManager.setXSLFilePath("MAIN", fXSLFilePath) ;

			String csLogINIFilePath = tagConfig.getVal("LogINIFilePath") ;
			if (csLogINIFilePath!=null && !csLogINIFilePath.equals(""))
			{
				csLogINIFilePath = m_csRootPath + csLogINIFilePath ;
				Log.open(csLogINIFilePath);
			}
			
			Tag tagFactory = tagConfig.getChild("factory") ;
			if (tagFactory != null)
			{
				m_factoryDialogs.Init(this, tagFactory) ;
			}
		}
	}
	
	private BaseDialogFactory m_factoryDialogs = null ;
	private ResourceManager m_ResourceManager = null ;
	/**
	 * @return
	 */
	public BaseDialogFactory getDialogFactory()
	{
		return m_factoryDialogs ;
	}
	public ResourceManager getResourceManager()
	{
		return m_ResourceManager ;
	}
}
