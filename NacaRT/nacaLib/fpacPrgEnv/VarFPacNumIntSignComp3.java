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
package nacaLib.fpacPrgEnv;

import nacaLib.varEx.DeclareTypeFPacSignIntComp3;
import nacaLib.varEx.VarBuffer;
import nacaLib.varEx.VarBufferPos;
import nacaLib.varEx.VarNumIntSignComp3;

public class VarFPacNumIntSignComp3 extends VarNumIntSignComp3 
{
	public VarFPacNumIntSignComp3(DeclareTypeFPacSignIntComp3 type, VarBuffer varBuffer, int nPosition)
	{
		super(type);		
		m_bufferPos = new VarBufferPos(varBuffer, nPosition);
		m_varDef.setTotalSize(m_varDef.getSingleItemRequiredStorageSize());
	}
}
