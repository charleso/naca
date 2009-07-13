/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.dbUtils;

import java.util.ArrayList;

import jlib.log.Log;
import jlib.misc.BaseDataFile;
import jlib.misc.BasePic9Comp3BufferSupport;
import jlib.misc.DataFileLineReader;
import jlib.misc.IntegerRef;
import jlib.misc.LineRead;
import jlib.misc.LogicalFileDescriptor;
import jlib.sql.BaseDbColDefinition;
import jlib.sql.BaseDbColDefinitionFactory;
import jlib.sql.DbColDefErrorManager;
import jlib.sql.DbConnectionBase;
import jlib.sql.DbPreparedStatement;
import jlib.sql.SQLLoadStatus;
import jlib.sql.SQLTypeOperation;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.varEx.FileDescriptor;

/**
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SQLLoad.java,v 1.34 2008/01/21 10:39:39 u930bm Exp $
 */
public class SQLLoad extends BaseSQLUtils
{
	public SQLLoad(BaseSession session, DbConnectionBase dbConnection)
	{
		super(session, dbConnection);
		BasePic9Comp3BufferSupport.init();
	}

	public SQLLoadStatus execute(FileDescriptor fileSysin)
	{
		FileSQLLoadScriptReader fileSQLLoadScriptReader = new FileSQLLoadScriptReader(getSession());
		SQLLoadStatus sqlLoadStatus = fileSQLLoadScriptReader.parse(this, fileSysin);
		return sqlLoadStatus;
	}

	int executeStatement(String csScriptLine)
	{
		return -1;
	}

	SQLLoadStatus executeStatement(IntegerRef rnNbRecord, LoadScriptLineInfo loadInfo)
	{
		String csDefaultTablePrefix = m_dbConnection.getEnvironmentPrefix();
		String csTableFullName = loadInfo.getFullTableName(csDefaultTablePrefix);
		
		BaseDbColDefinitionFactory dbColDefinitionFactory = new BaseDbColDefinitionFactory();
		ArrayList<BaseDbColDefinition> arrDbColDef = dbColDefinitionFactory.makeArrayDbColDefinitions(m_dbConnection, loadInfo.getTablePrefix(), loadInfo.getUnprefixedTableName());
		if (arrDbColDef != null)
		{
			String csFileDataIn = loadInfo.getInddnValue();
			return insertData(rnNbRecord, csFileDataIn, csTableFullName, arrDbColDef, loadInfo.isReplace());
		}
		return SQLLoadStatus.loadFailure;
	}
	
	private int lockTable(String csTableName)
	{
		String csSqlLock = "LOCK TABLE " + csTableName + " IN EXCLUSIVE MODE"; 
		return executeSQLClause(csSqlLock);
	}

	private int deleteTable(String csTableName)
	{
		String csSqlDelete = "DELETE FROM " + csTableName;
		return executeSQLClause(csSqlDelete);
	}

