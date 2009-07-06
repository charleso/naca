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

public class CESMUserPermission
{
}

//public class CESMUserPermission
//{
//	public CESMUserPermission(CESMUserIdentity id)
//	{
//		m_Identity = id ;
//	}
//	protected CESMUserIdentity m_Identity = null ;
//	protected Vector m_arrRights = new Vector() ;
//	protected class UserRight
//	{
//		public String m_Description = "" ;
//		public String m_Application = "" ;
//		public String m_Grouping = "" ;
//		public String m_GroupingUpdate = "" ;
//		public String m_Update = "" ;
//	}
//	public boolean loadPermissions(CSQLConnection conn, String proj)
//	{
//		String req = "SELECT" +
//		  " CDPROJ, CDSTINI, CDCENPI, RECOLL, CDAPPLI, CDCONS, CDREGCO," +
//		  " CDMUT, CDREGMU, TERMID, DTVALD, DTVALF, DTMUT, REMUT " ;
//		if (proj.equals("RS"))
//		{
//			req += "FROM RS0104 " ;
//		}
//		else if (proj.equals("PG"))
//		{
//			req += "FROM PG0104 " ;
//		}
//		req += " WHERE CDPROJ = #1" +
//		  " AND CDSTINI = #2" +
//		  " AND CDCENPI = #3" +
//		  " AND RECOLL = #4" +
//		  " ORDER BY CDAPPLI";
//		SQL reqUser = new SQL(null, conn, req, false) ;
//		reqUser.param(1, proj) ;
//		reqUser.param(2, m_Identity.getCompany()) ;
//		reqUser.param(3, m_Identity.getAgency()) ;
//		reqUser.param(4, m_Identity.getId()) ;
//		CSQLResultSet result = reqUser.executeQuery() ;
//		
//		if (result != null)
//		{
//			ResultSet rs = result.getResultSet() ;
//			try
//			{
//				while (rs.next())
//				{
//					UserRight right = new UserRight() ;
//					String s = rs.getString("CDPROJ") ;
//					s = rs.getString("CDSTINI") ;
//					s = rs.getString("CDCENPI") ;
//					s = rs.getString("RECOLL") ;
//					s = rs.getString("CDAPPLI") ;
//					right.m_Application = s ;
//					s = rs.getString("CDCONS") ;
//					s = rs.getString("CDREGCO") ;
//					right.m_Grouping = s ;
//					if (!right.m_Grouping.equals(""))
//					{
//						m_Identity.setGrouping(right.m_Grouping) ;
//					}
//					s = rs.getString("CDMUT") ;
//					right.m_Update = s ;
//					s = rs.getString("CDREGMU") ;
//					right.m_GroupingUpdate = s ;
//					s = rs.getString("TERMID") ;
//					s = rs.getString("DTVALD") ;
//					s = rs.getString("DTVALF") ;
//					s = rs.getString("DTMUT") ;
//					s = rs.getString("REMUT") ;
//					right.m_Description = ReadApplicationDescription(right.m_Application, conn, proj) ; 
//					m_arrRights.add(right) ;
//				}
//				rs.close() ;
//			}
//			catch (SQLException e)
//			{
//				e.printStackTrace() ;
//				return false ;
//			}
//		}
//		else
//		{
//			return false ;
//		}
//		return true ;
//	}
//
//	private String ReadApplicationDescription(String app, CSQLConnection conn, String proj)
//	{
////		rs0323 = (Rs0323)checkCache(table, codeId + code + languageP);
////		if (rs0323 != null) {
////		  return;
////		}
//
//		String req = "SELECT HELDON, HELVAL, UTILNG, LICHMR5, LICHMRA, LICHMST, ANNCOD, LICHMCP " ; 
//		if (proj.equals("RS"))
//		{
//			req += " FROM RS0323 " ;
//		}
//		else if (proj.equals("PG"))
//		{
//			req += " FROM PG0323 " ;
//		}
//		else
//		{
//			return "" ;
//		}
//		req += " WHERE HELDON = #1" +
//		  " AND HELVAL = #2" +
//		  " AND UTILNG = #3";
//		SQL reqUser = new SQL(null, conn, req, false) ;
//		reqUser.param(1, "DICAPP");
//		reqUser.param(2, app);
//		reqUser.param(3, m_Identity.getLangCode());
//		CSQLResultSet result = reqUser.executeQuery() ;
//		String desc = "" ;
//		if (result != null)
//		{
//			ResultSet rs = result.getResultSet() ;
//			try
//			{
//				while (rs.next())
//				{
//					String s = rs.getString("HELDON") ;
//					s = rs.getString("HELVAL") ;
//					s = rs.getString("UTILNG") ;
//					s = rs.getString("LICHMR5") ;
//					s = rs.getString("LICHMRA") ;
//					s = rs.getString("LICHMST") ;
//					desc = s ;
//					s = rs.getString("ANNCOD") ;
//					s = rs.getString("LICHMCP") ;
//				}
//				rs.close() ;
//				return desc ;
//			}
//			catch (SQLException e)
//			{
//				e.printStackTrace() ;
//				return "" ;
//			}
//		}
//		else
//		{
//			return "" ;
//		}
//
//	} 
//
//}
