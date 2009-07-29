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
package jlib.xml;

/*
 * Created on 26 mai 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import jlib.misc.NumberParser;
import jlib.misc.StringUtil;

import org.w3c.dom.CDATASection;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;


/**
 * Class to load and hold Xml documents.
 * Once loaded (see methods {@link #load} and {@link #loadFromString}), the document
 * can be explored through different methods:
 * <ul>
 * 	<li>{@link #getChild}, {@link #getChilds}, {@link #getEnumChild}, among others, allow
 * 	to descend in the document tree.</li>
 * 	<li>{@link #getParent} method allows to navigate towards the root element
 * 	of the document tree.</li>
 * 	<li>{@link #addTag}, {@link #addChild}, ... allows to add new elements to 
 * 	the document.</li>
 * 	<li>Different overloads of {@link #addVal} allows to add new attributes to
 * 	the elements of the document.
 * </ul>
 * 
 * After the document has been edited, it can be saved with the methods
 * {@link #exportToFile}, {@link #exportToFileUTF8}, {@link #exportToStream} and
 * {@link #exportToString}.<p/>
 * 
 * This class is typically used for loading the JLib.log configuration files, which
 * are xml files.
 * 
 * @author PJD
 */
public class Tag
{
	public Tag()
	{
		m_doc = CreateDocument();
	}
	
	public Tag(Document doc)
	{
		setDoc(doc);
	}
	
	public Tag(String name)
	{
		m_doc = CreateDocument();
		m_elem = m_doc.createElement(name) ;
		m_doc.appendChild(m_elem) ;
	}
	
	public void setDoc(Document doc)
	{
		m_doc = doc;
		m_elem = m_doc.getDocumentElement();
	}
	
	public Document getDoc()
	{
		return m_doc;
	}
	
	public Element getElement()
	{
		return m_elem;
	}
			
	private Tag(Document doc, Element elem)
	{
		m_doc = doc;
		m_elem = elem;
	}
	
	private Tag(Tag tag)
	{
		m_doc = tag.m_doc;
		m_elem = tag.m_elem;
	}	
	
	private Tag(Tag tagParent, String csTagName)
	{
		m_doc = tagParent.m_doc;
				
		m_elem = m_doc.createElement(csTagName);
		if(tagParent.m_elem != null)
			tagParent.m_elem.appendChild(m_elem);
		else
			m_doc.appendChild(m_elem);
	}
	
	private Document CreateDocument()
	{
		try
		{
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument() ;
		}
		catch (ParserConfigurationException e)
		{
			LogTagError.log(e);
			e.printStackTrace();
		}
		catch (FactoryConfigurationError e)
		{
			e.printStackTrace();
		}
		return null ;
	}
	
	public Tag addTag(String csTagName)
	{
		Tag tag = new Tag(this, csTagName);
		return tag;
	}
	
	public void addTextTag(String csTagName, String csText)
	{
		Tag tag = new Tag(this, csTagName);
		tag.setText(csText);
	}
	
	public boolean addVal(String csArgName, String csValue)
	{
		if(csValue == null)
			csValue = "";
		try
		{
			m_elem.setAttribute(csArgName, csValue);
		}
		catch (DOMException e)
		{
			LogTagError.log(e);
			return false;
		}
		return true;
	}
	
	public boolean addVal(String csArgName, Date dateValue)
	{
		String csValue = "";
		if(dateValue != null)
			csValue = String.valueOf(dateValue.getTime());
		try
		{
			m_elem.setAttribute(csArgName, csValue);
		}
		catch (DOMException e)
		{
			LogTagError.log(e);
			return false;
		}
		return true;
	}
	
	public boolean addVal(String csArgName, int nValue)
	{
		try
		{
			m_elem.setAttribute(csArgName, String.valueOf(nValue));
		}
		catch (DOMException e)
		{
			LogTagError.log(e);
			return false;
		}
		return true;
	}
	
	public boolean addVal(String csArgName, double dValue)
	{
		try
		{
			m_elem.setAttribute(csArgName, String.valueOf(dValue));
		}
		catch (DOMException e)
		{
			LogTagError.log(e);
			return false;
		}
		return true;
	}
	
	public boolean addVal(String csArgName, long lValue)
	{
		try
		{
			m_elem.setAttribute(csArgName, String.valueOf(lValue));
		}
		catch (DOMException e)
		{
			LogTagError.log(e);
			return false;
		}
		return true;
	}

