/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 7 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import jlib.misc.StringUtil;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */



public class RWNumEdited
{
	static boolean isMaskAllZeroSuppression(String csFormat)
	{
		for(int n=0; n<csFormat.length(); n++)
		{
			char c = csFormat.charAt(n);
			if(!(c == 'Z' || c == '.' || c == ',' || c == '*' || c == '+' || c == '-' || c == 'C' || c == 'R' || c == 'D' || c == 'B'))
				return false;
		}
		return true;
	}
	
	static String internalFormatAndWrite(Dec dec, String csFormat, boolean bBlankWhenZero)
	{
		boolean bSignFilled = false;
		if(csFormat == null)
			return "";
		
		int nLgFormat = csFormat.length();
		if(dec.isZero())
		{
			if(isMaskAllZeroSuppression(csFormat))
			{
				return StringUtil.fillString(' ', nLgFormat);
			}
		}
		
		if(nLgFormat == 0)
			return "";
			
		if (bBlankWhenZero && dec.isZero())
		{
			return StringUtil.fillString(' ', nLgFormat);
		}
		
		StringBuffer sDest = new StringBuffer(nLgFormat);
		sDest.setLength(nLgFormat);
		
		String sSourceInt = dec.getUnsignedLongAsString();	// varNumberChunk.getAbsIntAsString();
		int nPosSource = sSourceInt.length() - 1;
		
		boolean bDoDecPart = false;

		int nDecimalSeparatorFormatPos = csFormat.indexOf('.');
		if(nDecimalSeparatorFormatPos == -1)	// dot (special insertion char) in format, then we will have a decimal part
			nDecimalSeparatorFormatPos = nLgFormat-1;
		else  
			bDoDecPart = true;
		
		int nPos$ = csFormat.indexOf('$');
		if(nPos$ == -1)
			nPos$ = csFormat.indexOf('£');
		
		// Integer part
		boolean bSignSet = false;
		boolean bSuppressLeading0 = false;
		for(int nFormatIndex=nDecimalSeparatorFormatPos; nFormatIndex>=0; nFormatIndex--)	// From right to left for integer part
		{
			char cSource = getDigitAtPosition(sSourceInt, nPosSource);	
			char cFormat = csFormat.charAt(nFormatIndex);
			if(cFormat == '9')	// Keep char at the source index current position
			{					
				sDest.setCharAt(nFormatIndex, cSource);
				nPosSource--;					
			}
			else if(cFormat == 'B')
				sDest.setCharAt(nFormatIndex, ' ');
			else if(cFormat == ' ')
				sDest.setCharAt(nFormatIndex, ' ');
			else if(cFormat == '0' || cFormat == '/' || cFormat == ',' || cFormat == '\'')	// Warning, ',' stands for 1000 separator, not decimal dot !!!
			{
				if(cFormat == '\'')
					sDest.setCharAt(nFormatIndex, ',');
				else
					sDest.setCharAt(nFormatIndex, cFormat);
			}
			else if(cFormat == '$' || cFormat == '£')
			{
				bSuppressLeading0 = true;
				sDest.setCharAt(nFormatIndex, cSource);
				nPosSource--;	
			}
			else if(cFormat == '+' || cFormat == '-')
			{
				if(nFormatIndex == nDecimalSeparatorFormatPos)	// Last char mask is sign
				{
					bSignFilled = true;
					if(cFormat == '+')
					{
					 	if(dec.isNegative())
					 		sDest.setCharAt(nLgFormat-1, '-');
						else
							sDest.setCharAt(nLgFormat-1, '+');
					}
					else if(cFormat == '-')
					{
					 	if(dec.isNegative())
					 		sDest.setCharAt(nLgFormat-1, '-');
						else
							sDest.setCharAt(nLgFormat-1, ' ');
					}
				 	nFormatIndex--;

					bSuppressLeading0 = true;
					sDest.setCharAt(nFormatIndex, cSource);
				}
				else	// leading - ou +
				{
					sDest.setCharAt(nFormatIndex, cSource);				
				}
				nPosSource--;
			}
			else if(cFormat == 'Z' || cFormat == '*')
			{
				bSuppressLeading0 = true;
				sDest.setCharAt(nFormatIndex, cSource);	// 1st pass: recopy the source char; it will be suppressed in next pass if needed 
				nPosSource--;
			}				
		}
		
		
		char cFormat = ' ';
		
		int nPosLastSuppress = -1;
		for(int nChar=0; nChar<nLgFormat && bSuppressLeading0; nChar++)
		{
			char cSource = sDest.charAt(nChar);
			cFormat = csFormat.charAt(nChar);
			if(cSource == '0')
			{
				if(cFormat == 'Z' || cFormat == '$' || cFormat == '£' || cFormat == '-' || cFormat == '+')
				{
					sDest.setCharAt(nChar, ' ');
					nPosLastSuppress = nChar;
				}				 
				else if(cFormat == '*')
					sDest.setCharAt(nChar, '*');
			}
			else if(cSource == ' ' || cSource == '$' || cSource == '£')
			{
			}
			else if(cSource == '.' || cSource == ',')	// Only leading 0; replace the , or . by the suppressing char
			{
				if(cFormat == '.' || cFormat == ',')
				{
					if(nChar > 0)
					{
						char cPrevious = sDest.charAt(nChar-1);
						if(cPrevious == ' ')	// we have a previous space
						{
							sDest.setCharAt(nChar, ' ');	// remove comma
							nPosLastSuppress = nChar;
						}
						if(cPrevious == '*')	// we have a previous star
							sDest.setCharAt(nChar, '*');	// remove comma
					}
				}
			}
			else	// Not a leading 0 anymore
			{
				bSuppressLeading0 = false;
			}
		}

		if(nPosLastSuppress != -1)
		{
			if(nPos$ != -1)
			{
				char cMoney = csFormat.charAt(nPos$);
				sDest.setCharAt(nPosLastSuppress, cMoney);	// set the money sign
			}
		}
		else if(nPos$ != -1)	// special case where there is no place left for the money sign, but we must set it insted of the forst digit
		{
			char cMoney = csFormat.charAt(nPos$);
			sDest.setCharAt(0, cMoney);	// set the money sign
		}
		
		if(bDoDecPart)	// Fill the decimal part
		{		
			// Second part: Decimal
			String sSourceDecPart = dec.getDecPart();	// String sSourceDecPart = varNumberChunk.getDecString();
			nPosSource = 0;	// Left to right
			for(int nFormatIndex=nDecimalSeparatorFormatPos; nFormatIndex<nLgFormat; nFormatIndex++)	// From left to right for dec part
			{
				cFormat = csFormat.charAt(nFormatIndex);
	
				if(cFormat == '9')	// Keep char at the source index current position
				{
					char cSource = getDigitAtPosition(sSourceDecPart, nPosSource);
					sDest.setCharAt(nFormatIndex, cSource);			
					nPosSource++;
				}
				else if(cFormat == '.')	// Insert dot 
					sDest.setCharAt(nFormatIndex, '.');
				else if(cFormat == 'B')	// Insert char
					sDest.setCharAt(nFormatIndex, ' ');	
				else if(cFormat == '0' || cFormat == '/' || cFormat == ',')
					sDest.setCharAt(nFormatIndex, cFormat);	
				else if(cFormat == 'Z' || cFormat == '*')
				{				
					char cSource = getDigitAtPosition(sSourceDecPart, nPosSource);
					if(cSource == '0' && cFormat == '*')
						sDest.setCharAt(nFormatIndex, '*');
					else
						sDest.setCharAt(nFormatIndex, cSource);			
					nPosSource++;
				}
			}				
		}
		
		if(!bSignFilled)
		{
			try
			{
				// Fill sign
				cFormat = csFormat.charAt(nLgFormat-1);	// Last char is the sign
				if(cFormat == '+')
				{
					// PJD commented updated because the sign erased the last digit
	//				sDest = sDest.deleteCharAt(nLgFormat-1);	// Delete first char to have the place to set the sign at the last position
	//				if(dec.isNegative())	//	if(varNumberChunk.isNegative())
	//					sDest.append('-');
	//				else
	//					sDest.append('+');
				 	if(dec.isNegative())
				 		sDest.setCharAt(nLgFormat-1, '-');
					else
						sDest.setCharAt(nLgFormat-1, '+');
				}
				else if(cFormat == '-')
				{
					// PJD commented updated because the sign erased the last digit
	//				sDest = sDest.deleteCharAt(nLgFormat-1);	// Delete first char to have the place to set the sign at the last position
	//			 	if(dec.isNegative())	//	if(varNumberChunk.isNegative())
	//					sDest.append('-');
	//				else
	//					sDest.append(' ');
					// PJD: Added
				 	if(dec.isNegative())
				 		sDest.setCharAt(nLgFormat-1, '-');
					else
						sDest.setCharAt(nLgFormat-1, ' ');
				}
				else	// Maybe sign at the begining
				{
					cFormat = csFormat.charAt(0);	// first char is the sign
					if(cFormat == '+' || cFormat == '-')
					{	
						int nPosLastSpace = getLastSpacePosition(sDest.toString(), csFormat);
						if(nPosLastSpace == -1)
							nPosLastSpace = 0;
						if(nPosLastSpace >= 0)
						{
							if(cFormat == '+')
							{				
								if(dec.isNegative())		// if(varNumberChunk.isNegative())
									sDest.setCharAt(nPosLastSpace, '-');
								else
									sDest.setCharAt(nPosLastSpace, '+');
							}
							else if(cFormat == '-')
							{
							 	if(dec.isNegative())	//if(varNumberChunk.isNegative())
									sDest.setCharAt(nPosLastSpace, '-');
								else
									sDest.setCharAt(nPosLastSpace, ' ');
							}
						}
					}
				}		
				char cLastFormat;
				// Clean leading - or + or $
				for(int n=0; n<nLgFormat;n++)
				{
					cLastFormat = cFormat;
					cFormat = csFormat.charAt(n);
					
					if(cFormat == '-' || cFormat == '+' || cFormat == ',')
					{
						char cDigit = sDest.charAt(n);
						if(cDigit == '0' || cDigit == ',' || cDigit == '-' || cDigit == '+' || cDigit == ' ')
						{
							if(cFormat == ',' && n > 0 && (cLastFormat == '+' || cLastFormat == '-'))
								cFormat = cLastFormat;

							if(cFormat == '+')
							{				
								if(dec.isNegative())		// if(varNumberChunk.isNegative())
									sDest.setCharAt(n, '-');
								else
									sDest.setCharAt(n, '+');
							}
							else if(cFormat == '-')
							{
							 	if(dec.isNegative())	//if(varNumberChunk.isNegative())
									sDest.setCharAt(n, '-');
								else
									sDest.setCharAt(n, ' ');
							}
							if(n > 0)
							{
								char cPrecDigit = sDest.charAt(n-1);
								if(cPrecDigit == '+' || cPrecDigit == '-' || cPrecDigit == ',' || cPrecDigit == ' ')
									sDest.setCharAt(n-1, ' ');
							}
						}
						else
						{
//							char cPrecDigit = sDest.charAt(n-1);
//							if(cPrecDigit == '+' || cPrecDigit == '-' || cPrecDigit == ',' || cPrecDigit == ' ')
//								sDest.setCharAt(n-1, ' ');
							break;
						}
					}
					else
					{
						break;
					}
				}
			}
			catch (Exception e)
			{
			}
		}
		
		String cs = sDest.toString();
		return cs;
	}
	
	static private int getLastSpacePosition(String sDest, String sFormat)
	{
		int nLg = sDest.length();
		for(int n=nLg-1; n>=0; n--)
		{
			char c = sDest.charAt(n);
			char cFormat = sFormat.charAt(n);
			if(c == ' ' && (cFormat == '-' || cFormat == '+'))
			{
				if (n < nLg-1 && sFormat.charAt(n+1) == ',' && sDest.charAt(n+1) == ' ')
				{
					// sign before separator thousand empty
					return n+1;
				}
				return n;
			}
		}
		return -1;
	}
	
	static private char getDigitAtPosition(String csSourceDecPart, int nPosSource)
	{
		if(nPosSource >= 0 && nPosSource < csSourceDecPart.length())
			return csSourceDecPart.charAt(nPosSource);
		return '0';
	}
}
