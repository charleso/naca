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

public class AsciiEbcdicConverter
{	
	private static int gs_tEbcdic[] = null;	// Collection of ebcdic char, indexed by ascii  
	private static int gs_tAscii[] = null;	// Collection of ascii char, indexed by ebcdic 
	
	public static void create()
	{
		new AsciiEbcdicConverter(); 
	}
	
	private AsciiEbcdicConverter()
	{
		if(gs_tEbcdic == null && gs_tAscii == null)
		{
			gs_tEbcdic = new int [256];
			gs_tAscii = new int [256];
			init();
		}
	}
	
	static void init()
	{
		// See Latin-1 Extended, Desk Top Publishing/Windows (01004)
		// http://www-03.ibm.com/servers/eserver/iseries/software/globalization/pdf/cp01047z.pdf  
		
		// ebcdic encoding: http://www-03.ibm.com/servers/eserver/iseries/software/globalization/pdf/cp01047z.pdf
		// ascii encoding : http://www-03.ibm.com/servers/eserver/iseries/software/globalization/pdf/cp01004z.pdf
		
		addChar("00", "00"); //          0 
		addChar("01", "01"); //          1  
		addChar("02", "02"); //          2  
		addChar("03", "03"); //          3  
		addChar("04", "37"); //          4  
		addChar("05", "2D"); //          5  
		addChar("06", "2E"); //          6  
		addChar("07", "2F"); //          7  
		addChar("08", "16"); //          8  
		addChar("09", "05"); //          9  
		addChar("0A", "15"); //         10  
		addChar("0B", "0B"); //         11  
		addChar("0C", "0C"); //         12  
		addChar("0D", "0D"); //         13  
		addChar("0E", "0E"); //         14  
		addChar("0F", "0F"); //         15  
		addChar("10", "10"); //         16  
		addChar("11", "11"); //         17  
		addChar("12", "12"); //         18  
		addChar("13", "13"); //         19  
		addChar("14", "3C"); //         20  
		addChar("15", "3D"); //         21  
		addChar("16", "32"); //         22  
		addChar("17", "26"); //         23  
		addChar("18", "18"); //         24  
		addChar("19", "19"); //         25  
		addChar("1A", "3F"); //         26  
		addChar("1B", "27"); //         27  
		addChar("1C", "1C"); //         28  
		addChar("1D", "1D"); //         29  
		addChar("1E", "1E"); //         30  
		addChar("1F", "1F"); //         31  
		addChar("20", "40"); //         32  
		addChar("21", "4F"); //   !     33  
		addChar("22", "7F"); //   "     34  
		addChar("23", "7B"); //   #     35  
		addChar("24", "5B"); //   $     36  
		addChar("25", "6C"); //   %     37  
		addChar("26", "50"); //   &     38  
		addChar("27", "7D"); //   '     39  
		addChar("28", "4D"); //   (     40  
		addChar("29", "5D"); //   )     41  
		addChar("2A", "5C"); //   *     42  
		addChar("2B", "4E"); //   +     43  
		addChar("2C", "6B"); //   ,     44  
		addChar("2D", "60"); //   -     45  
		addChar("2E", "4B"); //   .     46  
		addChar("2F", "61"); //   /     47  
		addChar("30", "F0"); //   0     48  
		addChar("31", "F1"); //   1     49  
		addChar("32", "F2"); //   2     50  
		addChar("33", "F3"); //   3     51  
		addChar("34", "F4"); //   4     52  
		addChar("35", "F5"); //   5     53  
		addChar("36", "F6"); //   6     54  
		addChar("37", "F7"); //   7     55  
		addChar("38", "F8"); //   8     56  
		addChar("39", "F9"); //   9     57  
		addChar("3A", "7A"); //   :     58  
		addChar("3B", "5E"); //   ;     59  
		addChar("3C", "4C"); //   <     60  
		addChar("3D", "7E"); //   =     61  
		addChar("3E", "6E"); //   >     62  
		addChar("3F", "6F"); //   ?     63  
		addChar("40", "7C"); //   @     64  
		addChar("41", "C1"); //   A     65  
		addChar("42", "C2"); //   B     66  
		addChar("43", "C3"); //   C     67  
		addChar("44", "C4"); //   D     68  
		addChar("45", "C5"); //   E     69  
		addChar("46", "C6"); //   F     70  
		addChar("47", "C7"); //   G     71  
		addChar("48", "C8"); //   H     72  
		addChar("49", "C9"); //   I     73  
		addChar("4A", "D1"); //   J     74  
		addChar("4B", "D2"); //   K     75  
		addChar("4C", "D3"); //   L     76  
		addChar("4D", "D4"); //   M     77  
		addChar("4E", "D5"); //   N     78  
		addChar("4F", "D6"); //   O     79  
		addChar("50", "D7"); //   P     80  
		addChar("51", "D8"); //   Q     81  
		addChar("52", "D9"); //   R     82  
		addChar("53", "E2"); //   S     83  
		addChar("54", "E3"); //   T     84  
		addChar("55", "E4"); //   U     85  
		addChar("56", "E5"); //   V     86  
		addChar("57", "E6"); //   W     87  
		addChar("58", "E7"); //   X     88  
		addChar("59", "E8"); //   Y     89  
		addChar("5A", "E9"); //   Z     90  
		addChar("5B", "4A"); //   [     91  
		addChar("5C", "E0"); //   \     92  
		addChar("5D", "5A"); //   ]     93  
		addChar("5E", "5F"); //   ^     94  
		addChar("5F", "6D"); //   _     95  
		addChar("60", "79"); //   `     96  
		addChar("61", "81"); //   a     97  
		addChar("62", "82"); //   b     98  
		addChar("63", "83"); //   c     99  
		addChar("64", "84"); //   d    100  
		addChar("65", "85"); //   e    101  
		addChar("66", "86"); //   f    102  
		addChar("67", "87"); //   g    103  
		addChar("68", "88"); //   h    104  
		addChar("69", "89"); //   i    105  
		addChar("6A", "91"); //   j    106  
		addChar("6B", "92"); //   k    107  
		addChar("6C", "93"); //   l    108  
		addChar("6D", "94"); //   m    109  
		addChar("6E", "95"); //   n    110  
		addChar("6F", "96"); //   o    111  
		addChar("70", "97"); //   p    112  
		addChar("71", "98"); //   q    113  
		addChar("72", "99"); //   r    114  
		addChar("73", "A2"); //   s    115  
		addChar("74", "A3"); //   t    116  
		addChar("75", "A4"); //   u    117  
		addChar("76", "A5"); //   v    118  
		addChar("77", "A6"); //   w    119  
		addChar("78", "A7"); //   x    120  
		addChar("79", "A8"); //   y    121  
		addChar("7A", "A9"); //   z    122  
		addChar("7B", "C0"); //   {    123  
		addChar("7C", "BB"); //   |    124  
		addChar("7D", "D0"); //   }    125  
		addChar("7E", "A1"); //   ~    126  
		addChar("7F", "07"); //       127  
		addChar("80", "20"); //   ?    128  
		addChar("81", "21"); //   ?    129  
		addChar("82", "22"); //   ?    130  
		addChar("83", "23"); //   ?    131  
		addChar("84", "24"); //   ?    132  
		addChar("85", "25"); //   ?    133  
		addChar("86", "06"); //   ?    134  
		addChar("87", "17"); //   ?    135  
		addChar("88", "28"); //   ?    136  
		addChar("89", "29"); //   ?    137  
		addChar("8A", "2A"); //   ?    138  
		addChar("8B", "2B"); //   ?    139  
		addChar("8C", "2C"); //   ?    140  
		addChar("8D", "09"); //   ?    141  
		addChar("8E", "0A"); //   ?    142  
		addChar("8F", "1B"); //   ?    143  
		addChar("90", "30"); //   ?    144  
		addChar("91", "31"); //   ?    145  
		addChar("92", "1A"); //   ?    146  
		addChar("93", "33"); //   ?    147  
		addChar("94", "34"); //   ?    148  
		addChar("95", "35"); //   ?    149  
		addChar("96", "36"); //   ?    150  
		addChar("97", "08"); //   ?    151  
		addChar("98", "38"); //   ?    152  
		addChar("99", "39"); //   ?    153  
		addChar("9A", "3A"); //   ?    154  
		addChar("9B", "3B"); //   ?    155  
		addChar("9C", "04"); //   ?    156  
		addChar("9D", "14"); //   ?    157  
		addChar("9E", "3E"); //   ?    158  
		addChar("9F", "FF"); //   ?    159  
		addChar("A0", "41"); //        160  
		addChar("A1", "AA"); //   ¡    161  
		addChar("A2", "B0"); //   ¢    162  
		addChar("A3", "B1"); //   £    163  
		addChar("A4", "9F"); //   ¤    164  
		addChar("A5", "B2"); //   ¥    165  
		addChar("A6", "6A"); //   ¦    166  
		addChar("A7", "B5"); //   §    167  
		addChar("A8", "BD"); //   ¨    168  
		addChar("A9", "B4"); //   ©    169  
		addChar("AA", "9A"); //   ª    170  
		addChar("AB", "8A"); //   «    171  
		addChar("AC", "BA"); //   ¬    172  
		addChar("AD", "CA"); //   ­    173  
		addChar("AE", "AF"); //   ®    174  
		addChar("AF", "BC"); //   ¯    175  
		addChar("B0", "90"); //   °    176  
		addChar("B1", "8F"); //   ±    177  
		addChar("B2", "EA"); //   ²    178  
		addChar("B3", "FA"); //   ³    179  
		addChar("B4", "BE"); //   ´    180  
		addChar("B5", "A0"); //   µ    181  
		addChar("B6", "B6"); //   ¶    182  
		addChar("B7", "B3"); //   ·    183  
		addChar("B8", "9D"); //   ¸    184  
		addChar("B9", "DA"); //   ¹    185  
		addChar("BA", "9B"); //   º    186  
		addChar("BB", "8B"); //   »    187  
		addChar("BC", "B7"); //   ¼    188  
		addChar("BD", "B8"); //   ½    189  
		addChar("BE", "B9"); //   ¾    190  
		addChar("BF", "AB"); //   ¿    191  
		addChar("C0", "64"); //   À    192  
		addChar("C1", "65"); //   Á    193  
		addChar("C2", "62"); //   Â    194  
		addChar("C3", "66"); //   Ã    195  
		addChar("C4", "63"); //   Ä    196  
		addChar("C5", "67"); //   Å    197  
		addChar("C6", "9E"); //   Æ    198  
		addChar("C7", "68"); //   Ç    199  
		addChar("C8", "74"); //   È    200  
		addChar("C9", "71"); //   É    201  
		addChar("CA", "72"); //   Ê    202  
		addChar("CB", "73"); //   Ë    203  
		addChar("CC", "78"); //   Ì    204  
		addChar("CD", "75"); //   Í    205  
		addChar("CE", "76"); //   Î    206  
		addChar("CF", "77"); //   Ï    207  
		addChar("D0", "AC"); //   Ð    208  
		addChar("D1", "69"); //   Ñ    209  
		addChar("D2", "ED"); //   Ò    210  
		addChar("D3", "EE"); //   Ó    211  
		addChar("D4", "EB"); //   Ô    212  
		addChar("D5", "EF"); //   Õ    213  
		addChar("D6", "EC"); //   Ö    214  
		addChar("D7", "BF"); //   ×    215  
		addChar("D8", "80"); //   Ø    216  
		addChar("D9", "FD"); //   Ù    217  
		addChar("DA", "FE"); //   Ú    218  
		addChar("DB", "FB"); //   Û    219  
		addChar("DC", "FC"); //   Ü    220  
		addChar("DD", "AD"); //   Ý    221  
		addChar("DE", "AE"); //   Þ    222  
		addChar("DF", "59"); //   ß    223  
		addChar("E0", "44"); //   à    224  
		addChar("E1", "45"); //   á    225  
		addChar("E2", "42"); //   â    226  
		addChar("E3", "46"); //   ã    227  
		addChar("E4", "43"); //   ä    228  
		addChar("E5", "47"); //   å    229  
		addChar("E6", "9C"); //   æ    230  
		addChar("E7", "48"); //   ç    231  
		addChar("E8", "54"); //   è    232  
		addChar("E9", "51"); //   é    233  
		addChar("EA", "52"); //   ê    234  
		addChar("EB", "53"); //   ë    235  
		addChar("EC", "58"); //   ì    236  
		addChar("ED", "55"); //   í    237  
		addChar("EE", "56"); //   î    238  
		addChar("EF", "57"); //   ï    239  
		addChar("F0", "8C"); //   ð    240  
		addChar("F1", "49"); //   ñ    241  
		addChar("F2", "CD"); //   ò    242  
		addChar("F3", "CE"); //   ó    243  
		addChar("F4", "CB"); //   ô    244  
		addChar("F5", "CF"); //   õ    245  
		addChar("F6", "CC"); //   ö    246  
		addChar("F7", "E1"); //   ÷    247  
		addChar("F8", "70"); //   ø    248  
		addChar("F9", "DD"); //   ù    249  
		addChar("FA", "DE"); //   ú    250  
		addChar("FB", "DB"); //   û    251  
		addChar("FC", "DC"); //   ü    252  
		addChar("FD", "8D"); //   ý    253  
		addChar("FE", "8E"); //   þ    254  
		addChar("FF", "FF"); //   ÿ    255
	}
	
