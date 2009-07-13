/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 10 nov. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import generate.CBaseLanguageExporter;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CEntityNoAction extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityNoAction(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected void DoExport()
	{
	}

	public boolean ignore()
	{
		return true ;
	}

	public boolean IgnoreVariable(CDataEntity data)
	{
		data.UnRegisterReadingAction(this) ;
		data.UnRegisterWritingAction(this) ;
		return true ;
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		field.UnRegisterReadingAction(this) ;
		field.UnRegisterWritingAction(this) ;
		return true ;
	}

}
