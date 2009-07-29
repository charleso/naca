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
 * Created on 13 août 2004
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
import semantic.CEntityDataSection;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CFileSection extends CCommentContainer
{

	/**
	 * @param line
	 */
	public CFileSection(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityDataSection eFSection = factory.NewEntityDataSection(getLine(), "FileSection") ;
		parent.AddChild(eFSection) ;
		return eFSection;
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
			if (tok.GetType() == CTokenType.DOT)
			{
				GetNext();
			}
			else if (tok.GetKeyword() == CCobolKeywordList.SKIP1)
			{
				GetNext();
			}
			else if (tok.GetKeyword() == CCobolKeywordList.SKIP2)
			{
				GetNext();
			}
			else if (tok.GetKeyword() == CCobolKeywordList.SKIP3)
			{
				GetNext();
			}
			else if (tok.GetKeyword() == CCobolKeywordList.COPY)
			{
				Transcoder.logError("ERROR: Found COPY in File section; should be declared as to inline");
//				CCopyInWorking copy = new CCopyInWorking(tok.getLine());
//				if (!Parse(copy))
//				{
//					return false ;
//				}
//				AddChild(copy) ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.FD || tok.GetKeyword() == CCobolKeywordList.SD)
			{
				CFileDescriptor fd = new CFileDescriptor(tok.getLine());
				AddChild(fd);
				if (!Parse(fd))
				{
					return false ;
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
		Element e = root.createElement("FileSection") ;
		return e;
	}

}
