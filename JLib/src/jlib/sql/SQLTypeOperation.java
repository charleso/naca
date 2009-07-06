/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 26 oct. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package jlib.sql;

import jlib.misc.StringUtil;

//import jlib.misc.StringRef;
//import jlib.misc.StringUtil;

// import nacaLib.base.*;

public class SQLTypeOperation
{
	public static final SQLTypeOperation Select = new SQLTypeOperation(true);
	public static final SQLTypeOperation CursorSelect = new SQLTypeOperation(true);
	public static final SQLTypeOperation Insert = new SQLTypeOperation(true);
	public static final SQLTypeOperation Update = new SQLTypeOperation(true);
	public static final SQLTypeOperation Delete = new SQLTypeOperation(true);
	public static final SQLTypeOperation Cursor = new SQLTypeOperation(true);
	public static final SQLTypeOperation Create = new SQLTypeOperation(true);
	public static final SQLTypeOperation Drop = new SQLTypeOperation(true);
	public static final SQLTypeOperation Commit = new SQLTypeOperation(false);
	public static final SQLTypeOperation Rollback = new SQLTypeOperation(false);
	public static final SQLTypeOperation Lock = new SQLTypeOperation(true);
	public static final SQLTypeOperation Declare = new SQLTypeOperation(false);
	
	private boolean m_bExecuteWithStatement;
		
	private SQLTypeOperation(boolean bExecuteWithStatement)
	{
		m_bExecuteWithStatement = bExecuteWithStatement;
	}
	
	public boolean executeWithStatement()
	{
		return m_bExecuteWithStatement;
	}
		
	public static SQLTypeOperation determineOperationType(String csQuery, boolean bCursor)
	{
		String csSQLOperation = StringUtil.getFirstWordWithStopList(csQuery, ";");
		return getSQLTypeOperation(csSQLOperation, bCursor);
	}
	
	private static SQLTypeOperation getSQLTypeOperation(String s, boolean bCursor)
	{
		if(s.equalsIgnoreCase("SELECT"))
		{
			if(bCursor)
				return SQLTypeOperation.CursorSelect;  
			else
				return SQLTypeOperation.Select;
		}
		else if(s.equalsIgnoreCase("DELETE"))
			return SQLTypeOperation.Delete;
		else if(s.equalsIgnoreCase("UPDATE"))
			return SQLTypeOperation.Update;
		else if(s.equalsIgnoreCase("INSERT"))
			return SQLTypeOperation.Insert;
		else if(s.equalsIgnoreCase("COMMIT"))
			return SQLTypeOperation.Commit;
		else if(s.equalsIgnoreCase("ROLLBACK"))
			return SQLTypeOperation.Rollback;
		else if(s.equalsIgnoreCase("CREATE"))
			return SQLTypeOperation.Create;
		else if(s.equalsIgnoreCase("DROP"))
			return SQLTypeOperation.Drop;
		else if(s.equalsIgnoreCase("LOCK"))
			return SQLTypeOperation.Lock;
		else if(s.equalsIgnoreCase("DECLARE"))
			return SQLTypeOperation.Declare;
		else if(s.equalsIgnoreCase("_SELECT"))
		{
			if(bCursor)
				return SQLTypeOperation.CursorSelect;  
			else
				return SQLTypeOperation.Select;
		}

		return null;
	}
	
	public static int minPositive(int nEnd1, int nEnd2)
	{
		if(nEnd1 >= 0 && nEnd2 >= 0)
		{
			if(nEnd1 < nEnd2)
				return nEnd1;
			return nEnd2;
		}
		
		if(nEnd1 >= 0)
			return nEnd1;
		
		if(nEnd2 >= 0)
			return nEnd2;
		return -1;
	}
		
	static private String addLeadingTablePrefix(String begining, String env, String csForcedReplacedPrefix, String querry)
	{
		int nPos = -1 ;
		int i = 0;
		do 
		{			
			nPos = querry.indexOf(',', i) ;
			while (querry.charAt(i) == ' ')
			{
				begining += ' ' ;
				i++ ;
			}
			if (nPos == -1)
			{
				String csRight = querry.substring(i) ;
				begining += setPrefixIfRequired(env, csRight, csForcedReplacedPrefix);
			}
			else
			{
				String csRight = querry.substring(i, nPos) ;
				begining += setPrefixIfRequired(env, csRight, csForcedReplacedPrefix);
				begining += ',';
				i = nPos+1 ;
			}
		}
		while (nPos != -1) ;
		
		return begining;
	}
	
