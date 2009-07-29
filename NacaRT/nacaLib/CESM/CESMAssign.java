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
package nacaLib.CESM;

import idea.onlinePrgEnv.OnlineEnvironment;
import idea.onlinePrgEnv.OnlineSession;
import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.varEx.Var;

public class CESMAssign extends CJMapObject {
	private BaseEnvironment m_env=null;
	
	public CESMAssign(BaseEnvironment env) {
		m_env=env;
	}
	public CESMAssign OPID(Var opid) {
		if (m_env==null) return this;
		opid.set(m_env.getUserId());
		return this;
	}
	public CESMAssign APPLID(Var licic) {
		if (m_env==null) return this;
		BaseSession session=m_env.getBaseSession();
		if (session instanceof OnlineSession) {
			OnlineSession oSess=(OnlineSession)session;
			licic.set(oSess.getServerName());
		}
		return this;
	}

}
