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

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jlib.xml.Tag;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.expression.CTerminal;
import semantic.CBaseActionEntity;
import semantic.CBaseEntityFactory;
import semantic.CBaseResourceEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;
import utils.CRulesManager;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityResourceFormContainer extends CBaseResourceEntity
{
	protected boolean m_bSaveCopy = false ;
	/**
	 * @param name
	 * @param cat
	 * @param exp
	 */
	public CEntityResourceFormContainer(int l, String name, CObjectCatalog cat, CBaseLanguageExporter lexp, boolean bSaveCopy)
	{
		super(l, name, cat, lexp);
		m_bSaveCopy = bSaveCopy ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseEntity#RegisterMySelfToCatalog()
	 */
	protected void RegisterMySelfToCatalog()
	{
//		m_ProgramCatalog.RegisterFormContainer(GetName(), this) ;		
	}
	public void AddForm(CEntityResourceForm form)
	{
		m_arrForm.add(form) ;
	}
	
	public void InitDependences(CBaseEntityFactory factory) 
	{
		for (int i=0; i<m_arrForm.size(); i++)
		{ 
			CEntityResourceForm form = m_arrForm.get(i);
			form.InitDependences(factory) ;
		}

//		String sav = GetName()+"S" ;
//		CIgnoreExternalEntity ext = factory.NewIgnoreExternalEntity(sav) ;
//		m_ProgramCatalog.AddIgnoredExternalEntity(sav, ext);
	}
	
	protected Vector<CEntityResourceForm> m_arrForm = new Vector<CEntityResourceForm>() ;
	protected CEntityResourceFormContainer m_Owner = null ;

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
	
	public CResourceStrings m_resStrings = null ;
	
	
	public void clearSavCopy(CBaseEntityFactory factory)
	{
		factory.m_ProgramCatalog.ClearSavCopy();
	}
	
	public CEntityResourceFormContainer MakeSavCopy(CBaseEntityFactory factory, boolean bFromRes)
	{
		CEntityResourceFormContainer newContainer = factory.NewEntityFormContainer(getLine(), GetName()+"S", true) ;
		//newContainer.SetDisplayName(GetName());
		newContainer.m_Of = this ;
		newContainer.m_Owner = null ;
		m_SavCopy = newContainer ;
		
		CObjectCatalog o = Transcoder.getCurrentObjectCatalog();
		o.clearSaveMaps();
		
		for (int i=0; i<m_arrForm.size(); i++)
		{ 
			CEntityResourceForm form = m_arrForm.get(i);
			CEntityResourceForm fs = factory.NewEntityForm(form.getLine(), form.GetName()+"S", true) ;
			fs.setResourceName(GetName()) ;
			fs.SetDisplayName(form.GetName());
			form.setSavCopy(fs) ;
			fs.m_Of = newContainer ;
			form.MakeSavCopy(fs, factory, bFromRes) ;
			factory.m_ProgramCatalog.RegisterSaveMap(fs, form) ;
			newContainer.AddForm(fs) ;
		}
		return newContainer ;
	}
	protected CEntityResourceFormContainer m_SavCopy = null ;
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#ignore()
	 */
	public boolean ignore()
	{
		Tag t = CRulesManager.getInstance().getRule("ReduceMaps") ;
		if (t != null)
		{
			boolean bReduce = t.getValAsBoolean("active") ;
			if (bReduce)
			{
				return m_bSaveCopy ;
			}
		}
		return false ;
	}

	public Document MakeXMLOutput(boolean bResources)
	{
		if (bResources)
		{
			return null;
		}

		if (m_csExportFilePath.equals("") || m_arrForm.size()==0)
		{
			return null ;
		}
		Document doc = createNewDocument() ;
		FieldComparator comp = new FieldComparator() ;
		SortedSet<FieldExportDescription> setFields = new TreeSet<FieldExportDescription>(comp) ;
//		for (int i=0; i<m_arrForm.size(); i++)
//		{
		CEntityResourceForm form = m_arrForm.get(0);
		form.ExportXMLFields(setFields, doc, m_resStrings) ;
//		}
		
		String name = FormatIdentifier(GetName()) ;
		Element ePFKeysDefine = form.MakePFKeysDescriptionDefine(doc) ;
		Element ePFKeysSpecial = form.MakePFKeysDescriptionAction(doc) ;
		Element eRoot = createNewFormBody(doc, name, name, ePFKeysDefine, ePFKeysSpecial) ;
		form.ExportCustomProperties(doc) ;
		Element eBody = createVBox(doc, eRoot);
		int nb = setFields.size() ;
		FieldExportDescription[] arr = new FieldExportDescription[nb] ;
		setFields.toArray(arr);
		int curline = 0 ;
		int curCol = 0 ;
		Element curLineElem = null ;
		FieldExportType lastType = null ;

		FieldExportDescription eLineToAdd = null ;
		
		for (int i=0; i<nb; i++)
		{
			FieldExportDescription f = arr[i] ;
			if (f.m_Type == FieldExportType.TYPE_LINE)
			{
				eLineToAdd = f ;
				continue ;
			}
			int nl = f.getLine();
			if (curline != nl)
			{
				if (eLineToAdd != null && curline == eLineToAdd.getLine())
				{
					eBody.appendChild(eLineToAdd.m_Tag) ;
					eLineToAdd = null ;
				}
				for (int j=0; j<nl-curline-1; j++)
				{
					curLineElem = createHBox(doc, eBody);
					if (eLineToAdd != null && curline+j+1 == eLineToAdd.getLine())
					{
						eBody.appendChild(eLineToAdd.m_Tag) ;
						eLineToAdd = null ;
					}
				}
				curLineElem = createHBox(doc, eBody);
				curline = nl ;
				curCol = 1 ;
				lastType = null ;
			}
			int nc = f.m_Col ;
			int nlen = f.m_Length;
			if (curCol == 1 && nc == 2)
			{
				createBlank(doc, curLineElem, 0) ;
			}
			else if (nc > curCol + 1)
			{
				if (lastType == null)
				{
					createBlank(doc, curLineElem, nc - curCol-1) ;
				}
				else
				{	// the 1-lenght blank are ignored, because managed in the XSL
					createBlank(doc, curLineElem, nc - curCol-2) ;
				}
			}
			curCol = nc + nlen ;
			curLineElem.appendChild(f.m_Tag);
			lastType = f.m_Type ;
		}
		
		Tag t = new Tag();
		t.setDoc(doc);
		t.exportToFile(m_csExportFilePath);

		return doc;
	}

	private Document createNewDocument()
	{
		try
		{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.newDocument();
			return doc;
		}
		catch(ParserConfigurationException e)
		{
			e.printStackTrace();
			return null ;
		}		
	}
	
	private Element createNewFormBody(Document doc, String csFormName, String csTitle, Element ePFKeysDefine, Element ePFKeysSpecial)
	{
		Element eForm = doc.createElement("form");
		doc.appendChild(eForm);
		eForm.setAttribute("name", csFormName);
		eForm.setAttribute("title", csTitle);
		eForm.appendChild(ePFKeysDefine);
		eForm.appendChild(ePFKeysSpecial);
		
		// list all languages
		String lang = m_resStrings.ExportAllLangId() ;
		eForm.setAttribute("allLanguages", lang) ;
		
		Element eBody = doc.createElement("formbody");
		eForm.appendChild(eBody);
		
		return eBody;
	}
	
	private Element createVBox(Document doc, Element eParent)
	{
		Element eVBox = doc.createElement("vbox");
		eParent.appendChild(eVBox);
		return eVBox;
	}
	
	private Element createHBox(Document doc, Element eParent)
	{
		Element eHBox = doc.createElement("hbox");
		eParent.appendChild(eHBox);
		return eHBox;
	} 
		
	private Element createBlank(Document doc, Element eParent, int size)
	{
		Element eBlank = doc.createElement("blank");
		eBlank.setAttribute("length", ""+size);
//		String cs = "" ;
//		for (int i=0; i<size; i++)
//		{
//			cs += " " ;
//		}
//		eBlank.setAttribute("text", cs);
		eParent.appendChild(eBlank);
		return eBlank;
	} 
	public static class FieldExportDescription
	{
		private int m_line = 0 ;
		int m_Col = 0 ;
		int m_Length = 0 ;
		
		boolean m_bRightJustified = false;	// Valid only for Edits
		String m_csFillValue = "";			// Valid only for Edits
		
		Element m_Tag = null ;
		FieldExportType m_Type = null ;
		
		void setLine(int n)
		{
			m_line = n;
			Transcoder.setLine(m_line);
		}
		
		int getLine()
		{
			return m_line;
		}
	}
	public enum FieldExportType 
	{
		TYPE_EDIT, TYPE_LABEL, TYPE_CUSTOM, TYPE_LINE ;
	}
	private class FieldComparator implements Comparator<FieldExportDescription> 
	{
		public int compare(FieldExportDescription e1, FieldExportDescription e2)
		{
			int line1 = e1.getLine();
			int line2 = e2.getLine();
			if (line1 < line2)
			{
				return -1 ;
			}
			else if (line1 > line2)
			{
				return 1 ;
			}
			else 
			{
				int col1 = e1.m_Col ;
				int col2 = e2.m_Col ;
				if (col1 < col2)
				{
					return -1 ;
				}
				else if (col1 > col2)
				{
					return 1 ;
				}
				else
				{
					return 0;
				}
			}
		}
	}
	protected String m_csExportFilePath = "" ;
	public void setExportFilePath(String string)
	{
		m_csExportFilePath = string ;
	}

	public boolean isSavCopy()
	{
		return m_bSaveCopy ;
	}
//	public String getExportFilePath()
//	{
//		return m_csExportFilePath ;
//	} 

	public void Clear()
	{
		if (m_ProgramCatalog != null)
		{
			m_ProgramCatalog.GetGlobalCatalog().ClearFormContainers() ;
		}
		super.Clear();
		if (m_SavCopy!=null)
		{
			m_SavCopy.Clear() ;
			m_SavCopy = null ;
		}
		for (int i=0; i<m_arrForm.size(); i++)
		{
			CEntityResourceForm form = m_arrForm.get(i);
			form.Clear() ;
		}
		m_arrForm.clear() ;
		m_Owner = null ;
	}

	/**
	 * @return
	 */
	public CEntityResourceFormContainer GetSavCopy()
	{
		return m_SavCopy ;
	}

	/**
	 * @return
	 */
	public CEntityResourceForm getForm()
	{
		if (m_arrForm.size()>0)
		{
			return m_arrForm.get(0);
		}
		return null;
	}

	/**
	 * @return
	 */
	public int GetNbForms()
	{
		return m_arrForm.size() ;
	}
	
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#SetDisplayName(java.lang.String)
	 */
	@Override
	public void SetDisplayName(String name)
	{
		super.SetDisplayName(name);
	}

}
