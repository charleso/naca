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
/*
 * Created on 21 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package idea.semanticContext;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import jlib.log.Log;

import nacaLib.base.CJMapObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class SemanticManager extends CJMapObject
{
	protected static SemanticManager ms_Instance = null ;
	public static SemanticManager GetInstance()
	{
		if (ms_Instance == null)
		{
			ms_Instance = new SemanticManager() ;
		}
		return ms_Instance ;
	}

	private SemanticManager()
	{
		ms_Instance = this ;
	}
	
	public void Init(String csFilePath)
	{
		LoadXMLConfig(csFilePath);
	}
	
	public void LoadXMLConfig(String csFilePath)
	{
		try
		{
			File fSS = new File(csFilePath);
			Source file = new StreamSource(fSS) ;
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument() ;
			Result res = new DOMResult(doc) ;
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(file, res);
			
			//Element eConf = doc.getDocumentElement() ;
			
			LoadMenus(doc);
			LoadConditions(doc);
		}
		catch (ParserConfigurationException e)
		{
			return ;
		}
		catch (TransformerConfigurationException e)
		{
			return ;
		}
		catch (TransformerException e)
		{
			return ;
		}
	}
	
	private void LoadMenus(Document doc)
	{
		NodeList menus = doc.getElementsByTagName("Menus") ;
		if (menus.getLength() > 0)
		{				
			int nMenus = 0;
			Element elMenus = (Element)menus.item(nMenus);	// Enum all cases tags
			if(elMenus != null)	// Only 1 elMenus tag
			{
				NodeList lstMenu = elMenus.getElementsByTagName("Menu") ;
				if(lstMenu != null)
				{
					int nMenu = 0;
					Element elMenu = (Element)lstMenu.item(nMenu);	// Enum all menu tags
					while(elMenu != null)
					{
						String csMenuId = elMenu.getAttribute("Id");
						String csTitle = elMenu.getAttribute("Title");
	
						CMenuDef MenuDef = createAndRegisterNewMenu(csMenuId);
						MenuDef.setTitle(csTitle);
				
						NodeList lstOptions = elMenu.getElementsByTagName("Options") ;
						if(lstOptions != null)
						{
							int nOptions = 0;
							Element elOptions = (Element)lstOptions.item(nOptions);	// Enum all conditions
							if(elOptions != null)	// Only 1 tag conditions
							{
								NodeList lstOption = elOptions.getElementsByTagName("Option") ;
								if(lstOption != null)
								{
									int nOption = 0;
									Element elOption = (Element)lstOption.item(nOption);	// Enum all conditions
									while(elOption != null)
									{
										String csLabel = elOption.getAttribute("Label") ;
										String csActionId = elOption.getAttribute("ActionId") ;
										
										CMenuOptionDef MenuOptionDef = MenuDef.createAndRegisterNewOption();
										MenuOptionDef.setActionId(csActionId);
										MenuOptionDef.setLabel(csLabel);
										
										nOption++;
										elOption = (Element)lstOption.item(nOption);	// Enum all conditions
									}
								}
							}
						}
						nMenu++;
						elMenu = (Element)lstMenu.item(nMenu);	// Enum all menu tags
					}
				}
			}
		}
	}

		
	private void LoadConditions(Document doc)
	{
		NodeList cases = doc.getElementsByTagName("Cases") ;
		if (cases.getLength() > 0)
		{				
			int nCases = 0;
			Element elCases = (Element)cases.item(nCases);	// Enum all cases tags
			if(elCases != null)	// Only 1 cases tag
			{
				NodeList lstCase = elCases.getElementsByTagName("Case") ;
				if(lstCase != null)
				{
					int nCase = 0;
					Element elCase = (Element)lstCase.item(nCase);	// Enum all case tags
					while(elCase != null)
					{
						String csMenuId = elCase.getAttribute("MenuId");
						CMenuDef MenuDef = getMenuId(csMenuId);
						if(MenuDef == null)	// The menu is undefined
						{
							Log.logImportant("A Semantic context condition references the menu "+csMenuId+". But it is undefined in the menu definitions");
						}
						else
						{
							NodeList lstConditions = elCase.getElementsByTagName("Conditions") ;
							if(lstConditions != null)
							{
								int nConditions = 0;
								Element elConditions = (Element)lstConditions.item(nConditions);	// Enum all conditions
								if(elConditions != null)	// Only 1 tag conditions
								{
									NodeList lstCondition = elConditions.getElementsByTagName("Condition") ;
									if(lstCondition != null)
									{
										int nCondition = 0;
										Element elCondition = (Element)lstCondition.item(nCondition);	// Enum all conditions
										while(elCondition != null)
										{
											String csScreenId = elCondition.getAttribute("ScreenId") ;
											String csSemanticId = elCondition.getAttribute("SemanticId") ;
											
											addSemanticCase(csSemanticId, csScreenId, MenuDef);
											
											nCondition++;
											elCondition = (Element)lstCondition.item(nCondition);	// Enum all conditions
										}
									}
								}
							}
						}
						nCase++;							
						elCase = (Element)lstCase.item(nCase);	// Enum all case tags
					}
				}
			}
		}
	}

	private CMenuDef createAndRegisterNewMenu(String csMenuId)
	{
		CMenuDef MenuDef = new CMenuDef();
		m_hashMenus.put(csMenuId, MenuDef);
		return MenuDef;
	}		
	
	private CMenuDef getMenuId(String csMenuId)
	{
		CMenuDef MenuDef = m_hashMenus.get(csMenuId);
		return MenuDef;		
	}
	
	private void addSemanticCase(String csSemanticId, String csScreenId, CMenuDef MenuDef)
	{
		if(csScreenId.equals("*"))
			csScreenId = null;
		CSemanticItem SemanticItem = new CSemanticItem(csScreenId, MenuDef);
		m_hashSemanticItems.put(csSemanticId, SemanticItem);
	}
	
	public CMenuDef getMenuForSemanticContext(String csScreen, String csSemanticContext)
	{
		if(csSemanticContext != null)
		{
			CSemanticItem semanticItem = m_hashSemanticItems.get(csSemanticContext);
			if(semanticItem != null)
			{
				// Find the one with the correct screen
				return semanticItem.m_MenuDef;
			}
		}
		return null;
	}
	
	HashMap<String, CMenuDef>  m_hashMenus= new HashMap<String, CMenuDef> ();			// Array of CMenuDef, indexed by String csMenuId
	HashMap<String, CSemanticItem> m_hashSemanticItems = new HashMap<String, CSemanticItem>();	// Array of CSemanticItem, indexed by String csSemanticId
}
