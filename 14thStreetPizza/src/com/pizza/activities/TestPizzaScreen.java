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

import com.pizza.dao.Drinks;
import com.pizza.interfaces.BaseInterface;
import com.pizza.utilities.Constants;
import com.pizza.utilities.Globals;
import com.pizza.utilities.Helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class TestPizzaScreen extends Activity implements BaseInterface {
	private String JSON_ARRAY = "drinks";
	private String GET_NAME = "name";
	private String GET_PRICE = "price";

	String jsonResult = null;
	String drinksNames[] = null;
	String prices[] = null;

	TableLayout tl;
	TableRow tr;
	TextView[] valueTV;
	CheckBox chckBox;
	CheckBox[] chkDrinks;
	RadioButton[] rdPizza;
	Spinner[] spinner;

	Button btnNext;
	Button btnReset;
	Button btnAddOther;

	RadioGroup radiogroup;
	// Images
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
			Toast.makeText(getApplicationContext(), "Your are on this Menu",
					Toast.LENGTH_LONG).show();
			return true;

		case R.id.sweets:

			startActivity(new Intent(getApplicationContext(),
					SweetsScreen.class));
			finish();
			return true;

		case R.id.deals:

			return true;

		case R.id.vieworder:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/** This function add the data to the table **/
	public void addData() {
		chkDrinks = new CheckBox[drinksNames.length];
		rdPizza = new RadioButton[drinksNames.length];
		
		spinner = new Spinner[drinksNames.length];
		valueTV = new TextView[drinksNames.length];

		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, Globals.arrQuantity);

		for (int i = 0; i < drinksNames.length; i++) {
			/** Create a TableRow dynamically **/
			tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			/** Creating a TextView to add to the row **/
			rdPizza[i] = new RadioButton(this);
			rdPizza[i].setText(drinksNames[i]);
			rdPizza[i].setId(i);
			// chckBox.setTextColor(Color.RED);
			rdPizza[i].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			rdPizza[i].setTextSize(18);
			rdPizza[i].setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			radiogroup.addView(rdPizza[i]);
			tr.addView(radiogroup); // Adding textView to tablerow.
			
			/** Creating another textview **/
			valueTV[i] = new TextView(this);
			valueTV[i].setText(prices[i]);
			// valueTV.setTextColor(Color.GREEN);
			valueTV[i].setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			valueTV[i].setPadding(5, 5, 5, 5);
			valueTV[i].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			tr.addView(valueTV[i]); // Adding textView to tablerow.

			spinner[i] = new Spinner(this);
			spinner[i].setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			spinner[i].setPadding(5, 5, 5, 5);
			// spinner[i].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			spinner[i].setAdapter(spinnerArrayAdapter);
			tr.addView(spinner[i]);

			// Add the TableRow to the TableLayout
			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}

	@Override
	public void setReferences() {

		btnNext = (Button) findViewById(R.id.btnnext);
		btnReset = (Button) findViewById(R.id.btnreset);
		btnAddOther = (Button) findViewById(R.id.btnaddanother);
		imgHeader = (ImageView) findViewById(R.id.imgheader);
		radiogroup = new RadioGroup(getApplicationContext());
		 
		
	}

	@Override
	public void loadUi() {

		imgHeader.setImageDrawable(getResources().getDrawable(
				R.drawable.header_drinks));
		new getPricesSlideLines()
				.execute(new String[] { Constants.SERVICE_DRINKS });
	}

	@Override
	public void setEventHandlers() {

		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Helper.getInstance().loadAnimationWithSound(
						getApplicationContext(), R.anim.fade_in, btnNext);
				createDrink();
				startActivity(new Intent(TestPizzaScreen.this, SweetsScreen.class));
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
		
	
	}

	// Async Task to access the web
	private class getPricesSlideLines extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog = null;

		@Override
		protected void onPreExecute() {

			progressDialog = new ProgressDialog(TestPizzaScreen.this);
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
				JSONArray jsonMainNode = jsonResponse.optJSONArray(JSON_ARRAY);
				drinksNames = new String[jsonMainNode.length()];
				prices = new String[jsonMainNode.length()];

				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
					drinksNames[i] = jsonChildNode.optString(GET_NAME);
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

	private void createDrink() {
		ArrayList<String> tmpdrinksNames = new ArrayList<String>();
		ArrayList<String> tmpdrinksPrice = new ArrayList<String>();
		ArrayList<String> tmpdrinksQuantity = new ArrayList<String>();

		for (int i = 0; i < drinksNames.length; i++) {

			if (chkDrinks[i].isChecked()) {
				tmpdrinksNames.add(chkDrinks[i].getText().toString());
				tmpdrinksPrice.add(prices[i]);
				tmpdrinksQuantity.add((String) spinner[i].getSelectedItem());
			}
		}
		Globals.drinksRecord.add(new Drinks(tmpdrinksNames, tmpdrinksPrice,
				tmpdrinksQuantity));

	}

}