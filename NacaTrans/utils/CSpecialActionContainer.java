/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 23 mars 2005
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package utils;

import java.util.Collection;
import java.util.Vector;

import jlib.xml.Tag;
import jlib.xml.TagCursor;

import lexer.CTokenList;
import lexer.Cobol.CCobolLexer;

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import semantic.*;
import semantic.CICS.*;
import semantic.Verbs.*;
import semantic.expression.*;
import semantic.forms.*;
import utils.CobolTranscoder.ProcedureCallTree;
import utils.CobolTranscoder.Notifs.NotifIsUsedCICSPreprocessor;

/**
 * @author U930CV
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CSpecialActionContainer
{
	/**
	 * @param cat
	 */
	public void DoCheckEditNameDescending(CDataEntity niv, CObjectCatalog cat)
	{
		Vector arr = niv.GetListOfChildren() ;
		for (int j=0; j<arr.size(); j++)
		{
			CDataEntity le = (CDataEntity)arr.get(j);
			if (le.GetDataType() == CDataEntity.CDataEntityType.FIELD && !le.HasChildren())
			{
				String name = le.GetName() ;
				if (cat.HasNameConflict(name, ""))
				{
					if (cat.IsExistingDataEntity(name, ""))
					{
						CDataEntity e = cat.GetDataEntity(name, "") ;
						if (e!= null && e.m_Of == null && le != e)
						{
							CDataEntity.CDataEntityType type = e.GetDataType() ;
							if (type != CDataEntity.CDataEntityType.FIELD_ATTRIBUTE)
							{
								le.SetName(name+"$edit") ;
							}
						}
					}
					else
					{  // no entry can be found with this name, this means that more that one entry has this name.
						le.SetName(name+"$edit") ;
					}
				}
			}
			else
			{
				DoCheckEditNameDescending(le, cat) ;
			}
		}
	}
	/**
	 * @param cat
	 */
	public void DoCheckEditName(CObjectCatalog cat)
	{
		int n = cat.GetNbMap() ;
		int m = cat.GetNbSaveMap() ;
		boolean bSave = false ;
		for (int i=0; i<n+m; i++)
		{
			CEntityResourceForm form = null ;
			if (i>=n)
				bSave = true ;
			if (!bSave)
			{
				form = cat.GetMap(i) ;
			}
			else
			{
				form = cat.GetSaveMap(i-n) ;
			}
			if (form.m_Of == null)  // redefine ?
			{
				DoCheckEditNameDescending(form, cat) ;
			}
		}
	}
	public void DoClearConstantAttributes(CObjectCatalog cat, CBaseEntityFactory factory)
	{
		int nbCste = cat.GetNbAttributes() ;
		for (int i=0; i<nbCste; i++)
		{
			CEntityAttribute att = cat.GetAttribute(i);
			String name = att.GetName() ;
			if (att.GetNbWrittingActions() == 0 && att.GetNbReadingActions() == 0)
			{  // attribute used as constant is tests only
				int nbTests = att.GetNbValueAccess() ;
				for (int j=0; j<nbTests; )
				{
					CBaseEntityCondExpr cond = att.GetValueAccess(j);
					CBaseEntityCondition cond1 = cond.getAsCondition() ;
					if (cond1 != null)
					{
						String val = att.GetInitialValue() ;
						CBaseEntityCondition newCond = cond1.GetSpecialConditionReplacing(val, factory, att) ;
						if (newCond != null)
						{
							att.UnRegisterValueAccess(j);
							nbTests = att.GetNbValueAccess() ;
							cond1.Replace(newCond) ;
						}
						else
						{
							j++ ;
						}
					}
					else
					{
						j++ ;
					}
				}

				nbTests = att.GetNbVarTesting() ;
				for (int j=0; j<nbTests; )
				{
					CBaseEntityCondition cond = att.GetVarTesting(j);
					String val = att.GetInitialValue() ;
					CBaseEntityCondition newCond = cond.GetSpecialConditionReplacing(val, factory, att) ;
					if (newCond != null)
					{
						att.UnRegisterVarTesting(j);
						nbTests = att.GetNbVarTesting() ;
						cond.Replace(newCond) ;
					}
					else
					{
						j++ ;
					}
				}
			}
			else if (att.GetNbWrittingActions() == 0 && att.GetNbVarTesting() == 0)
			{  // attribute used as constant is read only
				int nbTests = att.GetNbValueAccess() ;
				for (int j=0; j<nbTests; )
				{
					CBaseEntityCondExpr condexp = att.GetValueAccess(j);
					CBaseEntityCondition cond = condexp.getAsCondition() ;
					if (cond != null)
					{
						String val = att.GetInitialValue() ;
						CBaseEntityCondition newCond = cond.GetSpecialConditionReplacing(val, factory, att) ;
						if (newCond != null)
						{
							att.UnRegisterValueAccess(j);
							nbTests = att.GetNbValueAccess() ;
							cond.Replace(newCond) ;
						}
						else
						{
							j++ ;
						}
					}
					else
					{
						j++ ;
					}
				}
				int nbRead = att.GetNbReadingActions() ;
				for (int j=0; j<nbRead; )
				{
					CBaseActionEntity cond = att.GetActionReading(j);
					String val = att.GetInitialValue() ;
					CBaseActionEntity newCond = cond.GetSpecialAssignement(val, factory) ;
					if (newCond != null)
					{
						if (cond.Replace(newCond))
						{
							att.UnRegisterReadingAction(j);
							nbRead = att.GetNbReadingActions() ;
						}
						else
						{
							j++;
						}
					}
					else
					{
						j++ ;
					}
				}
			}
		}
		
		Collection<CDataEntity> col = factory.getAllSpecialConstantAttributes() ;
		for (CDataEntity e : col)
		{
			for (int i=0; i<e.GetNbReadReferences();i++)
			{
				CGenericDataEntityReference ref = e.GetReadReference(i);
				if (ref.GetNbWrittingActions() == 0)
				{
					for (int j=0; j<ref.GetNbReadingActions();)
					{
						CBaseActionEntity act = ref.GetActionReading(j) ;
						if (act.ReplaceVariable(ref, e))
						{
						}
						ref.UnRegisterReadingAction(act) ;
					}
					for (int j=0; j<ref.GetNbValueAccess();)
					{
						CBaseEntityCondExpr act = ref.GetValueAccess(j) ;
						if (act.ReplaceVariable(ref, e))
						{
						}
						ref.UnRegisterValueAccess(act) ;
					}
				}
			}
		}
	}
	
