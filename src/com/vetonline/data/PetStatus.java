package com.vetonline.data;

import java.io.Serializable;
import java.util.Date;

public class PetStatus implements Serializable {

	private Date date;
	private String text;
	
	public PetStatus() {
	}
	
	public PetStatus(Date date, String text) {
		this.date = date;
		this.text = text;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
