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
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.mapSupport.*;

public class TestWorkingLevelForm extends Map
{
	static TestWorkingLevelForm Copy(BaseProgram program) 
	{
		return new TestWorkingLevelForm(program);
	}
	
	TestWorkingLevelForm(BaseProgram program) 
	{
		super(program);
	}
	                                                                            // (2) 
	Form MainForm = declare.form("form", 24, 80);
		LocalizedString str_group = declare.localizedString()
			.text("RS0501D", "050000")
			.text("RS0501F", "050001")
			.text("RS0501G", "050002")
			.text("RS0501I", "050003");
		Edit fGroup1 = declare.edit("fGroup1", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
		Edit fGroup2 = declare.edit("fGroup2", 10).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).protection(MapFieldAttrProtection.AUTOSKIP).intensity(MapFieldAttrIntensity.NORMAL).justify(MapFieldAttrJustify.LEFT).justifyFill(MapFieldAttrFill.BLANK).edit();
}
