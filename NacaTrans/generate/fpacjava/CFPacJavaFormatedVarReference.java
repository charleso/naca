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
import semantic.CDataEntity;
import semantic.CEntityFormatedVarReference;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id$
 */
public class CFPacJavaFormatedVarReference extends CEntityFormatedVarReference
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CFPacJavaFormatedVarReference(CDataEntity object, CObjectCatalog cat, CBaseLanguageExporter out, String format)
	{
		super(object, cat, out, format);
	}

	/**
	 * @see semantic.CDataEntity#ExportReference(getLine())
	 */
	@Override
	public String ExportReference(int nLine)
	{
		return m_Reference.ExportReference(getLine());
	}

	/**
	 * @see semantic.CDataEntity#HasAccessors()
	 */
	@Override
	public boolean HasAccessors()
	{
		return true;
	}

	/**
	 * @see semantic.CDataEntity#ExportWriteAccessorTo(java.lang.String)
	 */
	@Override
	public String ExportWriteAccessorTo(String value)
	{
		String cs = "moveFormated(" + value + ", " + m_Reference.ExportReference(getLine()) + ", \"" + m_csFormat + "\"); " ;
		return cs;
	}

	/**
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	@Override
	protected void DoExport()
	{
		// unused
	}

}
