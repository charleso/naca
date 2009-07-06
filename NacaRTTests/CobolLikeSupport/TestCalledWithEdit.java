/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import nacaLib.varEx.*;
import nacaLib.callPrg.CalledProgram;
import nacaLib.program.*;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


public class TestCalledWithEdit extends CalledProgram
{
	nacaLib.varEx.DataSection WorkingStorage = declare.workingStorageSection();
	
	Var WSFill = declare.level(1).picX(52).var();
	
	DataSection LinkageSection = declare.linkageSection();
		Var LSEdit = declare.level(1).picX(20).var();
	
	ParamDeclaration c = declare.using(LSEdit);
		
	Paragraph Main = new Paragraph(this){public void run(){Main();}};void Main()
	{
		assertIfFalse(isEqual(LSEdit, "12345678901234567890"));	// ((10*20)/3) + 5
		move("98765432109876543210", LSEdit);
	}	
}
