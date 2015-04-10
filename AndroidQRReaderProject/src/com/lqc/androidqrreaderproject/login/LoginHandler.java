package com.lqc.androidqrreaderproject.login;
import java.util.ArrayList;
import java.util.List;


public class LoginHandler {
	
	private static LoginHandler mInstance;
	
	private LoginHandler () {}
	
	public static LoginHandler get() {
		if(mInstance == null)
			mInstance = new LoginHandler();
		
		return mInstance;
	}

	public List<Integer> register(String[] loginInfo) {
		
		int index = 0;
		boolean isEmptyString = false;
		List<Integer> emptyFieldsList = null;
		
		for(String field : loginInfo) {
			isEmptyString = checkIfIsEmptyField(field);
			
			if(isEmptyString) {
				if(emptyFieldsList == null)
					emptyFieldsList = new ArrayList<Integer>();
				
				emptyFieldsList.add(index);
			}
			
			++index;
		}	
		return emptyFieldsList;
	}

	private static boolean checkIfIsEmptyField(String field) {
		if(!field.equals(""))
			return false;
		
		else 
			return true; 
	}
}
