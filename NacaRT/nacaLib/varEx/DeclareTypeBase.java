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
/*
 * Created on 17 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.programPool.SharedProgramInstanceData;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class DeclareTypeBase extends CJMapObject
{
	protected VarLevel m_varLevel = null;
	private boolean m_bVariableLengthDeclaration = false;
	
	public DeclareTypeBase()
	{
	}
	
	// to be removed
	public DeclareTypeBase(VarLevel varLevel)
	{
		set(varLevel);
	}
	
	void set(VarLevel varLevel)
	{
		m_varLevel = varLevel;
		m_bVariableLengthDeclaration = false;
	}
	
	public VarLevel getLevel()
	{
		return m_varLevel;
	}
	
	int getLevelValue()
	{
		return m_varLevel.getLevel();
	}
	
	public VarDefBuffer getOrCreateVarDef(SharedProgramInstanceData sharedProgramInstanceData /*VarInstancesHolder varInstancesHolder*/)
	{
		if(sharedProgramInstanceData != null)
		{
			VarLevel varLevel = getLevel();
			BaseProgramManager p = varLevel.getProgramManager();  
			int nId = p.getAndIncLastVarId();

			VarDefBuffer varDef = sharedProgramInstanceData.getVarDef(nId);
			if(varDef == null)	// No Cached VarDef
			{
				VarDefBuffer varDefParent = p.popLevel(varLevel.getLevel());
				varDef = createVarDef(varDefParent);
				varDef.setId(nId);				
				
				sharedProgramInstanceData.addVarDef(varDef);
								
				CInitialValue initialValue = getInitialValue();
				sharedProgramInstanceData.setInitialValue(nId, initialValue);

				if(varDef != null)
					p.pushLevel(varDef);
			}
			return varDef;
		}
		return null;
	}
	
	VarBase getRoot()
	{
		VarLevel varLevel = getLevel();
		if(varLevel != null)
		{
			BaseProgramManager p = varLevel.getProgramManager();
			if(p != null)
				return p.getRoot();
		}
		return null;
	}
	
	BaseProgramManager getProgramManager()
	{
		return m_varLevel.getProgramManager();
	}
	
	BaseProgram getProgram()
	{
		return m_varLevel.getProgram();
	}
	
	void setVariableLengthDeclaration()
	{
		m_bVariableLengthDeclaration = true;
	}
	
	boolean isVariableLengthDeclaration()
	{
		return m_bVariableLengthDeclaration;
	}
	
	public abstract VarDefBuffer createVarDef(VarDefBuffer varDefParent);
	public abstract CInitialValue getInitialValue();
}
