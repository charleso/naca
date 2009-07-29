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
package nacaLib.fpacPrgEnv;

import jlib.log.Log;
import jlib.misc.FileEndOfLine;
import jlib.misc.LineRead;
import jlib.misc.RecordLengthDefinition;
import nacaLib.debug.BufferSpy;
import nacaLib.varEx.BaseFileDescriptor;
import nacaLib.varEx.RecordDescriptorAtEnd;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarBuffer;


public class FPacFileDescriptor extends BaseFileDescriptor
{	
	private FPacRecordFiller m_FPacRecordFillerInput = null;
	private FPacRecordFiller m_FPacRecordFillerOutput = null;
	private final static int MAX_RECORD_LENGTH = 32768;
	
	private byte m_tBytes[] = null;
	private char m_acBuffer[] = null;
	private VarBuffer m_varBuffer = null; 
	private FPacVarManager m_fpacVarManager = null;
	RecordLengthDefinition m_forcedRecordLengthDefinition = null;
	private int m_nLastReadRecordLength = -1;
	
	public FPacFileDescriptor(FPacProgram program, String csLogicalName)
	{
		super(program.getProgramManager().getEnv(), csLogicalName);
		init(program);
	}
	
	void setRecordFillers(FPacRecordFiller FPacRecordFillerInput, FPacRecordFiller FPacRecordFillerOutput)
	{
		m_FPacRecordFillerInput = FPacRecordFillerInput;
		m_FPacRecordFillerOutput = FPacRecordFillerOutput;
	}
	
	public FPacFileDescriptor openOutput()
	{
		super.openOutput();
		
		fillOutputBuffer();		
		return this;
	}
	
	public FPacFileDescriptor openInput()
	{
		super.openInput();
		return this;
	}
	
	
	public FPacFileDescriptor openInputOutput()
	{
		super.openInputOutput();
		return this;
	}
		
	public void variableLength()
	{
		m_fileManagerEntry.setVariableLength();
	}

	private void init(FPacProgram program)
	{
		m_tBytes = new byte [MAX_RECORD_LENGTH];
		
		m_acBuffer = new char [MAX_RECORD_LENGTH];
		m_varBuffer = new VarBuffer(m_acBuffer);
		
		m_fpacVarManager = new FPacVarManager(program);
	}
	
	private void fillInputBuffer()
	{
		if(m_FPacRecordFillerInput != null)
			m_FPacRecordFillerInput.fillBuffer(m_acBuffer);
	}

	private void fillOutputBuffer()
	{
		if(m_FPacRecordFillerOutput != null)
			m_FPacRecordFillerOutput.fillBuffer(m_acBuffer);
	}

	public RecordDescriptorAtEnd read()
	{		
		fillInputBuffer();
		m_nLastReadRecordLength = -1;
		
		if(m_fileManagerEntry.isVariableLength())	 // Variable size record
		{
			long lLastHeaderStartPosition = m_fileManagerEntry.m_dataFile.getFileCurrentPosition();	// Keep header start position
			LineRead header = m_fileManagerEntry.m_dataFile.readBuffer(4, false);		// Read header
			if(header != null)
			{
				int nLengthExcludingHeader = header.getAsLittleEndingUnsignBinaryInt();	// Length in header doesn't count the header itself
				int nHeaderLength = m_varBuffer.setFromLineRead(header, 0);			// write the record after the record length at the beginning; it includes the length itself
				LineRead lineRead = m_fileManagerEntry.m_dataFile.readBuffer(nLengthExcludingHeader, true);		// Read including trailing LF
				m_fileManagerEntry.m_dataFile.setLastPosition(lLastHeaderStartPosition);	// Save current position at the header start
				if(lineRead != null)
				{
					m_nLastReadRecordLength = m_varBuffer.setFromLineRead(lineRead, 4) + nHeaderLength;
					incNbRecordRead();
				}				
			}
		}
		else		// Constant record size
		{
			int nRecordLength = 0;
			if(m_forcedRecordLengthDefinition == null)
				nRecordLength = getRecordLength(null);				
			else
				nRecordLength = m_forcedRecordLengthDefinition.getRecordLength();
			
			LineRead lineRead = null;
			if(nRecordLength > 0)
				lineRead = m_fileManagerEntry.m_dataFile.readBuffer(nRecordLength, true);
			else
				lineRead = m_fileManagerEntry.m_dataFile.readNextUnixLine();

			if(lineRead != null)
			{
				m_nLastReadRecordLength = m_varBuffer.setFromLineRead(lineRead, 0);	// Record length does not includes LF !
				incNbRecordRead();
			}
		}
		
		if(m_fileManagerEntry.m_dataFile.isEOF())
			return RecordDescriptorAtEnd.End;
		return RecordDescriptorAtEnd.NotEnd;
	}
	
