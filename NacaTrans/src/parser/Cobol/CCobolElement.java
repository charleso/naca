/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol;



import lexer.CBaseToken;
import lexer.CTokenList;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;
import parser.CFunctionIdentifier;
import parser.CIdentifier;
import parser.CLanguageElement;
import parser.condition.CCondAndStatement;
import parser.condition.CCondDifferentStatement;
import parser.condition.CCondEqualsStatement;
import parser.condition.CCondGreaterStatement;
import parser.condition.CCondIsAll;
import parser.condition.CCondIsAlphabetic;
import parser.condition.CCondIsNumeric;
import parser.condition.CCondLessStatement;
import parser.condition.CCondNotStatement;
import parser.condition.CCondOrStatement;
import parser.expression.CConstantTerminal;
import parser.expression.CExpression;
import parser.expression.CIdentifierTerminal;
import parser.expression.CNumberTerminal;
import parser.expression.COppositeExpression;
import parser.expression.CProdExpression;
import parser.expression.CStringTerminal;
import parser.expression.CSumExpression;
import parser.expression.CTermExpression;
import parser.expression.CTerminal;
import parser.expression.CProdExpression.CProdType;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CCobolElement extends CLanguageElement
{
	public CCobolElement(int line)
	{
		super(line) ;
	};
		
	public static CIdentifier ReadIdentifier(CTokenList lstTokens)
	{
		CFakeElement e = new CFakeElement(lstTokens) ;
		lstTokens.StartIter() ;
		CBaseToken tok = lstTokens.GetCurrentToken() ;
		while (tok.IsWhiteSpace())
		{
			tok = lstTokens.GetNext() ;
		}
		return e.ReadIdentifier() ;
	}	
	
	public CIdentifier ReadIdentifier()
	{
		CBaseToken tok = GetCurrentToken() ;
		CIdentifier ident = null ;
		if (tok.GetType() == CTokenType.IDENTIFIER)
		{
			String id = tok.GetValue();
			id = id.toUpperCase() ;
			ident = new CIdentifier(id) ;
			tok = GetNext() ;
		}
		else if (tok.GetKeyword() == CCobolKeywordList.FUNCTION)
		{
			ident = new CFunctionIdentifier(m_lstTokens, this);
			tok = GetCurrentToken() ;
		}
		else if (tok.GetKeyword() == CCobolKeywordList.CURRENT_DATE)
		{
			ident = new CFunctionIdentifier(m_lstTokens, this);
			tok = GetCurrentToken() ;
		}
		else if (tok.GetKeyword() == CCobolKeywordList.LENGTH)
		{
			ident = new CFunctionIdentifier(m_lstTokens, this);
			tok = GetCurrentToken() ;
		}
		else if (tok.GetKeyword() == CCobolKeywordList.ADDRESS)
		{
			ident = new CFunctionIdentifier(m_lstTokens, this);
			tok = this.GetCurrentToken() ;
		}
		else
		{
			return null ;
		}
		while (tok != null)
		{
			tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.OF)
			{	// --> ID OF ID
				tok = GetNext() ;
				//CIdentifier idOf = ReadIdentifier() ;
				if (tok.GetType() == CTokenType.IDENTIFIER)
				{
					ident.setMemberOf(tok.GetValue().toUpperCase()) ;
					tok = GetNext() ;
				}
				else
				{
					return null ;
				}
			} 
			else if (tok.GetKeyword() == CCobolKeywordList.IN)
			{	// --> ID OF ID
				tok = GetNext() ;
				//CIdentifier idOf = ReadIdentifier() ;
				if (tok.GetType() == CTokenType.IDENTIFIER)
				{
					ident.setMemberOf(tok.GetValue().toUpperCase()) ;
					tok = GetNext() ;
				}
				else
				{
					return null ;
				}
			} 
			else if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				GetNext();
				boolean bDone = false ;
				while (!bDone)
				{
					tok = GetCurrentToken() ;
					CExpression exp =  ReadCalculExpression() ; 
						
					CBaseToken tok2 = GetCurrentToken() ;
					if (tok2.GetType() == CTokenType.COMMA)
					{
						ident.AddArrayIndex(exp) ;
						GetNext() ;
					}
					else if (tok2.GetType() == CTokenType.RIGHT_BRACKET)
					{
						ident.AddArrayIndex(exp) ;
						tok = GetNext() ;	 // consume RIGHT_BRACKET
						bDone = true ;
					}
					else if (tok2.GetType() == CTokenType.COLON)
					{
						GetNext() ; // consume ':' 
						CExpression expr2 = ReadCalculExpression() ;
						CBaseToken tokNext = GetCurrentToken() ;
						if (tokNext.GetType() == CTokenType.RIGHT_BRACKET)
						{
							GetNext() ; // consume ')'
							ident.SetSubStringReference(exp, expr2) ;
							bDone = true ;
						}
						else
						{
							return null ;
						}
					}
					else if (tok2.GetType() == CTokenType.IDENTIFIER || tok2.GetType() == CTokenType.NUMBER)
					{
						ident.AddArrayIndex(exp) ; // then loop
					}
					else
					{
						return null ;
					}
				}
				tok = GetCurrentToken() ;
			}
			else
			{
				break ;
			}
		}
		return ident ;
	}

	public CTerminal ReadTerminal()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetType() == CTokenType.IDENTIFIER || tok.IsKeyword())
		{
			CIdentifier id = ReadIdentifier() ;
			if (id != null)
			{
				return new CIdentifierTerminal(id) ;
			}
			else
			{
				return null ;
			} 
		}
		else if (tok.GetType() == CTokenType.NUMBER)
		{
			CBaseToken tokNext = GetNext() ;
			String cs = tok.GetValue() ;
			if (CCobolParser.ms_bCommaIsDecimalPoint && tokNext.GetType() == CTokenType.COMMA)
			{
				cs += "." ;
				tokNext = GetNext();
				if (tokNext.GetType() == CTokenType.NUMBER)
				{
					cs += tokNext.GetValue();
					tokNext = GetNext();
				}
			}
			return new CNumberTerminal(cs) ;
		}
		else if (tok.GetType() == CTokenType.MINUS)
		{
			tok = GetNext() ; // consume '-'
			if (tok.GetType() == CTokenType.NUMBER)
			{ 
				GetNext(); // consume number
				return new CNumberTerminal("-" + tok.GetValue()) ;
			}
			else
			{
				return null ;
			}
		}
		else if (tok.GetType() == CTokenType.PLUS)
		{
			tok = GetNext() ; // consume '+' 
			GetNext(); // consume number
			return new CNumberTerminal(tok.GetValue()) ;
		}
		else if (tok.GetType() == CTokenType.STRING)
		{
			GetNext() ;
			return new CStringTerminal(tok.GetCharValue()) ;
		}
		else if (tok.GetType() == CTokenType.CONSTANT)
		{
			GetNext() ;
			return new CConstantTerminal(tok.GetValue()) ;
		}
		return null ; 
	}

	public CExpression ReadExpression()
	{
		CExpression exprGlobal = null ;
		GetCurrentToken() ;
		exprGlobal = ReadConditionalStatement() ;
		return exprGlobal ;
	}

	public CExpression ReadCalculExpression()
	{
		CExpression exprGlobal = null ;
		GetCurrentToken() ;
		exprGlobal = ReadSumExpr() ;
		return exprGlobal ;
	}
		
	private CExpression ReadSumExpr()
	{
		CExpression exprSum = null ;
		CExpression expr1 = ReadProdExpr();
		if (expr1 == null)
		{
			return null ;
		}
		exprSum = expr1 ;
		
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tok = GetCurrentToken();
			if (tok.GetType() == CTokenType.PLUS)
			{
				GetNext() ;
				CExpression expr2 = ReadProdExpr() ;
				if (expr2 == null)
				{
					bDone = true ;
				}
				else
				{
					exprSum = new CSumExpression(tok.getLine(), exprSum, expr2, CSumExpression.CSumType.ADD);
				}				
			}
			else if (tok.GetType() == CTokenType.MINUS)
			{
				GetNext() ;
				CExpression expr2 = ReadProdExpr() ;
				if (expr2 == null)
				{
					bDone = true ;
				}
				else
				{
					exprSum = new CSumExpression(tok.getLine(), exprSum, expr2, CSumExpression.CSumType.SUB);
				}				
			}
			else 
			{
				bDone = true ;
			}
		}
		return exprSum ;
	}
	
	private CExpression ReadProdExpr()
	{
		CExpression exprProd = null ;
		exprProd  = ReadTerminalExpr() ;
		if (exprProd == null)
		{
			return null ;
		}
		
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tok = GetCurrentToken();
			if (tok.GetType() == CTokenType.STAR || tok.GetType() == CTokenType.SLASH || tok.GetType() == CTokenType.STAR_STAR)
			{
				GetNext() ;
				CExpression e = ReadTerminalExpr() ;
				if (e != null)
				{
					CProdType type = CProdExpression.CProdType.PROD;
					if (tok.GetType() == CTokenType.SLASH)
						type = CProdExpression.CProdType.DIVIDE;
					else if (tok.GetType() == CTokenType.STAR_STAR)
						type = CProdExpression.CProdType.POW;
					exprProd = new CProdExpression(tok.getLine(), exprProd, e, type) ;
				}
				else
				{
					bDone = true ;
				}
			}
			else
			{
				bDone = true ;
			}
		}
		return exprProd ;
	}

	public CExpression ReadConditionalStatement()
	{ // this function is called at the begining of the statment
//		boolean bDone = false ; 
//		CExpression curCond = null ;
//		while (!bDone)
//		{
//			CBaseToken tok = GetCurrentToken() ;
//			if (tok == null)
//			{
//				return curCond ;
//			}
//			if (curCond != null && tok.IsKeyword() && tok.GetKeyword() == CCobolKeywordList.AND)
//			{
//				GetNext() ;
//				CExpression st = ReadConditionalStatement(curCond.GetFirstOperand()) ;
//				if (st == null)
//				{
//					//CExpression op = curCond.GetFirstOperand() ;
//					CExpression exp = ReadConditionalStatement(defaultOperand) ;
//					return new CCondAndStatement(curCond, exp)  ;
//				}
//				else
//				{
//					return new CCondAndStatement(curCond, st) ;
//				}
//			}
//			else if (curCond != null && tok.IsKeyword() && tok.GetKeyword() == CCobolKeywordList.OR)
//			{
//				GetNext() ;
//				CExpression st = ReadConditionalStatement(curCond.GetFirstOperand()) ;
//				if (st == null)
//				{
////					CExpression op = curCond.GetFirstOperand() ;
////					CExpression exp = ReadBinaryCondEvaluator(op) ;
//					CExpression exp = ReadConditionalStatement(defaultOperand) ;
//					st = exp ;
//				}
//				return new CCondOrStatement(curCond, st) ;
//			}
//			else if (curCond == null)
//			{
//				curCond = ReadSimpleCondition(defaultOperand) ;
//				if (curCond == null)
//				{
//					return null ;	
//				}
//			}
//			else
//			{
//				bDone = true ; 
//			}
//		}
//		ASSERT(curCond) ;
		return ReadORStatement(null) ;
	}
	private CExpression ReadORStatement(CExpression defaultOperand)
	{
		CExpression fstOperand = ReadANDStatement(defaultOperand) ;
		if (fstOperand == null)
		{
			return null ;
		}
		else
		{
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.OR)
			{
				tok = GetNext() ;
				if (defaultOperand == null)
				{
					defaultOperand = fstOperand.GetFirstConditionOperand() ;
				}
				CExpression curCond = ReadORStatement(defaultOperand) ;
//				if ((curCond.IsReference() || curCond.IsConstant())
//						&& !fstOperand.IsBinaryCondition() && !fstOperand.IsReference())
//				{
//					m_Logger.warn("WARNING : be carrefull to Abbreviated combined relation condition ; line "+tok.getLine()) ;  
//				}
				return new CCondOrStatement(tok.getLine(), fstOperand, curCond) ;
			}
			else
			{
				return fstOperand ;
			}
		}
	}
	private CExpression ReadANDStatement(CExpression defaultOperand)
	{
		CExpression fstOperand = ReadSimpleCondition(defaultOperand) ;
		if (fstOperand == null)
		{
			return null ;
		}
		else
		{
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.AND)
			{
				int line = tok.getLine() ;
				tok = GetNext() ;
				if (fstOperand.GetFirstConditionOperand() != null)
				{
					defaultOperand = fstOperand.GetFirstConditionOperand() ;
				}
				CExpression curCond = ReadANDStatement(defaultOperand) ;
				return new CCondAndStatement(line, fstOperand, curCond) ;
			}
			else
			{
				return fstOperand ;
			}
		}
	}
	private CExpression ReadSimpleCondition(CExpression defaultOperand)
	{
		CExpression curCond = null ;
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetType() == CTokenType.IDENTIFIER || tok.GetType() == CTokenType.STRING ||
			tok.GetType() == CTokenType.NUMBER || tok.GetType() == CTokenType.CONSTANT || 
			tok.GetType() == CTokenType.MINUS || tok.GetType() == CTokenType.PLUS
			|| tok.GetType() == CTokenType.LEFT_BRACKET)
		{
			CExpression curTerminal = ReadCalculExpression();
			curCond = ReadBinaryCondEvaluator(curTerminal, false);
			if (curCond == null)
			{
				curCond = curTerminal ; 
			}
		}
