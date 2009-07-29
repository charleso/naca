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
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntitySortReturn;
import utils.CObjectCatalog;

public class CJavaSortReturn extends CEntitySortReturn
{

	public CJavaSortReturn(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport()
	{
		String cs = "" ;
		if (m_eFileDesc != null)
		{
			cs = "returnSort("+m_eFileDesc.ExportReference(getLine()) ;
		}
		else
		{
			cs = "returnSort([Undefined]" ;
		}
		if (m_eDataInto != null)
		{
			cs += ", "+m_eDataInto.ExportReference(getLine())+")" ;
		}
		else
		{
			cs += ")" ;
		}
		if (m_blocAtEnd != null)
		{
			WriteLine("if("+cs+".atEnd()) {") ;
			StartOutputBloc() ;
			DoExport(m_blocAtEnd) ;
			EndOutputBloc() ;
			WriteLine("}") ;
			
			if (m_blocNotAtEnd != null)
			{
				WriteLine("else {") ;
				StartOutputBloc() ;
				DoExport(m_blocNotAtEnd) ;
				EndOutputBloc() ;
				WriteLine("}") ;
			}
		}
		else if (m_blocNotAtEnd != null)
		{
			WriteLine("if(!"+cs+".atEnd()) {") ;
			StartOutputBloc() ;
			DoExport(m_blocNotAtEnd) ;
			EndOutputBloc() ;
			WriteLine("}") ;
		}
		else
		{
			WriteLine(cs + " ;") ;
		}
	}

}
