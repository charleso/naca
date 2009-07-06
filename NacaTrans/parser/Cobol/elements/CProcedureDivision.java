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

import java.util.Vector;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CCommentContainer;
import parser.CIdentifier;
import semantic.CDataEntity;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.CEntityBloc;
import semantic.CEntityProcedureDivision;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CProcedureDivision extends CCommentContainer
{
	/**
	 * @param line
	 */
	public CProcedureDivision(int line) {
		super(line);
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CProcedureSection curSection = null ;
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tok = GetCurrentToken() ;
			if (tok == null)
			{
				return true ;
			}
			if (tok.GetType()==CTokenType.IDENTIFIER || tok.GetType() == CTokenType.NUMBER) 
			{	// maybe a label ?
				String csLabel = tok.GetValue();
				GetNext() ;
				
				CBaseToken tokSection = GetCurrentToken() ;
				if (tokSection.IsKeyword() && tokSection.GetKeyword() == CCobolKeywordList.SECTION)
				{	// maybe the starting of a section
					CBaseToken tokDot = GetNext() ;
					if (tokDot.GetType() != CTokenType.DOT)
					{
						Transcoder.logError(getLine(), "Expecting 'DOT'") ;
						return false ;
					}
					else
					{
						GetNext() ;
					}
					curSection = new CProcedureSection(csLabel, tok.getLine()) ;
					AddChild(curSection) ;
					if (!Parse(curSection))
					{
						return false ;
					}
				}
				else if (tokSection.GetType() == CTokenType.DOT)
				{
					CProcedure eProc = new CProcedure(csLabel, tokSection.getLine()) ;
					if (curSection == null)
					{
						AddChild(eProc) ;
					}
					else
					{
						curSection.AddProcedure(eProc) ;
					}
					if (!Parse(eProc))
					{
						return false ;
					}
				}
				else
				{
					Transcoder.logError(tokSection.getLine(), "Unexpecting token : " + tokSection.GetValue()) ;
					return false ;
				}
			}
			else if (tok.GetType() == CTokenType.KEYWORD)
			{
				m_ProcedureDivisionBloc = new CBaseProcedure(getLine());
				CBaseToken tok1 = GetCurrentToken() ;
				if (!Parse(m_ProcedureDivisionBloc))
				{
					return false ;
				}
				CBaseToken tok2 = GetCurrentToken() ;
				if (tok2 == tok1)
				{
					Transcoder.logError(tok1.getLine(), "Token not parsed : " + tok1.GetValue());
					GetNext() ;
				}
//				else if (tok2.GetType() == CTokenType.DOT)
//				{
//					GetNext() ;
//				}
			}
			else if (tok.GetType() == CTokenType.END_OF_BLOCK)
			{
				GetNext();
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Unexpecting token : " + tok.GetValue());
				GetNext();
			}
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eProc = root.createElement("ProcedureDivision") ;
		for (int i=0; i<m_arrUsingRef.size();i++)
		{
			CIdentifier id = m_arrUsingRef.get(i);
			Element euse = root.createElement("Using");
			eProc.appendChild(euse);
			id.ExportTo(euse, root);
		}
		if (m_ProcedureDivisionBloc != null)
		{
			Element e = m_ProcedureDivisionBloc.Export(root) ;
			eProc.appendChild(e);
		}
		return eProc;
	}
	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (factory.m_ProgramCatalog.isMissingIncludeStructure())
		{
			return null ;
		}
		CEntityProcedureDivision pro = factory.NewEntityProcedureDivision(getLine()) ;
		parent.AddChild(pro) ;
		for (int i=0; i<m_arrUsingRef.size();i++)
		{
			CIdentifier id = m_arrUsingRef.get(i);
			CDataEntity e = id.GetDataReference(getLine(), factory); 
			pro.AddCallParameter(e) ;
		}
		if (m_ProcedureDivisionBloc != null)
		{
			CEntityBloc e = (CEntityBloc)m_ProcedureDivisionBloc.DoSemanticAnalysis(pro, factory);
			pro.SetProcedureBloc(e) ;
		}

		return parent ;
	}
	
	protected Vector<CIdentifier> m_arrUsingRef = new Vector<CIdentifier>() ;
	public void AddUsingRef(CIdentifier id)
	{
		m_arrUsingRef.add(id);		
	} 
	
	protected CBaseProcedure m_ProcedureDivisionBloc = null ;
}
