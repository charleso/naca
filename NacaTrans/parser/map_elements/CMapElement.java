/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 30 juil. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.map_elements;


import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

import jlib.misc.NumberParser;
import jlib.xml.Tag;
import jlib.xml.TagCursor;

import lexer.*;
import lexer.BMS.CBMSConstantList;
import lexer.BMS.CBMSKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.utils.StringVector;

import parser.CBaseElement;
import parser.BMS.CBMSElement;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.forms.CEntityResourceField;
import semantic.forms.CEntityResourceForm;
import semantic.forms.CEntityResourceFormContainer;
import semantic.forms.CResourceStrings;
import utils.NacaTransAssertException;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CMapElement extends CBMSElement
{

	/**
	 * @param name
	 * @param line
	 */
	public CMapElement(String name, int line)
	{
		super(name, line);
	}

	/* (non-Javadoc)
	 * @see parser.CBMSElement#DoExportCustom(org.w3c.dom.Document)
	 */
	protected Element DoExportCustom(Document root)
	{
		Element eMS = root.createElement("Map") ;
		Element eAttr = root.createElement("Attributes") ;
		eMS.appendChild(eAttr) ;
		eAttr.setAttribute("SizeCol", String.valueOf(m_Size_Col)) ;
		eAttr.setAttribute("SizeLine", String.valueOf(m_Size_Line)) ;
		eAttr.setAttribute("Line", m_Line.m_Name) ;
		eAttr.setAttribute("Column", m_Column.m_Name) ;
		eAttr.setAttribute("Data", m_Data.m_Name) ;
		eAttr.setAttribute("TIOAPFX", m_TIOAPFX.m_Name) ;
		eAttr.setAttribute("OBFMT", m_OBFMT.m_Name) ;
		for (int i=0; i<m_arrCTRL.size(); i++)
		{
			String val = m_arrCTRL.elementAt(i) ;
			Element e = root.createElement("CTRL") ;
			e.setAttribute("Value", val);
			eAttr.appendChild(e) ; 
		}
		for (int i=0; i<m_arrMAPATTS.size(); i++)
		{
			String val = m_arrMAPATTS.elementAt(i) ;
			Element e = root.createElement("MAPATTS") ;
			e.setAttribute("Value", val);
			eAttr.appendChild(e) ; 
		}
		for (int i=0; i<m_arrJUSTIFY.size(); i++)
		{
			String val = m_arrJUSTIFY.elementAt(i) ;
			Element e = root.createElement("JUSTIFY") ;
			e.setAttribute("Value", val);
			eAttr.appendChild(e) ; 
		}
		for (int i=0; i<m_arrDSATTS.size(); i++)
		{
			String val = m_arrDSATTS.elementAt(i) ;
			Element e = root.createElement("DSATTS") ;
			e.setAttribute("Value", val);
			eAttr.appendChild(e) ; 
		}
		return eMS ;
	}


	/* (non-Javadoc)
	 * @see parser.CBMSElement#InterpretKeyword(lexer.CReservedKeyword, lexer.CTokenList)
	 */
	protected boolean InterpretKeyword(CReservedKeyword kw, CTokenList lstTokens)
	{
		if (kw == CBMSKeywordList.SIZE)
		{ //SIZE=(024,080)
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetType() != CTokenType.LEFT_BRACKET)
			{
				Transcoder.logError(getLine(), "Expecting LEFT_BRACKET") ;
				return false ;
			}
			tok = GetNext() ;
			if (tok.GetType() != CTokenType.NUMBER)
			{
				Transcoder.logError(getLine(), "Expecting NUMBER") ;
				return false ;
			}
			m_Size_Line = tok.GetIntValue() ;
			tok = GetNext() ;
			if (tok.GetType() != CTokenType.COMMA)
			{
				Transcoder.logError(getLine(), "Expecting COMMA") ;
				return false ;
			}
			tok = GetNext();
			if (tok.GetType() != CTokenType.NUMBER)
			{
				Transcoder.logError(getLine(), "Expecting NUMBER") ;
				return false ;
			}
			m_Size_Col = tok.GetIntValue() ;
			tok = GetNext() ;
			if (tok.GetType() != CTokenType.RIGHT_BRACKET)
			{
				Transcoder.logError(getLine(), "Expecting RIGHT_BRACKET") ;
				return false ;
			}
			StepNext() ;
		}
		else if (kw == CBMSKeywordList.LINE)
		{ //LINE=NEXT
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetConstant() == CBMSConstantList.NEXT)
			{
				m_Line = tok.GetConstant();
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting for LINE : " + tok.GetValue()) ;
				return false ; 
			}
			StepNext() ;
		}
		else if (kw == CBMSKeywordList.COLUMN)
		{ // COLUMN=SAME
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetConstant() == CBMSConstantList.SAME)
			{
				m_Column = tok.GetConstant();
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting for COLUMN : " + tok.GetValue()) ;
				return false ; 
			}
			StepNext() ;
		}
		else if (kw == CBMSKeywordList.DATA)
		{ // DATA=FIELD
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetConstant() == CBMSConstantList.FIELD)
			{
				m_Data = tok.GetConstant();
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting for DATA : " + tok.GetValue()) ;
				return false ; 
			}
			StepNext() ;
		}
		else if (kw == CBMSKeywordList.OBFMT)
		{ //OBFMT=NO
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetConstant() == CBMSConstantList.NO)
			{
				m_OBFMT = tok.GetConstant();
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting for OBFMT : " + tok.GetValue()) ;
				return false ; 
			}
			StepNext() ;
		}
		else if (kw == CBMSKeywordList.TIOAPFX)
		{ // TIOAPFX=YES
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetConstant() == CBMSConstantList.YES)
			{
				m_TIOAPFX = tok.GetConstant();
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting for TIOAPFX : " + tok.GetValue()) ;
				return false ; 
			}
			StepNext() ;
		}
		else if (kw == CBMSKeywordList.CTRL)
		{ // CTRL=(HONEOM,FREEKB,ALARM,FRSET)
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetType()!= CTokenType.LEFT_BRACKET)
			{
				Transcoder.logError(getLine(), "Expecting LEFT_BRACKET") ;
				return false ;
			}
			tok = GetNext();
			boolean bDone = false ;
			while (!bDone)
			{
				tok = GetCurrentToken();
				if (tok.GetConstant() == CBMSConstantList.HONEOM ||
					tok.GetConstant() == CBMSConstantList.FREEKB || 
					tok.GetConstant() == CBMSConstantList.ALARM ||
					tok.GetConstant() == CBMSConstantList.FRSET || 
					tok.GetConstant() == CBMSConstantList.L80)
				{
					m_arrCTRL.addElement(tok.GetValue()) ;
				}
				else if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					bDone = true ;
				}
				else if (tok.GetType() == CTokenType.COMMA)
				{
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting for CTRL : " + tok.GetValue()) ;
					return false ; 
				}
				StepNext() ;
			}
		}
		else if (kw == CBMSKeywordList.MAPATTS)
		{ // MAPATTS=(COLOR,PS,HILIGHT,VALIDN)
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetType()!= CTokenType.LEFT_BRACKET)
			{
				Transcoder.logError(getLine(), "Expecting LEFT_BRACKET") ;
				return false ;
			}
			tok = GetNext();
			boolean bDone = false ;
			while (!bDone)
			{
				tok = GetCurrentToken();
				if (tok.GetKeyword() == CBMSKeywordList.COLOR ||
					tok.GetConstant() == CBMSConstantList.PS || 
					tok.GetKeyword() == CBMSKeywordList.HILIGHT ||
					tok.GetConstant() == CBMSConstantList.VALIDN)
				{
					m_arrMAPATTS.addElement(tok.GetValue()) ;
				}
				else if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					bDone = true ;
				}
				else if (tok.GetType() == CTokenType.COMMA)
				{
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting for MAPATTS : " + tok.GetValue()) ;
					return false ; 
				}
				StepNext() ;
			}
		}
		else if (kw == CBMSKeywordList.DSATTS)
		{ // DSATTS=(COLOR,PS,HILIGHT,VALIDN)
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetType()!= CTokenType.LEFT_BRACKET)
			{
				Transcoder.logError(getLine(), "Expecting LEFT_BRACKET") ;
				return false ;
			}
			tok = GetNext();
			boolean bDone = false ;
			while (!bDone)
			{
				tok = GetCurrentToken();
				if (tok.GetKeyword() == CBMSKeywordList.COLOR ||
					tok.GetConstant() == CBMSConstantList.PS || 
					tok.GetKeyword() == CBMSKeywordList.HILIGHT ||
					tok.GetConstant() == CBMSConstantList.VALIDN)
				{
					m_arrDSATTS.addElement(tok.GetValue()) ;
				}
				else if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					bDone = true ;
				}
				else if (tok.GetType() == CTokenType.COMMA)
				{
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting for DSATTS : " + tok.GetValue()) ;
					return false ; 
				}
				StepNext() ;
			}
		}
		else if (kw == CBMSKeywordList.JUSTIFY)
		{ // JUSTIFY=(LEFT)
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetType()!= CTokenType.LEFT_BRACKET)
			{
				Transcoder.logError(getLine(), "Expecting LEFT_BRACKET") ;
				return false ;
			}
			tok = GetNext();
			boolean bDone = false ;
			while (!bDone)
			{
				tok = GetCurrentToken();
				if (tok.GetConstant() == CBMSConstantList.LEFT ||
					tok.GetConstant() == CBMSConstantList.FIRST || 
//					tok.GetConstant() == CBMSConstantList. ||
					tok.GetConstant() == CBMSConstantList.BLANK)
				{
					m_arrJUSTIFY.addElement(tok.GetValue()) ;
				}
				else if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					bDone = true ;
				}
				else if (tok.GetType() == CTokenType.COMMA)
				{
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting for JUSTIFY : " + tok.GetValue()) ;
					return false ; 
				}
				StepNext() ;
			}
		}
