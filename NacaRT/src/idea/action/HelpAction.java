/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (Le chemin d'accès spécifié est introuvable))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package idea.action;

import idea.emulweb.CScenarioPlayer;
import idea.onlinePrgEnv.OnlineSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jlib.xml.*;

import nacaLib.basePrgEnv.BaseProgramLoader;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** 
 * HelpAction.java created by EasyStruts - XsltGen.
 * http://easystruts.sf.net
 * created on 01-26-2005
 * 
 * XDoclet definition:
 * @struts:action path="/help" name="helpForm" input="/help.jsp" validate="true"
 */
public class HelpAction extends Action
{

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/** 
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception
	{
		//HelpForm helpForm = (HelpForm)form;

		HttpSession javaSession = request.getSession(true);
		OnlineSession appSession = null ;
		appSession = (OnlineSession)javaSession.getAttribute("AppSession");
		if (appSession == null)
		{
			return null ;
		}

		CHTTPMapFieldLoader fieldLoader = new CHTTPMapFieldLoader(request) ;
		String sceId = fieldLoader.getFieldValue("scenarioWarningId") ;
		String ignId = fieldLoader.getFieldValue("ignoreWarningId") ;
		if (sceId != null && !sceId.equals(""))
		{
			doHelpForScenarioWarning(appSession, sceId) ;
		}
		else if (ignId != null && !ignId.equals(""))
		{
			doIgnoreScenarioWarning(appSession, ignId) ;
		}
		else
		{
			BaseProgramLoader prgLoader = BaseProgramLoader.GetInstance() ;
			prgLoader.doHelp(fieldLoader, appSession) ;
		}
		ActionForward fw = mapping.findForward("ViewHelp");
		return fw ;	
	}

	/**
	 * @param appSession
	 * @param sceId
	 */
	private void doIgnoreScenarioWarning(OnlineSession appSession, String sceId)
	{
		CScenarioPlayer player = appSession.getScenarioPlayer() ;
		if (player != null)
		{
			player.IgnoreWarning(sceId) ;
		}
		doHelpForScenarioWarning(appSession, sceId) ;
	}

	/**
	 * @param appSession
	 * @param sceId
	 */
	private void doHelpForScenarioWarning(OnlineSession appSession, String sceId)
	{
		CScenarioPlayer player = appSession.getScenarioPlayer() ;
		if (player != null)
		{
			CScenarioPlayer.CScenarioWarningDetail detail = player.getWarningDetail(sceId) ;
			Document doc = XMLUtil.CreateDocument() ;
			Element eHelp = doc.createElement("helpPage") ;
			doc.appendChild(eHelp) ;
			
			Element eTitle = doc.createElement("title") ;
			eHelp.appendChild(eTitle) ;
			eTitle.setAttribute("text", "Scenario Warning Details") ;

			Element eline = doc.createElement("line") ;
			eHelp.appendChild(eline) ;
			
			Element eName = doc.createElement("titledlabel") ;
			eHelp.appendChild(eName) ;
			eName.setAttribute("title", "Field Name") ;
			eName.setAttribute("text", detail.m_PageFieldDetails.name) ;

			Element eLine = doc.createElement("titledlabel") ;
			eHelp.appendChild(eLine) ;
			eLine.setAttribute("title", "Field Line") ;
			eLine.setAttribute("text", detail.m_PageFieldDetails.posline) ;

			Element eCol = doc.createElement("titledlabel") ;
			eHelp.appendChild(eCol) ;
			eCol.setAttribute("title", "Field Column") ;
			eCol.setAttribute("text", detail.m_PageFieldDetails.poscol) ;

			eline = doc.createElement("line") ;
			eHelp.appendChild(eline) ;
	
			Element eOrVal = doc.createElement("titledlabel") ;
			eHelp.appendChild(eOrVal) ;
			eOrVal.setAttribute("title", "Original Value") ;
			eOrVal.setAttribute("text", detail.m_ScenarioFieldDetails.value) ;

			Element eCurVal = doc.createElement("titledlabel") ;
			eHelp.appendChild(eCurVal) ;
			eCurVal.setAttribute("title", "Current Value") ;
			eCurVal.setAttribute("text", detail.m_PageFieldDetails.value) ;

			if (detail.m_ScenarioFieldDetails.mutable)
			{
				Element eButton = doc.createElement("sub-title") ;
				eHelp.appendChild(eButton) ;
				eButton.setAttribute("text", "This warning is ignored") ;
			}
			else
			{
				Element eButton = doc.createElement("button") ;
				eHelp.appendChild(eButton) ;
				eButton.setAttribute("display", "Ignore this warning") ;
				eButton.setAttribute("value", sceId) ;
			}
			
			appSession.setHelpPage(doc) ;
		}
	}

}
