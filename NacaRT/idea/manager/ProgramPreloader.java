/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.manager;


import java.io.File;
import java.util.ArrayList;

import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.basePrgEnv.ProgramSequencer;
import nacaLib.programPool.SharedProgramInstanceData;
import nacaLib.programPool.SharedProgramInstanceDataCatalog;
import nacaLib.tempCache.TempCacheLocator;


import jlib.log.AssertException;
import jlib.log.Log;
import jlib.misc.FileSystem;
import jlib.xml.Tag;

public class ProgramPreloader
{
	public ProgramPreloader()
	{
	}
	
	public ArrayList<PreloadProgramSettings> buildArrayPreloadProgramFromList(String csPreLoadProgramFile)
	{
		Log.logNormal("Building array of programs to preload from file " + csPreLoadProgramFile);
		ArrayList<PreloadProgramSettings> arrProgramToPreload = new ArrayList<PreloadProgramSettings>();
		Tag tagList = Tag.createFromFile(csPreLoadProgramFile);
		if(tagList != null)
		{
			Tag tagProgram = tagList.getEnumChild("Program");
			while(tagProgram != null)
			{
				String csName = tagProgram.getVal("Name");
				PreloadProgramSettings preloadProgramSettings = new PreloadProgramSettings(csName);
				boolean b = tagProgram.isValExisting("Qty");
				if(b)
				{
					int nQty = tagProgram.getValAsInt("Qty");
					preloadProgramSettings.setQty(nQty);
				}
				arrProgramToPreload.add(preloadProgramSettings);				
				tagProgram = tagList.getEnumChild();
			}
		}
		Log.logNormal("Done");
		return arrProgramToPreload;
	}
	
	public ArrayList<PreloadProgramSettings> buildArrayPreloadProgramFromDir(String csPreLoadProgramDir)
	{
		ArrayList<PreloadProgramSettings> arrProgramToPreload = new ArrayList<PreloadProgramSettings>(); 
		Log.logNormal("Building array of programs to preload from directory " + csPreLoadProgramDir);
		
		PreloadFileFilter preloadFileFilter = new PreloadFileFilter();  

		File[] arrFile = FileSystem.getFileList(csPreLoadProgramDir, preloadFileFilter);
		for(int n=0; n<arrFile.length; n++)
		{
			File file = arrFile[n];
			String csName = file.getName();
			csName = FileSystem.getNameWithoutExtension(csName);
			long lLastModifiedTime = file.lastModified();
			long lLength = file.length();
			//String csId = csName + "_" + lLastModifiedTime + "_" + lLength; 
			
			PreloadProgramSettings preloadProgramSettings = new PreloadProgramSettings(csName);
			arrProgramToPreload.add(preloadProgramSettings);
		}
		Log.logNormal("Done");
		
		return arrProgramToPreload;
	}
	
	
//	private SharedProgramInstanceData tryDeserialize(String csVarDefCatalogueSerilizationPath, String csProgramName)
//	{
//		SharedProgramInstanceData s = new SharedProgramInstanceData();
//		boolean b = s.deserialize(csVarDefCatalogueSerilizationPath, csProgramName);
//		if(b)
//			return s;
//		return null;		
//	}

//	private void serialize(String csVarDefCatalogueSerilizationPath, String csProgramName, SharedProgramInstanceData sharedProgramInstanceData)
//	{
//		sharedProgramInstanceData.serialize(csVarDefCatalogueSerilizationPath, csProgramName);
//	}
	
	public int preloadProgramsSynchronous(ArrayList<PreloadProgramSettings> arrProgramToPreload, ProgramSequencer seq, String csPreLoadProgramList)
	{
		TempCacheLocator.setTempCache();

		m_sharedProgramInstanceDataCatalog = new SharedProgramInstanceDataCatalog();

		//OnlineSession session = new OnlineSession() ;
	
		Tag tagPreloadList = null;
		if(csPreLoadProgramList != null)
		{
			Log.logNormal("Will keep preloaded programs in file " + csPreLoadProgramList);
			tagPreloadList = new Tag("PreloadedPrograms");
		}
				
		int nNbPrograms = arrProgramToPreload.size();
		int nNbProgramPreloaded = 0;
		Log.logNormal("Beginning to preload " + nNbPrograms + " programs");
		for(int n=0; n<nNbPrograms; n++)
		{
			PreloadProgramSettings preloadProgramSettings = arrProgramToPreload.get(n); 
			String csProgramName = preloadProgramSettings.getName();
			
			int nQty = preloadProgramSettings.getQty();
			if(!csProgramName.equalsIgnoreCase("Pub2000Routines"))
			{
				try
				{
					int hh = 0;
					SharedProgramInstanceData sharedProgramInstanceData = null;
//					if(bSerializePreLoadedProgram)
//						sharedProgramInstanceData = tryDeserialize(csVarDefCatalogueSerilizationPath, csProgramName);
					if(sharedProgramInstanceData == null)// No serialized file, or obsolete one: Must load the class
						sharedProgramInstanceData = seq.forcePreloadSessionProgram(csProgramName, nQty);
					if( sharedProgramInstanceData != null)
					{
						nNbProgramPreloaded++;
						if(tagPreloadList != null)
						{
							Tag tagProgram = tagPreloadList.addTag("Program");
							tagProgram.addVal("Name", csProgramName);
							tagProgram.addVal("KeepCode", true);
							tagProgram.addVal("Qty", nQty);							
						}
					}									
				}
				catch(AssertException e)
				{
					Log.logCritical("Program "+ csProgramName + " throws " + e.getMessage());
				}
				catch(NullPointerException e)
				{
					Log.logCritical("Program "+ csProgramName + " throws " + e.getMessage());
				}
			}
			Log.logNormal("Program preloaded (success/tried/Entries): " + nNbProgramPreloaded + "/" + n + "/" + nNbPrograms);
		}
		
		if(tagPreloadList != null)
		{
			tagPreloadList.exportToFile(csPreLoadProgramList);
		}
	
		TempCacheLocator.relaseTempCache();
		
		if(BaseResourceManager.isGCAfterPreloadPrograms())
		{
			System.gc();
		}
		
		return 0;
	}
	
	//private ArrayList<String> m_arrProgramToPreload = null;
	SharedProgramInstanceDataCatalog m_sharedProgramInstanceDataCatalog = null;
}
