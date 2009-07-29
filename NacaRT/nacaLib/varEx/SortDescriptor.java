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
package nacaLib.varEx;


public class SortDescriptor extends BaseFileDescriptor
{
	public SortDescriptor()
	{
		super();
	}
	
	void fillRecord(byte[] tBytes)
	{
		int nOffset = 0;
		int nLength = tBytes.length;
		if(hasVarVariableLengthMarker())	// The sort descriptor is of variable length
		{
			nOffset = 4;	// Skip leading variable length
			nLength -= 4;
		}			
		
		m_varLevel01.setFromByteArray(tBytes, nOffset, nLength);
	}
	
	public void moveInto(Var varInto)
	{
		m_varLevel01.transferTo(varInto);
	}
	
	protected boolean doClose()
	{
		return true;
	}
	
	protected FileDescriptor doOpenInput()
	{
		return null;
	}
	
	protected FileDescriptor doOpenOutput()
	{
		return null;
	}
	
	public SortDescriptor lengthDependingOn(Var varLengthDependingOn)
	{
		setVarLengthDependingOn(varLengthDependingOn);
		return this;
	}
}
