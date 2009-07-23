/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 19, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Vector;

import jlib.misc.StringUtil;

import lexer.*;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.*;

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import parser.expression.CTerminal;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.Verbs.CEntityAssign;
import semantic.Verbs.CEntityAssignWithAccessor;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CMove extends CCobolElement
{
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	 
	/**
	 * @param line
	 */
	public CMove(int line) 
	{
		super(line);
	}
	
	protected boolean DoParsing()
	{
		CBaseToken tokMove = GetCurrentToken();
		if (tokMove.GetType()!=CTokenType.KEYWORD|| tokMove.GetKeyword()!=CCobolKeywordList.MOVE)
		{
			Transcoder.logError(getLine(), "Expecting 'MOVE' keyword") ;
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tokMove.GetKeyword().m_Name) ;
		
		// read the FROM token
		CBaseToken tokFrom = GetNext() ;
		if (tokFrom.GetKeyword() == CCobolKeywordList.CORR || tokFrom.GetKeyword() == CCobolKeywordList.CORRESPONDING)
		{
			m_bMoveCorresponding = true ;
			tokFrom = GetNext();
		}
		if (tokFrom.GetKeyword() == CCobolKeywordList.ALL)
		{
			m_bFillAll = true ;
			GetNext();
		}
		m_valueFrom = ReadTerminal() ;

		IgnoreComma();
		// read the 'TO'
		CBaseToken tokTo = GetCurrentToken();
		if (tokTo.GetKeyword() != CCobolKeywordList.TO)
		{
			Transcoder.logError(getLine(), "Expecting 'TO' keyword") ;
			return false ;
		}
		
		GetNext() ;
		//read the DEST tokens
		boolean bDone0 = false ;
		while (!bDone0)
		{
			CBaseToken tokId = GetCurrentToken() ;
			if (tokId.GetType() == CTokenType.IDENTIFIER)
			{
				CIdentifier id = ReadIdentifier() ;
				m_arrToIdentifiers.add(id) ;
			}
			else if (tokId.GetType() == CTokenType.COMMA)
			{
				GetNext();
			}
			else if (tokId.GetType() == CTokenType.DOT)
			{
				bDone0 = true ;
			}
			else
			{
				bDone0 = true ;
			}
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eMove ;
		if (m_bMoveCorresponding)
		{
			eMove = root.createElement("MoveCorresponding") ;
		}
		else
		{
			eMove = root.createElement("Move") ;
		}
		Element eFrom = root.createElement("From") ;
		m_valueFrom.ExportTo(eFrom, root) ;
		eMove.appendChild(eFrom) ;
		ListIterator i = m_arrToIdentifiers.listIterator() ;
		try
		{
			CIdentifier idDest = (CIdentifier)i.next() ;
			while (idDest != null)
			{
				Element dest ;
				if (m_bFillAll)
				{
					dest = root.createElement("Fill") ;
				}
				else
				{
					dest = root.createElement("To") ;
				}
				idDest.ExportTo(dest, root) ;
				eMove.appendChild(dest) ;
				idDest = (CIdentifier)i.next() ;
			}
		}
		catch (NoSuchElementException e)
		{
			// nothing 
		}
		return eMove;
	}
	
	//protected CMoveFromType m_FromType = null ;	// STRING / NUMBER / IDENTIFIER / SPACE / ZERO
	protected CTerminal m_valueFrom = null ;
	protected Vector<CIdentifier> m_arrToIdentifiers = new Vector<CIdentifier>() ;
	protected boolean m_bFillAll = false ;
	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		Vector<CDataEntity> vDest = new Vector<CDataEntity>() ;
		if (!m_valueFrom.IsReference())
		{ // no value to used a MOVE, it needs a special assignement function
			for (int i=0; i<m_arrToIdentifiers.size(); i++)
			{
				CIdentifier id = m_arrToIdentifiers.get(i) ;
				if (id != null)
				{
					CDataEntity e = id.GetDataReference(getLine(), factory) ;  
					if (e == null)
					{
						Transcoder.addOnceUnboundReference(getLine(), id.GetName());
						//Transcoder.logError(getLine(), "Identifier can't be bound : " + id.GetName()) ;
					}
					else 
					{
						if (!vDest.contains(e))
						{
							vDest.add(e) ;
							CBaseActionEntity eAction = null ;
							eAction = e.GetSpecialAssignment(m_valueFrom, factory, getLine()) ;
							if (eAction != null)
							{
								parent.AddChild(eAction) ;
								e.RegisterWritingAction(eAction) ;
							}
							else 
							{
								CDataEntity eFrom = m_valueFrom.GetDataEntity(getLine(), factory) ;
								if (eFrom != null)
								{
									eAction = e.GetSpecialAssignment(eFrom, factory, getLine()) ;
									if (eAction != null)
									{
										parent.AddChild(eAction) ;
//										e.RegisterWritingAction(eAction);
									}
									else if (e.HasAccessors())
									{
										CEntityAssignWithAccessor eAcc = factory.NewEntityAssignWithAccessor(getLine()) ;
										parent.AddChild(eAcc) ;
										eAcc.SetAssign(e, eFrom) ;
										eAcc.SetFillAll(m_bFillAll) ;
										e.RegisterWritingAction(eAcc);
									}
									else
									{
										CEntityAssign eAsgn = factory.NewEntityAssign(getLine()) ;
										eAsgn.SetFillAll(m_bFillAll) ;
										eAsgn.SetValue(eFrom) ;
										eAsgn.AddRefTo(e);
										parent.AddChild(eAsgn) ;
										e.RegisterWritingAction(eAsgn);
										eFrom.RegisterReadingAction(eAsgn);
									}
								}
								else
								{
									String csName = e.GetName();
									if(StringUtil.isEmpty(csName))
										csName = id.GetName();
									Transcoder.logError(getLine(), "Special assignement needed for value : " + m_valueFrom.GetValue() + " to variable : "+csName) ;
								}
							}
						}
						else
						{
							int n=0; 
						}
					}
				}
			}
			return null ;
		}
		else
		{
			CEntityAssign eAsgn = factory.NewEntityAssign(getLine()) ;
			CDataEntity eFrom = m_valueFrom.GetDataEntity(getLine(), factory) ;
			eAsgn.SetValue(eFrom) ;
			boolean bMoveToUsed = false ;
			for (int i=0; i<m_arrToIdentifiers.size(); i++)
			{
				CIdentifier id = m_arrToIdentifiers.get(i) ;
				if (id != null)
				{
					CDataEntity e = id.GetDataReference(getLine(), factory) ;  
					if (e == null)
					{
						Transcoder.addOnceUnboundReference(getLine(), id.GetName());
						//Transcoder.logError(getLine(), "Identifier can't be bound : " + id.GetName()) ;
					}
					else 
					{
						if (!vDest.contains(e))
						{
							vDest.add(e) ;
							CBaseActionEntity eAction = null ;
							if (m_valueFrom.IsReference())
							{
								eAction = e.GetSpecialAssignment(eFrom, factory, getLine()) ;
							}
							else
							{
								eAction = e.GetSpecialAssignment(m_valueFrom, factory, getLine()) ;
							}
							if (eAction != null)
							{
								parent.AddChild(eAction) ;
								e.RegisterWritingAction(eAction);
								eFrom.RegisterReadingAction(eAction) ;
							}
							else
							{
								if (e.HasAccessors())
								{
									CEntityAssignWithAccessor eAcc = factory.NewEntityAssignWithAccessor(getLine()) ;
									parent.AddChild(eAcc) ;
									eAcc.SetAssign(e, eFrom) ;
									e.RegisterWritingAction(eAcc);
									eFrom.RegisterReadingAction(eAcc) ;
								}
								else
								{
									eAsgn.AddRefTo(e);
									eAsgn.SetFillAll(m_bFillAll) ;
									eAsgn.SetAssignCorresponding(m_bMoveCorresponding);
									e.RegisterWritingAction(eAsgn);
									bMoveToUsed = true ;
								}
							}
						}
					}
				}
			}
			if (bMoveToUsed)
			{		 
				parent.AddChild(eAsgn) ;
				if (eFrom != null)
				{
					eFrom.RegisterReadingAction(eAsgn);
				}
				return eAsgn;
			}
			else
			{
				return null ;
			}
		}
	}
	protected boolean m_bMoveCorresponding = false ;
}
