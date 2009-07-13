/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.varEx;

import nacaLib.bdb.*;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: VarTypeId.java,v 1.6 2007/01/23 14:50:41 u930di Exp $
 */
public class VarTypeId
{
	// Vars
	public static final int VarDefUnknownTypeId = -1;
	public static final int VarDefXTypeId = 0;
	public static final int VarDefGTypeId = 1;
	public static final int VarDefNumIntComp0TypeId = 2;
	public static final int VarDefNumIntComp0LongTypeId = 3;
	public static final int VarDefNumIntComp3TypeId = 4;
	public static final int VarDefNumIntComp3LongTypeId = 5;
	public static final int VarDefNumIntComp4TypeId = 6;
	public static final int VarDefNumIntComp4LongTypeId = 7;
	public static final int VarDefNumIntSignComp0TypeId = 8;
	public static final int VarDefNumIntSignComp0LongTypeId = 9;
	public static final int VarDefNumIntSignComp3TypeId = 10;
	public static final int VarDefNumIntSignComp3LongTypeId = 11;
	public static final int VarDefNumIntSignComp4TypeId = 12;
	public static final int VarDefNumIntSignComp4LongTypeId = 13;
	public static final int VarDefNumIntSignLeadingComp0TypeId = 14;
	public static final int VarDefNumIntSignLeadingComp0LongTypeId = 15;
	public static final int VarDefNumIntSignTralingComp0TypeId = 16;
	public static final int VarDefNumIntSignTralingComp0LongTypeId = 17;
	public static final int VarDefNumDecComp0 = 18;
	public static final int VarDefNumDecComp3 = 19;
	public static final int VarDefNumDecComp4 = 20;
	public static final int VarDefNumDecSignComp0 = 21;
	public static final int VarDefNumDecSignComp3 = 22;
	public static final int VarDefNumDecSignComp4 = 23;
	public static final int VarDefNumDecSignTrailingComp0 = 24;
	public static final int VarDefNumDecSignLeadingComp0 = 25;
	public static final int MaxStandardCobolVarId = 25; 
	
	public static final int VarDefNumEditedTypeId = 26;
	public static final int VarDefFPacAplhaNum = 27;
	public static final int VarDefFPacRaw = 28;
	public static final int VarDefInternalBool = 29;
	public static final int VarDefInternalInt = 30;
	public static final int VarDefFormTypeId = 31;
	public static final int VarDefMapRedefine = 32;

	// Edits
	public static final int EditMinTypeId = 33;
	public static final int VarDefEditInMapTypeId = 34;  
	public static final int VarDefEditInMapRedefineTypeId = 35;
	public static final int VarDefEditInMapRedefineNumTypeId = 36;
	public static final int VarDefEditInMapRedefineNumEditedTypeId = 37;
	
	public static final int NbTotalVarEditTypes = 38;
	
	
	// SegmentKey type
	public final static BtreeSegmentKeyTypeFactory m_segmentKeyTypeFactoryString = new SegmentKeyTypeFactoryAlphaNum();
	public final static BtreeSegmentKeyTypeFactory m_segmentKeyTypeFactoryComp0 = new SegmentKeyTypeFactoryComp0();
	public final static BtreeSegmentKeyTypeFactory m_segmentKeyTypeFactoryComp3 = new SegmentKeyTypeFactoryComp3();
	public final static BtreeSegmentKeyTypeFactory m_segmentKeyTypeFactorySignComp4 = new SegmentKeyTypeFactorySignBinary();
	public final static BtreeSegmentKeyTypeFactory m_segmentKeyTypeFactoryUnsignComp4 = new SegmentKeyTypeFactoryUnsignBinary();

}
