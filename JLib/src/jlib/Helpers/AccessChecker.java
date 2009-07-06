/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.Helpers;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Utility class for handling users access rights to resources or applications.
 * <p>(Examples are given in Xml, even if the implementation is not necessarily Xml).</p>
 * There are three different structures:
 * <ul>
 * 	<li>A list of users. Each users can belong to none, one or several groups.</li>
 * 	<li>A list of groups. As the users, groups can belong to none, one or several groups.</li>
 * 	<li>A function tree. Each function has two access list: one for reading, one for writing.
 * 	Access lists consist in list of users and groups.</li>
 * </ul>
 * The tree would be as following:
 * <pre>
 * 	[accesstree]
 * 		[users]
 * 			[user name="goodUser"]
 * 				[group name="goodGroup"/]
 * 			[/user]
 * 			[user name="badUser"]
 * 				[group name="badGroup"/]
 * 			[/user]
 * 		[/users]
 * 		[groups]
 * 			[group name="goodGroup"]
 * 				[group name="all"/]
 * 			[/group]
 * 			[group name="badGroup"]
 * 				[group name="all"/]
 * 			[/group]
 * 		[/groups]
 * 		[functions]
 * 			[function name="administration" grant="..." deny="..."]
 * 				[function name="customers"  grant="..." deny="..."]
 * 					[function name="changePassword"  grant="..." deny="..."]
 * 						[parameter name="user" grant="..." deny="..."]goodUser[/parameter]
 * 						[parameter name="user"  grant="..." deny="..."]badUser[/parameter]
 * 					[/function]
 * 					[function name="changeEmail" grant="..." deny="..."]
 * 						[parameter name="user" grant="..." deny="..."]goodUser[/parameter]
 * 						[parameter name="user" grant="..." deny="..."]badUser[/parameter]
 * 					[/function]
 * 				[/function]
 * 				[function name="invoicing" grant="..." deny="..."]]
 * 					[function name="invoices" grant="..." deny="..."]]
 * 					[function name="reports" grant="..." deny="..."]]
 * 				[/function]
 * 			[/function]
 * 		[/functions]
 * 	[/accesstree]
 * </pre>
 * 
 * Requests for access have to specify:
 * <ul>
 * 	<li>A function path: <code>administration / customers / changePassword</code>.</li>
 * 	<li>A user name, or a group.</li>
 * 	<li>An access method: <code>read</code> or <code>write</code>.</li>
 * 	<li>Possibly, a parameter name, and a parameter value.</li>
 * </ul>
 * 
 * Access rules are the following:
 * <ul>
 * 	<li>Initially, the user hasn't access to the requested function. A <code>hasAccess</code> flag is 
 * 	initialized to <code>false</code>.</li>
 * 	<li>The function tree is parsed from the top, following the function path.</li>
 * 	<li>If the <code>hasAccess</code> is <code>false</code> then only 
 * 	the <code>grant</code> list is checked.</li>
 * 	<li>If the <code>hasAccess</code> is <code>true</code> then only
 * 	the <code>deny</code> list is checked.</li>
 * 	<li>If the user name, or the name of any group he belongs to, is present in the
 * 	checked <code>grant</code> or <code>deny</code> list, then the <code>hasAccess</code> flag
 * 	is switched to the correspondent value.</li>
 * 	<li>When the function tree exploration is finished, the current value of <code>hasAccess</code>
 * 	determines whether or not the user has access.</li>
 * </ul>
 * </p>
 * <p>This base class has no serialization / deserialization functionalities. It can be extended
 * to implement these tasks in the most suitable way.</p>
 * @author U930GN
 */
