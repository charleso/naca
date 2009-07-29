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
 * Created on 8 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.SQL;

import java.util.Vector;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.SQL.CEntitySQLCall;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLCall extends CBaseExecSQLAction
{

	/**
	 * @param l
	 */
	public CExecSQLCall(int l)
	{
		super(l);
	}
	public Element ExportCustom(Document root)
	{
		Element eCall = root.createElement("SQLCall");
		eCall.setAttribute("Reference", m_Reference.GetName());
		for (int i=0; i<m_arrParameters.size();i++)
		{
			CIdentifier id = m_arrParameters.get(i);
			Element e = root.createElement("Parameter");
			eCall.appendChild(e);
			id.ExportTo(e, root);
		}
		return eCall;
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySQLCall eCall = factory.NewEntitySQLCall(getLine()) ;
		CDataEntity prgRef = m_Reference.GetDataReference(getLine(), factory) ;
		eCall.setReference(prgRef) ;
		
		for (CIdentifier term : m_arrParameters)
		{
			CDataEntity param = term.GetDataReference(getLine(), factory) ;
			eCall.addParameter(param) ; 
		}
		
		parent.AddChild(eCall) ;
		return eCall;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.CALL)
		{
			return false ;
		}
		tok = GetNext() ;
		if (tok.GetType() == CTokenType.COLON)
		{
			tok = GetNext() ;
		}
		m_Reference = new CIdentifier(tok.GetValue()) ;
		
		tok = GetNext() ;
		if (tok.GetType() == CTokenType.LEFT_BRACKET)
		{
			GetNext();
			boolean bDone = false ;
			while (!bDone)
			{
				tok = GetCurrentToken() ;
				if (tok.GetType()== CTokenType.COLON)
				{
					tok = GetNext() ;
					CIdentifier id = ReadIdentifier();
					m_arrParameters.add(id);
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.COMMA)
					{
						GetNext();
					}
					else
					{
					}
				}
				else if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					GetNext();
					bDone = true ;
				}
				else 
				{
					bDone = true ;
				}
			}
		}
		return true ;
	}
	
	protected CIdentifier m_Reference = null ;
	protected Vector<CIdentifier> m_arrParameters = new Vector<CIdentifier>() ;

}