	static private void addChar(String csAscii, String csEbcdic)
	{
		int nAscii = getCode(csAscii);
		int nEbcdic = getCode(csEbcdic);
		gs_tEbcdic[nAscii] = nEbcdic; 
		gs_tAscii[nEbcdic] = nAscii; 
	}
	
	public static int getEbcdicCorrepondingCode(int nAscii)
	{
		if(nAscii < 256)
			return gs_tEbcdic[nAscii];
		return 0;
	}
	
	
	static private int getCode(String cs)
	{
		char cHigh = cs.charAt(0);
		int nHigh = getHexValue(cHigh);
		char cLow = cs.charAt(1);
		int nLow = getHexValue(cLow);
		int n = (nHigh * 16) + nLow;
		return n;
	}
	
	public static String getEbcdicHexaValue(int nAscii)
	{
		if(nAscii >= 0 && nAscii <= 255)
		{
			int nEbcdic = gs_tEbcdic[nAscii];
			return getHexaValue(nEbcdic);
		}
		return "Invalid char code";
	}

	public static String getHexaValue(int nAscii)
	{
		if(nAscii >= 0 && nAscii <= 255)
		{
			int nHigh = nAscii / 16;
			char cHigh = getHexChar(nHigh);
			int nLow = nAscii % 16;
			char cLow = getHexChar(nLow);
			String cs = new String();
			cs += cHigh;
			cs += cLow;
			
			return cs;
		}
		return "Invalid char code";
	}	
	