public class AccessChecker {
//**********************************************************************************************
//**                             Class constructor.                                           **
//**********************************************************************************************
/**
 * 
 */
	public String toString() {
		StringBuilder s=new StringBuilder();
		s.append("<accessTree>\r\n");

//.......................... Represents the users ............................................
		s.append("	<users>\r\n");
		Iterator<Map.Entry<String, List<String>>>  usersIterator=_users.entrySet().iterator();
		while(usersIterator.hasNext()) {
			Map.Entry<String, List<String>> userEntry=usersIterator.next();
			s.append("		<user name=\""+userEntry.getKey()+"\">\r\n");
			for(String group:userEntry.getValue()) {
				s.append("			<group name=\""+group+"\"/>\r\n");
			}
			s.append("		</user>\r\n");
		}
		s.append("	</users>\r\n");

//.......................... Represents the groups ...........................................
		s.append("	<groups>\r\n");
		Iterator<Map.Entry<String, List<String>>>  groupsIterator=_groups.entrySet().iterator();
		while(groupsIterator.hasNext()) {
			Map.Entry<String, List<String>> groupEntry=groupsIterator.next();
			s.append("		<group name=\""+groupEntry.getKey()+"\">\r\n");
			for(String group:groupEntry.getValue()) {
				s.append("			<group name=\""+group+"\"/>\r\n");
			}
			s.append("		</group>\r\n");
		}
		s.append("	</groups>\r\n");

//.......................... Represents the functions ........................................
		s.append("	<functions>\r\n");
		s.append(_functions.toString());
		s.append("	</functions>\r\n");
//.......................... Returns the string representation ...............................
		s.append("</accessTree>\r\n");
		return s.toString();
	}
	

//**********************************************************************************************
//**                             Class constructor.                                           **
//**********************************************************************************************
	private Hashtable<String,List<String>> _users;        // Contains the list of users, and the list of groups the users belong to.
	private Hashtable<String,List<String>> _groups;       // Contains the list of groups, and the list of groups the groups belong to.
	private AccessCheckerFunction _functions;             // Contains the function tree.

//********************************* Default class constructor **********************************
/**
 * Default class construcutor.
 * The access structure is initialized as empty. It has to be populated by calling other methods
 * in the class:
 * <ul>
 * 	<li>Declaring users and groups of users with {@link #declareUser}, {@link #declareGroup}.</li>
 * 	<li>Grant access to functions with {@link #grantWriteAccessToUsersOrGroups and {@link #grantReadAccessToUsersOrGroups}.</li>
 * 	<li>Deny access to functions with {@link #denyWriteAccessToUsersOrGroups and {@link #denyReadAccessToUsersOrGroups}.</li>
 * </ul>
 * Class can be extended to implement automatic population from external data, like a database,
 * a file, a stream...
 */
	public AccessChecker() throws Exception {
		_users=new Hashtable<String,List<String>>();
		_groups=new Hashtable<String,List<String>>();
		_functions=new AccessCheckerFunction();
	}

//************************************************************************************************
//**                Methods for adding users to the access structure.                           **
//************************************************************************************************
/**
 * Declares a new user in the access structure.
 * @param userName The user name.
 * @param groupsList The list of groups where the user has to be included. If <code>null</code> or
 * empty, the user is not included in any group. All specified groups have to be previously declared
 * in the <code>groups</code> section ({@link #declareGroup}, or loaded with {@link #load} or 
 * {@link #AccessChecker(InputStream)}). The <code>groupsList</code> parameter can be a comma-separated
 * list of group names as: <pre>"users,administrators,backup"</pre>
 * @exception Exception if the specified <code>userName</code> already exists.
 * @exception Exception if any of the specified groups in the <code>groupsList</code> parameter isn't declared.
 */
	public void declareUser(String userName,String groupsList) throws Exception {
		try {
//******************************* Initialization *************************************************
			if (userName==null) userName="";
			if (userName.length()==0)
				throw new Exception("'userName' parameter cannot be null or empty.");

//******************************** Adds the user to the 'users' section ***************************
			if (_users.containsKey(userName))
				throw new Exception("User '"+userName+"' is already declared.");

			_users.put(userName,new ArrayList<String>());

//******************************** Adds the user to the specified list of groups ******************
			includeUserInGroups(userName,groupsList);
		}

//****************************** Exception management *********************************************
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.declareUser('"+userName+"','"+groupsList+"')", e));
		}
	}

//*************************************************************************************************
//**                Includes the specified user in the specified list of groups.                 **
//*************************************************************************************************
/**
 * Includes the specified user in the specified list of groups.
 * @param userName The user name.
 * @param groups The list of groups where the user has to be included. If <code>null</code> or
 * empty, the user is not included in any group. All specified groups have to be previously declared
 * in the <code>groups</code> section ({@link #declareGroup}, or loaded with {@link #load} or 
 * {@link #AccessChecker(InputStream)}). The parameter can be a comma-separated
 * list of group names as: <pre>"users,administrators,backup"</pre>
 * @exception Exception if the specified <code>userName</code> isn't declared.
 * @exception Exception if any of the specified groups in the <code>groups</code> parameter isn't declared.
 */
	public void includeUserInGroups(String userName,String groups) throws Exception {
		try {
//******************************* Initialization *************************************************
			if (groups==null) groups="";
			if (groups.length()==0)
				return;

			if (userName==null) userName="";
			if (userName.length()==0)
				throw new Exception("'userName' parameter cannot be null or empty.");

			if (_groups.size()==0)
				throw new Exception("No group has been declared yet in the 'groups' section.");

			if (!_users.containsKey(userName))
				throw new Exception("User '"+userName+"' isn't declared in the 'users' section.");

//........................... Retrieves the list of groups the user is already in .................
			List<String> groupsList=_users.get(userName);

// If the user isn't in any group yet, initializes a new list of groups:
			if (groupsList==null) {
				groupsList=new ArrayList<String>();
				_users.remove(userName);
				_users.put(userName, groupsList);
			}

//******************************** Adds the user to the specified list of groups ******************
			String[] group=groups.split(",");
			int nn=group.length;
			for(int n=0;n<nn;n++) {

//................................ Checks if the group exists .....................................
				if (!_groups.containsKey(group[n]))
					throw new Exception("Cannot include user '"+userName+"' in group '"+group[n]+"' because this group is not declared.");

//................................ Adds the group in the user's list of groups ....................				
				groupsList.add(group[n]);
			}
		}

//****************************** Exception management *********************************************
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.includeUserInGroups('"+userName+"','"+groups+"')", e));
		}
	}

//*************************************************************************************************
//**                      Declares a new group in the groups section.                            **
//*************************************************************************************************
/**
 * Declares a new group in the 'groups' section.
 * @param groupName The new group's name.
 * @exception Exception If specified name is null or empty.
 * @exception Exception If the group already exists.
 */
	public void declareGroup(String groupName) throws Exception {
		try {
			declareGroup(groupName,null);
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.declareGroup('"+groupName+"')",e));
		}
	}
