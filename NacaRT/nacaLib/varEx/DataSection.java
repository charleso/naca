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
package nacaLib.varEx;

import java.util.ArrayList;

import jlib.log.Log;
import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.programPool.SharedProgramInstanceData;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;

public class DataSection extends CJMapObject
{	
	private DataSectionType m_dataSectionType = null;
	
	public DataSection(BaseProgram prg, DataSectionType dataSectionType)
	{
		m_dataSectionType = dataSectionType;
		m_prg = prg;
		m_Buffer = new VarBuffer();
	}
	
	public void createRootVarOfSection()
	{
		if(m_dataSectionType == DataSectionType.Working)
			createRootVar(m_prg, "WS");
		else if(m_dataSectionType == DataSectionType.Linkage)
			createRootVar(m_prg, "LS");
		else if(m_dataSectionType == DataSectionType.File)
			createRootVar(m_prg, "File");
		pushLevel(m_RootVar.getVarDef());
	}
	
	private void createRootVar(BaseProgram prg, String csSuffix)
	{
		BaseProgramManager pm = prg.getProgramManager();
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		VarLevel varlevel = tempCache.getVarLevel();
		varlevel.set(m_prg, (short)0);
		
		DeclareTypeG declareTypeG = tempCache.getDeclareTypeG();
		declareTypeG.set(varlevel);
		
		m_RootVar = new VarGroup(declareTypeG);
		if(m_RootVar.m_varDef != null)
		{
			pm.getSharedProgramInstanceData().setVarFullName(m_RootVar.getVarDef().getId(), "%InternalRoot_" + csSuffix + "%");
		}
	}

	
	public BaseProgram getProgram()
	{
		return m_prg;
	}
	
	public void pushLevel(VarDefBuffer varDef)
	{
		CLevel level = new CLevel(varDef, varDef.getLevel());
		if(m_stackLevel == null)
			m_stackLevel = new StackLevel(); 
		m_stackLevel.push(level);
	}
	
	public VarDefBuffer getVarDefAtParentLevel(int nLevel)
	{
		VarDefBuffer varDefParent = null;
		if(nLevel == 77)	// Level 77 do not change the stack, but are parented by the root var
			varDefParent = m_RootVar.getVarDef();
		else if(m_stackLevel != null)
		{
			CLevel level = m_stackLevel.getParentLevel(nLevel);
			if(level != null)
				varDefParent = level.getVarDef();
		}
		return varDefParent;
	}

	private StackLevel m_stackLevel = null;
	
	Var getRootVar()
	{
		return m_RootVar;
	}
	
	public VarBuffer computeStorage(boolean bFirstInstance)
	{
		m_stackLevel = null;
		
		SharedProgramInstanceData sharedProgramInstanceData = m_prg.getProgramManager().getSharedProgramInstanceData();
		int nBufferSize = 0;
		if(m_RootVar != null)
		{
			if(bFirstInstance)	// The var defs of the catalog have not been already computed: 1st absolute run; the 2nd, 3rd... run of a program alreday loaded have access to the catalog of varDef: No need to recompute var def again.
			{
				VarDefBuffer varDefBuffer = m_RootVar.getVarDef();
				
				varDefBuffer.assignEditInMapRedefine();
				nBufferSize = varDefBuffer.calcSize();
				varDefBuffer.calcPositionsIntoBuffer(sharedProgramInstanceData);	// No var used in map redefines
				varDefBuffer.calcOccursOwners();
			}
			else
				nBufferSize = m_RootVar.getTotalSize();
		}
		
		m_Buffer.allocBufferStorage(nBufferSize);
		return m_Buffer;
	}
		
	public void fillWorkingInitialValues(SharedProgramInstanceData sharedProgramInstanceData)
	{			
		TempCache cache = TempCacheLocator.getTLSTempCache();
		if(cache != null && m_RootVar != null)
			m_RootVar.getVarDef().fillInitialValueAndClearUnusedMembers(cache, sharedProgramInstanceData, m_Buffer);
	}
	
	public void dumpRootVar(String csSectionName)
	{
		if(IsSTCheck)
		{
			if(m_RootVar != null)
			{
				Log.logFineDebug("dumpSTCheck:" + this.m_prg.getSimpleName() + " " + csSectionName);
				m_RootVar.getVarDef().dumpToSTCheck(m_prg.getProgramManager());
			}
		}
	}
	
	public void mapCallParameters(ArrayList<CCallParam> arrCallerCallParam, ArrayList<Var> arrDeclaredCallArg)
	{		
		if(arrDeclaredCallArg != null)
		{
			for(int n=0; n<arrDeclaredCallArg.size(); n++)
			{
				Var varLinkageSection = getDeclaredCallArgAtIndex(arrDeclaredCallArg, n);
				varLinkageSection.fill(CobolConstant.LowValue);
			}
		}
			 
		if(arrCallerCallParam != null && arrDeclaredCallArg != null)
		{
			int nNbArg = arrCallerCallParam.size();
			for(int nArg=0; nArg<nNbArg; nArg++)
			{
				CCallParam callParam = (CCallParam) arrCallerCallParam.get(nArg);
				 
				Var varLinkageSection = getDeclaredCallArgAtIndex(arrDeclaredCallArg, nArg);
				if(varLinkageSection != null)
				{
					callParam.MapOn(varLinkageSection);
				}
			}
		}
	}

	private Var getDeclaredCallArgAtIndex(ArrayList arrDeclaredCallArg, int nIndex)
	{
		if(nIndex >= 0 && nIndex < arrDeclaredCallArg.size())
			return (Var)arrDeclaredCallArg.get(nIndex);
		return null;		
	}
	
	protected BaseProgram m_prg = null;
	public Var m_RootVar = null;
	public VarBuffer m_Buffer = null;
}
