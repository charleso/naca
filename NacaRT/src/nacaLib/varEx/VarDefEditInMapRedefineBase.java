/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 15 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import nacaLib.mapSupport.MapFieldAttribute;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class VarDefEditInMapRedefineBase extends VarDefEdit
{
	public VarDefEditInMapRedefineBase(VarDefBase varDefParent, VarLevel varLevel)
	{
		super(varDefParent, varLevel);
		m_bJustifyRight = varLevel.getJustifyRight();
	}

	public void mapOnOriginEdit()
	{
		VarDefMapRedefine mapRedefine = getMapRedefine();
		if(mapRedefine == null)
		{
			int n =0 ;
		}
		
		FoundFlag foundFlag = new FoundFlag();
		int n = mapRedefine.getNbEditUntil(this, foundFlag);
		m_varDefEditOrigin = mapRedefine.m_varDefFormRedefineOrigin.getChild(n);
		m_varDefRedefinOrigin = mapRedefine.m_varDefFormRedefineOrigin.getChild(n);
		if(m_varDefEditOrigin != null)
		{
			m_nTotalSize = m_varDefEditOrigin.m_nTotalSize;
		}
		else
		{
			m_nTotalSize = 0;	// Should never happen
		}
	}
	
	void assignForm(VarDefForm varDefForm)
	{
		m_varDefFormRedefineOrigin = varDefForm;
		m_varDefEditOrigin = m_varDefFormRedefineOrigin.getChildAtDefaultPosition(m_nDefaultAbsolutePosition);
	}
	
	VarDefBuffer getVarDefEditInMapOrigin()
	{
		return m_varDefEditOrigin;
	}
	
	public VarDefEditInMapRedefineBase()
	{
		super();
	}
	
	public int getBodyLength()
	{
		return m_nTotalSize - getHeaderLength();
	}
	
	protected int getHeaderLength()
	{
		if(m_OccursDef == null)
			return 7;
		// We are an edit Occurs
		return 0;			
	}
			
	protected boolean isEditInMapRedefine()
	{
		return true;
	}
	
	protected boolean isEditInMapOrigin()
	{
		return false;
	}
	
	protected boolean isVarInMapRedefine()
	{
		return false;
	}
	
	protected boolean isVarDefForm()
	{
		return false;
	}
	
	
	protected void adjustCustomProperty(VarDefBuffer varDefBufferCopySingleItem)
	{
		VarDefEditInMapRedefineBase varDefCopy = (VarDefEditInMapRedefineBase)varDefBufferCopySingleItem;
		//varDefCopy.m_mapFieldAttribute = m_mapFieldAttribute;
		//varDefCopy.m_varDefEditOrigin = m_varDefEditOrigin;
		varDefCopy.m_bJustifyRight = m_bJustifyRight;
	}

	protected MapFieldAttribute m_mapFieldAttribute = null;
	protected VarDefBuffer m_varDefEditOrigin = null;
	protected boolean m_bJustifyRight = false;
}
