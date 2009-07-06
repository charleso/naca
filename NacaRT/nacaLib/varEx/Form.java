/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

//import nacaLib.program.Var;
import java.util.Hashtable;
import java.util.SortedSet;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jlib.misc.ArrayDyn;
import jlib.misc.ArrayFix;
import jlib.misc.ArrayFixDyn;
import jlib.misc.AsciiEbcdicConverter;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.programPool.SharedProgramInstanceData;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/*
 * Created on 13 oct. 2004
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
public class Form extends Var
{
	public Form(DeclareTypeForm declareTypeForm, String csDeclaredFormName)
	{
		super(declareTypeForm);
		
		SharedProgramInstanceData sharedProgramInstanceData = declareTypeForm.getProgramManager().getSharedProgramInstanceData();
		sharedProgramInstanceData.addVarDefForm((VarDefForm)m_varDef);
		
		m_csDeclaredFormName = csDeclaredFormName;
		
		//VarLevel varLevelHeader = new VarLevel(declareTypeForm.getProgram(), 2);
	}
	
	protected Form()
	{
		super();
	}
	
	protected VarBase allocCopy()
	{
		Form v = new Form();
		return v;
	}
	
	public void assignBufferExt(VarBuffer bufferSource)
	{
		super.assignBufferExt(bufferSource);
		// Swap type
		if(m_arrEdits.isDyn())
		{
			int nSize = m_arrEdits.size();
			EditInMap arr[] = new EditInMap[nSize];
			m_arrEdits.transferInto(arr);
			
			ArrayFix<EditInMap> arrFix = new ArrayFix<EditInMap>(arr);
			m_arrEdits = arrFix;	// replace by a fix one (uning less memory)
		}
	}
	
	public void set(String cs)
	{
		m_varDef.write(m_bufferPos, cs);
	}
	
	public void set(char c)
	{
	}

	protected String getAsLoggableString()
	{
		return "";
	}
	
	VarDefForm getDefForm()
	{
		return (VarDefForm) m_varDef;
	}
	
	public boolean hasType(VarTypeEnum e)
	{
		return false;
	}
	
	public void encodeToVar(Var varDest)
	{
		((VarDefForm)m_varDef).encodeToVar(m_bufferPos, varDest);
	}
	
	public void decodeFromVar(Var varSource)
	{
		((VarDefForm)m_varDef).decodeFromVar(m_bufferPos, varSource);
	}
	
	public void decodeFromCharBuffer(InternalCharBuffer charBufferSource)
	{
		((VarDefForm)m_varDef).decodeFromCharBuffer(m_bufferPos, charBufferSource);
	}
	
	void addEdit(EditInMap edit)
	{
		m_arrEdits.add(edit);
	}
	
	public Document getXMLData(String langID, int cursorPosition)
	{
		try
		{
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument() ;
			Element eForm = doc.createElement("form");
			doc.appendChild(eForm);
			eForm.setAttribute("name", m_csDeclaredFormName) ; 
			eForm.setAttribute("lang", langID) ;
			if (cursorPosition != 0)
			{
				eForm.setAttribute("cursorPosition", new Integer(cursorPosition).toString()) ;
			}
			for (int i=0; i<m_arrEdits.size(); i++)
			{
				Edit edit = m_arrEdits.get(i);
				Element e = edit.exportXML(doc, langID) ;
				eForm.appendChild(e) ;
			}
			return doc ;
		}
		catch (ParserConfigurationException e)
		{
		}
		return null;
	}
	
	public Edit getEdit(String name)
	{
		for (int i=0; i<m_arrEdits.size(); i++)
		{
			EditInMap e = m_arrEdits.get(i) ;
			if (e.getDeclaredEditName().equals(name))
			{
				return e ;
			}
		}
		return null ;
	}
	public void ExportFields(SortedSet<Element> setFields, Document doc, String csLangId)
	{
		for (int i=0; i<m_arrEdits.size(); i++)
		{
			Edit edit = m_arrEdits.get(i);
			Element e = edit.exportXML(doc, csLangId) ;
			if(e != null)
				setFields.add(e) ;
		}
		
		for (int i=0; i<m_arrEdits.size(); i++)
		{		
			Edit edit = m_arrEdits.get(i);
			Element e = edit.exportXML(doc, csLangId) ;
			if(e != null)
				setFields.add(e) ;
		}
	}
//	
/*
//	public Edit GetFieldAt(int nField)
//	{
//		assertIfFalse(nField < m_arrFields.size()) ;
//		return (Edit) m_arrFields.get(nField) ;
//	}
*/

	public Edit GetEditAt(int nField)
	{
		if(nField < m_arrEdits.size())
			return m_arrEdits.get(nField) ;
		return null; 
	}
	
	public String getDeclaredFormName()
	{
		return m_csDeclaredFormName;
	}

	public InternalCharBuffer encodeToCharBuffer()
	{
		int nDestLength = m_varDef.getBodyLength() + m_varDef.getHeaderLength();
		InternalCharBuffer charBuffer = ((VarDefForm)m_varDef).encodeToCharBuffer(nDestLength);
		return charBuffer;
	}
	
	public void loadValues(Document xmlData)
	{
		Element eForm = xmlData.getDocumentElement() ;
		NodeList lstFields = eForm.getElementsByTagName("field") ;
		Hashtable<String, Element> tabFields = new Hashtable<String, Element>() ;
		int nFields = lstFields.getLength() ;
		for (int i=0; i<nFields; i++)
		{
			Element e = (Element)lstFields.item(i) ;
			String name = e.getAttribute("name");
			tabFields.put(name, e) ;
		}
		for (int i=0; i<m_arrEdits.size(); i++)
		{
			EditInMap edit = m_arrEdits.get(i) ;
			Element eField = tabFields.get(edit.getDeclaredEditName()) ;
			edit.fillWithValue(eField);
		}
	}

