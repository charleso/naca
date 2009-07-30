/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.basePrgEnv;

import jlib.misc.BaseDataFile;
import jlib.misc.DataFileLineReader;
import jlib.misc.DataFileReadWrite;
import jlib.misc.DataFileWrite;
import jlib.misc.EnvironmentVar;
import jlib.misc.JVMReturnCodeManager;
import jlib.misc.LittleEndingSignBinaryBufferStorage;
import jlib.misc.LogicalFileDescriptor;
import jlib.misc.RecordLengthDefinition;
import jlib.misc.StringUtil;
import nacaLib.base.CJMapObject;
import nacaLib.exceptions.CannotOpenFileException;
import nacaLib.exceptions.FileDescriptorNofFoundException;
import nacaLib.exceptions.InputFileNotFoundException;
import nacaLib.exceptions.TooManyCloseFileException;
import nacaLib.varEx.FileDescriptorOpenStatus;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: FileManagerEntry.java,v 1.28 2007/10/25 15:10:27 u930di Exp $
 */
public class FileManagerEntry extends CJMapObject
{
	public BaseDataFile m_dataFile = null;
	private FileDescriptorOpenStatus m_fileDescriptorOpenStatus = null;
	private LogicalFileDescriptor m_logicalFileDescriptor = null;
	private int m_nNbRecordRead = 0;
	private int m_nNbRecordWrite = 0;

	public FileManagerEntry()
	{
		m_nNbRecordRead = 0;
		m_nNbRecordWrite = 0;
	}
	
	public void setVariableLength()
	{
		m_logicalFileDescriptor.setVariableLength();
	}
		
	public String getPhysicalName(String csLogicalName, BaseSession baseSession)
	{
		m_logicalFileDescriptor = null;
		if(baseSession != null && csLogicalName != null)
		{
			LogicalFileDescriptor logicalFileDescriptor = baseSession.getLogicalFileDescriptor(csLogicalName);
			if(logicalFileDescriptor != null)
			{
				m_logicalFileDescriptor = logicalFileDescriptor;	// Inherit logical file descriptor
			}
			else	// Logical name not already defines
			{			
				String csPhysicalDesc = EnvironmentVar.getParamValue(csLogicalName);
				if(StringUtil.isEmpty(csPhysicalDesc))
					csPhysicalDesc = EnvironmentVar.getParamValue("File_" + csLogicalName);
				if(csPhysicalDesc == null || StringUtil.isEmpty(csPhysicalDesc))
					csPhysicalDesc = csLogicalName;
				m_logicalFileDescriptor = new LogicalFileDescriptor(csLogicalName, csPhysicalDesc);
				baseSession.putLogicalFileDescriptor(csLogicalName, m_logicalFileDescriptor);
			}
		}
		if(m_logicalFileDescriptor != null)
		{
			return m_logicalFileDescriptor.getPath();
		}

//		Log.logCritical("Environnement or Session ERROR: Logical File \'"+m_csLogicalName + "\' has no physical definition");
		throw new FileDescriptorNofFoundException(csLogicalName, null);
		//"Environnement or Session ERROR: Logical File \'"+csLogicalName + "\' has no physical definition"
	}
	
	public boolean isDummyFile()
	{
		if(m_logicalFileDescriptor != null)
			return m_logicalFileDescriptor.isDummyFile();
		return true;
	}
		
	public void reportFileDescriptorStatus(FileDescriptorOpenStatus status)
	{
		m_fileDescriptorOpenStatus = status;
	}
	
		
	void autoClose()
	{
		if(m_fileDescriptorOpenStatus != null)
		{
			if(m_fileDescriptorOpenStatus != FileDescriptorOpenStatus.CLOSE && m_dataFile != null)
			{
				m_fileDescriptorOpenStatus = FileDescriptorOpenStatus.CLOSE;
				m_dataFile.close();
			}
		}
	}
	
	void autoFlush()
	{
		if(m_fileDescriptorOpenStatus != null)
		{
			if(m_fileDescriptorOpenStatus != FileDescriptorOpenStatus.CLOSE && m_dataFile != null)
			{
				m_dataFile.flush();
			}
		}
	}
	
	public RecordLengthDefinition getRecordLengthDefinition()
	{
		return m_logicalFileDescriptor.getRecordLengthDefinition();
	}
	
	public LogicalFileDescriptor getLogicalFileDescriptor()
	{
		return m_logicalFileDescriptor;
	}
	
