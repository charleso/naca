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
import semantic.Verbs.*;
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
				else if(m_bDecrementByOne)
				{
					cs = "dec(" ;
				}
			}
			cs += m_Variable.ExportReference(getLine()) ;
			WriteWord(cs+")) {") ;
			WriteEOL() ;
			StartOutputBloc() ;
			ExportChildren() ;
			EndOutputBloc() ;
			WriteLine("}") ;
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
			EndOutputBloc();
			WriteLine("}");
			WriteLine("else {");
			StartOutputBloc() ;
			WriteLine("break ;");
			EndOutputBloc();
			WriteLine("}");
			
			EndOutputBloc() ;
			WriteLine("}") ;
		}
	}

}
