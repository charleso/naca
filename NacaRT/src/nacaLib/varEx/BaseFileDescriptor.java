/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

import jlib.misc.BaseDataFile;
import jlib.misc.FileEndOfLine;
import jlib.misc.RecordLengthDefinition;
import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.basePrgEnv.FileManagerEntry;

public abstract class BaseFileDescriptor extends CJMapObject
{	
	private BaseSession m_baseSession = null;
	protected FileManagerEntry m_fileManagerEntry = null; 
	private String m_csLogicalName = null;
	protected Var m_varLevel01 = null;
	private Var m_varVariableLengthMarker = null;
	private Var m_varLengthDependingOn = null;	// Variable declared by a FileDescriptorDepending: it gives the variable whose value give the length of the dynamic part of the record
	private int m_nSizeConstantRecordLength = 0;
	private int m_nSizeOccursDependingOn = 1;
	
	BaseFileDescriptor()
	{
	}
	
	public BaseFileDescriptor(BaseEnvironment env, String csLogicalName)
	{
		m_csLogicalName = csLogicalName;
		if(env != null)
			m_fileManagerEntry = env.getFileManagerEntry(csLogicalName);
		else
			m_fileManagerEntry = new FileManagerEntry();
	}
		
	public void restoreFileManagerEntry(FileManagerEntry fileManagerEntry)
	{
		m_fileManagerEntry = fileManagerEntry;
	}

	public String getLogicalName()
	{
		return m_csLogicalName;
	}
	
	public void setRecordStruct(Var varLevel01)
	{
		m_varLevel01 = varLevel01;
	}
	
	public void setVarVariableLengthMarker(Var var)
	{
		m_varVariableLengthMarker = var;
	}
	
	private void computeSizeConstantRecordLength()
	{
		if(m_varVariableLengthMarker != null && m_varLevel01 != null)
		{
			int nPosFixRecordStart = m_varLevel01.getAbsolutePosition();
			int nPosVariableRecordStart = m_varVariableLengthMarker.getAbsolutePosition(); 
			m_nSizeConstantRecordLength = nPosVariableRecordStart - nPosFixRecordStart;
			m_nSizeOccursDependingOn = m_varVariableLengthMarker.getAt(1).getTotalSize();
		}
		else
		{	
			m_nSizeConstantRecordLength = 0;
			m_nSizeOccursDependingOn = 1;
		}	
	}
	
	int getConstantRecordSize()
	{
		return m_nSizeConstantRecordLength;
	}
	
	int getOccursDependingOnRecordSize()
	{
		return m_nSizeOccursDependingOn;
	}
		
	int getTotalRecordSize()
	{
		if(m_varLevel01 != null)
			return m_varLevel01.getTotalSize();
		return 0;
	}
	
	int getVariableRecordLength(int nTotalRecordLength)
	{
		return (nTotalRecordLength - m_nSizeConstantRecordLength) / getOccursDependingOnRecordSize();
	}
		
	protected int getRecordLength(VarBase varSource)
	{
		if(m_varLengthDependingOn != null)
		{
			return getConstantRecordSize() + (m_varLengthDependingOn.getInt() * getOccursDependingOnRecordSize());
		}

		if (m_fileManagerEntry != null)
		{	
			RecordLengthDefinition recordLengthDefinition = m_fileManagerEntry.getRecordLengthDefinition();
			if(recordLengthDefinition != null)	// No record length defined by the FileDescriptor
				return recordLengthDefinition.getRecordLength();
		}
		if(varSource != null)
		{
			return varSource.getTotalSize();	// Get record length from structure
		}
		return 0;
	}
	
	public boolean hasVarVariableLengthMarker()
	{
		if(m_varVariableLengthMarker != null || m_varLengthDependingOn != null)
			return true;
		return false;
	}
	
	void fillVarLengthDependingOn(int nVariableRecordLength)
	{
		if(m_varLengthDependingOn != null)
			m_varLengthDependingOn.set(nVariableRecordLength);
	}
	
