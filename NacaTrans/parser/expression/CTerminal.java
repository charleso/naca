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
 * Created on Jul 19, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.expression;

import jlib.xml.Tag;

import org.w3c.dom.*;

import semantic.CDataEntity;
import semantic.CBaseEntityFactory;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CTerminal
{
//	public abstract String GetType() ;
//	public abstract String GetValue() ;
	 
	protected CTerminal()
	{
	}

	public abstract String GetValue() ;  	
	public abstract boolean IsReference() ;
//	public abstract boolean IsOne();
//	public abstract boolean IsMinusOne();
	

	public void ExportTo(Tag tag)
	{
		ExportTo(tag.getElement(), tag.getDoc());
	}
	
	public abstract void ExportTo(Element e, Document root);
	//public abstract void ExportTo(CBaseLanguageExporter e) ;

//	public CIdentifier GetIdentifier()
//	{
//		return null ;
//	}
	
	public abstract CDataEntity GetDataEntity(int nLine, CBaseEntityFactory factory) ;

	public CDataEntity GetDataReference(int nLine, CBaseEntityFactory factory)
	{
		return null;
	}
	
	public abstract boolean IsNumber() ;
}
