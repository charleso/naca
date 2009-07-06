/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.CESM;

/*
 * Created on 24 déc. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class CESMAgency
{
}

//public class CESMAgency
//{
//	public CESMAgency(String agency)
//	{
//		m_AgencyCode = agency ;
//	}
//	protected String m_AgencyCode = "" ;
//	protected String m_AgencyDesc = "" ;
//	public boolean Load(CSQLConnection conn, String proj, String date)
//	{
//		String req = "SELECT" +
//			" UTISTE, UTICPR, ADRNO, VALDATD, VALDATF, ADCGEN, UTISTSG," +
//			" ADRQAL, ADRTITC, ADRNOM, ADRPREN, ADRNOMC, ADRPRO, ADRIMMN, ADRIMMS," +
//			" ADRIMMC, ADRRUE, ADRFAC, ADRRUBO, ADRRUC, ADRNPL, ADRNPLC, ADRLOC," +
//			" ADRLOCQ, ADRLOCG, ADRLOCS, ADRCASN, ADRNPC, ADRNPCC, ADRLOCA, ADRLOQ," +
//			" ADRLOG, ADRLOS, ADRLOCE, ADRREG, ADRNPE, ADRPAYN, ADRPAYS, ADRPAYL," +
//			" ADRTELI, ADRTELN, ADRTELE, ADRTEFI, ADRTEFN, ADRTLXN, ADRTLXE, ADRVDXI," +
//			" ADRVDXN, ADRCCP, UTILNG, ADCLIBP, ADCLIBE, ANNCOD, MUTDAT, MUTREF," +
//			" MUTCPR, SAIDAT, SAIREF, SAICPR, DICVER, ADRNOMZ, ADRLOCZ, UTIAVD," +
//			" TVAADH, ADRCTL, ADRCTC, ADRWEB, ADRMAIL, UTICPHS, UTINIV" ;
//		if (proj.equals("RS"))
//		{
//			req += " FROM RS7801" ;
//		}
//		else
//		{
//			req += " FROM VI7801" ;
//		} 
//		req += " WHERE UTISTE = #1" +
//			" AND UTICPR = #2" +
//			" AND ADRNO = #3" +
//			" AND VALDATD <= #4" +
//			" AND VALDATF >= #5";
//		SQL reqUser = new SQL(null, conn, req, false) ;
//		reqUser.param(1, m_AgencyCode.substring(0, 2)) ;
//		reqUser.param(2, m_AgencyCode.substring(2, 5)) ;
//		reqUser.param(3, 0) ;
//		reqUser.param(4, new Integer(date).intValue()) ;
//		reqUser.param(5, new Integer(date).intValue()) ;
//		CSQLResultSet result = reqUser.executeQuery() ;
//	
//		if (result != null)
//		{
//			ResultSet rs = result.getResultSet() ;
//			try
//			{
//				rs.next();
//				String s = rs.getString("ADCLIBP") ;
//				m_AgencyDesc = s ;
//				rs.close() ;
//			}
//			catch (SQLException e)
//			{
//				e.printStackTrace();
//				return false ;
//			}
//		}
//		else
//		{
//			return false ;
//		}
//		return true ;
//	}
//}
