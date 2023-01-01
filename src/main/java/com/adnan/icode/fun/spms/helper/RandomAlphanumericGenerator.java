package com.adnan.icode.fun.spms.helper;

import java.util.Random;

public class RandomAlphanumericGenerator {
	
	public String generate() {
		
		Random random = new Random();
		
		// chose a Character random from this String
		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		         + "0123456789"
		         + "abcdefghijklmnopqrstuvxyz";
		
		// create random string builder
	    StringBuilder sb = new StringBuilder();
	    
	    int length = 10;
	    
	    for(int i = 0; i < length; i++) {
	    	
	    	// generate random index number
	    	int randomIndex = random.nextInt(alphaNumericString.length());
	    	
	    	// get character specified by index from the string
	        
	    	char randomChar = alphaNumericString.charAt(randomIndex);
	    	
	    	sb.append(randomChar);
	    	
	    }
	    
	    String randomString = sb.toString();

		
		return randomString;
	}
}
