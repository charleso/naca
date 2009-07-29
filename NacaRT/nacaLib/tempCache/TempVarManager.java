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
package nacaLib.tempCache;

import nacaLib.varEx.*;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
//public class TempVarManager<T>
//{
//	private TempVarTypeManager<T> m_tarrTemp[] = null;
//
//	public TempVarManager(int nNbTypes)
//	{
//		m_tarrTemp = new TempVarTypeManager[nNbTypes];
//		for(int n=0; n<nNbTypes; n++)
//		{
//			m_tarrTemp[n] = new TempVarTypeManager();
//		}
//	}
//	
//	public CoupleVar<T> getTempCouple(int nVarDefTypeId)
//	{
//		return m_tarrTemp[nVarDefTypeId].getTempCoupleVar();
//	}
//	
//	public CoupleVar addTemp(int nVarDefTypeId, VarDefBuffer varDefItem, T var)
//	{
//		return m_tarrTemp[nVarDefTypeId].addTempVar(varDefItem, var);
//	}
//	
//	public void resetTempIndex(int nVarDefTypeId)
//	{
//		m_tarrTemp[nVarDefTypeId].reset();
//	}
//}

public class TempVarManager
{
	private TempVarTypeManager m_tarrTemp[] = null;

	public TempVarManager(int nNbTypes)
	{
		m_tarrTemp = new TempVarTypeManager[nNbTypes];
		for(int n=0; n<nNbTypes; n++)
		{
			m_tarrTemp[n] = new TempVarTypeManager();
		}
	}
	
	public CoupleVar getTempCouple(int nVarDefTypeId)
	{
		return m_tarrTemp[nVarDefTypeId].getTempCoupleVar();
	}
	
	public CoupleVar addTemp(int nVarDefTypeId, VarDefBuffer varDefItem, VarBase var)
	{
		return m_tarrTemp[nVarDefTypeId].addTempVar(varDefItem, var);
	}
	
	public void resetTempIndex(int nVarDefTypeId)
	{
		m_tarrTemp[nVarDefTypeId].reset();
	}
	
	public void resetTempIndexAndForbidReuse(int nVarDefTypeId)
	{
		m_tarrTemp[nVarDefTypeId].resetAndForbidReuse();
	}

}
