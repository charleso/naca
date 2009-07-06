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
import nacaLib.calledPrgSupport.BaseCalledPrgPublicArgPositioned;
import nacaLib.varEx.CCallParam;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarBuffer;
import nacaLib.varEx.VarDefBuffer;

public class DataDivision extends Division
{
	public DataDivision(BaseProgram prg)
	{
		super(prg);
	}
	
	public VarBuffer manageWorkingLinkageVars(BaseProgram program, boolean bFirstInstance, ArrayList<CCallParam> arrCallerCallParam, ArrayList<Var> arrDeclaredCallArg)
	{
		VarBuffer varBufferWS = computeWorkingStorageVarBuffer(program, bFirstInstance);
		if(m_FileSection != null)
		{
			VarBuffer varBufferFile = computeFileVarBuffer(program, bFirstInstance);
			program.getProgramManager().assignBufferFile(varBufferFile);
		}
		VarBuffer varBufferLS = computeLinkageVarBuffer();
				
		program.getProgramManager().assignBufferWS(varBufferWS);
		
		program.getProgramManager().assignBufferLS(varBufferLS);
				
		if(bFirstInstance)
			m_WorkingStorageSection.fillWorkingInitialValues(program.getProgramManager().getSharedProgramInstanceData());
				
		mapLinkageCallParameters(arrCallerCallParam, arrDeclaredCallArg);
		
		if(IsSTCheck)
			m_WorkingStorageSection.dumpRootVar("Working Storage");
		
		return varBufferWS;
	}

	
	private VarBuffer computeWorkingStorageVarBuffer(BaseProgram prg, boolean bFirstInstance)
	{
		if(!m_bWorkingStorageComputed)
		{
			grantWorkingStorageSection(prg);
			VarBuffer varBuffer = m_WorkingStorageSection.computeStorage(bFirstInstance);
			m_bWorkingStorageComputed = true;
			return varBuffer;
		}
		return null;
	}
	
	private VarBuffer computeFileVarBuffer(BaseProgram prg, boolean bFirstInstance)
	{
		if(!m_bFileStorageComputed)
		{
			m_bFileStorageComputed = true;
			if(m_FileSection != null)
			{
				VarBuffer varBuffer = m_FileSection.computeStorage(bFirstInstance);				
				return varBuffer;
			}
		}
		return null;
	}
	
	public VarBuffer getWorkingStorageVarBuffer()
	{
		return m_WorkingStorageSection.m_Buffer;
	}
	
	public VarBuffer computeLinkageVarBuffer()
	{
		return m_LinkageSection.computeStorage(true);	// Compute Linkage section vars that are not already set with an arg provided By Ref
	}
	
	public void registerFileVarStruct(Var var)
	{
		if(isFileSectionCurrent())
		{
			m_FileSection.assignLevel01(var);
		}
	}
	
	public void defineVarDynLengthMarker(Var var)
	{
		if(isFileSectionCurrent())
		{
			m_FileSection.defineVarDynLengthMarker(var);
		}
	}
	
	public void mapLinkageCallParameters(ArrayList arrCallerCallParam, ArrayList<Var> arrDeclaredCallArg)
	{
		m_LinkageSection.mapCallParameters(arrCallerCallParam, arrDeclaredCallArg);
		if(IsSTCheck)
			m_LinkageSection.dumpRootVar("Linkage Storage");
	}
	
	public void mapCalledPrgReturnParameters(ArrayList<BaseCalledPrgPublicArgPositioned> arrSPClientParam, ArrayList<Var> arrSPServerDeclaredCallArg)
	{	
		if(arrSPClientParam != null && arrSPServerDeclaredCallArg != null)
		{
			int nNbArg = arrSPClientParam.size();
			for(int nArg=0; nArg<nNbArg; nArg++)
			{
				BaseCalledPrgPublicArgPositioned callParamSPDest = arrSPClientParam.get(nArg);
				Var varSource = arrSPServerDeclaredCallArg.get(nArg);
				callParamSPDest.fillWithVar(varSource);
			}
		}
	}
	