	public boolean doOpenExtend(String csLogicalName, BaseSession baseSession, boolean bVariableLength)
	{
		boolean bOpened = false;
		if(checkCanOpen())
		{
			getPhysicalName(csLogicalName, baseSession);
			if(isDummyFile())
				return true;
			
			if(bVariableLength)
				setVariableLength();
			
			DataFileWrite dataFile = new DataFileWrite(m_logicalFileDescriptor.getPath(), false);
			m_dataFile = dataFile;
			bOpened = dataFile.openInAppend(m_logicalFileDescriptor);			
			if(!bOpened)
			{
				JVMReturnCodeManager.setExitCode(8);
				CannotOpenFileException e = new CannotOpenFileException(csLogicalName, m_logicalFileDescriptor);
				throw(e);
			}			
			reportFileDescriptorStatus(FileDescriptorOpenStatus.OPEN);
		}
		return bOpened;
	}
	
	public boolean doOpenOutput(String csLogicalName, BaseSession baseSession, boolean bVariableLength, boolean bCanAuthoriseFileHeaderWrite)
	{
		boolean bOpened = false;
		if(checkCanOpen())
		{			
			String csPhysicalFileName = getPhysicalName(csLogicalName, baseSession);
			
			if(m_logicalFileDescriptor.getExt())	// Force extend mode
				return doOpenExtend(csLogicalName, baseSession, bVariableLength);
			
			if(isDummyFile())	// The logical name is dummy: 
				return true;
			
			if(BaseDataFile.isNullFile(csPhysicalFileName))
				bOpened = true;	// Physical outout file is null: Simulte a correct open
			else
			{	
				if(bVariableLength)
					setVariableLength();				
				
				boolean bMustWriteFileHeader = false;
				//if(bCanAuthoriseFileHeaderWrite)
				//	bMustWriteFileHeader = BaseResourceManager.getMustWriteFileHeader(); 
				m_dataFile = new DataFileWrite(m_logicalFileDescriptor.getPath(), bMustWriteFileHeader);
				bOpened = m_dataFile.open(m_logicalFileDescriptor);
			}			
			if(!bOpened)
			{
				JVMReturnCodeManager.setExitCode(8);
				CannotOpenFileException e = new CannotOpenFileException(csLogicalName, m_logicalFileDescriptor);
				throw(e);
			}			
			reportFileDescriptorStatus(FileDescriptorOpenStatus.OPEN);
				
			String csDdname = baseSession.getDynamicAllocationInfo("DDNAME");
			if (csDdname != null && csDdname.equals(csLogicalName))
			{
				if (baseSession.getDynamicAllocationInfo("SYSOUT") != null)
				{
					// InfoPrint Manager
					StringBuffer sb = new StringBuffer();
					// StringUtil.rightPad(jobId, 8, ' ')
					sb.append("#300#");
					sb.append("PPSSSCCCAANNYYYYMMDDHHMMSSC");
					sb.append(StringUtil.leftPad(baseSession.getDynamicAllocationInfo("COPIES"), 3, '0'));  // Nb copies supplémentaires
					sb.append(StringUtil.rightPad(baseSession.getDynamicAllocationInfo("SYSOUT").substring(9), 4, ' '));  // No de formulaire
					sb.append(StringUtil.rightPad(baseSession.getDynamicAllocationInfo("DEST"), 8, ' '));  // Nom imprimante
					sb.append(StringUtil.rightPad(baseSession.getDynamicAllocationInfo("SYSOUT").substring(0, 1), 1, ' '));  // Classe impression
					sb.append(StringUtil.rightPad(baseSession.getDynamicAllocationInfo("BURST"), 1, ' '));  // Burst
					sb.append(StringUtil.rightPad("", 4, ' '));  // Flash
					sb.append(StringUtil.rightPad(baseSession.getDynamicAllocationInfo("CHARS"), 16, ' ')); // Chars
					sb.append(StringUtil.rightPad(baseSession.getDynamicAllocationInfo("PAGEDEF"), 6, ' '));  // Pagedef
					sb.append(StringUtil.rightPad(baseSession.getDynamicAllocationInfo("FORMDEF"), 6, ' '));  // Formdef
					sb.append(StringUtil.rightPad(baseSession.getDynamicAllocationInfo("HOLD"), 1, ' '));  // Hold
					sb.append(StringUtil.leftPad(baseSession.getDynamicAllocationInfo("PRTY"), 2, '0'));  // Priority
					if (isVariableLength())
					{
						byte[] tbyHeader = new byte[4];
						LittleEndingSignBinaryBufferStorage.writeInt(tbyHeader, sb.length(), 0);
						m_dataFile.write(tbyHeader, 0, 4);
					}
					m_dataFile.writeRecord(sb.toString());
				}
				baseSession.resetDynamicAllocationInfo();
			}
		}
		return bOpened;
	}
	
