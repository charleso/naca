/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.action;

import idea.emulweb.CEmulMapFieldLoader;
import idea.emulweb.CScenarioPlayer;
import idea.manager.CMapFieldLoader;
import idea.onlinePrgEnv.OnlineResourceManager;
import idea.onlinePrgEnv.OnlineResourceManagerFactory;
import idea.onlinePrgEnv.OnlineSession;
import idea.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jlib.xml.XMLUtil;
import jlib.xml.XSLTransformer;
import nacaLib.appOpening.CalendarOpenState;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.exceptions.AbortSessionException;
import nacaLib.misc.KeyPressed;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
 * Created on 8 déc. 2004
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


public class ActionCompat extends Action
{
	public ActionCompat()
	{
		super();
	}
	
	InputAnalyser m_inputAnalyser = new InputAnalyser() ;
	BaseProgramLoader m_prgseq = BaseProgramLoader.GetInstance() ;
		
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception
	{
		if (m_prgseq == null)
		{
			response.setStatus(500) ;
			response.getOutputStream().println("Internal Error : ProgramSequencer not valid") ;
			return null;
		}
		
		HttpSession javaSession = request.getSession(true);
		/*
		OnlineSession appSession = null ;
		appSession = (OnlineSession)javaSession.getAttribute("AppSession");
		if (appSession == null)
		{
			appSession = new OnlineSession(false) ;
			javaSession.setAttribute("AppSession", appSession);
			appSession.setInputWrapper(new CEmulMapFieldLoader());
		}
		else
		{
			// create wrapper for form fields
			CHTTPMapFieldLoader reqLoader = new CHTTPMapFieldLoader(request);
			appSession.setInputWrapper(reqLoader);
		}
		
		if(appSession.blockUntilLocked())
		{
			// 1st session has finished
			return appSession.m_actionForward;
		}
		*/
		
		boolean bNewSession = false;
		OnlineSession appSession = (OnlineSession)javaSession.getAttribute("AppSession");
		if (appSession == null)
		{
			appSession = new OnlineSession(false) ;
			bNewSession = true;
		}

		if(!appSession.reserveSessionForCurrentThread())
		{
			// 1st session has finished
			return appSession.m_actionForward;
		}
		
		if(bNewSession)
		{
			javaSession.setAttribute("AppSession", appSession);
			appSession.setInputWrapper(new CEmulMapFieldLoader());
		}
		else
		{
			// create wrapper for form fields
			CHTTPMapFieldLoader reqLoader = new CHTTPMapFieldLoader(request);
			appSession.setInputWrapper(reqLoader);
		}
		
		
		String csElapsedTime = request.getParameter("elapsedTime");
		if (csElapsedTime != null && !csElapsedTime.equals(""))
		{	
			appSession.stopNetwork(new Long(csElapsedTime));
		}
		else
		{
			appSession.stopNetwork(0);
		}
		
		// We are the 1st session
		//appSession.lock();
		
		appSession.m_actionForward = doExecute(
						appSession,
						javaSession,
						mapping,
						form,
						request,
						response);
		
		//appSession.unlock();
		appSession.unreserveSession();
		
		appSession.startNetwork();
		
		return appSession.m_actionForward;
	}
	
