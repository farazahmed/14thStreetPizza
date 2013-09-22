package com.pizza.dao;

import java.util.ArrayList;

/**
 * @author Faraz
 *
 * This class will track whole the Drinks
 */
public class Drinks {
	   
		private ArrayList<String> drinksNames;
		private ArrayList<String> drinksPrices;
		private ArrayList<String> drinksQuantity;
		
		public Drinks() {
		
		}
		
		public Drinks(ArrayList<String> drinksNames, ArrayList<String> drinksPrices, ArrayList<String> drinksQuantity)  {
			
			this.setDrinksNames(drinksNames);
			this.setDrinksPrices(drinksPrices);
			this.setDrinksQuantity(drinksQuantity);
		}

		public ArrayList<String> getDrinksNames() {
			return drinksNames;
		}

		public void setDrinksNames(ArrayList<String> drinksNames) {
			this.drinksNames = drinksNames;
		}

		public ArrayList<String> getDrinksPrices() {
			return drinksPrices;
		}

		public void setDrinksPrices(ArrayList<String> drinksPrices) {
			this.drinksPrices = drinksPrices;
		}

		public ArrayList<String> getDrinksQuantity() {
			return drinksQuantity;
		}

		public void setDrinksQuantity(ArrayList<String> drinksQuantity) {
			this.drinksQuantity = drinksQuantity;
		}
		
}
