/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 6 juin 2005
 *
 */
package utils.CobolTranscoder;

import java.util.Hashtable;
import java.util.Vector;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CEntityProcedure;
import semantic.CEntityProcedureDivision;
import semantic.CEntityProcedureSection;
import semantic.CProcedureReference;
import semantic.Verbs.CEntityCallFunction;
import semantic.Verbs.CEntityGoto;
import semantic.Verbs.CEntityReturn;
import utils.Transcoder;
import utils.NacaTransAssertException;

/**
 * @author U930CV
 *
 */
public class ProcedureCallTree
{
	private class BaseNode
	{
		boolean m_bExplicitCallByGoto = false ;
		boolean m_bExplicitCallAsProcedure = false ;
		boolean m_bImplicitCall = false ;
		boolean m_bHasExplicitGetOut = false ;
		Vector<CEntityCallFunction> m_arrProcedureCallRef = new Vector<CEntityCallFunction>() ;
		Vector<CEntityGoto> m_arrGotoRef = new Vector<CEntityGoto>() ;
		Vector<BaseNode> m_arrProcedureCallNode = new Vector<BaseNode>() ;
		Vector<BaseNode> m_arrGotoNode = new Vector<BaseNode>() ;
		public boolean isCalled()
		{
			return m_bExplicitCallByGoto || m_bExplicitCallAsProcedure || m_bImplicitCall ;
		}
	}
	
	private class RootNodeDivision extends BaseNode
	{
		public RootNodeDivision()
		{
			m_bImplicitCall = true ;
		}
		public CEntityProcedureDivision m_div = null ;
		public Vector<NodeSection> m_arrSections = new Vector<NodeSection>() ;
		public Vector<NodeProcedure> m_arrProcedures = new Vector<NodeProcedure>() ;
		public Vector<CProcedureReference> m_arrGlobalGotoRef = new Vector<CProcedureReference>() ;
	}
	private class NodeSection extends BaseNode
	{
		public NodeSection(CEntityProcedureSection div)
		{
			m_Sec = div ;
		}
		public CEntityProcedureSection m_Sec = null ;
		public Vector<NodeProcedure> m_arrProcedures = new Vector<NodeProcedure>() ;
		/**
		 * @return
		 */
	}
	private class NodeProcedure extends BaseNode
	{
		public NodeProcedure(CEntityProcedure div)
		{
			m_Proc = div ;
		}
		public CEntityProcedure m_Proc = null ;
	}
	
	/**
	 * @param division
	 */
	public void SetProcedureDivision(CEntityProcedureDivision division)
	{
		m_root.m_div = division ;
	}
	
	protected RootNodeDivision m_root = new RootNodeDivision() ;
	protected NodeSection m_currentNodeSection = null ;
	protected NodeProcedure m_currentNodeProcedure = null ;
	protected Hashtable<CEntityProcedure, BaseNode> m_tabProcedureNodes = new Hashtable<CEntityProcedure, BaseNode>() ;
	
	/**
	 * @param cont
	 */
	public void RegisterProcedure(CEntityProcedure cont)
	{
		NodeProcedure node = new NodeProcedure(cont) ;
		m_tabProcedureNodes.put(cont, node) ;
		m_currentNodeProcedure = node ;
		if (m_currentNodeSection != null)
		{
			m_currentNodeSection.m_arrProcedures.add(node) ;
		}
		else
		{
			m_root.m_arrProcedures.add(node) ;
		}
	}

	/**
	 * @param sec
	 */
	public void RegisterSection(CEntityProcedureSection sec)
	{
		NodeSection node = new NodeSection(sec) ;
		m_tabProcedureNodes.put(sec, node) ;
		m_currentNodeSection = node ;
		m_currentNodeProcedure = null ;
		m_root.m_arrSections.add(node) ;
	}

	/**
	 * @param ref
	 */
	public void RegisterGoto(CEntityGoto ref)
	{
		if (m_currentNodeProcedure != null)
		{
			m_currentNodeProcedure.m_arrGotoRef.addElement(ref) ;
		}
		else if (m_currentNodeSection != null)
		{
			m_currentNodeSection.m_arrGotoRef.addElement(ref) ;
		}
		else
		{
			m_root.m_arrGotoRef.addElement(ref) ;
		}
	}

	/**
	 * @param ref
	 */
	public void RegisterProcedureCall(CEntityCallFunction ref)
	{
		if (m_currentNodeProcedure != null)
		{
			m_currentNodeProcedure.m_arrProcedureCallRef.addElement(ref) ;
		}
		else if (m_currentNodeSection != null)
		{
			m_currentNodeSection.m_arrProcedureCallRef.addElement(ref) ;
		}
		else
		{
			m_root.m_arrProcedureCallRef.addElement(ref) ;
		}
	}

