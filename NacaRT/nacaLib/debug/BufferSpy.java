/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.debug;

import java.util.Hashtable;

import jlib.log.Log;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarBufferPos;

public class BufferSpy
{
	public static boolean BUFFER_WRITE_DEBUG = false;	// compile with true to activate buffer write debugging
	
	private static ThreadLocal<Boolean> mtls_writeStarted = new ThreadLocal<Boolean>();
	
	private static int m_nPrewriteCount = 0; 
	private static Hashtable<char [], BufferSpyChunks> ms_hashByBuffer = new Hashtable<char [], BufferSpyChunks>();
	
	public static synchronized void prewrite(char acBuffer[], int nStartAbsolutePosition, int nLength)
	{		
		if(!BUFFER_WRITE_DEBUG)
			return ;
		if(m_nPrewriteCount > 0)
			Log.logImportant("BufferSpy Error: Nested prewrite: Missing endwrite()");
		m_nPrewriteCount++;
		
		// Check 
		BufferSpyChunks bufferSpyChunks = ms_hashByBuffer.get(acBuffer);
		if(bufferSpyChunks == null)
			return ;
		BufferSpyChunk chunkWritten = bufferSpyChunks.isSpying(nStartAbsolutePosition, nLength);
		if(chunkWritten == null)
		{
			mtls_writeStarted.set(false);
			return ;
		}
	
		mtls_writeStarted.set(true);		
		Throwable th = new Throwable();
		StackTraceElement tStack[] = th.getStackTrace();
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		BaseProgramManager prgManager = tempCache.getProgramManager();
		String csProgramName = prgManager.getProgramName(); 
		csProgramName += ".java";
		
		for (int n=0; n<tStack.length; n++)
	    {
			StackTraceElement stackEntry = tStack[n];
			String cs = stackEntry.getFileName();
			if(csProgramName.equals(cs))
			{
				chunkWritten.showWrite(stackEntry);
				break;
			}
	    }
	}
	
	public static synchronized void endwrite()
	{
		if(!BUFFER_WRITE_DEBUG)
			return ;
		if(m_nPrewriteCount <= 0)
			Log.logImportant("BufferSpy Error: Nested endwrite(): Missing prewrite()");
		m_nPrewriteCount--;
		
		boolean bWriteStarted = mtls_writeStarted.get();
		if(bWriteStarted)
		{
			
		}
	}
	
	public static void alloc(char acBuffer[], int nLength)
	{
		if(!BUFFER_WRITE_DEBUG)
			return ;
	}
	
	public static void addVarToSpy(Var var)
	{
		VarBufferPos varBufferPos = var.getBuffer();
		char acBuffer[] = varBufferPos.m_acBuffer;
		BufferSpyChunks bufferSpyChunks = ms_hashByBuffer.get(acBuffer);
		if(bufferSpyChunks == null)
		{
			bufferSpyChunks = new BufferSpyChunks(); 
			ms_hashByBuffer.put(acBuffer, bufferSpyChunks);
		}
		
		int nStartPos = var.getAbsolutePosition();
		int nSize = var.getTotalSize();
		String csVarName = var.getVarDef().getNameForDebug();
		BufferSpyChunk bufferSpyChunk = new BufferSpyChunk(var, csVarName, nStartPos, nSize); 
		bufferSpyChunks.addOnce(bufferSpyChunk);
	}
}
