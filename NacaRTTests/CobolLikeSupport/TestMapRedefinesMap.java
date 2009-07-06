/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 13 déc. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import nacaLib.varEx.*;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.mapSupport.*;

public class TestMapRedefinesMap extends Map 
{
	static TestMapRedefinesMap Copy(BaseProgram program) 
	{
		return new TestMapRedefinesMap(program);
	}
	
	TestMapRedefinesMap(BaseProgram program) 
	{
		super(program);
	}
	
	Form MainForm = declare.form("TestMapRedefinesMap", 24, 80) ;
		//Edit Fake = declare.edit("Fake", 5).edit();
		Edit E0 = declare.edit("E0", 5).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit E1 = declare.edit("E1", 5).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();

		LocalizedString str_E2 = declare.localizedString()
			.text("RS0501D", "050000")
			.text("RS0501F", "050001")
			.text("RS0501G", "050002")
			.text("RS0501I", "050003");
		Edit E2 = declare.edit("E2", 5).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			
		LocalizedString str_E3 = declare.localizedString()
			.text("RS0501D", "__PUB-Zürich_______")
			.text("RS0501F", "___PUB-Lausanne____")
			.text("RS0501G", "__PUB-LONDON_______")
			.text("RS0501I", "__PUB-Lausanne_____");

		Edit E3 = declare.edit("E3", 5).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		
		Edit E40 = declare.edit("E40", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom2 = declare.edit("I11", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom3 = declare.edit("J11", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom4 = declare.edit("I12", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom5 = declare.edit("J12", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom6 = declare.edit("I13", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom7 = declare.edit("J13", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom8 = declare.edit("I14", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom9 = declare.edit("J14", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit fNom10 = declare.edit("fNom10", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		
		Edit E41 = declare.edit("E41", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom11 = declare.edit("I21", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom12 = declare.edit("J21", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom13 = declare.edit("I22", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom14 = declare.edit("J22", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom15 = declare.edit("I23", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom16 = declare.edit("J23", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom17 = declare.edit("I24", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Edit fNom18 = declare.edit("J24", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit fNom19 = declare.edit("fNom19", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		
		Edit fNom20 = declare.edit("fNom20", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		
		Edit fGroup = declare.edit("fGroup", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			Var vGroupItem1 = declare.level(3).picX(5).var();
			Var vGroupItem2 = declare.level(3).picX(5).var();
	}
