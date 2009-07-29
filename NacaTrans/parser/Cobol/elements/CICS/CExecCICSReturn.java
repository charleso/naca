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
/*
 * Created on 7 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.CICS;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CICS.CEntityCICSReturn;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSReturn extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSReturn(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSReturn ret = factory.NewEntityCICSReturn(getLine());
		parent.AddChild(ret);
		
		if (m_TransID != null)
		{
			CDataEntity TID ;
			boolean bChecked = false ;
			if (m_TransID.IsReference())
			{
				TID = m_TransID.GetDataEntity(getLine(), factory);
				TID.RegisterReadingAction(ret) ;
				factory.m_ProgramCatalog.RegisterVariableTransID(TID) ;
			}
			else
			{
				String transID = m_TransID.GetValue() ;
				String programID = factory.m_ProgramCatalog.GetProgramForTransaction(transID);
				if (programID.equals(""))
				{
					TID = m_TransID.GetDataEntity(getLine(), factory);
					TID.RegisterReadingAction(ret) ;
					factory.m_ProgramCatalog.RegisterVariableTransID(TID) ;
				}
				else
				{
					TID = factory.NewEntityString(programID) ;
					if (factory.m_ProgramCatalog.CheckProgramReference(programID, true, 0, false))
					{
						bChecked = true ;
					}
				}
			}
			CDataEntity comma = null;
			CDataEntity comlen = null ;
			if (m_CommArea != null)
			{
				comma = m_CommArea.GetDataReference(getLine(), factory);
				comma.RegisterReadingAction(ret) ;
				if (m_CommAreaLength != null)
				{
					comlen = m_CommAreaLength.GetDataEntity(getLine(), factory);
					comlen.RegisterReadingAction(ret) ;
				}
			}
			ret.SetTransID(TID, comma, comlen, bChecked);
		} 
		return ret;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.RETURN)
		{
			tok = GetNext() ;
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.TRANSID)
		{
			tok = GetNext() ;
			boolean bLoop = false;	// PJD Added loop
			int nNbLoops = 0;		// PJD Added			
			while(!bLoop && nNbLoops < 3)// PJD Added
			{	// PJD Added
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_TransID = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext() ;
					}
				}
				
				if (tok.GetKeyword() == CCobolKeywordList.COMMAREA)
				{
					tok = GetNext() ;
					if (tok.GetType() == CTokenType.LEFT_BRACKET)
					{
						tok = GetNext() ;
						m_CommArea = ReadIdentifier() ;
						tok = GetCurrentToken() ;
						if (tok.GetType() == CTokenType.RIGHT_BRACKET)
						{
							tok = GetNext() ;
						}
					}
				}
	
				if (tok.GetKeyword() == CCobolKeywordList.LENGTH)
				{
					tok = GetNext() ;
					if (tok.GetType() == CTokenType.LEFT_BRACKET)
					{
						tok = GetNext() ;
						m_CommAreaLength = ReadTerminal() ;
						tok = GetCurrentToken() ;
						if (tok.GetType() == CTokenType.RIGHT_BRACKET)
						{
							tok = GetNext() ;
						}
					}
				}
				if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)	// PJD Added
					bLoop = false;	// PJD Added
				nNbLoops++;		// PJD Added
			}					// PJD Added
		}
		
		if (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS RETURN");
			return false ;
		}
		StepNext() ;
		return true ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("ExecCICSReturn") ;
		if (m_TransID != null)
		{
			Element eTID = root.createElement("TransIS");
			e.appendChild(eTID) ;
			m_TransID.ExportTo(eTID, root) ;
		}
		if (m_CommArea != null)
		{
			Element eCA = root.createElement("CommArea");
			e.appendChild(eCA) ;
			m_CommArea.ExportTo(eCA, root) ;
			if (m_CommAreaLength != null)
			{
				Element el = root.createElement("Length");
				eCA.appendChild(el);
				m_CommAreaLength.ExportTo(el, root);
			}
		}
		return e;
	}

	protected CTerminal m_TransID = null ;
	protected CIdentifier m_CommArea = null ;
	protected CTerminal m_CommAreaLength = null ;
}
