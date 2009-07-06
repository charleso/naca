/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package parser.FPac.elements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.CLanguageElement;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.Verbs.CEntityInc;

public class CFPacInc extends CLanguageElement
{

	private CIdentifier m_id;
	private CTerminal m_term;

	public CFPacInc(int line)
	{
		super(line);
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityInc add = factory.NewEntityInc(getLine()) ;
		parent.AddChild(add) ;
		CDataEntity dest = m_id.GetDataReference(getLine(), factory) ;
		CDataEntity val = m_term.GetDataEntity(getLine(), factory) ;
		add.SetAddDest(dest) ;
		add.SetAddValue(val) ;
		return add ;

	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("Increment") ;
		Element eid = root.createElement("Var") ;
		e.appendChild(eid) ;
		m_id.ExportTo(eid, root) ;
		Element eval = root.createElement("Val") ;
		e.appendChild(eval) ;
		m_term.ExportTo(eval, root) ;
		return e;
	}

	public void Increments(CIdentifier id, CTerminal term)
	{
		m_id = id ;
		m_term = term ;
	}


}
