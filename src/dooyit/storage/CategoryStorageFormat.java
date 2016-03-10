package dooyit.storage;

import dooyit.logic.core.Category;
import dooyit.logic.core.Colour;

public class CategoryStorageFormat {
	private String name;
	private String colour;
	
	CategoryStorageFormat(Category category) {
		this.name = category.getName();
		this.colour = category.getColour().toSavableString();
	}
}
