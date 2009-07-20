/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 12 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import java.util.Vector;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolConstantList;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import parser.expression.CTerminal;
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.Verbs.CEntityCount;
import semantic.Verbs.CEntityInspectConverting;
import semantic.Verbs.CEntityReplace;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CInspect extends CCobolElement
{

	/**
	 * @param line
	 */
	public CInspect(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (m_Method == CInspectActionType.REPLACING)
		{
			CEntityReplace eReplace = factory.NewEntityReplace(getLine());
			CDataEntity eVar = m_idStringVariable.GetDataReference(getLine(), factory) ;
			eReplace.SetReplace(eVar);
			eVar.RegisterWritingAction(eReplace) ;
			parent.AddChild(eReplace) ;
			
			// variable into witch replacing, and methode
			for (int i = 0; i<m_arrItemToReplace.size(); i++)
			{
				CInspectValueToReplace item = m_arrItemToReplace.get(i);
				CTerminal term ;
				if (item.m_ValToReplaceAll != null)
				{
					term = item.m_ValToReplaceAll ;
					eReplace.AddReplaceAll();
				}
				else if (item.m_ValToReplaceFirst != null)
				{
					term = item.m_ValToReplaceFirst ;
					eReplace.AddReplaceFirst();
				}
				else if (item.m_ValToReplaceLeading != null)
				{
					term = item.m_ValToReplaceLeading ;
					eReplace.AddReplaceLeading();
				}
				else
				{
					Transcoder.logError(getLine(), "Incoherent data for INSPECT");
					return null;
				}
				
				// value to replace
				if (term.GetValue().equals(CCobolConstantList.SPACE.m_Name) || term.GetValue().equals(CCobolConstantList.SPACES.m_Name))
				{
					eReplace.ReplaceSpaces() ;
				}
				else if (term.GetValue().equals(CCobolConstantList.ZERO.m_Name) || term.GetValue().equals(CCobolConstantList.ZEROS.m_Name) || term.GetValue().equals(CCobolConstantList.ZEROES.m_Name))
				{
					eReplace.ReplaceZeros() ;
				}
				else if (term.GetValue().equals(CCobolConstantList.LOW_VALUE.m_Name) || term.GetValue().equals(CCobolConstantList.LOW_VALUES.m_Name))
				{
					eReplace.ReplaceLowValues();
				}
				else if (term.GetValue().equals(CCobolConstantList.HIGH_VALUE.m_Name) || term.GetValue().equals(CCobolConstantList.HIGH_VALUES.m_Name))
				{
					eReplace.ReplaceHighValues() ;
				}
				else
				{
					CDataEntity eRep = term.GetDataEntity(getLine(), factory) ;
					if (eRep != null)
					{ // string or number
						eReplace.ReplaceData(eRep);
					}// else constant
					else
					{
						Transcoder.logError(getLine(), "Incoherent data for INSPECT");
						return null;
					}
				}
				
				// value to replace by
				if (item.m_ValNew.GetValue().equals(CCobolConstantList.SPACE.m_Name) || item.m_ValNew.GetValue().equals(CCobolConstantList.SPACES.m_Name))
				{
					eReplace.BySpaces() ;
				}
				else if (item.m_ValNew.GetValue().equals(CCobolConstantList.ZERO.m_Name) || item.m_ValNew.GetValue().equals(CCobolConstantList.ZEROS.m_Name) || item.m_ValNew.GetValue().equals(CCobolConstantList.ZEROES.m_Name))
				{
					eReplace.ByZeros() ;
				}
				else if (item.m_ValNew.GetValue().equals(CCobolConstantList.LOW_VALUE.m_Name) || item.m_ValNew.GetValue().equals(CCobolConstantList.LOW_VALUES.m_Name))
				{
					eReplace.ByLowValues();
				}
				else if (item.m_ValNew.GetValue().equals(CCobolConstantList.HIGH_VALUE.m_Name) || item.m_ValNew.GetValue().equals(CCobolConstantList.HIGH_VALUES.m_Name))
				{
					eReplace.ByHighValues() ;
				}
				else
				{
					CDataEntity eBy = item.m_ValNew.GetDataEntity(getLine(), factory);
					if (eBy != null)
					{
						eReplace.ByData(eBy);
					}
					else
					{
						Transcoder.logError(getLine(), "Incoherent data for INSPECT");
						return null;
					}
				}
			}
			return eReplace ;
		}
		else if (m_Method == CInspectActionType.TALLYING)
		{
			CEntityCount eCount = factory.NewEntityCount(getLine());
			CDataEntity eVar = m_idStringVariable.GetDataReference(getLine(), factory) ;
			eVar.RegisterReadingAction(eCount) ;
			eCount.SetCount(eVar);
			parent.AddChild(eCount) ;
			for (int i=0; i<m_arrItemToCount.size();i++)
			{
				CInspectItemToCount itemToCount = m_arrItemToCount.get(i);
				CDataEntity evar = itemToCount.m_Variable.GetDataReference(getLine(), factory);
				eCount.SetToVar(evar);
				for (CInspectItem item : itemToCount.m_Items)
				{
					for (int j=0; j<item.m_TokenToCount.size(); j++)
					{
						CTerminal t = item.m_TokenToCount.get(j);
						if (item.m_bAll)
						{
							eCount.CountAll(t.GetDataEntity(getLine(), factory));
						}
						else if (item.m_bCharactersAfter)
						{
							eCount.CountAfter(t.GetDataEntity(getLine(), factory));
						}
						else if (item.m_bCharactersBefore)
						{
							eCount.CountBefore(t.GetDataEntity(getLine(), factory));
						}
						else
						{
							eCount.CountLeading(t.GetDataEntity(getLine(), factory));
						}
					}
				}		
			} 
			return eCount ;
		}
		else if (m_Method == CInspectActionType.CONVERTING)
		{
			CEntityInspectConverting entity = factory.NewEntityInspectConverting(getLine());
			CDataEntity eVar = m_idStringVariable.GetDataReference(getLine(), factory);
			eVar.RegisterWritingAction(entity) ;
			parent.AddChild(entity) ;
			entity.SetConvert(eVar);
			entity.SetFrom(m_Converting.m_From.GetDataEntity(getLine(), factory));
			entity.SetTo(m_Converting.m_To.GetDataEntity(getLine(), factory));
			return entity;
		}
		else
		{
			Transcoder.logError(getLine(), "No Semantic Analysis yet for INSPECT");
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.INSPECT)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		tok = GetNext(); 
		m_idStringVariable = ReadIdentifier() ;
		tok = GetCurrentToken();
		if (tok.GetKeyword() == CCobolKeywordList.REPLACING)
		{
			m_Method = CInspectActionType.REPLACING ;
			tok = GetNext();
			boolean bDone = false ;
			while (!bDone)
			{
				tok = GetCurrentToken();
				CInspectValueToReplace item = new CInspectValueToReplace() ;
				if (tok.GetKeyword() == CCobolKeywordList.ALL)
				{
					GetNext();
					item.m_ValToReplaceAll = ReadTerminal() ;
				}
				else if (tok.GetKeyword() == CCobolKeywordList.LEADING)
				{
					GetNext();
					item.m_ValToReplaceLeading = ReadTerminal() ;
				}
				else if (tok.GetKeyword() == CCobolKeywordList.FIRST)
				{
					GetNext();
					item.m_ValToReplaceFirst = ReadTerminal() ;
				}
				else
				{ // default = ALL
					item.m_ValToReplaceAll = ReadTerminal() ;
					if (item.m_ValToReplaceAll == null)
					{
						break ;
					}
				}
				
				tok = GetCurrentToken() ;
				if (tok.GetKeyword() != CCobolKeywordList.BY)
				{
					Transcoder.logError(tok.getLine(), "Unexpecting token instead of BY : "+tok.GetValue()) ;
					return false;
				}
				GetNext() ;
				item.m_ValNew = ReadTerminal() ;
				tok = GetCurrentToken() ;
				if (tok.GetType() == CTokenType.COMMA)
					tok = GetNext() ;
				m_arrItemToReplace.add(item);
			}
		}
		else if (tok.GetKeyword() == CCobolKeywordList.TALLYING)
		{
			m_Method = CInspectActionType.TALLYING ;
			tok = GetNext();
			boolean bDone2 = false ;
			while (!bDone2)
			{
				CIdentifier variableForCountResult = ReadIdentifier();
				tok = GetCurrentToken();
				if (tok.GetKeyword() == CCobolKeywordList.FOR)
				{
					tok = GetNext();
					if (tok.GetKeyword() == CCobolKeywordList.CHARACTERS)
					{
						tok = GetNext() ;
						CInspectItemToCount icount = new CInspectItemToCount() ;
						icount.m_Variable = variableForCountResult ;
						CInspectItem count = new CInspectItem();
						if (tok.GetKeyword() == CCobolKeywordList.AFTER)
						{
							count.m_bCharactersAfter = true ;
						}
						else if (tok.GetKeyword() == CCobolKeywordList.BEFORE)
						{
							count.m_bCharactersBefore = true ;
						}
						else
						{
							Transcoder.logError(tok.getLine(), "Error line ");
							return false ;
						}
						
						tok = GetNext();
						if (tok.GetKeyword() == CCobolKeywordList.INITIAL)
						{
							tok=GetNext();
						}
						CTerminal t = ReadTerminal();
						count.m_TokenToCount.add(t) ; 
						icount.m_Items.add(count) ; 
						m_arrItemToCount.add(icount);
					}
					else
					{
						boolean bDone = false ;
						while (!bDone)
						{
							tok = GetCurrentToken();
							boolean bAll = false  ;
							if (tok.GetKeyword() == CCobolKeywordList.ALL)
							{
								tok = GetNext();
								bAll = true ;
							}
							else if (tok.GetKeyword() == CCobolKeywordList.LEADING)
							{
								tok = GetNext();
								bAll = false ;
							}
							else
							{
								bDone =true  ;
							}
							if (!bDone)
							{
								CTerminal t = ReadTerminal();
								CInspectItemToCount itemToCount = new CInspectItemToCount() ;
								itemToCount.m_Variable = variableForCountResult ;
								while (t != null)
								{
									CInspectItem item = new CInspectItem();
									item.m_bAll = bAll ; 
									item.m_TokenToCount.add(t) ;
									
									tok = GetCurrentToken() ;
									if (tok.GetType() == CTokenType.COMMA)
									{
										tok = GetNext() ;
									} 
									if (tok.GetType() == CTokenType.STRING || tok.GetType() == CTokenType.CONSTANT || tok.GetType() == CTokenType.NUMBER)
									{
										t = ReadTerminal();
									}
									else
									{
										t = null ;
									}
									itemToCount.m_Items.add(item);
								}
								m_arrItemToCount.add(itemToCount);
								
								if (tok.GetKeyword() == CCobolKeywordList.AFTER || tok.GetKeyword() == CCobolKeywordList.BEFORE)
								{
									CInspectItem item = new CInspectItem();
									if (tok.GetKeyword() == CCobolKeywordList.AFTER)
									{
										item.m_bCharactersAfter = true ;
									}
									else if (tok.GetKeyword() == CCobolKeywordList.BEFORE)
									{
										item.m_bCharactersBefore = true ;
									}
									tok = GetNext();
									if (tok.GetKeyword() == CCobolKeywordList.INITIAL)
									{
										tok=GetNext();
									}
									item.m_TokenToCount.add(ReadTerminal()) ;
									itemToCount.m_Items.add(item);
								}
							}
						} 
					}
					tok = GetCurrentToken() ;
					if (tok.GetType() != CTokenType.IDENTIFIER)
					{
						bDone2 = true ;
					}
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Unexpecting situation");
					return false ;
				}
			}
		}
		else if (tok.GetKeyword() == CCobolKeywordList.CONVERTING)
		{
			m_Method = CInspectActionType.CONVERTING ;
			m_Converting = new CInspectConverting();
			GetNext();
			m_Converting.m_From = ReadTerminal();
			Assert(CCobolKeywordList.TO);
			m_Converting.m_To = ReadTerminal();
		}
		else 
		{
			Transcoder.logError(tok.getLine(), "Unexpecting INSPECT action : "+tok.GetValue()) ;
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eInsp ;
		if (m_Method == CInspectActionType.REPLACING)
		{
			eInsp = root.createElement("InspectRemplace") ;
			for (int i =0; i<m_arrItemToReplace.size(); i++)
			{
				CInspectValueToReplace item = m_arrItemToReplace.get(i); 
				Element e = null ;
				if (item.m_ValToReplaceAll != null)
				{
					e = root.createElement("All") ;
					eInsp.appendChild(e) ;
					item.m_ValToReplaceAll.ExportTo(e, root) ;
				}
				else if(item.m_ValToReplaceFirst != null)
				{
					e = root.createElement("First") ;
					eInsp.appendChild(e) ;
					item.m_ValToReplaceFirst.ExportTo(e, root) ;
				}
				else if(item.m_ValToReplaceLeading != null)
				{
					e = root.createElement("Leading") ;
					eInsp.appendChild(e) ;
					item.m_ValToReplaceLeading.ExportTo(e, root) ;
				}
				Element eBy = root.createElement("By") ;
				e.appendChild(eBy);
				item.m_ValNew.ExportTo(eBy, root);
			}
		}
		else if (m_Method == CInspectActionType.CONVERTING)
		{
			eInsp = root.createElement("InspectConvert") ;
		}
		else if (m_Method == CInspectActionType.TALLYING)
		{
			eInsp = root.createElement("InspectEnum") ;
			Element eVar = root.createElement("Variable");
			m_idStringVariable.ExportTo(eVar, root);
			eInsp.appendChild(eVar);
			
			for (int i=0; i<m_arrItemToCount.size(); i++)
			{
				CInspectItemToCount itemToCount = m_arrItemToCount.get(i);
				Element eCount = root.createElement("Count");
				eInsp.appendChild(eCount);
				Element eRes = root.createElement("Result") ;
				eCount.appendChild(eRes); 
				itemToCount.m_Variable.ExportTo(eRes, root) ;
				String cs = "Leading" ;
				
				for (CInspectItem item : itemToCount.m_Items)
				{
					if (item.m_bAll)
					{
						cs = "All";
					}
					else if (item.m_bCharactersAfter)
					{
						cs = "CharsAfter" ;
					}
					else if (item.m_bCharactersBefore)
					{
						cs = "CharsBefore" ;
					}
					for (int j=0; j<item.m_TokenToCount.size(); j++)
					{
						CTerminal term = item.m_TokenToCount.get(j);
						Element e = root.createElement(cs) ;
						eCount.appendChild(e);
						term.ExportTo(e, root);
					}
				}
			} 
		}
		else
		{
			return null ;
		}
		return eInsp;
	}

	protected class CInspectValueToReplace
	{
		public CTerminal m_ValToReplaceAll = null ;
		public CTerminal m_ValToReplaceLeading = null ;
		public CTerminal m_ValToReplaceFirst = null ;
		public CTerminal m_ValNew = null ;
	}
	protected Vector<CInspectValueToReplace> m_arrItemToReplace = new Vector<CInspectValueToReplace>() ;
	protected CIdentifier m_idStringVariable = null ; 
	protected CInspectActionType m_Method = null ; 
	protected CInspectConverting m_Converting = null ;
	protected Vector<CInspectItemToCount> m_arrItemToCount = new Vector<CInspectItemToCount>() ;
	protected static class CInspectActionType
	{
		public static CInspectActionType REPLACING = new CInspectActionType() ;
		public static CInspectActionType TALLYING = new CInspectActionType() ;
		public static CInspectActionType CONVERTING = new CInspectActionType() ;
	}
	protected class CInspectItemToCount
	{
		Vector<CInspectItem> m_Items = new Vector<CInspectItem>() ;
		CIdentifier m_Variable = null ;
	}
	protected class CInspectItem
	{
		boolean m_bAll = false ;
		boolean m_bCharactersBefore = false ;
		boolean m_bCharactersAfter = false ;
		Vector<CTerminal> m_TokenToCount = new Vector<CTerminal>() ;
	}
	protected class CInspectConverting
	{
		CTerminal m_From = null ;
		CTerminal m_To = null ;
	}
}
