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

import java.util.Vector;

import jlib.misc.NumberParser;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.FPac.CFPacElement;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CEntityFileBuffer;
import semantic.CEntityFileDescriptor;
import semantic.Verbs.CEntityOpenFile;
import utils.Transcoder;
import utils.FPacTranscoder.notifs.NotifRegisterUpdateFile;

/**
 * @author S. Charton
 * @version $Id$
 */
public class CFPacUpdateFile extends CFPacElement
{

	/**
	 * 
	 */
	public CFPacUpdateFile(int line)
	{
		super(line);
	}

	protected int m_ulFileId = 0;
	protected boolean m_bVariableFile = false; 
	private CTerminal m_CLR; 

	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken();
		if (tok.GetKeyword() == CFPacKeywordList.UPF)
			m_ulFileId = 0 ;
		else if (tok.GetKeyword().m_Name.startsWith("UPF"))
		{
			m_ulFileId = NumberParser.getAsInt(tok.GetKeyword().m_Name.substring(3)); 
		}
		else
		{
			Transcoder.logError(getLine(), "Unexpecting token : "+tok.toString()) ;
			return false ;
		}
		
		tok = GetNext() ;
		if  (tok.GetType() != CTokenType.EQUALS)
		{
			return false ;
		}
		
		tok = GetNext() ;
		if (tok.GetKeyword() == CFPacKeywordList.SQ)
		{
			m_bVariableFile = false ;
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.MINUS)
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CFPacKeywordList.VAR)
				{
					tok =GetNext() ;
					m_bVariableFile = true ;
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting token : "+tok.toString() + ";Expecting : SQ-VAR") ;
				}
			}
		}
		else
		{
			Transcoder.logError(getLine(), "Unexpecting token : "+tok.toString() + ";Expecting : SQ[-VAR]") ;
			return false ;
		}

		while (tok.GetType() == CTokenType.COMMA)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() == CFPacKeywordList.CLR)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.EQUALS) 
				{
					tok = GetNext() ;
					m_CLR = ReadTerminal() ;
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting token : "+tok.toString() + " after CLR") ;
					return false ;
				}
			}
			else if (tok.GetType() == CTokenType.NUMBER)
			{
				m_arrNumbers.add(tok.GetValue()) ;
				tok = GetNext();
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting token : "+tok.toString() + " after SQ") ;
				return false ;
			}
		}
		return true ;
		
	}
	
	protected Vector<String> m_arrNumbers = new Vector<String>() ;

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		String csDescName = "UPF"+m_ulFileId ;
		String csDescAlias = "U"+m_ulFileId ;
		if (m_ulFileId == 0)
		{
			csDescName = "UPF";
			csDescAlias = "U0" ;
		}
		CEntityFileDescriptor att = factory.NewEntityFileDescriptor(getLine(), csDescName) ;
		factory.m_ProgramCatalog.RegisterFileDescriptor(csDescAlias, att) ;

		att.setFileAccessType(CEntityOpenFile.OpenMode.INPUT_OUTPUT) ;
		att.setRecordSizeVariable(m_bVariableFile) ;
		
		CEntityFileBuffer buff = factory.NewEntityFileBuffer(csDescAlias, att) ;
		NotifRegisterUpdateFile notif = new NotifRegisterUpdateFile() ;
		notif.id = csDescAlias ;
		notif.fileBuffer = buff ;
		factory.m_ProgramCatalog.SendNotifRequest(notif) ;
		
		parent.AddChild(att) ;
		return att ;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element eAdd = root.createElement("UpdateFile") ;
		eAdd.setAttribute("FileId", String.valueOf(m_ulFileId)) ;
		eAdd.setAttribute("Var", String.valueOf(m_bVariableFile)) ;
		if (m_CLR != null)
		{
			Element eCLR = root.createElement("CLR") ;
			m_CLR.ExportTo(eCLR, root) ;
			eAdd.appendChild(eCLR) ;
		}
		for (String cs: m_arrNumbers)
		{
			Element e = root.createElement("Number") ;
			eAdd.appendChild(e) ;
			e.setAttribute("Value", cs) ;
		}
		return eAdd ;
	}
	
}
