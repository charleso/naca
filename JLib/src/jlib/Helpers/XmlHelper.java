/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.Helpers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import jlib.exception.ProgrammingException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * Class providing some very commonly used operations for processing Xml information.
 * It also allows to hide some implementation dependent features (as loading (parse) documents,
 * saving (serialize) documents, searching (xpath) into DOM trees) from
 * the callee functions.
 * This class is currently adapted for the SAXON 8.XX library and the Xerces parser. So
 * the packages xercesImpl.jar, saxon8.jar and saxon8-xpath.jar must be present in
 * the classpath.  
 * @author jmgonet
 */
public class XmlHelper {

//*****************************************************************************
//**           Looks for a single node by using a XPath expression           **
//*****************************************************************************
	private static XPathFactory _xPathFactory;
	private static XPathFactory getXPathFactory() throws XPathFactoryConfigurationException {
		if (_xPathFactory==null) {
			String magicValue=System.getProperty("javax.xml.xpath.XPathFactory:http://java.sun.com/jaxp/xpath/dom");
	    	System.setProperty("javax.xml.xpath.XPathFactory:http://java.sun.com/jaxp/xpath/dom","net.sf.saxon.xpath.XPathFactoryImpl");
//	    	System.setProperty("javax.xml.xpath.XPathFactory:http://java.sun.com/jaxp/xpath/dom","org.apache.xpath.jaxp.XPathFactoryImpl");
//	    	System.setProperty("jaxp.debug","yes");
	    	_xPathFactory=XPathFactory.newInstance(XPathConstants.DOM_OBJECT_MODEL);
	    	if (magicValue==null)
	    		System.clearProperty("javax.xml.xpath.XPathFactory:http://java.sun.com/jaxp/xpath/dom");
	    	else
	    		System.setProperty("javax.xml.xpath.XPathFactory:http://java.sun.com/jaxp/xpath/dom",magicValue);
		}
		return _xPathFactory;
	}
	
/**
 * Looks for a single node by using a XPath expression.
 * @param parent The context for the XPath expression.
 * @param expression A XPath expression. As this method returns a {@link Node}
 * object, be sure to specify XPath expression selecting nodes (not text nodes,
 * for example).
 * @return The first node matching the XPath expression in the specified context.
 * Can return null if there are no matching nodes.
 */
	public static Node SelectSingleNode(Node parent,String expression) {
		return SelectSingleNode(parent,expression,false);
	}
/**
 * Looks for a single node by using a XPath expression.
 * @param parent The context for the XPath expression.
 * @param expression A XPath expression. As this method returns a {@link Node}
 * object, be sure to specify XPath expression selecting nodes (not text nodes,
 * for example). If you're looking for nodes in the default namespace, prefix the node
 * names with ':'.
 * @param defaultNamespaceAware If <i>true</i>, the method will use a namespace
 * context provider ({@link DefaultNamespaceContextProvider}) to handle default
 * namespace. Don't use this feature when not necessary, as it slows down the
 * setup of the {@link XPath} operation.
 * @return The first node matching the XPath expression in the specified context.
 * Can return null if there are no matching nodes.
 */
    public static Node SelectSingleNode(Node parent,String expression,boolean defaultNamespaceAware) {
    	try {
	    	XPath xpath=getXPathFactory().newXPath();
	    	if (defaultNamespaceAware) {
		    	NamespaceContext nsc=new DefaultNamespaceContextProvider(parent);
		    	xpath.setNamespaceContext(nsc);
	    	}
	    	Node node=(Node)xpath.evaluate(expression,parent,XPathConstants.NODE);
	   		return node;
    	}
    	catch (XPathFactoryConfigurationException e) {
    		throw new ProgrammingException("XMLHELPER","Error creating new XPath Factory: "+e.getMessage(),e);
    	}
    	catch (XPathExpressionException e) {
    		throw new ProgrammingException("XMLHELPER","Expression error: '"+expression+"': "+e.getMessage(),e);
    	}
    }

//*****************************************************************************
//**          Returns the list of nodes matching an XPath expression.        **
//*****************************************************************************
/**
 * Returns the list of nodes matching an XPath expression.
 * @param parent The context for the XPath expression.
 * @param expression A XPath expression. As this method returns a {@link NodeList}
 * object, be sure to specify XPath expression selecting nodes (not text nodes,
 * for example).
 * @return The list of nodes matching the XPath expression in the specified context.
 * Can return null if there are no matching nodes.
 */
    public static NodeList SelectNodes(Node parent,String expression) {
    	return SelectNodes(parent,expression,false);
    }
 
/**
 * Returns the list of nodes matching an XPath expression.
 * @param parent The context for the XPath expression.
 * @param expression A XPath expression. As this method returns a {@link NodeList}
 * object, be sure to specify XPath expression selecting nodes (not text nodes,
 * for example). If you're looking for nodes in the default namespace, prefix the node
 * names with ':'.
 * @param defaultNamespaceAware If <i>true</i>, the method will use a namespace
 * context provider ({@link DefaultNamespaceContextProvider}) to handle default
 * namespace. Don't use this feature when not necessary, as it slows down the
 * setup of the {@link XPath} operation.
 * @return The list of nodes matching the XPath expression in the specified context.
 * Can return null if there are no matching nodes.
 */
	public static NodeList SelectNodes(Node parent,String expression,boolean defaultNamespaceAware) {
		try {
	    	XPath xpath=getXPathFactory().newXPath();
	    	if (defaultNamespaceAware) {
		    	NamespaceContext nsc=new DefaultNamespaceContextProvider(parent);
		    	xpath.setNamespaceContext(nsc);
	    	}
	    	NodeList nodes=(NodeList)xpath.evaluate(expression,parent,XPathConstants.NODESET);
	   		return nodes;
		}
    	catch (XPathFactoryConfigurationException e) {
    		throw new ProgrammingException("XMLHELPER","Error creating new XPath Factory: "+e.getMessage(),e);
    	}
    	catch (XPathExpressionException e) {
    		throw new ProgrammingException("XMLHELPER","Expression error: '"+expression+"': "+e.getMessage(),e);
    	}
	}

//*****************************************************************************
//**                         Extrait un attribut d'un node.                  **
//*****************************************************************************
/**
 * Returns the content of the specified attribute in the specified node.
 * @param node The node containing the desired attribute.
 * @param attributeName The name of the desired attribute.
 * @return The text of the specified attribute. If the attribute doesn't exist
 * in the specified node, the method returns an empty string.
 */
	public static String GetNodeAttribute(Node node,String attributeName) {
		Element x=(Element)node;
		return x.getAttribute(attributeName);
	}

//*****************************************************************************
//**                       Sette un attribut.                                **
//*****************************************************************************
/**
 * Sets the content of an attribute in the specified node.
 * If the attribute already exists, its content is modified with the text
 * specified in <b>value</b>. If the attribute doesn't exist, a new one
 * (with the name specified in <b>name</b>) is created.
 * @param node The node that will contain the specified attribute.
 * @param name The name of the attribute to set the text.
 * @param value The text to place in the specified attribute.
 */
	public static void SetNodeAttribute(Node node,String name,String value) {
		Element x=(Element)node;
		x.setAttribute(name,value);
	}

//*****************************************************************************
//**                     Sauve un fichier XML.                               **
//*****************************************************************************
/**
 * Saves a Node as a <i>ISO-8859-1</i> encoded xml file.
 * @param x The node to save.
 * @param fileName The file name where to save the file.
 */
	public static void Save(Node x,String fileName) {
		Save(x,fileName,"ISO-8859-1");
	}
/**
 * Saves a Node as a xml file with the specified encoding.
 * @param x The node to save.
 * @param fileName The file name where to save the file.
 * @param encoding The encoding to use for the xml file.
 */
	public static void Save(Node x,String fileName,String encoding) {
		try {
			if (x.getNodeType()==Node.DOCUMENT_NODE)
				Save(x.getFirstChild(),fileName,encoding);
			else {
				FileOutputStream fos=new FileOutputStream(fileName);
				Save(x,fos,encoding);
				fos.close();
			}
		}
		catch (FileNotFoundException e) {
			throw new ProgrammingException("XMLHELPER","'"+fileName+"' not found.",e);
		}
		catch (IOException e) {
			throw new ProgrammingException("XMLHELPER","I/O problem with file '"+fileName+"'",e);			
		}
	}	

/**
 * Saves a Node as a xml file with the specified encoding.
 * @param x The node to save.
 * @param os Any output stream ready to receive data.
 * @param encoding The encoding to use for the xml file.
 */
	public static void Save(Node x,OutputStream os,String encoding) {
		Save(x,os,encoding,null);		
	}
	
/**
 * Saves a Node as a xml file with the specified encoding.
 * @param x The node to save.
 * @param fileName The file name where to save the file.
 * @param encoding The encoding to use for the xml file.
 * @param cdataElements CData elements list separated by space to serialize the specified node.
 */	
	public static void Save(Node x,String fileName,String encoding,String cdataElements){
		try {
			FileOutputStream fos=new FileOutputStream(fileName);
			Save(x,fos,encoding,cdataElements);
		}
		catch (FileNotFoundException e) {
			throw new ProgrammingException("XMLHELPER","'"+fileName+"' not found.",e);
		}
	}

/**
 * Saves a Node as a xml file with the specified encoding.
 * @param x The node to save.
 * @param os Any output stream ready to receive data.
 * @param encoding The encoding to use for the xml file.
 * @param cdataElements CData elements list separated by space to serialize the specified node.
 */
	public static void Save(Node x,OutputStream os,String encoding,String cdataElements) {
		try {
			if (x.getNodeType()==Node.DOCUMENT_NODE)
				Save(x.getFirstChild(),os,encoding,cdataElements);
			else {
//********************* Retrieves the identity transformer ********************
// The default transfomer is the identity transfomer,
// which performs no operation: output is equal to input.
				SAXTransformerFactory tf=(SAXTransformerFactory)SAXTransformerFactory.newInstance();
				TransformerHandler th=tf.newTransformerHandler();
				Transformer serializer=th.getTransformer();
				serializer.setOutputProperty(OutputKeys.METHOD,"xml");
				serializer.setOutputProperty(OutputKeys.ENCODING,encoding);
				serializer.setOutputProperty(OutputKeys.INDENT,"yes");
				if (cdataElements != null)
					serializer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, cdataElements);

//***************** Sets the input and output for the transformation **********
// The input is the specified node.
// The output is is sent through a StreamResult, which		
// is connected to a string writer. Serialization is performed here.
				DOMSource source=new DOMSource(x);
				BufferedOutputStream bos=new BufferedOutputStream(os);
				StreamResult result=new StreamResult(bos);

//****************** Perform the identity transformation **********************
				serializer.transform(source,result);
				os.close();
			}
		}
		catch (TransformerConfigurationException e) {
    		throw new ProgrammingException("XMLHELPER","Error creating a new transformer handler: "+e.getMessage(),e);			
		}
		catch (TransformerException e) {
    		throw new ProgrammingException("XMLHELPER","Error performing identity transformation: "+e.getMessage(),e);			
		}
		catch (IOException e) {
    		throw new ProgrammingException("XMLHELPER","Error accessing the provided output stream: "+e.getMessage(),e);						
		}
	}
	
