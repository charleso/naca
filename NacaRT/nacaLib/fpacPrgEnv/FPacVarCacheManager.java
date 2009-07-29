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
package nacaLib.fpacPrgEnv;

import java.util.Hashtable;

import nacaLib.varEx.Var;
import nacaLib.varEx.VarType;

public class FPacVarCacheManager
{
	private Hashtable<Long, Var> m_hashVar = new Hashtable<Long, Var>();
	
	public FPacVarCacheManager()
	{
	}
	
	Var get(int nBufferId, VarType varType, int nPosition1Based, int nBufferLength)
	{
		long lId = getId(nBufferId, varType, nPosition1Based, nBufferLength);
		Var v = m_hashVar.get(lId); 
		return v;
	}
	
	void set(Var v, int nBufferId, int nPosition1Based, int nBufferLength)
	{
		long lId = getId(nBufferId, v.getVarType(), nPosition1Based, nBufferLength);
		m_hashVar.put(lId, v); 
	}	
	
	Var get(int nBufferId, VarType varType, int nPosition1Based, String csMask)
	{
		long lId = getId(nBufferId, varType, nPosition1Based, csMask);
		Var v = m_hashVar.get(lId); 
		return v;
	}
	
	void set(Var v, int nBufferId, int nPosition1Based, String csMask)
	{
		long lId = getId(nBufferId, v.getVarType(), nPosition1Based, csMask);
		m_hashVar.put(lId, v); 
	}
	
		
	private long getId(int nBufferId, VarType varType, int nPosition1Based, int nBufferLength)
	{
		long l = (varType.getId() * 65536L * 65536L * nBufferId) + (nPosition1Based * 65536L) + nBufferLength;
		return l;
	}
	
	private long getId(int nBufferId, VarType varType, int nPosition1Based, String csMask)
	{
		long l = ((1000L+varType.getId()) * 65536L * 65536L * nBufferId) + (nPosition1Based * 65536L) + csMask.hashCode();
		return l;
	}
}