	/**
	 * 
	 */
	public void ComputeTree()
	{
		if (m_root.m_div == null)
		{
			return ;
		}
		// compute all the tree : analyse calls in each procedure
		ComputeNodeCalls(m_root) ;
		for (int i=0; i<m_root.m_arrProcedures.size(); i++)
		{
			NodeProcedure node = m_root.m_arrProcedures.get(i) ;
			ComputeNodeCalls(node) ;
		}
		for (int i=0; i<m_root.m_arrSections.size(); i++)
		{
			NodeSection node = m_root.m_arrSections.get(i) ;
			ComputeNodeCalls(node) ;
			for (int j=0; j<node.m_arrProcedures.size(); j++)
			{
				NodeProcedure nodeP = node.m_arrProcedures.get(j) ;
				ComputeNodeCalls(nodeP) ;
			}
		}
		
		// analyse status of each procedure
		boolean bProcedureDivisionFinished = m_root.m_div.hasExplicitGetout() ;
		m_root.m_bHasExplicitGetOut = bProcedureDivisionFinished ;
		
		boolean bPrecedentFinished = bProcedureDivisionFinished ;
		for (int i=0; i<m_root.m_arrProcedures.size(); i++)
		{
			NodeProcedure node = m_root.m_arrProcedures.get(i) ;
			String nameP = node.m_Proc.GetName() ;
			node.m_bImplicitCall = !bPrecedentFinished ;
			if (node.m_bImplicitCall)
			{
				Transcoder.logDebug("Procedure implicitly called : "+nameP) ;
			}
			node.m_bHasExplicitGetOut = node.m_Proc.hasExplicitGetOut() ;

			bPrecedentFinished = node.m_bExplicitCallAsProcedure || node.m_bHasExplicitGetOut ;
		}
		
		for (int i=0; i<m_root.m_arrSections.size(); i++)
		{
			NodeSection node = m_root.m_arrSections.get(i) ;
			String name = node.m_Sec.GetName() ;
			node.m_bImplicitCall = !bPrecedentFinished ;
			if (node.m_bImplicitCall)
			{
				Transcoder.logDebug("Section implicitly called : "+name) ;
			}
			node.m_bHasExplicitGetOut = node.m_Sec.hasExplicitGetOut() ;
			
			boolean bPrecedentParagraphFinished = !(node.isCalled() && !node.m_bHasExplicitGetOut) ;
			for (int j=0; j<node.m_arrProcedures.size(); j++)
			{
				NodeProcedure nodeP = node.m_arrProcedures.get(j) ;
				String nameP = nodeP.m_Proc.GetName() ;
				nodeP.m_bImplicitCall = !bPrecedentParagraphFinished ;
				if (nodeP.m_bImplicitCall)
				{
					Transcoder.logDebug("Procedure implicitly called : "+nameP) ;
				}
				nodeP.m_bHasExplicitGetOut = nodeP.m_Proc.hasExplicitGetOut() ;

				bPrecedentParagraphFinished = nodeP.m_bExplicitCallAsProcedure || nodeP.m_bHasExplicitGetOut ;
			}

			bPrecedentFinished = node.m_bExplicitCallAsProcedure || !node.isCalled() || node.m_bHasExplicitGetOut  ;
			
			
		}
		
	}

	/**
	 * @param root
	 */
	private void ComputeNodeCalls(RootNodeDivision node)
	{
		ComputeNodeCalls((BaseNode)node) ;
		for (int i=0; i<node.m_arrGlobalGotoRef.size(); i++)
		{
			CProcedureReference ref = node.m_arrGlobalGotoRef.get(i) ;
			CEntityProcedure proc = ref.getProcedure() ;
			BaseNode n = m_tabProcedureNodes.get(proc) ;
			n.m_bExplicitCallByGoto = true ;
		}
	}

	/**
	 * @param root
	 */
	private void ComputeNodeCalls(BaseNode node)
	{
		for (int i=0; i<node.m_arrGotoRef.size(); i++)
		{
			CEntityGoto gto = node.m_arrGotoRef.get(i);
			CProcedureReference ref = gto.getReference() ;
			CEntityProcedure proc = ref.getProcedure() ;
			if (proc != null)
			{
				BaseNode p = m_tabProcedureNodes.get(proc) ;
				if (p!=null)
				{
					p.m_bExplicitCallByGoto = true ;
					node.m_arrGotoNode.add(p) ;
				}
			}
		}
		for (int i=0; i<node.m_arrProcedureCallRef.size(); i++)
		{
			CEntityCallFunction call = node.m_arrProcedureCallRef.get(i);
			CProcedureReference ref = call.getReference() ; 
			CEntityProcedure proc = ref.getProcedure() ;
			if (proc != null)
			{
				BaseNode p = m_tabProcedureNodes.get(proc) ;
				if (p!=null)
				{
					p.m_bExplicitCallAsProcedure = true ;
					node.m_arrProcedureCallNode.add(p) ;
				}
			}
		}
	}

