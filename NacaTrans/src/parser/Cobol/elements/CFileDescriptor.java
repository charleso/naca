/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Sep 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CBaseElement;
import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import parser.Cobol.elements.SQL.CExecSQL;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CEntityFileDescriptor;
import semantic.CEntityStructure;
import utils.CGlobalEntityCounter;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CFileDescriptor extends CCobolElement
{
	/**
	 * @param line
	 */
	public CFileDescriptor(int line)
	{
		super(line);
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityFileDescriptor eFD = null ;
		if (m_bSorted)
		{
			eFD = factory.NewEntitySortedFileDescriptor(getLine(), m_FD.GetName()) ;
		}
		else
		{
			eFD = factory.NewEntityFileDescriptor(getLine(), m_FD.GetName()) ;
		}
		parent.AddChild(eFD) ;
		
		if (m_DependingOnLenghtRecord != null)
		{
			CDataEntity e = m_DependingOnLenghtRecord.GetDataReference(getLine(), factory) ;
			e.RegisterFileDescriptorDepending(eFD);
			eFD.setRecordSizeVariable(e) ;
		}
		
		CBaseLanguageEntity firstEntity = null ;
		for (CBaseElement be : m_children)
		{
			CBaseLanguageEntity le = be.DoSemanticAnalysis(eFD, factory) ;
			if (firstEntity == null)
			{
				firstEntity = le ;
			}
			else
			{
				int n = le.GetInternalLevel() ;
				if (n == 1)
				{
					CDataEntity p = firstEntity.FindFirstDataEntityAtLevel(1) ;
					CEntityStructure att = (CEntityStructure)le ;
					att.SetRedefine(p) ;
				}
				else if (n>0)
				{
//					CBaseLanguageEntity p = firstEntity.FindLastEntityAvailableForLevel(n) ;
//					if (p == null)
//						p = firstEntity ;
//					p.AddChild(le);
				}
			}
		}
		m_bAnalysisDoneForChildren = true ;
		
		return eFD;
	}
	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.FD)
		{
			m_bSorted = false; 
		}
		else if (tok.GetKeyword() == CCobolKeywordList.SD)
		{
			m_bSorted = true ;
		}
		else
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		
		tok = GetNext() ;
		m_FD = ReadIdentifier();
		if (m_FD == null)
		{
			return false ;
		}
		
		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.COMMA)
			{
				tok = GetNext() ;
			}
			if (tok.GetKeyword() == CCobolKeywordList.VALUE)
			{
				tok = GetNext() ; // Of
				tok = GetNext() ; // FILE-ID
				tok = GetNext() ; // Is
				tok = GetNext() ; // TODO IGNORE?
				tok = GetNext() ; // DOT
			}
			else if (tok.GetKeyword() == CCobolKeywordList.RECORD)
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.CONTAINS)
				{
					tok = GetNext() ;
				}
				if (tok.GetType() == CTokenType.NUMBER)
				{
					m_MaxLenghtRecord = Integer.parseInt(tok.GetValue());
					tok = GetNext() ;
					if (tok.GetKeyword() == CCobolKeywordList.CHARACTERS)
					{
						GetNext() ;
					}
					else if (tok.GetKeyword() == CCobolKeywordList.TO)
					{
						m_MinLenghtRecord = m_MaxLenghtRecord ;
						tok = GetNext() ;
						m_MaxLenghtRecord = Integer.parseInt(tok.GetValue());
						
						tok = GetNext() ;
						if (tok.GetKeyword() == CCobolKeywordList.CHARACTERS)
						{
							GetNext() ;
						}
					}
				}
				else if (tok.GetKeyword() == CCobolKeywordList.IS || tok.GetKeyword() == CCobolKeywordList.VARYING)
				{
					if (tok.GetKeyword() == CCobolKeywordList.IS)
					{
						tok = GetNext() ;
					}
					tok = GetNext();
					if (tok.GetKeyword() == CCobolKeywordList.IN)
					{
						tok = GetNext() ;
					}
					if (tok.GetKeyword() == CCobolKeywordList.SIZE)
					{
						tok = GetNext() ;
					}
					if (tok.GetKeyword() == CCobolKeywordList.FROM)
					{
						tok = GetNext() ;
					}
					if (tok.GetType() == CTokenType.NUMBER)
					{
						m_MinLenghtRecord = Integer.parseInt(tok.GetValue());
						tok = GetNext() ;
						if (tok.GetKeyword() == CCobolKeywordList.TO)
						{
							tok = GetNext() ;
							if (tok.GetType() == CTokenType.NUMBER)
							{
								m_MaxLenghtRecord = Integer.parseInt(tok.GetValue());
								tok = GetNext() ;
								if (tok.GetKeyword() == CCobolKeywordList.CHARACTERS)
								{
									tok = GetNext() ;
								}
								if (tok.GetKeyword() == CCobolKeywordList.DEPENDING)
								{
									tok = GetNext();
									if (tok.GetKeyword() == CCobolKeywordList.ON)
									{
										tok = GetNext() ;
									}
									m_DependingOnLenghtRecord = ReadIdentifier();
								}
							}
							else
							{
								return false ;
							}
						}
						else
						{
							return false ;
						}
					}
				}
				else
				{
					return false ;
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.LABEL)
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.RECORD || tok.GetKeyword() == CCobolKeywordList.RECORDS)
				{
					tok = GetNext() ;
					if (tok.GetKeyword()== CCobolKeywordList.IS || tok.GetKeyword()== CCobolKeywordList.ARE)
					{
						tok = GetNext() ;
					}
					if (tok.GetKeyword() == CCobolKeywordList.STANDARD || tok.GetKeyword() == CCobolKeywordList.OMITTED)
					{
						tok = GetNext() ;
					}
					else
					{
						return false ;
					}
				}
				else if (tok.GetKeyword() == CCobolKeywordList.STANDARD)
				{
					tok = GetNext() ;
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.BLOCK)
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.CONTAINS)
				{
					tok = GetNext();
				}
				if (tok.GetType() == CTokenType.NUMBER)
				{
					m_MaxBlockLenght = Integer.parseInt(tok.GetValue());
					tok = GetNext() ;
					if (tok.GetKeyword() == CCobolKeywordList.TO)
					{
						tok =GetNext() ;
						if (tok.GetType() == CTokenType.NUMBER)
						{ 
							m_MinBlockLenght = m_MaxBlockLenght ;
							m_MaxBlockLenght = Integer.parseInt(tok.GetValue());
							tok = GetNext() ;
						}
						else
						{
							return false ;	
						}
					}
					if (tok.GetKeyword() == CCobolKeywordList.RECORDS)
					{
						tok = GetNext();
					}
				}
				else 
				{
					return false ;
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.DATA)
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.RECORD)
				{
					tok = GetNext() ;
					if (tok.GetKeyword() == CCobolKeywordList.IS)
					{
						tok = GetNext();
					}
					CIdentifier dataRecord = ReadIdentifier();
					while (dataRecord != null)
					{
						dataRecord = ReadIdentifier();
					}
				} 
				else
				{
					return false ;
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.RECORDING)
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.MODE)
				{
					tok = GetNext() ;
				}
				if (tok.GetKeyword() == CCobolKeywordList.IS)
				{
					tok = GetNext(); 
				}
				m_RecordingMode = ReadTerminal();
			}
