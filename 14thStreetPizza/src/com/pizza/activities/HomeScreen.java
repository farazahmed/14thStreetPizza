package com.pizza.activities;

import com.pizza.interfaces.BaseInterface;
import com.pizza.utilities.Constants;
import com.pizza.utilities.Globals;
import com.pizza.utilities.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class HomeScreen extends Activity implements BaseInterface {

	Button btnOrderPizza;
	Button btnOrderSliderLines;
	Button btnOrderDrinks;
	Button btnOrderSweets;
	Button btnOrderDeals;
	Button btnPanic;
	Button btnInfo;
	Button btndeveloper;
	// Images
		ImageView imgHeader = null;
	public HomeScreen() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		boolean isInternetConnected = isNetworkConnected();
		if(isInternetConnected){
			
			setReferences();
			setEventHandlers();
			loadUi();
		}
		else {
			
			showAlert(Constants.TITLE_WARNING, Constants.CONNECTION_ERROR);
			Globals.offlineMode = true;
			setReferences();
			setEventHandlers();
			loadUi();
			
		}
	}

	@Override
	public void setReferences() {

		btnOrderPizza = (Button) findViewById(R.id.btnorderpizza);
		btnOrderSliderLines = (Button) findViewById(R.id.btnorderslidelines);
		btnOrderDrinks = (Button) findViewById(R.id.btnorderdrinks);
		btnOrderSweets = (Button) findViewById(R.id.btnordersweets);
		btnOrderDeals = (Button) findViewById(R.id.btnorderdeals);
		btnPanic = (Button) findViewById(R.id.btnpanic);
		btnInfo = (Button) findViewById(R.id.btninfo);
		btndeveloper = (Button) findViewById(R.id.btndeveloper);
		imgHeader = (ImageView) findViewById(R.id.imgheader);

	}

	@Override
	public void loadUi() {
		
		imgHeader.setImageDrawable(getResources().getDrawable(
				R.drawable.header_home));
		Helper.getInstance().loadAnimation(getApplicationContext(),
				R.anim.fade_in, btnOrderPizza);
		Helper.getInstance().loadAnimation(getApplicationContext(),
				R.anim.fade_in, btnOrderSliderLines);
		Helper.getInstance().loadAnimation(getApplicationContext(),
				R.anim.fade_in, btnOrderDrinks);
		Helper.getInstance().loadAnimation(getApplicationContext(),
				R.anim.fade_in, btnOrderSweets);
		Helper.getInstance().loadAnimation(getApplicationContext(),
				R.anim.fade_in, btnOrderDeals);
		Helper.getInstance().loadAnimation(getApplicationContext(),
				R.anim.fade_in, btnPanic);
		Helper.getInstance().loadAnimation(getApplicationContext(),
				R.anim.fade_in, btndeveloper);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pizza.interfaces.BaseInterface#setEventHandlers()
	 * 
	 * Event listeners
	 */
	@Override
	public void setEventHandlers() {

		btnOrderPizza.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Helper.getInstance().loadAnimationWithSound(
						getApplicationContext(), R.anim.fade_in, btnOrderPizza);
				startActivity(new Intent(getApplicationContext(),
						PizzaSizeScreen.class));
				finish();
				
//				startActivity(new Intent(getApplicationContext(),
//						TestPizzaScreen.class));
					
			}
		});

		btnOrderSliderLines.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Helper.getInstance().loadAnimationWithSound(
						getApplicationContext(), R.anim.fade_in,
						btnOrderSliderLines);
				startActivity(new Intent(getApplicationContext(),
						SideLinesScreen.class));
				finish();
			}
		});

		btnOrderDrinks.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Helper.getInstance()
						.loadAnimationWithSound(getApplicationContext(),
								R.anim.fade_in, btnOrderDrinks);
				startActivity(new Intent(getApplicationContext(),
						DrinksScreen.class));
				finish();
			}
		});

		btnOrderSweets.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Helper.getInstance()
						.loadAnimationWithSound(getApplicationContext(),
								R.anim.fade_in, btnOrderSweets);
				startActivity(new Intent(getApplicationContext(),
						SweetsScreen.class));
				finish();
			}
		});

		btnOrderDeals.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

//				Helper.getInstance().loadAnimationWithSound(
//						getApplicationContext(), R.anim.fade_in, btnOrderDeals);
//				startActivity(new Intent(getApplicationContext(),
//						DealsScreen.class));
//				finish();
				Helper.getInstance().loadAnimationWithSound(
						getApplicationContext(), R.anim.fade_in, btnOrderDeals);
				startActivity(new Intent(getApplicationContext(),
						DealsScreen.class));
				finish();
			}
		});
		
		btnInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				
				showAlert(Constants.TITLE_INFO, Constants.MESSAGE_NOT_IMPLEMENTED);
			
			}
			
		});
		
		btndeveloper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showAlert(Constants.TITLE_INFO, Constants.MESSAGE);
			
			}
			
		});
		
		
		
	}
	private boolean isNetworkConnected() {
  	  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
  	  NetworkInfo ni = cm.getActiveNetworkInfo();
  	  if (ni == null) {
  	   // There are no active networks.
  	   return false;
  	  } else
  	   return true;
  	 }
	 private void showAlert(String title ,String message) {
			
	    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeScreen.this);
	        alertDialog.setTitle(title);
	        alertDialog.setMessage(message);
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
	 private void showMessage() {
			
	    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeScreen.this);
	        alertDialog.setTitle("Info");
	        alertDialog.setMessage("internet is necessary for this application.");
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
