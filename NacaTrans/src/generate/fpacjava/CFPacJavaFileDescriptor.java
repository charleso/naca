/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CEntityFileDescriptor;
import utils.CObjectCatalog;

public class CFPacJavaFileDescriptor extends CEntityFileDescriptor
{

	/* (non-Javadoc)
	 * @see semantic.CDataEntity#ignore()
	 */
	@Override
	public boolean ignore()
	{
		return false ;
	}

	public CFPacJavaFileDescriptor(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out);
	}

	@Override
	public String ExportReference(int nLine)
	{
		return FormatIdentifier(GetName()) ;
	}

	@Override
	protected void DoExport()
	{
		String cs = "FPacFileDescriptor " + FormatIdentifier(GetName()) ;
		cs += " = declare.fpacFile(\""+GetName().toUpperCase()+"\")" ;
		WriteWord(cs) ;
		if (m_eOutputBufferInitialValue != null)
		{
			WriteWord(".fillOutputBuffer("+m_eOutputBufferInitialValue.ExportReference(getLine())+")") ;
		}
		WriteWord(".file() ;") ;
		WriteEOL() ;

	}
	
	

}
