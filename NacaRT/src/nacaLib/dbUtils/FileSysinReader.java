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

import jlib.log.Log;
import jlib.misc.BaseDataFile;
import jlib.misc.DataFileLineReader;
import jlib.misc.LineRead;
import jlib.misc.LogicalFileDescriptor;
import jlib.misc.StringUtil;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.varEx.FileDescriptor;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: FileSysinReader.java,v 1.6 2007/02/22 15:08:46 u930bm Exp $
 */
public class FileSysinReader extends BaseFileScriptReader
{
	private DataFileLineReader m_dataFileIn = null;
	private BaseSession m_session = null;
	
	FileSysinReader(BaseSession session)
	{
		m_session = session;
	}
	
	boolean parse(BaseSQLUtils sqlUtils, FileDescriptor fileIn)
	{
		int nSumNbRecords = 0;
		fileIn.setSession(m_session);
		String csFileIn = fileIn.getPhysicalName();
		if(BaseDataFile.isNullFile(csFileIn))
			return false;
		
		m_dataFileIn = new DataFileLineReader(csFileIn, 65536, 0);
		boolean bInOpened = m_dataFileIn.open();
		if(bInOpened)
		{
			String csSQLClause = readSQLLine();
			while(csSQLClause != null)
			{
				if(!StringUtil.isEmpty(csSQLClause))
				{
					csSQLClause = csSQLClause.toUpperCase();
					int nNbRecord = sqlUtils.executeStatement(csSQLClause);
					if(nNbRecord == -1)
					{
						Log.logCritical("SQL Statement " + csSQLClause + " not correctly executed; whole processing aborted");
						return false;
					}
					else
					{
						Log.logCritical("SQL Statement " + csSQLClause + " executed on " + nNbRecord + " records");
						nSumNbRecords += nNbRecord;
					}
				}
				csSQLClause = readSQLLine();
			}
		}
		Log.logNormal("File correctly executed on " + nSumNbRecords + " sum of records");
		return true;
	}
	
	private String readSQLLine()
	{
		LineRead lineRead = m_dataFileIn.readNextUnixLine();
		if(lineRead == null)
			return null;
		
		String csSQLLine = "";
		while(lineRead != null)
		{
			String csPhysicalLine = lineRead.getChunkAsString();
			csPhysicalLine = removeCrLf(csPhysicalLine);
			csPhysicalLine = removeCommentAndLineNumber(csPhysicalLine);
			csPhysicalLine = csPhysicalLine.trim();
			if(!StringUtil.isEmpty(csPhysicalLine))
				csSQLLine += " " + csPhysicalLine;
			if(isEndOfSQLLine(csSQLLine))
			{
				csSQLLine = csSQLLine.trim();
				return csSQLLine;
			}
			lineRead = m_dataFileIn.readNextUnixLine();
		}
		return csSQLLine;		
	}
}
