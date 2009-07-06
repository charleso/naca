/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package parser.FPac;


import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.FPac.CFPacKeywordList;
import parser.CIdentifier;
import parser.CLanguageElement;
import parser.condition.CCondAndStatement;
import parser.condition.CCondOrStatement;
import parser.expression.*;
import utils.Transcoder;

public abstract class CFPacElement extends CLanguageElement
{

	/* (non-Javadoc)
	 * @see parser.CBaseElement#GetNext()
	 */
	@Override
	protected CBaseToken GetNext()
	{
		CBaseToken tok = super.GetNext();
		while (tok != null && tok.IsWhiteSpace())
		{
			tok = super.GetNext() ;
		}
		return tok ;
	}
	protected void StepNext()
	{
		CBaseToken tok = super.GetNext();
		while (tok != null && tok.IsWhiteSpace())
		{
			tok = super.GetNext() ;
		}
	}

	public CFPacElement(int line)
	{
		super(line);
	}
	
	protected abstract boolean DoParsing() ;


	protected CIdentifier ReadIdentifier()
	{
		CBaseToken tok = GetCurrentToken() ;
		CIdentifier ident = null ;
		if (tok.GetType() == CTokenType.IDENTIFIER)
		{
			String id = tok.GetValue();
			id = id.toUpperCase() ;
			ident = new CIdentifier(id) ;
			tok = GetNext() ;
			
			if (tok.GetType() == CTokenType.MINUS)
			{
				tok = GetNext() ;
			}
			else
			{
				return ident ;
			}
		}
		
		if (tok.GetType() == CTokenType.NUMBER)
		{
			if (ident  == null)
			{
				ident = new CIdentifier("") ;
			}
			String csStart = tok.GetValue() ;
			CExpression eStart = new CTermExpression(0, new CNumberTerminal(csStart)) ;
			CExpression eLength = null ;
			tok = GetNext() ;
			
			if (tok.GetType() == CTokenType.COMMA)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.NUMBER)
				{
					String csLength = tok.GetValue() ;
					eLength = new CTermExpression(0, new CNumberTerminal(csLength)) ;
					StepNext() ;
				}
			}
			ident.SetSubStringReference(eStart, eLength) ;
		}
		
		return ident ;
	}
	
	
	protected CExpression ReadCondition()
	{
		CExpression exp = ReadAndCondition() ;
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.OR)
		{
			StepNext();
			CExpression exp2 = ReadCondition() ;
			CCondOrStatement st = new CCondOrStatement(tok.getLine(), exp,exp2) ;
			return st ;
		}
		return exp ;
	}

	/**
	 * @return
	 */
	private CExpression ReadAndCondition()
	{
		CExpression exp = ReadExpression() ;
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.AND)
		{
			StepNext();
			CExpression exp2 = ReadAndCondition() ;
			CCondAndStatement st = new CCondAndStatement(tok.getLine(), exp,exp2) ;
			return st ;
		}
		return exp ;
	}
	protected CExpression ReadExpression()
	{
		CBaseToken tok = GetCurrentToken() ;
		CFPacGenericExpression exp = new CFPacGenericExpression(tok.getLine()) ;
		boolean bOk = true ;
		while (bOk)
		{
			tok = GetCurrentToken() ;
			if  (tok.GetKeyword() == CFPacKeywordList.P)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.STRING)
				{
					String cs = tok.GetValue() ;
					CTerminal term = new CNumberTerminal(cs);
					exp.AddTerm(new CTermExpression(tok.getLine(), term)) ;
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Expecting 'STRING' after 'P'") ;
					return null ;
				}
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.C)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.STRING)
				{
					String cs = tok.GetValue() ;
					CTerminal term = new CStringTerminal(cs);
					exp.AddTerm(new CTermExpression(tok.getLine(), term)) ;
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Expecting 'STRING' after 'C'") ;
					return null ;
				}
				tok = GetNext() ;
			}
			else if (tok.GetType() == CTokenType.STRING)
			{
				String cs = tok.GetValue() ;
				CTerminal term = new CStringTerminal(cs);
				exp.AddTerm(new CTermExpression(tok.getLine(), term)) ;
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.X)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.STRING)
				{
					String cs = tok.GetValue() ;
					CTerminal term = new CNumberTerminal("0x" + cs) ;
					exp.AddTerm(new CTermExpression(tok.getLine(), term)) ;
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Expecting 'STRING' after 'X'") ;
					return null ;
				}
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.E)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.STRING)
				{
					String cs = tok.GetValue() ;
					CTerminal term = new CStringTerminal(cs);
					exp.AddTerm(new CTermExpression(tok.getLine(), term)) ;
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Expecting 'STRING' after 'E'") ;
					return null ;
				}
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.F)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.STRING)
				{
					String cs = tok.GetValue() ;
					CTerminal term = new CNumberTerminal(cs);
					exp.AddTerm(new CTermExpression(tok.getLine(), term)) ;
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Expecting 'STRING' after 'F'") ;
					return null ;
				}
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.EQ ||
							tok.GetKeyword() == CFPacKeywordList.NE ||
							tok.GetKeyword() == CFPacKeywordList.GE ||
							tok.GetKeyword() == CFPacKeywordList.GT ||
							tok.GetKeyword() == CFPacKeywordList.LE ||
							tok.GetKeyword() == CFPacKeywordList.LT ||
							tok.GetKeyword() == CFPacKeywordList.NUMERIC ||
							tok.GetKeyword() == CFPacKeywordList.SPACE)
			{
				exp.SetKeyword(tok.GetKeyword()) ;
				tok = GetNext() ;
			}
			else if (tok.GetType() == CTokenType.NUMBER)
			{
				CTerminal term = new CAddressTerminal(tok.GetValue()) ;
				CExpression e = new CTermExpression(tok.getLine(), term) ;
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.PLUS)
				{
					tok = GetNext() ;
					CExpression exp2 = ReadTerminalExpression() ;
					if (exp2 == null)
					{
						Transcoder.logError(tok.getLine(), "Expecting expression after '+'") ;
						return null ;
					}
					CSumExpression sum = new CSumExpression(tok.getLine(), e, exp2, CSumExpression.CSumType.ADD) ;
					exp.AddTerm(sum) ;
				}
				else
				{
					exp.AddTerm(e) ;
				}
			}
			else if (tok.GetType() == CTokenType.IDENTIFIER)
			{
				CIdentifier id = new CIdentifier(tok.GetValue()) ;
				tok = GetNext() ;
				if  (tok.GetType() == CTokenType.MINUS)
				{
					tok = GetNext() ;
					if (tok.GetType() == CTokenType.NUMBER)
					{
						CExpression e = new CTermExpression(0, new CAddressTerminal(tok.GetValue())) ;
						//id.SetSubStringReference(exp, null) ;
						CTerminal term = new CIdentifierTerminal(id) ;
						exp.AddTerm(new CTermExpression(tok.getLine(), term)) ;
						exp.AddTerm(e) ;
						tok = GetNext() ;
					}
					else
					{
						Transcoder.logError(tok.getLine(), "Expecting number instead of token : "+tok.toString()) ;
						return null ;
					}
				}
				else if (tok.GetType() == CTokenType.PLUS)
				{
					tok = GetNext() ;
					CExpression e = ReadTerminalExpression() ;
					if (e == null)
					{
						Transcoder.logError(tok.getLine(), "Expecting expression after '+'") ;
						return null ;
					}
					CSumExpression sum = new CSumExpression(tok.getLine(), new CTermExpression(tok.getLine(), new CIdentifierTerminal(id)), e, CSumExpression.CSumType.ADD) ;
					exp.AddTerm(sum) ;
				}
				else if (tok.GetType() == CTokenType.COMMA)
				{
					CTerminal term = new CIdentifierTerminal(id) ;
					exp.AddTerm(new CTermExpression(tok.getLine(), term)) ;
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Unexpecting token : "+tok.toString()) ;
					return null ;
				}
			}
		
			tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.COMMA)
			{
				tok = GetNext();
			}
			else
			{
				bOk = false ;
			}
		}
		
		return exp ;
	}


	protected CExpression ReadTerminalExpression()
	{
		CTerminal term = ReadTerminal() ;
		CTermExpression exp = new CTermExpression(0, term) ;
		return exp ;
	}

	protected CTerminal ReadTerminal()
	{
		CBaseToken tok = GetCurrentToken();
		if (tok.GetKeyword() == CFPacKeywordList.C)
		{
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.STRING)
			{
				String cs = tok.GetValue() ;
				CTerminal term = new CStringTerminal(cs) ;
				StepNext() ;
				return term ;
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Expecting 'STRING' after 'C'") ;
				return null ;
			}
		}
		else if (tok.GetKeyword() == CFPacKeywordList.C)
		{
			String cs = tok.GetValue() ;
			CTerminal term = new CStringTerminal(cs) ;
			StepNext() ;
			return term ;
		}
		else if (tok.GetKeyword() == CFPacKeywordList.P)
		{
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.STRING)
			{
				String cs = tok.GetValue() ;
				CTerminal term = new CNumberTerminal(cs) ;
				StepNext() ;
				return term ;
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Expecting 'STRING' after 'P'") ;
				return null ;
			}
		}
		else if (tok.GetKeyword() == CFPacKeywordList.F)
		{
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.STRING)
			{
				String cs = tok.GetValue() ;
				CTerminal term = new CNumberTerminal(cs) ;
				StepNext() ;
				return term ;
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Expecting 'STRING' after 'F'") ;
				return null ;
			}
		}
		else if (tok.GetKeyword() == CFPacKeywordList.X)
		{
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.STRING)
			{
				String cs = tok.GetValue() ;
				CTerminal term = new CNumberTerminal("0x"+cs) ;
				StepNext() ;
				return term ;
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Expecting 'STRING' after 'X'") ;
				return null ;
			}
		}
		else if (tok.GetType() == CTokenType.NUMBER)
		{
			CAddressTerminal term = new  CAddressTerminal(tok.GetValue()) ;
			tok = GetNext() ;
			return term ;
		}
		else if (tok.GetType() == CTokenType.IDENTIFIER)
		{
			CIdentifier id = ReadIdentifier() ;
			if (id != null)
			{
				CIdentifierTerminal term = new CIdentifierTerminal(id) ;
				return term ;
			}
			else
			{
				// ...
			}
		}
		else if (tok.GetType() == CTokenType.STRING)
		{
			String cs = tok.GetValue() ;
			CTerminal term = new CStringTerminal(cs) ;
			StepNext() ;
			return term ;
		}
		else
		{
			Transcoder.logError(tok.getLine(), "Unexpecting token while reading terminal : " + tok.toString()) ;
			return null ;
		}
		return null ;
	}
}
