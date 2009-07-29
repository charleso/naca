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
 * Created on 20 août 04
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
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.SQL.CEntitySQLCursor;
import semantic.SQL.CEntitySQLDeclareTable;
import semantic.SQL.CEntitySQLDeleteStatement;
import utils.CGlobalEntityCounter;
import utils.NacaTransAssertException;
import utils.Transcoder;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLDelete extends CBaseExecSQLAction
{
	public CExecSQLDelete(int nLine)
	{
		super(nLine);
	}
	
	protected boolean DoParsing()
	{
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
			{
				bDone = true ;
				break;
			}
			else if (tok.GetType() == CTokenType.STRING)
			{
				String cs = new String("'" + tok.GetValue() + "'");
				AppendRequiredSpace();
				m_Clause += cs;
				GetNext();
			}
			else if (tok.GetType() == CTokenType.DOT || tok.GetType() == CTokenType.COMMA)
			{
				String cs = new String(tok.GetType().GetSourceValue());
				m_Clause += cs; 
				GetNext();
			}
			else if (tok.GetType() == CTokenType.LESS_THAN)
			{
				String cs = new String(tok.GetType().GetSourceValue());
				AppendRequiredSpace() ;
				m_Clause += cs; 
				tok = GetNext();
				if (tok.GetType() == CTokenType.GREATER_THAN)
				{
					cs = new String(tok.GetType().GetSourceValue());
					m_Clause += cs ;
					GetNext() ;
				}
				else
				{
					continue ;
				}
			}
			else if (tok.GetType() == CTokenType.COLON)
			{
				tok = GetNext() ;
				String cs = tok.GetValue();
				tok = GetNext() ;
				CIdentifier id ;
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
				m_arrParameters.add(id);
				AppendRequiredSpace();
				m_Clause += "#"+ m_arrParameters.size() ; 
			}
			else if (tok.GetType() == CTokenType.CIRCUMFLEX)
			{
				String cs = new String(tok.GetType().GetSourceValue());
				AppendRequiredSpace() ;
				m_Clause += cs; 
				tok = GetNext();
				if (tok.GetType() == CTokenType.EQUALS)
				{
					cs = new String(tok.GetType().GetSourceValue());
					m_Clause += cs ;
					GetNext() ;
				}
			}
			else if (tok.GetType().HasSourceValue())
			{
				String cs = new String(tok.GetType().GetSourceValue());
				AppendRequiredSpace();
				m_Clause += cs; 
				GetNext();
			}
			else if (tok.GetType() == CTokenType.STRING)
			{
				String cs = new String("'" + tok.GetValue() + "'");
				AppendRequiredSpace();
				m_Clause += cs;
				GetNext();
			}
			else
			{
				String cs = new String(tok.GetValue());
				if (tok.GetType() == CTokenType.IDENTIFIER && m_csViewName.equals(""))
				{
					m_csViewName = cs ;					
				}
				AppendRequiredSpace();
				m_Clause += cs; 
				GetNext();
			}
				
		}
		return true ;
	}
		
	public void AppendRequiredSpace()
	{
		if(m_Clause.endsWith(" ") == false && m_Clause.endsWith(":") == false && m_Clause.endsWith(".") == false)
			m_Clause += " ";			
	}


	public Element ExportCustom(Document root)
	{
		Element e = root.createElement("SQLDelete") ;
		e.setAttribute("Clause", m_Clause) ;
		//ExportParameters(root, e);
	
		return e;
	}
	
	private void ExportParameters(Document root, Element parent)
	{
		try
		{
			Element e = root.createElement("Parameters") ;
			parent.appendChild(e);
	
			int nNbItems = m_arrParameters.size();
			for(int n=0; n<nNbItems; n++)
			{
				Element eParam = root.createElement("Parameter") ;
				e.appendChild(eParam);
					
				CIdentifier s = m_arrParameters.elementAt(n);
				s.ExportTo(eParam, root) ;
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
		Vector<CDataEntity> v = new Vector<CDataEntity>();
		for (int i=0; i<m_arrParameters.size(); i++)
		{
			CIdentifier id = m_arrParameters.get(i);
			CDataEntity e = id.GetDataReference(getLine(), factory);
			v.add(e);
		}
		m_Clause = CExecSQL.CheckConcat(m_Clause, v , factory);
		String tablename = "" ;
		CEntitySQLDeclareTable table = factory.m_ProgramCatalog.GetSQLTable(m_csViewName);
		if (table == null)
		{	
			CGlobalEntityCounter.GetInstance().RegisterProgramToRewrite(parent.GetProgramName(), getLine(), "Missing table declaration : "+m_csViewName);
			if (m_csViewName.startsWith("V") && m_csViewName.length() > 6)
			{
				tablename = m_csViewName.substring(1, m_csViewName.length()-1) ;
			}
			else
			{
				tablename = m_csViewName ;
			}
		}
		else
		{
			tablename = table.GetTableName();			
		}
		CGlobalEntityCounter.GetInstance().CountSQLTableAccess("DELETE", tablename, parent.GetProgramName());
		m_Clause = m_Clause.replaceAll(m_csViewName, tablename);
		
		CEntitySQLCursor cursor = null ;
		int n = m_Clause.indexOf("WHERE CURRENT OF") ;
		if (n>0)
		{
			String cur = m_Clause.substring(n + 17) ;
			cursor = factory.m_ProgramCatalog.GetSQLCursor(cur) ;
			if (cursor == null) 
			{
				throw new NacaTransAssertException("Cursor not found : "+cur) ; // ASSERT
			}
			m_Clause = m_Clause.substring(0, n);
		}
		else
		{
			n = m_Clause.indexOf("SELECT") ;
			if (n>0)
			{
				int nFrom = m_Clause.indexOf("FROM", n) ;
				while (nFrom > 0)
				{
					int nWhere = m_Clause.indexOf("WHERE", nFrom) ;
					String from = "" ;
					String where = "" ;
					if (nWhere > 0)
					{
						where = m_Clause.substring(nWhere) ;
						from = m_Clause.substring(nFrom+5, nWhere) ;
					}
					else
					{
						from = m_Clause.substring(nFrom+5);
					}
					from = CExecSQLSelect.ManageFrom(parent, from, factory, false) ;
					m_Clause = m_Clause.substring(0, nFrom+5) + from + where ;
					
					nFrom = m_Clause.indexOf("FROM", nFrom + 1);
				}
			}
		}

		CEntitySQLDeleteStatement eSQL = factory.NewEntitySQLDeleteStatement(getLine(), m_Clause, v);
		Transcoder.checkSQL(getLine(), m_Clause);
		parent.AddChild(eSQL) ;
		eSQL.setCursor(cursor) ;
		for (int i=0; i<v.size(); i++)
		{
			CDataEntity e = v.get(i);
			e.RegisterReadingAction(eSQL) ;
		}
		return eSQL;
	}
	
	public String m_Clause = "" ;
	public String m_csViewName = "" ;
	public Vector<CIdentifier> m_arrParameters = new Vector<CIdentifier>() ;
}

