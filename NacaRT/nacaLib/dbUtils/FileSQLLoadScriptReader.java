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

import java.util.ArrayList;

import jlib.log.Log;
import jlib.misc.BaseDataFile;
import jlib.misc.DataFileLineReader;
import jlib.misc.IntegerRef;
import jlib.misc.LineRead;
import jlib.misc.LogicalFileDescriptor;
import jlib.misc.StringUtil;
import jlib.sql.SQLLoadStatus;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.varEx.FileDescriptor;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class FileSQLLoadScriptReader extends BaseFileScriptReader
{
	private DataFileLineReader m_dataFileIn = null;
	private BaseSession m_session = null;
	
	FileSQLLoadScriptReader(BaseSession session)
	{
		m_session = session;
	}
	
	SQLLoadStatus parse(SQLLoad sqlLoad, FileDescriptor fileIn)
	{
		int nSumRecords = 0;
		fileIn.setSession(m_session);
		String csFileIn = fileIn.getPhysicalName();
		if(BaseDataFile.isNullFile(csFileIn))
		{
			Log.logImportant("Physical load File " + csFileIn + " script NOT correctly loaded: No load execution done");
			return SQLLoadStatus.loadFailure;
		}			

		m_dataFileIn = new DataFileLineReader(csFileIn, 65536, 0);
		boolean bInOpened = m_dataFileIn.open();
		if(!bInOpened)
			return SQLLoadStatus.loadFailure;
		int nLineIndex = 0;
		
		SQLLoadStatus loadGlobalStatus = SQLLoadStatus.loadSuccess; 
		String csLine = readLogicalLine(nLineIndex);
		while(csLine != null)
		{
			nLineIndex++;
			if(!StringUtil.isEmpty(csLine))
			{
				csLine = csLine.toUpperCase();
				LoadScriptLineInfo loadInfo = parseLoadLine(csLine);
				if(loadInfo != null)
				{
					IntegerRef rnNbRecord = new IntegerRef();  
					SQLLoadStatus loadStatus = sqlLoad.executeStatement(rnNbRecord, loadInfo);
					loadGlobalStatus = SQLLoadStatus.updateWithLocalStatus(loadGlobalStatus, loadStatus);
					int nNbRecord = rnNbRecord.get();
					if(!loadGlobalStatus.isSuccess())
					{
						Log.logCritical("Load line " + csLine + " not correctly executed; whole processing aborted");
						return SQLLoadStatus.loadFailure;
					}
					else	// Success even if duplicates
					{
						Log.logNormal("Nb Record processed: " + nNbRecord + " records for line:" + csLine);
						nSumRecords += nNbRecord;
					}
				}
			}
			csLine = readLogicalLine(nLineIndex);
		}
		Log.logNormal("Physical load File " + csFileIn + " script correctly executed, with sum of records processed=" + nSumRecords);
		return loadGlobalStatus;
	}
	
//	private String readLogicalLine()
//	{
//		LineRead lineRead = m_dataFileIn.readNextUnixLine();
//		if(lineRead == null)
//		{
//			String csLine = m_csLastPhysicalLine;
//			m_csLastPhysicalLine = null;
//			return csLine;
//		}
//			
//		String csLine = "";
//		if(m_csLastPhysicalLine != null)
//			csLine = m_csLastPhysicalLine;
//		while(lineRead != null)
//		{
//			String csPhysicalLine = lineRead.getChunkAsString();
//			csPhysicalLine = removeCrLf(csPhysicalLine);
//			csPhysicalLine = removeCommentAndLineNumber(csPhysicalLine);
//			if(!StringUtil.isEmpty(csPhysicalLine))
//			{
//				if(!isContinuationLine(csPhysicalLine))
//				{
//					m_csLastPhysicalLine = csPhysicalLine;
//					return csLine;
//				}
//				csLine += " " + csPhysicalLine;
//			}
//			
//			lineRead = m_dataFileIn.readNextUnixLine();
//		}
//		if(lineRead == null)
//			m_csLastPhysicalLine = null;
//		return csLine;
//	}
	private ArrayList<String> m_arrLines = null;
	
	private String readLogicalLine(int nLine)
	{
		if(m_arrLines == null)
			readAllLines();
		if(nLine >= m_arrLines.size())
			return null;
		if(nLine < 0)
			return null;
		return m_arrLines.get(nLine);
	}
	
	private void readAllLines()
	{
		if(m_arrLines != null)
			return ;
		m_arrLines = new ArrayList<String>();
		
		String csCurrentLine = new String();
		LineRead lineRead = m_dataFileIn.readNextUnixLine();
		while(lineRead != null)
		{
			String csPhysicalLine = lineRead.getChunkAsString();
			csPhysicalLine = removeCrLf(csPhysicalLine);
			csPhysicalLine = removeCommentAndLineNumber(csPhysicalLine);
			if(!StringUtil.isEmpty(csPhysicalLine))
			{
				if(isContinuationLine(csPhysicalLine))	// The csPhysicalLine is the beginnin of a new logical line
					csCurrentLine += " " + csPhysicalLine;
				else
				{
					if(!StringUtil.isEmpty(csCurrentLine))
						m_arrLines.add(csCurrentLine);
					csCurrentLine = csPhysicalLine;
				}
			}
			
			lineRead = m_dataFileIn.readNextUnixLine();
		}
		if(!StringUtil.isEmpty(csCurrentLine))
			m_arrLines.add(csCurrentLine);
	}
	
	private boolean isContinuationLine(String csPhysicalLine)
	{
		if(csPhysicalLine.startsWith("   "))	// At least 3 spaces indiciates a continuation line
			return true;
		return false;
	}
	
	private LoadScriptLineInfo parseLoadLine(String csLine)
	{
		int nPos = csLine.indexOf("LOAD");
		if(nPos != -1)
		{
			LoadScriptLineInfo info = new LoadScriptLineInfo();
			String csRight = csLine.substring(nPos).trim();
			if(csRight.indexOf("REPLACE") != -1)	// LOAD REPLACE
				info.setReplace(true);

			nPos = csRight.indexOf("INDDN");
			if(nPos != -1)	// INDDN
			{
				csRight = csRight.substring(nPos+5);
				String csInddnValue = StringUtil.getFirstWord(csRight);
				info.setInddnValue(csInddnValue);
			}
			else	// No INDDN
				info.setInddnValue("SYSREC");

			nPos = csRight.indexOf("INTO");
			if(nPos != -1)
			{
				csRight = csRight.substring(nPos+4);
				csRight = csRight.trim();
				nPos = csRight.indexOf("TABLE");
				if(nPos != -1)
				{
					csRight = csRight.substring(nPos+5).trim();
					String csTableName = StringUtil.getFirstWord(csRight);
					info.setFullTable(csTableName);
				}
			}
			return info;
		}
		return null;	// No load script line
	}
}
