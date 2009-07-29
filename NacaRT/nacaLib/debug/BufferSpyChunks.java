/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.debug;

import java.util.ArrayList;

public class BufferSpyChunks
{
	private ArrayList<BufferSpyChunk> m_arrChunks = new ArrayList<BufferSpyChunk>(); 
	
	BufferSpyChunks()
	{
	}
	
	synchronized void addOnce(BufferSpyChunk chunkToAdd)
	{
		for(int n=0; n<m_arrChunks.size(); n++)
		{
			BufferSpyChunk chunk = m_arrChunks.get(n);
			if(chunk.contains(chunkToAdd))
				return ;
		}
		m_arrChunks.add(chunkToAdd);
	}
	
	BufferSpyChunk isSpying(int nStartAbsolutePosition, int nLength)
	{
		for(int n=0; n<m_arrChunks.size(); n++)
		{
			BufferSpyChunk chunk = m_arrChunks.get(n);
			if(chunk.contains(nStartAbsolutePosition, nLength))
				return chunk;
		}
		return null;
	}
}