	private static char getHexChar(int n)
	{
		if(n <= 9)
			return (char)(n + '0');
		return (char)((n-10) + 'A');
	}
	
	private static int getHexValue(char c)
	{
		if(c <= '9')
			return (c - '0'); 
		return (c - 'A') + 10;
	}
	
	public static int compareEbcdic(int n1, int n2)
	{
		int e1 = AsciiEbcdicConverter.getEbcdicCorrepondingCode(n1);
		int e2 = AsciiEbcdicConverter.getEbcdicCorrepondingCode(n2);
		if(e1 < e2)
			return -1;
		if(e1 > e2)
			return 1;
		return 0;
	}
	
	public static char getEbcdicChar(char cAscii)
	{
		int nEbcdic = gs_tEbcdic[cAscii];
		char cEbcdic = (char)nEbcdic;
		return cEbcdic;	
	}
	
	public static char getAsciiChar(char cEbcdic)
	{
		int nAscii = gs_tAscii[cEbcdic];
		char cAscii = (char)nAscii;
		return cAscii;	
	}
	
	public static char getAsciiChar(byte byEbcdic)
	{
		int nEbcdic = byEbcdic;
		if(nEbcdic < 0)
			nEbcdic += 256;
		int nAscii = gs_tAscii[nEbcdic];
		char cAscii = (char)nAscii;
		return cAscii;	
	}
	
