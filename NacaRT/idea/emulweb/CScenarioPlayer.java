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
 * Created on Feb 3, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package idea.emulweb;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Vector;

import idea.manager.CMapFieldLoader;
import idea.onlinePrgEnv.OnlineSession;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import nacaLib.base.CJMapObject;
import nacaLib.misc.KeyPressed;
import jlib.xml.*;
;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CScenarioPlayer extends CJMapObject
{
	protected static class ScenarioPlayerState
	{
		public static int SHOW_PAGE = 1 ;
		public static int CALL_PROGRAM = 2 ;
	}
	protected static class ScenarioRecordDataMode
	{
		public static ScenarioRecordDataMode MODE_3270 = new ScenarioRecordDataMode() ;
		public static ScenarioRecordDataMode MODE_WEB = new ScenarioRecordDataMode() ;
	}
	public class CScenarioWarningDetail extends CJMapObject
	{
		public Element m_PageField;
		public Element m_ScenarioField;
		public EditedField m_PageFieldDetails;
		public EditedField m_ScenarioFieldDetails;

	}
	public CScenarioPlayer(String filepath, OnlineSession session)
	{
		m_session = session ;
		m_FilePath = filepath ;
		m_docScenario = XMLUtil.LoadXML(filepath) ;
		if (m_docScenario == null)
		{
			m_lstPages = null ;
		}
		else
		{
			String title = m_docScenario.getDocumentElement().getNodeName() ;
			String csXMLItemName = "" ;
			if (title.equalsIgnoreCase("datarecord"))
			{
				m_modeRecord = ScenarioRecordDataMode.MODE_WEB ;
				csXMLItemName = "form" ;
			}
			else if (title.equalsIgnoreCase("ST3270Catch"))
			{
				m_modeRecord = ScenarioRecordDataMode.MODE_3270 ;
				csXMLItemName = "Cycle" ;
			}
			else
			{
				throw new RuntimeException() ;
			}
			m_lstPages = m_docScenario.getElementsByTagName(csXMLItemName) ;
			m_nCurrentPage = 0 ;
		}
	}
	protected OnlineSession m_session = null ;
	protected String m_FilePath = "" ;
		/**
	 * @param m_docScenario
	 */

	protected Document m_docScenario = null ;
	protected NodeList m_lstPages = null ;
	protected int m_nPlayerState = 0 ;
	protected int m_nCurrentPage = 0 ;
	protected ScenarioRecordDataMode m_modeRecord = null ; 
//	protected String m_csScenarioFilePath = "" ;
	
	public void rewindScenario()
	{
		m_nPlayerState = 0 ;
		m_nCurrentPage = 0 ;
	}
	
	protected Document getCurrentPage()
	{
		Document docData = XMLUtil.CreateDocument() ;
		if (m_nCurrentPage>=0 && m_nCurrentPage<m_lstPages.getLength())
		{
			if (m_modeRecord == ScenarioRecordDataMode.MODE_WEB)
			{
				Element eForm = (Element)m_lstPages.item(m_nCurrentPage) ;
				Element e = (Element)docData.importNode(eForm, true) ;
				docData.appendChild(e);
			}
			else if (m_modeRecord == ScenarioRecordDataMode.MODE_3270)
			{
				Element eCycle = (Element)m_lstPages.item(m_nCurrentPage) ;
				Element eForm = docData.createElement("form") ;
				docData.appendChild(eForm) ;
				
				String csKeyPressed = SelectKeyPressedFrom3270(eCycle) ;
				eForm.setAttribute("keypressed", csKeyPressed) ;

				Document xmlOutput = m_session.getXMLOutput();
				String page = getPageNameFromXMLOutput(xmlOutput) ;
				eForm.setAttribute("page", page) ;
				
				FillFormFieldsFrom3270(xmlOutput.getDocumentElement(), eCycle, docData);
			}
			return docData ;
		}
		return null ;
	}

	private static void FillFormFieldsFrom3270(Element eForm, Element eCycle, Document data)
	{
		NodeList lst = eCycle.getElementsByTagName("FieldsUpdated") ;
		if (lst.getLength()>0)
		{
			Element eFields = (Element)lst.item(0);
			FillFormFieldsFrom3270Fields(eForm, eFields, data) ;
		}
	}

	/**
	 * @param eForm
	 * @param eFields
	 */
	public static class EditedField
	{
		public String value = "" ;
		public String poscol = "" ;
		public String posline = "" ;
		public String length = "" ;
		public String name = "" ;
		public String modified = "" ;
		Element field = null ;
		public boolean mutable = false ;
		String getKey()
		{
			return "c"+poscol+"_l"+posline ;
		}
	}
	private static void FillFormFieldsFrom3270Fields(Element eForm, Element eFields, Document data)
	{
		NodeList lst = eFields.getElementsByTagName("FieldUpdated") ;
		Hashtable<String, EditedField> tabFields = new Hashtable<String, EditedField>() ;
		for (int i=0; i<lst.getLength(); i++)
		{
			Element eField = (Element)lst.item(i) ;
			EditedField f = new EditedField() ;
			f.poscol = eField.getAttribute("X") ;
			f.posline = eField.getAttribute("Y") ;
			f.length = eField.getAttribute("Len");
			String modified = eField.getAttribute("KeyHit");
			if (modified.equals("true"))
			{
				f.value = eField.getAttribute("NewVal");
			}
			else
			{
				f.value = eField.getAttribute("OldVal");
			}
//			f.modified = eField.getAttribute("Update");
			String csKey = f.getKey() ;
			tabFields.put(csKey, f) ;
		}
		
		Vector<EditedField> arrFields = new Vector<EditedField>() ;
		lst = eForm.getElementsByTagName("edit") ;
		for (int i=0; i<lst.getLength(); i++)
		{
			Element eEdit = (Element)lst.item(i) ;
			EditedField f = new EditedField() ;
			f.poscol = eEdit.getAttribute("col") ;
			f.posline = eEdit.getAttribute("line") ;
			f.length = eEdit.getAttribute("length");
			f.value = eEdit.getAttribute("value");
			f.name = eEdit.getAttribute("linkedvalue");
			f.modified = eEdit.getAttribute("modified");
			String csKey = f.getKey() ;
			EditedField ff = tabFields.get(csKey) ;
			if (ff != null)
			{
				f.value = ff.value ;
				f.modified = "true" ;
			}
			arrFields.add(f) ;
		}

		Element eData = data.getDocumentElement() ;
		for (int i=0; i<arrFields.size(); i++)
		{
			EditedField f = arrFields.get(i) ;
			Element e = data.createElement("field") ;
			eData.appendChild(e) ;
			e.setAttribute("name", f.name) ;
			e.setAttribute("updated", f.modified) ;
			e.setAttribute("value", f.value) ;
		}
		
	}

	public String getPageNameFromXMLOutput(Document xmlOutput)
	{
		NodeList lst = xmlOutput.getElementsByTagName("form") ;
		if (lst.getLength() > 0)
		{
			Element e = (Element)lst.item(0);
			String name = e.getAttribute("name") ;
			return name ;
		}
		return "";
	}

	private String SelectKeyPressedFrom3270(Element eCycle)
	{
		NodeList lst = eCycle.getElementsByTagName("CommandKey");
		if (lst.getLength()>0)
		{
			Element eCmd = (Element)lst.item(0);
			String val = eCmd.getAttribute("Value") ;
			KeyPressed k = KeyPressed.getKey(val) ;
			if (k != null)
			{
				return k.m_csValue ;
			}
			else
			{
				return "" ;
			}
		}
		return "" ;
	}

	/**
	 * @return
	 */
	public boolean isPlayingScenario()
	{
		return m_docScenario != null && m_nCurrentPage < m_lstPages.getLength() ;
	}
	/**
	 * @return
	 */
	public boolean isCallProgram()
	{
		return m_nPlayerState == ScenarioPlayerState.CALL_PROGRAM;
	}
	/**
	 * 
	 */
	public void StepScenario()
	{
		if (m_nPlayerState == 0)
		{ // initial state : call program for first page
			m_nPlayerState = ScenarioPlayerState.CALL_PROGRAM;
		}
		else if (m_nPlayerState == ScenarioPlayerState.CALL_PROGRAM)
		{	// program has been called, show the page with new fields
			m_nPlayerState = ScenarioPlayerState.SHOW_PAGE ; 
			Document data = getCurrentPage() ;
			m_session.setXMLData(data) ;
		}
		else if (m_nPlayerState == ScenarioPlayerState.SHOW_PAGE)
		{ // the new page has been shown, call program with real values
			m_nPlayerState = ScenarioPlayerState.CALL_PROGRAM ;
			Document data = getCurrentPage() ;
			CMapFieldLoader fieldLoader = m_session.getInputWrapper() ;
			KeyPressed kp = fieldLoader.getKeyPressed() ;
			if (kp == KeyPressed.ENTER)
			{ // replace key pressed by scenario key pressed
				String k = data.getDocumentElement().getAttribute("keypressed");
				KeyPressed kp2 = KeyPressed.getKey(k) ;
				if (kp2 != null)
				{
					fieldLoader.setKeyPressed(kp2) ;
				}
			}
			else
			{	// user hit some key : stop scenario
				m_docScenario = null ;
				m_lstPages = null ;
				m_nCurrentPage = 0 ;
				m_nPlayerState = 0 ;
				return ;
			}
			
			m_nCurrentPage ++ ; 
			
		} 
	}
	public String getDisplay()
	{
		if (m_docScenario != null && m_lstPages != null)
		{
			if (m_nCurrentPage < m_lstPages.getLength())
			{
				if (m_nPlayerState == ScenarioPlayerState.CALL_PROGRAM)
				{	// program has been called, show the page with new fields
					return "Replay program call" ;
				}
				else if (m_nPlayerState == ScenarioPlayerState.SHOW_PAGE)
				{ // the new page has been shown, call program with real values
					return "Fill Fields" ;
				}
			}
		} 
		return "" ;
	}
	/**
	 * @return
	 */
	public boolean isShowPage()
	{
		return m_nPlayerState == ScenarioPlayerState.SHOW_PAGE ;
	}

	/**
	 * @return
	 */
	public int nextPage()
	{
		m_nCurrentPage ++ ;
		return m_nCurrentPage ;
	}

	protected Hashtable<String, CScenarioWarningDetail> m_tabScenarioWarningDetails = new Hashtable<String, CScenarioWarningDetail>() ;
	/**
	 * @param xmlOutput
	 */
	public void CheckOutput(Document xmlOutput)
	{
		if (m_docScenarioPlayingLog == null)
		{
			doCheckOutput(xmlOutput) ;
		}
		else
		{
			doLogPlaying(xmlOutput) ;
		}
	}

	private void doLogPlaying(Document xmlOutput)
	{	
		Element ePage = m_docScenarioPlayingLog.createElement("Output") ;
		m_docScenarioPlayingLog.getDocumentElement().appendChild(ePage) ;
		String name = getPageNameFromXMLOutput(xmlOutput) ;
		ePage.setAttribute("program", name) ;
		if (m_modeRecord == ScenarioRecordDataMode.MODE_3270)
		{
			String lang = xmlOutput.getDocumentElement().getAttribute("lang") ;
			Hashtable<String, EditedField> tabPageFields = new Hashtable<String, EditedField>() ;
			
			NodeList lst = xmlOutput.getElementsByTagName("edit") ;
			for (int i=0; i<lst.getLength(); i++)
			{
				Element eEdit = (Element)lst.item(i) ;
				EditedField f = new EditedField() ;
				f.poscol = eEdit.getAttribute("col") ;
				f.posline = eEdit.getAttribute("line") ;
				f.length = eEdit.getAttribute("length");
				f.value = eEdit.getAttribute("value").trim() ;
				f.name = eEdit.getAttribute("linkedvalue");
				f.modified = eEdit.getAttribute("modified");
				String mutable = eEdit.getAttribute("replayMutable") ;
				f.mutable = (mutable!= null) && mutable.equalsIgnoreCase("true") ;
				
				String intents = eEdit.getAttribute("intensity");
				f.mutable |= (intents!=null && intents.equals("dark")) ;
				
				String csKey = f.getKey() ;
				tabPageFields.put(csKey, f) ;
			}
			lst = xmlOutput.getElementsByTagName("label") ;
			for (int i=0; i<lst.getLength(); i++)
			{
				Element eEdit = (Element)lst.item(i) ;
				EditedField f = new EditedField() ;
				f.poscol = eEdit.getAttribute("col") ;
				f.posline = eEdit.getAttribute("line") ;
				f.length = eEdit.getAttribute("length");
				f.value = getLabelValue(eEdit, lang);
				f.name = eEdit.getAttribute("linkedvalue");
				f.modified = eEdit.getAttribute("modified");
				String intents = eEdit.getAttribute("intensity");
				f.mutable |= (intents!=null && intents.equals("dark")) ;
				
				String csKey = f.getKey() ;
				tabPageFields.put(csKey, f) ;
			}
			lst = xmlOutput.getElementsByTagName("title") ;
			for (int i=0; i<lst.getLength(); i++)
			{
				Element eEdit = (Element)lst.item(i) ;
				EditedField f = new EditedField() ;
				f.poscol = eEdit.getAttribute("col") ;
				f.posline = eEdit.getAttribute("line") ;
				f.length = eEdit.getAttribute("length");
				f.value = getLabelValue(eEdit, lang);
				f.name = eEdit.getAttribute("linkedvalue");
				f.modified = eEdit.getAttribute("modified");
				f.mutable = true ;
				String csKey = f.getKey() ;
				tabPageFields.put(csKey, f) ;
			}
			
			Element eCycle = (Element)m_lstPages.item(m_nCurrentPage) ;
			lst = eCycle.getElementsByTagName("Field") ;
			if (lst.getLength() == 0)
			{
				return ;
			}
			for (int i=0; i<lst.getLength(); i++)
			{
				Element eField = (Element)lst.item(i) ;
				EditedField f = new EditedField() ;
				f.poscol = eField.getAttribute("X") ;
				f.posline = eField.getAttribute("Y") ;
				f.length = eField.getAttribute("Len");
				//String modified = eField.getAttribute("KeyHit");
				f.value = eField.getAttribute("Val").trim();
				f.modified = eField.getAttribute("Update");
				String mutable = eField.getAttribute("mutable") ;
				if (mutable != null && !mutable.equals(""))
				{
					f.mutable = true ;
				}
				f.field = eField ;
				String csKey = f.getKey() ;
				EditedField ff = tabPageFields.get(csKey) ;
				if (ff != null)
				{
					Element e = m_docScenarioPlayingLog.createElement("Field") ;
					ePage.appendChild(e) ;
					e.setAttribute("name", ff.name) ;
					e.setAttribute("key", ff.posline+"."+ff.poscol) ;
					e.setAttribute("value", ff.value) ;
					if (f.mutable || ff.mutable)
					{
						e.setAttribute("mutable", "true") ;
					}
				}
			}
		}		
	}

	private void doCheckOutput(Document xmlOutput)
	{
		m_tabScenarioWarningDetails.clear() ;
		if (m_modeRecord == ScenarioRecordDataMode.MODE_3270)
		{
			String lang = xmlOutput.getDocumentElement().getAttribute("lang") ;
			Hashtable<String, EditedField> tabPageFields = new Hashtable<String, EditedField>() ;
			Element eCycle = (Element)m_lstPages.item(m_nCurrentPage) ;
			NodeList lst = eCycle.getElementsByTagName("Field") ;
			if (lst.getLength() == 0)
			{
				return ;
			}
			for (int i=0; i<lst.getLength(); i++)
			{
				Element eField = (Element)lst.item(i) ;
				EditedField f = new EditedField() ;
				f.poscol = eField.getAttribute("X") ;
				f.posline = eField.getAttribute("Y") ;
				f.length = eField.getAttribute("Len");
				//String modified = eField.getAttribute("KeyHit");
				f.value = eField.getAttribute("Val").trim();
				f.modified = eField.getAttribute("Update");
				String mutable = eField.getAttribute("mutable") ;
				if (mutable != null && !mutable.equals(""))
				{
					f.mutable = true ;
				}
				f.field = eField ;
				String csKey = f.getKey() ;
				tabPageFields.put(csKey, f) ;
			}
			
			lst = xmlOutput.getElementsByTagName("edit") ;
			for (int i=0; i<lst.getLength(); i++)
			{
				Element eEdit = (Element)lst.item(i) ;
				EditedField f = new EditedField() ;
				f.poscol = eEdit.getAttribute("col") ;
				f.posline = eEdit.getAttribute("line") ;
				f.length = eEdit.getAttribute("length");
				f.value = eEdit.getAttribute("value").trim() ;
				f.name = eEdit.getAttribute("linkedvalue");
				f.modified = eEdit.getAttribute("modified");
				String mutable = eEdit.getAttribute("replayMutable") ;
				boolean bMutable = mutable!= null && mutable.equalsIgnoreCase("true") ; 
				String csKey = f.getKey() ;
				EditedField ff = tabPageFields.get(csKey) ;
				if (ff != null)
				{
					if ((!f.value.equals(ff.value) && !f.value.endsWith(ff.value)) && !bMutable && !ff.mutable)
					{
						System.out.println("Unmatching value for field : "+f.name+" ; field : "+f.value + " ; original : "+ff.value) ;
						Element eWarn = xmlOutput.createElement("warning") ; 
						if (ff.value.equals(""))
						{
							eWarn.setAttribute("value", "(empty)") ;
						}
						else
						{
							eWarn.setAttribute("value", ff.value) ;
						}
						eEdit.getParentNode().insertBefore(eWarn, eEdit) ;
						CScenarioWarningDetail detail = new CScenarioWarningDetail() ;
						detail.m_PageField = eEdit ;
						detail.m_ScenarioField = ff.field ;
						detail.m_PageFieldDetails = f ;
						detail.m_ScenarioFieldDetails = ff ;
						String id = "EDIT" + i ;
						eWarn.setAttribute("id", id) ;
						m_tabScenarioWarningDetails.put(id, detail) ;
					}
					tabPageFields.remove(csKey) ;
				}
				else
				{
//					System.out.println("Field not found in original page : "+f.name) ;
				}
			}
			lst = xmlOutput.getElementsByTagName("label") ;
			for (int i=0; i<lst.getLength(); i++)
			{
				Element eEdit = (Element)lst.item(i) ;
				EditedField f = new EditedField() ;
				f.poscol = eEdit.getAttribute("col") ;
				f.posline = eEdit.getAttribute("line") ;
				f.length = eEdit.getAttribute("length");
				f.value = getLabelValue(eEdit, lang);
				f.name = eEdit.getAttribute("linkedvalue");
				f.modified = eEdit.getAttribute("modified");
				String csKey = f.getKey() ;
				EditedField ff = tabPageFields.get(csKey) ;
				if (ff != null)
				{
					if (!f.value.equals(ff.value) && !ff.mutable)
					{
						System.out.println("Unmatching value for label : col="+f.poscol+" ; line="+f.posline+" ; field : "+f.value + " ; original : "+ff.value) ;
						Element eWarn = xmlOutput.createElement("warning") ; 
						if (ff.value.equals(""))
						{
							eWarn.setAttribute("value", "(empty)") ;
						}
						else
						{
							eWarn.setAttribute("value", ff.value) ;
						}
						eEdit.getParentNode().insertBefore(eWarn, eEdit) ;
						CScenarioWarningDetail detail = new CScenarioWarningDetail() ;
						detail.m_PageField = eEdit ;
						detail.m_ScenarioField = ff.field ;
						detail.m_PageFieldDetails = f ;
						detail.m_ScenarioFieldDetails = ff ;
						String id = "LABEL" + i ;
						eWarn.setAttribute("id", id) ;
						m_tabScenarioWarningDetails.put(id, detail) ;
						if (f.name.equals(""))
						{
							f.name = "(label)" ;
						}
					}
					tabPageFields.remove(csKey) ;
				}
				else
				{
//					System.out.println("Label not found in original page : col="+f.poscol+" ; line="+f.posline+" ; value="+f.value) ;
				}
			}
			lst = xmlOutput.getElementsByTagName("title") ;
			for (int i=0; i<lst.getLength(); i++)
			{
				Element eEdit = (Element)lst.item(i) ;
				EditedField f = new EditedField() ;
				f.poscol = eEdit.getAttribute("col") ;
				f.posline = eEdit.getAttribute("line") ;
				f.length = eEdit.getAttribute("length");
				f.value = getLabelValue(eEdit, lang);
				f.name = eEdit.getAttribute("linkedvalue");
				f.modified = eEdit.getAttribute("modified");
				String csKey = f.getKey() ;
				EditedField ff = tabPageFields.get(csKey) ;
				if (ff != null)
				{
					if (!f.value.equalsIgnoreCase(ff.value) && !ff.mutable)
					{
						System.out.println("Unmatching value for title : "+f.name+" ; field : "+f.value + " ; original : "+ff.value) ;
						Element eWarn = xmlOutput.createElement("warning") ; 
						if (ff.value.equals(""))
						{
							eWarn.setAttribute("value", "(empty)") ;
						}
						else
						{
							eWarn.setAttribute("value", ff.value) ;
						}
						eEdit.getParentNode().insertBefore(eWarn, eEdit) ;
						CScenarioWarningDetail detail = new CScenarioWarningDetail() ;
						detail.m_PageField = eEdit ;
						detail.m_ScenarioField = ff.field ;
						detail.m_PageFieldDetails = f ;
						detail.m_ScenarioFieldDetails = ff ;
						String id = "TITLE" + i ;
						eWarn.setAttribute("id", id) ;
						m_tabScenarioWarningDetails.put(id, detail) ;
					}
					tabPageFields.remove(csKey) ;
				}
				else
				{
//					System.out.println("title not found in original page : col="+f.poscol+" ; line="+f.posline+" ; value="+f.value) ;
				}
			}
			
			Enumeration enumere = tabPageFields.elements() ;
			try
			{
				EditedField f = (EditedField)enumere.nextElement() ;
				while (f != null)
				{
//					System.out.println("Field not found in new page : col="+f.poscol+" ; line="+f.posline+" ; value="+f.value) ; 
					f = (EditedField)enumere.nextElement() ;
				}
			}
			catch (NoSuchElementException e)
			{
			}
		}		
	}

	/**
	 * @param edit
	 * @param lang
	 * @return
	 */
	private String getLabelValue(Element edit, String lang)
	{
		NodeList lst = edit.getElementsByTagName("text") ;
		for (int i=0; i<lst.getLength(); i++)
		{
			Element e = (Element)lst.item(i);
			String l = e.getAttribute("lang") ;
			if (l.equals(lang))
			{
				String text = e.getFirstChild().getNodeValue() ;
				return text.trim() ;
			}
		}
		
		return "" ;
	}

	/**
	 * @return
	 */
	public KeyPressed getKeyPressed()
	{
		return null;
	}

	/**
	 * @param sceId
	 * @return
	 */
	public CScenarioWarningDetail getWarningDetail(String sceId)
	{
		return m_tabScenarioWarningDetails.get(sceId) ;
	}

	/**
	 * @param sceId
	 */
	public void IgnoreWarning(String sceId)
	{
		CScenarioWarningDetail detail = m_tabScenarioWarningDetails.get(sceId) ;
		if (detail != null)
		{
			detail.m_ScenarioField.setAttribute("mutable", "true") ;
			if (XMLUtil.ExportXML(m_docScenario, m_FilePath))
			{
				detail.m_ScenarioFieldDetails.mutable = true ;
			}
		}
	}

	/**
	 * @param docOutput
	 */
	public void setDocumentTracing(Document docOutput)
	{
		m_docScenarioPlayingLog = docOutput ;
	}
	protected Document m_docScenarioPlayingLog = null ;

}
