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
package nacaLib.program;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

public class CopyManager
{	
	static void register(String csCopyName, String csProgramName)
	{
		ProgramCopyOwner programCopyOwner = ms_hashProgramsByCopy.get(csCopyName);
		if(programCopyOwner == null)
		{
			programCopyOwner = new ProgramCopyOwner(csCopyName);
			ms_hashProgramsByCopy.put(csCopyName, programCopyOwner);			
		}			
		programCopyOwner.add(csProgramName);
	}
	
	static public void removeCopyFormProg(String csCopyName, String csProgramName)
	{
		ProgramCopyOwner programCopyOwner = ms_hashProgramsByCopy.get(csCopyName);
		if(programCopyOwner != null)
		{
			boolean bRemove = programCopyOwner.removeProgramOwner(csProgramName);
			if(bRemove)
			{
				ms_hashProgramsByCopy.remove(csCopyName);
			}
		}
	}
	
	static public void showBeans(boolean b)
	{
		Collection<ProgramCopyOwner> col = ms_hashProgramsByCopy.values();
		Iterator<ProgramCopyOwner> iter = col.iterator();
		while(iter.hasNext())
		{
			ProgramCopyOwner programCopyOwner = iter.next();
			programCopyOwner.showBean(b);			
		}
	}	
	
	private static Hashtable<String, ProgramCopyOwner> ms_hashProgramsByCopy = new Hashtable<String, ProgramCopyOwner>();
}
