package dooyit.logic.core;
import java.util.ArrayList;
import java.util.Random;

import dooyit.common.datatype.Colour;

public class ColourManager {
	//Colour[] availableColours;
	ArrayList<Colour> usedColours;
	Random random;
	ArrayList<Colour> recommendedColour;
	ArrayList<Colour> colourPool;
	
	public ColourManager(){
		random = new Random();
		//availableColours = new Colour[10];
		
		recommendedColour = new ArrayList<Colour>();
		
		recommendedColour.add(Colour.BLUE);
		recommendedColour.add(Colour.CYAN);
		recommendedColour.add(Colour.GREEN);
		recommendedColour.add(Colour.MAGENTA);
		recommendedColour.add(Colour.PINK);
		recommendedColour.add(Colour.RED);
		recommendedColour.add(Colour.YELLOW);
		recommendedColour.add(Colour.GREY);
		
		colourPool = new ArrayList<Colour>(recommendedColour);
	}
	
	public Colour pickRandomColour(){
		
		if(colourPool.size() == 0){
			colourPool = new ArrayList<Colour>(recommendedColour);
		}
		
		return colourPool.remove(random.nextInt(colourPool.size()));
	}
	
}