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
package parser.Cobol.elements;

import jlib.xml.Tag;
import lexer.CBaseToken;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CLength extends CCobolElement
{
	public CLength(int line) 
	{
		super(line);
	}

	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.LENGTH)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.OF)
			{
				CGlobalEntityCounter.GetInstance().CountCobolVerb("LENGTH_OF") ;
				tok = GetNext() ;
				m_varLengthOf = ReadIdentifier();
				return true;
			}
		}
		Transcoder.logError(getLine(), "Expecting 'LENGTH' keyword") ;
		return false ;
	}
		
	protected Element ExportCustom(Document root)
	{
		Tag tagRoot = new Tag(root);
		Tag tagLengthOf = tagRoot.addTag("LengthOf");
		m_varLengthOf.ExportTo(tagLengthOf);
		
		Element e = tagLengthOf.getElement();
		return e;
	}
	
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
//		if (m_arrReference.size() == 1)
//		{
//			CEntityGoto e = factory.NewEntityGoto(getLine(), m_arrReference.elementAt(0), parent.getSectionContainer()) ;
//			parent.AddChild(e) ;
//			return e;
//		}
//		else
		{
			Transcoder.logError(getLine(), "No semantic analysis for GOTO ... DEPENDING") ;
			return null ;
		}
	}
	
	private CIdentifier m_varLengthOf = null;
}