/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Aug 10, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.SQL;

import java.util.Vector;

import jlib.misc.RegExpMatcher;
import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CEntityStructure;
import semantic.SQL.CEntitySQLCursor;
import semantic.SQL.CEntitySQLCursorSelectStatement;
import semantic.SQL.CEntitySQLDeclareTable;
import semantic.SQL.CEntitySQLSelectStatement;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLSelect extends CBaseExecSQLAction
{
	/**
	 * @param l
	 */
	public CExecSQLSelect(int l)
	{
		super(l);
	}
	/* (non-Javadoc)
	 * @see parser.elements.CExecSQL.CBaseExecSQLAction#Export(org.w3c.dom.Document)
	 */
	public Element ExportCustom(Document root)
	{
		Element eSelect = null;
		Element eReturned = null;
		if(m_bCursor)
		{
			Element eCursor = root.createElement("SQLCursor") ;
			eReturned = eCursor; 
			eCursor.setAttribute("Name", m_csCursorName);
			
			eSelect = root.createElement("SQLSelect") ;
			eCursor.appendChild(eSelect);
		}
		else
		{
			eSelect = root.createElement("SQLSelect") ;
			eReturned = eSelect;
		}
		eSelect.setAttribute("Clause", m_Clause) ;
		//ExportParameters(root, eSelect);
		//ExportInto(root, eSelect);

		return eReturned;
	}

	static String PrepareSelectStatement(CBaseLanguageEntity parent, String clause, Vector<String> arrColumns, CBaseEntityFactory factory, boolean bCursor)
	{
		String newClause = "SELECT " ;
		boolean bFirst = true ;
		int nbCol = 0;
		int nFrom = clause.indexOf("FROM");
		if (nFrom == -1)
		{
			return "" ;
		}
		int nFromEnd = clause.length() ;
		int nWhere = clause.indexOf("WHERE");
		if (nWhere > 0 && nWhere < nFromEnd)
			nFromEnd = nWhere ;
		int nForUpdate = clause.indexOf("FOR UPDATE") ;
		if (nForUpdate > 0 && nForUpdate < nFromEnd)
			nFromEnd = nForUpdate ;
		int nGroup = clause.indexOf("GROUP") ;
		if (nGroup > 0 && nGroup < nFromEnd)
			nFromEnd = nGroup ;
		int nOrder = clause.indexOf("ORDER") ;
		if (nOrder > 0 && nOrder < nFromEnd)
			nFromEnd = nOrder ;
		String select = clause.substring(7, nFrom) ;
		String from = "" ;
		String where = "" ;
		if (nFromEnd < clause.length())
		{
			from = clause.substring(nFrom+5, nFromEnd) ;
			where = clause.substring(nFromEnd) ;
		}
		else
		{
			from = clause.substring(nFrom+5) ;
		}
		
		// analyse returned columns
		int nPos = 0; 

		do
		{
			nPos = select.indexOf(',');
			String cs ;
			if (nPos == -1)
			{
				cs = select.trim();
				select = "" ;
				cs = checkAlias(cs) ;
				newClause += cs ;
				arrColumns.addElement(cs) ;
			}
			else
			{
				cs = select.substring(0, nPos).trim() ;
				if (cs.indexOf('(') > 0)
				{
					int nbPar = 0 ;
					int p = 1 ;
					char c = select.charAt(p) ;
					while (nbPar > 0 || c!=',')
					{
						if (c == '(')
							nbPar ++ ;
						else if (c == ')')
							nbPar -- ;
						p ++ ;
						if (p == select.length())
							break ;
						c = select.charAt(p) ;
					}
					if (p == select.length())
						nPos = -1 ;
					else
						nPos = p ;
					cs = select.substring(0, p).trim() ;
				}
				cs = checkAlias(cs) ;
				
				if (nPos == -1)
				{
					select = "" ;
					newClause += cs ;
				}
				else
				{
					select = select.substring(nPos+1);
					newClause += cs + ", " ;
				}
				arrColumns.addElement(cs) ;
			} 
			nbCol ++ ;
		} while (nPos != -1) ;


		//newClause += select ;
		
		from = ManageFrom(parent, from, factory, bCursor) ;
		
		newClause += " FROM "+from ;
		if (!where.equals(""))
		{
			newClause += " " ;
			int nPosFrom = where.indexOf("FROM") ;
			while (nPosFrom > 0)
			{
				String inte = where.substring(0, nPosFrom + 5) ;
				newClause += inte ;
				int nPosWhere = where.indexOf("WHERE", nPosFrom) ;
				if (nPosWhere > 0)
				{
					from = where.substring(nPosFrom+5, nPosWhere) ;
					where = where.substring(nPosWhere) ;
				}
				else
				{
					from = where.substring(nPosFrom+5) ;
					where = "" ;
				}
				from = ManageFrom(parent, from, factory, bCursor) ;
				newClause += from ;
				nPosFrom = where.indexOf("FROM") ;
			}
			newClause += where ;
		}

		int posUnion = newClause.indexOf("UNION") ;
		if (posUnion > 0)
		{
			int  posselect = newClause.indexOf("SELECT", posUnion) ;
			String secondClause = newClause.substring(posselect) ;
			Vector<String> arrNewColumns = new Vector<String>() ;
			String result = PrepareSelectStatement(parent, secondClause, arrNewColumns, factory, bCursor) ;
			newClause = newClause.substring(0, posselect) ;
			newClause += result ;
		}
		
		newClause = PrepareSelectStatementJoin(parent, newClause, factory, bCursor, "LEFT");
		newClause = PrepareSelectStatementJoin(parent, newClause, factory, bCursor, "RIGHT");
		newClause = PrepareSelectStatementJoin(parent, newClause, factory, bCursor, "INNER");
		
		return newClause ;
	}
	
	static String PrepareSelectStatementJoin(CBaseLanguageEntity parent, String clause, CBaseEntityFactory factory, boolean bCursor, String joinType)
	{
		String newClause = clause;
		
		joinType += " JOIN ";
		
		int posJoin = newClause.indexOf(joinType);
		while (posJoin > 0)
		{
			posJoin += joinType.length();
			int posAfter = newClause.indexOf(" ", posJoin);
			String from = newClause.substring(posJoin, posAfter);
			from = ManageFrom(parent, from, factory, bCursor);
			newClause = newClause.substring(0, posJoin) + from + newClause.substring(posAfter);
			posJoin = newClause.indexOf(joinType, posJoin);
		}

		return newClause;
	}

	/**
	 * @param cs
	 * @return
	 */
	private static String checkAlias(String cs)
	{	
		if (cs.indexOf('\'') >= 0) 
		{
			String out = "";
			String csMatch = cs;
			if (csMatch.startsWith("DISTINCT ")) 
			{
				csMatch = cs.substring(9);
				out = "DISTINCT ";
			}
			RegExpMatcher matcher = new RegExpMatcher() ;
			if (matcher.isMatching(csMatch, "('.*')(\\s+|\\s+AS\\s+)'([^-]*)'"))
			{
				out += matcher.group(1) + " AS " + matcher.group(3) ;
				return out ;
			}
			else if (matcher.isMatching(csMatch, "('.*')(\\s+|\\s+AS\\s+)'(.*)'"))
			{
				out += matcher.group(1) + " AS \\\"" + matcher.group(3) + "\\\"";
				return out ;
			}
			else if (matcher.isMatching(csMatch, "([\\w\\._]*)(\\s+|\\s+AS\\s+)'([^-]*)'"))
			{
				out += matcher.group(1) + " AS " + matcher.group(3) ;
				return out ;
			}
			else if (matcher.isMatching(csMatch, "([\\w\\._]*)(\\s+|\\s+AS\\s+)'(.*)'"))
			{
				out += matcher.group(1) + " AS \\\"" + matcher.group(3) + "\\\"";
				return out ;
			}
		}
		return cs ;
	}
	static String CheckConcat(int nLine, String clause, Vector m_arrParameters, Vector<CDataEntity> arrParam, CBaseEntityFactory factory)
	{
		for (int i=0; i<m_arrParameters.size(); i++)
		{
			CIdentifier id = (CIdentifier)m_arrParameters.get(i);
			CDataEntity e = id.GetDataReference(nLine, factory);
			arrParam.add(e);
		}
		String newClause = CExecSQL.CheckConcat(clause, arrParam, factory);
		return newClause ;
	}
	
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		Vector<CDataEntity> arrInto = new Vector<CDataEntity>() ;
		Vector<String> arrColumns = new Vector<String>() ;
		String newClause = PrepareSelectStatement(parent, m_Clause, arrColumns, factory, m_bCursor) ;
		processInto(m_arrInto, arrInto, arrColumns.size(), factory) ;
		int nbCol = arrColumns.size() ;
		for (String col : arrColumns)
		{
			if (col.contains("*"))
			{
				nbCol = 0 ;
			}
		}
		if (arrInto.size()>0 && nbCol>0 && arrInto.size() != nbCol)
		{
			// number of columns returned and number of variables for into are differents
			Transcoder.logError(getLine(), "Bad number of variables for INTO");
			CGlobalEntityCounter.GetInstance().RegisterProgramToRewrite(parent.GetProgramName(), getLine(), "INTO:Nb Vars") ;
		}
		
		Vector<CDataEntity> arrInd = new Vector<CDataEntity>(); 
		for (int i=0; i<m_arrIndicators.size(); i++)
		{
			CIdentifier id = m_arrIndicators.get(i) ;
			if (id != null)
			{
				CDataEntity e = id .GetDataReference(getLine(), factory) ;
				arrInd.add(e) ;
			}
			else
			{
				arrInd.add(null) ;
			}
		}
		Vector<CDataEntity> arrParam = new Vector<CDataEntity>() ;
		m_Clause = CheckConcat(getLine(), newClause, m_arrParameters, arrParam, factory) ;
		if(!m_bCursor)
		{
			CEntitySQLSelectStatement eSQL = factory.NewEntitySQLSelectStatement(getLine(), m_Clause, arrParam, arrInto, arrInd) ;
			Transcoder.checkSQL(getLine(), m_Clause);
			parent.AddChild(eSQL) ;
			for (int i=0; i<arrParam.size(); i++)
			{
				CDataEntity e = arrParam.get(i) ;
				if (e!=null)
				{
					e.RegisterReadingAction(eSQL) ;
				}
			}
			for (int i=0; i<arrInto.size(); i++)
			{
				CDataEntity e = arrInto.get(i) ;
				e.RegisterWritingAction(eSQL) ;
			}
			for (int i=0; i<arrInd.size(); i++)
			{
				CDataEntity e = arrInd.get(i) ;
				if (e!= null)
				{
					e.RegisterWritingAction(eSQL) ;
				}
			}
			return eSQL;
		}
		// Cursor
		CEntitySQLCursor cur = factory.m_ProgramCatalog.GetSQLCursor(m_csCursorName) ;
		if (cur == null)
		{
			cur = factory.NewEntitySQLCursor(m_csCursorName);
		}
		else
		{
			Transcoder.logError(getLine(), "Cursor already defined : " + m_csCursorName);
		}
		CEntitySQLCursorSelectStatement eSQL = factory.NewEntitySQLCursorSelectStatement(getLine()) ;
		eSQL.SetSelect(m_Clause, arrParam, cur, nbCol, m_bWithHold) ;
		Transcoder.checkSQL(getLine(), m_Clause);
		cur.SetSelect(eSQL);
		parent.AddChild(eSQL) ;
		for (int i=0; i<arrParam.size(); i++)
		{
			CDataEntity e = arrParam.get(i) ;
			if (e != null)
			{
				e.RegisterReadingAction(eSQL) ;
			}
		}
		return eSQL;
		
	}	
	
	/**
	 * @param arrInto
	 * @param arrInto2
	 */
	private static void processInto(Vector<CIdentifier> arrIntoInput, Vector<CDataEntity> arrIntoOutput, int nbCol, CBaseEntityFactory factory)
	{
		if (arrIntoInput != null && arrIntoOutput != null)
		{
			// manage INTO
			for (int i=0; i<arrIntoInput.size(); i++)
			{
				CIdentifier id = arrIntoInput.get(i);
				if (id != null)
				{
					CDataEntity e = id.GetDataReference(0, factory);
					if (arrIntoInput.size()<nbCol)
					{ // less INTO than col number... expand structure passed in INTO
						Vector v = e.GetListOfChildren();
						for (int j=0; j<v.size(); j++)
						{
							CEntityStructure le = (CEntityStructure)v.get(j);
							if (!le.IsRedefine())
							{
								arrIntoOutput.addElement(le);
							}
						}
					}
					else
					{
						arrIntoOutput.addElement(e);
					}
				}
			}
		}
	}
	/**
	 * @param from
	 * @return
	 */
	public static String ManageFrom(CBaseLanguageEntity parent, String from, CBaseEntityFactory factory, boolean bCursor)
	{
		int nPos = -1 ;
		int a = 0 ;
		do 
		{
			nPos = from.indexOf(',', a);
			if (nPos == a)
			{
				a = a+1 ;
				nPos = from.indexOf(',', a) ;
			}
			String cs = from ;
			if (nPos != -1)
			{
				cs = from.substring(a, nPos).trim() ;
			}
			else
			{
				cs = from.substring(a).trim() ;
			}
			int n=cs.indexOf(')');
			if (n != -1)
			{
				cs = cs.substring(0, n).trim() ;
			}
			else
			{
				n=cs.indexOf(' ');
				if (n != -1)
				{
					cs = cs.substring(0, n).trim() ;
				}
			}
			String tablename = cs;
			CEntitySQLDeclareTable table = factory.m_ProgramCatalog.GetSQLTable(cs) ;
			if (table != null)
			{
				tablename = table.GetTableName();
				from = from.replaceFirst(table.GetViewName(), tablename) ;
			}
			else 
			{
				if (factory.m_ProgramCatalog.getProcedureDivision() != null)
				{
					CGlobalEntityCounter.GetInstance().RegisterProgramToRewrite(factory.m_ProgramCatalog.getProcedureDivision().GetProgramName(), 0, "Missing table declaration : "+cs);
				}
				if (cs.startsWith("V") && cs.length() > 6)
				{
					tablename = cs.substring(1, cs.length()-1) ;
					from = from.replaceFirst(cs, tablename) ;
				}
			}
			if (bCursor)
			{
				CGlobalEntityCounter.GetInstance().CountSQLTableAccess("SELECT_CURSOR", tablename, parent.GetProgramName());
			}
			else
			{
				CGlobalEntityCounter.GetInstance().CountSQLTableAccess("SELECT", tablename, parent.GetProgramName());
			}
			a = nPos ;
		} 
		while (nPos != -1) ;
		return from ;
	}
	/**
	 * @param m_Clause
	 * @return
	 */
	public void SetCursorName(String csCursorName, boolean bWithHold)
	{
		m_csCursorName = csCursorName;
		m_bCursor = true;
		m_bWithHold = bWithHold ;
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
			else if (tok.GetKeyword() == CCobolKeywordList.INTO)
			{
				boolean b = false ;
				tok = GetNext() ;
				while (!b)
				{
					if (tok.GetType() == CTokenType.COLON)
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
						m_arrInto.addElement(id);
						tok = GetCurrentToken() ;
						if (tok.GetType() == CTokenType.COMMA)
						{
							tok = GetNext() ;
							m_arrIndicators.addElement(null);
						}
						else if (tok.GetType() == CTokenType.COLON)
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
							m_arrIndicators.addElement(id);
							tok = GetCurrentToken() ;
							if (tok.GetType() == CTokenType.COMMA)
							{
								tok = GetNext() ;
							}
						}
					}
					else 
					{
						b = true ;
					}
				}
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
			else if (tok.GetType() == CTokenType.EXCLAMATION)
			{
				String cs = new String(tok.GetType().GetSourceValue());
				m_Clause += cs; 
				GetNext();
			}
			else if (tok.GetType() == CTokenType.CIRCUMFLEX)
			{
				String cs = new String(tok.GetType().GetSourceValue());
				tok = GetNext();
				if (tok.GetType() == CTokenType.EQUALS)
				{
					AppendRequiredSpace() ;
					m_Clause += cs; 
					cs = new String(tok.GetType().GetSourceValue());
					m_Clause += cs ;
					GetNext() ;
				}
				else if (tok.GetType() == CTokenType.LESS_THAN)
				{
					cs = ">=" ;
					AppendRequiredSpace() ;
					m_Clause += cs; 
					GetNext() ;
				}
				else if (tok.GetType() == CTokenType.GREATER_THAN)
				{
					cs = "<=" ;
					AppendRequiredSpace() ;
					m_Clause += cs; 
					GetNext() ;
				}
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
			else if (tok.GetType() == CTokenType.RIGHT_SQUARE_BRACKET)
			{
				String cs = new String(tok.GetType().GetSourceValue());
				m_Clause += cs; 
				GetNext();
			}
			else if (tok.GetType().HasSourceValue())
			{
				String cs = new String(tok.GetType().GetSourceValue());
				AppendRequiredSpace(cs);
				m_Clause += cs; 
				GetNext();
			}
			else
			{
				String cs = new String(tok.GetValue());
				if (cs.equalsIgnoreCase("CURRENT"))
				{
					tok = GetNext() ;
					String cs2 = tok.GetValue() ;
					if (cs2.equalsIgnoreCase("DATE"))
					{
						AppendRequiredSpace();
						m_Clause += "CHAR(CURRENT DATE, EUR)" ; 
						GetNext();
					}
					else if (cs2.equalsIgnoreCase("TIME"))
					{
						AppendRequiredSpace();
						m_Clause += "CURRENT_TIME" ; 
						GetNext();
					}
					else
					{
						AppendRequiredSpace();
						m_Clause += cs ; 
					}
				}
//				else if (cs.equalsIgnoreCase("DIGITS"))
//				{
//					tok = GetNext() ;
//					if (tok.GetType() == CTokenType.LEFT_BRACKET)
//					{
//						tok = GetNext() ;
//						m_Clause += "DIGITS(" ; 
//					}
//					else
//					{
//					}
//				}
				else
				{
					AppendRequiredSpace();
					m_Clause += cs; 
					GetNext();
				}
			}
			
		}
		return true ;		
	}
	
	public void AppendRequiredSpace()
	{
		AppendRequiredSpace("") ;
	}
	
	public void AppendRequiredSpace(String cs)
	{
		if(m_Clause.endsWith(" ") || m_Clause.endsWith(":") || m_Clause.endsWith(".") || m_Clause.endsWith("!") || m_Clause.endsWith("("))
			return  ;
		if (cs.equals("(") || cs.equals(")"))
			return ;
		m_Clause += " ";			
	}
	
	public String m_Clause = "" ;
	protected Vector<CIdentifier> m_arrParameters = new Vector<CIdentifier>() ;
	protected Vector<CIdentifier> m_arrInto = new Vector<CIdentifier>() ;
	protected Vector<CIdentifier> m_arrIndicators = new Vector<CIdentifier>() ;
	private String m_csCursorName = null;
	private boolean m_bCursor = false;
	private boolean m_bWithHold = false ;
}
