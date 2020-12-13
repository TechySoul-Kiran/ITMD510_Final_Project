package models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
	
	public static String MD5(String password)
	{
		try { 
			MessageDigest md = MessageDigest.getInstance("MD5"); 

			byte[] messageDigest = md.digest(password.getBytes()); 
			BigInteger number = new BigInteger(1, messageDigest); 
			String finalValue = number.toString(16); 
			while (finalValue.length() < 32) { 
				finalValue = "0" + finalValue; 
			} 
			return finalValue; 
		} 
		catch (NoSuchAlgorithmException e) { 
			throw new RuntimeException(e); 
		} 		
	}
}
