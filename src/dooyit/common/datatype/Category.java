package dooyit.common.datatype;

public class Category {
	private String name;
	
	private Colour colour;
	
	public Category(String name) {
		this.name = name;
		this.colour = Colour.BLUE;
	}
	
	public Category(String name, Colour colour) {
		this.name = name;
		this.colour = colour;
	}

	public Category(String name, float r, float g, float b){
		this.name = name;
		this.colour = new Colour(r, g, b);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Colour getColour(){
		return colour;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Category){
			Category category = (Category)o;
			return getName().toLowerCase().equals(category.getName().toLowerCase()) ;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return name + " " + colour.toString();
	}
}