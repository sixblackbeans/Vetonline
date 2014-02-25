package com.vetonline.adapter;

import java.util.List;

import com.google.common.collect.Lists;
import com.vetonline.R;
import com.vetonline.R.drawable;
import com.vetonline.R.id;
import com.vetonline.R.layout;
import com.vetonline.data.Pet;
import com.vetonline.data.PetStatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PetArrayAdapter extends ArrayAdapter<Pet> {

	private Context context;
	
	public PetArrayAdapter(Context context, int listPetId, int listPetTextId) {
		super(context, listPetId, listPetTextId);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the layout list_pet.xml
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_pet_item, parent, false);
		
		// Get the individual views within the layout
		ImageView imageView = (ImageView) rowView.findViewById(R.id.pet_image);
		TextView petNameTextView = (TextView) rowView.findViewById(R.id.pet_name);
		TextView currentStatusTextView = (TextView) rowView.findViewById(R.id.pet_current_status);
		
		// Set the data in the view from pet instance.
		Pet pet = getItem(position);
		imageView.setImageResource(pet.getSpecies() == Pet.Species.CAT ? R.drawable.cat : R.drawable.dog);
		petNameTextView.setText(pet.getName());
		currentStatusTextView.setText(getCurrentStatus(pet));
		
		return rowView;
	}

	private CharSequence getCurrentStatus(Pet pet) {
		PetStatus latestStatus = pet.getLatestStatus();
		if (latestStatus != null) {
			return latestStatus.getText();
		}
		return "";
	}

}
