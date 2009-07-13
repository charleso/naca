/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.fileConverter;

import jlib.misc.FileSystem;
import nacaLib.varEx.FileDescriptor;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: FileEncodingConverter.java,v 1.4 2007/06/09 12:04:22 u930bm Exp $
 */
public class FileEncodingConverter
{
	protected FileDescriptor m_fileIn = null;
	protected FileDescriptor m_fileOut = null;
	
	protected boolean m_bHost = false;
	protected int m_nLengthRecord = 0;
	protected boolean m_bVariable4 = false;
	protected boolean m_bHeaderEbcdic = false;

	public FileEncodingConverter(FileDescriptor fileIn, FileDescriptor fileOut)
	{
		m_fileIn = fileIn;
		m_fileOut = fileOut;
	}
	
	public void setHost(String csParameter)
	{
		m_bHost = true;
		String csParameterUpper = csParameter.toUpperCase();
		if (csParameterUpper.indexOf("RECORDLENGTH={") != -1)
		{
			int nPos = csParameterUpper.indexOf("RECORDLENGTH={") + 14;
			int nPosEnd = csParameterUpper.indexOf("}", nPos);
			m_nLengthRecord = new Integer(csParameter.substring(nPos, nPosEnd)).intValue();				
		}
		if (csParameterUpper.indexOf("VARIABLE4") != -1)
		{
			m_bVariable4 = true;
		}
		if (csParameterUpper.indexOf("HEADEREBCDIC") != -1)
		{
			m_bHeaderEbcdic = true;
		}
		System.out.println("FileEncodingConverter: Converting Host file");
		if (m_nLengthRecord == 0)
			System.out.println("FileEncodingConverter: Length record determined by header");
		else	
			System.out.println("FileEncodingConverter: Length record : " + m_nLengthRecord);
		if (m_bHeaderEbcdic)
			System.out.println("FileEncodingConverter: Add header ebcdic");
	}

	protected boolean copyFile()
	{
		return FileSystem.copy(m_fileIn.getPhysicalName(), m_fileOut.getPhysicalName());
	}
}
