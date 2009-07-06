/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.exception;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: ApplicativeException.java,v 1.3 2008/02/20 07:53:46 u930gn Exp $
 */
public class ApplicativeException extends Exception
{
	private static final long serialVersionUID = 9203631895766632784L;
	private String m_csError = null;
	private  String m_csMessage = null;
	private  Throwable m_throwable = null;
	
	protected ApplicativeException(ApplicativeException e)
	{
		super(e);
		m_csError = e.m_csError;
		m_csMessage = e.m_csMessage;
		m_throwable = e.m_throwable;
	}
	
	protected ApplicativeException(String csError, String csMessage)
	{
		m_csError = csError;
		m_csMessage = csMessage;
		m_throwable = new Throwable(); 
	}
	
	protected ApplicativeException(String csError, String csMessage, Throwable throwable)
	{
		m_csError = csError;
		m_csMessage = csMessage;
		m_throwable = throwable;
	}
	
	public String getCode()
	{
		return m_csError;
	}
	
	public String getMessage()
	{
		return m_csMessage;
	}
	
	public Throwable getThrowable()
	{
		return m_throwable;
	}
}
