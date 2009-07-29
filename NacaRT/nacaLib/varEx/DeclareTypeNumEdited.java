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
 * Created on 1 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DeclareTypeNumEdited extends DeclareTypeBase
{
	public DeclareTypeNumEdited()
	{
	}
	
	public void set(VarLevel varLevel, String csFormat)
	{
		super.set(varLevel);
		m_csFormat = csFormat;	
		m_bBlankWhenZero = false;
	}
	
	public VarDefBuffer createVarDef(VarDefBuffer varDefParent)
	{
		VarDefBuffer varDef = new VarDefNumEdited(varDefParent, this);
		return varDef;		
	}
	
	public VarNumEdited var()
	{
		VarNumEdited var = new VarNumEdited(this);
		return var;
	}

	public Edit edit()	// Edit in a map redefine
	{		
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		DeclareTypeEditInMapRedefineNumEdited declareTypeEditInMapRedefineNumEdited = tempCache.getDeclareTypeEditInMapRedefineNumEdited();
		declareTypeEditInMapRedefineNumEdited.set(getLevel(), m_csFormat, m_bBlankWhenZero);

		EditInMapRedefineNumEdited var2Edit = new EditInMapRedefineNumEdited(declareTypeEditInMapRedefineNumEdited);
		return var2Edit;
	}

	public VarNumEdited filler()
	{
		VarNumEdited var = new VarNumEdited(this);
		var.declareAsFiller();
		return null;
	}
	
	public DeclareTypeNumEdited value(double d)
	{
		m_InitialValue = new CInitialValue(d, false);
		return this;
	}
		
	public DeclareTypeNumEdited value(int n)
	{
		m_InitialValue = new CInitialValue(n, false);
		return this;
	}
	
	public CInitialValue getInitialValue()
	{
		return m_InitialValue;
	}
 
	private CInitialValue m_InitialValue = null;
	String m_csFormat = null;
	boolean m_bBlankWhenZero = false;

	public DeclareTypeNumEdited valueZero()
	{
		m_InitialValue = new CInitialValue(0, true);
		return this;
	}
	
	public DeclareTypeNumEdited blankWhenZero()
	{
		m_bBlankWhenZero = true;
		return this;
	}

	/**
	 * @return
	 */
	public DeclareTypeNumEdited valueSpaces()
	{
		m_InitialValue = new CInitialValue(' ', true);
		return this;
	}
}
