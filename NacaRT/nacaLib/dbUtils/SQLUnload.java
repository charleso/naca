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
/**
 * 
 */
package nacaLib.dbUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jlib.log.Log;
import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.BaseDataFile;
import jlib.misc.DBIOAccounting;
import jlib.misc.DBIOAccountingType;
import jlib.misc.JVMReturnCodeManager;
import jlib.misc.StringUtil;
import jlib.sql.BaseDbColDefinition;
import jlib.sql.BaseDbColDefinitionFactory;
import jlib.sql.DbConnectionBase;
import jlib.sql.DbPreparedStatement;
import jlib.sql.LogSQLException;
import jlib.sql.SQLTypeOperation;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.varEx.FileDescriptor;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class SQLUnload extends BaseSQLUtils
{
	private int m_nNbSelectProcessed = 0;
	private boolean m_bConnectionValid = false;
	private boolean m_bExcel = false;
	
	public SQLUnload(BaseSession session, DbConnectionBase dbConnection, boolean bExcel)
	{
		super(session, dbConnection);
		m_bExcel = bExcel;
	}
	
	public boolean execute(FileDescriptor fileIn)
	{
		FileSysinReader fileSysinReader = new FileSysinReader(getSession());

		boolean bExecuted = fileSysinReader.parse(this, fileIn);
		return bExecuted;
	}
		
	int executeStatement(String csClause)
	{
		m_bConnectionValid = true;
		int nNbRecords = -1;
		
		SQLTypeOperation typeOperation = SQLTypeOperation.determineOperationType(csClause, false);	// cursor clause not supported
		if(typeOperation == null)
			return -1;	// Do not manage this order

		if(typeOperation.equals(SQLTypeOperation.Commit))
		{
			if(m_dbConnection.commit() == 0)	// success
				return 0;
			return -1;	// failure
		}
		
		if(!typeOperation.equals(SQLTypeOperation.Select))
			return -1;	// Do not manage this order
		
		String csSysrecName;
		if (m_bExcel)
			csSysrecName = "UNLOAD";
		else
			csSysrecName = getSysrecName(m_nNbSelectProcessed);
		
		FileDescriptor fileDescOuput = new FileDescriptor(csSysrecName);
		fileDescOuput.setSession(getSession());
		fileDescOuput.openOutput();
		boolean bEbcdicOutput = fileDescOuput.isEbcdic();
		BaseDataFile fileOuput = fileDescOuput.getDataFile();

		// Remove ending ';' as it is not supported by UDB
		if(csClause.endsWith(";"))
			csClause = csClause.substring(0, csClause.length()-1);
		
		csClause = SQLTypeOperation.addEnvironmentPrefix(m_dbConnection.getEnvironmentPrefix(), csClause, typeOperation, "");
		DBIOAccounting.startDBIO(DBIOAccountingType.Prepare);
		DbPreparedStatement stmt = m_dbConnection.prepareStatement(csClause, 0, false);
		DBIOAccounting.endDBIO();
		if(stmt != null)
		{
			if(typeOperation == SQLTypeOperation.Select)
			{
				ResultSet rs = stmt.executeSelect();
				if(rs != null)
				{
					nNbRecords = unloadRecords(rs, csClause, bEbcdicOutput, fileOuput);
				}
			}
		}

		if(fileOuput != null)
		{
			fileOuput.close();
		}
		
		m_nNbSelectProcessed++;
		
		if(m_bConnectionValid)
			return nNbRecords;
		return -1;
	}
	
	private int unloadRecords(ResultSet resultSet, String csClause, boolean bEbcdicOutput, BaseDataFile fileOuput)
	{
		int nNbRecordRead = 0;
		ArrayList<BaseDbColDefinition> arrDbColDef = null;
		
		byte aSeparatorComma[] = new String(",").getBytes();
		if(bEbcdicOutput)	// Must outout in ebcdic
			AsciiEbcdicConverter.swapByteAsciiToEbcdic(aSeparatorComma, 0, aSeparatorComma.length);
			
		while(next(resultSet))
		{
			if(fileOuput != null)
			{
				if(nNbRecordRead == 0)
				{
					BaseDbColDefinitionFactory dbColDefinitionItemFactory = new BaseDbColDefinitionFactory(); 
					arrDbColDef = dbColDefinitionItemFactory.makeArrayDbColDefinitions(resultSet);
				}
				if(arrDbColDef != null)
				{
					for(int nCol=0; nCol<arrDbColDef.size(); nCol++)
					{
						BaseDbColDefinition dbColDefinition = arrDbColDef.get(nCol);
						byte aBytes[];
						if (m_bExcel)
						{
							if (nCol > 0)
								fileOuput.write(aSeparatorComma);
							aBytes = dbColDefinition.getExcelValue(resultSet, nCol+1, bEbcdicOutput);
						}
						else
						{
							aBytes = dbColDefinition.getByteValue(resultSet, nCol+1, bEbcdicOutput);							
						}
						if(aBytes != null)
						{
							fileOuput.write(aBytes);
						}
						else
						{
							Log.logCritical("Unload aborted");
							return -1;
						}
					}
					fileOuput.writeEndOfRecordMarker();
				}
			}
			nNbRecordRead++;
		}
		return nNbRecordRead;
	}
	
	private boolean next(ResultSet resultSet)
	{
		if(resultSet != null)
		{
			try
			{
				return resultSet.next();
			}
			catch (SQLException e)
			{
				LogSQLException.log(e);
				m_bConnectionValid = false;
				return false;
			}
		}
		return false;
	}
	
	private String getSysrecName(int nNbSelectProcessed)
	{
		return "SYSREC" + StringUtil.FormatWithFill2LeftZero(nNbSelectProcessed);
	}
}
