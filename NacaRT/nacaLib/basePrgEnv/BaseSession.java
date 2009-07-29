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
package nacaLib.basePrgEnv;

import java.util.Hashtable;

import jlib.misc.LogicalFileDescriptor;
import jlib.sql.DbConnectionManagerBase;

import org.w3c.dom.Document;

public abstract class BaseSession
{
	protected BaseResourceManager m_baseResourceManager = null;
	private boolean m_bUseJmx = true;	
	
	public abstract void fillCurrentUserInfo(CurrentUserInfo currentUserInfo);
	public abstract Document getLastScreenXMLData();
	
	public BaseSession(BaseResourceManager baseResourceManager)
	{
		m_baseResourceManager = baseResourceManager;
		m_bUseJmx = m_baseResourceManager.getUsingJmx();
//		if(m_bUseJmx)
//			JmxGeneralStat.incNbSession();
	}
	
//	public void finalize()
//	{
//		if(m_bUseJmx)
//			JmxGeneralStat.decNbSession();
//	}

	public BaseResourceManager getBaseResourceManager() 
	{
		return m_baseResourceManager;
	}
	
	public abstract BaseEnvironment createEnvironment(DbConnectionManagerBase connectionManager);
	public abstract String getType();
	
	
	public abstract void RunProgram(BaseProgramLoader seq);
	
	public abstract void setHelpPage(Document doc);
	
	
//	public void addBatchFile(String csLogicalName, String csPath, boolean bEbcdicFile,  boolean bExt, int nLength)
//	{
//		LogicalFileDescriptor fd = new LogicalFileDescriptor(csPath, bEbcdicFile,  bExt, nLength); 
//		if(m_hashLogicalFileDescriptors == null)
//			m_hashLogicalFileDescriptors = new Hashtable<String, LogicalFileDescriptor>(); 
//		m_hashLogicalFileDescriptors.put(csLogicalName, fd);
//	}
	
	public LogicalFileDescriptor getLogicalFileDescriptor(String csLogicalName)
	{
		if(m_hashLogicalFileDescriptors != null)
			return m_hashLogicalFileDescriptors.get(csLogicalName);
		return null;
	}
	
	public void putLogicalFileDescriptor(String csLogicalName, LogicalFileDescriptor logicalFileDescriptor)
	{
		if(m_hashLogicalFileDescriptors == null)
			m_hashLogicalFileDescriptors = new Hashtable<String, LogicalFileDescriptor>(); 
		m_hashLogicalFileDescriptors.put(csLogicalName, logicalFileDescriptor);
	}

	public void removeLogicalFileDescriptor(String csLogicalName)
	{
		if(m_hashLogicalFileDescriptors != null)
			m_hashLogicalFileDescriptors.remove(csLogicalName);
	}

	private Hashtable<String, LogicalFileDescriptor> m_hashLogicalFileDescriptors = null;

	public void addBatchInfo(String csInfo, String csValue)
	{
		if(m_hashLogicalJobInfo == null)
			m_hashLogicalJobInfo = new Hashtable<String, String>(); 
		m_hashLogicalJobInfo.put(csInfo, csValue);
	}

	public String getLogicalJobInfo(String csInfo)
	{
		if(m_hashLogicalJobInfo != null)
			return m_hashLogicalJobInfo.get(csInfo);
		return "";
	}

	private Hashtable<String, String> m_hashLogicalJobInfo = null;
	
	public boolean isAsync()
	{
		return m_bAsync;
	}

	protected void setAsync(boolean b)
	{
		m_bAsync = b;
	}

	private boolean m_bAsync = false;

	public String getDynamicAllocationInfo(String csKey)
	{
		if(m_hashDynamicAllocationInfo != null)
			return m_hashDynamicAllocationInfo.get(csKey);
		return null;
	}

	public void addDynamicAllocationInfo(String csKey, String csValue)
	{
		if(m_hashDynamicAllocationInfo == null)
			m_hashDynamicAllocationInfo = new Hashtable<String, String>(); 
		m_hashDynamicAllocationInfo.put(csKey, csValue);
	}
	
	public void resetDynamicAllocationInfo()
	{
		m_hashDynamicAllocationInfo.clear();
	}

	private Hashtable<String, String> m_hashDynamicAllocationInfo = null;
	
	private static int ms_LastDynamicAllocationID = 0 ;
	public String getNextDynamicAllocationID()
	{
		int n = ms_LastDynamicAllocationID++;
		return "" + (n/100)%10 + (n/10)%10 + (n)%10 ;
	}
	
	private int m_nNetwork_ms = 0;
	public int getNetwork_ms()
	{
		return m_nNetwork_ms;
	}
	public void setNetwork_ms(int nNetwork_ms)
	{
		m_nNetwork_ms = nNetwork_ms;
	}
	
	public Object getSpecialObject(String csKey)
	{
		if(m_hashSpecialObject != null)
			return m_hashSpecialObject.get(csKey);
		return null;
	}

	public void addSpecialObject(String csKey, Object object)
	{
		if(m_hashSpecialObject == null)
			m_hashSpecialObject = new Hashtable<String, Object>(); 
		m_hashSpecialObject.put(csKey, object);
	}
	
	public void removeSpecialObject(String csKey)
	{
		if(m_hashSpecialObject != null)
			m_hashSpecialObject.remove(csKey);
	}
	
	public void resetSpecialObject()
	{
		m_hashSpecialObject.clear();
	}

	private Hashtable<String, Object> m_hashSpecialObject = null;
}