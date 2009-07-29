/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.misc;

import jlib.xml.Tag;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class TempFileManager
{
	private String m_csTempPath = null;
	private String m_csLastTempFilePathName = null;
	
	public TempFileManager(String csTempPath)
	{
		m_csTempPath = FileSystem.normalizePath(csTempPath);
		FileSystem.createPath(m_csTempPath);
		cleanupTempPath(m_csTempPath);
	}
	
	public void cleanupTempPath(String csTempPath)
	{
		m_csTempPath = FileSystem.normalizePath(csTempPath);
		FileSystem.DeleteContent(m_csTempPath);
	}

	public String makeTempFileName(String csFileName, String csTmpExt)
	{
		csFileName = FileSystem.getNameWithoutExtension(csFileName);
		m_csLastTempFilePathName = FileSystem.buildFileName(m_csTempPath, csFileName, csTmpExt);
		return m_csLastTempFilePathName;
	}
	
//	public void saveTmpFile(Tag tag, String csFile, String csExt, int nStep)
//	{
//		String csFullExt = "" + nStep + "." + csExt + "." + nStep;
//		String csFileTmpOut = makeTempFileName(csFile, csFullExt);
//		//tag.exportIndentedFileUft8(csFileTmpOut);
//		tag.exportToFileUTF8(csFileTmpOut);
//	}
	
	public String saveIndentedTmpFile(Tag tag, String csFile, String csExt, int nStep)
	{
		String csFullExt = "" + nStep + "." + csExt + "." + nStep;
		String csFileTmpOut = makeTempFileName(csFile, csFullExt);
		tag.exportIndentedUtf8(csFileTmpOut);
		return csFileTmpOut;
	}	
//	
//	public void saveIndentedTmpFileHtml(Tag tag, String csFile, String csExt, int nStep)
//	{
//		String csFullExt = "" + nStep + "." + csExt + "." + nStep;
//		String csFileTmpOut = makeTempFileName(csFile, csFullExt);
//		tag.exportIndentedUtf8Html(csFileTmpOut);
//	}

	// PJD: Sometimes calling saveIndentedTmpFile wites lots of huge xmlcomment with the grammar of the DTD; it's not the case when using saveNotIndentedTmpFile; Why ? 
	public void saveNotIndentedTmpFile(Tag tag, String csFile, String csExt, int nStep)
	{
		String csFullExt = "" + nStep + "." + csExt + "." + nStep;
		String csFileTmpOut = makeTempFileName(csFile, csFullExt);
		tag.exportToFileUTF8(csFileTmpOut);
	}
	
//	public boolean saveTmpFile(StringBuilder sb, String csFile, String csExt, int nStep)
//	{
//		boolean b = true;
//		
//		String csFullExt = "" + nStep + "." + csExt + "." + nStep;
//		String csTempFileName = makeTempFileName(csFile, csFullExt);
//		DataOutputStream stream = FileSystem.openWrite(csTempFileName);
//		String cs = sb.toString();
//		try
//		{
//			byte tb[] = cs.getBytes();
//			stream.write(tb);
//		}
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			b= false;
//		}
//		FileSystem.closeFile(stream);
//		
//		if(!b)
//			Log.logCritical("Could not save temp file " + getLastTempFilePathName());
//		return b;
//	}	
	
	public boolean saveTmpFile(StringBuilder sb, String csFile, String csExt, int nStep)
	{
		String csFullExt = "" + nStep + "." + csExt + "." + nStep;
		String csTempFileName = makeTempFileName(csFile, csFullExt);
		boolean b = FileSystem.writeFileUtf8(csTempFileName, sb);
		return b;
	}
		
	public String getLastTempFilePathName()
	{
		return m_csLastTempFilePathName;
	}
}
