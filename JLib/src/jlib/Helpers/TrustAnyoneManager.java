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
package jlib.Helpers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import jlib.exception.ProgrammingException;

/**
 * Decides which X509 certificates may be used to authenticate the remote side of a secure socket. 
 * This manager trusts all certificates without any checking. Not very secure, but practical
 * for accessing servers inside the same private network. 
 * 
 * @author U930GN
 */
public class TrustAnyoneManager implements X509TrustManager {

/**
 * Returns an initialized <code>InputStream</code> with the specified <code>url</code>
 * @param url A valid link to the remote resource. 
 * @return An initialized <code>InputStream</code> ready to provide data.
 * @throws IOException If the specified <code>url</code> cannot be resolved (i.e. the server
 * doesn't exist, or the server doesn't contain the specified resource). 
 */
	public static InputStream openStreamFromUrl(URL url) throws IOException {
		URLConnection connection=url.openConnection();

//******************* Troubles start when the connection is SSL **********************
		if (connection instanceof HttpsURLConnection) {
//................. Retrieves an instance of a SSL context with 'TLS' algorithm ......
// (which is the most common).
			SSLContext context;
			try {
				context = SSLContext.getInstance("TLS");
			} catch (NoSuchAlgorithmException e) {
				throw new ProgrammingException(ProgrammingException.MISSING_SSL_ALGORITHM,"Could not initialize an SSL context with 'TLS' algorithm.",e);
			}

//............... Initializes the instance with the 'TrustAnyone' manager .............
			TrustAnyoneManager taom=new TrustAnyoneManager();
			try {
				context.init(null, new TrustManager[] {taom}, null);
			} catch (KeyManagementException e) {
				// This should never happen, as we don't specify any key manager...
				throw new ProgrammingException(ProgrammingException.UNKNOWN,"Could not initialize the SSL context.",e);
			}

//.................... Initializes a SSL socket factory with the SSL context ...........
			SSLSocketFactory sf = context.getSocketFactory();

//................... Sets up the SSL connection with the SSL socket factory ...........
// (which trusts any one, so should never have problems).
			HttpsURLConnection ssl=(HttpsURLConnection)connection;
			HttpsURLConnection.setDefaultSSLSocketFactory(sf);

// Disables the check of the name server:
			ssl.setHostnameVerifier(new HostnameVerifier (){  
				public boolean verify (String hostname,SSLSession session) {  
					// I don't care if the certificate doesn't match host name 
					return true; 
				}  
			});
		}

//*********************** Returns the input stream from the connection *****************
		return connection.getInputStream();
	}

	public static InputStream openStreamFromUrl(String urlString) throws IOException {
		URL url = new URL(urlString);
		return openStreamFromUrl(url);
	}
	
	public TrustAnyoneManager() {
		super();
	}

	public X509Certificate[] getAcceptedIssuers() {
	    throw new UnsupportedOperationException();
	}

	public void checkClientTrusted(X509Certificate[] chain, String authType)
		throws CertificateException {
	    throw new UnsupportedOperationException();
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	    return;
	}
}
