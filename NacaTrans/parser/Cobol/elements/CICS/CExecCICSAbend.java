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
import semantic.CDataEntity;
import semantic.CICS.CEntityCICSAbend;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSAbend extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSAbend(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSAbend abend = factory.NewEntityCICSAbend(getLine());
		parent.AddChild(abend) ;
		if (m_ABCode != null)
		{
			CDataEntity e = m_ABCode.GetDataEntity(getLine(), factory) ;
			e.RegisterReadingAction(abend) ;
			abend.SetABCode(e);
		}
		return abend ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetValue().equals("ABEND"))
		{
			tok = GetNext();
		}
		
		if (tok.GetValue().equals("ABCODE"))
		{
			tok = GetNext();
			if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				tok = GetNext() ;
				m_ABCode = ReadTerminal();
				tok = GetCurrentToken() ;
				if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					tok = GetNext();
				}
			}
		}
		if (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS ABEND");
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
		Element e = root.createElement("ExecCICSAbend") ;
		if (m_ABCode != null)
		{
			Element eAB = root.createElement("ABCode");
			e.appendChild(eAB);
			m_ABCode.ExportTo(eAB, root);
		}
		return e;
	}

	protected CTerminal m_ABCode = null ;
}
