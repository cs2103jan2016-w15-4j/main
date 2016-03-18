package dooyit.common.datatype;

import javafx.scene.paint.Color;

public class Category {
	private String name;

	private CustomColor customColor;

	public Category(String name) {
		this.name = name;
		this.customColor = CustomColor.BLUE;
	}

	public Category(String name, CustomColor colour) {
		this.name = name;
		this.customColor = colour;
	}

	public Category(String name, float r, float g, float b) {
		this.name = name;
		this.customColor = new CustomColor(r, g, b);
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
}
