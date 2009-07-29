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
package nacaLib.stringSupport;

import jlib.log.Log;
import nacaLib.tempCache.CStr;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarAndEdit;

public class InspectConverting
{
	private Var m_var = null;
	private boolean m_bModeSingleChar = false;
	
	// Mode where from and to are var 
	private CStr m_cstrFrom = null;
	private int m_nLengthFrom = 0;
	private CStr m_cstrTo = null;
	private int m_nLengthTo = 0;
	
	// Mode Mode where from and to are single char
	private char m_cFrom = 0;
	private char m_cTo = 0;
	
	public InspectConverting(Var var, Var varFrom, Var varTo)
	{
		m_var = var;
		m_cstrFrom = varFrom.getOwnCStr();
		m_cstrTo = varTo.getOwnCStr();
		m_bModeSingleChar = false;
	}
	
	
	public InspectConverting(Var var, char cFrom, char cTo)
	{
		m_var = var;
		m_cFrom = cFrom;
		m_cTo = cTo;
		m_bModeSingleChar = true;
	}
		
	public boolean execute()
	{
		if(m_bModeSingleChar)
			return executeForSingleChar();
			
		CStr cstr = m_var.getOwnCStr();
		
		m_nLengthFrom = m_cstrFrom.length(); 		
		m_nLengthTo = m_cstrTo.length();
		
		if(m_nLengthFrom != m_nLengthTo)
		{
			m_cstrFrom = null;
			m_cstrTo = null;
			Log.logImportant("Error: InspectConverting variable " + m_var.getLoggableValue() + " with From:"+m_cstrFrom.getAsString() + " length != To: " + m_cstrTo.getAsString());
			return false;	// Pb: from and to must have same length 
		}
		
		int nLength = cstr.length();
		for(int n=0; n<nLength; n++)
		{
			char cIn = cstr.charAt(n);
			char cOut = getCorrespondingChar(cIn);
			cstr.setCharAt(n, cOut);			
		}
		
		m_cstrFrom = null;
		m_cstrTo = null;
		return true;
	}
	
	private char getCorrespondingChar(char cIn)
	{
		for(int n=0; n<m_nLengthFrom; n++)
		{
			char c = m_cstrFrom.charAt(n);
			if(c == cIn)
			{
				char cOut = m_cstrTo.charAt(n);
				return cOut;
			}			
		}
		return cIn;	// Not found
	}
	
	
	private boolean executeForSingleChar()
	{
		CStr cstr = m_var.getOwnCStr();
		
		int nLength = cstr.length();
		for(int n=0; n<nLength; n++)
		{
			char cIn = cstr.charAt(n);
			if(m_cFrom == cIn)
				cstr.setCharAt(n, m_cTo);
		}
		return true;
	}
}
