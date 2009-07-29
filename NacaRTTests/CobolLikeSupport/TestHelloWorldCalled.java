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
/**
 * 
 */

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
import nacaLib.callPrg.CalledProgram;
import nacaLib.program.Paragraph;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.ParamDeclaration;
import nacaLib.varEx.Var;

public class TestHelloWorldCalled extends CalledProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var varHelloWorld = declare.level(1).var();
		Var hello = declare.level(5).picX(5).var();
		Var space = declare.level(5).picX(1).value(" ").filler();
		Var world = declare.level(5).picX(5).var();
		Var count = declare.level(5).pic9(4).var();
		
	public DataSection LinkageSection = declare.linkageSection();
		public Var LSRoot = declare.level(1).var();
			public Var LSResult = declare.level(2).pic9(4).var();
			public Var LSV1 = declare.level(2).pic9(3).var();
			public Var LSV2 = declare.level(2).pic9(3).var();
		public Var LSCountToReturn = declare.level(1).pic9(4).var();
	
	public ParamDeclaration c = declare.using(LSRoot).using(LSCountToReturn);

	public void procedureDivision()
	{
		move("Hello", hello);
		move("World", world);
		move(LSResult, count);
		perform(DisplayHelloWorld);
		move(count, LSCountToReturn);
		inc(LSCountToReturn);
		
		stopRun();
	}
	
	Paragraph DisplayHelloWorld = new Paragraph(this){public void run(){DisplayHelloWorld();}};void DisplayHelloWorld()
	{
		setAssertActive(true);
		
		display(varHelloWorld);
		display(" Count=");
		display(count);
	}
}

