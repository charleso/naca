/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 17 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import java.math.BigDecimal;


import nacaLib.bdb.BtreeSegmentKeyTypeFactory;
import nacaLib.debug.BufferSpy;
import nacaLib.sqlSupport.CSQLItemType;
import nacaLib.tempCache.CStr;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarDefX extends VarDefVariable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public VarDefX(VarDefBase varDefParent, DeclareTypeX declareTypeX)
	{
		super(varDefParent, declareTypeX.m_varLevel);
		m_nSize = declareTypeX.getLength();
		m_bJustifyRight = declareTypeX.getJustifyRight();
	}

	protected VarDefX()
	{
		super();
	}
	
	void transfer(VarBufferPos bufferSource, VarAndEdit Dest)
	{
		Dest.m_varDef.write(Dest.m_bufferPos, this, bufferSource);
	}
	

//	VarDefX(VarDefX varDefSource)
//	{
//		super(varDefSource);
//		m_nSize = varDefSource.m_nSize;
//	}
//	
//	VarDefBuffer deepDuplicate()
//	{
//		return new VarDefX(this);
//	}
//	
	protected VarDefBuffer allocCopy()
	{
		VarDefX v = new VarDefX();
		v.m_nSize = m_nSize;
		v.m_bJustifyRight = m_bJustifyRight; 
		return v;
	}
			
	CSQLItemType getSQLType()
	{
		return CSQLItemType.SQL_TYPE_STRING;
	}
	
	public int getBodyLength()
	{
		return m_nTotalSize;
	}
	
	protected int getHeaderLength()
	{
		return 0;
	}

	public int getSingleItemRequiredStorageSize()
	{
		return m_nSize;
	}
	
