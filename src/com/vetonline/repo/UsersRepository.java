package com.vetonline.repo;

import java.util.List;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class UsersRepository {

	private static final String PET_OBJECT = "User";
	
	public ParseUser findUserByUsername(String username) {
		try {
			ParseQuery<ParseUser> query = ParseUser.getQuery();
			query.whereEqualTo("username", username);
			List<ParseUser> users = query.find();
			if (!users.isEmpty()) {
				return users.get(0);
			}
			return null;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void createUser(String email, String password, SignUpCallback callback) {
		ParseUser parseUser = new ParseUser();
		parseUser.setEmail(email);
		parseUser.setPassword(password);
		parseUser.signUpInBackground(callback);
	}

	
}
