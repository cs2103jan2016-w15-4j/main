package dooyit.storage;

import dooyit.common.datatype.Category;
import dooyit.common.datatype.Colour;

public class CategoryStorageFormat {
	private String name;
	private String colour;

	CategoryStorageFormat(Category category) {
		this.name = category.getName();
		this.colour = category.getColour().toSavableString();
	}
}
