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
import java.io.InputStream;
import java.util.Vector;

public class HostFileInputStream extends InputStream
{
	protected InputStream m_Stream = null ;
	protected boolean m_bHeaderVariable = false;
	protected int m_nLength = 0;
	protected int[] m_Record = null;
	protected int m_nCurrentRecordRead = 0;
	private byte[] m_tbyHeader = new byte[4];
	
	public HostFileInputStream(InputStream is, String csFormat, int nLength)
	{
		m_Stream = is;
		if (csFormat != null && csFormat.equals("VB")) {
			m_bHeaderVariable = true;
		}
		m_nLength = nLength;
	}

	public int read() throws IOException
	{
		if (m_Record == null)
		{
			if (m_Stream.available() == 0)
			{
				return -1;
			}
			Vector<Integer> v = new Vector<Integer>();
			if (m_bHeaderVariable)
			{
				m_tbyHeader[0] = (byte)m_Stream.read();
				m_tbyHeader[1] = (byte)m_Stream.read();
				m_tbyHeader[2] = (byte)m_Stream.read();
				m_tbyHeader[3] = (byte)m_Stream.read();
				int nLengthExcludingHeader = LittleEndingSignBinaryBufferStorage.readInt(m_tbyHeader, 0);
				for (int i=0; i < nLengthExcludingHeader; i++)
				{
					v.add(m_Stream.read());
				}
				m_Stream.read();
			}
			else
			{
				if (m_nLength == 0)
				{
					int b = m_Stream.read();
					while (b != '\n' && b != -1)
					{
						v.add(b) ;
						b = m_Stream.read();
					}
				}
				else
				{
					for (int i=0; i < m_nLength; i++)
					{
						v.add(m_Stream.read());
					}
					m_Stream.read();
				}
			}
			
			m_Record = new int[v.size()+3];
			if (m_Stream.available() == 0)
			{
				m_Record[0] = 64;
			}
			else
			{
				m_Record[0] = 128;
			}
			m_Record[1] = v.size() / 256; 
			m_Record[2] = v.size() % 256;
			for (int i=0; i<v.size(); i++)
			{
				m_Record[i+3] = v.get(i);
			}
			m_nCurrentRecordRead = 0;
		}
		
		int b = m_Record[m_nCurrentRecordRead];
		m_nCurrentRecordRead++;
		if (m_nCurrentRecordRead == m_Record.length)
		{
			m_Record = null;
			m_nCurrentRecordRead = 0;
		}
		
		return b;
	}

	public int available() throws IOException
	{
		return m_Stream.available() + m_Record.length-m_nCurrentRecordRead ;
	}

	public void close() throws IOException
	{
		m_Stream.close() ;
	}
}