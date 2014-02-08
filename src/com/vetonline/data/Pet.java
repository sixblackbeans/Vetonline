package com.vetonline.data;

public class Pet {

	public static enum Species {
		DOG, CAT, OTHER
	}
	private String name;
	private Species species;
	
	public Pet() {
	}
	public Pet(String name, Species species) {
		this.name = name;
		this.species = species;
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
	
	
}
