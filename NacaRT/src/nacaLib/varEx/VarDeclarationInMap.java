/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 7 janv. 05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.varEx;

import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.mapSupport.*;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class VarDeclarationInMap extends VarDeclaration 
{
	public VarDeclarationInMap(BaseProgram prg, Map mapOwner)
	{
		super(prg);
		m_mapOwner = mapOwner;
	}
	
	private VarDefForm m_curDefForm = null;
	private Form m_curVarForm = null;
	private Map m_mapOwner = null;
	
	public DeclareTypeEditInMap edit(String csName, int nWidth)
	{
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		VarLevel varlevel = tempCache.getVarLevel();
		varlevel.set(m_Program, 2);
		//VarLevel varlevel = new VarLevel(m_Program, 2);
		DeclareTypeEditInMap declareTypeEdit = tempCache.getDeclareTypeEditInMap();
		declareTypeEdit.set(varlevel, m_curVarForm, m_curDefForm, csName, nWidth);
		return declareTypeEdit;
	}
		

//		Var varParent = m_Program.getProgramManager().getVarAtParentLevel(2);
//		if(varParent != null)
//		{
//			Edit edit = new Edit(varParent, csName, nWidth);
//			m_curForm.addField(edit);
//			return edit;
//		}
//		return null;
//	}

	public Form form(String csName, int line, int col)
	{
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		VarLevel varlevel = tempCache.getVarLevel();
		varlevel.set(m_Program, 1);

		DeclareTypeForm declareTypeForm = tempCache.getDeclareTypeForm();
		declareTypeForm.set(varlevel);
		
		Form varForm = new Form(declareTypeForm, csName);
		m_curDefForm = varForm.getDefForm();
		m_curVarForm = varForm;
		if(m_mapOwner != null)
			m_mapOwner.registerForm(m_curDefForm);
		
		return varForm;
		
		
//		Var varParent = m_Program.getProgramManager().getVarAtParentLevel(1);	// The forms are at level depth 1
//		if(varParent != null)
//		{
//			Form form = new Form(varParent, csName, col, line);	//, m_tabLocalizedTexts);
//			m_curForm = form;
//			if(m_mapOwner != null)
//				m_mapOwner.registerForm(form);
//			return form;
//		}
//		return null;
	}
	
	public LocalizedString localizedString()
	{
		LocalizedString lcs = new LocalizedString();
		return lcs ;
	}

}
