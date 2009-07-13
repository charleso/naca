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
package generate.java.verbs;


import generate.CBaseLanguageExporter;
import generate.java.CJavaUnknownReference;
import semantic.CEntityProcedure;
import semantic.CEntityProcedureSection;
import semantic.Verbs.CEntityCallFunction;
import utils.CObjectCatalog;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCallFunction extends CEntityCallFunction
{

	/**
	 * @param cat
	 * @param out
	 * @param ref
	 */
	public CJavaCallFunction(int l, CObjectCatalog cat, CBaseLanguageExporter out, String ref, String csRefThru, CEntityProcedureSection section)
	{
		super(l, cat, out, ref, csRefThru, section);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		if (m_refRepetitions != null)
		{
			String index = "loop_index" ;
			while (m_ProgramCatalog.IsExistingDataEntity(index, ""))
			{
				index += "$" ;
			}
			CJavaUnknownReference ref = new CJavaUnknownReference(getLine(), GetName(), m_ProgramCatalog, null) ;
			m_ProgramCatalog.RegisterDataEntity(index, ref) ;
			String cs = "for (int "+index+"=0; isLess("+index+", " + m_refRepetitions.ExportReference(getLine()) + "); "+index+"++) {"  ;
			WriteLine(cs) ;
			StartOutputBloc() ;
		}
		if (m_ReferenceThru != null)
		{
			CEntityProcedure e = m_Reference.getProcedure() ;
			CEntityProcedure eThru = m_ReferenceThru.getProcedure() ;
			String line = "performThrough(" + e.ExportReference(getLine()) + ", " +eThru.ExportReference(getLine())+ ") ;" ;
			WriteLine(line);
		}
		else if (m_Reference != null)
		{
			CEntityProcedure e = m_Reference.getProcedure() ;
			if (e!=null)
			{
				String line = "perform(" + e.ExportReference(getLine()) +") ;" ;
				WriteLine(line);
			}
			else
			{
				Transcoder.logError(getLine(), "Unbound reference/identity");
				WriteLine("perform([UNDEFINED]) ; ");
			}
		}
		else
		{
			ExportChildren();
		}
		if (m_refRepetitions != null)
		{
			EndOutputBloc() ;
			WriteLine("}") ;
		}
	}

}
