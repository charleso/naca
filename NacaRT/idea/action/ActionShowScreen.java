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
package idea.action;

import idea.onlinePrgEnv.OnlineResourceManager;
import idea.onlinePrgEnv.OnlineResourceManagerFactory;
import idea.onlinePrgEnv.OnlineSession;
import idea.view.XMLMerger;
import idea.view.XMLMergerManager;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jlib.log.AssertException;
import jlib.misc.StringUtil;
import jlib.xml.XSLTransformer;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ActionShowScreen extends Action
{
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
		CHTTPMapFieldLoader reqLoader = new CHTTPMapFieldLoader(request);
		
		String csPage = reqLoader.getFieldValue("showPage").toUpperCase();
		Document page;
		try
		{
			page = resMan.GetXMLPage(csPage);
		}
		catch (AssertException e)
		{
			page = resMan.GetXMLPage("RS01A11");
		}
		
		String csLang = reqLoader.getFieldValue("showLanguage");
		if (csLang.equals(""))
		{
			csLang = "FR";
		}		
		
		XMLMerger merger = XMLMergerManager.get(null);
		Element eForm = page.getDocumentElement() ;
		Document docOutput = merger.BuildXLMStructure(resMan.getXmlFrame(), eForm) ;
		XMLMergerManager.release(merger);
			
		docOutput.getDocumentElement().setAttribute("lang", csLang) ;
		setEditTagsForName(docOutput, "title");
		setEditTagsForName(docOutput, "edit");
		setPFKeys(docOutput);
		SetFormProperties(docOutput);
		
		renderOutput(docOutput, response, resMan);
		return null ;
	}
	
	private void SetFormProperties(Document eOutput)
	{
		NodeList lstForms = eOutput.getElementsByTagName("form");
		int nb = lstForms.getLength();
		for (int i=0; i<nb; i++)
		{
			Element eForm = (Element)lstForms.item(i);
			eForm.setAttribute("zoom", "false");
			eForm.setAttribute("bold", "false");
			eForm.setAttribute("printScreen", "showScreen");
		}
	}

	private void setPFKeys(Document docOutput)
	{
		NodeList temp = docOutput.getElementsByTagName("pfkeydefine") ;
		if (temp.getLength() != 0)
		{	
			Element eDefine = (Element)temp.item(0);
			NodeList lstPFOutput = docOutput.getElementsByTagName("pfkey") ;
			for (int i=0; i<lstPFOutput.getLength(); i++)
			{
				Element ePF = (Element)lstPFOutput.item(i);
				String name = ePF.getAttribute("name");
				String valid = eDefine.getAttribute(name);			
				String ignore = "true" ;
				if (valid.equalsIgnoreCase("true"))
				{
					ignore = "false" ;
				}
				ePF.setAttribute("ignore", ignore);
			}
		}
	}
	
	private void setEditTagsForName(Document docOutput, String csName) 
	{
		NodeList lst = docOutput.getElementsByTagName(csName) ;
		int nb = lst.getLength() ;
		for (int i=0; i<nb; i++)
		{
			Element e = (Element)lst.item(i) ;
			if (e.hasAttribute("linkedvalue"))
			{
				String value = e.getAttribute("value");
				if (value != null && value.equals(""))
				{
					String protection = e.getAttribute("protection");
					if (protection == null || protection.equals("") || protection.equals("autoskip"))
					{
						String length = e.getAttribute("length");
						int nLength = 1;
						if (length != null && !length.equals("")) {
							nLength = new Integer(length).intValue();
						}
						e.setAttribute("value", StringUtil.rightPad("", nLength, '*'));
					}				
				}
			}
		}
	}

	private void renderOutput(Document xmlOutput, HttpServletResponse res, OnlineResourceManager resMan)
	{
		XSLTransformer trans = resMan.getXSLTransformer() ;
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
				else if (!trans.doTransform(xmlOutput, out))
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
}