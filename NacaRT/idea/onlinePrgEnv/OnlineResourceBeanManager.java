/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.onlinePrgEnv;

import idea.view.XMLMerger;
import idea.view.XMLMergerManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jlib.classLoader.JarEntries;
import jlib.classLoader.JarItemEntry;
import jlib.jmxMBean.BaseCloseMBean;
import jlib.log.AssertException;
import jlib.log.Log;
import jlib.misc.FileSystem;
import jlib.misc.StopWatch;
import jlib.misc.StringUtil;
import jlib.xml.XMLUtil;

public class OnlineResourceBeanManager extends BaseCloseMBean
{
	private Hashtable<String, Document> m_tabResources = null;

	private Hashtable<String, File> m_tabResourceFiles = null;

	private Hashtable<String, Document> m_tabResourceStruct = null;

	private OnlineResourceManager m_resourceManager = null;

	OnlineResourceBeanManager(OnlineResourceManager resourceManager)
	{
		super("_ Resources files", "_ Resources files");
		m_resourceManager = resourceManager;
	}

	protected void buildDynamicMBeanInfo()
	{
		addAttribute("NbFiles", getClass(), "NbFiles", int.class);
		addAttribute("NbResources", getClass(), "NbResources", int.class);
		addAttribute("NbDocuments", getClass(), "NbDocuments", int.class);

		addOperation("Reload ressource files", getClass(), "reloadResourcesFiles"); // Boolean.TYPE);
	}

	public int getNbFiles()
	{
		m_unloadRWLock.readLock().lock();
		int n = 0;
		if (m_tabResourceFiles != null)
			n = m_tabResourceFiles.size();
		m_unloadRWLock.readLock().unlock();
		return n;
	}

	public int getNbResources()
	{
		m_unloadRWLock.readLock().lock();
		int n = 0;
		if (m_tabResources != null)
			n = m_tabResources.size();
		m_unloadRWLock.readLock().unlock();
		return n;
	}

	public int getNbDocuments()
	{
		m_unloadRWLock.readLock().lock();
		int n = 0;
		if (m_tabResourceStruct != null)
			n = m_tabResourceStruct.size();
		m_unloadRWLock.readLock().unlock();
		return n;
	}

	public void reloadResourcesFiles()
	{
		Log.logImportant("reloadResourcesFiles started");
		m_unloadRWLock.writeLock().lock(); // Get exclusive lock

		if (m_tabResourceStruct != null)
			m_tabResourceStruct.clear();
		if (m_tabResources != null)
			m_tabResources.clear();
		if (m_tabResourceFiles != null)
			m_tabResourceFiles.clear();

		LoadResourceCache(OnlineResourceManager.ms_bCacheResourceFiles);

		m_unloadRWLock.writeLock().unlock(); // Release exclusive lock;
												// unlocking optinal thread
												// waiting to obtain read lock
												// in getUnusedInstance()
		Log.logImportant("reloadResourcesFiles ended");
	}

	void setJarXMLFile(String csJarXMLFile)
	{
		m_csJarXMLFile = csJarXMLFile;
	}
	
	private int doLoadResourceFiles(String csResourcePath, boolean bCacheResourceFiles)
	{
		File lst[] = FileSystem.getFileListBySuffix(csResourcePath, ".res");
		if (lst != null)
		{
			Log.logImportant("Beginning to cache " + lst.length + " resources files from " + csResourcePath);
			for (int i = 0; i < lst.length; i++)
			{
				File file = lst[i];
				if (file.isFile())
				{
					Document doc = XMLUtil.LoadXML(file);
					if (doc != null)
					{
						NodeList lstForms = doc.getElementsByTagName("form");
						for (int j = 0; j < lstForms.getLength(); j++)
						{
							Element eForm = (Element) lstForms.item(j);
							String csFormName = eForm.getAttribute("name");
							csFormName = csFormName.toUpperCase();
							m_tabResourceFiles.put(csFormName, file);
							if (bCacheResourceFiles)
								m_tabResources.put(csFormName, doc);
						}
					}
				}
			}
			return lst.length;
		}
		return 0;
	}


