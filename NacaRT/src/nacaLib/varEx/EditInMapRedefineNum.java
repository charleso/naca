/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EditInMapRedefineNum extends EditInMapRedefine
{
	EditInMapRedefineNum(DeclareTypeEditInMapRedefineNum declareTypeEditInMapRedefine)
	{
		super(declareTypeEditInMapRedefine);
	}
			
	protected EditInMapRedefineNum()
	{
		super();
	}
	
	protected VarBase allocCopy()
	{
		EditInMapRedefine v = new EditInMapRedefine();
		return v;
	}
	
	public EditInMapRedefine allocOccursedItem(VarDefBuffer varDefItem)
	{ 
		EditInMapRedefineNum vItem = new EditInMapRedefineNum();
		vItem.m_bufferPos = m_bufferPos;
		vItem.m_varDef = varDefItem;
		
		vItem.m_attrManager = vItem.getEditAttributManager();
		//Inherit attributes
		return vItem;
	}
//	
//	public void set(String cs)
//	{
//		String csFormatted = ((VarDefEditInMapRedefineNumEdited)m_varDef).applyFormatting(cs);
//		m_Var2EditInMap.set(csFormatted);
//	}
}