	private void doWrite()
	{		
		if(!m_fileManagerEntry.isVariableLength())		// Constant record size
		{
			int nRecordLength = getRecordLength(null);
			if(nRecordLength == 0)
			{
				if(m_fileManagerEntry.m_dataFile.isUpdateable())
					nRecordLength = m_nLastReadRecordLength;
			}
			
			if(nRecordLength >= 0)
			{
				fillBuffer(m_varBuffer.m_acBuffer, 0, nRecordLength);
				write(m_tBytes, 0, nRecordLength, true);
				incNbRecordWrite();
			}
			else
			{
				Log.logCritical("FPacFileDescriptor::File: Cannot write record because No length defined for fixed length FPac file " + getLogicalName());
			}
		}
		else
		{
			if(m_fileManagerEntry.m_dataFile.isUpdateable())	// rewrite, not write 
			{
				fillBuffer(m_varBuffer.m_acBuffer, 0, m_nLastReadRecordLength);
				write(m_tBytes, 0, m_nLastReadRecordLength, true);
				incNbRecordWrite();
			}
			else	// Use header to get record length 
			{
				int nRecordLength = m_varBuffer.getIntAt(0);	// Read record length encoded in the 4 leading bytes; it doesn't includes the record header itself, nor the trailing LF
				int nTotalRecordLength = nRecordLength + 4;
				fillBuffer(m_varBuffer.m_acBuffer, 0, nTotalRecordLength);
				write(m_tBytes, 0, nTotalRecordLength, true);
				incNbRecordWrite();
			}
		}
		
		fillOutputBuffer();
	}
	
	public void write()
	{		
		if(m_fileManagerEntry.m_dataFile.isWritable())
		{
			if(m_fileManagerEntry.m_dataFile.isReadable())	// File open in update mode
			{
				long l = m_fileManagerEntry.m_dataFile.getLastPosition();
				m_fileManagerEntry.m_dataFile.setFileCurrentPosition(l);
			}
			doWrite();
		}
	}
	
	private void fillBuffer(char tcSourceBuffer[], int nSourceOffset, int nRecordLength)
	{
		for(int n=0; n<nRecordLength; n++)
		{
			m_tBytes[n] = (byte)tcSourceBuffer[n + nSourceOffset];
		}
	}
	
	public FPacVarManager getFPacVarManager()
	{
		return m_fpacVarManager;
	}
	
	public VarBuffer getVarBuffer()
	{
		return m_varBuffer;
	}
	
	Var createFPacVarAlphaNum(int nAbsolutePosition1Based, int nNbDigitsInteger)
	{
		return getFPacVarManager().createFPacVarAlphaNum(m_varBuffer, nAbsolutePosition1Based, nNbDigitsInteger);
	}
	
	Var createFPacVarRaw(int nAbsolutePosition1Based, int nNbDigitsInteger)
	{
		return getFPacVarManager().createFPacVarRaw(m_varBuffer, nAbsolutePosition1Based, nNbDigitsInteger);
	}

	Var createFPacVarNumIntSignComp3(int nAbsolutePosition1Based, int nBufferLength)
	{
		return getFPacVarManager().createFPacVarNumIntSignComp3(m_varBuffer, nAbsolutePosition1Based, nBufferLength);
	}
	
	Var createFPacVarNumSignComp4(int nAbsolutePosition1Based, int nBufferLength)
	{
		return getFPacVarManager().createFPacVarNumSignComp4(m_varBuffer, nAbsolutePosition1Based, nBufferLength);
	}	

	public void setRecordLengthForced(int nRecordLengthForced)
	{
		m_forcedRecordLengthDefinition = new RecordLengthDefinition(nRecordLengthForced);
	}
	
	public String toString()
	{
		if(m_fileManagerEntry != null)
			return m_fileManagerEntry.toString();
		return "No File Manager";
	}
}
