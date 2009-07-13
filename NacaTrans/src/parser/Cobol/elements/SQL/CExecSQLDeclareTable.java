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
import lexer.*;
import lexer.Cobol.CCobolKeywordList;

import java.util.ArrayList;

import jlib.xml.Tag;

//import org.apache.xml.utils.StringVector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;

import semantic.SQL.CEntitySQLDeclareTable;
import utils.CRulesManager;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLDeclareTable extends CBaseExecSQLAction
{
	protected static Hashtable ms_tabViewRenamed = null ;
	
	public CExecSQLDeclareTable(int nLine)
	{
		super(nLine);
	}
	
	public void SetTableName(String csTableName)
	{
		if (csTableName == null)
		{
			int n = 0; 
		}
		m_csTableName = csTableName;
	} 
	
	/* (non-Javadoc)
	 * @see parser.elements.CExecSQL.CBaseExecSQLAction#Export(org.w3c.dom.Document)
	 */
	public Element ExportCustom(Document root)
	{
	//	Element eSelect = null;
		Element eReturned = null;
	/*	if(m_bCursor)
		{
			Element eCursor = root.createElement("SQLDeclareTable") ;
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
		ExportParameters(root, eSelect);
		ExportInto(root, eSelect);
*/
		return eReturned;
	}

	
	/* (non-Javadoc)
	 * @see parser.elements.CExecSQL.CBaseExecSQLAction#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CheckTabViewRenamed() ;
		String csActualTableName = "" ;
		if (m_csTableName.startsWith("V"))
		{
			String table = (String)ms_tabViewRenamed.get(m_csTableName);
			if (table == null)
			{
				if (m_csTableName.length() > 6)
					csActualTableName = m_csTableName.substring(1, m_csTableName.length()-1);
				else
					csActualTableName = m_csTableName;
			}
			else
			{
				csActualTableName = table ;
			}
		}
		else
		{
			csActualTableName = m_csTableName ;
		}
		CEntitySQLDeclareTable eSQL = factory.NewEntitySQLDeclareTable(getLine(), csActualTableName, m_csTableName, m_arrTableColDescription);
		parent.AddChild(eSQL) ;
		return eSQL;	
	}	
	
	/*
	   
         UTISTE                         CHAR(2) NOT NULL,
         UTIENTO                        CHAR(5) NOT NULL,
         PRTCODG                        CHAR(2) NOT NULL,
         PRTCODD                        CHAR(2) NOT NULL,
         VALDATD                        DECIMAL(8, 0) NOT NULL,
         UTICPR                         CHAR(3) NOT NULL,
         VALDATF                        DECIMAL(8, 0) NOT NULL,
         SAIDAT                         DECIMAL(8, 0) NOT NULL,
         SAIREF                         CHAR(3) NOT NULL,
         MUTDAT                         DECIMAL(8, 0) NOT NULL,
         MUTREF                         CHAR(3) NOT NULL,
         ANNCOD                         CHAR(1) NOT NULL,
         DICVER                         SMALLINT NOT NULL
       ) END-EXEC.
       */
       	
	/**
	 * 
	 */
	private void CheckTabViewRenamed()
	{
		if (ms_tabViewRenamed == null)
		{
			ms_tabViewRenamed = new Hashtable() ;
			CRulesManager rules = CRulesManager.getInstance() ;
			
			int nb = rules.getNbRules("renameSQLView") ;
			for (int i=0; i<nb; i++) 
			{
				Tag e = rules.getRule("renameSQLView", i);
				if (e != null)
				{
					String view = e.getVal("viewName");
					String table = e.getVal("tableName");
					ms_tabViewRenamed.put(view, table) ;
				}
			}
		}		
	}

	protected boolean DoParsing()
	{
		// Parse until reaching END-EXEC.
		boolean bDone = false ;
						
		while (!bDone)
		{
			CSQLTableColDescriptor SQLTableColDescriptor = new CSQLTableColDescriptor();
			
			CBaseToken tok = GetCurrentToken() ;
			
			if (tok.GetType() == CTokenType.IDENTIFIER || tok.GetType() == CTokenType.STRING)
			{
				String csName = new String(tok.GetValue());
				SQLTableColDescriptor.SetName(csName);
				tok = GetNext();
				if (tok.GetType() == CTokenType.IDENTIFIER)	// Type
				{
					String csType = new String(tok.GetValue());
					SQLTableColDescriptor.SetType(csType);
					tok = GetNext();
				}
				else if (tok.GetKeyword() == CCobolKeywordList.DATE)
				{
					String csType = "DATE" ;
					SQLTableColDescriptor.SetType(csType);
					tok = GetNext();
				}
				else if (tok.GetKeyword() == CCobolKeywordList.TIME)
				{
					String csType = "TIME" ;
					SQLTableColDescriptor.SetType(csType);
					tok = GetNext();
				}
			}
			else 
			{
				// Should ASSERT();
				return false;			
			}
						
			if (tok.GetType() == CTokenType.LEFT_BRACKET)	// Length
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.NUMBER)
				{					
					String csLength = new String(tok.GetValue());
					SQLTableColDescriptor.SetLength(Integer.parseInt(csLength));
					tok = GetNext();
					if (tok.GetType() == CTokenType.COMMA)	// Precision
					{
						tok = GetNext();
						if (tok.GetType() == CTokenType.NUMBER)
						{
							String csPrecision = new String(tok.GetValue());
							SQLTableColDescriptor.SetDecimal(Integer.parseInt(csPrecision));
							tok = GetNext();
						}
					}
				}
				if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					tok = GetNext();
			}

			if (tok.GetKeyword() == CCobolKeywordList.NOT)
			{
				tok = GetNext();
				if (tok.GetKeyword() == CCobolKeywordList.NULL)
				{
					SQLTableColDescriptor.SetNull(false);
					tok = GetNext();
				}
				else
				{
					// Should assert
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.NULL)
			{
				SQLTableColDescriptor.SetNull(true);
				tok = GetNext();
			}

			if (tok.GetType() == CTokenType.COMMA)
			{
				tok = GetNext();	
				m_arrTableColDescription.add(SQLTableColDescriptor);
			}
			else if (tok.GetType() == CTokenType.RIGHT_BRACKET)	// Last ')'
			{
				m_arrTableColDescription.add(SQLTableColDescriptor);
				tok = GetNext();
			}

			if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
			{				
				bDone = true ;
				break;
			}
		}		
		return true ;
	}
	
	protected ArrayList<CSQLTableColDescriptor> m_arrTableColDescription = new ArrayList<CSQLTableColDescriptor>();
	protected String m_csTableName = "" ;
}

