package com.vetonline.repo;

import java.io.Serializable;
import java.security.acl.Owner;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.text.format.DateUtils;
import android.util.Log;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.vetonline.data.Pet;
import com.vetonline.data.PetStatus;
import com.vetonline.data.data.PetOwner;

public class PetsRepository {
	
	private static final String PET_PARSE_NAME = "Pet";
	private static final String PET_NAME = "name";
	private static final String PET_SPECIES = "species";
	private static final String PET_STATUS_LIST = "statusList";
	private static final String PET_STATUS_TEXT = "petStatusText";
	private static final String PET_STATUS_DATE_LONG = "petStatusDateLong";
	private static final String PET_OWNER = "petOwner";
	private static final String PET_OWNER_EMAIL = "petOwnerEmail";
	private static final String PET_RAW_JSON = "pet";
	
	/**
	 * Deletes all pets in db. THis is synchronous operation.
	 */
	public void deleteAll() {
		try {
			ParseQuery<ParseObject> q = ParseQuery.getQuery(PET_PARSE_NAME);
			List<ParseObject> result = q.find();
			ParseObject.deleteAll(result);
		} catch (Exception e) {
			String msg = "exception thrown tring to delete Pets";
			Log.e("app", msg, e);
			throw new RuntimeException(msg, e);
		}
	}
	
	/**
	 * Inserts pets into db. Asynchronous. If saveCallback is not null, will call after done. Sets owner to value passed in.
	 * @param pets
	 * @param user 
	 * @param saveCallback
	 */
	public void insert(List<Pet> pets, final ParseUser owner, SaveCallback saveCallback) {
		try {
			List<ParseObject> parseObjects = Lists.transform(pets, new Function<Pet, ParseObject>() {
				public ParseObject apply(Pet pet) {
					return toParseObject(pet, owner);
				}
			});
			if (saveCallback != null) {
				ParseObject.saveAllInBackground(parseObjects, saveCallback);
			} else {
				ParseObject.saveAll(parseObjects);
			}
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Inserts a pet into db. Asynchronous. If saveCallback not null, will call after done.
	 * @param pet
	 * @param saveCallback
	 */
	public void insert(Pet pet, final ParseUser owner, SaveCallback saveCallback) {
		ParseObject obj = toParseObject(pet, owner);
		if (saveCallback != null) {
			obj.saveInBackground(saveCallback);
		} else {
			obj.saveInBackground();
		}
	}
	
	public void getPetsOfUser(ParseUser user, FindCallback<ParseObject> callback) {
		ParseQuery<ParseObject> q = ParseQuery.getQuery(PET_PARSE_NAME);
		q.whereEqualTo(PET_OWNER, user);
		q.findInBackground(callback);
	}
	
	public List<Pet> getPetsOfUser(ParseUser user) {
		try {
			ParseQuery<ParseObject> q = ParseQuery.getQuery(PET_PARSE_NAME);
			q.whereEqualTo(PET_OWNER, user);
			List<ParseObject> result = q.find();
			return toPets(result);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}	
	
	public List<Pet> toPets(List<ParseObject> petParseObjects) {
		List<Pet> pets = Lists.newArrayList();
		for (ParseObject parseObject : petParseObjects) {
			pets.add(toPet(parseObject));
		}
		return pets;
	}

	private Pet toPet(ParseObject parseObject) {
		String petJson = parseObject.getString(PET_RAW_JSON);
		Pet pet = new Gson().fromJson(petJson, Pet.class);
		return pet;
	}

	private ParseObject toParseObject(Pet pet, ParseUser owner) {
		ParseObject obj = new ParseObject(PET_PARSE_NAME);
		obj.put(PET_RAW_JSON, new Gson().toJson(pet));
		obj.put(PET_OWNER, owner);
		return obj;
	}
}