	private ActionForward doExecute(
					OnlineSession appSession,
					HttpSession javaSession,
					ActionMapping mapping,
					ActionForm form,
					HttpServletRequest request,
					HttpServletResponse response) throws Exception
	{
		String csInternTest = request.getParameter("interntest");
		if (csInternTest != null && !csInternTest.equals(""))
		{
			appSession.setInternTest(true);
		}
		
		String csLUName = request.getParameter("luname");
		if(csLUName != null && !csLUName.equals(""))
		{
			csLUName = csLUName.toUpperCase();
			appSession.SetLUName(csLUName);
			response.addCookie(new Cookie("NACA_Luname", csLUName));
		}
		String csRedirect = request.getParameter("redirect") ;
		if(csRedirect != null && !csRedirect.equals(""))
		{
			response.sendRedirect(csRedirect);
			return null;
		}
		//String csCmp = request.getParameter("cmp") ;
//		if (csCmp!=null && !csCmp.equals(""))
//		{
//			appSession.setCmp(csCmp);
//			response.addCookie(new Cookie("NACA_Cmp", csCmp));
//			String idPage = appSession.getIdPage();
//			CMapFieldLoader reqLoader = appSession.getInputWrapper() ;
//			if (idPage != null && idPage.equals("rs7aa3h")) //TODO Config file or ????
//			{	
//				reqLoader.setKeyPressed(KeyPressed.ENTER);
//			}
//			else if (idPage != null && !idPage.equals("") && !idPage.equals("MapLogin"))
//			{
//				appSession.setActionAlias("naca.do") ;
//				return mapping.findForward("ViewCompat");
//			}
//			else
//			{
//				if (reqLoader.getKeyPressed() == null)
//				{
//					reqLoader.setKeyPressed(KeyPressed.ENTER);
//				}
//			}
//		}

		if (BaseResourceManager.isInUpdateMode())
		{
			Document doc = XMLUtil.CreateDocument() ;
			Element eForm = doc.createElement("form") ;
			doc.appendChild(eForm) ;
			eForm.setAttribute("name", "update") ;
			
			appSession.setXMLData(doc) ;
			appSession.setIdPage("update") ;
			appSession.setActionAlias("naca.do") ;
			return mapping.findForward("ViewCompat");
		}

		// Check app open state
		CalendarOpenState openState = BaseResourceManager.getAppOpenState();
		if(!appSession.isInternTest() && openState != CalendarOpenState.AppOpened)
		{
			String csReason = openState.getString();
			String csRemark = null;
			
			if(openState == CalendarOpenState.AppManuallyClosed)
				csRemark = BaseResourceManager.getManualCloseReason();
			else if(openState == CalendarOpenState.AppClosed)
				csRemark = BaseResourceManager.getCurrentOpenCalendarRangeString(); 
			else
				csRemark = "";
					
			Document doc = XMLUtil.CreateDocument() ;
			Element eForm = doc.createElement("form") ;
			doc.appendChild(eForm) ;
			eForm.setAttribute("name", "close") ;
			
			Element eField = doc.createElement("field") ;
			eForm.appendChild(eField) ;
			eField.setAttribute("name", "reason") ;
			eField.setAttribute("value", csReason) ;

			eField = doc.createElement("field") ;
			eForm.appendChild(eField) ;
			eField.setAttribute("name", "remark") ;
			eField.setAttribute("value", csRemark) ;
			
			appSession.setXMLData(doc) ;
			appSession.setIdPage("close") ;
			appSession.setActionAlias("naca.do") ;
			return mapping.findForward("ViewCompat");
		}
		
		String csPrintScreen = request.getParameter("printScreen") ;
		if (csPrintScreen!=null && csPrintScreen.equals("requested"))
		{
			m_inputAnalyser.BuildXMLDataForPrintScreen(appSession) ;
			appSession.getXMLData().getDocumentElement().setAttribute("printScreen", "show") ;
			appSession.setActionAlias("naca.do") ;
			return mapping.findForward("ViewCompat");
		}
		
		String csZoom = request.getParameter("zoom") ;
		if (csZoom!=null && csZoom.equals("requested"))
		{
			m_inputAnalyser.BuildXMLDataForPrintScreen(appSession) ;
			if (appSession.isZoom())
			{	
				appSession.setZoom(false);
				response.addCookie(new Cookie("NACA_Zoom", "false"));
			}	
			else
			{	
				appSession.setZoom(true);
				response.addCookie(new Cookie("NACA_Zoom", "true"));
			}	
			appSession.setActionAlias("naca.do") ;
			return mapping.findForward("ViewCompat");			
		}
		
		String csBold = request.getParameter("bold") ;
		if (csBold!=null && csBold.equals("requested"))
		{
			m_inputAnalyser.BuildXMLDataForPrintScreen(appSession) ;
			if (appSession.isBold())
			{	
				appSession.setBold(false);
				response.addCookie(new Cookie("NACA_Bold", "false"));
			}	
			else
			{	
				appSession.setBold(true);
				response.addCookie(new Cookie("NACA_Bold", "true"));
			}	
			appSession.setActionAlias("naca.do") ;
			return mapping.findForward("ViewCompat");			
		}

		// manage LDAP login 
		if (!appSession.isLogged() && !appSession.isPlayingScenario())
		{
			if (appSession.getLUName() == null || appSession.getLUName().equals(""))
			{
				Cookie[] cookies = request.getCookies();
				if (cookies != null)
				{	
					for (int i=0; i < cookies.length; i++)
					{
						if (cookies[i].getName().equals("NACA_Luname"))
						{
							appSession.SetLUName(cookies[i].getValue());
						}
						else if (cookies[i].getName().equals("NACA_Cmp"))
						{
							//appSession.setCmp(cookies[i].getValue());
						}
						else if (cookies[i].getName().equals("NACA_Zoom"))
						{
							appSession.setZoom(new Boolean(cookies[i].getValue()));
						}
						else if (cookies[i].getName().equals("NACA_Bold"))
						{
							appSession.setBold(new Boolean(cookies[i].getValue()));
						}
					}
				}
			}

			String csUserid = request.getParameter("userid");
			if (!appSession.doLDAPLogin(/*"", */csUserid))
			{				
				appSession.setActionAlias("naca.do") ;
				return mapping.findForward("ViewCompat");
			}
		}

		// test if we are playing a scenario, and if interaction with program has to be done
		if (appSession.isCallProgram())
		{
			// build XML Data
			if (m_inputAnalyser.BuildXMLData(appSession))
			{
				try
				{
					// call programm
					appSession.RunProgram(m_prgseq);
				} 
				catch (AbortSessionException e)
				{				
					Document doc = XMLUtil.CreateDocument() ;
					Element eForm = doc.createElement("form") ;
					doc.appendChild(eForm) ;
					eForm.setAttribute("name", "error") ;
					
					Element eField = doc.createElement("field") ;
					eForm.appendChild(eField) ;
					eField.setAttribute("name", "programName") ;
					eField.setAttribute("value", e.m_ProgramName) ;
	
					eField = doc.createElement("field") ;
					eForm.appendChild(eField) ;
					eField.setAttribute("name", "errorMessage") ;
					eField.setAttribute("value", e.m_Reason.toString()) ;
	
//					StackTraceElement[] tabStack = e.m_Reason.getStackTrace() ;
//					int n = 1 ;
//					for (int i=0; i<tabStack.length;i++)
//					{
//						String cs = tabStack[i].toString() ;
//						if (!cs.startsWith("nacaLib") && !cs.startsWith("idea") && !cs.startsWith("org")
//								 && !cs.startsWith("javax") && !cs.startsWith("java"))
//						{
//							eField = doc.createElement("field") ;
//							eForm.appendChild(eField) ;
//							eField.setAttribute("name", "stack"+n) ;
//							n++ ;
//							eField.setAttribute("value", cs) ;
//						}
//					}
	
					appSession.setXMLData(doc) ;
					appSession.setIdPage("error") ;
					appSession.setActionAlias("naca.do") ;
					return mapping.findForward("ViewCompat");
				}
			}
		}
		if (appSession.isLoggedOut())
		{
			javaSession.setAttribute("AppSession", null);
			javaSession.invalidate() ;
			ActionForward forward = mapping.findForward("RestartNaca") ;
			return forward ;
		}
		else
		{
			appSession.setActionAlias("naca.do") ;
			return mapping.findForward("ViewCompat");
		}
	}

