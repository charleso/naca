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
package idea.onlinePrgEnv;

import idea.emulweb.CScenarioPlayer;
import idea.manager.CMapFieldLoader;
import idea.semanticContext.CMenuDef;

import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jlib.log.Log;
import jlib.misc.LdapRequester;
import jlib.misc.StopWatch;
import jlib.sql.DbConnectionManagerBase;
import jlib.xml.XMLUtil;
import nacaLib.accounting.CriteriaEndRunMain;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.basePrgEnv.CBaseMapFieldLoader;
import nacaLib.basePrgEnv.CurrentUserInfo;
import nacaLib.exceptions.AbortSessionException;
import nacaLib.misc.KeyPressed;

import org.apache.struts.action.ActionForward;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class OnlineSession extends BaseSession implements HttpSessionBindingListener
{
	protected OnlineResourceManager m_ResourceManager = null ;
	
	protected Document m_xmlData = null ;
	protected Document m_XMLOutput = null ;
	protected String m_currentPage = "" ;
	//protected String m_cmp = "" ;
	protected CScenarioPlayer m_ScenarioPlayer = null ;
	protected boolean m_bCheckScenario = true;
	private int m_nHttpSessionMaxInactiveInterval_s;
	private boolean m_bZoom = false;
	private boolean m_bBold = false;
	private boolean m_bInternTest = false;

	public OnlineSession(boolean bAsyncSession)
	{
		super(OnlineResourceManagerFactory.GetInstance());
		m_ResourceManager = OnlineResourceManagerFactory.GetInstance() ;
		m_nHttpSessionMaxInactiveInterval_s = m_ResourceManager.getHttpSessionMaxInactiveInterval_s();
		String doc = m_ResourceManager.getScenarioFilePath() ;
		if (doc != null && !doc.equals(""))
		{
			m_ScenarioPlayer = new CScenarioPlayer(doc, this) ;
		}
		
		setAsync(bAsyncSession);
		
//		if(bAsyncSession)
//		{
//			setAsync(true);
//			//JmxGeneralStat.incNbCurrentAsyncStartSession(1);
//		}
//		else
//		{
//			setAsync(false);
//			//JmxGeneralStat.incNbCurrentOnlineSession(1);
//		}
	}
	
//	public void finalize()
//	{
//		if(isAsync())
//			JmxGeneralStat.incNbCurrentAsyncStartSession(-1);
//		else
//			JmxGeneralStat.incNbCurrentOnlineSession(-1);
//	}
	
	protected String m_csLUName = "";
	
	public String getTerminalNet()
	{
		if(m_csLUName == null || m_csLUName.equals(""))
			return "L930CON1";
		return m_csLUName;
	}
	
	public String getTerminalNetLu62()
	{
		if(m_csLUName == null || m_csLUName.equals(""))
			return "L930CON1";
		return m_csLUName;
	}
	
	public String getTerminalTerm()
	{
		if(m_csLUName != null && !m_csLUName.equals(""))
		{
			int nLength = m_csLUName.length();
			if(nLength >= 4)
				return m_csLUName.substring(nLength-4, nLength);
		}
		return "CON1";
	}
	
	public String getTerminalTermLu62()
	{
		if(m_csLUName != null && !m_csLUName.equals(""))
		{
			int nLength = m_csLUName.length();
			if(nLength >= 4)
				return m_csLUName.substring(nLength-4, nLength);
		}
		return "CON1";
	}

	public String getLUName()
	{
		return m_csLUName;
	}
	public void SetLUName(String csLUName)
	{
		m_csLUName = csLUName ; 
	}
	
	public void valueBound(HttpSessionBindingEvent event) 
	{
	}

	public void valueUnbound(HttpSessionBindingEvent event)
	{
		if(event.getName().equals("AppSession"))
		{
			Log.logNormal("Removing session");
			OnlineSession session = (OnlineSession)event.getValue();
			m_ResourceManager.removeSession(session);
		}
		else
		{
			Log.logImportant("Removing unknown object from session: "+event.getName());
		}
	}

	public int getOnceHttpSessionMaxInactiveInterval_s()
	{
		int n = m_nHttpSessionMaxInactiveInterval_s;
		m_nHttpSessionMaxInactiveInterval_s = 0;
		return n;		
	}

	
	public void reset()
	{
		m_xmlData = null ;
		m_XMLOutput = null ;
		m_currentPage = "" ;
		m_InputWrapper = null ;
		m_bIsLoggedOnLDAP = false ;
		m_csLDAPUser  = "" ;
		m_bIsLoggedOnLDAP = false ;
		m_currentPage = "MapLogin" ;
		m_csApplicationCredentials = "" ;	
	
		m_csUserLdapId = "" ;
		m_csUserLdapName = "" ;

		m_csLUName = null;
	}

	public Document CreateXMLDataRoot()
	{
		try
		{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			m_xmlData = builder.newDocument();
			Element eRoot = m_xmlData.createElement("form");
			m_xmlData.appendChild(eRoot);
			return m_xmlData;
		}
		catch(ParserConfigurationException e)
		{
			return null ;
		}		
	}
	
	public Document getLastScreenXMLData()
	{
		return m_xmlData ;
	}
	

	public Document getXMLData()
	{
		return m_xmlData ;
	}

	public Document getXMLStructure(String idPage)
	{
		if (idPage == null || idPage.equals(""))
		{
			return null ;
		}
		return m_ResourceManager.GetXMLStructure(idPage) ;
	}

	public Document getCurrentXMLStructure()
	{
		return m_ResourceManager.GetXMLStructure(m_currentPage) ;
	}

	public Document getCurrentXMLStructureForPrintScreen()
	{
		return m_ResourceManager.GetXMLStructureForPrintScreen(m_currentPage) ;
	}
	
	public Document getCurrentXMLStructureForServerDown()
	{
		return m_ResourceManager.GetXMLStructureForPrintScreen(m_currentPage) ;
	}
	
	public CMenuDef getMenuForSemanticContext(String csSemanticContext)
	{		
		return m_ResourceManager.getMenuForSemanticContext(m_currentPage, csSemanticContext);
	}

	public Document getXMLOutput()
	{
		if (m_ScenarioPlayer != null && m_XMLOutput != null)
		{
			String display = m_ScenarioPlayer.getDisplay() ;
			m_XMLOutput.getDocumentElement().setAttribute("replay", display);
		}
		return m_XMLOutput ;
	}

	public String getIdPage()
	{
		return m_currentPage;
	}
	public void setIdPage(String id)
	{
		m_currentPage = id ;
	}
	
//	public String getCmp()
//	{
//		return m_cmp;
//	}
//	public void setCmp(String cmp)
//	{
//		m_cmp = cmp;
//	}
	
	public boolean isZoom()
	{
		return m_bZoom;
	}
	public void setZoom(boolean bZoom) {
		m_bZoom = bZoom;
	}
	
	public boolean isBold()
	{
		return m_bBold;
	}
	public void setBold(boolean bBold) {
		m_bBold = bBold;
	}
	
	public boolean isInternTest()
	{
		return m_bInternTest;
	}
	public void setInternTest(boolean bInternTest)
	{
		m_bInternTest = bInternTest; 
	}
	
	public void setXMLData(Document doc)
	{
		if (doc != null)
		{
			m_xmlData = doc ;
		}
	}

	public void setInputWrapper(CMapFieldLoader reqLoader)
	{
		m_InputWrapper = reqLoader ;
		if (isPlayingScenario())
		{
			m_ScenarioPlayer.StepScenario();
		}
	}
	
	protected CMapFieldLoader m_InputWrapper = null ;
	public CMapFieldLoader getInputWrapper()
	{
		return m_InputWrapper ;
	}

	public void setXMLOutput(Document xmlOutput)
	{
		m_XMLOutput = xmlOutput ;
		if (isPlayingScenario() && isCheckScenario())
		{
			m_ScenarioPlayer.CheckOutput(xmlOutput) ;
		}
	}

	/**
	 * @param doc
	 */
	public void setHelpPage(Document doc)
	{
		m_helpPage = doc ;		
	}
	
	public Document getHelpPage()
	{
		return m_helpPage ;
	}
	
	protected Document m_helpPage = null ;
	/**
	 * @return
	 */
	public boolean isPlayingScenario()
	{
		return m_ScenarioPlayer != null && m_ScenarioPlayer.isPlayingScenario() ;
	}
	
	public boolean isCheckScenario()
	{
		return m_bCheckScenario;
	}

	/**
	 * @return
	 */
	public boolean isCallProgram()
	{
		if (m_InputWrapper != null && m_InputWrapper.getKeyPressed() == KeyPressed.LOG_OUT)
		{
			return false ;
		}
		if (m_InputWrapper != null && m_InputWrapper.getKeyPressed() == KeyPressed.CHANGE_USER)
		{
			m_xmlData = null ;
			m_XMLOutput = null ;
			m_csApplicationCredentials = "" ;
			m_currentPage = "" ;
			BaseProgramLoader.GetInstance().removeSession(this) ;			
			return true ;
		}
		if (m_ScenarioPlayer == null || !m_ScenarioPlayer.isPlayingScenario())
		{
			return true ;
		}
		else
		{
			return m_ScenarioPlayer.isCallProgram() ;
		}
	}

	/**
	 * @return
	 */
	public boolean isUpdatedValues()
	{
		if (m_ScenarioPlayer == null || !m_ScenarioPlayer.isPlayingScenario())
		{
			return false ;
		}
		else
		{
			return m_ScenarioPlayer.isShowPage() ;
		}
	}

	/**
	 * @return
	 */
	public CScenarioPlayer getScenarioPlayer()
	{
		return m_ScenarioPlayer;
	}

	/**
	 * @return
	 */
	public boolean isLoggedOut()
	{
		if (m_InputWrapper == null)
		{
			return false ;
		}
		boolean b = m_InputWrapper.getKeyPressed() == KeyPressed.LOG_OUT ;
		return b ;
	}

	/**
	 * @return
	 */
	public KeyPressed getKeyPressed()
	{
		if (m_xmlData != null)
		{
			String cs = m_xmlData.getDocumentElement().getAttribute("keypressed") ;
			return KeyPressed.getKey(cs) ;
		}
		return null ;
	}

	/**
	 * @return
	 */
	public String getActionAlias()
	{
		return m_csActionAlias;
	}
	protected String m_csActionAlias = "" ;
	public void setActionAlias(String cs)
	{
		m_csActionAlias = cs ;
	}

	/**
	 * @param doc
	 */
	public void SetScenario(String scenarioFilePath)
	{
		m_currentPage = "" ;
		m_xmlData = null ;
		m_XMLOutput = null ;
		m_ScenarioPlayer = new CScenarioPlayer(scenarioFilePath, this) ;		
	}
	
	public void setCheckScenario(boolean bCheckScenario)
	{
		m_bCheckScenario = bCheckScenario;
	}

	/**
	 * @return
	 */
	public boolean isLogged()
	{
		return m_bIsLoggedOnLDAP ;
	}
	protected boolean m_bIsLoggedOnLDAP = false ;
	protected String m_csLDAPUser  = "" ;

	/**
	 * 
	 */
	public boolean doLDAPLogin(/*String csCmp, */String csUserid)
	{
		if(csUserid != null && csUserid.equals("test") && m_baseResourceManager.getSimulateRealEnvironment())
		{
			m_csUserLdapId = csUserid;
			m_csUserLdapName = csUserid;
			m_csApplicationCredentials = "all";
			m_bIsLoggedOnLDAP = true;
			return true;
		}
		
		String csMessage = "";
		if (m_InputWrapper != null && !isLoggedOut())
		{
			boolean bLoginAuto = false;
			String csPassword = "";
//			if (csCmp != null && !csCmp.equals("") && csUserid != null && !csUserid.equals(""))
//			{
//				int nPos = csUserid.indexOf("CN=");
//				if (nPos != 1)
//				{
//					m_csUserLdapId = csUserid.substring(nPos + 3, csUserid.indexOf(",", nPos));
//					csPassword = "AUTO";
//					bLoginAuto = true;
//				}
//			}
//			else
			//{
				m_csUserLdapId = m_InputWrapper.getFieldValue("userid");
				csPassword = m_InputWrapper.getFieldValue("password");
			//}
			if (!m_csUserLdapId.equals("") && !csPassword.equals(""))
			{
				m_csUserLdapId = m_csUserLdapId.toUpperCase();
				LdapRequester ldapReq = m_ResourceManager.getLdapRequester() ;
				String csUserDN = ldapReq.getUserLogin(m_csUserLdapId, csPassword, bLoginAuto) ;
				
				boolean bLogged = csUserDN != null && !csUserDN.equals("") ;
				if (bLogged)
				{
					m_csApplicationCredentials = ldapReq.getAttribute(csUserDN, "extensionAttribute12") ;
					if (m_csApplicationCredentials == null)
					{
						m_csApplicationCredentials = "" ;
					}
					String csSn = ldapReq.getAttribute(csUserDN, "sn") ;					
					if (csSn == null)
					{
						m_csUserLdapName = "";
					}
					else
					{
						m_csUserLdapName = csSn;
						String csGivenName = ldapReq.getAttribute(csUserDN, "givenName") ;
						if (csGivenName != null) {
							m_csUserLdapName += " " + csGivenName;
						}
					}
					m_bIsLoggedOnLDAP = true ;
					return true ;
				}
				else
				{
					csMessage = "Identification incorrecte / Falsche Anmeldung / Identificazione errata";
				}
			}
			else
			{
				if (m_currentPage.equals("MapLogin"))
				{
					csMessage = "Identification incomplète / Unvollständige Anmeldung / Identificazione incompleta";
				}	
			}
		}
		m_bIsLoggedOnLDAP = false ;
		m_currentPage = "MapLogin" ;

		Document data = XMLUtil.CreateDocument();
		Element eForm = data.createElement("form");
		eForm.setAttribute("page", "MapLogin");
		data.appendChild(eForm);
		Element eField = data.createElement("field");
		eForm.appendChild(eField);
		eField.setAttribute("name", "userid");
		eField.setAttribute("value", m_csUserLdapId);
		Element eMessage = data.createElement("field");
		eForm.appendChild(eMessage);
		eMessage.setAttribute("name", "errormessage");
		eMessage.setAttribute("value", csMessage);		
		setXMLData(data) ;
		
		return false ;
	} 
	
	protected String m_csApplicationCredentials = "" ;	
	public String getApplicationCredentials()
	{
		return m_csApplicationCredentials;
	}
	
	protected String m_csUserLdapId = "" ;
	public String getUserLdapId()
	{
		return m_csUserLdapId;
	}
	
	protected String m_csUserLdapName = "" ;
	public String getUserLdapName()
	{
		return m_csUserLdapName;
	}
	
	public String getServerName()
	{
		String csServerName = m_ResourceManager.getServerName();
//		if (!m_cmp.equals("")) {
//			csServerName += " CMP";
//		}
		return csServerName;
	}
	
	public OnlineEnvironment createEnvironment(DbConnectionManagerBase connectionManager)
	{
		OnlineEnvironment env = new OnlineEnvironment(this, connectionManager) ; // from session
		env.resetApplicationCredentials(getApplicationCredentials()) ;
		return env;
	}
	
	public void RunProgram(BaseProgramLoader baseProgramLoader)
	{
		//StopWatch sw = new StopWatch();
		BaseEnvironment env = baseProgramLoader.GetEnvironment(this, null, null) ;
		
		boolean bStarted = env.startRunTransaction();
		if(!bStarted)
		{
			AbortSessionException e = new AbortSessionException();
			e.m_Reason = new Error("Could not start Transaction (maybe no DB connection)");
			e.m_ProgramName = env.getNextProgramToLoad();
			throw e;
		}
		
		prepareRunSessionProgram(env, null) ;
		try
		{
			baseProgramLoader.runTopProgram(env, null);
			env.endRunTransaction(CriteriaEndRunMain.Normal);
		}
		catch(AbortSessionException e)
		{
			env.endRunTransaction(CriteriaEndRunMain.Abort);
			throw e;
		}
		catch(Exception e)
		{
			env.endRunTransaction(CriteriaEndRunMain.Abort);
			throwAbortSession(e);
		}
		
		//long lms = sw.getElapsedTime();
		//Log.logVerbose("Programs run for " + lms + " ms"); 
	}
	
	private void throwAbortSession(Throwable e)
	{
		AbortSessionException exp = new AbortSessionException();
		exp.m_Reason = e;
		throw exp;
	}

	private void prepareRunSessionProgram(BaseEnvironment baseEnv, String defaultProgramName) throws AbortSessionException
	{	
		OnlineEnvironment env = (OnlineEnvironment)baseEnv;
		CBaseMapFieldLoader mapField = getInputWrapper() ;
		if (mapField != null)
		{
			String page = mapField.getFieldValue("page");
			if (page != null && !page.equals(""))
			{
				env.setCommarea(null);
				env.setNextProgramToLoad(page) ;
			}
			else
			{
				env.setXMLData(getXMLData()) ;
				KeyPressed key = getKeyPressed();
				if (key == null)
				{
					env.resetKeyPressed() ;
				}
				else
				{
					env.setKeyPressed(key);
				}
			}
		}
	}
	
	public void fillCurrentUserInfo(CurrentUserInfo currentUserInfo)
	{
		currentUserInfo.set(m_csLUName, m_csUserLdapId);
	}	

	public String getType()
	{
		return "Online";
	}
	
//	public void lock()
//	{
//		m_lock.lock();
//	}
	
//	public void unlock()
//	{
//		m_lock.unlock();
//	}
	
//	public boolean blockUntilLocked()
//	{
//		if(m_lock.isLocked())
//		{
//			m_lock.lock();
//			// Wait until thread that owns m_lock has released it 
//			m_lock.unlock();
//			return true;
//		}
//		return false;
//	}
	
	public boolean reserveSessionForCurrentThread()
	{
		if(!m_lock.tryLock())	// Could not atomically get the lock: the session is already running in another thread
		{
			m_lock.lock();
			// Wait until thos thread that owns m_lock has released it 
			m_lock.unlock();
			return false;
		}
		return true;
	}
	
	public void unreserveSession()
	{
		m_lock.unlock();
	}
	
	private StopWatch m_stopWatchNetwork = new StopWatch();
	public void startNetwork()
	{
		m_stopWatchNetwork.Reset();
	}
	public void stopNetwork(long clientElapsedTime)
	{
		if (clientElapsedTime == 0)
		{
			setNetwork_ms(0);
		}
		else
		{
			setNetwork_ms((int)(m_stopWatchNetwork.getElapsedTime() - clientElapsedTime));
			if (getNetwork_ms() < 0)
				setNetwork_ms(0);
		}
	}
	
	public ActionForward m_actionForward = null;
	private ReentrantLock m_lock = new ReentrantLock(); 
}
