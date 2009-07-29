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
package nacaLib.dbUtils;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class BaseFileScriptReader
{	
	
	protected String removeCrLf(String csPhysicalLine)
	{
		int nLg = csPhysicalLine.length();
		if(nLg > 0)
		{
			char c = csPhysicalLine.charAt(nLg - 1);
			if(c == (char)0x0D || c == (char)0x0A)
			{
				csPhysicalLine = csPhysicalLine.substring(0, nLg - 1);
				csPhysicalLine = removeCrLf(csPhysicalLine);
			}
		}
		return csPhysicalLine;
	}
		
	protected  String removeCommentAndLineNumber(String csPhysicalLine)
	{	
		if(csPhysicalLine.startsWith("--"))	// comment
			return "";
		
		if(csPhysicalLine.startsWith("**"))	// comment
			return "";		
		
		if(csPhysicalLine.length() == 80)	// Compatible mode
		{
			String csLineNumber = csPhysicalLine.substring(72, 79);
			if(isValidLineNumer(csLineNumber))
				csPhysicalLine = csPhysicalLine.substring(0, 72);
		}
		
		return csPhysicalLine;
	}
	
	protected boolean isEndOfSQLLine(String csPhysicalLine)
	{
		if(csPhysicalLine.endsWith(";"))
			return true;
		return false;
	}
	
	private boolean isValidLineNumer(String csLineNumber)
	{
		if(csLineNumber.length() == 7)
		{
			for(int n=0; n<7; n++)
			{
				char c = csLineNumber.charAt(n);
				if(!(c >= '0' && c <= '9'))
					return false;					
			}
			return true;				
		}
		return false;
	}
}
