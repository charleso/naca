/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 27 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import java.math.BigDecimal;


import nacaLib.bdb.BtreeSegmentKeyTypeFactory;
import nacaLib.mathSupport.MathAdd;
import nacaLib.misc.StringAsciiEbcdicUtil;
import nacaLib.sqlSupport.CSQLItemType;
import nacaLib.tempCache.CStr;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarDefEditInMapRedefine extends VarDefEditInMapRedefineBase
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VarDefEditInMapRedefine(VarDefBase varDefParent, DeclareTypeEditInMapRedefine declareTypeEditInMapRedefine)
	{
		super(varDefParent, declareTypeEditInMapRedefine.m_varLevel);
	}
	
	void assignForm(VarDefForm varDefForm)
	{
		if(getId() == 272)
		{
			int n = 0;
		}
		m_varDefFormRedefineOrigin = varDefForm;
		if(m_varDefFormRedefineOrigin == null)
		{
			int n = 0; 
		}
		m_varDefEditOrigin = m_varDefFormRedefineOrigin.getChildAtDefaultPosition(m_nDefaultAbsolutePosition);
	}
	
	VarDefBuffer getVarDefEditInMapOrigin()
	{
		return m_varDefEditOrigin;
	}
	
	public VarDefEditInMapRedefine()
	{
		super();
	}
	
	CSQLItemType getSQLType()
	{
		return CSQLItemType.SQL_TYPE_STRING;
	}
		
	void transfer(VarBufferPos bufferSource, VarAndEdit Dest)
	{
//		boolean bMoveTextOnly = true;
//		boolean bDestIsEditInMapRedefine = Dest.m_varDef.isEditInMapRedefine();
//		if(bDestIsEditInMapRedefine)
//		{
//			if(m_arrChildren != null)
//			{
//				for(int n=0; n<m_arrChildren.size(); n++)
//				{
//					VarDefBuffer varDefChild = (VarDefBuffer)m_arrChildren.get(n);
//					if(!varDefChild.isVarInMapRedefine())	// Sub-Edit
//					{
//						bMoveTextOnly = false;
//						break;
//					}
//				}
//			}
//		}
//		if(bMoveTextOnly)
			Dest.m_varDef.write(Dest.m_bufferPos, this, bufferSource);
//		else
//		{		
			// Sauver tous les attributs des edits dans le buffer
			// transferer le buffer source dans le buffer destination
			// Recharger tous les attributs des edits avec le buffer destination 
//			
//			BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
//			
//			VarDefEditInMapRedefine destVarDef = (VarDefEditInMapRedefine)Dest.m_varDef;
//			destVarDef.writeSubEditAndAttributes(Dest.m_bufferPos, this, bufferSource);
//
//			// Enum all children to transfer their attributes
//			int nPosAttributes = Dest.getBodyAbsolutePosition() - Dest.getVarDef().getHeaderLength();	// Position of the attributes in the buffer 
//			for(int n=0; n<m_arrChildren.size(); n++)
//			{
//				EditInMapRedefine editDestChildTemplate = (EditInMapRedefine)Dest.getEditChildAt(n+1);
//				EditInMapRedefine subItem = editDestChildTemplate.allocOccursedItem(editDestChildTemplate.getVarDef());
//
//				subItem.m_varDef.adjustSetting(editDestChildTemplate.getVarDef(), Dest.getBodyAbsolutePosition()-7, 0, 1, null);
//				
//				int nBodyLength = editDestChildTemplate.getBodyLength();
//				nPosAttributes += Dest.getVarDef().getHeaderLength() + nBodyLength; 
//			}
//
//		}			
	}

	void write(VarBufferPos buffer, VarDefEditInMap varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		write(buffer, cs);
	}
	
