package com.pizza.dao;

import java.util.ArrayList;

/**
 * @author Faraz
 *
 * This class will track whole the slideline
 */
public class SideLines {
	   
		private ArrayList<String> slideLinesNames;
		private ArrayList<String> slideLinesPrices;
		private ArrayList<String> slideLinesQuantity;
		public SideLines() {
		
		}
		
		public SideLines(ArrayList<String> slideLinesNames, ArrayList<String> slideLinesPrices,ArrayList<String> slideLinesQuantity)  {
			
			this.slideLinesNames = slideLinesNames;
			this.slideLinesPrices = slideLinesPrices;
			this.slideLinesQuantity = slideLinesQuantity;
		}

		public ArrayList<String> getSlideLinesNames() {
			return slideLinesNames;
		}

		public void setSlideLinesNames(ArrayList<String> slideLinesNames) {
			this.slideLinesNames = slideLinesNames;
		}

		public ArrayList<String> getSlideLinesPrices() {
			return slideLinesPrices;
		}

		public void setSlideLinesPrices(ArrayList<String> slideLinesPrices) {
			this.slideLinesPrices = slideLinesPrices;
		}

		public ArrayList<String> getSlideLinesQuantity() {
			return slideLinesQuantity;
		}

		public void setSlideLinesQuantity(ArrayList<String> slideLinesQuantity) {
			this.slideLinesQuantity = slideLinesQuantity;
		}
			
		
		
}