//	String getRawString(VarBuffer buffer)
//	{
//		String cs = buffer.getStringAt(m_nAbsolutePosition, m_nTotalSize);
//		return cs;
//	}
	
	CStr getAsDecodedString(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		return cs;
	}
	
	int getAsDecodedInt(VarBufferPos buffer)
	{
		int n = buffer.getAsUnsignedInt(buffer.m_nAbsolutePosition, m_nTotalSize);
		
//		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
//		int n = cs.getAsInt();
		//cs.resetManagerCache();
		return n;
	}
	
	int getAsDecodedUnsignedInt(VarBufferPos buffer)
	{
		int n = buffer.getAsUnsignedInt(buffer.m_nAbsolutePosition, m_nTotalSize);
		
//		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
//		int n = cs.getAsInt();
		//cs.resetManagerCache();
		return n;
	}

	long getAsDecodedLong(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		long l = cs.getAsLong();
		//cs.resetManagerCache();
		return l;
	}

	Dec getAsDecodedDec(VarBufferPos buffer)
	{
		long lInt = getAsDecodedLong(buffer);
		Dec dec = new Dec(lInt, "");
		return dec;
	}
	
	CStr getAsAlphaNumString(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		return cs;
	}	
	
	CStr getDottedSignedString(VarBufferPos buffer)
	{	
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		return cs;
	}
	
	CStr getDottedSignedStringAsSQLCol(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		return cs;
	}

	
	void write(VarBufferPos buffer, char c)
	{
		String cs = String.valueOf(c);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
	}
	
	public void write(VarBufferPos buffer, String cs)
	{
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
	}
	
	public void inc(VarBufferPos buffer, int n)
	{
//		Dec dec = getAsDecodedDec(buffer);
//		dec.inc(n);
//		write(buffer, dec);
	}
	
	public void inc(VarBufferPos buffer, BigDecimal bdStep)
	{
	}
	
	public void write(VarBufferPos buffer, int n)
	{
		if(n < 0)
			n = -n;
		String cs = String.valueOf(n);
 		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
	}
	
	public void write(VarBufferPos buffer, long l)
	{
		write(buffer, (int)l);
	}

	
	void write(VarBufferPos buffer, double d)
	{
//		assertIfFalse(false);
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}
	
	void write(VarBufferPos buffer, Dec dec)
	{
//		assertIfFalse(false);
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}
	
	public void write(VarBufferPos buffer, BigDecimal bigDecimal)
	{
		String cs = bigDecimal.toString();
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
	}
	
	
	void write(VarBufferPos buffer, VarDefG varDefSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If the sending item is a group item, and the receiving item is an elementary item, the compiler ignores the receiving item description except for the size description, in bytes, and any JUSTIFIED clause. It conducts no conversion or editing on the sending item's data
		//internalPhysicalWrite(buffer, varSource, bufferSource); 
		
		//String cs = varDefSource.getRawStringExcludingHeader(bufferSource);
		CStr cs = bufferSource.getBodyCStr(varDefSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
	}
	
	public void moveIntoSameType(VarBufferPos buffer, VarDefBuffer varDefSource, VarBufferPos bufferSource)
	{
		if(!m_bJustifyRight)	// data is structured identically
		{
			int nPosSource = bufferSource.m_nAbsolutePosition;
			int nPosDest = buffer.m_nAbsolutePosition;

			if(m_nTotalSize <= varDefSource.m_nTotalSize)	// Destination is smaller or same lengh than source
			{
				int nPositionDest = buffer.m_nAbsolutePosition;
				int nPositionSource = bufferSource.m_nAbsolutePosition;
				if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPositionDest, m_nTotalSize);
				for(int n=0; n<m_nTotalSize; n++)
				{
					buffer.m_acBuffer[nPositionDest++] = bufferSource.m_acBuffer[nPositionSource++];
				}
				if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
				return ;
//				buffer.copyBytes(nPosDest, m_nTotalSize, nPosSource, bufferSource);
//				return ;
			}
			else // Destination is longer than source
			{
				buffer.copyBytes(nPosDest, varDefSource.m_nTotalSize, nPosSource, bufferSource);
				int nNbSpacesToPadOnRight = m_nTotalSize - varDefSource.m_nTotalSize;
				nPosDest += varDefSource.m_nTotalSize;
				while(nNbSpacesToPadOnRight > 0)
				{
					if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPosDest, 1);
					buffer.m_acBuffer[nPosDest] = ' ';
					if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
					nNbSpacesToPadOnRight--;
					nPosDest++;
				}
				return ;
			}
		}
		CStr cs = bufferSource.getBodyCStr(varDefSource);
		internalWriteJustifyRightPaddingSpace(buffer, cs);
	}
	
	
	void write(VarBufferPos buffer, VarDefX varDefSource, VarBufferPos bufferSource)
	{
		if(!m_bJustifyRight)	// data is structured identically
		{
			int nPosSource = bufferSource.m_nAbsolutePosition;
			int nPosDest = buffer.m_nAbsolutePosition;

			if(m_nTotalSize <= varDefSource.m_nTotalSize)	// Destination is smaller or same lengh than source
			{
				int nPositionDest = buffer.m_nAbsolutePosition;
				int nPositionSource = bufferSource.m_nAbsolutePosition;
				if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPositionDest, m_nTotalSize);
				for(int n=0; n<m_nTotalSize; n++)
				{
					buffer.m_acBuffer[nPositionDest++] = bufferSource.m_acBuffer[nPositionSource++];
				}
				if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
				return ;
//				buffer.copyBytes(nPosDest, m_nTotalSize, nPosSource, bufferSource);
//				return ;
			}
			else // Destination is longer than source
			{
				buffer.copyBytes(nPosDest, varDefSource.m_nTotalSize, nPosSource, bufferSource);
				int nNbSpacesToPadOnRight = m_nTotalSize - varDefSource.m_nTotalSize;
				nPosDest += varDefSource.m_nTotalSize;
				while(nNbSpacesToPadOnRight > 0)
				{
					if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPosDest, 1);
					buffer.m_acBuffer[nPosDest] = ' ';
					if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
					nNbSpacesToPadOnRight--;
					nPosDest++;
				}
				return ;
			}
		}
		else
		{
			CStr cs = bufferSource.getBodyCStr(varDefSource);
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		}
			
