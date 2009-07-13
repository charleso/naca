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

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CSemanticItem
{
	CSemanticItem(String csScreenId, CMenuDef MenuDef)
	{
		m_csScreenId = csScreenId; 
		m_MenuDef = MenuDef;
	}
	
	String m_csScreenId = null;
	CMenuDef m_MenuDef = null;
}
