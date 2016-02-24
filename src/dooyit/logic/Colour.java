package dooyit.logic;

public class Colour {
	public float r; // red
	public float g; // green
	public float b; // blue
	
	public Colour(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public static final Colour BLACK = new Colour(0, 0, 0); 
	public static final Colour CYAN = new Colour(0, 1, 1); 
	public static final Colour GREY = new Colour(0.5f, 0.5f, 0.5f); 
	public static final Colour GREEN = new Colour(0, 1, 0); 
	public static final Colour MAGENTA = new Colour(1, 0, 1); 
	public static final Colour PINK = new Colour(1.f, 0.753f, 0.796f); 
	public static final Colour RED = new Colour(1, 0, 0); 
	public static final Colour WHITE = new Colour(1, 1, 1); 
	public static final Colour YELLOW = new Colour(1.f, 0.92f, 0.016f); 
	public static final Colour BLUE = new Colour(0, 0, 1); 
	
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
}
