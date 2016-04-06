//@@author A0126356E
package dooyit.common.datatype;

import javafx.scene.paint.Color;

public class Category {
	private String name;

	private CustomColour customColor;

	public Category(String name) {
		this.name = name;
	}

	public Category(String name, CustomColour customColour) {
		this.name = name;
		this.customColor = customColour;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		this.name = newName;
	}

	public Color getColour() {
		return customColor.getColor();
	}

	public CustomColour getCustomColour() {
		return customColor;
	}

	public void setCustomColour(CustomColour customColour){
		this.customColor = customColour;
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

	public CategoryData convertToData() {
		CategoryData categoryData;
		categoryData = new CategoryData(name, getCustomColourName());
		return categoryData;
	}
}
