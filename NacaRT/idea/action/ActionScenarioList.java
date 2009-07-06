/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 6 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package idea.action;

import java.io.File;
import java.io.FilenameFilter;

import idea.onlinePrgEnv.OnlineResourceManager;
import idea.onlinePrgEnv.OnlineResourceManagerFactory;
import idea.onlinePrgEnv.OnlineSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jlib.xml.*;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ActionScenarioList extends Action
{
	public static class XMLFilter implements FilenameFilter
	{
		public boolean accept(File arg0, String arg1)
		{
			return arg1.endsWith(".xml");
		}
	}
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception
		{
			HttpSession javaSession = request.getSession(true);
			OnlineSession appSession = null ;
			appSession = (OnlineSession)javaSession.getAttribute("AppSession");
			if (appSession == null)
			{
				appSession = new OnlineSession(false) ;
				javaSession.setAttribute("AppSession", appSession);
			}
			
			OnlineResourceManager resMan = OnlineResourceManagerFactory.GetInstance() ;
			// create wrapper for form fields
			CHTTPMapFieldLoader reqLoader = new CHTTPMapFieldLoader(request);
			String choix = reqLoader.getFieldValue("choixScenario") ;
			if (choix != null && !choix.equals(""))
			{ 
				String scepath = resMan.getScenarioDir() + "/" + choix ;
				appSession = new OnlineSession(false) ;
				javaSession.setAttribute("AppSession", appSession);
				appSession.SetScenario(scepath) ;
				return mapping.findForward("StartNaca");
			}
			String scenarioFile = reqLoader.getFieldValue("scenariofile") ;
			if (scenarioFile != null && !scenarioFile.equals(""))
			{ 
				appSession = new OnlineSession(false) ;
				javaSession.setAttribute("AppSession", appSession);
				appSession.SetScenario(scenarioFile) ;
				return mapping.findForward("StartNaca");
			}
			
			
			// display liste of scenarios
			Document doc = XMLUtil.CreateDocument() ;
			Element eRoot = doc.createElement("form") ;
			doc.appendChild(eRoot) ;
			eRoot.setAttribute("lang", "fr") ;
			eRoot.setAttribute("name", "scenarii") ;
			
			File dir = new File(resMan.getScenarioDir()) ;
			File lst[] = dir.listFiles(new XMLFilter()) ;
			int index = 1 ;
			if (lst != null)
			{
				for (int i=0; i<lst.length; i++)
				{
					File file = lst[i] ;
					if (file.isFile())
					{
						Document test = XMLUtil.LoadXML(file) ;
						if (test != null)
						{
							Element e = test.getDocumentElement() ;
							String name = e.getNodeName() ;
							if (name.equalsIgnoreCase("ST3270Catch") || name.equalsIgnoreCase("datarecord"))
							{
								String filename = file.getName() ;
								String csItemName = "scenar" + (index/10) + (index%10) ;
								index ++ ;
								Element eItem = doc.createElement("field") ;
								eRoot.appendChild(eItem) ;
								eItem.setAttribute("name", csItemName) ;
								eItem.setAttribute("value", filename) ;
							}
						}
					}
				}
			}

			Element eItem = doc.createElement("field") ;
			eRoot.appendChild(eItem) ;
			eItem.setAttribute("name", "choixScenario") ;
			eItem.setAttribute("value", "") ;
			
			appSession.setXMLData(doc) ;
			appSession.setIdPage("scenarii") ;			
			appSession.setActionAlias("scenario.do") ;
			return mapping.findForward("ViewCompat");
		}
}
