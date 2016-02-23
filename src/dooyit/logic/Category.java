package dooyit.logic;

public class Category {
	private String name;
	
	private Colour colour;
	
	public Category(String name) {
		this.name = name;
		
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
	
	public String toString() {
		return name + " " + colour.toString();
	}
}
