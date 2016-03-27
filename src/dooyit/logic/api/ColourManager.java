package dooyit.logic.api;

import java.util.ArrayList;
import java.util.Random;

import dooyit.common.datatype.CustomColor;

public class ColourManager {
	ArrayList<CustomColor> usedColours;
	Random random;
	ArrayList<CustomColor> recommendedColours;
	ArrayList<CustomColor> availableColours;
	ArrayList<CustomColor> colourPool;
	
	public ColourManager() {
		random = new Random();
		availableColours = new ArrayList<CustomColor>();
		availableColours.add(CustomColor.BLACK);
		availableColours.add(CustomColor.BLUE);
		availableColours.add(CustomColor.CYAN);
		availableColours.add(CustomColor.GREY);
		availableColours.add(CustomColor.GREEN);
		availableColours.add(CustomColor.MAGENTA);
		availableColours.add(CustomColor.PINK);
		availableColours.add(CustomColor.RED);
		availableColours.add(CustomColor.YELLOW);
		availableColours.add(CustomColor.WHITE);
		
		recommendedColours = new ArrayList<CustomColor>();
		recommendedColours.add(CustomColor.BLUE);
		recommendedColours.add(CustomColor.CYAN);
		recommendedColours.add(CustomColor.GREEN);
		recommendedColours.add(CustomColor.MAGENTA);
		recommendedColours.add(CustomColor.PINK);
		recommendedColours.add(CustomColor.RED);
		recommendedColours.add(CustomColor.YELLOW);
		recommendedColours.add(CustomColor.GREY);

		colourPool = new ArrayList<CustomColor>(recommendedColours);
	}

	public CustomColor pickRandomCustomColour() {
		if (colourPool.size() == 0) {
			colourPool = new ArrayList<CustomColor>(recommendedColours);
		}
		return colourPool.remove(random.nextInt(colourPool.size()));
	}
	
	public boolean contains(String name){
		for(CustomColor customColor : availableColours){
			if(customColor.equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(CustomColor customColor){
		boolean hasCustomColor = availableColours.contains(customColor);
		return hasCustomColor;
	}
	
	
	
	public CustomColor find(String customColorString){
		for(CustomColor customColor : availableColours){
			if(customColor.equals(customColorString)){
				return customColor;
			}
		}
		return null;
	}
	
}
