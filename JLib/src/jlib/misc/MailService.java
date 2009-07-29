/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

/**
 * <p>Service de Mail</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Consultas</p>
 * @author <a href=mailto:dbarman@consultas.ch>Barman Dominique</a>
 * @version 1.0
 */

public class MailService 
{
	private String m_csSMTPServer = null;
	private String m_csAddressFrom = null;
	private StringArray m_arrAddressTo = new StringArray() ;
	
	public MailService(String smtp, String from) 
	{
		this.m_csSMTPServer = smtp;
		this.m_csAddressFrom = from;
	}

	public Mail createMail() 
	{
		Mail m = new Mail(this);
		m.setFrom(m_csAddressFrom);
		for (int i=0; i<m_arrAddressTo.size(); i++)
		{
			String add = m_arrAddressTo.get(i) ;
			m.addTo(add);
		}
		return m ;
	}

	public String getSMTPServer() 
	{
		return m_csSMTPServer;
	}
//
//	public String getFrom() 
//	{
//		return m_csAddressFrom;
//	}

	public void addAddressTo(String add) 
	{
		m_arrAddressTo.add(add); 
	}
}