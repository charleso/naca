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
 * Created on 2 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import generate.*;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityComment extends CBaseLanguageEntity
{

	/**
	 * @param name
	 * @param cat
	 */
	public CEntityComment(int l, CObjectCatalog cat, CBaseLanguageExporter out, String comment)
	{
		super(l, "", cat, out);
		m_csComment = comment;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#RegisterMySelfToCatalog()
	 */
	protected void RegisterMySelfToCatalog()
	{
		// NOTHING
	}

	protected String m_csComment = "" ;
	public boolean ignore()
	{
		return false; 
	}

	/**
	 * 
	 */
	public void DoExportComment()
	{
		DoExport();
	}
	
	public abstract String ExportReference(int nLine) ;
	
	public String getOriginalComment()
	{
		if(m_csComment.indexOf("VALUE 'RS0333D'") >= 0)
		{
			int gg = 0;
		}
		String csOut = m_csComment.replaceAll("\n", "0x000A").replaceAll("\r", "0x000D") ;
		return csOut;
	}

}