	private static int getPositionFirstStopListKeywordNextFrom(String csQueryUpper, int nStart)
	{
		int nEndWhere = csQueryUpper.indexOf("WHERE", nStart) ;

		// search for position of keyword following the where
		int nEndParenthesis = csQueryUpper.indexOf(")", nStart) ;
		int nEnd = minPositive(nEndWhere, nEndParenthesis);
		
		int nEndOrder = csQueryUpper.indexOf("ORDER", nStart) ;
		nEnd = minPositive(nEnd, nEndOrder);					
		
		int nEndGroup = csQueryUpper.indexOf("GROUP BY", nStart) ;
		nEnd = minPositive(nEnd, nEndGroup); 
		
		int nEndForUpdate = csQueryUpper.indexOf("FOR UPDATE", nStart) ;
		nEnd = minPositive(nEnd, nEndForUpdate);
		
		int nEndForUnion = csQueryUpper.indexOf("UNION", nStart) ;
		nEnd = minPositive(nEnd, nEndForUnion);
		
		int nEndJoin = csQueryUpper.indexOf("JOIN", nStart) ;
		nEnd = minPositive(nEnd, nEndJoin);
		return nEnd;
	}
		
	
	private static String addEnvironmentPrefixStandardParser(String env, String csQuery, String csQueryUpper, SQLTypeOperation typeOperation, String csForcedReplacedPrefix, boolean bSupportJoin)
	{
		int nStart = 0 ;

		int n = csQueryUpper.indexOf("SELECT");
		if (n>=0)
		{
			nStart = n +7 ;
			n = csQueryUpper.indexOf("FROM", nStart);
			while (n>=0)
			{
				nStart = n +5 ;
				int i = 0 ;
				String begining = csQuery.substring(0, nStart) ;
				String querry = csQuery.substring(nStart) ;
	
				int nEndWhere = csQueryUpper.indexOf("WHERE", nStart) ;
				
				int nEndParenthesis = csQueryUpper.indexOf(")", nStart) ;
				int nEnd = minPositive(nEndWhere, nEndParenthesis);
				
				int nEndOrder = csQueryUpper.indexOf("ORDER", nStart) ;
				nEnd = minPositive(nEnd, nEndOrder);					
				
				int nEndGroup = csQueryUpper.indexOf("GROUP BY", nStart) ;
				nEnd = minPositive(nEnd, nEndGroup); 
				
				int nEndForUpdate = csQueryUpper.indexOf("FOR UPDATE", nStart) ;
				nEnd = minPositive(nEnd, nEndForUpdate);
				
				int nEndForUnion = csQueryUpper.indexOf("UNION", nStart) ;
				nEnd = minPositive(nEnd, nEndForUnion);

				if(bSupportJoin)
				{
					int nEndJoin = csQueryUpper.indexOf("JOIN", nStart) ;
					nEnd = minPositive(nEnd, nEndJoin);
				}
				
				String end = "" ;
				if (nEnd != -1)
				{
					end = csQuery.substring(nEnd) ;
					querry = csQuery.substring(nStart, nEnd) ;
				}
				int nPos = -1 ;
				do 
				{
					nPos = querry.indexOf(',', i) ;
					while (querry.charAt(i) == ' ')
					{
						begining += ' ' ;
						i++ ;
					}
					if (nPos == -1)
					{
						String csRight = querry.substring(i) ;
						csRight = setPrefixIfRequired(env, csRight, csForcedReplacedPrefix);
						begining += csRight;						
					}
					else
					{
						String csRight = querry.substring(i, nPos) ;
						csRight = setPrefixIfRequired(env, csRight, csForcedReplacedPrefix);
						begining += csRight;
						begining += ',';
						i = nPos+1 ;
					}
				}
				while (nPos != -1) ;
				csQuery = begining + " " + end ;
				csQueryUpper = csQuery.toUpperCase();
				n = csQueryUpper.indexOf("FROM", nStart);
			}
		}
		
		nStart = 0;
		if(typeOperation == SQLTypeOperation.Insert)
		{
			n = csQueryUpper.indexOf("INTO");
			if (n>=0)
			{
				nStart = n + 4;
			}
		}
		else if(typeOperation == SQLTypeOperation.Update)
		{
			nStart = 6;
		}
		else if(typeOperation == SQLTypeOperation.Delete)
		{
			n = csQueryUpper.indexOf("FROM");
			if (n>=0)
			{
				nStart = n + 4;
			}
		}
		else if(typeOperation == SQLTypeOperation.Create)
		{
			n = csQueryUpper.indexOf("TABLE");
			if (n >= 0)
			{
				nStart = n + 5;
			}
		}
		else if(typeOperation == SQLTypeOperation.Drop)
		{
			n = csQueryUpper.indexOf("TABLE");
			if (n >= 0)
			{
				nStart = n + 5;
			}
		}
		else if(typeOperation == SQLTypeOperation.Lock)
		{
			n = csQueryUpper.indexOf("TABLE");
			if (n >= 0)
			{
				nStart = n + 5;
			}
		}
		
		if (nStart == 0) 
			return csQuery;
		
		String csLeft = csQuery.substring(0, nStart);
		String csRight = csQuery.substring(nStart);
		csRight = StringUtil.trimLeft(csRight);
		int nWhiteSpace = StringUtil.getNextWhiteSpacePosition(csRight);
		if(nWhiteSpace != -1)
		{
			String csTableName = csRight.substring(0, nWhiteSpace);
			String csRemaining = csRight.substring(nWhiteSpace);
			csTableName = setPrefixIfRequired(env, csTableName, csForcedReplacedPrefix);
			csQuery = csLeft + " " + csTableName + " " +  csRemaining;
		}
		
		return csQuery;
	}
	
