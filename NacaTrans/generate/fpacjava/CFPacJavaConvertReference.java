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
import semantic.Verbs.CEntityConvertReference;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id$
 */
public class CFPacJavaConvertReference extends CEntityConvertReference
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CFPacJavaConvertReference(CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(cat, out);
	}

	/**
	 * @see semantic.CDataEntity#ExportReference(getLine())
	 */
	@Override
	public String ExportReference(int nLine)
	{
		String csF = "" ;
		if (m_bConvertToPacked)
		{
			csF = "P" ;
		}
		else if (m_bConvertToAlphaNum)
		{
			csF = "X" ;
		}
		else
		{
			return m_Reference.ExportReference(getLine());
		}
		if (m_Reference.HasAccessors())
		{
			String cs = m_Reference.ExportReference(getLine()) ;
			cs += csF  ;
			return cs ;
		}
		else
		{
			String cs = "buffer"+csF+"(";
			cs += m_Reference.ExportReference(getLine()) ;
			return cs ;
		}
	}

	/**
	 * @see semantic.CDataEntity#HasAccessors()
	 */
	@Override
	public boolean HasAccessors()
	{
		return true ;
	}

	/**
	 * @see semantic.CDataEntity#ExportWriteAccessorTo(java.lang.String)
	 */
	@Override
	public String ExportWriteAccessorTo(String value)
	{
		return null;
	}


}
