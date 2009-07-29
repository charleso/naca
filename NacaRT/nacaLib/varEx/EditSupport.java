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
 * Created on 3 mars 2005
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
public class EditSupport extends DeclareTypeBase
{
	public EditSupport(VarLevel varLevel)
	{
		super(varLevel);
	}
	
	public VarDefBuffer createVarDef(VarDefBuffer varDefParent)
	{
//		VarDef varDef = new VarDefG(nId, varDefParent, this);
//		return varDef;		
		return null;
	}

	public CInitialValue getInitialValue()
	{
		return null;
	}
	
//	public Edit edit()	// Edit in a map redefine
//	{
//		Edit edt = m_VarLevelManager.createEdit();
//		return edt;
//	}
//	
//	public Edit editSkip()
//	{
//		return editSkip(1);
//	}
//
//	public Edit editSkip(int nNbItemToSkip)
//	{
//		m_VarLevelManager.editSkip(nNbItemToSkip);
//		return null;
//	}
//	
//	public Edit editOccurs(int nNbOccurs, String csName)
//	{
//		Edit edt = m_VarLevelManager.editOccurs(nNbOccurs, csName);
//		return edt;
//	}
}
