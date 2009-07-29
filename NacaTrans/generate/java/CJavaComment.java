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
package generate.java;

import jlib.misc.StringUtil;
import generate.*;
import semantic.*;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaComment extends CEntityComment
{

	/**
	 * @param cat
	 * @param comment
	 */
	public CJavaComment(int l, CObjectCatalog cat, CBaseLanguageExporter out, String comment)
	{
		super(l, cat, out, comment);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#DoExport()
	 */
	protected void DoExport()
	{
		String cs = m_csComment ;
		if (cs.indexOf('\n') > 0 || cs.indexOf('\r') > 0)
		{
			cs = cs.replaceAll("\n", "0x000A").replaceAll("\r", "Ox000D") ;
		}
		cs = StringUtil.trimRight(cs) ;
		WriteComment("// " + cs) ;	
	}

	/**
	 * @see semantic.CEntityComment#ExportReference(getLine())
	 */
	@Override
	public String ExportReference(int nLine)
	{
		String cs = m_csComment ;
		if (cs.indexOf('\n') > 0 || cs.indexOf('\r') > 0)
		{
			cs = cs.replaceAll("\n", "0x000A").replaceAll("\r", "Ox000D") ;
		}
		cs = StringUtil.trimRight(cs) ;
		return "// " + cs ;	
	}
}
