package dooyit.logic.core;

public class Colour {
	public float r; // red
	public float g; // green
	public float b; // blue
	
	public Colour(float r, float g, float b){
		this.r = r / 255;
		this.g = g / 255;
		this.b = b / 255;
	}
	
	public static final Colour BLACK = new Colour(40, 40, 40); 
	public static final Colour BLUE = new Colour(26, 214, 253); 
	public static final Colour CYAN = new Colour(82, 237, 199); 
	public static final Colour GREY = new Colour(219, 221, 222); 
	public static final Colour GREEN = new Colour(135, 252, 125); 
	public static final Colour MAGENTA = new Colour(239, 77, 182); 
	public static final Colour PINK = new Colour(255, 73, 129); 
	public static final Colour RED = new Colour(255, 58, 84);  
	public static final Colour YELLOW = new Colour(255, 204, 0); 
	public static final Colour WHITE = new Colour(247, 247, 247);
	
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Colour){
			Colour colour = (Colour)o;
			return r == colour.r && g == colour.g && b == colour.b;
		}
		return false;
	}
	
	@Override 
	public String toString(){
		return "Colour: " + r + "," + g +"," + b;
	}
	
	public String toSavableString() {
		return this.r + " " + this.g + " " + this.b;
	}
}
