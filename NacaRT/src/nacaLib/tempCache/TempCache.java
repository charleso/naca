/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.tempCache;

import java.util.Stack;


import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.bdb.BtreeKeyDescription;
import nacaLib.programPool.SharedProgramInstanceData;
import nacaLib.sqlSupport.CSQLStatus;
import nacaLib.varEx.*;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: TempCache.java,v 1.16 2007/03/12 16:55:57 u930di Exp $
 */
public class TempCache
{
	private TempVarManager m_tempVarManager = null;
	private CStrManager m_CStrManager = null;
	private VarLevel m_varLevel = null;
	private DeclareTypeX m_declareTypeX = null;
	private DeclareType9 m_declareType9 = null;
	private DeclareTypeEditInMap m_declareTypeEditInMap = null;
	private DeclareTypeEditInMapRedefine m_declareTypeEditInMapRedefine = null;
	private DeclareTypeEditInMapRedefineNum m_declareTypeEditInMapRedefineNum = null;
	private DeclareTypeEditInMapRedefineNumEdited m_declareTypeEditInMapRedefineNumEdited = null;
	private DeclareTypeForm m_declareTypeForm = null;
	private DeclareTypeFPacSignComp4 m_declareTypeFPacSignComp4 = null;
	private DeclareTypeFPacSignIntComp3 m_declareTypeFPacSignIntComp3 = null;
	private DeclareTypeG m_declareTypeG = null;
	private DeclareTypeMapRedefine m_declareTypeMapRedefine = null;
	private DeclareTypeNumEdited m_declareTypeNumEdited = null;
	private DeclareTypeCond m_declareTypeCond = null;
	private InitializeManagerNone m_initializeManagerNone = null;
	private InitializeManagerInt m_initializeManagerInt = null;
	private InitializeManagerDouble m_initializeManagerDouble = null;
	private InitializeManagerString m_initializeManagerString = null;
	private InitializeManagerIntEdited m_initializeManagerIntEdited = null;
	private InitializeManagerDoubleEdited m_initializeManagerDoubleEdited = null;
	private InitializeManagerStringEdited m_initializeManagerStringEdited = null;
	private InitializeManagerLowValue m_initializeManagerLowValue = null;
	//private BaseProgramManager m_programManager = null; 
	private Stack<BaseProgram> m_stackPrograms = new Stack<BaseProgram>(); 
	private BtreeKeyDescription m_btreeKeyDescription = null;
	private BaseEnvironment m_env = null;	
	private String m_csLastSQLCodeErrorText = null;
	private BaseProgramManager m_CurrentBaseProgramManager = null;
	private BaseProgram m_CurrentBaseProgram = null;

	
	private static final int INC_ALMOST_CURRENT_TIME_PERIOD = 10000;	// increment current only every 1000 getAlomostCurrentTime() request
	private int m_nCurrentTimeTryCounter = INC_ALMOST_CURRENT_TIME_PERIOD;
	
	TempCache()
	{
		m_tempVarManager = new TempVarManager(VarTypeId.NbTotalVarEditTypes);
		m_CStrManager = new CStrManager();
		m_varLevel = new VarLevel();
		m_declareTypeX = new DeclareTypeX();
	}
	
	public CoupleVar getTempVar(int nVarDefTypeId)
	{
		return m_tempVarManager.getTempCouple(nVarDefTypeId);
	}
	
	public CoupleVar addTempVar(int nVarDefTypeId, VarDefBuffer varDefItem, VarBase var)
	{
		setUseTempVar();
		return m_tempVarManager.addTemp(nVarDefTypeId, varDefItem, var);
	}
	
	public void resetCStr()
	{
		m_CStrManager.reset();
	}

	public void rewindCStrMapped(int n)
	{
		m_CStrManager.rewindCStrMapped(n);
	}

