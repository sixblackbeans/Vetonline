package com.vetonline.fragments;
 
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.common.collect.Lists;
import com.vetonline.PetArrayAdapter;
import com.vetonline.R;
import com.vetonline.R.id;
import com.vetonline.R.layout;
import com.vetonline.data.Pet;
 
public class PetStatusFragment extends SherlockFragment {
	
	private static final List<Pet> PETS = Lists.newArrayList(
			new Pet("dori", Pet.Species.CAT),
			new Pet("dsefes", Pet.Species.CAT),
			new Pet("dwesi", Pet.Species.DOG),
			new Pet("desri", Pet.Species.DOG),
			new Pet("asi", Pet.Species.CAT),
			new Pet("maggie", Pet.Species.DOG));
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View view = inflater.inflate(R.layout.fragment_tab_petstatus, container, false);
        ListView listView = (ListView) view.findViewById(R.id.pet_list_view);
        listView.setAdapter(createListAdapter());
        
        return view;
    }
 
    private ListAdapter createListAdapter() {
    	
    	return new PetArrayAdapter(getActivity(), R.layout.list_pet_item, R.id.pet_name, PETS);
    	
//    	return new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, 
//    			Lists.newArrayList("one", "two"));
	}

	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }
 
}