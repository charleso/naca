/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils.CobolTranscoder;

import generate.CJavaEntityFactory;
import generate.java.CJavaExporter;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import jlib.engine.NotificationEngine;
import jlib.misc.FileSystem;
import jlib.misc.StringRef;
import jlib.misc.StringUtil;
import jlib.xml.Tag;
import jlib.xml.TagCursor;
import lexer.CBaseLexer;
import lexer.CTokenList;
import lexer.BMS.CBMSLexer;
import parser.CParser;
import parser.BMS.CBMSParser;
import parser.map_elements.CFieldElement;
import parser.map_elements.CMapElement;
import parser.map_elements.CMapSetElement;
import semantic.forms.CEntityResourceFormContainer;
import utils.CGlobalEntityCounter;
import utils.CObjectCatalog;
import utils.COriginalLisiting;
import utils.CTransApplicationGroup;
import utils.NacaTransAssertException;
import utils.PosLineCol;
import utils.Transcoder;
import utils.TranscoderEngine;

public class BMSTranscoderEngine extends TranscoderEngine<CMapSetElement, CEntityResourceFormContainer>
{
	public static BMSTranscoderEngine ms_BMSTranscoderEngine = null;
	
	public BMSTranscoderEngine()
	{
		if(ms_BMSTranscoderEngine == null)
			ms_BMSTranscoderEngine = this;
	}
	
	protected CFormEnhancer m_FormEnhancer = null ;  


	@Override
	protected CParser<CMapSetElement> doParsing(CTokenList lst)
	{
		CBMSParser parser = new CBMSParser() ;
		if (parser.StartParsing(lst))
		{
			CGlobalEntityCounter.GetInstance().CountBMSFile();
			return parser ;
		}
		else
		{
			Transcoder.logError("BMS parsing failed") ;
			return null ;
		}
	}				

	@Override
	protected CEntityResourceFormContainer doSemanticAnalysis(CParser<CMapSetElement> parser, String fileName, CObjectCatalog cat, CTransApplicationGroup grp, boolean bResources)
	{
		CJavaExporter outjava = new CJavaExporter(cat.m_Listing, fileName, parser.m_CommentContainer, bResources) ;
		CJavaEntityFactory factory = new CJavaEntityFactory(cat, outjava) ;
		CEntityResourceFormContainer eSem = (CEntityResourceFormContainer)parser.GetRootElement().DoSemanticAnalysis(null, factory) ;

		if(m_FormEnhancer != null)
		{	
			m_FormEnhancer.ProcessFormContainer(eSem, bResources) ;
		}	
		
		return eSem ;
	}

	protected @Override CBaseLexer getLexer()
	{
		return new CBMSLexer();
	}

	private CEntityResourceFormContainer doAllAnalysisforResFiles(String filename, String csApplication, CTransApplicationGroup grp, boolean bResources)
	{
		String csFileNameNoExt = FileSystem.getNameWithoutExtension(filename);
		if (csFileNameNoExt.endsWith("S"))
		{
			String map = csFileNameNoExt.substring(0, csFileNameNoExt.length()-1) ;
			CEntityResourceFormContainer cont = m_cat.GetFormContainer(map, grp, bResources) ;
			if (cont != null)
			{
				String fileNameJavaS = grp.m_csOutputPath + csFileNameNoExt + ".java" ;
				CJavaExporter outjavaS = new CJavaExporter(cont.getExporter(), fileNameJavaS) ;
				CJavaEntityFactory factoryS = new CJavaEntityFactory(cont.m_ProgramCatalog, outjavaS) ;
				CEntityResourceFormContainer eSav = cont.MakeSavCopy(factoryS, false) ;
				m_cat.RegisterFormContainer(eSav.GetName(), eSav) ;
				if(m_FormEnhancer != null)
					m_FormEnhancer.ProcessFormContainer(eSav, bResources) ;
				return eSav;
			}
		}

		CEntityResourceFormContainer ext = importRESResource(filename, csApplication, grp, bResources) ;
		if(ext != null)
			m_cat.RegisterFormContainer(filename, ext) ;
		return ext;
	}
	
