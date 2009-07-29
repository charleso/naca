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
import semantic.CEntityFileBuffer;
import semantic.CEntityFileDescriptor;
import semantic.Verbs.CEntityReadFile;
import utils.Transcoder;
import utils.FPacTranscoder.OperandDescription;
import utils.FPacTranscoder.notifs.NotifSetDefaultInputFile;

public class CFPacGet extends CFPacElement
{

	private CIdentifier m_GetFile;
	private CFPacCodeBloc m_AtEofBloc ;

	public CFPacGet(int line)
	{
		super(line);
	}

	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.GET)
		{
			tok = GetNext();
		}
		
		if (tok.GetType() == CTokenType.MINUS)
		{
			tok = GetNext() ;
		}
		else
		{
			return true ;
		}
		
		if (tok.GetType() == CTokenType.IDENTIFIER)
		{
			m_GetFile = ReadIdentifier() ;
			if (m_GetFile == null)
			{
				Transcoder.logError(getLine(), "Expecting identifier after 'GET-'") ;
				return false  ;
			}
		}
		else
		{
			Transcoder.logError(getLine(), "Expecting identifier after 'GET-'") ;
			return false  ;
		}
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.AT)
		{
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.MINUS)
			{
				tok = GetNext() ;
				if (tok.GetKeyword()  == CFPacKeywordList.EOF)
				{
					tok = GetNext() ;
					m_AtEofBloc = new CFPacCodeBloc(tok.getLine(), "") ;
					if (!Parse(m_AtEofBloc))
					{
						return false ;
					}
				}								
				tok = GetCurrentToken() ;
				if (tok.GetKeyword() == CFPacKeywordList.ATEND)
				{
					tok = GetNext() ;
				}
			}
				
		}
		return true;
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityFileDescriptor desc = null;
		if (m_GetFile != null)
		{
			desc = factory.m_ProgramCatalog.getFileDescriptor(m_GetFile.GetName()) ;
			NotifSetDefaultInputFile notif = new NotifSetDefaultInputFile() ;
			notif.fileRef = m_GetFile.GetName() ;
			factory.m_ProgramCatalog.SendNotifRequest(notif) ;
		}
		else
		{
			CEntityFileBuffer buf = OperandDescription.getDefaultInputFileBuffer(factory.m_ProgramCatalog) ;
			if (buf != null)
			{
				desc = buf.GetFileDescriptor() ;
			}
		}
		CEntityReadFile readfile = factory.NewEntityReadFile(getLine());
		readfile.setFileDescriptor(desc, null) ;
		
		if (m_AtEofBloc != null)
		{
			CBaseLanguageEntity bloc = m_AtEofBloc.DoSemanticAnalysis(null, factory) ;
			readfile.SetAtEndBloc(bloc) ;
		}
		
//		NotifRegisterFileGet notif = new NotifRegisterFileGet() ;
//		notif.readFile = readfile;
//		factory.m_ProgramCatalog.SendNotifRequest(notif) ;
		
		parent.AddChild(readfile) ;
		return readfile ;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element eAdd = root.createElement("Get") ;
		if (m_GetFile != null)
		{
			Element e = root.createElement("File") ;
			m_GetFile.ExportTo(e, root) ;
			eAdd.appendChild(e) ;
		}
		return eAdd ;
	}

}
