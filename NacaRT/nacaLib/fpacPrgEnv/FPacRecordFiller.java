/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.fpacPrgEnv;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: FPacRecordFiller.java,v 1.2 2006/06/08 06:22:22 cvsadmin Exp $
 */
public class FPacRecordFiller
{
	private byte m_byFiller = 0;
	
	FPacRecordFiller(byte by)
	{
		setFiller(by);
	}
	
	FPacRecordFiller(char c)
	{
		setFiller(c);
	}	
	
	void setFiller(byte by)
	{
		m_byFiller = by;
	}
	
	void setFiller(char c)
	{
		m_byFiller = (byte)c;
	}
	
	void fillBuffer(char tChars[])
	{
		for(int n=0; n<tChars.length; n++)
		{
			tChars[n] = (char)m_byFiller; 
		}
	}
}