//		else if (tok.GetType() == CTokenType.LEFT_BRACKET)
//		{
//			tok = GetNext() ;
//			//CExpression braCond = ReadConditionalStatement(defaultCondition) ;
//			CExpression braCond = ReadConditionalStatement() ;
//		
//			tok = GetCurrentToken() ;
//			if (tok.GetType() == CTokenType.RIGHT_BRACKET)
//			{
//				tok = GetNext();
//				CExpression testCond = ReadBinaryCondEvaluator(braCond, false) ;
//				if (testCond != null)
//				{
//					curCond = testCond ;
//				}
//				else
//				{
//					curCond = braCond ;
//				}
//			}
//			else
//			{
//				// not good;			
//			}
//		}
		else if (tok.GetKeyword() == CCobolKeywordList.NOT)
		{
			tok = GetNext() ;
			curCond  = ReadNOTCondition(defaultOperand) ;
		}
		else if (defaultOperand != null)
		{
			curCond = ReadBinaryCondEvaluator(defaultOperand, false);
		}
		else
		{
			return null ;
		}
		ASSERT(curCond);
		return curCond ;
	}
	
	private CExpression ReadNOTCondition(CExpression defaultOperand)
	{ // read with NOT considered as composed logical operator (NOT > / NOT = / NOT < ...)
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetType() == CTokenType.IDENTIFIER || tok.GetType() == CTokenType.STRING ||
			tok.GetType() == CTokenType.NUMBER || tok.GetType() == CTokenType.CONSTANT || 
			tok.GetType() == CTokenType.MINUS || tok.GetType() == CTokenType.PLUS
			|| tok.GetType() == CTokenType.LEFT_BRACKET)
		{
			CExpression curTerminal = ReadCalculExpression();
			CExpression curCond = ReadBinaryCondEvaluator(curTerminal, false);
			if (curCond == null)
			{
				curCond = curTerminal ; 
			}
			return new CCondNotStatement(tok.getLine(), curCond) ;
		}
