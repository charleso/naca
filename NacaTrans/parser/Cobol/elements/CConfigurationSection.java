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
import parser.CIdentifier;
import parser.Cobol.CCobolParser;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CEntityConfigurationSection;
import semantic.CEntityFileSelect;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CConfigurationSection extends CCommentContainer
{
	protected String m_csSourceComputer = "" ;
	protected String m_csObjectComputer = "" ;
	/**
	 * @param line
	 */
	public CConfigurationSection(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if(CCobolParser.ms_bCommaIsDecimalPoint)
		{
			CEntityConfigurationSection e = factory.NewEntityConfigurationSection() ;
			parent.AddChild(e) ;
			e.setDecimalPointIsComma();
			return e;
		}
		return parent ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tok = GetCurrentToken() ;
//			if (tok.GetType() == CTokenType.COMMENT)
//			{
//				ParseComment() ;
//			}
			if (tok.GetKeyword() == CCobolKeywordList.SOURCE_COMPUTER)
			{
				tok = GetNext() ;
				if (tok.GetType() != CTokenType.DOT)
				{
					Transcoder.logError(getLine(), "Expecting DOT");
					return false ;
				}
				tok = GetNext() ;
				if (tok.GetType() != CTokenType.IDENTIFIER)
				{
					Transcoder.logError(getLine(), "Expecting IDENTIFIER");
					return false ;
				}
				m_csSourceComputer = tok.GetValue();
				tok = GetNext();
				if (tok.GetType() != CTokenType.DOT)
				{
					Transcoder.logError(getLine(), "Expecting DOT");
					return false ;
				}
				GetNext() ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.OBJECT_COMPUTER)
			{
				tok = GetNext() ;
				if (tok.GetType() != CTokenType.DOT)
				{
					Transcoder.logError(getLine(), "Expecting DOT");
					return false ;
				}
				tok = GetNext() ;
				if (tok.GetType() != CTokenType.IDENTIFIER)
				{
					Transcoder.logError(getLine(), "Expecting IDENTIFIER");
					return false ;
				}
				m_csObjectComputer = tok.GetValue();
				tok = GetNext();
				if (tok.GetType() != CTokenType.DOT)
				{
					Transcoder.logError(getLine(), "Expecting DOT");
					return false ;
				}
				GetNext() ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.SPECIAL_NAMES)
			{
				tok = GetNext();
				if (tok.GetType() != CTokenType.DOT)
				{
					Transcoder.logError(getLine(), "Expecting DOT");
					return false ;
				}
				tok = GetNext() ;
				boolean bLoop = true; 
				while(bLoop)	// PJD Added lop to support multiple values
				{
					if (tok.GetType() == CTokenType.IDENTIFIER)
					{
						CIdentifier id = ReadIdentifier() ;
						tok = GetCurrentToken() ;
						if (tok.GetKeyword() == CCobolKeywordList.IS)
						{
							tok = GetNext();
							CIdentifier id2 = ReadIdentifier();	
							tok = GetCurrentToken() ;
							if (tok.GetType() == CTokenType.DOT)
							{
								GetNext();
								bLoop = false;
							}					
						}
					}
					else if (tok.GetKeyword() == CCobolKeywordList.DECIMAL_POINT)
					{
						tok = GetNext() ;
						if (tok.GetKeyword() == CCobolKeywordList.IS)
						{
							tok = GetNext();
							if (tok.GetKeyword() == CCobolKeywordList.COMMA)
							{
								tok = GetNext() ;
								CCobolParser.ms_bCommaIsDecimalPoint = true ;
							}
						}
						if (tok.GetType() == CTokenType.DOT)
						{
							bLoop = false;
							GetNext() ;
						}						
					}
					else
						bLoop = false;
				}

//				if (tok.GetType() == CTokenType.IDENTIFIER)
//				{
//					CIdentifier id = ReadIdentifier() ;
//					tok = GetCurrentToken() ;
//					if (tok.GetKeyword() == CCobolKeywordList.IS)
//					{
//						GetNext();
//						CIdentifier id2 = ReadIdentifier();	
//						tok = GetCurrentToken() ;
//						if (tok.GetType() == CTokenType.DOT)
//						{
//							GetNext();
//						}					
//					}
//				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.DECIMAL_POINT)
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.IS)
				{
					tok = GetNext();
					if (tok.GetKeyword() == CCobolKeywordList.COMMA)
					{
						tok = GetNext() ;
						CCobolParser.ms_bCommaIsDecimalPoint = true ;
					}
				}
				if (tok.GetType() != CTokenType.DOT)
				{
					Transcoder.logError(getLine(), "Expecting DOT");
					return false ;
				}
				GetNext() ;
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
		Element eConfig = root.createElement("Configuration") ;
		eConfig.setAttribute("SourceComputer", m_csSourceComputer) ;
		eConfig.setAttribute("ObjectComputer", m_csObjectComputer) ;
		return eConfig ;
	}

}
