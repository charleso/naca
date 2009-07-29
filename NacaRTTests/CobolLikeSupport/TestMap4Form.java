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
/*
 * Created on 23 sept. 04
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


public class TestMap4Form extends Map
{
	static TestMap4Form Copy(BaseProgram program)
	{
		return new TestMap4Form(program); 
	}

	TestMap4Form(BaseProgram program)	// Same as a DFHMDI macro
	{
		super(program);
	}
	
	Form Screen = declare.form("TestMap4Form", 80, 24);
		Edit editGenre = declare.edit("editGenre", 3).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editNom = declare.edit("editNom", 20).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		
		Edit editPrenoma = declare.edit("editPrenoma", 20).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).semanticContext("SEM_editPrenoma").edit();
		Edit editAdr1a = declare.edit("editAdr1a", 20).color(MapFieldAttrColor.RED).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editTel1a = declare.edit("editTel1a", 20).color(MapFieldAttrColor.GREEN).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editFax1a = declare.edit("editFax1a", 20).color(MapFieldAttrColor.YELLOW).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editAdr2a = declare.edit("editAdr2a", 20).color(MapFieldAttrColor.BLUE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editTel2a = declare.edit("editTel2a", 20).color(MapFieldAttrColor.PINK).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editFax2a = declare.edit("editFax2a", 20).color(MapFieldAttrColor.NEUTRAL).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();

		Edit editPrenomb = declare.edit("editPrenomb", 20).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editAdr1b = declare.edit("editAdr1b", 20).color(MapFieldAttrColor.RED).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editTel1b = declare.edit("editTel1b", 20).color(MapFieldAttrColor.GREEN).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editFax1b = declare.edit("editFax1b", 20).color(MapFieldAttrColor.YELLOW).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editAdr2b = declare.edit("editAdr2b", 20).color(MapFieldAttrColor.BLUE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editTel2b = declare.edit("editTel2b", 20).color(MapFieldAttrColor.PINK).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editFax2b = declare.edit("editFax2b", 20).color(MapFieldAttrColor.NEUTRAL).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();

		Edit editPrenomc = declare.edit("editPrenomc", 20).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editAdr1c = declare.edit("editAdr1c", 20).color(MapFieldAttrColor.RED).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editTel1c = declare.edit("editTel1c", 20).color(MapFieldAttrColor.GREEN).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editFax1c = declare.edit("editFax1c", 20).color(MapFieldAttrColor.YELLOW).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editAdr2c = declare.edit("editAdr2c", 20).color(MapFieldAttrColor.BLUE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editTel2c = declare.edit("editTel2c", 20).color(MapFieldAttrColor.PINK).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editFax2c = declare.edit("editFax2c", 20).color(MapFieldAttrColor.NEUTRAL).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();

		Edit editDateNais = declare.edit("editDateNais", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editSalaire = declare.edit("editSalaire", 6).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit editItem = declare.edit("editItem", 13).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
}

