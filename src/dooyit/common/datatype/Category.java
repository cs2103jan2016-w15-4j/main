//@@author A0126356E
package dooyit.common.datatype;

import javafx.scene.paint.Color;

public class Category {
	private String name;

	private CustomColor customColor;

	public Category(String name) {
		this.name = name;
	}

	public Category(String name, CustomColor customColour) {
		this.name = name;
		this.customColor = customColour;
	}

	public String getName() {
		return this.name;
	}

	public Color getColour() {
		return customColor.getColor();
	}
	
	public CustomColor getCustomColour() {
		return customColor;
	}

	public String getCustomColourName() {
		return customColor.getName();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof String) {
			String categoryName = (String) o;
			return getName().toLowerCase().equals(categoryName.toLowerCase());
		} else if (o instanceof Category) {
			Category category = (Category) o;
			return getName().toLowerCase().equals(category.getName().toLowerCase());
		}
		return false;
	}

	@Override
	public String toString() {
		return name + " " + customColor.toString();
	}
	
	public CategoryData convertToData(){
		CategoryData categoryData;
		categoryData = new CategoryData(name, getCustomColourName());
		return categoryData;
	}
}
