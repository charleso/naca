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
public class InitializeManagerDoubleEdited extends InitializeManager
{
	private double m_dValue = 0.0;
	
	public InitializeManagerDoubleEdited(double d)
	{
		m_dValue = d;
	}
	
	public void set(double d)
	{
		m_dValue = d;
	}
		
	public void initialize(VarBufferPos buffer, VarDefBuffer varDef, int nOffset, InitializeCache initializeCache)
	{
		varDef.initializeEditedAtOffset(buffer, nOffset, m_dValue);
		if(initializeCache != null)
			initializeCache.setNotManaged();
	}
}