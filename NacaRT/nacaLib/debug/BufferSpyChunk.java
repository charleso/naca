/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.debug;

import nacaLib.varEx.Var;
import jlib.log.Log;

public class BufferSpyChunk
{
	private Var m_var = null; 
	private int m_nStartPos = 0;
	private int m_nLength = 0;
	private String m_csVarName = null;
	
	BufferSpyChunk(Var var, String csVarName, int nStartPos, int nLength)
	{
		m_var = var;
		m_csVarName = csVarName;
		m_nStartPos = nStartPos;
		m_nLength = nLength;
	}
	
	boolean contains(BufferSpyChunk chunk)
	{
		return contains(chunk.m_nStartPos, chunk.m_nLength);
	}
	
	boolean contains(int nStartAbsolutePosition, int nLength)
	{
		if(m_nStartPos <= nStartAbsolutePosition && nStartAbsolutePosition+nLength <= m_nStartPos + m_nLength)
			return true;
		return false;
	}
	
	void showWrite(StackTraceElement stackEntry)
	{
		Log.logImportant("BufferSpy; writting variable " + m_var.toString() + " in "+stackEntry.getFileName() + "::" + stackEntry.getMethodName() + " Line "+stackEntry.getLineNumber());
	}
}