/**
 * Saves a Node into a <i>ISO-8859-1</i> encoded string.
 * @param x The node to save.
 * @return The string containing the serialized version of the specified node.
 */
	public static String SaveToString(Node x) {
		return SaveToString(x,"ISO-8859-1");
	}

/**
 * Saves a Node into a string.
 * @param x The node to save.
 * @param encoding The encoding to use to serialize the specified node.
 * @return The string containing the serialized version of the specified node.
 */
	public static String SaveToString(Node x,String encoding) {
		try {
			if (x.getNodeType()==Node.DOCUMENT_NODE)
				return SaveToString(x.getFirstChild(),encoding);

//********************* Retrieves the identity transformer ********************
// The default transfomer is the identity transfomer,
// which performs no operation: output is equal to input.
			SAXTransformerFactory tf=(SAXTransformerFactory)SAXTransformerFactory.newInstance();
			TransformerHandler th=tf.newTransformerHandler();
			Transformer serializer=th.getTransformer();                 
			serializer.setOutputProperty(OutputKeys.METHOD,"xml");
			serializer.setOutputProperty(OutputKeys.ENCODING,encoding);
			serializer.setOutputProperty(OutputKeys.INDENT,"yes");

//***************** Sets the input and output for the transformation **********
// The input is the specified node.
// The output is is sent through a StreamResult, which		
// is connected to a string writer. Serialization is performed here.
			DOMSource source=new DOMSource(x);
			StringWriter sw=new StringWriter();
			StreamResult result=new StreamResult(sw);

//****************** Perform the identity transformation **********************
			serializer.transform(source,result);

//********************* Returns the result as a string ************************
			return sw.toString();
		}
		catch (TransformerConfigurationException e) {
    		throw new ProgrammingException("XMLHELPER","Error creating a new transformer handler: "+e.getMessage(),e);			
		}
		catch (TransformerException e) {
    		throw new ProgrammingException("XMLHELPER","Error performing identity transformation: "+e.getMessage(),e);			
		}
	}

