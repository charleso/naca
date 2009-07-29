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
 * Created on 3 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntityAssign;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaAssign extends CEntityAssign
{

	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaAssign(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}
	
	protected void DoExport()
	{
		int l = getLine();
		if(l == 244 || l == 245)
		{
			int gg = 0;
		}
		String cs = "move(" ;
		if (m_bFillAll)
		{
			cs = "moveAll(";
		}
		else if (m_bMoveCorresponding)
		{
			cs = "moveCorresponding(";
		}
		if (m_Value != null)
		{
			cs += m_Value.ExportReference(getLine()) ;
		}
		else
		{
			cs += "[UNDEFINED]"; 
		}
		cs += ", ";
		
		for (int i=0; i<GetNbRefTo(); i++)
		{
			String line = cs ;
			CDataEntity e1 = GetRefTo(i) ;
			String csRef = e1.ExportReference(getLine());
			if(csRef.equalsIgnoreCase("tally"))
			{
				int gg = 0;
				e1.ExportReference(getLine());
			}
			line += csRef + ");";
			
			WriteLine(line);
		}
	}

	

}
