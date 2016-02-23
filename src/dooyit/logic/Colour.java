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
	
	public static Colour black(){
		return new Colour(0, 0, 0);
	}
	
	public static Colour cyan(){
		return new Colour(0, 1, 1);
	}
	
	public static Colour gray(){
		return new Colour(0.5f, 0.5f, 0.5f);
	}
	
	public static Colour grey(){
		return new Colour(0.5f, 0.5f, 0.5f);
	}
	
	public static Colour green(){
		return new Colour(0, 1, 0);
	}
	
	public static Colour magenta(){
		return new Colour(1, 0, 1);
	}
	
	public static Colour pink(){
		return new Colour(1.f, 0.753f, 0.796f);
	}
	
	public static Colour red(){
		return new Colour(1, 0, 0);
	}
	
	public static Colour white(){
		return new Colour(1, 1, 1);
	}
	
	public static Colour yellow(){
		return new Colour(1.f, 0.92f, 0.016f);
	}
	
	@Override 
	public String toString(){
		return "Colour: " + r + "," + g +"," + b;
	}
}
