/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CBaseLanguageEntity;
import semantic.CEntityCondition;
import utils.CObjectCatalog;

public class CFPacJavaCondition extends CEntityCondition
{

	public CFPacJavaCondition(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	@Override
	protected void DoExport()
	{
		if (m_Condition == null)
		{
			return ;
		}

		if (!m_bAlternativeCondition)
			WriteWord("if (");
		else
			WriteWord("else if (");
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
		if (m_arrAlternativeConditions != null)
		{
			if (!m_arrAlternativeConditions.isEmpty())
			{
				n = m_lstChildren.getFirst().getLine() -1 ;
			}
			WriteLine("}", n) ;
			for (CBaseLanguageEntity e : m_arrAlternativeConditions)
			{
				DoExport(e) ;
			}
		}
		else
		{
			WriteLine("}", n) ;
		}
		if (m_ElseBloc != null)
		{
			WriteLine("else {", m_ElseBloc.getLine()) ;
			DoExport(m_ElseBloc) ;
			WriteLine("}", m_ElseBloc.GetEndLine()) ;
		}	}

}
