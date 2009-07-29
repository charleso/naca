/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.Helpers;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import jlib.misc.StringUtil;

/**
 * Thin layer class for encapsulating some of the JDBC features.
 * Methods in this class are quasi-direct calls to JDBC packages. Features are:
 * <ul>
 * 	<li>By extending the class, it is easy to place the connection configuration retrieval
 * 	directly in the class constructor:
 * 	<pre>
 * class MyDb extends Db {
 * 	public MyDb() {
 * 		PropertyLoader pl = new PropertyLoader();
 * 		driver=pl.getProperties().getProperty("MYDb.driver");
 * 		connectionString=pl.getProperties().getProperty("MYDb.connectionString");
 * 		user=pl.getProperties().getProperty("MYDb.user");
 * 		password=pl.getProperties().getProperty("MYDb.password");
 * 		this.connectTo(driver,connectionString,user,password);
 *  }
 * }
 * 	</pre></li>
 * 	<li>SQL statements can be written as simple strings. Only variable parts have to be tagged. This makes
 * 	the source code more readable:
 * 	<pre>
 * 	db=new MyDb();
 * 	String id;
 * 	ResultSet rcs=db.getResultSet("select * from myTable where id={s"+id+"}");
 * 	if (rcs.next()) {
 * 		String a1=rcs("a1");
 * 		String a2=rcs("a2");
 * 		. . .
 * 		. . .
 * 	}
 * 	db.closeResultSet(rcs);
 * 	</pre></li>
 * </ul>
 * 
 * @author U930GN
 */
