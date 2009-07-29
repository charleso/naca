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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.forms.CEntityFieldRedefine;
import semantic.forms.CResourceStrings;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaFieldRedefine extends CEntityFieldRedefine
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	
	
	public CJavaFieldRedefine(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, String level)
	{
		super(l, name, cat, out, level);
	}
	public boolean IsEntryField()
	{
		return true ;
	}
	public Element DoXMLExport(Document doc, CResourceStrings res)
	{
		return null;
	}
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.FIELD;
	}
	public String ExportReference(int nLine)
	{
		return FormatIdentifier(GetName());
	}
	public String ExportWriteAccessorTo(String value)
	{
		// unused
		return "" ;
	}
	public boolean isValNeeded()
	{
		return false;
	}

	protected void DoExport()
	{
		String name = GetName() ;
		name = FormatIdentifier(name) ;
		String cs = "Edit " + name + " = declare.level("+Integer.parseInt(m_csLevel)+")" ;
		if (!m_Type.equals(""))
		{
			cs += ".pic(" ;
			if (m_Type.equals("pic9"))
			{
				cs += "\"";
				for (int i=0; i < m_nLength; i++)
					cs += "9";
				if (m_nDecimals>0)
				{
					cs += ".";
					for (int i=0; i < m_nDecimals; i++)
						cs += "9";
				}
				cs += "\"" ;
			}
			else if (!m_Format.equals(""))
			{
				cs += "\"" + m_Format + "\"" ;
			}
			cs += ")" ;
		}
		if (m_bRightJustified)
		{
			cs += ".justifyRight()" ;
		}
		if (m_bBlankWhenZero)
		{
			cs += ".blankWhenZero()" ;
		}
		cs += ".edit() ;" ;
		WriteLine(cs);
		StartOutputBloc() ;
		ExportChildren();
		EndOutputBloc() ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseExternalEntity#GetTypeDecl()
	 */
	public String GetTypeDecl()
	{
		return "" ; // unused
	}
}
