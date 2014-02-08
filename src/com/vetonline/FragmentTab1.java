package com.vetonline;
 
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
import com.vetonline.R;
 
public class FragmentTab1 extends SherlockFragment {
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView1);
        listView.setAdapter(createListAdapter());
        
        return view;
    }
 
    private ListAdapter createListAdapter() {
    	List<String> list = new ArrayList<String>();
    	list.add("one");
    	list.add("two");
    	return new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
	}

	@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }
 
}