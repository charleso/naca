/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 8 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jlib.log;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.util.List;


/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PatternLayoutSTCheck extends LogPatternLayout
{
	public PatternLayoutSTCheck()
	{
		super();
	}
	
	String getMessage(LogParams logParams)
	{
		String csMessage = logParams.getMessage();
		return csMessage;	
	}
	
	private long m_mem[] = new long[3];

	String format(LogParams logParams, int n)
	{
		 
		if(n == 0)
		{
			String csMessage = logParams.toString();
			csMessage = logParams.toString();
			StackTraceElement stackElem = logParams.m_caller;
			String csOut;
			if(stackElem != null)
			{
				String csFile = stackElem.getFileName();
				int nLine = stackElem.getLineNumber();
				csOut = csFile+"("+nLine+"):"+csMessage;
			}
			else
			{
				csOut = "():"+csMessage;
			}
			csOut += "\r\n******** Mem:";
			int nMem = 0;
			List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
			for (MemoryPoolMXBean p: pools)
			{
				if(p.getType().compareTo(MemoryType.HEAP) == 0)
				{
					String cs = p.getName();
					MemoryUsage mem = p.getUsage();
					long l = mem.getUsed();
					long lOldMem = m_mem[nMem];
					long lOffset = l - lOldMem; 
					csOut += cs+"="+l+"["+lOffset+"];";
					m_mem[nMem] = l;
					nMem++;
				}
			}
			csOut += "\r\n";
			return csOut;
		}
		return null;
	}
	
	int getNbLoop(LogParams logParams)
	{
		return 1;
	}
}