//	void writeSubEditAndAttributes(VarBufferPos bufferDest, VarDefEditInMapRedefine varSource, VarBufferPos bufferSource)
//	{
//		CStr cs = bufferSource.getStringAt(bufferSource.m_nAbsolutePosition, m_nTotalSize);
//		writeEditAndSubEditRightPadding(bufferDest, cs, ' ');
//	}
	
	public void moveIntoSameType(VarBufferPos buffer, VarDefBuffer varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		write(buffer, cs);
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefine varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		write(buffer, cs);
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefineNumEdited varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		write(buffer, cs);
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefineNum varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getUnsignedInt(bufferSource);
		write(buffer, n);
	}
			
	protected VarDefBuffer allocCopy()
	{
		VarDefEditInMapRedefine v = new VarDefEditInMapRedefine();
		v.m_bJustifyRight = m_bJustifyRight; 
		return v;
	}
	
		
	
	CStr getAsDecodedString(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
		return cs;
	}
	
	CStr getAsAlphaNumString(VarBufferPos buffer)
	{		
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		return cs;		
	}

	
	int getAsDecodedInt(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
		int n = cs.getAsInt();
		return n;
	}
	
	int getAsDecodedUnsignedInt(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
		int n = cs.getAsUnsignedInt();
		return n;
	}

			
	long getAsDecodedLong(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
		long l = cs.getAsLong();
		return l;
	}
	
	Dec getAsDecodedDec(VarBufferPos buffer)
	{
		long lInt = getAsDecodedLong(buffer);
		Dec dec = new Dec(lInt, "");
		return dec;
	}
	
	CStr getDottedSignedString(VarBufferPos buffer)
	{	
		CStr cs = buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
		return cs;
	}
	
	CStr getDottedSignedStringAsSQLCol(VarBufferPos buffer)
	{	
		return buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
	}

	
	void write(VarBufferPos buffer, char c)
	{
		String cs = String.valueOf(c);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
	}
	
	public void write(VarBufferPos buffer, String cs)
	{
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
	}
	
	void write(VarBufferPos buffer, CStr cs)
	{
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
	}
	
	public void inc(VarBufferPos buffer, int n)
	{
		int nVal = getAsDecodedInt(buffer);
		nVal += n;
		write(buffer, nVal);
	}
	
	public void inc(VarBufferPos buffer, BigDecimal bdStep)
	{
		CStr s1 = getDottedSignedString(buffer);
		Dec dec = MathAdd.inc(s1, bdStep);
		write(buffer, dec);
	}
		
	public void write(VarBufferPos buffer, int n)
	{
		if(n < 0)
			n = -n;
		String cs = String.valueOf(n);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');

	}
	
	public void write(VarBufferPos buffer, long l)
	{
		if(l < 0)
			l = -l;
		String cs = String.valueOf(l);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');

	}
	
	void write(VarBufferPos buffer, double d)
	{
		assertIfFalse(false);
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}
	
	void write(VarBufferPos buffer, Dec dec)
	{
		assertIfFalse(false);
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item.
	}
	
	public void write(VarBufferPos buffer, BigDecimal bigDecimal)
	{
		assertIfFalse(false);
	}	
	
	void write(VarBufferPos buffer, VarDefG varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If the sending item is a group item, and the receiving item is an elementary item, the compiler ignores the receiving item description except for the size description, in bytes, and any JUSTIFIED clause. It conducts no conversion or editing on the sending item's data
		internalEditPhysicalWrite(buffer, varSource, bufferSource); 
	}
		
	
	void write(VarBufferPos buffer, VarDefX varDefSource, VarBufferPos bufferSource)
	{
		CStr cs = bufferSource.getBodyCStr(varDefSource);
		//String cs = varDefSource.getRawStringExcludingHeader(bufferSource);
		
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
	}
	
	void write(VarBufferPos buffer, VarDefFPacAlphaNum varDefSource, VarBufferPos bufferSource)
	{
		CStr cs = bufferSource.getBodyCStr(varDefSource);
		
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
	}
	
	void write(VarBufferPos buffer, VarDefFPacRaw varDefSource, VarBufferPos bufferSource)
	{
		CStr cs = bufferSource.getBodyCStr(varDefSource);
		
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
	}
	
	void write(VarBufferPos buffer, VarDefNumDecComp0 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}
	
	void write(VarBufferPos buffer, VarDefNumDecComp3 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}

	
	void write(VarBufferPos buffer, VarDefNumDecComp4 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp4 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}

	
	void write(VarBufferPos buffer, VarDefNumDecSignComp0 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp3 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// Parag 9.c: A noninteger numeric literal or data item cannot be moved to an alphanumeric or alphanumeric edited data item. 
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp0 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		//cs.resetManagerCache();
	}

	void write(VarBufferPos buffer, VarDefNumIntComp0Long varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		//cs.resetManagerCache();
	}

	void write(VarBufferPos buffer, VarDefNumIntComp3 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		//cs.resetManagerCache();
	}	
	
	void write(VarBufferPos buffer, VarDefNumIntComp3Long varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
	}	
	
	void write(VarBufferPos buffer, VarDefNumIntComp4 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp4Long varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp0 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp0Long varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
	}
	
	void write(VarBufferPos buffer, VarDefFPacNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		//cs.resetManagerCache();
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters).
		
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp3Long varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		//cs.resetManagerCache();
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
	}	
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp4 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		//cs.resetManagerCache();
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
 	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp4Long varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		//cs.resetManagerCache();
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
 	}

	
	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
	}

	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0Long varSource, VarBufferPos bufferSource)
	{
 		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
	}

	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0Long varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsAlphaNumString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
		// @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
		// parag 11: When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
		// If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
		// The sign character is not moved. 
		// The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 
	}
	
	private int writeEditRightPadding(VarBufferPos buffer, CStr cs, char cPad)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		return internalWriteRightPadding(buffer, nBodyPosStart, nBodyLength, cs, cPad);
	}
	
