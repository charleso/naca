/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.display;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/*
 * Created on Dec 13, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InitServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);

		m_Config = DisplayConfig.getInstance() ;

		String path = this.getServletContext().getRealPath("/") ;
		m_Config.setRootPath(path) ;

		String csINIFilePath = config.getInitParameter("INIFilePath");
		csINIFilePath = m_Config.getRootPath() + csINIFilePath ;
		m_Config.LoadConfig(csINIFilePath) ;


/*
		m_ResourceManager.setXMLConfigFilePath(csINIFilePath) ;
		m_ResourceManager.Init() ;
		
		m_ResourceManager.loadDBSemanticContextDef();

		// Load semantic context data dictionnary: Defines semantic context associtaed to DB columns
				
		
		// Load semantic context configuration file: Defines menus, options, ...
		String csSemanticContext = m_ResourceManager.getSemanticContextPathFile();
		if(csSemanticContext != null && csSemanticContext.length() != 0)
		{
			SemanticManager semanticManager = SemanticManager.GetInstance();
			semanticManager.Init(csSemanticContext);
			m_ResourceManager.registerSemanticManager(semanticManager);			
		}
	*/
	}
	
	DisplayConfig m_Config = null ;
}