	public static byte getAsciiByte(byte byEbcdic)
	{
		int nEbcdic = byEbcdic;
		if(nEbcdic < 0)
			nEbcdic += 256;
		int nAscii = gs_tAscii[nEbcdic];
		byte byteAscii = (byte)nAscii;
		return byteAscii;	
	}
	
	public static int getAsAscii(int nEbcdic)
	{
		int nAscii = gs_tAscii[nEbcdic];		
		return nAscii;	
	}
	
	public static byte getEbcdicByte(byte byAscii)
	{
		int nAscii = byAscii;
		if(nAscii < 0)
			nAscii += 256;
		int nEbcdic = gs_tEbcdic[nAscii];
		byte byteEbcdic = (byte)nEbcdic;
		return byteEbcdic;	
	}
	
	public static String getEbcdicString(String csIn)
	{
		String csOut = new String();
		int nLg = csIn.length();
		for(int n=0; n<nLg; n++)
		{
			char cIn = csIn.charAt(n);
			int nIn = cIn;
			int nEbcdic = gs_tEbcdic[nIn];
			char cEbcdic = (char)nEbcdic;
			csOut += cEbcdic;
		}
		return csOut;
	}
		
	public static byte[] convertUnicodeToEbcdic(char[] tChars)
	{
		int nLength = tChars.length;
		byte [] tOut = new byte[nLength];
		for(int n=0; n<nLength; n++)
		{
			char c = tChars[n];
			byte b = (byte)c;
			int nIndex = b;
			if(nIndex < 0)
				nIndex += 256;
			int nOut = gs_tEbcdic[nIndex];
			tOut[n] = (byte)nOut;
		}
		return tOut;
	}
	
