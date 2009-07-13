/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on Jan 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.CESM;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CESMReturnCode
{
	private CESMReturnCode(int cond, String code)
	{
		m_nCondition = cond ;
		m_sCode = code ;
	}
	private int m_nCondition = 0;
	private String m_sCode = "" ;
	public String getCode()
	{
		return m_sCode ;
	}
	public int getCondition()
	{
		return m_nCondition ;
	}
	
	public static CESMReturnCode NORMAL = new CESMReturnCode(0, "\u0000") ;
	public static CESMReturnCode NOT_FOUND = new CESMReturnCode(13, "a") ;	
	public static CESMReturnCode DUPREC = new CESMReturnCode(14, "b") ;
	public static CESMReturnCode ENDFILE = new CESMReturnCode(20, "\u000f") ;		
	public static CESMReturnCode ITEMERR = new CESMReturnCode(26, "\u0001") ;
	public static CESMReturnCode QIDERR = new CESMReturnCode(44, "\u0002") ;
	public static CESMReturnCode LENGERR = new CESMReturnCode(22, "\u00f7") ;
	public static CESMReturnCode NET_NAME_ERROR = new CESMReturnCode(99, "\uFFFF") ;
	/**
	 * @param n
	 * @return
	 */
	//http://publibfp.boulder.ibm.com/cgi-bin/bookmgr/BOOKS/dfhp4p06/A.1?SHELF=&DT=20040113165420&CASE
	public static CESMReturnCode Select(int n)
	{
		if (n == 0)
		{
			return NORMAL ;
		}
		else if (n == NOT_FOUND.m_nCondition)
		{
			return NOT_FOUND ;
		}
		else if (n == NET_NAME_ERROR.m_nCondition)
		{
			return NET_NAME_ERROR ;
		}	
		else if (n == QIDERR.m_nCondition)
		{
			return QIDERR ;
		}
		else if (n == ITEMERR.m_nCondition)
		{
			return ITEMERR ;
		}
		else
		{
			throw new RuntimeException() ; // must not be there : in this case, a condition code is not handled 
		}
	}
}
