/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
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
	public static SQLWarningErrorType WarningContinue = new SQLWarningErrorType("WarningContinue");
	public static SQLWarningErrorType WarningGoto = new SQLWarningErrorType("WarningGoto");
	
	public static SQLWarningErrorType ErrorContinue = new SQLWarningErrorType("ErrorContinue");
	public static SQLWarningErrorType ErrorGoto = new SQLWarningErrorType("ErrorGoto");
	
	public static SQLWarningErrorType NotFoundContinue = new SQLWarningErrorType("NotFoundContinue");
	public static SQLWarningErrorType NotFoundGoto = new SQLWarningErrorType("NotFoundGoto");

	private String m_cs = null;
	SQLWarningErrorType(String cs)
	{
		m_cs = cs;
	}
	
	public static String getSQLWarningErrorStatement(SQLWarningErrorType type, String csSQLWarningErrorArg)
	{
		String cs = "";
		if(type == ErrorGoto)
			cs = ".onErrorGoto(";
		else if(type == WarningGoto)
			cs = ".onWarningGoto(";
		else if(type == NotFoundGoto)
			cs = ".onNotFoundGoto(";
		else if(type == WarningContinue)
			cs = ".onWarningContinue(";
		else if(type == ErrorContinue)
			cs = ".onErrorContinue(";
		else if(type == NotFoundContinue)
			cs = ".onNotFoundContinue(";

		if(!cs.equals(""))
		{
			if(csSQLWarningErrorArg != null)
				cs += csSQLWarningErrorArg;
			cs += ")";
		}
		return cs;
		
		/* PJD: Was befaiore adding NotFoundContinue / NotFoundGoto
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
		*/
	}
}
