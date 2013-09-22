package com.pizza.utilities;

/**
 * @author Faraz
 * 
 *         This class will contain all the constants varibles like settings
 *         values or commands etc.
 * 
 */
public class Constants {

	// singleton object
	public static Constants instance = null;

	/**
	 * @return Constants object.
	 */
	public static Constants getInstance() {

		if (instance == null) {

			return new Constants();
		}
		return instance;
	}

	public Constants() {

		
	}
	// Server name
	public static final String SERVER= "http://faraz1990.site11.com/";
	
	// Service names
	
	// get pizza prices data
	public static final String SERVICE_PIZZA_ = SERVER+"pizza.php";
	
	// get pizza prices data
	public static final String SERVICE_PIZZA_PRICE = SERVER+"pizzaservice.php";
	// get slidelines data
	public static final String SERVICE_SLIDELINES = SERVER+"sidelines.php";
	// get drinks data
	public static final String SERVICE_DRINKS = SERVER+"drinks.php";
	// get sweetssomething data
	public static final String SERVICE_SWEETS = SERVER+"sweetssomething.php";
	// get sauce data
	public static final String SERVICE_SAUCE = SERVER+"sauce.php";
	// get flavour data
	public static final String SERVICE_FLAVOUR = SERVER+"flavour.php";
	// get flavour data
	public static final String SERVICE_VEGGIES = SERVER+"veggies.php";
	// get Deals data
	public static final String SERVICE_DEALS = SERVER+"deals.php";
	// get extra menu
	public static final String SERVICE_EXTRA_MENU = SERVER+"extramenu.php";
	
	// Menu Names
	public static final String MENU_ORDER_PIZZA ="orderpizza";
	public static final String MENU_ORDER_SLIDE_LINES ="orderslidelines";
	public static final String MENU_ORDER_DRINKS ="orderdrinks";
	public static final String MENU_ORDER_SWEETS ="ordersweets";
	public static final String MENU_ORDER_DEALS ="orderdeals";
	
	
	// offline mode constants dummy data
	
public static String pizzaPrices[] = {"100", "200", "300", "400","100"};	
	
public static String slidesNames[] = {"A","B","C","D"};
public static String sideLineprices[] = {"100","200", "100","200"};

	
public static String drinksNames[] = {"DRINKS 1","DRINKS 2","DRINKS 3","DRINKS 4"};
public static String drinksPrices[] = {"100","200", "100","200"};

/// continue not completed

public static final String CONNECTION_ERROR = "Internet is necessary for this application.";
public static final String TITLE_WARNING = "Warning";
public static final String TITLE_INFO = "Info";
public static final String MESSAGE = "Developer: Faraz Ahmed \nEmail: fastian.faraz@gmail.com";
public static final String MESSAGE_NOT_IMPLEMENTED = "not implemented yet";


}
