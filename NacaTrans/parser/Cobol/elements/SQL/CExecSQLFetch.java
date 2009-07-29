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
 * Created on 19 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.SQL;

import java.util.Vector;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.expression.CExpression;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.SQL.CEntitySQLCursor;
import semantic.SQL.CEntitySQLFetchStatement;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLFetch extends CBaseExecSQLAction
{
	public CExecSQLFetch(int l)
	{
		super(l);
	}

	public Element ExportCustom(Document root)
	{
		Element e = root.createElement("SQLFetch") ;
		e.setAttribute("Name", m_csCursorName);
		ExportInto(root, e);
		
		return e;
	}
	
	private void ExportInto(Document root, Element parent)
	{
		try
		{
			Element e = root.createElement("Into") ;
			parent.appendChild(e);

			int nNbItems = m_arrInto.size();
			for(int n=0; n<nNbItems; n++)
			{
				Element eParam = root.createElement("Parameter") ;
				e.appendChild(eParam);
				
				CIdentifier id = m_arrInto.elementAt(n);
				id.ExportTo(eParam, root) ;
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
			//System.out.println(e.toString());
		}
	}


	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySQLCursor cur = factory.m_ProgramCatalog.GetSQLCursor(m_csCursorName);
		if (cur == null)
		{
			Transcoder.logError(getLine(), "Cursor can't be found : "+m_csCursorName);
			return null ;
		}
		int nbCol = cur.GetNbColumns();
		boolean bResolve = nbCol > m_arrInto.size() && m_arrInto.size()==1 ;
		Vector<CBaseLanguageEntity> v = new Vector<CBaseLanguageEntity>();
		for (int i=0; i<m_arrInto.size(); i++)
		{
			CIdentifier id = m_arrInto.get(i);
			CDataEntity e = id.GetDataReference(getLine(), factory);
			if (e == null)
			{
				Transcoder.logError(getLine(), "Variable can't be found : "+id.GetName());
			}
			if (bResolve)
			{
				v = e.GetListOfChildren() ;
			}
			else
			{
				v.add(e) ;
			}
		}
		
		if(nbCol > 0)
		{
			if (v.size() != nbCol)
			{
				if(v.size() < nbCol)	// The cursor declares more columns than destination variables
				{
					Transcoder.logWarn(getLine(), "Severe warning: Too few variables to fill for cursor; program may be wrong");
					CGlobalEntityCounter.GetInstance().RegisterProgramToRewrite(parent.GetProgramName(), getLine(), "INTO:Nb Vars") ;					
				}
				else	// We have more destination variable than columns declared in the cursor
				{
					int nNbIgnore = v.size() - nbCol; 
					// number of columns returned and number of variables for into are differents
					Transcoder.logError(getLine(), "Bad number of variables for INTO: too many variables provided; the last " + nNbIgnore + " will be ignored");
					CGlobalEntityCounter.GetInstance().RegisterProgramToRewrite(parent.GetProgramName(), getLine(), "INTO:Nb Vars") ;
				}
			}
		}

		CEntitySQLFetchStatement eSQL = factory.NewEntitySQLFetchStatement(getLine(), cur) ;
		Vector<CDataEntity> arrInd = new Vector<CDataEntity>(); 
		for (int i=0; i<m_arrIndicators.size(); i++)
		{
			CIdentifier id = m_arrIndicators.get(i) ;
			if (id != null)
			{
				CDataEntity e = id .GetDataReference(getLine(), factory) ;
				e.RegisterWritingAction(eSQL) ;
				arrInd.add(e) ;
			}
			else
			{
				arrInd.add(null) ;
			}
		}

		for (int i=0; i<v.size(); i++)
		{
			CDataEntity e = (CDataEntity)v.get(i);
			e.RegisterWritingAction(eSQL);
			CDataEntity ind = null ;
			if (i<arrInd.size())
				ind = arrInd.get(i) ;
			if(i >= nbCol)	// Too many variables: The cursor declares more variables than specified during FETCH
			{
				eSQL.AddIgnoredFetchInto(e, ind) ;
			}
			else
				eSQL.AddFetchInto(e, ind) ;
		}
		if(nbCol > v.size())	// Missing variables: The cursor declares more variables than specified during FETCH
		{
			int nNbMissingVariables = nbCol - v.size();
			eSQL.RegisterMissingFetchVariable(nNbMissingVariables);
		}
		parent.AddChild(eSQL) ;
		return eSQL;
	}
	
	protected boolean DoParsing()
	{
		// Parse until reaching END-EXEC.
		boolean bDone = false ;
		boolean bInto = false;
		
		while (!bDone)
		{
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.IDENTIFIER)
			{
				if (bInto)
				{
					if (!ReadInto())
					{
						return false ;
					}
				}
				else
				{
					m_csCursorName = new String(tok.GetValue());
					tok = GetNext() ;
				}
				continue;
			}
			
			if (tok.GetKeyword() == CCobolKeywordList.INTO)
			{
				bInto = true;
				tok = GetNext() ;
				continue;
			}
			if (tok.GetType() == CTokenType.COLON)
			{
				tok = GetNext();
				
				if(bInto)
				{
					if (tok.GetType() == CTokenType.IDENTIFIER)
					{
						if (!ReadInto())
						{
							return false ;
						}
						continue;
					}
				}
			}
			if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
			{
				bDone = true ;
				break;
			}
			GetNext();
		}		
		return true ;
	}
	
	/**
	 * 
	 */
	private boolean ReadInto()
	{
		CBaseToken tok = GetCurrentToken() ;
		String cs = tok.GetValue();
		
		tok = GetNext() ;
		CIdentifier id = null ;
		if (tok.GetType() == CTokenType.DOT)
		{					
			tok = GetNext() ;
			if (tok.GetType() != CTokenType.IDENTIFIER)
			{
				return false ;
			}
			id = new CIdentifier(tok.GetValue(), cs) ;
			tok = GetNext() ;
		}
		else
		{
			id = new CIdentifier(cs) ;
		}
		m_arrInto.addElement(id);	
		
		tok = GetCurrentToken() ;
		if (tok.GetType() == CTokenType.COLON)
		{
			tok = GetNext() ;
			cs = tok.GetValue();
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.DOT)
			{
				tok = GetNext();
				String cs2 = tok.GetValue() ;
				tok = GetNext();
				id = new CIdentifier(cs2, cs) ;
			}
			else
			{
				id = new CIdentifier(cs) ;
			}
			cs = tok.GetValue();
			if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				GetNext();
				boolean bDone = false ;
				while (!bDone)
				{
					tok = GetCurrentToken() ;
					CExpression exp =  ReadCalculExpression() ; 
						
					CBaseToken tok2 = GetCurrentToken() ;
					if (tok2.GetType() == CTokenType.COMMA)
					{
						id.AddArrayIndex(exp) ;
						GetNext() ;
					}
					else if (tok2.GetType() == CTokenType.RIGHT_BRACKET)
					{
						id.AddArrayIndex(exp) ;
						tok = GetNext() ;	 // consume RIGHT_BRACKET
						bDone = true ;
					}
					else if (tok2.GetType() == CTokenType.IDENTIFIER || tok2.GetType() == CTokenType.NUMBER)
					{
						id.AddArrayIndex(exp) ; // then loop
					}
				}
			}
			m_arrIndicators.addElement(id);
		}
		else
		{
			m_arrIndicators.add(null) ;
		}
		return true ;
	}

	private String m_csCursorName = null;
	private Vector<CIdentifier> m_arrInto = new Vector<CIdentifier>() ;
	private Vector<CIdentifier> m_arrIndicators = new Vector<CIdentifier>() ;
}
