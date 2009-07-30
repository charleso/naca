/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

import jlib.misc.BaseDataFile;
import jlib.misc.BaseDataFileBuffered;
import jlib.misc.EnvironmentVar;
import jlib.misc.LineRead;
import jlib.misc.LittleEndingSignBinaryBufferStorage;
import jlib.misc.LogicalFileDescriptor;
import jlib.misc.RecordLengthDefinition;
import jlib.misc.StringUtil;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.batchOOApi.WriteBufferExt;

public class FileDescriptor extends BaseFileDescriptor
{
	private static final int PAGE_LINES = 60;
	private VarDefEncodingConvertibleManagerContainer m_varDefEncodingConvertibleManagerContainer = null;
	private byte[] m_tbyHeader = null;
	private Var status;
	private int count;
	
	public FileDescriptor(String csLogicalName)
	{
		super(null, csLogicalName);
	}
	
	public FileDescriptor(String csLogicalName, BaseSession session)
	{
		super(null, csLogicalName);
		setSession(session);
	}
	
	public FileDescriptor(BaseEnvironment env, String csLogicalName)
	{
		super(env, csLogicalName);
	}
		
	public static boolean isExistingFileDescriptor(String csLogicalName, BaseSession baseSession)
	{
		if(baseSession != null && csLogicalName != null)
		{
			LogicalFileDescriptor logicalFileDescriptor = baseSession.getLogicalFileDescriptor(csLogicalName);
			if(logicalFileDescriptor != null)
			{
				return true;
			}
			else	// Logical name not already defines
			{
			
				String csPhysicalDesc = EnvironmentVar.getParamValue(csLogicalName);
				if(StringUtil.isEmpty(csPhysicalDesc))
					csPhysicalDesc = EnvironmentVar.getParamValue("File_" + csLogicalName);
				if(csPhysicalDesc != null && !StringUtil.isEmpty(csPhysicalDesc))
					return true;
			}
		}
		return false;
	}	
	
	public FileDescriptor status(Var status)
	{
		this.status = status;
		return this;
	}
	
	public void inheritSettings(FileDescriptor fileDescSource)
	{
		m_fileManagerEntry.inheritSettings(fileDescSource.m_fileManagerEntry);
	}

	public boolean isEbcdic()
	{
		return m_fileManagerEntry.isEbcdic();
	}
	
	public boolean isVariableLength()
	{
		return m_fileManagerEntry.isVariableLength();
	}
	
	public boolean isVariableLength4BytesHeaderWithLF()
	{
		return m_fileManagerEntry.isVariableLength4BytesHeaderWithLF();
	}
	
	public RecordLengthDefinition getRecordLengthDefinition()
	{
		return m_fileManagerEntry.getRecordLengthDefinition();
	}

	public FileDescriptor lengthDependingOn(Var varLengthDependingOn)
	{
		setVarLengthDependingOn(varLengthDependingOn);
		return this;
	}
	
	public void write()
	{
		writeFrom(m_varLevel01, false);
	}
	
	public void writeAfter(int after)
	{
		after(after);
		writeFrom(m_varLevel01, false);
	}

	private void after(int after)
	{
		if (after < 0)
		{
			after = (PAGE_LINES - (count % PAGE_LINES)) % PAGE_LINES;
			after++;
		}
		after--;
		for (int i = 0; i < after; i++)
		{
			m_fileManagerEntry.m_dataFile.writeWithEOL(new byte[0], 0);
			incNbRecordWrite();
		}
	}
	
	public void writeFrom(VarBase varWorking)
	{
		writeFrom(varWorking, false);
	}
	
	public void rewrite()
	{
		writeFrom(m_varLevel01, true);
	}
	
	public void rewriteFrom(VarBase varWorking)
	{
		writeFrom(varWorking, true);
	}
	
