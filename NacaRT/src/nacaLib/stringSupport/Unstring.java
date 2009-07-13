/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 16 déc. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.stringSupport;
// import nacaLib.base.*;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarAndEdit;

public class Unstring
{
	public Unstring(VarAndEdit varSource)
	{
		m_UnstringManager = new UnstringManager(varSource);
	}
	
	public Unstring delimitedBy(VarAndEdit varDelimiter)
	{
		return delimitedBy(varDelimiter.getString());	
	}

	public Unstring delimitedBy(String csDelimiter)
	{
		UnstringDelimiter Delimiter = new UnstringDelimiter(csDelimiter, false);
		m_UnstringManager.m_arrDelimiters.add(Delimiter);
		return this;		
	}
	
	public Unstring delimitedByAll(Var varDelimiter)
	{
		return delimitedByAll(varDelimiter.getString());
	}
	
	public Unstring delimitedByAll(String csDelimiter)
	{
		UnstringDelimiter Delimiter = new UnstringDelimiter(csDelimiter, true);
		m_UnstringManager.m_arrDelimiters.add(Delimiter);
		return this;		
	}
	
	public Unstring tallying(Var varTallying)
	{
		m_UnstringManager.tallying(varTallying);
		return this;
	}
	
	public UnstringToManager to(Var varDelimiterDest, Var varDelimiterIn, Var varCountDest)
	{
		UnstringToManager unstringToManager = new UnstringToManager(m_UnstringManager);
		m_UnstringManager.doInto(varDelimiterDest, varDelimiterIn, varCountDest);
		return unstringToManager;
	}
	
	public UnstringToManager to(Var varDelimiterDest)
	{
		UnstringToManager unstringToManager = new UnstringToManager(m_UnstringManager);
		m_UnstringManager.doInto(varDelimiterDest, null, null);
		return unstringToManager;
	}
	
//	public boolean failed()
//	{
//		if(m_UnstringManager == null)
//			return true;
//		return m_UnstringManager.failed();
//	}
//	
//	public boolean notFailed()
//	{
//		if(m_UnstringManager == null)
//			return false;
//		return !m_UnstringManager.failed();
//	}
	
	public Unstring withPointer(Var varPointer)
	{
		m_UnstringManager.withPointer(varPointer);
		return this ;
	}
	
	private UnstringManager m_UnstringManager = null;
}
