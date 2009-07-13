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

import nacaLib.programStructure.DataSectionFile;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: FPacFileDeclaration.java,v 1.5 2006/07/04 10:17:32 cvsadmin Exp $
 */
public class FPacFileDeclaration
{
	FPacVarSectionDeclaration m_section = null;
	String m_csName = null;
	
	private FPacRecordFiller m_FPacRecordFillerInput = new FPacRecordFiller((byte)0xff);
	private FPacRecordFiller m_FPacRecordFillerOutput = new FPacRecordFiller((byte) ' ');
	private boolean m_bRecordLengthForced = false;
	private int m_nRecordLength = 0;	
	
	FPacFileDeclaration(FPacVarSectionDeclaration section, String csName)
	{
		m_section = section;
		m_csName = csName;		
	}
	
	public FPacFileDescriptor file()
	{
		DataSectionFile fileSection = m_section.fileSection();
		FPacFileDescriptor fpacFileDescriptor = new FPacFileDescriptor((FPacProgram)m_section.getProgram(), m_csName);
		if(m_bRecordLengthForced)
			fpacFileDescriptor.setRecordLengthForced(m_nRecordLength);
		fpacFileDescriptor.setRecordFillers(m_FPacRecordFillerInput, m_FPacRecordFillerOutput);
		fileSection.setCurrentFileDef(fpacFileDescriptor);
		return fpacFileDescriptor;
	}

	// Supersede the logical name declared record length 
	public FPacFileDeclaration forcedRecordLength(int n)
	{
		m_nRecordLength = n;
		m_bRecordLengthForced = true;
		return this;
	}
	
	public FPacFileDeclaration fillInputBuffer(byte by)
	{
		m_FPacRecordFillerInput.setFiller(by);
		return this;
	}
	
	public FPacFileDeclaration fillInputBuffer(char c)
	{
		m_FPacRecordFillerInput.setFiller(c);
		return this;
	}
	
	public FPacFileDeclaration fillOutputBuffer(byte by)
	{
		m_FPacRecordFillerOutput.setFiller(by);
		return this;	
	}
	
	public FPacFileDeclaration fillOutputBuffer(char c)
	{
		m_FPacRecordFillerOutput.setFiller(c);
		return this;
	}
	public FPacFileDeclaration fillOutputBuffer(String cs)
	{
		if (cs != null && cs.length() >= 1)
			m_FPacRecordFillerOutput.setFiller(cs.charAt(0));
		return this;
	}
}

