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
 * Created on 10 nov. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author PJD
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.program;

import nacaLib.base.JmxGeneralStat;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.varEx.*;

public class Copy
{
	public Copy(BaseProgram program, CopyReplacing copyReplacing)
	{
		declare = new VarDeclaration(program, copyReplacing);
		String csCopyName = toString();
		int n = csCopyName.indexOf('@');
		if(n > 0)
			csCopyName = csCopyName.substring(0, n);
		if(program != null)
		{
			String csProgramOwnerName = program.getSimpleName();
			CopyManager.register(csCopyName, csProgramOwnerName);
		}
		JmxGeneralStat.incCopyClassLoaded(1);
	}
	
	public void finalize()
	{
		//Log.logNormal("Copy finalized: " +toString()); 
		JmxGeneralStat.incCopyClassLoaded(-1);
	}
	
	protected VarDeclaration declare = null;
}