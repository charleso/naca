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
public class InitializeManagerDouble extends InitializeManager
{
	public InitializeManagerDouble(String csd)
	{
		m_csd = csd;
	}
	
	public void set(String csd)
	{
		m_csd = csd;
	}

	public void initialize(VarBufferPos buffer, VarDefBuffer varDef, int nOffset, InitializeCache initializeCache)
	{
		//varDef.write(buffer, m_csd);
		varDef.write(buffer, m_csd, nOffset, m_csd.length());
		if(initializeCache != null)
			initializeCache.setNotManaged();
	}
	
	private String m_csd;
}