/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 3 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CBaseExternalEntity;
import semantic.CEntityInline;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaInline extends CEntityInline
{

	/**
	 * @param cat
	 * @param out
	 * @param e
	 */
	public CJavaInline(int l, CObjectCatalog cat, CBaseLanguageExporter out, CBaseExternalEntity e)
	{
		super(l, cat, out, e);
		cat.RegisterExternalDataStructure(e) ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#DoExport()
	 */
	protected void DoExport()
	{
		String line = "" ;
		if (m_externalData.IsNeedDeclarationInClass())
		{
			String type = m_externalData.GetTypeDecl() ;
			line = type + " " + m_externalData.ExportReference(getLine()) + " = " + type + ".Copy(this" ;
			if (m_externalData.GetReplaceItem() != 0)
			{
				line += ", replacing("+ m_externalData.GetReplaceItem() + ", " + m_externalData.GetReplaceValue() + ")" ;
			}
			line += ") ;" ;
			WriteLine(line);
		}
		else
		{
			m_externalData.setLanguageExporter(GetXMLOutput()) ;
			DoExport(m_externalData) ;
		}
		StartOutputBloc() ;
		ExportChildren() ;
		EndOutputBloc();
	}

}
