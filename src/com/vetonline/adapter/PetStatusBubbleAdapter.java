package com.vetonline.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vetonline.R;
import com.vetonline.data.PetStatus;

public class PetStatusBubbleAdapter extends BaseAdapter {

	private Context context;
	private List<PetStatus> petStatuses;
	
	
	/**
	 * TODO: When create this adapter, must pass in all status messages from Pet object.
	 * @param mContext
	 * @param mMessages
	 */
	public PetStatusBubbleAdapter(Context mContext, List<PetStatus> mMessages) {
		super();
		this.context = mContext;
		this.petStatuses = mMessages;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PetStatus petStatus = (PetStatus) getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.status_row, parent, false);
			TextView textView = (TextView) convertView.findViewById(R.id.status_text);
			textView.setText(petStatus.getText());
		} else {
			TextView statusText = (TextView) convertView.findViewById(R.id.status_text);
			statusText.setText(petStatus.getText());
		}
		return convertView;
	}

	@Override
	public int getCount() {
		return petStatuses.size();
	}

	@Override
	public Object getItem(int position) {
		return petStatuses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


}
