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
 * Created on Aug 17, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CEntityEnvironmentVariable;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaEnvironmentVariable extends CEntityEnvironmentVariable
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaEnvironmentVariable(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, String acc, String write, boolean bNumericVariable)
	{
		super(l, name, cat, out, acc, write, bNumericVariable);
	}
	public CJavaEnvironmentVariable(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, String acc, boolean bNumericVariable)
	{
		super(l, name, cat, out, acc, "", bNumericVariable);
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#ExportReference(semantic.CBaseLanguageExporter)
	 */
	public String ExportReference(int nLine)
	{
		return m_csAccessor ;
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#HasAccessors()
	 */
	public boolean HasAccessors()
	{
		return !m_csWriteAccessor.equals("") ;
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#ExportWriteAccessorTo(semantic.CBaseLanguageExporter)
	 */
	public String ExportWriteAccessorTo(String value)
	{
		return m_csWriteAccessor + value + ");" ;
	}
	
	public boolean isValNeeded()
	{
		return false;
	}


	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		// unused
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetDataType()
	 */
	public CDataEntityType GetDataType()
	{
		if (m_bNumericVariable)
		{
			return CDataEntityType.NUMERIC_VAR ;
		}
		else
		{
			return CDataEntityType.VAR ;
		}
	}
}
