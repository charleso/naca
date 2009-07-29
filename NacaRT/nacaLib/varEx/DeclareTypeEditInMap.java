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
 * Created on 25 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import nacaLib.mapSupport.*;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DeclareTypeEditInMap extends DeclareTypeBase
{
	private Form m_curVarForm = null;
	private VarDefForm m_curDefForm = null;
	
	MapFieldAttribute m_mapFieldAttribute = null;
	int m_nSize = 0;
	String m_csName = null;
	LocalizedString m_localizedString = null;
	String m_csFormat = null;
	boolean m_bHasCursor = false;
	String m_csDevelopableMark = null;
	//String m_csSemanticContextValue = null;
	
	public DeclareTypeEditInMap()
	{
	}
	
	public void set(VarLevel varLevel, Form curVarForm, VarDefForm curDefForm, String csName, int nSize)
	{
		super.set(varLevel);
		m_curVarForm = curVarForm;
		m_curDefForm = curDefForm;
		m_nSize = nSize;
		if(m_mapFieldAttribute == null)
			m_mapFieldAttribute = new MapFieldAttribute();
		else
			m_mapFieldAttribute.resetDefaultValues();
		m_csName = csName;
		
		m_localizedString = null;
		m_csFormat = null;
		m_bHasCursor = false;
		m_csDevelopableMark = null;
		//m_csSemanticContextValue = null;
	}
	
	public VarDefBuffer createVarDef(VarDefBuffer varDefParent)
	{
		VarDefBuffer varDef = new VarDefEditInMap(varDefParent, this);
		return varDef;		
	}
	
	public CInitialValue getInitialValue()
	{
		return null;
	}
	
	public Edit edit()
	{
		EditInMap varEdit = new EditInMap(this);
		m_curDefForm.addField(varEdit.m_varDef);
		return varEdit;
	}
	
	public DeclareTypeEditInMap initialValue(LocalizedString localizedString)
	{
		m_localizedString = localizedString;
		return this;
	}	
	
	public DeclareTypeEditInMap format(String csFormat)
	{
		m_csFormat = csFormat;
		return this;
	}	
	
			
	public DeclareTypeEditInMap color(MapFieldAttrColor mapFieldAttrColor)
	{
		m_mapFieldAttribute.setColor(mapFieldAttrColor);
		return this;
	}
	
	public DeclareTypeEditInMap highLighting(MapFieldAttrHighlighting mapFieldAttrHighlighting)
	{
		m_mapFieldAttribute.setHighlighting(mapFieldAttrHighlighting);
		return this;
	}
	
	public DeclareTypeEditInMap protection(MapFieldAttrProtection mapFieldAttrProtection)
	{
		m_mapFieldAttribute.setProtection(mapFieldAttrProtection);
		return this;
	}
	
	public DeclareTypeEditInMap intensity(MapFieldAttrIntensity mapFieldAttrIntensity)
	{
		m_mapFieldAttribute.setIntensity(mapFieldAttrIntensity);
		return this;
	}
	
	public DeclareTypeEditInMap justify(MapFieldAttrJustify mapFieldAttrJustify)
	{
		m_mapFieldAttribute.setJustify(mapFieldAttrJustify);
		return this;
	}
	
	public DeclareTypeEditInMap justifyRight()
	{
		m_mapFieldAttribute.setJustify(MapFieldAttrJustify.RIGHT);
		return this;
	}
	
	public DeclareTypeEditInMap justifyFill(MapFieldAttrFill mapFieldAttrFill)
	{
		m_mapFieldAttribute.setFill(mapFieldAttrFill);
		return this;
	}
	
	public DeclareTypeEditInMap setCursor(boolean b)
	{
		m_bHasCursor = b;
		return this;
	}
	
	public DeclareTypeEditInMap setModified(MapFieldAttrModified modified)
	{
		m_mapFieldAttribute.setAttrModified(modified);
		return this;
	}
	
	public DeclareTypeEditInMap setModified()
	{
		setModified(MapFieldAttrModified.MODIFIED);
		return this ;
	}
	
	public DeclareTypeEditInMap setUnmodified()
	{
		setModified(MapFieldAttrModified.UNMODIFIED);
		return this;
	}
	
	public DeclareTypeEditInMap setDevelopableMark(String string)
	{
		m_csDevelopableMark = string ;
		return this ;
	}
	
	public DeclareTypeEditInMap semanticContext(String csSemanticContextValue)
	{
		//m_csSemanticContextValue = csSemanticContextValue;
		return this;
	}
	
	
	
	public void registerEditInForm(EditInMap edit)
	{
		m_curVarForm.addEdit(edit);
	}
}
