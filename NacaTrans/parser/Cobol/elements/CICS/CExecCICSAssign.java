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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

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
import semantic.CICS.CEntityCICSAssign;
import utils.CGlobalEntityCounter;
import utils.Transcoder;
import utils.modificationsReporter.Reporter;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSAssign extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSAssign(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSAssign eAss = factory.NewEntityCICSAssign(getLine()) ;
		parent.AddChild(eAss);
		Enumeration iter = m_tabRequests.keys() ;
		try
		{
			String cs = (String)iter.nextElement();
			while (!cs.equals(""))
			{
				CIdentifier id = m_tabRequests.get(cs);
				CDataEntity e = id.GetDataReference(getLine(), factory);
				eAss.AddRequest(cs, e) ;
				cs = (String)iter.nextElement() ;				
			}		
		}
		catch (NoSuchElementException e)
		{
			//e.printStackTrace();
		}
		return eAss ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.ASSIGN)
		{
			tok = GetNext();
		}
		
		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetCurrentToken() ;
			String cs = tok.GetValue() ;
			if (cs.equals("APPLID"))
			{
				CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("ASSIGN", "APPLID") ;
			}
			else if (cs.equals("TCTUALENG"))
			{
				CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("ASSIGN", "TCTUALENG") ;
			}
			else if (cs.equals("OPID"))		// PJD Added
			{
				Reporter.Add("Modif_PJ", "CExecCICS OPID");
				CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("OPID", "$$$UNMANAGED_OPID") ;
			}
			else
			{
				bDone = true ;
			}
			if (!bDone)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					CIdentifier id = ReadIdentifier() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext() ;
					}
					m_tabRequests.put(cs, id) ;					
				}
			}
		}
		
		if (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS ASSIGN");
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
		Element eass = root.createElement("ExecCICSAssign") ;
		Enumeration enumere = m_tabRequests.keys() ;
		try 
		{
			String cs = (String)enumere.nextElement() ;
			while (cs != null)
			{
				CIdentifier id = m_tabRequests.get(cs) ;
				Element e = root.createElement(cs);
				eass.appendChild(e) ;
				id.ExportTo(e, root) ;
				cs = (String)enumere.nextElement() ;
			}
		}
		catch (NoSuchElementException e)
		{
			//e.printStackTrace();
		}
		return eass;
	}

	protected Hashtable<String, CIdentifier> m_tabRequests = new Hashtable<String, CIdentifier>() ;
}