public class Db {
// Requête utile pour contrôler le nombre de curseurs ouverts:
//	SELECT sum(v.value) AS numopencursors,
//       substr(s.machine,1,20) as machine,
//       substr(s.osuser,1,10) as osuser,
//       substr(s.username,1,10) as username
//  FROM V$SESSTAT v, V$SESSION s   
//  WHERE v.statistic# = 3 
//    and v.sid = s.sid 
//  GROUP by s.machine,s.osuser,s.username
//******************************************************************************
//**                      Class properties.                                   **
//******************************************************************************

//....................... The connection string ................................
/**
 * The JDBC connection string to use.
 */
	protected String _conString;
/**
 * The JDBC connection string to use.
 */
	public String getConString() {
		return _conString;
	}
/**
 * The JDBC connection string to use.
 */
	public void setConString(String conString) throws Exception {
		if (!_conString.equals(conString)) cleanConnection();
		_conString=conString;
	}

//............................ The driver ......................................
/**
 * The JDBC name of the database driver to use.
 */
	protected String _driver;
/**
 * The JDBC name of the database driver to use.
 */
	public String getDriver(){
		return _driver;
	}
/**
 * The JDBC name of the database driver to use.
 */
	public void setDriver(String driver) throws Exception {
		if (!_driver.equals(driver)) cleanConnection();
		_driver=driver;
	}

//............................... The user name ..............................
/**
 * A user with rights to log in the database.
 */
	protected String _user;
/**
 * A user with rights to log in the database.
 */
	public String getUser(){
		return _user;
	}
/**
 * A user with rights to log in the database.
 */
	public void setUser(String user) throws Exception {
		if (!_user.equals(user)) cleanConnection();
		_user=user;
	}

//............................... The password ..................................
/**
 * The password for the specified <code>user</code>.
 */
	protected String _password;
/**
 * The password for the specified <code>user</code>.
 */
	public String getPassword() {
		return _password;
	}
/**
 * The password for the specified <code>user</code>.
 */
	public void setPassword(String password) throws Exception {
		if (!_password.equals(password)) cleanConnection();
		_password=password;
	}
	
//******************************************************************************
//**                       Class variables.                                   **
//******************************************************************************
/**
 * The current connection to a database.
 * All SQL commands are sent to this connection.
 */
	protected Connection _connection=null;

/**
 * The current connection to a database.
 * All SQL commands are sent to this connection.
 */
	public Connection getConnection() {
		return _connection;
	}

/**
 * The current connection to a database.
 * All SQL commands are sent to this connection.
 */
	public void setConnection(Connection connection) {
		_connection=connection;
	}

//*****************************************************************************
//**                Prepares a SQL statement.                                **
//*****************************************************************************
/**
 * Prepares a SQL statement.
 * The sql request is specified as a simple string, as it would be when typed
 * on a command line console. Variable sections can be enclosed withing 
 * brackets <code>{x...}</code>, where:
 * <ul>
 * 	<li><b><code>x</code></b> represents the value type. Supported types are:
 * 		<ul>
 * 			<li><code>s</code> A string.</li>
 * 			<li><code>n</code> A number (integer or decimal).</li>
 * 			<li><code>d</code> A date.</li>
 * 		</ul></li>
 *	<li><b><code>...</code></b> Represents the value, as a plain string.</li>
 * </ul>
 * For example: 
 * <pre>
 * select * from table where field1={sA string} and field2={n1020} 
 * </pre>
 * This method is called by {@link #getResultSet}, {@link #getResultSetCount} and {@link #executeSQL}.
 * @return The precompiled statement corresponding to the specified <code>sql</code> statement.
 * @exception {@link SQLException} When an exception is returned by the database.
 * @exception {@link Exception} When an unexpected error happens.
 */
	protected PreparedStatement prepareStatement(String sql) throws Exception {
		int n0,n1,n2;
		StringBuffer parametrizedSql;
		ArrayList <String>parameters;
		String parameter;
		PreparedStatement preparedStatement;
		char parameterPrefix;
		try { 
//.............................. Initialisation ...............................
			if (_connection==null) 
				connectTo();

			parametrizedSql=new StringBuffer();
			parameters=new ArrayList<String>();

//.............. Replaces sections between {} by parameteres .................
			n0=0;
			for(;;) {
// Looks for a parameters in the SQL string:
				n1=sql.indexOf('{',n0);
				if (n1<0)
					break;
				if (n0>0)
					if (sql.charAt(n0-1)=='\\')
						break;

// Looks for the end of the parameter.
				n2=sql.indexOf('}', n1);
				if (n2<0)
					throw new Exception("Char '{' at position "+n1+" is not completed with a '}'.");

// Isolates the paramters, and checks its validity.
				parameter=sql.substring(n1+1,n2);
				if (parameter.indexOf('{')>=0 || parameter.indexOf('}')>=0)
					throw new Exception("Parameter starting at position "+n1+" contains nested '{'.");

// Adds the paramter in the collection.
				parameters.add(parameter);

// Completes the SQL request with a place holder for the paramter.
				parametrizedSql.append(sql.substring(n0,n1));
				parametrizedSql.append('?');

// Next paramter:
				n0=n2+1;
			}

// Completes the SQL request with the bit after the last parameter.
			parametrizedSql.append(sql.substring(n0));

//....................... Compiles the statement .........................................
			sql=parametrizedSql.toString();
			preparedStatement=_connection.prepareStatement(sql);

//.................... Adds the parameters to the prepared statement .....................
			n1=parameters.size();
			for(n0=0;n0<n1;n0++) {
				parameter=parameters.get(n0);
				parameterPrefix=parameter.charAt(0);
				switch(parameterPrefix) {
// If it is a number:
					case 'n':
						double nn;
						try {
							nn=Double.parseDouble(parameter.substring(1));
						} catch (NumberFormatException e) {
							throw new Exception("Parameter "+n0+" ('"+parameter+"') is not correctly formatted for a number:'"+e.getMessage()+"'");
						}
						preparedStatement.setDouble(n0+1,nn);
						break;

// If it is a Date / Time:
					case 'd':
						Calendar cal;
						try {
							cal=StringUtil.StringToCalendar(parameter.substring(1));
						} catch (Exception e) {
							throw new Exception("Parameter "+n0+" ('"+parameter+"') could not be transformed to a date:"+e.getMessage());
						}
						java.sql.Date date=new Date(cal.getTimeInMillis());
						preparedStatement.setDate(n0+1, date);
						break;

// If it is a string:
					case 's':
						preparedStatement.setString(n0+1,parameter.substring(1));
						break;
					default:
						throw new Exception("Parameter "+n0+" ('"+parameter+"') is not of a valid type. Valid type should prefix the parameter value. Accepted types are 'd' (date), 'n' (numeric) or 's' (string). Not '"+parameterPrefix+"'.");
				}
			}

//.......................... Returns the prepared statement with paramters set ...........
			return preparedStatement;
		}

//************************************* Exception management *****************************
		catch (SQLException e) {
			throw new SQLException(e.getMessage()+" while preparing '"+sql+"'",e.getSQLState(),e.getErrorCode());
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("prepareStatement('"+sql+"')", e));
		}
	}

