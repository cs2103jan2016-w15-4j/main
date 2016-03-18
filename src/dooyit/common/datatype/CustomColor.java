package dooyit.common.datatype;

import java.awt.Color;

public class CustomColor {
	public float r; // red
	public float g; // green
	public float b; // blue

	public CustomColor(float r, float g, float b) {
		this.r = r / 255;
		this.g = g / 255;
		this.b = b / 255;
	}

	public static final CustomColor BLACK = new CustomColor(40, 40, 40);
	public static final CustomColor BLUE = new CustomColor(26, 214, 253);
	public static final CustomColor CYAN = new CustomColor(82, 237, 199);
	public static final CustomColor GREY = new CustomColor(219, 221, 222);
	public static final CustomColor GREEN = new CustomColor(135, 252, 125);
	public static final CustomColor MAGENTA = new CustomColor(239, 77, 182);
	public static final CustomColor PINK = new CustomColor(255, 73, 129);
	public static final CustomColor RED = new CustomColor(255, 58, 84);
	public static final CustomColor YELLOW = new CustomColor(255, 204, 0);
	public static final CustomColor WHITE = new CustomColor(247, 247, 247);

	public Color getColor(){
		return new Color(r, g, b);
	}
	
	@Override
	public boolean equals(Object o) {
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
