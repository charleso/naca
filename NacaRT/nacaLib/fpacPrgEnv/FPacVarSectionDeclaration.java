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

import nacaLib.varEx.VarSectionDeclaration;

public class FPacVarSectionDeclaration extends VarSectionDeclaration
{
	
	public FPacVarSectionDeclaration(FPacProgram program)
	{
		super(program);
	}
	
	public FPacFileDeclaration fpacFile(String csName)
	{
		return new FPacFileDeclaration(this, csName);
//		DataSectionFile fileSection = fileSection();
//		FPacFileDescriptor fileDef = new FPacFileDescriptor((BaseFPacProgram)m_Program, csName);
//		fileSection.setCurrentFileDef(fileDef);
//		return fileDef;
	}


}