//******************************************************************************
//**                         Constructeurs.                                   **
//******************************************************************************

//......................... Constructeur par défaut ............................
/**
 * The default constructor.
 * After creating the instance, {@link #connectTo} has to be called.
 */
	public Db() {
	}

//.................. Constructeur avec paramètres de connection ................
/**
 * Immediately establishes a connection to the specified database.
 * The specified parameters are copied into the properties {@link #_driver}, 
 * {@link #_conString}, {@link #_driver} and {@link #_password}.
 */
	public Db(String driver,String conString,String user,String password) throws Exception {
		connectTo(driver,conString,user,password);
	}

//******************************************************************************
//**              Crée une connection à partir d'une chaine de connection.    **
//******************************************************************************

//*************** Crée une connection à partir des proprietés de la classe *****
/**
 * Establishes a new connection to a database.
 * The connection parameters are set in the properties {@link #_driver}, {@link #_conString},
 * {@link #_driver} and {@link #_password}.
 */
	public void connectTo() throws Exception {
		try {
			if (_driver==null) throw new Exception("Property \"Driver\" is not initialized.");
			if (_driver.length()==0) throw new Exception("Property \"Driver\" is empty.");
			if (_conString==null) throw new Exception("Property \"ConString\" is not initialized.");
			if (_conString.length()==0) throw new Exception("Property \"\" is empty.");
			if (_user==null) throw new Exception("Property \"User\" is not initialized.");
			if (_user.length()==0) throw new Exception("Property \"User\" is empty.");
			if (_password==null) throw new Exception("Property \"Password\" is not initialized.");
			if (_password.length()==0) throw new Exception("Property \"Password\" is empty.");
			connectTo(_driver,_conString,_user,_password);
		}
		catch(Exception e) {
			throw new Exception(ParseError.parseError("Db.connectTo",e));
		}
	}

//*********************************** Crée une connection **********************
/**
 * Establishes a new connection to the specified database.
 * The specified parameters are copied into the properties {@link #_driver}, 
 * {@link #_conString}, {@link #_driver} and {@link #_password}.
 * @param driver The JDBC name of the database driver to use.
 * @param conString The JDBC connection string to use.
 * @param user A user with rights to log in the database.
 * @param password The password for the specified <code>user</code>.
 */
	public void connectTo(String driver,String conString,String user,String password) throws Exception {
		try {
//................................. Class initialization .......................
			cleanConnection();
			_driver=driver;
			_conString=conString;
			_user=user;
			_password=password;
//...................... Loads the driver ......................................
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				throw new Exception(driver+" not found.");
			}    

//........................ Establishes a new connection ........................
			cleanConnection();
			_connection=DriverManager.getConnection(_conString,user,password);
		}

//****************************** Exception management **************************
		catch (SQLException e) {
			throw new SQLException(e.getMessage()+" while connecting to '"+driver+"','"+conString+"','"+user+"','"+password+"'",e.getSQLState(),e.getErrorCode());
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("Db.ConnectTo('"+driver+"','"+conString+"','"+user+"','"+password+"')",e));	
		}
	}

//*****************************************************************************
//**                 Ferme la connection à la BD.                            **
//*****************************************************************************
/**
 * Closes the connection to the database.
 */
	public void cleanConnection() throws SQLException {
		if (_connection!=null) {
			_connection.close();
			_connection=null;
		}	
	}

