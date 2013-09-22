package com.pizza.dao;

import java.util.ArrayList;

/**
 * @author Faraz
 *
 * This class will track whole the sweets
 */
public class Sweets {
	   
		private ArrayList<String> sweetsNames;
		private ArrayList<String> sweetsPrices;
		private ArrayList<String> sweetsQuantity;
		
		
		public Sweets() {
		
		}
		
		public Sweets(ArrayList<String> sweetsNames, ArrayList<String> sweetsPrices,ArrayList<String> sweetsQuantity)  {
			
			this.sweetsNames = sweetsNames;
			this.sweetsPrices = sweetsPrices;
			this.sweetsQuantity = sweetsQuantity;
		}

		public ArrayList<String> getsweetsNames() {
			return sweetsNames;
		}

		public void setsweetsNames(ArrayList<String> sweetsNames) {
			this.sweetsNames = sweetsNames;
		}

		public ArrayList<String> getsweetsPrices() {
			return sweetsPrices;
		}

		public void setsweetsPrices(ArrayList<String> sweetsPrices) {
			this.sweetsPrices = sweetsPrices;
		}

		public ArrayList<String> getsweetsQuantity() {
			return sweetsQuantity;
		}

		public void setsweetsQuantity(ArrayList<String> sweetsQuantity) {
			this.sweetsQuantity = sweetsQuantity;
		}
			
		
		
}