	private void writeFrom(VarBase varFrom, boolean bRewriteMode)
	{
		if(m_fileManagerEntry.isDummyFile())
			return ;

		VarBase varLevel01 = m_varLevel01;
		if(varLevel01 == null)
			varLevel01 = varFrom;
		
		int nRecordSize = varLevel01.getTotalSize();
		int nVarFromSize = nRecordSize; 
		int nMinSize = nRecordSize;
		int nMaxSize = nRecordSize;
		
		if(varLevel01 != varFrom)
		{
			nVarFromSize = varFrom.getTotalSize();			
			if(nRecordSize <= nVarFromSize)
			{
				nMinSize = nRecordSize;
				nMaxSize = nVarFromSize;
			}
			else
			{
				varLevel01.fill(CobolConstant.LowValue);
				nMinSize = nVarFromSize;
				nMaxSize = nRecordSize;
			}
		}
		
		// Move bytes of working into record, up to record length
		byte tbyFilebuffer[] = m_fileManagerEntry.m_dataFile.getByteBuffer(nMaxSize);
		varFrom.exportToByteArray(tbyFilebuffer, nVarFromSize);
		if (varLevel01 != varFrom)
			varLevel01.setFromByteArray(tbyFilebuffer, 0, nMinSize);	// Used when record buffer is longer than working buffer; we must keep the right part of the record at the initialized values
		
		if(m_fileManagerEntry.isEbcdic())	// Must convert string chunks
		{
			if(m_varDefEncodingConvertibleManagerContainer == null)
				m_varDefEncodingConvertibleManagerContainer = new VarDefEncodingConvertibleManagerContainer();
			m_varDefEncodingConvertibleManagerContainer.getConvertedBytesAsciiToEbcdic(varLevel01, tbyFilebuffer, nMaxSize);
		}
		else
		{
			varLevel01.exportToByteArray(tbyFilebuffer, nRecordSize);
		}
		

		// Write varLevel01 
		if(m_fileManagerEntry.isVariableLength())
		{
			int nRecordLength = getRecordLength(varLevel01);	// Measure record length
			// write record header
			if(m_tbyHeader == null)
				m_tbyHeader = new byte[4];
			LittleEndingSignBinaryBufferStorage.writeInt(m_tbyHeader, nRecordLength, 0);	// DO not include header length in header !
			if(bRewriteMode)
				m_fileManagerEntry.m_dataFile.rewrite(m_tbyHeader, 0, 4);
			else
				m_fileManagerEntry.m_dataFile.write(m_tbyHeader, 0, 4);
			
			m_fileManagerEntry.m_dataFile.writeWithEOL(tbyFilebuffer, nRecordLength);
			incNbRecordWrite();
		}
		else
		{
			if(bRewriteMode)
				m_fileManagerEntry.m_dataFile.rewriteWithEOL(tbyFilebuffer, nRecordSize);
			else
				m_fileManagerEntry.m_dataFile.writeWithEOL(tbyFilebuffer, nRecordSize);
			incNbRecordWrite();
		}
		if(status != null)
			status.set("00");
	}
	
	@Override
	protected void incNbRecordWrite() {
		count++;
		super.incNbRecordWrite();
	}
	
	public byte [] getWriteBuffer(int nMaxSize)
	{
		byte tbyFilebuffer[] = m_fileManagerEntry.m_dataFile.getByteBuffer(nMaxSize);
		return tbyFilebuffer;
	}
	
	public void writeFrom(LineRead lineRead)
	{
		m_fileManagerEntry.m_dataFile.writeWithEOL(lineRead);
	}
	
	public RecordDescriptorAtEnd read()
	{
		return readInto(m_varLevel01);
	}
	
	private void convertEbcdicToAsciiAndWrite(LineRead lineRead, Var varDest)
	{
		if(m_varDefEncodingConvertibleManagerContainer == null)
			m_varDefEncodingConvertibleManagerContainer = new VarDefEncodingConvertibleManagerContainer();

		if(m_varLevel01 != varDest)
			m_varDefEncodingConvertibleManagerContainer.getEncodingManagerConvertAndWrite(lineRead, varDest);
		else
			m_varDefEncodingConvertibleManagerContainer.getEncodingManagerConvertAndWrite(lineRead, m_varLevel01);
	}
	
	private void convertEbcdicToAsciiAndWrite(LineRead lineRead)
	{
		if(m_varDefEncodingConvertibleManagerContainer == null)
			m_varDefEncodingConvertibleManagerContainer = new VarDefEncodingConvertibleManagerContainer();

		m_varDefEncodingConvertibleManagerContainer.getEncodingManagerConvertAndWrite(lineRead, m_varLevel01);
	}