	public boolean addVal(String csArgName, boolean bValue)
	{
		try
		{
			m_elem.setAttribute(csArgName, String.valueOf(bValue));
		}
		catch (DOMException e)
		{
			LogTagError.log(e);
			return false;
		}
		return true;
	}
	
	public boolean removeVal(String csArgName)
	{
		try
		{
			m_elem.removeAttribute(csArgName);
		}
		catch (DOMException e)
		{
			LogTagError.log(e);
			return false;
		}
		return true;
	}
	
	public String getVal(String csArgName)
	{
		if(m_elem != null)
			return m_elem.getAttribute(csArgName);
		return null;
	}
	
	public String getVal(String csArgName, String csDefaultValue)
	{
		if(m_elem != null)
		{
			String cs = m_elem.getAttribute(csArgName);
			if(!StringUtil.isEmpty(cs))
				return cs;
		}
		return csDefaultValue;
	}
	
	public void updateVal(String csArgName, String csValue)
	{
		if(m_elem != null)
		{
			if(m_elem.getAttribute(csArgName) != null)
				removeVal(csArgName);
			addVal(csArgName, csValue);
		}
	}
	
	public String getNodeVal()
	{
		if(m_elem != null)
			return m_elem.getFirstChild().getNodeValue() ;
		return null;
	}
	
	public int getValAsInt(String csArgName)
	{
		if(m_elem != null)
		{
			String cs = m_elem.getAttribute(csArgName);
			return NumberParser.getAsInt(cs);
		}
		return 0;
	}
	
	public int getValAsInt(String csArgName, int nDefaultValue)
	{
		if(m_elem != null)
		{
			String cs = m_elem.getAttribute(csArgName);
			if(StringUtil.isEmpty(cs))
				return nDefaultValue;
			return NumberParser.getAsInt(cs);
		}
		return 0;	// Bug: Should be nDefaultValue
	}
	
	public long getValAsLong(String csArgName)
	{
		if(m_elem != null)
		{
			String cs = m_elem.getAttribute(csArgName);
			return NumberParser.getAsLong(cs);
		}
		return 0L;
	}
	
	public Date getValAsDate(String csArgName)
	{
		if(m_elem != null)
		{
			String cs = m_elem.getAttribute(csArgName);
			long lTime = NumberParser.getAsLong(cs);
			Date date = new Date(lTime);
			return date;
		}
		return null;
	}
	
	public double getValAsDouble(String csArgName)
	{
		if(m_elem != null)
		{
			String cs = m_elem.getAttribute(csArgName);
			return NumberParser.getAsDouble(cs);
		}
		return 0.0;
	}
	
	
	public boolean getValAsBoolean(String csArgName)
	{
		if(m_elem != null)
		{
			String cs = m_elem.getAttribute(csArgName);
			return NumberParser.getAsBoolean(cs);
		}
		return false;
	}
	
	public boolean getValAsBoolean(String csArgName, boolean bDefaultValue)
	{
		if(m_elem != null)
		{
			String cs = m_elem.getAttribute(csArgName);
			if(!StringUtil.isEmpty(cs))
				return NumberParser.getAsBoolean(cs);
		}
		return bDefaultValue;
	}
	
	public boolean isValExisting(String csArgName)
	{
		if(m_elem != null)
		{
			boolean b = m_elem.hasAttribute(csArgName);
			return b ;
		}
		return false;
	}
	
	
	// Reading
	public String getName()
	{
		return m_elem.getTagName();
	}
	
	public boolean isNamed(String csNameSearched)
	{
		String cs = m_elem.getTagName();
		if(cs.equalsIgnoreCase(csNameSearched))
			return true;
		return false;
	}
	
	public Tag getRootTag()
	{
		Tag tagRoot = new Tag(this);
		tagRoot.m_elem = tagRoot.m_doc.getDocumentElement();
		return tagRoot;
	}
	
	public Tag getParent()
	{
		Element eParent = (Element)m_elem.getParentNode();
		if(eParent != null)
		{
			Tag tagParent = new Tag(m_doc, eParent);
			return tagParent;
		}
		return null;
	}
	
	public Tag getFirstChildConstrainedTagAttribute(String csTagName, String csAttributName, String csAttributeValue)
	{
		TagCursor curTag = new TagCursor();
		Tag currentChild = getFirstChild(curTag, csTagName);
		while(currentChild != null)
		{
			if(currentChild.hasConstrainedAttribute(csAttributName, csAttributeValue))
				return currentChild;
			currentChild = getNextChild(curTag);
		}
		return null;
	}
	
