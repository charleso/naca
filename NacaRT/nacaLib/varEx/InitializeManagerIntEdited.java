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
 * Created on 22 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InitializeManagerIntEdited extends InitializeManager
{
	private int m_nValue = 0;
	
	public InitializeManagerIntEdited(int nValue)
	{
		m_nValue = nValue;
	}
	
	public void set(int nValue)
	{
		m_nValue = nValue;
	}
	
	public void initialize(VarBufferPos buffer, VarDefBuffer varDef, int nOffset, InitializeCache initializeCache)
	{
		varDef.initializeEditedAtOffset(buffer, nOffset, m_nValue);
		if(initializeCache != null)
			initializeCache.setNotManaged();
	}
}