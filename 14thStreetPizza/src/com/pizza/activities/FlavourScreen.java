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

import com.pizza.dao.ExtraMenu;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

public class FlavourScreen extends Activity implements BaseInterface,
		OnClickListener {

	private String JSON_ARRAY = "flavour";
	private String JSON_ARRAY_EXTRA_MENU = "extra";

	private String GET_NAME = "name";

	String jsonResult = null;
	String jsonResultExtra = null;

	String flavourNames[] = null;
	String extraMenuNames[] = null;

	AlertDialog dlgExtraMenu;

	TableLayout tl;
	TableRow tr;
	CheckBox chckBox;
	CheckBox[] chkFlavours;
	Button btnVeggies;
	Button btnReset;
	Button btnAddOther;
	final ArrayList seletedItems = new ArrayList();

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
			startActivity(new Intent(getApplicationContext(), DealsScreen.class));
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

	/** This function add the data to the table **/
	public void addData() {
		addHeaders();
		chkFlavours = new CheckBox[flavourNames.length];
		for (int i = 0; i < flavourNames.length; i++) {
			/** Create a TableRow dynamically **/
			tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			/** Creating a TextView to add to the row **/
			chkFlavours[i] = new CheckBox(this);
			chkFlavours[i].setText(flavourNames[i]);
			chkFlavours[i].setId(i);
			chkFlavours[i].setOnClickListener(this);
			// chckBox.setTextColor(Color.RED);
			chkFlavours[i].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			chkFlavours[i].setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

			tr.addView(chkFlavours[i]); // Adding textView to tablerow.
			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			/** Creating another textview **/

		}
	}

	@Override
	public void setReferences() {

		// Buttons
		btnVeggies = (Button) findViewById(R.id.btnnext);
		btnVeggies.setText("Veggies");
		btnReset = (Button) findViewById(R.id.btnreset);
		btnAddOther = (Button) findViewById(R.id.btnaddanother);
		imgHeader = (ImageView) findViewById(R.id.imgheader);
	}

	@Override
	public void loadUi() {
		imgHeader.setImageDrawable(getResources().getDrawable(
				R.drawable.header_pizza));
		new GetFlavourNames()
				.execute(new String[] { Constants.SERVICE_FLAVOUR });
	}

	@Override
	public void setEventHandlers() {

		btnVeggies.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Helper.getInstance().loadAnimationWithSound(
						getApplicationContext(), R.anim.fade_in, btnVeggies);
				createExtraMenu();
				startActivity(new Intent(FlavourScreen.this,
						VeggiesScreen.class));
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
				// startActivity(new Intent(FlavourScreen.this,
				// HomeScreen.class));
				// finish();
				// loadExtraMenu1();
				new GetExtraMenu()
						.execute(new String[] { Constants.SERVICE_EXTRA_MENU });
			}
		});
	}

	// Async Task to access the web
	private class GetFlavourNames extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog = null;

		@Override
		protected void onPreExecute() {

			progressDialog = new ProgressDialog(FlavourScreen.this);
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
			getAllFlavourNames();
			addData();
		}

		public void getAllFlavourNames() {
			try {
				JSONObject jsonResponse = new JSONObject(jsonResult);
				JSONArray jsonMainNode = jsonResponse.optJSONArray(JSON_ARRAY);
				flavourNames = new String[jsonMainNode.length()];

				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
					flavourNames[i] = jsonChildNode.optString(GET_NAME);

				}

			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "Error" + e.toString(),
						Toast.LENGTH_SHORT).show();
			}

		}

	}

	public void extraMenu() {
		AlertDialog dialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select");
		builder.setMultiChoiceItems(extraMenuNames, null,
				new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int indexSelected, boolean isChecked) {
						if (isChecked) {
							// If the user checked the item, add it to the
							// selected items
							seletedItems.add(indexSelected);
						} else if (seletedItems.contains(indexSelected)) {
							// Else, if the item is already in the array, remove
							// it
							seletedItems.remove(Integer.valueOf(indexSelected));
						}
					}
				})
				// Set the action buttons
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// Your code when user clicked on Cancel

							}
						});

		dialog = builder.create();// AlertDialog dialog; create like this
									// outside onClick
		dialog.show();
	}

	// Async Task to access the web
	private class GetExtraMenu extends AsyncTask<String, Void, String> {
		ProgressDialog progressDialog = null;

		@Override
		protected void onPreExecute() {

			progressDialog = new ProgressDialog(FlavourScreen.this);
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
				jsonResultExtra = inputStreamToString(entity.getContent())
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
			getAllExtraMenuNames();
			extraMenu();
		}

		public void getAllExtraMenuNames() {
			try {
				JSONObject jsonResponse = new JSONObject(jsonResultExtra);
				JSONArray jsonMainNode = jsonResponse
						.optJSONArray(JSON_ARRAY_EXTRA_MENU);
				extraMenuNames = new String[jsonMainNode.length()];

				for (int i = 0; i < jsonMainNode.length(); i++) {
					JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
					extraMenuNames[i] = jsonChildNode.optString(GET_NAME);
				}

			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "Error" + e.toString(),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void createExtraMenu() {
		ArrayList<String> tmpExtraMenuNames = new ArrayList<String>();

		for (int i = 0; i < seletedItems.size(); i++) {

			tmpExtraMenuNames
					.add(extraMenuNames[(Integer) seletedItems.get(i)]);
		}
		Globals.extraMenuRecord.add(new ExtraMenu(tmpExtraMenuNames));
	}

	@Override
	public void onClick(View v) {

		for (int i = 0; i < chkFlavours.length; i++) {
			CheckBox chk = (CheckBox) v;
			if (i != chk.getId()) {
				chkFlavours[i].setChecked(false);

			}
		}

	}

	private void addHeaders() {
		tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		TextView txtViewDescription = new TextView(this);
		txtViewDescription.setText("Flavours");
		// valueTV.setTextColor(Color.GREEN);
		txtViewDescription.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		txtViewDescription.setPadding(5, 5, 5, 5);
		txtViewDescription.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		txtViewDescription.setTextColor(Color.BLUE);
		txtViewDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tr.addView(txtViewDescription);

		tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	}

}