//		else if (tok.GetType() == CTokenType.LEFT_BRACKET)
//		{
//			tok = GetNext() ;
//			//CExpression braCond = ReadConditionalStatement(defaultCondition) ;
//			CExpression braCond = ReadConditionalStatement() ;
//		
//			tok = GetCurrentToken() ;
//			if (tok.GetType() == CTokenType.RIGHT_BRACKET)
//			{
//				tok = GetNext();
//				CExpression curCond = new CCondNotStatement(tok.getLine(), braCond) ;
//				return curCond ;
//			}
//			else
//			{
//				// not good;	
//				ASSERT(null) ;
//				return null ;
//			}
//		}
		else if (tok.GetKeyword() == CCobolKeywordList.NOT)
		{
			tok = GetNext() ;
			CExpression notCond = ReadNOTCondition(defaultOperand) ;
			CExpression curCond = new CCondNotStatement(tok.getLine(), notCond) ;
			return curCond ;
		}
		else if (defaultOperand != null)
		{
			CExpression curCond = ReadBinaryCondEvaluator(defaultOperand, true);
			return curCond ;
		}
		else
		{
			return null ;
		}
	}


	private void ASSERT(Object o)
	{
		if (o == null)
		{
			//throw new NacaTransAssertException("ASSERT") ;
		}
	}


	private CExpression ReadBinaryCondEvaluator(CExpression operand1, boolean bIsOpposite)
	{
		CBaseToken tok = GetCurrentToken();
		if (tok.GetType() == CTokenType.EQUALS) 
		{
			CBaseToken tokNext = GetNext() ;
			if (tokNext.GetKeyword() == CCobolKeywordList.ALL)
			{
				GetNext() ; // consume 'ALL'
				CExpression term2 = ReadCalculExpression() ;
				CCondIsAll condGlobal = new CCondIsAll(tokNext.getLine(), operand1, term2);
				if (bIsOpposite)
				{
					condGlobal.setOpposite() ;
				}
				return condGlobal ;
			}
			else
			{
				CExpression term2 = ReadSimpleCondition(null);
				if (bIsOpposite)
				{
					CExpression condGlobal = new CCondDifferentStatement(tokNext.getLine(), operand1, term2) ;
					return condGlobal ;
				}
				else
				{
					CExpression condGlobal = new CCondEqualsStatement(tokNext.getLine(), operand1, term2) ;
					return condGlobal ;
				}
			}
		}
		else if (tok.GetKeyword() == CCobolKeywordList.EQUAL) 
		{
			CBaseToken tokNext = GetNext() ;
			if (tokNext.GetKeyword() == CCobolKeywordList.TO)
			{
				tokNext = GetNext() ; // consume 'TO'
			}
			CExpression term2 = ReadCalculExpression();
			if (bIsOpposite)
			{
				CExpression condGlobal = new CCondDifferentStatement(tok.getLine(), operand1, term2) ;
				return condGlobal ;
			}
			else
			{
				CExpression condGlobal = new CCondEqualsStatement(tok.getLine(), operand1, term2) ;
				return condGlobal ;
			}
		}
		else if (tok.GetKeyword() == CCobolKeywordList.ALL)
		{
			GetNext() ; // consume 'ALL'
			CExpression term2 = ReadCalculExpression() ;
			CCondIsAll condGlobal = new CCondIsAll(tok.getLine(), operand1, term2);
			if (bIsOpposite)
			{
				condGlobal.setOpposite() ;
			}
			return condGlobal ;
		}
		else if (tok.GetType() == CTokenType.GREATER_THAN || tok.GetKeyword() == CCobolKeywordList.GREATER) 
		{
			boolean bOrEquals = getNextThan() ;
			CExpression term2 = ReadSimpleCondition(null);
			if (bIsOpposite)
			{
				return new CCondLessStatement(tok.getLine(), operand1, term2, true) ;
			}
			else
			{
				return new CCondGreaterStatement(tok.getLine(), operand1, term2, bOrEquals) ;
			}
		}
		else if (tok.GetType() == CTokenType.LESS_THAN || tok.GetKeyword() == CCobolKeywordList.LESS) 
		{
			boolean bOrEquals = getNextThan() ;
			CExpression term2 = ReadSimpleCondition(null);
			if (bIsOpposite)
			{
				return new CCondGreaterStatement(tok.getLine(), operand1, term2, true) ;
			}
			else
			{
				return new CCondLessStatement(tok.getLine(), operand1, term2, bOrEquals) ;
			}
		}
		else if (tok.GetType() == CTokenType.GREATER_OR_EQUALS) 
		{
			GetNext() ;
			CExpression term2 = ReadSimpleCondition(null);
			if (bIsOpposite)
			{
				return new CCondLessStatement(tok.getLine(), operand1, term2) ;
			}
			else
			{
				return new CCondGreaterStatement(tok.getLine(), operand1, term2, true) ;
			}
		}
		else if (tok.GetType() == CTokenType.LESS_OR_EQUALS) 
		{
			GetNext() ;
			CExpression term2 = ReadSimpleCondition(null);
			if (bIsOpposite)
			{
				return new CCondGreaterStatement(tok.getLine(), operand1, term2) ;
			}
			else
			{
				return new CCondLessStatement(tok.getLine(), operand1, term2, true) ;
			}
		}
		else if (tok.IsKeyword() && tok.GetKeyword() == CCobolKeywordList.NOT)
		{
			GetNext() ;
			return ReadBinaryCondEvaluator(operand1, !bIsOpposite) ;
//			if (tokNext.GetType() == CTokenType.EQUALS)
//			{
//				tokNext = GetNext() ;
//				if (tokNext.GetKeyword() == CCobolKeywordList.ALL)
//				{
//					GetNext() ; // consume 'ALL'
//					CExpression term2 = ReadCalculExpression() ;
//					CExpression condGlobal = new CCondIsAll(operand1, term2);
//					return new CCondNotStatement(condGlobal);
//				}
//				else
//				{
//					CExpression term2 = ReadSimpleCondition(null);
//					CExpression condGlobal = new CCondDifferentStatement(operand1, term2) ;
//					return condGlobal ;
//				}
//			}
//			else
//			{
//				CExpression st = null ;
//				if (tokNext.GetType() == CTokenType.IDENTIFIER || tokNext.GetType() == CTokenType.STRING || tokNext.GetType() == CTokenType.CONSTANT || tokNext.GetType() == CTokenType.NUMBER)
//				{
//					CExpression exp2 = ReadCalculExpression();
//					st = ReadBinaryCondEvaluator(exp2) ;
//					if (st == null)
//					{ // in this case we have somthing like A NOT B
//						return new CCondDifferentStatement(operand1, exp2);
//					}
//				}
//				else
//				{
//					st = ReadBinaryCondEvaluator(operand1) ;
//					if (st == null)
//					{
//						//CExpression termTest = ReadCalculExpression() ;
//						CExpression termTest = ReadConditionalStatement() ;
//						if (termTest != null)
//						{
//							//return new CCondNotStatement(new CCondEqualsStatement(term, termTest));
//							return new CCondNotStatement(termTest);
//						}
//						else
//						{
//							m_Logger.warn("WARNING line "+tok.getLine()+" : Token unexpected : 'NOT'"); 
//							return null ;
//						}
//					}
//				}
//				return new CCondNotStatement(st) ;
//			}
		}
		else if (tok.IsKeyword() && tok.GetKeyword() == CCobolKeywordList.IS)
		{
			GetNext();
			return ReadBinaryCondEvaluator(operand1, bIsOpposite) ;
		}
		else if (tok.IsKeyword() && tok.GetKeyword() == CCobolKeywordList.NUMERIC)
		{
			GetNext();
			return new CCondIsNumeric(tok.getLine(), operand1, bIsOpposite);
		}
		else if (tok.IsKeyword() && tok.GetKeyword() == CCobolKeywordList.POSITIVE)
		{
			GetNext() ;
			CExpression term2 = new CTermExpression(tok.getLine(), new CNumberTerminal("0"));
			if (bIsOpposite)
			{
				return new CCondLessStatement(tok.getLine(), operand1, term2, true) ;
			}
			else
			{
				return new CCondGreaterStatement(tok.getLine(), operand1, term2) ;
			}
		}
		else if (tok.IsKeyword() && tok.GetKeyword() == CCobolKeywordList.NEGATIVE)
		{
			GetNext() ;
			CExpression term2 = new CTermExpression(tok.getLine(), new CNumberTerminal("0"));
			if (bIsOpposite)
			{
				return new CCondGreaterStatement(tok.getLine(), operand1, term2, true) ;
			}
			else
			{
				return new CCondLessStatement(tok.getLine(), operand1, term2) ;
			}
		}
		else if (tok.GetKeyword() == CCobolKeywordList.ALPHABETIC_UPPER)
		{
			GetNext();
			return new CCondIsAlphabetic(tok.getLine(), operand1, 1, bIsOpposite);
		}
		else if (tok.GetKeyword() == CCobolKeywordList.ALPHABETIC)
		{
			GetNext();
			return new CCondIsAlphabetic(tok.getLine(), operand1, 0, bIsOpposite);
		}
		else if (tok.GetType() == CTokenType.IDENTIFIER || tok.GetType() == CTokenType.STRING ||
			tok.GetType() == CTokenType.NUMBER || tok.GetType() == CTokenType.CONSTANT || 
			tok.GetType() == CTokenType.MINUS || tok.GetType() == CTokenType.PLUS)
		{
			CExpression curTerminal = ReadCalculExpression();
			if (bIsOpposite)
			{
				CExpression condGlobal = new CCondDifferentStatement(tok.getLine(), operand1, curTerminal) ;
				return condGlobal ;
			}
			else
			{
				CExpression condGlobal = new CCondEqualsStatement(tok.getLine(), operand1, curTerminal) ;
				return condGlobal ;
			}
		}

		return null ;
	}

	private boolean getNextThan()
	{
		CBaseToken next = GetNext() ;
		if (next.GetKeyword() == CCobolKeywordList.THAN)
		{
			next = GetNext() ;
			if (next.GetKeyword() == CCobolKeywordList.OR)
			{
				GetNext() ; // Equal
				GetNext() ; // To
				GetNext() ;
				return true ;
			}
		}
		return false ;
	}

	protected String ReadStringUntilEOL()
	{
		String data = "" ;
		CBaseToken tok = GetCurrentToken() ;
		if (tok.m_bIsNewLine)
		{
			return data ;
		}
		int n = tok.getLine() ;		
		while (tok != null && tok.getLine() == n)
		{
			if (tok.GetType() != CTokenType.DOT)
			{
				String cs = tok.GetValue() ;
				if (!data.equals(""))
				{
					data += " " ;
				}
				data += cs ;
				tok = GetNext() ;
			}
			else 
			{
				// in case of a dot, we must check if this dot is a the end of the line or not ;
				// if not, the DOT is part of the result string
				tok = GetNext() ;
				if (tok == null)
				{
					return data ;
				}
				if (tok.getLine() == n)
				{
					data += "." ;
				}
			}
		}
		//GetNext() ; // consume NEW_LINE token
		return data ;		
	}

	private CExpression ReadTerminalExpr()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetType() == CTokenType.LEFT_BRACKET)
		{
			GetNext() ; // consume '('
//			CExpression exp = ReadCalculExpression() ;
			CExpression exp = ReadORStatement(null) ;
			tok = GetCurrentToken() ;
			if (tok.GetType() != CTokenType.RIGHT_BRACKET)
			{
				Transcoder.logError(tok.getLine(), "Expecting a ')', found a : "+tok.GetValue()) ;
				return null ;
			}
			GetNext() ;
			return exp;
		}
		else if (tok.GetType() == CTokenType.MINUS)
		{
			tok = GetNext() ; // consume '-'
			if (tok.GetType() == CTokenType.NUMBER)
			{ 
				GetNext(); // consume number
				CTerminal term = new CNumberTerminal("-" + tok.GetValue()) ;
				return new CTermExpression(tok.getLine(), term) ;
			}
			else
			{
				CExpression exp = ReadTerminalExpr();
				if (exp != null)
				{
					return new COppositeExpression(exp.getLine(), exp);
				}
				else
				{
					return null ;
				}
			}
		}
		else 
		{
			CTerminal term = ReadTerminal() ;
			if (term != null)
			{
				return new CTermExpression(tok.getLine(), term) ;
			}
			else
			{
				return null ;
			}
		}
	}
}
