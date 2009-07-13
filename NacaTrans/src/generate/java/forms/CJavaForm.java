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
package generate.java.forms;

import generate.CBaseLanguageExporter;



//import semantic.forms.CEntityResourceField;
import semantic.forms.CEntityResourceField;
import semantic.forms.CEntityResourceForm;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaForm extends CEntityResourceForm
{

	/**
	 * @param name
	 * @param cat
	 * @param exp
	 */
	public CJavaForm(int l, String name, CObjectCatalog cat, CBaseLanguageExporter lexp, boolean bSave)
	{
		super(l, name, cat, lexp, bSave);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseEntity#DoExport()
	 */
//	public Element DoXMLExport(Document doc)
//	{
//		Element eForm = doc.createElement("form") ;
//		// set attributes 
//		eForm.setAttribute("name", m_parent.GetName()) ;
//		eForm.setAttribute("sizecol", ""+m_nSizeCol);		
//		eForm.setAttribute("sizeline", ""+m_nSizeLine);
//		if (!m_csCustomSubmitMethod.equals(""))
//		{
//			eForm.setAttribute("customSubmit", m_csCustomSubmitMethod) ;
//		}				
//		
//		// add fields
////		for(int nCurField = 0 ; nCurField<m_arrFields.size(); nCurField++) 
////		{
////			CEntityResourceField field = (CEntityResourceField)m_arrFields.get(nCurField);
////			Element eField = field.DoXMLExport() ;
////			eForm.appendChild(eField) ;
////		}
//		return eForm;		
//	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#ExportReference(semantic.CBaseLanguageExporter)
	 */
	public String ExportReference(int nLine)
	{
		String cs = "" ;
		if (m_Of != null)
		{
			cs = m_Of.ExportReference(getLine()) + "." ;
		}
		cs += FormatIdentifier(GetName()) ;
		return cs ;		
	}
	public boolean HasAccessors()
	{
		return false;
	}
	protected void DoExport()
	{
		String name = FormatIdentifier(GetName()) ;
		String formname = FormatIdentifier(m_csResourceName);
		WriteLine("Form " + name + " = declare.form(\""+formname+"\", "+m_nSizeLine+", "+m_nSizeCol+") ;") ;
			
		StartOutputBloc() ;
		int nbFields = m_arrFields.size() ;
		for (int j=0;j<nbFields; j++)
		{
			CEntityResourceField eField = (CEntityResourceField)m_arrFields.get(j);
//			String cs = GetLineForField(eField) ;
//			WriteLine(cs);
			DoExport(eField) ;
		} 
		EndOutputBloc() ;
	}
	public String ExportWriteAccessorTo(String value)
	{
		// unsued		
		return "" ;
	}
	public boolean isValNeeded()
	{
		return false;
	}


	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetDataType()
	 */
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.FORM ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseExternalEntity#GetTypeDecl()
	 */
	public String GetTypeDecl()
	{
		return ""; // unsued
	}

}
