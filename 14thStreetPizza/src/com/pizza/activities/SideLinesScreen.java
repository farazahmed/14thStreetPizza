package com.pizza.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pizza.dao.SideLines;
import com.pizza.interfaces.BaseInterface;
import com.pizza.utilities.Constants;
import com.pizza.utilities.Globals;
import com.pizza.utilities.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class SideLinesScreen extends Activity implements BaseInterface {
	
	private String JSON_ARRAY="sidelines";
	private String GET_NAME="name";
	private String GET_PRICE="price";
	
	String jsonResult = null;
	String slidesNames[] = null;
	String prices[] = null;
	Button[]  btnQuantity;
	
	TableLayout tl;
	TableRow tr;
	TextView[] valueTV;
	
	Button btnNext;
	Button btnReset;
	Button btnAddOther;
	
	//Images 
	ImageView imgHeader = null;
	
	Spinner[] spinner ;
	CheckBox[] chkSlideLines; 
	
	OnClickListener btnListenerQuantity;
	AlertDialog dlgQuantity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.generalinput_screen);
		tl = (TableLayout) findViewById(R.id.maintable);
		setReferences();
		setEventHandlers();
		loadUi();
	
	}
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu)
	    {
	        MenuInflater menuInflater = getMenuInflater();
	        menuInflater.inflate(R.layout.menu, menu);
	        return true;
	    }
		/**
	     * Event Handling for Individual menu item selected
	     * Identify single menu item by it's id
	     * */
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	        switch (item.getItemId())
	        {
	        
	        case R.id.pizzaorder:
	        	
	        	startActivity(new Intent(getApplicationContext(),
						PizzaSizeScreen.class));
				finish();
	            return true;
	  
	        case R.id.sidelines:
	        	Toast.makeText(getApplicationContext(), "Your are on this Menu", Toast.LENGTH_LONG).show();
	            return true;
	 
	        case R.id.drinks:
	           
	        	startActivity(new Intent(getApplicationContext(),
						DrinksScreen.class));
				finish();
	            return true;
	 
	        case R.id.sweets:
	           
	        	startActivity(new Intent(getApplicationContext(),
						SweetsScreen.class));
				finish();
	            return true;
	 
	        case R.id.deals:
	        	startActivity(new Intent(getApplicationContext(),
						DealsScreen.class));
				finish();
				
	            return true;
	 
	        case R.id.vieworder:
	        	startActivity(new Intent(getApplicationContext(),
						CompleteOrderScreen.class));
	            return true;
	 
	       
	 
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }    
	    

	/** This function add the data to the table 
	 * @param prices 
	 * @param slidesNames **/
	@SuppressWarnings("unchecked")
	public void addData(String[] slidesNames, String[] prices) {
		addHeaders();
		chkSlideLines = new CheckBox[slidesNames.length];
		spinner = new Spinner[slidesNames.length];
		valueTV = new TextView[slidesNames.length];
		btnQuantity = new Button[slidesNames.length];
		@SuppressWarnings("rawtypes")
		ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
		        android.R.layout.simple_spinner_item,Globals.arrQuantity);
		
		for (int i = 0; i < slidesNames.length; i++) {
			/** Create a TableRow dynamically **/
			tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			/** Creating a TextView to add to the row **/
			chkSlideLines[i] = new CheckBox(this);
			chkSlideLines[i].setText(slidesNames[i]);
			// chckBox.setTextColor(Color.RED);
			chkSlideLines[i].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			chkSlideLines[i].setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			tr.addView(chkSlideLines[i]); // Adding textView to tablerow.
			valueTV[i] = new TextView(this);
			valueTV[i].setText(prices[i]);
			// valueTV.setTextColor(Color.GREEN);
			valueTV[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			valueTV[i].setPadding(5, 5, 5, 5);
			valueTV[i].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			tr.addView(valueTV[i]); // Adding textView to tablerow.
			// Add the TableRow to the TableLayout
			
			btnQuantity[i] = new Button(this);
			btnQuantity[i].setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			btnQuantity[i].setPadding(5, 5, 5, 5);
			// spinner[i].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			btnQuantity[i].setId(i);
			btnQuantity[i].setText("1");

			//btnQuanity[i].setBackgroundColor(Color.CYAN);
			btnQuantity[i].setOnClickListener(btnListenerQuantity);
			tr.addView(btnQuantity[i]);
			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
		}
		
	}

	@Override
	public void setReferences() {

		// Buttons
		btnNext  = (Button)findViewById(R.id.btnnext);
		btnReset= (Button)findViewById(R.id.btnreset);
		btnAddOther  = (Button)findViewById(R.id.btnaddanother);
		
		imgHeader = (ImageView)findViewById(R.id.imgheader);
	}

	@Override
	public void loadUi() {
		
		imgHeader.setImageDrawable(getResources().getDrawable(R.drawable.header_sidelines));
		if(Globals.offlineMode){
			
			addData(Constants.slidesNames, Constants.sideLineprices);
		}
		else {
			new getPricesSlideLines()
			.execute(new String[] { Constants.SERVICE_SLIDELINES });
		}
	}

	@Override
	public void setEventHandlers() {
		
	btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Helper.getInstance().loadAnimationWithSound(getApplicationContext(), R.anim.fade_in, btnNext);
				createSideLine();
				startActivity(new Intent(SideLinesScreen.this, DrinksScreen.class));
				finish();
			}
		});
		
		
		btnReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Helper.getInstance().loadAnimationWithSound(getApplicationContext(), R.anim.fade_in, btnReset);
				
			}
		});
		
		
		btnAddOther.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Helper.getInstance().loadAnimationWithSound(getApplicationContext(), R.anim.fade_in, btnAddOther);
			}
		});
		
		btnListenerQuantity = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Button btn = (Button) v;
				getQuantity(btn);
			}
		};
		
	}

	// Async Task to access the web
	private class getPricesSlideLines extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog = null;

		@Override
		protected void onPreExecute() {

			progressDialog = new ProgressDialog(SideLinesScreen.this);
			progressDialog.setTitle("Processing...");
			progressDialog.setIcon(R.drawable.loader);
			progressDialog.setMessage("Please wait.");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(true);
			progressDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			try {
				HttpResponse response = httpclient.execute(httppost);
				Log.d("Response", response.toString());
				HttpEntity entity = response.getEntity();
				jsonResult = inputStreamToString(entity.getContent())
						.toString();
			}

			catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				// e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {

			progressDialog.dismiss();
			getAllPricesSlideLines();
			addData(slidesNames,prices);

		}

		public void getAllPricesSlideLines() {
			try {
				JSONObject jsonResponse = new JSONObject(jsonResult);
				JSONArray jsonMainNode = jsonResponse
						.optJSONArray(JSON_ARRAY);
				slidesNames = new String[jsonMainNode.length()];
				prices = new String[jsonMainNode.length()];

				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
					slidesNames[i] = jsonChildNode.optString(GET_NAME);
					prices[i] = jsonChildNode.optString(GET_PRICE);
				}
				
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "Error" + e.toString(),
						Toast.LENGTH_SHORT).show();
			}
			
		}
	}
	
	private void createSideLine() {
		ArrayList<String> sideLinesNames = new ArrayList<String>();
		ArrayList<String> sideLinesPrice = new ArrayList<String>();
		ArrayList<String> sideLinesQuantity = new ArrayList<String>();
		
		for(int i=0;i<chkSlideLines.length;i++){
			
				if(chkSlideLines[i].isChecked()){
					sideLinesNames.add(chkSlideLines[i].getText().toString());
					if(Globals.offlineMode){
						sideLinesPrice.add(Constants.sideLineprices[i]);	
					}
					else {
						sideLinesPrice.add(prices[i]);	
					}
					sideLinesQuantity.add(btnQuantity[i].getText().toString().trim());
				}
		}
		
		Globals.sideLinesRecord.add(new SideLines(sideLinesNames, sideLinesPrice, sideLinesQuantity));
	}

	public void getQuantity(final Button btn){
		
        // Creating and Building the Dialog 
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Quantity");
        builder.setSingleChoiceItems(Globals.arrQuantity, -1, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {

        	btn.setText(Integer.toString(item+1));
            dlgQuantity.dismiss();    
            }
        });
        dlgQuantity = builder.create();
        dlgQuantity.show();

}
	
	private void addHeaders() {
		tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		TextView txtViewDescription = new TextView(this);
		txtViewDescription.setText("SlideLines");
		// valueTV.setTextColor(Color.GREEN);
		txtViewDescription.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		txtViewDescription.setPadding(5, 5, 5, 5);
		txtViewDescription.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		txtViewDescription.setTextColor(Color.BLUE);
		txtViewDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tr.addView(txtViewDescription);
	
		TextView txtViewQuantity = new TextView(this);
		txtViewQuantity.setText("Price");
		// valueTV.setTextColor(Color.GREEN);
		txtViewQuantity.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		txtViewQuantity.setPadding(5, 5, 5, 5);
		txtViewQuantity.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		txtViewQuantity.setTextColor(Color.BLUE);
		txtViewQuantity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tr.addView(txtViewQuantity);
		
		TextView txtViewPrice = new TextView(this);
		txtViewPrice.setText("Quantity");
		// valueTV.setTextColor(Color.GREEN);
		txtViewPrice.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		txtViewPrice.setPadding(5, 5, 5, 5);
		txtViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		txtViewPrice.setTextColor(Color.BLUE);
		txtViewPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tr.addView(txtViewPrice);
		
		tl.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}
}