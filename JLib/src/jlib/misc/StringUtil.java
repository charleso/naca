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

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
 * Created on 18 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StringUtil
{
	public StringUtil()
	{
	}
	
	public static String leftPad(String csIn, int nRequiredLength, char cFill)
	{
		String csOut = new String();
		int nLgStringIn = csIn.length();
		int nNbcharToPad = nRequiredLength - nLgStringIn;
		if(nNbcharToPad > 0)
		{
			for(int n=0; n<nNbcharToPad; n++)
			{
				csOut = csOut + cFill;
			}
			csOut = csOut + csIn;
		}
		else	// Keep only leftmost chars
		{
			csOut = csIn.substring(0, nRequiredLength);
		}
		
		return csOut;
	}
	
	public static String FormatWithFill4LeftZero(int n)
	{
		if(n == 0)
			return "0000";
		if(n < 10)
			return "000" + String.valueOf(n);
		if(n < 100)
			return "00" + String.valueOf(n);
		if(n < 1000)
			return "0" + String.valueOf(n);
		return String.valueOf(n);
	}

	public static String FormatWithFill2LeftZero(int n)
	{
		if(n == 0)
			return "00";
		if(n < 10)
			return "0" + String.valueOf(n);
		return String.valueOf(n);
	}
	
	public static String rightPad(String csIn, int nRequiredLength, char cFill)
	{
		String csOut = new String();
		int nLgStringIn = csIn.length();
		int nNbcharToPad = nRequiredLength - nLgStringIn;
		if(nNbcharToPad > 0)
		{
			csOut = csIn;
			for(int n=0; n<nNbcharToPad; n++)
			{
				csOut = csOut + cFill;
			}
		}
		else	// Keep only leftmost chars
		{
			csOut = csIn.substring(0, nRequiredLength);
		}
		
		return csOut;
	}
	
	public static String decodeSignComp3String(String s, int nNbDigitInteger)
	{
		boolean bEvenNumberOfDigits = false;
		if((nNbDigitInteger % 2) == 0)
			bEvenNumberOfDigits = true;
		
		String sOut = new String();
		int nChar = 0;
		int nBibble = 0;
		char c = 0;
		int nLg = s.length();
		for(int nIndex=0; nIndex<nLg; nIndex++)
		{
			nChar = s.charAt(nIndex);
			nBibble = nChar / 16;
			c = (char)(nBibble + '0');
			sOut += c;
	
			if(nIndex == nLg-1)	// No sign in right nibble
			{
				nBibble = nChar % 16;
				if(nBibble == 12)	// +
					sOut += "+";
				else
					sOut += "-";
			}
			else
			{
				nBibble = nChar % 16;
				c = (char)(nBibble + '0');
				sOut += c;  
			}
		}
		if(bEvenNumberOfDigits)
		{
			// Remove leading 0 that was there as a placeholder due to the even number of digits + sign -> implies an odd number of nibbles; the leading compensated that odd number
			sOut = sOut.substring(1);	//sOut = sOut.substring(1, nNbDigitInteger+1);			
		}
		return sOut;
	}
	
	public static String decodeComp3String(String s, int nNbDigitInteger)
	{
		boolean bEvenNumberOfDigits = false;
		if((nNbDigitInteger % 2) == 0)
			bEvenNumberOfDigits = true;
		
		String sOut = new String();
		int nChar = 0;
		int nBibble = 0;
		char c = 0;
		int nLg = s.length();
		for(int nIndex=0; nIndex<nLg; nIndex++)
		{
			nChar = s.charAt(nIndex);
			nBibble = nChar / 16;
			c = (char)(nBibble + '0');
			sOut += c;
	
			if(nIndex == nLg-1)	// No sign in right nibble
			{
				nBibble = nChar % 16;
			}
			else
			{
				nBibble = nChar % 16;
				c = (char)(nBibble + '0');
				sOut += c;  
			}
		}
		if(bEvenNumberOfDigits)
		{
			// Remove leading 0 that was there as a placeholder due to the even number of digits + sign -> implies an odd number of nibbles; the leading compensated that odd number
			sOut = sOut.substring(1);	//sOut = sOut.substring(1, nNbDigitInteger+1);			
		}
		return sOut;
	}
	
	public static String setAsPrintableAstring(String csSource)
	{
		StringBuffer sb = new StringBuffer();
		for(int n=0; n<csSource.length(); n++)
		{
			char c = csSource.charAt(n);
			int nVal = c;
			if((nVal < 32 || nVal >= 128) && nVal != 0xA3)
			{
				String cs = FormatAs2CharHexa(nVal);
				sb.append("\\" + cs);	
			}
			else
				sb.append(c);
		}
		return sb.toString();
	}

	
	public static String replace(String csSource, String csOldChunk, String csNewChunk, boolean bAll)
	{
		int n = csSource.indexOf(csOldChunk);
		while(n >= 0)
		{
			String csLeft = csSource.substring(0, n);
			String csRight = csSource.substring(n+csOldChunk.length());
			csSource = csLeft + csNewChunk + csRight;
			
			if(bAll)
				n =  csSource.indexOf(csOldChunk);
			else
				n = -1;
		}
		return csSource;
	}
	
	public static String replace(String csSource, String csOldChunk, int nNewChunk, boolean bAll)
	{
		String csNewChunk = String.valueOf(nNewChunk);
		return replace(csSource, csOldChunk, csNewChunk, bAll);
	}
	
	public static String replace(String csSource, String csOldChunk, long lNewChunk, boolean bAll)
	{
		String csNewChunk = String.valueOf(lNewChunk);
		return replace(csSource, csOldChunk, csNewChunk, bAll);
	}
	
	static public String trimLeft(String s)
	{
		boolean b = false;
		if (s == null || s.length() == 0) 
			return s;
		
		int n = 0; 
		while (Character.isWhitespace(s.charAt(n)))
	    {
	    	n++;
	    	b = true;
	    }
		if(b)
			s = s.substring(n);
	    return s;
	}
	
	static public String trimLeft(String s, char c)
	{
		boolean b = false;
		if (s == null || s.length() == 0) 
			return s;
	    int n = 0;
	    int len = s.length();
		while (n < len && s.charAt(n) == c)
	    {
	    	n++;
	    	b = true;
	    }
		if(b)
			s = s.substring(n);
	    return s;
	}
	
	static public String trimRight(String s)
	{
		boolean b = false;
		if (s == null || s.length() == 0) 
			return s;
		int len = s.length();
		while (len > 0 && Character.isWhitespace(s.charAt(len-1)))
		{
			len--;
			b = true;
	    }
		if(b)
			s = s.substring(0, len);
	    return s;
	}
	
	static public String trimRight(String s, char c)
	{
		boolean b = false;
		if (s == null) 
			return s;
	    int len = s.length();
	    while (len > 0 && s.charAt(len-1) == c)
	    {
	    	len--;
	    	b = true;
	    }
	    if(b)
	    	s = s.substring(0, len);
	    return s;
	}
	
	static public String trimLeftRight(String s)
	{
		return trimLeft(trimRight(s));		
	}
	
	static public String trimLeftRight(String s, char c)
	{
		return trimLeft(trimRight(s, c), c);		
	}
	
	static public String concatArgWithSeparator(String csBase, String csSeparator, String csArg)
	{
		if(!csBase.endsWith(csSeparator))
		{
			csBase += csSeparator;
		}
		csBase += csArg;
		return csBase; 
	}
	
	static public boolean isEmpty(String cs)
	{
		if(cs == null)
			return true;
		if(cs.length() == 0)
			return true;
		return false;
	}
	
	static public boolean trimEquals(String cs1, String cs2)
	{
		if(cs1.trim().equals(cs2.trim()))
			return true;
		return false;
	}
	
	static public boolean isEmptyOrOnlyWhitespaces(String cs)
	{
		if(cs == null)
			return true;
		return isEmpty(cs.trim());
	}
	
	static public boolean isEmptyOrZero(String cs)
	{
		if(cs == null)
			return true;
		if("0".equals(cs))
			return true;
		return isEmpty(cs.trim());
	}
	
	static public int getNbEmptyOrOnlyWhitespaces(String cs1, String cs2)
	{
		int n = 0;
		boolean b = isEmpty(cs1.trim());
		if(b)
			n++;
		b = isEmpty(cs2.trim());
		if(b)
			n++;
		return n;
	}
	
	static public int getNbEmptyOrOnlyWhitespaces(String cs1, String cs2, String cs3)
	{
		int n = 0;
		boolean b = isEmpty(cs1.trim());
		if(b)
			n++;
		b = isEmpty(cs2.trim());
		if(b)
			n++;
		b = isEmpty(cs3.trim());
		if(b)
			n++;
		return n;
	}
	
	static public String removeSurroundingQuotes(String cs)
	{
		if(cs != null)
		{
			if(cs.length() > 0)
				if(cs.charAt(0) == '\"')
					cs = cs.substring(1);
			if(cs.length() > 0)
				if(cs.charAt(cs.length()-1) == '\"')
					cs = cs.substring(0, cs.length()-1);
		}
		return cs;
	}
	
	static public String FormatAs4CharHexa(int nValue)
	{
		String cs = "";
		char c;
		for (int n=0; n<4; n++)
		{			
			int nChar = nValue % 16;
			nValue = nValue / 16;
			if(nChar <= 9)
				c = (char) ('0' + nChar);
			else
				c = (char) ('A' + nChar - 10);
			cs = c + cs;			
		}
		return cs;
	}
	
	static public String FormatAs2CharHexa(int nValue)
	{
		String cs = "";
		char c;
		for (int n=0; n<2; n++)
		{			
			int nChar = nValue % 16;
			nValue = nValue / 16;
			if(nChar <= 9)
				c = (char) ('0' + nChar);
			else
				c = (char) ('A' + nChar - 10);
			cs = c + cs;			
		}
		return cs;
	}

	static public String FormatAs2CharHexa(byte byValue)
	{
		if(byValue < 0)
			return FormatAs2CharHexa((int)byValue + 256);
		else
			return FormatAs2CharHexa((int)byValue);
	}
	
	public static String extractCurrentWord(StringRef rcs)
	{
		return extractCurrentWord(rcs, true, null);
	}
	
	public static String extractCurrentWord(StringRef rcs, boolean bSepIsWithSpace, String csOtherSep)
	{
		String cs = rcs.get();
		cs = cs.trim();
		int nLen = cs.length();
		for(int n=1; n<nLen-1; n++)
		{
			boolean b = false;
			
			char c = cs.charAt(n);
			if(bSepIsWithSpace)
				if(Character.isWhitespace(c))
					b = true;
			if(!b && csOtherSep != null)
			{
				if(csOtherSep.indexOf(c) != -1)
					b = true;
			}
			
			if(b)
			{
				String csWord = cs.substring(0, n);
				String csRight = cs.substring(n);
				csRight = csRight.trim();
				rcs.set(csRight);
				
				return csWord;
			}
		}
		return null;
	}
	
	public static String extractCurrentWordIncludingLast(StringRef rcs, boolean bSepIsWithSpace, String csOtherSep)
	{
		String csWord = extractCurrentWord(rcs, bSepIsWithSpace, csOtherSep);
		if(csWord == null)
		{
			csWord = rcs.get();
			rcs.set(null);
		}
		return csWord;
	}
	
	/*
	private static int indexOfNoCase(char[] source, int sourceOffset, int sourceCount, char[] target, int targetOffset, int targetCount, int fromIndex) 
    {
		if (fromIndex >= sourceCount) 
		{
	         return (targetCount == 0 ? sourceCount : -1);
		}
	 	if (fromIndex < 0) 
	 	{
	 	    fromIndex = 0;
	 	}
		if (targetCount == 0) 
		{
		    return fromIndex;
		}
	
	     char first  = target[targetOffset];
	     int max = sourceOffset + (sourceCount - targetCount);
	
	     int i = sourceOffset + fromIndex;
	     char cSource = source[i];
	     if(cSource >= 'a' && cSource <= 'Z')
	    	 cSource -= 'a' + 'A';
	     for (; i <= max; i++) 
	     {
	         // Look for first character.
	         if (cSource != first) 
	         {
	             while (++i <= max)
	             {
	        	     char cSource = source[i];
	        	     if(cSource >= 'a' && cSource <= 'Z')
	        	    	 cSource -= 'a' + 'A';
	            	 if(cSource == first)
	            		 break;
	         }
	
	         // Found first character, now look at the rest of v2
	         if (i <= max) 
	         {
	             int j = i + 1;
	             int end = j + targetCount - 1;
	             for (int k = targetOffset + 1; j < end && source[j] == target[k]; j++, k++);
	
	             if (j == end) 
	             {
	                 // Found whole string.
	                 return i - sourceOffset;
	             }
	         }
	     }
	     return -1;
	 }
	 */
	
	public static String getFirstWord(String cs)
	{
		cs = cs.trim();
		int nLen = cs.length();
		for(int n=1; n<nLen-1; n++)
		{
			char c = cs.charAt(n);
			if(Character.isWhitespace(c))
			{
				String csWord = cs.substring(0, n);
				return csWord;
			}
		}
		return cs;
	}	
	
	public static String removeFirstWord(String cs)
	{
		// Skip leading whitespaces
		int nLen = cs.length();
		for(int n=0; n<nLen; n++)
		{
			char c = cs.charAt(n);
			if(!Character.isWhitespace(c))
			{
				for(int m=n; m<nLen; m++)
				{
					c = cs.charAt(m);
					if(Character.isWhitespace(c))
					{
						String csRight = cs.substring(m);
						return csRight;
					}
				}
				return cs;
			}
		}			
		return "";
	}

	public static String getWordFollowing(String csText, String csMarker)
	{
		int n = csText.indexOf(csMarker);
		if(n != -1)
		{
			String csRight = csText.substring(n + csMarker.length());
			String csFollowingWord = StringUtil.getFirstWord(csRight);
			return csFollowingWord;
		}
		return null;
	}
	
	public static String getUncotedParameterValue(String csText, String csParamName)
	{
		csParamName = csParamName + "=";
		String cs = getWordFollowing(csText, csParamName);
		if(cs != null)
		{
			if(cs.startsWith("\""))
			{
				int nPos=1;	// Skip leading "
				while(nPos < cs.length())
				{
					char c = cs.charAt(nPos);
					if(c == '\"')
					{
						cs = cs.substring(1, nPos); 
						return cs;
					}
					nPos++;
				}
			}
		}
		return null;
	}
	
	public static String getFirstWordWithStopList(String cs, String csStopListChars)
	{
		return getFirstWordWithStopListAtOffset(cs, 1, csStopListChars);
	}
	
	public static String getFirstWordWithStopListAtOffset(String cs, int nOffsetSource, String csStopListChars)
	{
		cs = cs.trim();
		int nLen = cs.length();
		for(int n=nOffsetSource; n<nLen; n++)
		{
			char c = cs.charAt(n);
			if(Character.isWhitespace(c) || csStopListChars.indexOf(c) != -1)
			{
				String csWord = cs.substring(0, n);
				return csWord;
			}
		}
		return "";
	}
	
	public static String extractFirstWordWithStopList(String cs[], String csStopListChars)
	{
		cs[0] = cs[0].trim();
		int nLen = cs[0].length();
		for(int n=1; n<nLen; n++)
		{
			char c = cs[0].charAt(n);
			if(csStopListChars.indexOf(c) != -1)
			{
				String csWord = cs[0].substring(0, n);
				cs[0] = cs[0].substring(n+1);
				return csWord;
			}
		}
		String csLast = cs[0];
		cs[0] = "";		
		return csLast;
	}
	
	public static int getNextWhiteSpacePosition(String cs)
	{
		// cs must be trimmed
		int nLen = cs.length();
		for(int n=0; n<nLen-1; n++)
		{
			char c = cs.charAt(n);
			if(Character.isWhitespace(c))
			{
				return n;
			}
		}
		return nLen;		
	}
	
	static public String fillString(char c, int nLength)
	{
		String cs = new String();
		for(int n=0; n<nLength; n++)
			cs += c;
		return cs;		
	}
	
	public static boolean isAllDigits(String cs)
	{
		for(int n=0; n<cs.length(); n++)
		{
			char c = cs.charAt(n);
			if(c < '0' || c > '9')
				return false;
		}
		return true;
	}
	
	public static String makeFullTableName(String csPrefix, String csTableName)
	{
		if(csPrefix != null)
			return csPrefix + "." + csTableName;
		return csTableName;
	}
	
	public static String getTablePrefix(String csFullTableName)
	{
		int nPos = csFullTableName.indexOf('.');
		if(nPos == -1)
			return "";
		String csLeft = csFullTableName.substring(0, nPos);
		return csLeft;
	}
	
	public static String getUnprefixedTableName(String csFullTableName)
	{
		int nPos = csFullTableName.indexOf('.');
		if(nPos == -1)
			return csFullTableName;
		String csRight = csFullTableName.substring(nPos+1);
		return csRight;
	}
	
	public static boolean startsWithNoCase(String csValue, String csStart)
	{
		csValue = csValue.toUpperCase();
		csStart = csStart.toUpperCase();
		return csValue.startsWith(csStart); 
	}
	
	public static boolean startsWithNoCase(String csValue, String csStart, int nOffset)
	{
		csValue = csValue.toUpperCase();
		csStart = csStart.toUpperCase();
		return csValue.startsWith(csStart, nOffset); 
	}
	
	public static int convertHexDigitAsInt(char c)
	{
		if(c >= '0' && c <= '9')
			return c - '0';
		if(c >= 'A' && c <= 'F')
			return c - 'A' + 10;
		if(c >= 'a' && c <= 'f')
			return c - 'a' + 10;
		return 0;
	}
	
	// Methods originated form StringHelper for compataibility
//	***************************************************************************************
//	**                   Fonction qui interprete une string comme une date.              **
//	***************************************************************************************
//	 Le format est le suivant:
//	 chiffres-paschiffres-chiffres-paschiffres- ...
//	 Par exemple: 12/4/2002 12h25:45
//	 Le premier groupe de chiffres c'est le jour.
//	 Le deuxieme c'est le mois.
//	 Le troisieme c'est l'année.
//	 Les heures, les minutes et les secondes.
//	 Les groupes de chiffres peuvent être séparés par n'importe quoi.
	public static Calendar StringToCalendar(String s)
	{
		return StringToCalendar(s, "DMYhms");
	}

//	Format_ represente l'ordre des groupes de chiffres.
	public static Calendar StringToCalendar(String s, String format)
	{
		int day=1, month=1, year=0;       // Jour, mois, année...
		int hour=0, minutes=0, seconds=0; // heure, minutes, secondes pour la date representée par s.

//	***************************** Sépare la chaîne en groupes de chiffres *******************************
		if (s == null)
			s = "";
		String[] numbers = s.split("[^0-9]+");	// Liste des groupes de chiffres dans la chaîne fournie.
		int nn = numbers.length;
		if (format.length() < nn)
			nn=format.length();

//	******************* Copie chaque groupe de chiffre dans le bon compteur *****************************
		boolean bThereIsDate = false;      // Indique qu'il y à effectivement quelque chose d'assimilable a une date
		for(int n=0; n < nn; n++) 
		{
			bThereIsDate = true;
			char f = format.charAt(n);	// Un caractere de format.
			switch(f) 
			{
				case 'D':
					day=Integer.parseInt(numbers[n]);
					break;
				case 'M':
					month=Integer.parseInt(numbers[n]);
					break;
				case 'Y':
					year=Integer.parseInt(numbers[n]);
					if ((year<=50) && (year>0)) year+=2000;
					if ((year>50) && (year<100)) year+=1900;
					break;
				case 'h':
					hour=Integer.parseInt(numbers[n]);
					break;
				case 'm':
					minutes=Integer.parseInt(numbers[n]);
					break;
				case 's':
					seconds=Integer.parseInt(numbers[n]);
					break;
			}	
		}

//	****************************** Construit la date ***************************************
		if (bThereIsDate)
		{
			Calendar d = new GregorianCalendar();	// Date representée par s.
			d.set(year, month-1+ Calendar.JANUARY, day, hour, minutes, seconds);
			d.set(Calendar.MILLISECOND, 0);
			return d;
		} 
		else
			return null;
    }

//	*****************************************************************************************
//	**         Replaces all occurrences of a string in a StringBuilder                     **
//	*****************************************************************************************
	/**
	 * Replaces all occurrences of a string in a StringBuilder.
	 * @param hasStack Where to look for.
	 * @param needle What to look for.
	 * @param s What has to replace the needle.
	 */
	public static void replace(StringBuilder sb, String csSearch, String csReplace)
	{
		int n = 0;
		n = sb.indexOf(csSearch, n);		
		while(n >= 0) 
		{
			sb.replace(n, n+csSearch.length(), csReplace);
			n += csReplace.length();
			n = sb.indexOf(csSearch, n);
		}
	}
	
	public static boolean replaceWithStatus(StringBuilder sb, String csSearch, String csReplace)
	{
		try 
		{
			replace(sb, csSearch, csReplace);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public static String renderCouple(String csName, String csValue)
	{
		return csName + "='" + csValue + "'";
	}
	
	public static String toString(Object o)
	{
		if(o == null)
			return "(null)";
		return o.toString();
	}
	/**
	 * Encode in HTML char the provide string
	 * @param csInput inout String
	 * @return encoded string
	 * Example: 
	 * Input = "ha<toto>&tutu"
	 * Output= "ha&lt;toto&gt;&amp;tutu" 
	 */
	public static String encodeAsHTML(String csInput)
	{
		if(isEmpty(csInput))
			return csInput;
		
		HTMLCharsetConverter.initOnce();
		
		StringBuilder sbOut = new StringBuilder();
		int nNbInputChars = csInput.length();
		for(int n=0; n<nNbInputChars; n++)
		{
			char c = csInput.charAt(n);
			String cs = HTMLCharsetConverter.getEncodedString(c);
			sbOut.append(cs);			
		}
		return sbOut.toString(); 
	}
	/**
	 * Decode form HTM format the provded string
	 * @param csInput string to decode
	 * @return decoded string
	 * Example:
	 * Input = "ha&lt;toto&gt;&amp;tutu"
	 * Output = "ha<toto>&tutu"
	 */
	public static String decodeFROMHTML(String csInput)
	{
		if(isEmpty(csInput))
			return csInput;
		
		HTMLCharsetConverter.initOnce();
		
		StringBuilder sbOut = new StringBuilder();
		int nIndexStart = csInput.indexOf("&");
		int nIndexEnd = csInput.indexOf(";");
		while(nIndexStart >= 0 && nIndexEnd > nIndexStart)
		{
			String csLeft = csInput.substring(0, nIndexStart);
			sbOut.append(csLeft);
			String csHTMLValue = csInput.substring(nIndexStart, nIndexEnd+1);
						
			char c = HTMLCharsetConverter.getDecodedChar(csHTMLValue);
			sbOut.append(c);
			
			csInput = csInput.substring(nIndexEnd+1);
			nIndexStart = csInput.indexOf("&");
			nIndexEnd = csInput.indexOf(";");
		}
		if(csInput.length() > 0)
			sbOut.append(csInput);
		return sbOut.toString();
		
	}
	
	
	public static ArrayList<String> extractPrefixedKeywords(String csValues, String csOptionalPrefixed)
	{
		boolean bUsePrefix = !StringUtil.isEmpty(csOptionalPrefixed);
		
		ArrayList<String> arr = null;
		csValues = csValues.trim();
		
		for(int n=0; n<csValues.length(); n++)
		{
			char c = csValues.charAt(n);
			if(Character.isWhitespace(c))
			{
				String csWord = csValues.substring(0, n);

				if(bUsePrefix)
				{
					if(csWord.startsWith(csOptionalPrefixed))
						arr = add(arr, csWord);
				}
				else
					arr = add(arr, csWord);
				
				csValues = csValues.substring(n).trim();
				n = 0;
			}
		}
		if(csValues.startsWith(csOptionalPrefixed))
		{
			arr = add(arr, csValues);
		}
		return arr;
	}
	
	
	private static ArrayList<String> extractBraketedPattern(ArrayList<String> arr, StringRef rcsValues, String csPattern)
	{
		String csValues = rcsValues.get();
		
		int nIndexBegin = csValues.indexOf(csPattern);
		while(nIndexBegin >= 0)
		{
			int nIndexEnd = csValues.indexOf("]", nIndexBegin);
			if( nIndexEnd>= 0)
			{
				String csLeft = csValues.substring(0, nIndexBegin);
				String csRight = csValues.substring(nIndexEnd+1);
				String csWord = csValues.substring(nIndexBegin, nIndexEnd+1);
				if(csRight.startsWith("@"))
				{
					csWord = csValues.substring(nIndexBegin, nIndexEnd+1);
					int n = getNextWhiteSpacePosition(csRight);
					if(n >= 0)
					{
						csWord += csRight.substring(0, n);
						csRight = csRight.substring(n);
					}
				}
				if(csWord.startsWith(csPattern))
					arr = add(arr, csWord);
				csValues = csLeft + csRight; 
			}
			
			nIndexBegin = csValues.indexOf(csPattern);
		}
		rcsValues.set(csValues);
		return arr;
	}

	public static ArrayList<String> extractPrefixedKeywordsBracketed(String csValues, String csOptionalPrefixed)
	{
		boolean bUsePrefix = !StringUtil.isEmpty(csOptionalPrefixed);
		
		ArrayList<String> arr = null;
		csValues = csValues.trim();
		
		// Isolate csOptionalPrefixed$[xxx]
		// Isolate csOptionalPrefixed[xxx]
		
		StringRef rcs = new StringRef(csValues);
		
		String csPattern = csOptionalPrefixed + "$[";
		arr = extractBraketedPattern(arr, rcs, csPattern);
		
		csPattern = csOptionalPrefixed + "[";
		arr = extractBraketedPattern(arr, rcs, csPattern);
		
		csValues = rcs.get();
		
		for(int n=0; n<csValues.length(); n++)
		{
			char c = csValues.charAt(n);
			if(Character.isWhitespace(c))
			{
				String csWord = csValues.substring(0, n);

				if(bUsePrefix)
				{
					if(csWord.startsWith(csOptionalPrefixed))
						arr = add(arr, csWord);
				}
				else
					arr = add(arr, csWord);
				
				csValues = csValues.substring(n).trim();
				n = 0;
			}
		}
		if(csValues.startsWith(csOptionalPrefixed))
		{
			arr = add(arr, csValues);
		}
		return arr;
	}
	
	private static ArrayList<String> add(ArrayList<String> arr, String cs)
	{
		if(arr == null)
			arr = new ArrayList<String>();

		arr.add(cs);
		return arr;
	}
	
	// Returns the number of line of the text. A null string is 0 line length; an empty one is 1 line length a\nb is 2 lines length, a\nb\c\n is 4 line length
	public static int getLineCount(String csText)
	{
		int nNblines = 1;
		if(csText == null)
			return 0;
		for(int n=0; n<csText.length(); n++)
		{
			char c = csText.charAt(n);
			if(c == '\n')
				nNblines++;
		}
		return nNblines;
	}
	
	public static String getUnprefixedMemberName(String csMemberName, String csPrefix, Type fieldType)
	{
		if(csMemberName.startsWith(csPrefix))
		{
			csMemberName = csMemberName.substring(csPrefix.length());
			if(fieldType.equals(Integer.TYPE))
			{
				if(csMemberName.startsWith("n"))
					return csMemberName.substring(1);
			}
			else if(fieldType.equals(String.class))
			{
				if(csMemberName.startsWith("cs"))
					return csMemberName.substring(2);
			}
			else if(fieldType.equals(Double.TYPE))
			{
				if(csMemberName.startsWith("d"))
					return csMemberName.substring(1);
			}
			else if(fieldType.equals(Long.TYPE))
			{
				if(csMemberName.startsWith("l"))
					return csMemberName.substring(1);
			}
			else if(fieldType.equals(Short.TYPE))
			{
				if(csMemberName.startsWith("s"))
					return csMemberName.substring(1);
			}
			else if(fieldType.equals(java.sql.Date.class))
			{
				if(csMemberName.startsWith("dt"))
					return csMemberName.substring(2);
			}
			else if(fieldType.equals(Timestamp.class))
			{
				if(csMemberName.startsWith("ts"))
					return csMemberName.substring(2);
			}
		}
		return csMemberName;
	}
	
	/**
     * This method ensures that the output String has only
     * valid XML unicode characters as specified by the
     * XML 1.0 standard. For reference, please see
     * <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the
     * standard</a>. This method will return an empty
     * String if the input is null or empty.
     *
     * @param in The String whose non-valid characters we want to remove.
     * @return The in String, stripped of non-valid characters.
     */
    public static String stripNonValidXMLCharacters(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.

        if (in == null || ("".equals(in))) return ""; // vacancy test.
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((current == 0x9) ||
                (current == 0xA) ||
                (current == 0xD) ||
                ((current >= 0x20) && (current <= 0xD7FF)) ||
                ((current >= 0xE000) && (current <= 0xFFFD)) ||
                ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }    		
    
    public static String addLeadingSpaces(String cs, int nNbSpaces)
    {
    	for(int n=0; n<nNbSpaces; n++)
    		cs = " " + cs;
    	return cs;
    }
    
    public static String addTrailingSpaces(String cs, int nNbSpaces)
    {
    	for(int n=0; n<nNbSpaces; n++)
    		cs = cs + " ";
    	return cs;
    }
}