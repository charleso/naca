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

import java.util.SortedSet;
import java.util.Vector;

import jlib.xml.Tag;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.expression.CTerminal;
import semantic.CBaseActionEntity;
import semantic.CBaseEntityFactory;
import semantic.CBaseResourceEntity;
import semantic.CDataEntity;
import semantic.CEntityNoAction;
import semantic.Verbs.CEntityInitialize;
import semantic.Verbs.CEntitySetConstant;
import semantic.forms.CEntityResourceFormContainer.FieldExportType;
import utils.CEntityHierarchy;
import utils.CObjectCatalog;
import utils.CRulesManager;
import utils.Transcoder;

import com.sun.org.apache.xml.internal.utils.StringToStringTable;
import com.sun.org.apache.xml.internal.utils.StringVector;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityResourceForm extends CBaseResourceEntity
{
	public class CFormByteConsumingState
	{
		public int m_nCurrentField = 0 ;
		public int m_nCurrentByteInField = 0 ;
	}
	
	protected boolean m_bSaveMap = false ;

	/**
	 * @param name
	 * @param cat
	 * @param exp
	 */
	public CEntityResourceForm(int l, String name, CObjectCatalog cat, CBaseLanguageExporter lexp, boolean bSaveCopy)
	{
		super(l, name, cat, lexp);
		m_bSaveMap = bSaveCopy ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseEntity#RegisterMySelfToCatalog()
	 */
//	protected void RegisterMySelfToCatalog()
//	{
//		super.RegisterMySelfToCatalog() ;
//		if (!m_bSaveMap)
//		{
//			m_ProgramCatalog.RegisterMasterMap(this) ;
//		}
//	}
	
	public void InitDependences(CBaseEntityFactory factory) 
	{
		for (int i=0; i<m_arrFields.size(); i++)
		{
			CEntityResourceField f = (CEntityResourceField)m_arrFields.get(i);
			f.SetParent(this) ;
			f.InitDependences(factory) ; 
		}
		for (int i=0; i<m_arrFormReferences.size();i++)
		{
			String cs = m_arrFormReferences.elementAt(i);
//			CEntityFormAccessor fa1 = factory.NewEntityFormAccessor(getLine(), cs+"I", this) ; 
//			CEntityFormAccessor fa2 = factory.NewEntityFormAccessor(getLine(), cs+"O", this) ; 
//			CEntityFormAccessor fa3 = factory.NewEntityFormAccessor(getLine(), cs, this) ; 
			factory.m_ProgramCatalog.RegisterDataEntity(cs+"I", this) ;
			factory.m_ProgramCatalog.RegisterDataEntity(cs+"O", this) ;
			factory.m_ProgramCatalog.RegisterDataEntity(cs, this) ;
		}
		String cs = GetName() ;
//		CEntityFormAccessor fa1 = factory.NewEntityFormAccessor(getLine(), GetName()+"I", this) ; 
//		CEntityFormAccessor fa2 = factory.NewEntityFormAccessor(getLine(), GetName()+"O", this) ; 
//		CEntityFormAccessor fa3 = factory.NewEntityFormAccessor(getLine(), GetName(), this) ; 
		factory.m_ProgramCatalog.RegisterDataEntity(cs+"I", this) ;
		factory.m_ProgramCatalog.RegisterDataEntity(cs+"O", this) ;
		factory.m_ProgramCatalog.RegisterDataEntity(cs, this) ;
	}

	public void AddField(CBaseResourceEntity e)
	{
		m_arrFields.add(e) ;
	}

	protected Vector<CBaseResourceEntity> m_arrFields = new Vector<CBaseResourceEntity>() ;
	protected StringVector m_arrFormReferences = new StringVector() ;
	public void SetReferences(StringVector v)
	{
		m_arrFormReferences = v ;
	}	
	public void SetSize(int col, int line)
	{
		m_nSizeCol = col ;
		m_nSizeLine = line ;
	}
	protected int m_nSizeCol = 0 ;
	protected int m_nSizeLine = 0 ;

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(parser.expression.CTerminal)
	 */
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		String value = term.GetValue() ;
		CEntitySetConstant eAssign = factory.NewEntitySetConstant(l) ;
		if (value.equals("ZERO") || value.equals("ZEROS") || value.equals("ZEROES"))
		{
			eAssign.SetToZero(this) ;
		}
		else if (value.equals("SPACE") || value.equals("SPACES"))
		{
			eAssign.SetToSpace(this) ;
		}
		else if (value.equals("LOW-VALUE"))
		{
			CEntityInitialize init = factory.NewEntityInitialize(l, this) ;
			RegisterWritingAction(init) ;
			return init ;
			//eAssign.SetToLowValue(this) ;
		}
		else
		{
			return null ;
		}
		RegisterWritingAction(eAssign) ;
		return eAssign ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(semantic.CBaseDataEntity)
	 */
	public CBaseActionEntity GetSpecialAssignment(CDataEntity term, CBaseEntityFactory factory, int l)
	{
		Tag t = CRulesManager.getInstance().getRule("ReduceMaps") ;
		if (t != null)
		{
			boolean bReduce = t.getValAsBoolean("active") ;
			if (bReduce)
			{
				if (term.GetDataType() == CDataEntityType.FORM && !IsSaveCopy())
				{
					CEntityNoAction act = factory.NewEntityNoAction(l) ; 
					factory.m_ProgramCatalog.RegisterMapCopy(act) ;
					return act ;
				}
			}
		}
		return null;
	}

	public void MakeSavCopy(CEntityResourceForm form, CBaseEntityFactory factory, boolean bFromRes)
	{
		form.SetSize(m_nSizeCol, m_nSizeLine) ;
		for (int i=0; i<m_arrFields.size(); i++)
		{
			CEntityResourceField f = (CEntityResourceField)m_arrFields.get(i);
			CEntityResourceField fs ;
			if (f.IsEntryField())
			{
				if(!bFromRes)
				{
					fs = factory.NewEntityEntryField(f.getLine(), "S"+f.GetName()) ;
					fs.SetDisplayName(f.GetName());
				}
				else
				{
					fs = factory.NewEntityEntryField(f.getLine(), "S"+f.GetName()) ;
					fs.SetDisplayName(f.GetDisplayName());
				}
				factory.m_ProgramCatalog.RegisterSaveField(fs, f) ;
			}
			else
			{
				fs = factory.NewEntityLabelField(f.getLine()) ;
			}
			fs.m_Of = form.m_Of ;
			fs.m_csInitialValue = f.m_csInitialValue ;
			fs.m_nLength = f.m_nLength ;
			fs.m_nPosCol = f.m_nPosCol ;
			fs.m_nPosLine = f.m_nPosLine ;
			fs.m_ProgramCatalog = f.m_ProgramCatalog ;
			fs.m_csColor = f.m_csColor ;
			fs.m_csHighLight = f.m_csHighLight ;
			fs.m_csBrightness = f.m_csBrightness ;
			fs.m_csFillValue = f.m_csFillValue ;
			fs.m_csProtection = f.m_csProtection ;
			fs.m_bRightJustified = f.m_bRightJustified ;
			fs.m_ResourceStrings = f.m_ResourceStrings ;
			if(bFromRes)
				fs.setDevelopable(f.m_csDevelopableFlagMark);
			
			form.m_arrFields.add(fs) ;
		}
	}
	
	protected int m_nCurrentField = -1;
	protected int m_nCurrentByteInField = 0 ;
	public void StartFieldAnalyse()
	{
		m_nCurrentField = -1 ;
		m_nCurrentByteInField = 0 ;
	}
	public int getCurrentPositionInField()
	{
		return m_nCurrentByteInField ;
	}
	public CFormByteConsumingState getCurrentConsumingState()
	{
		CFormByteConsumingState state = new CFormByteConsumingState() ;
		state.m_nCurrentByteInField = m_nCurrentByteInField ;
		state.m_nCurrentField = m_nCurrentField ;
		return state ;
	}
	public void setCurrentConsumingState(CFormByteConsumingState state)
	{
		if (state != null)
		{
			m_nCurrentByteInField = state.m_nCurrentByteInField ;
			m_nCurrentField = state.m_nCurrentField ;
		}
	}
	public int ConsumeFieldsAsBytes(int bytes)
	{
		int nbBytesLeft = bytes ;
		if (m_nCurrentField == -1 && m_nCurrentByteInField == 0)
		{
			nbBytesLeft -= 12 ; // there are 12 bytes at begining of MAP
			m_nCurrentField = 0 ;
			if (nbBytesLeft <0)
			{
				Transcoder.logError(getLine(), "Unexpecting situation");
				return 0 ;
			}
			else if (nbBytesLeft == 0)
			{
				return -1 ;
			}
		}	
		int nbFields = 0;
		while (nbBytesLeft > 0 && m_nCurrentField < m_arrFields.size())
		{
			CEntityResourceField f = getCurrentField();
			if (f != null)
			{
				int byteLeftInField = f.GetByteLength() - m_nCurrentByteInField ;
				if (nbBytesLeft < byteLeftInField)
				{
					m_nCurrentByteInField += nbBytesLeft ;
					nbBytesLeft = 0 ;
				}
				else
				{
					nbBytesLeft -= byteLeftInField ;
					nbFields ++ ;
					m_nCurrentByteInField = 0;
					m_nCurrentField ++ ;
				}
			}
		} 
		if (nbBytesLeft == 0)
		{
			return nbFields ;
		}
		else
		{ // error 
			//m_logger.error("WARNING : Redefine is larger than original size. Check if this is not important");
			return nbFields ;
		}
	}
	
	/**
	 * @return
	 */
	private CEntityResourceField getCurrentField()
	{
		if (m_nCurrentField < m_arrFields.size())
		{
			CEntityResourceField f = (CEntityResourceField)m_arrFields.get(m_nCurrentField) ;
			while (!f.IsEntryField())
			{
				m_nCurrentField ++ ;
				if (m_nCurrentField == m_arrFields.size())
				{
					return null ;
				}
				f = (CEntityResourceField)m_arrFields.get(m_nCurrentField) ;
			}
			return f ;
		}
		return null ;
	}

	public void ConsumeFields(int n)
	{
		int nToDO = n ;
		while (m_nCurrentField < m_arrFields.size() && nToDO>0)
		{
			CEntityResourceField f = (CEntityResourceField)m_arrFields.get(m_nCurrentField);
			if (f.IsEntryField())
			{
				nToDO -- ;
			}
			m_nCurrentField ++ ;
		}
	}
	public CEntityHierarchy GetHierarchy()
	{
		CEntityHierarchy hier = super.GetHierarchy() ;
		hier.AddLevel(GetName()+"I");
		hier.AddLevel(GetName()+"O");
		return hier;
	}

	public boolean IsSaveCopy()
	{
		return m_bSaveMap;
	}

	public class CFieldRedefineDescription
	{
		public String SKIP = "SKIP" ;
		public String FIELD = "FIELD" ;
		public String OCCURS = "OCCURS" ;
		public CEntityResourceField m_Field = null ;
		public String m_Name = "" ;
		public String m_Type = "" ;
		public int m_Size = 0 ;
		
		public CFieldRedefineDescription Next()
		{
			if (m_Next == null)
			{
				m_Next = new CFieldRedefineDescription() ;
			}
			return m_Next ;
		}
		protected CFieldRedefineDescription m_Next = null ;
	}
	public class CFieldRedefineStructure
	{
		public CFieldRedefineDescription Current()
		{
			return m_Current ;
		}
		public CFieldRedefineDescription Next()
		{
			m_Current = m_Current.Next() ;
			return m_Current;
		}
		protected CFieldRedefineDescription m_Current = null ;
		protected CFieldRedefineDescription m_Start = null ;
	}
	public CFieldRedefineStructure GetRedefineStructure()
	{
		if (m_RedefineStructure.m_Start == null)
		{
			m_RedefineStructure.m_Start = new CFieldRedefineDescription() ;
		}
		m_RedefineStructure.m_Current = m_RedefineStructure.m_Start ;
		return m_RedefineStructure;
	}
	protected CFieldRedefineStructure m_RedefineStructure = new CFieldRedefineStructure() ;

	public void ExportXMLFields(SortedSet<CEntityResourceFormContainer.FieldExportDescription> setFields, Document doc, CResourceStrings res)
	{
		for (int i=0; i<m_arrFields.size(); i++)
		{
			CEntityResourceField field = (CEntityResourceField)m_arrFields.get(i);
			Element e = field.DoXMLExport(doc, res) ;
			if (e != null)
			{
				CEntityResourceFormContainer.FieldExportDescription exp = new CEntityResourceFormContainer.FieldExportDescription() ;
				exp.m_Col = field.m_nPosCol ;
				exp.setLine(field.m_nPosLine);
				exp.m_Length = field.m_nLength ;
				exp.m_bRightJustified = field.m_bRightJustified;
				exp.m_csFillValue = field.m_csFillValue;
				
				exp.m_Tag = e ;
				if (e.getNodeName().equalsIgnoreCase("edit"))
				{
					exp.m_Type = CEntityResourceFormContainer.FieldExportType.TYPE_EDIT ;
				}
				else if (e.getNodeName().equalsIgnoreCase("label"))
				{
					exp.m_Type = CEntityResourceFormContainer.FieldExportType.TYPE_LABEL ;
				}
				else if (e.getNodeName().equalsIgnoreCase("title"))
				{
					exp.m_Type = CEntityResourceFormContainer.FieldExportType.TYPE_LABEL ;
				}
				setFields.add(exp) ;
			}
		}
		
		if (m_arrAddedItems != null)
		{
			for (int i=0; i<m_arrAddedItems.size(); i++)
			{
				CEntityResourceFormContainer.FieldExportDescription exp = m_arrAddedItems.get(i) ;
				exp.m_Tag = (Element)doc.importNode(exp.m_Tag, true) ;
				setFields.add(exp) ;
			}
		}
		if (m_arrLines != null)
		{
			for (int i=0; i<m_arrLines.size(); i++)
			{
				CEntityResourceFormContainer.FieldExportDescription exp = m_arrLines.get(i) ;
				exp.m_Tag = doc.createElement("line") ;
				exp.m_Tag.setAttribute("line", String.valueOf(exp.getLine())) ;
				exp.m_Tag.setAttribute("start", String.valueOf(exp.m_Col)) ;
				exp.m_Tag.setAttribute("length", String.valueOf(exp.m_Length)) ;
				setFields.add(exp) ;
			}
		}
	}
	
	protected StringToStringTable m_tabActivePFKeys = new StringToStringTable() ;
	public String getPFActive(String key)
	{
		return m_tabActivePFKeys.get(key);
	}
	public void setPFActive(String key, String status)
	{
		m_tabActivePFKeys.put(key, status);
	}	
	
	protected StringToStringTable m_tabActionPFKeys = new StringToStringTable() ;
	public String getPFAction(String key)
	{
		return m_tabActionPFKeys.get(key);
	}
	public void setPFAction(String key, String action)
	{
		m_tabActionPFKeys.put(key, action);
	}
	
	public Element MakePFKeysDescriptionDefine(Document doc)
	{
		Element ePFKEys = doc.createElement("pfkeydefine") ;
		for (int i=0; i<m_tabActivePFKeys.getLength(); i+=2)
		{
			String pf = m_tabActivePFKeys.elementAt(i) ;
			String status = m_tabActivePFKeys.elementAt(i+1);
			if (status.equals("true"))
			{
				ePFKEys.setAttribute(pf, status) ;
			}
		}
		return ePFKEys;
	}
	public Element MakePFKeysDescriptionAction(Document doc)
	{
		Element ePFKEys = doc.createElement("pfkeyaction") ;
		for (int i=0; i<m_tabActionPFKeys.getLength(); i+=2)
		{
			String pf = m_tabActionPFKeys.elementAt(i) ;
			String action = m_tabActionPFKeys.elementAt(i+1);
			ePFKEys.setAttribute(pf, action) ;
		}
		return ePFKEys;
	}

	public void setSavCopy(CEntityResourceForm fs)
	{
		m_SaveCopy = fs ;
	}
	CEntityResourceForm m_SaveCopy = null ;
	public CEntityResourceForm getSaveCopy()
	{
		return m_SaveCopy ;
	}

//	public void setDisplayName(String string)
//	{
//		m_csDisplayName = string ;
//	}
//	protected String m_csDisplayName = "" ;

	public void Clear()
	{
		super.Clear();
		for (int i=0; i<m_arrFields.size(); i++)
		{
			CEntityResourceField field = (CEntityResourceField)m_arrFields.get(i);
			field.Clear() ;
		}
		if (m_SaveCopy!=null)
		{
			m_SaveCopy.Clear();
		}
		m_SaveCopy = null ;
		m_arrFields.clear() ;
	}

	/**
	 * @param from
	 * @param to
	 */
	public void RenameField(String from, String to)
	{
		CEntityResourceField field = getField(from) ;
		if (field != null)
		{
			field.SetDisplayName(to) ;
		}
	}

	public void setDevelopable(String name, String flagMark)
	{
		CEntityResourceField field = getField(name) ;
		if (field != null)
		{
			field.setDevelopable(flagMark) ;
		}
	}
	
	public void setFormat(String name, String format)
	{
		CEntityResourceField field = getField(name) ;
		if (field != null)
		{
			field.setFormat(format);
		}
	}

	/**
	 * @param name
	 * @param valueOn
	 * @param valueOff
	 */
	public void setCheckBox(String name, String valueOn, String valueOff)
	{
		CEntityResourceField field = getField(name) ;
		if (field != null)
		{
			field.setCheckBox(valueOn, valueOff) ;
		}
	}
	
	protected CEntityResourceField getField(String name)
	{
		for (int i=0; i<m_arrFields.size(); i++)
		{
			CEntityResourceField field = (CEntityResourceField)m_arrFields.get(i) ;
			if (field != null)
			{
				if (field.GetDisplayName().equalsIgnoreCase(name))
				{
					return field ;
				}
			}
		}
		return null ;
	}
	protected CEntityResourceField getField(int col, int line)
	{
		for (int i=0; i<m_arrFields.size(); i++)
		{
			CEntityResourceField field = (CEntityResourceField)m_arrFields.get(i) ;
			if (field != null)
			{
				if (field.m_nPosCol == col && field.m_nPosLine == line)
				{
					return field ;
				}
			}
		}
		return null ;
	}

	/**
	 * @param col
	 * @param line
	 * @param name
	 */
	public void setNameLabel(int col, int line, String name)
	{
		CEntityResourceField field = getField(col, line); 
		if (field != null)
		{
			field.SetDisplayName(name) ;
		}
	}

	/**
	 * @param name
	 */
	public void setTitle(String name)
	{
		CEntityResourceField field = getField(name); 
		if (field != null)
		{
			CEntityResourceFormContainer cont = (CEntityResourceFormContainer)m_Of ;
			field.SetTitle(cont.m_resStrings) ;
		}
	}

	/**
	 * @param label
	 * @param value
	 * @param target
	 * @param submit
	 */
	public void setActiveChoice(String label, String value, String target, boolean submit)
	{
		CEntityResourceField field = getField(label) ;
		if (field != null) 
		{
			if (getField(target) != null)
			{
				field.setActiveChoice(value, target, submit);
			}
		}
	}
	public void setLinkedActiveChoice(String label, String edit, String target, boolean submit)
	{
		CEntityResourceField field = getField(label) ;
		CEntityResourceField link = getField(edit) ;
		if (field != null) 
		{
			if (getField(target) != null && getField(edit)!=null)
			{
				field.setLinkedActiveChoice(edit, target, submit);
			}
		}
	}

	/**
	 * @param field
	 */
	public void setEditReplayMutable(String name)
	{
		CEntityResourceField field = getField(name) ;
		if (field != null) 
		{
			field.setReplayMutable();
		}
	}

	/**
	 * @return
	 */
	public int GetRemainingBytesInCurrentField()
	{
		if (m_nCurrentField>=0)
		{
			CEntityResourceField field = getCurrentField() ;
			if (field != null)
			{
				return field.GetByteLength() - m_nCurrentByteInField ;
			}
		}
		else
		{
			return  12 - m_nCurrentByteInField ;
		}
		return 0 ;
	}

	/**
	 * @return
	 */
	public CEntityResourceField GetCurrentRedefiningField()
	{
		return getCurrentField() ;
	}

	/**
	 * @return
	 */
	public boolean isFormAlias(String id)
	{
		id = id.trim() ;
		String cs = GetDisplayName() ;
		if (cs.equals(id))
		{
			return true ;
		}
		for (int i=0; i<m_arrFormReferences.size(); i++)
		{
			if (m_arrFormReferences.elementAt(i).equals(id))
			{
				return true ;
			}
		}
		return false ;
	}
	
	/**
	 * @param method
	 */
	public void SetCustomOnload(String method)
	{
		m_csCustomOnloadMethod = method ;
	}
	protected String m_csCustomOnloadMethod = "" ;

	/**
	 * @param method
	 */
	public void SetCustomSubmit(String method)
	{
		m_csCustomSubmitMethod = method ;
	}
	protected String m_csCustomSubmitMethod = "" ;
	
	/**
	 * @param field
	 */
	public void SetDefaultCursor(String field)
	{
		m_csDefaultCursor = field ;
	}
	protected String m_csDefaultCursor = "";

	/**
	 * @param root
	 */
	public void ExportCustomProperties(Document doc)
	{
		Element eForm = doc.getDocumentElement() ;
		if (!m_csCustomOnloadMethod.equals(""))
		{
			eForm.setAttribute("customOnload", m_csCustomOnloadMethod) ;
		}
		if (!m_csCustomSubmitMethod.equals(""))
		{
			eForm.setAttribute("customSubmit", m_csCustomSubmitMethod) ;
		}
		if (!m_csDefaultCursor.equals(""))
		{
			eForm.setAttribute("defaultCursor", m_csDefaultCursor) ;
		}
	}

	public void AddSwitchCase(String name, String value, String protection, Element tag)
	{
		CEntityResourceField field = getField(name) ;
		if (field != null) 
		{
			field.AddSwitchCase(value, protection, tag);
		}
	}

	public void HideField(String name)
	{
		CEntityResourceField field = getField(name) ;
		if (field != null) 
		{
			field.Hide();
		}
	}

	public void HideField(int col, int line)
	{
		CEntityResourceField field = getField(col, line); 
		if (field != null)
		{
			field.Hide() ;
		}
	}

	public void AddItem(int c, int l, int s, Element tag)
	{
		if (m_arrAddedItems == null)
		{
			m_arrAddedItems = new Vector<CEntityResourceFormContainer.FieldExportDescription>() ;
		}
		CEntityResourceFormContainer.FieldExportDescription exp = new CEntityResourceFormContainer.FieldExportDescription() ;
		exp.m_Col = c ;
		exp.setLine(l) ;
		exp.m_Length = s ;
		exp.m_Tag = tag ;
		exp.m_Type = FieldExportType.TYPE_CUSTOM ;
		m_arrAddedItems.add(exp) ;
	} 
	protected Vector<CEntityResourceFormContainer.FieldExportDescription> m_arrAddedItems = null ;
	protected Vector<CEntityResourceFormContainer.FieldExportDescription> m_arrLines = null ;

	public void AddLine(int c, int l, int s)
	{
		if (m_arrLines == null)
		{
			m_arrLines = new Vector<CEntityResourceFormContainer.FieldExportDescription>() ;
		}
		CEntityResourceFormContainer.FieldExportDescription exp = new CEntityResourceFormContainer.FieldExportDescription() ;
		exp.m_Col = c ;
		exp.setLine(l) ;
		exp.m_Length = s ;
		exp.m_Tag = null ;
		exp.m_Type = FieldExportType.TYPE_LINE;
		m_arrLines.add(exp) ;
	}

	public void MoveField(String name, int nc, int nl)
	{
		CEntityResourceField field = getField(name) ;
		if (field != null) 
		{
			field.move(nc, nl);
		}
	}

	public void MoveField(int c, int l, int nc, int nl)
	{
		CEntityResourceField field = getField(c, l) ;
		if (field != null) 
		{
			field.move(nc, nl);
		}
	}

	public void setResourceName(String name)
	{
		m_csResourceName = name ;
	}
	protected String m_csResourceName = "" ;

}
