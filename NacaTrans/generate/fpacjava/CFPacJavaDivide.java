/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityDivide;
import utils.CObjectCatalog;

public class CFPacJavaDivide extends CEntityDivide {

	public CFPacJavaDivide(int line, CObjectCatalog cat, CBaseLanguageExporter out) {
		super(line, cat, out);
	}

	@Override
	protected void DoExport() {
		WriteWord("divide(") ;
		WriteWord(this.m_What.ExportReference(getLine())) ;
		WriteWord(", ") ;
		WriteWord(this.m_By.ExportReference(getLine())) ;
		WriteWord(").to(") ;
		WriteWord(this.m_Result.ExportReference(getLine())) ;
		
		WriteWord(") ;") ;
		WriteEOL() ;
	}

}
