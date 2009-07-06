/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 8 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import java.util.Vector;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CEntityFileDescriptor;
import semantic.Verbs.CEntitySort;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CSort extends CCobolElement
{

	public class CSortKey
	{
		public CIdentifier m_Id = null ;
		public boolean m_bAscending = true ; 
	}
	/**
	 * @param line
	 */
	public CSort(int line)
	{
		super(line);
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySort eSort = factory.NewEntitySort(getLine()) ;
		parent.AddChild(eSort) ;
		
		CEntityFileDescriptor fileDesc = factory.m_ProgramCatalog.getFileDescriptor(m_TempSortFile.GetName()) ;
		if (fileDesc != null)
		{
			eSort.setFileDesriptor(fileDesc) ;
		}
		else
		{
			Transcoder.logError(getLine(), "File descriptor not found : " + m_TempSortFile.GetName());
		}
		
		for (int i=0; i<m_arrKeys.size(); i++)
		{
			CSortKey key = m_arrKeys.get(i) ;
			CDataEntity eKey = key.m_Id.GetDataReference(getLine(), factory) ;
			eSort.AddKey(key.m_bAscending, eKey) ;
		}
		
		if (m_InputFile != null)
		{
			CEntityFileDescriptor eInput = factory.m_ProgramCatalog.getFileDescriptor(m_InputFile.GetName()) ;
			eSort.setInputFile(eInput) ;
		}
		if (m_InputProcedure != null)
		{
			//CEntityProcedure proc = factory.m_ProgramCatalog.GetProcedure(m_InputProcedure.GetName(), "") ;
			eSort.setInputProcedure(m_InputProcedure.GetName()) ;
		}
		if (m_OutputFile != null)
		{
			CEntityFileDescriptor eOutput = factory.m_ProgramCatalog.getFileDescriptor(m_OutputFile .GetName()) ;
			eSort.setOutputFile(eOutput) ;
		}
		if (m_OutputProcedure != null)
		{
			//CEntityProcedure proc = factory.m_ProgramCatalog.GetProcedure(m_OutputProcedure.GetName(), "") ;
			eSort.setOutputProcedure(m_OutputProcedure.GetName()) ;
		}
		
		return eSort ;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.SORT)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		
		tok = GetNext() ;
		m_TempSortFile = ReadIdentifier() ;
		
		tok = GetCurrentToken() ;
		boolean bAscending = true ;
		boolean bDone = false ;
		while (!bDone)
		{
			if (tok.GetKeyword() == CCobolKeywordList.ON)
			{
				tok = GetNext() ;
			}
			if (tok.GetKeyword() == CCobolKeywordList.ASCENDING)
			{
				bAscending = true ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.DESCENDING)
			{
				bAscending = false ;
			} 
			else
			{
				Transcoder.logError(tok.getLine(), "Missing sort order");
				return false ;
			}
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.KEY)
			{
				tok = GetNext() ;
			}

			while (tok.GetType() == CTokenType.IDENTIFIER)
			{
				CIdentifier id = ReadIdentifier();
				CSortKey k = new CSortKey() ;
				k.m_Id = id ;
				k.m_bAscending = bAscending ;
				m_arrKeys.add(k);
				
				tok = GetCurrentToken() ;
				if (tok.GetType() == CTokenType.COMMA)
				{
					tok = GetNext() ;
				}
			}
			
			if (tok.GetKeyword() != CCobolKeywordList.ON && tok.GetKeyword() != CCobolKeywordList.ASCENDING && tok.GetKeyword() != CCobolKeywordList.DESCENDING)
			{
				bDone = true ;
			} 
		}
		
		// Input
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.USING)
		{
			tok = GetNext() ;
			m_InputFile = ReadIdentifier();
		}
		else if (tok.GetKeyword() == CCobolKeywordList.INPUT)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.PROCEDURE)
			{
				tok = GetNext();
				if (tok.GetKeyword() == CCobolKeywordList.IS)
				{
					tok = GetNext();
				}
				m_InputProcedure = ReadIdentifier();
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Unexpecting situation");
				return false ;
			}
		}
		
		//Ouput
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.GIVING)
		{
			tok = GetNext() ;
			m_OutputFile = ReadIdentifier();
		}
		else if (tok.GetKeyword() == CCobolKeywordList.OUTPUT)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.PROCEDURE)
			{
				tok = GetNext();
				if (tok.GetKeyword() == CCobolKeywordList.IS)
				{
					tok = GetNext();
				}
				m_OutputProcedure = ReadIdentifier();
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Unexpecting situation");
				return false ;
			}
		}		
		
		return true;
	}
	protected Element ExportCustom(Document root)
	{
		String cs = "Sort" ;
		Element eSort = root.createElement(cs);
		
		Element eFile = root.createElement("File");
		m_TempSortFile.ExportTo(eFile, root);
		eSort.appendChild(eFile);
		
		for (int i =0; i<m_arrKeys.size(); i++)
		{
			CSortKey k = m_arrKeys.get(i);
			Element eK = root.createElement("Key");
			if (k.m_bAscending)
			{
				cs += "Ascending" ;
			}
			else
			{
				cs += "Descending" ;
			}
			eK.setAttribute("Sort", cs) ;
			k.m_Id.ExportTo(eK, root);
			eSort.appendChild(eK);
		}
		
		if (m_InputFile != null)
		{
			Element e = root.createElement("InputFile");
			eSort.appendChild(e);
			m_InputFile.ExportTo(e, root);
		}
		if (m_InputProcedure != null)
		{
			Element e = root.createElement("InputProcedure");
			eSort.appendChild(e);
			m_InputProcedure.ExportTo(e, root);
		}
		if (m_OutputFile != null)
		{
			Element e = root.createElement("OutputFile");
			eSort.appendChild(e);
			m_OutputFile.ExportTo(e, root);
		}
		if (m_OutputProcedure != null)
		{
			Element e = root.createElement("OutputProcedure");
			eSort.appendChild(e);
			m_OutputProcedure.ExportTo(e, root);
		}
		return eSort;
	}

	protected CIdentifier m_TempSortFile = null ;
//	protected boolean m_bAscending = false ;
	protected Vector<CSortKey> m_arrKeys = new Vector<CSortKey>() ; 
	protected CIdentifier m_InputFile = null ;
	protected CIdentifier m_OutputFile = null ;
	protected CIdentifier m_InputProcedure = null ;
	protected CIdentifier m_OutputProcedure = null ;
}
