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
package idea.servlets;

import idea.onlinePrgEnv.OnlineResourceManager;
import idea.onlinePrgEnv.OnlineResourceManagerFactory;
import idea.onlinePrgEnv.OnlineSession;
import idea.view.View;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jlib.xml.XSLTransformer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
 * Created on 29 déc. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XSLTServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private View m_view ;

	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{	
		HttpSession javaSession = req.getSession(true);
		OnlineSession appSession = (OnlineSession)javaSession.getAttribute("AppSession");
		int n = appSession.getOnceHttpSessionMaxInactiveInterval_s();
		if(n != 0)
			javaSession.setMaxInactiveInterval(n);
		
		String path = req.getServletPath() ;
		
		if (path.equalsIgnoreCase("/showhelp"))
		{
			ShowHelp(appSession, res) ;
			return ;
		}
		 
		if (path.equalsIgnoreCase("/showprintscreen"))
		{
			m_view.mergeOutputForPrintScreen(appSession) ;
			ShowPrintScreen(appSession, res) ;
			return ;
		}
				 
		// make output
		if (appSession.isUpdatedValues())
		{
			m_view.updateOutput(appSession) ;
		}
		else
		{
			m_view.mergeOutput(appSession) ;
		}
		
		// render output
		Document xmlOutput = appSession.getXMLOutput();
		if (xmlOutput == null)
		{
			res.setStatus(500);
		}
		else
		{
			String sessionid = javaSession.getId() ;
			xmlOutput.getDocumentElement().setAttribute("SESSIONID", sessionid) ;

			String csServletPath = req.getRequestURI();
			String s = csServletPath.replaceFirst("XSLTServlet", appSession.getActionAlias()) ; 
			s = res.encodeURL(s) ;
			xmlOutput.getDocumentElement().setAttribute("URL", s) ;
			renderOutput(xmlOutput, res, appSession.isZoom(), appSession.isBold());
		}
	}

	/**
	 * 
	 */
	private void ShowHelp(OnlineSession appSession, HttpServletResponse res)
	{
		Document xmlOutput = appSession.getHelpPage() ;
		if (xmlOutput == null)
		{
			res.setStatus(500);
		}
		else
		{
			renderHelp(xmlOutput, res);
		}
	}

	private void ShowPrintScreen(OnlineSession appSession, HttpServletResponse res)
	{
		Document xmlOutput = appSession.getXMLOutput() ;
		if (xmlOutput == null)
		{
			res.setStatus(500);
		}
		else
		{
			((Element)xmlOutput.getElementsByTagName("form").item(0)).setAttribute("printScreen", "true");
			renderPrintScreen(xmlOutput, res);
		}
	}	

	private void doRenderOutput(Document xmlOutput, HttpServletResponse res, XSLTransformer trans)
	{
		res.setContentType("text/html");
		try
		{
			ServletOutputStream out = res.getOutputStream();
			if (xmlOutput == null)
			{
				res.setStatus(500);
				out.println("Session aborded") ;
			}
			else
			{
				if (trans == null)
				{
					out.println("Erreur interne") ;
					res.setStatus(500);
				}
				
				if (!trans.doTransform(xmlOutput, out))
				{
					out.println("Erreur interne") ;
					out.close() ;
					res.setStatus(500);
				}
			}
			out.close();
		}
		catch (IOException e)
		{
			res.setStatus(500);
		}
	}

	private void renderHelp(Document xmlOutput, HttpServletResponse res)
	{
		OnlineResourceManager resource = OnlineResourceManagerFactory.GetInstance() ;
		XSLTransformer xformer = resource.getHelpTransformer() ;
		doRenderOutput(xmlOutput, res, xformer) ;
	}
	
	private void renderPrintScreen(Document xmlOutput, HttpServletResponse res)
	{
		OnlineResourceManager resource = OnlineResourceManagerFactory.GetInstance() ;
		XSLTransformer xformer = resource.getPrintScreenTransformer() ;
		if (xformer == null)
			xformer = resource.getXSLTransformer();
		doRenderOutput(xmlOutput, res, xformer) ;
	}
	
	private void renderOutput(Document xmlOutput, HttpServletResponse res)
	{
		renderOutput(xmlOutput, res, false, false);
	}
	private void renderOutput(Document xmlOutput, HttpServletResponse res, boolean bZoom, boolean bBold)
	{
		OnlineResourceManager resource = OnlineResourceManagerFactory.GetInstance();
		XSLTransformer xformer = null;
		if (bZoom)
		{
			if (bBold)
				xformer = resource.getXSLTransformerZoomBold();
			else
				xformer = resource.getXSLTransformerZoom();
		}	
		else
		{
			if (bBold)
				xformer = resource.getXSLTransformerBold();	
		}
		
		if (xformer == null)
		{
			xformer = resource.getXSLTransformer();
		}
			
		if (xformer != null)
		{
			doRenderOutput(xmlOutput, res, xformer) ;
		}
		else
		{
			res.setStatus(500) ;
			try
			{
				res.getOutputStream().println("Error : missing XSLT Processor") ;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig arg0) throws ServletException
	{
		super.init(arg0);
		m_view = new View() ;
	}
}
