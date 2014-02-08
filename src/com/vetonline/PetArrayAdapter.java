package com.vetonline;

import java.util.List;

import com.vetonline.data.Pet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PetArrayAdapter extends ArrayAdapter<Pet> {

	private Context context;
	
	public PetArrayAdapter(Context context, int listPetId, int listPetTextId, List<Pet> pets) {
		super(context, listPetId, listPetTextId, pets);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the layout list_pet.xml
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_pet_item, parent, false);
		
		// Get the individual views within the layout
		TextView textView = (TextView) rowView.findViewById(R.id.pet_name);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.pet_image);
		
		// Set the data in the view from pet instance.
		Pet pet = getItem(position);
		textView.setText(pet.getName());
		imageView.setImageResource(pet.getSpecies() == Pet.Species.CAT ? R.drawable.cat : R.drawable.dog);
		
		return rowView;
	}

}
