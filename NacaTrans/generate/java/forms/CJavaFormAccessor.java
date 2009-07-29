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
package generate.java.forms;

import generate.CBaseLanguageExporter;
import semantic.forms.CEntityFormAccessor;
import semantic.forms.CEntityResourceForm;
import utils.CObjectCatalog;


/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaFormAccessor extends CEntityFormAccessor
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 * @param owner
	 */
	public CJavaFormAccessor(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CEntityResourceForm owner)
	{
		super(l, name, cat, out, owner);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#ExportReference(semantic.CBaseLanguageExporter)
	 */
	public String ExportReference(int nLine)
	{
		return m_Owner.ExportReference(getLine()) ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#HasAccessors()
	 */
	public boolean HasAccessors()
	{
		return m_Owner.HasAccessors();
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#ExportWriteAccessorTo(semantic.CBaseLanguageExporter)
	 */
	public String ExportWriteAccessorTo(String value)
	{
		return m_Owner.ExportWriteAccessorTo(value) ;
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
		// unsued		
	}

}
