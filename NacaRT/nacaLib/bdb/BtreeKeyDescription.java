/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.bdb;

import java.util.ArrayList;

import nacaLib.varEx.SortKeySegmentDefinition;

import jlib.misc.ArrayDyn;
import jlib.misc.ArrayFix;
import jlib.misc.ArrayFixDyn;
import jlib.misc.LineRead;
import jlib.misc.LittleEndingUnsignBinaryBufferStorage;
import jlib.misc.NumberParser;
import jlib.misc.StringUtil;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BtreeKeyDescription.java,v 1.20 2007/02/01 12:46:49 u930di Exp $
 */
public class BtreeKeyDescription
{	
	private String m_csKeys = null;
	private ArrayFixDyn<BtreeKeySegment> m_arrKeySegment = new ArrayDyn<BtreeKeySegment>();
	private byte[] m_tbyKey = null;
	int m_nKeyLength = 0;
	private int m_nKeyPositionInKey = 0;
	boolean m_bFileInEbcdic = false;
	
	public BtreeKeyDescription()
	{		
		m_nKeyPositionInKey = 0;
	}
	
	boolean set(String csKeys, boolean bAddSegmentRecordId)
	{
		m_csKeys = csKeys.trim();
		if(m_csKeys.startsWith("("))
			m_csKeys = m_csKeys.substring(1);
		if(m_csKeys.endsWith(")"))
			m_csKeys = m_csKeys.substring(0, m_csKeys.length()-1);
		
		m_nKeyPositionInKey = 0;
		while(!StringUtil.isEmpty(m_csKeys))
		{
			int nKeyPositionInData = getChunkAsInt()-1;
			int nKeyLength = getChunkAsInt();
			String csType = getChunk();
			String csOrder = getChunk();
			boolean bAscending = true;
			if(!csOrder.equalsIgnoreCase("A"))
				bAscending = false;
			
			BtreeKeySegment seg = null;
			if(csType.equalsIgnoreCase("CH"))
				seg = new BtreeKeySegmentAlphaNum(nKeyPositionInData, m_nKeyPositionInKey, nKeyLength, bAscending);
			else if(csType.equalsIgnoreCase("PD"))	// packed
				seg = new BtreeKeySegmentComp3(nKeyPositionInData, m_nKeyPositionInKey, nKeyLength, bAscending);
			else if(csType.equalsIgnoreCase("C4"))	// Binary
				seg = new BtreeKeySegmentBinary(nKeyPositionInData, m_nKeyPositionInKey, nKeyLength, bAscending);
			else if(csType.equalsIgnoreCase("BI"))	// Binary or packed
				seg = new BtreeKeySegmentUnsignedBinaryOrPacked(nKeyPositionInData, m_nKeyPositionInKey, nKeyLength, bAscending);
			else if(csType.equalsIgnoreCase("FI"))
				seg = new BtreeKeySegmentSignBinary(nKeyPositionInData, m_nKeyPositionInKey, nKeyLength, bAscending);

			m_nKeyPositionInKey += nKeyLength;
			if(seg != null)
				m_arrKeySegment.add(seg);
		}
		
		if(bAddSegmentRecordId)
			addRecordIdKeySegment();
		return true;
	}
	
	public void addRecordIdKeySegment()
	{		
		BtreeKeySegment segRecordId = new BtreeKeySegmentBinary(0, m_nKeyPositionInKey, 4, true);	// Binary ascending
		m_arrKeySegment.add(segRecordId);		
		m_nKeyPositionInKey += 4;
		
		// Compress
		int nSize = m_arrKeySegment.size();
		BtreeKeySegment arr[] = new BtreeKeySegment[nSize];
		m_arrKeySegment.transferInto(arr);
		ArrayFix<BtreeKeySegment> arrFix = new ArrayFix<BtreeKeySegment>(arr);
		m_arrKeySegment = arrFix;	// replace by a fix one (uning less memory)
	}
	
	public void addSegmentDefinition(SortKeySegmentDefinition keySegmentDefinition)
	{
		int nKeyPositionInData = keySegmentDefinition.getBufferStartPosKey();
		int nBufferLength = keySegmentDefinition.getBufferLengthKey();
		BtreeSegmentKeyTypeFactory btreeSegmentKeyTypeFactory = keySegmentDefinition.getSegmentKeyType();
		BtreeKeySegment btreeKeySegment = btreeSegmentKeyTypeFactory.make(nKeyPositionInData, m_nKeyPositionInKey, nBufferLength, keySegmentDefinition.m_bAscending);

		m_arrKeySegment.add(btreeKeySegment);
		m_nKeyPositionInKey += nBufferLength;
	}	
	
	private int getChunkAsInt()
	{
		String cs = getChunk();
		return NumberParser.getAsInt(cs);
	}
	