//	private int writeEditAndSubEditRightPadding(VarBufferPos buffer, CStr cs, char cPad)
//	{
//		int nBodyPosStart = buffer.m_nAbsolutePosition;
//		int nBodyLength = m_nTotalSize;
//		return internalWriteRightPadding(buffer, nBodyPosStart, nBodyLength, cs, cPad);
//	}
	
	private int writeEditRightPadding(VarBufferPos buffer, String cs, char cPad)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		return internalWriteRightPadding(buffer, nBodyPosStart, nBodyLength, cs, cPad);
	}
	
	private void writeEditJustifyRightPadding(VarBufferPos buffer, CStr cs, char cPad)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		internalWriteJustifyRightPadding(buffer, nBodyPosStart, nBodyLength, cs, cPad);
	}
	
	private void writeEditJustifyRightPadding(VarBufferPos buffer, String cs, char cPad)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		internalWriteJustifyRightPadding(buffer, nBodyPosStart, nBodyLength, cs, cPad);
	}
	

	protected int writeEditRepeatingchar(VarBufferPos buffer, char c)
	{
		return buffer.writeRepeatingCharAt(buffer.m_nAbsolutePosition+getHeaderLength(), c, m_nTotalSize-getHeaderLength());
	}	
	
	protected int writeEditRepeatingchar(VarBufferPos buffer, char c, int nOffset, int nNbChars)
	{
		int nMaxCharOnRight = m_nTotalSize - getHeaderLength() - nOffset;
		int nNbCharsToWrite = Math.min(nMaxCharOnRight, nNbChars);
		return buffer.writeRepeatingCharAt(buffer.m_nAbsolutePosition+nOffset+getHeaderLength(), c, nNbCharsToWrite);
	}	
	
	void write(VarBufferPos buffer, VarDefNumEdited varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		if(!m_bJustifyRight)
			writeEditRightPadding(buffer, cs, ' ');
		else
			writeEditJustifyRightPadding(buffer, cs, ' ');
	}
	
	
	void write(VarBufferPos buffer, CobolConstantZero cst)
	{
		writeEditRepeatingchar(buffer, cst.getValue());
	}

	void write(VarBufferPos buffer, CobolConstantSpace cst)
	{
		writeEditRepeatingchar(buffer, cst.getValue());
	}

	void write(VarBufferPos buffer, CobolConstantLowValue cst)
	{
		writeEditRepeatingchar(buffer, cst.getValue());
	}

	void write(VarBufferPos buffer, CobolConstantHighValue cst)
	{
		writeEditRepeatingchar(buffer, cst.getValue());
	}
	
	void write(VarBufferPos buffer, CobolConstantZero cst, int nOffsetPosition, int nNbChar)
	{
		writeEditRepeatingchar(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantSpace cst, int nOffsetPosition, int nNbChar)
	{
		writeEditRepeatingchar(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantLowValue cst, int nOffsetPosition, int nNbChar)
	{
		writeEditRepeatingchar(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantHighValue cst, int nOffsetPosition, int nNbChar)
	{
		writeEditRepeatingchar(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, String csValue, int nOffsetPosition, int nNbChar)
	{		
		internalWriteAtOffsetPosition(buffer, csValue, nOffsetPosition, nNbChar, ' ');
	}
	
	void writeAndFill(VarBufferPos buffer, char c)
	{
		writeEditRepeatingchar(buffer, c);
	}
	
//	public void initialize(VarBufferPos buffer)
//	{
//		writeEditRightPadding(buffer, " ", ' ');
//	}

	public void initializeAtOffset(VarBufferPos buffer, int nOffset, InitializeCache initializeCache)
	{
		writeEditRightPaddingBlankInit(buffer, nOffset, initializeCache);
	}

//	void initialize(VarBufferPos buffer, String cs)
//	{
//		assertIfFalse(false) ;
//		//writeEditRightPadding(buffer, cs, ' ');
//	}
//	
//	void initialize(VarBufferPos buffer, int n)
//	{
//		assertIfFalse(false);
//	}
	
	void initializeAtOffset(VarBufferPos buffer, int nOffset, int n)
	{
	}
	
	
	void initializeEdited(VarBufferPos buffer, String cs)
	{
		assertIfFalse(false);
	}
	
	void initializeAtOffset(VarBufferPos buffer, int nOffset, String cs)
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
		assertIfFalse(false);
	}
	
	int compare(ComparisonMode mode, VarBufferPos bufferSource, VarAndEdit var2)
	{
		// return var2.m_varDef.compare(var2.m_buffer, this, bufferSource);
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp3 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}

	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp4 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp4 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignLeadingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignTrailingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		long l1 = varDefNum1.getAsDecodedLong(buffer1);
		long l2 = getAsDecodedLong(buffer2);
		return internalCompare(l1, l2);
	}

	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp3 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp3Long varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		long l1 = varDefNum1.getAsDecodedLong(buffer1);
		long l2 = getAsDecodedLong(buffer2);
		return internalCompare(l1, l2);
	}

	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp4 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp4Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is a group item, the compiler treats the numeric operand as if it had been moved into a group item the same size as the numeric operand and then compared. This is equivalent to a group move. 
		// The compiler ignores the description of the numeric item (except for length) and, therefore, includes in its length any operational sign, whether carried as a separate character or as an overpunched character. Overpunched characters are never ASCII numeric digits. They are characters ranging from A to R, left brace ({), or right brace (}). Thus, the sign and the digits, stored as ASCII bytes, take part in the comparison, and zeros are not supplied for P characters in the PICTURE character-string. 
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		long l2 = getAsDecodedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}
	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp0 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		long l2 = getUnsignedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacNumIntSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp3Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		long l2 = getUnsignedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp4 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp4Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		long l2 = getUnsignedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignLeadingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignLeadingComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		long l2 = getUnsignedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		long l2 = getUnsignedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefX varDef1, VarBufferPos buffer1)
	{
//		String cs1 = varDef1.getRawStringExcludingHeader(buffer1);
//		String cs2 = getRawStringExcludingHeader(buffer2);
		
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		CStr cs2 = buffer2.getBodyCStr(this);
		return internalCompare(mode, cs1, cs2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacAlphaNum varDef1, VarBufferPos buffer1)
	{
//		String cs1 = varDef1.getRawStringExcludingHeader(buffer1);
//		String cs2 = getRawStringExcludingHeader(buffer2);
		
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		CStr cs2 = buffer2.getBodyCStr(this);		
		return internalCompare(mode, cs1, cs2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacRaw varDef1, VarBufferPos buffer1)
	{
//		String cs1 = varDef1.getRawStringExcludingHeader(buffer1);
//		String cs2 = getRawStringExcludingHeader(buffer2);
		
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		CStr cs2 = buffer2.getBodyCStr(this);		
		return internalCompare(mode, cs1, cs2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefG varDef1, VarBufferPos buffer1)
	{
//		CStr cs1 = buffer1.getBodyCStr(varDef1);
//		String cs2 = getRawStringExcludingHeader(buffer2);
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		CStr cs2 = buffer2.getBodyCStr(this);
		return internalCompare(mode, cs1, cs2);
	}
			
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumEdited varDef1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		// TODO how to compare with num edited ?
		return 0;
	}
	
	boolean isNumeric(VarBufferPos buffer)
	{
		return internalIsRawStringNumeric(buffer);
	}
	
	public boolean isAlphabetic(VarBufferPos buffer)
	{
		return internalIsRawStringAlphabetic(buffer);
	}
	
	public String digits(VarBufferPos buffer)
	{
		return getAsAlphaNumString(buffer).getAsString();
	}
	
	boolean isConvertibleInEbcdic()
	{
		return true;
	}
	
	private void internalEditPhysicalWrite(VarBufferPos bufferDest, VarDefBuffer varSource, VarBufferPos bufferSource)
	{
		int nNbCharSource = varSource.getBodyLength();
		int nNbCharDest = getBodyLength();
		int nNbCharToCopy = Math.min(nNbCharSource, nNbCharDest);
		bufferDest.copyBytesFromSource(getBodyAbsolutePosition(bufferDest), bufferSource, varSource.getBodyAbsolutePosition(bufferSource), nNbCharToCopy);
		
		// Add spaces on right
		int nPosDest = getBodyAbsolutePosition(bufferDest) + nNbCharSource;
		while(nNbCharSource < nNbCharDest)
		{
			bufferDest.m_acBuffer[nPosDest] = ' ';
			//bufferDest.setCharAt(nPosDest, ' ');
			nNbCharSource++;
			nPosDest++;
		}
	}
	
	public int getTypeId()
	{
		return VarTypeId.VarDefEditInMapRedefineTypeId;
	}
	
	public BtreeSegmentKeyTypeFactory getSegmentKeyTypeFactory()
	{
		return VarTypeId.m_segmentKeyTypeFactoryString;
	}	

	public boolean isEbcdicAsciiConvertible()
	{
		return false;
	}
	
	protected void adjustCustomProperty(VarDefBuffer varDefBufferCopySingleItem)
	{
		super.adjustCustomProperty(varDefBufferCopySingleItem);
	}

	protected void adjustCustomPropertyForCharGetAt(VarDefBuffer varDefBufferCopySingleItem)
	{
		super.adjustCustomProperty(varDefBufferCopySingleItem);
	}
}
