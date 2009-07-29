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
 * Created on Jul 19, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.expression.CExpression;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.Verbs.CEntityCase;
import semantic.Verbs.CEntityCaseSearchAll;
import semantic.expression.CBaseEntityCondition;


/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CWhenSearchAllBloc extends CBlocElement
{
	public CWhenSearchAllBloc(CExpression cond, int line)
	{
		super(line);
		m_cond = cond ;
	}
	
	
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("WhenSearchAll") ;
		Element eCondition = root.createElement("Condition") ;
		e.appendChild(eCondition);
		Element eCond = m_cond.Export(root) ;
		eCondition.appendChild(eCond) ;
		return e;
	}
	
	protected CExpression m_cond = null ;

	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCaseSearchAll e = factory.NewEntityCaseSearchAll(getLine(), m_nEndLine) ;
		if (m_cond.IsConstant() || m_cond.GetConstantValue().equals("OTHER"))
		{
			e.SetCondition(null) ;
		}
		else
		{
			CBaseEntityCondition eCond = m_cond.AnalyseCondition(factory);
			eCond.setForceCompare();
			e.SetCondition(eCond) ;
		}
		parent.AddChild(e) ;
		return e;
	}

	/* (non-Javadoc)
	 * @see parser.elements.CBlocElement#isTopLevelBloc()
	 */
	protected boolean isTopLevelBloc()
	{
		return false;
	} 
}
