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
 * Created on 5 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntityInitialize;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaInitialize extends CEntityInitialize
{

	/**
	 * @param cat
	 * @param out
	 */
	public CJavaInitialize(int l, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity data)
	{
		super(l, cat, out, data);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		if (m_FillAlphaWith != null)
		{
			WriteLine("initializeReplacingAlphaNum("+m_data.ExportReference(getLine())+", "+m_FillAlphaWith.ExportReference(getLine())+") ;") ;
			//WriteLine("initializeFillingAlphaNum("+m_data.ExportReference(getLine())+", "+m_FillAlphaWith.ExportReference(getLine())+") ;") ;
		}
		else if (m_RepAlphaWith != null)
		{
			WriteLine("initializeReplacingAlphaNum("+m_data.ExportReference(getLine())+", "+m_RepAlphaWith.ExportReference(getLine())+") ;") ;
		}
		else if (m_RepNumWith != null)
		{
			WriteLine("initializeReplacingNum("+m_data.ExportReference(getLine())+", "+m_RepNumWith.ExportReference(getLine())+") ;") ;
		}
		else if (m_RepNumEditedWith != null)
		{
			WriteLine("initializeReplacingNumEdited("+m_data.ExportReference(getLine())+", "+m_RepNumEditedWith.ExportReference(getLine())+") ;") ;
		}
		else
		{
			if (m_data.ExportReference(getLine()).equals("getSQLCode()"))
			{	
				WriteLine("resetSQLCode(0);") ;
			}
			else
			{
				WriteLine("initialize(" + m_data.ExportReference(getLine()) + ") ;") ;
			}
		}
	}

}