	public boolean hasConstrainedAttribute(String csAttributName, String csAttributeValueToSearch)
	{
		String csVal = getVal(csAttributName);
		if(csVal != null)
			if(csAttributeValueToSearch.equals(csVal))
				return true;
		return false;
	}
	
	public Tag getFirstChildConstrainedTagAttributeNoCase(String csTagName, String csAttributName, String csAttributeValue)
	{
		TagCursor curTag = new TagCursor();
		Tag currentChild = getFirstChild(curTag, csTagName);
		while(currentChild != null)
		{
			if(currentChild.hasConstrainedAttributeNoCase(csAttributName, csAttributeValue))
				return currentChild;
			currentChild = getNextChild(curTag);
		}
		return null;
	}
	
	public boolean hasConstrainedAttributeNoCase(String csAttributName, String csAttributeValueToSearch)
	{
		String csVal = getVal(csAttributName);
		if(csVal != null)
			if(csAttributeValueToSearch.equalsIgnoreCase(csVal))
				return true;
		return false;
	}

	// csName can contain tag names separeted by '/'
	public Tag getChild(String csName)
	{
		int nSep = csName.indexOf('/');
		if(nSep != -1)
		{
			String csChunk = csName.substring(0, nSep);
			csName = csName.substring(nSep+1);
			Tag tagChild = getChild(csChunk);
			if(tagChild != null)
			{
				return tagChild.getChild(csName);
			}
		}
		Node node = m_elem.getFirstChild();
		while(node != null)
		{
			String cs = node.getNodeName();
			if(cs.equals(csName))
			{
				if(node.getNodeType() == Node.ELEMENT_NODE)
				{
					Tag tagChild = new Tag(m_doc, (Element)node);
					return tagChild;
				}
			}
			node = node.getNextSibling();
		}
		return null;
	}

	public Tag getFirstChild(TagCursor cur)
	{		
		return getFirstChild(cur, null);
	}
	
	public Tag getEnumChild()
	{		
		return getEnumChild(null);
	}
	
	public Tag getEnumChild(String csName)
	{		
		if(m_curTag == null)
		{
			m_curTag = new TagCursor();
			Tag currentChild = getFirstChild(m_curTag, csName);		
			if(currentChild == null)
				m_curTag = null;
			return currentChild; 
		}
		else
		{
			Tag currentChild = getNextChild(m_curTag);
			if(currentChild == null)
				m_curTag = null;
			return currentChild; 
		}
	}
	
	public Tag GetCurrentChild()
	{
		if(m_curTag != null)
			return m_curTag.getCurrentTag();
		return null;
	}
	
	public boolean has1TextChildren()
	{
		int nNbTextNode = 0;
		int nNbNodeNotText = 0;
		Node node = m_elem.getFirstChild();
		while(node != null)
		{
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				nNbNodeNotText++;
				//break;
			}			
			else if(node.getNodeType() == Node.CDATA_SECTION_NODE)
			{
				nNbNodeNotText++;
				//break;
			}
			else if(node.getNodeType() == Node.TEXT_NODE)
			{
				nNbTextNode++;
				//break;
			}	
			node = node.getNextSibling();
		}
		if(nNbNodeNotText == 0 && nNbNodeNotText == 0 && nNbTextNode == 1)
			return true;
		return false;
	}
	
	public Tag getFirstChild(TagCursor cur, String csName)
	{		
		cur.setNameEnumeration(csName);
		Node node = m_elem.getFirstChild();
		while(node != null)
		{
			if(csName != null)	// Must check name
			{
				String cs = node.getNodeName();
				if(!cs.equals(csName))
				{
					node = node.getNextSibling();
					continue;
				}
			}

			// No name restiction or name is correct
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element eChild = (Element)node;
				if(eChild != null)
				{
					Tag tagChild = new Tag(m_doc, eChild);
					if(cur != null)
						cur.setCurrentTag(tagChild);
					return tagChild;
				}
			}
	
			node = node.getNextSibling();
		}
		if(cur != null)
			cur.setInvalid();
		return null;
	}
	
	public Tag getNextChild(TagCursor cur)
	{
		String csName = cur.getNameEnumeration();
		Tag tagChild = cur.getCurrentTag();
		if(tagChild == null)
			return null;
		Node node = tagChild.m_elem.getNextSibling();
		while(node != null)
		{
			if(csName != null)
			{
				String cs = node.getNodeName();
				if(!cs.equals(csName))
				{
					node = node.getNextSibling();
					continue;
				}
			}
			// No name or it matches 
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element eNextChild = (Element)node;		
				if(eNextChild != null)
				{
					Tag tagNextChild = new Tag(tagChild.m_doc, eNextChild);
					cur.setCurrentTag(tagNextChild);
					return tagNextChild;
				}
			}
			node = node.getNextSibling();
		}
		cur.setInvalid();
		return null;
	}
	
	public Tag removeChild(String csName)
	{
		return removeChild(csName, null, null);
	}
	
