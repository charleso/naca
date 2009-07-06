/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.Helpers;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;

import jlib.misc.StringUtil;

import org.w3c.dom.Node;

//*****************************************************************************
//**                Private class to handle namespaces.                      **
//*****************************************************************************
/**
 * Namespace context provider for handling default namespace in the
 * {@link XmlHelper.SelectSingleNode} and {@link XmlHelper.SelectNodes} methods.  
 */
public class DefaultNamespaceContextProvider implements NamespaceContext {
/**
 * The default namespace URI.
 */
	private String _defaultNamespaceURI;
/**
 * The root element of the node being processed.
 * The method assumes that all namespaces are declared in the root element, which is true
 * in most cases.
 */
	private Node _rootElement;
//***************************************************************************
//**                            The class constructor.                     **
//***************************************************************************
/**
 * This constructor uses the <b>node</b> parameter to reach the root element
 * of the document, assuming that there will be all namespace declarations.
 * @param node The context node for performing XPath operations.
 */
	public DefaultNamespaceContextProvider(Node node) {
		if (node.getNodeType()==Node.DOCUMENT_NODE)
			_rootElement=node.getFirstChild();
		else {
			_rootElement=node.getOwnerDocument().getFirstChild();
			while(_rootElement.getNodeType()!=Node.ELEMENT_NODE) {
				_rootElement=_rootElement.getNextSibling();
				if (_rootElement==null)
					break;
			}
		}

		if (_rootElement.getPrefix()!=null)
			_defaultNamespaceURI=_rootElement.getNamespaceURI();
		else {
			_defaultNamespaceURI=XmlHelper.GetNodeAttribute(_rootElement,"xmlns");
			if (StringUtil.isEmptyOrOnlyWhitespaces(_defaultNamespaceURI))
				_defaultNamespaceURI=_rootElement.getNamespaceURI();
			if (StringUtil.isEmptyOrOnlyWhitespaces(_defaultNamespaceURI))
				_defaultNamespaceURI=XMLConstants.NULL_NS_URI;
		}
	}

/**
 * Constructor.
 * Looks for the default namespace defined for the specified node:
 * <ul>
 * 	<li>If the specified node is a <i>DOCUMENT_NODE</i>, the method examines
 * 	the first child (which corresponds to the document element):
 * 		<ul>
 * 			<li>If it hasn't any prefix (its prefix is an empty string), it means
 * 			that the document element belongs to the default namespace. Thus the
 * 			default namespace is the namespace of the document element.</li>
 * 			<li>Else the method looks for the <i>xmlns</i> attribute, which
 * 			contains the default namespace for the child elements.</li>
 * 		</ul>
 * 	</li>
 * 	<li>Else, the method examines the current node:
 * 		<ul>
 * 			<li>If the node hasn't prefix, then it belongs to the default namespace.</li>
 * 			<li>Else the method looks the <i>xmlns</i> attribute, wich specifies the
 * 			default namespace</li>
 *			<li>If both previous test fail, then the method examines the parent
 *			node.</li>
 *		</ul>
 *	</li> 
 * </ul>
 * The prefix for the default namespace is set to an empty string. Thus the
 * nodes without prefix are assigned to the default namespace.
 * @param node The default namespace URI will be taken from the specified node.
 *
	public DefaultNamespaceContextProvider(Node node) {
		String prefix;
		String xmlns;
		if (node.getNodeType()==Node.DOCUMENT_NODE) {
			node=node.getFirstChild();
			prefix=node.getPrefix();
			if (prefix==null)
				_defaultNamespaceURI=node.getNamespaceURI();
			else
				_defaultNamespaceURI=XmlHelper.GetNodeAttribute(node,"xmlns");
		} else do {
			prefix=node.getPrefix();
			if (prefix==null) {
				_defaultNamespaceURI=node.getNamespaceURI();
				break;
			} else {
				xmlns=XmlHelper.GetNodeAttribute(node,"xmlns");
				if (xmlns.length()>0) {
					_defaultNamespaceURI=xmlns;
					break;
				}
			}					
		} while(node.getNodeType()!=Node.DOCUMENT_NODE);
		_defaultNamespacePrefix="";
	}*/
/**
 * Returns the namespace corresponding to the specified prefix.
 */
	public String getNamespaceURI( String prefix) {
		String namespaceURI;
//..................... If the default namespace is requested ......................
		if (prefix.equals("") || prefix.equals("def"))
			return _defaultNamespaceURI;

//........................ If any other namespace is requested .....................
		namespaceURI=XmlHelper.GetNodeAttribute(_rootElement,"xmlns:"+prefix);
		if (namespaceURI.length()>0)
			return namespaceURI;

//................... If the requested namespace has not been found ................
		return XMLConstants.NULL_NS_URI;
	}
	
/**
 * Returns the prefix corresponding to the specified namespace.
 */
	public String getPrefix(String namespaceURI) {
		if (namespaceURI.equals(_defaultNamespaceURI))
			return "";
		return null;
	}
	
/**
 * Returns the list of prefixes corresponding to the specified namespace.
 * This method is not implementes, as it is not used by the Xerces implementation 
 * of the {@link XPath.evaluate} method.
 */
	public Iterator getPrefixes( String namespaceURI) {
		throw new RuntimeException("XmlHelper.NamespaceContextProvider.getPrefixes('"+namespaceURI+"')->This method is not implemented.");
	}
}
