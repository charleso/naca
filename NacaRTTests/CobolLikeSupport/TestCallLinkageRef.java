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
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;


/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import nacaLib.varEx.*;
import nacaLib.program.*;

public class TestCallLinkageRef extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
		Var Filler1 = declare.level(1).picX(10).var();
		Var WNumber1 = declare.level(1).pic9(2).var();
		Var WNumber2 = declare.level(1).pic9(2).var();
		Var WNbRecurs = declare.level(1).pic9(2).var();
		Var Filler2 = declare.level(1).picX(10).var();
		
	DataSection LinkageSection = declare.linkageSection();
		Var LS = declare.level(1).var();
			Var LSNumber = declare.level(2).pic9(2).var();
			Var LSNbRecurs = declare.level(2).pic9(2).var();
		
	ParamDeclaration param = declare.using(LS);	// Call parameters declaration
	
	Paragraph Main = new Paragraph(this){public void run(){Main();}};void Main()
	{
		// Check implicitly used commarea
		int n = LSNumber.getInt();
		int nNbRecurs = LSNbRecurs.getInt();
				
		n = n + 10;
		move(n, LSNumber);
		
		if(nNbRecurs > 0)
		{
			dec(LSNbRecurs);
			move(LSNumber, WNumber1);
			move(LSNbRecurs, WNumber2);
			call(TestCallLinkageRef.class)
				.using(WNumber1)		// Explicitly used in the called program !
				.using(WNumber2)
				.executeCall();
		}
		
		
		
//		if(nNbRecurs == 2)
			
	}	
}

