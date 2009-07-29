/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package parser.map_elements;

import jlib.xml.Tag;
import jlib.xml.TagCursor;
import lexer.*;
import lexer.BMS.CBMSConstantList;
import lexer.BMS.CBMSKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.utils.StringVector;

import parser.BMS.CBMSElement;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.forms.CEntityResourceField;
import semantic.forms.CResourceStrings;
import utils.CEntityHierarchy;
import utils.PosLineCol;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CFieldElement extends CBMSElement
{

	/**
	 * @param name
	 * @param line
	 */
	public CFieldElement(String name, int line)
	{
		super(name, line);
	}

	/* (non-Javadoc)
	 * @see parser.CBMSElement#DoExportCustom(org.w3c.dom.Document)
	 */
	protected Element DoExportCustom(Document root)
	{
		Element eF = root.createElement("Field") ;
		eF.setAttribute("PosLine", String.valueOf(m_PosLine));
		eF.setAttribute("PosCol", String.valueOf(m_PosCol));
		eF.setAttribute("Length", String.valueOf(m_Length));
		if (m_Color != null)
		{
			eF.setAttribute("Color", m_Color.m_Name);
		}
		if (m_HighLight != null)
		{
			eF.setAttribute("HighLight", m_HighLight.m_Name);
		}
		eF.setAttribute("Value", m_Value);
		for (int i=0; i<m_arrATTRB.size(); i++)
		{
			String val = m_arrATTRB.elementAt(i) ;
			Element e = root.createElement("ATTRB") ;
			e.setAttribute("Value", val);
			eF.appendChild(e) ; 
		}
		for (int i=0; i<m_arrJustify.size(); i++)
		{
			String val = m_arrJustify.elementAt(i) ;
			Element e = root.createElement("JUSTIFY") ;
			e.setAttribute("Value", val);
			eF.appendChild(e) ; 
		}
		return eF ;
	}

	/* (non-Javadoc)
	 * @see parser.CBMSElement#InterpretKeyword(lexer.CReservedKeyword, lexer.CTokenList)
	 */
	protected boolean InterpretKeyword(CReservedKeyword kw, CTokenList lstTokens)
	{
		if (kw == CBMSKeywordList.POS)
		{ //POS=(001,001)
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
			m_PosLine = tok.GetIntValue() ;
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
			m_PosCol = tok.GetIntValue() ;
			tok = GetNext() ;
			if (tok.GetType() != CTokenType.RIGHT_BRACKET)
			{
				Transcoder.logError(getLine(), "Expecting RIGHT_BRACKET") ;
				return false ;
			}
			StepNext() ;
		}
		else if (kw == CBMSKeywordList.LENGTH)
		{ // LENGTH=006
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.NUMBER)
			{
				m_Length = tok.GetIntValue();
			}
			else
			{
				Transcoder.logError(getLine(), "Expecting NUMBER") ;
				return false ; 
			}
			StepNext() ;
		}
		else if (kw == CBMSKeywordList.COLOR)
		{ // COLOR=TURQUOISE
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetConstant() == CBMSConstantList.TURQUOISE ||
				tok.GetConstant() == CBMSConstantList.GREEN ||
				tok.GetConstant() == CBMSConstantList.YELLOW|| 
				tok.GetConstant() == CBMSConstantList.RED || 
				tok.GetConstant() == CBMSConstantList.BLUE || 
				tok.GetConstant() == CBMSConstantList.PINK || 
				tok.GetConstant() == CBMSConstantList.NEUTRAL || 
				tok.GetConstant() == CBMSConstantList.DEFAULT)
			{
				m_Color = tok.GetConstant();
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting COLOR : " + tok.GetValue()) ;
				return false ; 
			}
			StepNext() ;
		}
		else if (kw == CBMSKeywordList.HILIGHT)
		{ // HILIGHT=OFF
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetConstant() == CBMSConstantList.OFF ||
				tok.GetConstant() == CBMSConstantList.REVERSE ||
				tok.GetConstant() == CBMSConstantList.UNDERLINE)
			{
				m_HighLight = tok.GetConstant();
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting HIGHLIGHT : " + tok.GetValue()) ;
				return false ; 
			}
			StepNext() ;
		}
		else if (kw == CBMSKeywordList.ATTRB)
		{ // ATTRB=(ASKIP,NORM)
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
				if (tok.GetConstant() == CBMSConstantList.ASKIP ||
					tok.GetConstant() == CBMSConstantList.DRK ||
					tok.GetConstant() == CBMSConstantList.PROT ||
					tok.GetConstant() == CBMSConstantList.UNPROT ||
					tok.GetConstant() == CBMSConstantList.BRT ||
					tok.GetConstant() == CBMSConstantList.NUM ||
					tok.GetConstant() == CBMSConstantList.IC ||
					tok.GetConstant() == CBMSConstantList.FSET ||
					tok.GetConstant() == CBMSConstantList.NORM)
				{
					m_arrATTRB.addElement(tok.GetValue()) ;
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
					Transcoder.logError(getLine(), "Unexpecting ATTRIBUTE : " + tok.GetValue()) ;
					return false ; 
				}
				StepNext() ;
			}
		}
		else if (kw == CBMSKeywordList.JUSTIFY)
		{ // JUSTIFY=(LEFT,BLANK)
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
					tok.GetConstant() == CBMSConstantList.RIGHT ||	
					tok.GetConstant() == CBMSConstantList.ZERO ||	
					tok.GetConstant() == CBMSConstantList.BLANK)
				{
					m_arrJustify.addElement(tok.GetValue()) ;
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
					Transcoder.logError(getLine(), "Unexpecting JUSTIFY : " + tok.GetValue()) ;
					return false ; 
				}
				StepNext() ;
			}
		}
		else if (kw == CBMSKeywordList.INITIAL)
		{ // 
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.STRING)
			{
				m_Value = tok.GetValue();
			}
			else
			{
				Transcoder.logError(getLine(), "Expecting STRING") ;
				return false ; 
			}
			StepNext() ;
		}
		else if (kw == CBMSKeywordList.GRPNAME)
		{ // 
			CBaseToken tok = GetCurrentToken() ;
			m_GrpName = tok.GetValue() ;
			StepNext() ;
		}
		else if (kw == CBMSKeywordList.PICIN)
		{ // 
			CBaseToken tok = GetCurrentToken() ;
			m_PicIn = tok.GetValue() ;
			StepNext() ;
		}
		else if (kw == CBMSKeywordList.PICOUT)
		{ // 
			CBaseToken tok = GetCurrentToken() ;
			m_PicOut = tok.GetValue() ;
			StepNext() ;
		}
		else
		{
			Transcoder.logError(getLine(), "Unexpecting keyword : "+ kw.m_Name) ;
			return false ;
		}
		return true ;
	}

	protected int m_PosLine = 0 ;
	protected int m_PosCol = 0 ;
	protected int m_Length = 0 ;
	protected CReservedConstant m_Color = null ;
	protected CReservedConstant m_HighLight = null ;
	protected StringVector m_arrATTRB = new StringVector() ;
	protected StringVector m_arrJustify = new StringVector() ;
	protected String m_Value = "" ;
	protected String m_GrpName = "" ;
	protected String m_PicIn = "" ;
	protected String m_PicOut = "" ;
	protected String m_csDisplayName = "";

	/* (non-Javadoc)
	 * @see parser.CBMSElement#GetType()
	 */
	public EBMSElementType GetType()
	{
		return EBMSElementType.FIELD ;
	}

	/* (non-Javadoc)
	 * @see parser.CBMSElement#DoSemanticAnalysis(semantic.CBaseEntityFactory)
	 */
	public CBaseLanguageEntity DoSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityResourceField ef ;
		if (getName().equals(""))
		{
			ef = factory.NewEntityLabelField(getLine()) ;
			ef.m_csInitialValue = m_Value ;
		}
		else
		{
			ef = factory.NewEntityEntryField(getLine(), getName()) ;
			if (!m_Value.equals(""))
			{
				ef.m_ResourceStrings = m_ResourceStrings ;
				ef.m_csInitialValue = m_Value ;
			}
		}
		ef.SetDisplayName(m_csDisplayName);
		ef.m_nPosCol = m_PosCol ;
		ef.m_nPosLine = m_PosLine ;
		ef.m_nLength = m_Length ;
		if (m_HighLight != null)
		{
			ef.SetHighLight(m_HighLight.m_Name) ;
		}
		if (m_Color != null)
		{
			ef.SetColor(m_Color.m_Name) ;
		}
		for (int i=0; i<m_arrATTRB.size(); i++)
		{
			String cs = m_arrATTRB.elementAt(i) ;
			if (cs.equals("ASKIP"))
			{
				ef.SetProtection("AUTOSKIP") ;
			}
			else if (cs.equals("UNPROT"))
			{
				ef.SetProtection("UNPROTECTED");
			}
			else if (cs.equals("NUM"))
			{
				ef.SetProtection("NUMERIC");
			}
			else if (cs.equals("NORM"))
			{
				ef.SetBrightness("NORMAL");
			}
			else if (cs.equals("DRK"))
			{
				ef.SetBrightness("DARK");
			}
			else if (cs.equals("BRT"))
			{
				ef.SetBrightness("BRIGHT");
			}
			else if (cs.equals("FSET"))
			{
				ef.SetModified();
			}
			else if (cs.equals("IC"))
			{
				ef.SetCursor();
			}
		}
		for (int i=0; i<m_arrJustify.size(); i++)
		{
			String cs = m_arrJustify.elementAt(i) ;
			if (cs.equals("LEFT"))
			{
				ef.SetRightJustified(false);
			}
			else if (cs.equals("RIGHT"))
			{
				ef.SetRightJustified(true);
			}
			else if (cs.equals("BLANK"))
			{
				ef.SetFillValue("BLANK");
			}
			else if (cs.equals("ZERO") || cs.equals("ZEROS") || cs.equals("ZEROES"))
			{
				ef.SetFillValue("ZERO");
			}
		}
		return ef;
	}
	public CResourceStrings GetResourceStrings()
	{
		return m_ResourceStrings ;
	}
	public void SetResourceStrings(CResourceStrings res)
	{
		m_ResourceStrings = res ;
	}
	protected CResourceStrings m_ResourceStrings = null ;

	public void SetName(String csAlias)
	{
		setName(csAlias);		
	}

	public String GetGroupName()
	{
		return m_GrpName ;
	}

	public CBMSElement loadTagParameters(Tag tagCurrent)
	{
		int nLine = tagCurrent.getValAsInt("Line");
		setLine(nLine);
		
		setName(tagCurrent.getVal("Name"));

		String csColor = tagCurrent.getVal("Color");
		m_Color = new CReservedConstant(null, csColor); 

		String csHighLight = tagCurrent.getVal("HighLight");
		m_HighLight = new CReservedConstant(null, csHighLight); 

		m_Length = tagCurrent.getValAsInt("Length");
		
		m_PosCol = tagCurrent.getValAsInt("PosCol");
		
		m_PosLine = tagCurrent.getValAsInt("PosLine");
		
		m_Value = tagCurrent.getVal("Value");
		
		// Enum all sub tags		
		TagCursor curChild = new TagCursor();
		Tag tagChild = tagCurrent.getFirstChild(curChild);
		while(tagChild != null)
		{
			String csChildName = tagChild.getName();
			if(csChildName.equalsIgnoreCase("ATTRB"))
			{
				String csVal = tagChild.getVal("Value");
				m_arrATTRB.addElement(csVal);
			}
			else if(csChildName.equalsIgnoreCase("JUSTIFY"))
			{
				String csVal = tagChild.getVal("Value");
				m_arrJustify.addElement(csVal);
			}
			tagChild = tagCurrent.getNextChild(curChild);
		}
		
		return this;
	}

	public CBMSElement parseXMLResource(Tag tag)
	{
		String csName = tag.getName();
		CBMSElement elem = null;
		if(csName.equalsIgnoreCase("Map"))
		{
			elem = new CMapElement("", 0);
			elem.loadTagParameters(tag);
		}
		return elem;
	}
	
	public boolean setAsClosingHBox(PosLineCol posLineCol)
	{
		m_Color = new CReservedConstant(null, "GREEN");
		m_HighLight = new CReservedConstant(null, "OFF");
		setName("");
		setLine(0);
		m_Length = 0;
		m_PosLine = posLineCol.getLine();		
		m_PosCol = posLineCol.getCol() + posLineCol.getLength() + 1;
		posLineCol.setLineColLength(m_PosLine+1, 0, 0);
		if(m_PosCol > 80)
			return false;
		
		
		m_Value = "";
		
		m_arrATTRB.addElement("ASKIP");
		m_arrATTRB.addElement("NORM");
		return true;
	}
	
	private static int ms_nNbBlank = 0;
	
	private boolean fillFromBlank(PosLineCol posLineCol, Tag tag, String csCurrentLanguage)
	{
		m_Color = new CReservedConstant(null, "GREEN");
		m_HighLight = new CReservedConstant(null, "OFF");
		setName("");
		setLine(ms_nNbBlank++);
		m_Length = 0;	//tag.getValAsInt("length");
		m_PosLine = posLineCol.getLine();		
		m_PosCol = posLineCol.getCol() + posLineCol.getLength() + 1;
		
		m_Value = "";
		
		m_arrATTRB.addElement("ASKIP");
		m_arrATTRB.addElement("NORM");
		
		return true;
	}
	
	public boolean loadTagParameters(PosLineCol posLineCol, Tag tag, String csCurrentLanguage)
	{
		String csTagName = tag.getName();
		if(csTagName.equalsIgnoreCase("edit"))
		{
			return fillFromEdit(posLineCol, tag, csCurrentLanguage, "");
		}
		else if(csTagName.equalsIgnoreCase("label"))
		{
			boolean b = fillFromEdit(posLineCol, tag, csCurrentLanguage, "");// "_LABEL");
			m_arrATTRB.addElement("ASKIP");
			m_arrATTRB.addElement("NORM");
			setName("");
			return b;
		}
		else if(csTagName.equalsIgnoreCase("title"))
		{
			boolean b = fillFromEdit(posLineCol, tag, csCurrentLanguage, "");
			while(m_Value.length() < m_Length)
				m_Value = m_Value + " ";							
			m_arrATTRB.addElement("ASKIP");
			m_arrATTRB.addElement("NORM");
			return b;
		}
		else if(csTagName.equalsIgnoreCase("blank"))
		{
			return fillFromBlank(posLineCol, tag, csCurrentLanguage);
		}
		else if(csTagName.equalsIgnoreCase("switch") && !tag.isValExisting("additem"))
		{
			boolean b = fillFromSwitch(posLineCol, tag, csCurrentLanguage, "");			
			return b;
		}
		return false;
	}
		
		
	private boolean fillFromEdit(PosLineCol posLineCol, Tag tag, String csCurrentLanguage, String csAppendColor)
	{
		String csColor = tag.getVal("color");
		m_Color = new CReservedConstant(null, csColor.toUpperCase() + csAppendColor); 
		
		String csHighLight = tag.getVal("highlighting");
		m_HighLight = new CReservedConstant(null, csHighLight.toUpperCase());
		
		if (tag.isValExisting("namecopy")) {
			setName(tag.getVal("namecopy").toUpperCase().replace('_', '-'));
			m_csDisplayName = tag.getVal("name").toUpperCase();
		} else if (tag.isValExisting("name"))
			setName(tag.getVal("name").toUpperCase());
		else
			setName("");
		
		//int nSourceLine = tag.getValAsInt("sourceline");
		//setLine(nSourceLine);
		
		m_Length = tag.getValAsInt("length");
		
		m_PosLine = tag.getValAsInt("line");
		
		m_PosCol = tag.getValAsInt("col");
		
		posLineCol.setLineColLength(m_PosLine, m_PosCol, m_Length);

		m_Value = "";
		
		Tag tagTexts = tag.getChild("texts");
		if(tagTexts != null)
		{
			TagCursor curText = new TagCursor();
			Tag tagText = tagTexts.getFirstChild(curText, "text");
			while(tagText != null)
			{
				String csLanguage = tagText.getVal("lang");
				if(csLanguage.equalsIgnoreCase(csCurrentLanguage))
				{
					m_Value = tagText.getText();
					break;
				}
				tagText = tagTexts.getNextChild(curText);
			}
		}
			
		String csJustify = tag.getVal("justify");
		if(csJustify.equalsIgnoreCase("right"))
			m_arrJustify.addElement(CBMSConstantList.RIGHT.m_Name) ;
		else if(csJustify.equalsIgnoreCase("left"))
			m_arrJustify.addElement(CBMSConstantList.LEFT.m_Name) ;

		String csFill = tag.getVal("fill");
		if(csFill.equalsIgnoreCase("blank"))
			m_arrJustify.addElement(CBMSConstantList.BLANK.m_Name) ;
		else if(csFill.equalsIgnoreCase("zero"))
			m_arrJustify.addElement(CBMSConstantList.ZERO.m_Name) ;
		
		String csProtection = tag.getVal("protection");
		manageAttrib(csProtection);

		String csIntensity = tag.getVal("intensity");
		manageAttrib(csIntensity);

		boolean bModified = tag.getValAsBoolean("modified");
		if(bModified)
			m_arrATTRB.addElement("FSET");
		
		boolean bCursor = tag.getValAsBoolean("cursor");
		if(bCursor)
			m_arrATTRB.addElement("IC");
		return true;
	}
	
	private boolean fillFromSwitch(PosLineCol posLineCol, Tag tag, String csCurrentLanguage, String csAppendColor)
	{
		if (tag.isValExisting("name"))				
			setName(tag.getVal("name").toUpperCase());
		else
			setName("");
		
		m_Length = tag.getValAsInt("length");
		
		m_PosLine = tag.getValAsInt("line");
		
		m_PosCol = tag.getValAsInt("col");
		
		posLineCol.setLineColLength(m_PosLine, m_PosCol, m_Length);

		m_Value = "";
		
		Tag tagTexts = tag.getChild("texts");
		if(tagTexts != null)
		{
			TagCursor curText = new TagCursor();
			Tag tagText = tagTexts.getFirstChild(curText, "text");
			while(tagText != null)
			{
				String csLanguage = tagText.getVal("lang");
				if(csLanguage.equalsIgnoreCase(csCurrentLanguage))
				{
					m_Value = tagText.getText();
					break;
				}
				tagText = tagTexts.getNextChild(curText);
			}
		}
			
		m_arrJustify.addElement(CBMSConstantList.LEFT.m_Name) ;
		
		m_arrJustify.addElement(CBMSConstantList.BLANK.m_Name) ;
		
		m_arrATTRB.addElement("ASKIP");
		m_arrATTRB.addElement("NORM");
		
		return true;
	}
	
	
	private void manageAttrib(String cs)
	{
		if(cs.equalsIgnoreCase("autoskip"))
			m_arrATTRB.addElement("ASKIP");
		else if(cs.equalsIgnoreCase("UNPROTECTED"))
			m_arrATTRB.addElement("UNPROT");
		else if(cs.equalsIgnoreCase("NUMERIC"))
		{
			m_arrATTRB.addElement("UNPROT");	// correct ?
			m_arrATTRB.addElement("NUM");
		}
		else if(cs.equalsIgnoreCase("NORMAL"))
			m_arrATTRB.addElement("NORM");
		else if(cs.equalsIgnoreCase("DARK"))
			m_arrATTRB.addElement("DRK");
		else if(cs.equalsIgnoreCase("BRIGHT"))
			m_arrATTRB.addElement("BRT");
	}
}
