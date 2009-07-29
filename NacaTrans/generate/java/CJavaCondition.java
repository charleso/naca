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
 * Created on 3 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CEntityCondition;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCondition extends CEntityCondition
{

	/**
	 * @param cat
	 * @param out
	 */
	public CJavaCondition(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#DoExport()
	 */
	protected void DoExport()
	{
		if (m_Condition == null)
		{
			return ;
		}
		if (m_Condition.ignore())
		{
			if (m_ElseBloc != null && !m_ElseBloc.ignore())
			{
				WriteLine("{", m_ElseBloc.getLine()) ;
				DoExport(m_ElseBloc) ;
				WriteLine("}") ;
			}
			return ; 
		}
		WriteWord("if (");
		m_Condition.SetLine(getLine());
		String cs = m_Condition.Export() ;
		cs += ") {" ;
		WriteWord(cs) ;
		WriteEOL() ;
		DoExport(m_ThenBloc) ;
		int n = m_ThenBloc.GetEndLine() ;
		if (n == 0 && m_ElseBloc != null)
		{
			n = m_ElseBloc.getLine() -1 ;
		}
		WriteLine("}", n) ;
		if (m_ElseBloc != null)
		{
			WriteLine("else {", m_ElseBloc.getLine()) ;
			DoExport(m_ElseBloc) ;
			WriteLine("}", m_ElseBloc.GetEndLine()) ;
		}
		

	}

}
