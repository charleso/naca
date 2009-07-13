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

import generate.CJavaEntityFactory;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.w3c.dom.*;

import lexer.*;
import lexer.Cobol.CCobolKeywordList;
import parser.*;
import parser.Cobol.CCobolElement;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.CEntityClass;
import semantic.CEntitySQLCursorSection;
import utils.LevelKeywordStackManager;
import utils.LevelKeywords;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CProgram extends CCommentContainer
{
	private CWorking m_eWorking;
	private CLinkageSection m_eLinkage;
	private CFileSection m_eFile;
	private CProcedureDivision m_eProcDiv;
	/**
	 * @param line
	 */
	public CProgram(int line)
	{
		super(line);
	}

	// start of the parsing process
	// this methode expects environnement/data/procedure/identification divisions and comments
	
	private void beginParseProgram()
	{	
		LevelKeywords levelKeywords = LevelKeywordStackManager.getAndPushNewLevelKeywords();
		levelKeywords.registerManagedKeyword(CCobolKeywordList.IDENTIFICATION);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.ID);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.ENVIRONMENT);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.DATA);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.PROCEDURE);
	}

	private void endParseProgram()
	{		
		LevelKeywordStackManager.popLevelKeywords();
	}	
	
	protected boolean DoParsing()
	{
		beginParseProgram();
		
		CBaseToken tok = GetCurrentToken() ;
		boolean bRet = true ;
		while (bRet && tok != null)
		{
			if (tok.IsKeyword())
			{
				CReservedKeyword kw = tok.GetKeyword() ;
				if (kw == CCobolKeywordList.IDENTIFICATION || kw == CCobolKeywordList.ID) 
				{
					bRet = ParseIdentificationDivision();
				}
				else if (kw == CCobolKeywordList.ENVIRONMENT)
				{
					bRet = ParseEnvironmentDivision();
				}
				else if (kw == CCobolKeywordList.DATA)
				{
					bRet = ParseDataDivision();
				}
				else if (kw == CCobolKeywordList.PROCEDURE)
				{
					bRet = ParseProcedureDivision();
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Unexpecting Token : " + tok.toString()) ; 
					CCobolElement e = new CUnparsedToken(tok.getLine()) ;
					bRet = Parse(e);
					AddChild(e) ;
				}
			}
			else if (tok.GetType() == CTokenType.END_OF_BLOCK)
			{
				GetNext();
			}
			else if (tok.GetType() == CTokenType.IDENTIFIER)
			{
				CCobolElement e = new CComment(tok.getLine(), tok.GetValue());
				AddChild(e);
				GetNext();
			}
			else
			{
				Transcoder.logError(GetCurrentToken().getLine(), "Unparsed Token : " + GetCurrentToken().toString()) ; 
				CCobolElement e = new CUnparsedToken(GetCurrentToken().getLine()) ;
				bRet = Parse(e);
				AddChild(e) ;
			}			
			if (!bRet)
			{
				CBaseToken tokRet = GetCurrentToken() ;
				if (tokRet == null)
				{
					endParseProgram();
					return false ;
				}
				Transcoder.logError(tokRet.getLine(), "Unparsed Token : " + tokRet.toString()) ; 
				CCobolElement e = new CUnparsedToken(tokRet.getLine()) ;
				bRet = Parse(e);
				AddChild(e) ;
			}
			tok = GetCurrentToken();
		}
		endParseProgram();
		return bRet ;
	}

	// ParseIdentificationDivision
	// token expected expected :
	//	- comments
	//	- parameters as keyword + DOT + value + DOT + EOL : PROGRAM-ID, AUTHOR or DATE-WRITTEN 
	protected boolean ParseIdentificationDivision()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.IDENTIFICATION && tok.GetKeyword() != CCobolKeywordList.ID)
		{
			return false ;
		}
		tok = GetNext();
		if (tok.GetKeyword() != CCobolKeywordList.DIVISION)
		{
			return false ; 
		}
		tok = GetNext();
		if (tok.GetType() != CTokenType.DOT)
		{
			return false ; 
		}
		GetNext();
		
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tokVar = GetCurrentToken() ;
			if (tokVar.IsKeyword())
			{
				CReservedKeyword kw = tokVar.GetKeyword() ; 
				if (kw == CCobolKeywordList.PROGRAM_ID || 
					kw == CCobolKeywordList.AUTHOR || 
					kw == CCobolKeywordList.DATE_WRITTEN ||
					kw == CCobolKeywordList.DATE_COMPILED ||
					kw == CCobolKeywordList.REMARKS)
				{
					String cs = "" ;
					CBaseToken tokDot = GetNext() ; // consume keyword, expecting a DOT
					if (tokDot.GetType() == CTokenType.DOT)
					{ 
						GetNext();	// consume DOT
						cs = ReadStringUntilEOL() ;
					}
					else
					{
						Transcoder.logError(getLine(), "Unexpecting sequence : " + tokVar.toString() + tokDot.toString()) ;
						return false ;					
					}
					if (kw == CCobolKeywordList.PROGRAM_ID)
					{
						m_ProgramID = cs ;
					}
					else if (kw == CCobolKeywordList.AUTHOR)
					{
						m_Author = cs ;
					}
					else if (kw == CCobolKeywordList.DATE_WRITTEN)
					{
						m_DateWritten = cs ;
					}
					else if (kw == CCobolKeywordList.REMARKS)
					{
						m_Remark = cs ;
					}
				}
				else if (kw == CCobolKeywordList.ENVIRONMENT || kw == CCobolKeywordList.DATA)
				{
					bDone = true ;
				}
				else
				{
					String cs =  "" ;
					cs = tok.GetValue();
					tok = GetNext();
					while (tok != null && !tok.m_bIsNewLine)
					{
						cs += " " + tok.GetValue() ;
						tok = GetNext();
					}
					if (tok == null)
					{
						return false ;
					} 
					CComment c = new CComment(tok.getLine(), cs) ;
					AddChild(c) ;
				}
			}
			else
			{
				// treated as comment
				String cs =  "" ;
				cs = tok.GetValue();
				tok = GetNext();
				while (!tok.m_bIsNewLine)
				{
					cs += " " + tok.GetValue() ;
					tok = GetNext();
				}
				CComment c = new CComment(tok.getLine(), cs) ;
				AddChild(c) ;
			}
		}
		return true ;
	}	

	protected boolean ParseEnvironmentDivision()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.ENVIRONMENT)
		{
			return false ;
		}
		tok = GetNext();
		if (tok.GetKeyword() != CCobolKeywordList.DIVISION)
		{
			return false ; 
		}
		tok = GetNext();
		if (tok.GetType() != CTokenType.DOT)
		{
			return false ; 
		}
		GetNext();

		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tokVar = GetCurrentToken() ;
			if (tokVar.GetKeyword() == CCobolKeywordList.CONFIGURATION)
			{
				tokVar = GetNext();
				if (tokVar.GetKeyword() != CCobolKeywordList.SECTION)
				{
					Transcoder.logError(tokVar.getLine(), "Expecting SECTION") ;
					return false ;
				}
				tokVar =GetNext() ;
				if (tokVar.GetType() != CTokenType.DOT)
				{
					Transcoder.logError(tokVar.getLine(), "Expecting DOT") ;
					return false ;
				}
				GetNext();
				CCobolElement e = new CConfigurationSection(tokVar.getLine()) ;
				if (!Parse(e))
				{
					return false ;
				}
				AddChild(e) ; 
			}
			else if (tokVar.GetKeyword() == CCobolKeywordList.INPUT_OUTPUT)
			{
				tokVar = GetNext();
				if (tokVar.GetKeyword() != CCobolKeywordList.SECTION)
				{
					Transcoder.logError(tokVar.getLine(), "Expecting SECTION") ;
					return false ;
				}
				tokVar =GetNext() ;
				if (tokVar.GetType() != CTokenType.DOT)
				{
					Transcoder.logError(tokVar.getLine(), "Expecting DOT") ;
					return false ;
				}
				GetNext();
				CCobolElement e = new CIOSection(tokVar.getLine()) ;
				if (!Parse(e))
				{
					return false ;
				}
				AddChild(e) ; 
			}
			else if (tokVar.GetKeyword()== CCobolKeywordList.EJECT)
			{
				GetNext() ;
			}
			//else to do later
			else
			{
				bDone = true ; // this token is not handled by this function, go back to caller
			}
		}
		return true ;
	}

