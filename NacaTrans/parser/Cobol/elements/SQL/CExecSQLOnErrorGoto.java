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


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CProcedureReference;
import semantic.SQL.CEntitySqlOnErrorGoto;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLOnErrorGoto extends CBaseExecSQLAction
{
	public CExecSQLOnErrorGoto(int l, String reference)
	{
		super(l);
		m_ref = reference;
	}
	public String m_ref = "" ;
	public Element ExportCustom(Document root)
	{
		Element e = root.createElement("SQLOnErrorGoto") ;
		e.setAttribute("Reference", m_ref) ;
		return e ;
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySqlOnErrorGoto eGoto = factory.NewEntitySQLOnErrorGoto(getLine(), m_ref) ; 
		if (!m_ref.equals(""))
		{
			CProcedureReference ref = new CProcedureReference(m_ref, "", factory.m_ProgramCatalog) ;
			factory.m_ProgramCatalog.getCallTree().RegisterGlobalGoto(ref) ; 
		}
		parent.AddChild(eGoto) ;
		return eGoto ;
	}
	protected boolean DoParsing()
	{
		// nothing
		return true;
	}
} 
