//@@author A0126356E
package dooyit.common.datatype;

import javafx.scene.paint.Color;

public class CustomColour {
	private float r; // red
	private float g; // green
	private float b; // blue
	private String name; // colour name
	
	public CustomColour(String name, float r, float g, float b) {
		this.name = name;
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
	}

	public static final CustomColour BLACK = new CustomColour("black", 40, 40, 40);
	public static final CustomColour BLUE = new CustomColour("blue", 0, 104, 139);
	public static final CustomColour CYAN = new CustomColour("cyan", 82, 237, 199);
	public static final CustomColour GREY = new CustomColour("grey", 219, 221, 222);
	public static final CustomColour GREEN = new CustomColour("green", 0, 139, 0);
	public static final CustomColour MAGENTA = new CustomColour("magenta", 239, 77, 182);
	public static final CustomColour PINK = new CustomColour("pink", 255, 73, 129);
	public static final CustomColour RED = new CustomColour("red", 255, 58, 84);
	public static final CustomColour YELLOW = new CustomColour("yellow", 255, 204, 0);
	public static final CustomColour WHITE = new CustomColour("white", 247, 247, 247);
	public static final CustomColour ORANGE = new CustomColour("orange", 255, 69, 0);

	public Color getColor(){
		return Color.color(r, g, b);
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof String){
			String colourString = (String)o;
			return name.equals(colourString);
		}
		
		if (o instanceof CustomColour) {
			CustomColour colour = (CustomColour) o;
			return r == colour.r && g == colour.g && b == colour.b;
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "Colour: " + r + "," + g + "," + b;
	}

	public String toSavableString() {
		return this.r + " " + this.g + " " + this.b;
	}
	
	
}
