/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 23 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package utils;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SQLWarningErrorType
{
	public static SQLWarningErrorType WarningContinue = new SQLWarningErrorType();
	public static SQLWarningErrorType WarningGoto = new SQLWarningErrorType();
	
	public static SQLWarningErrorType ErrorContinue = new SQLWarningErrorType();
	public static SQLWarningErrorType ErrorGoto = new SQLWarningErrorType();

	SQLWarningErrorType()
	{
	}
	
	public static String getSQLWarningErrorStatement(SQLWarningErrorType type, String csSQLWarningErrorArg)
	{
		String cs = "";
		if(type == ErrorGoto)
			cs = ".onErrorGoto(";
		else if(type == WarningGoto)
			cs = ".onWarningGoto(";
		else
			cs = ".onErrorContinue(";
		if(!cs.equals(""))
		{
			if(csSQLWarningErrorArg != null)
				cs += csSQLWarningErrorArg;
			cs += ")";
		}
		return cs;
	}
}
