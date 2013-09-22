package com.pizza.dao;

/**
 * @author Faraz
 *
 * this is baby class info, we will change according to our need this is just created to give you some idea how we will use it.
 */
public class Pizza1 {
	
		
		private String slice;
		private int slicePrice;
		private String half;
		private int halfPrice;
		private String splitHalf;
		private int splitHalfPrice;
		private String halfandHalf;
		private int halfandHalfPrice;
		private String full;
		private int fullPrice;
	    private int noOfPizza = 0;	
		
		
		
	
		/**
		 * Default constructor
		 * will be used in database handler class
		 */
		public Pizza1() {
			
			
		}
		
		
		/**
		 * @param slice
		 * @param slicePrice
		 * @param half
		 * @param halfPrice
		 * @param splitHalf
		 * @param splitHalfPrice
		 * @param halfandHalf
		 * @param halfandHalfPrice
		 * @param full
		 * @param fullPrice
		 * @param noOfPizza
		 */
		public Pizza1(String slice, int slicePrice, String half, int halfPrice, String splitHalf, int splitHalfPrice, String halfandHalf, int halfandHalfPrice, String full, int fullPrice, int noOfPizza){
			this.slice = slice;
			this.slicePrice = slicePrice;
			this.half = half;
			this.halfPrice = halfPrice;
			this.splitHalf = splitHalf; 
			this.splitHalfPrice = splitHalfPrice;
			this.halfandHalf = halfandHalf;
			this.halfandHalfPrice = halfandHalfPrice;
			this.full = full;
			this.fullPrice = fullPrice;
			this.noOfPizza = noOfPizza;
		
		}
		public String getSlice() {
			return slice;
		}

		public void setSlice(String slice) {
			this.slice = slice;
		}

		public int getSlicePrice() {
			return slicePrice;
		}

		public void setSlicePrice(int slicePrice) {
			this.slicePrice = slicePrice;
		}

		public String getHalf() {
			return half;
		}

		public void setHalf(String half) {
			this.half = half;
		}

		public int getHaflfePrice() {
			return halfPrice;
		}

		public void setHaflfePrice(int haflfePrice) {
			this.halfPrice = haflfePrice;
		}

		public int getSplitHalfPrice() {
			return splitHalfPrice;
		}

		public void setSplitHalfPrice(int splitHalfPrice) {
			this.splitHalfPrice = splitHalfPrice;
		}

		public String getSplitHalf() {
			return splitHalf;
		}

		public void setSplitHalf(String splitHalf) {
			this.splitHalf = splitHalf;
		}

		public int getsHalfandHalfPrice() {
			return halfandHalfPrice;
		}

		public void setsHalfandHalfPrice(int sHalfandHalfPrice) {
			this.halfandHalfPrice = sHalfandHalfPrice;
		}

		public String getHalfandHalf() {
			return halfandHalf;
		}

		public void setHalfandHalf(String halfandHalf) {
			this.halfandHalf = halfandHalf;
		}

		public String getFull() {
			return full;
		}
		
		public void setFull(String full) {
			this.full = full;
		}

		public int getFullPrice() {
			return fullPrice;
		}

		public void setFullPrice(int fullPrice) {
			this.fullPrice = fullPrice;
		}

		public int getNoOfPizza() {
			return noOfPizza;
		}

		public void setNoOfPizza(int noOfPizza) {
			this.noOfPizza = noOfPizza;
		}

}
