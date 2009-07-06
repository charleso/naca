/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.programStructure;

import java.util.ArrayList;

import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.basePrgEnv.FileManagerEntry;
import nacaLib.varEx.*;


public class DataSectionFile extends DataSection
{
	public DataSectionFile(BaseProgram prg)
	{
		super(prg, DataSectionType.File);
		m_bRecordDefSet = false;
	}
	
	public void setCurrentFileDef(BaseFileDescriptor fileDescriptor)
	{
		m_recordDef = fileDescriptor;
		m_bRecordDefSet = false;
		if(fileDescriptor != null)
		{
			if(m_arrFileDefs == null)
				 m_arrFileDefs = new ArrayList<BaseFileDescriptor>();
			m_arrFileDefs.add(fileDescriptor);
		}
	}

	public void setCurrentSortDef(SortDescriptor sortDescriptor)
	{
		m_recordDef = sortDescriptor;
		m_bRecordDefSet = false;
		if(sortDescriptor != null)
		{
			if(m_arrFileDefs == null)
				 m_arrFileDefs = new ArrayList<BaseFileDescriptor>();
			m_arrFileDefs.add(sortDescriptor);
		}
	}
	
	public void assignLevel01(Var varLevel01)	
	{
		if(m_recordDef != null && !m_bRecordDefSet)
		{
			m_recordDef.setRecordStruct(varLevel01);
			m_bRecordDefSet = true;
		}
		else
		{
			if(!varLevel01.getVarDef().isARedefine())
			{
				assertIfFalse(false, "Assigning a var at level 01 to a file already having one record definition");
			}
		}
	}
	
	void defineVarDynLengthMarker(Var var)
	{
		if(m_recordDef != null)
			m_recordDef.setVarVariableLengthMarker(var);
	}
	
	public void setOnSession(BaseSession session)
	{
		if(m_arrFileDefs != null)
		{
			for(int n=0; n<m_arrFileDefs.size(); n++)
			{
				BaseFileDescriptor fileDescriptor = m_arrFileDefs.get(n);
				fileDescriptor.setSession(session);
			}
		}
	}
	
	public void restoreFileManagerEntries(BaseEnvironment env)
	{
		if(m_arrFileDefs != null)
		{
			for(int n=0; n<m_arrFileDefs.size(); n++)
			{
				BaseFileDescriptor fileDescriptor = m_arrFileDefs.get(n);
				String csLogicalName = fileDescriptor.getLogicalName();
				FileManagerEntry fileManagerEntry = env.getFileManagerEntry(csLogicalName);
				fileDescriptor.restoreFileManagerEntry(fileManagerEntry);
			}
		}
			
	}
	
	private BaseFileDescriptor m_recordDef = null;
	private ArrayList<BaseFileDescriptor> m_arrFileDefs = null;
	private boolean m_bRecordDefSet = false;
}
