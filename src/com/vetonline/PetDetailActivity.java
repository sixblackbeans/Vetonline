package com.vetonline;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.vetonline.adapter.PetStatusBubbleAdapter;
import com.vetonline.data.Pet;
import com.vetonline.data.PetStatus;
import com.vetonline.fragments.PetStatusFragment;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class PetDetailActivity extends SherlockActivity {

	/** Key in intent whose value is the Pet object that was clicked. */
	public final static String CLICKED_PET = "com.vetonline.CLICKED_PET";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet_detail);
		// Show the Up button in the action bar.
		setupActionBar();
		setupContent();
	}

	private void setupContent() {
		Intent intent = getIntent();
		Pet pet = (Pet) intent.getSerializableExtra(CLICKED_PET);
		TextView textViewPetName = (TextView) findViewById(R.id.text_pet_detail_name);
		TextView textViewCurrentStatus = (TextView) findViewById(R.id.text_pet_detail_status);
//		TextView textViewStatusHistory = (TextView) findViewById(R.id.text_pet_status_history);
		
		textViewPetName.setText(pet.getName());
		createCurrentStatusText(pet);
		textViewCurrentStatus.setText(createCurrentStatusText(pet));
		
		ListView statusListView = (ListView) findViewById(R.id.pet_status_list);
		statusListView.setAdapter(new PetStatusBubbleAdapter(this, pet.getPetStatus()));
//		textViewStatusHistory.setText(createStatusHistoryText(pet.getPetStatus()));
	}

	private Spanned createCurrentStatusText(Pet pet) {
		String status = pet.getLatestStatus() == null ? "Not at clinic" : pet.getLatestStatus().getText();
		return Html.fromHtml("I am now: <b>" + status + "</b>");
	}

	private Spanned createStatusHistoryText(List<PetStatus> petStatus) {
		List<String> statuses = Lists.transform(petStatus, new Function<PetStatus, String>() {
			public String apply(PetStatus ps) {
				Date date = ps.getDate();
				String stringDate = DateUtils.formatDateTime(getApplicationContext(),
						date.getTime(), 
						DateUtils.FORMAT_SHOW_DATE | 
						DateUtils.FORMAT_SHOW_TIME |
						DateUtils.FORMAT_NUMERIC_DATE);
				
				return stringDate + ": <b>" + ps.getText() + "</b>";
			}
		});
		Log.d("sdf", "statuses: " + statuses);
		String formattedStatusesText = Joiner.on("<br/>").join(statuses);
		return Html.fromHtml(formattedStatusesText);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	private void setupActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * Need this so that when user clicking back on action bar goes back to previous activity.
	 */
	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
	}
}