	public static char[] convertEbcdicToUnicode(byte[] tBytes)
	{
		int nLength = tBytes.length;
		char [] tOut = new char[nLength]; 
		for(int n=0; n<nLength; n++)
		{
			tOut[n] = getAsciiChar(tBytes[n]);
		}
		return tOut;
	}
	
	public static char[] noConvertEbcdicToUnicode(byte[] tBytes)
	{
		return noConvertEbcdicToUnicode(tBytes, tBytes.length);
	}
	
	public static char[] noConvertEbcdicToUnicode(byte[] tBytes, int nLength)
	{
		char[] tChars = new char[nLength];
		for(int n=0; n<nLength; n++)
		{
			tChars[n] = (char)tBytes[n];
		}
		return tChars;
	}
	
	public static byte[] noConvertUnicodeToEbcdic(char[] tChars)
	{
		byte[] tBytes = new byte[tChars.length];
		for(int n=0; n<tChars.length; n++)
		{
			tBytes[n] = (byte)tChars[n];
		}
		return tBytes;
	}
	
	public static byte[] noConvertUnicodeToEbcdic(char[] tChars, int nSourceOffset, int nLength)
	{
		byte[] tBytes = new byte[nLength];
		for(int n=0; n<nLength; n++)
		{
			tBytes[n] = (byte)tChars[nSourceOffset+n];
		}
		return tBytes;
	}
	
	public static void swapByteAsciiToEbcdic(byte tBytesData[], int nOffset, int nLength)
	{
		for(int n=0; n<nLength; n++)
		{
			int nAscii = tBytesData[n+nOffset];
			if(nAscii < 0)
				nAscii += 256;
			int nEbcdic = gs_tEbcdic[nAscii];
			tBytesData[n+nOffset] = (byte)nEbcdic; 
		}
	}
	
	public static void swapByteEbcdicToAscii(byte tBytesData[], int nOffset, int nLength)
	{
		for(int n=0; n<nLength; n++)
		{
			int nEbcdic = tBytesData[n+nOffset];
			if(nEbcdic < 0)
				nEbcdic += 256;
			int nAscii = gs_tAscii[nEbcdic];
			tBytesData[n+nOffset] = (byte)nAscii; 
		}
	}
	
	private static final byte   AFP_ASCII_5A  			=   (byte)0x5D; // 5A
	private static final byte[] AFP_ASCII_PAGEFORMAT 	= { (byte)0x4C, (byte)0xBF, (byte)0xAD }; // D3ABCA
	private static final byte[] AFP_ASCII_COPYGROUP 	= { (byte)0x4C, (byte)0xBF, (byte)0xF6 }; // D3ABCC
	private static final byte[] AFP_ASCII_SEGMENT 		= { (byte)0x4C, (byte)0xAE, (byte)0x5E }; // D3AF5F
	private static final byte[] AFP_ASCII_SFI 			= { (byte)0x4C, (byte)0xD3, (byte)0xBA }; // D3EE9B
	