	/**
	 * 
	 */
	public void DoFilterSections(CBaseEntityFactory factory)
	{
		for (int i=0; i<m_root.m_arrProcedures.size(); i++)
		{
			NodeProcedure nodeP = m_root.m_arrProcedures.get(i) ;
			String nameP = nodeP.m_Proc.GetName() ;
			if (!nodeP.isCalled())
			{
				// this procedure is never called...
				Transcoder.logDebug("Procedure ignored (4) : "+nameP) ;
				nodeP.m_Proc.setIgnore() ;
			}
			else if (nodeP.m_Proc.isEmpty())
			{
				// this procedure do nothing...
				Transcoder.logDebug("Procedure ignored (5) : "+nameP) ;
				nodeP.m_Proc.setIgnore() ;
			}
			else if (i==0 && nodeP.m_bImplicitCall && nodeP.m_bHasExplicitGetOut)
			{
				if (m_root.m_div.getProcedureBloc() != null)
				{
					CEntityCallFunction ePerform = factory.NewEntityCallFunction(0, nameP, "", null) ;
					m_root.m_div.getProcedureBloc().AddChild(ePerform) ;
					Transcoder.logDebug("Perform to "+nameP+" added to procedure division") ;
					nodeP.m_bImplicitCall = false ;
					nodeP.m_bExplicitCallAsProcedure = true ;
				}
			}
		}
		
		boolean bAllSectionsAreReduced = true ;  // flag to tell is all sections before the current one have been reduced ;
												// if so, we can reduce current one, else we can't
		for (int i=0; i<m_root.m_arrSections.size(); i++)
		{
			NodeSection node = m_root.m_arrSections.get(i) ;
			String name = node.m_Sec.GetName() ;
			
			boolean bIgnoreAllProcedures = true ;
			boolean bCanReduceCurrentSection = true ;  // flag to tell if current section can be reduced :
						// -> no procedure in it, or procedures can be ignored (never called or empty)
						// -> no implicit call between procedures, and all procedures are called by perform : section can be reduce to procedure
			int nbValidProcedures = 0 ;
			NodeProcedure lastValidProcedure = null ;
			for (int j=0; j<node.m_arrProcedures.size(); j++)
			{
				NodeProcedure nodeP = node.m_arrProcedures.get(j) ;
				String nameP = nodeP.m_Proc.GetName() ;
				if (!nodeP.isCalled())
				{
					// this procedure is never called...
 					Transcoder.logDebug("Procedure ignored (3) : "+nameP) ;
					nodeP.m_Proc.setIgnore() ;
				}
				else if (nodeP.m_Proc.isEmpty())
				{
					// this procedure do nothing...
					if (nodeP.m_bImplicitCall && !nodeP.m_bExplicitCallAsProcedure && !nodeP.m_bExplicitCallByGoto)
					{
						// simply ignore current procedure
						Transcoder.logDebug("Procedure ignored (2) : "+nameP) ;
						nodeP.m_Proc.setIgnore() ;
					}
					else if (nodeP.m_bExplicitCallAsProcedure)
					{
						// simply ignore current procedure, call will be ignore too
						Transcoder.logDebug("Procedure ignored (1) : "+nameP) ;
						nodeP.m_Proc.setIgnore() ;
					}
					else if (nodeP.m_bExplicitCallByGoto)
					{
						if (j==node.m_arrProcedures.size()-1  && node.m_bExplicitCallAsProcedure)
						{ // if this is the last procedure => EXIT procedure
							for (int k=0; k<node.m_arrGotoRef.size(); k++)
							{
								CEntityGoto gto = node.m_arrGotoRef.get(k) ;
//								Vector v = gto.GetParent().GetListOfChildren() ;
//								int index = v.size()-1  ;
//								if (v.get(index) == gto)
//								{
//									CEntityNoAction ret = factory.NewEntityNoAction(gto.GetLine()) ;
//									gto.Replace(ret) ;
//									ret.SetParent(gto.GetParent()) ;
//								}
//								else
//								{
									CEntityReturn ret = factory.NewEntityReturn(gto.getLine()) ;
									ret.SetOnlyReturnFromProcedure() ;
									gto.Replace(ret) ;
									ret.SetParent(gto.GetParent()) ;
//								}
							}
							Transcoder.logDebug("Procedure ignored (6) : "+nameP) ;
							nodeP.m_Proc.setIgnore() ;
						}
						else
						{ // change GOTO to the next procedure
							NodeProcedure nextnode = node.m_arrProcedures.get(j+1) ;
							throw new NacaTransAssertException("unmanaged situation") ;
						}
					}
				}
				else if (!nodeP.m_bExplicitCallAsProcedure && !nodeP.m_bExplicitCallByGoto && nodeP.m_bImplicitCall)
				{  // in this case, the procedure is never called by itself, and can be suppressed, its content added to the previous procedure.
					if (nbValidProcedures == 0)
					{
						CBaseLanguageEntity[] lst = nodeP.m_Proc.GetChildrenList(null, null) ;
						if (node.m_Sec.getSectionBloc() == null)
						{
							node.m_Sec.SetSectionBloc(factory.NewEntityBloc(node.m_Sec.getLine())) ;
						}
						for (int k=0; k<lst.length; k++)
						{
							CBaseLanguageEntity le = lst[k] ;
							node.m_Sec.getSectionBloc().AddChild(le) ;
						}
						Transcoder.logDebug("Procedure "+nameP+" merged into "+name) ;
						nodeP.m_Proc.setIgnore() ;
					}
					else
					{
						throw new NacaTransAssertException("unmanaged situation") ;
					}
				}
				else if (nodeP.m_bExplicitCallAsProcedure && !nodeP.m_bExplicitCallByGoto && !nodeP.m_bImplicitCall)
				{  // in this case, the section can be reduced, because the procedure doesn't need a section and can be alone in the programme.
					bIgnoreAllProcedures = false ;
					lastValidProcedure = nodeP ;
					nbValidProcedures ++;
				}
				else
				{
					nbValidProcedures ++;
					lastValidProcedure = nodeP ;
					bIgnoreAllProcedures = false ;
					bCanReduceCurrentSection = false ;
				}
			}			
			
			if (!node.isCalled())
			{
				// this section is never called...
				if (bIgnoreAllProcedures)
				{	// all of the procedures are never called
					node.m_Sec.setIgnore() ;
					Transcoder.logDebug("Section ignored : "+name) ;
				}
				else if (bAllSectionsAreReduced)
				{ // remove section object, and leave all procedures alone in program
					//CBaseTranscoder.logInfo("Section reduced : "+name) ;
					node.m_Sec.ReduceToProcedure() ;
				}
				else if (bCanReduceCurrentSection)
				{
					//CBaseTranscoder.logInfo("Section reduced : "+name) ;
					node.m_Sec.ReduceToProcedure() ;
				}
				else if (bAllSectionsAreReduced)
				{
					Transcoder.logDebug("Section not reduced : "+name) ;
					bAllSectionsAreReduced = false ;
				}
			}
			else if (bCanReduceCurrentSection && bAllSectionsAreReduced)
			{
				//CBaseTranscoder.logInfo("Section reduced : "+name) ;
				node.m_Sec.ReduceToProcedure() ;
			}
			else if (nbValidProcedures == 1)
			{
				if (node.isCalled() && !lastValidProcedure.isCalled())
				{
					String cs = node.m_Sec.GetName() ;
					node.m_Sec.Rename("$"+cs+"$") ;
					lastValidProcedure.m_Proc.Rename(cs) ;
					node.m_Sec.ReduceToProcedure() ;
				}
				else if (node.isCalled() && lastValidProcedure.m_bImplicitCall && lastValidProcedure.m_bExplicitCallAsProcedure)
				{
					String nameP = lastValidProcedure.m_Proc.GetName() ;
					CEntityCallFunction ePerform = factory.NewEntityCallFunction(0, nameP, "", node.m_Sec) ;
					node.m_Sec.getSectionBloc().AddChild(ePerform) ;
					Transcoder.logDebug("Section reduced : "+name+" and perform to "+nameP+" added") ;
					node.m_Sec.ReduceToProcedure() ;
				}
				else
				{
					Transcoder.logDebug("Section not reduced : "+name) ;
					bAllSectionsAreReduced = false ;
				}
			}
			else if (bAllSectionsAreReduced)
			{
				Transcoder.logDebug("Section not reduced : "+name) ;
				bAllSectionsAreReduced = false ;
			}
		}
	}

	/**
	 * @param ref
	 */
	public void RegisterGlobalGoto(CProcedureReference ref)
	{
		m_root.m_arrGlobalGotoRef.add(ref) ;
	}
}
