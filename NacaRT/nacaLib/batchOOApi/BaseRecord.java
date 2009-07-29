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
package nacaLib.batchOOApi;

import jlib.misc.BaseDataFile;
import jlib.misc.RecordLengthDefinition;
import nacaLib.varEx.FileDescriptor;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public abstract class BaseRecord
{
	private FileDescriptor m_file = null;
	private FillerReadWriteExt m_filler = new FillerReadWriteExt();
		
	protected BaseRecord(FileDescriptor file)
	{
		m_file = file;
	}
	
	public FillerReadWriteExt getFiller()
	{
		return m_filler;
	}
	
	/**
	 * write: public write method; can be use by application code  
	 */
	public void write()
	{
		getFiller().setMode(ModeReadWriteExt.Write);
		getFiller().allocOrResetBufferExt();
		
		fillRW();
		if (getFiller().getVariableChunkLength() != -1)	// The record has a variable length
			m_file.write(getFiller().getBuffer(), true);
		else
			m_file.write(getFiller().getBuffer(), false);
		
		getFiller().setMode(ModeReadWriteExt.Unknown);
	}
	
	public void rewrite()
	{
		getFiller().setMode(ModeReadWriteExt.Write);
		getFiller().allocOrResetBufferExt();
		
		fillRW();
		m_file.rewrite(getFiller().getBuffer());
		
		getFiller().setMode(ModeReadWriteExt.Unknown);
	}

	public boolean read()
	{
		if (m_file != null)
		{
			getFiller().setMode(ModeReadWriteExt.Read);
			RecordLengthDefinition recordLengthDefinition = m_file.getRecordLengthDefinition();
			if(recordLengthDefinition == null)
			{
				BaseDataFile dataFileIn = m_file.getBaseDataFile();
				m_file.tryAutoDetermineRecordLengthIfRequired(dataFileIn);
			}
			
			getFiller().allocOrResetBufferExt();
			boolean bRead = m_file.read(getFiller().getBuffer());
			if (bRead)
				fillRW();
			
			getFiller().setMode(ModeReadWriteExt.Unknown);
			return bRead;
		}
		return false;
	}
	
	public abstract void fillRW();
}