/**
 * Declares a new group in the 'groups' section, as a child of the specified parent group.
 * If the specified group already exists, then the method only adds the group in the specified parent group.
 * @param groupName The new group's name.
 * @param parentGroup The name of the parent.
 * @exception Exception If specified name is null or empty.
 * @exception Exception if the parent group doesn't exist. 
 */
	public void declareGroup(String groupName,String parentGroups) throws Exception {
		try {
//********************************* Initialization ******************************************
			if (groupName==null) throw new Exception("groupName parameter cannot be null or empty.");
			if (groupName.length()==0) throw new Exception("groupName parameter cannot be null or empty.");

//************************** Creates the new group *******************************************
			if (!_groups.containsKey(groupName)) {
				_groups.put(groupName,new ArrayList<String>());
			}

//***************************** Sets or adds the parent group ********************************
			includeGroupInGroups(groupName,parentGroups);
		}

//******************************* Exception management ***************************************
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.declareGroup('"+groupName+"','"+parentGroups+"')",e));
		}
	}

//*************************************************************************************************
//**                Includes the specified user in the specified list of groups.                 **
//*************************************************************************************************
/**
 * Includes the specified user in the specified list of groups.
 * @param userName The user name.
 * @param groupsList The list of groups where the user has to be included. If <code>null</code> or
 * empty, the user is not included in any group. All specified groups have to be previously declared
 * in the <code>groups</code> section ({@link #declareGroup}, or loaded with {@link #load} or 
 * {@link #AccessChecker(InputStream)}). The <code>groupsList</code> parameter can be a comma-separated
 * list of group names as: <pre>"users,administrators,backup"</pre>
 * @exception Exception if the specified <code>userName</code> isn't declared.
 * @exception Exception if any of the specified groups in the <code>groupsList</code> parameter isn't declared.
 */
	public void includeGroupInGroups(String groupName,String parentGroups) throws Exception {
		try {
//******************************* Initialization *************************************************
			if (parentGroups==null) parentGroups="";
			if (parentGroups.length()==0)
				return;

			if (groupName==null) groupName="";
			if (groupName.length()==0)
				throw new Exception("'groupName' parameter cannot be null or empty.");

			if (_groups.size()==0)
				throw new Exception("No group has been declared yet in the 'groups' section.");

			if (!_groups.containsKey(groupName))
				throw new Exception("Group '"+groupName+"' isn't declared in the 'groups' section.");

//........................... Retrieves the list of groups the group is already in ................
			List<String> groupsList=_groups.get(groupName);

// If the group isn't in any group yet, initializes a new list of groups:
			if (groupsList==null) {
				groupsList=new ArrayList<String>();
				_users.remove(groupName);
				_users.put(groupName, groupsList);
			}

//******************************** Adds the user to the specified list of groups ******************
			String[] group=parentGroups.split(",");
			int nn=group.length;
			for(int n=0;n<nn;n++) {

//................................ Checks if the group exists .....................................
				if (!_groups.containsKey(group[n]))
					throw new Exception("Cannot include group '"+groupName+"' in group '"+group[n]+"' because '"+group[n]+"' is not declared.");

//................................ Adds the group in the groups's list of groups ..................				
				groupsList.add(group[n]);
			}
		}

//****************************** Exception management *********************************************
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.includeGroupInGroups('"+groupName+"','"+parentGroups+"')", e));
		}
	}

//********************************************************************************************
//**               Grants access to a function to a list of users and groups.               **
//********************************************************************************************
/**
 * Grants <code>write</code> access to a function to a list of users and groups.
 * @param functionPath The path to the function the access is granted. Can be on the form 
 * of: <pre>"userManager/userProfile/userChangePassword"</pre>. If function path doesn't exist yet,
 * it is created by this call.
 * @param usersOrGroups The list of users and/or groups to set the access. Is a comma separated list.
 * @exception Exception if no user has been yet declared.
 * @exception Exception if <code>parameterName</code> is not null, and <code>parameterValue</code> is.
 * @exception Exception if <code>functionPath</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> contains undeclared users or groups.
 */
	public void grantWriteAccessToUsersOrGroups(String functionPath,String usersOrGroups) throws Exception {
		try {
			establishAccessToUsersOrGroups("grant","write",functionPath,usersOrGroups,null,null);
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.grantWriteAccessToUsersOrGroups('"+functionPath+"','"+usersOrGroups+"')",e));			
		}
	}