//			else if (tok.GetType() == CTokenType.COMMENT)
//			{
//				ParseComment();
//			}
			else
			{
				bDone = true ;
			}
		} 
		
		//file record structure
		tok = GetCurrentToken() ;
		if (tok.GetType() == CTokenType.DOT)
		{
			tok = GetNext() ;
		}
		bDone = false ;
		while (!bDone)
		{
//			if (tok.GetType() == CTokenType.COMMENT)
//			{
//				ParseComment();
//			}
			if (tok.GetKeyword() == CCobolKeywordList.COPY)
			{
				CCopyInWorking fdcopy = new CCopyInWorking(tok.getLine());
				if (!Parse(fdcopy))
				{
					return false ;
				}
				AddChild(fdcopy) ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.COPYREC)
			{
				CCopyRec fdcopy = new CCopyRec(tok.getLine());
				if (!Parse(fdcopy))
				{
					return false ;
				}
				AddChild(fdcopy) ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.EXEC)
			{
				CBaseToken tokType = GetNext() ;
				CCobolElement eExec = null ;
				if (tokType.GetKeyword() == CCobolKeywordList.SQL)
				{
					eExec = new CExecSQL(tok.getLine()) ;
				}
				else
				{
					eExec = new CExecStatement(tok.getLine()) ;
				}
				AddChild(eExec) ;
				if (!Parse(eExec))
				{
					return false ;
				} ;
			}
			else if (tok.GetType() == CTokenType.NUMBER)
			{
				CWorkingEntry fdstruct = new CWorkingEntry(tok.getLine());
				if (!Parse(fdstruct))
				{
					return false ;
				}
				AddChild(fdstruct) ;
			}
			else 
			{
				bDone = true; 
			}
			tok = GetCurrentToken();
		}
		
		
		return true ;
	}
	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eFD = null ;
		if (m_bSorted)
		{
			eFD = root.createElement("SD");
		}
		else
		{
			eFD = root.createElement("FD");
		}
		m_FD.ExportTo(eFD, root);
		
		Element rec = root.createElement("Record");
		eFD.appendChild(rec);		
		if (m_VariableLenghtRecord)
		{
			rec.setAttribute("MaxLength", ""+m_MaxLenghtRecord);
			if (m_MinLenghtRecord>0)
			{
				rec.setAttribute("MinLength", ""+m_MinLenghtRecord);
			}
			if (m_DependingOnLenghtRecord != null)
			{
				Element eDep = root.createElement("Depending");
				m_DependingOnLenghtRecord.ExportTo(eDep, root);
				rec.appendChild(eDep);
			}			
		}
		else
		{
			rec.setAttribute("Length", ""+m_MaxLenghtRecord);
		}
		
		Element block = root.createElement("Block");
		if (m_MinBlockLenght >0)
		{
			block.setAttribute("MaxLenght", ""+m_MaxBlockLenght) ;
			block.setAttribute("MinLenght", ""+m_MinBlockLenght) ;
		}
		else
		{
			block.setAttribute("Lenght", ""+m_MaxBlockLenght) ;
		}
		
		if (m_DataRecord != null)
		{
			Element eDataRec = root.createElement("DataRecord");
			eFD.appendChild(eDataRec);
			m_DataRecord.ExportTo(eDataRec, root); 
		}
		if (m_RecordingMode != null)
		{
			Element eDataRec = root.createElement("RecordingMode");
			eFD.appendChild(eDataRec);
			m_RecordingMode.ExportTo(eDataRec, root); 
		}
		return eFD;
	}
	
	protected CIdentifier m_FD = null ;
	protected int m_MaxLenghtRecord = 0 ; 
	protected int m_MinLenghtRecord = 0 ;
	protected boolean m_VariableLenghtRecord = false ;
	protected CIdentifier m_DependingOnLenghtRecord = null ; 
	protected int m_MaxBlockLenght = 0 ;
	protected int m_MinBlockLenght = 0 ;
	protected CIdentifier m_DataRecord = null ;
	protected CTerminal m_RecordingMode = null;
	protected boolean m_bSorted = false ;
	//protected CIdentifier m_DependingLengthRecordRef = null ;
}
