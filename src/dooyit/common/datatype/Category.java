package dooyit.common.datatype;

public class Category {
	private String name;

	private CustomColor colour;

	public Category(String name) {
		this.name = name;
		this.colour = CustomColor.BLUE;
	}

	public Category(String name, CustomColor colour) {
		this.name = name;
		this.colour = colour;
	}

	public Category(String name, float r, float g, float b) {
		this.name = name;
		this.colour = new CustomColor(r, g, b);
	}

	public String getName() {
		return this.name;
	}

	public CustomColor getColour() {
		return colour;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Category) {
			Category category = (Category) o;
			return getName().toLowerCase().equals(category.getName().toLowerCase());
		}
		return false;
	}

	@Override
	public String toString() {
		return name + " " + colour.toString();
	}
}
