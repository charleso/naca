/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.view;


import idea.onlinePrgEnv.OnlineResourceManager;
import idea.onlinePrgEnv.OnlineResourceManagerFactory;
import idea.onlinePrgEnv.OnlineSession;

import org.w3c.dom.Document;

/*
 * Created on 8 déc. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class View
{

	/**
	 * @param out
	 * @param appSession
	 */
	public void mergeOutput(OnlineSession appSession)
	{
		Document xmlData = appSession.getXMLData() ;
		Document xmlStruct = appSession.getCurrentXMLStructure() ;
		if (xmlStruct != null)
		{
			XMLMerger merger = XMLMergerManager.get(appSession);
			Document xmlOutput = merger.doMerging(xmlStruct, xmlData) ;	// 5 ms per loop
			XMLMergerManager.release(merger);
			appSession.setXMLOutput(xmlOutput) ;	// 1ms per loop: should be true
		}
	}

	public void mergeOutputForPrintScreen(OnlineSession appSession)
	{
		Document xmlData = appSession.getXMLData() ;
		Document xmlStruct = appSession.getCurrentXMLStructureForPrintScreen() ;
		if (xmlStruct != null)
		{
			XMLMerger merger = XMLMergerManager.get(appSession);
			Document xmlOutput = merger.doMerging(xmlStruct, xmlData) ;	// 5 ms per loop
			XMLMergerManager.release(merger);
			appSession.setXMLOutput(xmlOutput) ;	// 1ms per loop: should be true
		}
	}
	
	public void mergeOutputForServerdown(OnlineSession appSession)
	{
		OnlineResourceManager resManager = OnlineResourceManagerFactory.GetInstance() ;
		
		Document xmlData = appSession.getXMLData() ;
		Document docServerDown = resManager.getMainPage("ServerDown") ;
		if (docServerDown != null)
		{
			XMLMerger merger = XMLMergerManager.get(appSession);
			Document xmlOutput = merger.doMerging(docServerDown, xmlData) ;	// 5 ms per loop
			XMLMergerManager.release(merger);
			appSession.setXMLOutput(xmlOutput) ;	// 1ms per loop: should be true
		}
	}
	
	

	/**
	 * @param appSession
	 */
	public void updateOutput(OnlineSession appSession)
	{
		Document xmlData = appSession.getXMLData() ;
		Document xmlOutput = appSession.getXMLOutput() ;
		if (xmlOutput != null)
		{
			XMLMerger merger = XMLMergerManager.get(appSession);
			merger.doUpdate(xmlOutput, xmlData) ;
			XMLMergerManager.release(merger);
			
		}
	}

}
