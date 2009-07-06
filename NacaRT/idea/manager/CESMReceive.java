/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.manager;

import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.misc.KeyPressed;
import nacaLib.varEx.Var;
import nacaLib.varEx.Form;

import org.w3c.dom.Document;

/*
 * Created on 28 sept. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */



public class CESMReceive extends CJMapObject
{
	CESMReceive(Document Loader, BaseEnvironment env)
	{
		m_xmlData = Loader;
		m_Env = env ;
	}
	
	CESMReceive setMap(String mapName)
	{
	//	m_MapName = mapName;
		return this;
	}
	
	
	public void into(Form var)
	{
		m_MapInto = var;
		receiveData() ;
		//return this;
	}
	public void into(Var var)
	{
		// if this function is called, that means a COPY is missing with the map defined in it
		assertIfFalse(var == null) ;
	}
	
	void receiveData()
	{
		if(m_xmlData != null)
		{
//			for(int n=0; n<m_MapInto.m_arrForms.size(); n++)
//			{
//				Form f = (CForm) m_MapInto.m_arrForms.get(n);
				m_MapInto.loadValues(m_xmlData);
				String k = m_xmlData.getDocumentElement().getAttribute("keypressed") ;
				m_Env.setKeyPressed(KeyPressed.getKey(k));
	//		}
		}		
	}
	
	private Form m_MapInto = null;
	//private String m_MapName = "";
	private Document m_xmlData = null;
	private BaseEnvironment m_Env = null ;

	public CESMReceive mapSet(String string)
	{
		// nothing to do with mapset...		
		return this ;
	}
	public CESMReceive mapSet(Var name)
	{
		// nothing to do with mapset...		
		return this ;
	}
}
