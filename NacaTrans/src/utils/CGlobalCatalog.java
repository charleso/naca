/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 3 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package utils;


import java.io.File;
import java.io.FilenameFilter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import semantic.CBaseEntityFactory;
import semantic.CEntityExternalDataStructure;
import semantic.CIgnoreExternalEntity;
import semantic.forms.CEntityResourceFormContainer;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CGlobalCatalog
{
	protected class CProgramFilenameFilter implements FilenameFilter
	{
		protected String m_PrgName = "" ;
		public CProgramFilenameFilter(String name)
		{
			m_PrgName = name ;
		}
		public boolean accept(File dir, String name)
		{
			if (name.equalsIgnoreCase(m_PrgName))
			{
				return true ;
			}
			if (name.equalsIgnoreCase(m_PrgName+".cbl"))
			{
				return true ;
			}
			return false ;
		}
	}
	
	protected Hashtable<String, CEntityExternalDataStructure> m_tabIncludedStructures = new Hashtable<String, CEntityExternalDataStructure>() ;
	//protected Hashtable<String, CEntityResourceFormContainer> m_tabFormContainers = new Hashtable<String, CEntityResourceFormContainer>() ;
	protected Hashtable<String, CIgnoreExternalEntity> m_tabIgnoredExternals = new Hashtable<String, CIgnoreExternalEntity>() ;
	//private Logger m_logger = Transcoder.ms_logger ;
	protected Transcoder m_Transcoder ;
	private String m_csReferenceGroupName = "" ;
	private String m_csResourceGroupName = "" ;
	private String m_csIncludeGroupName = "" ;
	
	
	public void AddIgnoredExternal(CIgnoreExternalEntity e)
	{
		String name = e.GetName() ;
		m_tabIgnoredExternals.put(name, e) ;
	}
	public boolean IsIgnoredExternal(String name)
	{
		try
		{
			return m_tabIgnoredExternals.get(name) != null ;
		}
		catch (Exception e)
		{
			return false ;
		}
	}
	public CGlobalCatalog(Transcoder trans, String grpReferences, String grpResources, String grpIncludes) 
	{
		m_Transcoder = trans ;
		m_csIncludeGroupName = grpIncludes ;
		m_csReferenceGroupName = grpReferences ;
		m_csResourceGroupName = grpResources ;
	}
	
	
	@SuppressWarnings("unchecked")
	public CEntityResourceFormContainer GetFormContainer(String contName, CBaseEntityFactory factory)
	{
		CTransApplicationGroup grpResources = m_Transcoder.getGroup(m_csResourceGroupName) ;
		if (grpResources != null)
		{
			BaseEngine<CEntityResourceFormContainer> engine = grpResources.getEngine() ;
			CTransApplicationGroup grp = new CTransApplicationGroup(engine);
			grp.m_csInputPath = grpResources.m_csInputPath ;
			grp.m_csInterPath = grpResources.m_csInterPath ;
			grp.m_csOutputPath = factory.m_LangOutput.getOutputDir() ;
			CEntityResourceFormContainer ext = GetFormContainer(contName, grp, factory.m_ProgramCatalog.m_Exporter.isResources()) ;

			return ext ;
		}
		return null ;
	}
	@SuppressWarnings("unchecked")
	public CEntityResourceFormContainer GetFormContainer(String contName, CTransApplicationGroup grp, boolean bResources)
	{
		if (m_tabFormContainers.containsKey(contName))
		{
			CEntityResourceFormContainer cont = m_tabFormContainers.get(contName) ;
			return cont ;
		}
		else
		{
			BaseEngine<CEntityResourceFormContainer> engine = grp.getEngine() ;
			CEntityResourceFormContainer ext = engine.doAllAnalysis(contName, "", grp, bResources) ;
			
			if (ext != null)
			{
				CTransApplicationGroup grpResources = m_Transcoder.getGroup(m_csResourceGroupName) ;
				if(grpResources != null)
				{
					String csFilePathXML = grpResources.m_csOutputPath + contName + ".res" ;
					ext.setExportFilePath(csFilePathXML);
				}
			}			
			return ext ;
		}
	}
	
	public CTransApplicationGroup getGroupResources()
	{
		return  m_Transcoder.getGroup(m_csResourceGroupName) ;
	}
	
	protected Hashtable<String, CEntityResourceFormContainer> m_tabFormContainers = new Hashtable<String, CEntityResourceFormContainer>() ; 
	public void RegisterFormContainer(String name, CEntityResourceFormContainer cont)
	{
		if (cont == null)
		{
			m_tabFormContainers.remove(name) ;
		}
		else
		{
			m_tabFormContainers.put(name, cont);
		}
	}

	
	
	
	
	public boolean CheckProgramReference(String prg, boolean bWithDFHCommarea, int nbParameters, boolean bRegisterSubProgram)
	{ 
		if (isCustomSubProgram(prg))
		{
			return true ;
		}
		if (isIgnoreSubProgram(prg))
		{
			return false ;
		}

		if (isProgramReference(prg))
		{
			if (bRegisterSubProgram)
			{
				if (registerSubProgram(prg, bWithDFHCommarea, nbParameters))
				{
					return true ;
				}
				else
				{
					return false ;
				}
			}
			else
			{
				return true ; 
			}
		}
		else
		{
			if (bRegisterSubProgram)
			{
				//m_logger.error("Missing sub-program : "+prg);
			}
			else
			{
//				m_logger.error("Missing program reference : "+prg);
			}
			return false ;
		}
	} 
	public boolean isProgramReference(String cs)
	{
		CTransApplicationGroup grpReferences = m_Transcoder.getGroup(m_csReferenceGroupName) ;
		if (grpReferences != null)
		{
			File dir = new File(grpReferences.m_csInputPath) ;
			FilenameFilter filter = new CProgramFilenameFilter(cs);
			File[] list = dir.listFiles(filter) ;
			if (list.length > 0)
			{
				return true ;
			}
		}
		return false ;
	} 
	public void RegisterExternalDataStructure(CEntityExternalDataStructure structure)
	{
		m_tabIncludedStructures.put(structure.GetName(), structure) ;
	}
	@SuppressWarnings("unchecked")
	public CEntityExternalDataStructure GetExternalDataStructure(String name)
	{
		CIgnoreExternalEntity ign = m_tabIgnoredExternals.get(name);
		if (ign != null)
		{
			return ign ;
		}
		
		CEntityExternalDataStructure ext = m_tabIncludedStructures.get(name);
		if (ext != null)
		{
			return ext ;
		}
		
		// else do transcoding ;
		CTransApplicationGroup grpIncludes = m_Transcoder.getGroup(m_csIncludeGroupName) ;
		if (grpIncludes != null)
		{
			BaseEngine<CEntityExternalDataStructure> engine = grpIncludes.getEngine() ;
			Transcoder.pushTranscodedUnit(name, grpIncludes.m_csInputPath);
			ext = engine.doAllAnalysis(name, "", grpIncludes, false) ;
			Transcoder.popTranscodedUnit();
	//		ext = m_TranscoderEngine.getExternalDataStructure(name, null) ;
			if (ext != null)
			{
				ext.StartExport() ;
			}
			else
			{
				Transcoder.logError("Missing include file : "+name) ;
			}
			
			return ext ;
		}
		return null ;
	}
	protected Hashtable<String, String> m_tabTransID = new Hashtable<String, String>() ;
	public void registerTransID(String TID, String prog)
	{
		m_tabTransID.put(TID, prog);		
	}
	public String GetProgramForTransaction(String transID)
	{
		String p = m_tabTransID.get(transID);
		if (p == null)
		{
			p = "" ;
		}
		return p ;
	}
	public void ExportTransID(Element eRoot, Document doc)
	{
		Enumeration enumere = m_tabTransID.keys() ;
		try
		{
			String cs = (String)enumere.nextElement() ;
			while (cs != null)
			{
				String p = m_tabTransID.get(cs);
				if (p != null)
				{
					Element e = doc.createElement("transid") ;
					e.setAttribute("id", cs) ;
					e.setAttribute("program", p) ;
					eRoot.appendChild(e) ;
				}
				cs = (String)enumere.nextElement() ;
			}
		}
		catch (NoSuchElementException e)
		{
		}
	}
	public void ImportTransID(Element eRoot)
	{
		NodeList lst = eRoot.getElementsByTagName("transid") ;
		for (int i=0; i<lst.getLength(); i++)
		{
			Element e = (Element)lst.item(i);
			String tid = e.getAttribute("id");
			String p = e.getAttribute("program");
			m_tabTransID.put(tid, p);
		}
	}
	/**
	 * @param structure
	 */
	public void AddCustomSubProgram(String name, boolean bIgnore)
	{
		if (bIgnore)
		{
			m_arrIgnoreSubProgram.addElement(name) ;
		}
		else
		{
			m_arrCustomSubProgram.addElement(name) ;
		}
	}
	public boolean isCustomSubProgram(String name)
	{
		return m_arrCustomSubProgram.contains(name) ;
	}
	public boolean isIgnoreSubProgram(String name)
	{
		return m_arrIgnoreSubProgram.contains(name) ;
	}
	protected Vector<String> m_arrCustomSubProgram = new Vector<String>() ;
	protected Vector<String> m_arrIgnoreSubProgram = new Vector<String>() ;
	public boolean CanExportResources(String name)
	{
		String cs = m_tabProgramNotExportingResource.get(name);
		return cs == null ;
	}
	public void RegisterNotExportingResource(String name)
	{
		m_tabProgramNotExportingResource.put(name, name) ;
	}
	protected Hashtable<String, String> m_tabProgramNotExportingResource = new Hashtable<String, String>() ;
	
	
	
	protected class CSubProgramCallDescription
	{
		public String m_SubProgramName = "" ;
		public boolean m_bCalledLikeCICS = false ; // <=> with implicit DFHCOMMAREA
		public int m_nNbParameters = 0 ;	// except DFHCOMMAREA
	}
	public boolean registerSubProgram(String cs, boolean bWithDFHCommarea, int nbParameters)
	{
		CSubProgramCallDescription desc = m_tabSubProgramCall.get(cs) ;
		if (desc == null)
		{
			desc = new CSubProgramCallDescription() ;
			desc.m_SubProgramName = cs ; 
			desc.m_bCalledLikeCICS = bWithDFHCommarea ;
			desc.m_nNbParameters = nbParameters ;
			m_tabSubProgramCall.put(cs, desc) ;
			m_arrSubProgramCalls.add(desc) ;
			return true ;
		}
		else
		{
			if (desc.m_bCalledLikeCICS != bWithDFHCommarea)
			{
				// Transcoder.logError("Bad call to "+cs+" : expecting DFHCOMMAREA parameter");
				return true ;
			}
			else if (nbParameters != desc.m_nNbParameters)
			{
				//m_logger.error("Bad call to "+cs+" : expecting "+desc.m_nNbParameters+" parameters");
				return true ; //return false ;
			}
			else
			{
				return true ;
			}
		}
	}
	protected Hashtable<String, CSubProgramCallDescription> m_tabSubProgramCall = new Hashtable<String, CSubProgramCallDescription>() ;
	protected Vector<CSubProgramCallDescription> m_arrSubProgramCalls = new Vector<CSubProgramCallDescription>() ;


	public void doRegisteredDependencies()
	{
		CTransApplicationGroup grpReferences = m_Transcoder.getGroup(m_csReferenceGroupName) ;
		if (grpReferences != null)
		{
			for (int i=0; i<m_arrSubProgramCalls.size(); i++)
			{
				CSubProgramCallDescription desc = m_arrSubProgramCalls.get(i) ;
				String ssprg = desc.m_SubProgramName ;
				BaseEngine engine = grpReferences.getEngine() ;
				if (!m_arrProgramDone.contains(ssprg))
				{
					engine.doFileTranscoding(ssprg, "", grpReferences, false) ;
				} 
			}
		}
	}

	public void registerProgram(String cs)
	{
		if (!m_arrProgramDone.contains(cs))
		{
			m_arrProgramDone.addElement(cs) ;
		}
	}
	protected Vector<String> m_arrProgramDone = new Vector<String>() ;

	private Hashtable<String, String> m_tabAlreadyCountedItems = new Hashtable<String, String>() ;


	/**
	 * @param filename
	 * @return
	 */
	public boolean canCount(String filename)
	{
		if (!m_tabAlreadyCountedItems.contains(filename))
		{
			m_tabAlreadyCountedItems.put(filename, filename) ;
			return true ;
		}
		return false ;
	}
	
	public void ClearFormContainers()
	{
		m_tabFormContainers.clear() ;
	}
	
}
