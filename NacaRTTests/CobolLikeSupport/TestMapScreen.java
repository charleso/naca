/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import nacaLib.varEx.*;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.mapSupport.*;


public class TestMapScreen extends Map
{
	public static TestMapScreen Copy(BaseProgram program)
	{
		return new TestMapScreen(program); 
	}

	public TestMapScreen(BaseProgram program)	// Same as a DFHMDI macro
	{
		super(program);
	}
	
	public Form ScreenDef = declare.form("TestMapScreen", 80, 24);
		public Edit editNom = declare.edit("editNom", 20).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		public Edit editCompany = declare.edit("editAge", 20).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		public Edit editPassword = declare.edit("editPassword", 20).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		public Edit editBirthDate = declare.edit("editBirthDate", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
			public Var JJ = declare.level(10).pic9(2).var();
			public Var filler_1 = declare.level(10).picX().value("/").filler();
			public Var MM = declare.level(10).pic9(2).var();
			public Var filler_2 = declare.level(10).picX().value("/").filler();
			public Var AAAA = declare.level(10).pic9(4).var();

		// Edit attributes
		public Edit editLeftBlank = declare.edit("editLeftBlank", 20).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		public Edit editRightBlank = declare.edit("editRightBlank", 20).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.RIGHT).justifyFill(MapFieldAttrFill.BLANK).edit();
		public Edit editLeftZero = declare.edit("editLeftZero", 20).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.ZERO).edit();
		public Edit editRightZero = declare.edit("editRightZero", 20).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.RIGHT).justifyFill(MapFieldAttrFill.ZERO).edit();
	
		public Edit editZ = declare.edit("editZ", 5).format("ZZZZZ").color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.RIGHT).justifyFill(MapFieldAttrFill.ZERO).edit();
		public Edit editNotZ = declare.edit("editNotZ", 5).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.RIGHT).justifyFill(MapFieldAttrFill.ZERO).edit();
		

		//Edit arrEdits = declare.edit("edit", 15).occurs(5);
}

