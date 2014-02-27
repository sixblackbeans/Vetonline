package com.vetonline;
 
import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFacebookUtils.Permissions;
import com.parse.ParseUser;
import com.vetonline.R;
import com.vetonline.adapter.ViewPagerAdapter;
 
public class MainActivity extends SherlockFragmentActivity {
 
	private static final String MENU_ITEM_LOGIN = "Login";
	private static final String MENU_ITEM_LOGOUT = "Logout";
    
    private ActionBar mActionBar;
    private ViewPager mPager;
    private Tab tab;
	private Menu menu;
 
	@Override
	protected void onRestart() {
		Log.d("app", "onRestart() called");
		super.onRestart();
	}
	@Override
	protected void onStart() {
		Log.d("app", "onStart() called");
		super.onStart();
	}
	@Override
	protected void onResume() {
		Log.d("app", "onResume() called");
		super.onResume();
	}
	@Override
	protected void onPause() {
		Log.d("app", "onPause() called");
		super.onPause();
	}
	@Override
	protected void onStop() {
		Log.d("app", "onStop() called");
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		Log.d("app", "onDestroy() called");
		super.onDestroy();
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.d("app", "onCreate() called");
    	
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_main);
 
        // Activate Navigation Mode Tabs
        setupActionBar();
 
        // Locate ViewPager in activity_main.xml
        mPager = (ViewPager) findViewById(R.id.pager);
 
        // Capture ViewPager page swipes
        ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Find the ViewPager Position
                mActionBar.setSelectedNavigationItem(position);
            }
        };
 
        mPager.setOnPageChangeListener(ViewPagerListener);
        // Locate the adapter class called ViewPagerAdapter.java
        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Set the View Pager Adapter into ViewPager
        mPager.setAdapter(viewpageradapter);
 
        // Capture tab button clicks
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
 
            @Override
            public void onTabSelected(Tab tab, FragmentTransaction ft) {
                // Pass the position on tab click to ViewPager
                mPager.setCurrentItem(tab.getPosition());
            }
 
            @Override
            public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            }
 
            @Override
            public void onTabReselected(Tab tab, FragmentTransaction ft) {
            }
        };
 
        // Create first Tab
        tab = mActionBar.newTab().setText("Pet Status").setTabListener(tabListener);
        mActionBar.addTab(tab);
 
        // Create second Tab
        tab = mActionBar.newTab().setText("Ask a vet").setTabListener(tabListener);
        mActionBar.addTab(tab);
 
        // Create third Tab
        tab = mActionBar.newTab().setText("Invoices").setTabListener(tabListener);
        mActionBar.addTab(tab);
 
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu = menu;
        SubMenu sub = menu.addSubMenu("Theme");
        
        if (ParseUser.getCurrentUser() == null) {
        	sub.add(0, R.style.Theme_Sherlock, 0, MENU_ITEM_LOGIN);
        } else {
        	sub.add(0, R.style.Theme_Sherlock, 0, MENU_ITEM_LOGOUT);
        }
        sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        sub.setIcon(R.drawable.ic_action_settings);
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	Log.d("app", "manu item clicked! " + featureId + " menuitem: " + item);
    	if (MENU_ITEM_LOGIN.equals(item.getTitle())) {
    		loginFacebook();
    	} else if (MENU_ITEM_LOGOUT.equals(item.getTitle())) {
    		Log.d("app", "Logging out user");
    		ParseUser.logOut();
    		recreateMenu();
    	}
    	return super.onMenuItemSelected(featureId, item);
    }
    
	private void loginFacebook() {
		ParseFacebookUtils.logIn(Arrays.asList("email", Permissions.Friends.ABOUT_ME), 
				this, 
				new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				if (user == null) {
					Log.d("app", "Oh no, user cancelled facebook login");
					return;
				} 
				if (user.isNew()) {
					Log.d("MyApp", "User signed up and logged in through Facebook!");
					saveFacebookInfoInBackground();
				} else {
					Log.d("MyApp", "User logged in through Facebook!");
				}
				recreateMenu();
			}
		});
	}
	
	private void recreateMenu() {
		menu.clear();
		onCreateOptionsMenu(menu);
	}
	
	private static void saveFacebookInfoInBackground() {
//		Request.executeMeRequestAsync(ParseFacebookUtils.getSession(), new Request.GraphUserCallback() {
		Request.newMeRequest(ParseFacebookUtils.getSession(), new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
			      if (user != null) {
				        ParseUser.getCurrentUser().put("fbId", user.getId());
				        ParseUser.getCurrentUser().setEmail((String)user.getProperty("email"));
				        ParseUser.getCurrentUser().put("emailVerified", true);
				        ParseUser.getCurrentUser().saveInBackground();
				      }
			}});
	}

	private void setupActionBar() {
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	}
}