/**
 * Grants <code>write</code> access to a function to a list of users and groups.
 * @param functionPath The path to the function the access is granted. Can be on the form 
 * of: <pre>"userManager/userProfile/userChangePassword"</pre>. If function path doesn't exist yet,
 * it is created by this call.
 * @param usersOrGroups The list of users and/or groups to set the access. Is a comma separated list.
 * @param parameterName If not <code>null</code>, sets the access to a particular parameter and value.
 * @param parameterValue Cannot be <code>null</code> if <code>parameterName</code> isn't. Sets the access to a 
 * particular parameter and value.
 * @exception Exception if no user has been yet declared.
 * @exception Exception if <code>parameterName</code> is not null, and <code>parameterValue</code> is.
 * @exception Exception if <code>functionPath</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> contains undeclared users or groups.
 */
	public void grantWriteAccessToUsersOrGroups(String functionPath,String usersOrGroups,String parameterName,String parameterValue) throws Exception {
		try {
			establishAccessToUsersOrGroups("grant","write",functionPath,usersOrGroups,parameterName,parameterValue);
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.grantWriteAccessToUsersOrGroups('"+functionPath+"','"+usersOrGroups+"','"+parameterName+"','"+parameterValue+"')",e));
		}
	}

/**
 * Grants <code>read</code> access to a function to a list of users and groups.
 * @param functionPath The path to the function the access is granted. Can be on the form 
 * of: <pre>"userManager/userProfile/userChangePassword"</pre>. If function path doesn't exist yet,
 * it is created by this call.
 * @param usersOrGroups The list of users and/or groups to set the access. Is a comma separated list.
 * @exception Exception if no user has been yet declared.
 * @exception Exception if <code>parameterName</code> is not null, and <code>parameterValue</code> is.
 * @exception Exception if <code>functionPath</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> contains undeclared users or groups.
 */
	public void grantReadAccessToUsersOrGroups(String functionPath,String usersOrGroups) throws Exception {
		try {
			establishAccessToUsersOrGroups("grant","read",functionPath,usersOrGroups,null,null);
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.grantReadAccessToUsersOrGroups('"+functionPath+"','"+usersOrGroups+"')",e));			
		}
	}

/**
 * Grants <code>read</code> access to a function to a list of users and groups.
 * @param functionPath The path to the function the access is granted. Can be on the form 
 * of: <pre>"userManager/userProfile/userChangePassword"</pre>. If function path doesn't exist yet,
 * it is created by this call.
 * @param usersOrGroups The list of users and/or groups to set the access. Is a comma separated list.
 * @param parameterName If not <code>null</code>, sets the access to a particular parameter and value.
 * @param parameterValue Cannot be <code>null</code> if <code>parameterName</code> isn't. Sets the access to a 
 * particular parameter and value.
 * @exception Exception if no user has been yet declared.
 * @exception Exception if <code>parameterName</code> is not null, and <code>parameterValue</code> is.
 * @exception Exception if <code>functionPath</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> contains undeclared users or groups.
 */
	public void grantReadAccessToUsersOrGroups(String functionPath,String usersOrGroups,String parameterName,String parameterValue) throws Exception {
		try {
			establishAccessToUsersOrGroups("grant","read",functionPath,usersOrGroups,parameterName,parameterValue);
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.grantReadAccessToUsersOrGroups('"+functionPath+"','"+usersOrGroups+"','"+parameterName+"','"+parameterValue+"')",e));
		}
	}

//********************************************************************************************
//**               Denies access to a function to a list of users and groups.               **
//********************************************************************************************
/**
 * Denies <code>write</code> access to a function to a list of users and groups.
 * @param functionPath The path to the function the access is granted. Can be on the form 
 * of: <pre>"userManager/userProfile/userChangePassword"</pre>. If function path doesn't exist yet,
 * it is created by this call.
 * @param usersOrGroups The list of users and/or groups to set the access. Is a comma separated list.
 * @exception Exception if no user has been yet declared.
 * @exception Exception if <code>parameterName</code> is not null, and <code>parameterValue</code> is.
 * @exception Exception if <code>functionPath</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> contains undeclared users or groups.
 */
	public void denyWriteAccessToUsersOrGroups(String functionPath,String usersOrGroups) throws Exception {
		try {
			establishAccessToUsersOrGroups("deny","write",functionPath,usersOrGroups,null,null);
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.denyWriteAccessToUsersOrGroups('"+functionPath+"','"+usersOrGroups+"')",e));			
		}
	}

/**
 * Denies <code>write</code> access to a function to a list of users and groups.
 * @param functionPath The path to the function the access is granted. Can be on the form 
 * of: <pre>"userManager/userProfile/userChangePassword"</pre>. If function path doesn't exist yet,
 * it is created by this call.
 * @param usersOrGroups The list of users and/or groups to set the access. Is a comma separated list.
 * @param parameterName If not <code>null</code>, sets the access to a particular parameter and value.
 * @param parameterValue Cannot be <code>null</code> if <code>parameterName</code> isn't. Sets the access to a 
 * particular parameter and value.
 * @exception Exception if no user has been yet declared.
 * @exception Exception if <code>parameterName</code> is not null, and <code>parameterValue</code> is.
 * @exception Exception if <code>functionPath</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> contains undeclared users or groups.
 */
	public void denyWriteAccessToUsersOrGroups(String functionPath,String usersOrGroups,String parameterName,String parameterValue) throws Exception {
		try {
			establishAccessToUsersOrGroups("deny","write",functionPath,usersOrGroups,parameterName,parameterValue);
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.denyWriteAccessToUsersOrGroups('"+functionPath+"','"+usersOrGroups+"','"+parameterName+"','"+parameterValue+"')",e));
		}
	}

