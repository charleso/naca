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
 * Created on 26 août 04
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
package nacaLib.programStructure;

import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.DataSectionType;

public class DataSectionLinkage extends DataSection
{
	public DataSectionLinkage(BaseProgram prg)
	{
		super(prg, DataSectionType.Linkage);
	}
	
//	public void registerVar(Var var)
//	{
//		int nLevel = var.m_VarManager.getLevel();
//		if(nLevel == 1 || nLevel == 77)
//		{
//			m_arrMappableVars.add(var);
//		} 
//	}
	
//	public void unregisterVar(Var var)
//	{
//		m_arrMappableVars.remove(var);
//	}
	
//	private ArrayList m_arrMappableVars = new ArrayList();	// Array of all var that can be mapped on call 
}
