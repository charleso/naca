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
package semantic.forms;

import generate.CBaseLanguageExporter;
import parser.expression.CTerminal;
import semantic.CBaseActionEntity;
import semantic.CBaseDataReference;
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CBaseEntityFieldAttribute extends CBaseDataReference
{
	public static class CEntityFieldAttributeType
	{
		public static CEntityFieldAttributeType LENGTH = new CEntityFieldAttributeType() ; 
		public static CEntityFieldAttributeType HIGHLIGHT = new CEntityFieldAttributeType() ; 
		public static CEntityFieldAttributeType COLOR = new CEntityFieldAttributeType() ; 
		public static CEntityFieldAttributeType FLAG = new CEntityFieldAttributeType() ; 
//		public static CEntityFieldAttributeType PROTECTED = new CEntityFieldAttributeType() ; 
		public static CEntityFieldAttributeType VALIDATION = new CEntityFieldAttributeType() ; 
		public static CEntityFieldAttributeType ATTRIBUTE = new CEntityFieldAttributeType() ; 
		public static CEntityFieldAttributeType DATA = new CEntityFieldAttributeType() ; 
		//public static CEntityFieldAttributeType DATAO = new CEntityFieldAttributeType() ; 
	}
	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	protected CBaseEntityFieldAttribute(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CEntityFieldAttributeType type, CDataEntity owner)
	{
		super(l, name, cat, out);
		m_Type = type ;
		m_Reference = owner ;
		m_parent = owner ; 
	}
	protected CEntityFieldAttributeType m_Type = null ;

	public void Clear()
	{
		super.Clear() ;
		m_Reference = null ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(parser.expression.CTerminal)
	 */
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(semantic.CBaseDataEntity)
	 */
	public CBaseActionEntity GetSpecialAssignment(CDataEntity term, CBaseEntityFactory factory, int l)
	{
		return null;
	}
	public boolean ignore()
	{
		return false ;
	}


	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#RegisterMySelfToCatalog()
	 */
	protected void RegisterMySelfToCatalog()
	{
		m_ProgramCatalog.RegisterDataEntity(GetName(), this) ;
//		m_ProgramCatalog.RegisterDataEntity("S" + GetName(), this) ;
	}
	public String GetConstantValue()
	{
		return "" ;
	}

}