/**
 * Denies <code>read</code> access to a function to a list of users and groups.
 * @param functionPath The path to the function the access is granted. Can be on the form 
 * of: <pre>"userManager/userProfile/userChangePassword"</pre>. If function path doesn't exist yet,
 * it is created by this call.
 * @param usersOrGroups The list of users and/or groups to set the access. Is a comma separated list.
 * @exception Exception if no user has been yet declared.
 * @exception Exception if <code>parameterName</code> is not null, and <code>parameterValue</code> is.
 * @exception Exception if <code>functionPath</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> contains undeclared users or groups.
 */
	public void denyReadAccessToUsersOrGroups(String functionPath,String usersOrGroups) throws Exception {
		try {
			establishAccessToUsersOrGroups("deny","read",functionPath,usersOrGroups,null,null);
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.denyReadAccessToUsersOrGroups('"+functionPath+"','"+usersOrGroups+"')",e));			
		}
	}

/**
 * Denies <code>read</code> access to a function to a list of users and groups.
 * @param functionPath The path to the function the access is granted. Can be on the form 
 * of: <pre>"userManager/userProfile/userChangePassword"</pre>. If function path doesn't exist yet,
 * it is created by this call.
 * @param usersOrGroups The list of users and/or groups to set the access. Is a comma separated list.
 * @param parameterName If not <code>null</code>, sets the access to a particular parameter and value.
 * @param parameterValue Cannot be <code>null</code> if <code>parameterName</code> isn't. Sets the access to a 
 * particular parameter and value.
 * @exception Exception if no user has been yet declared.
 * @exception Exception if <code>parameterName</code> is not null, and <code>parameterValue</code> is.
 * @exception Exception if <code>functionPath</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> contains undeclared users or groups.
 */
	public void denyReadAccessToUsersOrGroups(String functionPath,String usersOrGroups,String parameterName,String parameterValue) throws Exception {
		try {
			establishAccessToUsersOrGroups("deny","read",functionPath,usersOrGroups,parameterName,parameterValue);
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.denyReadAccessToUsersOrGroups('"+functionPath+"','"+usersOrGroups+"','"+parameterName+"','"+parameterValue+"')",e));
		}
	}

//********************************************************************************************
//**  Internal method for establishing access to a function to a list of users and groups   **
//********************************************************************************************
/**
 * Establishes access to a function to a list of users and groups.
 * Method used internally.
 * @param action Can be <code>"grant"</code> or <code>"deny"</code>. Grants or denies the access.
 * @param access Can be <code>"read"</code> or <code>"write"</code>. Specifies the type of access to establish.
 * @param functionPath The path to the function the access is granted. Can be on the form 
 * of: <pre>"userManager/userProfile/userChangePassword"</pre>. If function path doesn't exist yet,
 * it is created by this call.
 * @param usersOrGroups The list of users and/or groups to set the access. Is a comma separated list.
 * @param parameterName If not <code>null</code>, sets the access to a particular parameter and value.
 * @param parameterValue Cannot be <code>null</code> if <code>parameterName</code> isn't. Sets the access to a 
 * particular parameter and value.
 * @exception Exception if no user has been yet declared.
 * @exception Exception if <code>parameterName</code> is not null, and <code>parameterValue</code> is.
 * @exception Exception if <code>action</code> is not one of the allowed values.
 * @exception Exception if <code>functionName</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> is empty or null.
 * @exception Exception if <code>usersOrGroups</code> contains undeclared users or groups.
 */
	public void establishAccessToUsersOrGroups(String action,String access,String functionPath,String usersOrGroups,String parameterName,String parameterValue) throws Exception {
		try {
//***************************************** Initialization ************************************
			if (action==null) action="";
			if (!action.equals("grant") && !action.equals("deny"))
				throw new Exception("Specified action can be 'grant' or 'deny' but not '"+action+"'");

			if (access==null) access="";
			if (!access.equals("read") && !access.equals("write"))
				throw new Exception("Specified access can be 'read' or 'write' but not '"+access+"'");

			if (functionPath==null) functionPath="";
			if (functionPath.length()==0)
				throw new Exception("Specified 'functionPath' cannot be null or empty.");

			if (usersOrGroups==null) usersOrGroups="";
			if (usersOrGroups.length()==0)
				throw new Exception("Specified list of users cannot be null or empty.");

//.............................. Checks the users and groups list .............................
			String[] userOrGroup=usersOrGroups.split(",");
			int nn=userOrGroup.length;
			for(int n=0;n<nn;n++) {
// Maybe it is a user?
				if (_users.containsKey(userOrGroup[n]))
					continue;

// Maybe it is a group?
				if (_groups.containsKey(userOrGroup[n]))
					continue;

// If none, then it is a problem:
				throw new Exception("Specified user or group '"+userOrGroup[n]+"' is neither a declared user or a declared group.");
			}

//**************************** Creates the requested element **********************************
// The requested element is the concatenation of the 
// function path, plus the parameter (if any specified).
			String[] functionName=functionPath.split("/");

//......................... Creates the function path .........................................
			nn=functionName.length;
			AccessCheckerFunction function=_functions;
			for(int n=0;n<nn;n++) {
				if (!function.tree.containsKey(functionName[n])) {
					AccessCheckerFunction childFunction=new AccessCheckerFunction();
					childFunction.functionName=functionName[n];
					function.tree.put(functionName[n], childFunction);
				}
				function=function.tree.get(functionName[n]);
			}

//............................ Creates the parameter ..........................................
			AccessCheckerElement element;
			if (parameterName!=null) {

// Locates or creates the parameter:
				AccessCheckerParameter parameter;
				if (!function.parameters.containsKey(parameterName)) {
					parameter=new AccessCheckerParameter();
					parameter.parameterName=parameterName;
					function.parameters.put(parameterName,parameter);
				} else
					parameter=function.parameters.get(parameterName);

// Locates or creates the parameter's value:
				if (!parameter.values.containsKey(parameterValue)) {
					element=new AccessCheckerElement();
					parameter.values.put(parameterValue, element);
				} else
					element=parameter.values.get(parameterValue);
			}
// If no parameter is specified, then the element to grant is directly
// the specified function.
			else
				element=function;

//********************** Establishes the access to the requested element ************************
			List<String> accessList=null;

//........... Locates the access list corresponding to the specified action and access ..........
			if (action.equals("grant")) {
				if (access.equals("write")) {
					accessList=element.grantWriting;
				} else if (access.equals("read")) {
					accessList=element.grantReading;					
				}
			} else if (action.equals("deny")) {
				if (access.equals("write")) {
					accessList=element.denyWriting;										
				} else if (access.equals("read")) {
					accessList=element.denyReading;					
				}
			}
			if (accessList==null)
				throw new Exception("Combination of action='"+action+"' and access='"+access+"' didn't determine an access list.");

//..................... Adds the specified users and groups to the access list ..................
			nn=userOrGroup.length;
			for(int n=0;n<nn;n++) {
				if (!accessList.contains(userOrGroup[n]))
					accessList.add(userOrGroup[n]);
			}
		}

//****************************** Exception management ******************************************
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.establishUserOrGroupAccess('"+action+"','"+functionPath+"','"+usersOrGroups+"','"+parameterName+"','"+parameterValue+"')",e));			
		}
	}

