package fr.glowstoner.sylvanus.crypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CryptMessage {
	
	public static String encrypt(String key, String base) {
		String r = null;
		
		CryptAES c = new CryptAES();
		try {
			c.setKey(key);
			r = c.encrypt(base);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException 
				| InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException |
				BadPaddingException e) {
			
			e.printStackTrace();
		}
		
		return r;
	}
	
	public static String decrypt(String key, String base) {
		String r = null;
		
		CryptAES c = new CryptAES();
		try {
			c.setKey(key);
			r = c.decrypt(base);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException 
				| InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException |
				BadPaddingException e) {
			
			e.printStackTrace();
		}
		
		return r;
	}

}
