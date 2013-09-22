package com.pizza.dao;

import java.util.ArrayList;

/**
 * @author Faraz
 *
 * This class will track whole the Pizza
 */
public class Pizza {
	   
		private ArrayList<String> pizzaNames;
		private ArrayList<String> pizzaPrices;
		private ArrayList<String> pizzaQuantity;
		
		public Pizza() {
		
		}
		
		public Pizza(ArrayList<String> pizzaNames, ArrayList<String> pizzaPrices, ArrayList<String> pizzaQuantity)  {
			
			this.setpizzaNames(pizzaNames);
			this.setpizzaPrices(pizzaPrices);
			this.setpizzaQuantity(pizzaQuantity);
		}

		public ArrayList<String> getpizzaNames() {
			return pizzaNames;
		}

		public void setpizzaNames(ArrayList<String> pizzaNames) {
			this.pizzaNames = pizzaNames;
		}

		public ArrayList<String> getpizzaPrices() {
			return pizzaPrices;
		}

		public void setpizzaPrices(ArrayList<String> pizzaPrices) {
			this.pizzaPrices = pizzaPrices;
		}

		public ArrayList<String> getpizzaQuantity() {
			return pizzaQuantity;
		}

		public void setpizzaQuantity(ArrayList<String> pizzaQuantity) {
			this.pizzaQuantity = pizzaQuantity;
		}
		
}