	public void resetTempVarIndex(int nVarTypeId)
	{
		if(getAndResetUseTempVar())
			m_tempVarManager.resetTempIndex(nVarTypeId);
		if(getAndResetUseCStr())
			m_CStrManager.reset();
		if(m_nCurrentTimeTryCounter-- <= 0)
			breakCurrentSessionIfTimeout();
	}

	public void resetTempVarIndexAndForbidReuse(VarBase varA)
	{
		if(getAndResetUseTempVar())
			m_tempVarManager.resetTempIndexAndForbidReuse(varA.m_varTypeId);
		if(getAndResetUseCStr())
			m_CStrManager.reset();
		if(m_nCurrentTimeTryCounter-- <= 0)
			breakCurrentSessionIfTimeout();
	}

	public void resetTempIndex(VarBase... vars)
	{
		if(getAndResetUseTempVar())
		{
			for(VarBase var : vars)
			{
				m_tempVarManager.resetTempIndex(var.m_varTypeId);
			}
		}
		if(getAndResetUseCStr())
			m_CStrManager.reset();
		if(m_nCurrentTimeTryCounter-- <= 0)
			breakCurrentSessionIfTimeout();
	}
	
	private void breakCurrentSessionIfTimeout()
	{
		m_nCurrentTimeTryCounter = INC_ALMOST_CURRENT_TIME_PERIOD;
		if(m_CurrentBaseProgramManager != null)
		{
			BaseEnvironment env = m_CurrentBaseProgramManager.getEnv();				
			if(env != null)
				env.breakCurrentSessionIfTimeout();
		}
	}
	
	public VarLevel getVarLevel()
	{
		return m_varLevel;
	}
	
	public DeclareTypeX getDeclareTypeX()
	{
		return m_declareTypeX;
	}
	
	public DeclareType9 getDeclareType9()
	{
		if(m_declareType9 == null)
			m_declareType9 = new DeclareType9();
		return m_declareType9;
	}
	
	public DeclareTypeEditInMap getDeclareTypeEditInMap()
	{
		if(m_declareTypeEditInMap == null)
			m_declareTypeEditInMap = new DeclareTypeEditInMap();
		return m_declareTypeEditInMap;
	}
	
	public DeclareTypeEditInMapRedefine getDeclareTypeEditInMapRedefine()
	{
		if(m_declareTypeEditInMapRedefine == null)
			m_declareTypeEditInMapRedefine = new DeclareTypeEditInMapRedefine();
		return m_declareTypeEditInMapRedefine;
	}
	
	public DeclareTypeEditInMapRedefineNum getDeclareTypeEditInMapRedefineNum()
	{
		if(m_declareTypeEditInMapRedefineNum == null)
			m_declareTypeEditInMapRedefineNum = new DeclareTypeEditInMapRedefineNum();
		return m_declareTypeEditInMapRedefineNum;
	}
	
	public DeclareTypeEditInMapRedefineNumEdited getDeclareTypeEditInMapRedefineNumEdited()
	{
		if(m_declareTypeEditInMapRedefineNumEdited == null)
			m_declareTypeEditInMapRedefineNumEdited = new DeclareTypeEditInMapRedefineNumEdited();
		return m_declareTypeEditInMapRedefineNumEdited;
	}
	
	public DeclareTypeForm getDeclareTypeForm()
	{
		if(m_declareTypeForm == null)
			m_declareTypeForm = new DeclareTypeForm();
		return m_declareTypeForm; 
	}
	
	public DeclareTypeFPacSignComp4 getDeclareTypeFPacSignComp4()
	{
		if(m_declareTypeFPacSignComp4 == null)
			m_declareTypeFPacSignComp4 = new DeclareTypeFPacSignComp4();
		return m_declareTypeFPacSignComp4;
	}

	public DeclareTypeFPacSignIntComp3 getDeclareTypeFPacSignIntComp3()
	{
		if(m_declareTypeFPacSignIntComp3 == null)
			m_declareTypeFPacSignIntComp3 = new DeclareTypeFPacSignIntComp3();
		return m_declareTypeFPacSignIntComp3;
	}
	
