/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.batchOOApi;

import jlib.misc.NumberParser;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.varEx.Pic9Comp3BufferSupport;
import nacaLib.varEx.RWNumIntComp0;

public class FillerReadWriteExt
{
	private WriteBufferExt m_bufferExt = null;
	private ModeReadWriteExt m_currentModeReadWriteExt = ModeReadWriteExt.Unknown;
	private int m_nVariableChunkLength = -1;
	private int m_nPositionEndFixedRecordChunk = -1;
	
	public WriteBufferExt getBuffer()
	{
		return m_bufferExt;
	}
	
	public void setMode(ModeReadWriteExt mode)
	{
		m_currentModeReadWriteExt = mode;
	}
	
	public int getVariableChunkLength()
	{
		return m_nVariableChunkLength;
	}
	
	public void setVariableChunkLength(int nVariableChunkLength)
	{
		m_nVariableChunkLength = nVariableChunkLength;
	}
	
	public void markEndFixedRecordChunk()
	{
		m_nPositionEndFixedRecordChunk = m_bufferExt.getRecordCurrentPosition();
		if(m_currentModeReadWriteExt == ModeReadWriteExt.Read)
		{			
			m_nVariableChunkLength = m_bufferExt.getVariableRecordWholeLength() - m_nPositionEndFixedRecordChunk;
		}
	}
	
	public void allocOrResetBufferExt()
	{
		if(m_bufferExt == null)
		{
			int nBufSize = BaseResourceManager.getFileLineReaderBufferSize();		
			m_bufferExt = new WriteBufferExt(nBufSize);
		}
		else
			m_bufferExt.resetCurrentPosition();
	}

	public String fill(String csValue, int nLength)
	{
		if(m_currentModeReadWriteExt == ModeReadWriteExt.Read)
		{
			csValue = m_bufferExt.getString(nLength);
		}
		else if(m_currentModeReadWriteExt == ModeReadWriteExt.Write)
		{
			m_bufferExt.fillWriteAsPicX(csValue, nLength);
		}
		m_bufferExt.advanceCurrentPosition(nLength);
		return csValue;
	}
	
	public int fillComp0Unsigned(int nValue, int nLength)
	{
		if(m_currentModeReadWriteExt == ModeReadWriteExt.Read)
		{
			String cs = m_bufferExt.getString(nLength);
			nValue = NumberParser.getAsUnsignedInt(cs);
		}
		else if(m_currentModeReadWriteExt == ModeReadWriteExt.Write)
		{
			RWNumIntComp0.setFromRightToLeft(m_bufferExt, 0, nValue, nLength, nLength);			
		}
		m_bufferExt.advanceCurrentPosition(nLength);
		return nValue;
	}
	
	public int fillComp3Unsigned(int nValue, int nLength)
	{
		if(m_currentModeReadWriteExt == ModeReadWriteExt.Read)
		{	
			nValue = Pic9Comp3BufferSupport.getAsUnsignedInt(m_bufferExt, nLength);
		}
		else if(m_currentModeReadWriteExt == ModeReadWriteExt.Write)
		{
			Pic9Comp3BufferSupport.setFromRightToLeftUnsigned(m_bufferExt, nLength, nLength, nValue);			
		}
		int nNbBytesWritten = (nLength / 2) + 1; 
		m_bufferExt.advanceCurrentPosition(nNbBytesWritten);
		return nValue;
	}
	
	public int fillComp3Signed(int nValue, int nLength)
	{
		if(m_currentModeReadWriteExt == ModeReadWriteExt.Read)
		{
			nValue = Pic9Comp3BufferSupport.getAsInt(m_bufferExt, nLength);
		}
		else if(m_currentModeReadWriteExt == ModeReadWriteExt.Write)
		{
			Pic9Comp3BufferSupport.setFromRightToLeftSigned(m_bufferExt, nLength, nLength, nValue);			
		}
		int nNbBytesWritten = (nLength / 2) + 1;
		m_bufferExt.advanceCurrentPosition(nNbBytesWritten);
		return nValue;
	}
}