//		CStr cs = bufferSource.getBodyCStr(varDefSource);
//		if(!m_bJustifyRight)
//			writeRightPadding(buffer, cs, ' ');
//		else
//			writeJustifyRightPadding(buffer, cs, ' ');
	}
	
	void write(VarBufferPos buffer, VarDefFPacAlphaNum varDefSource, VarBufferPos bufferSource)
	{
		//String cs = varDefSource.getRawStringExcludingHeader(bufferSource);
		CStr cs = bufferSource.getBodyCStr(varDefSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
	}
	
	void write(VarBufferPos buffer, VarDefFPacRaw varDefSource, VarBufferPos bufferSource)
	{
		//String cs = varDefSource.getRawStringExcludingHeader(bufferSource);
		CStr cs = bufferSource.getBodyCStr(varDefSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
	}
	
	void write(VarBufferPos buffer, VarDefNumDecComp0 varSource, VarBufferPos bufferSource)
	{
//		String cs = varSource.getAsAlphaNumString(bufferSource);
//		writeRightPadding(buffer, cs, ' ');

		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}
	
	void write(VarBufferPos buffer, VarDefNumDecComp3 varSource, VarBufferPos bufferSource)
	{
//		String cs = varSource.getAsAlphaNumString(bufferSource);
//		writeRightPadding(buffer, cs, ' ');

		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}
	
	void write(VarBufferPos buffer, VarDefNumDecComp4 varSource, VarBufferPos bufferSource)
	{
//		String cs = varSource.getAsAlphaNumString(bufferSource);
//		writeRightPadding(buffer, cs, ' ');
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}

	void write(VarBufferPos buffer, VarDefNumDecSignComp4 varSource, VarBufferPos bufferSource)
	{
//		String cs = varSource.getAsAlphaNumString(bufferSource);
//		writeRightPadding(buffer, cs, ' ');
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}

	void write(VarBufferPos buffer, VarDefNumDecSignComp0 varSource, VarBufferPos bufferSource)
	{
//		String cs = varSource.getAsAlphaNumString(bufferSource);
//		writeRightPadding(buffer, cs, ' ');		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp3 varSource, VarBufferPos bufferSource)
	{
//		String cs = varSource.getAsAlphaNumString(bufferSource);
//		writeRightPadding(buffer, cs, ' ');
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
//		String cs = varSource.getAsAlphaNumString(bufferSource);
//		writeRightPadding(buffer, cs, ' ');
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
//		String cs = varSource.getAsAlphaNumString(bufferSource);
//		writeRightPadding(buffer, cs, ' ');

		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp0 varSource, VarBufferPos bufferSource)
	{
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
//
//		
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}

	void write(VarBufferPos buffer, VarDefNumIntComp0Long varSource, VarBufferPos bufferSource)
	{
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
//
//		
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}

	void write(VarBufferPos buffer, VarDefNumIntComp3 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPadding(buffer, buffer.m_nAbsolutePosition, m_nTotalSize, cs, ' ');
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
		
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}

	void write(VarBufferPos buffer, VarDefNumIntComp3Long varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
		
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}

	void write(VarBufferPos buffer, VarDefNumIntComp4 varSource, VarBufferPos bufferSource)
	{
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
 		
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
	}

	void write(VarBufferPos buffer, VarDefNumIntComp4Long varSource, VarBufferPos bufferSource)
	{
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
 		
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp0 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp0Long varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
		//int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}

	void write(VarBufferPos buffer, VarDefFPacNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);		
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp3Long varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);		
		////cs.resetManagerCache();
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp4 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
 	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp4Long varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0Long varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}

	
	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}

	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0Long varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
//		int n = varSource.getUnsignedInt(bufferSource);
// 		String cs = String.valueOf(n);
// 		writeRightPadding(buffer, cs, ' ');
	}

	void write(VarBufferPos buffer, VarDefEditInMap varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);	// PJD EditInMap TO Var
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefine varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
	}	
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefineNumEdited varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
	}	
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefineNum varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);	
		//cs.resetManagerCache();
	}

	
	void write(VarBufferPos buffer, VarDefNumEdited varSource, VarBufferPos bufferSource)
	{
		// see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		CStr cs = varSource.getAsDecodedString(bufferSource); 
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, cs);
		else
			internalWriteJustifyRightPaddingSpace(buffer, cs);
		//cs.resetManagerCache();
	}
	
	
