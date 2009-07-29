/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MFCobolFileEncoder
{
	public MFCobolFileEncoder()
	{	
	}
	
	public int decode(String csEncodedFile, String csDecodedFile)
	{
		int nNbByteWritten = 0;
		try
		{
			BufferedInputStream inputStream = new BufferedInputStream(new DataInputStream(new FileInputStream(csEncodedFile)));
			BufferedOutputStream outputStream = new BufferedOutputStream(new DataOutputStream(new FileOutputStream(csDecodedFile)));
			if (inputStream != null && outputStream != null)  
			{			  	
				boolean bLeadingNullRead = false;
				int nByte = inputStream.read();
				while(nByte >= 0)
				{
					if(nByte == 0)
					{
						if(!bLeadingNullRead)
							bLeadingNullRead = true;	// Do not write 1st 00
						else
						{
							bLeadingNullRead = false;	// But write 2nd 00
							outputStream.write(nByte);	
							nNbByteWritten++;
						}	
					}
					else
					{
						bLeadingNullRead = false;
						outputStream.write(nByte);
						nNbByteWritten++;
					}	
					nByte = inputStream.read();
				}
				inputStream.close();
				outputStream.close();
		  	}
			return nNbByteWritten;
		}
		catch (FileNotFoundException e)
		{
		}
		catch (IOException e)
		{
		}
		return -1;
	}
	
	public int encode(String csDecodedFile, String csEncodedFile)
	{
		int nNbByteWritten = 0;
		try
		{
			BufferedInputStream inputStream = new BufferedInputStream(new DataInputStream(new FileInputStream(csDecodedFile)));
			BufferedOutputStream outputStream = new BufferedOutputStream(new DataOutputStream(new FileOutputStream(csEncodedFile)));
			if (inputStream != null && outputStream != null)  
			{			  	
				int nByte = inputStream.read();
				while(nByte >= 0)
				{
					if(nByte < 32)
					{
						outputStream.write(0);	// char with code < 32 are prefixed by a leading 0 byte
						nNbByteWritten++;
					}
					outputStream.write(nByte);
					nNbByteWritten++;
					
					nByte = inputStream.read();
				}
				inputStream.close();
				outputStream.close();
		  	}
			return nNbByteWritten;
		}
		catch (FileNotFoundException e)
		{
		}
		catch (IOException e)
		{
		}
		return -1;
	}
}