	@Override
	public CEntityResourceFormContainer doAllAnalysis(String filename, String csApplication, CTransApplicationGroup grp, boolean bResources)
	{
		if (bResources)
		{
			// Export from .res to .java
			filename += ".res";
			return doAllAnalysisforResFiles(filename, csApplication, grp, bResources);
		}
		
		if (filename.endsWith("S"))
		{
			String map = filename.substring(0, filename.length()-1) ;
			CEntityResourceFormContainer cont = m_cat.GetFormContainer(map, grp, bResources) ;
			if (cont != null)
			{
				String fileNameJavaS = grp.m_csOutputPath + filename + ".java" ;
				CJavaExporter outjavaS = new CJavaExporter(cont.getExporter(), fileNameJavaS) ;
				CJavaEntityFactory factoryS = new CJavaEntityFactory(cont.m_ProgramCatalog, outjavaS) ;
				CEntityResourceFormContainer eSav = cont.MakeSavCopy(factoryS, false) ;
				m_cat.RegisterFormContainer(eSav.GetName(), eSav) ;
				m_FormEnhancer.ProcessFormContainer(eSav, bResources) ;
				return eSav;
			}
		}
			
		CEntityResourceFormContainer ext = super.doAllAnalysis(filename, csApplication, grp, bResources) ;
		if (ext != null)
		{
			m_cat.RegisterFormContainer(filename, ext) ;
		}
		return ext ;
	}

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
		Tag tagCobol = eConf.getChild("BMSSpec") ;
		if (tagCobol != null)
		{
			String csFormTransformPath = tagCobol.getVal("FormTransformPath") ; 
			String csGlobalTransformPath = tagCobol.getVal("GlobalFormTransform") ; 
			m_FormEnhancer = new CFormEnhancer(csFormTransformPath, csGlobalTransformPath) ;
		}
		return true ;
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
		return ReplaceExtensionFileName(filename, "bms") ;
	}
	
	private void createDirIsRequired(String csOutputDir, String csApplication)
	{
		if (!csApplication.equals(""))
		{
			csOutputDir += csApplication + "/" ;
			File dir = new File(csOutputDir) ;
			if (!dir.isDirectory())
			{
				dir.mkdirs();
			}
		}
	}
	
	private CEntityResourceFormContainer importRESResource(String inputFileName, String csApplication, CTransApplicationGroup grp, boolean bResources)
	{
		String csOutputFile = generateOutputFileName(inputFileName) ;
		CTransApplicationGroup grpResources = m_cat.getGroupResources();
		String csFullInputFileName = grpResources.m_csOutputPath + inputFileName;
		
		//String csOutputFile = generateOutputFileName(inputFileName) ;
		//createDirIsRequired(grp.m_csOutputPath, csApplication);	// For .res output
		
		Tag tagRoot = Tag.createFromFile(csFullInputFileName);
		if(tagRoot == null)
			return null;
		
		if(grp.m_csOutputPath != null)
			createDirIsRequired(grp.m_csOutputPath, csApplication);	// For .java output
		
		CBMSParser BMSParser = parseRESResource(tagRoot);
		if(BMSParser != null)
		{
			Transcoder.logDebug("Transcoding resource " + inputFileName);
			//exportXMLToFile(BMSParser, "D:/Dev/naca/Pub2000Cobol/Inter/BMS/RS01A05b.xml") ; // Reexport XML

			NotificationEngine engine = new NotificationEngine() ;
			doPopulateSpecialActionHandlers(engine) ;
			COriginalLisiting listing = new COriginalLisiting();
			CObjectCatalog cat = new CObjectCatalog(m_cat, listing, grp.m_eType, engine) ;
			try
			{
				if(grp.m_csOutputPath != null)
				{
					String csJavaOutFileName = FileSystem.appendFilePath(grp.m_csOutputPath + csApplication, ReplaceExtensionFileName(csOutputFile, "java"));
					CEntityResourceFormContainer ext = doSemanticAnalysis(BMSParser, csJavaOutFileName, cat, grp, bResources) ;
					if (ext != null)
					{
						// To reexport .res file, uncomment below
//						String csResOutFileName = grp.m_csOutputPath + ReplaceExtensionFileName(csOutputFile, "res");					
//						ext.setExportFilePath(csResOutFileName);	
//						
//						Transcoder.logInfo("Exporting resource file "+csResOutFileName);
//						ext.MakeXMLOutput(true) ;
						

						m_cat.RegisterFormContainer(inputFileName, ext) ;
						
						// PJD 08/08/2007 Uncomment to export xxx.java screen copy file. These are duplicated files generated twice beforecorrect generation export by    
						//Transcoder.logInfo("Exporting java file "+csJavaOutFileName);
						//ext.StartExport() ;
						
						String fileNameJavaS = FileSystem.appendFilePath(grp.m_csOutputPath + csApplication, ReplaceExtensionFileNameWithSuffix(csOutputFile, "S", "java"));
						//String fileNameJavaS = grp.m_csOutputPath + csApplication + "/" + ReplaceExtensionFileNameWithSuffix(csOutputFile, "S", "java");
						CJavaExporter outjavaS = new CJavaExporter(ext.getExporter(), fileNameJavaS) ;
						CJavaEntityFactory factoryS0 = new CJavaEntityFactory(ext.m_ProgramCatalog, outjavaS) ;
						m_cat = ms_BMSTranscoderEngine.getGlobalCatalog();
						CJavaEntityFactory factoryS = new CJavaEntityFactory(cat, outjavaS) ;
												
						ext.clearSavCopy(factoryS) ;
						
						CEntityResourceFormContainer eSav = ext.MakeSavCopy(factoryS, true) ;	// we are generating directly form a .res file; the name of variables in *S.java file is not very well managed in taht case, so, the flag ... 
						if(ext.GetSavCopy() != null)
						{
							// PJD 08/08/2007 Uncomment to export xxxS.java screen copy file. These are duplicated files generated twice beforecorrect generation export by
//							Transcoder.logInfo("Exporting javaS file "+fileNameJavaS);
//							ext.GetSavCopy().StartExport() ;
						}
					}	
					return ext;
				}
			}
			catch (NacaTransAssertException e)
			{
				Transcoder.logError("Failure while transcoding "+csFullInputFileName+" : "+e.m_csMessage) ;
			}
		}
			
		return null;
	}
	
	private CBMSParser parseRESResource(Tag tagForm)
	{
		Hashtable<String, CMapElement> hashMapsByLanguage = new Hashtable<String, CMapElement>();
		Hashtable<String, PosLineCol> hashPosLineColByLanguage = new Hashtable<String, PosLineCol>();
		
		CBMSParser BMSParser = new CBMSParser();
		CMapSetElement eMapSet = new CMapSetElement("", 0);
		BMSParser.setRoot(eMapSet);
		
		String csName = tagForm.getVal("name");
		csName = csName.toUpperCase();
		
		eMapSet.loadFromRES(csName);
		
		csName = csName.substring(0, 4) + csName.substring(5);
		
		String csLanguages = tagForm.getVal("allLanguages");
		StringRef rcsLanguages = new StringRef(csLanguages); 
		String csLanguage = StringUtil.extractCurrentWord(rcsLanguages, false, ";");
		while(csLanguage != null)
		{
			CMapElement eMap = new CMapElement("", 0);
			eMap.loadFromRES(0, csName, csLanguage);
			eMapSet.AddElement(eMap);
			hashMapsByLanguage.put(csLanguage, eMap);
			
			PosLineCol posLineCol = new PosLineCol();
			hashPosLineColByLanguage.put(csLanguage, posLineCol);
			
			if(rcsLanguages == null)
			{
				csLanguage = null;
				continue;
			}
			csLanguage = StringUtil.extractCurrentWord(rcsLanguages, false, ";");
			if(csLanguage == null)
			{
				csLanguage = rcsLanguages.get();				
				rcsLanguages = null;
			}
			csLanguage = StringUtil.trimLeft(csLanguage, ';');			
		}
		// Skip <pfkeydefine>
		// Skip <pfkeyaction>
		Tag tagFormBody = tagForm.getChild("formbody");
		if(tagFormBody != null)
		{
			Tag tagVBox = tagFormBody.getChild("vbox");
			if(tagVBox != null)
			{
				// Enum all tag hbox
				TagCursor curHBox = new TagCursor();
				Tag tagHBox = tagVBox.getFirstChild(curHBox, "hbox");
				while(tagHBox != null)
				{
					// Enum all edits
					TagCursor curEdit = new TagCursor();
					Tag tagEditTitle = tagHBox.getFirstChild(curEdit);
					while(tagEditTitle != null)
					{
						// Enum all Maps in every language and add the item into the map
						Set<Entry<String, CMapElement> > set = hashMapsByLanguage.entrySet();
						Iterator<Entry<String, CMapElement> > iter = set.iterator();
						while(iter.hasNext())
						{
							Entry<String, CMapElement> entry = iter.next();
							String csLg = entry.getKey();
							CMapElement eMap = entry.getValue();
							PosLineCol posLineCol = hashPosLineColByLanguage.get(csLg);
	
							CFieldElement eField = new CFieldElement("", 0);
							boolean bToAdd = eField.loadTagParameters(posLineCol, tagEditTitle, csLg);
							if(bToAdd)
								eMap.AddElement(eField);
						}
						
						tagEditTitle = tagHBox.getNextChild(curEdit);
					}
					// Found </hbox>
					addTagForClosingHBox(hashPosLineColByLanguage, hashMapsByLanguage);
					tagHBox = tagVBox.getNextChild(curHBox);
				}
			}
		}
		
		return BMSParser;
	}
	
	private void addTagForClosingHBox(Hashtable<String, PosLineCol> hashPosLineColByLanguage, Hashtable<String, CMapElement> hashMapsByLanguage)
	{
		// Enum all Maps in every language and add the item into the map
		Set<Entry<String, CMapElement> > set = hashMapsByLanguage.entrySet();
		Iterator<Entry<String, CMapElement> > iter = set.iterator();
		while(iter.hasNext())
		{
			Entry<String, CMapElement> entry = iter.next();
			String csLg = entry.getKey();
			CMapElement eMap = entry.getValue();
			PosLineCol posLineCol = hashPosLineColByLanguage.get(csLg);

			CFieldElement eField = new CFieldElement("", 0);
			boolean bAdd = eField.setAsClosingHBox(posLineCol);
			if(bAdd)
				eMap.AddElement(eField);
		}
	}
	
	private CBMSParser parseXMLResource(Tag tagCurrent)
	{
		CBMSParser BMSParser = new CBMSParser();
		String csName = tagCurrent.getName();
		if(csName.equals("MapSet"))
		{
			CMapSetElement e = new CMapSetElement("", 0);
			BMSParser.setRoot(e);
			e.loadTagParameters(tagCurrent);
		}
		return BMSParser;
	}	
}
