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
 * Created on 11 nov. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.varEx;

import nacaLib.base.*;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;

public class VarLevel extends CJMapObject
{
	private CInitialValue m_InitialValue = null;
	private boolean m_bJustifyRight = false;
	private VarDefBase m_VarDefRedefineOrigin = null;
	private OccursDefBase m_OccursDef = null;
	private BaseProgram m_program = null;
	private short m_sLevel = 0;
	private boolean m_bVariableLength = false;
	
	public VarLevel()
	{
	}
	
	public void set(BaseProgram program, int nLevel)
	{
		m_InitialValue = null;
		m_bJustifyRight = false;
		m_VarDefRedefineOrigin = null;
		m_OccursDef = null;
		
		m_program = program; 
		m_sLevel = (short)nLevel;
		m_bVariableLength = false;
	}
	
	public VarGroup var()	// Creates a group
	{
		DeclareTypeG declareTypeG = TempCacheLocator.getTLSTempCache().getDeclareTypeG();
		declareTypeG.set(this);
		if(m_bVariableLength)
			declareTypeG.setVariableLengthDeclaration();
		VarGroup var2G = new VarGroup(declareTypeG);
		return var2G;
	}
	
	public Var filler()
	{
		DeclareTypeG declareTypeG = TempCacheLocator.getTLSTempCache().getDeclareTypeG();
		declareTypeG.set(this);
		VarGroup var2G = new VarGroup(declareTypeG);
		var2G.declareAsFiller();
		return var2G;
	}
	
	public DeclareTypeX picX()
	{
		return picX(1);
	}

	public DeclareTypeX comp1()
	{
		// PJD to be implemented...
		return null;
	}
	
	public DeclareTypeX comp2()
	{
		// PJD to be implemented...
		return null;
	}
	
	public DeclareTypeX picX(int nLength)
	{
		DeclareTypeX declareTypeX = TempCacheLocator.getTLSTempCache().getDeclareTypeX();
		declareTypeX.set(this, nLength);
		if(m_bVariableLength)
			declareTypeX.setVariableLengthDeclaration();
		
		//DeclareTypeX varLevelX = new DeclareTypeX(this, nLength);
		return declareTypeX;
	}
	
	public DeclareTypeNumEdited pic(String csFormat)
	{
		// Should identify either pic9(csFormat) or picX(csFormat);  
		return pic9(csFormat);
	}
	
	public DeclareType9 pic9(int nNbDigitInteger)
	{
		return pic9Define(false, nNbDigitInteger, 0);
	}

	public DeclareType9 pic9(int nNbDigitInteger, int nNbDigitDecimal)
	{
		return pic9Define(false, nNbDigitInteger, nNbDigitDecimal);
	}	

	public DeclareType9 picS9(int nNbDigitInteger)
	{
		return pic9Define(true, nNbDigitInteger, 0);
	}

	public DeclareType9 picS9(int nNbDigitInteger, int nNbDigitDecimal)
	{
		return pic9Define(true, nNbDigitInteger, nNbDigitDecimal);
	}
	
	private DeclareType9 pic9Define(boolean bSigned, int nNbDigitInteger, int nNbDigitDecimal)
	{
		DeclareType9 declareType9 = TempCacheLocator.getTLSTempCache().getDeclareType9();
		declareType9.set(this, bSigned, nNbDigitInteger, nNbDigitDecimal);
		//DeclareType9 varLevel9 = new DeclareType9(this, bSigned, nNbDigitInteger, nNbDigitDecimal);
		return declareType9;
	}
	
	public VarLevel redefines(Edit varEditRedefineOrigin)
	{
		m_VarDefRedefineOrigin = varEditRedefineOrigin.getVarDef();
		return this;
	}

	public VarLevel redefines(Var VarRedefineOrigin)
	{
		m_VarDefRedefineOrigin = VarRedefineOrigin.getVarDef();
		return this;
	}

	public VarLevel occurs(int nNbOccurs)
	{
		BaseProgramManager pm = getProgramManager();
		if(pm.isFirstInstance())	// || pm.isLinkageSectionCurrent())
			m_OccursDef = new OccursDef(nNbOccurs);
		return this;
	}	
		
	public VarLevel occurs(Var varOccurs)
	{
		BaseProgramManager pm = getProgramManager();
		if(pm.isFirstInstance())	// || pm.isLinkageSectionCurrent())			
			m_OccursDef = new OccursDefVar(varOccurs);
		return this;
	}

	public VarLevel occursDepending(int nNbOccurs, Var varOccurs)
	{
		BaseProgramManager pm = getProgramManager();
		if(pm.isFirstInstance())	// || pm.isLinkageSectionCurrent())
		{
			if(varOccurs.isBufferComputed())
				occurs(varOccurs);
			else
				occurs(nNbOccurs);
		}
		m_bVariableLength = true;
		return this;
	}	

	public VarLevel occursDependingRecord(int nNbOccurs, Var varOccurs)
	{
		BaseProgramManager pm = getProgramManager();
		if(pm.isFirstInstance())
		{
			m_OccursDef = new OccursDefRecordDependingVar(nNbOccurs, varOccurs);
		}
		return this;
	}	

//	private String removeCharAtPos(String csFormat, int n)
//	{
//		int nLg = csFormat.length();
//		csFormat = csFormat.substring(0, n) + csFormat.substring(n+1, nLg-1);	// Remove the char
//		return csFormat;
//	}

