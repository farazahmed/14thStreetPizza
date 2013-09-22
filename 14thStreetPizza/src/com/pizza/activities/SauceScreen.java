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

import com.pizza.dao.Veggies;
import com.pizza.interfaces.BaseInterface;
import com.pizza.utilities.Constants;
import com.pizza.utilities.Globals;
import com.pizza.utilities.Helper;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class SauceScreen extends Activity implements BaseInterface, OnClickListener {
	@SuppressWarnings("unused")
	private String TAG = "saucescreen";
	private String JSON_ARRAY="sauce";
	private String GET_NAME="name";
	
	String jsonResult = null;
	String sauceNames[] = null;
//	String prices[] = null;

	TableLayout tl;
	TableRow tr;
	
	CheckBox chckBox;
	CheckBox[] chkSauceScreen;
	Button btnFlavour;
	Button btnReset;
	Button btnAddOther;
	
	//Images 
	ImageView imgHeader = null;
	
	

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
        	 startActivity(new Intent(getApplicationContext(),
 					SideLinesScreen.class));
 			finish();
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
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }  
	/** This function add the data to the table **/
	public void addData() {
		addHeaders();
		chkSauceScreen = new CheckBox[sauceNames.length];

		for (int i = 0; i < sauceNames.length; i++) {
			/** Create a TableRow dynamically **/
			tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			/** Creating a TextView to add to the row **/
			chkSauceScreen[i] = new CheckBox(this);
			chkSauceScreen[i].setText(sauceNames[i]);
			chkSauceScreen[i].setId(i);
			chkSauceScreen[i].setOnClickListener(this);
			// chckBox.setTextColor(Color.RED);
			chkSauceScreen[i].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			chkSauceScreen[i].setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			tr.addView(chkSauceScreen[i]); // Adding textView to tablerow.
			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}

	@Override
	public void setReferences() {

		// Buttons
		btnFlavour  = (Button)findViewById(R.id.btnnext);
		btnFlavour.setText("Flavour");
		btnReset= (Button)findViewById(R.id.btnreset);
		btnAddOther  = (Button)findViewById(R.id.btnaddanother);
		imgHeader = (ImageView)findViewById(R.id.imgheader);
	}

	@Override
	public void loadUi() {
		imgHeader.setImageDrawable(getResources().getDrawable(R.drawable.header_pizza));
		new GetSauceNames()
				.execute(new String[] { Constants.SERVICE_SAUCE });
	}

	
	@Override
	public void setEventHandlers() {
		
	  btnFlavour.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Helper.getInstance().loadAnimationWithSound(getApplicationContext(), R.anim.fade_in, btnFlavour);
				createSauce();
				startActivity(new Intent(SauceScreen.this, FlavourScreen.class));
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
				startActivity(new Intent(SauceScreen.this, HomeScreen.class));
				finish();
			}
		});
	}

	// Async Task to access the web
	private class GetSauceNames extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog = null;

		@Override
		protected void onPreExecute() {

			progressDialog = new ProgressDialog(SauceScreen.this);
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
			getAllSauceNames();
			addData();

		}

		public void getAllSauceNames() {
			try {
				JSONObject jsonResponse = new JSONObject(jsonResult);
				JSONArray jsonMainNode = jsonResponse
						.optJSONArray(JSON_ARRAY);
				sauceNames = new String[jsonMainNode.length()];
			
				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
					sauceNames[i] = jsonChildNode.optString(GET_NAME);
					
				}
				
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "Error" + e.toString(),
						Toast.LENGTH_SHORT).show();
			}

			
		}
		

	}
	private void createSauce() {
		ArrayList<String> tmpSauceNames= new ArrayList<String>();
		
		for(int i=0;i<chkSauceScreen.length;i++){
				
				if(chkSauceScreen[i].isChecked()){
					tmpSauceNames.add(chkSauceScreen[i].getText().toString());
				}
		}
		Globals.veggiesRecord.add(new Veggies(tmpSauceNames));
	}
	
	@Override
	public void onClick(View v) {
		
		for(int i=0;i<chkSauceScreen.length;i++){
			CheckBox chk = (CheckBox) v;
			if(i!=chk.getId()){
				
				chkSauceScreen[i].setChecked(false);
				
			}
		}
	}
	private void addHeaders() {
		tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		TextView txtViewDescription = new TextView(this);
		txtViewDescription.setText("Sauce");
		// valueTV.setTextColor(Color.GREEN);
		txtViewDescription.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		txtViewDescription.setPadding(5, 5, 5, 5);
		txtViewDescription.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		txtViewDescription.setTextColor(Color.BLUE);
		txtViewDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tr.addView(txtViewDescription);
	
		
		tl.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}
	

}