/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 5 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jlib.engine.NotificationEngine;
import jlib.xml.Tag;
import lexer.CBaseLexer;
import lexer.CBaseToken;
import lexer.CTokenList;

import org.w3c.dom.Document;

import parser.CBaseElement;
import parser.CParser;
import semantic.CBaseLanguageEntity;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class TranscoderEngine<T_Elem extends CBaseElement, T_Entity extends CBaseLanguageEntity> extends BaseEngine<T_Entity>
{
	protected TranscoderEngine()
	{
	}
	
	public boolean MainInit(Tag eConf)
	{
		String csCallGroupName  = eConf.getVal("ReferenceGroupName") ;
		String csIncludeGroupName = eConf.getVal("IncludeGroupName") ;
		String csResGroupName  = eConf.getVal("ResourceGroupName") ;
		m_cat = new CGlobalCatalog(m_Transcoder, csCallGroupName, csResGroupName, csIncludeGroupName) ;
		return CustomInit(eConf) ;
	}
	
	/**
	 * @param conf
	 * @return
	 */
	protected abstract boolean CustomInit(Tag conf) ;
	
	protected NotificationEngine m_NotificationEngine = new NotificationEngine() ;
	protected CGlobalCatalog m_cat = null ;

	public CGlobalCatalog getGlobalCatalog()
	{
		return m_cat ;
	}
	
	@Override
	public void doFileTranscoding(String filename, String csApplication, CTransApplicationGroup grp, boolean bResources)
	{
		T_Entity eSem = doAllAnalysis(filename, csApplication,  grp, bResources) ;
		if (eSem == null)
		{
			return ;
		}
		
		eSem.StartExport() ;
		if (m_cat.CanExportResources(eSem.GetProgramName()))
		{
			eSem.m_ProgramCatalog.ExportRegisteredFormContainer(bResources) ;
		}
		eSem.Clear();
		
		m_cat.registerProgram(filename);
		Transcoder.dumpUnboundReferences();
	}
	
	public T_Entity doAllAnalysis(String filename, String csApplication, CTransApplicationGroup grp, boolean bResources)
	{
		String outname = generateOutputFileName(filename) ;
		String csOutputDir = grp.m_csOutputPath ;
		if (!csApplication.equals(""))
		{
			csOutputDir += csApplication + "/" ;
			File dir = new File(csOutputDir) ;
			if (!dir.isDirectory())
			{
				if (!dir.mkdirs())
				{
					return null ;
				}
			}
		}
		
		doLogs(grp.m_csInputPath + filename, csOutputDir + outname) ;
		COriginalLisiting listing = new COriginalLisiting() ;
		CTokenList lst = doLexing(grp.m_csInputPath + filename, listing);
		if (lst != null)
		{			
			Transcoder.logDebug("Transcoding " + filename);
			CParser<T_Elem> p = doParsing(lst) ;
			if (p!= null)
			{
				if(m_Transcoder.mustGenerate())
				{
					NotificationEngine engine = new NotificationEngine() ;
					doPopulateSpecialActionHandlers(engine) ;
					CObjectCatalog cat = new CObjectCatalog(m_cat, listing, grp.m_eType, engine) ;
					try
					{
						T_Entity eSem = doSemanticAnalysis(p, csOutputDir + outname, cat, grp, bResources) ;
						return eSem ;
					}
					catch (NacaTransAssertException e)
					{
						Transcoder.logError("Failure while transcoding "+filename+" : "+e.m_csMessage) ;
					}
					p.Clear() ;
					lst.Clear();
					cat.Clear() ;
				}
			}
		}
		return null ;
	}
	
	/**
	 * @param filename
	 * @return
	 */
	protected abstract String generateOutputFileName(String filename) ;
	protected abstract String generateInputFileName(String filename) ;

	protected void ExportTokens(CTokenList lst, String filename)
	{
		try
		{
			if (lst != null && lst.GetNbTokens()>0)
			{
				FileOutputStream file = new FileOutputStream(filename) ;
				PrintStream output = new PrintStream(file, true) ;
				lst.StartIter() ;
				CBaseToken tok = lst.GetCurrentToken() ;
				int nCurLine = tok.getLine() ;
				output.print("" + nCurLine + ":") ;
				while (tok != null)
				{
					//if (tokEntry.getLine() > nCurLine)
					if (tok.m_bIsNewLine)
					{
						output.println("") ;
						nCurLine = tok.getLine() ;
						output.print("" + nCurLine + ":") ;
					}
					output.print(tok.toString());
					tok = lst.GetNext() ;
				}
			}
			else
			{
				Transcoder.logError("No tokens to export for "+filename);
			}
		}
		catch (Exception e)
		{
			Transcoder.logError(e.toString() + "\n" + e.getStackTrace());
		}
	}

	
	protected abstract void doPopulateSpecialActionHandlers(NotificationEngine engine) ;

	protected abstract void doLogs(String csInput, String csOutput) ;

	protected String ReplaceExtensionFileName(String filename, String ext)
	{
		int nPos = filename.lastIndexOf('.') ;
		if (nPos > 0)
		{
			return filename.substring(0, nPos) + "." + ext ;	// Modification PJD 14/06/07; was return filename.substring(0, nPos) + ext ;  
		}
		else
		{
			return filename + "." + ext ;
		}
	}
	
	protected String ReplaceExtensionFileNameWithSuffix(String filename, String csSuffix, String ext)
	{
		int nPos = filename.lastIndexOf('.') ;
		if (nPos > 0)
		{
			return filename.substring(0, nPos) + csSuffix + "." + ext ;	// Modification PJD 14/06/07; was return filename.substring(0, nPos) + ext ;  
		}
		else
		{
			return filename + csSuffix + "." + ext ;
		}
	}

	protected CTokenList doLexing(String filename, COriginalLisiting cat)
	{
		CTokenList lst = null ;
		String csFullFileName = generateInputFileName(filename);
		try
		{
			CBaseLexer lexer = getLexer() ;			
			FileInputStream file = new FileInputStream(csFullFileName) ;
			boolean b = lexer.StartLexer(file, cat) ;
			if (b)
			{
				if (m_cat.canCount(filename))
				{
					lexer.DoCount() ;
				}
				lexer.DoCount() ;
				lst = lexer.GetTokenList() ;
			}
			else
			{
				Transcoder.logError("Lexing failed"); 
			}
		}
		catch (FileNotFoundException e)
		{
			//Transcoder.error("File not found : "+csFullFileName); 
			return null ;
		}
		catch (Exception e)
		{
			Transcoder.logError(e.toString() + "\n" + e.getStackTrace());
		}
		return lst ;
	}
	

	protected abstract CParser<T_Elem> doParsing(CTokenList lst) ;

	protected void exportXMLToFile(CParser<T_Elem> parser, String csFileOut)
	{
		Document doc = parser.Export() ;
		Tag tag = new Tag();
		tag.setDoc(doc);
		tag.exportToFile(csFileOut);	
	}
	
	protected void ExportParser(CParser<T_Elem> parser, String filename)
	{
		try
		{
			Document doc = parser.Export() ;
			if (doc != null)
			{
				Source source = new DOMSource(doc);
				FileOutputStream file = new FileOutputStream(filename);
				StreamResult res = new StreamResult(file) ;
				Transformer xformer = TransformerFactory.newInstance().newTransformer();
				xformer.setOutputProperty(OutputKeys.ENCODING, "ISO8859-1");
				xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				xformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
				xformer.transform(source, res);
			}
		}
		catch (FileNotFoundException e)
		{
		}
		catch (TransformerConfigurationException e)
		{
		}
		catch (TransformerException e)
		{
			Transcoder.logError(e.toString() + "\n" + e.getStackTrace());
		}	
	}
	
	protected abstract T_Entity doSemanticAnalysis(CParser<T_Elem> parser, String fileName, CObjectCatalog cat, CTransApplicationGroup grp, boolean bResources) ;
	
	/**
	 * Lexer Factory
	 */
	protected abstract CBaseLexer getLexer() ;

}
