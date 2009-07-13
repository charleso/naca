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

//import org.w3c.dom.Element;


//import semantic.forms.CEntityResourceForm;
import semantic.forms.CEntityResourceForm;
import semantic.forms.CEntityResourceFormContainer;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaFormContainer extends CEntityResourceFormContainer
{

	/**
	 * @param name
	 * @param cat
	 * @param exp
	 */
	public CJavaFormContainer(int l, String name, CObjectCatalog cat, CBaseLanguageExporter lexp, boolean bSave)
	{
		super(l, name, cat, lexp, bSave);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseEntity#DoExport()
	 */
//	public Element DoXMLExport()
//	{
//		Element eForms = GetXMLOutput().CreateRoot("Forms") ;
//		eForms.setAttribute("Name", GetName()) ;
//		
//		for (int i=0; i<m_arrForm.size(); i++)
//		{
//			CEntityResourceForm e = (CEntityResourceForm)m_arrForm.get(i) ;
//			Element el = e.DoXMLExport() ;
//			eForms.appendChild(el) ;
//		}
//		Element eStrings = m_resStrings.Export(eForms, GetXMLOutput().GetDocument()) ;
//		return eForms ;
//	}
	public String ExportReference(int nLine)
	{
		//e.WriteWord(GetName()) ;
		//return m_Form.ExportReference(getLine());	
		return FormatIdentifier(GetName());	
	}
	public boolean HasAccessors()
	{
		return false;
	}
	protected void DoExport()
	{
		String name = GetName();
//		if (!m_bSaveCopy)
//		{
			WriteEOL() ;
			WriteLine("import nacaLib.mapSupport.* ;") ;
			WriteLine("import nacaLib.varEx.* ;") ;
			WriteLine("import nacaLib.program.* ;") ;
			WriteLine("import nacaLib.basePrgEnv.* ;") ;
			WriteEOL() ;
//		}
		WriteLine("class "+name+" extends Map {") ;
		StartOutputBloc() ;

		WriteLine("static "+name+" Copy(BaseProgram program) {");
		StartOutputBloc() ;
		WriteLine("return new "+name+"(program);");
		EndOutputBloc();
		WriteLine("}");
		
		WriteLine("static "+name+" Copy(BaseProgram program, CopyReplacing rep)  {");
		StartOutputBloc() ;
		WriteLine("Assert(\"Unimplemented replacing for MAPs\") ;");
		WriteLine("return null ;");
		EndOutputBloc();
		WriteLine("}");
		
		WriteLine(""+name+"(BaseProgram program) {");
		StartOutputBloc() ;
		WriteLine("super(program);");
		EndOutputBloc();
		WriteLine("}");
		WriteLine("");

//		for (int j=0;j<nbStrings; j++)
//		{
//			Element eString = (Element)lstString.item(j);
//			String strname = eString.getAttribute("Name") ;
//			String cs = "LocalizedString " + FormatIdentifier(strname) + " = localizedString()";
//			WriteWord(cs);
//			NodeList lstLang = eString.getElementsByTagName("LocalizedText") ;
//			int nbLang = lstLang.getLength() ;
//			for (int k=0; k<nbLang; k++)
//			{
//				cs = "" ;
//				Element e = (Element)lstLang.item(k) ;
//				String text = e.getAttribute("Text");
//				String lang = e.getAttribute("LangID");
//				cs += ".text(\""+lang+"\", \""+text+"\")" ;
//				WriteWord(cs) ;
//			}			
//			WriteWord(";");
//			WriteEOL();
//		} 

		int nbForms = m_arrForm.size() ;
		for (int i=0; i<nbForms; i++)
		{
			CEntityResourceForm eForm = m_arrForm.get(i) ;
			DoExport(eForm) ;
//			String formname = FormatIdentifier(eForm.GetName()) ;
////			String sizeCol = eForm.getAttribute("SizeCol");
////			String sizeLine = eForm.getAttribute("SizeLine");
//			WriteLine("Form " + formname + " = form() ;") ;
//			
//			StartOutputBloc() ;
//			Vector lstFields = eForm.GetListOfChildren() ;
//			int nbFields = lstFields.size() ;
//			for (int j=0;j<nbFields; j++)
//			{
//				CEntityResourceField eField = (CEntityResourceField)lstFields.get(j);
//				String cs = GetLineForField(eField) ;
//				WriteLine(cs);
//			} 
//			NodeList lstLabels = eForm.getElementsByTagName("Label") ;
//			int nbLabels = lstLabels.getLength() ;
//			for (int j=0;j<nbLabels; j++)
//			{
//				Element eField = (Element)lstLabels.item(j);
//				String cs = GetLineForLabel(eField) ;
//				WriteLine(cs);
//			} 
//
//			EndOutputBloc() ;
		}
		WriteLine("");


		EndOutputBloc() ;
		WriteLine("}") ;
		WriteLine("");
		WriteLine("");
//		if (m_SavCopy != null)
//		{
//			m_SavCopy.StartExport() ;
//		}
	}
	public boolean IsNeedDeclarationInClass()
	{
		return true ;
	}
	public String ExportWriteAccessorTo(String value)
	{
		// unused		
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
		if (m_Owner != null)
		{// these are class names, not identifiers, so DO NOT FORMAT like others identifiers
			return m_Owner.GetTypeDecl() + "." + GetName().replace('-', '_'); 
		}
		else
		{
			return GetName().replace('-', '_');
		}
	}


}
