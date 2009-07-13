/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;
 
/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CallParamByStringValue extends CCallParam
{
 public CallParamByStringValue(String cs)
 {
  m_cs = cs;
 }
 
 public int getParamLength()
 {
  if(m_cs != null)
   return m_cs.length();
  return 0;
 }
 
 public void MapOn(Var varLinkageSection)
 {
  varLinkageSection.set(m_cs);
 }
 
 public Var getCallerSourceVar()
 {
  return null;
 }
 
 private String m_cs = null;
}