	private SQLLoadStatus insertData(IntegerRef rnNbRecordInserted, String csLogicalFileDataIn, String csTableFullName, ArrayList<BaseDbColDefinition> arrDbColDef, boolean bLoadReplace)
	{
		int nBatchSize = BaseResourceManager.getSQLInsertStatementBatchSize();		
		int nBatchCommitSize = BaseResourceManager.getSQLInsertStatementBatchCommitSize();
		int nBatchDone = 0;
		
		if (bLoadReplace)
		{
			lockTable(csTableFullName);
			deleteTable(csTableFullName);
			if (nBatchCommitSize > 0)
				m_dbConnection.commit();
		}
		
		FileDescriptor fileDescriptorIn = new FileDescriptor(csLogicalFileDataIn);
		fileDescriptorIn.setSession(getSession());
		String csPhysicalFileIn = fileDescriptorIn.getPhysicalName();
		
		if (BaseDataFile.isNullFile(csPhysicalFileIn))	// file giving record to insert is nullfile: simulate a correct working
			return SQLLoadStatus.loadSuccess; 
		
		LogicalFileDescriptor logicalFileDescriptor = fileDescriptorIn.getLogicalFileDescriptor();
		
		DataFileLineReader dataFileIn = new DataFileLineReader(csPhysicalFileIn, 65536, 0);
		boolean bInOpened = dataFileIn.open(logicalFileDescriptor);
		if (!bInOpened)
			return SQLLoadStatus.loadFailure;
		
		boolean bEbcdicInput = fileDescriptorIn.isEbcdic();

		fileDescriptorIn.tryAutoDetermineRecordLengthIfRequired(dataFileIn);
		
		// Prepare the array of the col values to insert
		int nNbCols = arrDbColDef.size();		
		String csInsertClause = BaseDbColDefinitionFactory.makeInsertString(csTableFullName, arrDbColDef);
		SQLTypeOperation typeOperation = SQLTypeOperation.determineOperationType(csInsertClause, false);	// cursor clause not supported
		
		// Remove ending ';' as it is not supported by UDB
		if(csInsertClause.endsWith(";"))
			csInsertClause = csInsertClause.substring(0, csInsertClause.length()-1);
		
		csInsertClause = SQLTypeOperation.addEnvironmentPrefix(m_dbConnection.getEnvironmentPrefix(), csInsertClause, typeOperation, "");
		DbPreparedStatement stmt = m_dbConnection.prepareStatement(csInsertClause, 0, false);
		if(stmt == null)
		{
			Log.logCritical("Could not prepare statement; SQL load aborted" + csInsertClause);
			dataFileIn.close();
			return SQLLoadStatus.loadFailure;
		}
		
		int nOffsetHeaderVariableLength = 0;
		if(fileDescriptorIn.isVariableLength())
			nOffsetHeaderVariableLength = 4;
			
		SQLLoadStatus globalStatus = SQLLoadStatus.loadSuccess;
		
		boolean bLockReplace = false;
		int nNbRecordInserted = 0;
		DbColDefErrorManager dbColDefErrorManager = new DbColDefErrorManager();
		LineRead lineRead = fileDescriptorIn.readALine(dataFileIn, null);		
		while (lineRead != null && globalStatus != SQLLoadStatus.loadFailure)
		{
			if (bLoadReplace && !bLockReplace)
			{
				lockTable(csTableFullName);
				bLockReplace = true;
			}
			dbColDefErrorManager.setLine(nNbRecordInserted);

			byte arrByteValue[] = lineRead.getBuffer();
			int nSourceOffset = lineRead.getOffset();
			nSourceOffset += nOffsetHeaderVariableLength;	// Skip variable length 4 bytes header
			
			for (int nCol = 0; nCol < nNbCols; nCol++)
			{
				BaseDbColDefinition dbColDef = arrDbColDef.get(nCol);				
				int nColLengthInFile = dbColDef.setByteValueInStmtCol(dbColDefErrorManager, stmt, nCol, arrByteValue, nSourceOffset, bEbcdicInput);
				nSourceOffset += nColLengthInFile;
			}

			stmt.addBatch();
			if (stmt.getBatchSize() >= nBatchSize)
			{
				nBatchDone++;
				SQLLoadStatus status = stmt.executeBatch(nNbRecordInserted-nBatchSize);
				globalStatus = SQLLoadStatus.updateWithLocalStatus(globalStatus, status);
				if (bLoadReplace && nBatchCommitSize > 0)
				{
					if (nBatchDone >= nBatchCommitSize)
					{
						m_dbConnection.commit();
						bLockReplace = false;
						nBatchDone = 0;
					}
				}
			}

			lineRead = fileDescriptorIn.readALine(dataFileIn, lineRead);			
			nNbRecordInserted++;
		}
		
		if (globalStatus != SQLLoadStatus.loadFailure && stmt.getBatchSize() > 0)
		{
			nBatchDone++;
			SQLLoadStatus status = stmt.executeBatch(nNbRecordInserted);
			globalStatus = SQLLoadStatus.updateWithLocalStatus(globalStatus, status);
		}
		if (globalStatus != SQLLoadStatus.loadFailure && bLoadReplace && nBatchCommitSize > 0 && nBatchDone > 0)
		{
			m_dbConnection.commit();
		}
		dataFileIn.close();
		
		rnNbRecordInserted.set(nNbRecordInserted);
		if (globalStatus == SQLLoadStatus.loadFailure || dbColDefErrorManager.getNbErrors() != 0)
		{
			String csErrorsText = dbColDefErrorManager.getErrorsText();
			manageLoadLogError(csInsertClause, csErrorsText, nNbRecordInserted);
		}
		
		return globalStatus;
	}

	private void manageLoadLogError(String csInsertClause, String csErrorsText, int nNbRecordInserted)
	{
		String cs = makeLogText(csInsertClause, csErrorsText, nNbRecordInserted);
		Log.logCritical("SQL Load Errors: " + cs);
		BaseProgramLoader.logMail("SQLLoad Errors", cs);
	}
}
