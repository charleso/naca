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
/*
 * Created on 12 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import java.util.Vector;

import jlib.xml.Tag;

import lexer.CBaseToken;
import lexer.CTokenConstant;
import lexer.CTokenList;
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
import utils.modificationsReporter.Reporter;

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
			Reporter.Add("Modif_PJ", "Inspect Tallying");
			CEntityCount eCount = factory.NewEntityCount(getLine());
			eCount.setFunctionReverse(m_bFunctionReverse);
			CDataEntity eVar = m_idStringVariable.GetDataReference(getLine(), factory) ;
			eVar.RegisterReadingAction(eCount) ;
			eCount.SetCount(eVar);
			parent.AddChild(eCount) ;
			for (int i=0; i<m_arrItemToCount.size();i++)
			{
				CInspectItemToCount item = m_arrItemToCount.get(i);
				CDataEntity evar = item.m_Variable.GetDataReference(getLine(), factory);
				for (int j=0; j<item.m_TokenToCount.size(); j++)
				{
					CTerminal t = item.m_TokenToCount.get(j);
					if (item.m_bAll)
					{
						eCount.CountAll(t.GetDataEntity(getLine(), factory), evar);
					}
					else if (item.m_bCharactersAfter)
					{
						eCount.CountAfter(t.GetDataEntity(getLine(), factory), evar);
					}
					else if (item.m_bCharactersBefore)
					{
						eCount.CountBefore(t.GetDataEntity(getLine(), factory), evar);
					}
					else
					{
						eCount.CountLeading(t.GetDataEntity(getLine(), factory), evar);
					}	
				}		
			} 
			return eCount ;
		}
		else if (m_Method == CInspectActionType.CONVERTING)
		{		
			Reporter.Add("Modif_PJ", "Inspect Converting");
			CEntityInspectConverting eEntityInspectConverting = factory.NewEntityInspectConverting(getLine());
			
			CDataEntity eVariable = m_idStringVariable.GetDataReference(getLine(), factory);
			eVariable.RegisterReadingAction(eEntityInspectConverting) ;
			eEntityInspectConverting.SetVariable(eVariable);			

			CDataEntity eFrom = m_inspectConverting.m_from.GetDataEntity(getLine(), factory) ;
			CDataEntity eTo = m_inspectConverting.m_to.GetDataEntity(getLine(), factory) ;
			eFrom.RegisterReadingAction(eEntityInspectConverting) ;
			eTo.RegisterWritingAction(eEntityInspectConverting) ;		
			eEntityInspectConverting.SetFromTo(eFrom, eTo);
				
			parent.AddChild(eEntityInspectConverting) ;
			return eEntityInspectConverting;
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
		if (tok.GetKeyword() == CCobolKeywordList.FUNCTION)
		{
			Reporter.Add("Modif_PJ", "Inspect Function");
			boolean bFunction = false;
			// INSPECT FUNCTION REVERSE(WS-ENCOURS) TALLYING LONG FOR LEADING SPACES
			tok = GetNext();
			if (tok.GetKeyword() == CCobolKeywordList.REVERSE)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)	// (
				{
					tok = GetNext();		
					m_idStringVariable = ReadIdentifier() ;
					m_bFunctionReverse = true;
					tok = GetNext();		// TALLYING
					bFunction = true;
				}				
			}
			if(!bFunction)
			{
				Transcoder.logError(tok.getLine(), "Unexpecting situation while parsing INSPECT FUNCTION");
				return false;
			}
		}
		else		
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
						CInspectItemToCount count = new CInspectItemToCount() ;
						count.m_Variable = variableForCountResult ;
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
						m_arrItemToCount.add(count);
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
								CInspectItemToCount item = new CInspectItemToCount() ;
								item.m_bAll = bAll ; 
								item.m_Variable = variableForCountResult ;
								while (t != null)
								{
									item.m_TokenToCount.add(t) ;
									
									tok = GetCurrentToken() ;
									if (tok.GetType() == CTokenType.COMMA)
									{
										tok = GetNext() ;
									} 
									if (tok.GetType() == CTokenType.STRING || 
										tok.GetType() == CTokenType.CONSTANT || 
										tok.GetType() == CTokenType.NUMBER)
									{
										t = ReadTerminal();
									}
									else
									{
										t = null ;
									} 
								}
								m_arrItemToCount.add(item);
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
			Reporter.Add("Modif_PJ", "Inspect Converting");
			m_Method = CInspectActionType.CONVERTING ;
			m_inspectConverting = new CInspectConverting();
			tok = GetNext();
			
//			if(tok.equals(CCobolConstantList.LOW_VALUE.m_Name))
//				
//			
//			CCobolConstantList.LOW_VALUE
			
			m_inspectConverting.m_from = ReadTerminal();
			tok = GetCurrentToken();
			if (tok.GetKeyword() == CCobolKeywordList.TO)
			{
				tok = GetNext();
				m_inspectConverting.m_to = ReadTerminal();
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Unexpecting INSPECT CONVERTING syntax");
				return false ;
			}				
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
			Reporter.Add("Modif_PJ", "Inspect Converting");
			Tag tagRoot = new Tag(root);
			Tag tagInspectConvert = tagRoot.addTag("InspectConvert");
			Tag tagVariable = tagInspectConvert.addTag("Variable");
			m_idStringVariable.ExportTo(tagVariable);
			
			Tag tagConverting = tagInspectConvert.addTag("Converting");
			Tag tagFrom = tagConverting.addTag("From");
			m_inspectConverting.m_from.ExportTo(tagFrom);
							
			Tag tagTo = tagConverting.addTag("To");
			m_inspectConverting.m_from.ExportTo(tagTo);
			eInsp = tagInspectConvert.getElement();
		}
		else if (m_Method == CInspectActionType.TALLYING)
		{
			eInsp = root.createElement("InspectEnum") ;
			Element eVar = root.createElement("Variable");
			m_idStringVariable.ExportTo(eVar, root);
			eInsp.appendChild(eVar);
			
			for (int i=0; i<m_arrItemToCount.size(); i++)
			{
				CInspectItemToCount item = m_arrItemToCount.get(i);
				Element eCount = root.createElement("Count");
				eInsp.appendChild(eCount);
				Element eRes = root.createElement("Result") ;
				eCount.appendChild(eRes); 
				item.m_Variable.ExportTo(eRes, root) ;
				String cs = "Leading" ;
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
	protected boolean m_bFunctionReverse = false;
	protected CIdentifier m_idStringVariable = null ; 
	protected CInspectActionType m_Method = null ; 
	protected Vector<CInspectItemToCount> m_arrItemToCount = new Vector<CInspectItemToCount>() ;
	
	protected static class CInspectActionType
	{
		public static CInspectActionType REPLACING = new CInspectActionType() ;
		public static CInspectActionType TALLYING = new CInspectActionType() ;
		public static CInspectActionType CONVERTING = new CInspectActionType() ;
	}
	protected class CInspectItemToCount
	{
		boolean m_bAll = false ;
		boolean m_bCharactersBefore = false ;
		boolean m_bCharactersAfter = false ;
		Vector<CTerminal> m_TokenToCount = new Vector<CTerminal>() ;
		CIdentifier m_Variable = null ;
	}
	
	protected CInspectConverting m_inspectConverting = null;
	protected class CInspectConverting
	{
		/*CIdentifier*/ CTerminal m_from = null;
		/*CIdentifier*/ CTerminal m_to = null;		
	}
}
