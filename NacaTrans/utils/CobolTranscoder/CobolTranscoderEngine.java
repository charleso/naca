/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 11 mai 2005
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package utils.CobolTranscoder;

import generate.CJavaEntityFactory;
import generate.java.CJavaExporter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jlib.engine.NotificationEngine;
import jlib.xml.Tag;
import lexer.CBaseLexer;
import lexer.CTokenList;
import lexer.Cobol.CCobolLexer;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CParser;
import parser.Cobol.CCobolParser;
import parser.Cobol.elements.CProgram;
import semantic.CBaseEntityFactory;
import semantic.CEntityClass;
import utils.CGlobalEntityCounter;
import utils.CObjectCatalog;
import utils.CSpecialActionContainer;
import utils.CTransApplicationGroup;
import utils.Transcoder;
import utils.TranscoderEngine;


/**
 * @author U930CV
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CobolTranscoderEngine extends TranscoderEngine<CProgram, CEntityClass>
{

	public boolean CustomInit(Tag eConf)
	{
		Transcoder.logInfo("Do CSD Registering...");
		DoCSDRegistering(eConf) ;

		Transcoder.logInfo("Init Global Entities...");
		CJavaEntityFactory factory = new CJavaEntityFactory(null, null) ;
		InitGlobalEntitiesFromRules(factory) ;
		factory.InitCustomGlobalEntities(m_cat) ;

		return true ;
	}
	/**
	 * 
	 */

	/**
	 * @param eConf
	 */
	private void DoCSDParsing(Tag eConf)
	{
		Tag eCSD = eConf.getChild("CSD") ;
		if (eCSD != null)
		{
			String csdFile = eCSD.getVal("File");
			String csdOutput = eCSD.getVal("Output");
			if (!csdFile.equals("") && !csdOutput.equals(""))
			{
				DoCSDParsing(csdFile, csdOutput) ;
			}
		}
	}
	
	/**
	 * @param eConf
	 */
	private void DoCSDRegistering(Tag eConf)
	{
		Tag eCSD = eConf.getChild("CSD") ;
		if (eCSD != null)
		{
			String csdOutput = eCSD.getVal("Output");
			if (!csdOutput.equals(""))
			{
				DoCSDRegistering(csdOutput) ;
			}
		}
	}


	/**
	 * @param appName
	 * @return
	 */
	protected @Override CBaseLexer getLexer()
	{
		return new CCobolLexer();
	}

	protected CParser<CProgram> doParsing(CTokenList lst)
	{
		CParser<CProgram> parser = new CCobolParser() ;
		if (parser.StartParsing(lst))
		{
			CGlobalEntityCounter.GetInstance().CountCobolFile();
			return parser ;
		}
		else
		{
			Transcoder.logError("COBOL parsing failed") ;
			return null ;
		}
	}				


	private void DoCSDParsing(String csdFilePath, String xmlFilePath)
	{
		try
		{
			BufferedReader inputStream = new BufferedReader(new FileReader(csdFilePath));
			String var = inputStream.readLine();
			while (var != null)
			{
				if (var.startsWith("DEFINE TRANSACTION"))
				{
					String TID = var.substring(19, 23) ;
					int i = 0 ;
					do
					{
						i = var.indexOf("PROGRAM(") ;
						if (i>0)
						{
							int f = var.indexOf(")", i+8);
							if (f>0)
							{
								String prog = var.substring(i+8, f) ;
								//m_cat.registerTransID(TID, prog) ;
							}
						}
						var = inputStream.readLine() ;
					} while (var != null && i == -1) ;
				}
				else
				{
					var = inputStream.readLine();
				}
			}
		}
		catch (FileNotFoundException e)
		{
			return ;
		}
		catch (IOException e)
		{
			return ;
		}
		try
		{
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument() ;
			Element eRoot = doc.createElement("root") ;
			doc.appendChild(eRoot);
			m_cat.ExportTransID(eRoot, doc);
			Source source = new DOMSource(doc);
			FileOutputStream file = new FileOutputStream(xmlFilePath);
			StreamResult res = new StreamResult(file) ;
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.setOutputProperty(OutputKeys.ENCODING, "ISO8859-1");
			xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			xformer.transform(source, res);
		}
		catch (DOMException e1)
		{
		}
		catch (FileNotFoundException e1)
		{
		}
		catch (TransformerConfigurationException e1)
		{
		}
		catch (IllegalArgumentException e1)
		{
		}
		catch (ParserConfigurationException e1)
		{
		}
		catch (FactoryConfigurationError e1)
		{
		}
		catch (TransformerFactoryConfigurationError e1)
		{
		}
		catch (TransformerException e1)
		{
		}
	}

	private void DoCSDRegistering(String xmlFilePath)
	{
		try
		{
			Tag docCSD = Tag.createFromFile(xmlFilePath) ;
			if (docCSD != null)
			{
				
				Collection<Tag> lst = docCSD.getChilds("transid") ;
				for (Tag e : lst)
				{
					String id = e.getVal("id");
					String program = e.getVal("program");
					if (id != null && !id.equals("") && program != null && !program.equals(""))
					{
						m_cat.registerTransID(id, program);
					}
				}
			}
		}
		catch (Exception e)
		{			
		}
		
	}
	
	protected CEntityClass doSemanticAnalysis(CParser<CProgram> parser, String fileName, CObjectCatalog cat, CTransApplicationGroup grp, boolean bResources)
	{
		CJavaExporter out = new CJavaExporter(cat.m_Listing, fileName, parser.m_CommentContainer, bResources) ;
		cat.setExporter(out) ;
		CJavaEntityFactory factory = new CJavaEntityFactory(cat, out) ;
		InitCustomCICSEntriesFromRules(factory) ;
		factory.InitCustomCICSEntities();
		CProgram prg = parser.GetRootElement() ;
		CEntityClass eSem = prg.DoSemanticAnalysis(factory) ;
		parser.m_CommentContainer.DoSemanticAnalysis(factory) ;
		DoAlgorythmicAnalysis(cat, factory);
		
		return eSem ;
	}
	
	private void InitGlobalEntitiesFromRules(CBaseEntityFactory factory)
	{
		int nb = m_RulesManager.getNbRules("ignoredCopy") ;
		for (int i=0; i<nb; i++)
		{
			Tag e = m_RulesManager.getRule("ignoredCopy", i) ;
			String name = e.getVal("copyName") ;
			m_cat.AddIgnoredExternal(factory.NewIgnoreExternalEntity(name)) ;
		}

		nb = m_RulesManager.getNbRules("customSubProgram") ;
		for (int i=0; i<nb; i++)
		{
			Tag e = m_RulesManager.getRule("customSubProgram", i) ;
			String name = e.getVal("subProgram") ;
			boolean bIgnore = e.getValAsBoolean("ignore") ;
			m_cat.AddCustomSubProgram(name, bIgnore);
		}

		nb = m_RulesManager.getNbRules("SpecialConstantValue") ;
		for (int i=0; i<nb; i++)
		{
			Tag e = m_RulesManager.getRule("SpecialConstantValue", i) ;
			String value = e.getVal("value") ;
			String constant = e.getVal("constant") ;
			char[] arr = value.toCharArray() ;
			char[] b = new char[arr.length/2] ;
			for (int j=0; j<arr.length; j+=2)
			{
				String s = "" + arr[j] + arr[j+1] ;
				Integer in = Integer.valueOf(s, 16) ;
				b[j/2] = (char) in.intValue() ;
			}
			String text = new String(b) ;
			factory.addSpecialConstantValue(text, constant) ;
		}
	}
	
	/**
	 * 
	 */
	private void InitCustomCICSEntriesFromRules(CBaseEntityFactory factory)
	{
		int nb = m_RulesManager.getNbRules("ignoreEntity") ;
		for (int i=0; i<nb; i++)
		{
			Tag e = m_RulesManager.getRule("ignoreEntity", i) ;
			String name = e.getVal("name") ;
			factory.NewIgnoreEntity(name) ;
		}
		nb = m_RulesManager.getNbRules("environmentVariable") ;
		for (int i=0; i<nb; i++)
		{
			Tag e = m_RulesManager.getRule("environmentVariable", i) ;
			String name = e.getVal("name") ;
			String read = e.getVal("methodeRead") ;
			String write = e.getVal("methodeWrite") ;
			boolean bNumeric = e.getValAsBoolean("Numeric") ;
			factory.NewEntityEnvironmentVariable(name, read, write, bNumeric) ;
		}
		nb = m_RulesManager.getNbRules("keyPressed") ;
		for (int i=0; i<nb; i++)
		{
			Tag e = m_RulesManager.getRule("keyPressed", i) ;
			String key = e.getVal("keyName") ;
			String alias = e.getVal("CICSAlias") ;
			factory.NewEntityKeyPressed(alias, key) ;
		}
		nb = m_RulesManager.getNbRules("routineEmulation") ;
		for (int i=0; i<nb; i++)
		{
			Tag e = m_RulesManager.getRule("routineEmulation", i) ;
			String name = e.getVal("routine") ;
			String method = e.getVal("method") ;
			factory.m_ProgramCatalog.RegisterRoutineEmulation(name, method) ;
		}
		
		nb = m_RulesManager.getNbRules("NoExportResource") ;
		for (int i=0; i<nb; i++)
		{
			Tag e = m_RulesManager.getRule("NoExportResource", i) ;
			String name = e.getVal("program") ;
			m_cat.RegisterNotExportingResource(name);
		}
		nb = m_RulesManager.getNbRules("SpecialConstantValue") ;
		for (int i=0; i<nb; i++)
		{
			Tag e = m_RulesManager.getRule("SpecialConstantValue", i) ;
			String value = e.getVal("value") ;
			String constant = e.getVal("constant") ;
			char[] arr = value.toCharArray() ;
			char[] b = new char[arr.length/2] ;
			for (int j=0; j<arr.length; j+=2)
			{
				String s = "" + arr[j] + arr[j+1] ;
				Integer in = Integer.valueOf(s, 16) ;
				b[j/2] = (char) in.intValue() ;
			}
			String text = new String(b) ;
			factory.addSpecialConstantValue(text, constant) ;
		}
	}
	
	protected void DoAlgorythmicAnalysis(CObjectCatalog cat, CBaseEntityFactory factory)
	{
		CSpecialActionContainer container = new CSpecialActionContainer() ;
		container.DoExplicitDFHCommarea(cat, factory) ;
		container.DoRenameSubPrograms(cat, factory);
		container.DoClearConstantAttributes(cat, factory) ;
		
		Tag t = m_RulesManager.getRule("ReduceMaps") ;
		if (t != null)
		{
			boolean bReduce = t.getValAsBoolean("active") ;
			if (bReduce)
			{
				container.DoClearSymbolicMap(cat, factory) ;
			}
		}
		container.DoRegisterPFKeys(cat) ;
		container.DoCheckEditName(cat) ;
		container.DoReplaceCall_RS7ZPA04(cat, factory) ;
		container.DoReplacePerformThrough(cat, factory) ;
		container.DoReplaceMapName(cat, factory) ;
		container.DoSimplifyDFHCommArea(cat) ;
		container.DoSimplifyFDVariableZones(cat, factory) ;
		//container.DoReduceSections(cat, factory) ;
	}
	

	@Override
	protected void doLogs(String csInput, String csOutput)
	{
		Transcoder.logInfo("Start transcoding file to "+ csOutput);
	}

	@Override
	protected void doPopulateSpecialActionHandlers(NotificationEngine engine)
	{
		engine.RegisterNotificationHandler(new SpecialCobolActionNotifHandler()) ;		
	}


	/**
	 * @see utils.TranscoderEngine#generateOutputFileName(java.lang.String)
	 */
	@Override
	protected String generateOutputFileName(String filename)
	{
		return ReplaceExtensionFileName(filename, "java") ;
	}
	/**
	 * @see utils.TranscoderEngine#generateInputFileName(java.lang.String)
	 */
	@Override
	protected String generateInputFileName(String filename)
	{
		return ReplaceExtensionFileName(filename, "cbl");
	}
}
