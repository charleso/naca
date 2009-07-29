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
/**
 * 
 */
package parser.Cobol.elements;

import jlib.misc.StringUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CCopyDeepReplacing
{
	private String m_csSourceValue = null;
	private String m_csDestinationValue = null;
	private String m_csMarkerLeft = null;
	private String m_csMarkerRight = null;
	
	public CCopyDeepReplacing(String csSourceValue, String csDestinationValue, String csMarkerLeft, String csMarkerRight)
	{
		m_csSourceValue = csSourceValue; 
		m_csDestinationValue = csDestinationValue;
		m_csMarkerLeft = csMarkerLeft;
		m_csMarkerRight = csMarkerRight;
	}
		
	public Element getAsElement(Document root)
	{
		Element eCopyReplacing = root.createElement("CopyReplacing");
		eCopyReplacing.setAttribute("SourceValue", m_csSourceValue);
		eCopyReplacing.setAttribute("DestinationValue", m_csDestinationValue);
		return eCopyReplacing;
	}
	
	public String GetCopyReference(String csCopyReference)
	{
		String cs = csCopyReference + "_" + m_csDestinationValue;
		cs = StringUtil.replace(cs, "-", "_", true);
		return cs;
	}
	
	public String GetDestinationName()
	{
		return m_csDestinationValue;
	}
	
	public String replaceData(StringBuilder sbIn)
	{
		String csIn = sbIn.toString();
		String csOldChunk = m_csMarkerLeft + m_csSourceValue + m_csMarkerRight;
		String csNewChunk = m_csDestinationValue;
		String csOut = StringUtil.replace(csIn, csOldChunk, csNewChunk, true);
		return csOut;
	}
}
