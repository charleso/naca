/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 18 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InitializeManagerLowValue extends InitializeManager
{
	public void initialize(VarBufferPos buffer, VarDefBuffer varDefBuffer, int nOffset, InitializeCache initializeCache)
	{
		varDefBuffer.writeRepeatingcharAtOffset(buffer, nOffset, '\0') ;
		if(initializeCache != null)
			initializeCache.addItem('\0', buffer.m_nAbsolutePosition+nOffset, varDefBuffer.m_nTotalSize);
	}
}