//	public void initialize()
//	{
//		InitializeManager initializeManagerManager = TempCacheLocator.getTLSTempCache().getInitializeManagerLowValue();
//		m_varDef.initializeItemAndChildren(m_bufferPos, initializeManagerManager, 0);
//		for (int i=0; i<m_arrEdits.size(); i++)
//		{
//			EditInMap edit = m_arrEdits.get(i) ;
//			edit.initializeAttributes();
//		}
//	}
	
	public void initialize(InitializeCache initializeCache)
	{
		if(initializeCache != null && initializeCache.isFilled())	// initializeCache may be null 
		{
			//m_varDef.initializeUsingCache(m_bufferPos, initializeCache);
			initializeCache.applyItems(m_bufferPos, m_bufferPos.m_nAbsolutePosition);
		}
		else	
		{
			TempCache tempCache = TempCacheLocator.getTLSTempCache();
			InitializeManager initializeManagerManager = tempCache.getInitializeManagerLowValue();
			
			m_varDef.initializeItemAndChildren(m_bufferPos, initializeManagerManager, 0, initializeCache);
			
			if(initializeCache != null)
				initializeCache.setFilledAndcompress(m_bufferPos.m_nAbsolutePosition);
		}
		
		for (int i=0; i<m_arrEdits.size(); i++)
		{
			EditInMap edit = m_arrEdits.get(i) ;
			edit.initializeAttributes();
		}
	}

	
	
	public int compareTo(int nValue)
	{
		int nVarValue = getInt();
		return nVarValue - nValue;
	}
	
	
	public int compareTo(double dValue)
	{
		double dVarValue = getDouble();
		double d = dVarValue - dValue;
		if(d < -0.00001)	//Consider epsilon precision at 10 e-5 
			return -1;
		else if(d > 0.00001)	//Consider epsilon precision at 10 e-5
			return 1;
		return 0;			
	} 
	

	protected byte[] convertUnicodeToEbcdic(char[] tChars)
	{
		return AsciiEbcdicConverter.noConvertUnicodeToEbcdic(tChars);
	}
	
	protected char[] convertEbcdicToUnicode(byte[] tBytes)
	{
		return AsciiEbcdicConverter.noConvertEbcdicToUnicode(tBytes);
	}
	
	
	public VarType getVarType()
	{
		return VarType.VarForm;
	}

	private ArrayFixDyn<EditInMap> m_arrEdits = new ArrayDyn<EditInMap>();	// Array of VarEditInMap
	private String m_csDeclaredFormName = null;
}
