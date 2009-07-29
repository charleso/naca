/*
 * NacaRTTests - Naca Tests for NacaRT support v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import jlib.log.Asserter;
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class TestWriteOnReadVars extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();

	Var varGroup = declare.level(1).var();	
		Var varHHMMSS = declare.level(5).pic9(6).var();
			Var varHH = declare.level(10).pic9(2).var();
			Var varMM = declare.level(10).pic9(2).var();
			Var varSS = declare.level(10).pic9(2).var();
	
	public void procedureDivision()
	{
		Asserter.setAssertActive(true);
		
		move("121314", varHHMMSS);
		assertIfFalse(varHHMMSS.equals(121314));
		move("23", varHH);
		assertIfFalse(varHH.equals(23));
		assertIfFalse(varHHMMSS.equals(231314));
		
		int n = varHHMMSS.getInt();
		int gg = 0;
	}
}