//***********************************************************************************************
//**              Checks access to a particular function and parameter.                        **
//***********************************************************************************************
/**
 * Checks access to a particular function name.
 * Rules are described in the class documentation {@link AccessChecker}.
 * @param userName The user name to check.
 * @param accessType Can be <code>read</code> or <code>write</code>.
 * @param functionPath The function name to check. This parameter can be on the
 * form of a relative path, like: <pre>"userManager/userProfile/userChangePassword"</pre> 
 * @exception If <code>functionPath</code> is null or empty.
 */
	public boolean hasUserAccessTo(String userName,String accessType,String functionPath) throws Exception {
		try {
			return hasUserAccessTo(userName,accessType,functionPath,null,null);
		} catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.hasUserAccessTo('"+userName+"','"+accessType+"','"+functionPath+"')",e));			
		}
	}

/**
 * Checks access to a particular function name.
 * Rules are described in the class documentation {@link AccessChecker}.
 * @param userName The user name to check.
 * @param functionPath The function name to check. This parameter can be on the
 * form of a relative path, like: <pre>"userManager/userProfile/userChangePassword"</pre>
 * @param accessType Can be <code>read</code> or <code>write</code>.
 * @param parameterName The parameter name to check. If not <code>null</code>, then the
 * <code>parameterValue</code> is mandatory.
 * @param parameterValue The parameter value to check.
 * @exception If <code>functionPath</code> is null or empty.
 * @exception If <code>parameterValue</code> is null or empty but <code>parameterName</code> is
 * not empty. 
 */
	public boolean hasUserAccessTo(String userName,String accessType,String functionPath,String parameterName,String parameterValue) throws Exception {
		try {
			if (functionPath==null) 
				throw new Exception("Specified functionName cannot be null or empty.");
			if (functionPath.length()==0) 
				throw new Exception("Specified functionName cannot be null or empty.");
			
			if (accessType==null)
				throw new Exception("Specified access cannot be null or empty.");
			if (!accessType.equals("read") && !accessType.equals("write"))
				throw new Exception("Specified access can be 'read' or 'write', but not '"+accessType+"'");

//****************************** If the user is not declared, then he has no access *************
			List<String> userGroups=retrieveUserGroups(userName);    // Returns the list of groups, and appends the specified user name as one of the groups.
			if (userGroups.size()==0)
				return false;

// By default, the user has no access:
			boolean hasUserAccess=false;

//************************* Looks for the function path ***********************************
			String[] functionName=functionPath.split("/");
			int nn=functionName.length;

			AccessCheckerFunction function=_functions;
			for(int n=0;n<nn;n++) {
//................ If the function is not in the tree, method exits .......................
// (With the current permission)
				if(!function.tree.containsKey(functionName[n]))
					return hasUserAccess;
				function=function.tree.get(functionName[n]);

//....................... Checks if the user has access ....................................
				hasUserAccess=function.hasUserAccess(hasUserAccess,accessType,userGroups);
			}

//**************************** Looks for the parameter ****************************************
			if (parameterName!=null) {
				if (parameterValue==null) 
					throw new Exception("If parameterName is not null, then parameterValue cannot be null or empty.");

//........................ Checks if the user has access to the parameter .....................

// If the parameter doesn't exist, the method returns the current access:
				if (!function.parameters.containsKey(parameterName))
					return hasUserAccess;
				

// Checks if the user has access to the parameter:
				AccessCheckerParameter parameter=function.parameters.get(parameterName);
				hasUserAccess=parameter.hasUserAccess(hasUserAccess,accessType,userGroups);

//........................ Checks if the user has access to the value .........................
// If the value doesn't exist, the method returns the current access:
				if (!parameter.values.containsKey(parameterValue))
					return hasUserAccess;
				

// Checks if the user has access to the value:
				AccessCheckerElement value=parameter.values.get(parameterValue);
				hasUserAccess=value.hasUserAccess(hasUserAccess,accessType,userGroups);
			}

//*********************** Returns the user's access permission ****************************
			return hasUserAccess;
		}

//************************************** Exception manager ********************************
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.hasUserAccessTo('"+userName+"','"+functionPath+"','"+parameterName+"','"+parameterValue+"')",e));			
		}
	}

