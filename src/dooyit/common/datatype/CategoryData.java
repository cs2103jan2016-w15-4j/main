package dooyit.common.datatype;

public class CategoryData {
	private String name;
	private String color;

	public CategoryData(String categoryName, String colorName) {
		this.name = categoryName;
		this.color = colorName;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}
}
