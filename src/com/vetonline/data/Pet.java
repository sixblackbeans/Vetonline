package com.vetonline.data;

import java.io.Serializable;
import java.security.acl.Owner;
import java.util.List;

import com.google.common.collect.Lists;
import com.vetonline.data.data.PetOwner;

public class Pet implements Serializable {

	public static enum Species {
		DOG, CAT, OTHER
	}
	private String name;
	private Species species;
	private String email;
	private List<PetStatus> petStatus = Lists.newArrayList();
	
	public Pet() {
	}
	public Pet(String name, Species species) {
		this.name = name;
		this.species = species;
	}
	public Pet(String email, String name, Species species, List<PetStatus> statuses) {
		this(name, species);
		this.setEmail(email);
		this.petStatus = statuses;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Species getSpecies() {
		return species;
	}
	public void setSpecies(Species species) {
		this.species = species;
	}
	public List<PetStatus> getPetStatus() {
		return petStatus;
	}
	public void setPetStatus(List<PetStatus> petStatus) {
		this.petStatus = petStatus;
	}
	public void addPetStatus(PetStatus petStatus) {
		this.petStatus.add(petStatus);
	}
	public PetStatus getLatestStatus() {
		if (petStatus.isEmpty()) {
			return null;
		}
		return petStatus.get(petStatus.size() - 1);
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