	// Not prefixed table name are prefixed by env
	// Table names prefixed by csForcedReplacedPrefix are also prefixed by env
	// Table names prefixed by another prefix are unchanged
	public static String addEnvironmentPrefix(String env, String csQuery, SQLTypeOperation typeOperation, String csForcedReplacedPrefix)
	{
		if (env.equals(""))
		{
			return csQuery;
		}
		
		if (csQuery.startsWith("_"))
		{			
			return csQuery.substring(1);
		}
		
		String csQueryUpper = csQuery.toUpperCase();
		
		if(csQueryUpper.indexOf(" JOIN ") == -1)	// No JOIN keyword : Use standard parser
			return addEnvironmentPrefixStandardParser(env, csQuery, csQueryUpper, typeOperation, csForcedReplacedPrefix, false);
		
		// Custom parser for join keyword support
		int nStart = 0 ;
		int nPosSelect = csQueryUpper.indexOf("SELECT");
		if (nPosSelect >= 0)
		{
			nStart = nPosSelect + 7;
			int nPosFrom = csQueryUpper.indexOf("FROM", nStart);
			if(nPosFrom >= 0)	// Only 1 FROM keyword in a SELECT clause //while (n>=0)
			{
				nStart = nPosFrom + 5;
				String begining = csQuery.substring(0, nStart) ;
				String querry = csQuery.substring(nStart) ;
				
				int nEnd = getPositionFirstStopListKeywordNextFrom(csQueryUpper, nStart); 
				String end = "" ;
				if (nEnd != -1)
				{
					end = csQuery.substring(nEnd) ;
					querry = csQuery.substring(nStart, nEnd) ;
				}

				begining = addLeadingTablePrefix(begining, env, csForcedReplacedPrefix, querry);
				csQuery = begining + " " + end ;
				csQueryUpper = csQuery.toUpperCase();
				//n = csQueryUpper.indexOf("FROM", nStart);
			}
			
			int nPosJoin = csQueryUpper.indexOf("JOIN", nStart);
			if(nPosJoin >= 0)
			{
				while(nPosJoin >= 0)
				{
					nStart = nPosJoin + 4;
					String csLeft = csQuery.substring(0, nStart) ;

					String csRight = csQuery.substring(nStart) ;
					String csRightUpper = csQueryUpper.substring(nStart) ;
					int nEnd = csRightUpper.indexOf(" ON ", 0) ;
					if (nEnd != -1)
					{	
						String csTableName = StringUtil.getFirstWord(csRight);
						csRight = StringUtil.removeFirstWord(csRight);
						csTableName = setPrefixIfRequired(env, csTableName, csForcedReplacedPrefix);
						
						csQuery = csLeft + " " + csTableName + " " + csRight;  
						csQueryUpper = csQuery.toUpperCase();

						nPosJoin = csQueryUpper.indexOf("JOIN", nStart);
					}
				}
			}
			csQueryUpper = csQuery.toUpperCase();
			csQuery = addEnvironmentPrefixStandardParser(env, csQuery, csQueryUpper, typeOperation, csForcedReplacedPrefix, true);
			return csQuery;
		}
		
		nStart = 0;
		int n;
		if(typeOperation == SQLTypeOperation.Insert)
		{
			n = csQueryUpper.indexOf("INTO");
			if (n>=0)
			{
				nStart = n + 4;
			}
		}
		else if(typeOperation == SQLTypeOperation.Update)
		{
			nStart = 6;
		}
		else if(typeOperation == SQLTypeOperation.Delete)
		{
			n = csQueryUpper.indexOf("FROM");
			if (n>=0)
			{
				nStart = n + 4;
			}
		}
		else if(typeOperation == SQLTypeOperation.Create)
		{
			n = csQueryUpper.indexOf("TABLE");
			if (n >= 0)
			{
				nStart = n + 5;
			}
		}
		else if(typeOperation == SQLTypeOperation.Drop)
		{
			n = csQueryUpper.indexOf("TABLE");
			if (n >= 0)
			{
				nStart = n + 5;
			}
		}
		else if(typeOperation == SQLTypeOperation.Lock)
		{
			n = csQueryUpper.indexOf("TABLE");
			if (n >= 0)
			{
				nStart = n + 5;
			}
		}
		
		if (nStart == 0) 
			return csQuery;
		
		String csLeft = csQuery.substring(0, nStart);
		String csRight = csQuery.substring(nStart);
		csRight = StringUtil.trimLeft(csRight);
		int nWhiteSpace = StringUtil.getNextWhiteSpacePosition(csRight);
		if(nWhiteSpace != -1)
		{
			String csTableName = csRight.substring(0, nWhiteSpace);
			String csRemaining = csRight.substring(nWhiteSpace);
			csTableName = setPrefixIfRequired(env, csTableName, csForcedReplacedPrefix);
			csQuery = csLeft + " " + csTableName + " " +  csRemaining;
		}
		
		return csQuery;
	}
		