//*****************************************************************************
//**        Returns the specified node as an input stream.                   **
//*****************************************************************************
/**
 * Returns an {@link InputStream} from which the serialization of the specified
 * node can be read. 
 * This is an example:
 * <pre>
 *  Node node=...;
 *  InputStream is = XmlHelper.SaveToInputStream(node,"UTF-8");
 *  OutputStream os = [any output stream initialization];
 *  byte buffer[1024*10];                   // A buffer between input and output streams.
 *  int nread = 0;                          // Number of bytes read from the input stream.
 *  while((nread=is.read(buffer))!=-1)      // Reads from the input stream.
 *  	os.write(buffer, 0, nread);         // Writes to the output stream.
 *  os.close();                             // Closes both streams.
 *  is.close();                             // Closes both streams.
 * </pre>
 * @param xml Node to serialize.
 * @param encoding The encoding to use to serialize the specified node.
 * @return An InputStream from which the serialization of the specified node
 * can be read.
 */
	static public InputStream SaveToInputStream(Node xml,String encoding) {
		return SaveToInputStream(xml,encoding,null);
	}

//*****************************************************************************
//**        Returns the specified node as an input stream.                   **
//*****************************************************************************
/**
 * Returns an {@link InputStream} from which the serialization of the specified
 * node can be read. 
 * This is an example:
 * <pre>
 *  Node node=...;
 *  InputStream is = XmlHelper.SaveToInputStream(node,"UTF-8");
 *  OutputStream os = [any output stream initialization];
 *  byte buffer[1024*10];                   // A buffer between input and output streams.
 *  int nread = 0;                          // Number of bytes read from the input stream.
 *  while((nread=is.read(buffer))!=-1)      // Reads from the input stream.
 *  	os.write(buffer, 0, nread);         // Writes to the output stream.
 *  os.close();                             // Closes both streams.
 *  is.close();                             // Closes both streams.
 * </pre>
 * @param xml Node to serialize.
 * @param encoding The encoding to use to serialize the specified node.
 * @param cdataElements CData elements list separated by space to serialize the specified node.
 * @return An InputStream from which the serialization of the specified node
 * can be read.
 */
	static public InputStream SaveToInputStream(Node xml,String encoding, String cdataElements) {
//********************** Thread performing the node serialization ***************
		class PipedThread extends Thread {
			private String _encoding;
			private DOMSource _source;
			private StreamResult _result;
			private PipedOutputStream _pos;
			private String _cdata;

//................................. Initialization ..............................
			public PipedThread(Node xml, String encoding, String cdata) throws Exception {
				_cdata = cdata;
				_encoding=encoding;

// Prepares the node as a source:
				_source=new DOMSource(xml);

// The result of the serialization is sent to a StreamResult, 
// which is connected to a PipedOutputStream:
				_pos=new PipedOutputStream();
				_result=new StreamResult(_pos);			
			}

//........................ To obtain the PipedOutputStream ......................
			public PipedOutputStream getPipedOutputStream() {
				return _pos;
			}

//...................... Performs the identity transformation ...................
			@Override
			public void run() {					
				try {
// Initializes an identity transformer:
// (The default transfomer is the identity transfomer,
// which performs no operation: output is equal to input)
					SAXTransformerFactory tf=(SAXTransformerFactory)SAXTransformerFactory.newInstance();
					TransformerHandler th=tf.newTransformerHandler();
					Transformer serializer=th.getTransformer();                 
					serializer.setOutputProperty(OutputKeys.METHOD,"xml");
					serializer.setOutputProperty(OutputKeys.ENCODING,_encoding);
					serializer.setOutputProperty(OutputKeys.INDENT,"yes");
					if (_cdata != null)
						serializer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, _cdata);
					
// Perform the identity transformation:
					serializer.transform(_source,_result);

// Closes output stream:
					_pos.close();
				} catch(Exception e){
					try {
						_pos.close();
					} catch (Exception ex) {
						System.out.println("Could not close the Piped Output Stream:"+e.getMessage());
					}
					throw new RuntimeException(ParseError.parseError("PipedThread.run",e));
				}
			}								
		}

