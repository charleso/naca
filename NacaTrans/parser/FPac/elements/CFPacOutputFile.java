/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package parser.FPac.elements;

import java.util.Vector;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.FPac.CFPacElement;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CEntityFileBuffer;
import semantic.CEntityFileDescriptor;
import semantic.Verbs.CEntityOpenFile;
import utils.Transcoder;
import utils.FPacTranscoder.notifs.NotifRegisterOutputFile;

public class CFPacOutputFile extends CFPacElement
{

	public CFPacOutputFile(int line)
	{
		super(line);
	}

	protected String m_csFileId = "";
	protected boolean m_bVariableFile = false; 
	private CTerminal m_CLR;
	private boolean m_bPFFile = false ;
	private boolean m_bCDFile = false ; 

	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken();
		if (tok.GetKeyword() == CFPacKeywordList.OPF)
			m_csFileId = "" ;
		else if (tok.GetKeyword().m_Name.startsWith("OPF"))
		{
			m_csFileId = tok.GetKeyword().m_Name.substring(3); 
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
		else if (tok.GetKeyword() == CFPacKeywordList.PR)
		{
			m_bPFFile  = true ;
			tok = GetNext() ;
		}
		else if (tok.GetKeyword() == CFPacKeywordList.CD)
		{
			m_bCDFile   = true ;
			tok = GetNext() ;
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
		if (m_bPFFile)
		{
			Transcoder.logError(getLine(), "PR file not supported yet") ;
		}
		if (m_bCDFile)
		{
			Transcoder.logError(getLine(), "CD file not supported yet") ;
		}
		String csDescName = "OPF"+m_csFileId ;
		String csDescAlias = "O"+m_csFileId ;
		if (m_csFileId.equals(""))
		{
			csDescName = "OPF";
			csDescAlias = "O0" ;
		}
		CEntityFileDescriptor att = factory.NewEntityFileDescriptor(getLine(), csDescName) ;
		factory.m_ProgramCatalog.RegisterFileDescriptor(csDescAlias, att) ;
		factory.m_ProgramCatalog.RegisterFileDescriptor(csDescName, att) ;

		att.setFileAccessType(CEntityOpenFile.OpenMode.OUTPUT) ;
		att.setRecordSizeVariable(m_bVariableFile) ;
		
		if (m_CLR != null)
		{
			CDataEntity e = m_CLR.GetDataEntity(getLine(), factory) ;
			if (e != null)
			{
				att.setOutputBufferInitialValue(e) ;
			}
		}
		
		CEntityFileBuffer buff = factory.NewEntityFileBuffer(csDescAlias, att) ;
		NotifRegisterOutputFile notif = new NotifRegisterOutputFile() ;
		notif.id = csDescAlias ;
		notif.fileBuffer = buff ;
		factory.m_ProgramCatalog.SendNotifRequest(notif) ;
		
		parent.AddChild(att) ;
		return att ;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element eAdd = root.createElement("OutputFile") ;
		eAdd.setAttribute("FileId", m_csFileId) ;
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
