/*
 * NacaRTTests - Naca Tests for NacaRT support v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
// Contributed by Charles O'Farell

import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;

public class Goto extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	Var counter = declare.level(1).pic9(3).comp3().var();
	Var maxCounter = declare.level(1).pic9(3).comp3().var();
	
	Var text = declare.level(1).var();
		Var text1 = declare.level(5).picX(12).var();
		Var text2 = declare.level(5).picX(8).value("; count=").var();
		Var text3 = declare.level(5).picX(3).var();
	
	DataSection workingstoragesection = declare.workingStorageSection();

	Section $1 = new Section(this, false);
	Paragraph a = new Paragraph(this);

	public void a() 
	{
		display("a");
		perform($2);
		perform($4);
	}

	Section $7 = new Section(this, false);
	Paragraph i = new Paragraph(this);

	public void i() 
	{
		display("i");
		exitProgram();
	}

	Section $4 = new Section(this, false);
	Paragraph d = new Paragraph(this);

	public void d() 
	{
		display("d");
		goTo(e);
	}

	Section $6 = new Section(this, false);
	Paragraph g = new Paragraph(this);

	public void g() 
	{
		display("g");
	}

	Paragraph h = new Paragraph(this);

	public void h() 
	{
		display("h");
	}

	Section $5 = new Section(this, false);
	Paragraph e = new Paragraph(this);

	public void e() 
	{
		display("e");
	}

	Paragraph f = new Paragraph(this);

	public void f() 
	{
		display("f");
		goTo(g);
	}

	Section $2 = new Section(this, false);
	Paragraph b = new Paragraph(this);

	public void b() 
	{
		display("b");
		goTo(c);
	}

	Section $3 = new Section(this, false);
	Paragraph c = new Paragraph(this);

	public void c()
	{
		display("c");
	}
}

