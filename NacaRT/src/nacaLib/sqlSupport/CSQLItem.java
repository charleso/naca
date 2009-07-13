/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 12 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.sqlSupport;

import nacaLib.varEx.VarAndEdit;
import nacaLib.varEx.VarBase;
import nacaLib.varEx.VarEnumerator;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CSQLItem
{	
	protected VarAndEdit m_var = null;
	protected String m_csValue = null;

	public CSQLItem(VarAndEdit var)
	{
		m_var = var;
	}
	public void set(VarAndEdit var)
	{
		m_var = var;
		m_csValue = null;
	}


	public CSQLItem(int nValue)
	{
		m_csValue = String.valueOf(nValue);
	}
	public void set(int nValue)
	{
		m_var = null;
		m_csValue = String.valueOf(nValue);
	}

	public CSQLItem(double dValue)
	{
		m_csValue = String.valueOf(dValue);
	}
	public void set(double dValue)
	{
		m_var = null;
		m_csValue = String.valueOf(dValue);
	}

	public CSQLItem(String cs)
	{
		m_csValue = cs;
	}	
	public void set(String cs)
	{
		m_var = null;
		m_csValue = cs;
	}
	
	public String getValue()
	{
		if(m_var != null)
		{
			if(isLongVarCharVarHolder())
			{
				VarEnumerator e = new VarEnumerator(m_var.getProgramManager(), m_var); 
				VarBase varChildLength = e.getFirstVarChild();
				VarBase varChildText = e.getNextVarChild();

				//int nLength = varChildLength.
				int nLength = varChildLength.getInt();
				//String csValue = varChildText.getDottedSignedString();
				String csValue = varChildText.getDottedSignedStringAsSQLCol();
				if(nLength < csValue.length())
					csValue = csValue.substring(0, nLength);
				return csValue;
			}
			return m_var.getDottedSignedStringAsSQLCol();
		}
		return m_csValue;
	}
	
	public String getDebugValue()
	{
		String cs = getValue();
		byte t[] = cs.getBytes();
		for(int n=0; n<t.length; n++)
		{
			byte b = t[n];
			if(b == 0)
				t[n] = '$';
		}
		cs = new String(t);
		return cs;
	}
	
	private boolean isLongVarCharVarHolder()	// Indicates if the m_var contains a long varchar structure
	{
		return m_var.getVarDef().isLongVarCharVarStructure();
	}

	public CSQLItemType getType()
	{
		if(m_var != null)
		{
			return m_var.getSQLType(); 
//			
//			if (m_var.hasType(VarTypeEnum.Type9))
//			{
//				return CSQLItemType.SQL_TYPE_INTEGER;
//			}
//			else if (m_var.hasType(VarTypeEnum.TypeX) 
//				|| m_var.hasType(VarTypeEnum.TypeEditedAlphaNum)
//				|| m_var.hasType(VarTypeEnum.TypeEditedNum)
//				|| m_var.hasType(VarTypeEnum.TypeFieldEdit)
//				|| m_var.hasType(VarTypeEnum.TypeGroup))
//			{
//				return CSQLItemType.SQL_TYPE_STRING ;
//			}
//			else
//			{
//				return CSQLItemType.SQL_TYPE_NONE;
//			}
		}
		return null;
	}

	/**
	 * @return
	 */
	public int getIntValue()
	{
		if(m_var != null)
			return m_var.getInt() ;
		return 0;
	}
	
	public long getLongValue()
	{
		if(m_var != null)
			return m_var.getLong() ;
		return 0L;
	}
}