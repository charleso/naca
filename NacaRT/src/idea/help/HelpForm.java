/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.help;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * HelpForm.java created by EasyStruts - XsltGen.
 * http://easystruts.sf.net
 * created on 01-26-2005
 * 
 * XDoclet definition:
 * @struts:form name="helpForm"
 */
public class HelpForm extends ActionForm
{

	// --------------------------------------------------------- Methods

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * Method validate
	 * @param ActionMapping mapping
	 * @param HttpServletRequest request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request)
	{

		//throw new UnsupportedOperationException("Generated method 'validate(...)' not implemented.");
		return null ;
	}

	/** 
	 * Method reset
	 * @param ActionMapping mapping
	 * @param HttpServletRequest request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		m_field = "" ;
		m_help = "" ;
		m_Page = "" ;
	}

	/**
	 * @param field
	 */
	public void setCurrentField(String field)
	{
		m_field = field ;		
	}
	public String getCurrentField()
	{
		return m_field ;	
	}
	protected String m_field = "" ;

	public String getPageList()
	{
		return "" ;
	}
	
	public void setCurrentPage(String page)
	{
		m_Page = page ;
	}
	protected String m_Page = "" ;
	public String getCurrentPage()
	{
		return m_Page ;
	}
	/**
	 * @param help
	 */
	public void setHelp(String help)
	{
		m_help = help ;		
	} 
	protected String m_help = "" ;
	public String getHelp()
	{
		return m_help ;
	}
	public HelpForm getHelpForm()
	{
		return this ;
	}
	public String getDisplay(String cs)
	{
		return cs ;
	}
	public String getLocalizedText(String cs)
	{
		return cs ;
	}
}
