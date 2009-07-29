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
/**
 * 
 */
package parser.Cobol.elements.SQL;

import lexer.CBaseToken;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import utils.Transcoder;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CExecSQLEndDeclareSection extends CBaseExecSQLAction
{
	public CExecSQLEndDeclareSection(int l)
	{
		super(l);
	}
	
	public Element ExportCustom(Document root)
	{
		Element e = root.createElement("SQLEndDeclareSection") ;
		return e ;
	}
	
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		// Nothing to generate
		return null;
	}
	
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetValue().equals("END"))	// EXEC SQL BEGIN DECLARE SECTION END-EXEC.
		{
			tok = GetNext();
			if (tok.GetKeyword() == CCobolKeywordList.DECLARE)
			{
				tok = GetNext();
				if (tok.GetKeyword() == CCobolKeywordList.SECTION)
				{
					tok = GetNext();
					if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
					{
						return true;
					}
				}				
			}
		}
		Transcoder.logError(tok.getLine(), "Could not lex token " + tok.GetDisplay());
		while(tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			tok = GetNext();
			Transcoder.logError(tok.getLine(), "Could not lex token " + tok.GetDisplay());
		}
		return false;
	}
}