package com.pizza.activities;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.os.AsyncTask;

/**
 * @author Faraz
 * 
 * This is the Main splash screen
 * 
 */
public class SplashScreen extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new WaitAndLoad().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	 private class WaitAndLoad extends AsyncTask<String, Void, String> {

         @Override
         protected String doInBackground(String... params) {
              
        	 	try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

               return "Executed";
         }      

         @Override
         protected void onPostExecute(String result) {
               
        	  startActivity(new Intent(getApplicationContext(), HomeScreen.class));
        	  finish();
         }

         @Override
         protected void onPreExecute() {
         }

         @Override
         protected void onProgressUpdate(Void... values) {
         }
   }   
		
}
