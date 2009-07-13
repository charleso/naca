/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.clientRequests;

import java.io.IOException;

import jlib.xml.Tag;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.*;

public class httpClientRequester
{
	public httpClientRequester()
	{
	}
	
	public boolean doHttpget(String csUrl)
	{
		boolean bStatus = false;
		m_responseBody = null;
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(csUrl);
		
		// Provide custom retry handler is necessary
		DefaultHttpMethodRetryHandler handler = new DefaultHttpMethodRetryHandler(3, false);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, handler);
		try
		{
			// Execute the method.
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) 
			{
				System.err.println("Method failed: " + method.getStatusLine());
			}
			// Read the response body.
			m_responseBody = method.getResponseBody();

			bStatus = true;
		} 
		catch (HttpException e) 
		{
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();			
		} 
		catch (IOException e) 
		{
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} 
		finally 
		{
			// Release the connection.
			method.releaseConnection();
		}  
		return bStatus;
	}
	
	String getResponseBodyAsString()
	{
		if(m_responseBody != null)
			return new String(m_responseBody);
		return null;
	}
	
	Tag getResponseBodyAsTag()
	{
		if(m_responseBody != null)
		{
			String cs = new String(m_responseBody);
			
			Tag tag = new Tag();
			tag.loadFromString(cs);
			return tag;			
		}
		return null;
	}
		
	private byte[] m_responseBody = null;
}
