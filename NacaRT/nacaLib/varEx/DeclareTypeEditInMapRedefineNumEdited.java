/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 7 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DeclareTypeEditInMapRedefineNumEdited extends DeclareTypeBase
{
	private String m_csNumEditedFormat = null;
	private boolean m_bBlankWhenZero = false;
	
	public DeclareTypeEditInMapRedefineNumEdited()
	{
	}
	
	public void set(VarLevel varLevel, String csFormat, boolean bBlankWhenZero)
	{
		super.set(varLevel);
		m_csNumEditedFormat = csFormat;
		m_bBlankWhenZero = bBlankWhenZero;
	}
	
	public VarDefBuffer createVarDef(VarDefBuffer varDefParent)
	{
		VarDefBuffer varDef = new VarDefEditInMapRedefineNumEdited(varDefParent, this);
		return varDef;		
	}
	
	public CInitialValue getInitialValue()
	{
		return null;
	}
	
	String getNumEditedFormat()
	{
		return m_csNumEditedFormat;
	}
	
	boolean getBlankWhenZero()
	{
		return m_bBlankWhenZero;
	}
	
}
