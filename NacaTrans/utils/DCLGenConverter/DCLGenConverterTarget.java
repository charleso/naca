/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaTrans - Naca Transcoder v1.2.0.beta.1
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils.DCLGenConverter;

import jlib.misc.EndOfLine;
import jlib.misc.FileSystem;
import jlib.misc.IntegerRef;
import parser.Cobol.elements.SQL.CSQLTableColDescriptor;
import semantic.SQL.CEntitySQLDeclareTable;

public class DCLGenConverterTarget
{
	// Currently, only Oracle is supported
	public static DCLGenConverterTarget DB2 = new DCLGenConverterTarget("DB2");
	public static DCLGenConverterTarget Oracle = new DCLGenConverterTarget("Oracle");
		
	private String m_csTargetName;
	
	private DCLGenConverterTarget(String csName)
	{
		m_csTargetName = csName;
	}
	
	public static DCLGenConverterTarget getTarget(String csName)
	{
		if(csName.equalsIgnoreCase(Oracle.m_csTargetName))
			return Oracle;
		return null;
	}
	
	void generate(CEntitySQLDeclareTable declareTable, String csOutputPath)
	{		
		// Generate Header
		StringBuilder sb = new StringBuilder("-- Create table " + declareTable.GetName());
		sb.append(EndOfLine.CRLF);
		sb.append(EndOfLine.CRLF);
		sb.append("-- Start of DB2 Script:");
		sb.append(EndOfLine.CRLF);
		sb.append("-- EXEC SQL DECLARE " + declareTable.GetName() + " TABLE");
		sb.append(EndOfLine.CRLF);
		sb.append("-- (");
		sb.append(EndOfLine.CRLF);
		StringBuilder sbColumns = generateRows("--    ", declareTable, DB2);
		sb.append(sbColumns);		
		sb.append("-- ) END-EXEC.");
		sb.append(EndOfLine.CRLF);
		sb.append("-- End of DB2 Script:");
		sb.append(EndOfLine.CRLF);
		sb.append(EndOfLine.CRLF);
		
		sb.append("-- Start of " + m_csTargetName + " Script; Original DB2 column type is in comment:");
		sb.append(EndOfLine.CRLF);
		// Generate for Target
		sb.append("CREATE TABLE ");
		sb.append(declareTable.GetTableName());
		sb.append(EndOfLine.CRLF);
		sb.append("(" + EndOfLine.CRLF);
				
		sbColumns = generateRows("    ", declareTable, this);
		sb.append(sbColumns);		
		sb.append(")");
		sb.append(EndOfLine.CRLF);
		sb.append("-- End of " + m_csTargetName + " Script:");
		sb.append(EndOfLine.CRLF);
		
		String csFileName = FileSystem.appendFilePath(csOutputPath, declareTable.GetTableName());
		csFileName += ".sql";
		FileSystem.writeFile(csFileName, sb.toString());
	}
	
	private StringBuilder generateRows(String csPrexif, CEntitySQLDeclareTable declareTable, DCLGenConverterTarget target)
	{
		StringBuilder sbColumns = new StringBuilder();
		IntegerRef nNameWidth = new IntegerRef();
		IntegerRef nTypeWidth = new IntegerRef();
		int nNbCols = declareTable.GetNbCols();
		for(int n=0; n<nNbCols; n++)
		{
			CSQLTableColDescriptor colDesc = declareTable.getColumDescriptionAtIndex(n);
			colDesc.prepareExport(this, nNameWidth, nTypeWidth);
		}

		
		boolean bAddOriginalType = true;
		if(target == DB2)
			bAddOriginalType = false;
		boolean bSetComma = true;

		for(int n=0; n<nNbCols; n++)
		{
			CSQLTableColDescriptor colDesc = declareTable.getColumDescriptionAtIndex(n);
			StringBuilder sbCol = new StringBuilder(csPrexif); 
			
			if(n == nNbCols-1)
				bSetComma = false;
			sbCol.append(colDesc.getColumnCreationOrder(target, nNameWidth.get(), nTypeWidth.get(), bSetComma, bAddOriginalType));
			sbCol.append(EndOfLine.CRLF);

			sbColumns.append(sbCol);
		}
		return sbColumns;
	}
	
	public boolean isOracle()
	{
		if(Oracle == this)
			return true;
		return false;
	}
	
	public boolean isDB2()
	{
		if(DB2 == this)
			return true;
		return false;
	}
}
