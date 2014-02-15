package com.vetonline;

import java.util.Date;
import java.util.List;

import android.app.Application;
import android.util.Log;

import com.google.common.collect.Lists;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.vetonline.data.Pet;
import com.vetonline.data.PetStatus;
import com.vetonline.repo.PetsRepository;
import com.vetonline.repo.UsersRepository;

public class VetOnlineApplication extends Application {

	private static final String PASSWORD = "password";
	private static final String USER = "user";
	private PetsRepository petRepo = new PetsRepository();
	private UsersRepository userRepo = new UsersRepository();
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Parse.initialize(this, "nMYjbWz5uTlO8znJN9KYBsw4sF4DflrWwTPpNWmh", "Y53oFYVpozjxKMLEeaxsfJyPCNu5XIuzYN5jlDrx");
		ParseFacebookUtils.initialize("1409549115960683");
		
		insertTestDataIntoDb();
		try {
			ParseUser.logIn(USER, PASSWORD);
			ParseUser currentUser = ParseUser.getCurrentUser();
			System.out.println(currentUser);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void insertTestDataIntoDb() {
		try {
			ParseUser parseUser = createUser();
			inserPetsIntoDb(parseUser);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private ParseUser createUser() throws ParseException {
		// Sign up user if not exist.
		ParseUser user = userRepo.findUserByUsername(USER);
		if (user != null) {
			return user;
		}
		user = new ParseUser();
		user.setEmail("email1@email.com");
		user.setUsername(USER);
		user.setPassword(PASSWORD);
		user.signUp();
		return user;
	}

	private boolean userExists() throws ParseException {
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("email", "email1@email.com");
		List<ParseUser> find = query.find();
		return ! find.isEmpty();
	}
	
	private void inserPetsIntoDb(ParseUser user) {
		petRepo.deleteAll();
		petRepo.insert(createPetsList(user), user, null);		
	}
	
    private List<Pet> createPetsList(ParseUser user) {
		List<PetStatus> petStatusList = createPetStatusList();
    	
    	List<Pet> pets = Lists.newArrayList(
			new Pet(user.getEmail(), "dori", Pet.Species.CAT, petStatusList),
			new Pet(user.getEmail(), "dsefes", Pet.Species.CAT, petStatusList),
			new Pet(user.getEmail(), "dwesi", Pet.Species.DOG, petStatusList),
			new Pet(user.getEmail(), "desri", Pet.Species.DOG, petStatusList),
			new Pet(user.getEmail(), "asi", Pet.Species.CAT, petStatusList),
			new Pet(user.getEmail(), "maggie", Pet.Species.DOG, petStatusList));

		return pets;
	}
	private List<PetStatus> createPetStatusList() {
		List<PetStatus> statusList = Lists.newArrayList();
		for (int i = 0; i < 10; i++) {
			PetStatus status = new PetStatus(new Date(), "status" + i);
			statusList.add(status);
		}
		return statusList;
	}
}
