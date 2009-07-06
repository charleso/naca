/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

public class SortParagHandler
{
	SortParagHandler(SortCommand sortCommand)
	{
		m_sortCommand = sortCommand;
	}
	
	public void release(Var varRecord)
	{
		m_sortCommand.release(varRecord);
	}
	
	public RecordDescriptorAtEnd returnSort(SortDescriptor sortDescriptor)
	{
		return m_sortCommand.returnSort(sortDescriptor);
	}
	
	private SortCommand m_sortCommand = null;
}
