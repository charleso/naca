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
 * Created on 2 mai 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.programPool;

import java.util.Hashtable;

import nacaLib.basePrgEnv.BaseResourceManager;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SharedProgramInstanceDataCatalog
{
	public SharedProgramInstanceDataCatalog()
	{
	}
	
	static synchronized public SharedProgramInstanceData getSharedProgramInstanceData(String csSimpleName)
	{
		SharedProgramInstanceData s = ms_hashSharedProgramInstanceData.get(csSimpleName);
		return s;
	}
	
	static synchronized public void removeSharedProgramInstanceData(String csSimpleName)
	{
		SharedProgramInstanceData s = ms_hashSharedProgramInstanceData.get(csSimpleName);
		if(s != null)
		{
			int nNbForm = s.getNbVarDefForm();
			for(int n=0; n<nNbForm; n++)
			{
				String csFormName = s.getFormName(n);
				if(csFormName != null)
					BaseResourceManager.removeResourceCache(csFormName);
			}
		}

		ms_hashSharedProgramInstanceData.remove(csSimpleName);
		s.prepareAutoRemoval();
	}
	
	static synchronized public void putSharedProgramInstanceData(String csSimpleName, SharedProgramInstanceData s)
	{
		ms_hashSharedProgramInstanceData.put(csSimpleName, s);
	}
	
	private static Hashtable<String, SharedProgramInstanceData> ms_hashSharedProgramInstanceData = new Hashtable<String, SharedProgramInstanceData>();		// hash table of SharedProgramInstanceData, indexed by program's simple name
}
