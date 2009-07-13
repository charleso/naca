/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.dbUtils;

import jlib.misc.StringUtil;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: LoadScriptLineInfo.java,v 1.4 2006/10/24 11:31:38 u930di Exp $
 */
public class LoadScriptLineInfo
{
	private boolean m_bReplace = false;
	private String m_csTablePrefix = null;
	private String m_csUnprefixedTableName = null;
	private String m_csInddnValue = null;
	
	void setFullTable(String csFullTable)
	{
		m_csTablePrefix = StringUtil.getTablePrefix(csFullTable);
		m_csUnprefixedTableName = StringUtil.getUnprefixedTableName(csFullTable);
	}
	
//	String getFullTableName()
//	{
//		return StringUtil.makeFullTableName(m_csTablePrefix, m_csUnprefixedTableName);
//	}
	
	String getFullTableName(String csPrefix)
	{
		if(!StringUtil.isEmpty(m_csTablePrefix) && !m_csTablePrefix.equalsIgnoreCase("PROD"))
			return StringUtil.makeFullTableName(m_csTablePrefix, m_csUnprefixedTableName);
		m_csTablePrefix = csPrefix;
		return StringUtil.makeFullTableName(csPrefix, m_csUnprefixedTableName);
	}
	
	String getTablePrefix()
	{
		return m_csTablePrefix;
	}
	
	String getUnprefixedTableName()
	{
		return m_csUnprefixedTableName;
	}

	void setReplace(boolean b)
	{
		m_bReplace = b;
	}
	
	boolean isReplace()
	{
		return m_bReplace;
	}
	
	void setInddnValue(String csInddnValue)
	{
		m_csInddnValue = csInddnValue;
	}
		
	String getInddnValue()
	{
		return m_csInddnValue;
	}
}