//*****************************************************************************
//**      Returns the number of rows selected by the specified sql statement.**
//*****************************************************************************
/**
 * Returns the number of rows selected by the specified sql statement.
 * The sql request is specified as a simple string, as it would be when typed
 * on a command line console. To take profit of the sql engine precompiler,
 * variable sections can be enclosed withing brackets <code>{x...}</code>, where:
 * <ul>
 * 	<li><b><code>x</code></b> represents the value type. Supported types are:
 * 		<ul>
 * 			<li><code>s</code> A string.</li>
 * 			<li><code>n</code> A number (integer or decimal).</li>
 * 			<li><code>d</code> A date.</li>
 * 		</ul></li>
 *	<li><b><code>...</code></b> Represents the value, as a plain string.</li>
 * </ul> 
 * @param sql A <code>SELECT</code> sql statement.
 * @return The number of rows selected by the specified <code>SELECT</code> statement.
 * use the {@link #closeResultSet} method.
 * @exception {@link SQLException} When an exception is returned by the database.
 * @exception {@link Exception} When an unexpected error happens.
 */
	public int getResultSetCount(String sql) throws Exception {
		String countSql;
		ResultSet rs;
		int n;
		try {
			countSql="select count(*) as c from (\n"+sql+")";
			rs=getResultSet(countSql);
			rs.next();
			n=rs.getInt("c");
			rs.close();
			return n;
		}
		catch(SQLException e) {
			throw new SQLException(e.getMessage()+" while selecting '"+sql+"'",e.getSQLState(),e.getErrorCode());
		}
		catch(Exception e) {
			throw new Exception(ParseError.parseError("Db.getResultSetCount('"+sql+"')",e));		
		}
	}

//*****************************************************************************
//**          Returns a result set open on the specified sql request.        **
//*****************************************************************************
/**
 * Returns a result set open on the specified sql request.
 * The sql request is specified as a simple string, as it would be when typed
 * on a command line console. To take profit of the sql engine precompiler,
 * variable sections can be enclosed withing brackets <code>{x...}</code>, where:
 * <ul>
 * 	<li><b><code>x</code></b> represents the value type. Supported types are:
 * 		<ul>
 * 			<li><code>s</code> A string.</li>
 * 			<li><code>n</code> A number (integer or decimal).</li>
 * 			<li><code>d</code> A date.</li>
 * 		</ul></li>
 *	<li><b><code>...</code></b> Represents the value, as a plain string.</li>
 * </ul> 
 * This is an example:
 * <pre>
 * 	db=new MyDb();
 * 	String id;
 * 	ResultSet rcs=db.getResultSet("select * from myTable where id={s"+id+"}");
 * 	if (rcs.next()) {
 * 		String a1=rcs("a1");
 * 		String a2=rcs("a2");
 * 		. . .
 * 		. . .
 * 	}
 * 	db.closeResultSet(rcs);
 * </pre>
 * @param A <code>SELECT</code> sql statement.
 * @return A result set open on the specified sql request. To close the cursor,
 * use the {@link #closeResultSet} method.
 * @exception {@link SQLException} When an exception is returned by the database.
 * @exception {@link Exception} When an unexpected error happens.
 */
	public ResultSet getResultSet(String sql) throws Exception {
		PreparedStatement preparedStatement;
		ResultSet rcs=null;
		try {
			preparedStatement=prepareStatement(sql);
			rcs=preparedStatement.executeQuery();
			return rcs;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage()+" while selecting '"+sql+"'",e.getSQLState(),e.getErrorCode());
		}
		catch(Exception e) {
			throw new Exception(ParseError.parseError("Db.getResultSet('"+sql+"')",e));		
		}
	}

//*********************************************************************************
//**             Closes the result set opened with getResultSet                  **
//*********************************************************************************
/**
 * Closes the result set opened with {@link #getResultSet}.
 * To free the allocated cursor and memory on the database server, call this method
 * when the result set is no longer useful.
 * This is an example:
 * <pre>
 * 	db=new MyDb();
 * 	String id;
 * 	ResultSet rcs=db.getResultSet("select * from myTable where id={s"+id+"}");
 * 	if (rcs.next()) {
 * 		String a1=rcs("a1");
 * 		String a2=rcs("a2");
 * 		. . .
 * 		. . .
 * 	}
 * 	db.closeResultSet(rcs);
 * </pre>
 * @param rcs A result set opened with {@link #getResultSet}.
 * @exception {@link SQLException} When an exception is returned by the database.
 * @exception {@link Exception} When an unexpected error happens.
 */
	public void closeResultSet(ResultSet rcs) throws Exception {
		try {
			Statement st=rcs.getStatement();
			if (st!=null)
				st.close();
			rcs.close();

		} catch (SQLException e) {
			throw new SQLException(e.getMessage(),e.getSQLState(),e.getErrorCode());
		}
		catch(Exception e) {
			throw new Exception(ParseError.parseError("Db.closeResultSet()",e));		
		}
	}

