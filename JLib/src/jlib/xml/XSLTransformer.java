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
package jlib.xml;

import java.io.File;
import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


import org.w3c.dom.Document;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XSLTransformer
{
	private Templates m_Template = null ;
	
	protected XSLTransformer(Templates trans)
	{
		m_Template = trans ;
	}
	
	public static XSLTransformer loadFromFile(File fSS, boolean bForCache)
	{
		try
		{
			Source stylesheet = new StreamSource(fSS) ; 
			if (bForCache)
			{ // if this processor is cached, use XALAN XSLTCompiler, instead of XALAN interpretor
				System.setProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.xsltc.trax.TransformerFactoryImpl") ;
			}
			Templates templ = TransformerFactory.newInstance().newTemplates(stylesheet) ;
			return new XSLTransformer(templ);
		}
		catch (TransformerConfigurationException e)
		{
			return null ;
		}
	}

	public boolean doTransform(Document xmlOutput, OutputStream out)
	{
		try
		{
			Transformer xformer = m_Template.newTransformer() ;
			Source source = new DOMSource(xmlOutput);
			StreamResult result = new StreamResult(out) ;
			xformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			//xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "false");
			xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			xformer.transform(source, result);
			return true ;
		}
		catch (TransformerException e)
		{
			return false ;
		}
	}
}
