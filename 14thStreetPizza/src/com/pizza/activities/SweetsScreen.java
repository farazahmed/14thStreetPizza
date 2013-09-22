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
import com.pizza.dao.Sweets;
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

public class SweetsScreen extends Activity implements BaseInterface {
	
	private String JSON_ARRAY="sweets";
	private String GET_NAME="name";
	private String GET_PRICE="price";
	
	String jsonResult = null;
	String sweetsNames[] = null;
	String prices[] = null;
	Button[] btnbtnQuantity;
	TableLayout tl;
	TableRow tr;
	TextView[] valueTV;
	CheckBox chckBox;
	
	Button btnNext;
	Button btnReset;
	Button btnAddOther;
	
	//Images 
	ImageView imgHeader = null;
	Spinner[] spinner;
	CheckBox[] chkSweets;
	
	AlertDialog dlgQuantity;
	
	OnClickListener btnListenerQuantity;

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
          Toast.makeText(getApplicationContext(), "Your are on this Menu", Toast.LENGTH_LONG).show();
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

	
	@SuppressWarnings("unchecked")
	public void addData() {
		addHeaders();
		chkSweets = new CheckBox[sweetsNames.length];
		spinner = new Spinner[sweetsNames.length];
		valueTV = new TextView[sweetsNames.length];
		btnbtnQuantity = new Button[sweetsNames.length];
		
		
		for (int i = 0; i < sweetsNames.length; i++) {
			/** Create a TableRow dynamically **/
			tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			/** Creating a TextView to add to the row **/
			chkSweets[i] = new CheckBox(this);
			chkSweets[i].setText(sweetsNames[i]);
			// chckBox.setTextColor(Color.RED);
			chkSweets[i].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			chkSweets[i].setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			tr.addView(chkSweets[i]); // Adding textView to tablerow.

			/** Creating another textview **/
			valueTV[i] = new TextView(this);
			valueTV[i].setText(prices[i]);
			// valueTV.setTextColor(Color.GREEN);
			valueTV[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			valueTV[i].setPadding(5, 5, 5, 5);
			valueTV[i].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			tr.addView(valueTV[i]); // Adding textView to tablerow.
			
			btnbtnQuantity[i] = new Button(this);
			btnbtnQuantity[i].setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			btnbtnQuantity[i].setPadding(5, 5, 5, 5);
			// spinner[i].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			btnbtnQuantity[i].setId(i);
			btnbtnQuantity[i].setText("1");
		
			//btnQuanity[i].setBackgroundColor(Color.CYAN);
			btnbtnQuantity[i].setOnClickListener(btnListenerQuantity);
			tr.addView(btnbtnQuantity[i]);
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
		// images
		imgHeader = (ImageView)findViewById(R.id.imgheader);
	}

	@Override
	public void loadUi() {
		imgHeader.setImageDrawable(getResources().getDrawable(R.drawable.header_sweets));
		new getPricesSlideLines()
				.execute(new String[] { Constants.SERVICE_SWEETS });
	}

	@Override
	public void setEventHandlers() {
		
	btnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Helper.getInstance().loadAnimationWithSound(getApplicationContext(), R.anim.fade_in, btnNext);
				createSweets();
				startActivity(new Intent(SweetsScreen.this, DealsScreen.class));
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
				startActivity(new Intent(SweetsScreen.this, HomeScreen.class));
				finish();
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

			progressDialog = new ProgressDialog(SweetsScreen.this);
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
			addData();

		}

		public String[] getAllPricesSlideLines() {
			try {
				JSONObject jsonResponse = new JSONObject(jsonResult);
				JSONArray jsonMainNode = jsonResponse
						.optJSONArray(JSON_ARRAY);
				sweetsNames = new String[jsonMainNode.length()];
				prices = new String[jsonMainNode.length()];

				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
					sweetsNames[i] = jsonChildNode.optString(GET_NAME);
					prices[i] = jsonChildNode.optString(GET_PRICE);
				}
				return prices;
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "Error" + e.toString(),
						Toast.LENGTH_SHORT).show();
			}

			return prices;
		}
	}
	private void createSweets() {
		ArrayList<String> tmpSweetNames = new ArrayList<String>();
		ArrayList<String> tmpSweetPrice = new ArrayList<String>();
		ArrayList<String> tmpSweetQuantity = new ArrayList<String>();
		
		for(int i=0;i<chkSweets.length;i++){
			
				if(chkSweets[i].isChecked()){
					tmpSweetNames.add(chkSweets[i].getText().toString());
					tmpSweetPrice.add(prices[i]);
					tmpSweetQuantity.add(btnbtnQuantity[i].getText().toString().trim());
				}
		}
		
		Globals.sweetsRecord.add(new Sweets(tmpSweetNames, tmpSweetPrice, tmpSweetQuantity));
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
		txtViewDescription.setText("Sweets");
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