//	private int writeRightPadding(VarBufferPos buffer, int nOffset, String cs, char cPad)
//	{
//		return internalWriteRightPadding(buffer, buffer.m_nAbsolutePosition+nOffset, m_nTotalSize, cs, cPad);
//	}

	void write(VarBufferPos buffer, CobolConstantZero cst)
	{
		writeRepeatingchar(buffer, cst.getValue());
	}

	void write(VarBufferPos buffer, CobolConstantSpace cst)
	{
		writeRepeatingchar(buffer, cst.getValue());
	}

	void write(VarBufferPos buffer, CobolConstantLowValue cst)
	{
		writeRepeatingchar(buffer, cst.getValue());
	}

	void write(VarBufferPos buffer, CobolConstantHighValue cst)
	{
		writeRepeatingchar(buffer, cst.getValue());
	}
	
	void write(VarBufferPos buffer, CobolConstantZero cst, int nOffsetPosition, int nNbChar)
	{
		writeRepeatingCharUpToEnd(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantSpace cst, int nOffsetPosition, int nNbChar)
	{
		writeRepeatingCharUpToEnd(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantLowValue cst, int nOffsetPosition, int nNbChar)
	{
		writeRepeatingCharUpToEnd(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantHighValue cst, int nOffsetPosition, int nNbChar)
	{
		writeRepeatingCharUpToEnd(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, String csValue, int nOffsetPosition, int nNbChar)
	{		
		internalWriteAtOffsetPosition(buffer, csValue, nOffsetPosition, nNbChar, ' ');
	}
	
	void writeAndFill(VarBufferPos buffer, char c)
	{
		writeRepeatingchar(buffer, c);
	}
	
//	public void initialize(VarBufferPos buffer)
//	{
//		writeRightPadding(buffer, " ", ' ');
//	}
	
	public void initializeAtOffset(VarBufferPos buffer, int nOffset, InitializeCache initializeCache)
	{
		internalWriteRightPaddingBlankInitSpace(buffer, buffer.m_nAbsolutePosition+nOffset, m_nTotalSize, initializeCache);
		//writeRightPadding(buffer, nOffset, " ", ' ');
	}
	
//	void initialize(VarBufferPos buffer, String cs)
//	{
//		if(!m_bJustifyRight)
//			writeRightPadding(buffer, cs, ' ');
//		else
//			writeJustifyRightPadding(buffer, cs, ' ');
//	}
	
	void initializeAtOffset(VarBufferPos buffer, int nOffset, String cs)
	{
		if(!m_bJustifyRight)
			internalWriteRightPaddingSpace(buffer, buffer.m_nAbsolutePosition+nOffset, m_nTotalSize, cs);
		else
			internalWriteJustifyRightPadding(buffer, buffer.m_nAbsolutePosition+nOffset, m_nTotalSize, cs, ' ');
	}
	

	
//	void initialize(VarBufferPos buffer, int n)
//	{
//	}

	void initializeAtOffset(VarBufferPos buffer, int nOffset, int n)
	{
	}
	
	void initializeEdited(VarBufferPos buffer, String cs)
	{
	}

	void initializeEdited(VarBufferPos buffer, int nOffset, String cs)
	{
	}
	
	void initializeEditedAtOffset(VarBufferPos buffer, int nOffset, int nValue)
	{
	}

	void initializeEditedAtOffset(VarBufferPos buffer, int nOffset, double dValue)
	{
	}

	void initializeEdited(VarBufferPos buffer, int n)
	{
	}
	
//	public void initializeAtOffset(VarBufferPos buffer, int nOffset, InitializeCache initializeCache)
//	{
//		internalWriteRightPaddingBlankInitSpace(buffer, buffer.m_nAbsolutePosition+nOffset, m_nTotalSize, initializeCache);
//		//writeRightPadding(buffer, nOffset, " ", ' ');
//	}

	
	int compare(ComparisonMode mode, VarBufferPos bufferSource, VarAndEdit var2)
	{
		return var2.m_varDef.compare(mode, var2.m_bufferPos, this, bufferSource);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
//		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(dec1, n2);
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp3 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
//		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(dec1, n2);
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp4 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
//		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(dec1, n2);
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp4 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
//		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(dec1, n2);
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}

	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
//		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(dec1, n2);
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
//		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(dec1, n2);
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignLeadingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
//		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(dec1, n2);
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignTrailingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
//		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(dec1, n2);
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
//		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(dec1, n2);
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
//		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
//		long l2 = getUnsignedLong(buffer2);
//		return internalCompare(dec1, l2);
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp3 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
//		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(dec1, n2);
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp3Long varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
//		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
//		long l2 = getUnsignedLong(buffer2);
//		return internalCompare(dec1, l2);
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp4 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
//		int n1 =  varDefNum1.getAsDecodedInt(buffer1);
//		if(n1 < 0)
//			n1 = -n1;
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(mode, String.valueOf(n1), String.valueOf(n2));
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp4Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
//		long l1 =  varDefNum1.getAsDecodedLong(buffer1);
//		if(l1 < 0)
//			l1 = -l1;
//		long l2 = getUnsignedLong(buffer2);
//		return internalCompare(mode, String.valueOf(l1), String.valueOf(l2));
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}

	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp0 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
//		int n1 =  varDefNum1.getAsDecodedInt(buffer1);
//		if(n1 < 0)
//			n1 = -n1;
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(mode, String.valueOf(n1), String.valueOf(n2));
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
//		long l1 =  varDefNum1.getAsDecodedLong(buffer1);
//		if(l1 < 0)
//			l1 = -l1;
//		long l2 = getUnsignedLong(buffer2);
//		return internalCompare(mode, String.valueOf(l1), String.valueOf(l2));
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
//		int n1 =  varDefNum1.getAsDecodedInt(buffer1);
//		if(n1 < 0)
//			n1 = -n1;
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(mode, String.valueOf(n1), String.valueOf(n2));
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}
		
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacNumIntSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
//		int n1 =  varDefNum1.getAsDecodedInt(buffer1);
//		if(n1 < 0)
//			n1 = -n1;
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(mode, String.valueOf(n1), String.valueOf(n2));
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}
	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp3Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
//		long l1 =  varDefNum1.getAsDecodedLong(buffer1);
//		if(l1 < 0)
//			l1 = -l1;
//		long l2 = getUnsignedLong(buffer2);
//		return internalCompare(mode, String.valueOf(l1), String.valueOf(l2));
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}


	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp4 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
