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
/**
 * 
 */
package nacaLib.varEx;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CoupleVar
{
	public CoupleVar(VarDefBuffer varDefBuffer, VarBase var_edit)
	{
		m_varDefBuffer = varDefBuffer;
		m_variable = var_edit;
	}
	
	public VarDefBuffer getVarDefItem()
	{
		return m_varDefBuffer;
	}
	
	VarDefBuffer m_varDefBuffer = null;
	VarBase m_variable = null;
}

//public class CoupleVar<T>
//{
//	public CoupleVar(VarDefBuffer varDefBuffer, T var_edit)
//	{
//		m_varDefBuffer = varDefBuffer;
//		m_variable = var_edit;
//	}
//	
//	public VarDefBuffer getVarDefItem()
//	{
//		return m_varDefBuffer;
//	}
//	
//	VarDefBuffer m_varDefBuffer = null;
//	T m_variable = null;
//}
