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
 * Created on Jul 28, 2004
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
import parser.expression.CExpression;
import semantic.CDataEntity;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.Verbs.CEntityAssign;
import semantic.Verbs.CEntityCalcul;
import semantic.expression.CBaseEntityExpression;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CCompute extends CCobolElement
{
	/**
	 * @param line
	 */
	public CCompute(int line) {
		super(line);
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tokComp = GetCurrentToken() ;
		if (tokComp.GetKeyword() != CCobolKeywordList.COMPUTE)
		{
			Transcoder.logError(getLine(), "Expecting 'COMPUTE' keyword") ;
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tokComp.GetKeyword().m_Name) ;
		
		CBaseToken tokId = GetNext();
		boolean bDone = false ;
		while (!bDone)
		{
			tokId = GetCurrentToken();
			if (tokId.GetType()!= CTokenType.IDENTIFIER)
			{
				Transcoder.logError(getLine(), "Expecting an identifier as detination of 'COMPUTE'") ;
				return false ;
			}
			CIdentifier idDestination = ReadIdentifier() ;
			if (idDestination == null)
			{
				Transcoder.logError(getLine(), "Identifier not read as detination of 'COMPUTE'") ;
				return false ;
			}
			
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.ROUNDED)
			{
				m_arrRoundedDestinations.add(idDestination);
				tok = GetNext(); 
			}
			else
			{
				m_arrDestinations.add(idDestination);
			}
			
			if (tok.GetType() != CTokenType.IDENTIFIER)
			{
				bDone = true ;
			} 
		}

		CBaseToken tokEquals = GetCurrentToken() ;
		if (tokEquals.GetType() != CTokenType.EQUALS)
		{
			Transcoder.logError(getLine(), "Expecting '=' in 'COMPUTE'") ;
			return false ;
		}
		
		tokEquals = GetNext();
		m_expr = ReadCalculExpression() ;
		if (m_expr == null)
		{
			Transcoder.logError(getLine(), "Can't read any Expression in 'COMPUTE'") ;
			return false ;
		}
		
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.ON)
		{
			tok = GetNext();
			if (tok.GetKeyword() == CCobolKeywordList.SIZE)
			{
				tok = GetNext();
				if (tok.GetKeyword() == CCobolKeywordList.ERROR)
				{
					GetNext();
					m_OnErrorBloc = new CGenericBloc("OnError", tok.getLine()) ;
					if (!Parse(m_OnErrorBloc))
					{
						return false ;
					}
				}
			}
		}
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.END_COMPUTE)
		{
			GetNext();
		}
		return true ;
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eComp = root.createElement("Compute") ;
		for (int i=0; i<m_arrDestinations.size();i++)
		{
			CIdentifier idDestination = m_arrDestinations.get(i) ;
			Element eDest = root.createElement("Destination");
			eComp.appendChild(eDest);
			idDestination.ExportTo(eDest, root) ;
		}
		for (int i=0; i<m_arrRoundedDestinations.size();i++)
		{
			CIdentifier idDestination = m_arrRoundedDestinations.get(i) ;
			Element eDest = root.createElement("RoundedDestination");
			eComp.appendChild(eDest);
			idDestination.ExportTo(eDest, root) ;
		}
		if (m_expr != null)
		{
			Element e = m_expr.Export(root);
			eComp.appendChild(e) ;
		}		
		if (m_OnErrorBloc != null)
		{
			Element e = m_OnErrorBloc.Export(root) ;
			eComp.appendChild(e);
		}
		return eComp ;
	}
	
	protected Vector<CIdentifier> m_arrDestinations = new Vector<CIdentifier>() ;
	protected Vector<CIdentifier> m_arrRoundedDestinations = new Vector<CIdentifier>() ;
	protected CExpression m_expr = null ;
	protected CBlocElement m_OnErrorBloc = null ;
	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (m_expr.IsReference())
		{
			CEntityAssign assgn = factory.NewEntityAssign(getLine()) ;
			CDataEntity val = m_expr.GetReference(factory) ;
			val.RegisterReadingAction(assgn) ;
			assgn.SetValue(val) ;
			for (int i=0; i<m_arrDestinations.size();i++)
			{
				CIdentifier idDestination = m_arrDestinations.get(i) ;
				CDataEntity dest = idDestination.GetDataReference(getLine(), factory) ;
				dest.RegisterWritingAction(assgn);
				assgn.AddRefTo(dest);
			}
			parent.AddChild(assgn) ;
			return assgn ;
		}
		else
		{
			CEntityCalcul eCalc = factory.NewEntityCalcul(getLine()) ;
			parent.AddChild(eCalc) ;
			for (int i=0; i<m_arrDestinations.size();i++)
			{
				CIdentifier idDestination = m_arrDestinations.get(i) ;
				CDataEntity dest = idDestination.GetDataReference(getLine(), factory) ;
				dest.RegisterWritingAction(eCalc);
				eCalc.AddDestination(dest);
			}
			for (int i=0; i<m_arrRoundedDestinations.size();i++)
			{
				CIdentifier idDestination = m_arrRoundedDestinations.get(i) ;
				CDataEntity dest = idDestination.GetDataReference(getLine(), factory) ;
				dest.RegisterWritingAction(eCalc);
				eCalc.AddRoundedDestination(dest);
			}
			
			CBaseEntityExpression eExpr = m_expr.AnalyseExpression(factory);
			eCalc.SetCalcul(eExpr) ;
			
			if (m_OnErrorBloc != null)
			{
				CBaseLanguageEntity eBloc = m_OnErrorBloc.DoSemanticAnalysis(eCalc, factory) ;
				eCalc.SetOnErrorBloc(eBloc);
			}
			return eCalc;
		}
	} 
}
