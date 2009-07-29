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
import semantic.CEntityFileDescriptor;
import semantic.Verbs.CEntityCloseFile;
import utils.Transcoder;
import utils.FPacTranscoder.notifs.NotifRegisterFileClose;

public class CFPacClose extends CFPacElement
{

	private CIdentifier m_CloseFile;

	public CFPacClose(int line)
	{
		super(line);
	}

	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.CLOSE)
		{
			tok = GetNext();
		}
		
		if (tok.GetType() == CTokenType.MINUS)
		{
			tok = GetNext() ;
		}
		else
		{
			Transcoder.logError(getLine(), "Expecting '-' after CLOSE") ;
			return false  ;
		}
		
		if (tok.GetType() == CTokenType.IDENTIFIER)
		{
			m_CloseFile = ReadIdentifier() ;
			if (m_CloseFile == null)
			{
				Transcoder.logError(getLine(), "Expecting identifier after 'CLOSE-'") ;
				return false  ;
			}
		}
		else
		{
			Transcoder.logError(getLine(), "Expecting identifier after 'CLOSE-'") ;
			return false  ;
		}
		return true;
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityFileDescriptor desc = factory.m_ProgramCatalog.getFileDescriptor(m_CloseFile.GetName()) ;
		NotifRegisterFileClose notif = new NotifRegisterFileClose() ;
		notif.m_FileDesc = desc ;
		factory.m_ProgramCatalog.SendNotifRequest(notif) ;
		
		CEntityCloseFile close = factory.NewEntityCloseFile(getLine()) ;
		close.setFileDescriptor(desc) ;
		parent.AddChild(close) ;
		return close ;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element eAdd = root.createElement("Close") ;
		Element e = root.createElement("File") ;
		m_CloseFile.ExportTo(e, root) ;
		eAdd.appendChild(e) ;
		return eAdd ;
	}

}
