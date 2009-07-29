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
package idea.action;

import idea.onlinePrgEnv.OnlineResourceManager;
import idea.onlinePrgEnv.OnlineResourceManagerFactory;
import idea.onlinePrgEnv.OnlineSession;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jlib.blowfish.Blowfish;
import jlib.xml.Tag;
import jlib.xml.TagCursor;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.SessionEnvironmentRequester;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

//import pub2000Utils.help.LanguageUtil;

public class CustomApplicationLauncherAction extends Action
{
	InputAnalyser m_inputAnalyser = new InputAnalyser() ;

	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception
	{
		HttpSession javaSession = request.getSession(true);
		OnlineSession appSession = null ;
		appSession = (OnlineSession)javaSession.getAttribute("AppSession");
		if (appSession == null)
		{
			return null ;
		}
		// create wrapper for form fields
		CHTTPMapFieldLoader reqLoader = new CHTTPMapFieldLoader(request);
		String appId = reqLoader.getFieldValue("ApplicationId") ;
		
		OnlineResourceManager resman = OnlineResourceManagerFactory.GetInstance() ;
		Tag tagConf = resman.getCustomApplicationLauncherConfig() ;
		TagCursor cur = new TagCursor()  ;
		Tag tagApp = tagConf.getFirstChild(cur, "App") ;
		while (tagApp != null)
		{
			if (tagApp.getVal("ID").equals(appId))
			{
				String url = BuildURL(reqLoader, tagApp, appSession) ;
				ActionForward actionForward = new ActionForward() ;
				actionForward.setRedirect(true) ;
				actionForward.setPath(url) ;
				return actionForward;
			}
			tagApp = tagConf.getNextChild(cur) ;
		}
		return null;
	}

	private String BuildURL(CHTTPMapFieldLoader reqLoader, Tag tagApp, OnlineSession appSession)
	{
		String url = tagApp.getVal("url") ;
		TagCursor cur = new TagCursor() ;
		Tag tagParam = tagApp.getFirstChild(cur, "Param") ;
		if (tagParam != null)	
		{
			url += "?" ;
		}
		while (tagParam != null)
		{
			String name = tagParam.getVal("name") ;
			url += name + "=" ;
			String val = "";
			if (tagParam.isValExisting("ref"))
			{
				String ref = tagParam.getVal("ref") ;
				val = GetValFromRef(ref, tagParam, appSession) ;				
			}
			else if (tagParam.isValExisting("field"))
			{
				String field = tagParam.getVal("field") ;
				val = GetValFromField(field, appSession) ;
			}
			else if (tagParam.isValExisting("request"))
			{
				String request = tagParam.getVal("request") ;
				val = GetValFromRequest(request, reqLoader) ;
			}
			else if (tagParam.isValExisting("value"))
			{
				val = tagParam.getVal("value") ;
				while (val.indexOf("{{ref=") != -1)
				{
					int nPosBegin = val.indexOf("{{ref=");
					int nPosEnd = val.indexOf("}}", nPosBegin);
					String ref = val.substring(nPosBegin + 6, nPosEnd);
					String valRef = GetValFromRef(ref, tagParam, appSession);
					val = val.substring(0, nPosBegin) + valRef + val.substring(nPosEnd + 2);
				}
				while (val.indexOf("{{field=") != -1)
				{
					int nPosBegin = val.indexOf("{{field=");
					int nPosEnd = val.indexOf("}}", nPosBegin);
					String field = val.substring(nPosBegin + 8, nPosEnd);
					String valField = GetValFromField(field, appSession);
					val = val.substring(0, nPosBegin) + valField + val.substring(nPosEnd + 2);
				}
				while (val.indexOf("{{request=") != -1)
				{
					int nPosBegin = val.indexOf("{{request=");
					int nPosEnd = val.indexOf("}}", nPosBegin);
					String request = val.substring(nPosBegin + 10, nPosEnd);
					String valRequest = GetValFromRequest(request, reqLoader);
					val = val.substring(0, nPosBegin) + valRequest + val.substring(nPosEnd + 2);
				}
			}
			if (tagParam.isValExisting("crypted"))
			{
				String crypted = tagParam.getVal("crypted");				
				Blowfish blowfish = new Blowfish(crypted, false);
				val = blowfish.encrypt(val);
			}
			url += val ;
			tagParam = tagApp.getNextChild(cur) ;
			if (tagParam != null)
			{
				url += "&" ;
			}
		}
		return url;
	}