	void LoadResourceCache(boolean bCacheResourceFiles)
	{
		m_unloadRWLock.writeLock().lock();

		StopWatch sw = new StopWatch();
		m_tabResourceFiles = new Hashtable<String, File>();
		m_tabResources = new Hashtable<String, Document>();
		m_tabResourceStruct = new Hashtable<String, Document>();

		// 1st load the .res files form standard resource path
		int nNbFiles = doLoadResourceFiles(m_resourceManager.m_csResourcePath, bCacheResourceFiles);
		
		// can also optionally load the .res files form another resource path
		if(!StringUtil.isEmpty(m_resourceManager.m_csAlternateResourcePath))
			nNbFiles += doLoadResourceFiles(m_resourceManager.m_csAlternateResourcePath, bCacheResourceFiles);
		
		// bCacheResourceFiles = true;

		int nXMLResource = 0;
		// 2nd load res in the jar (lesser priority)
		if (bCacheResourceFiles) // Jar With XML data is valid only in mode
									// bCacheResourceFiles
		{
			if (!StringUtil.isEmpty(m_csJarXMLFile))
			{
				JarEntries jarEntries = new JarEntries();
				boolean b = jarEntries.open(m_resourceManager.m_csResourcePath, m_csJarXMLFile, true, ".res"); // Load
																												// all
																												// .res
																												// entries
				if (b)
				{
					Enumeration<String> e = jarEntries.getKeys();
					while (e.hasMoreElements())
					{
						String csKey = e.nextElement();
						JarItemEntry jarEntry = jarEntries.getEntry(csKey);
						if (jarEntry != null)
						{
							byte[] tbXML = jarEntry.loadBytes(jarEntries);
							ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(tbXML);
							Document doc = XMLUtil.loadXML(byteArrayInputStream);

							if (doc != null)
							{
								NodeList lstForms = doc.getElementsByTagName("form");
								for (int j = 0; j < lstForms.getLength(); j++)
								{
									Element eForm = (Element) lstForms.item(j);
									String csFormName = eForm.getAttribute("name");
									csFormName = csFormName.toUpperCase();
									// m_tabResourceFiles.put(csFormName, file)
									// ; // Files are unused
									Document docFile = m_tabResources.get(csFormName);
									if (docFile == null) // Check that the
															// form is not
															// already in
															// m_tabResources
									{
										m_tabResources.put(csFormName, doc);
										nXMLResource++;
									}
								}
							}

						}
					}
				}
				jarEntries.close();
			}
		}
		Log.logNormal("LoadResourceCache Unique XML files loaded=" + nNbFiles + "; XML from jar resource=" + nXMLResource + "; Total load Time (ms)=" + sw.getElapsedTime());

		m_unloadRWLock.writeLock().unlock();
	}

	public Document GetXMLPage(String csIdPageupperCase)
	{
		m_unloadRWLock.readLock().lock();
		if (m_tabResources != null)
		{
			Document docPage = m_tabResources.get(csIdPageupperCase);
			if (docPage == null)
			{
				File file = m_tabResourceFiles.get(csIdPageupperCase);
				if (file == null)
				{
					m_unloadRWLock.readLock().unlock();
					throw new AssertException("Missing resource file : " + csIdPageupperCase);
				}
				docPage = XMLUtil.LoadXML(file);
				if (docPage != null) // &&
										// OnlineResourceManager.ms_bCacheResourceFiles)
					m_tabResources.put(csIdPageupperCase, docPage);
			}
			m_unloadRWLock.readLock().unlock();
			return docPage;
		}
		m_unloadRWLock.readLock().unlock();
		return null;
	}

	public void removeResourceCache(String csForm)
	{
		m_unloadRWLock.readLock().lock();
		if (m_tabResourceStruct != null)
			m_tabResourceStruct.remove(csForm);
		if (m_tabResources != null)
			m_tabResources.remove(csForm);
		// if(m_tabResourceFiles != null)
		// m_tabResourceFiles.remove(csForm);
		m_unloadRWLock.readLock().unlock();
	}

	public Document GetXMLStructure(String idPage)
	{
		m_unloadRWLock.readLock().lock();
		String csIdPageupperCase = idPage.toUpperCase();
		Document struct = m_tabResourceStruct.get(csIdPageupperCase);
		if (struct != null)
		{
			m_unloadRWLock.readLock().unlock();
			return struct;
		}

		Document doc = GetXMLPage(csIdPageupperCase);
		if (doc != null)
		{
			XMLMerger merger = XMLMergerManager.get(null); // new
															// XMLMerger(null) ;
			NodeList lstForms = doc.getElementsByTagName("form");
			for (int j = 0; j < lstForms.getLength(); j++)
			{
				Element eForm = (Element) lstForms.item(j);
				String name = eForm.getAttribute("name");
				if (name.equalsIgnoreCase(idPage))
				{
					struct = merger.BuildXLMStructure(m_resourceManager.getXmlFrame(), eForm);
					m_tabResourceStruct.put(csIdPageupperCase, struct);
					XMLMergerManager.release(merger);
					m_unloadRWLock.readLock().unlock();
					return struct;
				}
			}
			XMLMergerManager.release(merger);
		}
		m_unloadRWLock.readLock().unlock();
		return null;
	}

	private ReentrantReadWriteLock m_unloadRWLock = new ReentrantReadWriteLock();

	private String m_csJarXMLFile = null;
}
