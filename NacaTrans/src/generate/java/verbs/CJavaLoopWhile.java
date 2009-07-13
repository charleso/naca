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
import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityLoopWhile;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaLoopWhile extends CEntityLoopWhile
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaLoopWhile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		if (m_bDoBefore)
		{
			WriteLine("do {") ;
			StartOutputBloc() ;
			ExportChildren() ;
			EndOutputBloc();
			WriteLine("}");
			WriteLine("while (" + m_WhileCondition.Export() + ") ;");
		}
		else
		{
			WriteLine("while ("+ m_WhileCondition.Export() + ") {");
			StartOutputBloc() ;
			ExportChildren() ;
			EndOutputBloc();
			WriteLine("}");
		}
	}

}
