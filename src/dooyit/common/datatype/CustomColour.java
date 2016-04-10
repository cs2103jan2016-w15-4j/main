//@@author A0126356E
package dooyit.common.datatype;

import dooyit.common.Constants;
import javafx.scene.paint.Color;

public class CustomColour {
	
	private float r; // red
	private float g; // green
	private float b; // blue
	private String name; // colour name
	
	private static final String COLOUR_TO_STRING = "Colour: %1$s, %2$s, %3$s";
	
	public CustomColour(String name, float r, float g, float b) {
		this.name = name;
		this.r = r / Constants.MAX_RGB;
		this.g = g / Constants.MAX_RGB;
		this.b = b / Constants.MAX_RGB;
	}

	public static final CustomColour BLACK = new CustomColour(Constants.BLACK_COLOUR, 40, 40, 40);
	public static final CustomColour BLUE = new CustomColour(Constants.BLUE_COLOUR, 26, 214, 253);
	public static final CustomColour CYAN = new CustomColour(Constants.CYAN_COLOUR, 82, 237, 199);
	public static final CustomColour GREY = new CustomColour(Constants.GREY_COLOUR, 219, 221, 222);
	public static final CustomColour GREEN = new CustomColour(Constants.GREEN_COLOUR, 135, 252, 125);
	public static final CustomColour MAGENTA = new CustomColour(Constants.MAGENTA_COLOUR, 239, 77, 182);
	public static final CustomColour PINK = new CustomColour(Constants.PINK_COLOUR, 255, 73, 129);
	public static final CustomColour RED = new CustomColour(Constants.RED_COLOUR, 255, 58, 84);
	public static final CustomColour YELLOW = new CustomColour(Constants.YELLOW_COLOUR, 255, 204, 0);
	public static final CustomColour WHITE = new CustomColour(Constants.WHITE_COLOUR, 247, 247, 247);
	public static final CustomColour ORANGE = new CustomColour(Constants.ORANGE_COLOUR, 255, 69, 0);

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
		return String.format(COLOUR_TO_STRING, r, g, g);
	}

}
