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
package nacaLib.varEx;

import nacaLib.basePrgEnv.BaseProgramManager;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class MoveCorrespondingEntry
{
	MoveCorrespondingEntry(VarDefBase varDefSource, VarDefBase varDefDest)
	{
		m_varDefSource = varDefSource;
		m_varDefDest = varDefDest;
	}
	
	void doMove(BaseProgramManager programManager, int nSourceOffset, int nDestOffset)
	{
		VarBase varSource = programManager.getVarFullName(m_varDefSource);
		VarBase varDest = programManager.getVarFullName(m_varDefDest);
		varSource.m_bufferPos.m_nAbsolutePosition += nSourceOffset;
		varDest.m_bufferPos.m_nAbsolutePosition += nDestOffset;
		varDest.set(varSource);
		varSource.m_bufferPos.m_nAbsolutePosition -= nSourceOffset;
		varDest.m_bufferPos.m_nAbsolutePosition -= nDestOffset;
	}
	
	private VarDefBase m_varDefSource = null;
	private VarDefBase m_varDefDest = null;
}