	private String GetValFromRef(String ref, Tag tagParam, OnlineSession appSession)
	{
		BaseProgramLoader prgseq = BaseProgramLoader.GetInstance() ;
		if (prgseq != null)
		{
			SessionEnvironmentRequester req = prgseq.getSessionEnvironmentRequester(appSession) ;
			if (req == null) return "";
			if (ref.equalsIgnoreCase("LANG"))
			{
				String lang = req.getUserLanguageId() ;
				return lang ;
			}
			//else if (ref.equalsIgnoreCase("LANG_ISO"))
			//{
			//	String lang = LanguageUtil.convertFromPub2000ToIso(req.getUserLanguageId());
			//	return lang ;
			//}
			else if (ref.equalsIgnoreCase("USER"))
			{
				String name = req.getUserId() ;
				return name.trim() ;
			}
			else if (ref.equalsIgnoreCase("USERLDAP"))
			{
				String name = req.getUserLdapId();
				return name.trim() ;
			}
			else if (ref.equalsIgnoreCase("PROFITCENTER"))
			{
				String name = req.getProfitCenter() ;
				return name ;
			}
//			else if (ref.equalsIgnoreCase("CMPSESSION"))
//			{
//				String name = req.getCmpSession();
//				return name ;
//			}
			else if (ref.equalsIgnoreCase("CONCAT_FIELD"))
			{
				String val = "";
				String[] fields = tagParam.getVal("field").split("#");
				for (int i=0; i < fields.length; i++)
				{
					val += GetValFromField(fields[i], appSession) ;
				}
				return val;
			}
			else if (ref.equalsIgnoreCase("DATE_MEDIAPRINT"))
			{
				String day = "";
				String month = "";
				String year = new SimpleDateFormat("yyyy").format(new Date());
				String field = tagParam.getVal("field1");
				String val = GetValFromField(field, appSession);
				if (val.trim().equals(""))
				{
					val = new SimpleDateFormat("mmdd").format(new Date());
				}
				else if (val.substring(0, 2).equals("--"))
				{
					year = year.substring(0, 2) + val.substring(2, 4);
					field = tagParam.getVal("field2");
					val = GetValFromField(field, appSession);
				}
				day = val.substring(0, 2);
				month = val.substring(2, 4);
				return day + month + year;
			}
		}
		return "";
	}
	
	private String GetValFromField(String field, OnlineSession appSession)
	{
		String fieldWork;
		int beginIndex = 0;
		int endIndex = 0;
		
		int posSubstr = field.indexOf("substr(");
		if (posSubstr != -1)
		{
			posSubstr += 7;
			int posFirst = field.indexOf(",");
			int posSecond = field.indexOf(",", posFirst + 1);
			int posEnd = field.indexOf(")");
			
			fieldWork = field.substring(posSubstr, posFirst).trim();
			if (posSecond == -1)
			{
				beginIndex = new Integer(field.substring(posFirst + 1, posEnd).trim()).intValue();
			}
			else
			{
				beginIndex = new Integer(field.substring(posFirst + 1, posSecond).trim()).intValue();
				endIndex = new Integer(field.substring(posSecond + 1, posEnd).trim()).intValue();
			}
		}
		else
		{
			fieldWork = field;
		}
		Document eRoot = appSession.getXMLData();
		
		Element eStruct = eRoot.getDocumentElement() ;
		NodeList lst = eStruct.getElementsByTagName("field") ;
		int nb = lst.getLength() ;
		for (int i=0; i<nb; i++)
		{
			Element e = (Element)lst.item(i) ;
			String name = e.getAttribute("name") ;
			if (name.equals(fieldWork))
			{
				if (beginIndex ==0 && endIndex == 0)
				{	
					return e.getAttribute("value");
				}	
				else
				{
					if (endIndex == 0)
						return e.getAttribute("value").substring(beginIndex);
					else
						return e.getAttribute("value").substring(beginIndex, endIndex);
				}
			}
		}
		
		return "";
	}
	
	private String GetValFromRequest(String request, CHTTPMapFieldLoader reqLoader)
	{		
		return reqLoader.getFieldValue(request);
	}
}
