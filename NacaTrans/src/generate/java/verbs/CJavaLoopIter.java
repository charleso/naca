/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 6 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

//import parser.expression.CBaseExpressionExporter;

import generate.*;
import semantic.CDataEntity;
import semantic.Verbs.*;
import semantic.expression.CBaseEntityCondition;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaLoopIter extends CEntityLoopIter
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaLoopIter(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		if (m_bTestBefore)
		{
			_for(m_InitialValue, m_Variable, m_WhileCondition, m_Increment, m_bIncrementByOne);
			for (CEntityAfter a : m_Afters)
			{
				_for(a.m_varFromValueAfter, a.m_VariableAfter, a.m_condUntilAfter, a.m_varByValueAfter, true);
			}
			ExportChildren() ;
			for (@SuppressWarnings("unused") CEntityAfter a : m_Afters)
			{
				_EndBlock();
			}
			_EndBlock();
		}
		else
		{
			WriteLine("move(" + m_InitialValue.ExportReference(getLine()) + ", " + m_Variable.ExportReference(getLine()) + ");");
			WriteLine("while (true) {");
			StartOutputBloc() ;
			ExportChildren() ;

			WriteLine("if (" + m_WhileCondition.Export() + ") {") ;
			StartOutputBloc() ;
			String cs = "" ;
			if (m_Increment != null)
			{
				cs = "add(" + m_Increment.ExportReference(getLine()) + ").to(" ;
			}
			else
			{
				if(m_bIncrementByOne)
				{
					cs = "inc(" ;
				}
				else if(m_bDecrementByOne)
				{
					cs = "dec(" ;
				}
			}
			cs += m_Variable.ExportReference(getLine()) ;
			WriteWord(cs+") ;") ;
			WriteEOL();
			_EndBlock();
			WriteLine("else {");
			StartOutputBloc() ;
			WriteLine("break ;");
			_EndBlock();
			
			_EndBlock();
		}
	}

	private void _for(CDataEntity m_InitialValue, CDataEntity m_Variable,
			CBaseEntityCondition m_WhileCondition, CDataEntity m_Increment, boolean m_bIncrementByOne)
	{
		String cs = "for (move(" + m_InitialValue.ExportReference(getLine()) + ", " + m_Variable.ExportReference(getLine()) + "); " ;
		WriteWord(cs);
		WriteWord(m_WhileCondition.Export() + "; ") ;

		cs = "" ;
		if (m_Increment != null)
		{
			cs = "inc(" + m_Increment.ExportReference(getLine()) + ", " ;
		}
		else
		{
			if(m_bIncrementByOne)
			{
				cs = "inc(" ;
			}
			else
			{
				cs = "dec(" ;
			}
		}
		cs += m_Variable.ExportReference(getLine()) ;
		WriteWord(cs+")) {") ;
		WriteEOL() ;
		StartOutputBloc() ;
	}

	private void _EndBlock() {
		EndOutputBloc() ;
		WriteLine("}") ;
	}

}
