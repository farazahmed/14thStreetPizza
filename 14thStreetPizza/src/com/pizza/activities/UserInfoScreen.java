package com.pizza.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


import com.pizza.interfaces.BaseInterface;

public class UserInfoScreen extends Activity implements BaseInterface {

	EditText edtPhoneNo;
	EditText edtName;
	EditText edtDelieveryAddress;
	EditText edtNearestLandMark;
	EditText edtExtraRequirements;
	Spinner spArea;
	Button btnSubmit;
	ImageView imgHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo_screen);
		setReferences();
		setEventHandlers();
		loadUi();
	}

	@Override
	public void setReferences() {
		edtPhoneNo = (EditText) findViewById(R.id.edtcontactNo);
		edtName = (EditText) findViewById(R.id.edtname);
		edtDelieveryAddress = (EditText) findViewById(R.id.edtdelieveryaddress);
		edtNearestLandMark = (EditText) findViewById(R.id.edtnearestlandmark);
		edtExtraRequirements = (EditText) findViewById(R.id.edtextrarequirements);
		spArea = (Spinner) findViewById(R.id.spdelieveryarea);

		// button submission
		btnSubmit = (Button) findViewById(R.id.btnsubmit);
		// Header Image
		imgHeader = (ImageView) findViewById(R.id.imgheader);

	}

	@Override
	public void loadUi() {

		imgHeader.setImageDrawable(getResources().getDrawable(
				R.drawable.header_order));

	}

	@Override
	public void setEventHandlers() {

		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				checkIfNotSelected();

			}

		});
	}

	private void checkIfNotSelected() {

		if (edtPhoneNo.getText().toString().equals("")) {

				showAlert("Phone Number");
		} 
		else if (edtName.getText().toString().equals("")) {
				showAlert("Name");
				
		} else if (edtDelieveryAddress.getText().toString().equals("")) {
				
			showAlert("Delievery Address");
		}	
		else {
			
				
		}

	}
	
	 private void showAlert(String message) {
			
	    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserInfoScreen.this);
	        alertDialog.setTitle("Warning..");
	        alertDialog.setMessage("Please insert " + message);
	        alertDialog.setIcon(R.drawable.loader);
	        alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            dialog.cancel();
	
	            //finish();
	            //Globals.offlineMode = true;
	            }
	        });
	        
	        alertDialog.show();
	    	
			
		}

}
