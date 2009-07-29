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
/**
 * 
 */
package nacaLib.bdb;

import jlib.log.Log;
import jlib.misc.DataFileLineReader;
import jlib.misc.DataFileWrite;
import jlib.misc.LineRead;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class FileKeyExporter
{
	private DataFileWrite m_dataFileKeyOut = null;
	private BtreeKeyDescription m_keyDescription = null;
	
	public FileKeyExporter(String csKeys, String csExportKeyFileOut, boolean bFileInEbcdic)
	{
		if(csExportKeyFileOut != null)
		{
			m_dataFileKeyOut = new DataFileWrite(csExportKeyFileOut, false);
			boolean bOutKeyOpened = m_dataFileKeyOut.open();
			if(!bOutKeyOpened)
			{
				m_dataFileKeyOut = null;
				Log.logImportant("Cannot create output key file " + csExportKeyFileOut);
			}
		}
		
		setKeyDescription(csKeys, bFileInEbcdic);
	}
	
	private void setKeyDescription(String csKeys, boolean bFileInEbcdic)
	{
		m_keyDescription = new BtreeKeyDescription();
		m_keyDescription.set(csKeys, false);
		m_keyDescription.prepare();
		m_keyDescription.setFileInEncoding(bFileInEbcdic);
	}
	
	public void execute(String csFileIn, int nBufferChunkReadAHead)
	{		
		int nNbRecordRead = 0;
		DataFileLineReader dataFileIn = new DataFileLineReader(csFileIn, nBufferChunkReadAHead, 0);
		boolean bInOpened = dataFileIn.open();
		if(bInOpened)
		{
			// doesn't manage variable length files 
			boolean b = true;
			LineRead lineRead = dataFileIn.readNextUnixLine();
			while(lineRead != null && b == true)
			{
				byte tbKey[] = m_keyDescription.fillKeyBufferIncludingRecordId(lineRead, false);	//, false);
				m_dataFileKeyOut.writeWithEOL(tbKey, tbKey.length);

				lineRead = dataFileIn.readNextUnixLine();
				nNbRecordRead++;
			}
			m_dataFileKeyOut.close();
			dataFileIn.close();
		}		
		Log.logNormal("" + nNbRecordRead + " records read file from " + csFileIn);
	}
}
