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
package nacaLib.CESM;

import nacaLib.varEx.InternalCharBuffer;
import nacaLib.varEx.Var;

public class CESMStartData
{
	private InternalCharBuffer m_buffer = null; 
	
	public CESMStartData(Var var, Var varLength)
	{
		int nSize = var.getLength();
		if(varLength != null)
			nSize = varLength.getInt();

		m_buffer = new InternalCharBuffer(var.getBuffer(), var.getAbsolutePosition(), nSize);
	}
	
	public InternalCharBuffer getCharBuffer()
	{
		return m_buffer;		
	}
	
	public int getLength()
	{
		return m_buffer.getBufferSize();
	}
}
