/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 25 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package idea.semanticContext;

import java.util.ArrayList;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CMenuDef
{
	CMenuDef()
	{
	}
	
	void setTitle(String csTitle)
	{
		m_csTitle = csTitle;
	}
	
	CMenuOptionDef createAndRegisterNewOption()
	{
		CMenuOptionDef MenuOptionDef = new CMenuOptionDef();
		m_arrOptions.add(MenuOptionDef);
		return MenuOptionDef;
	}
	
	public String buildHTMLMenu()
	{
		String cs = "<H1><CENTER>"+m_csTitle+"</CENTER></H1><BR><table>";
		for(int n=0; n<m_arrOptions.size(); n++)
		{
			CMenuOptionDef MenuOptionDef = m_arrOptions.get(n);
			cs += "<tr><td><BUTTON TYPE=SUBMIT>" + MenuOptionDef.m_csLabel + "</BUTTON></td></tr>";
			// use MenuOptionDef.m_csAction to identify option
		}
		cs += "</table>";
		return cs;
	}
		
	ArrayList<CMenuOptionDef> m_arrOptions = new ArrayList<CMenuOptionDef>();	// Array of  MenuOptionDef
	public String m_csTitle = null;
}
