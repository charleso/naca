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
import nacaLib.varEx.*;
import nacaLib.callPrg.CalledProgram;
import nacaLib.program.*;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestCalledWithString extends CalledProgram
{
	public nacaLib.varEx.DataSection WorkingStorage = declare.workingStorageSection();
	
	public Var WSFill = declare.level(1).picX(5).value("ABCDE").var();
	public Var WSCount = declare.level(1).pic9(5).value(10).var();
	public Var WSX = declare.level(1).picX(5).var();
	public Var WS9 = declare.level(1).pic9(5).var();
	
	public DataSection LinkageSection = declare.linkageSection();
		public Var LSRoot = declare.level(1).picX(10).var();
		
	public ParamDeclaration c = declare.using(LSRoot);
	public Paragraph Main = new Paragraph(this){public void run(){Main();}};void Main()
	{
		display("TestCalledWithString: "+LSRoot.getString());
		inc(WSCount);
	}	
}
