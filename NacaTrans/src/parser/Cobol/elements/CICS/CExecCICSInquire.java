/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 7 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.CICS;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Cobol.CCobolElement;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CICS.CEntityCICSInquire;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSInquire extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSInquire(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis( CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSInquire inq = factory.NewEntityCICSInquire(getLine());
		parent.AddChild(inq) ;
		if (m_Transaction != null)
		{
			inq.m_Transaction = m_Transaction.GetDataEntity(getLine(), factory) ;
			inq.m_Transaction.RegisterReadingAction(inq) ;
		}
		if (m_Program != null)
		{
			inq.m_Program = m_Program.GetDataEntity(getLine(), factory);
			inq.m_Program.RegisterWritingAction(inq) ;
		}
		return inq ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.INQUIRE)
		{
			tok = GetNext();
		}
		
		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
			{
				bDone = true ;
			}
			else
			{
				String cs = tok.GetValue() ;
				tok = GetNext();
				CTerminal id = null ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					id = ReadTerminal() ;
					tok = GetCurrentToken();
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				} 
				if (cs.equalsIgnoreCase("TRANSACTION"))
				{
					m_Transaction = id ;
				}
				else if (cs.equalsIgnoreCase("PROGRAM"))
				{
					m_Program = id ;
				}
				else if (cs.equalsIgnoreCase("SYSTEM"))
				{
					// missing
				}
				else if (cs.equalsIgnoreCase("RELEASE"))
				{
					m_Release = id ;
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Unexpecting token : "+cs);
				}				
			}
		}
		
		if (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS INQUIRE");
			return false ;
		}
		StepNext();
		return true ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eInq = root.createElement("ExecCICSInquire") ;
		
		if (m_Transaction != null)
		{
			Element e = root.createElement("Transaction");
			eInq.appendChild(e);
			m_Transaction.ExportTo(e, root);
		}
		if (m_Program != null)
		{
			Element e = root.createElement("Program");
			eInq.appendChild(e);
			m_Program.ExportTo(e, root);
		}
		return eInq;
	}

	protected CTerminal m_Transaction = null ;
	protected CTerminal m_Release = null ;
	protected CTerminal m_Program = null ;
}
