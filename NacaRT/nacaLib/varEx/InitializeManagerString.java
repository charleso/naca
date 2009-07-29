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
public class InitializeManagerString extends InitializeManager
{
	public InitializeManagerString(String cs)
	{
		m_cs = cs;
	}
	
	public void set(String cs)
	{
		m_cs = cs;
	}
	
//	public void initialize(VarBufferPos buffer, VarDefBuffer varDef)
//	{
//		varDef.initialize(buffer, m_cs);
//	}
	
	public void initialize(VarBufferPos buffer, VarDefBuffer varDef, int nOffset, InitializeCache initializeCache)
	{
		varDef.initializeAtOffset(buffer, nOffset, m_cs);
		if(initializeCache != null)
			initializeCache.setNotManaged();
	}
	

	
	String getString()
	{
		return m_cs;
	}
	
	private String m_cs;
}