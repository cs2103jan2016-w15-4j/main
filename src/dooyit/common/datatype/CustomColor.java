package dooyit.common.datatype;

import javafx.scene.paint.Color;

public class CustomColor {
	public float r; // red
	public float g; // green
	public float b; // blue
	public String name; // colour name
	
	public CustomColor(String name, float r, float g, float b) {
		this.name = name;
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
	}

	public static final CustomColor BLACK = new CustomColor("black", 40, 40, 40);
	public static final CustomColor BLUE = new CustomColor("blue", 26, 214, 253);
	public static final CustomColor CYAN = new CustomColor("cyan", 82, 237, 199);
	public static final CustomColor GREY = new CustomColor("grey", 219, 221, 222);
	public static final CustomColor GREEN = new CustomColor("green", 135, 252, 125);
	public static final CustomColor MAGENTA = new CustomColor("magenta", 239, 77, 182);
	public static final CustomColor PINK = new CustomColor("pink", 255, 73, 129);
	public static final CustomColor RED = new CustomColor("red", 255, 58, 84);
	public static final CustomColor YELLOW = new CustomColor("yellow", 255, 204, 0);
	public static final CustomColor WHITE = new CustomColor("white", 247, 247, 247);

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
		
		if (o instanceof CustomColor) {
			CustomColor colour = (CustomColor) o;
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
