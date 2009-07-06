/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.action;


import idea.manager.CMapFieldLoader;
import idea.onlinePrgEnv.OnlineSession;
import nacaLib.basePrgEnv.CBaseMapFieldLoader;
import nacaLib.misc.KeyPressed;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
public class InputAnalyser
{
	public boolean BuildXMLData(OnlineSession appSession)
	{
		CMapFieldLoader reqLoader = appSession.getInputWrapper() ;
		if (reqLoader == null)
		{
			return false;
		}
		KeyPressed key = reqLoader.getKeyPressed() ;
		if (key == null)
		{
			return false;
		}
		
		Document eRoot = appSession.CreateXMLDataRoot();		
		eRoot.getDocumentElement().setAttribute("keypressed", key.m_csValue);
		
		String idPage = reqLoader.getIDPage() ;
		eRoot.getDocumentElement().setAttribute("page", idPage) ;
		eRoot.getDocumentElement().setAttribute("name", idPage) ;
		String idLang = reqLoader.getFieldValue("idLang") ;
		eRoot.getDocumentElement().setAttribute("lang", idLang) ;
		Document xmlStruct = appSession.getXMLStructure(idPage) ;
		if (xmlStruct != null)
		{
			Element eStruct = xmlStruct.getDocumentElement() ;
			NodeList lst = eStruct.getElementsByTagName("edit") ;
			int nb = lst.getLength() ;
			for (int i=0; i<nb; i++)
			{
				Element e = (Element)lst.item(i) ;
				String ref = e.getAttribute("linkedvalue");
				String field = e.getAttribute("name") ;
				String val = reqLoader.getFieldValue(field) ;
				boolean bUpdated = reqLoader.isFieldModified(field) ;
				AddField(eRoot, ref, val, bUpdated) ; 
			}

			lst = eStruct.getElementsByTagName("switch") ;
			nb = lst.getLength() ;
			for (int i=0; i<nb; i++)
			{
				Element e = (Element)lst.item(i) ;
				String ref = e.getAttribute("linkedvalue");
				String field = e.getAttribute("name") ;
				String val = reqLoader.getFieldValue(field) ;
				boolean bUpdated = reqLoader.isFieldModified(field) ;
				AddField(eRoot, ref, val, bUpdated) ; 
			}
		}
		appSession.setXMLData(eRoot) ;
		
		return true;
	}
	public void BuildXMLDataForPrintScreen(OnlineSession appSession)
	{
		Document eRoot = appSession.getXMLData();
		CBaseMapFieldLoader reqLoader = appSession.getInputWrapper() ;
		if (reqLoader == null)
		{
			return  ;
		}
		
		Element eStruct = eRoot.getDocumentElement() ;
		NodeList lst = eStruct.getElementsByTagName("field") ;
		int nb = lst.getLength() ;
		for (int i=0; i<nb; i++)
		{
			Element e = (Element)lst.item(i) ;
			String name = e.getAttribute("name") ;			
			boolean bUpdated = reqLoader.isFieldModified(name) ;
			if (bUpdated)
			{
				String val = reqLoader.getFieldValue(name) ;
				e.setAttribute("value", val);
				e.setAttribute("updated", "true") ;
			}
		}
		
		appSession.setXMLData(eRoot) ;		
	}

	/**
	 * @param eRoot
	 * @param ref
	 * @param val
	 */
	private void AddField(Document eRoot, String ref, String val, boolean bUpdated)
	{
		Element field = eRoot.createElement("field") ;
		eRoot.getDocumentElement().appendChild(field) ;
		field.setAttribute("name", ref) ;
		field.setAttribute("value", val) ;
		if (bUpdated)
		{
			field.setAttribute("updated", "true") ;
		}
		else
		{
			field.setAttribute("updated", "false") ;
		}
	}

}
