/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 3 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate;

//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import parser.CGlobalCommentContainer;
//
//import javax.xml.parsers.*;
//import javax.xml.transform.*;
//import javax.xml.transform.dom.*;
//import javax.xml.transform.stream.*;
//
//import org.w3c.dom.*;

import semantic.CEntityComment;
import utils.COriginalLisiting;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CBaseLanguageExporter
{
	protected CGlobalCommentContainer m_CommentContainer = null ;
	protected String m_IndentItem = "\t" ;
	protected int m_IndentWidth = 4 ;
	protected int m_WidthBeforeOriginalCode = 80 ;

	private int m_LastFillerIndex = 0 ;
	public void ResetFillerIndex()
	{
		m_LastFillerIndex = 0 ;
	}
	public int GetLastFillerIndex()
	{
		m_LastFillerIndex++ ;
		return m_LastFillerIndex ;
	}

	public CBaseLanguageExporter(COriginalLisiting cat, CGlobalCommentContainer commCont)
	{
		m_catalog = cat ;
		m_CommentContainer = commCont ;
	}
	public CBaseLanguageExporter(CBaseLanguageExporter exporter)
	{
		m_catalog = exporter.m_catalog ;
		m_CommentContainer = exporter.m_CommentContainer ;
	}
	protected COriginalLisiting m_catalog = null ;
	
	public void closeOutput()
	{
		String csCurrentLine = "" ;
		m_nLastOriginalLineWritten ++ ;
		while (csCurrentLine != null)
		{
			if (m_CommentContainer.GetCurrentCommentLine() == m_nLastOriginalLineWritten)
			{
				CEntityComment com = m_CommentContainer.GetCurrentComment() ;
				com.DoExportComment() ;
			}
			else
			{
				csCurrentLine = m_catalog.GetOriginalLine(m_nLastOriginalLineWritten);
				if (csCurrentLine != null)
				{
					int blanksize = m_WidthBeforeOriginalCode - m_Indent.length()*m_IndentWidth;
					char[] c = new char[blanksize] ; // COBOL comments starts on line 60
					Arrays.fill(c, ' ') ;
					String blankline = new String(c) ;
					blankline += "// (" + m_nLastOriginalLineWritten + ") " + csCurrentLine;
					DoWriteLine(blankline);
				}
			}	
			m_nLastOriginalLineWritten ++ ;
		}
		doCloseOutput() ;
	}
	protected abstract void doCloseOutput() ;
	public abstract void CloseBracket() ;
	public abstract void OpenBracket() ;
	protected int m_nLastOriginalLineWritten = 0 ;
	protected abstract void DoWriteLine(String line) ;
	protected void DoWriteComment(String line, int n)
	{
		String fullLine = line ;
		if (m_nLastOriginalLineWritten < n)  
		{ 
			DisplaySkippedLines(n) ;
			int blanksize = m_WidthBeforeOriginalCode - line.length() - m_Indent.length()*m_IndentWidth;
			if (blanksize > 0)
			{
				char[] c = new char[blanksize] ; // COBOL comments starts on line 80
				Arrays.fill(c, ' ') ;
				String blankline = new String(c) ;
				fullLine = line + blankline + "// (" + n + ")" ;
			}
			else
			{
				fullLine = line + m_IndentItem + "// (" + n + ")";
			}
			m_nLastOriginalLineWritten = n;
		}
		DoWriteLine(fullLine) ;
	}
	protected void DoWriteLine(String line, int n)
	{
		if (n <= m_nLastOriginalLineWritten || n==0)
		{
			DoWriteLine(line) ;
		}
		else
		{
			DisplaySkippedLines(n);
			String csOrigLine = "" ;
			if (m_CommentContainer.GetCurrentCommentLine() == n)
			{
				CEntityComment com = m_CommentContainer.GetCurrentComment() ;
				if (!line.equals(""))
					line += m_IndentItem ;
				line += com.ExportReference(n) ;
				//com.DoExportComment() ;
				if (m_nLastOriginalLineWritten < n)
				{
					csOrigLine = "   (" + n + ") " ;
					String orig = m_catalog.GetOriginalLine(n) ;
					String comm = com.getOriginalComment() ;
					orig = orig.replace(comm, "").trim() ;
					if (!orig.equals(""))
						csOrigLine += orig ;
					m_nLastOriginalLineWritten = n;
				}
			}
			else if (m_nLastOriginalLineWritten < n)  
			{ //m_nLastOriginalLineWritten == n-1
				csOrigLine = "// (" + n + ") " ;
				if (m_catalog.GetOriginalLine(n) != null)
				{
					csOrigLine += m_catalog.GetOriginalLine(n);
				}
				m_nLastOriginalLineWritten = n;
			}
			if (!line.equals("") || !csOrigLine.equals(""))
			{
				int blanksize = m_WidthBeforeOriginalCode - line.length() - m_Indent.length()*m_IndentWidth;
				String fullline ;  
				if (blanksize > 0)
				{
					char[] c = new char[blanksize] ; // COBOL comments starts on line 80
					Arrays.fill(c, ' ') ;
					String blankline = new String(c) ;
					fullline = line + blankline + csOrigLine ;
				}
				else
				{
					fullline = line + m_IndentItem + csOrigLine ;
				}
				DoWriteLine(fullline) ;
			}
		}
	}
	/**
	 * 
	 */
	private void DisplaySkippedLines(int n)
	{
		for (int i=m_nLastOriginalLineWritten+1; i<n; i++)
		{
			if (m_CommentContainer.GetCurrentCommentLine() == i)
			{
				CEntityComment com = m_CommentContainer.GetCurrentComment() ;
				com.DoExportComment() ;
			}
			else
			{
				String cs = m_catalog.GetOriginalLine(i);
				if (cs != null)
				{
					int blanksize = m_WidthBeforeOriginalCode - m_Indent.length()*m_IndentWidth;
					blanksize = Math.max(0, blanksize);
					char[] c = new char[blanksize] ; // COBOL comments starts on line 60
					Arrays.fill(c, ' ') ;
					String blankline = new String(c) ;
					blankline += "// (" + i + ") " + m_catalog.GetOriginalLine(i);
					DoWriteLine(blankline);
					m_nLastOriginalLineWritten = i ;
				}
			}				
		}
	}
	public void WriteLine(String line)
	{
		WriteLine(line, m_nLastOriginalLineWritten) ;
	}
	public void WriteLine(String line, int n)
	{
		if (!m_CurrentLine.equals(""))
		{
			DoWriteLine(m_CurrentLine, m_nLastOriginalLineWritten) ;
			m_CurrentLine = "" ;
		}
		WriteWord(line, n); //m_nLastOriginalLineWritten) ;
		WriteEOL(n) ;
	}
	public void WriteComment(String line, int n)
	{
		DoWriteComment(line, n) ;
	}
	public void WriteEOL() 
	{
		WriteEOL(m_nLastOriginalLineWritten) ;
	}
	public void WriteEOL(int n)
	{
		if (!m_CurrentLine.equals(""))
		{
			String line = m_CurrentLine ;
			m_CurrentLine = "" ;
			DoWriteLine(line, n) ;
		}
	}
	public void WriteWord(String word) 
	{
		WriteWord(word, m_nLastOriginalLineWritten) ;
	}
	public void WriteWord(String word, int n) 
	{
		if (n > m_nLastOriginalLineWritten+1)
		{ // more than one original line to be written
			DoWriteLine("", n-1) ;
		}
		
		int pos = word.indexOf("\n") ;
		if (pos != -1)
		{
			String cs1 = word.substring(0, pos) ;
			String cs2 = word.substring(pos+1);
			WriteWord(cs1, n) ;
			WriteWord(cs2, n) ;
			return ;
		}
		
		if (m_CurrentLine.length() + word.length() > m_WidthBeforeOriginalCode-m_IndentWidth*m_Indent.length() && word.length()>2 && m_CurrentLine.length()>2)
		{
			String l = m_CurrentLine ;
			m_CurrentLine = "" ;
			DoWriteLine(l, n);
			m_CurrentLine = m_IndentItem ;
		}
		m_CurrentLine += word ;
	}
	public void WriteLongString(String string, int n) 
	{
		if (n > m_nLastOriginalLineWritten+1)
		{ // more than one original line to be written
			DoWriteLine("", n-1) ;
		}
				
		String remainString = string ;
		int nSizeRemaining = m_WidthBeforeOriginalCode-m_IndentWidth*m_Indent.length()-m_CurrentLine.length() ;
		while (nSizeRemaining > 0 && remainString.length() - nSizeRemaining > 5)
		{
			int nPos = remainString.indexOf(' ', nSizeRemaining);
			if (nPos == -1)
			{
				if (!m_CurrentLine.equals(m_IndentItem))
				{
					DoWriteLine(m_CurrentLine, n);
				}
				m_CurrentLine = m_IndentItem + "\"" + remainString + "\"" ;
				remainString = "" ;
			}
			else
			{
				String item = remainString.substring(0, nPos+1);
				remainString = remainString.substring(nPos+1) ;
				m_CurrentLine += "\"" + item + "\"+" ;
				DoWriteLine(m_CurrentLine, n);
				m_CurrentLine = m_IndentItem ;
			}
			nSizeRemaining = m_WidthBeforeOriginalCode-m_IndentWidth*m_Indent.length()-m_CurrentLine.length() ;
		}
		if (!remainString.equals(""))
		{
			m_CurrentLine += "\"" + remainString + "\"" ;
		}
	}
	
	public void StartBloc()
	{
		m_Indent += m_IndentItem ;
	}
	public void EndBloc()
	{
		int index = m_Indent.lastIndexOf(m_IndentItem) ;
		m_Indent = m_Indent.substring(0, index) ;
	}
	protected String m_Indent = "" ;
	protected String m_CurrentLine = "" ;

	public String FormatIdentifier(String cs)
	{
		cs = cs.replace('-', '_') ;
		cs = cs.replace('#', '$') ;
		return cs ;
	}

	/**
	 * @return
	 */
	protected String GenereTempFileName(String filename)
	{
		File f = new File(filename) ;
		File par = f.getParentFile();
		
		Date date = new Date() ;
		DateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS") ;
		String cs = format.format(date) ;
		return par.getAbsolutePath() + "/~" + cs + "~.tmp" ;
	}
	
	public abstract String getOutputDir() ;

	public abstract boolean isResources() ;
	
	
	// XML exporter
//	public Element CreateRoot(String name)
//	{
//		try
//		{
//			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//			m_Document = builder.newDocument();
//			m_Root = m_Document.createElement(name) ;
//			m_Document.appendChild(m_Root) ;
//			return m_Root ;
//		}
//		catch (Exception e)
//		{
//		}
//		return null ;
//	}
//	public Element CreateElementTo(String name, Element eto)
//	{
//		Element e = m_Document.createElement(name) ;
//		eto.appendChild(e) ;
//		return e ;
//	}
//	public Element CreateElement(String name)
//	{
//		return m_Document.createElement(name) ;
//	}
//	public Document GetDocument()
//	{
//		return m_Document;
//	}
//	
//	public void ExportTo(String filename)
//	{
//	   if (m_Document != null)
//	   {
//			try
//			{
//				Source source = new DOMSource(m_Document);
//				FileOutputStream file = new FileOutputStream(filename);
//				StreamResult res = new StreamResult(file) ;
//				Transformer xformer = TransformerFactory.newInstance().newTransformer();
//				xformer.setOutputProperty(OutputKeys.ENCODING, "ISO8859-1");
//				xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//				xformer.setOutputProperty(OutputKeys.INDENT, "yes");
//				xformer.transform(source, res);
//			}
//			catch (FileNotFoundException e)
//			{
//			}
//			catch (TransformerConfigurationException e)
//			{
//			}
//			catch (TransformerException e)
//			{
//			}
//		}
//	}
//	
	
//	protected Document m_Document = null ;
//	protected Element m_Root = null ; 
	
}
