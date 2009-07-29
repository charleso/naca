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
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityLoopIter;
import utils.CObjectCatalog;
import utils.NacaTransAssertException;

/**
 * @author S. Charton
 * @version $Id$
 */
public class CFPacJavaLoopIter extends CEntityLoopIter
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CFPacJavaLoopIter(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/**
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	@Override
	protected void DoExport()
	{
		if (m_bTestBefore)
		{
			String cs = "for (" + m_Variable.ExportReference(getLine()) + "=" + m_InitialValue.ExportReference(getLine()) + "; " ;
			WriteWord(cs);
			WriteWord(m_WhileCondition.Export() + "; ") ;
		
			cs = m_Variable.ExportReference(getLine()) ;
			if (m_Increment != null)
			{
				cs += "+=" + m_Increment.ExportReference(getLine()) ;
			}
			else
			{
				if(m_bIncrementByOne)
				{
					cs += "++" ;
				}
				else if(m_bDecrementByOne)
				{
					cs += "--" ;
				}
			}
			WriteWord(cs+") {") ;
			WriteEOL() ;
			StartOutputBloc() ;
			ExportChildren() ;
			EndOutputBloc() ;
			WriteLine("}") ;
		}
		else
		{
			throw new NacaTransAssertException("Expecting not a Loop");
		}
	}

}
