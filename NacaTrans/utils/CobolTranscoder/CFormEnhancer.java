/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 16 févr. 2005
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package utils.CobolTranscoder;

import java.io.File;
import java.io.FilenameFilter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import semantic.forms.CEntityResourceForm;
import semantic.forms.CEntityResourceFormContainer;

/**
 * @author sly
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CFormEnhancer
{
	protected String m_csTransformDir = "" ;
	protected Document m_docGlobalTransform = null ;
	public CFormEnhancer(String path, String global)
	{
		m_csTransformDir = path ;
		if (path == null)
		{
			m_csTransformDir = "" ;
		}
		if (!global.equals(""))
		{
			m_docGlobalTransform = LoadXML(new File(global)) ;
		}
	}
	/**
	 * @param cont
	 */
	public void ProcessFormContainer(CEntityResourceFormContainer cont, boolean bResources)
	{
		if (cont == null)
		{
			return ;
		}
		CEntityResourceForm form = cont.getForm() ;
		if (form == null)
		{
			return ;
		}
		
		if (m_docGlobalTransform != null)
		{
			ApplyTransform(form, m_docGlobalTransform, bResources) ;
		}

		Document transform = FindTransformation(cont.GetDisplayName());
		if (transform != null)
		{
			ApplyTransform(form, transform, bResources) ;
		}
		else
		{		
			transform = FindTransformation(cont.GetDisplayName().substring(0, 4));
			if (transform != null)
			{
				ApplyTransform(form, transform, bResources) ;
			}
		}
	}
	
	private void ApplyTransform(CEntityResourceForm form, Document transform, boolean bResources)
	{
		Element root = transform.getDocumentElement() ;
		NodeList lst = root.getChildNodes() ;
		if (bResources)
		{	
			for (int i=0; i<lst.getLength(); i++)
			{
				Node node = lst.item(i) ;
				if (node.getNodeName().equals("setDevelopable"))
				{
					doSetDevelopable((Element)node, form) ;
				}
				else if (node.getNodeName().equals("setFormat"))
				{
					doSetFormat((Element)node, form) ;
				}
				else if (node.getNodeName().equals("setReplayMutable"))
				{
					doSetReplayMutable((Element)node, form) ;
				}
			}
		}
		else
		{
			for (int i=0; i<lst.getLength(); i++)
			{
				Node node = lst.item(i) ;
				if (node.getNodeName().equals("renameField"))
				{
					doRenameField((Element)node, form) ;
				}
				else if (node.getNodeName().equals("defineCheckBox"))
				{
					doSetCheckBox((Element)node, form) ;
				}
				else if (node.getNodeName().equals("setDevelopable"))
				{
					doSetDevelopable((Element)node, form) ;
				}
				else if (node.getNodeName().equals("setFormat"))
				{
					doSetFormat((Element)node, form) ;
				}
				else if (node.getNodeName().equals("nameLabel"))
				{
					doNameLabel((Element)node, form) ;
				}
				else if (node.getNodeName().equals("nameEdit"))
				{
					doNameLabel((Element)node, form) ;
				}
				else if (node.getNodeName().equals("setTitle"))
				{
					doSetTitle((Element)node, form) ;
				}
				else if (node.getNodeName().equals("activeChoice"))
				{
					doSetActiveChoice((Element)node, form) ;
				}
				else if (node.getNodeName().equals("setReplayMutable"))
				{
					doSetReplayMutable((Element)node, form) ;
				}
				else if (node.getNodeName().equals("setPfkeyDefine"))
				{
					doSetPfkeyDefine((Element)node, form) ;
				}
				else if (node.getNodeName().equals("setPfkeyAction"))
				{
					doSetPfkeyAction((Element)node, form) ;
				}
				else if (node.getNodeName().equals("setCustomOnload"))
				{
					doSetCustomOnload((Element)node, form) ;
				}
				else if (node.getNodeName().equals("setCustomSubmit"))
				{
					doSetCustomSubmit((Element)node, form) ;
				}
				else if (node.getNodeName().equals("defineSwitch"))
				{
					doSetSwitchCase((Element)node, form) ;
				}
				else if (node.getNodeName().equals("hideField"))
				{
					doHideField((Element)node, form) ;
				}
				else if (node.getNodeName().equals("addItem"))
				{
					doAddItem((Element)node, form) ;
				}
				else if (node.getNodeName().equals("addLine"))
				{
					doAddLine((Element)node, form) ;
				}
				else if (node.getNodeName().equals("moveField"))
				{
					doMoveField((Element)node, form) ;
				}
				else if (node.getNodeName().equals("setDefaultCursor"))
				{
					doSetDefaultCursor((Element)node, form) ;
				}
			}
		}
	}
	
	private void doMoveField(Element element, CEntityResourceForm form)
	{
		String col = element.getAttribute("col") ;
		String line = element.getAttribute("line") ;
		String newcol = element.getAttribute("newcol") ;
		String newline = element.getAttribute("newline") ;
		String name = element.getAttribute("name") ;
		if (newcol != null && !newcol.equals(""))
		{
			if (newline != null && !newline.equals(""))
			{
				int nc = Integer.parseInt(newcol);
				int nl = Integer.parseInt(newline) ;
				if (!name.equals(""))
				{
					form.MoveField(name, nc, nl) ;
				}
				else 
				{
					if (col != null && !col.equals(""))
					{
						if (line != null && !line.equals(""))
						{
							int c = Integer.parseInt(col);
							int l = Integer.parseInt(line) ;
							
							form.MoveField(c, l, nc, nl) ;
						}
					}
				}
			}
		}
	}
	private void doAddLine(Element element, CEntityResourceForm form)
	{
		String col = element.getAttribute("col") ;
		String line = element.getAttribute("line") ;
		String length = element.getAttribute("length") ;
		if (col != null && !col.equals(""))
		{
			if (line != null && !line.equals(""))
			{
				if (length != null && !length.equals(""))
				{
					int c = Integer.parseInt(col);
					int l = Integer.parseInt(line) ;
					int s = Integer.parseInt(length) ;
					
					form.AddLine(c, l, s) ;
				}
			}
		}
	}
	private void doAddItem(Element element, CEntityResourceForm form)
	{
		String col = element.getAttribute("col") ;
		String line = element.getAttribute("line") ;
		String length = element.getAttribute("length") ;
		if (col != null && !col.equals(""))
		{
			if (line != null && !line.equals(""))
			{
				if (length != null && !length.equals(""))
				{
					int c = Integer.parseInt(col);
					int l = Integer.parseInt(line) ;
					int s = Integer.parseInt(length) ;
					
					Node n = element.getFirstChild() ;
					while (n.getNodeType() != Node.ELEMENT_NODE)
					{
						n = n.getNextSibling() ;
					}
					if (n != null)
					{
						form.AddItem(c, l, s, (Element)n) ;
					}
				}
			}
		}
		
	}
	private void doHideField(Element element, CEntityResourceForm form)
	{
		String col = element.getAttribute("col") ;
		String line = element.getAttribute("line") ;
		String name = element.getAttribute("name") ;
		if (name != null && !name.equals(""))
		{
			form.HideField(name) ;
		}
		else if (col != null && !col.equals(""))
		{
			if (line != null && !line.equals(""))
			{
				int c = Integer.parseInt(col);
				int l = Integer.parseInt(line) ;
				form.HideField(c, l) ;
			}
		}
	}
	private void doSetSwitchCase(Element element, CEntityResourceForm form)
	{
		String field = element.getAttribute("name") ;
		
		NodeList lst = element.getElementsByTagName("case") ;
		if (lst != null)
		{
			for (int i=0; i<lst.getLength(); i++)
			{
				Element cas = (Element)lst.item(i) ;
				String value = null;
				String protection = null;
				if (cas.hasAttribute("value"))
					value = cas.getAttribute("value") ;
				if (cas.hasAttribute("protection"))
					protection = cas.getAttribute("protection") ;
				Node n = cas.getFirstChild() ;
				while (n.getNodeType() != Node.ELEMENT_NODE)
				{
					n = n.getNextSibling() ;
				}
				Element tag = (Element)n ;
				form.AddSwitchCase(field, value, protection, tag) ;
			}
		}
		lst = element.getElementsByTagName("default") ;
		if (lst != null && lst.getLength()>0)
		{
			Element cas = (Element)lst.item(0) ;
			Node n = cas.getFirstChild() ;
			while (n.getNodeType() != Node.ELEMENT_NODE)
			{
				n = n.getNextSibling() ;
			}
			Element tag = (Element)n ;
			form.AddSwitchCase(field, null, null, tag) ;
		}
		
	}
	/**
	 * @param element
	 * @param form
	 */
	private void doSetCustomOnload(Element element, CEntityResourceForm form)
	{
		String method = element.getAttribute("function") ;
		form.SetCustomOnload(method) ;
	}
	/**
	 * @param element
	 * @param form
	 */
	private void doSetCustomSubmit(Element element, CEntityResourceForm form)
	{
		String method = element.getAttribute("function") ;
		form.SetCustomSubmit(method) ;
	}
	/**
	 * @param element
	 * @param form
	 */
	private void doSetPfkeyDefine(Element element, CEntityResourceForm form)
	{
		String key = element.getAttribute("key");
		String status = element.getAttribute("status");
		if (key != null && !key.equals("") && status != null && !status.equals(""))
		{
			form.setPFActive(key, status);
		}
	}
	/**
	 * @param element
	 * @param form
	 */
	private void doSetPfkeyAction(Element element, CEntityResourceForm form)
	{
		String key = element.getAttribute("key");
		String action = element.getAttribute("action");
		if (key != null && !key.equals("") && action != null && !action.equals(""))
		{
			form.setPFAction(key, action);
		}
	}
	/**
	 * @param element
	 * @param form
	 */
	private void doSetDefaultCursor(Element element, CEntityResourceForm form)
	{
		String field = element.getAttribute("name") ;
		form.SetDefaultCursor(field) ;
	}
	/**
	 * @param element
	 * @param form
	 */
	private void doSetReplayMutable(Element element, CEntityResourceForm form)
	{
		String field = element.getAttribute("field");
		if (field != null && !field.equals(""))
		{
			form.setEditReplayMutable(field) ;
		}
	}
	/**
	 * @param element
	 * @param form
	 */
	private void doSetActiveChoice(Element element, CEntityResourceForm form)
	{
		String field = element.getAttribute("field") ;
		String value = element.getAttribute("value") ;
		String editvalue = element.getAttribute("editvalue") ;
		String target = element.getAttribute("fieldTarget");
		String submit = element.getAttribute("submit");
		if (field!= null && !field.equals(""))
		{
			boolean bSubmit = false ;
			if (submit != null && submit.equals("true"))
			{
				bSubmit = true ;
			}
			if (target!=null && !target.equals(""))
			{
				if (value != null && !value.equals(""))
				{
					form.setActiveChoice(field, value, target, bSubmit) ;
				}
				else if (editvalue != null && !editvalue.equals(""))
				{
					form.setLinkedActiveChoice(field, editvalue, target, bSubmit) ;
				}
			}
		}
		
	}
	/**
	 * @param element
	 * @param form
	 */
	private void doSetTitle(Element element, CEntityResourceForm form)
	{
		String name = element.getAttribute("label") ;
		if (name != null && !name.equals(""))
		{
			form.setTitle(name) ;
		}
	}
	/**
	 * @param element
	 * @param form
	 */
	private void doNameLabel(Element element, CEntityResourceForm form)
	{
		String col = element.getAttribute("col") ;
		String line = element.getAttribute("line") ;
		String name = element.getAttribute("name") ;
		if (col != null && !col.equals(""))
		{
			if (line != null && !line.equals(""))
			{
				if (name != null && !name.equals(""))
				{
					int c = Integer.parseInt(col);
					int l = Integer.parseInt(line) ;
					form.setNameLabel(c, l, name) ;
				}
			}
		}
	}	
	/**
	 * @param element
	 * @param form
	 */
	private void doSetDevelopable(Element element, CEntityResourceForm form)
	{
		String field = element.getAttribute("field") ;
		String flag = element.getAttribute("flag") ;
		if (field != null && !field.equals(""))
		{
			if (flag != null && !flag.equals(""))
			{
				form.setDevelopable(field, flag) ;
			}
		}
	}
	/**
	 * @param element
	 * @param form
	 */
	private void doSetFormat(Element element, CEntityResourceForm form)
	{
		String field = element.getAttribute("field") ;
		String format = element.getAttribute("format") ;
		if (field != null && !field.equals(""))
		{
			if (format != null && !format.equals(""))
			{
				form.setFormat(field, format) ;
			}
		}
	}
	/**
	 * @param element
	 * @param form
	 */
	private void doSetCheckBox(Element element, CEntityResourceForm form)
	{
		String name = element.getAttribute("field") ;
		String valueOff = element.getAttribute("cleanValue") ;
		String valueOn = element.getAttribute("settedValue") ;
		if (name != null && !name.equals(""))
		{
			if (valueOff != null && !valueOff.equals(""))
			{
				if (valueOn != null && !valueOn.equals(""))
				{
					form.setCheckBox(name, valueOn, valueOff) ;
				}
			}
		}
	}
	/**
	 * @param element
	 * @param form
	 */
	private void doRenameField(Element element, CEntityResourceForm form)
	{
		String from = element.getAttribute("from") ;
		String to = element.getAttribute("to") ;
		if (from != null && !from.equals(""))
		{
			if (to != null && !to.equals(""))
			{
				form.RenameField(from, to) ;
			}
		}
	}
	/**
	 * @param string
	 * @return
	 */
	private class TransformFileNameFilter implements FilenameFilter
	{
		protected String m_csName = "" ; 
		public TransformFileNameFilter(String name)
		{
			m_csName = name ;
		}
		public boolean accept(File dir, String name)
		{
			return name.toUpperCase().startsWith(m_csName.toUpperCase() + ".");
		}
	}
	
	private Document FindTransformation(String name)
	{
		File dir = new File(m_csTransformDir);
		FilenameFilter filter = new TransformFileNameFilter(name) ;
		File[] lst = dir.listFiles(filter) ;
		if (lst != null && lst.length>0)
		{
			File trans = lst[0] ;
			return LoadXML(trans) ;
		} 
		return null;
	}

	private Document LoadXML(File f)
	{
		try
		{
			Source file = new StreamSource(f) ;
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument() ;
			Result res = new DOMResult(doc) ;
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(file, res);
			return doc ;
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace() ;
			return null ;
		}
		catch (TransformerConfigurationException e)
		{
			e.printStackTrace() ;
			return null ;
		}
		catch (TransformerException e)
		{
			e.printStackTrace() ;
			return null ;
		}
	}
}
