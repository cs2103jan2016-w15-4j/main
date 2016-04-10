//@@author A0126356E
package dooyit.logic.api;

import java.util.ArrayList;
import java.util.Random;

import dooyit.common.datatype.CustomColour;

/**
 * The colour manager will store all the possible colours tha dooyit has. It
 * also has pickRecommendedColour to get a random colour that are more suitable
 * and visible for the UI
 * 
 * @author limtaeu
 *
 */
public class ColourManager {
	ArrayList<CustomColour> usedColours;
	Random random;
	ArrayList<CustomColour> recommendedColours;
	ArrayList<CustomColour> availableColours;
	ArrayList<CustomColour> colourPool;

	public ColourManager() {
		addAvailableColours();
		addRecommendedColours();
		init();
	}

	/**
	 * initialise the random generator and colourpool with recommendedColours
	 */
	public void init() {
		random = new Random();
		colourPool = new ArrayList<CustomColour>(recommendedColours);
	}

	/**
	 * add recommendedColours into recommendedColours arraylist
	 */
	public void addRecommendedColours() {
		recommendedColours = new ArrayList<CustomColour>();
		recommendedColours.add(CustomColour.BLUE);
		recommendedColours.add(CustomColour.CYAN);
		recommendedColours.add(CustomColour.GREEN);
		recommendedColours.add(CustomColour.PINK);
		recommendedColours.add(CustomColour.RED);
		recommendedColours.add(CustomColour.YELLOW);
		recommendedColours.add(CustomColour.ORANGE);
	}

	/**
	 * add all availableColours into availableColours arrayList
	 */
	public void addAvailableColours() {
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
