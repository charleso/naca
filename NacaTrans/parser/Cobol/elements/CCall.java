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
 * Created on Jul 19, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import java.util.Vector;

import lexer.*;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import parser.expression.CIdentifierTerminal;
import parser.expression.CTerminal;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.Verbs.CEntityCallProgram;
import semantic.Verbs.CEntityRoutineEmulation;
import semantic.Verbs.CEntityRoutineEmulationCall;
import semantic.expression.CEntityLengthOf;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CCall extends CCobolElement
{
	private boolean m_bSsPrgNameByString = false;
	private boolean m_bSsPrgNameByReference = false;
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	 
	/**
	 * @param line
	 */
	public CCall(int line) {
		super(line);
	}
	public class CCallParameter
	{
		public CTerminal term = null ;
		public String method = "" ;
	}
	
	protected boolean DoParsing()
	{
		CBaseToken tokPerf = GetCurrentToken();
		if (tokPerf.GetType() != CTokenType.KEYWORD || tokPerf.GetKeyword() != CCobolKeywordList.CALL)
		{
			Transcoder.logError(getLine(), "Expecting 'CALL' keyword") ;
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tokPerf.GetKeyword().m_Name) ;

		// read sub-program name
		CBaseToken tokRef = GetNext();
		if (tokRef.GetType()== CTokenType.STRING)
		{
			m_bSsPrgNameByString = true;
			m_Reference = ReadTerminal();
		}
		else if(tokRef.GetType() == CTokenType.IDENTIFIER)
		{
			m_Reference = ReadTerminal();
			m_bSsPrgNameByReference = true;
		}
		else
		{
			Transcoder.logError(getLine(), "Expecting a STRING token as reference for CALL") ;
			return false ;
		}
		
		//read parameter sent to sub-soutine
		CBaseToken tokUsing = GetCurrentToken() ;
		if (tokUsing.GetKeyword() != CCobolKeywordList.USING)
		{
			return true ; // parsing is stoping here
		}
		GetNext() ;
		// read each variable
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetType()== CTokenType.IDENTIFIER)
			{
				CIdentifier id = ReadIdentifier();
				if (id == null)
				{
					Transcoder.logError(getLine(), "No identifier read as parameter for CALL") ;
					return false ;
				}
				CCallParameter p = new CCallParameter();
				p.term = new CIdentifierTerminal(id) ;
				p.method = "BY_REFERENCE" ;
				m_arrParams.addElement(p) ;
				
				// more IDs ?
				CBaseToken tokComma = GetCurrentToken() ;
				if (tokComma.GetType() == CTokenType.COMMA)
				{
					GetNext() ;
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.END_CALL)
			{
				GetNext();
				bDone = true ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.BY)
			{
				String csMethod = "BY" ;
				CBaseToken tokMethod = GetNext();
				if (tokMethod.GetKeyword()== CCobolKeywordList.REFERENCE)
				{
					csMethod += "_REFERENCE" ;
				}
				else if (tokMethod.GetKeyword()== CCobolKeywordList.CONTENT)
				{
					csMethod += "_CONTENT" ;
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting methode for CALL : " + tokMethod.GetValue()) ;
					return false ;
				}
				
				CBaseToken tokNext = GetNext() ;
				if (tokNext.GetType() == CTokenType.IDENTIFIER)
				{
					CIdentifier id = ReadIdentifier();
					if (id == null)
					{
						Transcoder.logError(getLine(), "No identifier read as parameter for CALL") ;
						return false ;
					}
					CCallParameter p = new CCallParameter();
					p.term = new CIdentifierTerminal(id) ;
					p.method = csMethod ;
					m_arrParams.addElement(p) ;
					// more IDs ?
					CBaseToken tokComma = GetCurrentToken() ;
					if (tokComma.GetType() == CTokenType.COMMA)
					{
						GetNext() ;
					}
				} 
				else if (tokNext.GetKeyword() == CCobolKeywordList.ADDRESS)
				{
					tokNext = GetNext() ;
					if (tokNext.GetKeyword() == CCobolKeywordList.OF)
					{
						tokNext = GetNext();
						if (tokNext.GetType() == CTokenType.IDENTIFIER)
						{
							csMethod += "_ADDRESS_OF" ;
							CIdentifier id = ReadIdentifier();
							if (id == null)
							{
								Transcoder.logError(getLine(), "No identifier read as parameter for CALL ADDRESS_OF") ;
								return false ;
							}
							CCallParameter p = new CCallParameter();
							p.term = new CIdentifierTerminal(id) ;
							p.method = csMethod ;
							m_arrParams.addElement(p) ;
							// more IDs ?
							CBaseToken tokComma = GetNext() ;
							if (tokComma.GetType() == CTokenType.COMMA)
							{
								GetNext() ;
							}
						}
						else
						{
							Transcoder.logError(getLine(), "Expecting an identifier as parameter for CALL") ;
							return false ;
						}
					}
				}
				else if (tokNext.GetKeyword() == CCobolKeywordList.LENGTH)
				{
					tokNext = GetNext() ;
					if (tokNext.GetKeyword() == CCobolKeywordList.OF)
					{
						tokNext = GetNext();
						if (tokNext.GetType() == CTokenType.IDENTIFIER)
						{
							csMethod += "_LENGTH_OF" ;
							CIdentifier id = ReadIdentifier();
							if (id == null)
							{
								Transcoder.logError(getLine(), "No identifier read as parameter for CALL LENGTH OF") ;
								return false ;
							}
							CCallParameter p = new CCallParameter();
							p.term = new CIdentifierTerminal(id) ;
							p.method = csMethod ;
							m_arrParams.addElement(p) ;
							// more IDs ?
							CBaseToken tokComma = GetCurrentToken() ;
							if (tokComma.GetType() == CTokenType.COMMA)
							{
								GetNext() ;
							}
						}
						else
						{
							Transcoder.logError(getLine(), "Expecting an identifier as parameter for CALL") ;
							return false ;
						}
					}
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting token : " + tokNext.GetValue()) ;
					return false ;
				}
			}
			else if (tok.GetType() == CTokenType.NUMBER || tok.GetType() == CTokenType.STRING)
			{
				tok = GetNext() ;
				CTerminal term = ReadTerminal() ; 
				CCallParameter p = new CCallParameter();
				p.term = term ;
				p.method = "VALUE" ;
				m_arrParams.addElement(p) ;
				// more IDs ?
				CBaseToken tokComma = GetNext() ;
				if (tokComma.GetType() == CTokenType.COMMA)
				{
					GetNext() ;
				}
			}
//			else if (tok.GetType() == CTokenType.KEYWORD)	// PJD Added: Maybe using a variable that has the same name as a reserved keyword
//			{
//				CIdentifier id = new CIdentifier(tok.GetValue()) ;
//				GetNext() ;
//				if (id == null)
//				{
//					Transcoder.logError(getLine(), "No identifier read as parameter for CALL") ;
//					return false ;
//				}
//				CCallParameter p = new CCallParameter();
//				p.term = new CIdentifierTerminal(id) ;
//				p.method = "BY_REFERENCE" ;
//				m_arrParams.addElement(p) ;
//				
//				// more IDs ?
//				CBaseToken tokComma = GetCurrentToken() ;
//				if (tokComma.GetType() == CTokenType.COMMA)
//				{
//					GetNext() ;
//				}
//			}
			else
			{
				bDone = true ;
			}
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("Call") ;
		Element eRef = root.createElement("Reference");		e.appendChild(eRef);
		m_Reference.ExportTo(eRef, root) ;
		for (int i = 0; i<m_arrParams.size(); i++)
		{
			CCallParameter p = m_arrParams.elementAt(i) ;
			Element ePar = root.createElement(p.method);
			e.appendChild(ePar) ;
			if(p == null)
			{
				int gg = 0;
			}
			if(p.term == null)
			{
				int gg = 0;
			}
				
			p.term.ExportTo(ePar, root) ;
		}
		return e;
	}
	
	protected CTerminal m_Reference = null ;
	protected Vector<CCallParameter> m_arrParams = new Vector<CCallParameter>();
	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CDataEntity eRef = m_Reference.GetDataEntity(getLine(), factory);
		boolean bChecked = false ;
		
		String prg = "" ;
		if (m_Reference.IsReference())
		{
			int n = eRef.GetNbWrittingActions() ;
			if (n>0)
			{// find the last writing action: this is the one that set the name of the called program
				CBaseActionEntity act = eRef.GetActionWriting(n-1);
				CDataEntity val = act.getValueAssigned() ;
				if (val != null)
				{
					int gg = 0;
//					act.IgnoreVariable(eRef) ;
//					eRef = val ;
//					prg = val.GetConstantValue() ;
				}
			}
		}
		
		if (!m_Reference.IsReference())
		{ // reference is a constant string : 'PRGM'
			prg = m_Reference.GetValue() ;
		}
		
		if (!prg.equals(""))
		{
			String prgname = parent.GetProgramName();
			CGlobalEntityCounter.GetInstance().RegisterSubProgram(prgname, prg) ;
			
			CEntityRoutineEmulation emul = factory.m_ProgramCatalog.getRoutineEmulation(prg) ;
			if (emul != null)
			{
				String csRequiredToolsLib = emul.getRequiredToolsLib();
				if(csRequiredToolsLib != null)
				{
					CBaseLanguageEntity topParent = parent.getTopParent();
					if(topParent != null)
						topParent.registerRequiredToolsLib(csRequiredToolsLib);
				}
				
				CEntityRoutineEmulationCall call = emul.NewCall(getLine(), factory) ;
				for (int i=0; i<m_arrParams.size();i++)
				{
					CCallParameter p = m_arrParams.get(i);
					CDataEntity eParam = p.term.GetDataEntity(getLine(), factory);
					if (p.method.equals("BY_CONTENT_LENGTH_OF"))
					{
						CEntityLengthOf lenof = factory.NewEntityLengthOf(eParam) ;
						call.AddParameter(lenof) ;
						lenof.RegisterReadingAction(call) ;
					}
					else
					{
						call.AddParameter(eParam) ;
						eParam.RegisterReadingAction(call);
					}
				}
				parent.AddChild(call);
				return call ;
			}
			else
			{
				boolean bWithDFHCommarea = false ;
				if(m_arrParams.size() > 0)	// PJD Added					
				{
					int nbParameters = m_arrParams.size() ;
					CCallParameter p = m_arrParams.get(0);	
					if (p.term.GetValue().equalsIgnoreCase("DFHCOMMAREA"))
					{
						bWithDFHCommarea = true ;
						nbParameters -- ;
					}
		
					if (!factory.m_ProgramCatalog.CheckProgramReference(prg, bWithDFHCommarea, nbParameters, true))
					{
						Transcoder.logError(getLine(), "Missing sub program : "+prg) ;
						CGlobalEntityCounter.GetInstance().RegisterMissingSubProgram(parent.GetProgramName(), prg) ;
						bChecked = false ;
					}
					else
					{
						//m_Logger.info("Referenced program found : "+prg) ;
						bChecked = true ;
					}
				}
				else	// PJD Added to support non calls without parameters
				{
					if (!factory.m_ProgramCatalog.CheckProgramReference(prg, bWithDFHCommarea, 0, true))
					{
						Transcoder.logError(getLine(), "Missing sub program : "+prg) ;
						CGlobalEntityCounter.GetInstance().RegisterMissingSubProgram(parent.GetProgramName(), prg) ;
						bChecked = false ;
					}
					else
					{
						//m_Logger.info("Referenced program found : "+prg) ;
						bChecked = true ;
					}
				}

				//				CCallParameter p = m_arrParams.get(0);
//				boolean bWithDFHCommarea = false ;
//				int nbParameters = m_arrParams.size() ;
//				if (p.term.GetValue().equalsIgnoreCase("DFHCOMMAREA"))
//				{
//					bWithDFHCommarea = true ;
//					nbParameters -- ;
//				}
//
//				if (!factory.m_ProgramCatalog.CheckProgramReference(prg, bWithDFHCommarea, nbParameters, true))
//				{
//					Transcoder.logError(getLine(), "Missing sub program : "+prg) ;
//					CGlobalEntityCounter.GetInstance().RegisterMissingSubProgram(parent.GetProgramName(), prg) ;
//					bChecked = false ;
//				}
//				else
//				{
//					//m_Logger.info("Referenced program found : "+prg) ;
//					bChecked = true ;
//				}
			}
		}
		else
		{
			//m_Logger.warn("Call use a variable to identify program") ;
		}
		CEntityCallProgram e = factory.NewEntityCallProgram(getLine(), eRef) ;
		e.setChecked(bChecked) ;
		parent.AddChild(e) ;
		for (int i=0; i<m_arrParams.size();i++)
		{
			CCallParameter p = m_arrParams.get(i);
			CDataEntity eParam = p.term.GetDataEntity(getLine(), factory);
			if (p.method.equals("BY_REFERENCE"))
			{
				e.SetParameterByRef(eParam) ;
				eParam.RegisterReadingAction(e);
//				eParam.RegisterWritingAction(e);
			}
			else if (p.method.equals("BY_CONTENT_LENGTH_OF"))
			{
				e.SetParameterLengthOf(eParam) ;
				eParam.RegisterReadingAction(e);
			}
			else if (p.method.equals("BY_CONTENT"))
			{
				e.SetParameterByContent(eParam) ;
				eParam.RegisterReadingAction(e);
			}
			else if (p.method.equals("BY_VALUE"))
			{
				e.SetParameterByValue(eParam) ;
				eParam.RegisterReadingAction(e);
			}
			else
			{
				e.SetParameterByRef(eParam) ;
				eParam.RegisterReadingAction(e);
				eParam.RegisterWritingAction(e);
			}
		}
		return e;
	}
	
	
}
