/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.Helpers;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlComparator {

//******************************************************************************************
//**                                   Holds the diagnostic.                              **
//******************************************************************************************
	private ArrayList<String> _diagnostic;

//*************************** Erases the list of differences *******************************
	private void clearDiagnostic() {
		_diagnostic=new ArrayList<String>();
	}

//***************************** Returns the list of differences. ***************************
/**
 * Returns the list of differences between the last two compared nodes.
 * If the compared nodes are identical, the list is empty (not null).
 */
	public ArrayList<String> getDiagnostic() {
		if (_diagnostic==null)
			clearDiagnostic();
		return _diagnostic;
	}

//*****************************************************************************************
//**                            Compares two Xml nodes.                                  **
//*****************************************************************************************
/**
 * Compares two Xml nodes and checks if they contain the same information.
 * This method doesn't check the attributes of upper level. (upper level 
 * attributes are attributes present directly in <i>x1</i> and <i>x2</i>; 
 * attributes in child nodes are always compared).
 * @param x1 One of the nodes to be compared.
 * @param x2 The other of the nodes to be compared.
 * @return <i>true</i> If both nodes contain the same information.
 */
	public boolean CompareXml(Node x1,Node x2) throws Exception {
		return CompareXml(x1,x2,true,false,false);
	}

/**
 * Compares two Xml nodes and checks if they contain the same information.
 * @param x1 One of the nodes to be compared.
 * @param x2 The other of the nodes to be compared.
 * @param checkAttributes When <i>true</i> the method checks the upper level
 * attributes (upper level attributes are attributes present directly in
 * <i>x1</i> and <i>x2</i>; attributes in child nodes are always compared)
 * @return <i>true</i> If both nodes contain the same information.
 */
	public boolean CompareXml(Node x1,Node x2,boolean ignoreBlankSpaces,boolean checkAttributes,boolean ignoreEmptyNodes) throws Exception {
		try {
			clearDiagnostic();

//...................... On commence par les comparaisons triviales ........................
			if ((x1==null) && (x2==null)) return true;
			if (x1==null) return false;
			if (x2==null) return false;

//........................... Puis les autres ..............................................
			if (!IsAllInfoPresent(x1,x2,ignoreBlankSpaces,checkAttributes,ignoreEmptyNodes)) return false;
			if (!IsAllInfoPresent(x2,x1,ignoreBlankSpaces,checkAttributes,ignoreEmptyNodes)) return false;
			return true;
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("XmlHelper.CompareXml",e));
		}
	}

