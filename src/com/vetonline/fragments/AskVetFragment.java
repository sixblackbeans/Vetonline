package com.vetonline.fragments;

import java.io.InputStream;



import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.actionbarsherlock.app.SherlockFragment;
import com.vetonline.R;
 
public class AskVetFragment extends SherlockFragment {
	
    protected static final int SELECT_PHOTO = 0;
	protected static final int REQUEST_IMAGE_CAPTURE = 1;
	private LinearLayout imagesLinearLayout;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		
        // Get the view from fragmenttab3.xml
        View view = inflater.inflate(R.layout.fragment_tab_askvet, container, false);
     
        
        View findViewById = view.findViewById(R.id.selectedImages);
        
        imagesLinearLayout = (LinearLayout) findViewById;
        
        configureImageGalleryButton(view);
        configureTakePictureButton(view);
        configureNextButton(view);
        
        addImagesToGallery(4);
        
        return view;
    }

	private void configureNextButton(View view) {
		Button button = (Button) view.findViewById(R.id.next_button);
		
	}

	private void configureTakePictureButton(View view) {
		ImageButton pictureButton = (ImageButton) view.findViewById(R.id.button_take_pic);
        if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
        	pictureButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
				        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
				    }
				}
			});
        } else {
        	pictureButton.setEnabled(false);
        }
	}

	private void configureImageGalleryButton(View view) {
		ImageButton galleryButton = (ImageButton) view.findViewById(R.id.button_select_gallery);
        galleryButton.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		// Choose image from gallery
        		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        		photoPickerIntent.setType("image/*");
        		startActivityForResult(photoPickerIntent, SELECT_PHOTO);  
        	}
        });
	}
 
	private void addImagesToGallery(int count) {
		for (int i = 0; i < count; i++) {
			ImageView image=new ImageView(getActivity());
			image.setBackgroundResource(R.drawable.dog);
			LayoutParams lpView = new LayoutParams(130,130);
			imagesLinearLayout.addView(image, lpView);
		}
	}
	
	private void addBitmapToGallery(Bitmap bitmap) {
		ImageView image=new ImageView(getActivity());
		image.setImageBitmap(bitmap);
//		addImageView(image);
//		imageGallery.addView(image);
		LayoutParams lpView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		imagesLinearLayout.addView(image, lpView);
//		LinearLayout.LayoutParams imParams = 
//				new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//				ImageView imSex = new ImageView(context);
//				imSex.setImageResource(getmyImage());
//
//				mainlayout.addView(imSex,imParams); 
	}

	private void addImageView(ImageView image) {
		Button button = (Button) getActivity().findViewById(R.id.next_button);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
	    
	    if (resultCode != Activity.RESULT_OK) {
	    	return;
	    }
	    
	    switch(requestCode) { 
	    	case SELECT_PHOTO:
	            Uri selectedImage = imageReturnedIntent.getData();
	            String[] filePathColumn = {MediaStore.Images.Media.DATA};

	            Cursor cursor = getActivity().getContentResolver().query(
	                               selectedImage, filePathColumn, null, null, null);
	            cursor.moveToFirst();

	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String filePath = cursor.getString(columnIndex);
	            cursor.close();
	            Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
	            addBitmapToGallery(yourSelectedImage);
//	            Toast.makeText(getActivity(), "got image", Toast.LENGTH_SHORT).show();
		        break;
	    	case REQUEST_IMAGE_CAPTURE:
	            Bundle extras = imageReturnedIntent.getExtras();
	            Bitmap imageBitmap = (Bitmap) extras.get("data");
	            addBitmapToGallery(imageBitmap);
	            break;
	    }
	}
	
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }
 
}