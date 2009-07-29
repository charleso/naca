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
/*
 * Created on 6 déc. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.stringSupport;
// import nacaLib.base.*;
import nacaLib.varEx.CobolConstant;
import nacaLib.varEx.VarAndEdit;

public class InspectReplacing
{	
	public static final InspectReplacingType TypeFirst = new InspectReplacingType();
	public static final InspectReplacingType TypeLeading = new InspectReplacingType();	
	public static final InspectReplacingType TypeAllLowValue = new InspectReplacingType();
	public static final InspectReplacingType TypeAllHighValue = new InspectReplacingType();
	public static final InspectReplacingType TypeAll = new InspectReplacingType();
	public static final InspectReplacingType TypeLeadingSpaces = new InspectReplacingType();
	
	public InspectReplacing(VarAndEdit var)
	{
		m_var = var;
	}
	
	public InspectReplacing before(String csBefore)
	{
		m_csBefore = csBefore;
		return this;
	}
	
	public InspectReplacing before(VarAndEdit varBefore)
	{
		m_csBefore = varBefore.getString();
		return this;
	}

	public InspectReplacing after(String csAfter)
	{
		m_csAfter = csAfter;
		return this;
	}

	public InspectReplacing after(VarAndEdit varAfter)
	{
		m_csAfter = varAfter.getString();
		return this;
	}

	
	public InspectReplacing first(String cs)
	{
		m_InspectReplacingType = TypeFirst;
		m_csPattern = cs;
		return this;
	}
	
	public InspectReplacing first(VarAndEdit var)
	{
		m_InspectReplacingType = TypeFirst;
		m_csPattern = var.getString();
		return this;
	}
	
	public InspectReplacing leading(String cs)
	{
		m_InspectReplacingType = TypeLeading;
		m_csPattern = cs;
		return this;
	}
	
	public InspectReplacing allLowValues()
	{
		m_InspectReplacingType = TypeAllLowValue;
		return this ;
	}
	
	public InspectReplacing allHighValues()
	{
		m_InspectReplacingType = TypeAllHighValue;
		return this ;
	}
	
	public InspectReplacing all(String s)
	{
		m_InspectReplacingType = TypeAll;
		m_csPattern = s ;
		return this ;
	}
	
	public InspectReplacing all(VarAndEdit v)
	{
		m_InspectReplacingType = TypeAll;
		m_csPattern = v.getString() ;
		return this ;
	}
	
	public InspectReplacing allSpaces()
	{
		m_InspectReplacingType = TypeAll;
		m_csPattern = " " ;
		return this ;
	}

	public InspectReplacing leadingSpaces()
	{
		m_InspectReplacingType = TypeLeadingSpaces;
		return this ;
	}
		
	public void bySpaces()
	{
		by(CobolConstant.Space.getValue());
	}

	public void byLowValues()
	{
		by(CobolConstant.LowValue.getValue());
	}

	public void byHighValues()
	{
		by(CobolConstant.HighValue.getValue());
	}

	public void byZero()
	{
		by(CobolConstant.Zero.getValue());
	}
	
	public void by(char c)
	{
		String cs = new String();
		cs += c;
		by(cs);
	}
	
	public void by(VarAndEdit var)
	{
		String cs = var.getString();
		by(cs);
	}
		
	public void by(String csReplacing)
	{
		int nNbCall = 0;
		m_csSource = m_var.getString();
		String csPrefixe = null;
		String csSuffixe = null;
		
		// Find substring where to count
		if(m_csAfter != null)	// We have a starting point
		{
			int nPosAfter = m_csSource.indexOf(m_csAfter);
			if(nPosAfter == -1)	// No delimiter found: Nothing to do
				return;
			csPrefixe = m_csSource.substring(0, nPosAfter+1);
			m_csSource = m_csSource.substring(nPosAfter+1);			
		}
		
		if(m_csBefore != null)	// We have a ending point
		{
			int nPosBefore = m_csSource.indexOf(m_csBefore);
			if(nPosBefore == -1)	// No delimiter found: Nothing to do
				return;
			csSuffixe = m_csSource.substring(nPosBefore);
			m_csSource = m_csSource.substring(0, nPosBefore);
		}
		
		StringBuffer csDest = new StringBuffer(m_csSource);
		
		int nReplaceLength = getReplaceLength();
		int nPos = getReplacePosition(nNbCall, 0, nReplaceLength);
		while(nPos != -1)
		{
			nNbCall++;
			// Replace chars
			for(int nDest=nPos, nReplacing=0; nDest<nPos+nReplaceLength; nDest++)
			{
				char cReplacingChar = csReplacing.charAt(nReplacing);
				csDest.setCharAt(nDest, cReplacingChar);	
			
				nReplacing++;
				if(nReplacing == csReplacing.length())
					nReplacing = 0;
			}
			
			// Find next occurence
			nPos += nReplaceLength;
			int nPosPattern = getReplacePosition(nNbCall, nPos, nReplaceLength);
			if(nPosPattern == -1)
				nPos = -1;
			else
				nPos += nPosPattern;
		}
		
		// Destination string is in csDest
		if(csPrefixe != null || csSuffixe != null)
		{
			String cs = new String(csDest.toString());
			if(csPrefixe != null)
				cs = csPrefixe + cs;
			if(csPrefixe != null)
				cs = cs + csSuffixe;
			m_var.set(cs);
		}
		else
			m_var.set(csDest.toString());		
	}
	
	private int getReplacePosition(int nNbCall, int nPosStart, int nNbOccurences)
	{
		String csSource = m_csSource;
		if(nPosStart != 0)
			csSource = m_csSource.substring(nPosStart);
		int nLg = csSource.length();
		if(m_InspectReplacingType == TypeFirst)
		{
			if(nNbCall == 0 && nPosStart == 0)	// 1st call
			{
				int nPosPattern = csSource.indexOf(m_csPattern);	// found the 1st position of the pattern
				if(nPosPattern >= 0)
					return nPosPattern;
			}
		}
		else if(m_InspectReplacingType == TypeLeading)
		{
			if(nNbCall == 0 && nPosStart == 0)	// 1st call
			{
				int nPosPattern = csSource.indexOf(m_csPattern);	// found the 1st position of the pattern
				if(nPosPattern >= 0)
					return nPosPattern;
				return -1;
			}
			int nPosPattern = csSource.indexOf(m_csPattern);
			return nPosPattern; 
		}
		else if(m_InspectReplacingType == TypeAll)
		{
			int nPosPattern = csSource.indexOf(m_csPattern);
			return nPosPattern; 
		}
		else if(m_InspectReplacingType == TypeAllLowValue)
		{
			// Try to find a consecutive range of nReplaceLength low value chars
 			int nOccurences = 0;
 			int n = 0;
			while(n != nLg && nOccurences < nNbOccurences)
			{
				char c = csSource.charAt(n);
				if(c == CobolConstant.LowValue.getValue())
				{
					nOccurences++;
					if(nOccurences == nNbOccurences)
						return n;
					n++;
				}
				else	// Retry from this position
				{
					nOccurences = 0;
					n++;
				}		
			}
			if(nOccurences == nNbOccurences)
				return n;
			return -1;
		}
		else if(m_InspectReplacingType == TypeAllHighValue)
		{
			// Try to find a consecutive range of nReplaceLength low value chars
 			int nOccurences = 0;
 			int n = 0;
			while(n != nLg && nOccurences < nNbOccurences)
			{
				char c = csSource.charAt(n);
				if(c == CobolConstant.HighValue.getValue())
				{
					nOccurences++;
					if(nOccurences == nNbOccurences)
						return n;
					n++;
				}
				else	// Retry from this position
				{
					nOccurences = 0;
					n++;
				}		
			}
			if(nOccurences == nNbOccurences)
				return n;
			return -1;
		}
		else if(m_InspectReplacingType == TypeLeadingSpaces)
		{
			if(nLg > 0)
			{
				// Try to find all consecutive range of nReplaceLength low value chars
				char c = csSource.charAt(0);	// nPosStart);
				if(c == ' ')
					return 0;
			}
			return -1; 
		}
		return -1;
	}

	private int getReplaceLength()
	{
		if(m_InspectReplacingType == TypeLeadingSpaces)
			return 1;
		else if(m_InspectReplacingType == TypeAllLowValue)
			return 1;
		else if(m_InspectReplacingType == TypeAllHighValue)
			return 1;
		return m_csPattern.length();
	}
	
	VarAndEdit m_var = null;
	String m_csBefore = null;
	String m_csAfter = null;
	String m_csSource = null;
	String m_csPattern = null;
	InspectReplacingType m_InspectReplacingType = null;
}
