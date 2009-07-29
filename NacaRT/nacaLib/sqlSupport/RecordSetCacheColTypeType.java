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
package nacaLib.sqlSupport;

import jlib.misc.ArrayDynDiscontinuous;
import jlib.misc.ArrayFix;
import jlib.misc.ArrayFixDyn;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class RecordSetCacheColTypeType
{
	RecordSetCacheColTypeType()
	{
		m_arrColsType = new ArrayDynDiscontinuous<RecordColTypeManagerBase>();	
	}
	
	RecordColTypeManagerBase getRecordColTypeManager(int nIndex)
	{
		return m_arrColsType.get(nIndex);
	}
	
//	void add(RecordColTypeManagerBase recordColTypeManagerBase)
//	{
//		m_arrColsType.add(recordColTypeManagerBase);
//	}
	
	void set(int nIndex, RecordColTypeManagerBase recordColTypeManagerBase)
	{
		m_arrColsType.set(nIndex, recordColTypeManagerBase);
	}
	
	void compress()
	{
		// Swap the type inside m_arrColsType
		if(m_arrColsType.isDyn())
		{
			int nSize = m_arrColsType.size();
			RecordColTypeManagerBase arr[] = new RecordColTypeManagerBase[nSize];
			m_arrColsType.transferInto(arr);
			
			ArrayFix<RecordColTypeManagerBase> arrFix = new ArrayFix<RecordColTypeManagerBase>(arr);
			m_arrColsType = arrFix;	// replace by a fix one (uning less memory)
		}
	}
	
	private ArrayFixDyn<RecordColTypeManagerBase> m_arrColsType = null;	// hash table of boolean, indexed by col id, indexed based 0
}
