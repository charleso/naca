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
import semantic.Verbs.CEntityWriteFile;
import utils.Transcoder;
import utils.FPacTranscoder.notifs.NotifGetDefaultOutputFile;

public class CFPacPut extends CFPacElement
{

	private CIdentifier m_PutFile;

	public CFPacPut(int line)
	{
		super(line);
	}

	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.PUT)
		{
			tok = GetNext();
		}
		
		if (tok.GetType() == CTokenType.MINUS)
		{
			tok = GetNext() ;
		}
		else
		{
			return true;
		}
		
		if (tok.GetType() == CTokenType.IDENTIFIER)
		{
			m_PutFile = ReadIdentifier() ;
			if (m_PutFile == null)
			{
				Transcoder.logError(getLine(), "Expecting identifier after 'PUT-'") ;
				return false  ;
			}
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
			m_PutFile = new CIdentifier(tok.GetValue()) ;
			tok = GetNext() ;
		}
		else
		{
			Transcoder.logError(getLine(), "Expecting identifier after 'PUT-'") ;
			return false  ;
		}
		return true;
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityWriteFile writefile = factory.NewEntityWriteFile(getLine());
		CEntityFileDescriptor desc = null ;
		if (m_PutFile == null)
		{
			NotifGetDefaultOutputFile notif = new NotifGetDefaultOutputFile() ;
			factory.m_ProgramCatalog.SendNotifRequest(notif) ;
			if (notif.fileBuffer != null)
			{
				desc = notif.fileBuffer.GetFileDescriptor() ;
			}
		}
		else
		{
			desc = factory.m_ProgramCatalog.getFileDescriptor(m_PutFile.GetName()) ;
		}
		if (desc == null)
		{
			Transcoder.logError(getLine(), "Expecting file identifier for 'PUT-'") ;
			return null ;
		}
		writefile.setFileDescriptor(desc, null) ;
		parent.AddChild(writefile);
		return writefile ;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element eAdd = root.createElement("Put") ;
		if (m_PutFile != null)
		{
			Element e = root.createElement("File") ;
			m_PutFile.ExportTo(e, root) ;
			eAdd.appendChild(e) ;
		}
		return eAdd ;
	}

}
