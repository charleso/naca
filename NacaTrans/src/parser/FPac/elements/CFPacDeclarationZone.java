/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package parser.FPac.elements;

import java.util.Collection;
import java.util.LinkedList;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.FPac.CFPacElement;
import parser.expression.CConstantTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CEntityDataSection;
import utils.Transcoder;

public class CFPacDeclarationZone extends CFPacElement
{

	public CFPacDeclarationZone(int line)
	{
		super(line);
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityDataSection data = factory.NewEntityDataSection(getLine(), "DeclarationSection") ;
		
		for (CConstantTerminal c : m_arrParams)
		{
			// TODO do semantic analysis
		}
		for (CFPacInputFile f : m_arrInputFiles)
		{
			f.DoSemanticAnalysis(data, factory) ;
		}
		for (CFPacOutputFile f : m_arrOutputFiles)
		{
			f.DoSemanticAnalysis(data, factory) ;
		}
		for (CFPacUpdateFile f : m_arrUpdateFiles)
		{
			f.DoSemanticAnalysis(data, factory) ;
		}
		
		parent.AddChild(data) ;
		return data ;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element eAdd = root.createElement("Declarations") ;
		for (CConstantTerminal c : m_arrParams)
		{
			Element e = root.createElement("Param") ;
			c.ExportTo(e, root) ;
			eAdd.appendChild(e) ;
		}
		for (CFPacInputFile f : m_arrInputFiles)
		{
			Element e = f.Export(root) ;
			eAdd.appendChild(e) ;
		}
		for (CFPacOutputFile f : m_arrOutputFiles)
		{
			Element e = f.Export(root) ;
			eAdd.appendChild(e) ;
		}
		for (CFPacUpdateFile f : m_arrUpdateFiles)
		{
			Element e = f.Export(root) ;
			eAdd.appendChild(e) ;
		}
		return eAdd ;
	}
	
	protected boolean DoParsing() 
	{
		boolean bParsed = true ;
		while (bParsed)
		{
			bParsed = CustomParsing() ;
		}
		return true ;
	}

	private boolean CustomParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == null)
			return false ;
		CFPacElement el = null ;
		if (tok.GetKeyword().m_Name.startsWith("IPF"))
		{
			CFPacInputFile file = new CFPacInputFile(tok.getLine());
			el = file ;
			m_arrInputFiles.add(file) ;
		}
		else if (tok.GetKeyword().m_Name.startsWith("OPF"))
		{
			CFPacOutputFile file = new CFPacOutputFile(tok.getLine());
			m_arrOutputFiles.add(file) ;
			el = file ;
		}
		else if (tok.GetKeyword().m_Name.startsWith("UPF"))
		{
			CFPacUpdateFile file = new CFPacUpdateFile(tok.getLine());
			m_arrUpdateFiles.add(file) ;
			el = file ;
		}
		else if(tok.GetKeyword() == CFPacKeywordList.PARM)
		{
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.EQUALS)
			{
				tok= GetNext() ;
				boolean bDone = false ;
				while (!bDone) 
				{
					if (tok.GetType() == CTokenType.CONSTANT)
					{
						CConstantTerminal term = new CConstantTerminal(tok.GetValue()) ;
						m_arrParams.add(term) ;
					}
					else
					{
						Transcoder.logError(tok.getLine(), "Expecting CONSTANT after PARM") ;
						return false ;
					}
					
					tok = GetNext() ;
					if (tok.GetType() == CTokenType.COMMA)
					{
						tok = GetNext() ;
					}
					else
					{
						bDone = true ;
					}
				}
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Expecting '=' after PARM") ;
				return false ;
			}
		}
		else 
			return false ;

		if (el != null)
		{
			if (!Parse(el))
				return false ;
		}
		return true ;
	}

	protected Collection<CFPacInputFile> m_arrInputFiles = new LinkedList<CFPacInputFile>() ;
	protected Collection<CFPacOutputFile> m_arrOutputFiles = new LinkedList<CFPacOutputFile>() ;
	protected Collection<CFPacUpdateFile> m_arrUpdateFiles = new LinkedList<CFPacUpdateFile>() ;
	protected Collection<CConstantTerminal> m_arrParams = new LinkedList<CConstantTerminal>() ;
}
