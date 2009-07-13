/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.misc;

import jlib.misc.DataFileLineReader;
import jlib.misc.LineRead;
import jlib.misc.LogicalFileDescriptor;
import jlib.misc.Mail;
import jlib.misc.MailService;
import nacaLib.varEx.FileDescriptor;

public class MailUtil
{
	private FileDescriptor m_file = null;
	
	private String m_csForcedMail = null;
	private String m_csSmtpServer = "smtp.intra.consultas.ch";

	public MailUtil(FileDescriptor file)
	{
		m_file = file;
	}

	public boolean execute(String csParameter)
	{
		if (csParameter != null)
		{	
			String csParameterUpper = csParameter.toUpperCase();
			if (csParameterUpper.indexOf("FORCEDMAIL=") != -1)
			{
				int nPos = csParameterUpper.indexOf("FORCEDMAIL=") + 11;
				int nPosEnd = csParameterUpper.indexOf(",", nPos);
				if (nPosEnd == -1)
					m_csForcedMail = csParameter.substring(nPos);
				else
					m_csForcedMail = csParameter.substring(nPos, nPosEnd);
			}
			if (csParameterUpper.indexOf("SMTPSERVER=") != -1)
			{
				int nPos = csParameterUpper.indexOf("SMTPSERVER=") + 11;
				int nPosEnd = csParameterUpper.indexOf(",", nPos);
				if (nPosEnd == -1)
					m_csSmtpServer = csParameter.substring(nPos);
				else
					m_csSmtpServer = csParameter.substring(nPos, nPosEnd);
			}
		}
		
		if (m_csForcedMail != null)
			System.out.println("MailUtil: Forced mail " + m_csForcedMail);
		
		try
		{
			String csFile = m_file.getPhysicalName();
			mail(csFile);
		}
		catch(Exception ex)
		{
			throw new RuntimeException(ex);
		}

		return true;
	}

	private boolean mail(String csFile)
	{
		DataFileLineReader dataFileIn = new DataFileLineReader(csFile, 65536, 0);
		LogicalFileDescriptor logicalFileDescriptor = new LogicalFileDescriptor("", csFile);
		if(logicalFileDescriptor != null)
		{
			boolean bInOpened = dataFileIn.open(logicalFileDescriptor);
			if(bInOpened)
			{
				LineRead lineRead = dataFileIn.readNextUnixLine();
				if (lineRead != null)
				{
					MailService mailService = new MailService(m_csSmtpServer, "");
					
					String csMail = null;
					String csSubject = null;
					String csMailFrom = null;
					boolean bStartText = false;
					StringBuffer sbText = new StringBuffer();
					
					while (lineRead != null)
					{
						String csLine = lineRead.getChunkAsString().trim();
						if (csLine.startsWith("%XMITIP "))
						{
							csMail = csLine.substring(8, csLine.length() - 2);
						}
						else if (csLine.startsWith("SUBJECT "))
						{
							csSubject = csLine.substring(9, csLine.length() - 3).trim();
						}
						else if (csLine.startsWith("FROM "))
						{
							csMailFrom = csLine.substring(5, csLine.length() - 2);
						}
						else if (csLine.startsWith("MSGT "))
						{
							bStartText = true;
							sbText.append("\r\n");
						}
						else if (csLine.startsWith("'"))
						{
							Mail mail = mailService.createMail();
							mail.setFrom(csMailFrom);
							if (m_csForcedMail == null)
								mail.addTo(csMail);
							else
								mail.addTo(m_csForcedMail);
							mail.setSubject(csSubject);
							mail.setText(sbText.toString());
							mail.send();
							bStartText = false;
							sbText = new StringBuffer();
						}
						else if (bStartText)
						{
							sbText.append(csLine.substring(0, csLine.length() - 2) + "\r\n");
						}

						lineRead = dataFileIn.readNextUnixLine();
					}
				}
				dataFileIn.close();
				return true;
			}
		}
		
		return false;
	}
}