//*****************************************************************************
//**                   Executes the specified sql command.                   **
//*****************************************************************************
/**
 * Executes the specified sql command.
 * The sql command is specified as a simple string, as it would be when typed
 * on a command line console. To take profit of the sql engine precompiler,
 * variable sections can be enclosed withing brackets <code>{x...}</code>, where:
 * <ul>
 * 	<li><b><code>x</code></b> represents the value type. Supported types are:
 * 		<ul>
 * 			<li><code>s</code> A string.</li>
 * 			<li><code>n</code> A number (integer or decimal).</li>
 * 			<li><code>d</code> A date.</li>
 * 		</ul></li>
 *	<li><b><code>...</code></b> Represents the value, as a plain string.</li>
 * </ul> 
 * This is an example:
 * <pre>
 * 	db=new MyDb();
 * 	String id=...;
 * 	int n=db.getResultSet("update myTable where id={s"+id+"}");
 * 	System.out.println(n+" rows affected.");
 * </pre>
 * When not specified, changes are immediately commited. To start a transaction
 * use {@link #startTransaction} and {@link #commitTransaction}.
 * @param A <code>UPDATE</code>, <code>DELETE</code>, <code>INSERT</code> sql statement.
 * @return The number of rows affected by the sql command.
 * @exception {@link SQLException} When an exception is returned by the database.
 * @exception {@link Exception} When an unexpected error happens.
 */
	public int executeSQL(String sql) throws Exception {
		PreparedStatement preparedStatement;
		int n;
		try {
			preparedStatement=prepareStatement(sql);
			n=preparedStatement.executeUpdate();
			preparedStatement.close();
			return n;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage()+" while executing '"+sql+"'",e.getSQLState(),e.getErrorCode());
		}
		catch(Exception e) {
			throw new Exception(ParseError.parseError("Db.executeSQL('"+sql+"')",e));		
		}		
	}

//*****************************************************************************
//**                        Commence une transaction.                        **
//*****************************************************************************
/**
 * Starts a transaction.
 * When a transaction is started, changes made with {@link #executeSQL} are not commited
 * until {@link #commitTransaction} is called.
 * Result sets returned by {@link #getResultSet} and {@link #getResultSetCount} are
 * in the same transaction. 
 */
	public void startTransaction() throws Exception {		
		if (_connection==null) connectTo();
		_connection.setAutoCommit(false);
	}

//*****************************************************************************
//**                      Finalisent une transaction.                        **
//*****************************************************************************
/**
 * Accepts the current transaction.
 * After a transaction is started (with {@link #startTransaction}), changes made 
 * with {@link #executeSQL} are not commited until {@link #commitTransaction} is called.
 * Result sets returned by {@link #getResultSet} and {@link #getResultSetCount} are
 * in the same transaction. 
 */
	public void commitTransaction() throws Exception {
		_connection.commit();
		_connection.setAutoCommit(true);
	}

/**
 * Cancels the current transaction and all modifications made within it.
 * After a transaction is started (with {@link #startTransaction}), changes made 
 * with {@link #executeSQL} are not commited until {@link #commitTransaction} is called.
 * Result sets returned by {@link #getResultSet} and {@link #getResultSetCount} are
 * in the same transaction. 
 */
	public void rollbackTransaction() throws Exception {
		_connection.rollback();
		_connection.setAutoCommit(true);
	}

//*****************************************************************************
//**            Remplace les apostrophes par des doubles cotes.              **
//*****************************************************************************
/**
 * Formats a string to be directly embedded in a SQL request.
 * This is not necessary if the SQL request has the parameters between brackets,
 * as described in {@link #getResultSet}.
 */
	public static String ParseForSQL(String s) throws Exception {
		int n1,n2;
		StringBuffer r=new StringBuffer("");
		try {
			if (s==null)
				return null;
			n1=0;
			n2=s.indexOf("'");
			while (n1>=0) {
				if (n2<0) {
					r.append(s.substring(n1,s.length()));
					break;
				}
				r.append(s.substring(n1,n2));			
				r.append("''");
				n1=n2+1;
				n2=s.indexOf("'",n1);
			}
			return r.toString();
		} catch (Exception e) {
			throw new Exception(ParseError.parseError("Db.ParseForSQL",e));
		}		
	}
}