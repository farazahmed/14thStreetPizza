package com.pizza.dao;

import java.util.ArrayList;

public class ExtraMenu {

		private ArrayList<String> extraMenu;
		
		public ExtraMenu() {
			
		}
		public ExtraMenu(ArrayList<String> extraMenu){
			
				this.setExtraMenu(extraMenu);
		}
		public ArrayList<String> getExtraMenu() {
			return extraMenu;
		}
		public void setExtraMenu(ArrayList<String> extraMenu) {
			this.extraMenu = extraMenu;
		}
		
}
