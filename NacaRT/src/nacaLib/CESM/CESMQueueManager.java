/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.CESM;

import java.util.Hashtable;

import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.varEx.InternalCharBuffer;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarBase;


/*
 * Created on Oct 19, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CESMQueueManager extends CJMapObject
{
	public CESMQueueManager(BaseEnvironment env)
	{
		m_CESMEnv = env;
	}
	
	private BaseEnvironment m_CESMEnv = null;
	protected Hashtable<String, CESMTempStorageColl> m_tabTempQueues = new Hashtable<String, CESMTempStorageColl>() ;
	protected Hashtable m_tabTransientQueues = new Hashtable() ;
	
	public int writeTempQueue(String csQueueName, InternalCharBuffer Data)
	{
		CESMTempStorageColl tempStorageColl = getOrCreateTempStorageColl(csQueueName);
		return tempStorageColl.add(Data) ;
	}
	
	public void writeTempQueue(String csQueueName, InternalCharBuffer varData, int nRewriteItem)
	{
		CESMTempStorageColl tempStorageColl = getOrCreateTempStorageColl(csQueueName);
		if (!tempStorageColl.set(nRewriteItem, varData)) {
			m_CESMEnv.setCommandReturnCode(CESMReturnCode.ITEMERR) ;
		}
	}

	public void readNextTempQueue(String csQueueName, VarBase varDest)
	{
		CESMTempStorageColl tempStorageColl = getExistingTempStorageColl(csQueueName);
		if(tempStorageColl == null)
		{
			m_CESMEnv.setCommandReturnCode(CESMReturnCode.QIDERR) ;
			return ;
		}
		InternalCharBuffer item = tempStorageColl.getNextItem();
		if(item == null)
		{
			m_CESMEnv.setCommandReturnCode(CESMReturnCode.ITEMERR) ;
			return;
		}	
		if (item.getBufferSize() > varDest.getTotalSize())
		{
			m_CESMEnv.setCommandReturnCode(CESMReturnCode.LENGERR) ;
			return;
		}
		varDest.copyBytesFromSourceIntoBody(item);
	}
	
	public void readIndexedTempQueue(String csQueueName, int nIndex, Var varDest, Var varLength)
	{
		CESMTempStorageColl tempStorageColl = getExistingTempStorageColl(csQueueName);
		if(tempStorageColl == null)
		{
			m_CESMEnv.setCommandReturnCode(CESMReturnCode.QIDERR) ;
			return ;
		}
		InternalCharBuffer item = tempStorageColl.getIndexedTempQueue(nIndex);
		if(item == null)
		{
			m_CESMEnv.setCommandReturnCode(CESMReturnCode.ITEMERR) ;
			return;
		}	
		if (item.getBufferSize() > varDest.getTotalSize())
		{
			m_CESMEnv.setCommandReturnCode(CESMReturnCode.LENGERR) ;
			return;
		}
		varDest.copyBytesFromSourceIntoBody(item);
		if (varLength != null)
		{
			varLength.set(item.getBufferSize());
		}
	}
	
	
	public void getNbItems(String csQueueName, Var varDest)
	{
		CESMTempStorageColl tempStorageColl = getExistingTempStorageColl(csQueueName);
		if(tempStorageColl == null)
		{
			m_CESMEnv.setCommandReturnCode(CESMReturnCode.QIDERR) ;
			return ;
		}
		int n = tempStorageColl.getNbItems();
		varDest.set(n);
	}
	

	public void deleteTempQueue(String csQueueName)
	{
		CESMTempStorageColl tempStorageColl = getExistingTempStorageColl(csQueueName);
		if(tempStorageColl == null)
		{
			m_CESMEnv.setCommandReturnCode(CESMReturnCode.QIDERR) ;
			return ;
		}
		m_tabTempQueues.remove(csQueueName);
	}
	
	private CESMTempStorageColl getOrCreateTempStorageColl(String csQueueName)
	{
		CESMTempStorageColl tempStorageColl = m_tabTempQueues.get(csQueueName);
		if (tempStorageColl == null)
		{
			tempStorageColl = new CESMTempStorageColl() ;
			m_tabTempQueues.put(csQueueName, tempStorageColl);
		}
		return tempStorageColl;
	}
	
	private CESMTempStorageColl getExistingTempStorageColl(String csQueueName)
	{
		CESMTempStorageColl tempStorageColl = m_tabTempQueues.get(csQueueName);
		return tempStorageColl;
	}	
}
