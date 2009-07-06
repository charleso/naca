/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
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
package nacaLib.mapSupport;

import java.util.ArrayList;

import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.varEx.VarDeclarationInMap;
import nacaLib.varEx.VarDefForm;

public class Map extends CJMapObject
{	
	public Map(BaseProgram program)
	{
		declare = new VarDeclarationInMap(program, this);
		m_arrDefForms = new ArrayList<VarDefForm>();
	}
		
	public void registerForm(VarDefForm varDefForm)
	{
		if(varDefForm != null)
			m_arrDefForms.add(varDefForm);
	}
		
//	public void move(CobolConstant constant)
//	{
//		char cValue = constant.getValue();
//		Form form = null;
//		
//		// All form's edit and labels are init at low value, including all children
//		for(int nForm=0; nForm<m_arrForms.size(); nForm++)
//		{
//			form = (Form) m_arrForms.get(nForm);
//			//form.set(cValue);
//		}
//	}

	private ArrayList<VarDefForm> m_arrDefForms = null;		// Array of VarDefForm
	public VarDeclarationInMap declare = null;
}
