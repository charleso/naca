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

import java.util.Vector;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.utils.StringVector;

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CICS.CEntityCICSHandleAID;
import semantic.CICS.CEntityCICSHandleCondition;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSHandle extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSHandle(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (m_arrConditions != null && m_arrConditions.size()>0)
		{
			CEntityCICSHandleCondition handle = factory.NewEntityCICSHandleCondition(getLine());
			parent.AddChild(handle);
			for (int i=0; i<m_arrConditions.size();i++)
			{
				String cond = m_arrConditions.elementAt(i);
				CIdentifier id = m_arrLabels.get(i);
				if (id != null)
				{
					handle.HandleCondition(cond, id.GetName());
				}
				else
				{
					handle.UnhandleCondition(cond);
				} 
			}
			return handle;
		}
		else if (m_arrAID.size()>0)
		{
			CEntityCICSHandleAID handle = factory.NewEntityCICSHandleAID(getLine());
			parent.AddChild(handle);
			for (int i=0; i<m_arrConditions.size();i++)
			{
				String cond = m_arrConditions.elementAt(i);
				CIdentifier id = m_arrLabels.get(i);
				if (id != null)
				{
					handle.HandleAID(cond, id.GetName());
				}
				else
				{
					handle.UnhandleAID(cond);
				} 
			}
			return handle;
		}
		else
		{
			return null ;
		}
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.HANDLE)
		{
			tok = GetNext();
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.CONDITION)
		{
			tok = GetNext() ;
			boolean bDone = false ;
			while (!bDone)
			{
				tok = GetCurrentToken() ;
				if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
				{
					bDone = true ;
				}
				else
				{
					String cond = tok.GetValue() ;
					CIdentifier label = null ;
					tok = GetNext() ;
					if (tok.GetType() == CTokenType.LEFT_BRACKET)
					{
						tok = GetNext();
						label = ReadIdentifier() ;
						tok = GetCurrentToken() ;
						if (tok.GetType() == CTokenType.RIGHT_BRACKET)
						{
							tok = GetNext();
						}
					}
					m_arrConditions.addElement(cond);
					m_arrLabels.add(label) ; 
				}
			}
		}
		else if (tok.GetKeyword() == CCobolKeywordList.AID)
		{
			tok = GetNext() ;
			boolean bDone = false ;
			while (!bDone)
			{
				tok = GetCurrentToken() ;
				if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
				{
					bDone = true ;
				}
				else
				{
					String cond = tok.GetValue() ;
					CIdentifier label = null ;
					tok = GetNext() ;
					if (tok.GetType() == CTokenType.LEFT_BRACKET)
					{
						tok = GetNext();
						label = ReadIdentifier() ;
						tok = GetCurrentToken() ;
						if (tok.GetType() == CTokenType.RIGHT_BRACKET)
						{
							tok = GetNext();
						}
					}
					m_arrAID.addElement(cond);
					m_arrLabels.add(label) ; 
				}
			}
		}
		else
		{
			Transcoder.logError(tok.getLine(), "Unhandled situation in HANDLE");
			String cs = "" ;
			tok = GetCurrentToken() ;
			while (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
			{
				cs += tok.GetDisplay() + " " ;
				tok = GetNext() ;
			}		
			GetNext() ;
			return true ;
		}
		
		if (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS HANDLE");
			return false ;
		}
		StepNext();
		return true ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eHandle = root.createElement("ExecCICSHandle") ;
		for (int i=0; i<m_arrConditions.size();i++)
		{
			String cond = m_arrConditions.elementAt(i);
			CIdentifier id = m_arrLabels.get(i);
			Element e ;
			if (id != null)
			{
				e = root.createElement("Handle");
				id.ExportTo(e, root);
			}
			else
			{
				e = root.createElement("Unhandle");
			} 
			eHandle.appendChild(e);
			e.setAttribute("Condition", cond);
		}
		return eHandle;
	}
	
	protected Vector<CIdentifier> m_arrLabels = new Vector<CIdentifier>() ;
	protected StringVector m_arrConditions = new StringVector() ;
	protected StringVector m_arrAID = new StringVector() ;

}
