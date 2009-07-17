/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Vector;

import lexer.*;
import lexer.Cobol.CCobolConstantList;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import parser.expression.CTerminal;
import semantic.CDataEntity;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.CEntityAttribute;
import semantic.CEntityIndex;
import semantic.CEntityStructure;
import semantic.CEntityValueReference;
import semantic.CIgnoredEntity;
import semantic.ITypableEntity;
import semantic.forms.CEntityFieldAttribute;
import semantic.forms.CEntityFieldColor;
import semantic.forms.CEntityFieldHighlight;
import semantic.forms.CEntityFieldLength;
import semantic.forms.CEntityFieldOccurs;
import semantic.forms.CEntityFieldFlag;
import semantic.forms.CEntityFieldRedefine;
import semantic.forms.CEntityFieldValidated;
import semantic.forms.CEntityFormRedefine;
import semantic.forms.CEntityResourceField;
import semantic.forms.CEntityResourceForm;
import semantic.forms.CEntitySkipFields;
import semantic.forms.CEntityResourceForm.CFieldRedefineDescription;
import utils.Transcoder;
import utils.CGlobalEntityCounter;
import utils.NacaTransAssertException;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CWorkingEntry extends CCobolElement
{
	/**
	 * @param line
	 */
	public CWorkingEntry(int line) {
		super(line);
	}

	public static class CWorkingPicType
	{
		public String m_Text = "" ;
		protected CWorkingPicType(String text)
		{
			m_Text = text ;
		}
		public static CWorkingPicType STRING = new CWorkingPicType("STRING") ;
		public static CWorkingPicType NUMBER = new CWorkingPicType("NUMBER") ;
		public static CWorkingPicType SIGNED = new CWorkingPicType("SIGNED NUMBER") ;
		public static CWorkingPicType SIGNED_DECIMAL = new CWorkingPicType("SIGNED DECIMAL") ;
		public static CWorkingPicType DECIMAL = new CWorkingPicType("DECIMAL") ;
		public static CWorkingPicType EDITED = new CWorkingPicType("ZONED NUMBER") ;
	}
	public static class CWorkingEntryType
	{
		protected CWorkingEntryType() {}
		public static CWorkingEntryType STRUCTURE = new CWorkingEntryType() ;
		public static CWorkingEntryType VARIABLE = new CWorkingEntryType() ;
	}
	public static class CWorkingSignType
	{
		protected CWorkingSignType() {}
		public static CWorkingSignType LEADING = new CWorkingSignType() ;
		public static CWorkingSignType TRAILING = new CWorkingSignType() ;
	}
	protected int m_EntryLevel = 0 ;
	protected String m_FormalLevel = "" ;
	protected CWorkingPicType m_Type = null ;
	protected String m_Name = "" ;
	protected CIdentifier m_Redefines = null ;
	protected int m_Length = 0 ;
	protected int m_Decimal = 0 ;
	protected CTerminal m_Value = null ;
	protected String m_Comp = "" ;
	protected boolean m_Sync = false ;
	protected CTerminal m_Occurs = null ;
	protected CIdentifier m_OccursDepending = null ;
	protected CWorkingEntryType m_EntryType = null ;
	protected String m_Format = ""  ;
	protected final List<CIdentifier> m_OccursIndexedBy = new ArrayList<CIdentifier>() ;
	protected CTerminal m_BlankWhenValue = null ;
	protected boolean m_bFillAll = false ;
	protected boolean m_bIsPointer = false ;
	protected boolean m_bIsIndex = false;
	protected boolean m_bJustifiedRight = false ;
	protected boolean m_bBlankWhenZero = false ;
	protected CWorkingSignType m_bSignSeparateType ;
	protected Vector<CIdentifier> m_arrTableSortKey = null ;
	protected boolean m_bTableSortedAscending = false ;
	protected boolean m_bBinary = true ;
	

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tokEntry = GetCurrentToken();
		if (tokEntry.GetType() == CTokenType.NUMBER)
		{
			m_EntryLevel = tokEntry.GetIntValue() ;
			if (m_EntryLevel == 77)
			{
				m_EntryType = CWorkingEntryType.VARIABLE ;
				CGlobalEntityCounter.GetInstance().CountCobolVerb("WORKING_VARIABLE") ;
			}
			else
			{
				m_EntryType = CWorkingEntryType.STRUCTURE ;
				CGlobalEntityCounter.GetInstance().CountCobolVerb("WORKING_ENTRY") ;
			} 
			m_FormalLevel = tokEntry.GetValue() ;
			CBaseToken tokName = GetNext(); // consume PIC LEVEL
			if (tokName.IsKeyword() && tokName.GetKeyword()==CCobolKeywordList.FILLER)
			{
				m_Name = "" ;
				GetNext() ; // consume FILLER
			}
			else if (tokName.GetType() == CTokenType.IDENTIFIER)
			{
				m_Name = tokName.GetValue() ;
				GetNext() ; // consume NAME
			}
			if (!ParsePicOptions())
			{
				return false ;
			}
			return ParseContent() ; // no PIC type, certainly a structure
		}
		else
		{
			Transcoder.logError(getLine(), "Unexpecting token : " + tokEntry.GetValue()) ;
			return false ;
		}
	}
	
	protected boolean ParsePicOptions()
	{
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tokPic = GetCurrentToken();
			if (tokPic == null)
			{
				return true;
			}
			if (tokPic.GetType() == CTokenType.DOT)
			{
				GetNext() ; // consume DOT
				return true ;
			}
			boolean bNext = false ;
			if (tokPic.GetKeyword()==CCobolKeywordList.REDEFINES)
			{	// in case of redefine...
				CBaseToken tokRedefine = GetNext() ; // consume REDEFINES, expecting an identifier
				if (tokRedefine.GetType() == CTokenType.IDENTIFIER)
				{
					m_Redefines = ReadIdentifier() ;
					bNext = true ;
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting sequence : " + tokPic.toString() + tokRedefine.toString()) ;
					return false ;
				}
			}
			else if (tokPic.GetKeyword()==CCobolKeywordList.PIC || tokPic.GetKeyword()==CCobolKeywordList.PICTURE)
			{
				boolean b = ParsePicItSelf() ;
				if (b)
				{
					bNext = true ;
				}
			}
			else if (tokPic.IsKeyword() && tokPic.GetKeyword()==CCobolKeywordList.COMP_4)
			{
				m_Comp = "COMP4" ;
				bNext = true ;
				GetNext();
			}
			else if (tokPic.IsKeyword() && tokPic.GetKeyword()==CCobolKeywordList.COMP_3)
			{
				m_Comp = "COMP3" ;
				bNext = true ;
				GetNext();
			}
			else if (tokPic.GetKeyword()==CCobolKeywordList.COMP || tokPic.GetKeyword()==CCobolKeywordList.COMP_5 || tokPic.GetKeyword()==CCobolKeywordList.COMPUTATIONAL)
			{
				m_Comp = "COMP" ;
				bNext = true ;
				GetNext();
			}
			else if (tokPic.IsKeyword() && tokPic.GetKeyword()==CCobolKeywordList.SYNC)
			{
				m_Sync = true ;
				bNext = true ;
				GetNext();
			}
			else if (tokPic.IsKeyword() && tokPic.GetKeyword()==CCobolKeywordList.SIGN)
			{
				CBaseToken tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.TRAILING)
				{
					tok = GetNext();
					if (tok.GetKeyword() == CCobolKeywordList.SEPARATE)
					{
						m_bSignSeparateType = CWorkingSignType.TRAILING ;
						bNext = true ;
						GetNext();
					}
					else
					{
						return false ;
					}
				}
				else if (tok.GetKeyword() == CCobolKeywordList.LEADING)
				{
					tok = GetNext();
					if (tok.GetKeyword() == CCobolKeywordList.SEPARATE)
					{
						m_bSignSeparateType = CWorkingSignType.LEADING ;
						bNext = true ;
						GetNext();
					}
					else
					{
						return false ;
					}
				}
				else
				{
					return false ;
				}
			}
			else if (tokPic.IsKeyword() && tokPic.GetKeyword()==CCobolKeywordList.BLANK)
			{
				CBaseToken tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.WHEN)
				{
					tok = GetNext();
				}
				if (tok.GetConstant() == CCobolConstantList.ZERO)
				{
					tok = GetNext();
					m_bBlankWhenZero = true ;
					bNext = true ;
				}
				else
				{
					return false ;
				}
			}
			else if (tokPic.GetKeyword()==CCobolKeywordList.JUST || tokPic.GetKeyword()==CCobolKeywordList.JUSTIFIED)
			{
				m_bJustifiedRight = true ;

				bNext = true ;
				CBaseToken tok = GetNext();
				if (tok.GetKeyword() == CCobolKeywordList.RIGHT)
				{
					GetNext();
				}
			}
			else if (tokPic.IsKeyword() && tokPic.GetKeyword()==CCobolKeywordList.USAGE)
			{
				bNext = true ;
				CBaseToken tok = GetNext();
				if (tok.GetKeyword() == CCobolKeywordList.IS)
				{
					tok = GetNext();
				}
				if (tok.GetKeyword() == CCobolKeywordList.POINTER)
				{
					m_bIsPointer = true ;
					tok = GetNext();
				}
				else if (tok.GetKeyword() == CCobolKeywordList.INDEX)
				{
					m_bIsIndex = true ;
					tok = GetNext();
				}
			}
			else if (tokPic.IsKeyword() && tokPic.GetKeyword()==CCobolKeywordList.BINARY)
			{
				GetNext() ;
				bNext = true ;
				m_bBinary = true ;
			}
			else if (tokPic.IsKeyword() && tokPic.GetKeyword()==CCobolKeywordList.VALUE)
			{
				CBaseToken tokValue = GetNext() ;
				if (tokValue.GetKeyword() == CCobolKeywordList.IS)
				{
					tokValue = GetNext() ;
				}
				if (tokValue.GetType()==CTokenType.STRING || tokValue.GetType()==CTokenType.NUMBER || 
					tokValue.GetType()==CTokenType.CONSTANT || tokValue.GetType()==CTokenType.MINUS)
				{
					m_Value = ReadTerminal() ; 
				}
				else if (tokValue.GetType()==CTokenType.PLUS)
				{
					CBaseToken tokNum = GetNext();
					if (tokNum.GetType() == CTokenType.NUMBER)
					{
						m_Value = ReadTerminal() ; 
					}
					else
					{
						Transcoder.logError(getLine(), "Expecting NUM value as value for PIC, instead of : " + tokNum.toString()) ;
					}
				}
				else if (tokValue.GetKeyword() == CCobolKeywordList.ALL)
				{
					GetNext();
					m_Value = ReadTerminal() ;
					m_bFillAll = true ;
				}
				bNext = true ;
			}
			else if (tokPic.GetKeyword() == CCobolKeywordList.BLANK)
			{
				bNext = true ;
				tokPic = GetNext();
				if (tokPic.GetKeyword() == CCobolKeywordList.WHEN)
				{
					tokPic = GetNext();
					m_BlankWhenValue = ReadTerminal() ;
				}
			}
			else if (tokPic.IsKeyword() && tokPic.GetKeyword()==CCobolKeywordList.OCCURS)
			{
				bNext = true ;
				CBaseToken tokOccurs = GetNext() ;
				if (tokOccurs.GetType() == CTokenType.NUMBER)
				{
					m_Occurs = ReadTerminal() ;
					CBaseToken tokTimes = GetCurrentToken() ;
					if (tokTimes.IsKeyword() && tokTimes.GetKeyword()==CCobolKeywordList.TIMES)
					{
						tokTimes = GetNext();
					}
					if(tokTimes.IsKeyword() && tokTimes.GetKeyword()==CCobolKeywordList.TO)
					{	// OCCURS DEPENDING ON statement
						CBaseToken tokTo = GetNext() ;
						if (tokTo.GetType() == CTokenType.NUMBER)
						{
							m_Occurs = ReadTerminal() ;
							CBaseToken tokDep = GetCurrentToken() ;
							if (tokDep.GetKeyword() == CCobolKeywordList.TIMES)
							{
								tokDep = GetNext() ;
							}
						}
						else 
						{
							return false ;
						}
					}
					boolean bDone2 = false ;
					while (!bDone2)
					{
						CBaseToken tokOpt = GetCurrentToken() ;
						if (tokOpt.GetType() == CTokenType.COMMA)
						{
							tokOpt = GetNext() ;
						}
						if (tokOpt.GetKeyword() == CCobolKeywordList.DEPENDING)
						{
							tokOpt = GetNext() ;
							if (tokOpt.GetKeyword() == CCobolKeywordList.ON)
							{
								tokOpt = GetNext();
							}
							if (tokOpt.GetType() != CTokenType.IDENTIFIER)
							{
								return false ;
							}
							m_OccursDepending = ReadIdentifier() ;
						}
						else if (tokOpt.GetKeyword() == CCobolKeywordList.INDEXED)
						{
							CBaseToken tokBy = GetNext();
							if (tokBy.GetKeyword() == CCobolKeywordList.BY)
							{
								tokBy = GetNext();
							}
							while (tokBy.GetType() == CTokenType.IDENTIFIER)
							{
								m_OccursIndexedBy.add(ReadIdentifier()) ;
								tokBy = GetCurrentToken();
							}
						}
						else if (tokOpt.GetKeyword() == CCobolKeywordList.ASCENDING || tokOpt.GetKeyword() == CCobolKeywordList.DESCENDING)
						{
							if (tokOpt.GetKeyword() == CCobolKeywordList.ASCENDING)
							{
								m_bTableSortedAscending = true ;
							}
							else
							{
								m_bTableSortedAscending = false ;
							}
							tokOpt = GetNext() ;
							if (tokOpt.GetKeyword() == CCobolKeywordList.KEY)
							{
								tokOpt = GetNext();
							}
							if (tokOpt.GetKeyword() == CCobolKeywordList.IS)
							{
								tokOpt = GetNext();
							}
							m_arrTableSortKey = new Vector<CIdentifier>() ;							
							CIdentifier tableSortKey ;
							tableSortKey = ReadIdentifier() ;
							while (tableSortKey != null)
							{
								m_arrTableSortKey.add(tableSortKey) ;
								tableSortKey = ReadIdentifier() ;
							}
						}
						else
						{
							bDone2 = true ;
						}
					}
				}
				else
				{
					Transcoder.logError(getLine(), "Expecting int value after occurs instead of : " + tokOccurs.toString());
					return false ;
				}
			}
			if (!bNext)
			{
				bDone = true ;
			}
		}
		return true ;
	}

	protected String ReadPicType()
	{
		String cs = "" ;
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tok = GetCurrentToken();
			if (tok.GetType() == CTokenType.NUMBER || tok.GetType() == CTokenType.IDENTIFIER)
			{
				cs += tok.GetValue() ;
			}
			else if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				cs += "(" ;
			}
			else if (tok.GetType() == CTokenType.RIGHT_BRACKET)
			{
				cs += ")" ;
			}
			else if (tok.GetType() == CTokenType.COMMA)
			{
				cs += "," ;
			}
			else if (tok.GetType() == CTokenType.DOLLAR)
			{
				cs += "$" ;
			}
			else if (tok.GetType() == CTokenType.PLUS)
			{
				cs += "+" ;
			}
			else if (tok.GetType() == CTokenType.MINUS)
			{
				cs += "-" ;
			}
			else if (tok.GetType() == CTokenType.DOT)
			{ // depending on following token
				CBaseToken tokNext = GetNext() ;
				if (tokNext == null || tokNext.m_bIsNewLine)
				{
					return cs ;
				}
				else
				{
					cs += "." ;
					continue ;
				}
			}
			else if (tok.GetType() == CTokenType.KEYWORD)
			{
				bDone = true ;
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting token in PIC type : " + tok.GetValue());
				return "" ; 
			}
			if (!bDone)
			{
				GetNext() ;
			}		
		}
		return cs ;
	}
	protected boolean m_bEdited = false ;
	protected boolean ParsePicItSelf()
	{
		CBaseToken tokType = GetNext() ; // consume PIC token, expecting S9 / 9 / X / XX ...
		String csPicType = ReadPicType() ;
		byte[] tab = csPicType.getBytes() ;
		int nCurrentChar = 0 ;
		char cRepeatPattern = 0 ;
		
		while (nCurrentChar<tab.length)
		{
			char c = (char)tab[nCurrentChar] ;
			if (m_Type == null)
			{
				if (c == 'X')
				{
					m_Type = CWorkingPicType.STRING ;
					m_Length = 1 ;
				}
				else if (c == 'S')
				{
					m_Type = CWorkingPicType.SIGNED;
				}
				else if (c == 'V')
				{
					m_Type = CWorkingPicType.DECIMAL ; 
					m_Length = 0 ;
				}
				else if (c == '9')
				{
					m_Type = CWorkingPicType.NUMBER;
					m_Length = 1 ;
					cRepeatPattern = '9' ;
				}
				else if (c == 'Z')
				{
					m_Type = CWorkingPicType.NUMBER ;
					m_Length = 1 ;
					m_bEdited = true ;
					cRepeatPattern = 'Z' ;
				}
				else if (c == 'B')
				{
					m_Type = CWorkingPicType.NUMBER ;
					m_Length = 1 ;
					m_bEdited = true ;
					cRepeatPattern = 'B' ;
				}
				else if (c == '+')
				{
					m_Type = CWorkingPicType.NUMBER ;
					m_Length = 0 ;
					m_bEdited = true ;
				}
				else if (c == '-')
				{
					m_Type = CWorkingPicType.NUMBER ;
					m_Length = 1 ;
					m_bEdited = true ;
					cRepeatPattern = '-' ;
				}
				else if (c == '$')
				{
					m_Type = CWorkingPicType.NUMBER ;
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting character in Pic Type : " + c);
					return false ;
				}
				m_Format += c ;
			}
			else
			{
				if (c == 'X' && m_Type == CWorkingPicType.STRING)
				{
					m_Length ++ ;
					m_Format += c ;
				}
				else if (c == '9' && (m_Type == CWorkingPicType.NUMBER || m_Type == CWorkingPicType.SIGNED || m_Type == CWorkingPicType.EDITED))
				{
					m_Length ++ ;
					m_Format += c ;
					cRepeatPattern = '9' ;
				}
				else if (c == 'B') // ????
				{
					m_Length ++ ;
					m_Format += c ;
					m_bEdited = true ;
					cRepeatPattern = 'B' ;
				}
				else if (c == 'Z' && m_Type == CWorkingPicType.EDITED)
				{
					m_Length ++ ;
					m_Format += c ;
					m_bEdited = true ;
					cRepeatPattern = 'Z' ;
				}
				else if (c == 'Z' && m_Type == CWorkingPicType.NUMBER)
				{
					m_Length ++ ;
					m_Format += c ;
					m_bEdited = true ;
					cRepeatPattern = 'Z' ;
				}
				else if (c == '-' && m_Type == CWorkingPicType.NUMBER)
				{
					m_Length ++ ;
					m_Format += c ;
					m_bEdited = true ;
				}
				else if (c == '+' && m_Type == CWorkingPicType.NUMBER)
				{
					m_Length ++ ;
					m_Format += c ;
					m_bEdited = true ;
				}
				else if (c == 'V' && m_Type == CWorkingPicType.NUMBER)
				{
					m_Type = CWorkingPicType.DECIMAL ; 
					m_Format += c ;
				}
				else if (c == 'V' && m_Type == CWorkingPicType.SIGNED)
				{
					m_Type = CWorkingPicType.SIGNED_DECIMAL ; 
					m_Format += c ;
				}
				else if (c == '9' && (m_Type == CWorkingPicType.DECIMAL || m_Type == CWorkingPicType.SIGNED_DECIMAL))
				{
					m_Decimal ++ ;
					m_Format += c ;
					cRepeatPattern = '9' ;
				}
				else if (c == '.' || c == ',' || c == '$')
				{
					m_bEdited = true ;
					m_Format += c ;
				}
				else if (c == '(')
				{
					nCurrentChar ++ ;
					c = (char)tab[nCurrentChar] ;
					String number = "" ;
					while (c >= '0' && c <= '9')
					{
						number += c ;
						nCurrentChar ++ ;
						c = (char)tab[nCurrentChar] ;
					}
					if (c != ')')
					{
						Transcoder.logError(getLine(), "Expecting ')' after number");
						return false ;
					}
					int n = Integer.parseInt(number) ;
					if (n == 0)
					{
						Transcoder.logError(getLine(), "Unparsed number '" + number +"'");
						return false ;
					}
					char completc = ' ' ;
					if (m_Type ==CWorkingPicType.STRING)
					{
						m_Length *= n ;
						completc = 'X' ;
					}
					else if (m_Type == CWorkingPicType.NUMBER  && m_bEdited)
					{
						m_Length *= n ;
						completc = cRepeatPattern ;
					}
					else if (m_Type == CWorkingPicType.NUMBER || m_Type == CWorkingPicType.SIGNED)
					{
						m_Length *= n ;
						completc = '9' ;
					}
					else if(m_Type == CWorkingPicType.DECIMAL || m_Type == CWorkingPicType.SIGNED_DECIMAL)
					{
						m_Decimal *= n;
						completc = '9' ;
					}
					else
					{
						Transcoder.logError(getLine(), "Unexpecting situation");
						return false ;
					}
					for (int i=1; i<n; i++)
					{
						m_Format += completc ;
					}
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting character : " + c) ;
					return false ;					
				}
			}
			nCurrentChar ++ ;
		}
		return true ;
	}			
	
	protected boolean ParsePicBrackets()
	{
		CBaseToken tokBra = GetCurrentToken();
		if (tokBra.GetType() == CTokenType.LEFT_BRACKET)
		{
			CBaseToken tokRep = GetNext() ; // consume '(', expecting an int
			int rep = tokRep.GetIntValue();
			if (rep > 0)
			{
				CBaseToken tokBraOff = GetNext();
				if (tokBraOff.GetType() == CTokenType.RIGHT_BRACKET)
				{
					m_Length = m_Length * rep ;
					GetNext() ;	// consume ')'
					return true ;
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting token after pic length : " + tokBraOff.toString()) ;
					return false ;
				}
			}
			else
			{
				Transcoder.logError(getLine(), "Invalid parameter for PIC length : " + tokRep.toString());
				return false ;
			}
			
		}
		return true ;
	}
	
	protected boolean ParseContent()
	{
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tokEntry = GetCurrentToken();
			if (tokEntry == null)
			{
				return true ;
			}
			if (tokEntry.GetType()==CTokenType.NUMBER)
			{
				int level = tokEntry.GetIntValue();
				if (level == 88)
				{
					CCobolElement eEntry = new CWorkingValueEntry(tokEntry.getLine());
					if (!Parse(eEntry))
					{
						return false ;
					}
					AddChild(eEntry) ;
				}
				else if (level > m_EntryLevel && level <= 49)
				{
					CCobolElement eEntry = new CWorkingEntry(tokEntry.getLine()) ;
					if (!Parse(eEntry))
					{
						return false ;
					}
					AddChild(eEntry) ;
				}
				else
				{
					bDone = true ; // this entry is a sub-entry of one of our parents
				}
			}
			else
			{
				bDone = true ;	// this token is not parsed by this function, go back to caller
			}
		}
		return true ;
	}

	
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eItem ;
		if (m_EntryType == CWorkingEntryType.STRUCTURE)
		{
			eItem = root.createElement("Item") ;
			eItem.setAttribute("Level", m_FormalLevel) ;
		}
		else if (m_EntryType == CWorkingEntryType.VARIABLE)
		{
			eItem = root.createElement("Variable") ; 
		}
		else
		{
			return null ;
		}
		if (m_Type != null)
		{
			eItem.setAttribute("Type", m_Type.m_Text) ;
		}
		eItem.setAttribute("Name", m_Name) ;
		if (m_Redefines != null)
		{
			Element eRedef = root.createElement("Redefines") ;
			eItem.appendChild(eRedef) ;
			m_Redefines.ExportTo(eRedef, root) ;
		}
		if (m_Length>1)
		{
			eItem.setAttribute("Length", String.valueOf(m_Length)) ;
		}
		if (m_Value != null)
		{
			m_Value.ExportTo(eItem, root) ;
		}
		if (!m_Comp.equals(""))
		{
			eItem.setAttribute("Comp", m_Comp);
		}
		if (m_Occurs != null)
		{
			Element eOccurs = root.createElement("Occurs") ;
			eItem.appendChild(eOccurs) ;
			m_Occurs.ExportTo(eOccurs, root) ;
			for (CIdentifier indexedBy : m_OccursIndexedBy)
			{
				Element eIndexed = root.createElement("IndexedBy") ;
				eOccurs.appendChild(eIndexed) ;
				indexedBy.ExportTo(eIndexed, root) ;
			}
		}
		if (m_Sync)
		{
			eItem.setAttribute("Sync", m_Sync?"yes":"no");
		}
		if (!m_Format.equals(""))
		{
			eItem.setAttribute("Format", m_Format);
		}
		return eItem;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityAttribute eAtt = null ;
		if (m_EntryType == CWorkingEntryType.STRUCTURE)
		{
			CEntityStructure eStruct = null ;
			if (m_Occurs != null)
			{
				eStruct = factory.NewEntityStructure(getLine(), m_Name, m_FormalLevel) ;
				eAtt = eStruct ;
				CDataEntity eSize = m_Occurs.GetDataEntity(getLine(), factory);
				if (m_OccursDepending != null)
				{
					CDataEntity eDep = m_OccursDepending.GetDataReference(getLine(), factory) ;
					CEntityValueReference eRef = factory.NewEntityValueReference(eDep) ;
					eStruct.SetTableSizeDepending(eSize, eRef) ;
					eDep.RegisterReadReference(eRef) ;
				}
				else
				{
					eStruct.SetTableSize(eSize) ;
				}
				
				for (CIdentifier indexedBy : m_OccursIndexedBy)
				{
					CEntityIndex index = factory.NewEntityIndex(indexedBy.GetName()) ;
					eStruct.setOccursIndex(index) ;
					parent.AddChild(index) ;
				}
			}
			if (m_Redefines != null)
			{
				CDataEntity e = m_Redefines.GetDataReference(getLine(), factory, parent) ;
				if (e != null)
				{
					if (e.GetDataType() == CDataEntity.CDataEntityType.VIRTUAL_FORM)
					{
						CIgnoredEntity ign = factory.NewIgnoreEntity(getLine(), m_Name) ;
						m_bAnalysisDoneForChildren = true ;// this is redefining a ignored structure => ignore children
						return ign ;
					}
					else if (e.GetDataType() == CDataEntity.CDataEntityType.FORM)
					{
						CEntityResourceForm form = (CEntityResourceForm)e ;
						//CEntityFormAccessor eAcc = (CEntityFormAccessor)e ; 
						CBaseLanguageEntity ebase = DoSemanticAnalysisForMapRedefine(form, factory);
						if (parent != null)
							parent.AddChild(ebase);
						return ebase ;
					}
					else
					{
						if (eStruct == null)
						{
							eStruct = factory.NewEntityStructure(getLine(), m_Name, m_FormalLevel) ;
						}
						eStruct.SetRedefine(e) ;
					}
				}
				else
				{
					Transcoder.logError(getLine(), "Identifier not found : " + m_Redefines.GetName());
				}
			}
			if (eStruct == null)
			{
				eStruct = factory.NewEntityStructure(getLine(), m_Name, m_FormalLevel) ;
			}
			eAtt = eStruct ;
		}
		else if (m_EntryType == CWorkingEntryType.VARIABLE)
		{
			eAtt = factory.NewEntityAttribute(getLine(), m_Name) ;
			factory.m_ProgramCatalog.RegisterAttribute(eAtt) ;
		}
		
		eAtt.SetSignSeparateType(m_bSignSeparateType) ;
		eAtt.SetJustifiedRight(m_bJustifiedRight) ;
		eAtt.SetBlankWhenZero(m_bBlankWhenZero) ;
		SetType(eAtt) ;
		eAtt.SetSync(m_Sync) ;
		if (parent!=null)
		{
			CBaseLanguageEntity par = parent.FindLastEntityAvailableForLevel(eAtt.GetInternalLevel()) ;
			if (par != null && par != parent)
			{
				par.AddChild(eAtt) ;
			}
			else
			{
				parent.AddChild(eAtt) ;
			}
		}
		if (m_Value != null)
		{
			if (m_Value.GetValue().equals(CCobolConstantList.SPACE.m_Name) || m_Value.GetValue().equals(CCobolConstantList.SPACES.m_Name))
			{
				eAtt.SetInitialValueSpaces() ;
			}
			else if (m_Value.GetValue().equals(CCobolConstantList.ZERO.m_Name) || m_Value.GetValue().equals(CCobolConstantList.ZEROS.m_Name)|| m_Value.GetValue().equals(CCobolConstantList.ZEROES.m_Name))
			{
				eAtt.SetInitialValueZeros() ;
			}
			else if (m_Value.GetValue().equals(CCobolConstantList.LOW_VALUE.m_Name) || m_Value.GetValue().equals(CCobolConstantList.LOW_VALUES.m_Name))
			{
				eAtt.SetInitialLowValue() ;
			}
			else if (m_Value.GetValue().equals(CCobolConstantList.HIGH_VALUE.m_Name) || m_Value.GetValue().equals(CCobolConstantList.HIGH_VALUES.m_Name))
			{
				eAtt.SetInitialHighValue() ;
			}
			else
			{
				CDataEntity eInitial = m_Value.GetDataEntity(getLine(), factory);
				if (eInitial != null)
				{
					if (m_bFillAll)
					{
						eAtt.SetInitialValueAll(eInitial) ;
					}
					else
					{
						eAtt.SetInitialValue(eInitial) ;
					}
					eInitial.RegisterReadReference(eAtt) ;
					factory.m_ProgramCatalog.RegisterInitializedStructure(eAtt) ;
				}
				else
				{
					Transcoder.logError(getLine(), "Missing semantic for initial value : "+m_Value.GetValue());
				}
			}
		}
		eAtt.SetComp(m_Comp) ;
		return eAtt;
		
	}
	
	/**
	 * @param att
	 */
	private void SetType(ITypableEntity eAtt)
	{
		if (m_bEdited)
		{
			eAtt.SetTypeEdited(m_Format) ;
		}
		else if (m_Type == CWorkingPicType.STRING)
		{
			eAtt.SetTypeString(m_Length) ;
		}
		else if (m_Type == CWorkingPicType.NUMBER || m_Type == CWorkingPicType.DECIMAL)
		{
			eAtt.SetTypeNum(m_Length, m_Decimal) ;
		}
		else if (m_Type == CWorkingPicType.SIGNED || m_Type == CWorkingPicType.SIGNED_DECIMAL)
		{
			eAtt.SetTypeSigned(m_Length, m_Decimal) ;
		}
		else if (m_Type != null)
		{
			Transcoder.logError(getLine(), "Not managed type");
		}
	}

	protected CBaseLanguageEntity DoSemanticAnalysisForMapRedefine(CEntityResourceForm eForm, CBaseEntityFactory factory)
	{
		boolean bSaveMap = eForm.IsSaveCopy() ;
		CEntityResourceForm.CFieldRedefineStructure lstFields  ;
		CEntityResourceForm map = factory.m_ProgramCatalog.GetAssociatedMap(eForm) ;
		if (bSaveMap)
		{
			lstFields = map.GetRedefineStructure() ;
		}
		else
		{
			lstFields = eForm.GetRedefineStructure() ;
		}
		CEntityValueReference ref = factory.NewEntityValueReference(eForm) ;
		eForm.RegisterReadReference(ref) ;
		CEntityFormRedefine eRedef = factory.NewEntityFormRedefine(getLine(), m_Name, ref, bSaveMap);
		eForm.StartFieldAnalyse();
		DoSemanticAnalysisForMapRedefineForChildren(eForm, factory, eRedef, bSaveMap, lstFields);
		if (bSaveMap)
		{
			factory.m_ProgramCatalog.RegisterSaveMap(eRedef, map) ;
		}
		else
		{
			factory.m_ProgramCatalog.RegisterMap(eRedef) ;
		}
		return eRedef ;
	}
	protected int DoSemanticAnalysisForMapRedefineForChildren(CEntityResourceForm eForm, CBaseEntityFactory factory, CBaseLanguageEntity eParent, boolean bSaveMap, CEntityResourceForm.CFieldRedefineStructure structure)
	{
		CEntityResourceForm.CFieldRedefineDescription curRedefineStructure = structure.Current() ;
			// this structure is used to link save-fields with their symbolic-field
		ListIterator i = m_children.listIterator() ;
		CWorkingEntry le = null ;
		CEntityFieldRedefine eFieldRedef = null ;  // current field
		CEntityFieldRedefine eLastFieldRedef = null ;  // current field
		le = GetNext(i);
		int nbFieldConsumed = 0 ; // total number of field consumed by children ; used for iteration
		Hashtable<CBaseLanguageEntity, CEntityResourceForm.CFormByteConsumingState> tabPassedStates = new Hashtable<CBaseLanguageEntity, CEntityResourceForm.CFormByteConsumingState>() ;
		while (le != null)
		{
			int nElementSize = le.GetByteLength() ;
			int nRemainingSizeInField = eForm.GetRemainingBytesInCurrentField();
			int nbFields = 0 ;
			if (nElementSize <= nRemainingSizeInField // current entry is part of a field
					&& le.m_Occurs == null   // current entry is not a group
					&& (le.m_children.size() == 0   // current entry is not a group
							|| eForm.getCurrentPositionInField()>0)) // but groups are allowed for DATA attribute field
			{ // current entry is an attribute of a field, or a field itself
				CIdentifier idRedefine = le.m_Redefines ;
				if (idRedefine != null)
				{ // current attribute is a redefine of an existing attribute
					if (factory.m_ProgramCatalog.IsExistingDataEntity(idRedefine.GetName(), idRedefine.GetMemberOf()))
					{
						CDataEntity e = idRedefine.GetDataReference(getLine(), factory) ;
						if (le.m_Name.equals("") && le.m_children.size()==1)
						{
							le = (CWorkingEntry)le.m_children.get(0) ;
						}
						if (e == null)
						{
							Transcoder.logError(le.getLine(), "Error during MAP REDEFINE");
						}
						else if (e.GetDataType() == CDataEntity.CDataEntityType.FIELD)
						{
//							CBaseTranscoder.ms_logger.info("INFO : Field redefined, line" + le.getLine()) ;
							le.DoCustomSemanticAnalysis(eParent, factory) ;
						}
						else if (e.GetDataType() == CDataEntity.CDataEntityType.FIELD_ATTRIBUTE)
						{
							factory.m_ProgramCatalog.RegisterDataEntity(le.m_Name, e) ;
						}
						else
						{
							Transcoder.logError(le.getLine(), "Unmanaged REDEFINE inside MAP REDEFINE");
							e.GetDataType() ;
						}
					}
					else
					{
						if (idRedefine.GetName().endsWith("F") || idRedefine.GetName().endsWith("A"))
						{
							CEntityFieldAttribute eCol = factory.NewEntityFieldAttribute(le.getLine(), le.m_Name, eFieldRedef) ;
						}
						else
						{
							Transcoder.logError(le.getLine(), "Unmanaged REDEFINE inside MAP REDEFINE");
						}
					}
				}
				else if (le.m_Name.equals("") && nElementSize < nRemainingSizeInField && eFieldRedef==null)
				{
					nbFields = eForm.ConsumeFieldsAsBytes(le.m_Length);
				}
				else if (le.m_Name.equals("") && nElementSize == nRemainingSizeInField && eFieldRedef==null)
				{
					nbFields = eForm.ConsumeFieldsAsBytes(nElementSize); // must be 1
					if (nbFields != -1)
					{ // -1 means the first 12 bytes => bypass
						if (le.m_children.size()>0)
						{
//							CBaseTranscoder.ms_logger.info("INFO : Data field splitted into sub-fields, line" + le.getLine()) ;
							String name = le.m_Name ;
							if (name.equals(""))
							{
								CEntityResourceField curF = eForm.GetCurrentRedefiningField() ;
								name = curF.GetName() + "$edit" ;
							}
							CEntityFieldRedefine eSkip = factory.NewEntityFieldRedefine(le.getLine(), name, le.m_FormalLevel); 
							eParent.AddChild(eSkip) ;
							le.DoSemanticAnalysisForChildren(eSkip, factory) ;
							if (curRedefineStructure.m_Field != null)
							{
								if (curRedefineStructure.m_Size != 1)
								{
									Transcoder.logError(le.getLine(), "Unexpecting situation while analysing MAP REDEFINE");
									throw new NacaTransAssertException("ERROR : unexpected situation while analysing MAP REDEFINE, line "+le.getLine()) ; // ASSERT
								} 
							}
							else
							{
								curRedefineStructure.m_Field = eSkip ;
								curRedefineStructure.m_Size = nbFields ;
								curRedefineStructure.m_Type = curRedefineStructure.SKIP ;
							}
						}
						else
						{
							CEntitySkipFields eSkip = factory.NewEntityWorkingSkipField(le.getLine(), le.m_Name, 1, le.m_FormalLevel); 
							eParent.AddChild(eSkip) ;
							if (curRedefineStructure.m_Field != null)
							{
								if (curRedefineStructure.m_Size != 1)
								{
									Transcoder.logError(le.getLine(), "Unexpecting situation while analysing MAP REDEFINE");
									throw new NacaTransAssertException("ERROR : unexpected situation while analysing MAP REDEFINE, line "+le.getLine()) ; // ASSERT
								} 
							}
							else
							{
								curRedefineStructure.m_Field = eSkip ;
								curRedefineStructure.m_Size = nbFields ;
								curRedefineStructure.m_Type = curRedefineStructure.SKIP ;
							}
						}
						curRedefineStructure = structure.Next() ;
						eFieldRedef = null ;
						eLastFieldRedef = null ;
						nbFieldConsumed ++ ;
					}
				}
				else
				{ // not redefine : consume bytes
					if (eForm.getCurrentPositionInField()>6 && nElementSize<nRemainingSizeInField)
					{  // the data field is cut into subfields but with no parent explicit : we must create such a parent for all subfields of the data field
//						CBaseTranscoder.ms_logger.info("INFO : Data field splitted into sub-fields, line" + le.getLine()) ;
						CEntityResourceField field = eForm.GetCurrentRedefiningField() ;
						eFieldRedef = factory.NewEntityFieldRedefine(le.getLine(), field.GetName()+"$edit", le.m_FormalLevel) ;
						eParent.AddChild(eFieldRedef) ;
						int nTotalSize = 0 ;
						while (nTotalSize < nRemainingSizeInField && le != null)
						{
							int level = new Integer(le.m_FormalLevel).intValue();
							le.m_FormalLevel = new Integer(level + 1).toString(); 
							le.DoCustomSemanticAnalysis(eFieldRedef, factory) ;
							nTotalSize += le.GetByteLength() ;
							if (nTotalSize < nRemainingSizeInField)
							{
								le = GetNext(i);
							}
						}
						nbFields = eForm.ConsumeFieldsAsBytes(nTotalSize) ;
						factory.m_ProgramCatalog.RegisterFieldRedefine(eFieldRedef) ;
						if (bSaveMap)
						{
							//factory.m_ProgramCatalog.RegisterSaveField(eFieldRedef, curRedefineStructure.m_Field) ;
						}
						else
						{
							//factory.m_ProgramCatalog.RegisterSymbolicField(eFieldRedef) ;
							curRedefineStructure.m_Field = eFieldRedef ;
							curRedefineStructure.m_Type = curRedefineStructure.FIELD ;
							curRedefineStructure.m_Size = nbFields ; //eField.GetByteLength() ;
							curRedefineStructure.m_Name = eFieldRedef.GetName() ;
						}
					}
					else
					{
						eFieldRedef = CheckRadical(eFieldRedef, le, eParent, factory, curRedefineStructure, bSaveMap) ;
						CreateAttributeForCurrentPositionInField(le, eForm, eFieldRedef, factory) ;
						if (le.m_children.size()>0)
						{
							if (le.m_children.size()==1 && eForm.getCurrentPositionInField()<7)
							{
								CWorkingEntry child = (CWorkingEntry)le.m_children.get(0);
								CreateAttributeForCurrentPositionInField(child, eForm, eFieldRedef, factory) ;
							}
							else
							{
//								CBaseTranscoder.ms_logger.info("INFO : Data field splitted into sub-fields, line" + le.getLine()) ;
								le.DoSemanticAnalysisForChildren(eFieldRedef, factory) ;
							}
						}
						nbFields = eForm.ConsumeFieldsAsBytes(nElementSize) ;
					}
					if (nbFields == 1)
					{
						curRedefineStructure = structure.Next() ;
						eLastFieldRedef = eFieldRedef ;
						eFieldRedef = null ;
						nbFieldConsumed ++ ;
					}
				}
			}
			else if (le.m_children.size()>0)
			{ // current entry is a structure wrapping several fields
				if (le.m_Redefines != null)
				{
					CDataEntity eRedef = le.m_Redefines.GetDataReference(getLine(), factory) ;
					if (eRedef.GetParent() == eParent && eRedef.HasChildren())
					{
						CEntityResourceForm.CFormByteConsumingState state_sav = eForm.getCurrentConsumingState();
						CEntityResourceForm.CFormByteConsumingState state = tabPassedStates.get(eRedef) ;
						if (state == null)
						{
							CBaseLanguageEntity entity = le.DoSemanticAnalysis(eParent, factory) ;
						}
						else
						{
							eForm.setCurrentConsumingState(state) ;
							
							CBaseLanguageEntity eData = le.DoCustomSemanticAnalysis(eParent, factory) ;
							int n = le.DoSemanticAnalysisForMapRedefineForChildren(eForm, factory, eData, bSaveMap, structure) ;
							
							eForm.setCurrentConsumingState(state_sav) ;
						}
					}
					else
					{
//						CBaseTranscoder.ms_logger.info("INFO : redefine, line" + le.getLine()) ;
						CBaseLanguageEntity entity = le.DoSemanticAnalysis(eParent, factory) ;
					}
				}
				else if (le.m_Occurs != null)
				{
					CDataEntity occ = le.m_Occurs.GetDataEntity(getLine(), factory);
					CEntityFieldOccurs eData = factory.NewEntityFieldOccurs(le.getLine(), le.m_Name);
					eData.SetFieldOccurs(le.m_FormalLevel, occ);
					eParent.AddChild(eData);
					CEntityResourceForm.CFormByteConsumingState state = eForm.getCurrentConsumingState();
					tabPassedStates.put(eData, state) ;
					if (curRedefineStructure.m_Field != null)
					{
//						if (!curRedefineStructure.m_Type.equals(curRedefineStructure.OCCURS))
//						{
//							Transcoder.logError("ERROR : unexpected situation while analysing MAP REDEFINE, line "+le.getLine());
//							throw new NacaTransAssertException("ERROR : unexpected situation while analysing MAP REDEFINE, line "+le.getLine()) ;
//						}
					}
					else
					{
						curRedefineStructure.m_Field = eData ;
						curRedefineStructure.m_Name = le.m_Name ;
						curRedefineStructure.m_Type = curRedefineStructure.OCCURS ;
					}
					structure.Next() ;
					nbFieldConsumed += le.DoSemanticAnalysisForMapRedefineForChildren(eForm, factory, eData, bSaveMap, structure) ;
					curRedefineStructure = structure.Current() ;
					//eParent.AddChild(eData) ;
				}
				else 
				{
					if (nElementSize > nRemainingSizeInField)
					{
//						CBaseTranscoder.ms_logger.info("INFO : group of fields, line" + le.getLine()) ;
					}
					CBaseLanguageEntity eData = le.DoCustomSemanticAnalysis(eParent, factory) ;
					CEntityResourceForm.CFormByteConsumingState state = eForm.getCurrentConsumingState();
					tabPassedStates.put(eData, state) ;
					int n = le.DoSemanticAnalysisForMapRedefineForChildren(eForm, factory, eData, bSaveMap, structure) ;
					curRedefineStructure = structure.Current() ;
					nbFieldConsumed += n;
					//eParent.AddChild(eData) ;
				}
			}
			else
			{ // current entry is a skipfield, with or without a name
				nbFields = eForm.ConsumeFieldsAsBytes(le.m_Length);
				if (nbFields>0)
				{
					CEntitySkipFields eSkip = factory.NewEntityWorkingSkipField(le.getLine(), le.m_Name, nbFields, le.m_FormalLevel); 
					eParent.AddChild(eSkip) ;
					nbFieldConsumed += nbFields ;
					if (curRedefineStructure.m_Field != null && bSaveMap)
					{
						int nRemaining = nbFields ;
						while (curRedefineStructure.m_Size>0 && curRedefineStructure.m_Size < nRemaining)
						{
							nRemaining -= curRedefineStructure.m_Size ;
							curRedefineStructure = structure.Next() ;
						}
						if (curRedefineStructure.m_Size != nRemaining)
						{
							Transcoder.logError(le.getLine(), "Unexpecting situation while analysing MAP REDEFINE");
							throw new NacaTransAssertException("ERROR : unexpected situation while analysing MAP REDEFINE, line "+le.getLine()) ; // ASSERT
						} 
					}
					else
					{
						curRedefineStructure.m_Field = eSkip ;
						curRedefineStructure.m_Size = nbFields ;
						curRedefineStructure.m_Type = curRedefineStructure.SKIP ;
					}
					curRedefineStructure = structure.Next() ;		
				}
				else
				{
					le.DoSemanticAnalysis(eParent, factory) ;
				}
			}
			
			le = GetNext(i);
			
		}
			
		m_bAnalysisDoneForChildren = true ;
		if (m_Occurs != null)
		{
			int n = Integer.parseInt(m_Occurs.GetValue());
			if (n < 2)
			{
				Transcoder.logError(le.getLine(), "Unexpecting situation while analysing MAP REDEFINE");
				return 0 ;
			}
			else
			{
				eForm.ConsumeFields((n-1)*nbFieldConsumed) ;
				nbFieldConsumed *= n ;
			}
		}
		else if (nbFieldConsumed == 1 && eLastFieldRedef != null)
		{
			eLastFieldRedef.m_csLevel = m_FormalLevel ;
			eLastFieldRedef.SetLine(getLine()) ;
			eParent.GetParent().AddChild(eLastFieldRedef) ;
			eParent.SetParent(null);
			if (eLastFieldRedef.GetName().equals(eParent.GetName()))
			{
				eParent.Rename("") ;
			}
		}
		return nbFieldConsumed ;
	}
	 
	 /**
	 * @param fieldRedef
	 * @param le
	 * @param eParent
	 * @param factory
	 * @param saveMap
	 * @param curRedefineStructure
	 * @return
	 */
	private CEntityFieldRedefine CheckRadical(CEntityFieldRedefine fieldRedef, CWorkingEntry le, CBaseLanguageEntity eParent, CBaseEntityFactory factory, CFieldRedefineDescription curRedefineStructure, boolean bSaveMap)
	{
		String csRadical = "" ;
		if (!le.m_Name.equals(""))
		{
			csRadical = le.m_Name.substring(0, le.m_Name.length()-1) ;
		}
		if (fieldRedef == null)
		{
			String name = csRadical ;
			int n=1 ;
			while (factory.m_ProgramCatalog.IsExistingFieldRedefine(name))
			{
				name = csRadical +"$" + n ;
				n++ ;
			}
			if (factory.m_ProgramCatalog.IsExistingDataEntity(name, ""))
			{
				name += "$edit" ;
			}
			fieldRedef = factory.NewEntityFieldRedefine(le.getLine(), name, le.m_FormalLevel);
			eParent.AddChild(fieldRedef);
			factory.m_ProgramCatalog.RegisterFieldRedefine(fieldRedef) ;
			if (bSaveMap)
			{
				//factory.m_ProgramCatalog.RegisterSaveField(fieldRedef, curRedefineStructure.m_Field) ;
			}
			else
			{
				//factory.m_ProgramCatalog.RegisterSymbolicField(fieldRedef) ;
				curRedefineStructure.m_Field = fieldRedef ;
				curRedefineStructure.m_Type = curRedefineStructure.FIELD ;
				curRedefineStructure.m_Size = 1 ; //eField.GetByteLength() ;
				curRedefineStructure.m_Name = name ;
			}
		}
		else if (!fieldRedef.GetName().equals(csRadical) && !csRadical.equals(""))
		{
			String name = csRadical ;
			int n=1 ;
			while (factory.m_ProgramCatalog.IsExistingFieldRedefine(name))
			{
				name = csRadical +"$" + n ;
				n++ ;
			}
			if (factory.m_ProgramCatalog.IsExistingDataEntity(name, ""))
			{
				name += "$edit" ;
			}
			fieldRedef.Rename(name) ;
			factory.m_ProgramCatalog.RegisterFieldRedefine(fieldRedef) ;
			if (bSaveMap)
			{
				//factory.m_ProgramCatalog.RegisterSaveField(fieldRedef, curRedefineStructure.m_Field) ;
			}
			else
			{
				//factory.m_ProgramCatalog.RegisterSymbolicField(fieldRedef) ;
			}
		}
		return fieldRedef ;
	}

	/**
	 * @param length
	 * @param currentPositionInField
	 * @param name
	 * @param field
	 * @return
	 */
	private boolean CreateAttributeForCurrentPositionInField(CWorkingEntry le, CEntityResourceForm eForm, CEntityResourceField eField, CBaseEntityFactory factory)
	{
		String name = le.m_Name ;
		int length = le.GetByteLength() ;
		int currentPositionInField = eForm.getCurrentPositionInField() ;
		if (length == 2 && currentPositionInField == 0)
		{
			CEntityFieldLength eLen = factory.NewEntityFieldLengh(le.getLine(), name, eField) ;
//			return eForm.ConsumeFieldsAsBytes(2) ;				
		}
		else if (currentPositionInField == 2 && length == 1)
		{
			CEntityFieldAttribute eCol = factory.NewEntityFieldAttribute(le.getLine(), name, eField) ;
//			return eForm.ConsumeFieldsAsBytes(1) ;				
		}
		else if (currentPositionInField == 3 && length == 1)
		{
			CEntityFieldColor eCol = factory.NewEntityFieldColor(le.getLine(), name, eField) ;
//			return eForm.ConsumeFieldsAsBytes(1) ;				
		}
		else if (currentPositionInField == 4 && length == 1)
		{
			CEntityFieldFlag eCol = factory.NewEntityFieldFlag(le.getLine(), name, eField) ;
//			return eForm.ConsumeFieldsAsBytes(1) ;				
		}
		else if (currentPositionInField == 5 && length == 1)
		{
			CEntityFieldHighlight eCol = factory.NewEntityFieldHighlight(le.getLine(), name, eField) ;
//			return eForm.ConsumeFieldsAsBytes(1) ;				
		}
		else if (currentPositionInField == 6 && length == 1)
		{
			CEntityFieldValidated eCol = factory.NewEntityFieldValidated(le.getLine(), name, eField) ;
//			return eForm.ConsumeFieldsAsBytes(1) ;				
		}
		else
		{
			if (!name.equals(""))
			{
				factory.m_ProgramCatalog.RegisterDataEntity(name, eField) ;
			}
			if (le.m_Type != CWorkingEntry.CWorkingPicType.STRING && le.m_Type!= null)
			{
//				CBaseTranscoder.ms_logger.info("INFO : Data field typed as "+le.m_Type.m_Text+":"+le.m_Format+", line" + le.getLine()) ;
			}
			le.SetType(eField) ;
			eField.SetRightJustified(le.m_bJustifiedRight) ;
			eField.SetBlankWhenZero(le.m_bBlankWhenZero) ;
//			return eForm.ConsumeFieldsAsBytes(length);
		}
		return true ;
	}

	private CWorkingEntry GetNext(ListIterator i)
	 {
	 	try
	 	{
			return (CWorkingEntry)i.next() ;
	 	}
	 	catch (NoSuchElementException ee)
	 	{
	 		return null ;
	 	}
	 	catch (ClassCastException e)
	 	{
			return GetNext(i);
	 	}
	 }
	 
	protected int GetByteLength()
	{
		int n = 0 ;
		if (m_children.size() > 0)
		{
			ListIterator i = m_children.listIterator() ;
			CWorkingEntry le = null ;
			try
			{	
				le = (CWorkingEntry)i.next() ;
			}
			catch (NoSuchElementException e)
			{
			}
			while (le != null)
			{
				if (le.m_Redefines == null)
				{
					n += le.GetByteLength() ;
				}
				try
				{	
					le = (CWorkingEntry)i.next() ;
				}
				catch (NoSuchElementException ee)
				{
					le = null ;
				}
			}
		}
		else
		{
			if (m_Type == CWorkingPicType.STRING)
			{
				n = m_Length ;
			}
			else if (m_Type == CWorkingPicType.EDITED || m_bEdited)
			{
				n = m_Format.length() ;
			}
			else 
			{	// NUMERIC TYPE
				if (m_Comp.equals(""))
				{
					n = m_Length + m_Decimal ;
				}
				else if (m_Comp.equals("COMP") || m_Comp.equals("COMP4"))
				{
					switch (m_Length)
					{
						case 4:
							n = 2 ;
							break ;
						default:
							n= m_Length / 2 ;
					}
				}
				else if (m_Comp.equals("COMP3"))
				{
					n = m_Length/2 + m_Length%2 ;
				}
				else
				{
					int ndsf = 0 ;
					Transcoder.logError(getLine(), "Unhandled situation : GetByteLength on unmanaged type");
				}
			}
		}
		if (m_Occurs!=null)
		{
			int nocc = Integer.parseInt(m_Occurs.GetValue()) ;
			n *= nocc ;
		}
		return n ;
	}
}
