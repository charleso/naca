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
public class InitializeManagerNone extends InitializeManager
{
	public InitializeManagerNone()
	{
	}
	
//	public void initialize(VarBufferPos buffer, VarDefBuffer varDefBuffer)
//	{
//		varDefBuffer.initialize(buffer);
//	}
	
	
	public void initialize(VarBufferPos buffer, VarDefBuffer varDefBuffer, int nOffset, InitializeCache initializeCache)
	{
		varDefBuffer.initializeAtOffset(buffer, nOffset, initializeCache);
	}
}