//		else if (kw == CBMSKeywordList.)
//		{ // 
//			CBaseToken tok = GetCurrentToken() ;
//			if (tok.GetType()!= CTokenType.LEFT_BRACKET)
//			{
//				return false ;
//			}
//			tok = GetNext();
//			boolean bDone = false ;
//			while (!bDone)
//			{
//				tok = GetCurrentToken();
//				if (tok.GetConstant() == CBMSConstantList. ||
//					tok.GetConstant() == CBMSConstantList. || 
//					tok.GetConstant() == CBMSConstantList. ||
//					tok.GetConstant() == CBMSConstantList.)
//				{
//					m_arr.add(tok.addElement(tok.GetValue()) ;
//				}
//				else if (tok.GetType() == CTokenType.RIGHT_BRACKET)
//				{
//					bDone = true ;
//				}
//				else if (tok.GetType() == CTokenType.COMMA)
//				{
//				}
//				else
//				{
//					return false ; 
//				}
//				GetNext() ;
//			}
//		}
//		else if (kw == CBMSKeywordList.)
//		{ // 
//			CBaseToken tok = GetCurrentToken() ;
//			if (tok.GetConstant() == CBMSConstantList.)
//			{
//				m_ = tok.GetConstant();
//			}
//			else
//			{
//				return false ; 
//			}
//			GetNext() ;
//		}
		else if (kw == CBMSKeywordList.TRAILER)
		{ // 
			CBaseToken tok = GetCurrentToken() ;
			m_Trailer = tok.GetValue();
			GetNext() ;
		}
		else
		{
			Transcoder.logError(getLine(), "Unexpecting keyword : " + kw.m_Name) ;
			return false ;
		}
		return true ;
	}

	public CBaseLanguageEntity DoSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityResourceFormContainer container = (CEntityResourceFormContainer)parent ;
		String csLang = "";
		csLang += getName().charAt(6) ;
		boolean bStore = false ;
		if (csLang.equals("F"))
		{
			bStore = true ;
		}
		else if (!csLang.equals("D") && !csLang.equals("I") && !csLang.equals("G") && !csLang.equals("N"))
		{
			//Transcoder.warn("WARNING : unexpected lang ID : " + csLang) ;
			bStore = true ;
		}
		else
		{
			bStore = false ;
		}
		
		boolean bFirstForm = false ;
		if (m_resStrings == null)
		{
			m_resStrings = factory.NewResourceString(m_Size_Line, m_Size_Col);
			bFirstForm = true ;
		}
		CEntityResourceForm ef= null ; 
		if (bStore)
		{
//			if (csLang.equals("F"))
//			{
//				ef = factory.NewEntityForm(getLine(), container.GetName(), false) ;
//			}
//			else
//			{
				ef = factory.NewEntityForm(getLine(), getName(), false) ;
//			}
		 	factory.m_ProgramCatalog.RegisterMap(ef) ;
			ef.SetSize(m_Size_Col, m_Size_Line) ;
			ef.m_Of = container ;
		}
		
		if (m_bFindArrays)
		{
			ManageArray() ;
		}
		
		ListIterator i = m_children.listIterator() ;
		try
		{
			Object o = i.next() ;
			CBMSElement le = (CBMSElement)o ;
			while (le != null)
			{
				if (le.GetType()==EBMSElementType.FIELD)
				{
					le.SetResourceStrings(m_resStrings) ;
					CEntityResourceField field = (CEntityResourceField)le.DoSemanticAnalysis(ef, factory) ;
					if (field.m_nLength>0)
					{
						field.m_Of = container ;
						if (field.m_nPosCol + field.m_nLength > m_Size_Col+1)
						{
							Transcoder.logWarn(0, "Form : "+m_Name+" / Field : "+
									field.GetName()+"("+field.m_nPosLine+","+field.m_nPosCol+") is too long : "								
									+field.m_nLength) ;
						}
						if (!bFirstForm)
						{
							if (!m_resStrings.isExistingField(field.m_nPosLine, field.m_nPosCol, field.m_nLength))
							{
								Transcoder.logWarn(0, "Form : "+m_Name+" / Field : "+
										field.GetName()+"("+field.m_nPosCol+","+field.m_nPosLine+") does not match fields in other form") ;
							}
						}
						if (bStore)
						{
							ef.AddField(field) ;
							factory.m_ProgramCatalog.RegisterSymbolicField(field) ;
							String resId = "" ;
							if (!field.m_csInitialValue.equals(""))
							{
								String name = field.GetName() ;
								if (name.equals(""))
								{
									name = m_resStrings.CreateName(getName()) ;
									field.SetName(name) ;
								}
								resId = "STR-" + name.toUpperCase() ;
							}
							m_resStrings.SetResourceText(field.m_nPosLine, field.m_nPosCol, field.m_csInitialValue, getName(), resId, field.m_nLength) ;
							field.m_csInitialValue = resId ;
						}
						else // if (!field.m_csInitialValue.equals(""))
						{
							m_resStrings.SetResourceText(field.m_nPosLine, field.m_nPosCol, field.m_csInitialValue, getName(), field.m_nLength) ; // MAP name is used as langid
						}
					}
				}
				else if (le.GetType() == EBMSElementType.GROUP)
				{
					CEntityResourceField field = (CEntityResourceField)le.DoSemanticAnalysis(ef, factory) ;
					field.SetOf(container) ;
					if (bStore)
					{
						ef.AddField(field) ;
						factory.m_ProgramCatalog.RegisterSymbolicField(field) ;
					}
				}
				else if (le.GetType() == EBMSElementType.ARRAY)
				{
					CEntityResourceField field = (CEntityResourceField)le.DoSemanticAnalysis(ef, factory) ;
					field.SetOf(container) ;
					if (bStore)
					{
						ef.AddField(field) ;
						factory.m_ProgramCatalog.RegisterSymbolicField(field) ;
					}
				}
				le = (CBMSElement)i.next() ;
			}
		}
		catch (NoSuchElementException e)
		{
			//System.out.println(e.toString());
		}
		return ef ;
	}	

	/**
	 * 
	 */
	private class FieldComparator implements Comparator<CFieldElement> 
	{
		public int compare(CFieldElement e1, CFieldElement e2)
		{
			int line1 = e1.m_PosLine ;
			int line2 = e2.m_PosLine ;
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
				int col1 = e1.m_PosCol ;
				int col2 = e2.m_PosCol ;
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
	private void ManageArray()
	{
		LinkedList<CBaseElement> newList = new LinkedList<CBaseElement>() ;
		FieldComparator comp = new FieldComparator() ;
		SortedSet<CFieldElement> setFields =  new TreeSet<CFieldElement>(comp) ;
		ListIterator<CBaseElement> iter = m_children.listIterator() ;
		try
		{
			CBaseElement o = iter.next() ;
			CFieldElement le = (CFieldElement)o ;
			while (le != null)
			{
				setFields.add(le) ;
				le = (CFieldElement)iter.next() ;
			}
		}
		catch (NoSuchElementException e)
		{
			//System.out.println(e.toString());
		}
		
		CBMSElement[] arrFields = new CBMSElement[setFields.size()] ;
		setFields.toArray(arrFields) ;
		CFieldArray array = null ;
		for (int i=0; i<arrFields.length; i++)
		{
			CBMSElement le = arrFields[i] ;
			if (array != null)
			{
				if (le.GetType()==EBMSElementType.FIELD)
				{
					CFieldElement field = (CFieldElement)le;
					String s = field.getName() ;
					if (array.ReadField(field))
					{
						//
					}
					else
					{
						array = null ;
						newList.add(le) ;
					}
				}
				else
				{
					newList.add(le) ;
				}
			}
			else
			{
				CFieldElement field = (CFieldElement)le;
				String name = field.getName() ;
				if (name.indexOf('(')>0 && name.indexOf(')')>0)
				{
					String item = name.substring(0, name.indexOf('(')) ;
					String index = name.substring(name.indexOf('(')+1, name.length()-1) ;
					int n = Integer.parseInt(index) ;
					if (n == 1)
					{
						array = new CFieldArray() ;
						newList.add(array) ;
						array.ReadField(field) ;
					}
					else
					{
						throw new NacaTransAssertException("ASSERT ManageArray") ;
					}
				}
				else
				{
					newList.add(le) ;
				}
			}
		}
		m_children = newList ;
	}

	protected int m_Size_Col = 0 ;
	protected int m_Size_Line = 0 ;
	protected CReservedConstant m_Line = null ;
	protected CReservedConstant m_Column = null ;
	protected CReservedConstant m_Data = null ;
	protected CReservedConstant m_OBFMT = null ;
	protected CReservedConstant m_TIOAPFX = null ;
	protected StringVector m_arrCTRL = new StringVector() ;
	protected StringVector m_arrMAPATTS = new StringVector() ;
	protected StringVector m_arrDSATTS = new StringVector() ;
	protected StringVector m_arrJUSTIFY = new StringVector() ;
	protected String m_Trailer = "" ;
	/* (non-Javadoc)
	 * @see parser.CBMSElement#GetType()
	 */
	public EBMSElementType GetType()
	{
		return EBMSElementType.MAP ;
	}

	protected CResourceStrings m_resStrings = null ;
	public CResourceStrings GetResourceStrings()
	{
		return m_resStrings;
	}
	public void SetResourceStrings(CResourceStrings res)
	{
		m_resStrings = res ;
		
	}

	public void setFindArrays()
	{
		m_bFindArrays = true ;
	}
	protected boolean m_bFindArrays = false ;
	
	public CBMSElement loadTagParameters(Tag tagCurrent)
	{
		int nLine = tagCurrent.getValAsInt("Line");
		setLine(nLine);
		//m_Line = new CReservedConstant(null, "" + nLine);
		setName(tagCurrent.getVal("Name"));
		
		return loadInternalTags(tagCurrent);
	}

	public CBMSElement parseXMLResource(Tag tag)
	{
		String csName = tag.getName();
		CBMSElement elem = null;
		if(csName.equalsIgnoreCase("Attributes"))
		{
			m_Size_Col = tag.getValAsInt("SizeCol");
			m_Size_Line = tag.getValAsInt("SizeLine");
			
			m_Line = new CReservedConstant(null, tag.getVal("Line"));
			
			m_Column = new CReservedConstant(null, tag.getVal("Column"));
			
			m_Data = new CReservedConstant(null, tag.getVal("Data"));
			
			m_TIOAPFX = new CReservedConstant(null, tag.getVal("TIOAPFX"));
			
			m_OBFMT = new CReservedConstant(null, tag.getVal("OBFMT"));
			
			// Enum all sub tags		
			TagCursor curChild = new TagCursor();
			Tag tagChild = tag.getFirstChild(curChild);
			while(tagChild != null)
			{
				String csChildName = tagChild.getName();
				if(csChildName.equalsIgnoreCase("CTRL"))
				{
					String csVal = tagChild.getVal("Value");
					m_arrCTRL.addElement(csVal);
				}
				else if(csChildName.equalsIgnoreCase("MAPATTS"))
				{
					String csVal = tagChild.getVal("Value");
					m_arrMAPATTS.addElement(csVal);
				}
				else if(csChildName.equalsIgnoreCase("JUSTIFY"))
				{
					String csVal = tagChild.getVal("Value");
					m_arrJUSTIFY.addElement(csVal);
				}
				else if(csChildName.equalsIgnoreCase("DSATTS"))
				{
					String csVal = tagChild.getVal("Value");
					m_arrDSATTS.addElement(csVal);
				}
				tagChild = tag.getNextChild(curChild);
			}
		}
		else if(csName.equalsIgnoreCase("Field"))
		{
			elem = new CFieldElement("", 0);
			elem.loadTagParameters(tag);
		}
		return elem;
	}
	
	private CBMSElement loadInternalTags(Tag tagCurrent)
	{
		TagCursor curChild = new TagCursor();
		Tag tagChild = tagCurrent.getFirstChild(curChild);
		while(tagChild != null)
		{
			CBMSElement elem = parseXMLResource(tagChild);
			if(elem != null)
				AddElement(elem);
			tagChild = tagCurrent.getNextChild(curChild);
		}
		return this;
	}
	
	public void loadFromRES(int nLine, String csName, String csLanguage)
	{
		setLine(nLine);
		if(csLanguage.equalsIgnoreCase("DE"))
			setName(csName + "D");
		else if(csLanguage.equalsIgnoreCase("FR"))
			setName(csName + "F");
		else if(csLanguage.equalsIgnoreCase("IT"))
			setName(csName + "I");
		else if(csLanguage.equalsIgnoreCase("EN"))
			setName(csName + "G");
		else
			setName(csName);
		
		m_Size_Col = 100;
		m_Size_Line = 30;		
		m_Line = new CReservedConstant(null, "NEXT");		
		m_Column = new CReservedConstant(null, "SAME");		
		m_Data = new CReservedConstant(null, "FIELD");		
		m_TIOAPFX = new CReservedConstant(null, "YES");		
		m_OBFMT = new CReservedConstant(null, "NO");
		
		m_arrCTRL.addElement("HONEOM");
		m_arrCTRL.addElement("FREEKB");
		m_arrMAPATTS.addElement("COLOR");
		m_arrMAPATTS.addElement("PS");
		m_arrMAPATTS.addElement("HILIGHT");
		m_arrMAPATTS.addElement("VALIDN");
		m_arrJUSTIFY.addElement("LEFT");
		m_arrDSATTS.addElement("COLOR");
		m_arrDSATTS.addElement("PS");
		m_arrDSATTS.addElement("HILIGHT");
		m_arrDSATTS.addElement("VALIDN");
	}

}