	public DeclareTypeNumEdited pic9(String csFormat)
	{	
		DeclareTypeNumEdited declareTypeNumEdited = TempCacheLocator.getTLSTempCache().getDeclareTypeNumEdited();
		declareTypeNumEdited.set(this, csFormat);
		return declareTypeNumEdited;
	}

	// Screen resource management: No .var() to add
	// Map redefine management
	public MapRedefine redefinesMap(Form formRedefineOrigin)
	{
		DeclareTypeMapRedefine declareTypeMapRedefine = TempCacheLocator.getTLSTempCache().getDeclareTypeMapRedefine();
		declareTypeMapRedefine.set(this, formRedefineOrigin);

		MapRedefine var2MapRedefine = new MapRedefine(declareTypeMapRedefine);
		return var2MapRedefine;
	}
	
	public VarLevel justifyRight()	// Edit in a map redefine
	{
		m_bJustifyRight = true;
		return this;
	}
	
	boolean getJustifyRight()
	{
		return m_bJustifyRight;
	}

		
	public Edit edit()	// Edit in a map redefine
	{		
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		DeclareTypeEditInMapRedefine declareTypeEditInMapRedefine = tempCache.getDeclareTypeEditInMapRedefine();
		declareTypeEditInMapRedefine.set(this);
		EditInMapRedefine var2Edit = new EditInMapRedefine(declareTypeEditInMapRedefine);
		return var2Edit;
	}
	
	public Edit editSkip()
	{
		return editSkip(1);
	}

	public Edit editSkip(int nNbItemToSkip)
	{
		for(int n=0; n<nNbItemToSkip; n++)
		{
			edit();
		}
		return null;
	}
	
	public Edit editOccurs(int nNbOccurs, String csName)
	{
		// remonter au dernier precedent de nivwau >= niveau courant
		// si c'est un edit occurs; il faut completer son tableau d'items 
		BaseProgramManager pm = getProgramManager();
		
		if(pm.isFirstInstance())
			m_OccursDef = new OccursDef(nNbOccurs);
		
		Edit varEdit = edit();
		
		if(pm.isFirstInstance())
		{
			this.getProgramManager().getSharedProgramInstanceData().setVarFullName(varEdit.getVarDef().getId(), csName);
			//varEdit.m_varDef.setFullName(csName);
		}
		
		return varEdit;
	}

	BaseProgramManager getProgramManager()
	{
		return m_program.getProgramManager();
	}
	
	BaseProgram getProgram()
	{
		return m_program;
	}
	
	public short getLevel()
	{
		return m_sLevel;
	}
	
	public VarDefBase getVarDefRedefineOrigin()
	{
		return m_VarDefRedefineOrigin;
	}
	
	public OccursDefBase getOccursDef()
	{
		return m_OccursDef;
	}

	public VarLevelGroup value(String cs)
	{
		BaseProgramManager pm = getProgramManager();
		if(pm.isFirstInstance())
			m_InitialValue = new CInitialValue(cs, false);
		VarLevelGroup varLevelGroup = new VarLevelGroup(this);
		return varLevelGroup;
	}
	
	public VarLevelGroup valueAll(char c)
	{
		BaseProgramManager pm = getProgramManager();
		if(pm.isFirstInstance())
			m_InitialValue = new CInitialValue(c, true);
		VarLevelGroup varLevelGroup = new VarLevelGroup(this);
		return varLevelGroup;
	}

	public VarLevelGroup valueAll(String cs)
	{
		BaseProgramManager pm = getProgramManager();
		if(pm.isFirstInstance())
			m_InitialValue = new CInitialValue(cs, true);
		VarLevelGroup varLevelGroup = new VarLevelGroup(this);
		return varLevelGroup;
	}
	
	public VarLevelGroup valueSpaces()
	{
		BaseProgramManager pm = getProgramManager();
		if(pm.isFirstInstance())
			m_InitialValue = new CInitialValue(CobolConstant.Space.getValue(), true);
		VarLevelGroup varLevelGroup = new VarLevelGroup(this);
		return varLevelGroup;
	}

	public VarLevelGroup valueZero()
	{
		BaseProgramManager pm = getProgramManager();
		if(pm.isFirstInstance())
			m_InitialValue = new CInitialValue(CobolConstant.Zero.getValue(), true);
		VarLevelGroup varLevelGroup = new VarLevelGroup(this);
		return varLevelGroup;
	}

	public VarLevelGroup valueHighValue()
	{
		BaseProgramManager pm = getProgramManager();
		if(pm.isFirstInstance())
			m_InitialValue = new CInitialValue(CobolConstant.HighValue.getValue(), true);
		VarLevelGroup varLevelGroup = new VarLevelGroup(this);
		return varLevelGroup;
	}

	public VarLevelGroup valueLowValue()
	{
		BaseProgramManager pm = getProgramManager();
		if(pm.isFirstInstance())
			m_InitialValue = new CInitialValue(CobolConstant.LowValue.getValue(), true);
		VarLevelGroup varLevelGroup = new VarLevelGroup(this);
		return varLevelGroup;
	} 
	
	CInitialValue getInitialValue()
	{
		return m_InitialValue;
	}
	
	public VarLevel variableLength()
	{
		m_bVariableLength = true;
		return this;
	}
}