	public RecordDescriptorAtEnd readInto(Var varDest)
	{
		if(m_fileManagerEntry.isDummyFile())
			return RecordDescriptorAtEnd.End;

		if(hasVarVariableLengthMarker())
		{
			long lLastHeaderStartPosition = m_fileManagerEntry.m_dataFile.getFileCurrentPosition();	// Keep header start position
			LineRead header = m_fileManagerEntry.m_dataFile.readBuffer(4, false);		// Read header
			if(header != null)
			{				
				int nLengthExcludingHeader = header.getAsLittleEndingUnsignBinaryInt();	// Length in header doesn't count the header itself
				LineRead lineRead = m_fileManagerEntry.m_dataFile.readBuffer(nLengthExcludingHeader, true);		// Read record body, including trailing LF
				m_fileManagerEntry.m_dataFile.setLastPosition(lLastHeaderStartPosition);	// Save current position at the header start
				if(lineRead != null)
				{
					fillInto(lineRead, varDest);
					int nVariableRecordLength = getVariableRecordLength(nLengthExcludingHeader);
					fillVarLengthDependingOn(nVariableRecordLength);
					return RecordDescriptorAtEnd.NotEnd;
				}
			}
			return RecordDescriptorAtEnd.End;
		}
		else
		{
			int nRecordLength = getRecordLength(m_varLevel01);
			LineRead lineRead;
			if(nRecordLength > 0)
			{
				lineRead = m_fileManagerEntry.m_dataFile.readBuffer(nRecordLength, true);	// PJD TO UNCOMMENT 
				//lineRead = ((DataFileLineReader)m_fileManagerEntry.m_dataFile).readDirect(nRecordLength);
			}
				
			else
				lineRead = m_fileManagerEntry.m_dataFile.readNextUnixLine();				
			if(lineRead != null)
			{
				fillInto(lineRead, varDest);
				return RecordDescriptorAtEnd.NotEnd;
			}
			return RecordDescriptorAtEnd.End;
		}
	}
	
	private void fillInto(LineRead lineRead, Var varDest)
	{
		incNbRecordRead();
		if (m_varLevel01 != varDest)
		{
			if (m_fileManagerEntry.isEbcdic())
				fillInto2DestEbcdic(lineRead, varDest);
			else
				varDest.setFromLineRead2DestWithFilling(lineRead, m_varLevel01);
		}
		else
		{			
			// m_varLevel01 == varDest: Not a readInto()
			if (m_fileManagerEntry.isEbcdic())
			{
				varDest.fill(CobolConstant.LowValue);
				convertEbcdicToAsciiAndWrite(lineRead);
			}
			else
			{
				int nRecordSize = m_varLevel01.getTotalSize();
				int nNbByteWritten = m_varLevel01.setFromLineRead(lineRead);
				if(nRecordSize > nNbByteWritten) 
					varDest.fillEndOfRecord(nNbByteWritten, nRecordSize);
			}
		}
	}
	
	private void fillInto2DestEbcdic(LineRead lineRead, Var varDest)
	{
		varDest.fill(CobolConstant.LowValue);
		m_varLevel01.fill(CobolConstant.LowValue);			

		int nRecordSize = m_varLevel01.getTotalSize();
		int nDestSize = varDest.getTotalSize();
		if (nRecordSize > nDestSize)
			nRecordSize = nDestSize;

		convertEbcdicToAsciiAndWrite(lineRead, varDest);
		
		byte tbyFilebuffer[] = m_fileManagerEntry.m_dataFile.getByteBuffer(nRecordSize);
		varDest.exportToByteArray(tbyFilebuffer, nRecordSize);
		m_varLevel01.setFromByteArray(tbyFilebuffer, 0, nRecordSize);
	}
	
//	private void fillInto(LineRead lineRead, Var varDest)
//	{
//		varDest.fill(CobolConstant.LowValue);
//		if (m_varLevel01 != varDest)
//			m_varLevel01.fill(CobolConstant.LowValue);			
//		
//		int nRecordSize = m_varLevel01.getTotalSize();
//		if (m_varLevel01 != varDest)
//		{
//			int nDestSize = varDest.getTotalSize();
//			if (nRecordSize > nDestSize)
//				nRecordSize = nDestSize;
//		}
//
//		if (m_fileManagerEntry.isEbcdic())
//			convertEbcdicToAsciiAndWrite(lineRead, varDest);
//		else
//			noConvertEbcdicToAsciiAndWrite(lineRead, varDest);
//
//		if (m_varLevel01 != varDest)
//		{
//			byte tbyFilebuffer[] = m_fileManagerEntry.m_dataFile.getByteBuffer(nRecordSize);
//			varDest.exportToByteArray(tbyFilebuffer, nRecordSize);
//			m_varLevel01.setFromByteArray(tbyFilebuffer, 0, nRecordSize);
//		}
//	}


	public String toString()
	{
		if(m_fileManagerEntry != null)
		{
			String cs = m_fileManagerEntry.toString();
			return cs + " mapped on " + m_varLevel01.toString();
		}
		return "Unknown FileManagerEntry";
	}
	
