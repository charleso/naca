/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 12 nov. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author PJD
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.varEx;

import jlib.log.Log;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.program.Section;
import nacaLib.programStructure.DataSectionFile;
import nacaLib.programStructure.Division;
import nacaLib.sqlSupport.SQLCursor;

public class VarSectionDeclaration extends VarDeclarationInMap 
{
	public VarSectionDeclaration(BaseProgram program)
	{
		super(program, null);
		if(program != null)
			m_ProgramManager = program.getProgramManager();
	}	
	
	private BaseProgramManager m_ProgramManager = null;
	
	public Division dataDivision()
	{
		Division d = m_ProgramManager.dataDivision();
		return d;
	}
	
	public DataSection workingStorageSection()
	{
		return m_ProgramManager.workingStorageSection();
	}
	
	public DataSection localStorageSection()
	{
		return m_ProgramManager.workingStorageSection();
	}
	
	public DataSection linkageSection()
	{
		return m_ProgramManager.linkageSection();
	}
	
	public DataSectionFile fileSection()
	{
		return m_ProgramManager.fileSection();
	}
	
	public DataSection cursorSection()	// can be omitted
	{
		return null;
	}
	
	public SQLCursor cursor()
	{
		if(IsSTCheck)
			Log.logFineDebug("cursor");

		SQLCursor sqlCursor = new SQLCursor(m_ProgramManager);
		return sqlCursor; 
	}
	
	public FileDescriptor file(String csName)
	{
		DataSectionFile fileSection = fileSection();
		FileDescriptor fileDef = new FileDescriptor(m_ProgramManager.getEnv(), csName);
		fileSection.setCurrentFileDef(fileDef);
		return fileDef;
	}
	
	public FileDescriptor filePath(String csPhysicalName)
	{
		DataSectionFile fileSection = fileSection();
		FileDescriptor fileDef = new FileDescriptor(m_ProgramManager.getEnv(), csPhysicalName);
		fileSection.setCurrentFileDef(fileDef);
		return fileDef;
	}
	
	public SortDescriptor sort()
	{
		DataSectionFile fileSection = fileSection();
		SortDescriptor sortDef = new SortDescriptor();
		fileSection.setCurrentSortDef(sortDef);
		return sortDef;
	}

	public DataSection variableSection()
	{
		// TODO fake method
		return null;
	}

//	public FileDescriptorDepending fileDescriptorDepending(FileDescriptor rs7brstd, Var implong)
//	{
//		// TODO fake methode
//		return null;
//	}
	
	public FileDescriptorDepending fileDescriptorDepending(FileDescriptor fileDesc, Var varLength)
	{
		fileDesc.lengthDependingOn(varLength);
		return null;
	}	
	
	public FileDescriptorDepending fileDescriptorDepending(SortDescriptor fileDesc, Var varLength)
	{
		fileDesc.lengthDependingOn(varLength);
		return null;
	}
	
}
