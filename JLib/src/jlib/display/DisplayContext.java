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
 * Created on 7 juil. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jlib.display;

import java.util.Stack;

import jlib.xml.Tag;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DisplayContext
{
	protected class DisplayedElement
	{
		public BaseDialog m_Dialog = null ;
		public String m_Display = "" ;
	}
	protected DisplayConfig m_Config ;
	protected Stack<DisplayedElement> m_stackDisplayedElements = new Stack<DisplayedElement>() ;
	
	public DisplayContext()
	{
		m_Config = DisplayConfig.getInstance() ;
	}
	
	/**
	 * @param reqLoader
	 * @param output
	 * @return
	 */
	public boolean OnRequest(HTTPMapFieldLoader reqLoader, DisplayOutput output)
	{
		if(m_stackDisplayedElements.isEmpty())
		{
			BaseDialogFactory factory = m_Config.getDialogFactory() ;
			BaseDialog dlg = factory.getInitialDialog(this) ;
			if (dlg == null)
			{
				return false ;
			}
			return OpenDialog(dlg, output) ;
		}

		DisplayedElement dlg = m_stackDisplayedElements.lastElement() ;
		if (!dlg.m_Dialog.HandleRequest(reqLoader))
		{
			return false ;
		}
		return ShowFrontDialog(output) ;
	}

	/**
	 * @param dlg
	 * @param output
	 */
	private boolean ShowFrontDialog(DisplayOutput output)
	{
		DisplayedElement element = m_stackDisplayedElements.lastElement() ;
		if (element == null || element.m_Dialog == null)
			return false ;

		Tag tagOutput = element.m_Dialog.getXMLDisplay(element.m_Display) ;
		if (tagOutput == null)
			return false  ;
		
		output.setXMLDisplay(tagOutput) ;
		return true;
	}

	private boolean OpenDialog(BaseDialog dlg, DisplayOutput output)
	{
		if (!dlg.BeforeDisplay())
			return false ;
		
		return ShowFrontDialog(output) ;
	}

	/**
	 * @return
	 */
	public String getRootPath()
	{
		String path = m_Config.getRootPath() ;
		return path;
	}

	/**
	 * @param dialog
	 * @param form
	 */
	public void AddDisplay(BaseDialog dialog, String form)
	{
		DisplayedElement el = new DisplayedElement() ;
		el.m_Dialog = dialog ;
		el.m_Display = form ;
		m_stackDisplayedElements.add(el) ;
	}
}