	public static void swapByteAsciiToEbcdicPrintAFP(byte tBytesData[], int nOffset, int nLength)
	{
		if (nLength > 6 && tBytesData[nOffset] == AFP_ASCII_5A)
		{
			if (isSpecialPrintAfp(tBytesData, nOffset, AFP_ASCII_COPYGROUP) || isSpecialPrintAfp(tBytesData, nOffset, AFP_ASCII_PAGEFORMAT))
			{
				AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, nOffset, 1);
				AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, nOffset+3, 3);
				AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, nOffset+9, 8);
				if (nLength > 17)
				{
					AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, nOffset+17, nLength-17);
				}
			}
			else if (isSpecialPrintAfp(tBytesData, nOffset, AFP_ASCII_SEGMENT))
			{
				AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, nOffset, 1);
				AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, nOffset+3, 3);
				AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, nOffset+9, 8);
				if (nLength > 23)
				{
					AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, nOffset+23, nLength-23);
				}
			}
			else if (isSpecialPrintAfp(tBytesData, nOffset, AFP_ASCII_SFI))
			{
				AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, nOffset, 1);
				AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, nOffset+3, 3);
				AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, nOffset+9, nLength-9);
			}
		}
		else
		{
			AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, nOffset, nLength);
		}
	}
	
	private static final byte   AFP_EBCDIC_5A  			=   (byte)0x5A;
	private static final byte[] AFP_EBCDIC_PAGEFORMAT 	= { (byte)0xD3, (byte)0xAB, (byte)0xCA }; // D3ABCA
	private static final byte[] AFP_EBCDIC_COPYGROUP 	= { (byte)0xD3, (byte)0xAB, (byte)0xCC }; // D3ABCC
	private static final byte[] AFP_EBCDIC_SEGMENT 		= { (byte)0xD3, (byte)0xAF, (byte)0x5F }; // D3AF5F
	
	public static void swapByteEbcdicToAsciiPrintAFP(byte tBytesData[], int nOffset, int nLength)
	{
		if (nLength > 6 && tBytesData[nOffset] == AFP_EBCDIC_5A)
		{
			if (isSpecialPrintAfp(tBytesData, nOffset, AFP_EBCDIC_PAGEFORMAT) || isSpecialPrintAfp(tBytesData, nOffset, AFP_EBCDIC_COPYGROUP))
			{
				AsciiEbcdicConverter.swapByteEbcdicToAscii(tBytesData, nOffset, 1);
				AsciiEbcdicConverter.swapByteEbcdicToAscii(tBytesData, nOffset+3, 3);
				AsciiEbcdicConverter.swapByteEbcdicToAscii(tBytesData, nOffset+9, 8);
				if (nLength > 17)
				{
					AsciiEbcdicConverter.swapByteEbcdicToAscii(tBytesData, nOffset+17, nLength-17);
				}
			}
			else if (isSpecialPrintAfp(tBytesData, nOffset, AFP_EBCDIC_SEGMENT))
			{
				AsciiEbcdicConverter.swapByteEbcdicToAscii(tBytesData, nOffset, 1);
				AsciiEbcdicConverter.swapByteEbcdicToAscii(tBytesData, nOffset+3, 3);
				AsciiEbcdicConverter.swapByteEbcdicToAscii(tBytesData, nOffset+9, 8);
				if (nLength > 23)
				{
					AsciiEbcdicConverter.swapByteEbcdicToAscii(tBytesData, nOffset+23, nLength-23);
				}
			}
		}
		else
		{
			AsciiEbcdicConverter.swapByteEbcdicToAscii(tBytesData, nOffset, nLength);
		}
	}
	
	private static boolean isSpecialPrintAfp(byte tBytesData[], int nOffset, byte[] arrToCheck)
	{
		if (tBytesData[nOffset+3] == arrToCheck[0] && tBytesData[nOffset+4] == arrToCheck[1] && tBytesData[nOffset+5] == arrToCheck[2])
			return true;
		else
			return false;
	}
}