//********* Launches a new thread performing the node serialization ***********
		PipedThread thread;
		PipedInputStream pis;
		try {
			if (xml.getNodeType()==Node.DOCUMENT_NODE)
				return SaveToInputStream(xml.getFirstChild(),encoding, cdataElements);

			thread=new PipedThread(xml,encoding, cdataElements);
			pis = new PipedInputStream(thread.getPipedOutputStream());
			thread.start();
			return pis;
		} catch (Exception e) {
   			throw new ProgrammingException("xmlhelper.CopyNode(parent,source,cname)",e.getMessage(),e);						
		}
	}

//*****************************************************************************
//**            Makes a new document from a node.                            **
//*****************************************************************************
/**
 * Makes a new document from a node.
 * @param node The node to make the new document from.
 * @return A new document with the same content as the provided node.
 */
	public static Document newDocument(Node node) {
		try {
			if (node.getNodeType()==Node.DOCUMENT_NODE)
				node=node.getFirstChild();

//********************* Retrieves the identity transformer ********************
// The default transfomer is the identity transfomer,
// which performs no operation: output is equal to input.
			SAXTransformerFactory tf=(SAXTransformerFactory)SAXTransformerFactory.newInstance();
			TransformerHandler th=tf.newTransformerHandler();
			Transformer serializer=th.getTransformer();                 

//***************** Sets the input and output for the transformation **********
// The input is the specified node.
// The output is is sent through a StreamResult, which		
// is connected to a string writer. Serialization is performed here.
			DOMSource source=new DOMSource(node);
			DOMResult result=new DOMResult();

//****************** Perform the identity transformation **********************
			serializer.transform(source,result);

//********************* Returns the result as a string ************************
			return (Document)result.getNode();
		}
		catch (TransformerConfigurationException e) {
    		throw new ProgrammingException("XMLHELPER","Error creating a new transformer handler: "+e.getMessage(),e);			
		}
		catch (TransformerException e) {
    		throw new ProgrammingException("XMLHELPER","Error performing identity transformation: "+e.getMessage(),e);			
		}
	}
		