	private String getChunk()
	{
		String cs = null;
		int nIndex = m_csKeys.indexOf(',');
		if(nIndex == -1)
		{
			cs = m_csKeys;
			cs = cs.trim();
			m_csKeys = null;
		}
		else
		{
			cs = m_csKeys.substring(0, nIndex);
			cs = cs.trim();
			m_csKeys = m_csKeys.substring(nIndex+1);			
		}
		return cs;
	}	
	
	void prepare()
	{
		m_nKeyLength = 0;
		int nNbSegments = m_arrKeySegment.size();
		for(int n=0; n<nNbSegments; n++)
		{
			m_nKeyLength += m_arrKeySegment.get(n).getLength();
		}
		m_tbyKey = new byte[m_nKeyLength];
	}
	
	byte[] fillKeyBufferExceptRecordId(LineRead lineRead, boolean bFileInVariableLength)	//, boolean bFileInEbcdic)
	{
		return fillKeyBuffer(lineRead, 1, bFileInVariableLength);	//, bFileInEbcdic);
	}
		
	byte[] fillKeyBufferIncludingRecordId(LineRead lineRead, boolean bFileInVariableLength)	//, boolean bConvertKeyToAscii)
	{
		return fillKeyBuffer(lineRead, 0, bFileInVariableLength);	//, bConvertKeyToAscii);
	}
	
	private byte[] fillKeyBuffer(LineRead lineRead, int nNbSegmentToExclude, boolean bFileInVariableLength)	//, boolean bConvertKeyToAscii)
	{
		int nOffset = lineRead.getOffset();
//		if(bFileInVariableLength)	// exclude record header from the key
//			nOffset += 4;	// Skip record header
		byte tbyData[] = lineRead.getBuffer();

		int nNbSegments = m_arrKeySegment.size();
		for(int n=0; n<nNbSegments-nNbSegmentToExclude; n++)
		{
			BtreeKeySegment btreeKeySegment = m_arrKeySegment.get(n);
			btreeKeySegment.appendKeySegmentData(tbyData, nOffset, m_tbyKey);	//, bConvertKeyToAscii);
		}
		return m_tbyKey;
	}

	
	byte[] fillKeyBuffer(byte tbyData[], int nOffset, int nNbRecordRead, boolean bFileInVariableLength)
	{
		int nPos = 0;
		if(bFileInVariableLength)	// exclude record header from the key
			nOffset += 4;	// Skip record header
		
		int nNbSegments = m_arrKeySegment.size();
		for(int n=0; n<nNbSegments-1; n++)	// Do not append last segment = record id
		{
			BtreeKeySegment btreeKeySegment = m_arrKeySegment.get(n);
			nPos = btreeKeySegment.appendKeySegmentData(tbyData, nOffset, m_tbyKey);	//, false);
		}

		if(nPos <= m_nKeyLength-4)
			LittleEndingUnsignBinaryBufferStorage.writeInt(m_tbyKey, nNbRecordRead, nPos);	// Add the record id in Intel format
		
		return m_tbyKey;
	}
	
	byte[] fillNewKeyBuffer(byte tbyData[], int nNbRecordRead, boolean bFileInVariableLength)
	{
		int nOffset = 0;
		byte[] tbyKey = new byte[m_nKeyLength]; 
		int nPos = 0;
		if(bFileInVariableLength)	// exclude record header from the key
			nOffset += 4;	// Skip record header
		
		int nNbSegments = m_arrKeySegment.size();
		for(int n=0; n<nNbSegments-1; n++)	// Do not append last segment = record id
		{
			BtreeKeySegment btreeKeySegment = m_arrKeySegment.get(n);
			nPos = btreeKeySegment.appendKeySegmentData(tbyData, nOffset, tbyKey);	//, false);
		}

		if(nPos <= m_nKeyLength-4)
			LittleEndingUnsignBinaryBufferStorage.writeInt(tbyKey, nNbRecordRead, nPos);	// Add the record id in Intel format
		
		return tbyKey;
	}
	
	int compare(Object d1, Object d2)
	{
        byte[] tby1 = (byte[])d1;
        byte[] tby2 = (byte[])d2;
        int nNbSegments = m_arrKeySegment.size();
		for(int n=0; n<nNbSegments; n++)
		{			
			BtreeKeySegment btreeKeySegment = m_arrKeySegment.get(n);
			int nCompare = btreeKeySegment.compare(tby1, tby2);
			if(nCompare != 0)
				return nCompare; 
		}
		return 0;
	}
	
	public void setFileInEncoding(boolean bFileInEbcdic)
	{
		for(int n=0; n<m_arrKeySegment.size(); n++)
		{			
			BtreeKeySegment btreeKeySegment = m_arrKeySegment.get(n);
			btreeKeySegment.setFileInEncoding(bFileInEbcdic);
		}
	}
	
}
