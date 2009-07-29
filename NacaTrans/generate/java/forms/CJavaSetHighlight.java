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
 * Created on 11 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.forms;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.forms.CEntitySetHighligh;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSetHighlight extends CEntitySetHighligh
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaSetHighlight(int line, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity field)
	{
		super(line, cat, out, field);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		if (m_bIsBlink)
		{
			WriteLine("setFieldBlink(" + m_RefField.ExportReference(getLine())  + ") ;") ;
		}
		if (m_bIsReverse)
		{
			WriteLine("setFieldReverse("+ m_RefField.ExportReference(getLine())  + ") ;") ;
		}
		if (m_bIsUnderlined)
		{
			WriteLine("setFieldUnderline(" + m_RefField.ExportReference(getLine())  + ") ;") ;
		}
		if (m_bIsNormal)
		{
			WriteLine("setFieldUnhighlighted(" + m_RefField.ExportReference(getLine())  + ") ;") ;
		}
		if (m_HighLightValue != null)
		{
			WriteLine("moveHighLighting(" + m_HighLightValue.ExportReference(getLine()) + ", " + m_RefField.ExportReference(getLine())  + ") ;") ;
		}
		if (!m_bIsBlink && !m_bIsNormal && !m_bIsUnderlined && !m_bIsReverse && m_HighLightValue==null)
		{
			WriteLine("resetFieldHighlighting(" + m_RefField.ExportReference(getLine())  + ") ;") ;
		}
	}

}
