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
import utils.FPacTranscoder.notifs.NotifSetDefaultOutputFile;

public class CFPacTo extends CFPacElement
{

	private CIdentifier m_idFile;

	public CFPacTo(int line)
	{
		super(line);
	}

	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.TO)
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
			else if (tok.GetKeyword() == CFPacKeywordList.OPF ||
							tok.GetKeyword() == CFPacKeywordList.OPF1 ||
							tok.GetKeyword() == CFPacKeywordList.OPF2 ||
							tok.GetKeyword() == CFPacKeywordList.OPF3 ||
							tok.GetKeyword() == CFPacKeywordList.OPF4 ||
							tok.GetKeyword() == CFPacKeywordList.OPF5 ||
							tok.GetKeyword() == CFPacKeywordList.OPF6 ||
							tok.GetKeyword() == CFPacKeywordList.OPF7 ||
							tok.GetKeyword() == CFPacKeywordList.OPF8 ||
							tok.GetKeyword() == CFPacKeywordList.OPF9)
			{
				m_idFile = new CIdentifier(tok.GetValue()) ;
				tok = GetNext() ;
			}
			else
			{
				Transcoder.logError(getLine(), "Expecting IDENTIFIER after TO- instead of "+tok.toString()) ;
				return false ;
			}
		}
		else
		{
			Transcoder.logError(getLine(), "Expecting '-' after TO instead of "+tok.toString()) ;
			return false ;
		}
		return true ;
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		NotifSetDefaultOutputFile notif = new NotifSetDefaultOutputFile() ;
		notif.fileRef = m_idFile.GetName() ;
		factory.m_ProgramCatalog.SendNotifRequest(notif) ;
		
		return null;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element eTo = root.createElement("To") ;
		Element e = root.createElement("File") ;
		eTo.appendChild(e) ;
		m_idFile.ExportTo(e, root) ;
		return eTo ;
	}

}
