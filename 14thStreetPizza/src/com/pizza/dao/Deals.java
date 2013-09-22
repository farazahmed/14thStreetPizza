package com.pizza.dao;

public class Deals {

	private String dealName;
	private String dealPizzaType;
	private String dealSideLine;
	private String dealDrink;
	private String dealPrice;
	
	public Deals() {
		
	}
	
	public Deals(String dealName, String dealPizzaType, String dealSideLine, String dealDrink, String dealPrice) {
			
			this.dealName = dealName;
			this.dealPizzaType = dealPizzaType;
			this.dealSideLine =dealSideLine;
			this.dealDrink = dealDrink;
			this.dealPrice = dealPrice;
	}
	
	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealPizzaType() {
		return dealPizzaType;
	}

	public void setDealPizzaType(String dealPizzaType) {
		this.dealPizzaType = dealPizzaType;
	}

	public String getDealSideLine() {
		return dealSideLine;
	}

	public void setDealSideLine(String dealSideLine) {
		this.dealSideLine = dealSideLine;
	}

	public String getDealDrink() {
		return dealDrink;
	}

	public void setDealDrink(String dealDrink) {
		this.dealDrink = dealDrink;
	}

	public String getDealPrice() {
		return dealPrice;
	}

	public void setDealPrice(String dealPrice) {
		this.dealPrice = dealPrice;
	}

}
