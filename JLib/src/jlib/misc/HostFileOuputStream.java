/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public class HostFileOuputStream extends OutputStream
{
	protected OutputStream m_Stream = null ;
	protected boolean m_bHeaderVariable = false;
	protected int m_nCurrentRecordLength = 0;
	protected int m_nCurrentRecordWritten = 0;
	protected Vector<Integer> m_arrRecordHeader = new Vector<Integer>();
	private byte[] m_tbyHeader = new byte[4];
	
	public HostFileOuputStream(OutputStream stream, String csFormat, boolean bHeaderEbcdic)
	{
		m_Stream = stream ;
		if (csFormat != null && csFormat.equals("VB")) {
			m_bHeaderVariable = true;
		}
		if (bHeaderEbcdic) {
			try
			{
				m_Stream.write(new String("<FileHeader Version=\"1\" Encoding=\"ebcdic\"/>").getBytes());
				FileSystem.WriteEOL(m_Stream);
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public void write(int arg0) throws IOException
	{
		if  (m_nCurrentRecordLength == 0)
		{
			m_arrRecordHeader.add(arg0 >= 0 ? arg0 : 256 + arg0);
			
			if (m_arrRecordHeader.size() >= 3)
			{
				int i1 = m_arrRecordHeader.get(1); 
				int i2 = m_arrRecordHeader.get(2); 
				m_nCurrentRecordLength = i1 * 256 + i2;
				m_nCurrentRecordWritten = 0;
				m_arrRecordHeader.clear();
				
				if (m_bHeaderVariable)
				{
					LittleEndingSignBinaryBufferStorage.writeInt(m_tbyHeader, m_nCurrentRecordLength, 0);
					m_Stream.write(m_tbyHeader);
				}
				
				if (m_nCurrentRecordLength == 0)
					FileSystem.WriteEOL(m_Stream);
			}
		}
		else
		{
			m_Stream.write(arg0);
			m_nCurrentRecordWritten++;
			if (m_nCurrentRecordWritten == m_nCurrentRecordLength)
			{
				FileSystem.WriteEOL(m_Stream);
				m_nCurrentRecordLength = 0;
			}
		}
	}

	public void close() throws IOException
	{
		m_Stream.close() ;
	}

	public void flush() throws IOException
	{
		m_Stream.flush()  ;
	}
}