	protected void setVarLengthDependingOn(Var varLengthDependingOn)
	{
		m_varLengthDependingOn = varLengthDependingOn;
	}
	
	public BaseFileDescriptor openOutputNoFileHeaderWrite()
	{
		return doOpenOutput(false);
	}
	
	public BaseFileDescriptor openOutput()
	{
		return doOpenOutput(true);
	}
	
	private BaseFileDescriptor doOpenOutput(boolean bCanAuthoriseFileHeaderWrite)
	{
		boolean bVariableLength = false;
		if(hasVarVariableLengthMarker())
			bVariableLength = true;
		
		boolean bOpened = m_fileManagerEntry.doOpenOutput(m_csLogicalName, m_baseSession, bVariableLength, bCanAuthoriseFileHeaderWrite);
		if(bOpened)
			return this;
		return null;
	}	
	
	public BaseFileDescriptor openInputOutput()
	{
		boolean bVariableLength = false;
		if(hasVarVariableLengthMarker())
			bVariableLength = true;

		boolean bOpened = m_fileManagerEntry.doOpenInputOutput(m_csLogicalName, m_baseSession, bVariableLength);
		if(bOpened)
			return this;
		return null;
	}
	
	public BaseFileDescriptor openInput()
	{
		boolean bVariableLength = false;
		if(hasVarVariableLengthMarker())
			bVariableLength = true;
		
		boolean bOpened = m_fileManagerEntry.doOpenInput(m_csLogicalName, m_baseSession, bVariableLength);
		if(bOpened)
			return this;
		return null;
	}
	
	public BaseDataFile getBaseDataFile()
	{
		if(m_fileManagerEntry != null)
			if(m_fileManagerEntry.m_dataFile != null)
				return m_fileManagerEntry.m_dataFile;
		return null;
	}
	

	public BaseFileDescriptor openExtend()
	{
		boolean bVariableLength = false;
		if(hasVarVariableLengthMarker())
			bVariableLength = true;
		
		boolean bOpened = m_fileManagerEntry.doOpenExtend(m_csLogicalName, m_baseSession, bVariableLength);
		if(bOpened)
			return this;
		return null;
	}
	
	public void close()
	{
		boolean b = m_fileManagerEntry.doClose(m_csLogicalName, m_baseSession);
		if(b)
			m_fileManagerEntry.reportFileDescriptorStatus(FileDescriptorOpenStatus.CLOSE);
	}
	
	public void write(byte[] tBytes, int nOffset, int nLength, boolean bWriteEndOfRecordMarker)
	{
		m_fileManagerEntry.m_dataFile.write(tBytes, nOffset, nLength);
		if(bWriteEndOfRecordMarker)
			m_fileManagerEntry.m_dataFile.writeEndOfRecordMarker();			
	}
	
	public void setSession(BaseSession baseSession)
	{
		m_baseSession = baseSession;
		computeSizeConstantRecordLength();
	}
		
	public String getPhysicalName()
	{
		return m_fileManagerEntry.getPhysicalName(m_csLogicalName, m_baseSession);
	}
	
	public String getEbcdic()
	{
		return m_fileManagerEntry.getPhysicalName(m_csLogicalName, m_baseSession);
	}
	
	public boolean isEbcdic()
	{
		return m_fileManagerEntry.isEbcdic();
	}
		
	public BaseDataFile getDataFile()
	{
		if(m_fileManagerEntry != null)
			return m_fileManagerEntry.getDataFile();
		return null;
	}
	
	protected void incNbRecordRead()
	{
		if(m_fileManagerEntry != null)
			m_fileManagerEntry.incNbRecordRead();
	}

	
	protected void incNbRecordWrite()
	{
		if(m_fileManagerEntry != null)
			m_fileManagerEntry.incNbRecordWrite();
	}
	
	public boolean isEOF()
	{
		if(m_fileManagerEntry != null)
			return m_fileManagerEntry.isEOF();
		return true;
	}
}

