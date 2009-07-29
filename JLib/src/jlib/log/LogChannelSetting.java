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
package jlib.log;
/**
 * Class containing the default settings for one channel.
 * The default settings are merged with the event settings before sending
 * the event to the {@link LogCenter}.
 */
public class LogChannelSetting
{
	LogChannelSetting()
	{
	}
/**
 * Returns the default <i>RunId</i> identifier for the channel.
 * @return The default <i>RunId</i> identifier for the channel.
 * @see {@link Log} for a discussion about the meaning of the <i>RunId</i>,
 * <i>RuntimeId</i>, and <i>Product</i> identifiers.
 */	
	String getRunId()
	{
		return m_csRunId;
	}
/**
 * Sets the default <i>RunId</i> for the channel. 
 * @param csRunId Default <i>RunId</i> to be used if events sent to a channel
 * have no <i>RunId</i> specified.
 * @see {@link Log} for a discussion about the meaning of the <i>RunId</i>,
 * <i>RuntimeId</i>, and <i>Product</i> identifiers.
 */
	void setRunId(String csRunId)
	{
		m_csRunId = csRunId;
	}

/**
 * Sets the default <i>RuntimeId</i> for the channel. 
 * @param csRuntimeId Default <i>RuntimeId</i> to be used if events sent to a channel
 * have no <i>RuntimeId</i> specified.
 * @see {@link Log} for a discussion about the meaning of the <i>RunId</i>,
 * <i>RuntimeId</i>, and <i>Product</i> identifiers.
 */
	void setRuntimeId(String csRuntimeId)
	{
		m_csRuntimeId = csRuntimeId;
	}

/**
 * Returns the default <i>RuntimeId</i> identifier for the channel.
 * @return The default <i>RuntimeId</i> identifier for the channel.
 * @see {@link Log} for a discussion about the meaning of the <i>RunId</i>,
 * <i>RuntimeId</i>, and <i>Product</i> identifiers.
 */	
	String getRuntimeId()
	{
		return m_csRuntimeId;
	}

/**
 * Returns the default <i>RuntimeId</i> identifier for the channel.
 * @return The default <i>RuntimeId</i> identifier for the channel.
 * @see {@link Log} for a discussion about the meaning of the <i>RunId</i>,
 * <i>RuntimeId</i>, and <i>Product</i> identifiers.
 */		
	String getProduct()
	{
		return m_csProduct;
	}
	
/**
 * Sets the default <i>Product</i> identifier for the channel.
 * @param csProduct The default <i>Product</i> identifier for the channel.
 * @see {@link Log} for a discussion about the meaning of the <i>RunId</i>,
 * <i>RuntimeId</i>, and <i>Product</i> identifiers.
 */		
	void setProduct(String csProduct)
	{
		m_csProduct = csProduct;
	}
	
	private String m_csRunId;		// manual or generated runtime id
	private String m_csRuntimeId;	// always generated runtime id
	private String m_csProduct;
}
