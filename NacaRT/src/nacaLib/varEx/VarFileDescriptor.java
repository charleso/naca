/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.basePrgEnv.FileManagerEntry;

public class VarFileDescriptor extends FileDescriptor {

	private final BaseEnvironment env;
	private final Var var;

	public VarFileDescriptor(BaseEnvironment env, Var var)
	{
		super(env, "");
		this.env = env;
		this.var = var;
		m_fileManagerEntry = new VarFileManagerEntry();
	}

	private String getVarName() {
		return m_csLogicalName = var.getString().trim();
	}

	@Override
	public String getLogicalName()
	{
		if("".equals(m_csLogicalName)) {
			m_fileManagerEntry = env.getFileManagerEntry(getVarName());
		}
		return super.getLogicalName();
	}

	public class VarFileManagerEntry extends FileManagerEntry
	{
		@Override
		public String getPhysicalName(String csLogicalName, BaseSession session)
		{
			return super.getPhysicalName(getVarName(), session);
		}
	}
}
