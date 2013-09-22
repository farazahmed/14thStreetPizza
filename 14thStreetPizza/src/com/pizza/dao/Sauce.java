package com.pizza.dao;

/**
 * @author Faraz
 *	
 *	this class will track of Sauce
 */
public class Sauce {
		
	
		private String sauceName;
		
		public Sauce() {
			
		}

		public Sauce(String sauceName){
			this.setSauceName(sauceName);
		}

		public String getSauceName() {
			return sauceName;
		}

		public void setSauceName(String sauceName) {
			this.sauceName = sauceName;
		}
		
}