	private static String setPrefixIfRequired(String env, String csRight, String csForcedReplacedPrefix)
	{
		if(csRight.indexOf('.') == -1)	// Prefix not already set
			return env + "." + csRight;	
		if(!StringUtil.isEmpty(csForcedReplacedPrefix))
		{
			if(StringUtil.startsWithNoCase(csRight, csForcedReplacedPrefix))	// table prefix is a prefix that must be replaced
			{
				int nForcedReplacedPrefixLength = csForcedReplacedPrefix.length();
				String csTable = csRight.substring(nForcedReplacedPrefixLength);
				return env + "." + csTable;
			}
		}
		return csRight;
	}
	
	public static String updateMarkers(String csQuery)
	{
		int nPosStart = csQuery.indexOf('#', 0);
		while (nPosStart != -1)
		{
			String sLeft = csQuery.substring(0, nPosStart);
			int n = nPosStart;
			n++; // Skip the #
			int nLength = csQuery.length();
			char c = csQuery.charAt(n);
			while (Character.isLetterOrDigit(c))
			{
				n++;
				if (n == nLength)
				{
					break;
				}
				c = csQuery.charAt(n);
			}
			String sItemId = csQuery.substring(nPosStart, n);
			if (sItemId != null)
			{
				nPosStart += sItemId.length();
				String sRight = csQuery.substring(nPosStart);
				csQuery = sLeft + "?" + sRight;
			}
	
			nPosStart = csQuery.indexOf('#', nPosStart);
		}
		return csQuery;
	}
}
