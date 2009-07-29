/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 2 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.misc;

import java.util.HashMap;

/**
 * @author u930di
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SemanticContextDef
{
	public SemanticContextDef()
	{
	}
	
	public String getSemanticContextValueDefinition(String csTable, String csCol)
	{
		String csTableColName = SemanticContextDef.getTableColName(csTable, csCol);
		return getSemanticContextValueDefinition(csTableColName);
	}
	
	public String getSemanticContextValueDefinition(String csTableColName)
	{
		String csSemanticContext = m_hashDBSemanticContext.get(csTableColName);
		return csSemanticContext;
	}
	
	public void setSemanticContextValueDefinition(String csTable, String csCol, String csSemanticContext)
	{
		String csTableColName = SemanticContextDef.getTableColName(csTable, csCol);		
		m_hashDBSemanticContext.put(csTableColName, csSemanticContext);
	}

		
	static public String getTableColName(String csTable, String csCol)
	{
		return csTable + "/" + csCol;
	}
		
	private HashMap<String, String> m_hashDBSemanticContext = new HashMap<String, String>();
}
