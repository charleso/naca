/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 22 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.sqlSupport;

import nacaLib.base.CJMapObject;
import nacaLib.exceptions.CGotoException;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
class SQLErrorGotoContinueType
{
	public static SQLErrorGotoContinueType OnErrorGoto = new SQLErrorGotoContinueType(); 
	public static SQLErrorGotoContinueType OnErrorContinue = new SQLErrorGotoContinueType();
	
	SQLErrorGotoContinueType()
	{
	}
}

public class SQLErrorManager extends CJMapObject
{
	public SQLErrorManager()
	{
	}
	
	public void reuse()
	{
		m_sectionErrorGoto = null;
		m_paragraphErrorGoto = null;
		m_SQLErrorGotoContinueType = null;
	}
	
	public void manageOnErrorGoto(Paragraph paragraphSQGErrorGoto, CSQLStatus sqlStatus)
	{
		registerOnErrorGoto(paragraphSQGErrorGoto);
		manageSQLError(sqlStatus);
	}
	
	public void manageOnErrorGoto(Section section, CSQLStatus sqlStatus)
	{
		registerOnErrorGoto(section);
		manageSQLError(sqlStatus);
	}
	
	public void manageOnErrorContinue(CSQLStatus sqlStatus)
	{
		registerOnErrorContinue();
		manageSQLError(sqlStatus);
	}



	
	private void registerOnErrorGoto(Section section)
	{
		m_sectionErrorGoto = section;
		m_paragraphErrorGoto = null;
		m_SQLErrorGotoContinueType = SQLErrorGotoContinueType.OnErrorGoto;
	}

	private void registerOnErrorGoto(Paragraph paragraph)
	{
		m_sectionErrorGoto = null;
		m_paragraphErrorGoto = paragraph;
		m_SQLErrorGotoContinueType = SQLErrorGotoContinueType.OnErrorGoto;
	}
	
	private void registerOnErrorContinue()
	{
		m_sectionErrorGoto = null;
		m_paragraphErrorGoto = null;
		m_SQLErrorGotoContinueType = SQLErrorGotoContinueType.OnErrorContinue;
	}
	
	public void manageSQLError(CSQLStatus sqlStatus)
	{
		if(sqlStatus != null)
		{
			boolean bSQLCodeError = sqlStatus.isLastSQLCodeAnError();
			if(bSQLCodeError)
			{
				if(m_SQLErrorGotoContinueType == SQLErrorGotoContinueType.OnErrorGoto)
				{
					if(m_paragraphErrorGoto != null)
					{
						CGotoException e = new CGotoException(m_paragraphErrorGoto);
						throw e;
					}
					else if(m_sectionErrorGoto != null)
					{
						CGotoException e = new CGotoException(m_sectionErrorGoto);
						throw e;
					}
				}
				else if(m_SQLErrorGotoContinueType == SQLErrorGotoContinueType.OnErrorContinue)
				{
					; // Do nothing
				}
				else
				{
					// Crash: TODO force shutdown app
				}
			}
		}
	}
	
	private Section m_sectionErrorGoto = null;
	private Paragraph m_paragraphErrorGoto = null;
	
	private SQLErrorGotoContinueType m_SQLErrorGotoContinueType = null;
}