//*****************************************************************************
//**                  Creates a new empty document.                          **
//*****************************************************************************
/**
 * Returns a new empty Document.
 * @return A new empty Document.
 */
	public static Document newDocument() {
		Document doc;
		String magicValue=System.getProperty("javax.xml.parsers.DocumentBuilderFactory");
    	System.setProperty("javax.xml.parsers.DocumentBuilderFactory","org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db=dbf.newDocumentBuilder();
			doc=db.newDocument();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    	if (magicValue==null)
    		System.clearProperty("javax.xml.parsers.DocumentBuilderFactory");
    	else
    		System.setProperty("javax.xml.parsers.DocumentBuilderFactory",magicValue);
		return doc;
	}

//*****************************************************************************
//**         Charge un document XML depuis la mémoire.                       **
//*****************************************************************************

//************************ Depuis une chaîne qui contient le code XML *********
	public static Document LoadXML(StringBuffer xml) {
		return LoadXML(xml.toString());
	}

//***************** Depuis une chaîne qui contient le code XML ****************
	public static Document LoadXML(String xml) {
		StringReader sr;
		sr=new StringReader(xml);
		return LoadXML(sr);
	}

//******************************* Depuis un Reader ****************************
	private static Document LoadXML(Reader xml) {
		try {
//..................... Retrieves the identity transformer ....................
// The default transfomer is the identity transfomer,
// which performs no operation: output is equal to input.
			SAXTransformerFactory tf=(SAXTransformerFactory)SAXTransformerFactory.newInstance();
			TransformerHandler th=tf.newTransformerHandler();
			Transformer serializer=th.getTransformer();

//................. Sets the input and output for the transformation ..........
// The input is the specified stream.
// The output is is a DOM structure.
			StreamSource source=new StreamSource(xml);
			DOMResult result=new DOMResult();

//.................. Perform the identity transformation ......................
			serializer.transform(source,result);
			xml.close();

//...................... Returns the loaded structure .........................
			return (Document)result.getNode();
		}
		catch (TransformerConfigurationException e) {
    		throw new ProgrammingException("XMLHELPER","Error creating a new transformer handler: "+e.getMessage(),e);			
		}
		catch (TransformerException e) {
    		throw new ProgrammingException("XMLHELPER","Error performing identity transformation: "+e.getMessage(),e);			
		}
		catch (IOException e) {
    		throw new ProgrammingException("XMLHELPER",e.getMessage(),e);						
		}
	}

//******************************************************************************
//**          Charge un document Xml depuis un flux.                          **
//******************************************************************************
	
//***************************** depuis une URL *********************************
	public static Document Load(URL url) {
//		try {
//			InputStream is = url.openStream();
//			BufferedInputStream buffer = new BufferedInputStream(is); 
//			return Load(buffer,null);
//		} catch (IOException e) {
//			throw new ProgrammingException("XMLHELPER","Error accessing to '"+url.toExternalForm()+"'",e);
//		}
		return null;
	}

//***************************** depuis une URL avec une dtd *********************************
	public static Document Load(URL url, EntityResolver er) {
//		try {
//			InputStream is = url.openStream();
//			BufferedInputStream buffer = new BufferedInputStream(is); 
//			return Load(buffer,er);
//		} catch (IOException e) {
//			throw new ProgrammingException("XMLHELPER","Error accessing to '"+url.toExternalForm()+"'",e);
//		}
		return null;
	}	

//***************************** depuis un fichier XML **************************
	public static Document Load(String fileName) {
		try {
			FileInputStream fis;
			File file;
			file=new File(fileName);
			if (!file.exists())
				throw new ProgrammingException("XMLHELPER","File '"+fileName+"' doesn't exist.");
			if (!file.isFile())
				throw new ProgrammingException("XMLHELPER","'"+fileName+"' isn't a file.");
			fis=new FileInputStream(file);
			return Load(fis);
		}
		catch (FileNotFoundException e) {
			throw new ProgrammingException("XMLHELPER","File '"+fileName+"' doesn't exist.");			
		}
	}
	
//*************************** depuis une inputStream ************************************************
	public static Document Load(InputStream is) {
		//return Load(is,null);
		return null;
	}

//	public static Document Load(InputStream is,EntityResolver er) {
//		try {
////..................... Retrieves the identity transformer ....................
//// The default transfomer is the identity transfomer,
//// which performs no operation: output is equal to input.
//			SAXTransformerFactory tf=(SAXTransformerFactory)SAXTransformerFactory.newInstance();
//			TransformerHandler th=tf.newTransformerHandler();
//			Transformer serializer=th.getTransformer();
//
////................. Sets the input and output for the transformation ..........
//// The input is the specified stream.
//// The output is is a DOM structure.
//			XMLReader xmlReader=XMLReaderFactory.createXMLReader();
//			if (er!=null)
//				xmlReader.setEntityResolver(er);
//			EmptyStreamDetector esd=new EmptyStreamDetector(is);
//			InputSource inputSource=new InputSource(esd);
//			SAXSource source=new SAXSource(xmlReader,inputSource);
//			DOMResult result=new DOMResult();
//
////.................. Perform the identity transformation ......................
//			serializer.transform(source,result);
//			is.close();
//
////...................... Returns the loaded structure .........................
//			return (Document)result.getNode();
//		}
//		catch (SAXException e) {
//			throw new ProgrammingException(ProgrammingException.DOM_CREATION_ERROR,"Error creating the xmlReader: "+e.getMessage(),e);			
//		}
//		catch (TransformerConfigurationException e) {
//			throw new ProgrammingException(ProgrammingException.DOM_CREATION_ERROR,"Error creating a new transformer handler: "+e.getMessage(),e);			
//		}
//		catch (TransformerException e) {
//			Throwable ee=e.getCause();
//			if (ee instanceof EmptyStreamException)
//				throw new ProgrammingException(ProgrammingException.DOM_EMPTYFILE,"Specified stream is empty: "+e.getMessage(),e);						
//    		throw new ProgrammingException(ProgrammingException.DOM_CREATION_ERROR,"Error performing identity transformation: "+e.getMessage(),e);			
//		}
//		catch (IOException e) {
//    		throw new ProgrammingException(ProgrammingException.DOM_READING_ERROR,"Error accessing the provided output stream: "+e.getMessage(),e);						
//		}
//	}

//****************************************************************************
//**                   Transforme un noeud XML en code HTML                 **
//****************************************************************************
	public static String XMLtoHTML(Node x) {
		String s=SaveToString(x);
		s=Replace(s,"   ","...");
		s=Replace(s,"<","&lt;");
		s=Replace(s,">","&gt;");
		s=Replace(s,"\n","<br>\n");
		return s;
	}
//*****************************************************************************
//**            Calcule la profondeur d'un flux XML.                         **
//*****************************************************************************
    public static int XMLDeep(Node parent) {
        Node child;
        NodeList childs;
        int n;
        int maxdeep,deep;
        maxdeep=0;
        childs=parent.getChildNodes();
        for(n=0;n<childs.getLength();n++) {
            child=(Node)childs.item(n);
            deep=XMLDeep(child);
            if (deep>maxdeep) maxdeep=deep;
        }
        maxdeep+=1;
        return maxdeep;
    }

//************************************************************
//**         Transforme une chaine en une chaine XML.       **
//************************************************************
// Elle transforme les caracteres illegaux comme "<" ou "&"
// en caracteres XML.
// La fonction peut être appelée plusieurs fois consecutives
// sur la même chaine, la codification aura lieu que la première fois.
// str<>ParseForXML(str)=ParseForXML(ParseForXML(str))
    public static String ParseForXML(String str) {
        String s=str;
        StringBuffer r=new StringBuffer("");
        int n1,n2,n3;

//............... Remplace les "&" ..........................
// Les "&" sont légaux sous la forme "&amp;", "&lt;" et "&gt;"
        n3=0;
        n1=str.indexOf("&");
        while (n1>0) {
            n2=str.indexOf(";",n1);
            if (n2>n1) {
                s=str.substring(n1,n2);
                r.append(str.substring(n3,n1));
                n3=n1+1;
                if (s.equals("&amp") || s.equals("&gt") || s.equals("&lt"))
                    r.append("&");
                else
                    r.append("&amp;");
            } else {
                r.append(str.substring(n3,n1));
                r.append("&amp;");
                n3=n1+1;
            }
            n1=str.indexOf("&",n3);
        }
        r.append(str.substring(n3));

//................. Remplace les ">" et les "<" ..............
        s=r.toString();
        s=Replace(s,"<","&lt;");
        s=Replace(s,">","&gt;");
        return s;
    }


//*****************************************************************************
//**                 Sette la valeur d'un node XML.                          **
//*****************************************************************************
	static public void SetNodeText(Node node,String value) {
		NodeList node_;
		Node x;
		int n,nnode;
		if (value==null)
			value="";
		if(node.getNodeType() == Node.TEXT_NODE)
			node.setNodeValue(value);
		else {
			node_=node.getChildNodes();
			nnode=node_.getLength();
			if (nnode==0) {
				x=(Node)node.getOwnerDocument().createTextNode(value);
				node.appendChild(x);
			} else {
				for (n=0;n<nnode;n++) {
					x=(Node)node_.item(n);
					if (x==null) continue;
					if (x.getNodeType()==Node.TEXT_NODE) {
						x.setNodeValue(value);
						break;
					}
				}
			}
		}
	}

//*****************************************************************************
//**                  Remplace une chaine par une autre.                     **
//*****************************************************************************
	static public String Replace(String orig,String find,String rby) {
		int n1,n2;
		StringBuffer r=new StringBuffer("");
		n1=0;
		n2=orig.indexOf(find);
		while (n1>=0) {
			if (n2<0) {
				r.append(orig.substring(n1,orig.length()));
				break;
			}
			r.append(orig.substring(n1,n2));			
			r.append(rby);
			n1=n2+find.length();
			n2=orig.indexOf(find,n1);
		}
		return r.toString();
	}

//*****************************************************************************
//**                     Recupere le texte d'un noeud.                       **
//*****************************************************************************
	static public String GetNodeText(Node parent) {
		String s;
		StringBuffer value;
		NodeList nodes;
		Node node;
		int n;

		value=new StringBuffer(""); 
		if (parent.hasChildNodes()) {
			nodes=parent.getChildNodes();
			for(n=0;n<nodes.getLength();n++) { 
				node=nodes.item(n);
				if (node.getNodeType()==Node.TEXT_NODE)
					value.append(node.getNodeValue());
				if (node.hasChildNodes())
					value.append(GetNodeText(node));
			}
		} else {
			s=parent.getNodeValue();
			if (s!=null) return s; else return "";
		}
		return value.toString();
	}

//*****************************************************************************
//**                 Recupere le texte d'un sous-noeud.                      **
//*****************************************************************************
	static public String GetNodeText(Node parent,String childname) {
		Node x=SelectSingleNode(parent,childname);
		if (x!=null) 
			return GetNodeText(x);
		return "";
	}

//*****************************************************************************
//**         Recupere le texte d'un sous-noeud qui a un certain attribut.    **
//*****************************************************************************
	static public String GetNodeTextA(Node parent, String childtype, String attr, String value,boolean defaultNamespaceAware) throws Exception {
		Node x;
		x=SelectSingleNode(parent,childtype+"[@"+attr+"=\""+value+"\"]",defaultNamespaceAware);
		if (x!=null)
			return GetNodeText(x);
		return "";
	}

//*****************************************************************************
//**                    Recupere le code XML d'un noeud.                     **
//*****************************************************************************
	static public String GetNodeXML(Node node) throws Exception {
		String xml=SaveToString(node);
		int n1=xml.indexOf(">")+1;
		int n2=xml.lastIndexOf("<");
		return xml.substring(n1,n2);
	}

//*****************************************************************************
//**                   Trie alphabetiquement une liste de noeuds             **
//*****************************************************************************
	static public void SortChilds(Node parent, String childname) {
		Node a;                            // Pour parcourrir les elements de parent.
		Node b;                            // Pour parcourrir les elements de parent.
		Node min;                          // Pointe sur le node "minimum" (alphabetiquement).
		String btext;                            // La valeur de l'element b.
		String mintext;                          // La valeur de l'element min
		boolean replace;

//********************* Parcourre tous les elements de type childname ****************
		a=SelectSingleNode(parent,childname);
		if (a!=null) {
			do {
//..................... Pour chaque element ..........................................
				min=a;
				mintext=GetNodeText(min);

//.................. On cherche un element plus petit ................................
				b=SelectSingleNode(a,"following-sibling::"+childname+"[position()=1]");
				replace=false;

				if (b!=null) {
					do {
						btext = GetNodeText(b);
						if (btext.compareTo(mintext)<0) {
							min=b;
							mintext=GetNodeText(min);
							replace=true;
						}
						b=SelectSingleNode(b,"following-sibling::"+childname+"[position()=1]");
					} while (b!=null);
				}
        
//............. Si on a trouve un element plus petit, on remplace ....................
				if (replace) {
					parent.removeChild(min);
					parent.insertBefore(min,a);
					a = min;
				}
				a=SelectSingleNode(a,"following-sibling::"+childname+"[position()=1]");
			} while (a!=null);
		}
	}

//******************************************************************************
//**                 Contrôle si un node Xml a du contenu.                    **
//******************************************************************************
/**
 * Checks if a node has contents.
 * A node is considered as <i>having content</i> when:
 * <ul>
 * 		<li>When the node or any of its childs contains a non-empty attribute.</li>
 * 		<li>When the node or any of its childs contains non-empty text nodes. If the 
 * 		parameter <b>ignoreBlankSpaces</b> is <i>true</i>, text nodes containing
 * 		only control characters (ascii codes equal or smaller than 32) are considered
 * 		empty.</li>
 * </ul>
 * Comment and processing instruction nodes are not considered as content.
 * @param x The node to check. If it is null, the method returns <i>false</i>.
 * @param ignoreBlankSpaces If <i>true</i> text nodes containing
 * only control characters (ascii codes equal or smaller than 32) are considered
 * empty.
 * @return <i>true</i> if the specified node has contents.
 */
	static public boolean NodeHasContent(Node x,boolean ignoreBlankSpaces) {
		int n,nn;                         // To parse child nodes of "x".
		Node child;                       // One of the childs of "x".
		int childType;                    // Type of the child node.
		String childContent;              // When the child is a text node, its content.

//.............................. Looks for "non-empty" nodes ...................
		if (x!=null) {
			nn=x.getChildNodes().getLength();
			for(n=0;n<nn;n++) {
				child=x.getChildNodes().item(n);
				childType=child.getNodeType();
				switch(childType) {
// Nodes containing text:
					case Node.CDATA_SECTION_NODE:
					case Node.TEXT_NODE:
						if (ignoreBlankSpaces)
							childContent=child.getNodeValue().trim();
						else
							childContent=child.getNodeValue();
						if (childContent.length()>0)
							return true;

// Elements (nodes containing nodes):
					case Node.ELEMENT_NODE:
						if (NodeHasContent(child,ignoreBlankSpaces))
							return true;

// Any other type of node are not considered as content:
					default:
						continue;
				}
			}
		}
//............................. If no "non-empty" node has been found ..........
		return false;
	}

//******************************************************************************
//**                     Copie un node d'un document a un autre.              **
//******************************************************************************
	static public Node CopyNode(Node parent,Node source) {
		return CopyNode(parent,source,"");
	}

	static public Node CopyNode(Node parent,Node source,String cname) {
		Document owner;
		Node destination;
		Node node;
		NodeList nodes;
		NamedNodeMap attrs;
		Attr attr;
		String name,value;
		short type;
		int n,nn;
		String namespace;

//............................ Initialisation .................................
// On obtient le document:
		owner=parent.getOwnerDocument();
		if (owner==null) owner=(Document)parent;
		namespace = source.getNamespaceURI();

//........................... Création du nouvel élément ......................
		if (cname.length()>0) name = cname; else name = source.getNodeName();
		type=source.getNodeType();
		switch(type) {
//****************************** Si la source est un document *****************
			case Node.DOCUMENT_NODE:
				if (source.hasChildNodes()) {
					node=source.getFirstChild();
					return CopyNode(parent,node,cname);
				} else
					throw new ProgrammingException("XMLHELPER","'source' argument is a DOCUMENT_NODE, but has no childs");
			
//*************************** Si la source est simplement un texte ************
			case Node.TEXT_NODE:
				value=source.getNodeValue();
				destination=(Node)owner.createTextNode(value);
				break;

//************************* Si la source est une section CDATA ****************
			case Node.CDATA_SECTION_NODE:
				value=source.getNodeValue();
				destination=(Node)owner.createCDATASection(value);
				break;

//************************ Si la source est un élément ************************
			case Node.ELEMENT_NODE:
				destination=(Node)owner.createElementNS(namespace,name);
//........................... On lui rajoute tous les attributs ...............
				attrs=source.getAttributes();
				nn=attrs.getLength();
				for (n=0;n<nn;n++) {
					attr=(Attr)attrs.item(n);
					name=attr.getName();
					value=attr.getValue();
					SetNodeAttribute(destination,name,value);
				}

//............................ On lui rajoute tous ses fils ...................    
				if (source.hasChildNodes()) {
					nodes=source.getChildNodes();
					nn=nodes.getLength();
					for(n=0;n<nn;n++) {
						node=(Node)nodes.item(n);
						CopyNode(destination,node);
					}
				} //else xmlhelper.SetNodeText(destination,source.getNodeValue());
				break;

//*************************** Si la source est un commentaire *****************
			case Node.COMMENT_NODE:
				value=source.getNodeValue();
				destination=(Node)owner.createComment(value);
				break;

//****************** Si la source est d'un type pas encore implementé ********
			default:
				throw new ProgrammingException("XMLHELPER","CopyNode(...) cannot copy nodes of type:"+type);
		}
		parent.appendChild(destination);

//............................ Et voilà .......................................
		return (Node)destination;
	}
}