	public boolean doOpenInput(String csLogicalName, BaseSession baseSession, boolean bVariableLength)
	{		
		boolean bOpened = false;
		if(checkCanOpen())
		{
			getPhysicalName(csLogicalName, baseSession);
			if(isDummyFile())
				return true;
			
			if(bVariableLength)
				setVariableLength();
			
			m_dataFile = new DataFileLineReader(m_logicalFileDescriptor.getPath(), 65536, 0);
			bOpened = m_dataFile.open(m_logicalFileDescriptor);
			if(!bOpened)
			{				
				JVMReturnCodeManager.setExitCode(8);
				InputFileNotFoundException e = new InputFileNotFoundException(csLogicalName, m_logicalFileDescriptor);
				throw(e);
			}
			reportFileDescriptorStatus(FileDescriptorOpenStatus.OPEN);
		}
		else
		{
			JVMReturnCodeManager.setExitCode(8);
			CannotOpenFileException e = new CannotOpenFileException(csLogicalName, m_logicalFileDescriptor);
			throw(e);
		}
		return bOpened;
	}
	
	public boolean doOpenInputOutput(String csLogicalName, BaseSession baseSession, boolean bVariableLength)
	{
		boolean bOpened = false;
		if(checkCanOpen())
		{
			getPhysicalName(csLogicalName, baseSession);
			if(isDummyFile())
				return true;
			
			if(bVariableLength)
				setVariableLength();
			
			m_dataFile = new DataFileReadWrite(m_logicalFileDescriptor.getPath());
			bOpened = m_dataFile.open(m_logicalFileDescriptor);
			if(!bOpened)
			{
				JVMReturnCodeManager.setExitCode(8);
				InputFileNotFoundException e = new InputFileNotFoundException(csLogicalName, m_logicalFileDescriptor);
				throw(e);
			}
			reportFileDescriptorStatus(FileDescriptorOpenStatus.OPEN);
		}
		return bOpened;
	}
	
	public boolean doClose(String csLogicalName, BaseSession baseSession)
	{
		if(isDummyFile())
			return true;
		
		if(checkCanClose())
		{
			m_dataFile.close();
			m_dataFile = null;
			baseSession.removeLogicalFileDescriptor(csLogicalName);
			return true;
		}
		
		TooManyCloseFileException e = new TooManyCloseFileException();
		throw e;
	}
	
	private boolean checkCanOpen()
	{
		if(m_dataFile == null)
			return true;
		return false;
	}
	
	private boolean checkCanClose()
	{
		if(m_dataFile != null && m_dataFile.isOpen())
			return true;
		return false;
	}
	
	public boolean isEbcdic()
	{
		return m_logicalFileDescriptor.isEbcdic();
	}
	
	public boolean isVariableLength()
	{
		return m_logicalFileDescriptor.isVariableLength();
	}
	
	public boolean isVariableLength4BytesHeaderWithLF()
	{
		return m_logicalFileDescriptor.isVariableLength4BytesHeaderWithLF();
	}
	
	public BaseDataFile getDataFile()
	{
		return m_dataFile; 
	}
	
	public String toString()
	{
		if(m_logicalFileDescriptor != null)
			return m_logicalFileDescriptor.toString();
		return "Unknown LogicalFileDescriptor";
	}
	
	public void inheritSettings(FileManagerEntry source)
	{
		m_logicalFileDescriptor.inheritSettings(source.m_logicalFileDescriptor);
	}
	
	public void incNbRecordRead()
	{
		m_nNbRecordRead++;
	}

	public void incNbRecordWrite()
	{
		m_nNbRecordWrite++;
	}
	
	public String dumpRWStat()
	{
		String cs;
		if(m_logicalFileDescriptor != null)
			cs = m_logicalFileDescriptor.getName();
		else
			cs = "Unknown logicalFileDescriptor ";
		cs += "Read=" + m_nNbRecordRead + " / Write=" + m_nNbRecordWrite; 
		return cs;
	}

	public boolean isEOF()
	{
		if(m_dataFile != null)
			return m_dataFile.isEOF();
		return true;
	}
}