//******************************************************************************************
//**             Retrieves lists of users and all groups to which the user belongs.       **
//******************************************************************************************
/**
 * Returns the list of groups, and appends the specified user name as one of the groups.
 */
	private List<String> retrieveUserGroups(String userName) throws Exception {
		try {

//********************** If the user doesn't exist, then the list is empty *****************
			if (!_users.containsKey(userName))
				return new ArrayList<String>();

//................... Initializes a list with the requested user already included ..........
			List<String> userGroups=new ArrayList<String>();
			userGroups.add(userName);

//************************** Retrieves the list of groups the user belongs to **************
			List<String> groupsList=_users.get(userName);

			Iterator<String> groupsIterator=groupsList.iterator();
			while (groupsIterator.hasNext()) {
				String groupName=groupsIterator.next();
				userGroups.add(groupName);
				userGroups.addAll(retrieveGroupGroups(groupName));
			}

//****************************** Returns the complete list of groups **********************
			return userGroups;
		}

//************************** Exception Management *****************************************
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.userGroups('"+userName+"')",e));						
		}
	}

//*****************************************************************************************
//**           Retrieves the list of all groups to which belongs the specified group.    **
//*****************************************************************************************
	private List<String> retrieveGroupGroups(String groupName) throws Exception {
		try {
//*********************************** Initialization **************************************
			if (groupName==null) 
				return new ArrayList<String>();
			if (groupName.length()==0) 
				return new ArrayList<String>();
			if (!_groups.containsKey(groupName)) 
				return new ArrayList<String>();

//............... Initializes the list of groups, including the requested group ...........
			List<String> groupGroups=new ArrayList<String>();

//*********************** Retrieves the list of groups the group belongs to ***************
			List<String> groupsList=_groups.get(groupName);
			Iterator<String> groupsIterator=groupsList.iterator();
			while(groupsIterator.hasNext()) {
				groupName=groupsIterator.next();
				groupGroups.add(groupName);
				groupGroups.addAll(retrieveGroupGroups(groupName));
			}

//************************ Returns the list of groups *************************************
			return groupGroups;
		}

//***************************** Exception management **************************************
		catch (Exception e) {
			throw new Exception(ParseError.parseError("AccessChecker.retrieveGroupGroups('"+groupName+"')",e));						
		}		
	}

//******************************************************************************************
//**           Private classes, for building the "functions" tree.                        **
//******************************************************************************************

