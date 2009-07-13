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
public class EditInMapRedefineNumEdited extends EditInMapRedefine
{
	EditInMapRedefineNumEdited(DeclareTypeEditInMapRedefineNumEdited declareTypeEditInMapRedefine)
	{
		super(declareTypeEditInMapRedefine);
	}
			
	protected EditInMapRedefineNumEdited()
	{
		super();
	}
	
	protected VarBase allocCopy()
	{
		EditInMapRedefineNumEdited v = new EditInMapRedefineNumEdited();
		return v;
	}
	
	public EditInMapRedefine allocOccursedItem(VarDefBuffer varDefItem)
	{ 
//		EditInMapRedefineNumEdited vItem = new EditInMapRedefineNumEdited();
//		vItem.m_bufferPos = m_bufferPos;
//		vItem.m_varDef = varDefItem;
//		
//		vItem.m_attrManager = vItem.getEditAttributManager(m_bufferPos.getProgramManager());
//		//Inherit attributes
//		return vItem;
		EditInMapRedefineNumEdited vItem = new EditInMapRedefineNumEdited();
		vItem.m_varDef = varDefItem;
		
		int nOffset = m_bufferPos.m_nAbsolutePosition - m_varDef.m_nDefaultAbsolutePosition;
		vItem.m_bufferPos = new VarBufferPos(m_bufferPos, varDefItem.m_nDefaultAbsolutePosition + nOffset);
		vItem.m_varTypeId = varDefItem.getTypeId();
		
		//assertIfFalse(vItem.m_bufferPos.getProgramManager() == m_bufferPos.getProgramManager());
		
		vItem.m_attrManager = vItem.getEditAttributManager();
		return vItem;
	}
	
//	
//	public void set(String cs)
//	{
//		String csFormatted = ((VarDefEditInMapRedefineNumEdited)m_varDef).applyFormatting(cs);
//		m_Var2EditInMap.set(csFormatted);
//	}
}
