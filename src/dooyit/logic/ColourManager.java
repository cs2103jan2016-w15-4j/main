//@@author A0126356E
package dooyit.logic;

import java.util.ArrayList;
import java.util.Random;

import dooyit.common.datatype.CustomColour;

public class ColourManager {
	ArrayList<CustomColour> usedColours;
	Random random;
	ArrayList<CustomColour> recommendedColours;
	ArrayList<CustomColour> availableColours;
	ArrayList<CustomColour> colourPool;

	public ColourManager() {
		random = new Random();
		availableColours = new ArrayList<CustomColour>();
		availableColours.add(CustomColour.BLACK);
		availableColours.add(CustomColour.BLUE);
		availableColours.add(CustomColour.CYAN);
		availableColours.add(CustomColour.GREY);
		availableColours.add(CustomColour.GREEN);
		availableColours.add(CustomColour.MAGENTA);
		availableColours.add(CustomColour.PINK);
		availableColours.add(CustomColour.RED);
		availableColours.add(CustomColour.YELLOW);
		availableColours.add(CustomColour.WHITE);
		availableColours.add(CustomColour.ORANGE);

		recommendedColours = new ArrayList<CustomColour>();
		recommendedColours.add(CustomColour.BLUE);
		recommendedColours.add(CustomColour.CYAN);
		recommendedColours.add(CustomColour.GREEN);
		recommendedColours.add(CustomColour.MAGENTA);
		recommendedColours.add(CustomColour.PINK);
		recommendedColours.add(CustomColour.RED);
		recommendedColours.add(CustomColour.YELLOW);
		recommendedColours.add(CustomColour.ORANGE);
		// recommendedColours.add(CustomColor.GREY);

		colourPool = new ArrayList<CustomColour>(recommendedColours);
	}

	public CustomColour pickRandomCustomColour() {
		if (colourPool.size() == 0) {
			colourPool = new ArrayList<CustomColour>(recommendedColours);
		}
		return colourPool.remove(random.nextInt(colourPool.size()));
	}

	public boolean contains(String name) {
		for (CustomColour customColor : availableColours) {
			if (customColor.equals(name)) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(CustomColour customColor) {
		boolean hasCustomColor = availableColours.contains(customColor);
		return hasCustomColor;
	}

	public CustomColour find(String customColorString) {
		for (CustomColour customColor : availableColours) {
			if (customColor.equals(customColorString)) {
				return customColor;
			}
		}
		return null;
	}

}