//	protected boolean ParseDataDivision()
//	{	
//		CBaseToken tok = GetNext();
//		if (tok.GetKeyword() != CCobolKeywordList.DIVISION)
//		{
//			return false ; 
//		}
//		tok = GetNext();
//		if (tok.GetType() != CTokenType.DOT)
//		{
//			return false ; 
//		}
//		GetNext();
//
//		boolean bDone = false ;
//		while (!bDone)
//		{
//			CBaseToken tokVar = GetCurrentToken() ;
//			if (tokVar == null)
//			{
//				break ;
//			}
//			if (tokVar.GetKeyword() == CCobolKeywordList.EJECT || 
//				tokVar.GetKeyword() == CCobolKeywordList.SKIP3 ||
//				tokVar.GetKeyword() == CCobolKeywordList.SKIP2)
//			{
//				tokVar = GetNext() ;
//				if (tokVar.GetType() == CTokenType.DOT)
//				{
//					tokVar = GetNext() ;
//				}
//			}
//			else if (tokVar.IsKeyword())
//			{	// only two sections expected : WORKING-STORAGE SECTION and LINKAGE SECTION
//				if (tokVar.GetKeyword() != CCobolKeywordList.WORKING_STORAGE
//					&& tokVar.GetKeyword() != CCobolKeywordList.LINKAGE 
//					&& tokVar.GetKeyword() != CCobolKeywordList.FILE)
//				{
//					return true ; // not for that function
//				}
//				CBaseToken tokSection = GetNext(); // consume first token, expect SECTION token
//				if (tokSection.GetKeyword() != CCobolKeywordList.SECTION)
//				{
//					m_Logger.error("ERROR Line " +getLine()+ " : " + "Unexpected sequence : " + tokVar.toString() + tokSection.toString());
//					return false ;
//				}
//				CBaseToken tokDot = GetNext() ; // consume SECTION token, expecting DOT token
//				if (tokDot.GetType() != CTokenType.DOT)
//				{
//					m_Logger.error("ERROR Line " +getLine()+ " : " + "Unexpected sequence : " + tokVar.toString() + tokSection.toString() + tokDot.toString());
//					return false ;
//				} 
//				tokDot = GetNext() ; // consume DOT 
//				if (tokVar.GetKeyword() == CCobolKeywordList.WORKING_STORAGE)
//				{
//					m_eWorking = new CWorking(tokVar.m_line) ;
//					AddChild(m_eWorking) ;
//					if (!Parse(m_eWorking))
//					{
//						return false ;
//					}
//				}
//				else if (tokVar.GetKeyword() == CCobolKeywordList.LINKAGE)
//				{
//					//ConsumeEndLineWithDot() ;
//					m_eLinkage = new CLinkageSection(tokVar.m_line) ;
//					AddChild(m_eLinkage) ;
//					if (!Parse(m_eLinkage))
//					{
//						return false ;
//					}
//				}
//				else if (tokVar.GetKeyword() == CCobolKeywordList.FILE)
//				{
//					//ConsumeEndLineWithDot() ;
//					m_eFile = new CFileSection(tokVar.m_line) ;
//					AddChild(m_eFile) ;
//					if (!Parse(m_eFile))
//					{
//						return false ;
//					}
//				}
//				else
//				{
//					m_Logger.error("ERROR Line " +getLine()+ " : " + "Unexpected sequence : " + tokVar.toString() + tokSection.toString() + tokDot.toString());
//					return false ;
//				}
//				
//			}
//			else
//			{
//				bDone = true ; // this token is not handled by this function, go back to caller
//			}
//		}
//		return true ;
//	}

	private void beginParseDataDivision()
	{	
		LevelKeywords levelKeywords = LevelKeywordStackManager.getAndPushNewLevelKeywords();
		levelKeywords.registerManagedKeyword(CCobolKeywordList.EJECT);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.SKIP2);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.SKIP3);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.WORKING_STORAGE);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.FILE);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.LINKAGE);
	}

	private void endParseDataDivision()
	{		
		LevelKeywordStackManager.popLevelKeywords();
	}

	protected boolean ParseDataDivision()
	{	
		beginParseDataDivision();
		CBaseToken tok = GetNext();
		if (tok.GetKeyword() != CCobolKeywordList.DIVISION)
		{
			endParseDataDivision();
			return false ; 
		}
		tok = GetNext();
		if (tok.GetType() != CTokenType.DOT)
		{
			endParseDataDivision();
			return false ; 
		}
		GetNext();
		
		boolean b = DoParseDataDivision();
		endParseDataDivision();
		return b;
	}
		
	protected boolean DoParseDataDivision()
	{
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tokVar = GetCurrentToken() ;
			if (tokVar == null)
			{
				break ;
			}
			if (tokVar.GetKeyword() == CCobolKeywordList.EJECT || 
				tokVar.GetKeyword() == CCobolKeywordList.SKIP3 ||
				tokVar.GetKeyword() == CCobolKeywordList.SKIP2)
			{
				tokVar = GetNext() ;
				if (tokVar.GetType() == CTokenType.DOT)
				{
					tokVar = GetNext() ;
				}
			}
			else if (tokVar.IsKeyword())
			{	// only two sections expected : WORKING-STORAGE SECTION and LINKAGE SECTION
				if (tokVar.GetKeyword() != CCobolKeywordList.WORKING_STORAGE
					&& tokVar.GetKeyword() != CCobolKeywordList.LINKAGE 
					&& tokVar.GetKeyword() != CCobolKeywordList.FILE)
				{
					return true ; // not for that function
				}
				CBaseToken tokSection = GetNext(); // consume first token, expect SECTION token
				if (tokSection.GetKeyword() != CCobolKeywordList.SECTION)
				{
					Transcoder.logError(getLine(), "Unexpecting sequence : " + tokVar.toString() + tokSection.toString());
					return false ;
				}
				CBaseToken tokDot = GetNext() ; // consume SECTION token, expecting DOT token
				if (tokDot.GetType() != CTokenType.DOT)
				{
					Transcoder.logError(getLine(), "Unexpecting sequence : " + tokVar.toString() + tokSection.toString() + tokDot.toString());
					return false ;
				} 
				tokDot = GetNext() ; // consume DOT 
				if (tokVar.GetKeyword() == CCobolKeywordList.WORKING_STORAGE)
				{
					m_eWorking = new CWorking(tokVar.getLine()) ;
					AddChild(m_eWorking) ;
					if (!Parse(m_eWorking))
					{
						return false ;
					}
				}
				else if (tokVar.GetKeyword() == CCobolKeywordList.LINKAGE)
				{
					//ConsumeEndLineWithDot() ;
					m_eLinkage = new CLinkageSection(tokVar.getLine()) ;
					AddChild(m_eLinkage) ;
					if (!Parse(m_eLinkage))
					{
						return false ;
					}
				}
				else if (tokVar.GetKeyword() == CCobolKeywordList.FILE)
				{
					//ConsumeEndLineWithDot() ;
					m_eFile = new CFileSection(tokVar.getLine()) ;
					AddChild(m_eFile) ;
					if (!Parse(m_eFile))
					{
						return false ;
					}
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting sequence : " + tokVar.toString() + tokSection.toString() + tokDot.toString());
					return false ;
				}
				
			}
			else
			{
				bDone = true ; // this token is not handled by this function, go back to caller
			}
		}
		return true ;
	}
	
	protected boolean ParseProcedureDivision()
	{
		boolean b = false;
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.PROCEDURE)
		{
			return false ;
		}
		tok = GetNext();
		if (tok.GetKeyword() != CCobolKeywordList.DIVISION)
		{
			return false ; 
		}
		tok = GetNext();

		LevelKeywords levelKeywords = LevelKeywordStackManager.getAndPushNewLevelKeywords();
		levelKeywords.registerManagedKeyword(CCobolKeywordList.PROCEDURE);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.DIVISION);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.USING);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.WORKING_STORAGE);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.FILE);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.LINKAGE);

		boolean bLoop = true;
		while(bLoop)
		{		
			bLoop = false;
			
			b = internalDoParseProcedureDivision();
			CBaseToken tokEntry = GetCurrentToken();
			if(tokEntry != null && !LevelKeywordStackManager.isTokenManagedByAnyParents(tokEntry))
			{
				//m_Logger.error("ERROR Line " + tokEntry.getLine() + " : " + "Consuming token " + tokEntry.toString());
				GetNext();
				bLoop = true;
			}
		}
		
		LevelKeywordStackManager.popLevelKeywords();
		return b;
	}
	
	protected boolean internalDoParseProcedureDivision()
	{
		CBaseToken tok = GetCurrentToken() ;
		
		m_eProcDiv = new CProcedureDivision(tok.getLine()) ;
		if (tok.GetType() == CTokenType.DOT)
		{
			GetNext();
		}
		else if (tok.GetKeyword() == CCobolKeywordList.USING)
		{
			tok = GetNext() ;
			boolean bDone = false ;
			while (!bDone)
			{
				if (tok.GetType() == CTokenType.IDENTIFIER)
				{
					CIdentifier id = ReadIdentifier();
					m_eProcDiv.AddUsingRef(id) ;
					tok = GetCurrentToken() ;
				}
				else if (tok.GetType() == CTokenType.DOT)
				{
					bDone = true ;
					GetNext() ;
				}
				else if (tok.GetType() == CTokenType.COMMA)
				{
					tok = GetNext() ;
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Unexpecting token : "+tok.GetValue());
					return false ;
				}
			}
		}
		else
		{
			return false ;
		} 
		
		AddChild(m_eProcDiv) ;
		if (!Parse(m_eProcDiv))
		{
			return false ;
		}
		return true ;
	}

	public Element ExportCustom(Document rootdoc)
	{
		Element e = rootdoc.createElement("Program") ;
		e.setAttribute("Program_ID", m_ProgramID) ;
		e.setAttribute("Date_Written", m_DateWritten) ;
		e.setAttribute("Author", m_Author) ;
		return e ;
	}

	private String m_ProgramID = "" ;
	private String m_DateWritten = "" ;
	private String m_Author = "" ;
	private String m_Remark = "" ;
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (parent != null)
		{
			return null ;
		}
		
		CEntityClass container = factory.NewEntityClass(getLine(), m_ProgramID) ;
		
		CEntitySQLCursorSection sec = factory.NewEntitySQLCursorSection() ;
		sec.SetCursors(factory.m_ProgramCatalog.GetSQLCursorList()) ;
		container.AddChild(sec);
		return container ;
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoSemanticAnalysisForChildren(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	@Override
//	protected void DoSemanticAnalysisForChildren(CBaseLanguageEntity parent, CBaseEntityFactory factory)
//	{
//		ListIterator i = m_children.listIterator() ;
//		CLanguageElement le = null ;
//		try
//		{	
//			le = (CLanguageElement)i.next() ;
//		}
//		catch (NoSuchElementException e)
//		{
//		}
//
//		CBaseLanguageEntity eVariableSection = null ;
//		CBaseLanguageEntity eFileSection = null ;
//		while (le != null)
//		{
//			if (le == m_eFile)
//			{
//				eVariableSection = m_eWorking.DoSemanticAnalysisForVariables(null, factory) ;
//			}
//			CBaseLanguageEntity e = le.DoSemanticAnalysis(parent, factory) ;
//			if (le == m_eFile)
//			{
//				eFileSection = e ;
//			}
//			else if (e != null)
//			{
//				parent.AddChild(e) ;
//			}
//			if (le == m_eWorking)
//			{
//				if (eVariableSection != null)
//				{
//					Vector<CBaseLanguageEntity> arrvar = eVariableSection.GetListOfChildren() ;
//					for (int j=arrvar.size(); j>0; j--)
//					{
//						CBaseLanguageEntity evar = arrvar.get(j-1) ;
////						if  (evar.ignore())
////						{ // variable not referenced in the file section
//							e.AddChild(evar, null) ;
//							evar.SetParent(e);
////						}
////						else
////						{
////							evar.SetLine(eVariableSection.GetLine()) ;
////						}
//					}
//				}
//				if (eFileSection != null)
//				{
//					parent.AddChild(eFileSection) ;  // file section is moved after working section.
//				}
//			}
//			
//			try
//			{	
//				le = (CLanguageElement)i.next() ;
//			}
//			catch (NoSuchElementException ee)
//			{
//				le = null ;
//			}
//		}
//		m_bAnalysisDoneForChildren = true ;
//	}
	protected void DoSemanticAnalysisForChildren(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		ListIterator i = m_children.listIterator() ;
		CCobolElement le = null ;
		try
		{	
			le = (CCobolElement)i.next() ;
		}
		catch (NoSuchElementException e)
		{
		}

		CBaseLanguageEntity eVariableSection = null ;
		CBaseLanguageEntity eLinkage = null ;
		CBaseLanguageEntity eFileSection = null ;
		while (le != null)
		{
			if (le == m_eFile)
			{
				eVariableSection = m_eWorking.DoSemanticAnalysis(parent, factory);
				if (m_eLinkage != null)
					eLinkage = m_eLinkage.DoSemanticAnalysis(parent, factory) ;
				eFileSection = le.DoSemanticAnalysis(parent, factory) ;
				parent.AddChild(eFileSection) ;
			}
			else if (le == m_eWorking && eFileSection != null)
			{
				parent.AddChild(eVariableSection) ;
//				if (m_eLinkage == null)
//					parent.AddChild(eFileSection) ;
			}
			else if (le == m_eLinkage && eFileSection != null)
			{
				parent.AddChild(eLinkage) ;
//				parent.AddChild(eFileSection) ;
//				addDependencySection(parent) ;
			}
			else
			{
				CBaseLanguageEntity e = le.DoSemanticAnalysis(parent, factory) ;
				if (e != null)
				{
					parent.AddChild(e) ;
				}
			}

			
			try
			{	
				le = (CCobolElement)i.next() ;
			}
			catch (NoSuchElementException ee)
			{
				le = null ;
			}
		}
		m_bAnalysisDoneForChildren = true ;
	}


	public CEntityClass DoSemanticAnalysis(CJavaEntityFactory factory)
	{
		return (CEntityClass)DoSemanticAnalysis(null, factory);
	}
}