//*****************************************************************************************
//**            Checks whether all info in the first node is present in the second.      **
//*****************************************************************************************
/**
 * Checks whether all info in the first node is present in the second.
 * @param x1 The first node to compare.
 * @param x2 The second node to compare.
 * @return <i>true</i> if all information contained in <b>x1</b> is present in <b>x2</b>.
 * <b>x2</b> may contain more information.
 */
	private boolean IsAllInfoPresent(Node x1,Node x2,boolean ignoreBlankSpaces,boolean checkAttributes,boolean ignoreEmptyNodes) throws Exception {
		Node x1Attr;                              // Un des attributs de x1.
		Node x1Node;                              // Un des fils de x1.
		String x1NodeName;                        // Le nom de x1Node;
		String x1NodeText;                        // Le texte contenu dans x1Node (quand c'est un node de texte).
		String x1AttrName;                        // Le nom d'un des attributs de x1Node.
		String x1AttrValue;                       // La valeur d'un des attributes de x2Node.
		NodeList x2Nodes;                         // Les noeuds de x2 qui ont le même nom de x1Node.
		Node x2Node;                              // Un des noeuds fils de x2 avec le même nom que x1Node.
		String x2NodeText;                        // Le texte contenu dans x2Node (quand c'est un node de texte). 
		String x2AttrValue;                       // La valeur d'un des attributs de x2Node.
		boolean isTextPresent;                    // Indique si un node texte présent dans x1 est aussi présent dans x2.
		boolean response=true;
		int n;                                    // Compteur.
		int m;                                    // Un autre compteur.
		try {
			if (x1==null) return true;
			if (x2==null) return false;
			if (x1.getNodeType()==Node.DOCUMENT_NODE)
				return  IsAllInfoPresent(x1.getFirstChild(),x2,ignoreBlankSpaces,checkAttributes,ignoreEmptyNodes);
			if (x2.getNodeType()==Node.DOCUMENT_NODE)
				return  IsAllInfoPresent(x1,x2.getFirstChild(),ignoreBlankSpaces,checkAttributes,ignoreEmptyNodes);

//************************** On contrôle qu'ils aient les mêmes attributs *****************
			if (checkAttributes)
				for(n=0;n<x1.getAttributes().getLength();n++) {
					x1Attr=x1.getAttributes().item(n);
					x1AttrName=x1Attr.getNodeName();
					x1AttrValue=x1Attr.getNodeValue();
					x2AttrValue=XmlHelper.GetNodeAttribute(x2,x1AttrName);
					if (!x2AttrValue.equals(x1AttrValue)) {
						_diagnostic.add("Attribute '"+x1AttrName+"' in node '"+x1.getNodeName()+"' is missing or different.");
						response=false;
					}
				}

//************************ Compare les noeuds texte ***************************************
// Les nodes texte doivent être dans le même ordre.
			x2Nodes=x2.getChildNodes();
			m=0;
			for(n=0;n<x1.getChildNodes().getLength();n++) {

//.................... On cherche le suivant node texte de x1 .............................
				x1Node=x1.getChildNodes().item(n);
				if (x1Node.getNodeType()!=Node.TEXT_NODE)
					continue;

// Ignore les espaces blancs:
				if (ignoreBlankSpaces) {
					x1NodeText=x1Node.getNodeValue().trim();
					if (x1NodeText.length()==0)
						continue;
				} else
					x1NodeText=x1Node.getNodeValue();

//.................... On cherche le suivant node texte de x2 .............................
				isTextPresent=false;
				do {
					x2Node=x2Nodes.item(m++);
					if (x2Node.getNodeType()!=Node.TEXT_NODE)
						continue;

// On ignore les espaces blancs:
					if (ignoreBlankSpaces) {
						x2NodeText=x2Node.getNodeValue().trim();
						if (x2NodeText.length()==0)
							continue;
					} else
						x2NodeText=x2Node.getNodeValue();

//.................... On compare les deux nodes ..........................................
					if (x1NodeText.equals(x2NodeText)) {
						isTextPresent=true;
						break;
					}
				} while (m<x2Nodes.getLength());

//............. Si on n'a trouvé aucun node texte dans X2 qui corrésponde ................. 
				if (!isTextPresent) {
					_diagnostic.add("Text content of node '"+x1.getNodeName()+"' is different.");
					response=false;
				}
			}	
				
//************************ Compare les elements *******************************************
// Les éléments ne doivent pas forcément être dans le même ordre.
			for(n=0;n<x1.getChildNodes().getLength();n++) {
				x1Node=x1.getChildNodes().item(n);
				if (x1Node.getNodeType()!=Node.ELEMENT_NODE)
					continue;

				if (x1Node.getChildNodes().getLength()==0)
					if (ignoreEmptyNodes)
						continue;
//............ Il y aura 1 ou plusieurs éléments dans x2 avec le même nom. .................
				x1NodeName=x1Node.getNodeName();
				if (x1NodeName.indexOf(":")<0)
					x1NodeName="def:"+x1NodeName;
				x2Nodes=XmlHelper.SelectNodes(x2,x1NodeName,true);
				if (x2Nodes.getLength()==0) {
					_diagnostic.add("Node '"+x2.getNodeName()+"' doesn't contain any node named '"+x1NodeName+"'");
					response=false;           // Si il n'y en a aucun, x1<>x2.
				}

//............. On en cherche un qui soit identique à x1Node. .............................
				for (m=0;m<x2Nodes.getLength();m++) {
					x2Node=x2Nodes.item(m);
					
					if (IsAllInfoPresent(x1Node,x2Node,ignoreBlankSpaces,checkAttributes,ignoreEmptyNodes)) {
						break;
					}
				}
				if (m==x2Nodes.getLength()) return false;           // Si il n'y en a aucun, x1<>x2.					
			}

//***************** Si on arrive jusqu'ici, c'est que le nodes sont identiques ************
			return response;
		}
		catch(Exception e) {
			throw new Exception(ParseError.parseError("XmlHelper.IsAllInfoPresent",e));
		}
	}
}
