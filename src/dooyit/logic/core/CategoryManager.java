package dooyit.logic.core;

import java.util.ArrayList;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.Colour;

public class CategoryManager {
	ArrayList<Category> categories;
	ColourManager colourManager;
	
	public CategoryManager(){
		colourManager = new ColourManager();
		categories = new ArrayList<Category>();
		setDefaultCategories();
	}
	
	private void setDefaultCategories(){
		addCategory("School");
		addCategory("Entertainment");
	}
	
	public Category addCategory(String categoryName){
		if(findTask(categoryName) != null){
			return null;
		}
		
		Category category = new Category(categoryName, colourManager.pickRandomColour());
		categories.add(category);
		return category;
	}
	
	public Category addCategory(String categoryName, Colour colour){
		if(findTask(categoryName) != null){
			return null;
		}
		
		Category category = new Category(categoryName, colour);
		categories.add(category);
		return category;
	}
	
	public boolean containsCategory(String categoryName){
		for(int i=0; i<categories.size(); i++){
			if(categories.get(i).getName().equals(categoryName)){
				return true;
			}
		}
		return false;
	}
	
	public Category findTask(String categoryName){
		for(int i=0; i<categories.size(); i++){
			if(categories.get(i).getName().equals(categoryName)){
				return categories.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Category> getCategoryList(){
		return categories;
	}
	
}
