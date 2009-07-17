/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils.CobolTranscoder;

import generate.CJavaEntityFactory;
import generate.java.CJavaExporter;

import java.util.Hashtable;

import jlib.engine.NotificationEngine;
import jlib.misc.FileSystem;
import jlib.xml.Tag;
import lexer.CBaseLexer;
import lexer.CTokenList;
import lexer.Cobol.CCobolLexer;
import parser.CParser;
import parser.Cobol.CCobolIncludeParser;
import parser.Cobol.elements.CStandAloneWorking;
import semantic.CBaseLanguageEntity;
import semantic.CEntityExternalDataStructure;
import utils.CGlobalEntityCounter;
import utils.CObjectCatalog;
import utils.CTransApplicationGroup;
import utils.CobolNameUtil;
import utils.Transcoder;
import utils.TranscoderEngine;

public class CobolIncludeTranscoderEngine extends TranscoderEngine<CStandAloneWorking, CEntityExternalDataStructure>
{
	

	@Override
	protected CParser<CStandAloneWorking> doParsing(CTokenList lst)
	{
		CParser<CStandAloneWorking> parser = new CCobolIncludeParser() ;
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

	@Override
	protected CEntityExternalDataStructure doSemanticAnalysis(CParser<CStandAloneWorking> parser, String filePath, CObjectCatalog cat, CTransApplicationGroup grp, boolean bResources)
	{
		String fileName = FileSystem.getNameWithoutExtension(filePath) ;
		String finalName = m_tabRulesRenameCopy.get(fileName);
		if (finalName == null)
		{
			finalName = fileName ;
		}
		if (m_cat.isProgramReference(finalName))
		{
			finalName += finalName+"$Copy" ;
		}
		String javafilePath = filePath.replaceAll(fileName, finalName) ;
		CJavaExporter exp = new CJavaExporter(cat.m_Listing, javafilePath, parser.m_CommentContainer, bResources) ;
		CJavaEntityFactory factory = new CJavaEntityFactory(cat, exp) ;
		
		CStandAloneWorking working = parser.GetRootElement() ;
		CEntityExternalDataStructure eFile = working.DoSemanticAnalysis(factory);
		
		String replace = m_tabRulesReplaceCopy.get(fileName) ;
		if (replace == null)
		{
			eFile.Rename(finalName) ;
			if (m_tabRulesRenameCopy.containsKey(fileName))
			{
				String newname = m_tabRulesRenameCopy.get(fileName) ;
				CBaseLanguageEntity[] lsta = eFile.GetChildrenList(null, null) ;
				for (int i=0; i<lsta.length; i++)
				{
					String s = lsta[i].GetName() ; 
					if (s.indexOf(fileName) >= 0)
					{
						s = s.replaceAll(fileName, newname) ;
						lsta[i].SetDisplayName(s) ;
					}
				}
			}
//			eFile.StartExport() ;
			return eFile  ;
		}
		else
		{
			Transcoder.logDebug("Include " + fileName + " is replaced by " + replace);
			Transcoder.popTranscodedUnit();
			Transcoder.pushTranscodedUnit(replace, grp.m_csInputPath);
			CEntityExternalDataStructure newext = doAllAnalysis(replace, "", grp, false) ;
			if(newext == null)
			{
				Transcoder.logError("File not found : "+replace); 
				return null;
			}
			eFile.Rename(newext.GetName()) ;
			CBaseLanguageEntity[] lstb = newext.GetChildrenList(null, null) ;
			CBaseLanguageEntity[] lsta = eFile.GetChildrenList(null, null) ;
			for (int i=0; i<lsta.length && i<lstb.length; i++)
			{
				String s = lsta[i].GetName() ; 
				lsta[i].SetDisplayName(lstb[i].GetDisplayName()) ;
			}
			return newext ;
		}
	}
	
	@Override
	protected CBaseLexer getLexer()
	{
		return new CCobolLexer();
	}

	private void setReplaceRule(String name, String replace)
	{
		m_tabRulesReplaceCopy.put(name, replace);	
	}

	private void setRenameRule(String name, String rename)
	{
		m_tabRulesRenameCopy.put(name, rename);
	}

	private Hashtable<String, String> m_tabRulesReplaceCopy = new Hashtable<String, String>() ;
	private Hashtable<String, String> m_tabRulesRenameCopy = new Hashtable<String, String>() ;


	@Override
	protected void doLogs(String csInput, String csOutput)
	{
		// Nothing
		
	}

	@Override
	protected void doPopulateSpecialActionHandlers(NotificationEngine engine)
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see utils.TranscoderEngine#CustomInit(jlib.xml.Tag)
	 */
	@Override
	public boolean CustomInit(Tag eConf)
	{
		int nb = m_RulesManager.getNbRules("renameCopy") ;
		for (int i=0; i<nb; i++)
		{
			Tag e = m_RulesManager.getRule("renameCopy", i) ;
			String name = e.getVal("name") ;
			String rename = e.getVal("rename") ;
			String replace = e.getVal("replace") ;
			if (!replace.equals(""))
			{
				setReplaceRule(name, replace) ;
			}
			if (!rename.equals(""))
			{
				setRenameRule(name, rename) ;
			}
		}
		return true ;
	}

	/**
	 * @see utils.TranscoderEngine#generateOutputFileName(java.lang.String)
	 */
	@Override
	protected String generateOutputFileName(String filename)
	{
		return ReplaceExtensionFileName(CobolNameUtil.fixJavaName(filename), "java") ;
	}

	/**
	 * @see utils.TranscoderEngine#generateInputFileName(java.lang.String)
	 */
	@Override
	protected String generateInputFileName(String filename)
	{
		return filename;
	}

	
}
