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
import jlib.log.Log;
import jlib.misc.FileSystem;
import jlib.misc.StringRef;
import jlib.xml.Tag;
import lexer.CBaseLexer;
import lexer.CBaseToken;
import lexer.CTokenList;

import org.w3c.dom.Document;

import parser.CBaseElement;
import parser.CParser;
import semantic.CBaseLanguageEntity;
import utils.CTransApplicationGroup.EProgramType;
import utils.SQLSyntaxConverter.SQLSyntaxConverter;
import utils.modificationsReporter.Reporter;

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
	
	
	public CTokenList doTextTranscoding(String csText, boolean bFromSource)
	{
		COriginalLisiting listing = new COriginalLisiting() ;
		CTokenList lst = doLexingString(csText, listing, EProgramType.TYPE_BATCH, bFromSource);
		return lst;
	}
	
	@Override
	public void doFileTranscoding(String filename, String csApplication, CTransApplicationGroup grp, boolean bResources)
	{
		Reporter.setCurrentFileName(filename);
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
		Reporter.resetCurrentFileName();
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
		if(filename.equals("ZZXAF12"))
		{
			int gg = 0;
		}
		CTokenList lstSource = doLexing(filename, grp.m_csInputPath, listing, grp.m_eType);
		if(lstSource == null)
		{
			Log.logImportant("Could not lex file "+filename);
		}
		CTokenList lst = null;
		
		SQLSyntaxConverter sqlSyntaxConverter = Transcoder.getSQLSyntaxConverter();
		if(sqlSyntaxConverter != null)
			lst = sqlSyntaxConverter.updateSQLStatements(filename, lstSource);
		else
			lst = lstSource;

		String csFullFileName = generateInputFileName(filename);
		if (lst != null)
		{						
			if(filename.equals("AFCFERR"))
			{
				int gg =0 ;
			}
			Reporter.setCurrentFileName(filename);
			Transcoder.logInfo("Transcoding " + filename);
			ExportTokens(lst, grp.m_csInterPath + ReplaceExtensionFileName(filename, "lex"));
			CParser<T_Elem> p = doParsing(lst) ;
			if (p!= null)
			{
				if(m_Transcoder.mustGenerate())
				{
					String csFileNameOut = grp.m_csInterPath + ReplaceExtensionFileName(filename, "xml");
					exportXMLToFile(p, csFileNameOut) ;
					//ExportParser(p, csFileNameOut) ;
					NotificationEngine engine = new NotificationEngine() ;
					doPopulateSpecialActionHandlers(engine) ;
					CObjectCatalog cat = new CObjectCatalog(m_cat, listing, grp.m_eType, engine) ;
					try
					{
						T_Entity eSem = doSemanticAnalysis(p, csOutputDir + outname, cat, grp, bResources) ;
						Reporter.resetCurrentFileName();
						return eSem ;
					}
					catch (NacaTransAssertException e)
					{
						e.printStackTrace();
						Transcoder.logError("Failure while transcoding "+filename+" : "+e.m_csMessage) ;
					}
					p.Clear() ;
					lst.Clear();
					cat.Clear() ;
				}
			}
			Reporter.resetCurrentFileName();
		}
		
		return null ;
	}
	
	/**
	 * @param filename
	 * @return
	 */
	protected abstract String generateOutputFileName(String filename) ;
	public abstract String generateInputFileName(String filename) ;

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
			e.printStackTrace();
			Transcoder.logError(e.toString() + "\n" + e.getStackTrace());
		}
	}

	
	protected abstract void doPopulateSpecialActionHandlers(NotificationEngine engine) ;

	protected abstract void doLogs(String csInput, String csOutput) ;


	protected String ReplaceExtensionFileName(String csPathFilenameExt/*filename*/, String ext)
	{
		StringRef rcsPath = new StringRef();
		StringRef rcsExt = new StringRef();
		String csPathFileName = FileSystem.splitFilePathExt(csPathFilenameExt, rcsPath, rcsExt);
		if(rcsPath.get() != null)
			csPathFileName = FileSystem.appendFilePath(rcsPath.get(), csPathFileName);
		String cs = csPathFileName + "." + ext ;
		return cs;
		
		/*
		int nPos = csFileName.lastIndexOf('.') ;
		if (nPos > 0)
		{
			return csPathFilename.substring(0, nPos) + "." + ext ;	// Modification PJD 14/06/07; was return filename.substring(0, nPos) + ext ;  
		}
		else
		{
			return csPathFilename + "." + ext ;
		}
		*/
	}
	
	protected String ReplaceExtensionFileNameWithSuffix(String csPathFilenameExt/*filename*/, String csSuffix, String ext)
	{
		StringRef rcsPath = new StringRef();
		StringRef rcsExt = new StringRef();
		String csPathFileName = FileSystem.splitFilePathExt(csPathFilenameExt, rcsPath, rcsExt);
		if(rcsPath.get() != null)
			csPathFileName = FileSystem.appendFilePath(rcsPath.get(), csPathFileName);
		
		String cs;
		if(rcsExt.get() != null)
			cs = csPathFileName + csSuffix + "." + ext ;
		else
			cs = csPathFileName + "." + ext ;
		return cs;
//		int nPos = filename.lastIndexOf('.') ;
//		if (nPos > 0)
//		{
//			return filename.substring(0, nPos) + csSuffix + "." + ext ;	// Modification PJD 14/06/07; was return filename.substring(0, nPos) + ext ;  
//		}
//		else
//		{
//			return filename + csSuffix + "." + ext ;
//		}
	}
	
	protected CTokenList doLexing(String csFileName, String csGroupSourcePath, COriginalLisiting cat, EProgramType eProgramType)
	{
		String filename = csGroupSourcePath + csFileName; 
		CTokenList lst = null ;
		String csFullFileName = generateInputFileName(filename);
		
		MissingFileManager missingFileManager = MissingFileManager.getInstance();
		if(!FileSystem.exists(csFullFileName))
		{
			missingFileManager.addFileNotFound(csFileName, csFullFileName);
			return null ;
		}
		missingFileManager.reset();
		
		CBaseLexer lexer = getLexer();
		lexer.setCopyCodeInliningSupport(m_cat);
		
		StringBuilder sb = FileSystem.readWholeFile(csFullFileName);
		
		FileContentBuffer buffer = new FileContentBuffer(eProgramType);
		buffer.initialLoad(sb);
		
		boolean b = lexer.StartLexer(buffer, cat, eProgramType) ;
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
		
		if(NacaTransLauncher.getDumpInlinedSourceFile())
		{
			csFullFileName += ".dumpInlined";
			buffer.dumpIfNeeded(csFullFileName);
		}
		return lst ;
	}
	
	protected CTokenList doLexingString(String csText, COriginalLisiting cat, EProgramType eProgramType, boolean bFromSource)
	{
		CBaseLexer lexer = getLexer();
		lexer.setModeString();
		
		CTokenList lst = null ;
		lexer.setCopyCodeInliningSupport(m_cat);
		
		FileContentBuffer buffer = new FileContentBuffer(eProgramType);
		if(!bFromSource)	// The text is not formatted using Cobol source conventions 
		{
			StringBuilder sb = new StringBuilder("      " + csText);
			buffer.initialLoad(sb);
		}
		else	// The text is formatted using Cobol source conventions
		{
			// Remove line heder (columns 0 to 5) and >= 72
			StringBuilder sb = new StringBuilder(csText);
			buffer.initialLoad(sb);
			buffer.removeCobolLineFormatting();
		}
		
		boolean b = lexer.StartLexer(buffer, cat, eProgramType) ;
		if (b)
		{
			lst = lexer.GetTokenList() ;
			return lst ;
		}
		Transcoder.logError("Text lexing failed");
		return null;
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
			e.printStackTrace();
		}
		catch (TransformerConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (TransformerException e)
		{
			e.printStackTrace();
			Transcoder.logError(e.toString() + "\n" + e.getStackTrace());
		}	
	}

	
	protected abstract T_Entity doSemanticAnalysis(CParser<T_Elem> parser, String fileName, CObjectCatalog cat, CTransApplicationGroup grp, boolean bResources) ;
	
	/**
	 * Lexer Factory
	 */
	protected abstract CBaseLexer getLexer() ;

}
