package com.pizza.dao;

import java.util.ArrayList;

/**
 * @author Faraz
 *
 *	this class will track the Veggies
 */
public class Veggies {
		
			private ArrayList<String> veggiesNames;	
			public Veggies() {
				
			}
			public Veggies(ArrayList<String> veggiesNames){
				
					this.veggiesNames = veggiesNames;
			}
			public ArrayList<String> getVeggiesNames() {
				return veggiesNames;
			}
			public void setVeggiesNames(ArrayList<String> veggiesNames) {
				this.veggiesNames = veggiesNames;
			}
}