	/*
	 * used by EmulWeb
	 */
	public void RunClientRequest(OnlineSession appSession, Document xmlData) throws AbortSessionException
	{
		appSession.setXMLData(xmlData) ;
		doRunClientRequest(appSession) ;
	}
//	public void RunClientRequest(OnlineSession appSession) throws AbortSessionException
//	{
//		// build XML Data
//		m_inputAnalyser.BuildXMLData(appSession) ;
//		doRunClientRequest(appSession) ;
//	}
	public void doRunClientRequest(OnlineSession appSession) throws AbortSessionException
	{
		// call programm
		appSession.RunProgram(m_prgseq);
		
		// call view
		View m_view = new View() ;
		m_view.mergeOutput(appSession);
	}
	
	public void runClientRequestWithRender(OnlineResourceManager resourceManager, CScenarioPlayer player, OnlineSession session, boolean bExportOutput)
	{
//		for(int n=0; n<1000; n++)
//		{
			player.rewindScenario();
			
			BaseProgramLoader basePrgLoader = BaseProgramLoader.GetInstance() ;
			basePrgLoader.removeSession(session);
			
			m_inputAnalyser.BuildXMLData(session) ;

			// call programm
			session.RunProgram(m_prgseq);
		
			// call view
			View m_view = new View() ;
			m_view.mergeOutput(session);
			
			//String csDir = resourceManager.getScenarioDir() ;
			Document xmlOutput = session.getXMLOutput();
			String csPage = player.getPageNameFromXMLOutput(xmlOutput) ;
			String csDirOut = resourceManager.getOutputDir() ;
			if (bExportOutput)
			{
				//String filePattern = csDir + "/"+0+"-"+csPage;
				String filePatternOut = csDirOut + "/"+0+"-"+csPage;
				//XMLUtil.ExportXML(xmlOutput, filePattern+".xml") ;
				internalRenderOutput(xmlOutput, filePatternOut+".html") ;
				//renderOutput(xmlOutput, csDirOut + "/output.html") ;
			}
//		}
		//System.out.println("Current page : " + csPage) ;
	}
	
	void internalRenderOutput(Document xmlOutput, String filename)
	{
		try
		{
			FileOutputStream file = new FileOutputStream(filename);
			if (xmlOutput == null)
			{
			}
			else
			{
				OnlineResourceManager resource = OnlineResourceManagerFactory.GetInstance() ;
				XSLTransformer xformer = resource.getXSLTransformer() ;
				if (!xformer.doTransform(xmlOutput, file))
				{
				}
				//XMLUtil.ExportXML(xmlOutput, "output.xml") ;
//				resource.returnXSLTransformer(xformer);
			}
			file.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
