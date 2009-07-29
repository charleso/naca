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
package idea.onlinePrgEnv;

import idea.manager.CESMManager;
import idea.manager.CESMSendMap;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import jlib.misc.NumberParser;

import nacaLib.basePrgEnv.BaseCESMManager;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseSession;
import jlib.sql.DbConnectionManagerBase;
import jlib.xml.*;
import nacaLib.program.CESMCommandCode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/*
 * Created on 20 oct. 2004
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

public class OnlineEnvironment extends BaseEnvironment 
{	
	public OnlineEnvironment(OnlineSession session, DbConnectionManagerBase connectionManager)
	{
		super(session, connectionManager, session.getBaseResourceManager());
		m_Session = session ;			
	}
	
	protected OnlineSession m_Session = null;
	protected CESMSendMap m_SendMapOrder = null;
	protected boolean m_bHasOutput = false;
	private Document m_xmlData = null;
	
	/* m_csLastCommandCode 
	 * see for info :
	 * http://publib.boulder.ibm.com/infocenter/txen/index.jsp?topic=/com.ibm.txseries510.doc/erzhai00148.htm
	 */
	public BaseSession getSession()
	{
		return m_Session;
	}
	
	public void resetSession()
	{
		m_Session = null;
		super.resetSession();
	}

	public void setXMLData(Document xmlData)
	{
		m_xmlData = xmlData;
	}
	

	
	public Document getXMLData()
	{
		return m_xmlData ;
	}

	public Document getXMLDisplay()
	{
		Document doc = createNewDocument() ;
		FieldComparator comp = new FieldComparator() ;
		SortedSet<Element> setFields = new TreeSet<Element>(comp) ;
//		if (m_SendMapOrder != null)
//		{
//			Form form = m_SendMapOrder.m_varFrom ;
//		}
		
		Element eRoot = createNewFormBody(doc, "CESM", "CESM") ;
		Element eBody = createVBox(doc, eRoot);
		int nb = setFields.size() ;
		Element[] arr = new Element[nb] ;
		setFields.toArray(arr);
		int curline = 0 ;
		int curCol = 0 ;
		Element curLineElem = null ;
		for (int i=0; i<nb; i++)
		{
			Element f = arr[i] ;
			int nl = NumberParser.getAsInt(f.getAttribute("PosLine"));
			if (curline != nl)
			{
				curLineElem = createHBox(doc, eBody);
				curline = nl ;
				curCol = 1 ;
			}
			int nc = NumberParser.getAsInt(f.getAttribute("PosCol"));
			int nlen = NumberParser.getAsInt(f.getAttribute("Length"));
			if (nc > curCol +1)
			{
				createBlank(doc, curLineElem, nc - curCol) ;
			}
			curCol = nc + nlen ;
			curLineElem.appendChild(f);
		}
		
		return doc;
	}
	
	private Document createNewDocument()
	{
		try
		{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.newDocument();
			return doc;
		}
		catch(ParserConfigurationException e)
		{
			return null ;
		}		
	}
	
	private Element createNewFormBody(Document doc, String csFormName, String csTitle)
	{
		Element eProgram = doc.createElement("Root") ;
		doc.appendChild(eProgram) ;
			
		Element eForm = doc.createElement("Form");
		eProgram.appendChild(eForm);
		Element eName = doc.createElement("Name") ;
		eForm.appendChild(eName) ;
		eName.appendChild(doc.createTextNode(csFormName));
		eForm.setAttribute("Title", csTitle);
		
		Element eBody = doc.createElement("FormBody");
		eForm.appendChild(eBody);
		
		return eBody;
	}
	
	private Element createVBox(Document doc, Element eParent)
	{
		Element eVBox = doc.createElement("VBox");
		eParent.appendChild(eVBox);
		return eVBox;
	}
	
	private Element createHBox(Document doc, Element eParent)
	{
		Element eHBox = doc.createElement("HBox");
		eParent.appendChild(eHBox);
		return eHBox;
	} 
		
	private Element createBlank(Document doc, Element eParent, int size)
	{
		Element eBlank = doc.createElement("Blank");
		eBlank.setAttribute("Length", ""+size);
		String cs = "" ;
		for (int i=0; i<size; i++)
		{
			cs += " " ;
		}
		eBlank.setAttribute("Text", cs);
		eParent.appendChild(eBlank);
		return eBlank;
	} 
	
	private class FieldComparator implements Comparator<Element> 
	{
		public int compare(Element e1, Element e2)
		{
			int line1 = NumberParser.getAsInt(e1.getAttribute("PosLine"));
			int line2 = NumberParser.getAsInt(e2.getAttribute("PosLine"));
			if (line1 < line2)
			{
				return -1 ;
			}
			else if (line1 > line2)
			{
				return 1 ;
			}
			else 
			{
				int col1 = NumberParser.getAsInt(e1.getAttribute("PosCol"));
				int col2 = NumberParser.getAsInt(e2.getAttribute("PosCol"));
				if (col1 < col2)
				{
					return -1 ;
				}
				else if (col1 > col2)
				{
					return 1 ;
				}
				else
				{
					return 0;
				}
			}
		}
	}

	public void addMapOrder(CESMSendMap order)
	{
		m_SendMapOrder = order ;		
	}
	
	public void addOutput()
	{
		m_bHasOutput = true;
	}
	
	public void resetOutput()
	{
		m_bHasOutput = false;
	}

	public void resetNewTransaction()
	{
		doResetNewTransaction();
		m_SendMapOrder = null  ;
	}
	public boolean hasOutput()
	{
		return m_SendMapOrder != null || m_bHasOutput;
	}	
	
	public void RegisterOutput()
	{
		if (m_SendMapOrder != null)
		{
			String csDeclaredFormName = m_SendMapOrder.m_varFrom.getDeclaredFormName(); 
			m_Session.setIdPage(csDeclaredFormName) ;
			Document doc = m_SendMapOrder.m_varFrom.getXMLData(m_SendMapOrder.m_MapName, m_SendMapOrder.m_nCursorPosition) ;
			m_Session.setXMLData(doc) ;
		}
		
	}
	
	/**
	 * @param m_elCESMConfig
	 */
	public void Init(Tag tagCESMConfig)
	{
		configInit(tagCESMConfig);
		if (tagCESMConfig != null)
		{			
			if(tagCESMConfig.isValExisting("StartProgramId"))
				setNextProgramToLoad(tagCESMConfig.getVal("StartProgramId"));
			
			if(tagCESMConfig.isValExisting("StartCommandCode"))
			{
				m_csLastCommandCode = tagCESMConfig.getVal("StartCommandCode") ;
				if (m_csLastCommandCode.equals("XCTL"))
				{
					m_csLastCommandCode = CESMCommandCode.XCTL ;
				}
				else if (m_csLastCommandCode.equals("START"))
				{
					m_csLastCommandCode = CESMCommandCode.START ;
				}
				else  
				{
					m_csLastCommandCode = "" ;
				}
			}
			
			if(tagCESMConfig.isValExisting("DataRecordPath"))
			{
				m_csDataRecordPath = tagCESMConfig.getVal("DataRecordPath") ;
				
				if (tagCESMConfig.isValExisting("DataRecordFilePattern"))
				{
					m_csDataRecordFilePattern = tagCESMConfig.getVal("DataRecordFilePattern") ;
				}
				
				if (!m_csDataRecordPath.endsWith("\\") && !m_csDataRecordPath.endsWith("/"))
				{
					m_csDataRecordPath += '/' ;
				}
			}
		}
		m_csTermID = OnlineEnvironment.getNextTermID() ; 
	}
	
	
	private static int ms_LastTermID = 0 ;
	private static String getNextTermID()
	{
		int n = ms_LastTermID ++ ;
		return "" + (n/1000)%10 + (n/100)%10 + (n/10)%10 + (n)%10 ;
	}
	/**
	 * @return
	 */
	



	public String getLanguageCode()
	{
		String cs = "" + m_acTCTTUA[36] + m_acTCTTUA[37] ;
		return cs ;
	}
	/**
	 * @return
	 */
	public String getSocietyCode()
	{
		String cs = "" + m_acTCTTUA[12] + m_acTCTTUA[13] + m_acTCTTUA[14] ;
		return cs ;
	}
	/**
	 * @return
	 */
	public String getAgencyCode()
	{
		String cs = "" + m_acTCTTUA[15] + m_acTCTTUA[16] + m_acTCTTUA[17] ;
		return cs ;
	}
	/**
	 * @return
	 */
	public String getUserId()
	{
		String cs = "" + m_acTCTTUA[38] + m_acTCTTUA[39] + m_acTCTTUA[40] ;
		return cs ;
	}
	
	/**
	 * @return
	 */
	public String getApplication()
	{
		String cs = "" + m_acTCTTUA[127] + m_acTCTTUA[128] ;
		return cs ;
	}
	
	protected String m_csDataRecordFilePattern = "" ;
	protected String m_csDataRecordPath = "" ;
	protected String m_csDataRecordFilePath = "" ;
	protected Document m_docDataRecord = null ;

	public void recordInput()
	{
		if (m_docDataRecord == null)
		{
			m_csDataRecordFilePath = m_csDataRecordPath + m_csDataRecordFilePattern + m_csTermID + ".xml" ;
			try
			{
				m_docDataRecord = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument() ;
			}
			catch (ParserConfigurationException e)
			{
				e.printStackTrace();
			}
			catch (FactoryConfigurationError e)
			{
				e.printStackTrace();
			}
			Element e = m_docDataRecord.createElement("datarecord") ;
			m_docDataRecord.appendChild(e) ;
		}
		Element eRoot = m_docDataRecord.getDocumentElement() ;
		Element eCopy = (Element)m_docDataRecord.importNode(m_xmlData.getDocumentElement(), true) ;
		eRoot.appendChild(eCopy) ;
		XMLUtil.ExportXML(m_docDataRecord, m_csDataRecordFilePath) ;
	}

	public String getUserLanguageId()
	{
		return getLanguageCode() ;
	}

	public String getProfitCenter()
	{
		return getSocietyCode().substring(1) + getAgencyCode();
	}

//	public String getCmpSession()
//	{
//		if(m_Session != null)
//			return m_Session.getCmp();
//		return "";
//	}

	public String getUserLdapId()
	{
		if(m_Session != null)
			return m_Session.getUserLdapId();
		return "";
	}

	public BaseCESMManager createCESMManager()
	{
		return new CESMManager(this);
	}
}
