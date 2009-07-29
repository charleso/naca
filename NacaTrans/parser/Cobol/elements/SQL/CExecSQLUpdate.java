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

import jlib.misc.StringUtil;

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
import semantic.SQL.CEntitySQLUpdateStatement;
import utils.CGlobalEntityCounter;
import utils.NacaTransAssertException;
import utils.Transcoder;
import utils.SQLSyntaxConverter.SQLSyntaxConverter;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLUpdate extends CBaseExecSQLAction
{
	private boolean m_bRightExpression = false;
	private boolean m_bCanSetRightExpression = false;
	private String m_csRightExpression = "";

	public CExecSQLUpdate(int nLine)
	{
		super(nLine);
		if(nLine == 3321)
		{
			int gg = 0;
		}
	}

	protected boolean DoParsing()
	{
		boolean bWhere = false ;
		boolean bValue = false ;
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
			{
				bDone = true ;
			}
			else if (tok.GetType() == CTokenType.STRING)
			{
				String cs = new String("'" + tok.GetValue() + "'");
				//AppendRequiredSpace();
				//m_Clause += cs;
				append(cs);
				
				GetNext();
			}
			else if (tok.GetType() == CTokenType.DOT || tok.GetType() == CTokenType.COMMA)
			{
				String cs = new String(tok.GetType().GetSourceValue());
				//m_Clause += cs;
				if(m_bRightExpression)
				{
					handleRightExpressionNoSpace(m_csRightExpression);
					m_bRightExpression = false;
				}
				appendNoSpace(cs);
		
				GetNext();
			}
			else if (tok.GetType() == CTokenType.LESS_THAN)
			{
				String cs = new String(tok.GetType().GetSourceValue());
//				AppendRequiredSpace() ;
//				m_Clause += cs; 
				append(cs);
				tok = GetNext();
				if (tok.GetType() == CTokenType.GREATER_THAN)
				{
					cs = new String(tok.GetType().GetSourceValue());
					//m_Clause += cs ;
					appendNoSpace(cs);
					GetNext() ;
				}
				else
				{
					continue ;
				}
			}
			else if (tok.GetType() == CTokenType.EXCLAMATION)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.EXCLAMATION)
				{
					// m_Clause += "!!" ;
					appendNoSpace("!!");
					GetNext() ;
				}
				else
				{
					//m_Clause += "! " ;
					appendNoSpace("! ");
				}
			}
			else if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				appendNoSpace("( ");
				//m_Clause += '(' ;
				GetNext() ;
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
				
				CIdentifier idIndicator = null;
				// PJD: Start added indicators for updates
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
						idIndicator = new CIdentifier(cs2, cs) ;
					}
					else
					{
						idIndicator = new CIdentifier(cs) ;
					}
					tok = GetCurrentToken() ;
				}
				//PJD: End added indicators for updates
				
				if (bValue)
				{
					if(m_arrSets.size()==34)
					{
						int gg = 0;
					}
					m_arrSets.add(id);
					m_arrSetsIndicators.add(idIndicator);
					String csParam = "#"+ m_arrSets.size() ;
					//AppendRequiredSpace();					
					//m_Clause += csParam;
					append(csParam);
				}
				else if (bWhere)
				{
					m_arrParameters.add(id);
					m_arrParametersIndicators.add(idIndicator);
					AppendRequiredSpace();
					m_Clause += "#"+ (m_arrParameters.size() + m_arrSets.size()) ; 
				}
			}
			else if (tok.GetType() == CTokenType.CIRCUMFLEX)
			{
				String cs = new String(tok.GetType().GetSourceValue());
				append(cs);
//				AppendRequiredSpace() ;
//				m_Clause += cs; 
				tok = GetNext();
				if (tok.GetType() == CTokenType.EQUALS)
				{
					cs = new String(tok.GetType().GetSourceValue());
					m_Clause += cs ;
					GetNext() ;
					if(m_bCanSetRightExpression)
					{
						m_bRightExpression = true;
						m_csRightExpression = "";
					}
				}
			}
			else if (tok.GetType().HasSourceValue())
			{
				String cs = new String(tok.GetType().GetSourceValue());
				append(cs);
//				AppendRequiredSpace();
//				m_Clause += cs; 
				GetNext();
				if(cs.equalsIgnoreCase("="))
				{
					if(m_bCanSetRightExpression)
					{
						if(!m_bRightExpression)
						{
							m_bRightExpression = true;
							m_csRightExpression = "";
						}
					}
				}
			}
			else if (tok.GetType() == CTokenType.STRING)
			{
				String cs = new String("'" + tok.GetValue() + "'");
				append(cs);
//				AppendRequiredSpace();
//				m_Clause += cs;
				GetNext();
			}
			else
			{
				if (tok.GetKeyword() == CCobolKeywordList.WHERE)
				{
					bWhere = true ;
					bValue = false ;
					if(m_bRightExpression)
						handleRightExpressionNoSpace(m_csRightExpression);
					m_bCanSetRightExpression = false;
					m_bRightExpression = false;
				}
				else if (tok.GetKeyword() == CCobolKeywordList.SET)
				{
					bWhere = false ;
					bValue = true ;
					m_bCanSetRightExpression = true;	// Comment to disable function parsing support 
				}
				String cs = new String(tok.GetValue());
				if (!bWhere && !bValue && tok.GetType() == CTokenType.IDENTIFIER && m_csViewName.equals(""))
				{
					m_csViewName = cs ;
					if(m_csViewName.equalsIgnoreCase("THISTBE"))
					{
						int gg = 0;
					}
				} 
				append(cs);
					 
				GetNext();
			}
		}
		
		if(m_csViewName.equalsIgnoreCase("THISTBE"))
		{
			int gg = 0;
		}
		return true ;
	}
	
	private void append(String cs)
	{
		if(m_bRightExpression && m_bCanSetRightExpression)
		{
			m_csRightExpression = AppendRequiredSpace(m_csRightExpression);
			m_csRightExpression += cs;
		}
		else
		{
			AppendRequiredSpace();
			m_Clause += cs;
		}
		int gg =0 ;
	}
	
	private void appendNoSpace(String cs)
	{
		if(m_bRightExpression && m_bCanSetRightExpression)
		{
			m_csRightExpression += cs;
		}
		else
		{
			m_Clause += cs;
		}
	}
	
	private void handleRightExpression(String csRightExpression)
	{
		if(!StringUtil.isEmpty(csRightExpression))
		{
			SQLSyntaxConverter sqlSyntaxConverter = Transcoder.getSQLSyntaxConverter();
			if(sqlSyntaxConverter != null)
			{
				String csRightExpressionTrimmed = csRightExpression.trim();
				String cs = sqlSyntaxConverter.resolve(csRightExpressionTrimmed);
				AppendRequiredSpace();
				m_Clause += cs;
			}
			else
			{
				AppendRequiredSpace();
				m_Clause += csRightExpression;
			}
		}
	}
	
	private void handleRightExpressionNoSpace(String csRightExpression)
	{
		if(!StringUtil.isEmpty(csRightExpression))
		{
			SQLSyntaxConverter sqlSyntaxConverter = Transcoder.getSQLSyntaxConverter();
			if(sqlSyntaxConverter != null)
			{
				String cs = sqlSyntaxConverter.resolve(csRightExpression);
				m_Clause += cs;
			}
			else
				m_Clause += csRightExpression;
		}
	}
		
	public void AppendRequiredSpace()
	{
		m_Clause = AppendRequiredSpace(m_Clause);
	}
	
	public String AppendRequiredSpace(String cs)
	{
		if(cs.endsWith(" ") == false && cs.endsWith(":") == false && cs.endsWith(".") == false)
			cs += " ";
		return cs;
	}


	public Element ExportCustom(Document root)
	{
		Element e = root.createElement("SQLUpdate") ;
		e.setAttribute("Clause", m_Clause) ;
		//ExportSet(root, e);
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

	private void ExportSet(Document root, Element parent)
	{
		try
		{
			Element e = root.createElement("Set") ;
			parent.appendChild(e);

			int nNbItems = m_arrSets.size();
			for(int n=0; n<nNbItems; n++)
			{
				Element eParam = root.createElement("Parameter") ;
				e.appendChild(eParam);
				
				CIdentifier s = m_arrSets.elementAt(n);
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
		Vector<CDataEntity> vVal = new Vector<CDataEntity>();
		for (int i=0; i<m_arrSets.size(); i++)
		{
			if(i == 35)
			{
				int gg= 0;
			}
			CIdentifier id = m_arrSets.get(i);
			CDataEntity e = id.GetDataReference(getLine(), factory);
			vVal.add(e); 
		}
		
		Vector<CDataEntity> vValInd = new Vector<CDataEntity>(); 
		for (int i=0; i<m_arrSetsIndicators.size(); i++)
		{
			if(i == 35)
			{
				int gg= 0;
			}
			CIdentifier id = m_arrSetsIndicators.get(i) ;
			if (id != null)
			{
				CDataEntity e = id .GetDataReference(getLine(), factory) ;
				vValInd.add(e) ;
			}
			else
			{
				vValInd.add(null) ;
			}
		}
		
		
		Vector<CDataEntity> vPar = new Vector<CDataEntity>();
		for (int i=0; i<m_arrParameters.size(); i++)
		{
			CIdentifier id = m_arrParameters.get(i);
			CDataEntity e = id.GetDataReference(getLine(), factory);
			vPar.add(e); 
		}
		Vector<CDataEntity> vParInd = new Vector<CDataEntity>(); 
		for (int i=0; i<m_arrParametersIndicators.size(); i++)
		{
			CIdentifier id = m_arrParametersIndicators.get(i) ;
			if (id != null)
			{
				CDataEntity e = id .GetDataReference(getLine(), factory) ;
				vParInd.add(e) ;
			}
			else
			{
				vParInd.add(null) ;
			}
		}
		
		
		m_Clause = CExecSQL.CheckConcat(m_Clause, vPar, factory);
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
		CGlobalEntityCounter.GetInstance().CountSQLTableAccess("UPDATE", tablename, parent.GetProgramName());
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
		
		CEntitySQLUpdateStatement eSQL = factory.NewEntitySQLUpdateStatement(getLine(), m_Clause, vVal, vValInd, vPar, vParInd);
		Transcoder.checkSQL(getLine(), m_Clause);
		parent.AddChild(eSQL) ;
		eSQL.setCursor(cursor) ;
		for (int i=0; i<vVal.size(); i++)
		{
			CDataEntity e = vVal.get(i);
			e.RegisterReadingAction(eSQL) ;
		}
		for (int i=0; i<vPar.size(); i++)
		{
			CDataEntity e = vPar.get(i);
			e.RegisterReadingAction(eSQL) ;
		}
		return eSQL;
	}
	
	public String m_Clause = "" ;
	public String m_csViewName = "" ;
	public Vector<CIdentifier> m_arrParameters = new Vector<CIdentifier>() ;
	public Vector<CIdentifier> m_arrParametersIndicators = new Vector<CIdentifier>() ;
	
	public Vector<CIdentifier> m_arrSets = new Vector<CIdentifier>() ;
	public Vector<CIdentifier> m_arrSetsIndicators = new Vector<CIdentifier>() ;

}

