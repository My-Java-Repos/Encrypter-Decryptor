package com.org.aes;

import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	
	private static final String key = "ZR+8Q5akk+P1o0VFGSlZJg=="; //aesEncryptionKey

	public static String encrypt(String value) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static String decrypt(String encrypted) {
		try {

			byte[] encryptionkeyOrigByteArr = Base64.getDecoder().decode(getKey(key));
			
			SecretKeySpec skeySpec = new SecretKeySpec(encryptionkeyOrigByteArr, "AES");
 
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}

		return null;
	}
	
	public static String getKey(String key) {
		String encodedKey = Base64.getEncoder().encodeToString(key.getBytes());
		return encodedKey;
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter consumer id:");
		String consumer_id = sc.nextLine();
		System.out.println("Enter analytic name:");
		String analyticName = sc.nextLine();
		System.out.println("Enter version:");
		String version = sc.nextLine();
		/*String consumer_id = "consumer_001"; 
    	String analyticName ="Inpatient Admissions Analytic"; 
    	String version = "2.0"; 
*/      
		String originalString = String.join("~", consumer_id,analyticName,version);
		System.out.println("Original String to encrypt - " + originalString);
		String encryptedString = encrypt(originalString);
		System.out.println("Encrypted String - " + encryptedString);
		String decryptedString = decrypt(encryptedString);
		System.out.println("After decryption - " + decryptedString);
	}
}
