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
package idea.view;

import idea.onlinePrgEnv.OnlineSession;

import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jlib.misc.FileSystem;
import jlib.misc.NumberParser;
import jlib.misc.StringUtil;
import jlib.xml.XMLUtil;

import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.basePrgEnv.SessionEnvironmentRequester;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
public class XMLMerger
{
	protected OnlineSession m_AppSession = null ;
	private Element m_CursorField = null ;
	private DocumentBuilder m_docBuilder = null;
	private Hashtable<String, Element> m_tab = null;
	private String m_csXMLMergerDebugOutputPath = null;

	public XMLMerger(OnlineSession appSession)
	{
		m_csXMLMergerDebugOutputPath = BaseResourceManager.getXMLMergerDebugOutputPath();
		m_AppSession = appSession ;
		m_tab = new Hashtable<String, Element>() ;
		try
		{
			m_docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void set(OnlineSession appSession)
	{
		m_AppSession = appSession ;
	}
	
	void clear()
	{
		m_AppSession = null ;
		m_CursorField = null ;
		m_tab.clear();
	}
	
	
	/**
	 * @param xmlStruct
	 * @param xmlData
	 * @return
	 */
	public Document doMerging(Document xmlStruct, Document xmlData)
	{
		if (xmlData == null)
		{
			return xmlStruct ;
		}
		
		//XMLUtil.ExportXML(xmlData, ResourceManager.getLogDir()+"xmlData.xml");
		//XMLUtil.ExportXML(xmlStruct, ResourceManager.getLogDir()+"xmlStruct.xml");
		Document xmlOutput = XMLUtil.CreateDocument() ;
		Element eOutput = (Element)xmlOutput.importNode(xmlStruct.getDocumentElement(), true) ;
		xmlOutput.appendChild(eOutput) ;
		Element eData = xmlData.getDocumentElement() ;
		
		if (eData.hasAttribute("cursorPosition"))
		{
			int cursorPosition = NumberParser.getAsInt(eData.getAttribute("cursorPosition"));
			SetCursorPosition(eOutput, cursorPosition);
		}
		SetEditTags(eOutput, eData) ;		
		SetFormProperties(eOutput, eData) ;		
		SetPFKeys(eOutput, eData) ;
		
		if(m_csXMLMergerDebugOutputPath != null)
		{
			String csOut = m_csXMLMergerDebugOutputPath+"xmlData.xml";
			if(xmlData != null)
				XMLUtil.ExportXML(xmlData, csOut);
			else
				FileSystem.delete(csOut);
			
			csOut = m_csXMLMergerDebugOutputPath+"xmlStruct.xml";
			if(xmlStruct != null)
				XMLUtil.ExportXML(xmlStruct, csOut);
			else
				FileSystem.delete(csOut);
						
			csOut = m_csXMLMergerDebugOutputPath+"xmlMergerOutput.xml";
			if(xmlOutput != null)
				XMLUtil.ExportXML(xmlOutput, csOut);
			else
				FileSystem.delete(csOut);
		}
		return xmlOutput ;
	}

	private void SetCursorPosition(Element eOutput, int cursorPosition)
	{
		int line = (cursorPosition / 80) + 1;
		int pos = cursorPosition % 80;
		NodeList lst = eOutput.getElementsByTagName("edit");
		int nb = lst.getLength() ;
		for (int i=0; i<nb; i++)
		{
			Element e = (Element)lst.item(i) ;
			if (e.hasAttribute("line") && e.hasAttribute("col") && e.hasAttribute("length"))
			{
				int editLine = NumberParser.getAsInt(e.getAttribute("line"));
				int editCol = NumberParser.getAsInt(e.getAttribute("col"));
				int editLength = NumberParser.getAsInt(e.getAttribute("length"));
				if (line == editLine && pos >= editCol && pos <= editCol + editLength)
				{
					m_CursorField = e;
					return;
				}
			}
		}
	}

	private void SetPFKeys(Element eOutput, Element eData)
	{
		NodeList temp = eOutput.getElementsByTagName("pfkeydefine") ;
		if (temp.getLength() == 0)
			return;
		Element eDefine = (Element)temp.item(0);
		
		temp = eOutput.getElementsByTagName("pfkeyaction") ;
		Element eAction = null;
		if (temp.getLength() != 0)
			eAction = (Element)temp.item(0);
		
		NodeList lstPFOutput = eOutput.getElementsByTagName("pfkey") ;
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
			if (!ePF.hasAttribute("ignore"))
			{
				ePF.setAttribute("ignore", ignore);
			}
			
			if (eAction != null)
			{
				String action = eAction.getAttribute(name);
				if (action != null && !action.equals(""))
				{
					ePF.setAttribute("action", action);
				}
			}
		}
	}

	private void SetEditTags(Element eOutput, Element eData)
	{
		NodeList lstFields = eData.getElementsByTagName("field") ;
		//Hashtable<String, Element> tabFields = new Hashtable<String, Element>() ;
		Element defaultCursorField = null ;
		int nFields = lstFields.getLength() ;
		for (int i=0; i<nFields; i++)
		{
			Element e = (Element)lstFields.item(i) ;
			String name = e.getAttribute("name");
			m_tab.put(name, e) ;
		}
		
		SetLabelTags(eOutput);
		SetEditTagsForName(eOutput, defaultCursorField, "title");
		SetEditTagsForName(eOutput, defaultCursorField, "edit");
		//SetEditTagsForName(eOutput, tabFields, defaultCursorField, "activedit");
		SetSwitchTags(eOutput) ;
		
		if (m_CursorField == null)
		{
			m_CursorField = defaultCursorField ;
		}
		eOutput.setAttribute("updateTime", BaseResourceManager.getUpdateTimeFormated());
		eOutput.setAttribute("autoRefresh", BaseResourceManager.getUpdateTimeAutoRefresh());
		eOutput.setAttribute("userName", m_AppSession.getUserLdapName());
		eOutput.setAttribute("serverName", m_AppSession.getServerName());
		eOutput.setAttribute("terminalName", m_AppSession.getTerminalTerm());
		
		BaseProgramLoader prgseq = BaseProgramLoader.GetInstance() ;
		if (prgseq != null)
		{
			SessionEnvironmentRequester req = prgseq.getSessionEnvironmentRequester(m_AppSession);
			if (req != null)
			{
				eOutput.setAttribute("profitCenter", req.getProfitCenter());
			}
		}
	}
	
	private void SetSwitchTags(Element eOutput)
	{
		NodeList lst = eOutput.getElementsByTagName("switch") ;
		int nb = lst.getLength() ;
		for (int i=0; i<nb; i++)
		{
			Element e = (Element)lst.item(0) ;
			
			String ref = e.getAttribute("linkedvalue");
			Element efield = m_tab.get(ref);
			if (efield != null)
			{
				String value = efield.getAttribute("value") ;
				String protection = efield.getAttribute("protection") ;
				
				if (!e.hasAttribute("addItem") || e.getAttribute("addItem").equals("false"))
				{
					Element eHidden = eOutput.getOwnerDocument().createElement("edit") ;
					eHidden.setAttribute("name", ref) ;
					eHidden.setAttribute("type", "hidden") ;
					eHidden.setAttribute("value", value) ;
					e.getParentNode().insertBefore(eHidden, e) ;
				}
				
				NodeList lstcase = e.getElementsByTagName("case") ;
				Element eTag = null  ;
				for (int j=0; j<lstcase.getLength() && eTag == null; j++)
				{
					Element ecase = (Element)lstcase.item(j) ;
					if (ecase.hasAttribute("value"))
					{
						String caseval = ecase.getAttribute("value") ;
						if (caseval.equals(value.trim()))
						{
							eTag = XMLUtil.GetFirstElementChild(ecase) ;
						}
					}
					if (ecase.hasAttribute("protection"))
					{
						String caseval = ecase.getAttribute("protection") ;
						if (caseval.equals(protection.trim()))
						{
							eTag = XMLUtil.GetFirstElementChild(ecase) ;
						}
						else
						{
							eTag = null;
						}
					}
				}
				if (eTag == null)
				{
					lstcase = e.getElementsByTagName("default") ;
					if (lstcase != null && lstcase.getLength()>0)
					{
						Element ecase = (Element)lstcase.item(0) ;
						eTag = XMLUtil.GetFirstElementChild(ecase) ;
					}
				}
				if (eTag != null)
				{
					Element eOut = (Element)eOutput.getOwnerDocument().importNode(eTag, true) ;
					e.getParentNode().replaceChild(eOut, e) ;
				}
				else
				{
					e.getParentNode().removeChild(e) ;
				}
			}
			else
			{
				e.getParentNode().removeChild(e) ;
			}
		}
	}
	
	private void SetLabelTags(Element eOutput) 
	{
		NodeList lst = eOutput.getElementsByTagName("label") ;
		int nb = lst.getLength() ;
		for (int i=0; i<nb; i++)
		{
			Element e = (Element)lst.item(i) ;			
			if (e.hasAttribute("type"))
			{
				if (e.getAttribute("type").equals("activeChoice"))
				{
					String target = e.getAttribute("activeChoiceTarget") ;
					Element eTarget = m_tab.get(target);
					if (eTarget==null || eTarget.getAttribute("protection").equals("autoskip"))
					{
						e.setAttribute("type", "") ;
					}
				}
			}
		}
	}
	
	private void SetEditTagsForName(Element eOutput, Element defaultCursorField, String csName) 
	{
		NodeList lst = eOutput.getElementsByTagName(csName) ;
		int nb = lst.getLength() ;
		for (int i=0; i<nb; i++)
		{
			Element e = (Element)lst.item(i) ;
			if (e.hasAttribute("cursor"))
			{
				if (defaultCursorField == null && e.getAttribute("cursor").equals("true"))
				{
					defaultCursorField = e ;
				}
			}
			String ref = e.getAttribute("linkedvalue");
			Element efield = m_tab.get(ref);
			if (efield != null)
			{
				MergeFieldAttributes(e, efield) ;
			}
			
			if (e.hasAttribute("type"))
			{
				if (e.getAttribute("type").equals("linkedActiveChoice"))
				{
					String link = e.getAttribute("activeChoiceLink") ;
					Element eLink = m_tab.get(link);
					if (eLink==null || eLink.getAttribute("value").trim().equals(""))
					{
						e.setAttribute("type", "") ;
					}
					else
					{	
						String target = e.getAttribute("activeChoiceTarget") ;
						Element eTarget = m_tab.get(target);
						if (eTarget==null || eTarget.getAttribute("protection").equals("autoskip"))
						{
							e.setAttribute("type", "") ;
						}
					}
				}
				else if (e.getAttribute("type").equals("activeChoice"))
				{		
					String target = e.getAttribute("activeChoiceTarget") ;
					Element eTarget = m_tab.get(target);
					if (eTarget==null || eTarget.getAttribute("protection").equals("autoskip"))
					{
						e.setAttribute("type", "") ;
					}
				}
			}
		}
	}

	private void MergeFieldAttributes(Element eField, Element eData)
	{
		String csProtection = "" ;
		if (eData.hasAttribute("SemanticContext"))
		{
			String csSemanticContext = eData.getAttribute("SemanticContext");
			eField.setAttribute("SemanticContext", csSemanticContext); 
			int n = 0;
		}
		if (eData.hasAttribute("protection"))
		{
			eField.setAttribute("protection", eData.getAttribute("protection")) ;
		}
		csProtection = eField.getAttribute("protection") ; 
		if (eData.hasAttribute("value"))
		{
			if (csProtection.equals("autoskip"))
			{
				eField.setAttribute("value", eData.getAttribute("value")) ;
			}
			else
			{
				eField.setAttribute("value", StringUtil.trimRight(eData.getAttribute("value"))) ;
			}
		}
		if (eData.hasAttribute("color"))
		{
			eField.setAttribute("color", eData.getAttribute("color")) ;
		}
		if (eData.hasAttribute("highlighting"))
		{
			eField.setAttribute("highlighting", eData.getAttribute("highlighting")) ;
		}
		if (eData.hasAttribute("intensity"))
		{
			eField.setAttribute("intensity", eData.getAttribute("intensity")) ;
		}
		if (eData.hasAttribute("modified"))
		{
			eField.setAttribute("modified", eData.getAttribute("modified")) ;
		}
		if (eData.hasAttribute("updated"))
		{
			eField.setAttribute("modified", eData.getAttribute("updated")) ;
		}
		if (eData.hasAttribute("messageId"))
		{
			eField.setAttribute("messageId", eData.getAttribute("messageId")) ;
		}
		if (eData.hasAttribute("cursor"))
		{
			if (eData.getAttribute("cursor").equals("true") && m_CursorField==null)
			{
				m_CursorField = eField ;
			}
		}
	}

	private void SetFormProperties(Element eOutput, Element eData)
	{
		NodeList lstForms = eOutput.getElementsByTagName("form") ;
		int nb = lstForms.getLength() ;
		for (int i=0; i<nb; i++)
		{
			Element eForm = (Element)lstForms.item(i) ;
			String name = eForm.getAttribute("name") ;
			String dataname = eData.getAttribute("name") ;
			if (m_AppSession.isZoom())
				eForm.setAttribute("zoom", "true") ;
			else
				eForm.setAttribute("zoom", "false") ;
			if (m_AppSession.isBold())
				eForm.setAttribute("bold", "true") ;
			else
				eForm.setAttribute("bold", "false") ;
			//eForm.setAttribute("cmpSession", m_AppSession.getCmp()) ;
			if (name.equals(dataname))
			{
				String lang = eData.getAttribute("lang") ;
				eOutput.setAttribute("lang", lang) ;
			}
			if (m_CursorField != null)
			{
				String cur = m_CursorField.getAttribute("name");
				eForm.setAttribute("cursor", cur) ;
			}
			String cs = eData.getAttribute("printScreen") ;
			if  (cs != null && cs.equals("show"))
			{
				eForm.setAttribute("printScreen", "show") ;
			}
			
		}
	}
	public Document BuildXLMStructure(Document xmlFrame, Element xmlForm)
	{
		Document xmlStruct = m_docBuilder.newDocument();
		Element eStruct = (Element)xmlStruct.importNode(xmlFrame.getDocumentElement(), true) ;
		xmlStruct.appendChild(eStruct) ;
		
		NodeList lst = xmlStruct.getDocumentElement().getElementsByTagName("pageform");
		for (int i=0; i<lst.getLength(); i++)
		{
			Element eForm = (Element)lst.item(i) ;
			Element eNewForm = (Element)xmlStruct.importNode(xmlForm, true) ;
			Node node = eForm.getParentNode() ;
			node.removeChild(eForm) ;
			node.appendChild(eNewForm) ;
		} 
		
		//do replacing fields
		lst = xmlStruct.getElementsByTagName("edit") ;
		
		//Hashtable<String, Element> tab = new Hashtable<String, Element>() ;
		for (int i=0; i<lst.getLength(); i++)
		{
			Element e = (Element)lst.item(i);
			String name = e.getAttribute("name");
			m_tab.put(name, e) ;
		}
		while (true)
		{
			lst = xmlStruct.getElementsByTagName("replaceField") ;
			if (lst.getLength() == 0)
			{
				break;
			}
			else
			{
				Element eRep = (Element)lst.item(0);
				String name = eRep.getAttribute("name");
				Element e = m_tab.get(name) ;
				if (e != null)
				{
					String length = e.getAttribute("length") ;
					Element eBlank = xmlStruct.createElement("blank");
					eBlank.setAttribute("length", length) ;
					e.getParentNode().insertBefore(eBlank, e);
					e.getParentNode().removeChild(e) ;
					eRep.getParentNode().insertBefore(e, eRep) ;
					eRep.getParentNode().removeChild(eRep) ;
				}
				else
				{
					eRep.getParentNode().removeChild(eRep) ;
				}
			}
		}
		
		// do replacing title
		lst = xmlStruct.getElementsByTagName("replaceTitle") ;
		if (lst.getLength()>0)
		{
			Element eRep = (Element)lst.item(0);
			lst = xmlStruct.getElementsByTagName("title") ;
			if (lst.getLength()>0)
			{
				Element e = (Element)lst.item(0);
				String length = e.getAttribute("length") ;
				Element eBlank = xmlStruct.createElement("blank");
				eBlank.setAttribute("length", length) ;
				e.getParentNode().insertBefore(eBlank, e);
				e.getParentNode().removeChild(e) ;
				eRep.getParentNode().insertBefore(e, eRep) ;
				eRep.getParentNode().removeChild(eRep) ;
			}
			else
			{
				eRep.getParentNode().removeChild(eRep) ;
			}
		}
		
		return xmlStruct ;
	}
	/**
	 * @param xmlOutput
	 * @param xmlData
	 */
	public void doUpdate(Document xmlOutput, Document xmlData)
	{
		if (xmlOutput == null || xmlData == null)
		{
			return ;
		}
		//XMLUtil.ExportXML(xmlData, ResourceManager.getLogDir()+"xmlData.xml");
		Element eOutput = xmlOutput.getDocumentElement();
		Element eData = xmlData.getDocumentElement() ;
		
		SetEditTags(eOutput, eData) ;
		
		//XMLUtil.ExportXML(xmlOutput, ResourceManager.getLogDir()+"xmlOutput.xml");
	}
}