	private void grantWorkingStorageSection(BaseProgram prg)
	{ 
		if(m_WorkingStorageSection == null)
			m_WorkingStorageSection = new DataSectionWorking(prg);
	}
	
	public boolean isLinkageSectionCurrent()
	{
		if(m_LinkageSection != null && m_CurrentDataSection == m_LinkageSection)
			return true;
		return false;
	}
	
	public boolean isFileSectionCurrent()
	{
		if(m_FileSection != null && m_CurrentDataSection == m_FileSection)
			return true;
		return false;
	}
	
	public void restoreFileManagerEntries(BaseEnvironment env)
	{
		if(m_FileSection != null)
		{
			m_FileSection.restoreFileManagerEntries(env);
		}
	}

	public boolean isWorkingSectionCurrent()
	{
		if(m_WorkingStorageSection != null && m_CurrentDataSection == m_WorkingStorageSection)
			return true;
		return false;
	}

	public DataSection grantAndSetCurrentWorkingStorageSection(BaseProgram prg)
	{
		grantWorkingStorageSection(prg);
		m_CurrentDataSection = m_WorkingStorageSection;
		m_WorkingStorageSection.createRootVarOfSection();
		resetCurrentFileDef();		
		return m_CurrentDataSection;
	}

	public void grantLinkageSection(BaseProgram prg)
	{ 
		if(m_LinkageSection == null)
			m_LinkageSection = new DataSectionLinkage(prg);
	}
	
	public DataSection grantAndSetCurrentLinkageSection(BaseProgram prg)
	{ 
		grantLinkageSection(prg);
		m_CurrentDataSection = m_LinkageSection;
		m_LinkageSection.createRootVarOfSection();
		resetCurrentFileDef();
		return m_CurrentDataSection;
	}
	
	public DataSectionFile grantAndSetCurrentFileSection(BaseProgram prg)
	{ 
		boolean bCreated = grantFileSection(prg);
		m_CurrentDataSection = m_FileSection;
		if(bCreated)
			m_FileSection.createRootVarOfSection();
		resetCurrentFileDef();
		return m_FileSection;
	}
	
	private boolean grantFileSection(BaseProgram prg)
	{ 
		if(m_FileSection == null)
		{
			m_FileSection = new DataSectionFile(prg);
			return true;
		}
		return false;		
	}

	
	public VarBuffer getWorkingStorageSectionVarBuffer()
	{
		if(m_WorkingStorageSection != null)
			return m_WorkingStorageSection.m_Buffer;
		return null;
	}

	public VarBuffer getLinkageSectionVarBuffer()
	{
		if(m_LinkageSection != null)
			return m_LinkageSection.m_Buffer;
		return null;
	}
	
	public VarDefBuffer getVarDefAtParentLevel(int nLevel)
	{
		if(m_CurrentDataSection != null)
			return m_CurrentDataSection.getVarDefAtParentLevel(nLevel);
		return null;
	}
	
	public void pushLevel(VarDefBuffer varDef)
	{
		if(m_CurrentDataSection != null)
			m_CurrentDataSection.pushLevel(varDef);
	}
	
	private void resetCurrentFileDef()
	{
		if(m_FileSection != null)
			m_FileSection.setCurrentFileDef(null);
	}
	
	private DataSectionLinkage m_LinkageSection = null;		// Allocated LinkageSection
	private DataSectionWorking m_WorkingStorageSection = null; // Allocated WorkingStorageSection
	private DataSectionFile m_FileSection = null;
	private DataSection m_CurrentDataSection = null;	// Current data section (either workingStorage or Linkage)
	private boolean m_bWorkingStorageComputed = false;	// true when the WS has been computed once
	private boolean m_bFileStorageComputed = false;	// true when the File storage has been computed once
}
