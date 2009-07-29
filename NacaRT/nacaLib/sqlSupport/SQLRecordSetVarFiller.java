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
 * Created on 5 juil. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.sqlSupport;

import jlib.misc.ArrayDyn;
import jlib.misc.ArrayFix;
import jlib.misc.ArrayFixDyn;
import nacaLib.varEx.VarBase;

/**
 * @author u930di
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SQLRecordSetVarFiller
{	
	public SQLRecordSetVarFiller()
	{
		m_recordSetCacheColTypeType = new RecordSetCacheColTypeType();
	}
	
	void apply(CSQLResultSet resultSet)
	{
		if(m_arrItem != null)
		{
			int nNbItems = m_arrItem.size();
			for(int n=0; n<nNbItems; n++)
			{
				SQLRecordSetVarFillerItem item = m_arrItem.get(n);
				item.apply(resultSet, m_recordSetCacheColTypeType); 
			}
		}
	}
	
	void addLinkColDestination(int nColSource, VarBase varInto, VarBase varIndicator)
	{
		SQLRecordSetVarFillerItem item = new SQLRecordSetVarFillerItem(nColSource, varInto, varIndicator);
		if(m_arrItem == null)
			m_arrItem = new ArrayDyn<SQLRecordSetVarFillerItem>();	// new ArrayList<SQLRecordSetVarFillerItem>();
		m_arrItem.add(item);
	}
	
	void compress()
	{
		if(m_arrItem != null)
		{
			if(m_arrItem.isDyn())
			{
				int nSize = m_arrItem.size();
				SQLRecordSetVarFillerItem arr[] = new SQLRecordSetVarFillerItem[nSize];
				m_arrItem.transferInto(arr);
				
				ArrayFix<SQLRecordSetVarFillerItem> arrFix = new ArrayFix<SQLRecordSetVarFillerItem>(arr);
				m_arrItem = arrFix;	// replace by a fix one (uning less memory)
			}
		}
	}
	
	RecordSetCacheColTypeType getRecordSetCacheColTypeType()
	{
		return m_recordSetCacheColTypeType;
	}
	
	public int getNbCol()
	{
		return m_nNbColResultSet;
	}
	
	public void setNbCol(int nNbColResultSet)
	{
		m_nNbColResultSet = nNbColResultSet;
	}
	
	private int m_nNbColResultSet = 0;
	
	private ArrayFixDyn<SQLRecordSetVarFillerItem> m_arrItem = null;
	// ArrayFixDynList<SQLRecordSetVarFillerItem> m_arrItem = null;
	
	private RecordSetCacheColTypeType m_recordSetCacheColTypeType = null; 
}
