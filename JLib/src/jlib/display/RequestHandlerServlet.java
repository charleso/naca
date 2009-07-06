/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */

package jlib.display;


import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

/*
 * Created on Sep 23, 2004
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
public class RequestHandlerServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	* Initializes the servlet. The method is called once, automatically, by the
	* Java Web Server when it loads the servlet. The init() method should save the
	* ServletConfig object so that it can be returned by the getServletConfig()
	* method.
	*/
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
	}

	/**
	* Method to process HTTP GET requests. In this method a Simple HTML form is
	* displayed. User can enter his/her name in User Name text field and press
	* "Submit Form" button. Upon pressing the button, the form gets submitted.
	* The Servlet uses getParameter() method to get the user name entered by the
	* user and displays it in the HTML page. The parameters to doGet() method is
	* 1) HttpServletRequest object, which encapsulates the data from the client
	* 2) HttpServletResponse object, which encapsulates the response to the client
	*/	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException 
	{
		// get session
		HttpSession javaSession = req.getSession(true);
		DisplayContext dispContext = null ;
		dispContext = (DisplayContext)javaSession.getAttribute("DisplayContext");
		if (dispContext == null)
		{
			dispContext = new DisplayContext() ;
			javaSession.setAttribute("DisplayContext", dispContext);
		}
		
		// create wrapper for form fields
		HTTPMapFieldLoader reqLoader = new HTTPMapFieldLoader(req);
		DisplayOutput output = new DisplayOutput(dispContext) ;
		if (!dispContext.OnRequest(reqLoader, output))
		{
			res.setStatus(500);
			return ;
		}

		// render output
		String csServletPath = req.getRequestURI();
		String s = res.encodeURL(csServletPath);
		output.setURL(s) ;
		output.doRenderOutput(res) ;
	}


	public void doPost(HttpServletRequest req, HttpServletResponse Res) 
		throws ServletException, IOException 
	{
		doGet(req, Res) ;
	}
	
	/**
	* Override the getServletInfo() method which is supposed to return information
	* about the Servlet, e.g. the Servlet name, version, author and copyright
	* notice. This is not required for the function of the HelloUser servlet but
	* can provide valuable information to the user of a servlet who sees the
	* returned text in the administration tool of the Web Server.
	*/
	public String getServletInfo()
	{
		return "Hello User Servlet 1.0 by Reghu";
	}


	/**
	* Destroy the servlet. This method is called once when the servlet is unloaded
	**/
	public void destroy() 
	{
		super.destroy();
	}
}
