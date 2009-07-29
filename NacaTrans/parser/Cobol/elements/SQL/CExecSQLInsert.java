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

import generate.java.CJavaStructure;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.expression.CConstantTerminal;
import parser.expression.CIdentifierTerminal;
import parser.expression.CNumberTerminal;
import parser.expression.CSQLInsertFunctionTerminal;
import parser.expression.CStringTerminal;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.SQL.CEntitySQLDeclareTable;
import semantic.SQL.CEntitySQLInsertStatement;
import utils.CGlobalEntityCounter;
import utils.Transcoder;
import utils.SQLSyntaxConverter.SQLSyntaxConverter;
import utils.modificationsReporter.Reporter;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import com.sun.org.apache.xml.internal.utils.StringVector;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLInsert extends CBaseExecSQLAction
{
	private SQLSyntaxConverter m_sqlSyntaxConverter = null; 

	public CExecSQLInsert(int nLine)
	{
		super(nLine);
		m_sqlSyntaxConverter = Transcoder.getSQLSyntaxConverter();
	}

	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.INSERT)
		{
			tok = GetNext();
		}
			
		if (tok.GetKeyword() == CCobolKeywordList.INTO)
		{
			tok = GetNext();
			m_csTable = tok.GetValue() ;
			if (m_csTable.equals("SESSION"))
			{
				tok = GetNext();
				tok = GetNext();
				m_bSessionTable = true;
				m_csTable = tok.GetValue() ;
			}
			tok = GetNext();
		}
		
		if (tok.GetType() == CTokenType.LEFT_BRACKET)
		{
			tok = GetNext();
			boolean bDone = false ;
			while (!bDone)
			{
				tok = GetCurrentToken();
				if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					tok = GetNext();
					bDone = true ;
				}
				else if (tok.GetType() == CTokenType.IDENTIFIER)
				{
					String cs = tok.GetValue() ;
					m_arrColumns.addElement(cs);
					tok = GetNext();
					if (tok.GetType() == CTokenType.COMMA)
					{
						tok = GetNext() ;
					}
				}
				else
				{
					bDone = true ;
				}
			}
		}

		if (tok.GetKeyword() == CCobolKeywordList.VALUES)
		{
			tok = GetNext();
			if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				tok = GetNext();
			}
			boolean bDone = false ;
			while (!bDone)
			{
				tok = GetCurrentToken();
				if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					tok = GetNext();
					bDone = true ;
				}
				else if (tok.GetType() == CTokenType.COLON)
				{
					tok = GetNext();
					String cs = tok.GetValue() ;
					tok = GetNext();
					CIdentifier id ;
					CIdentifier idIndicator = null;
					if (tok.GetType() == CTokenType.DOT)
					{
						tok = GetNext();
						String cs2 = tok.GetValue() ;
						tok = GetNext();
						id = new CIdentifier(cs2, cs);
					}
					else if (tok.GetType() == CTokenType.COLON)
					{
						tok = GetNext();
						String cs2 = tok.GetValue() ;
						tok = GetNext();
						id = new CIdentifier(cs);
						idIndicator = new CIdentifier(cs2);
					}
					else
					{
						id = new CIdentifier(cs);
					}
					CTerminal term = new CIdentifierTerminal(id);
					m_arrValues.add(term);
					
					if(idIndicator != null)
					{
						CTerminal termIndicator = new CIdentifierTerminal(idIndicator);
						m_hashIndicatorsById.put(term, termIndicator);
					}

					tok = GetCurrentToken();
					if (tok.GetType() == CTokenType.COMMA)
					{
						tok = GetNext() ;
					}
				}
				else if (tok.GetType() == CTokenType.STRING)
				{
					String cs = tok.GetValue();
					tok = GetNext();
					CTerminal term = new CStringTerminal(cs);
					m_arrValues.add(term);
					tok = GetCurrentToken();
					if (tok.GetType() == CTokenType.COMMA)
					{
						tok = GetNext() ;
					}
				}
				else if (tok.GetType() == CTokenType.NUMBER)
				{
					String cs = tok.GetValue();
					tok = GetNext();
					CTerminal term = new CNumberTerminal(cs);
					m_arrValues.add(term);
					tok = GetCurrentToken();
					if (tok.GetType() == CTokenType.COMMA)
					{
						tok = GetNext() ;
					}
				}
				else if (tok.GetValue().equals("TO_DATE"))	// support for TO_DATE(:xxx, ¨Format')
				{
					boolean bOk = false;
					tok = GetNext();
					if (tok.GetType() == CTokenType.LEFT_BRACKET)
					{
						tok = GetNext();
						if (tok.GetType() == CTokenType.COLON)
						{
							tok = GetNext();
							String cs = tok.GetValue() ;
							CIdentifier id = new CIdentifier(cs);
							tok = GetNext();
							if (tok.GetType() == CTokenType.COMMA)
							{
								tok = GetNext();
								String csFormat = tok.GetValue() ;
								tok = GetNext();
								if (tok.GetType() == CTokenType.RIGHT_BRACKET)
								{
									tok = GetNext();
									bOk = true;
									CTerminal term = new CSQLInsertFunctionTerminal(id, csFormat) ;
									m_arrValues.add(term);
									if (tok.GetType() == CTokenType.COMMA)
									{
										tok = GetNext() ;
									}
								}
							}							
						}
					}
					if(!bOk)
					{
						Transcoder.logError(getLine(), "Error parsing SQL INSERT TO_DATE format");
					}
				}
				else if (tok.GetValue().equals("CURRENT"))	 
				{
					tok = GetNext();
					if (tok.GetValue().equals("TIMESTAMP"))		// CURRENT TIMESTAMP; transformed in terminal constant CURRENT_TIMESTAMP
					{
						CTerminal term = new CConstantTerminal("CURRENT TIMESTAMP") ;
						m_arrValues.add(term);
						tok = GetNext();
						if (tok.GetType() == CTokenType.COMMA)
						{
							tok = GetNext() ;
						}
					}
					else if (tok.GetValue().equals("DATE"))		// CURRENT DATE; transformed in terminal constant CURRENT_DATE
					{
						Reporter.Add("Modif_PJ", "CExecSQLInsert CURRENT DATE");
						CTerminal term = new CConstantTerminal("CURRENT DATE") ;
						m_arrValues.add(term);
						tok = GetNext();
						if (tok.GetType() == CTokenType.COMMA)
						{
							tok = GetNext() ;
						}
					}
				}
				else if (tok.GetValue().equals("CURRENT_TIMESTAMP"))	// CURRENT_TIMESTAMP: Same as CURRENT TIMESTAMP
				{
					Reporter.Add("Modif_PJ", "CExecSQLInsert CURRENT_TIMESTAMP");
					CTerminal term = new CConstantTerminal("CURRENT_TIMESTAMP") ;
					m_arrValues.add(term);
					tok = GetNext();
					if (tok.GetType() == CTokenType.COMMA)
					{
						tok = GetNext() ;
					}
				}
				else if (tok.GetValue().equals("CURRENT_DATE"))
				{
					Reporter.Add("Modif_PJ", "CExecSQLInsert CURRENT_DATE");
					CTerminal term = new CConstantTerminal("CURRENT_DATE") ;
					m_arrValues.add(term);
					tok = GetNext();
					if (tok.GetType() == CTokenType.COMMA)
					{
						tok = GetNext() ;
					}
				}
				else if (tok.GetValue().equals("DEFAULT"))
				{
					Reporter.Add("Modif_PJ", "CExecSQLInsert DEFAULT");
					CTerminal term = new CConstantTerminal("DEFAULT") ;
					m_arrValues.add(term);
					tok = GetNext();
					if (tok.GetType() == CTokenType.COMMA)
					{
						tok = GetNext() ;
					}
				}
				else if (tok.GetType() == CTokenType.IDENTIFIER)
				{
					String cs = tok.GetValue() ;
					tok = GetNext();
					CIdentifier id ;
					if (tok.GetType() == CTokenType.DOT)
					{
						tok = GetNext();
						String cs2 = tok.GetValue() ;
						tok = GetNext();
						id = new CIdentifier(cs2, cs);
					}
					else
					{
						id = new CIdentifier(cs);
					}
					CTerminal term = new CIdentifierTerminal(id);
					m_arrValues.add(term);
					tok = GetCurrentToken();
					if (tok.GetType() == CTokenType.COMMA)
					{
						tok = GetNext() ;
					}
				}
				else if(tok.GetKeyword() == CCobolKeywordList.NULL)
				{
					CTerminal term = new CConstantTerminal("SQL_NULL") ;
					m_arrValues.add(term);
					tok = GetNext();
					if (tok.GetType() == CTokenType.COMMA)
					{
						tok = GetNext() ;
					}
				}
				else
				{
					bDone = true ;
				}
			}
		}
		else if (tok.GetKeyword()  == CCobolKeywordList.SELECT)
		{
			boolean bDone = false ;
			while (!bDone)
			{
				tok = GetCurrentToken() ;
				if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
				{
					bDone = true ;
					break;
				}
				else if (tok.GetType() == CTokenType.STRING)
				{
					String cs = new String("'" + tok.GetValue() + "'");
					AppendRequiredSpace();
					m_SelectClause += cs;
					GetNext();
				}
				else if (tok.GetType() == CTokenType.DOT || tok.GetType() == CTokenType.COMMA)
				{
					String cs = new String(tok.GetType().GetSourceValue());
					m_SelectClause += cs; 
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
					m_arrParametersForSelect.add(id);
					AppendRequiredSpace();
					m_SelectClause += "#"+ m_arrParametersForSelect.size() ; 
				}
				else if (tok.GetType() == CTokenType.EXCLAMATION)
				{
					String cs = new String(tok.GetType().GetSourceValue());
					m_SelectClause += cs; 
					GetNext();
				}
				else if (tok.GetType() == CTokenType.CIRCUMFLEX)
				{
					String cs = new String(tok.GetType().GetSourceValue());
					AppendRequiredSpace() ;
					m_SelectClause += cs; 
					tok = GetNext();
					if (tok.GetType() == CTokenType.EQUALS)
					{
						cs = new String(tok.GetType().GetSourceValue());
						m_SelectClause += cs ;
						GetNext() ;
					}
				}
				else if (tok.GetType() == CTokenType.LESS_THAN)
				{
					String cs = new String(tok.GetType().GetSourceValue());
					AppendRequiredSpace() ;
					m_SelectClause += cs; 
					tok = GetNext();
					if (tok.GetType() == CTokenType.GREATER_THAN)
					{
						cs = new String(tok.GetType().GetSourceValue());
						m_SelectClause += cs ;
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
					m_SelectClause += cs; 
					GetNext();
				}
				else if (tok.GetType().HasSourceValue())
				{
					String cs = new String(tok.GetType().GetSourceValue());
					AppendRequiredSpace();
					m_SelectClause += cs; 
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
							m_SelectClause += "CURRENT_DATE" ; 
							GetNext();
						}
						else if (cs2.equalsIgnoreCase("TIME"))
						{
							AppendRequiredSpace();
							m_SelectClause += "CURRENT_TIME" ; 
							GetNext();
						}
						else
						{
							AppendRequiredSpace();
							m_SelectClause += cs ; 
						}
					}
					else
					{
						AppendRequiredSpace();
						m_SelectClause += cs; 
						GetNext();
					}
				}
				
			}
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}

	public Element ExportCustom(Document root)
	{
		Element e = root.createElement("SQLInsert") ;
		e.setAttribute("Table", m_csTable) ;
		
		return e;
	}
	
	private void ExportParameters(Document root, Element parent)
	{
		try
		{
			Element e = root.createElement("Parameters") ;
			parent.appendChild(e);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
			//System.out.println(e.toString());
		}
	}

	public void AppendRequiredSpace()
	{
		if(!m_SelectClause.endsWith(" ") && !m_SelectClause.endsWith(":") && !m_SelectClause.endsWith(".") && !m_SelectClause.endsWith("!"))
			m_SelectClause += " ";			
	}
	
	protected String m_SelectClause = "" ;
	protected Vector<CIdentifier> m_arrParametersForSelect = new Vector<CIdentifier>() ;

	private void ExportValues(Document root, Element parent)
	{
		try
		{
			Element e = root.createElement("Values") ;
			parent.appendChild(e);

			int nNbItems = m_arrValues.size();
			for(int n=0; n<nNbItems; n++)
			{
				Element eParam = root.createElement("Parameter") ;
				e.appendChild(eParam);
				
				CTerminal s = m_arrValues.elementAt(n);
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
		if(this.getLine() == 1313)
		{
			int gg = 0;
		}
		CEntitySQLInsertStatement eSQL = factory.NewEntitySQLInsertStatement(getLine());
		parent.AddChild(eSQL) ;
		
		String tablename = "" ;
		CEntitySQLDeclareTable table = factory.m_ProgramCatalog.GetSQLTable(m_csTable);
		if (table == null)
		{
			CGlobalEntityCounter.GetInstance().RegisterProgramToRewrite(parent.GetProgramName(), getLine(), "Missing table declaration : "+m_csTable);
			if (m_csTable.startsWith("V") && m_csTable.length() > 6)
			{
				tablename = m_csTable.substring(1, m_csTable.length()-1) ;
			}
			else
			{
				tablename = m_csTable ;
			}
		}
		else 
		{
			tablename = table.GetTableName() ;			
		}
		CGlobalEntityCounter.GetInstance().CountSQLTableAccess("INSERT", tablename, parent.GetProgramName());

		if (!m_SelectClause.equals(""))
		{
			Vector<String> arrColumns = new Vector<String>() ;
			String newClause = CExecSQLSelect.PrepareSelectStatement(parent, m_SelectClause, arrColumns, factory, false) ;
			
			Vector<CDataEntity> arrParam = new Vector<CDataEntity>() ;
			String clause = CExecSQLSelect.CheckConcat(getLine(), newClause, m_arrParametersForSelect, arrParam, factory) ;
			eSQL.SetInsert(tablename, clause, arrParam);
			Transcoder.checkSQL(getLine(), clause);
		}
		else
		{
			boolean bSingleIndicatorInOccurs = false;
			Vector<CBaseLanguageEntity> v = null;
			Vector<CBaseLanguageEntity> vIndicators = new Vector<CBaseLanguageEntity>();
			if (m_arrValues.size() == 1)
			{
				CTerminal id = m_arrValues.get(0); 
				CDataEntity e = id.GetDataEntity(getLine(), factory);
				if(e.HasChildren())
					v = e.GetListOfChildren() ;
				else
				{
					v = new Vector<CBaseLanguageEntity>();
					v.add(e);
				}
				
				int nNbVariables = v.size();
				
				CTerminal idIndicator = m_hashIndicatorsById.get(id);
				if(idIndicator != null)
				{
					CDataEntity eIndicator = idIndicator.GetDataEntity(getLine(), factory);
					vIndicators.add(eIndicator);
					
					bSingleIndicatorInOccurs = true;
				}
				else
					vIndicators.add(null);
					
					//CDataEntity eaaa = eIndicator.GetArrayReference(vIndicators, factory); 
//					
//					
//					if(eIndicator.GetArrayReference(v, factory)())
//						vIndicators = eIndicator.GetListOfArrayItems() ;
//					else
//					{
//						
//						eIndicator.GetListOfChildren() ;
//						
//						// Maybe eIndicator is an array of the same size
//						if(eIndicator instanceof CJavaStructure)
//						{
//							CJavaStructure jsIndicator = (CJavaStructure) eIndicator;
//							int nSize = jsIndicator.getTableSizeAsInt();
//						}
//					}	
//				}
			}
			else
			{
				v = new Vector<CBaseLanguageEntity>();
				vIndicators = new Vector<CBaseLanguageEntity>();
				for (int i=0; i<m_arrValues.size(); i++)
				{
					CTerminal id = m_arrValues.get(i);
					CDataEntity e = id.GetDataEntity(getLine(), factory);
					v.add(e);
					
					CTerminal idIndicator = m_hashIndicatorsById.get(id);
					if(idIndicator != null)
					{
						CDataEntity eIndicator = idIndicator.GetDataEntity(getLine(), factory);
						vIndicators.add(eIndicator);
					}
					else
						vIndicators.add(null);
				} 
			}
			if (m_arrColumns.size() == 0)
			{
				if (table == null)
				{
					for (int i=0; i < v.size(); i++)
					{
						CDataEntity e = (CDataEntity)v.get(i);
						String name = e.GetName();
						if (name.indexOf("$") != -1)
						{
							name = name.substring(0, name.indexOf("$"));
						}
						m_arrColumns.addElement(name);
					}
					eSQL.SetInsert(tablename, m_arrColumns, v, vIndicators, bSingleIndicatorInOccurs);
				}
				else
				{	
					eSQL.SetInsert(table, v, vIndicators, bSingleIndicatorInOccurs);
				}	
			}
			else
			{
				eSQL.SetInsert(tablename, m_arrColumns, v, vIndicators, bSingleIndicatorInOccurs);
			}
		}
		eSQL.setSessionTable(m_bSessionTable);
		
		return eSQL;
	}
	
	protected StringVector m_arrColumns = new StringVector() ;
	protected Vector<CTerminal> m_arrValues = new Vector<CTerminal>() ;
	protected Hashtable<CTerminal, CTerminal> m_hashIndicatorsById = new Hashtable<CTerminal, CTerminal>() ;
	protected String m_csTable = "" ;
	protected boolean m_bSessionTable = false;
}

