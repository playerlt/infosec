package com.index.utils;

//import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
//import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import com.sun.xml.internal.ws.api.config.management.ManagedEndpointFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;


public class PasswordHelper {

	private static PasswordEncoder passwordEncoder =
			PasswordEncoderFactories.createDelegatingPasswordEncoder();

	
	public static final String getSaltedMD5Password(String password, Object salt){

		MessageDigestPasswordEncoder md5 = new MessageDigestPasswordEncoder("MD5");
		return md5.encode(password);
	}
	

	public static final String getSaltedSHAPassword(String password, Object salt){
		MessageDigestPasswordEncoder sha = new MessageDigestPasswordEncoder("SHA-256");
		return sha.encode(password);
	}
	
	public static final String getBCryptPassword(String password){
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		return bCrypt.encode(password);
	} 

	public static void main(String[] args) {
				
		String pwd_plaintext = "123456";
		
		//String encoded_password = PasswordHelper.getSaltedSHAPassword(pwd_plaintext, null);
		String encoded_password = PasswordHelper.getBCryptPassword(pwd_plaintext);
		
		System.out.println(encoded_password);

	}
	
}
