package com.vetonline.fragments;
 
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.common.collect.Lists;
import com.vetonline.PetArrayAdapter;
import com.vetonline.PetDetailActivity;
import com.vetonline.R;
import com.vetonline.data.Pet;
import com.vetonline.data.PetStatus;
 
public class PetStatusFragment extends SherlockFragment {
	
	/** Key in intent whose value is the Pet object that was clicked. */
	public final static String CLICKED_PET = "com.vetonline.CLICKED_PET";
	
	private List<Pet> pets;
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	pets = createHardCodedPetsList();
    	
        View view = inflater.inflate(R.layout.fragment_tab_petstatus, container, false);
        ListView listView = (ListView) view.findViewById(R.id.pet_list_view);
        listView.setAdapter(createListAdapter());
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View parentLayout, int index,
					long rowId) {
				// START HERE!!! Need to go to another activity to show details of 
				// pet status. Also show latest status in pet list view for each pet.
				Intent intent = new Intent(getActivity(), PetDetailActivity.class);
				intent.putExtra(CLICKED_PET, pets.get(index));
				startActivity(intent);
			}
		});
        
        return view;
    }
 
    private List<Pet> createHardCodedPetsList() {
		List<PetStatus> petStatusList = createPetStatusList();
    	
    	List<Pet> pets = Lists.newArrayList(
			new Pet("dori", Pet.Species.CAT, petStatusList),
			new Pet("dsefes", Pet.Species.CAT, petStatusList),
			new Pet("dwesi", Pet.Species.DOG, petStatusList),
			new Pet("desri", Pet.Species.DOG, petStatusList),
			new Pet("asi", Pet.Species.CAT, petStatusList),
			new Pet("maggie", Pet.Species.DOG, petStatusList));
		
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

	private ListAdapter createListAdapter() {
    	
    	return new PetArrayAdapter(getActivity(), R.layout.list_pet_item, R.id.pet_name, pets);
    	
//    	return new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, 
//    			Lists.newArrayList("one", "two"));
	}

	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }
 
}