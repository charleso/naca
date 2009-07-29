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
package idea.clientRequests;

import idea.onlinePrgEnv.OnlineSession;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ClientRequestServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientRequestServlet()
	{
		super();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse Res) 
	throws ServletException, IOException 
	{
		// get session
		HttpSession javaSession = req.getSession(true);
		OnlineSession appSession = null ;
		appSession = (OnlineSession)javaSession.getAttribute("AppSession");
		if (appSession == null)
		{
		}
		
//		// create wrapper for form fields
//		CHTTPMapFieldLoader reqLoader = new CHTTPMapFieldLoader(req);
//		appSession.setInputWrapper(reqLoader);
//		
//		try
//		{
//			// call action
//			m_action.RunClientRequest(appSession);
//		} 
//		catch (AbortSessionException e)
//		{
//			javaSession.invalidate() ;
//			res.getOutputStream().println(e.getMessage()) ;
//			return ;
//		}	
//		
//		// render output
//		Document xmlOutput = appSession.getXMLOutput();
//		String csServletPath = req.getRequestURI();
//		String s = res.encodeURL(csServletPath);
//		if (xmlOutput == null)
//		{
//			res.setStatus(500);
//		}
//		else
//		{
//			xmlOutput.getDocumentElement().setAttribute("URL", s) ;
//			renderOutput(xmlOutput, res);
//		}
	}

}
