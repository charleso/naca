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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;


import jlib.display.widgets.MapCtrl;
import jlib.display.widgets.MapCtrl.MapMarker;
import jlib.misc.NumberParser;
import jlib.xml.Tag;
import jlib.xml.TagCursor;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class BaseDialog
{
	private DisplayContext m_Context = null ; 
	public BaseDialog(DisplayContext context)
	{
		m_csDisplayFile = context.getRootPath() + DeclareDispalyFile() ;
		m_Context = context ;
	}
	/**
	 * 
	 */
	public abstract boolean BeforeDisplay() ;
	protected abstract String DeclareDispalyFile() ;

	/**
	 * @return
	 */
	public Tag getXMLDisplay(String csDisplay)
	{
		Tag tagDisplay = new Tag("Root") ;
		Tag tagForm = getXMLFormFromResources(csDisplay) ;
		if (tagForm != null)
		{
			tagDisplay.addChild(tagForm) ;
		}
		
		FillWidgetValuesForChildren(tagDisplay) ;
		
		return tagDisplay ;
	}
	/**
	 * @param tagDisplay
	 */
	private void FillWidgetValuesForChildren(Tag tagParent, int index)
	{
		TagCursor cur = new TagCursor() ;
		Tag child = tagParent.getFirstChild(cur) ;
		while (child != null && cur.isValid())
		{
			FillWidgetValues(child, index) ;
			child = tagParent.getNextChild(cur) ;
		}
	}
	private void FillWidgetValuesForChildren(Tag tagParent)
	{
		FillWidgetValuesForChildren(tagParent, -1) ;
	}
	/**
	 * @param child
	 */
//	private void FillWidgetValues(Tag tag)
//	{
//		FillWidgetValues(tag, -1) ;
//	}
	private void FillWidgetValues(Tag tag, int index)
	{
		String csName = tag.getName();
		if (csName.equals("Edit"))
		{
			String widget = tag.getVal("Name") ;
			String val = getLocalValueForWidget(widget, index) ;
			tag.addVal("Value", val) ;
		}
		else if (csName.equals("Label"))
		{
			String widget = tag.getVal("Name") ;
			if (widget != null  && !widget.equals(""))
			{
				if (!tag.isValExisting("Text"))
				{
					String val = getLocalValueForWidget(widget, index) ;
					if (val != null)
					{
						tag.addVal("Text", val) ;
					}
				}
			}
		}
		else if (csName.equals("Link"))
		{
			String csText = tag.getVal("Text") ;
			String csParam = FindParam(csText) ;
			while (csParam != null)
			{
				String csVal = getLocalValueForWidget(csParam, index)  ;
				if (csVal == null)
				{
					csVal = ""; 
				}
				csText = csText.replaceAll("%"+csParam+"%", csVal) ;
				csParam = FindParam(csText) ;
			}
			tag.addVal("Text", csText) ;
			
			csText = tag.getVal("Link") ;
			csParam = FindParam(csText) ;
			while (csParam != null)
			{
				String csVal = getLocalValueForWidget(csParam, index)  ; 
				if (csVal == null)
				{
					csVal = "" ;
				}
				csText = csText.replaceAll("%"+csParam+"%", csVal) ;
				csParam = FindParam(csText) ;
			}
			tag.addVal("Link", csText) ;
		}
		else if (csName.equals("Array") && index == -1)
		{
			String name = tag.getVal("Name") ;
			String val = getLocalValueForWidget(name, index) ;
			int nb = NumberParser.getAsInt(val) ;
			Tag tagTemp = tag.getChild("Template") ;
			if (tagTemp != null && nb > 0)
			{
				for (int i=0; i<nb; i++)
				{
					Tag tagCell = tag.addTag("Cell") ;
					TagCursor cur = new TagCursor() ;
					Tag tagItem = tagTemp.getFirstChild(cur) ;
					while (tagItem != null)
					{
						Tag tagNew = tagItem.getCopy() ;
						tagCell.addChild(tagNew) ;
						String cs = tagNew.getVal("Name") ;
						FillWidgetValues(tagNew, i) ;
						tagNew.addVal("Name", cs + "_CELL_" + i) ;
						tagItem = tagTemp.getNextChild(cur) ;
					}
				}
			}
		}
		else if (csName.equals("Button") && index >= 0)
		{
//			String cs = tag.getVal("Name") ;
//			cs += "_CELL_" + index ;
//			tag.addVal("Name", cs) ;
			
			String csAction = tag.getVal("Action") ;
			csAction  += "_CELL_" + index ;
			tag.addVal("Action", csAction) ;
		}
		else if (csName.equals("If"))
		{
//			String cs = tag.getVal("Name") ;
//			cs += "_CELL_" + index ;
//			tag.addVal("Name", cs) ;
			
			String csTest = tag.getVal("Test") ;
			String val = getLocalValueForWidget(csTest, index) ;
			tag.addVal("Value", val) ;
			
			Tag tagThen = tag.getChild("Then") ;
			if (tagThen != null)
			{
				FillWidgetValuesForChildren(tagThen, index) ;
			}
			Tag tagElse = tag.getChild("Else") ;
			if (tagElse != null)
			{
				FillWidgetValuesForChildren(tagElse, index) ;
			}
		}
		else if (csName.equals("Map") && index == -1)
		{
			String csMapName = tag.getVal("Name") ;
			MapCtrl map ;
			try
			{
				map = (MapCtrl)getLocalObjectForWidget(csMapName, index) ;
			}
			catch (ClassCastException e)
			{
				e.printStackTrace() ;
				return ;
			}
			
			double lng = map.getCenterLng() ;
			double lat = map.getCenterLat() ;
			int zoom = map.getZoom() ;
			tag.addVal("CenterLng", lng) ;
			tag.addVal("CenterLat", lat) ;
			tag.addVal("Zoom", zoom) ;
			
			for (int i=0; i<map.getNbmarkers(); i++)
			{
				MapMarker mark = map.getMarker(i) ;
				Tag tagMark = tag.addTag("Marker") ;
				tagMark.addVal("Label", mark.m_csLabel) ;
				tagMark.addVal("Lng", mark.m_dLng) ;
				tagMark.addVal("Lat", mark.m_dLat) ;
			}
		}
		else
		{
			FillWidgetValuesForChildren(tag, index) ;
		}
	}
	/**
	 * @param csText
	 * @return
	 */
	private String FindParam(String csText)
	{
		int n = csText.indexOf('%')  ;
		if (n>=0)
		{
			int m = csText.indexOf('%', n+1) ;
			if (m>0) 
			{
				String cs = csText.substring(n+1, m) ;
				return cs ;
			}
		}
		return null;
	}
	/**
	 * @param widget
	 * @return
	 */
	private String getLocalValueForWidget(String widget, int index)
	{
		Class cl = this.getClass() ;
		Class[] args ;
		if (index == -1)
		{
			args = new Class[] {} ;
		}
		else
		{
			args = new Class[] { int.class } ;
		}
		String csMethode = "get"+widget ;
		try
		{
			Method  m = cl.getMethod(csMethode, args) ;
			if (m != null)
			{
				Object[] params ;
				if (index == -1)
				{
					params = new Object[] {} ;
				}
				else
				{
					params = new Object[] {index} ;
				}
				String cs = (String)m.invoke(this, params) ;
				if (cs == null)
				{
					return "" ;
				}
				return cs ;
			}
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			System.out.println(e.getMessage());
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return null ;
	}
	private Object getLocalObjectForWidget(String widget, int index)
	{
		Class cl = this.getClass() ;
		Class[] args ;
		if (index == -1)
		{
			args = new Class[] {} ;
		}
		else
		{
			args = new Class[] { int.class } ;
		}
		String csMethode = "get"+widget ;
		try
		{
			Method  m = cl.getMethod(csMethode, args) ;
			if (m != null)
			{
				Object[] params ;
				if (index == -1)
				{
					params = new Object[] {} ;
				}
				else
				{
					params = new Object[] {index} ;
				}
				Object o = m.invoke(this, params) ;
				return o ;
			}
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return null ;
	}
	private Tag getXMLFormFromResources(String csDisplay)
	{
		Tag tagDisplay = Tag.createFromFile(m_csDisplayFile) ;
		if (tagDisplay != null)
		{
			Tag tagForms = tagDisplay.getChild("Forms") ;
			if (tagForms != null)
			{
				TagCursor cur = new TagCursor() ;
				Tag tagForm = tagForms.getFirstChild(cur, "Form") ;
				while (tagForm != null && cur.isValid())
				{
					String csName = tagForm.getVal("Name") ;
					if (csName.equals(csDisplay))
					{
						return tagForm ;
					}
					tagForm = tagForms.getNextChild(cur) ;
				}
			}
		}
		return null ;
	}
	private String m_csDisplayFile = "" ;
	
	protected DisplayContext getContext()
	{
		return m_Context ;
	}
	
	protected void OpenDisplay(String form)
	{
		m_Context.AddDisplay(this, form) ;
	}
	
	protected void OpenDisplay(BaseDialog dlg)
	{
		dlg.BeforeDisplay() ;
	}
	/**
	 * @param reqLoader
	 * @return
	 */
	public boolean HandleRequest(HTTPMapFieldLoader reqLoader)
	{
		Enumeration en = reqLoader.getFieldNames() ;
		if (en == null)
			return false;
		
		Class cl = this.getClass() ;

		while (en.hasMoreElements())
		{
			String name = (String)en.nextElement() ;
			String val = reqLoader.getFieldValue(name) ;
			int index = -1 ;
			int pos = name.indexOf("_CELL_") ;
			if (pos > 0)
			{
				String cs = name.substring(pos + 6) ;
				index = NumberParser.getAsInt(cs) ;
				name = name.substring(0, pos) ;
			}

			Class[] args ;
			if (index >= 0)
			{
				args = new Class[] {String.class, int.class} ;
			}
			else
			{
				args = new Class[] {String.class} ;
			}
			String csMethode = "set"+name ;
			try
			{
				Method  m = cl.getMethod(csMethode, args) ;
				if (m != null)
				{
					Object[] vals ;
					if (index >= 0)
					{
						vals = new Object[] {val, index} ;
					}
					else
					{
						vals = new Object[] {val} ;
					}
					m.invoke(this, vals) ;
				}
			}
			catch (SecurityException e)
			{
				e.printStackTrace();
			}
			catch (IllegalArgumentException e)
			{
				//e.printStackTrace();
				int n=0 ;
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
			catch (NoSuchMethodException e)
			{
				//e.printStackTrace();
			}
		}
		
		String act = reqLoader.getAction() ;
		if (act != null && !act.equals(""))
		{
			try
			{
				int index = -1 ;
				int pos = act.indexOf("_CELL_") ;
				if (pos > 0)
				{
					String cs = act.substring(pos + 6) ;
					index = NumberParser.getAsInt(cs) ;
					act = act.substring(0, pos) ;
				}
				Class[] args ;
				if (index >= 0)
				{
					args = new Class[] {int.class} ;
				}
				else
				{
					args = new Class[] {} ;
				}
				Method  m = cl.getMethod(act, args) ;
				if (m != null)
				{
					Object[] vals ;
					if (index >= 0)
					{
						vals = new Object[] {index} ;
					}
					else
					{
						vals = new Object[] {} ;
					}
					Boolean b = (Boolean)m.invoke(this, vals) ;
					return b.booleanValue() ;
				}
			}
			catch (SecurityException e)
			{
				e.printStackTrace();
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (NoSuchMethodException e)
			{
				//e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
			return false ;
		}
		return true ;	
	}
	
	protected void CloseCurrentDisplay()
	{
		if (!m_Context.m_stackDisplayedElements.isEmpty())
		{
			m_Context.m_stackDisplayedElements.pop() ;
		}
	}
}