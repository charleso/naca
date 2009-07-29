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
package jlib.display;


import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

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
public class HTTPMapFieldLoader
{
	protected HttpServletRequest m_HttpRequest = null ;
	public HTTPMapFieldLoader(HttpServletRequest req)
	{
		m_HttpRequest = req ;
	}


	public Enumeration getFieldNames()
	{
		return m_HttpRequest.getParameterNames() ;
	}
	
	public String getAction() 
	{
		return getFieldValue("DisplayContextAction") ;
	}
	/* (non-Javadoc)
	 * @see CJMap.CBaseMapFieldLoader#GetFieldValue(java.lang.String)
	 */
	public String getFieldValue(String fieldName)
	{
		if (m_HttpRequest != null)
		{
			String cs = m_HttpRequest.getParameter(fieldName);
			if (cs == null)
			{
				return "" ;
			}
			else if (cs.indexOf(195) > 0)
			{
				String charsetname = Charset.defaultCharset().displayName() ;
				if (charsetname.equalsIgnoreCase("UTF-8"))
				{
					Charset charset = Charset.forName("ISO-8859-1") ;
					//ByteBuffer buf = ByteBuffer.wrap(cs.getBytes()) ;
					ByteBuffer buf = charset.encode(cs) ;
					String cs2 = new String(buf.array()) ;
					cs2 = cs2.trim() ;
					return cs2;
				}
				else
				{
					Charset charset = Charset.forName("UTF-8") ;
					ByteBuffer buf = ByteBuffer.wrap(cs.getBytes()) ;
					CharBuffer cbuf = charset.decode(buf) ;
					String cs2 = new String(cbuf.array()) ;
					cs2 = cs2.trim() ;
					return cs2;
				}
			}
			return cs ;
		}
		return "" ;
	}
}
