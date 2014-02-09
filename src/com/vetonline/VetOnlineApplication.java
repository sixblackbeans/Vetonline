package com.vetonline;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

public class VetOnlineApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Parse.initialize(this, "nMYjbWz5uTlO8znJN9KYBsw4sF4DflrWwTPpNWmh", "Y53oFYVpozjxKMLEeaxsfJyPCNu5XIuzYN5jlDrx");
		ParseFacebookUtils.initialize("1409549115960683");
		
		// Just to see if this works
//		ParseObject testObject = new ParseObject("TestObject");
//		testObject.put("foo", "bar");
//		testObject.saveInBackground();
	}
}
