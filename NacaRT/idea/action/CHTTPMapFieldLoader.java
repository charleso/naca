/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.action;


import idea.manager.CMapFieldLoader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import nacaLib.misc.KeyPressed;

/*
 * Created on Sep 23, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CHTTPMapFieldLoader extends CMapFieldLoader
{
	protected HttpServletRequest m_HttpRequest = null ;
	public CHTTPMapFieldLoader(HttpServletRequest req)
	{
		m_HttpRequest = req ;
		String csKey = req.getParameter("PFKey") ;
		m_KeyPressed = KeyPressed.getKeyFromHttp(csKey);
	}


	/* (non-Javadoc)
	 * @see CJMap.CBaseMapFieldLoader#GetFieldValue(java.lang.String)
	 */
	public String getFieldValue(String fieldName)
	{
		String cs = m_HttpRequest.getParameter(fieldName);
		if (cs == null)
		{
			return "" ;
		}
		else if (cs.indexOf(195) >= 0)
		{
			String encoding = "" ;
			try
			{
				InputStreamReader isr = new InputStreamReader(m_HttpRequest.getInputStream()) ;
				encoding = isr.getEncoding() ;
			}
			catch (IOException e)
			{
				encoding = Charset.defaultCharset().displayName() ;
			}
			if (encoding.equalsIgnoreCase("UTF-8"))
			{
				Charset charset = Charset.forName("ISO-8859-1") ;
				//ByteBuffer buf = ByteBuffer.wrap(cs.getBytes()) ;
				ByteBuffer buf = charset.encode(cs) ;
				String cs2 = new String(buf.array()) ;
				cs2 = cs2.trim() ;
				return cs2;
			}
//			else if (encoding.equalsIgnoreCase("ASCII"))
//			{
//				Charset charset1 = Charset.forName("UTF-8") ;
//				Charset charset2 = Charset.forName("ISO-8859-1") ;
//				//ByteBuffer buf = ByteBuffer.wrap(cs.getBytes()) ;
//				ByteBuffer buf = charset2.encode(cs) ;
//				CharBuffer cbuf = charset1.decode(buf) ;
//				String cs2 = new String(cbuf.array()) ;
//				cs2 = cs2.trim() ;
//				return cs2;
//			}
			else
			{
				Charset charset1 = Charset.forName("UTF-8") ;
				Charset charset2 = Charset.forName("ISO-8859-1") ;
				//ByteBuffer buf = ByteBuffer.wrap(cs.getBytes()) ;
				ByteBuffer buf = charset2.encode(cs) ;
				CharBuffer cbuf = charset1.decode(buf) ;
				String cs2 = new String(cbuf.array()) ;
				cs2 = cs2.trim() ;
				return cs2;
			}
		}
		return cs ;
	}
	/* (non-Javadoc)
	 * @see CJMap.CBaseMapFieldLoader#IsFieldModified(java.lang.String)
	 */
	public boolean isFieldModified(String fieldName)
	{
		String cs = m_HttpRequest.getParameter(fieldName+"UPD");
		if (cs == null)
		{
			return false ;
		}
		if (cs.equals("1"))
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}

//	public KeyPressed getKeyPressed()
//	{
//		return null;
//	}

	/* (non-Javadoc)
	 * @see CBaseMapFieldLoader#getIDPage()
	 */
	public String getIDPage()
	{
		String cs = m_HttpRequest.getParameter("idPage");
		if (cs == null)
		{
			cs = "" ;
		}
		return cs;
	}
}
