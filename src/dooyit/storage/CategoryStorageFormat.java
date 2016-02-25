package dooyit.storage;

import dooyit.logic.Category;
import dooyit.logic.Colour;

public class CategoryStorageFormat {
	private String name;
	private String colour;
	
	CategoryStorageFormat(Category category) {
		this.name = category.getName();
		this.colour = category.getColour().toSavableString();
	}
}
