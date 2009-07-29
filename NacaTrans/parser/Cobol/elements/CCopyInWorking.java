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
 * Created on Jul 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import jlib.xml.Tag;

import lexer.CBaseToken;
import lexer.CReservedKeyword;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.utils.StringVector;


import parser.Cobol.CCobolElement;
import semantic.CBaseExternalEntity;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.CEntityInline;
import utils.CGlobalEntityCounter;
import utils.CRulesManager;
import utils.Transcoder;
import utils.modificationsReporter.Reporter;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CCopyInWorking extends CCobolElement
{
	private CCopyDeepReplacing m_copyDeepReplacing = null;
	
	/**
	 * @param line
	 */
	public CCopyInWorking(int line)
	{
		super(line);
		Reporter.Add("Modif_PJ", "CCopyInWorking");
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	

	private boolean eatToken(CTokenType tokenType)
	{
		CBaseToken tok = GetCurrentToken();
		if((tok.GetType() != tokenType))
		{
			GetNext();
			return false;
		}
		GetNext();
		return true;
	}
	
	private boolean eatDoubleEquals()
	{
		if(!eatToken(CTokenType.EQUALS))	// 1st =
			return false;
		if(!eatToken(CTokenType.EQUALS))	// 2nd =
			return false;
		
		return true;
	}
	
	// Handle ==(COM)== BY ==FMAJP==
	private boolean TrySpecialPGCopyReplacing()
	{
		if(!eatDoubleEquals())
			return false;
		
		CBaseToken tok = GetCurrentToken();
		String csMarkerLeft = tok.GetType().GetSourceValue();
		if(csMarkerLeft.length() != 1)	// 1 single marker char 
			return false;		
		tok = GetNext();
		
		String csSourceValue = tok.GetValue();
		tok = GetNext();

		String csMarkerRight = tok.GetType().GetSourceValue();
		if(csMarkerRight.length() != 1)	// 1 single marker char 
			return false;
		tok = GetNext();
		
		if(!eatDoubleEquals())
			return false;
		
		tok = GetCurrentToken();
		if(tok.GetKeyword() != CCobolKeywordList.BY)	// BY
			return false;
		
		tok = GetNext();
		if(!eatDoubleEquals())
			return false;
		tok = GetCurrentToken();
		
		String csDestinationValue = tok.GetValue();
		tok = GetNext();
		if(!eatDoubleEquals())
			return false;
		
		tok = GetCurrentToken();
		if (tok.GetType() == CTokenType.DOT)
		{
			GetNext();
		}
		
		//m_arrReplace.addElement(csSourceValue);
		//m_arrReplaceBy.addElement(csDestinationValue);
		
		tok = GetCurrentToken();
		
		m_copyDeepReplacing = new CCopyDeepReplacing(csSourceValue, csDestinationValue, csMarkerLeft, csMarkerRight);
		
		return true;
	}
	
	protected boolean DoParsing()
	{
		CBaseToken tokCopy = GetCurrentToken() ;
		if (tokCopy.GetType() != CTokenType.KEYWORD && tokCopy.GetKeyword() != CCobolKeywordList.COPY)
		{
			Transcoder.logError(getLine(), "Expecting 'COPY' keyword") ;
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tokCopy.GetKeyword().m_Name) ;
		CBaseToken tokRef = GetNext();
		if (tokRef.GetType() != CTokenType.IDENTIFIER  && tokRef.GetType() != CTokenType.STRING)	// Support for String Quoted copy (Thanks Charles O'Farrell) 
		{
			Transcoder.logError(getLine(), "Expecting an identifier after COPY, instead of : " + tokRef.toString()) ;
			return false ;
		} 
		m_csCopyReference = tokRef.GetValue() ;
		Transcoder.pushTranscodedUnit(m_csCopyReference, "");
		CBaseToken tokSuppr = GetNext() ;
		if (tokSuppr.GetKeyword() == CCobolKeywordList.SUPPRESS)
		{
			m_bSuppress = true ;
			tokSuppr = GetNext() ;
		} 
		if (tokSuppr.GetKeyword() == CCobolKeywordList.REPLACING)
		{
			CBaseToken tok = GetNext();
			if(tok.GetType() == CTokenType.EQUALS)// Special case PG: COPY F0CREDRP REPLACING ==(COM)== BY ==FMAJP==
			{
				TrySpecialPGCopyReplacing();
			}
			else	// Normal case
			{
				while (tok.GetType() == CTokenType.NUMBER || tok.GetType() == CTokenType.STRING || tok.GetType() == CTokenType.IDENTIFIER)
				{
					String csReplace = tok.GetValue();
					m_arrReplace.addElement(csReplace);
					tok = GetNext();
					if (tok.GetKeyword() != CCobolKeywordList.BY)
					{
						Transcoder.logError(getLine(), "Expecting 'BY' keyword") ;
						Transcoder.popTranscodedUnit();
						return false ;
					}
					tok = GetNext();
					String csReplaceBy = tok.GetValue();
					m_arrReplaceBy.addElement(csReplaceBy);
					tok = GetNext();
				}
			}
		} 
		tokSuppr = GetCurrentToken() ;
		if (tokSuppr.GetType() == CTokenType.DOT)
		{
			GetNext();
		}
		
		//CBaseToken tokCurrent = GetCurrentToken() ;	// Just debug

		// PJD 03/04/2009: next line could be commented:
		// If commented, then it works for
		//COPY TOTO.
		//01 var PIC X(10)	
		// but not for 
		//COPY TOTO.
		//77 var PIC X(10): Pic X Is included in the copy, in the output XML data, and it's not set on the program working
		// We can keep it uncommented if at line 212: if (level > 1 && level <= 49) // PJD Pb Copy indent and following levels
		//boolean b = ParseContent();					
		//tokCurrent = GetCurrentToken() ;			// Just debug
		
		Transcoder.popTranscodedUnit();
		//return b;	// See comment below
		return true;
	}

	protected boolean ParseContent()
	{
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tokEntry = GetCurrentToken();
			if (tokEntry.GetType()==CTokenType.NUMBER)
			{
				int level = tokEntry.GetIntValue();
				if (level > 1 && level <= 49) // PJD 03/04/2009 Pb Copy indent and following levels; it was if (level > 1)
				{
					CCobolElement eEntry = new CWorkingEntry(tokEntry.getLine()) ;
					if (!Parse(eEntry))
					{
						Transcoder.logError(getLine(), "Error while parsing wotking entry") ;
						return false ;
					}
					AddChild(eEntry) ;
				}
				else
				{
					bDone = true ; // this entry is a top-level entry
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
		Element eCopy = root.createElement("Copy") ;
		eCopy.setAttribute("Reference", m_csCopyReference);
		if(m_copyDeepReplacing != null)
		{
			Element eCopyReplacing = m_copyDeepReplacing.getAsElement(root);
			eCopy.appendChild(eCopyReplacing);
		}
		if (m_bSuppress)
		{
			eCopy.setAttribute("Suppress", "true") ;
		}
		for (int i=0; i<m_arrReplace.size(); i++)
		{
			Element e = root.createElement("Replacing");
			eCopy.appendChild(e);
			e.setAttribute("Replace", m_arrReplace.elementAt(i));
			e.setAttribute("ReplaceBy", m_arrReplaceBy.elementAt(i));
		}			
		return eCopy;
	}
	
	protected String m_csCopyReference = "" ;
	protected StringVector m_arrReplace = new StringVector() ;
	protected StringVector m_arrReplaceBy = new StringVector() ;
	protected boolean m_bSuppress = false ;
	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if(m_copyDeepReplacing != null)	// We have a deep copy replacing
		{
			String csNewCopyReference = m_copyDeepReplacing.GetCopyReference(m_csCopyReference);
			Transcoder.logWarn(getLine(), "COPY DEEP REPLACING found; Generating source Cobol COPY file " + csNewCopyReference + " from " + m_csCopyReference);
			CGlobalEntityCounter.GetInstance().RegisterDeepCopy(parent.GetProgramName(), m_csCopyReference, csNewCopyReference) ;
			factory.m_ProgramCatalog.GenerateDeepCopyCobolFile(m_csCopyReference, m_copyDeepReplacing);
			m_csCopyReference = csNewCopyReference;
		}
		else
			CGlobalEntityCounter.GetInstance().RegisterCopy(parent.GetProgramName(), m_csCopyReference) ;
		CBaseExternalEntity e = factory.m_ProgramCatalog.GetExternalDataReference(m_csCopyReference, factory) ;
		if (e == null)
		{			
			CGlobalEntityCounter.GetInstance().RegisterMissingCopy(parent.GetProgramName(), m_csCopyReference) ;
			return null ;
		}
		boolean  bOtherData = factory.m_ProgramCatalog.IsExistingDataEntity(e.GetName(), "");
		
		if (m_arrReplace.size()>0 && m_arrReplaceBy.size()>0)
		{
			String cs1 = m_arrReplace.elementAt(0);
			String cs2 = m_arrReplaceBy.elementAt(0);
			int n1 = Integer.parseInt(cs1) ;
			int n2 = Integer.parseInt(cs2);
			if (n1 > 0 && n2 > 0)
			{
				e.ReplaceLevel(n1, n2) ;
			}
		}

		CBaseLanguageEntity ent = parent.FindLastEntityAvailableForLevel(e.GetInternalLevel());
		if (ent == null)
		{
			ent = parent ;	
		}

		CEntityInline eil = factory.NewEntityInline(getLine(), e) ;
		e.InitDependences(factory) ; 
		ent.AddChild(eil) ;
		e.SetParent(eil);

		ListIterator i = m_children.listIterator() ;
		CCobolElement le = null ;
		try
		{	
			le = (CCobolElement)i.next() ;
		}
		catch (NoSuchElementException ex)
		{
			//ex.printStackTrace();
		}
		while (le != null)
		{
			CBaseLanguageEntity eSub = le.DoSemanticAnalysis(null, factory) ;
			int level = eSub.GetInternalLevel() ;
			CBaseLanguageEntity newParent = parent.FindLastEntityAvailableForLevel(level);
			if (newParent != null)
			{
				//eil.ReplaceParentForChild(eSub, newParent);
				newParent.AddChild(eSub) ;
			}
			else
			{
				eil.AddChild(eSub) ;
			}
			try
			{	
				le = (CCobolElement)i.next() ;
			}
			catch (NoSuchElementException exp)
			{
				//exp.printStackTrace();
				le = null ;
			}
		}
		m_bAnalysisDoneForChildren = true ;
		
		return eil ;
	}
}