//	public void replaceChild(Tag tagOld, Tag tagNew)
//	{
//		Tag tagNewCurrentDoc = new Tag(m_doc, tagNew.m_elem);
//		m_elem.replaceChild(tagNewCurrentDoc.m_elem, tagOld.m_elem);
//	}
	
	public void removeAllChildren()
	{
		ArrayList<Tag> arr = new ArrayList<Tag>(); 
		TagCursor cur = new TagCursor();
		Tag tag = getFirstChild(cur);
		while(tag != null)
		{
			arr.add(tag);
			tag = getNextChild(cur);
		}
		boolean b = true;
		for(int n=0; b && n<arr.size(); n++)
		{
			tag = arr.get(n);
			removeChild(tag);
		}
		arr.clear();
	}
	
	/**
	 * Remove first Tag that matches conditions.
	 * @param csName Name of Tag to remove.
	 * @param csTagValName Name of a Tag value, condition to be satisfy to remove the element. 
	 * @param csTagValValue Value of Tag value, condition to be satisfy to remove the element.
	 * @return Removed Tag.
	 */
	public Tag removeChild(String csName, String csTagValName, String csTagValValue)
	{
		/*int nSep = csName.indexOf('/');
		if(nSep != -1)
		{
			String csChunk = csName.substring(0, nSep);
			csName = csName.substring(nSep+1);
			Tag tagChild = getChild(csChunk);
			if(tagChild != null)
			{
				return tagChild.getChild(csName);
			}
		}*/
		Node node = m_elem.getFirstChild();
		while(node != null)
		{
			String cs = node.getNodeName();
			if(cs.equals(csName))
			{
				if(node.getNodeType() == Node.ELEMENT_NODE)
				{
					Tag tagChild = new Tag(m_doc, (Element)node);
					boolean bMatchesCondition = false;
					
					if (csTagValName == null || csTagValName.equals(""))
					{
						bMatchesCondition = true;
					}
					else if (tagChild.getVal(csTagValName) != null)
					{
						if (tagChild.getVal(csTagValName).equals(csTagValValue))
						{
							bMatchesCondition = true;
						}
					}
					
					if (bMatchesCondition)
					{
						// Clean CR placed after node if any
						if (node.getNextSibling() != null && node.getNextSibling().getNodeType() == Node.TEXT_NODE && node.getNextSibling().getNodeValue().contains("\n"))
						{
							node.getParentNode().removeChild(node.getNextSibling());
						}
						
						node.getParentNode().removeChild(node);
						return tagChild;
					}
					
				}
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	public void removeChild(Tag tagchild)
	{
		if(tagchild != null)
		{
			m_elem.removeChild(tagchild.m_elem);
		}
	}
	
	public String getFirstVal(ValCursor cur)
	{
		NamedNodeMap nodeMap = m_elem.getAttributes();
		cur.setEnumVal(nodeMap);
		return cur.getFirstVal();
	}

	public String getNextVal(ValCursor cur)
	{
		return cur.getNextVal();
	}

	public Node getFirstParam(ValCursor cur)
	{
		NamedNodeMap nodeMap = m_elem.getAttributes();
		cur.setEnumVal(nodeMap);
		return cur.getFirstParam();
	}

	public Node getNextParam(ValCursor cur)
	{
		return cur.getNextParam();
	}
	
	public void normalize()
	{
		m_doc.normalizeDocument();
	}
	
	public String getFirstNamedVal(ValCursor cur, String csParameterName)
	{
		Node node = getFirstParam(cur);
		while(node != null)
		{
			if(node.getNodeName().equalsIgnoreCase(csParameterName))
				return node.getNodeValue();
			node = getNextParam(cur);
		}
		return null;
	}
	
	public String getNextNamedVal(ValCursor cur, String csParameterName)
	{
		Node node = getNextParam(cur);
		while(node != null)
		{
			if(node.getNodeName().equalsIgnoreCase(csParameterName))
				return node.getNodeValue();
			node = getNextParam(cur);
		}
		return null;
	}

	// Import / Export
	public boolean exportToFile(String csFilename)
	{
		try
		{
			FileOutputStream file = new FileOutputStream(csFilename);
			StreamResult res = new StreamResult(file) ;
			return exportToStream(res, "ISO-8859-1");
		}
		catch (FileNotFoundException e)
		{
			LogTagError.log(e);
			return false ;
		}
	}
	
	public boolean exportUTF8(OutputStream os)
	{
		BufferedOutputStream bos = new BufferedOutputStream(os);
		StreamResult result = new StreamResult(bos);
		return exportToStream(result, "UTF8");
	}
	
	public boolean exportToFileUTF8(String csFilename)
	{
		try
		{
			FileOutputStream file = new FileOutputStream(csFilename);
			StreamResult res = new StreamResult(file) ;
			return exportToStream(res, "UTF-8");
		}
		catch (FileNotFoundException e)
		{
			LogTagError.log(e);
			return false ;
		}
	}
		
	public String exportToString()
	{
		StringWriter sw = new StringWriter(); 
		StreamResult res = new StreamResult(sw);
		boolean b = exportToStream(res, "ISO8859-1");
		if(b)
			return sw.toString();
		return null;
	}
	
	public String toString()
	{
		StringWriter sw = new StringWriter(); 
		StreamResult res = new StreamResult(sw);
		boolean b = exportToStream(res, "ISO8859-1");
		if(b)
			return sw.toString();
		return "";
	}
			
//	private boolean exportToStream(StreamResult res)
//	{
//		if (m_doc != null)
//		{
//			try
//			{
//				Source source = new DOMSource(m_elem);
//				
//				Transformer xformer = TransformerFactory.newInstance().newTransformer();
//				xformer.setOutputProperty(OutputKeys.ENCODING, "ISO8859-1");
//				//xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "false");
//				xformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			
//				xformer.transform(source, res);
//				return true ;
//			}
//			catch (TransformerConfigurationException e)
//			{
//				LogTagError.log(e);
//				return false ;
//			}
//			catch (TransformerException e)
//			{
//				LogTagError.log(e);
//				return false ;
//			}
//		}
//		return false;
//	}
	
	public String exportIndented()
	{
		 try 
		 {
	        OutputFormat format = new OutputFormat(m_doc);
	        format.setIndenting(true);
	        format.setIndent(4);
	        StringWriter sw = new StringWriter();
	        Writer output = new BufferedWriter(sw);
	        XMLSerializer serializer = new XMLSerializer(output, format);
	        serializer.serialize(m_doc);
	        
	        String cs = sw.getBuffer().toString();
	        return cs;
		 } 
		 catch (Exception e) 
		 { 
			 return null; 
		 }
	}
    
	public boolean exportIndentedUtf8(String csFileName)
	{
		 try 
		 {
	        OutputFormat format = new OutputFormat(m_doc);
	        format.setIndenting(true);
	        format.setIndent(4);
	        FileOutputStream out = new FileOutputStream(csFileName);
	        Writer output = new BufferedWriter(new OutputStreamWriter(out, "utf-8"));
	        //Writer output = new BufferedWriter( new FileWriter(csFileName) );
	        XMLSerializer serializer = new XMLSerializer(output, format);
	        serializer.serialize(m_doc);
		 } 
		 catch (Exception e) 
		 { 
			 return false; 
		 }
		 return true;
	}
	
	private boolean exportToStream(StreamResult res, String csEncoding)
	{
		if (m_doc != null)
		{
			try
			{
				Source source = new DOMSource(m_elem);
				
				Transformer xformer = TransformerFactory.newInstance().newTransformer();
				xformer.setOutputProperty(OutputKeys.ENCODING, csEncoding);
				xformer.setOutputProperty(OutputKeys.INDENT, "yes");
				//xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
				xformer.transform(source, res);
				return true ;
			}
			catch (TransformerConfigurationException e)
			{
				LogTagError.log(e);
				return false ;
			}
			catch (TransformerException e)
			{
				LogTagError.log(e);
				return false ;
			}
		}
		return false;
	}
	
	public static Tag createFromFile(String csFilePath)
	{
		Tag tag = new Tag();
		boolean b = tag.load(csFilePath);
		if(b)
			return tag;
		return null;
	}
	
	public static Tag createFromFile(String csFilePath, ArrayList<String> arrIncludePath)
	{
		Tag tag = new Tag();
		File f = new File(csFilePath) ;
		boolean b = false ;
		if (f.exists() && f.isFile())
		{
			b = tag.load(f);
		}
		if(!b && arrIncludePath != null)
		{
			int nNbPath = arrIncludePath.size();
			for(int n=0; n<nNbPath && !b; n++)
			{
				String csPath = arrIncludePath.get(n);
				String csFullPath = csPath + csFilePath;
				f = new File(csFullPath) ;
				if (f.exists() && f.isFile())
				{
					b = tag.load(f);
				}
			}
		}
			
		if(b)
			return tag;
		return null;
	}

	public static Tag createFromStream(InputStream is)
	{
		Tag tag = new Tag();
		boolean b = tag.load(is);
		if(b)
			return tag;
		return null;
	}
	
	public static Tag createFromString(String cs)
	{
		Tag tag = new Tag();
		boolean b = tag.loadFromString(cs);
		if(b)
			return tag;
		return null;
	}
	
	public static Tag createFromFile(File f)
	{
		Tag tag = new Tag();
		boolean b = tag.load(f);
		if(b)
			return tag;
		return null;
	}

	
	public boolean load(String csFilePath)
	{
		if (StringUtil.isEmpty(csFilePath))
			return false;
		File file = new File(csFilePath);
		return load(file);
	}

	public boolean load(File f)
	{
		try
		{
			if (!f.exists())
				return false;
			FileInputStream fis=new FileInputStream(f);
			return load(fis);
		}
		catch (Exception e)
		{
			LogTagError.log(e);
			return false;
		}
	} 
	
	public boolean load(InputStream s)
	{
		try
		{
			Source file = new StreamSource(s) ;
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument() ;
			Result res = new DOMResult(doc) ;
			TransformerFactory tr = TransformerFactory.newInstance();
			Transformer xformer = tr.newTransformer();
			xformer.transform(file, res);
			m_doc = doc;
			m_elem = m_doc.getDocumentElement();			
			return true;
		}
		catch (Exception e)
		{
			LogTagError.log(e);
			return false;
		}		
	}
	
	public boolean loadFromString(String cs)
	{
		try
		{
			StringReader stringReader = new StringReader(cs);
			InputSource inputSource = new InputSource(stringReader);
			m_doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource); 
			m_elem = m_doc.getDocumentElement();			
			return true;
		}
		catch (Exception e)
		{
			LogTagError.log(e);
			return false;
		}
	}
	
	private Document m_doc = null;
	private Element m_elem = null;
	private TagCursor m_curTag = null;
	//private Tag m_currentChild = null;
	/**
	 * @param string The child name.
	 * @return The text contained in the child.
	 */
	public String getChildText(String string)
	{
		Tag t = getChild(string) ;
		if (t != null)
		{
			String cs = t.getText() ;
			return cs ;
		}
		return "" ;
	}
	
	public int getChildTextAsInt(String string)
	{
		String cs = getChildText(string);
		return NumberParser.getAsInt(cs);
	}
	
	public double getChildTextAsDouble(String string)
	{
		String cs = getChildText(string);
		return NumberParser.getAsDouble(cs);
	}
	
	public boolean getChildTextAsBoolean(String string)
	{
		String cs = getChildText(string);
		return NumberParser.getAsBoolean(cs);
	}

	public String getText()
	{
		return m_elem.getTextContent() ;
	}

	public Document getEmbeddedDocument()
	{
		return m_doc ;
	}

	public void addChild(Tag tagForm)
	{
		Element el = tagForm.m_elem ;
		if (el != null)
		{
			if (tagForm.m_doc != m_doc)
			{
				el = (Element)m_doc.importNode(el, true) ;
			}
			if (m_elem != null)
			{
				m_elem.appendChild(el) ;
			}
			else
			{
				m_doc.appendChild(el) ;
			}
		}
	}
	public Tag getCopy()
	{
		if (m_elem == null || m_doc == null)
		{
			return null ;
		}
//		Element e = m_doc.createElement(m_elem.getNodeName()) ;
//		NamedNodeMap map = e.getAttributes() ;
//		int nb = map.getLength() ;
//		for (int i=0; i<nb; i++)
//		{
//			Node node = map.item(i) ;
//			Attr att = (Attr)node ;
//			String name = att.getNodeName() ;
//			String val = att.getValue() ;
//			e.setAttribute(name, val) ;
//		}
		Element e = (Element)m_elem.cloneNode(true) ;
		Tag ntag = new Tag(m_doc, e) ;
		return ntag ;
	}
	public void setName(String string)
	{
		m_doc.renameNode(m_elem, "", string) ;
	}
	
	public void setText(String csText)
	{
		m_elem.setTextContent(csText);
	}

	public void exportToFile(File f)
	{
		exportToFile(f.getAbsolutePath()) ;
	}

	public Vector<Tag> getChilds(String tagName)
	{
		Vector<Tag> arr = new Vector<Tag>() ;
		TagCursor cur = new TagCursor() ;
		Tag tag = getFirstChild(cur, tagName) ;
		while (tag != null)
		{
			arr.add(tag) ;
			tag = getNextChild(cur) ;
		}
		return arr ;
	}
	
	public void addCData(String csCData)
	{
		CDATASection cdata = m_doc.createCDATASection(csCData);
		m_elem.appendChild(cdata);
	}
	
	public void addCData(int nData)
	{
		String csCData = String.valueOf(nData); 
		addCData(csCData);		
	}
	
	public void addCData(long lData)
	{
		String csCData = String.valueOf(lData); 
		addCData(csCData);
	}

	public void addCData(short sData)
	{
		String csCData = String.valueOf(sData); 
		addCData(csCData);
	}
	
	public String getCData()
	{
		return getNodeVal();
	}
	
	public int getNbAttributesWithValue(String csParameterName, String csOptionalPrefixed)
	{
		int n  = 0;
		String csName = getName();
		ValCursor cur = new ValCursor(); 
		String csValue = getFirstNamedVal(cur, csParameterName);
		while(csValue != null)
		{
			ArrayList<String> arr = StringUtil.extractPrefixedKeywords(csValue, csOptionalPrefixed);
			if(arr != null)
				n += arr.size();
			csValue = getNextNamedVal(cur,  csParameterName);
		}
		return n; 
	}
	
	public ArrayList<String> getAttributesWithValue(String csParameterName, String csOptionalPrefixed)
	{
		ArrayList<String> arrs = null; 
		int n  = 0;
		String csName = getName();
		ValCursor cur = new ValCursor(); 
		String csValue = getFirstNamedVal(cur, csParameterName);
		while(csValue != null)
		{
			ArrayList<String> arr = StringUtil.extractPrefixedKeywords(csValue, csOptionalPrefixed);
			if(arr != null)
			{
				if(arrs == null)
					arrs = new ArrayList<String>();
				arrs.addAll(arr);
			}
			csValue = getNextNamedVal(cur,  csParameterName);
		}
		return arrs; 
	}
	
	public String getFirstAttributesWithValue(String csParameterName, String csOptionalPrefixed)
	{
		int n  = 0;
		String csName = getName();
		ValCursor cur = new ValCursor(); 
		String csValue = getFirstNamedVal(cur, csParameterName);
		while(csValue != null)
		{
			ArrayList<String> arr = StringUtil.extractPrefixedKeywords(csValue, csOptionalPrefixed);
			if(arr != null)
			{
				return arr.get(0);
			}
			csValue = getNextNamedVal(cur,  csParameterName);
		}
		return null; 
	}
	
	public ArrayList<String> getAttributesWithValueOptionalBrackets(String csParameterName, String csOptionalPrefixed)
	{
		ArrayList<String> arrs = null; 
		int n  = 0;
		String csName = getName();
		ValCursor cur = new ValCursor(); 
		String csValue = getFirstNamedVal(cur, csParameterName);
		while(csValue != null)
		{
			ArrayList<String> arr = StringUtil.extractPrefixedKeywordsBracketed(csValue, csOptionalPrefixed);
			if(arr != null)
			{
				if(arrs == null)
					arrs = new ArrayList<String>();
				arrs.addAll(arr);
			}
			csValue = getNextNamedVal(cur,  csParameterName);
		}
		return arrs; 
	}

}
