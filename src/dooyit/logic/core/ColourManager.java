package dooyit.logic.core;

import java.util.ArrayList;
import java.util.Random;

import dooyit.common.datatype.CustomColor;

public class ColourManager {
	// Colour[] availableColours;
	ArrayList<CustomColor> usedColours;
	Random random;
	ArrayList<CustomColor> recommendedColour;
	ArrayList<CustomColor> colourPool;

	public ColourManager() {
		random = new Random();
		// availableColours = new Colour[10];

		recommendedColour = new ArrayList<CustomColor>();

		recommendedColour.add(CustomColor.BLUE);
		recommendedColour.add(CustomColor.CYAN);
		recommendedColour.add(CustomColor.GREEN);
		recommendedColour.add(CustomColor.MAGENTA);
		recommendedColour.add(CustomColor.PINK);
		recommendedColour.add(CustomColor.RED);
		recommendedColour.add(CustomColor.YELLOW);
		recommendedColour.add(CustomColor.GREY);

		colourPool = new ArrayList<CustomColor>(recommendedColour);
	}

	public CustomColor pickRandomColour() {

		if (colourPool.size() == 0) {
			colourPool = new ArrayList<CustomColor>(recommendedColour);
		}

		return colourPool.remove(random.nextInt(colourPool.size()));
	}

}
