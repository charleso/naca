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

import nacaLib.varEx.DeclareTypeFPacSignComp4;
import nacaLib.varEx.VarBuffer;
import nacaLib.varEx.VarBufferPos;
import nacaLib.varEx.VarNumIntSignComp4Long;

public class VarFPacNumLongSignComp4 extends VarNumIntSignComp4Long
{
	public VarFPacNumLongSignComp4(DeclareTypeFPacSignComp4 type, VarBuffer varBuffer, int nPosition)
	{
		super(type);		
		m_bufferPos = new VarBufferPos(varBuffer, nPosition);
		m_varDef.setTotalSize(m_varDef.getSingleItemRequiredStorageSize());
	}
}
