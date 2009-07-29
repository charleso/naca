/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

public class AdvancedFileDescriptorMode
{
	private boolean m_bVariableLength = false;
	private boolean m_bUsesRecordLengthHeader = false;
	private boolean m_bEndsCRLF = false;
	private boolean m_bEndsLF = false;
	//private boolean m_bTextMode = false;
	private boolean m_bMFCobolLineSequential = false;
	//private boolean m_bFromMFCobol = false;
	//private boolean m_bToMFCobol = false;
	
	
	public String toString()
	{
		String cs = "Advanced mode: ";
		if(m_bVariableLength)
			cs += "VariableLength; ";
		if(m_bUsesRecordLengthHeader)
			cs += "UsesRecordLengthHeader; ";
		if(m_bEndsCRLF)
			cs += "CRLF; ";	
		if(m_bEndsLF)
			cs += "LF; ";	
//		if(m_bTextMode)
//			cs += "TextMode; ";			
		if(m_bMFCobolLineSequential)
			cs += "MFCobolLineSequential; ";	
//		if(m_bFromMFCobol)
//			cs += "FromMFCobol; ";	
//		if(m_bToMFCobol)
//			cs += "ToMFCobol; ";
		return cs;
	}
	
	void setVariable()
	{
		m_bVariableLength = true;
	}
		
	void setFixed()
	{
		m_bVariableLength = false;
	}

	public boolean isVariable()
	{
		return m_bVariableLength;
	}
	
	void setRecordLengthHeader()
	{
		m_bUsesRecordLengthHeader = true;
	}
	
	void setNoRecordLengthHeader()
	{
		m_bUsesRecordLengthHeader = false;
	}
	
	public boolean isUsingRecordLengthHeader()
	{
		return m_bUsesRecordLengthHeader;
	}
	
	public boolean isEndingCRLF()
	{
		return m_bEndsCRLF;
	}

	public boolean isEndingLF()
	{
		return m_bEndsLF;
	}

	void setCRLF()
	{
		m_bEndsCRLF = true;
		m_bEndsLF = false;
	}
	
	void setLF()
	{
		m_bEndsCRLF = false;
		m_bEndsLF = true;
	}
	
	void setNoRecordEnd()
	{
		m_bEndsCRLF = false;
		m_bEndsLF = false;
	}
	
//	void setTextMode()
//	{
//		m_bTextMode = true;
//	}
//	
//	public boolean getTextMode()
//	{
//		return m_bTextMode;
//	}
	
	void setMFCobolLineSequential()
	{
		m_bMFCobolLineSequential = true;
	}
	
	public boolean getMFCobolLineSequential()
	{
		return m_bMFCobolLineSequential;
	}

	
//	void setFromMFCobol()
//	{
//		m_bFromMFCobol = true;
//	}
//	
//	void setToMFCobol()
//	{
//		m_bToMFCobol = true;
//	}
//	
//	public boolean isUsingFromMFCobolFormat()
//	{
//		return m_bFromMFCobol;
//	}
//	
//	public boolean isUsingToMFCobolFormat()
//	{
//		return m_bToMFCobol;
//	}
}
