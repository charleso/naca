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
import semantic.Verbs.CEntityOpenFile;
import utils.Transcoder;
import utils.FPacTranscoder.notifs.NotifRegisterFileOpen;

public class CFPacOpen extends CFPacElement
{

	public CFPacOpen(int line)
	{
		super(line);
	}

	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.OPEN)
		{
			tok = GetNext();
		}
		
		if (tok.GetType() == CTokenType.MINUS)
		{
			tok = GetNext() ;
		}
		else
		{
			Transcoder.logError(getLine(), "Expecting '-' after OPEN") ;
			return false  ;
		}
		
		if (tok.GetType() == CTokenType.IDENTIFIER)
		{
			m_OpenFile = ReadIdentifier() ;
			if (m_OpenFile == null)
			{
				Transcoder.logError(getLine(), "Expecting identifier after 'OPEN-'") ;
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
						tok.GetKeyword() == CFPacKeywordList.OPF9 ||
						tok.GetKeyword() == CFPacKeywordList.IPF ||
						tok.GetKeyword() == CFPacKeywordList.IPF1 ||
						tok.GetKeyword() == CFPacKeywordList.IPF2 ||
						tok.GetKeyword() == CFPacKeywordList.IPF3 ||
						tok.GetKeyword() == CFPacKeywordList.IPF4 ||
						tok.GetKeyword() == CFPacKeywordList.IPF5 ||
						tok.GetKeyword() == CFPacKeywordList.IPF6 ||
						tok.GetKeyword() == CFPacKeywordList.IPF7 ||
						tok.GetKeyword() == CFPacKeywordList.IPF8 ||
						tok.GetKeyword() == CFPacKeywordList.IPF9 )
		{
			m_OpenFile = new CIdentifier(tok.GetValue()) ;
			tok = GetNext() ;
		}
		else
		{
			Transcoder.logError(getLine(), "Expecting identifier after 'OPEN-'") ;
			return false  ;
		}
		return true;
	}
	
	protected CIdentifier m_OpenFile = null ;

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityFileDescriptor desc = factory.m_ProgramCatalog.getFileDescriptor(m_OpenFile.GetName()) ;
		CEntityOpenFile openfile = factory.NewEntityOpenFile(getLine()) ;
		openfile.setFileDescriptor(desc, desc.getAccessMode()) ;
		parent.AddChild(openfile) ;
		
		NotifRegisterFileOpen notif = new NotifRegisterFileOpen() ;
		notif.m_FileDesc = desc ;
		factory.m_ProgramCatalog.SendNotifRequest(notif) ;
		return null;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element eAdd = root.createElement("Open") ;
		Element e = root.createElement("File") ;
		m_OpenFile.ExportTo(e, root) ;
		eAdd.appendChild(e) ;
		return eAdd ;
	}

}
