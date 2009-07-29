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
package jlib.blowfish;

public class KeyGenerator {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("KeyGenerator <key> <password>");
			return;
		}
		System.out.println("Key      :"+args[0]);
		System.out.println("Password :"+args[1]);
		Blowfish blowfish = new Blowfish(args[0], true);
		String crypt = blowfish.encrypt(args[1]);		
		String decrypt = blowfish.decrypt(crypt);		
		System.out.println("Crypted  :"+crypt);
		System.out.println("Decrypted:"+decrypt);
	}
}