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
 * Created on Sep 29, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.expressions;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.expression.CEntityAddressOf;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaAddressOf extends CEntityAddressOf
{
	/**
	 * @param cat
	 * @param out
	 * @param data
	 */
	public CJavaAddressOf(CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity data)
	{
		super(cat, out, data);
	}
	public String ExportReference(int nLine)
	{
		return "addressOf(" + m_Reference.ExportReference(getLine()) + ")";
	}
	public boolean isValNeeded()
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#ignore()
	 */
}