//	private class CodeRegion
//	{
//		public int start = 0 ;
//		public int end = 0 ;
//	}
	
	public void DoClearSymbolicMap(CObjectCatalog cat, CBaseEntityFactory factory)
	{
//		int lCopy = cat.GetLineOfMapCopy() ;
//		int lSend = cat.GetLineOfMapSend() ;
		
//		Vector<CodeRegion> arrCodeRegion = buildCodeRegionsOfSymbolicMapModification(cat) ;
		
		int nbSavFields = cat.GetNbSaveFields() ;
		for (int i=0; i<nbSavFields; i++)
		{
			CEntityResourceField savfield = cat.GetSaveField(i) ;
			CEntityResourceField field = cat.GetAssociatedField(savfield) ;
			if (field == null)
			{
				continue ;
			}
			String name = field.GetName() ;
			for (int j = 0; j<field.GetNbWrittingActions();)
			{
				CBaseActionEntity act = field.GetActionWriting(j);
				// try to find a move from a savfiled to the corresponding field
				if (savfield != null)
				{
					boolean bfound = false ;
					for (int k=0; k<savfield.GetNbReadingActions(); k++)
					{
						if (savfield.GetActionReading(k) == act)
						{
							Transcoder.logWarn(act.getLine(), "Unrecommanded field move") ;
							bfound = true ;
						}
					}
					for (int k=0; k<savfield.GetNbWrittingActions(); k++)
					{
						CBaseActionEntity actsav = savfield.GetActionWriting(k) ;
						if (Math.abs(actsav.getLine() - act.getLine()) <= 1)
						{
							bfound = true ;
						}
					}
					if (!bfound)
					{
						CDataEntity e = act.getValueAssigned() ;
						if (e != null)
						{
							Transcoder.logWarn(act.getLine(), "Move to symbolic map without move to save map") ;
						}
						else
						{
							int n=0 ;
						}
					}
				}
				
//				if (!isActionInCodeRegion(act, arrCodeRegion))
//				{ // drop write access to symbolic map
					if (!act.IgnoreVariable(field))
						j++ ;
//				}
//				else 
//				{
//					j++ ;
//				}
			}
			int n = field.GetNbWriteReferences() ;
			for (int j = 0; j<n; j++)
			{
				CGenericDataEntityReference ref = field.GetWriteReference(j);
				for (int k=0; k<ref.GetNbWrittingActions(); )
				{
					CBaseActionEntity act = ref.GetActionWriting(k) ;
					if (act != null)
					{
//						if (!isActionInCodeRegion(act, arrCodeRegion))
//						{ // drop write access to symbolic map
							if (!act.IgnoreVariable(ref))
								k++ ;
//						}
//						else
//						{
//							k++ ;
//						}
					}	
					else
					{
						k++ ;
					}
				}
			}
		}
		
		nbSavFields = cat.GetNbSaveFields() ;
		for (int i=0; i<nbSavFields; i++)
		{
			CEntityResourceField field = cat.GetSaveField(i) ;
			String name = field.GetName() ;
			for (int j = 0; j<field.GetNbWrittingActions(); )
			{
				CBaseActionEntity act = field.GetActionWriting(j);
//				if (!isActionInCodeRegion(act, arrCodeRegion))
//				{ // replace write access to save map by access to symbolic map
					CDataEntity var = cat.GetAssociatedField(field) ;
					if (var == null || !act.ReplaceVariable(field, var))
						j++ ;
//				}	
//				else
//				{
//					CBaseTranscoder.ms_logger.warn("WARNING Line "+act.GetLine()+" : access to save map") ;
//					if (!act.IgnoreVariable(field))
//						j++ ;
//				}
			}
			CDataEntity var = cat.GetAssociatedField(field) ;
			if (var != null)
			{
				field.ReplaceBy(var);
			}
		}
		
		int nbMaps = cat.GetNbSaveMap() ;
		for (int i=0; i<nbMaps; i++)
		{
			CEntityResourceForm sav = cat.GetSaveMap(i);
			CEntityResourceForm map = cat.GetAssociatedMap(sav) ;
			boolean bHasChildren = false ;
			
			CEntityInline act1 = map.GetInlineAction() ;
			CEntityInline act2 = sav.GetInlineAction() ;
			if (act2 != null && act1 != null)
			{
				Vector v = act2.GetListOfChildren() ;
				bHasChildren = v.size()>0 ;
				for(int j=0; j<v.size(); j++)
				{
					CBaseLanguageEntity e = (CBaseLanguageEntity)v.get(j);
					act1.AddChild(e) ;
					e.SetParent(act1) ;
				}
			} 
			else if (act1 == null && act2 != null)
			{
				act2.ReplaceExternalData(sav.m_Of, map.m_Of) ;
			}


			for (int j=0; j<sav.GetNbWrittingActions();)
			{
				CBaseActionEntity act = sav.GetActionWriting(j) ;
				if (!act.ReplaceVariable(sav, map))
					j++ ;
//				if (bHasChildren)
//				{
//				}
//				else
//				{
//					if (!act.IgnoreVariable(sav))
//						j++ ;
//				}
			}
			for (int j=0; j<sav.GetNbWriteReferences(); )
			{
				CGenericDataEntityReference ref = sav.GetWriteReference(j) ;
				if (bHasChildren)
				{
					for (int k=0; k<ref.GetNbWrittingActions(); )
					{
						if (!ref.ReplaceVariable(sav, map, false))
							k ++ ;
//						CBaseActionEntity act = ref.GetActionWriting(k) ;
//						if (act != null)
//						{
//							if (!act.IgnoreVariable(ref))
//								k++ ;
//						}
//						else
//							k++ ;
					}
					j++ ;
				}
				else
				{
					if (!ref.ReplaceVariable(sav, map, false))
					{
						j++ ;
					}
				}
			}
//			CEntityResourceForm map = cat.GetAssociatedMap(sav);
			sav.ReplaceBy(map) ;
		}
		
	}