	public DeclareTypeG getDeclareTypeG()
	{
		if(m_declareTypeG == null)
			m_declareTypeG = new DeclareTypeG();
		return m_declareTypeG;
	}
	
	public DeclareTypeMapRedefine getDeclareTypeMapRedefine()
	{
		if(m_declareTypeMapRedefine == null)
			m_declareTypeMapRedefine = new DeclareTypeMapRedefine();
		return m_declareTypeMapRedefine;
	}
	
	public DeclareTypeNumEdited getDeclareTypeNumEdited()
	{
		if(m_declareTypeNumEdited == null)
			m_declareTypeNumEdited = new DeclareTypeNumEdited();
		return m_declareTypeNumEdited;
	}
	
	public DeclareTypeCond getDeclareTypeCond()
	{
		if(m_declareTypeCond == null)
			m_declareTypeCond = new DeclareTypeCond();
		return m_declareTypeCond;
	}
	
	public InitializeManagerNone getInitializeManagerNone()
	{
		if(m_initializeManagerNone == null)
			m_initializeManagerNone = new InitializeManagerNone();
		return m_initializeManagerNone;
	}
	
	public InitializeManagerInt getInitializeManagerInt(int n)
	{
		if(m_initializeManagerInt == null)
			m_initializeManagerInt = new InitializeManagerInt(n);
		else
			m_initializeManagerInt.set(n);
		return m_initializeManagerInt;
	}
	
	public InitializeManagerDouble getInitializeManagerDouble(String cs)
	{
		if(m_initializeManagerDouble == null)
			m_initializeManagerDouble = new InitializeManagerDouble(cs);
		else
			m_initializeManagerDouble.set(cs);
		return m_initializeManagerDouble;
	}
	
	public InitializeManagerString getInitializeManagerString(String cs)
	{
		if(m_initializeManagerString == null)
			m_initializeManagerString = new InitializeManagerString(cs);
		else
			m_initializeManagerString.set(cs);
		return m_initializeManagerString;
	}
	
	public InitializeManagerIntEdited getInitializeManagerIntEdited(int n)
	{
		if(m_initializeManagerIntEdited == null)
			m_initializeManagerIntEdited = new InitializeManagerIntEdited(n);
		else
			m_initializeManagerIntEdited.set(n);
		return m_initializeManagerIntEdited;
	}
	
	public InitializeManagerDoubleEdited getInitializeManagerDoubleEdited(double d)
	{
		if(m_initializeManagerDoubleEdited == null)
			m_initializeManagerDoubleEdited = new InitializeManagerDoubleEdited(d);
		else
			m_initializeManagerDoubleEdited.set(d);		
		return m_initializeManagerDoubleEdited;
	}
	
	public InitializeManagerStringEdited getInitializeManagerStringEdited()
	{
		if(m_initializeManagerStringEdited == null)
			m_initializeManagerStringEdited = new InitializeManagerStringEdited();
		return m_initializeManagerStringEdited;
	}
	
	public InitializeManagerLowValue getInitializeManagerLowValue()
	{
		if(m_initializeManagerLowValue == null)
			m_initializeManagerLowValue = new InitializeManagerLowValue();
		return m_initializeManagerLowValue;
	}
	
	public SharedProgramInstanceData getSharedProgramInstanceData()
	{
		BaseProgramManager pm = getProgramManager();
		if(pm != null)
			return pm.getSharedProgramInstanceData();
		return null;
	}
	
	public BaseProgramManager getProgramManager()
	{
		//BaseProgram prg = m_stackPrograms.peek();
		//return prg.getProgramManager();
		return m_CurrentBaseProgramManager;
	}
	
