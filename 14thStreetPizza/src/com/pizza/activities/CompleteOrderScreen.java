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

import com.pizza.dao.Deals;
import com.pizza.dao.Drinks;
import com.pizza.dao.SideLines;
import com.pizza.dao.Sweets;
import com.pizza.dao.Veggies;
import com.pizza.interfaces.BaseInterface;
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

public class CompleteOrderScreen extends Activity implements BaseInterface, OnClickListener {
	@SuppressWarnings("unused")
	private String TAG = "saucescreen";
	private String JSON_ARRAY="sauce";
	private String GET_NAME="name";
	
	String jsonResult = null;
	String sauceNames[] = {"Mild","Hot","Extra Hot"};
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
	
	// TextView 
	
	TextView txtNetPrice;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.completeorder_screen);
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
        	Toast.makeText(getApplicationContext(), "Your are on this Menu",
					Toast.LENGTH_LONG).show();
        case R.id.share:
        	Toast.makeText(getApplicationContext(), "Not Implemented",
					Toast.LENGTH_LONG).show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }  
	/** This function add the data to the table **/
	public void addData() {
		Globals.noOfItems = 1;
		addHeaders();
		addSideLines();
		addDrinks();
		addSweets();
		addDeals();
		calculatePrice();
	}
	
	private void addHeaders() {
		tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		TextView txtViewItem = new TextView(this);
		txtViewItem.setText("Item");
		// valueTV.setTextColor(Color.GREEN);
		txtViewItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		txtViewItem.setPadding(5, 5, 5, 5);
		txtViewItem.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		txtViewItem.setTextColor(Color.BLUE);
		tr.addView(txtViewItem);
		
		
		TextView txtViewDescription = new TextView(this);
		txtViewDescription.setText("Description");
		// valueTV.setTextColor(Color.GREEN);
		txtViewDescription.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		txtViewDescription.setPadding(5, 5, 5, 5);
		txtViewDescription.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		txtViewDescription.setTextColor(Color.BLUE);
		tr.addView(txtViewDescription);
	
		TextView txtViewQuantity = new TextView(this);
		txtViewQuantity.setText("Quantity");
		// valueTV.setTextColor(Color.GREEN);
		txtViewQuantity.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		txtViewQuantity.setPadding(5, 5, 5, 5);
		txtViewQuantity.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		txtViewQuantity.setTextColor(Color.BLUE);
		tr.addView(txtViewQuantity);
		
		TextView txtViewPrice = new TextView(this);
		txtViewPrice.setText("Price");
		// valueTV.setTextColor(Color.GREEN);
		txtViewPrice.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		txtViewPrice.setPadding(5, 5, 5, 5);
		txtViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		txtViewPrice.setTextColor(Color.BLUE);
		tr.addView(txtViewPrice);
		
		tl.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	}
	private void addSideLines() {
		SideLines objSideLines;
		TextView txtViewItem;
		TextView txtViewDescription;
		TextView txtViewQuantity;
		TextView txtViewPrice;
		
		if(Globals.sideLinesRecord.size()>0){
			
			for(int i=0;i<Globals.sideLinesRecord.size();i++){
					
					objSideLines = Globals.sideLinesRecord.get(i);
					
					for(int j=0;j<objSideLines.getSlideLinesNames().size();j++){
						tr = new TableRow(this);
						tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewItem= new TextView(CompleteOrderScreen.this);
						txtViewItem.setText(Globals.noOfItems+"");
						// valueTV.setTextColor(Color.GREEN);
						txtViewItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewItem.setPadding(5, 5, 5, 5);
						txtViewItem.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewItem); 
						txtViewDescription= new TextView(CompleteOrderScreen.this);
						txtViewDescription.setText(objSideLines.getSlideLinesNames().get(j));
						// valueTV.setTextColor(Color.GREEN);
						txtViewDescription.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewDescription.setPadding(5, 5, 5, 5);
						txtViewDescription.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewDescription); // Adding textView to tablerow.
						
						txtViewQuantity= new TextView(CompleteOrderScreen.this);
						txtViewQuantity.setText(objSideLines.getSlideLinesQuantity().get(j));
						// valueTV.setTextColor(Color.GREEN);
						txtViewQuantity.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewQuantity.setPadding(5, 5, 5, 5);
						txtViewQuantity.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewQuantity); // Adding textView to tablerow.
						
						txtViewPrice= new TextView(CompleteOrderScreen.this);
						txtViewPrice.setText("Rs "+objSideLines.getSlideLinesPrices().get(j));
						// valueTV.setTextColor(Color.GREEN);
						txtViewPrice.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewPrice.setPadding(5, 5, 5, 5);
						txtViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewPrice); // Adding textView to tablerow.
						tl.addView(tr, new TableLayout.LayoutParams(
								LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
						Globals.noOfItems ++;
				}
			}	
		}
		
		tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	 }
	
	private void addSweets() {
		Sweets objSweets;
		TextView txtViewItem;
		TextView txtViewDescription;
		TextView txtViewQuantity;
		TextView txtViewPrice;
		
		
		if(Globals.sweetsRecord.size()>0){
			
			for(int i=0;i<Globals.sweetsRecord.size();i++){
					
					objSweets = Globals.sweetsRecord.get(i);
					
					for(int j=0;j<objSweets.getsweetsNames().size();j++){
						tr = new TableRow(this);
						tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewItem= new TextView(CompleteOrderScreen.this);
						txtViewItem.setText(Globals.noOfItems+"");
						// valueTV.setTextColor(Color.GREEN);
						txtViewItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewItem.setPadding(5, 5, 5, 5);
						txtViewItem.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewItem); 
						txtViewDescription= new TextView(CompleteOrderScreen.this);
						txtViewDescription.setText(objSweets.getsweetsNames().get(j));
						// valueTV.setTextColor(Color.GREEN);
						txtViewDescription.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewDescription.setPadding(5, 5, 5, 5);
						txtViewDescription.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewDescription); // Adding textView to tablerow.
						
						txtViewQuantity= new TextView(CompleteOrderScreen.this);
						txtViewQuantity.setText(objSweets.getsweetsQuantity().get(j));
						// valueTV.setTextColor(Color.GREEN);
						txtViewQuantity.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewQuantity.setPadding(5, 5, 5, 5);
						txtViewQuantity.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewQuantity); // Adding textView to tablerow.
						
						txtViewPrice= new TextView(CompleteOrderScreen.this);
						txtViewPrice.setText("Rs "+objSweets.getsweetsPrices().get(j));
						// valueTV.setTextColor(Color.GREEN);
						txtViewPrice.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewPrice.setPadding(5, 5, 5, 5);
						txtViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewPrice); // Adding textView to tablerow.
						tl.addView(tr, new TableLayout.LayoutParams(
								LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
						Globals.noOfItems ++;
				}
			}	
		}
		tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	 }
	
	
	private void addDrinks() {
		
		Drinks objDrinks;
		TextView txtViewItem;
		TextView txtViewDescription;
		TextView txtViewQuantity;
		TextView txtViewPrice;
		
		if(Globals.drinksRecord.size()>0){
			
			for(int i=0;i<Globals.drinksRecord.size();i++){
					
					objDrinks = Globals.drinksRecord.get(i);
					
					for(int j=0;j<objDrinks.getDrinksNames().size();j++){
						tr = new TableRow(this);
						tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						
						txtViewItem= new TextView(CompleteOrderScreen.this);
						txtViewItem.setText(Globals.noOfItems+"");
						// valueTV.setTextColor(Color.GREEN);
						txtViewItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewItem.setPadding(5, 5, 5, 5);
						txtViewItem.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewItem); 
						txtViewDescription= new TextView(CompleteOrderScreen.this);
						txtViewDescription.setText(objDrinks.getDrinksNames().get(j));
						// valueTV.setTextColor(Color.GREEN);
						txtViewDescription.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewDescription.setPadding(5, 5, 5, 5);
						txtViewDescription.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewDescription); // Adding textView to tablerow.
						
						txtViewQuantity= new TextView(CompleteOrderScreen.this);
						txtViewQuantity.setText(objDrinks.getDrinksQuantity().get(j));
						// valueTV.setTextColor(Color.GREEN);
						txtViewQuantity.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewQuantity.setPadding(5, 5, 5, 5);
						txtViewQuantity.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewQuantity); // Adding textView to tablerow.
						
						txtViewPrice= new TextView(CompleteOrderScreen.this);
						txtViewPrice.setText("Rs "+objDrinks.getDrinksPrices().get(j));
						// valueTV.setTextColor(Color.GREEN);
						txtViewPrice.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewPrice.setPadding(5, 5, 5, 5);
						txtViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewPrice); // Adding textView to tablerow.
						tl.addView(tr, new TableLayout.LayoutParams(
								LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
						Globals.noOfItems ++;
					}
			}	
		}
		
		tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	 }
	
	private void addDeals() {
		
		Deals objDeals;
		TextView txtViewItem;
		TextView txtViewDescription;
		TextView txtViewQuantity;
		TextView txtViewPrice;
		
		if(Globals.dealsRecord.size()>0){
			
			for(int i=0;i<Globals.dealsRecord.size();i++){
					
				objDeals = Globals.dealsRecord.get(i);
						
						tr = new TableRow(this);
						tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewItem= new TextView(CompleteOrderScreen.this);
						txtViewItem.setText(Globals.noOfItems+"");
						// valueTV.setTextColor(Color.GREEN);
						txtViewItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewItem.setPadding(5, 5, 5, 5);
						txtViewItem.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewItem); 
						txtViewDescription= new TextView(CompleteOrderScreen.this);
						txtViewDescription.setText(objDeals.getDealName());
						// valueTV.setTextColor(Color.GREEN);
						txtViewDescription.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewDescription.setPadding(5, 5, 5, 5);
						txtViewDescription.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewDescription); // Adding textView to tablerow.
						
						txtViewQuantity= new TextView(CompleteOrderScreen.this);
						txtViewQuantity.setText(Integer.toString(1));
						// valueTV.setTextColor(Color.GREEN);
						txtViewQuantity.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewQuantity.setPadding(5, 5, 5, 5);
						txtViewQuantity.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewQuantity); // Adding textView to tablerow.
						
						txtViewPrice= new TextView(CompleteOrderScreen.this);
						txtViewPrice.setText("Rs "+objDeals.getDealPrice());
						// valueTV.setTextColor(Color.GREEN);
						txtViewPrice.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
						txtViewPrice.setPadding(5, 5, 5, 5);
						txtViewPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
						tr.addView(txtViewPrice); // Adding textView to tablerow.
						tl.addView(tr, new TableLayout.LayoutParams(
								LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
						Globals.noOfItems ++;
			}	
		}
		
		tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	 }
	@Override
	public void setReferences() {

		// Buttons
		btnFlavour  = (Button)findViewById(R.id.btnnext1);
		btnFlavour.setText("Flavour");
		btnReset= (Button)findViewById(R.id.btnreset1);
		btnAddOther  = (Button)findViewById(R.id.btnaddanother1);
		imgHeader = (ImageView)findViewById(R.id.imgheader);
		txtNetPrice = (TextView)findViewById(R.id.tvnettotalprice1);
	
	}

	@Override
	public void loadUi() {
		imgHeader.setImageDrawable(getResources().getDrawable(R.drawable.header_price));
		addData();
//		new GetSauceNames()
//				.execute(new String[] { Constants.SERVICE_SAUCE });
	}

	
	@Override
	public void setEventHandlers() {
		
	  btnFlavour.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Helper.getInstance().loadAnimationWithSound(getApplicationContext(), R.anim.fade_in, btnFlavour);
				createSauce();
				startActivity(new Intent(CompleteOrderScreen.this, FlavourScreen.class));
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
				startActivity(new Intent(CompleteOrderScreen.this, HomeScreen.class));
				finish();
			}
		});
	}


	private void createSauce() {
		ArrayList<String> tmpVeggiesNames = new ArrayList<String>();
		
		for(int i=0;i<chkSauceScreen.length;i++){
			
				if(chkSauceScreen[i].isChecked()){
					tmpVeggiesNames.add(chkSauceScreen[i].getText().toString());
				}
		}
		Globals.veggiesRecord.add(new Veggies(tmpVeggiesNames));
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
	
	private void calculatePrice() {
		
			float salesTax =  0;
			float sideLinesPrice = getSideLinesPrice();
			sideLinesPrice = sideLinesPrice + salesTax;
			//txtNetPrice.setText("Rs " + sideLinesPrice+"");

			float drinksPrice = getDrinksPrice();
			drinksPrice = drinksPrice + salesTax;
			sideLinesPrice = sideLinesPrice + drinksPrice;
			//txtNetPrice.setText("Rs " + sideLinesPrice+"");
			
			float sweetsPrice = getSweetsPrice();
			sweetsPrice = sweetsPrice + salesTax;
			sideLinesPrice = sideLinesPrice + sweetsPrice;

			//txtNetPrice.setText("Rs " + sweetsPrice+"");
			
			float dealsPrice = getDealsPrice();
			dealsPrice = dealsPrice + salesTax;
			sideLinesPrice = sideLinesPrice + dealsPrice;
			salesTax = (float) (sideLinesPrice*0.16);
			sideLinesPrice = sideLinesPrice +salesTax;
			txtNetPrice.setText("Rs " + sideLinesPrice+"");
			
			
	}
	private int getSideLinesPrice() {
		SideLines objSideLines;

		int priceSideline = 0;
		
		if(Globals.sideLinesRecord.size()>0){
			
			for(int i=0;i<Globals.sideLinesRecord.size();i++){

				    objSideLines = Globals.sideLinesRecord.get(i);
					for(int j=0;j<objSideLines.getSlideLinesNames().size();j++){
						
						priceSideline = priceSideline + Integer.valueOf(objSideLines.getSlideLinesQuantity().get(j)) * Integer.valueOf(objSideLines.getSlideLinesPrices().get(j)); 
				}
			}	
		}

		return priceSideline;
	}
	
	private int getDrinksPrice() {
		Drinks objDrinks;

		int priceDrinks = 0;
		
		if(Globals.drinksRecord.size()>0){
			
			for(int i=0;i<Globals.drinksRecord.size();i++){

				    objDrinks = Globals.drinksRecord.get(i);
					for(int j=0;j<objDrinks.getDrinksNames().size();j++){
						
						priceDrinks = priceDrinks + Integer.valueOf(objDrinks.getDrinksQuantity().get(j)) * Integer.valueOf(objDrinks.getDrinksPrices().get(j)); 
				}
			}	
		}

		return priceDrinks;
	}
	
	private int getSweetsPrice(){
		Sweets objSweets;

		int priceSweets= 0;
		
		if(Globals.sweetsRecord.size()>0){
			
			for(int i=0;i<Globals.sweetsRecord.size();i++){

				    objSweets = Globals.sweetsRecord.get(i);
					for(int j=0;j<objSweets.getsweetsNames().size();j++){

						priceSweets = priceSweets + Integer.valueOf(objSweets.getsweetsQuantity().get(j)) * Integer.valueOf(objSweets.getsweetsPrices().get(j)); 
				}
			}	
		}

		return priceSweets;
	}
private int  getDealsPrice() {
		
		Deals objDeals;
		int dealsPrice = 0;
		if(Globals.dealsRecord.size()>0){
			
			for(int i=0;i<Globals.dealsRecord.size();i++){
					
				objDeals = Globals.dealsRecord.get(i);
				dealsPrice = dealsPrice + Integer.valueOf(objDeals.getDealPrice());
			}	
		}
		
		return dealsPrice; 
}

	

		

}