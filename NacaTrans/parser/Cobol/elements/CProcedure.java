/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.CEntityProcedure;
import utils.CGlobalEntityCounter;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CProcedure extends CBaseProcedure
{
	public CProcedure(String name, int line)
	{
		super(line);
		m_csName = name ;
	}
	
	protected boolean DoParsing()
	{
		CGlobalEntityCounter.GetInstance().CountCobolVerb("PROCEDURE") ;
		return super.DoParsing();
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eProc = root.createElement("Procedure") ;
		eProc.setAttribute("Name", m_csName) ;
		return eProc;
	}
	
	protected String m_csName = "" ;

	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityProcedure eProc = factory.NewEntityProcedure(getLine(), m_csName, parent.getSectionContainer()) ;
		parent.AddChild(eProc) ;
		if (m_nRewriteLine != 0)
		{
			CGlobalEntityCounter.GetInstance().RegisterProgramToRewrite(parent.GetProgramName(), m_nRewriteLine, "NEXT SENTENCE") ;
		}
		return eProc;
	}
}
