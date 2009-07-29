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
package nacaLib.accounting;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CriteriaEndRunMain
{
	public static final CriteriaEndRunMain Normal = new CriteriaEndRunMain(0, "Normal");
	public static final CriteriaEndRunMain Return = new CriteriaEndRunMain(1, "Return");
	public static final CriteriaEndRunMain Exit = new CriteriaEndRunMain(2, "Exit");
	public static final CriteriaEndRunMain StopRun = new CriteriaEndRunMain(3, "StopRun");
	public static final CriteriaEndRunMain XCtl = new CriteriaEndRunMain(4, "XCtl");
	public static final CriteriaEndRunMain Abort = new CriteriaEndRunMain(5, "Abort");
	public static final CriteriaEndRunMain Dump = new CriteriaEndRunMain(6, "Dump");
	public static final CriteriaEndRunMain GotoInAsyncStart = new CriteriaEndRunMain(7, "GotoInAsyncStart");
	private int m_nIndex = 0;
	private String m_csName = null;

	public static final int getNbIndex()
	{
		return 7;
	}

	private CriteriaEndRunMain(int n, String csName)
	{
		m_csName = csName;
		m_nIndex = n;
	}
	
	public String getName()
	{
		return m_csName;
	}

	public int getIndex()
	{
		return m_nIndex;
	}
}
