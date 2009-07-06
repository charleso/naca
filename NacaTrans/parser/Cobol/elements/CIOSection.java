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

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CCommentContainer;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CIOSection extends CCommentContainer
{

	/**
	 * @param line
	 */
	public CIOSection(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		return parent;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tok = GetCurrentToken();
//			if (tok.GetType() == CTokenType.COMMENT)
//			{
//				ParseComment() ;
//			}
			if (tok.GetKeyword() == CCobolKeywordList.FILE_CONTROL)
			{
				tok = GetNext() ;
				if (tok.GetType() != CTokenType.DOT)
				{
					Transcoder.logError(getLine(), "Expecting DOT");
					return false ;
				}
				tok = GetNext();
				boolean bDone2 = false ;
				while (!bDone2)
				{
//					while (tok.GetType() == CTokenType.COMMENT)
//					{
//						ParseComment();
//						tok = GetCurrentToken();
//					}
					if (tok.GetKeyword() == CCobolKeywordList.SELECT)
					{
						CFileSelect fc = new CFileSelect(tok.getLine()); 
						AddChild(fc);
						if (!Parse(fc))
						{
							return false ;
						}
					}
					else
					{
						bDone2 = true ;
					}
					tok = GetCurrentToken() ;
				}
			}
			else 
			{
				bDone = true ;
			}
			
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eIO = root.createElement("IO") ;
		return eIO ;
	}

}
