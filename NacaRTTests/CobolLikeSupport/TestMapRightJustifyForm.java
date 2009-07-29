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
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.mapSupport.Map;
import nacaLib.mapSupport.MapFieldAttrColor;
import nacaLib.mapSupport.MapFieldAttrFill;
import nacaLib.mapSupport.MapFieldAttrHighlighting;
import nacaLib.mapSupport.MapFieldAttrIntensity;
import nacaLib.mapSupport.MapFieldAttrJustify;
import nacaLib.mapSupport.MapFieldAttrProtection;
import nacaLib.varEx.Edit;
import nacaLib.varEx.Form;

public class TestMapRightJustifyForm extends Map
{
	static TestMapRightJustifyForm Copy(BaseProgram program)
	{
		return new TestMapRightJustifyForm(program); 
	}

	TestMapRightJustifyForm(BaseProgram program)	// Same as a DFHMDI macro
	{
		super(program);
	}
	
	Form Screen = declare.form("TestMapRightJustify", 80, 24);
		Edit editGenre = declare.edit("editGenre", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editNom = declare.edit("editNom", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justifyRight().justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit edit1 = declare.edit("editNom", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit edit2 = declare.edit("editNom", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit edit3 = declare.edit("editNom", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit edit4 = declare.edit("editNom", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit edit5 = declare.edit("editNom", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
}