//		int n1 =  varDefNum1.getAsDecodedInt(buffer1);
//		if(n1 < 0)
//			n1 = -n1;	
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(mode, String.valueOf(n1), String.valueOf(n2));
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}	

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp4Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
//		long l1 =  varDefNum1.getAsDecodedLong(buffer1);
//		if(l1 < 0)
//			l1 = -l1;
//		long l2 = getUnsignedLong(buffer2);
//		return internalCompare(mode, String.valueOf(l1), String.valueOf(l2));
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}	

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignLeadingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
//		int n1 =  varDefNum1.getAsDecodedInt(buffer1);
//		if(n1 < 0)
//			n1 = -n1;
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(mode, String.valueOf(n1), String.valueOf(n2));
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignLeadingComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
//		long l1 =  varDefNum1.getAsDecodedLong(buffer1);
//		if(l1 < 0)
//			l1 = -l1;
//		long l2 = getUnsignedLong(buffer2);
//		return internalCompare(mode, String.valueOf(l1), String.valueOf(l2));
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}	

	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
//		int n1 =  varDefNum1.getAsDecodedInt(buffer1);
//		if(n1 < 0)
//			n1 = -n1;
//		int n2 = getUnsignedInt(buffer2);
//		return internalCompare(mode, String.valueOf(n1), String.valueOf(n2));
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
//		long l1 =  varDefNum1.getAsDecodedLong(buffer1);
//		if(l1 < 0)
//			l1 = -l1;
//		long l2 = getUnsignedLong(buffer2);
//		return internalCompare(mode, String.valueOf(l1), String.valueOf(l2));
		return -varDefNum1.compare(mode, buffer1, this, buffer2);
	}	
	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefX varDef1, VarBufferPos buffer1)
	{
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		//cs1.selfTrimLeftRight();

		CStr cs2 = buffer2.getBodyCStr(this);
		//cs2.selfTrimLeftRight();
		
		int n = internalCompare(mode, cs1, cs2);
		return n;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacAlphaNum varDef1, VarBufferPos buffer1)
	{
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		//cs1.selfTrimLeftRight();

		CStr cs2 = buffer2.getBodyCStr(this);
		//cs2.selfTrimLeftRight();
		
		return internalCompare(mode, cs1, cs2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacRaw varDef1, VarBufferPos buffer1)
	{
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		//cs1.selfTrimLeftRight();

		CStr cs2 = buffer2.getBodyCStr(this);
		//cs2.selfTrimLeftRight();
		
		return internalCompare(mode, cs1, cs2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefG varDef1, VarBufferPos buffer1)
	{
//		String cs1 = varDef1.getRawStringExcludingHeader(buffer1);
//		String cs2 = getRawStringExcludingHeader(buffer2);
		
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		CStr cs2 = buffer2.getBodyCStr(this);
		int n = internalCompare(mode, cs1, cs2);
		return n;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumEdited varDef1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		// TODO how to compare with num edited ?
		return 0;
	}
	
	
	boolean isNumeric(VarBufferPos buffer)
	{
		CStr cs = buffer.getBodyCStr(this);
		boolean b = cs.isOnlyNumericPicX();
		return b;
	}
	
	public boolean isAlphabetic(VarBufferPos buffer)
	{
		return internalIsRawStringAlphabetic(buffer);
	}
	
	boolean isTypedLongVarCharText()
	{
		return true;
	}
	
	public String digits(VarBufferPos buffer)
	{
		return getAsAlphaNumString(buffer).getAsString();
	}
	
	boolean isConvertibleInEbcdic()
	{
		return true;
	}
	
	public int getTypeId()
	{
		return VarTypeId.VarDefXTypeId;
	}

	public boolean isEbcdicAsciiConvertible()
	{
		return true;
	}
	
	public BtreeSegmentKeyTypeFactory getSegmentKeyTypeFactory()
	{
		return VarTypeId.m_segmentKeyTypeFactoryString;
	}
	
	protected void adjustCustomProperty(VarDefBuffer varDefBufferCopySingleItem)
	{
		VarDefX varDefCopy = (VarDefX)varDefBufferCopySingleItem;
		varDefCopy.m_nSize = m_nSize;
		varDefCopy.m_bJustifyRight = m_bJustifyRight;
	}
	
	protected void adjustCustomPropertyForCharGetAt(VarDefBuffer varDefBufferCopySingleItem)
	{
		VarDefX varDefCopy = (VarDefX)varDefBufferCopySingleItem;
		varDefCopy.m_nSize = 1;
		varDefCopy.m_bJustifyRight = m_bJustifyRight;
	}	
	
	private void internalWriteRightPaddingSpace(VarBufferPos buffer, String cs)
	{
		int nPosition = buffer.m_nAbsolutePosition;
		int nLength = 0;
		if(cs != null)
		{
			nLength = cs.length();
			if(m_nTotalSize < nLength)
				nLength = m_nTotalSize;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPosition, nLength);
			cs.getChars(0, nLength, buffer.m_acBuffer, nPosition);
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nPosition += nLength;
		}
		if(nLength < m_nTotalSize)	// Padding with spaces on the right
		{
			int nNbChars = m_nTotalSize-nLength;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPosition, nNbChars);
			for(int n=0; n<nNbChars; n++)
				buffer.m_acBuffer[nPosition++] = ' ';
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		}
	}	
	
	private void internalWriteRightPaddingSpace(VarBufferPos buffer, CStr cs)
	{
		int nPosition = buffer.m_nAbsolutePosition;
		int nLength = 0;
		if(cs != null)
		{
			nLength = Math.min(cs.length(), m_nTotalSize);
			nPosition = buffer.setStringAt(nPosition, cs, nLength);
		}
		if(nLength < m_nTotalSize)	// Padding with BLANK on the right
		{
			int nNbChars = m_nTotalSize-nLength;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPosition, nNbChars);
			for(int n=0; n<nNbChars; n++)
				buffer.m_acBuffer[nPosition++] = ' ';
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		}
	}
	
	private void internalWriteJustifyRightPaddingSpace(VarBufferPos buffer, String cs)
	{
		int nPosition = buffer.m_nAbsolutePosition;
		if(cs != null)
		{
			int nSourceLength = cs.length();
			if(nSourceLength > m_nTotalSize)					// truncate leftmosts char of source
			{
				cs = cs.substring(nSourceLength - m_nTotalSize);
				nSourceLength = m_nTotalSize;
			}
			int nPosStart = m_nTotalSize - nSourceLength;
			buffer.setStringAt(nPosition+nPosStart, cs, nSourceLength);
			nPosition = buffer.writeRepeatingCharAt(nPosition, ' ', nPosStart);
		}
	}
	
	private void internalWriteJustifyRightPaddingSpace(VarBufferPos buffer, CStr cs)
	{
		int nPosition = m_nDefaultAbsolutePosition;
		if(cs != null)
		{
			int nSourceLength = cs.length();
			if(nSourceLength > m_nTotalSize)					// truncate leftmosts char of source
			{
				cs.selfSubstring(nSourceLength - m_nTotalSize);
				nSourceLength = m_nTotalSize;
			}
			int nPosStart = m_nTotalSize - nSourceLength;
			buffer.setStringAt(nPosition+nPosStart, cs, nSourceLength);
			nPosition = buffer.writeRepeatingCharAt(nPosition, ' ', nPosStart);
		}
	}
	
	private int m_nSize = 0;
	private boolean m_bJustifyRight = false;
}