//	private boolean isActionInCodeRegion(CBaseActionEntity act, Vector<CodeRegion> arrCodeRegion)
//	{
//		int line = act.GetLine() ;
//		for (int i=0; i<arrCodeRegion.size(); i++)
//		{
//			CodeRegion reg = arrCodeRegion.get(i) ;
//			if (reg.start <= line && reg.end >= line)
//			{
//				return true ;
//			}
//		}
//		return false;
//	}
//	private Vector<CodeRegion> buildCodeRegionsOfSymbolicMapModification(CObjectCatalog cat)
//	{
//		Vector<CodeRegion> arrCodeRegions = new Vector<CodeRegion>() ;
//		
//		int ncopy = cat.GetNbMapCopy() ;
//		int nsend = cat.GetNbMapSend() ;
//		
//		if (ncopy == 0 || nsend == 0)
//			return arrCodeRegions ;
//
//		for (int icopy=0; icopy<ncopy; icopy++)
//		{
//			CBaseActionEntity eCopy = cat.getMapCopy(icopy) ;
//			int nStartLine = eCopy.GetLine() ;
//			
//			CBaseLanguageEntity eParent = eCopy.GetParent() ;
//			CBaseLanguageEntity[] arrFollowingActions = eParent.GetChildrenList(eCopy, null) ;
//			for (int iFollowing=1; iFollowing<arrFollowingActions.length; iFollowing++)
//			{
//				CBaseActionEntity e = (CBaseActionEntity)arrFollowingActions[iFollowing] ;
//				boolean bFound = false ;
//				for (int isend=0; isend<nsend; isend++)
//				{
//					CBaseActionEntity eSend = cat.getMapSend(isend) ;
//					if (eSend == e)
//					{
//						CodeRegion reg = new CodeRegion();
//						reg.start = nStartLine ;
//						reg.end = eSend.GetLine() ;
//						arrCodeRegions.add(reg) ;
//						bFound = true ;
//					}
//				}
//				if (!bFound)
//				{
//					if (CEntityCallFunction.class.isInstance(e))
//					{
//						CEntityCallFunction eCall = (CEntityCallFunction)e ;
//						CEntityProcedure proc = eCall.getFirstProcedure() ;
//						Vector<CBaseLanguageEntity> arrChildren = proc.GetListOfChildren() ;
//						int nFirstChildLine = arrChildren.get(0).GetLine() ;
//						for (int iChild=0; iChild<arrChildren.size() && !bFound; iChild++)
//						{
//							CBaseLanguageEntity eChild = arrChildren.get(iChild) ;
//							for (int isend=0; isend<nsend; isend++)
//							{
//								CBaseActionEntity eSend = cat.getMapSend(isend) ;
//								if (eSend == e)
//								{
//									CodeRegion reg = new CodeRegion();
//									reg.start = nFirstChildLine ;
//									reg.end = eSend.GetLine() ;
//									arrCodeRegions.add(reg) ;
//									bFound = true ;
//								}
//							}
//						}
//						if (!bFound)
//						{
//							int nLastChildLine = arrChildren.get(arrChildren.size()-1).GetLine() ;
//							CodeRegion reg = new CodeRegion();
//							reg.start = nFirstChildLine ;
//							reg.end = nLastChildLine;
//							arrCodeRegions.add(reg) ;
//						}
//					}
//				}
//			}
//		}
//		
//		return arrCodeRegions ;
//	}
	public void DoExplicitDFHCommarea(CObjectCatalog cat, CBaseEntityFactory factory)
	{
		CEntityDataSection linkage = cat.getLinkageSection();
		if (linkage == null)
		{
			//throw new NacaTransAssertException("Missing LINKAGE SECTION") ;
			return ;
		}
		NotifIsUsedCICSPreprocessor notifIsUsed = new NotifIsUsedCICSPreprocessor() ;
		cat.SendNotifRequest(notifIsUsed) ;
		if (notifIsUsed.m_bUsed)
		{
			Vector<CBaseLanguageEntity> arrChildren = linkage.GetListOfChildren() ;
			boolean bHasStructure = false ;
			CDataEntity dfh = null ;
			for (int i=0; i<arrChildren.size(); i++)
			{
				CBaseLanguageEntity e = arrChildren.get(i);
				if (e.GetName().equalsIgnoreCase("DFHCOMMAREA"))
				{
					bHasStructure = true ;
					dfh = (CDataEntity)e ;
				}
			}
			if (!bHasStructure)
			{
				CEntityStructure s = factory.NewEntityStructure(0, "DFHCOMMAREA", "01") ;
				s.SetTypeString(1) ;
				dfh = s ;
				linkage.AddChild(dfh);
			}
			
			CEntityProcedureDivision prodiv = cat.getProcedureDivision() ;
			if (prodiv != null)
			{
				Vector<CDataEntity> arrParam = prodiv.getCallParameters() ;
				if (arrParam.size() > 0)
				{
					CDataEntity e = arrParam.get(0);
					if (e.GetName().equalsIgnoreCase("DFHCOMMAREA"))
					{
						arrParam.insertElementAt(dfh, 0);
					}
				}
				else
				{
					arrParam.add(dfh);
				}
			}
		}
	}
	public void DoSimplifyDFHCommArea(CObjectCatalog cat)
	{
		CEntityDataSection linkage = cat.getLinkageSection();
		if (linkage == null)
		{
			//throw new NacaTransAssertException("Missing LINKAGE SECTION") ;
			return ;
		}
		Vector<CBaseLanguageEntity> arrChildren = linkage.GetListOfChildren() ;
		CEntityStructure dfh = null ;
		for (int i=0; i<arrChildren.size(); i++)
		{
			CBaseLanguageEntity e = arrChildren.get(i);
			if (e.GetName().equalsIgnoreCase("DFHCOMMAREA"))
			{
				dfh = (CEntityStructure)e ;
			}
		}
		if (dfh != null)
		{
			DoSimplifyDFHCommAreaForEntry(cat, dfh) ;
		}
	}
	public void DoSimplifyDFHCommAreaForEntry(CObjectCatalog cat, CEntityStructure eData)
	{
		if (eData != null)
		{
			CDataEntity eDep = eData.getTableSizeDepending() ;
			if (eDep != null && eDep.GetName().equals("EIBCALEN"))
			{
				int pic = eData.getVariableSize() ;
				int occurs = eData.getTableSizeAsInt() ;
				if (occurs / pic > 100)
				{
					eData.SetTableSizeDepending(null, null) ;
					eData.SetTypeString(occurs * pic) ;
				}
				return ;
			}
			Vector<CBaseLanguageEntity> arrChildren = eData.GetListOfChildren() ;
			for (int i=0; i<arrChildren.size(); i++)
			{
				CBaseLanguageEntity e = arrChildren.get(i);
				if (CEntityStructure.class.isInstance(e))
				{
					CEntityStructure struct = (CEntityStructure)e ;
					DoSimplifyDFHCommAreaForEntry(cat, struct) ;
				}
			}
		}
		
	}
	public void DoRegisterPFKeys(CObjectCatalog cat)
	{
		CEntityResourceForm form = cat.GetMap(0) ;
		if (form == null)
		{
			return ;
		}
		String[][] pfDefinition = {
				{"DFHPF1", "pf1"},
				{"DFHPF2", "pf2"},
				{"DFHPF3", "pf3"},
				{"DFHPF4", "pf4"},
				{"DFHPF5", "pf5"},
				{"DFHPF6", "pf6"},
				{"DFHPF7", "pf7"},
				{"DFHPF8", "pf8"},
				{"DFHPF9", "pf9"},
				{"DFHPF10", "pf10"},
				{"DFHPF11", "pf11"},
				{"DFHPF12", "pf12"},
				{"DFHPF13", "pf13"},
				{"DFHPF14", "pf14"},
				{"DFHPF15", "pf15"},
				{"DFHPF16", "pf16"},
				{"DFHPF17", "pf17"},
				{"DFHPF18", "pf18"},
				{"DFHPF19", "pf19"},
				{"DFHPF20", "pf20"},
				{"DFHPF21", "pf21"},
				{"DFHPF22", "pf22"},
				{"DFHPF23", "pf23"},
				{"DFHPF24", "pf24"}};
		CDataEntity pf;
		if (form.getPFActive("enter") == null)
		{
			// The PF Enter is always set to true
			form.setPFActive("enter", "true");
		}
		for (int i=0; i < pfDefinition.length; i++) {
			if (form.getPFActive(pfDefinition[i][1]) == null)
			{
				pf =  cat.GetDataEntity(pfDefinition[i][0], "");
				if (pf.GetNbValueAccess() > 0)
				{
					form.setPFActive(pfDefinition[i][1], "true");
				}
				else
				{
					form.setPFActive(pfDefinition[i][1], "false");
				}
			}	
		}
	} 
	public void DoReplaceCall_RS7ZPA04(CObjectCatalog cat, CBaseEntityFactory factory)
	{
		int n = cat.getNbCICSLink() ;
		for (int i=0; i<n; i++)
		{
			CEntityCICSLink link = cat.getCICSLink(i);
			if (link != null) 
			{
				CDataEntity ePrg = link.GetProgramReference() ;
				String prg = ePrg.GetConstantValue() ;
				if (prg != null && prg.equalsIgnoreCase("RS7ZS04"))
				{
					CDataEntity param = link.GetCommareaParameter() ;
					
					// find parameter
					if (param.GetName().equals("RS7ZPA04"))
					{
						Vector<CBaseLanguageEntity> v = param.GetListOfChildren() ;
						for (CBaseLanguageEntity ent : v)
						{
							if (ent.GetName().equals("TRTMASQUE"))
							{
								CDataEntity pass = (CDataEntity)ent ;
								// find form 
								int nbWrite = pass.GetNbWrittingActions() ;
								for (int k=0; k<nbWrite; k++)
								{
									CBaseActionEntity wr = pass.GetActionWriting(k) ;
									CDataEntity form = wr.getValueAssigned() ;
									if (form.GetDataType() == CDataEntity.CDataEntityType.FORM)
									{
										Tag el = GetAlternativeTreatment(link.GetProgramName()) ;
										if (el == null)
										{ // replace LINK by the routine emulation call
											CEntityRoutineEmulationCall call = factory.NewEntityRoutineEmulationCall(link.getLine()) ;											 
											call.SetDisplay("Pub2000Routines.fillDefaultValueFromDB") ;
											call.AddParameter(factory.NewEntityNumber("getProgramManager()"));
											call.AddParameter(param) ;
											call.AddParameter(form) ;
											link.GetParent().UpdateAction(link, call);
										}
										else
										{ // the call to the routine emulation is under condition
											CBaseEntityCondition cond  = makeIfStatement(el, cat, factory) ;

											// make bloc if true
											CEntityRoutineEmulationCall call1 = factory.NewEntityRoutineEmulationCall(link.getLine()) ;											 
											call1.SetDisplay("Pub2000Routines.fillDefaultValueFromDB") ;											
  											call1.AddParameter(factory.NewEntityNumber("getProgramManager()"));
  											call1.AddParameter(param) ; //
											call1.AddParameter(form) ;
											CDataEntity list1 = makeListOfFields(el, cat, factory, form, true) ;
											call1.AddParameter(list1) ;
											CEntityBloc bloc1 = factory.NewEntityBloc(0) ;
											bloc1.AddChild(call1) ;
											
											// make bloc if false
											CEntityRoutineEmulationCall call2 = factory.NewEntityRoutineEmulationCall(link.getLine()) ;											 
											call2.SetDisplay("Pub2000Routines.fillDefaultValueFromDB") ;
											call2.AddParameter(factory.NewEntityNumber("getProgramManager()"));
											call2.AddParameter(param) ;
											call2.AddParameter(form) ;
											CDataEntity list2 = makeListOfFields(el, cat, factory, form, false) ;
											call2.AddParameter(list2) ;
											CEntityBloc bloc2 = factory.NewEntityBloc(0) ;
											bloc2.AddChild(call2) ;

											CEntityCondition ifStatement = factory.NewEntityCondition(link.getLine()) ;
											ifStatement.SetCondition(cond, bloc1, bloc2) ;
											link.GetParent().UpdateAction(link, ifStatement);
										}
										pass.ReplaceBy(form) ;
										
										// remove any treatment on passzone
										int nbRead = pass.GetNbReadingActions() ;
										for (int l=0; l<nbRead; l++)
										{
											CBaseActionEntity act = pass.GetActionReading(l) ;
											Vector vVars = act.getVarsAssigned() ; 
											for (int m=0; m<vVars.size(); m++)
											{
												CDataEntity e = (CDataEntity)vVars.get(m) ;
												e.SetIgnoreStructure() ;
											}
										}
									}
									else
									{
										form.GetDataType() ;
										throw new NacaTransAssertException("Unexpecting situation in treatment of RS7ZS04") ;
									}
								}
							}
						}
					}
					else
					{
						throw new RuntimeException() ;
					}
				}
			}
		}
	}
	/**
	 * @param el
	 * @param cat
	 * @param factory
	 * @param b
	 * @return
	 */
	private CDataEntity makeListOfFields(Tag el, CObjectCatalog cat, CBaseEntityFactory factory, CDataEntity form, boolean b)
	{
		String label = "testFalse" ;
		if (b)
		{
			label = "testTrue" ;
		}
		CEntityList list = factory.NewEntityList("") ;
		Tag tagLabel = el.getChild(label) ;
		if (tagLabel != null)
		{
			TagCursor cur = new TagCursor() ;
			Tag ef = tagLabel.getFirstChild(cur) ;
			while (ef != null)
			{
				String name = ef.getVal("name") ;
				CDataEntity f = cat.GetDataEntity(name, form.GetName());
				if (f != null && f.GetDataType()==CDataEntity.CDataEntityType.FIELD)
				{
					CEntityResourceField field = (CEntityResourceField)f ;
					String disp = field.GetDisplayName() ;
					CEntityString str = factory.NewEntityString(disp) ;
					list.AddData(str) ;
				} 
				ef = el.getNextChild(cur) ;
			}
		}
		return list ;
	}
	/**
	 * @param el
	 * @param cat
	 * @param factory
	 * @return
	 */
	private CBaseEntityCondition makeIfStatement(Tag el, CObjectCatalog cat, CBaseEntityFactory factory)
	{
		String varName = el.getVal("testVariable") ;
		CCobolLexer lexer = new CCobolLexer(0, varName.length()) ;
		boolean bOk = lexer.StartLexer(varName, null) ;
		CTokenList lstTokens = lexer.GetTokenList() ;
		CIdentifier id = CCobolElement.ReadIdentifier(lstTokens) ;
		CDataEntity eVar = id.GetDataReference(0, factory) ;
		if (eVar == null)
		{
			throw new NacaTransAssertException("Not found variable "+id.GetName());
		}

		String csTestValue = el.getVal("testValue");
		String csType = "STRING" ;
		int nposDot = csTestValue.indexOf(".") ;
		if (nposDot>0)
		{
			csType = csTestValue.substring(0, nposDot) ;
			csTestValue = csTestValue.substring(nposDot+1) ;
		}
		CDataEntity eVal = null ;
		if (csType.equalsIgnoreCase("STRING"))
		{
			eVal = factory.NewEntityString(csTestValue) ;
		}
		else if (csType.equalsIgnoreCase("INT"))
		{
			eVal = factory.NewEntityNumber(csTestValue) ;
		}
		else
		{
			throw new NacaTransAssertException("Bad entity type : "+csType);
		}
		
		CBaseEntityExpression exp1 = factory.NewEntityExprTerminal(eVar) ;
		CBaseEntityExpression exp2 = factory.NewEntityExprTerminal(eVal) ;
		CEntityCondEquals eq = factory.NewEntityCondEquals() ;
		eq.SetEqualCondition(exp1, exp2) ;
		return eq ;
	}
	/**
	 * @param string
	 * @return
	 */
	private Tag GetAlternativeTreatment(String string)
	{
		CRulesManager manager = CRulesManager.getInstance() ;
		int n=manager.getNbRules("RS7ZS04AlternativeTreatment") ;
		for (int i=0; i<n; i++)
		{
			Tag e = manager.getRule("RS7ZS04AlternativeTreatment", i) ;
			String prg = e.getVal("program") ;
			if (prg.equals(string))
			{
				return e ;
			}
		}
		return null ;
	}
	protected CEntityRenamer m_SubProgramRenamer = null ;
	
	protected void InitSubProgramRenamer()
	{
		m_SubProgramRenamer = new CEntityRenamer() ;
		CRulesManager manager = CRulesManager.getInstance() ;
		int n = manager.getNbRules("subProgramRename") ;
		for (int i=0; i<n; i++)
		{
			Tag e = manager.getRule("subProgramRename", i) ;
			String csMask = e.getVal("subProgramMask");
			String action = e.getVal("action") ;
			String param = e.getVal("param");
			m_SubProgramRenamer.AddRule(csMask, action, param);
		}
	}

	public void DoRenameSubPrograms(CObjectCatalog cat, CBaseEntityFactory factory)
	{
		if (m_SubProgramRenamer == null)
		{
			InitSubProgramRenamer();
		}
		int n = cat.getNbCICSLink() ;
		for (int i=0; i<n; i++)
		{
			CEntityCICSLink link = cat.getCICSLink(i);
			if (link != null) 
			{
				CDataEntity ePrg = link.GetProgramReference() ;
				String prg = ePrg.GetConstantValue() ;
				if (prg != null && !prg.equals(""))
				{
					String newprg = m_SubProgramRenamer.FindAndApplyRule(prg);
					if (newprg!=null && !newprg.equals(prg))
					{
						CEntityString str = factory.NewEntityString(newprg);
						CEntityCallProgram call = factory.NewEntityCallProgram(link.getLine(), str) ;
						boolean b = cat.m_Global.CheckProgramReference(newprg, false, 1, true) ;
						call.setChecked(b) ;
						CDataEntity param = link.GetCommareaParameter() ;
						call.SetParameterByRef(param) ;
						link.GetParent().UpdateAction(link, call);
					}
					else if (!link.isReferenceChecked())
					{
						Transcoder.logError("Missing sub-program : "+prg);
					}
				}
			}
		}
		
		n = cat.getNbCallProgram() ;
		for (int i=0; i<n; i++)
		{
			CEntityCallProgram call = cat.getCallProgram(i);
			if (call != null) 
			{
				CDataEntity ePrg = call.getProgramReference() ;
				String prg = ePrg.GetConstantValue() ;
				if (prg != null && !prg.equals(""))
				{
					String newprg = m_SubProgramRenamer.FindAndApplyRule(prg);
					if (newprg!=null && !newprg.equals(prg))
					{
						CDataEntity eNewProgram = factory.NewEntityString(newprg);
						call.UpdateProgramReference(eNewProgram) ;
						boolean b = cat.m_Global.CheckProgramReference(newprg, false, 1, true) ;
						call.setChecked(b) ;
					}
				}
			}
		}
	}
	
	public void DoReplacePerformThrough(CObjectCatalog cat, CBaseEntityFactory factory)
	{
		for (int i=0; i<cat.getNbPerformThrough(); i++)
		{
			CEntityCallFunction ePTh = cat.getPerformThrough(i) ;
			CEntityProcedure eStart = ePTh.getFirstProcedure() ;
			CEntityProcedure eEnd = ePTh.getLastProcedure();
			CBaseLanguageEntity eParent = eStart.GetParent() ;
			if (eEnd.GetParent() == eParent)
			{	// if the 2 paragraphs are not in the same section, nothing can be done...
				CBaseLanguageEntity[] arrChildren = eParent.GetChildrenList(eStart, eEnd) ;
				if (arrChildren!= null && arrChildren.length == 2)
				{
					CEntityCallFunction eCall1 = factory.NewEntityCallFunction(ePTh.getLine(), eStart.GetName(), "", null) ;
					CEntityCallFunction eCall2 = factory.NewEntityCallFunction(ePTh.getLine(), eEnd.GetName(), "", null) ;
					ePTh.GetParent().ReplaceChild(ePTh, eCall1);
					ePTh.GetParent().AddChild(eCall2, eCall1) ;
				}
				else
				{
					int n=0;
				}
			}
		}
	}
	
	public void DoReplaceMapName(CObjectCatalog cat, CBaseEntityFactory factory)
	{
		int n = cat.getNbInitializedStructure() ;
		for (int i=0; i<n; i++)
		{
			CEntityAttribute es = cat.getInitializedStructure(i) ;
			String cs = es.GetInitialValue() ;
			CDataEntity le = factory.getSpecialConstantValue(cs) ;
			if (le != null)
			{
				es.SetInitialValue(le) ;
			}
		}
		
	}
	
	public void DoReduceSections(CObjectCatalog cat, CBaseEntityFactory factory)
	{
		CEntityProcedureDivision div = cat.getProcedureDivision() ;
		ProcedureCallTree tree = cat.getCallTree() ;

		tree.ComputeTree() ;
		
		// look for dead code
		tree.DoFilterSections(factory) ;

//		int nb = cat.getNbSections() ;
//		boolean bCanReduce = true ;
//		for (int i=0; i<nb; i++)
//		{
//			CEntityProcedureSection section = cat.getProcedureSection(i) ;
//			CBaseLanguageEntity[] children = section.GetChildrenList(null, null) ;
//			if (children.length==0)
//			{
//				// bCanReduce = true ;
//			}
//			else if (children.length==1)
//			{
//				bCanReduce |= children[0].ignore() ;
//			}
//			else if (children.length>1)
//			{
//				bCanReduce |= children[0].ignore() ;
//			}
//		}
	}
	/**
	 * @param cat
	 */
	public void DoSimplifyFDVariableZones(CObjectCatalog cat, CBaseEntityFactory factory)
	{
		CEntityDataSection depSection = factory.NewEntityDataSection(0, "DependencySection") ;
		depSection.AddChild(factory.NewEntityComment(0, "***********************************************************")) ;
		depSection.AddChild(factory.NewEntityComment(0, "* FileDescriptor length-dependency Section")) ;
		depSection.AddChild(factory.NewEntityComment(0, "***********************************************************")) ;
		boolean bFound = false ;
		for (CEntityFileDescriptor desc : cat.getFileDescriptors())
		{
			CDataEntity var = desc.getRecordSizeDepending() ;
			CDataEntity var2 = findVariableVarInChildren(desc) ;
			if (var == null)
				var = var2 ;
			if (var != null)
			{
				CEntityFileDescriptorLengthDependency dep = factory.NewEntityFileDescriptorLengthDependency(desc.GetName()+ "_dependency") ;
				dep.setDependency(desc, var) ;
				depSection.AddChild(dep) ;
				bFound = true ;
			}
			else
			{
				//checkBinaryFieldsInChildren(desc) ;
			}
		}
		
		if (bFound) 
		{
			CEntityDataSection linkage = cat.getLinkageSection() ;
			if (linkage == null)
			{
				linkage = cat.getWorkingSection() ;
			}
			linkage.GetParent().AddChild(depSection, linkage) ;
		}
		
	}
	/**
	 * @param desc
	 * @param factory 
	 * @param depSection 
	 * @return
	 */
	
	
	private void checkBinaryFieldsInChildren(CBaseLanguageEntity desc)
	{
		for (CBaseLanguageEntity le : desc.GetListOfChildren())
		{
			if  (le.GetInternalLevel()>0 && CEntityStructure.class.isInstance(le))
			{
				CEntityStructure struct = (CEntityStructure) le ;
				if (struct.getComp() != null && (struct.getComp().equalsIgnoreCase("COMP4") || struct.getComp().equalsIgnoreCase("COMP")))
				{
					Transcoder.logWarn(struct.getLine(), "COMP4 in fixed length file structure : "+struct.ExportReference(0)) ;
				}
				if (struct.HasChildren())
				{
					checkBinaryFieldsInChildren(le) ;
				}
			}
			else if (CEntityInline.class.isInstance(le))
			{
				CEntityInline inline = (CEntityInline)le ;
				CBaseExternalEntity ext = inline.getExternalEntity() ;
				checkBinaryFieldsInChildren(ext) ;
			}
		}
	}
	
	private CDataEntity findVariableVarInChildren(CBaseLanguageEntity desc)
	{
		for (CBaseLanguageEntity le : desc.GetListOfChildren())
		{
			if  (le.GetInternalLevel()>0 && CEntityStructure.class.isInstance(le))
			{
				CEntityStructure struct = (CEntityStructure) le ;
				CDataEntity dep = struct.getTableSizeDepending() ;
				if (dep == null)
				{
					dep = findVariableVarInChildren(le) ;
					if (dep != null)
					{
						return dep ;
					}
				}
				else
				{
					struct.SetTableSizeDepending(struct.getTableSize(), null) ;
					return dep ;
				}
			}
			else if (CEntityInline.class.isInstance(le))
			{
				CEntityInline inline = (CEntityInline)le ;
				CBaseExternalEntity ext = inline.getExternalEntity() ;
				CDataEntity dep = findVariableVarInChildren(ext) ;
				if (dep != null)
				{
					return dep ;
				}
			}
		}
		return null ;
	}

}
