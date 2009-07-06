/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic;

import generate.CBaseLanguageExporter;
import semantic.expression.CBaseEntityExpression;
import utils.CObjectCatalog;

public class CEntityFileBuffer extends CDataEntity
{
	protected CEntityFileDescriptor m_FileDescriptor = null ;
	
	protected CEntityFileBuffer(String name, CEntityFileDescriptor filedesc, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(0, name, cat, out);
		m_FileDescriptor = filedesc ;
	}

	@Override
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.VAR ;
	}

	@Override
	public String ExportReference(int nLine)
	{
		return m_FileDescriptor.ExportReference(getLine()) ;
	}
	
	public CEntityFileDescriptor GetFileDescriptor()
	{
		return m_FileDescriptor ;
	}
	
	@Override
	public boolean HasAccessors()
	{
		return false;
	}

	@Override
	public String ExportWriteAccessorTo(String value)
	{
		return null;
	}

	@Override
	public boolean isValNeeded()
	{
		return false;
	}

	@Override
	public String GetConstantValue()
	{
		return null;
	}

	@Override
	protected void DoExport()
	{
		// nothing

	}

	/* (non-Javadoc)
	 * @see semantic.CDataEntity#GetSubStringReference(semantic.expression.CBaseEntityExpression, semantic.expression.CBaseEntityExpression, semantic.CBaseEntityFactory)
	 */
	@Override
	public CDataEntity GetSubStringReference(CBaseEntityExpression start, CBaseEntityExpression length, CBaseEntityFactory factory)
	{
		CSubStringAttributReference ref = factory.NewEntitySubString(getLine()) ;
		ref.SetReference(this, start, length) ;
		return ref ;
	}
	
	public boolean ignore()
	{
		return false ;
	}

}