	public LineRead readALine(BaseDataFileBuffered dataFileIn, LineRead lastLineRead)
	{
		if(m_fileManagerEntry.isDummyFile())
			return null;

		if(isVariableLength())
		{
			boolean bReadLF = isVariableLength4BytesHeaderWithLF();
			boolean bHeader4Bytes = isVariableLength4BytesHeaderWithLF();
			lastLineRead = dataFileIn.readVariableLengthLine(bReadLF, bHeader4Bytes, lastLineRead);	// Read a vairable length line (length is given in record header 4 bytes)
		}
		else
		{
			RecordLengthDefinition recordLengthDefinition = getRecordLengthDefinition();
			if(recordLengthDefinition == null)	// No record length defined by the input file descriptor
				lastLineRead = dataFileIn.readNextUnixLine();
			else
			{
				int nRecordLength = recordLengthDefinition.getRecordLength();
				lastLineRead = dataFileIn.readBuffer(nRecordLength, true);
			}
		}
		return lastLineRead;
	}
	
	public LogicalFileDescriptor getLogicalFileDescriptor()
	{
		if(m_fileManagerEntry != null)
			return m_fileManagerEntry.getLogicalFileDescriptor();
		return null;
	}
	
	public void tryAutoDetermineRecordLengthIfRequired(BaseDataFile dataFileIn)
	{
		// the return value is a flag that indicates if we have a valid file position on output 
		if(isVariableLength())
			return ;	// We are a variable length file: no need to try to autodetermine record length; file position is valid
		if(getRecordLengthDefinition() != null)
			return ;	// we have the record definition: no need to try to autodetermine record length; file position is valid
		
		// We must try to autodetermine record length
		LogicalFileDescriptor logicalFileDescriptor = getLogicalFileDescriptor();
		if(logicalFileDescriptor != null)
			logicalFileDescriptor.tryAutoDetermineRecordLength(dataFileIn);
	}
	
	// New OO API support 
	public void write(WriteBufferExt writeBufferExt, boolean bForcedVariableLenght)
	{
		if(m_fileManagerEntry.isVariableLength() || bForcedVariableLenght)
		{
			int nRecordLength = writeBufferExt.getRecordCurrentPosition();	// Measure record length
			// write record header
			if(m_tbyHeader == null)
				m_tbyHeader = new byte[4];
			LittleEndingSignBinaryBufferStorage.writeInt(m_tbyHeader, nRecordLength, 0);	// DO not include header length in header !
			m_fileManagerEntry.m_dataFile.write(m_tbyHeader, 0, 4);
			
			byte tbyFilebuffer[] = writeBufferExt.getAsByteArrayWithTrailingLF();
			m_fileManagerEntry.m_dataFile.writeWithEOL(tbyFilebuffer, tbyFilebuffer.length);

			incNbRecordWrite();
		}
		else
		{
			byte tbyFilebuffer[] = writeBufferExt.getAsByteArrayWithTrailingLF();
			m_fileManagerEntry.m_dataFile.writeWithEOL(tbyFilebuffer, tbyFilebuffer.length);
			incNbRecordWrite();
		}
	}
	
	public void rewrite(WriteBufferExt writeBufferExt)
	{
		byte tbyFilebuffer[] = writeBufferExt.getAsByteArrayWithTrailingLF();
		m_fileManagerEntry.m_dataFile.rewriteWithEOL(tbyFilebuffer, tbyFilebuffer.length);
		incNbRecordWrite();
	}
				
	public boolean read(WriteBufferExt writeExt)
	{
		if(m_fileManagerEntry.isDummyFile())
			return false;

		if(isVariableLength())
		{
			long lLastHeaderStartPosition = m_fileManagerEntry.m_dataFile.getFileCurrentPosition();	// Keep header start position
			LineRead header = m_fileManagerEntry.m_dataFile.readBuffer(4, false);		// Read header
			if(header != null)
			{				
				int nLengthExcludingHeader = header.getAsLittleEndingUnsignBinaryInt();	// Length in header doesn't count the header itself
				LineRead lineRead = m_fileManagerEntry.m_dataFile.readBuffer(nLengthExcludingHeader, true);		// Read record body, including trailing LF
				m_fileManagerEntry.m_dataFile.setLastPosition(lLastHeaderStartPosition);	// Save current position at the header start
				if(lineRead != null)
				{
					writeExt.setFromLineRead(lineRead, 0);	
					int n = getVariableRecordLength(nLengthExcludingHeader);
					writeExt.setVariableRecordWholeLength(n);
					return true;
				}
			}
			return false;
		}
		else
		{
			if(m_fileManagerEntry.m_dataFile.isEOF())
				return false;
			int nRecordLength = getRecordLength(null);
			LineRead lineRead = null;
			if(nRecordLength > 0)
				lineRead = m_fileManagerEntry.m_dataFile.readBuffer(nRecordLength, true);	// PJD TO UNCOMMENT 
			else
				lineRead = m_fileManagerEntry.m_dataFile.readNextUnixLine();
			if(lineRead != null)
			{
				writeExt.setFromLineRead(lineRead, 0);	
				incNbRecordRead();
				return true;
			}
			return false;
		}
	}
}
