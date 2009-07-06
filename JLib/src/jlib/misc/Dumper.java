/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.misc;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class Dumper
{
	public static void dump(String cs)
	{
		System.out.println(cs);
	}
	
	
	/*
  |00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F |
  |-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- |
00|50 31 41 FF FF FF FF 30 30 30 39 39 39 39 39 39 |P1Aÿÿÿÿ000999999
01|39 39 38 30 30 30 00 00 00 5C 00 00 1C 00 00 00 |998000
02|23                                              |#
	 */
	public static void dump(byte arr[])
	{
		if(arr != null)
		{
			String csHexa = "  |";
			String csText = "";
			for(int n=0; n<16; n++)
			{
				String csByte = StringUtil.FormatAs2CharHexa(n);
				csHexa += csByte + " ";
			}
			
			dump(csHexa+"|");
			dump("  |-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- |");
			csHexa = "00|";
			
			int n=0;
			for(; n<arr.length; n++)
			{
				if((n % 16) == 0 && n != 0)
				{
					dump(csHexa+"|"+csText);
					csHexa = StringUtil.FormatAs2CharHexa(n/16)+"|";
					csText = "";
				}
				String csByte = StringUtil.FormatAs2CharHexa(arr[n]);
				csHexa += csByte + " ";
				if(arr[n] < 0)
					csText += (char) (arr[n] + 256);
				else
					csText += (char) (arr[n]);
			}
			while((n % 16) != 0)
			{
				csHexa += "   ";
				n++;
			}
			
			dump(csHexa+"|"+csText);
		}
		else
			dump("byte array is null");
	}
	
	public static boolean isFileRecordsOrdered(String csFilePath, boolean bAscending)
	{
		DataFileRead file = new DataFileRead(csFilePath);
		boolean b = file.open(null);
		if(b)
		{
			b = compareRecords(file, bAscending);
			file.close();
		}
		return b;
	}
	
	private static boolean compareRecords(DataFileRead file, boolean bAscending)
	{
		int nRecordId = 0;
		byte tBytesOld[] = new byte[65536];
		byte tBytesNew[] = new byte[65536];
		int nRecordLengthOld = file.readUnixLine(tBytesOld, 0, 65535);
		while(!file.isEOF())
		{
			int nRecordLengthNew = file.readUnixLine(tBytesNew, 0, 65535);
			int nLength = Math.min(nRecordLengthOld, nRecordLengthNew);
			for(int n=0; n<nLength; n++)
			{
				int n1 = (int)tBytesOld[n];
				int n2 = (int)tBytesNew[n];
				if(n1 < 0)
					n1 += 256;
				if(n2 < 0)
					n2 += 256;
				if(n1 == n2)
					continue;
				else if((n1 < n2 && !bAscending) || (n1 > n2 && bAscending))
					return false;
				else
					break;
			}
			if((nRecordLengthOld < nRecordLengthNew && !bAscending) || (nRecordLengthOld > nRecordLengthNew && bAscending))
				return false;
				
			nRecordLengthOld = nRecordLengthNew;
			for(int n=0; n<nRecordLengthNew; n++)
			{
				tBytesOld[n] = tBytesNew[n];
			}
			nRecordId++;
		}
		return true;
	}

	
}
