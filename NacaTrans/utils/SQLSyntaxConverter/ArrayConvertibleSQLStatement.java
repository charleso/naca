/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils.SQLSyntaxConverter;

import java.util.ArrayList;

import utils.Transcoder;

import jlib.misc.StringUtil;
import jlib.xml.Tag;
import jlib.xml.TagCursor;
import lexer.CBaseToken;
import lexer.CTokenList;
import lexer.CTokenType;

public class ArrayConvertibleSQLStatement
{
	private ArrayList<ConvertibleSQLStatement> m_arrConvertibleSQLStatements = null;
	private String m_csGroup = null;
	private String m_csProgram = null;
	
	boolean manageProgramStatementsToReplace(String csProgram, String csGroup, String csFile)
	{
		m_csProgram = csProgram;
		m_csGroup = csGroup;
		if(StringUtil.isEmpty(csFile))
			return false;
		Tag tag = Tag.createFromFile(csFile);
		if(tag == null)
			return false;
		
		TagCursor cur = new TagCursor();
		Tag tagStatement = tag.getFirstChild(cur, "StatementToReplace");
		while(tagStatement != null)
		{
			boolean bFromSource = tagStatement.getValAsBoolean("FromSource", false);
			boolean bIntoSignificant = tagStatement.getValAsBoolean("IntoSignificant", false);
			Tag tagFrom = tagStatement.getChild("Pattern");
			Tag tagTo = tagStatement.getChild("Replacement");
			
			if(tagFrom != null && tagTo != null)
			{
				String csFrom = tagFrom.getText();
				String csTo = tagTo.getText();
				ConvertibleSQLStatement c = new ConvertibleSQLStatement(csFrom, csTo, bFromSource, bIntoSignificant);
				if(m_arrConvertibleSQLStatements == null)
					m_arrConvertibleSQLStatements = new ArrayList<ConvertibleSQLStatement>();
				m_arrConvertibleSQLStatements.add(c);
			}
			tagStatement = tag.getNextChild(cur);
		}	
		return true;
	}
	
	void lexStatements(Transcoder transcoder)
	{
		if(m_arrConvertibleSQLStatements == null)
			return ;
		for(int n=0; n<m_arrConvertibleSQLStatements.size(); n++)
		{
			ConvertibleSQLStatement stmt = m_arrConvertibleSQLStatements.get(n);
			stmt.lex(m_csGroup, transcoder);
		}	
	}
	
	CTokenList updateProgramSQLStatements(CTokenList tokLstSource)
	{	
		if(m_arrConvertibleSQLStatements == null)
			return tokLstSource;
		
		boolean bUpdate = false;
		int nLastLine = 0;
		boolean bDefinitionError = false;
		ArrayList<CBaseToken> arrTokensSource = tokLstSource.getAsArray();	// Array of all token of the source cobol file
		
		for(int n=0; n<m_arrConvertibleSQLStatements.size(); n++)	// Enum all statements that can be replaced
		{
			if(n == 1)
			{
				int gg =0 ;
			}
			ConvertibleSQLStatement stmt = m_arrConvertibleSQLStatements.get(n);
			boolean bIntoSignificant = stmt.getIntoSignificant();
			if(bIntoSignificant)
			{
				int gg = 0;
			}
			ArrayList<CBaseToken> arrTokensSearchPattern = stmt.getSearchPattern();
			
			if(arrTokensSearchPattern != null)
			{
				TokenReplaceManager tokenReplaceManager = new TokenReplaceManager();
				tokenReplaceManager.init(arrTokensSearchPattern);
				
				for(int nSourceIndex=0; nSourceIndex<arrTokensSource.size(); nSourceIndex++)
				{
					CBaseToken curToken = (CBaseToken)arrTokensSource.get(nSourceIndex);
	
					if(nSourceIndex == 820 || nSourceIndex == 830)
					{
						int gg =0 ;
					}
					boolean bEquals = tokenReplaceManager.isTokenEquals(curToken, nSourceIndex, bIntoSignificant);
					if(bEquals)	// token is found
					{
						nLastLine = curToken.getLine();
						if(tokenReplaceManager.isAllTokenFound())
						{
							ArrayList<CBaseToken> arrTokensReplacements = stmt.getReplacements();
							if(arrTokensReplacements != null)
							{							
								// Remove all obsolete tokens
								int nFirstSourceIndex = tokenReplaceManager.getFirstSourceIndex();
								int nNbTokensToRemove = tokenReplaceManager.getNbTokensToSearch();
								while(nNbTokensToRemove > 0)
								{
									arrTokensSource.remove(nFirstSourceIndex);
									nNbTokensToRemove--;
								}
								
								boolean bNextTokenIsVariableName = false;
								// Insert new tokens
								for(int i=0, j=0; i<arrTokensReplacements.size(); i++)
								{
									CBaseToken tokToInsert = arrTokensReplacements.get(i);
									if(tokToInsert.GetType() != CTokenType.END_OF_BLOCK)
									{
										if(bNextTokenIsVariableName)	// We are on variable's name. It must be filled with the corresponding variable of the cobol source code  
										{
											bNextTokenIsVariableName = false;
											String csReplacementVarId = tokToInsert.GetValue();  
											CBaseToken tokSourceVar = tokenReplaceManager.getSourceVarToken(csReplacementVarId);
											if(tokSourceVar == null)
											{
												bDefinitionError = true;
												Transcoder.logError(nLastLine, "SQL Replacement: unmatched pattern variable name ('" + csReplacementVarId + "') + between pattern and replacement strings; Pattern statement: "+stmt.getSearchPatternString());
											}
											else
											{
												arrTokensSource.add(nFirstSourceIndex + j, tokSourceVar);
												j++;
											}
										}
										else
										{
											tokToInsert.setLine(nLastLine);
											arrTokensSource.add(nFirstSourceIndex + j, tokToInsert);
											j++;
										}

										if(tokToInsert.GetType() == CTokenType.COLON)
											bNextTokenIsVariableName = true;
									}
								}
								
								if(bDefinitionError)
									Transcoder.logError(nLastLine, "SQL Replacement: Could not replaced SQL statement; The original statement has been kept");
								else
								{
									bUpdate = true;
									Transcoder.logInfo(nLastLine, "SQL Replacement: Replaced SQL statement");
								}
									
								nSourceIndex = nFirstSourceIndex;
								tokenReplaceManager.reset();
							}
						}
					}
				}
			}
		}
		
		if(bUpdate)	// arrTokensSource has been update; it must be translated in a CTokenList tokLstSource
		{
			tokLstSource = new CTokenList();
			tokLstSource.fillFromArray(arrTokensSource);
		}
		
		return tokLstSource;
	}

}
