package dooyit.logic;

import java.util.ArrayList;

public class CategoryManager {
	ArrayList<Category> categories;
	ColourManager colourManager;
	
	public CategoryManager(){
		colourManager = new ColourManager();
		categories = new ArrayList<Category>();
		setDefaultCategories();
	}
	
	private void setDefaultCategories(){
		addCategory("school");
		addCategory("Entertainment");
	}
	
	public Category addCategory(String name){
		Category category = new Category(name, colourManager.pickRandomColour());
		categories.add(category);
		return category;
	}
	
	public Category addCategory(String name, Colour colour){
		Category category = new Category(name, colour);
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