	public BaseProgram popCurrentProgram()
	{
		BaseProgram prg = null;
		m_CurrentBaseProgramManager = null;
		if (!m_stackPrograms.empty())
		{
			prg = m_stackPrograms.pop();
			if (!m_stackPrograms.empty())
			{
				m_CurrentBaseProgramManager = m_stackPrograms.peek().getProgramManager();
				if(m_CurrentBaseProgramManager != null)
					m_CurrentBaseProgram = m_CurrentBaseProgramManager.getProgram();
				else
					m_CurrentBaseProgram = null;
				
			}
		}	
		return prg;
	}

	public void pushCurrentProgram(BaseProgram prg)
	{
		if(prg != null)
		{
			m_stackPrograms.push(prg);
			m_CurrentBaseProgramManager = prg.getProgramManager();
			m_CurrentBaseProgram = prg;
		}
		else
		{
			m_CurrentBaseProgramManager = null;
			m_CurrentBaseProgram = null;
		}
	}
	
//	public void setCurrentBaseProgramManagerForPreloadOnly(BaseProgramManager baseProgramManager)
//	{
//		m_CurrentBaseProgramManager = baseProgramManager;
//		if(m_CurrentBaseProgramManager != null)
//			m_CurrentBaseProgram = m_CurrentBaseProgramManager.getProgram();
//		else
//			m_CurrentBaseProgram = null;
//	}
	
	public void resetStackProgram()
	{	 
		while(!m_stackPrograms.empty())
		{
			m_stackPrograms.pop();
		}
		m_CurrentBaseProgramManager = null;
		m_CurrentBaseProgram = null;
		m_csLastSQLCodeErrorText = "";
	}

	public CStr getReusableCStr()
	{
		setUseCStr();
		return m_CStrManager.getReusable();
	}

	public CStr getMappedCStr()
	{
		setUseCStr();
		return m_CStrManager.getMapped();
	}

	public CStrNumber getCStrNumber()
	{
		setUseCStr();
		return m_CStrManager.getNumber();
	}

	public CStrString getCStrString()
	{
		setUseCStr();
		return m_CStrManager.getString();
	}
	
	public BtreeKeyDescription getBtreeKeyDescription()
	{
		return m_btreeKeyDescription;
	}
	
	public void setBtreeKeyDescription(BtreeKeyDescription btreeKeyDescription)
	{
		m_btreeKeyDescription = btreeKeyDescription;
	}
	
	public void setCurrentEnv(BaseEnvironment env)
	{
		m_env = env;
	}
	
	public BaseEnvironment getCurrentEnv()
	{
		return m_env;
	}
	
	public void setUseTempVar()
	{
		if(!m_bUsedTempVar)
		{
			m_bUsedTempVar = true;
			if(m_CurrentBaseProgram != null)
				m_CurrentBaseProgram.setUseTempVar();
		}
	}
	
	public void setUseCStr()
	{
		if(!m_bUsedCStr)
		{
			m_bUsedCStr = true;
			if(m_CurrentBaseProgram != null)
				m_CurrentBaseProgram.setUseCStr();
		}
	}
	
	public boolean getAndResetUseTempVar()
	{
		if(m_bUsedTempVar)
		{
			m_bUsedTempVar = false;
			if(m_CurrentBaseProgram != null)
				m_CurrentBaseProgram.resetUseTempVar();
			return true;
		}
		return false;
	}
	
	public boolean getAndResetUseCStr()
	{
		if(m_bUsedCStr)
		{
			m_bUsedCStr = false;
			if(m_CurrentBaseProgram != null)
				m_CurrentBaseProgram.resetUseCStr();
			return true;
		}
		return false;
	}
	
	public String getLastSQLCodeErrorText()
	{	
		return m_csLastSQLCodeErrorText;
	}
	
	public void fillLastSQLCodeErrorText(CSQLStatus sqlStatus)
	{
		StringBuffer sb = sqlStatus.getAsStringBuffer();
		sb.append("  | From program=");
		sb.append(m_CurrentBaseProgramManager.getProgramName());
		m_csLastSQLCodeErrorText = sb.toString();
	}
	
	private boolean m_bUsedTempVar = false;
	private boolean m_bUsedCStr = false;
}
