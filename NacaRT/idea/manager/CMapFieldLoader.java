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
package idea.manager;

import nacaLib.basePrgEnv.CBaseMapFieldLoader;
import nacaLib.misc.KeyPressed;

public abstract class CMapFieldLoader extends CBaseMapFieldLoader 
{
	public void setKeyPressed(KeyPressed kp2)
	{
		m_KeyPressed = kp2 ;		
	}

	public KeyPressed getKeyPressed()
	{
		return m_KeyPressed ;
	}
	
	protected KeyPressed m_KeyPressed = null ;
}
