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
package parser.FPac.elements;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.FPac.CFPacElement;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import utils.Transcoder;
import utils.FPacTranscoder.notifs.NotifSetDefaultInputFile;

/**
 * @author S. Charton
 * @version $Id$
 */
public class CFPacFrom extends CFPacElement
{

	private CIdentifier m_idFile;

	/**
	 * @param line
	 */
	public CFPacFrom(int line)
	{
		super(line);
	}

	/**
	 * @see parser.FPac.CFPacElement#DoParsing()
	 */
	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.FROM)
		{
			tok = GetNext() ;
		}
		
		if (tok.GetType() == CTokenType.MINUS)
		{
			tok =GetNext() ;
			if (tok.GetType() == CTokenType.IDENTIFIER)
			{
				m_idFile = new CIdentifier(tok.GetValue()) ;
				tok =GetNext() ;
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Expecting IDENTIFIER after FROM- instead of "+tok.toString()) ;
				return false ;
			}
		}
		else
		{
			Transcoder.logError(tok.getLine(), "Expecting '-' after FROM instead of "+tok.toString()) ;
			return false ;
		}
		return true ;
	}

	/**
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		NotifSetDefaultInputFile notif = new NotifSetDefaultInputFile() ;
		notif.fileRef = m_idFile.GetName() ;
		factory.m_ProgramCatalog.SendNotifRequest(notif) ;
		
		return null;
	}

	/**
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	@Override
	protected Element ExportCustom(Document root)
	{
		Element eTo = root.createElement("From") ;
		Element e = root.createElement("File") ;
		eTo.appendChild(e) ;
		m_idFile.ExportTo(e, root) ;
		return eTo ;
	}

}
