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

import jlib.misc.ArrayDyn;
import jlib.misc.ArrayFix;
import jlib.misc.ArrayFixDyn;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: OccursItemSettings.java,v 1.1 2006/04/19 09:53:08 cvsadmin Exp $
 */
public class OccursItemSettings
{
	OccursItemSettings()
	{
		m_arrVarDefOccursOwner = new ArrayDyn<VarDefBase>();
	}
	
	void compress()
	{
		if(m_arrVarDefOccursOwner != null)
		{	
			// Swap the type inside m_arrRedefinition 
			if(m_arrVarDefOccursOwner.isDyn())
			{
				int nSize = m_arrVarDefOccursOwner.size();
				VarDefBase arr[] = new VarDefBase [nSize];
				m_arrVarDefOccursOwner.transferInto(arr);
				
				ArrayFix<VarDefBase> arrVarDefOccursOwnerFix = new ArrayFix<VarDefBase>(arr);
				m_arrVarDefOccursOwner = arrVarDefOccursOwnerFix;	// replace by a fix one (uning less memory)
			}
		}
	}
	
	protected OccursOwnerLocation m_aOccursOwnerLocation[] = null;
	protected ArrayFixDyn<VarDefBase> m_arrVarDefOccursOwner = null;	// Array of VarDefBase; That is all occurs owner; may include ourself
}
