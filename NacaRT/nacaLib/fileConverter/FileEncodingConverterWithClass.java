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
package nacaLib.fileConverter;

import jlib.log.Log;
import jlib.misc.DataFileLineReader;
import jlib.misc.LineRead;
import jlib.misc.LittleEndingSignBinaryBufferStorage;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.classLoad.CustomClassDynLoaderFactory;
import nacaLib.tempCache.TempCacheLocator;
import nacaLib.varEx.*;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class FileEncodingConverterWithClass extends FileEncodingConverter
{
	public FileEncodingConverterWithClass(FileDescriptor fileIn, FileDescriptor fileOut)
	{
		super(fileIn, fileOut);
	}
	
	public boolean execute(String csCopyClass)
	{
		m_fileIn.getPhysicalName();
		m_fileOut.getPhysicalName();
		if(m_fileIn.isEbcdic() != m_fileOut.isEbcdic() || m_bHost)
		{
			return convert(csCopyClass);
		}
		else
		{
			return copyFile();
		}
	}

	private boolean convert(String csCopyClass)
	{
		boolean bEbcdicIn = m_fileIn.isEbcdic();
		boolean bEbcdicOut = m_fileOut.isEbcdic();

		BaseResourceManager.initCopyConverterClassLoader();
		
		TempCacheLocator.setTempCache();	// Init TLS
		ConverterProgram converterProgram = new ConverterProgram();
		Object obj = CopyConverterClassLoader.getInstance(csCopyClass, CustomClassDynLoaderFactory.getInstance(), converterProgram);
		if(obj == null)
		{
			Log.logCritical("Cannot load Copy class " + csCopyClass);
			return false;
		}
			
		// Compute the working
		BaseProgramManager converterProgramManager = converterProgram.getProgramManager();
		converterProgramManager.prepareCall(null, converterProgram, null, null, true);
		
		VarBase varRoot = converterProgram.getProgramManager().getRoot();
		VarDefEncodingConvertibleManagerContainer varDefEncodingConvertibleManagerContainer = new VarDefEncodingConvertibleManagerContainer();
		
		// Read all record form source into structure
		String csFileIn = m_fileIn.getPhysicalName();
		DataFileLineReader dataFileIn = new DataFileLineReader(csFileIn, 65536, 0);
		boolean bInOpened = dataFileIn.open();
		if(bInOpened)
		{
			m_fileOut.openOutput();
			boolean bVariableLength = m_fileIn.isVariableLength();
			if (m_bHost)
			{
				if (m_bHeaderEbcdic)
				{
					byte[] tbyHeaderEbcdic = new String("<FileHeader Version=\"1\" Encoding=\"ebcdic\"/>").getBytes();
					m_fileOut.write(tbyHeaderEbcdic, 0, tbyHeaderEbcdic.length, true);
				}

				if (m_nLengthRecord == 0)
				{
					byte[] tbyHeader4 = new byte[4];
					if (m_bVariable4)
					{	
						LineRead lineRead = dataFileIn.readBuffer(4, false);
						while (lineRead != null)
						{
							int i1 = lineRead.getBuffer()[lineRead.getOffset()];
							if (i1 < 0) i1 = 256 + i1;
							int i2 = lineRead.getBuffer()[lineRead.getOffset() + 1];
							if (i2 < 0) i2 = 256 + i2;
							int nCurrentRecordLength = (i1 * 256) + i2 - 4;
							
							if (bVariableLength)
							{
								LittleEndingSignBinaryBufferStorage.writeInt(tbyHeader4, nCurrentRecordLength, 0);
								m_fileOut.write(tbyHeader4, 0, tbyHeader4.length, false);
							}
							
							lineRead = dataFileIn.readBuffer(nCurrentRecordLength, false);
							if (bEbcdicIn && !bEbcdicOut)
								varDefEncodingConvertibleManagerContainer.getEncodingManagerConvertAndWrite(lineRead, varRoot);
							else
								varRoot.setFromLineRead(lineRead);
							m_fileOut.writeFrom(varRoot);
							m_fileOut.getBaseDataFile().writeEndOfRecordMarker();
							lineRead = dataFileIn.readBuffer(4, false);
						}
					}
					else
					{
						LineRead lineRead = dataFileIn.readBuffer(3, false);
						while (lineRead != null)
						{
							int i1 = lineRead.getBuffer()[lineRead.getOffset() + 1];
							int i2 = lineRead.getBuffer()[lineRead.getOffset() + 2];
							int nCurrentRecordLength = i1 * 256 + i2;
							
							if (bVariableLength)
							{
								LittleEndingSignBinaryBufferStorage.writeInt(tbyHeader4, nCurrentRecordLength, 0);
								m_fileOut.write(tbyHeader4, 0, tbyHeader4.length, false);
							}
							
							lineRead = dataFileIn.readBuffer(nCurrentRecordLength, false);
							if (bEbcdicIn && !bEbcdicOut)
								varDefEncodingConvertibleManagerContainer.getEncodingManagerConvertAndWrite(lineRead, varRoot);
							else
								varRoot.setFromLineRead(lineRead);
							m_fileOut.writeFrom(varRoot);
							m_fileOut.getBaseDataFile().writeEndOfRecordMarker();
							lineRead = dataFileIn.readBuffer(3, false);
						}
					}
				}
				else
				{
					LineRead lineRead = dataFileIn.readBuffer(m_nLengthRecord, false);
					while (lineRead != null)
					{
						if (bEbcdicIn && !bEbcdicOut)	// Must convert string chunks to ascii
							varDefEncodingConvertibleManagerContainer.getEncodingManagerConvertAndWrite(lineRead, varRoot);
						else	// !bEbcdicIn && bEbcdicOut
							varRoot.setFromLineRead(lineRead);
						m_fileOut.writeFrom(varRoot);
						m_fileOut.getBaseDataFile().writeEndOfRecordMarker();
						lineRead = dataFileIn.readBuffer(m_nLengthRecord, false);
					}
				}
			}
			else
			{
				LineRead lineRead = m_fileIn.readALine(dataFileIn, null);
				while(lineRead != null)
				{
					if(bVariableLength)
						lineRead.shiftOffset(4);	// Skip record header
					
					if (bEbcdicIn && !bEbcdicOut)	// Must convert string chunks to ascii
						varDefEncodingConvertibleManagerContainer.getEncodingManagerConvertAndWrite(lineRead, varRoot);
					else	// !bEbcdicIn && bEbcdicOut
						varRoot.setFromLineRead(lineRead);
	
					if(bVariableLength)
						lineRead.shiftOffset(-4);
	
					m_fileOut.writeFrom(varRoot);
					lineRead = m_fileIn.readALine(dataFileIn, lineRead);
				}
			}	
			m_fileOut.close();
			dataFileIn.close();
		}
		
		return true;
	}
}
