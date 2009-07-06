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

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import jlib.xml.Tag;
import jlib.xml.XSLTransformer;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DisplayOutput
{
	protected DisplayContext m_Context = null  ;
	protected DisplayConfig m_Config = null ;
	public DisplayOutput(DisplayContext context)
	{
		m_Context = context ;
		m_Config = DisplayConfig.getInstance() ;
	}
	/**
	 * @param tagOutput
	 */
	public void setXMLDisplay(Tag tagOutput)
	{
		m_tagDisplayOutput = tagOutput ;		
	}
	
	protected Tag m_tagDisplayOutput = null ;

	public void doRenderOutput(HttpServletResponse res)
	{
		res.setContentType("text/html");
		try
		{
			Document xmlOutput = m_tagDisplayOutput.getEmbeddedDocument() ;
			m_tagDisplayOutput.exportToFile(m_Config.getRootPath()+"output.xml") ;
			
			ServletOutputStream out = res.getOutputStream();
			if (xmlOutput == null)
			{
				res.setStatus(500);
				out.println("Session aborded") ;
			}
			else
			{
				ResourceManager man = m_Config.getResourceManager() ;
				XSLTransformer trans = man.getXSLTransformer("MAIN") ;
				if (trans == null)
				{
					out.println("Erreur interne") ;
					res.setStatus(500);
				}
				
				if (!trans.doTransform(xmlOutput, out))
				{
					out.println("Erreur interne") ;
					res.setStatus(500);
				}
				
			}
		}
		catch (IOException e)
		{
			res.setStatus(500);
		}
	}
	/**
	 * @param s
	 */
	public void setURL(String s)
	{
		m_tagDisplayOutput.addVal("URL", s) ;
	}
}
