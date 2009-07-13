/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.varEx;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: OccursDefBase.java,v 1.1 2006/04/19 09:53:08 cvsadmin Exp $
 */
public abstract class OccursDefBase
{
	public abstract int getNbOccurs();
	public abstract void prepareAutoRemoval();
	public abstract Var getRecordDependingVar();
}
