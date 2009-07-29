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
/**
 * 
 */
package jlib.polling;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class MaskFileFilter implements FileFilter
{
	//FileFilter fileFilter = new WildcardFileFilter("*test*.java~*~");

	private ArrayList<FileFilter> m_arrMaskFilters = null;	// Optional array of all masks; only files amtching these masks are polled
	
	public void setMasks(String csMasks)
	{
		if(csMasks != null)
		{
			String arr[] = csMasks.split(";");
			for(int n=0; n<arr.length; n++)
				add(arr[n]);
		}
	}
	
	void add(String csMask)
	{
		if(csMask != null)
		{
			if(m_arrMaskFilters == null)
				m_arrMaskFilters = new ArrayList<FileFilter>();
			
			csMask = csMask.trim();
			FileFilter fileFilter = new WildcardFileFilter(csMask);
			m_arrMaskFilters.add(fileFilter);
		}
	}
	
	public boolean accept(File pathname)
	{
		if(m_arrMaskFilters == null)
			return true;
		
		for(int n=0; n<m_arrMaskFilters.size(); n++)
		{
			FileFilter maskFilter = m_arrMaskFilters.get(n);
			if(maskFilter.accept(pathname))
				return true;
		}
		return false;
	}
}