//************************ All elements in the tree have grant and deny lists **************
/**
 * Base class containing the access lists and the method for checking access.
 */
	private class AccessCheckerElement {
		public List<String> grantReading;
		public List<String> denyReading;		
		public List<String> grantWriting;
		public List<String> denyWriting;

/**
 * Default class constructor.
 * Initializes the access lists.
 */
		public AccessCheckerElement() {
			grantReading=new ArrayList<String>();
			denyReading=new ArrayList<String>();
			grantWriting=new ArrayList<String>();
			denyWriting=new ArrayList<String>();
		}
/**
 * A String representation of the <code>AccessCheckerElement</code>
 */
		@Override
		public String toString() {
			StringBuilder ss=new StringBuilder();
			ss.append("grantReading=\"");
			int n=0;
			for(String s:grantReading) {
				if (n++>0)
					ss.append(", ");
				ss.append(s);
			}

			ss.append("\" ");
			ss.append("grantWriting=\"");
			n=0;
			for(String s:grantWriting) {
				if (n++>0)
					ss.append(", ");
				ss.append(s);
			}
			ss.append("\" ");

			ss.append("denyReading=\"");
			n=0;
			for(String s:denyReading) {
				if (n++>0)
					ss.append(", ");
				ss.append(s);
			}
			ss.append("\" ");

			ss.append("denyWriting=\"");
			n=0;
			for(String s:denyWriting) {
				if (n++>0)
					ss.append(", ");
				ss.append(s);
			}
			ss.append("\" ");
		
			return ss.toString();
		}

/**
 * Checks the user access to the current element.
 * Check is performed as follows:
 * <ul>
 * 	<li>Only one of the four access list is tested:
 * 		<ul>
 * 			<li>If the user <b>has</b> access to the parent element (<code>accessToParent=true</code>, and the 
 * 			requested access type is <code>read</code>, then only {@link #denyReading} is checked.</li> 
 * 			<li>If the user <b>has</b> access to the parent element (<code>accessToParent=true</code>, and the 
 * 			requested access type is <code>write</code>, then only {@link #denyWriting} is checked.</li> 
 * 			<li>If the user <b>has no</b> access to the parent element (<code>accessToParent=false</code>, and the 
 * 			requested access type is <code>read</code>, then only {@link #grantReading} is checked.</li> 
 * 			<li>If the user <b>has no</b> access to the parent element (<code>accessToParent=false</code>, and the 
 * 			requested access type is <code>write</code>, then only {@link #grantWriting} is checked.</li> 
 * 		</ul></li>
 * 	<li>If the user, or one of the groups he belongs to (parameter <code>userGroups</code>) is found in the
 * 	pertinent list, then the value returned by the method is the negated value of <code>accessToParent</code>.</li>
 * 	<li>If neither the user, or any of the groups he belongs to (parameter <code>userGroups</code>) is found in the
 * 	pertinent list, then the value returned by the method is directly the value of <code>accessToParent</code>.</li>
 * </ul>
 * @param accessToParent Has to be <code>true</code> if the user has access to the parent element.
 * @param accessType Can be either <code>"read"</code> or <code>"write"</code>.
 * @param userGroups A list containing the user name, plus all groups he belongs to.
 * @return <code>true</code> if the user has access to the element, <code>false</code> otherwise.
 * @throws Exception If <code>accessType</code> is not <code>"read"</code> or <code>"write"</code>.
 */
		public boolean hasUserAccess(boolean accessToParent,String accessType,List<String> userGroups) throws Exception {
			List<String> accessList;

// Retrieves the pertinent list to check:
			if (accessToParent) {
				if (accessType.equals("read"))
					accessList=denyReading;
				else if (accessType.equals("write"))
					accessList=denyWriting;
				else
					throw new Exception("Access type can be 'read' or 'write' but not '"+accessType+"'");
			} else {
				if (accessType.equals("read"))
					accessList=grantReading;
				else if (accessType.equals("write"))
					accessList=grantWriting;
				else
					throw new Exception("Access type can be 'read' or 'write' but not '"+accessType+"'");
			}

// If the user is mentioned in the pertinent list:
			Iterator<String> userGroupsIterator=userGroups.iterator();
			while(userGroupsIterator.hasNext()) {
				String userGroup=userGroupsIterator.next();
				if (accessList.contains(userGroup))
					return !accessToParent;       // Then his access has changed from parent element.
			}

// If the user is not mentioned in the pertinent list:
			return accessToParent;                // Then his access is the same as for the parent element.
		}
	}

//******************* Additionally, functions can have descendants and parameters **********
/**
 * Extension of the base class {@link AccessCheckerElement} for describing a function.
 * Functions can have descendants and parameters.
 */
	private class AccessCheckerFunction extends AccessCheckerElement {
		public String functionName;
		public Map<String,AccessCheckerFunction> tree;
		public Map<String,AccessCheckerParameter> parameters;

/**
 * Default class constructor.
 * Initializes an empty function tree, and an empty parameters collection.
 */
		public AccessCheckerFunction() {
			super();
			tree=new Hashtable<String,AccessCheckerFunction>();
			parameters=new Hashtable<String,AccessCheckerParameter>();
		}

/**
 * A string representation for <code>AccessChecker</code>
 */
		@Override
		public String toString() {
			return toString(2);
		}
		private String toString(int level) {
			StringBuilder ss=new StringBuilder();
			String indent="";
			for(int n=0;n<level;n++) {
				indent+="	";
			}

			ss.append(indent+"<function name=\""+functionName+"\" "+super.toString()+">\r\n");
			for(AccessCheckerFunction acl:tree.values()) {
				ss.append(acl.toString(level+1));
			}
			for(AccessCheckerParameter parameter:parameters.values()) {
				ss.append(parameter.toString(level+1));
			}
			ss.append(indent+"</function>\r\n");
			return ss.toString();
		}
	}

//******************* Additionally, parameters have a collection of values *******************
/**
 * Extension of the base class {@link AccessCheckerElement} for describing a parameter.
 * Functions can have a collection of values.
 */
	private class AccessCheckerParameter extends AccessCheckerElement {
		public String parameterName;
		public Map<String,AccessCheckerElement> values;
		public AccessCheckerParameter() {
			super();
			values=new Hashtable<String,AccessCheckerElement>();
		}
/**
 * A string representation for <code>AccessParameter</code>
 */
		@Override
		public String toString() {
			return toString(0);
		}
		private String toString(int level) {
			StringBuilder ss=new StringBuilder();
			String indent="";
			for(int n=0;n<level;n++) {
				indent+="	";
			}
			Iterator<Map.Entry<String,AccessChecker.AccessCheckerElement>>  valuesIterator=values.entrySet().iterator();
			while(valuesIterator.hasNext()) {
				Map.Entry<String, AccessCheckerElement> valueEntry=valuesIterator.next();
				ss.append(indent+"<parameter name=\""+parameterName+"\" "+valueEntry.getValue().toString()+">"+valueEntry.getKey()+"</parameter>\r\n");
			}
			return ss.toString();
		}
	}
}