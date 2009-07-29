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
 * Created on Oct 11, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import lexer.CBaseToken;
import lexer.CTokenType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CBaseProcedure extends CBlocElement
{
	public CBaseProcedure(int line)
	{
		super(line);
	}

	protected boolean DoParsing()
	{
		boolean bDone = false ;
		while (!bDone)
		{
			boolean bParsed = super.DoParsing() ;
			if (!bParsed)
			{
				return false ;
			}
			CBaseToken tok = GetCurrentToken() ;
			if (tok == null)
			{
				bDone = true ;
			}
			else if (tok.GetType()== CTokenType.DOT)
			{
				if (m_fCheckForNextSentence.ISSet())
				{
					m_fCheckForNextSentence.UnSet() ; // 
				}
				GetNext();
				//  continue
			}
			else
			{
				bDone = true ; // this token can't be parsed by this function
			}
		}
		return true;
	}

	protected Element ExportCustom(Document root)
	{
		Element eProc = root.createElement("Procedure") ;
		return eProc;
	}

	/* (non-Javadoc)
	 * @see parser.elements.CBlocElement#isTopLevelBloc()
	 */
	protected boolean isTopLevelBloc()
	{
		return true ;
	}
}
