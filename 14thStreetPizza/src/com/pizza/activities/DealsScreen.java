package com.pizza.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pizza.dao.Deals;
import com.pizza.interfaces.BaseInterface;
import com.pizza.utilities.Constants;
import com.pizza.utilities.Globals;
import com.pizza.utilities.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class DealsScreen extends Activity implements BaseInterface {
	private final  String JSON_ARRAY = "deals";
	private final String GET_NAME = "name";
	private final String GET_PIZZA_TYPE = "pizza";
	private final String GET_SIDELINE = "slideline";
	private final String GET_DRINK = "drink";
	private final String GET_PRICE = "price";

	String jsonResult = null;
	String dealsNames[] = null;
	String dealsPizzaType[] = null;
	String dealsSideline[] = null;
	String dealsDrink[] = null;
	String prices[] = null;

	TableLayout tl;
	TableRow tr;
	TextView[] valueTV;
	CheckBox chckBox;
	CheckBox[] chkDrinks;
	Spinner[] spinner;

	Button btnNext;
	Button btnReset;
	Button btnAddOther;

	// Images
	ImageView imgHeader = null;
	
	 private ListView lstDeals ;  
	 private ArrayAdapter<String> listAdapter ; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deals_screen);
		tl = (TableLayout) findViewById(R.id.maintable);
		setReferences();
		setEventHandlers();
		loadUi();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

	/**
	 * Event Handling for Individual menu item selected Identify single menu
	 * item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
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
			return true;

		case R.id.sweets:

			startActivity(new Intent(getApplicationContext(),
					SweetsScreen.class));
			finish();
			return true;

		case R.id.deals:
			Toast.makeText(getApplicationContext(), "Your are on this Menu",
					Toast.LENGTH_LONG).show();
			return true;

		case R.id.vieworder:
			startActivity(new Intent(getApplicationContext(),
					CompleteOrderScreen.class));
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/** This function add the data to the table **/
	public void addData() {
//		chkDrinks = new CheckBox[dealsNames.length];
//		spinner = new Spinner[dealsNames.length];
//		valueTV = new TextView[dealsNames.length];
		 ArrayList<String> dealsList= new ArrayList<String>();  
		 dealsList.addAll(Arrays.asList(dealsNames)); 
		 listAdapter = new ArrayAdapter<String>(this, R.layout.row, dealsList); 
		 // Set the ArrayAdapter as the ListView's adapter.  
		 lstDeals.setAdapter( listAdapter );   
		
	}

	@Override
	public void setReferences() {

		btnNext = (Button) findViewById(R.id.btnnext);
		btnReset = (Button) findViewById(R.id.btnreset);
		btnAddOther = (Button) findViewById(R.id.btnaddanother);
		imgHeader = (ImageView) findViewById(R.id.imgheader);
		lstDeals = (ListView) findViewById( R.id.mainListView );  
	}

	@Override
	public void loadUi() {

		imgHeader.setImageDrawable(getResources().getDrawable(
				R.drawable.header_deals));
		new getDealsRecords()
				.execute(new String[] { Constants.SERVICE_DEALS});
	}

	@Override
	public void setEventHandlers() {

		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Helper.getInstance().loadAnimationWithSound(
						getApplicationContext(), R.anim.fade_in, btnNext);
				//createDeal(index)
				startActivity(new Intent(DealsScreen.this, CompleteOrderScreen.class));
				finish();
			}

		});

		btnReset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Helper.getInstance().loadAnimationWithSound(
						getApplicationContext(), R.anim.fade_in, btnReset);

			}
		});

		btnAddOther.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Helper.getInstance().loadAnimationWithSound(
						getApplicationContext(), R.anim.fade_in, btnAddOther);
			}
		});


		lstDeals.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				 showDeal(position);
			}
			
		});
	}

	// Async Task to access the web
	private class getDealsRecords extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog = null;

		@Override
		protected void onPreExecute() {

			progressDialog = new ProgressDialog(DealsScreen.this);
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

		public void getAllPricesSlideLines() {
			try {
				JSONObject jsonResponse = new JSONObject(jsonResult);
				JSONArray jsonMainNode = jsonResponse.optJSONArray(JSON_ARRAY);
				dealsNames = new String[jsonMainNode.length()];
				dealsPizzaType = new String[jsonMainNode.length()];
				dealsSideline = new String[jsonMainNode.length()];
				dealsDrink= new String[jsonMainNode.length()];
				prices = new String[jsonMainNode.length()];

				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
					dealsNames[i] = jsonChildNode.optString(GET_NAME);
					dealsPizzaType[i] = jsonChildNode.optString(GET_PIZZA_TYPE);
					dealsSideline[i] = jsonChildNode.optString(GET_SIDELINE);
					dealsDrink[i] = jsonChildNode.optString(GET_DRINK);
					prices[i] = jsonChildNode.optString(GET_PRICE);
				}
				
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "Error" + e.toString(),
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	private void createDeal(int index) {
		
		Globals.dealsRecord.add(new Deals(dealsNames[index], dealsPizzaType[index], dealsSideline[index], dealsDrink[index], prices[index]));
		Toast.makeText(DealsScreen.this, dealsNames[index] + " added"  , Toast.LENGTH_SHORT).show();

	}
	public void showDeal(final int index){
			
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(DealsScreen.this);
		 
        
        alertDialog.setTitle(dealsNames[index]);
 
        // Setting Dialog Message
        alertDialog.setMessage(dealsPizzaType[index] + "\n" + dealsSideline[index] + "\n" + dealsDrink[index] + "\nPrice: " + prices[index]);
 
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.icon_deals);
        alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	dialog.cancel();
            	createDeal(index);
            }
			
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
		
	}

}