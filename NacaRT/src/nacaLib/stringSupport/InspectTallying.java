/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 7 déc. 04
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
import nacaLib.varEx.VarAndEdit;

public class InspectTallying
{
	public static final InspectTallying TypeForAll = new InspectTallying("");
	public static final InspectTallying TypeForChars = new InspectTallying("");
	public static final InspectTallying TypeLeading = new InspectTallying("");

	public InspectTallying(VarAndEdit var)
	{
		m_source = var.getString() ;
	}
	public InspectTallying(String var)
	{
		m_source = var;
	}
	
	
	public InspectTallying countAll(String csSearchForAll)
	{
		m_csSearchForAll = csSearchForAll;
		m_InspectTallyingType = TypeForAll;
		return this;
	}
	public InspectTallying countAll(VarAndEdit csSearchForAll, VarAndEdit result)
	{
		return countAll(csSearchForAll.getString(), result);
	}
	public InspectTallying countAll(String csSearchForAll, VarAndEdit result)
	{
		m_csSearchForAll = csSearchForAll;
		m_InspectTallyingType = TypeForAll;
		return to(result);
	}
	
	public InspectTallying countAll(VarAndEdit varSearchForAll)
	{
		m_csSearchForAll = varSearchForAll.getString();
		m_InspectTallyingType = TypeForAll;
		return this;
	}
	
	public InspectTallying countLeading(String csLeading)
	{
		m_csSearchForAll = csLeading;
		m_InspectTallyingType = TypeLeading;
		return this;
	}

	public InspectTallying countLeading(VarAndEdit varLeading)
	{
		m_csSearchForAll = varLeading.getString();
		m_InspectTallyingType = TypeLeading;
		return this;
	}

	public InspectTallying countLeading(String csLeading, VarAndEdit vto)
	{
		m_csSearchForAll = csLeading;
		m_InspectTallyingType = TypeLeading;
		return to(vto) ;
	}

	public InspectTallying countCharsBefore(VarAndEdit csBefore, VarAndEdit vto)
	{
		return countCharsBefore(csBefore.getString(), vto);
	}

	public InspectTallying countCharsBefore(String csBefore, VarAndEdit vto) {
		m_csBefore = csBefore;
		m_InspectTallyingType = TypeForChars;
		return to(vto) ;
	}

	public InspectTallying forChars()
	{
		m_InspectTallyingType = TypeForChars;
		return this;
	}
	
	public InspectTallying before(String csBefore)
	{
		m_csBefore = csBefore;
		return this;
	}
	
	public InspectTallying before(VarAndEdit varBefore)
	{
		m_csBefore = varBefore.getString();
		return this;
	}

	public InspectTallying after(String csAfter)
	{
		m_csAfter = csAfter;
		return this;
	}

	public InspectTallying after(VarAndEdit varAfter)
	{
		m_csAfter = varAfter.getString();
		return this;
	}
		
	public InspectTallying to(VarAndEdit varCount)
	{
		String csSource = m_source ;
		
		// Find substring where to count
		if(m_csAfter != null)	// We have a starting point
		{
			int nPosAfter = csSource.indexOf(m_csAfter);
			if(nPosAfter == -1)	// No delimiter found: Nothing to do
				return this;
			csSource = csSource.substring(nPosAfter+1);
		}
		
		if(m_csBefore != null)	// We have a ending point
		{
			int nPosBefore = csSource.indexOf(m_csBefore);
			if(nPosBefore == -1)	// No delimiter found: Nothing to do
				return this;
			csSource = csSource.substring(0, nPosBefore);
		}
		
		// We now an the substring on which to operate the counting
		if(m_InspectTallyingType == TypeForChars)	// Count the number of chars
		{
			int nCount = csSource.length();
			varCount.set(nCount+varCount.getInt());
		}
		else if(m_InspectTallyingType == TypeForAll)	// Count the number of occurences
		{
			int nCount = 0;
			int nPos = csSource.indexOf(m_csSearchForAll);
			while(nPos >= 0)
			{
				nCount++;
				csSource = csSource.substring(nPos+m_csSearchForAll.length());
				nPos = csSource.indexOf(m_csSearchForAll);
			}
			varCount.set(nCount+varCount.getInt());
		}
		else if(m_InspectTallyingType == TypeLeading)	// Count the number of occurences, if the string begins by the search pattern
		{
			int nCount = 0;
			int nPos = csSource.indexOf(m_csSearchForAll);
			if(nPos == 0)	// The source string begins by the search pattern 
			{
				while(nPos == 0)
				{
					nCount++;
					csSource = csSource.substring(nPos+m_csSearchForAll.length());
					nPos = csSource.indexOf(m_csSearchForAll);
				}
				varCount.set(nCount+varCount.getInt());
			}
		}		
		return this ;
	}
	
	private String m_source = null;
	private String m_csSearchForAll = null;
	private String m_csBefore = null;
	private String m_csAfter = null;
	private InspectTallying m_InspectTallyingType = null;	
}
