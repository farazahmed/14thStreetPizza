package com.pizza.utilities;

import java.util.ArrayList;

import com.pizza.dao.Deals;
import com.pizza.dao.Drinks;
import com.pizza.dao.ExtraMenu;
import com.pizza.dao.Pizza;
import com.pizza.dao.Pizza1;
import com.pizza.dao.Sauce;
import com.pizza.dao.SideLines;
import com.pizza.dao.Sweets;
import com.pizza.dao.Veggies;

/**
 * @author Faraz
 *	
 * This class will contain all the global variables like DB Adaptors, Global variables etc.
 * 
 */
public class Globals {
	
	// singleton object
		public static Globals instance = null;

		/**
		 * @return Globals object.
		 */
		public static Globals getInstance() {

			if (instance == null) {

				return new Globals();
			}
			return instance;

		}

		public Globals() {
			
			
		}
		public static int noOfItems = 1;
		
		public static final String[] arrQuantity = new String[] { "1", "2", "3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20" };
		
		public static String selectedMenu = "";
		//public static ArrayList<Pizza1> pizzaRecord = new ArrayList<Pizza1>();
		public static ArrayList<Pizza> pizzaRecord = new ArrayList<Pizza>();
		public static ArrayList<SideLines> sideLinesRecord = new ArrayList<SideLines>();
		public static ArrayList<Drinks> drinksRecord = new ArrayList<Drinks>();
		public static ArrayList<Sauce> sauceRecord = new ArrayList<Sauce>();
		public static ArrayList<Veggies> veggiesRecord = new ArrayList<Veggies>();
		public static ArrayList<Sweets> sweetsRecord = new ArrayList<Sweets>();
		public static ArrayList<Deals> dealsRecord = new ArrayList<Deals>();
		public static ArrayList<ExtraMenu> extraMenuRecord = new ArrayList<ExtraMenu>();
		
		
		// set global variable to check wether offline mode of online mode
		public static boolean offlineMode = false;
		
		
		
		
}
